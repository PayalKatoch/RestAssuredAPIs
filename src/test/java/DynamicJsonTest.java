import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJsonTest {

// public static void main(String[] args) {
    @Test(dataProvider = "booksData")
    public void addBook(String isbn, String aisle)
    {

        RestAssured.baseURI = "https://rahulshettyacademy.com/";


        String res = given().header("Content-Type", "application/json")
        .body(Payload.addBookLibrary(isbn,aisle))
        .when().post("Library/Addbook.php")
        .then().log().all().assertThat()
        .statusCode(200).extract().response().asString();


        System.out.println(res);

        JsonPath js = new JsonPath(res);
        String id = js.getString("ID");

        System.out.println(id);


    }

    @DataProvider(name = "booksData")
    public Object[][] getData()
    {
        return new Object[][]
                {
                        {"dfff","4342"},
                        {"fggfg","134234"},
                        {"ggfdfd","11234"}
                };
    }

}
// }