package apiTests.delete;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kostyuk.api.requests.BaseRequest.checkResponseCode;
import static com.kostyuk.api.requests.BaseRequest.checkResponseHasNoBody;
import static com.kostyuk.api.requests.User.deleteUser;

@Epic("Api tests")
@Feature("Delete user request tests")
@DisplayName("Delete user request tests")
public class DeleteUserTests {

    @Test
    @DisplayName("Positive test")
    @Description("Should return code 204 and no body")
    public void positiveShouldReturnCode204AndNoBody() {
        Response response = deleteUser(2);
        checkResponseCode(response, HttpStatus.SC_NO_CONTENT);
        checkResponseHasNoBody(response);
    }
}
