package org.openway.pages;

import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
