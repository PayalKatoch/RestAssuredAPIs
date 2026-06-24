

import org.junit.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

public static void main(String[] args) {

    JsonPath js = new JsonPath(Payload.complexJson());


// 1. Print No of courses returned by API


    int count = js.getInt("courses.size()");
    System.out.println(count);

//2.Print Purchase Amount

int amount = js.getInt("dashboard.purchaseAmount");
System.out.println(amount);

// 3. Print Title of the first course
String title = js.get("courses.title[0]");
System.out.println(title);

// 4. Print All course titles and their respective Prices

for ( int i = 0; i<count; i++)
{
    String allTitle = js.get("courses["+i+"].title");
    int price = js.getInt("courses["+i+"].price");

    System.out.println("the title is " + allTitle);

    System.out.println("the prize is " + price);
}

// 5. Print no of copies sold by RPA Course

// int copiesCount = js.getInt("courses[2].copies");
// System.out.println(copiesCount);


System.out.println("RPA copies count with dynamical handling");
for ( int i = 0; i<count; i++)
{
    String getTitle = js.get("courses["+i+"].title");
    if(getTitle.equals("RPA")){

        int copiesCount = js.getInt("courses["+i+"].copies");
        System.out.println(copiesCount);
        break;

    }
}
  


//6. Verify if Sum of all Course prices matches with Purchase Amount

System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");
int sum = 0;
for ( int j = 0; j<count; j++)
{
    
    int getPrice = js.getInt("courses["+j+"].price");
    int getcopies = js.getInt("courses["+j+"].copies");

    int totalprize = getPrice * getcopies;
    sum=sum+totalprize;

    System.out.println(totalprize);
    
} 
int purchaseAmount = js.getInt("dashboard.purchaseAmount");
System.out.println(sum);
Assert.assertEquals(sum, purchaseAmount);
}
}

