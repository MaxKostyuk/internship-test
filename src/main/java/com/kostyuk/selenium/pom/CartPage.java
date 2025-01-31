package com.kostyuk.selenium.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//h3[@id='totalp']")
    private WebElement totalPrice;
    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;
    @FindBy(xpath = "//input[@id='name']")
    private WebElement nameField;
    @FindBy(xpath = "//input[@id='card']")
    private WebElement cardField;
    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseButton;
    @FindBy(xpath = "//h2[text()='Thank you for your purchase!']")
    private WebElement thankYouSign;
    @FindBy(xpath = "//h2[text()='Thank you for your purchase!']/following-sibling::p")
    private WebElement orderInfo;


    public CartPage(WebDriver driver, Duration waitDuration) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitDuration);
        PageFactory.initElements(driver, this);
    }

    @Step("Checking that actual sum is equal to expected {expSum}")
    public void checkTotalSumEqualsToExpected(int expSum) {
        wait.until(ExpectedConditions.visibilityOf(totalPrice));
        int actualTotal = Integer.parseInt(totalPrice.getText());
        assertEquals(expSum, actualTotal, "Actual total sum " + actualTotal + " isn't equal to expected " + expSum);
    }

    @Step("Placing the order")
    public void placeOrder() {
        clickPlaceOrderButton();
        fillNameField("name");
        fillCardField("123");
        clickPurchaseButton();
    }

    @Step("Checking if placing order was successful")
    public void checkSuccessfulOrderPlacement() {
        assertDoesNotThrow(() -> wait.until(ExpectedConditions.visibilityOf(thankYouSign)));
    }

    @Step("Checking if order date is today")
    public void checkOrderDateIsToday() {
        String stringOrderInfo = wait.until(ExpectedConditions.visibilityOf(orderInfo)).getText();
        String[] infoSplit = stringOrderInfo.split("Date: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate orderDate = LocalDate.parse(infoSplit[1], formatter);
        LocalDate today = LocalDate.now();
        assertEquals(today, orderDate, "Date from order and is not equal to today's date");
    }

    @Step("Clicking on place order button")
    private void clickPlaceOrderButton() {
        placeOrderButton.click();
    }

    @Step("Filling name field with {name}")
    private void fillNameField(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameField)).sendKeys(name);
    }

    @Step("Filling card field with {card}")
    private void fillCardField(String card) {
        wait.until(ExpectedConditions.visibilityOf(cardField)).sendKeys(card);
    }

    @Step("Confirming order")
    private void clickPurchaseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
    }
}
