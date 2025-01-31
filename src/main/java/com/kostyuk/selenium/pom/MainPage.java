package com.kostyuk.selenium.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {
    private static final String MAIN_PAGE_URL = "https://www.demoblaze.com/";
    public static final String SIGN_UP_SUCCESSFUL = "Sign up successful.";
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//a[@id='signin2']")
    private WebElement signUpButton;
    @FindBy(xpath = "//input[@id='sign-username']")
    private WebElement signUpUserNameField;
    @FindBy(xpath = "//input[@id='sign-password']")
    private WebElement signUpPasswordField;
    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement confirmSignUpButton;
    @FindBy(xpath = "//a[@id='login2']")
    private WebElement loginButton;
    @FindBy(xpath = "//input[@id='loginusername']")
    private WebElement loginUserNameField;
    @FindBy(xpath = "//input[@id='loginpassword']")
    private WebElement loginPasswordField;
    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement confirmLoginButton;
    @FindBy(xpath = "//a[@id='nameofuser']")
    private WebElement welcomeMessage;
    @FindBy(xpath = "//a[@id='itemc']")
    private List<WebElement> categoriesButtons;
    @FindBy(xpath = "//div[@id='tbodyid']//a[1]")
    private WebElement firstItemInCategory;
    @FindBy(xpath = "//a[@id='cartur']")
    private WebElement cartButton;

    public MainPage(WebDriver driver, Duration waitDuration) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitDuration);
        PageFactory.initElements(driver, this);
    }

    @Step("Opening main page")
    public void open() {
        driver.get(MAIN_PAGE_URL);
    }

    @Step("Registering user with name = {userName} and password = {password}")
    public void register(String userName, String password) {
        clickSignUpButton();
        fillSignUpUserName(userName);
        fillSignUpPassword(password);
        clickOnConfirmSignUpButton();
        checkRegistrationIsSuccessful();
    }

    @Step("Login with name = {name} and password = {password}")
    public void login(String name, String password) {
        clickOnLoginButton();
        fillLoginUserName(name);
        fillLoginPassword(password);
        clickOnConfirmLogInButton();
        checkSuccessfulLogin();
    }

    @Step("Getting all category buttons")
    public List<String> getCategoryButtons() {
        return categoriesButtons.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Selecting category {categoryName}")
    public void selectCategory(String categoryName) {
        driver.findElement(By.xpath("//a[@id='itemc' and text()='" + categoryName + "']")).click();
    }

    @Step("Selecting first item in category")
    public void selectFirstItemInCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(firstItemInCategory)).click();
    }

    @Step("Clicking on cart button")
    public void clickCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    @Step("Click on sign up button")
    private void clickSignUpButton() {
        signUpButton.click();
    }

    @Step("Filling user name field with {userName}")
    private void fillSignUpUserName(String userName) {
        wait.until(ExpectedConditions.visibilityOf(signUpUserNameField)).sendKeys(userName);
    }

    @Step("Filling password field with {password}")
    private void fillSignUpPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(signUpPasswordField)).sendKeys(password);
    }

    @Step("Clicking on confirm sign up button")
    private void clickOnConfirmSignUpButton() {
        confirmSignUpButton.click();
    }

    @Step("Checking that registration was successful")
    private void checkRegistrationIsSuccessful() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        assertEquals(SIGN_UP_SUCCESSFUL, alertMessage, "Alert message " + alertMessage + "is not equal to expected " + SIGN_UP_SUCCESSFUL);
        alert.accept();
    }

    @Step("Clicking on login button")
    private void clickOnLoginButton() {
        loginButton.click();
    }

    @Step("Filling user name field with {userName}")
    private void fillLoginUserName(String userName) {
        wait.until(ExpectedConditions.visibilityOf(loginUserNameField)).sendKeys(userName);
    }

    @Step("Filling password field with {password}")
    private void fillLoginPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(loginPasswordField)).sendKeys(password);
    }

    @Step("Clicking on confirm log in button")
    private void clickOnConfirmLogInButton() {
        confirmLoginButton.click();
    }

    @Step("Checking if login was successful")
    private void checkSuccessfulLogin() {
        assertDoesNotThrow(() -> wait.until(ExpectedConditions.visibilityOf(welcomeMessage)));
    }
}

