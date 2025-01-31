package com.kostyuk.api.requests;

import com.kostyuk.api.models.UpdateUserBody;
import com.kostyuk.api.models.UpdateUserResponse;
import com.kostyuk.api.models.UserList;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class User extends BaseRequest {

    private static final String USERS_BASE = "/users/";

    @Step("Sending request to get user list")
    public static Response getUserList() {
        return RestAssured.given()
                .get(USERS_BASE);
    }

    @Step("Sending delete user request with userId = {userId}")
    public static Response deleteUser(int userId) {
        return RestAssured.given()
                .delete(USERS_BASE + userId);
    }

    @Step("Sending update user request with name = {name}, job = {job} and id = {userId}")
    public static Response updateUser(String name, String job, int userId) {
        UpdateUserBody updateUserBody = new UpdateUserBody(name, job);
        return RestAssured.given()
                .body(updateUserBody)
                .patch(USERS_BASE + userId);
    }

    @Step("Checking if user list response has a valid structure")
    public static void checkUserListResponse(Response response) {
        UserList userList = Assertions.assertDoesNotThrow(() -> fromJson(response.asString(), UserList.class), "Response has unexpected structure");
        assertAll(() -> assertNotNull(userList.getPage(), "Page field shouldn't be null"),
                () -> assertNotNull(userList.getPerPage(), "PerPage field shouldn't be null"),
                () -> assertNotNull(userList.getTotal(), "Total field shouldn't be null"),
                () -> assertNotNull(userList.getTotalPages(), "TotalPages field shouldn't be null"),
                () -> assertNotNull(userList.getData(), "Data field shouldn't be null"),
                () -> assertNotNull(userList.getSupport(), "Support field shouldn't be null"));
    }

    @Step("Checking all emails ends with @reqres.in")
    public static void checkAllEmailsEndWithReqresIn(List<String> emailList) {
        for (String email : emailList)
            assertTrue(email.endsWith("@reqres.in"));
    }

    @Step("Checking update user response body")
    public static void checkUpdateUserResponseBody(Response response, String name, String job) {
        UpdateUserResponse updateUserResponse = Assertions.assertDoesNotThrow(() -> fromJson(response.asString(), UpdateUserResponse.class), "Response has unexpected structure");
        assertAll(() -> assertNotNull(updateUserResponse.getName(), "Name field shouldn't be null"),
                () -> assertNotNull(updateUserResponse.getJob(), "Job field shouldn't be null"),
                () -> assertNotNull(updateUserResponse.getUpdatedAt(), "UpdatedAt field shouldn't be null"),
                () -> assertEquals(name, updateUserResponse.getName(), "Received name " + updateUserResponse.getName() + " doesn't match with expected " + name),
                () -> assertEquals(job, updateUserResponse.getJob(), "Received job " + updateUserResponse.getJob() + " doesn't match with expected " + job)
                );
    }

    @Step("Checking if update time in response is less then {timeOffset} ms")
    public static void checkTimeInResponseLessThenOffset(Response response, long millisecondsOffset) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime responseTime = Assertions.assertDoesNotThrow(() -> fromJson(response.path("updatedAt").toString(), OffsetDateTime.class));
        OffsetDateTime updatedAtUtc = responseTime.withOffsetSameInstant(ZoneOffset.UTC);
        long timeDifference = Duration.between(updatedAtUtc, now).toMillis();
        assertTrue(timeDifference < millisecondsOffset, "Time difference between time in response and check moment is more than " + millisecondsOffset + " milliseconds");
    }
}
