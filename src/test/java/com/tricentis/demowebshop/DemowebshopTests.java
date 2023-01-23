package com.tricentis.demowebshop;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static com.tricentis.demowebshop.Endpoints.CART_ADD_ENDPOINT;
import static com.tricentis.demowebshop.Endpoints.FILTERS_ENDPOINT;
import static com.tricentis.demowebshop.Specs.request;
import static com.tricentis.demowebshop.Specs.responseSpec;
import static org.hamcrest.Matchers.is;
import static io.restassured.RestAssured.given;

public class DemowebshopTests {
    TestData testData = new TestData();

    @Test
    void addToCartTest() {
        given()
                .spec(request)
                .cookie(testData.cookieName, testData.cookieValue)
                .body(testData.body)
                .when()
                .post(CART_ADD_ENDPOINT)
                .then()
                .log().all()
                .spec(responseSpec)
                .body("success", is(true))
                .body("message", is(testData.successMessageText));
    }

    @Test
    void addToCartAnonymTest() {

        given()
                .spec(request)
                .body(testData.body)
                .when()
                .post(CART_ADD_ENDPOINT)
                .then()
                .log().all()
                .spec(responseSpec)
                .body("success", is(true))
                .body("message", is(testData.successMessageText))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void checkFilterByPriceTest() {
        Response response = given()
                .spec(request)
                .param("orderby", 10)
                .when()
                .get(FILTERS_ENDPOINT)
                .then()
                .spec(responseSpec)
                //.log().all()
                .extract()
                .response();
        String value = response.htmlPath().getString("**.findAll{it.@class == 'price actual-price'}");

        String replace = value.replace("[","");
        String replace1 = replace.replace("]","");
        List<String> pricesList = new ArrayList<>(Arrays.asList(replace1.split(",")));

        pricesList.stream().sorted().collect(Collectors.toList()).equals(pricesList);
    }
}
