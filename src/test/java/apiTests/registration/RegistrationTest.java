package apiTests.registration;

import com.kostyuk.api.utils.TestUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.kostyuk.api.requests.BaseRequest.checkErrorResponse;
import static com.kostyuk.api.requests.BaseRequest.checkResponseCode;
import static com.kostyuk.api.requests.CreateUser.checkSuccessfulUserRegistrationResponse;
import static com.kostyuk.api.requests.CreateUser.createUser;
import static com.kostyuk.api.utils.ErrorMessages.MISSING_PASSWORD;

@Epic("Api tests")
@Feature("Create user request tests")
@DisplayName("Create user request tests")
public class RegistrationTest {
    private static Map<String, String> testValues;

    @BeforeAll
    public static void setup() {
        testValues = TestUtils.generateCorrectUserRegistrationData();
    }


    @Test
    @DisplayName("Positive test")
    @Description("Positive test of user creation. Should return code 200 and successful response body")
    public void positiveShouldReturnSuccessfulBody() {
        Response response = createUser(testValues.get("email"), testValues.get("password"));
        checkResponseCode(response, HttpStatus.SC_OK);
        checkSuccessfulUserRegistrationResponse(response);
    }

    @Test
    @DisplayName("Negative test case without password")
    @Description("Negative test case. No password. Should return code 400 and error message")
    public void negativeWithoutPasswordCode400AndErrorMessage() {
        Response response = createUser(testValues.get("email"), null);
        checkResponseCode(response, HttpStatus.SC_BAD_REQUEST);
        checkErrorResponse(response, MISSING_PASSWORD);
    }

    @AfterEach
    @Step("Deleting created user")
    public void tearDown() {
        //Здесь должно быть удаление созданного пользователя
    }
}
