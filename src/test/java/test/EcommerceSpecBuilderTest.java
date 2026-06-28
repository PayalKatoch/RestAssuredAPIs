package test;

import utils.ConfigLoader;
import api.Route;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.datafaker.Faker;
import org.testng.annotations.Test;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceSpecBuilderTest {

    Faker faker = new Faker();

    @Test(description = "Validate Add Place API using RequestSpec and ResponseSpec builders")
    public void validateAddPlaceWithSpecBuilder() {
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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri(ConfigLoader.getInstance().getBaseUrl())
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

        RequestSpecification res = given().spec(req).body(p);

        res.when().post(Route.ADD_PLACE)
                .then().log().all().spec(responseSpec)
                .extract().response().asString();
    }
}
