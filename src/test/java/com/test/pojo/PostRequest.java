package com.test.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest extends BaseTest{
    private PayloadObject payloadObject = new PayloadObject("value1", "value2");

    @Test
    public void postRequestWithJasonPayloadSerialization() {
        Map<String, Object> payload = new HashMap<>() {{
            put("key1", "value1");
            put("key2", "value1");
        }};
        given()
            .body(payload)
            .when()
            .post("/post")
            .then()
            .body("key1", equalTo("value1"),
                "key2", equalTo("value2"))
            .extract().path("workspace.id")
        ;
    }

    @Test
    public void postRequestWithObjectClass() {
        given()
            .body(payloadObject)
            .when()
            .post("/post")
            .then()
            .body("key1", equalTo(payloadObject.getKey1()),
                "key2", equalTo(payloadObject.getKey2()))
            .extract().path("workspace.id")
        ;
    }

    @Test
    public void postRequestWithoutParameterizedConstructor() {
        PayloadObject payloadObject = new PayloadObject();
        payloadObject.setKey1("value1");
        payloadObject.setKey2("value2");

        given()
            .body(payloadObject)
            .when()
            .post("/post")
            .then()
            .body("key1", equalTo(payloadObject.getKey1()),
                "key2", equalTo(payloadObject.getKey2()))
            .extract().path("workspace.id")
        ;
    }

    @Test
    public void deserializing() {
        PayloadObject deserializedPayloadObject = given()
            .body(payloadObject)
            .when()
            .post("/post")
            .then()
            .extract()
            .response()
            .as(PayloadObject.class)
        ;

        ObjectMapper objectMapper = new ObjectMapper();
        String deserializedPayloadString;
        try {
            deserializedPayloadString = objectMapper.writeValueAsString(deserializedPayloadObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String payloadStr;
        try {
            payloadStr = objectMapper.writeValueAsString(payloadObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            assertThat(objectMapper.readTree(deserializedPayloadString), equalTo(objectMapper.readTree(payloadStr)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
