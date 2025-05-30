package org.openway.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openway.components.global.NavbarComponent;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    private static final By PRELOADER = By.className("preloader");
    private static final By NAVBAR_ROOT = By.cssSelector(".middle-inner");

    protected WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement preloader = driver.findElement(PRELOADER);
            waitForInvisibility(preloader);
        } catch (Exception _) {
            // Ignore exception
        }
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public NavbarComponent getNavbar() {
        return new NavbarComponent(findElement(NAVBAR_ROOT), driver);
    }

}
