package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;

public class CheckoutPage extends BasePage {
    private static final By CHECKOUT_LOGO = By.cssSelector("#payment-address > div > div.left2 > div > img");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getCheckoutLogoTitle() {
        return findElement(CHECKOUT_LOGO).getDomProperty("title");
    }
}
