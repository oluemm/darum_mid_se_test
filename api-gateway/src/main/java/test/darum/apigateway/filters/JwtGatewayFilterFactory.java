package test.darum.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.darum.apigateway.dtos.AuthClaims;

@Component
public class JwtGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

  private static final Logger log = LoggerFactory.getLogger(JwtGatewayFilterFactory.class);
  private final WebClient.Builder lbWebClientBuilder;
  private final String authServiceId;

  public JwtGatewayFilterFactory(@LoadBalanced WebClient.Builder lbWebClientBuilder,
                                 @Value("${auth.service.id:AUTH-SERVICE}") String authServiceId) {
    super(Object.class);
    this.lbWebClientBuilder = lbWebClientBuilder;
    this.authServiceId = authServiceId;
  }

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if (token == null || !token.toLowerCase().startsWith("bearer ")) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      String jwtToken = token.substring(7);

      return lbWebClientBuilder.baseUrl("lb://" + authServiceId)
                               .build()
                               .get()
                               // Pass the token as a parameter to the validation endpoint
                               .uri("/auth/validate")
                               .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                               .retrieve()
                               .onStatus(status -> status == HttpStatus.UNAUTHORIZED || status == HttpStatus.FORBIDDEN,
                                         clientResponse -> Mono.error(new SecurityException("Token validation failed:" +
                                                                                                " Invalid/Expired " +
                                                                                                "Token")))
                               // If the token is invalid or expired, the Auth Service should return UNAUTHORIZED (401)
                               .onStatus(status -> status != HttpStatus.OK,
                                         clientResponse -> Mono.error(new SecurityException("Token validation failed")))

                               .bodyToMono(AuthClaims.class)
                               .flatMap(claims -> {
                                 // forward claims to service backend as headers
                                 ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                                                            .header("X-User-Email", claims.getEmail())
                                                                            .header("X-User-Role", claims.getRole())
                                                                            .build();

                                 return chain.filter(exchange.mutate().request(mutatedRequest).build());
                               })
                               // invalid token, network error, 401 response from Auth Service
                               .onErrorResume(SecurityException.class, e -> {
                                 log.error("Error occurred while processing jwt token", e);
                                 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                 return exchange.getResponse().setComplete();
                               })
                               .onErrorResume(e -> {
                                 // Catch other unexpected errors (e.g., service unavailable)
                                 log.error("Something went wrong", e);
                                 exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                 return exchange.getResponse().setComplete();
                               });
    };
  }
}
