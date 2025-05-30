package org.openway.components.loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;
import org.openway.pages.AccountPage;

public class LoginComponent extends BaseComponent {
    private static final By EMAIL_FIELD = By.name("email");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By LOGIN_BUTTON = By.id("button-login");

    public LoginComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public void enterEmail(String email) {
        WebElement emailField = findElement(EMAIL_FIELD);
        sendKeys(emailField, email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = findElement(PASSWORD_FIELD);
        sendKeys(passwordField, password);
    }

    public AccountPage clickLogin() {
        WebElement loginButton = findElement(LOGIN_BUTTON);
        click(loginButton);
        return new AccountPage(driver);
    }
}
