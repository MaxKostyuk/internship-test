package apiTests.userlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kostyuk.requests.BaseRequest.checkResponseCode;
import static com.kostyuk.requests.User.*;

@Epic("Api tests")
@Feature("Get user list request tests")
@DisplayName("Get user list request tests")
public class UserListTests {

    @Test
    @DisplayName("Positive test")
    @Description("Positive test. Should return code 200 and response body with expected structure")
    public void positiveShouldReturn200AndResponseWithExpectedStructure() {
        Response response = getUserList();
        checkResponseCode(response, HttpStatus.SC_OK);
        checkUserListResponse(response);
        List<String> userEmails = response.jsonPath().getList("data.email");
        checkAllEmailsEndWithReqresIn(userEmails);
    }
}
