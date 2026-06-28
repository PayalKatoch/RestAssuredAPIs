package test;

import utils.ConfigLoader;
import api.Route;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pojo.Api;
import pojo.GetCourses;

import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    @Test(description = "Validate OAuth2 token generation and fetch course details using access token")
    public void validateOAuthCourseDetails() {
        RestAssured.baseURI = ConfigLoader.getInstance().getBaseUrl();

        String response =
                given().formParams("client_id", ConfigLoader.getInstance().getClientId())
                        .formParams("client_secret", ConfigLoader.getInstance().getClientSecret())
                        .formParams("grant_type", "client_credentials")
                        .formParams("scope", "trust")
                        .when().log().all().post(Route.OAUTH_TOKEN)
                        .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        GetCourses gc =
                given().queryParam("access_token", accessToken)
                        .when().log().all().get(Route.GET_COURSE_DETAILS)
                        .as(GetCourses.class);

        System.out.println(gc);

        System.out.println("TestCase 1 : " + gc.getLinkedIn());
        System.out.println("TestCase 2 : " + gc.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = gc.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }
    }
}
