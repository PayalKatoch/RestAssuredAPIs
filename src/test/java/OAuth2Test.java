//
//import com.pojo.*;
//
//import static io.restassured.RestAssured.*;
//
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import pojo1.Api;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Assert;
//
//public class OAuth2Test {
//
//    public static void main(String[] args) {
//
//        String[] webCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
//
//        RestAssured.baseURI = "https://rahulshettyacademy.com/";
//
//
//        String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
//        .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
//        .formParam("grant_type", "client_credentials")
//        .formParam("trust")
//        .when().log().all().post("oauthapi/oauth2/resourceOwner/token")
//        .then().assertThat().statusCode(200)
//        .extract().response().asString();
//
//        System.out.println(response);
//
//        JsonPath js = new JsonPath(response);
//        String accessToken = js.getString("access_token");
//
//
//        // String courseDetails = given().queryParam("access_token", accessToken)
//        // .when().log().all().get("oauthapi/getCourseDetails").asString();
//
//         GetCourses courseDetails =
//         given().queryParam("access_token", accessToken)
//        .when().log().all().get("oauthapi/getCourseDetails").as(GetCourses.class);
//
//
//        courseDetails.getLinkedIn();
//        System.out.println(courseDetails.getLinkedIn());
//        System.out.println(courseDetails.getInstructor());
//
//        // this will print the title in api at index 1
//        // CouseDetails>courses>api>get the titile of 2nd index item, bcz, getAPI will return list of object
//        System.out.println(courseDetails.getCourses().getApi().get(1).getCourseTitle());
//
//
//// Above we use the index to get the tille of course in api, but should not be used index bcz we never know where is that in actual
//// Dynamically getting the course title and getting price of that course
//        List<Api> apicourses = courseDetails.getCourses().getApi();
//        for(int i = 0; i<apicourses.size(); i++){
//            if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
//                {
//                    System.out.println(apicourses.get(i).getPrice());
//                }
//
//
//        }
//
//// Print all the titles of webAutomation
//ArrayList <String> a = new ArrayList<>();
//      List<webAutomation> webCourses = courseDetails.getCourses().getWebAutomation();
//      for(int i=0; i<webCourses.size(); i++)
//        {
//            String wb=webCourses.get(i).getCourseTitle();
//            System.out.println(wb); // this is simple printing
//
//
//// lets say i want to compare the webcourse title with actuals
//         a.add(webCourses.get(i).getCourseTitle());
//
//            // lets say i want to compare the webcourse title with actuals
//    // List <String> expectList = Arrays.asList(webCourseTitles);
//    // Assert.assertEquals(wb, expectList);
//
//
//        }
//
//    List <String> expectList = Arrays.asList(webCourseTitles);
//    Assert.assertEquals(a, expectList);
//
//    }
//
//}
