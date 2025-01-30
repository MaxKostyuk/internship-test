package com.kostyuk.requests;

import com.kostyuk.models.UserList;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class User extends BaseRequest {

    @Step("Sending request to get user list")
    public static Response getUserList() {
        return RestAssured.given()
                .get("/users");
    }

    @Step("Checking if user list response has a valid structure")
    public static void checkUserListResponse(Response response) {
        UserList userList = assertDoesNotThrow(()-> fromJson(response.asString(), UserList.class), "Response has unexpected structure");
        assertAll(() -> assertNotNull(userList.getPage(), "Page field shouldn't be null"),
                () -> assertNotNull(userList.getPerPage(), "PerPage field shouldn't be null"),
                () -> assertNotNull(userList.getTotal(), "Total field shouldn't be null"),
                () -> assertNotNull(userList.getTotalPages(), "TotalPages field shouldn't be null"),
                () -> assertNotNull(userList.getData(), "Data field shouldn't be null"),
                () -> assertNotNull(userList.getSupport(), "Support field shouldn't be null"));
    }

    @Step("Checking all emails ends with @reqres.in")
    public static void checkAllEmailsEndWithReqresIn(List<String> emailList) {
        for(String email : emailList)
            assertTrue(email.endsWith("@reqres.in"));
    }
}
