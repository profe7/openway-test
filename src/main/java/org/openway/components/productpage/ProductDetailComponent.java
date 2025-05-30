package org.openway.components.productpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class ProductDetailComponent extends BaseComponent {
    private static final By ADD_TO_CART_BUTTON = By.className("btn-add-to-cart");
    private static final By MODAL_CLOSE_BUTTON = By.cssSelector("#Notification-Modal .btn-modal-close, #Notification-Modal .close, #Notification-Modal [data-dismiss='modal']");
    private static final By NOTIFICATION_MODAL = By.id("Notification-Modal");

    public ProductDetailComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }


    public void clickAddToCart() {
        WebElement addToCartButton = findElement(ADD_TO_CART_BUTTON);
        click(addToCartButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> closeButtons = driver.findElements(MODAL_CLOSE_BUTTON);
        if (!closeButtons.isEmpty()) {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(MODAL_CLOSE_BUTTON));
            closeButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(NOTIFICATION_MODAL));
        } else {
            List<WebElement> modals = driver.findElements(NOTIFICATION_MODAL);
            if (!modals.isEmpty()) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(NOTIFICATION_MODAL));
            }
        }
    }
}
