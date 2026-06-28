package api;

import utils.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.datafaker.Faker;

import java.io.File;

public class EcommerceSpecBuilder {

    private static final Faker faker = new Faker();

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().setBaseUri(ConfigLoader.getInstance().getBaseUrl())
                .setContentType(ContentType.JSON).build();
    }

    public static RequestSpecification addProduct(String token, String userId) {
        return new RequestSpecBuilder().setBaseUri(ConfigLoader.getInstance().getBaseUrl())
                .addHeader("authorization", token)
                .addFormParam("productName", faker.text().text(3, 20))
                .addFormParam("productAddedBy", userId)
                .addFormParam("productCategory", "fashion")
                .addFormParam("productSubCategory", "shirts")
                .addFormParam("productPrice", String.valueOf(faker.number().numberBetween(100, 50000)))
                .addFormParam("productDescription", faker.commerce().material() + " shirt")
                .addFormParam("productFor", "Women")
                .addMultiPart("productImage", new File(EcommerceSpecBuilder.class.getClassLoader().getResource("product_image.jpeg").getFile()))
                .build();
    }

    public static RequestSpecification createProduct(String token) {
        return new RequestSpecBuilder().setBaseUri(ConfigLoader.getInstance().getBaseUrl())
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    public static RequestSpecification deleteProduct(String token) {
        return new RequestSpecBuilder().setBaseUri(ConfigLoader.getInstance().getBaseUrl())
                .addHeader("authorization", token).setContentType(ContentType.JSON)
                .build();
    }
}
