import com.pm490.PM490.Pm490Application;
import com.pm490.PM490.model.Category;
import com.pm490.PM490.repository.CategoryRepository;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Pm490Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CategoryControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeClass
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api/category";
    }

    @Test
    public void testGetAllCategories() {
        Category categ1= new Category(3,"category1");
        Category categ2= new Category(4,"category2");
        Category categ3= new Category(5,"category3");

        categoryRepository.save(categ1);
        categoryRepository.save(categ2);
        categoryRepository.save(categ3);

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("$", hasSize(5))
                .body("[0].name", equalTo("electronic"))
                .body("[1].name", equalTo("clothes"))
                .body("[2].name", equalTo("category1"))
                .body("[3].name", equalTo("category2"))
                .body("[4].name", equalTo("category3"));

        categoryRepository.delete(categ1);
        categoryRepository.delete(categ2);
        categoryRepository.delete(categ3);
    }
}
