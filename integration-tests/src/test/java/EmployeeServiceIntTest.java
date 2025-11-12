import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class EmployeeServiceIntTest {
  @BeforeAll
  static void setUp(){
    RestAssured.baseURI = "http://localhost:5000/api";
  }
}
