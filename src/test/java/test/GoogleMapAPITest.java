package test;

import utils.ConfigLoader;
import api.Route;
import payload.RequestPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoogleMapAPITest {

    @Test(description = "End to end validation of Add, Update and Get Place APIs on Google Maps")
    public void validateAddUpdateGetPlace() {
        RestAssured.baseURI = ConfigLoader.getInstance().getBaseUrl();

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(RequestPayload.addPlace()).when().post(Route.ADD_PLACE)
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP")).extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        String newAddress = "70 Summer walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put(Route.UPDATE_PLACE)
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get(Route.GET_PLACE)
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress, newAddress);
    }
}
