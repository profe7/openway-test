package org.openway.components.productpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;

import java.util.List;

public class ProductCarouselComponent extends BaseComponent {
    private static final By NEXT_BUTTON = By.cssSelector("div > div > div.owl-controls > div.owl-nav > div.owl-next > i");
    private static final By OWL_ITEMS = By.cssSelector(".owl-item");

    public ProductCarouselComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getISBN() {
        List<WebElement> owlItems = findElements(OWL_ITEMS);
        for (WebElement item : owlItems) {
            WebElement isbnElement = item.findElement(By.cssSelector("p:nth-child(3)"));
            if (!isbnElement.getText().isEmpty() && isbnElement.getText().length() == 13 && isNumeric(isbnElement.getText())) {
                return isbnElement.getText().trim();
            }
        }
        clickNextButton();
        return getISBN();
    }

    public void clickNextButton() {
        WebElement nextButton = findElement(NEXT_BUTTON);
        click(nextButton);
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
