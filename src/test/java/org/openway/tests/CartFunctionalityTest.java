package org.openway.tests;

import org.openway.base.BaseTest;
import org.openway.pages.HomePage;
import org.openway.pages.ShoppingCartPage;
import org.openway.utils.SanitizePrice;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartFunctionalityTest extends BaseTest {
    private static final String SEARCH_QUERY = "How to Build a Car";
    private static final String SEARCH_QUERY_2 = "Formula 1";

    @Test(description = "PRPL_SC_01 - Removal of a book from the shopping cart.", priority = 1)
    public void testAddRemoveBookFromCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        //maybe use isbn number instead
        Assert.assertTrue(shoppingCartPage.getBasket().getProductTitles().stream()
                .anyMatch(title -> title.contains(SEARCH_QUERY)));
        homePage = removeBookFlow(shoppingCartPage);
    }

    @Test(description = "PRPL_SC_02 - Addition and removal of multiple books to the shopping cart.", priority = 2)
    public void testAddRemoveMultipleBooksToCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        shoppingCartPage = addBookFlow(SEARCH_QUERY_2);

        List<String> titles = shoppingCartPage.getBasket().getProductTitles();
        Assert.assertTrue(
            titles.stream().anyMatch(title -> title.contains(SEARCH_QUERY)));
        Assert.assertTrue(
            titles.stream().anyMatch(title -> title.contains(SEARCH_QUERY_2)));
        homePage = removeBookFlow(shoppingCartPage);
    }

    @Test(description = "PRPL_SC_03 - Increase and decrease the quantity of a book in the shopping cart.", priority = 3)
    public void testIncreaseDecreaseBookQuantityInCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        String beforeQuantity = shoppingCartPage.getBasket().getQuantity();
        shoppingCartPage.getBasket().clickPlusButton();

        String afterQuantity = shoppingCartPage.getBasket().getQuantity();
        Assert.assertNotEquals(beforeQuantity, afterQuantity);
        shoppingCartPage.getBasket().clickMinusButton();

        String finalQuantity = shoppingCartPage.getBasket().getQuantity();
        Assert.assertEquals(finalQuantity, beforeQuantity);
        homePage = removeBookFlow(shoppingCartPage);
    }

    @Test(description = "PRPL_SC_04 - Verify the subtotal of a book in the shopping cart singular book.", priority = 4)
    public void testVerifyBookSubtotalInCartSingular() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        String priceText = shoppingCartPage.getBasket().getProductPrices().getFirst();
        long price = SanitizePrice.sanitizePrice(priceText);
        long subtotal = SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(subtotal, price);

        shoppingCartPage.getBasket().clickPlusButton();

        new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
            .until(_ -> SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal()) == price * 2);

        long newSubtotal = SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(newSubtotal, price * 2);

        shoppingCartPage.getBasket().clickMinusButton();

        new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
            .until(_ -> SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal()) == price);

        long finalSubtotal = SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(finalSubtotal, price);

        homePage = removeBookFlow(shoppingCartPage);
    }

    @Test(description = "PRPL_SC_05 - Verify the subtotal of multiple books in the shopping cart.", priority = 5)
    public void testVerifyBooksSubtotalInCartMultiple() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        shoppingCartPage = addBookFlow(SEARCH_QUERY_2);

        List<String> pricesText = shoppingCartPage.getBasket().getProductPrices();
        long totalPrice = pricesText.stream()
            .mapToLong(SanitizePrice::sanitizePrice)
            .sum();

        long subtotal = SanitizePrice.sanitizePrice(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(subtotal, totalPrice);

        homePage = removeBookFlow(shoppingCartPage);
    }

    private ShoppingCartPage addBookFlow(String searchQuery) {
        homePage.getNavbar().typeSearch(searchQuery);
        searchPage = homePage.getNavbar().clickSearch();
        productPage = searchPage.getProducts().getFirst().getProductPage();
        productPage.getProductDetailComponent().clickAddToCart();
        shoppingCartPage = productPage.getNavbar().clickCart();
        return shoppingCartPage;
    }

    private HomePage removeBookFlow(ShoppingCartPage shoppingCartPage) {
        shoppingCartPage.getBasket().clearCart();
        Assert.assertTrue(shoppingCartPage.getEmptyMessage()
                .contains("Your shopping cart is empty"));
        return shoppingCartPage.getNavbar().clickHome();
    }
}
