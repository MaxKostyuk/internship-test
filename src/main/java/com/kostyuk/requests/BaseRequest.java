package com.kostyuk.requests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BaseRequest {
    private static final ObjectMapper mapper;


    static {
        RestAssured.baseURI = "https://reqres.in/api";
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    protected static <T> T fromJson(String jsonString, Class<T> classOfT) throws Exception {
        return mapper.readValue(jsonString, classOfT);
    }

    @Step("Checking response code is equal to expected {expCode}")
    public static void checkResponseCode(Response response, int expCode) {
        assertEquals(expCode, response.statusCode(), "Received response code " + response.statusCode() + " is not equal to expected " + expCode);
    }
}
