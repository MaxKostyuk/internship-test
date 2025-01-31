package seleniumTests.RegistrationAndOrder;

import com.kostyuk.selenium.pom.CartPage;
import com.kostyuk.selenium.pom.ItemPage;
import com.kostyuk.selenium.pom.MainPage;
import com.kostyuk.selenium.utils.WebDriverFactory;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kostyuk.selenium.utils.DataUtils.generateUserTestData;

public class RegistrationAndOrderTests {
    private WebDriver driver;
    private MainPage mainPage;
    private ItemPage itemPage;
    private CartPage cartPage;
    private int orderSum;
    private Map<String,String> testData;

    @BeforeEach
    public void setup() {
        testData = generateUserTestData();
        driver = WebDriverFactory.createDriver();
        mainPage = new MainPage(driver, Duration.ofSeconds(10));
        itemPage = new ItemPage(driver, Duration.ofSeconds(10));
        cartPage = new CartPage(driver, Duration.ofSeconds(10));
        orderSum = 0;
    }

    @Test
    public void positiveRegistrationAndOrder() {
        mainPage.open();
        mainPage.register(testData.get("name"), testData.get("password"));
        mainPage.login(testData.get("name"), testData.get("password"));
        order();
        mainPage.clickCartButton();
        cartPage.checkTotalSumEqualsToExpected(orderSum);
        cartPage.placeOrder(testData.get("name"), testData.get("card"));
        cartPage.checkSuccessfulOrderPlacement();
        cartPage.checkOrderDateIsToday();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Step("Ordering items from all categories")
    public void order() {
        List<String> categoriesButtons = mainPage.getCategoryButtons();
        for (String category : categoriesButtons) {
            mainPage.selectCategory(category);
            mainPage.selectFirstItemInCategory();
            orderSum += itemPage.addItemToCart();
            mainPage.open();
        }
    }
}
