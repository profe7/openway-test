package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openway.base.BasePage;
import org.openway.components.shoppingcartpage.BasketComponent;

import java.time.Duration;

public class ShoppingCartPage extends BasePage {
    private static final By BASKET_ROOT = By.cssSelector(".shopping-summery");
    private static final By EMPTY_MESSAGE = By.xpath("//div[contains(@class, 'content') and contains(text(), 'Your shopping cart is empty')]");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(BASKET_ROOT));
    }

    public BasketComponent getBasket() {
        return new BasketComponent(findElement(BASKET_ROOT), driver);
    }

    public String getEmptyMessage() {
        return findElement(EMPTY_MESSAGE).getText().trim();
    }
}
