package org.openway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;
import java.util.logging.Logger;


public class PeriplusProductAddToCartTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Dotenv dotenv = Dotenv.load();
    private static final Logger logger = Logger.getLogger(PeriplusProductAddToCartTest.class.getName());

    private static final String BASE_URL = "https://www.periplus.com/";
    private static final String EMAIL = dotenv.get("EMAIL");
    private static final String PASSWORD = dotenv.get("PASSWORD");
    private static final String SEARCH_QUERY = "How to Build a Car Adrian Newey";

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testAddToCart()  {
        login();
        bookSearch();
        bookSelect();
        addToCart();
        checkCart();
        clearCart();
        verifyCartIsEmpty();
    }

    private void login() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-signin-text")));
        signInButton.click();
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        logger.info("EMAIL: " + EMAIL);
        logger.info("PASSWORD: " + PASSWORD);
        emailField.sendKeys(EMAIL);
        passwordField.sendKeys(PASSWORD);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-login")));
        loginButton.click();
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());

        driver.navigate().to(BASE_URL);
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());
    }

    private void bookSearch() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name")));
        logger.info("SEARCH QUERY: " + SEARCH_QUERY);
        searchField.sendKeys(SEARCH_QUERY);

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("ti-search")));
        searchButton.click();
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());
    }

    private void bookSelect() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement book = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".row.row-category-grid > .col-xl-3 > .single-product > .product-content.product-contents > h3 > a")
        )).getFirst();
        book.click();
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());
    }

    private void addToCart() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.className("btn-add-to-cart")
        ));
        addToCartButton.click();
    }

    private void checkCart() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Notification-Modal .btn-modal-close")));
        closeButton.click();
        wait.until(ExpectedConditions.and(
                ExpectedConditions.invisibilityOfElementLocated(By.id("Notification-Modal")),
                ExpectedConditions.elementToBeClickable(By.id("show-your-cart"))
        ));
        WebElement cartButton = driver.findElement(By.id("show-your-cart"));
        cartButton.click();
        logger.info("CURRENT PAGE URL: " + driver.getCurrentUrl());
    }

    private void clearCart() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-cart-remove")));
        removeButton.click();
    }

    private void verifyCartIsEmpty() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'content') and contains(text(), 'Your shopping cart is empty')]")
        ));
        assert emptyCartMessage.isDisplayed();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser.");
            driver.quit();
            logger.info("Browser closed.");
        }
    }

}