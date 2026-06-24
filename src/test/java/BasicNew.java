
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class BasicNew {

    public static void main(String[] args)
    {
        // given - all input details, json payload, headers , cookies
        // when - submit the api
        // then - validate

        RestAssured.baseURI="https://rahulshettyacademy.com";

        String response =
                given().queryParam("Key","qaclick123")
                .header("Content-type","application/json")
                .body(Payload.addPlace())
                .when().log().all().post("/maps/api/place/add/json").then().assertThat().log().all().statusCode(200).extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeId= js.getString("place_id");

        System.out.println(placeId);


    //Add place Done - > update place api to update the address, which need place id from add place response ->
        // then get place to verify the address updated

        //update place
//        String newAddress = "Antriksh Golf View 2";
//
//                given().log().all().queryParam("Key","qaclick123")
//                .header("Content-type","application/json")
//                .body("{\n" + //
//                        "\"place_id\":\""+placeId+"\",\n" + //
//                        "\"address\":\""+newAddress+"\",\n" + //
//                        "\"key\":\"qaclick123\"\n" + //
//                        "}\n" + //
//                        "")
//                .when().post("/maps/api/place/get/json")
////                .then().log().all().assertThat().statusCode(200)
////                        .body("msg",equalTo("Address successfully updated") );
//                .then().log().all().assertThat().statusCode(200).
//                        body("msg",equalTo("Address successfully updated") );
//

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




        System.out.println("xxxxxxxxxx---------------");
//        // Get Place to verify the
//        String getPlaceResponse =
//                given().log().all().queryParam("key","qaclick123")
//                        .queryParam("place_id", placeId)
//                        .when().get("maps/api/place/get/json")
//                        .then().assertThat().log().all().statusCode(200).extract().response().asString();
//
//        JsonPath js1 = new JsonPath(getPlaceResponse);
//        String actualAddress = js1.getString("address");
//
//        System.out.println(actualAddress);
//        Assert.assertEquals(actualAddress, "Antriksh Golf View 2");

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
