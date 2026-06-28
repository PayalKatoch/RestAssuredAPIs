package test;

import payload.RequestPayload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationLogicTest {

    @Test(description = "Verify sum of all course prices matches the total purchase amount")
    public void validateSumOfCoursePrices() {
        JsonPath js = new JsonPath(RequestPayload.complexJson());
        int count = js.getInt("courses.size()");
        System.out.println(count);
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            int amount = price * copies;
            System.out.println(amount);
            sum = sum + amount;
        }
        int totalamount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, totalamount);
    }
}
