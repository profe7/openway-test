package org.openway.base;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openway.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    protected static WebDriver driver;
    protected static HomePage homePage;
    protected static LoginPage loginPage;
    protected static AccountPage accountPage;
    protected static SearchPage searchPage;
    protected static ProductPage productPage;
    protected static ShoppingCartPage shoppingCartPage;
    private static final Dotenv dotenv = Dotenv.load();

    private static final String BASE_URL = "https://www.periplus.com/";
    private static final String EMAIL = dotenv.get("EMAIL");
    private static final String PASSWORD = dotenv.get("PASSWORD");

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(BASE_URL);

        homePage = new HomePage(driver);
        loginPage = homePage.getNavbar().clickSignIn();
        loginPage.getLogin().enterEmail(EMAIL);
        loginPage.getLogin().enterPassword(PASSWORD);
        accountPage = loginPage.getLogin().clickLogin();
        homePage = accountPage.getNavbar().clickHome();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterMethod
    public void afterMethod() {
        removeBookFlow(shoppingCartPage);
    }

    private void removeBookFlow(ShoppingCartPage shoppingCartPage) {
        shoppingCartPage.getBasket().clearCart();
        Assert.assertTrue(shoppingCartPage.getEmptyMessage()
                .contains("Your shopping cart is empty"));
        homePage = shoppingCartPage.getNavbar().clickHome();
    }

}
