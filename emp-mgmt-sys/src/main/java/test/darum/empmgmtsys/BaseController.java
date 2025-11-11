package test.darum.empmgmtsys;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RefreshScope
@Tag(name = "Api Status", description = "Health Checks")
public class BaseController {
  @Value("${app.name}")
  private String appName;
  @Value("${spring.mvc.servlet.path}")
  private String basePath;

  @GetMapping("configs")
  @Operation(hidden = true)
  public ResponseEntity<?> getAppName() {
    return ResponseEntity.ok(Map.of("appName", appName));
  }

  @GetMapping({"", "/", "/docs"})
  @Operation(hidden = true)
  public RedirectView index() {

    return new RedirectView(basePath + "/swagger-ui/index.html");
  }
}
