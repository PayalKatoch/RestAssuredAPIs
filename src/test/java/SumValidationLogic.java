import io.restassured.path.json.JsonPath;
import org.junit.Assert;

public class SumValidationLogic {

    public static void main(String[] args)
    {
        JsonPath js = new JsonPath(Payload.complexJson());
        int count = js.getInt("courses.size()");
        System.out.println(count);
        int sum = 0;

        for(int i=0;i<count;i++)
        {
            int price = js.getInt("courses["+i+"].price");
            int copies= js.getInt("courses["+i+"].copies");
            int amount = price * copies;
            System.out.println(amount);

            sum =sum+amount;
        }
        int totalamount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum,totalamount);


    }
}
