package com.kostyuk.selenium.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemPage {
    private static final String PRODUCT_ADDED = "Product added.";
    private final WebDriver driver;
    private final WebDriverWait wait;
    @FindBy(xpath = "//h3[@class='price-container']")
    private WebElement priceTag;
    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement addToCartButton;

    public ItemPage(WebDriver driver, Duration waitDuration) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitDuration);
        PageFactory.initElements(driver, this);
    }

    @Step("Adding item to cart")
    public int addItemToCart() {
        int itemPrice = getPrice();
        addToCart();
        checkItemAddedToCart();
        return itemPrice;
    }

    @Step("Getting item price")
    private int getPrice() {
        wait.until(ExpectedConditions.visibilityOf(priceTag));
        return Integer.parseInt(priceTag.getText().replaceAll("[^0-9]", ""));
    }

    @Step("Clicking on add to cart button")
    private void addToCart() {
        addToCartButton.click();
    }

    @Step("Checking item added to cart")
    private void checkItemAddedToCart() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        assertEquals(PRODUCT_ADDED, alertMessage, "Alert message " + alertMessage + "is not equal to expected " + PRODUCT_ADDED);
        alert.accept();
    }

}
