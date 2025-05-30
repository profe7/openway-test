package org.openway.components.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BaseComponent {
    protected WebElement root;
    protected WebDriver driver;
    private final WebDriverWait wait;
    private static final By PRELOADER = By.className("preloader");

    protected BaseComponent(WebElement root, WebDriver driver) {
        this.root = root;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement preloader = driver.findElement(PRELOADER);
            waitForInvisibility(preloader);
        } catch (Exception _) {
            // Ignore exception
        }
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected WebElement findElement(By by) {
        return root.findElement(by);
    }

    protected List<WebElement> findElements(By by) {
        return root.findElements(by);
    }
}
