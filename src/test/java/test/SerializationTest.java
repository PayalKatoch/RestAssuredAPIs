package test;

import utils.ConfigLoader;
import api.Route;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.testng.annotations.Test;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class SerializationTest {

    Faker faker = new Faker();

    @Test(description = "Validate Java object serialization to JSON for Add Place API request")
    public void validateSerialization() {
        AddPlace p = new AddPlace();
        p.setAccuracy(faker.number().numberBetween(1, 100));
        p.setAddress(faker.address().fullAddress());
        p.setLanguage("English");
        p.setName(faker.name().fullName());
        p.setPhone_number(faker.phoneNumber().cellPhone());
        p.setWebsite("http://" + faker.internet().domainName());

        List<String> mylist = new ArrayList<>();
        mylist.add(faker.lorem().word() + " park");
        mylist.add("park");
        p.setTypes(mylist);

        Location l = new Location();
        l.setLat(Double.parseDouble(faker.address().latitude()));
        l.setLng(Double.parseDouble(faker.address().longitude()));
        p.setLocation(l);

        RestAssured.baseURI = ConfigLoader.getInstance().getBaseUrl();

        given().log().all().queryParam("key", "qaclick123")
                .body(p)
                .when().post(Route.ADD_PLACE)
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
    }
}
