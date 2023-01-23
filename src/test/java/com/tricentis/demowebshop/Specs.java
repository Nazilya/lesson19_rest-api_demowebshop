package com.tricentis.demowebshop;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.tricentis.demowebshop.Endpoints.BASE_URL;
import static io.restassured.RestAssured.with;

public class Specs {
    public static RequestSpecification request = with()
            .baseUri(BASE_URL)
//            .log().all()
            .log().uri()
            .log().method()
            .log().body()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8");

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
