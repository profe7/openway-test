package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openway.base.BasePage;
import org.openway.components.productpage.ProductCarouselComponent;
import org.openway.components.productpage.ProductDetailComponent;

public class ProductPage extends BasePage {
    private static final By PRODUCT_DETAIL_ROOT = By.cssSelector("body > div.container.mt-4 > div:nth-child(1)");
    private static final By CAROUSEL_ROOT = By.cssSelector("body > div.container.mt-4 > div:nth-child(2)");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductDetailComponent getProductDetailComponent() {
        return new ProductDetailComponent(findElement(PRODUCT_DETAIL_ROOT), driver);
    }

    public ProductCarouselComponent getProductCarouselComponent() {
        return new ProductCarouselComponent(findElement(CAROUSEL_ROOT), driver);
    }
}
