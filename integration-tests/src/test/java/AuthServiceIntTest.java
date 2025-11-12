import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthServiceIntTest {

  @BeforeAll
  static void setUp(){
    RestAssured.baseURI = "http://localhost:5000/api";
  }

  @Test
  public void should_ReturnOK_WithValidToken() {
    String loginPayload = """
          {
            "email": "admin@darum.ng",
            "password": "password123"
          }
        """;

    Response response = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .body("token", notNullValue())
        .extract()
        .response();

    System.out.println("Generated Token: " + response.jsonPath().getString("token"));
  }

  @Test
  public void should_ReturnUnauthorized_OnInvalidLogin() {
    String loginPayload = """
          {
            "email": "invalid_user@test.com",
            "password": "wrongpassword"
          }
        """;

    given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(401);
  }

  @Test
  public void validate_Should_ReturnOK_WithValidToken() {
    String loginPayload = """
          {
            "email": "admin@darum.ng",
            "password": "password123"
          }
        """;

    Response response = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .extract()
        .response();

    String token = response.jsonPath().getString("token");

    given()
        .contentType("application/json")
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/auth/validate")
        .then()
        .statusCode(200);
  }
}
