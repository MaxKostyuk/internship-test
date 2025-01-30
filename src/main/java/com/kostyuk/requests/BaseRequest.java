package com.kostyuk.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostyuk.models.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;


public class BaseRequest {
    private static final ObjectMapper mapper;


    static {
        RestAssured.baseURI = "https://reqres.in/api";
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(io.restassured.config.ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> mapper));
    }

    protected static <T> T fromJson(String jsonString, Class<T> classOfT) throws Exception {
        return mapper.readValue(jsonString, classOfT);
    }

    @Step("Checking response code is equal to expected {expCode}")
    public static void checkResponseCode(Response response, int expCode) {
        assertEquals(expCode, response.statusCode(), "Received response code " + response.statusCode() + " is not equal to expected " + expCode);
    }

    @Step("Checking error response is correct and contains message {message}")
    public static void checkErrorResponse(Response response, String message) {
        ErrorResponse responseObject = assertDoesNotThrow(() -> fromJson(response.asString(), ErrorResponse.class), "Response has unexpected structure");
        assertNotNull(responseObject.getError(), "Error message shouldn't be null");
        assertEquals(message, responseObject.getError(), "Received error message " + responseObject.getError() + " is not equal to expected " + message);

    }
}
