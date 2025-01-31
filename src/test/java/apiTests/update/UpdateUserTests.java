package apiTests.update;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.Map;

import static com.kostyuk.requests.BaseRequest.checkResponseCode;
import static com.kostyuk.requests.User.*;
import static com.kostyuk.utils.TestUtils.generateCorrectUserUpdateData;

@Epic("Api tests")
@Feature("Update user request tests")
@DisplayName("Update user request tests")
public class UpdateUserTests {

    private static Map<String, String> updateUserTestData;
    private int userId;

    @BeforeAll
    public static void setTestData() {
        updateUserTestData = generateCorrectUserUpdateData();
    }

    @BeforeEach
    public void setup() {
        //здесь должно быть создание пользователя для обновления и присваивание тестовой переменной его id
        userId = 2;
    }

    @Test
    public void positiveShouldReturnCode200AndBodyWithNewData() {
        Response response = updateUser(updateUserTestData.get("name"), updateUserTestData.get("job"), userId);
        checkResponseCode(response, HttpStatus.SC_OK);
        checkUpdateUserResponseBody(response, updateUserTestData.get("name"), updateUserTestData.get("job"));
        checkTimeInResponseLessThenOffset(response, 2000);
    }

    @AfterEach
    public void tearDown() {
        //здесь должно быть удаление созданного пользователя
    }
}
