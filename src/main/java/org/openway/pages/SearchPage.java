package org.openway.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.base.BasePage;
import org.openway.components.searchpage.SearchProductComponent;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {
    private static final By PRODUCT = By.className("single-product");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public List<SearchProductComponent> getProducts() {
        List<WebElement> productElements = findElements(PRODUCT);
        List<SearchProductComponent> products = new ArrayList<>();
        for (WebElement productElement : productElements) {
            products.add(new SearchProductComponent(productElement, driver));
        }
        return products;
    }
}
