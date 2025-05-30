package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;
import org.openway.components.productpage.ProductDetailComponent;

public class ProductPage extends BasePage {
    private static final By PRODUCT_DETAIL_ROOT = By.cssSelector(".row.row-product-detail");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductDetailComponent getProductDetailComponent() {
        return new ProductDetailComponent(findElement(PRODUCT_DETAIL_ROOT), driver);
    }
}
