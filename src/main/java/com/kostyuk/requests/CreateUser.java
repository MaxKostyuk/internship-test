package com.kostyuk.requests;

import com.kostyuk.models.UserRegistration;
import com.kostyuk.models.UserRegistrationResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateUser extends BaseRequest {

    @Step("Sending create user request with email = {email}, password = {password}")
    public static Response createUser(String email, String password) {
        UserRegistration user = new UserRegistration(email, password);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/register");
    }

    @Step("Checking successful user registration response is valid")
    public static void checkSuccessfulUserRegistrationResponse(Response response) {
        UserRegistrationResponse responseObject = assertDoesNotThrow(() -> fromJson(response.asString(), UserRegistrationResponse.class), "Response has unexpected structure");
        assertAll(() -> assertNotNull(responseObject.getId(), "Id field shouldn't be null"),
                () -> assertNotNull(responseObject.getToken(), "Token shouldn't be null"));
    }
}
