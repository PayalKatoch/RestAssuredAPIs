package test;

import utils.TokenManager;
import api.EcommerceSpecBuilder;
import api.Route;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.OrderDetail;
import pojo.Orders;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EcommerceAPITest {

    String token;
    String userId;
    String productID;

    @BeforeClass
    public void setup() {
        token = TokenManager.getEcommerceToken();
        userId = TokenManager.getEcommerceUserId();
    }

    @Test(description = "Add the new product to list", priority = 1)
    public void addProduct() {
        RequestSpecification reqAddProduct = given().log().all().spec(EcommerceSpecBuilder.addProduct(token, userId));

        String addProductResponse = reqAddProduct.when().post(Route.ADD_PRODUCT)
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        productID = js.getString("productId");
        Assert.assertNotNull(productID, "Product ID was not returned by the API");
        System.out.println(productID);
    }

    @Test(description = "Create Order for the product", priority = 2, dependsOnMethods = "addProduct")
    public void createOrder() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq = given().log().all().spec(EcommerceSpecBuilder.createProduct(token)).body(orders);

        String responseAddOrder = createOrderReq.when().post(Route.CREATE_ORDER)
                .then().log().all().extract().response().asString();
        System.out.println(responseAddOrder);
    }

    @Test(description = "Delete the added product from the list", priority = 3, dependsOnMethods = {"addProduct", "createOrder"})
    public void deleteProduct() {
        Assert.assertNotNull(productID, "productID is null - addProduct may have failed");
        RequestSpecification deleteProdReq = given().log().all().spec(EcommerceSpecBuilder.deleteProduct(token)).pathParams("productId", productID);
        String deleteProductResponse = deleteProdReq.when().delete(Route.DELETE_PRODUCT)
                .then().log().all().extract().response().asString();

        JsonPath js1 = new JsonPath(deleteProductResponse);
        Assert.assertEquals(js1.get("message"), "Product Deleted Successfully");
    }
}
