
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;


public class Basics {
    
     public static void main( String[] args )
    {

// validate if add place api is working as expected

// given - all input details
// when - submit api request( put or post method)
// then - all the assertion part, validation part


        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json")
        .body(Payload.addPlace()).when().post("maps/api/place/add/json")
                        .then().assertThat().statusCode(200)
                        .body("scope", equalTo("APP")).extract().response().asString();

    
        System.out.println(response);

        JsonPath js = new JsonPath(response); // for parsing json to get a particular value from string response json
        String placeId= js.getString("place_id");

        System.out.println(placeId);



    // Add Place Done, now update place with new address, then Get place to validate if new address is update in response or not

    // 2. Starting with update place api automation
    String newAddress = "70 Summer walk, USA";
    given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json")
    .body("{\n" + //
                "\"place_id\":\""+placeId+"\",\n" + //
                "\"address\":\""+newAddress+"\",\n" + //
                "\"key\":\"qaclick123\"\n" + //
                "}\n" + //
                "")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated") );



    //3 Get
    String getPlaceResponse = given().log().all().queryParam("key","qaclick123")
    .queryParam("place_id", placeId)
    .when().get("maps/api/place/get/json")
    .then().assertThat().log().all().statusCode(200).extract().response().asString();


    JsonPath js1 = new JsonPath(getPlaceResponse);
    String actualAddress = js1.getString("address");

    System.out.println(actualAddress);

    Assert.assertEquals(actualAddress, newAddress);
        

    }
}
