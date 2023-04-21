
import com.pm490.PM490.Pm490Application;
import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.User;
import com.pm490.PM490.payload.request.LoginRequest;
import com.pm490.PM490.payload.request.SignupRequest;
import com.pm490.PM490.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pm490Application.class)
public class SignRestTest {

    @Autowired
    UserRepository userRepository;

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI= "http://localhost";
        RestAssured.basePath = "/api/auth";
    }

    @Test
    public void testSignIn() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Temka");
        loginRequest.setPassword(("12345"));

        Response response = given()
                .relaxedHTTPSValidation("TLSv1.2")
                .contentType("application/json")
                .body(loginRequest)
                .post("/signin");


        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.getBody().asString().contains("token"));
    }

    @Test
    public void testSignUp() {
        // Create a new user to register
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("johndoe");
        signupRequest.setPassword("password");
        signupRequest.setEmail("johndoe@example.com");
        signupRequest.setFullName("John Doe");
        signupRequest.setPhone(12345678);
        signupRequest.setRole(Role.CUSTOMER.toString());
        signupRequest.setAddress("123 Main St");

        // Send the signup request and receive the response
        given()
                .contentType("application/json")
                .body(signupRequest)
                .when()
                .post("/signup")
                .then()
                .statusCode(200)
                .body("message", equalTo("User registered successfully!"));

        // Verify that the user was saved in the database
        Optional<User> user = userRepository.findByUsername("johndoe");
        assertTrue(user.isPresent());
        assertEquals(signupRequest.getEmail(), user.get().getEmail());
        assertEquals(signupRequest.getFullName(), user.get().getFullName());
        assertEquals(signupRequest.getPhone(), user.get().getPhone());
        assertEquals(signupRequest.getRole(), user.get().getRole().toString());
        assertEquals(signupRequest.getAddress(), user.get().getAddress());

        userRepository.delete(user.get());
    }
}
