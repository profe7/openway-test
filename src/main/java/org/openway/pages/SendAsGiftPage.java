package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;
import org.openway.components.sendasgiftpage.Carousel;

public class SendAsGiftPage extends BasePage {
    private static final By CAROUSEL_ROOT = By.cssSelector("#form-sendasgift > div > div:nth-child(1) > div > div");

    public SendAsGiftPage(WebDriver driver) {
        super(driver);
    }

    public Carousel getCarousel() {
        return new Carousel(findElement(CAROUSEL_ROOT), driver);
    }


}
