package test;

import utils.ConfigLoader;
import api.Route;
import payload.RequestPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJsonTest {

    @Test(dataProvider = "booksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = ConfigLoader.getInstance().getBaseUrl();

        String res = given().header("Content-Type", "application/json")
                .body(RequestPayload.addBookLibrary(isbn, aisle))
                .when().post(Route.ADD_BOOK)
                .then().log().all().assertThat()
                .statusCode(200).extract().response().asString();

        System.out.println(res);
        JsonPath js = new JsonPath(res);
        String id = js.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "booksData")
    public Object[][] getData() {
        return new Object[][]{
                {"dfff", "4342"},
                {"fggfg", "134234"},
                {"ggfdfd", "11234"}
        };
    }
}
