package org.openway.components.shoppingcartpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;

import java.util.List;

public class BasketComponent extends BaseComponent {
    private static final By PRODUCT_TITLE = By.cssSelector(".row-cart-product .product-name.limit-lines a");
    private static final By REMOVE_BUTTON = By.cssSelector(".btn.btn-cart-remove");
    private static final By PLUS_BUTTON = By.cssSelector(".qty .btn-number[data-type='plus']");
    private static final By MINUS_BUTTON = By.cssSelector(".qty .btn-number[data-type='minus']");
    private static final By PRODUCT_ROW = By.cssSelector(".row-cart-product");
    private static final By PRODUCT_PRICE = By.xpath(".//div[contains(@class,'row') and contains(text(),'Rp')]");
    private static final By SUBTOTAL = By.cssSelector("ul li:first-child span#sub_total");
    private static final By SUCCESS_MESSAGE = By.cssSelector(".success");
    private static final By EMPTY_CART_MESSAGE = By.xpath("//div[contains(@class, 'content') and contains(text(), 'Your shopping cart is empty')]");
    private static final By QUANTITY_FIELD = By.cssSelector("input.input-number.text-center");
    private static final By ISBN = By.xpath(".//div[contains(@class, 'row') and (contains(text(), '978') or contains(text(), '979'))]");
    

    public BasketComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public List<String> getProductTitles() {
        return findElements(PRODUCT_TITLE).stream()
                .map(WebElement::getText)
                .toList();
    }

    public void clickRemoveButton() {
        WebElement removeButton = findElement(REMOVE_BUTTON);
        click(removeButton);
    }

    public void clearCart() {
        boolean first = true;
        while (driver.findElements(EMPTY_CART_MESSAGE).isEmpty()) {

            List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTON);

            try {
                WebElement preloader = driver.findElement(By.className("preloader"));
                waitForInvisibility(preloader);
            } catch (Exception _) {
                // Ignored
            }

            WebElement removeButton = removeButtons.getFirst();
            removeButton.click();
            waitForInvisibility(removeButton);

            if (first) {
                try {
                    WebElement successMsg = findElement(SUCCESS_MESSAGE);
                    waitForVisibility(successMsg);
                    waitForInvisibility(successMsg);
                } catch (Exception _) {
                    // Ignored
                }
                first = false;
            }
        }
    }

    public void clickPlusButton() {
        WebElement plusButton = findElement(PLUS_BUTTON);
        click(plusButton);
    }

    public void clickMinusButton() {
        WebElement minusButton = findElement(MINUS_BUTTON);
        click(minusButton);
    }

    public String getSubtotal() {
        WebElement subtotal = findElement(SUBTOTAL);
        return subtotal.getText().trim();
    }

    public List<String> getProductPrices() {
        return findElements(PRODUCT_ROW).stream()
                .map(row -> {
                    WebElement price = row.findElement(PRODUCT_PRICE);
                    String priceText = price.getText().trim();
                    int orIndex = priceText.indexOf("or");
                    if (orIndex != -1) {
                        priceText = priceText.substring(0, orIndex).trim();
                    }
                    return priceText;
                })
                .toList();
        }

    public String getQuantity() {
        WebElement quantityField = findElement(QUANTITY_FIELD);
        return quantityField.getDomProperty("value");
    }

    public String getIsbn() {
        WebElement isbnElement = findElement(ISBN);
        return isbnElement.getText().trim();
    }
}
