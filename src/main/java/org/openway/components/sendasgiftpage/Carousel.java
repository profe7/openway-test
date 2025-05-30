package org.openway.components.sendasgiftpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openway.components.base.BaseComponent;

public class Carousel extends BaseComponent {
    private static final By CARD_TEXT = By.cssSelector("div > div:nth-child(1) > h3");

    public Carousel(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getCardText() {
        WebElement cardTextElement = findElement(CARD_TEXT);
        return cardTextElement.getText().trim();
    }
}
