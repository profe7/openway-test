package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;
import org.openway.components.loginpage.LoginComponent;

public class LoginPage extends BasePage {
    private static final By LOGIN_ROOT = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginComponent getLogin() {
        return new LoginComponent(findElement(LOGIN_ROOT), driver);
    }
}
