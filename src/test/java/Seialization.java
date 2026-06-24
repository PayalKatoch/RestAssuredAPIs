

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Seialization {


    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        given().queryParam("key", "qaclick123")
        .body("args")
        .when().post("/maps/api/place/add/json")
        .then().log().all().assertThat().statusCode(200)
        .extract().response().asString();
        
    }

}
