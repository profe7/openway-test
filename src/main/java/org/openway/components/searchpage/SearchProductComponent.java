package org.openway.components.searchpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;
import org.openway.pages.ProductPage;

public class SearchProductComponent extends BaseComponent {
    private static final By TITLE = By.cssSelector(".product-content h3 a");
    private static final By AUTHOR = By.cssSelector(".product-author a");
    private static final By BINDING = By.cssSelector(".product-binding");
    private static final By PRICE = By.cssSelector(".product-price div");
    private static final By IMAGE_LINK = By.cssSelector(".product-img a");

    public SearchProductComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getTitle() {
        WebElement titleElement = findElement(TITLE);
        return titleElement.getText();
    }

    public String getAuthor() {
        WebElement authorElement = findElement(AUTHOR);
        return authorElement.getText();
    }

    public String getBinding() {
        WebElement bindingElement = findElement(BINDING);
        return bindingElement.getText();
    }

    public String getPrice() {
        WebElement priceElement = findElement(PRICE);
        return priceElement.getText();
    }

    public ProductPage getProductPage() {
        WebElement imageElement = findElement(IMAGE_LINK);
        click(imageElement);
        return new ProductPage(driver);
    }
}
