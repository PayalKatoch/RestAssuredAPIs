package test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void globalSetup() {
        log.info("Initializing test suite with Allure filter");
        RestAssured.filters(new AllureRestAssured());
    }
}
