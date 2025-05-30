package org.openway.components.global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;
import org.openway.pages.HomePage;
import org.openway.pages.LoginPage;
import org.openway.pages.SearchPage;
import org.openway.pages.ShoppingCartPage;

public class NavbarComponent extends BaseComponent {
    private static final By HOME_BUTTON = By.cssSelector(".logo-new a");
    private static final By SEARCH_BUTTON = By.className("ti-search");
    private static final By SEARCH_INPUT = By.id("filter_name");
    private static final By CART_BUTTON = By.id("show-your-cart");
    private static final By SIGN_IN_BUTTON = By.id("nav-signin-text");

    public NavbarComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public HomePage clickHome() {
        WebElement homeButton = findElement(HOME_BUTTON);
        click(homeButton);
        return new HomePage(driver);
    }

    public SearchPage clickSearch() {
        WebElement searchButton = findElement(SEARCH_BUTTON);
        click(searchButton);
        return new SearchPage(driver);
    }

    public void typeSearch(String searchTerm) {
        WebElement searchInput = findElement(SEARCH_INPUT);
        sendKeys(searchInput, searchTerm);
    }

    public ShoppingCartPage clickCart() {
        WebElement cartButton = findElement(CART_BUTTON);
        click(cartButton);
        return new ShoppingCartPage(driver);
    }

    public LoginPage clickSignIn() {
        WebElement signInButton = findElement(SIGN_IN_BUTTON);
        click(signInButton);
        return new LoginPage(driver);
    }
}
