package test;

import payload.RequestPayload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplexJsonParseTest {

    @Test(description = "Validate complex JSON parsing including course details and purchase amount verification")
    public void validateComplexJsonParsing() {
        JsonPath js = new JsonPath(RequestPayload.complexJson());

        int count = js.getInt("courses.size()");
        System.out.println(count);

        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println(amount);

        String title = js.get("courses.title[0]");
        System.out.println(title);

        for (int i = 0; i < count; i++) {
            String allTitle = js.get("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            System.out.println("the title is " + allTitle);
            System.out.println("the prize is " + price);
        }

        System.out.println("RPA copies count with dynamical handling");
        for (int i = 0; i < count; i++) {
            String getTitle = js.get("courses[" + i + "].title");
            if (getTitle.equals("RPA")) {
                int copiesCount = js.getInt("courses[" + i + "].copies");
                System.out.println(copiesCount);
                break;
            }
        }

        System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");
        int sum = 0;
        for (int j = 0; j < count; j++) {
            int getPrice = js.getInt("courses[" + j + "].price");
            int getcopies = js.getInt("courses[" + j + "].copies");
            int totalprize = getPrice * getcopies;
            sum = sum + totalprize;
            System.out.println(totalprize);
        }
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(sum);
        Assert.assertEquals(sum, purchaseAmount);
    }
}
