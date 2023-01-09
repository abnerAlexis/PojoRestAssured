package com.test.pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected Object readProperties(Object property) {
        Properties props = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("./globalProps.properties");
            props.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.get(property);
    }

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri((String) readProperties("mock4pojoServerURL"))
            .addHeader("x-api-key", (String) readProperties("API_KEY"))
            .log(LogDetail.ALL)
            .setContentType((ContentType.JSON));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_OK)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }
}
