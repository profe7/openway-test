package org.openway.tests;

import org.openway.base.BaseTest;
import org.openway.pages.ShoppingCartPage;
import org.openway.utils.SanitizeNumbers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartFunctionalityTest extends BaseTest {
    private static final String SEARCH_QUERY = "How to Build a Car";
    private static final String SEARCH_QUERY_2 = "Formula 1";
    private static final String CARD_STRING = "Card";
    private static final String PERIPLUS_STRING = "Periplus Online Bookstore";
    private static final String HOMEPAGE_TITLE = "Periplus Online Bookstore - Toko Buku Online murah, terbaik, terpercaya, lengkap dan besar buku lokal & Impor di Indonesia (Biggest - Trusted - Cheapest - Complete - Best Local & Imported Book Retail store - Bookshop)";

    private String ISBN_EXPECTED = "";

    @Test(description = "PRPL_SC_01 - Removal of a book from the shopping cart.", priority = 1)
    public void testAddRemoveBookFromCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        Assert.assertTrue(shoppingCartPage.getBasket().getProductTitles().stream()
                .anyMatch(title -> title.contains(SEARCH_QUERY)));
        String ISBN_SHOPPING_CART = shoppingCartPage.getBasket().getIsbn();
        Assert.assertEquals(ISBN_SHOPPING_CART, ISBN_EXPECTED);

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
    }

    @Test(description = "PRPL_SC_04 - Verify the subtotal of a book in the shopping cart singular book.", priority = 4)
    public void testVerifyBookSubtotalInCartSingular() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        String priceText = shoppingCartPage.getBasket().getProductPrices().getFirst();
        long price = SanitizeNumbers.sanitizeNumbers(priceText);
        long subtotal = SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(subtotal, price);

        shoppingCartPage.getBasket().clickPlusButton();

        new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
            .until(_ -> SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal()) == price * 2);

        long newSubtotal = SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(newSubtotal, price * 2);

        shoppingCartPage.getBasket().clickMinusButton();

        new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
            .until(_ -> SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal()) == price);

        long finalSubtotal = SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(finalSubtotal, price);
    }

    @Test(description = "PRPL_SC_05 - Verify the subtotal of multiple books in the shopping cart.", priority = 5)
    public void testVerifyBooksSubtotalInCartMultiple() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        shoppingCartPage = addBookFlow(SEARCH_QUERY_2);

        List<String> pricesText = shoppingCartPage.getBasket().getProductPrices();
        long totalPrice = pricesText.stream()
            .mapToLong(SanitizeNumbers::sanitizeNumbers)
            .sum();

        long subtotal = SanitizeNumbers.sanitizeNumbers(shoppingCartPage.getBasket().getSubtotal());
        Assert.assertEquals(subtotal, totalPrice);
    }

    @Test(description = "PRPL_SC_06 - To update the cart.", priority = 6)
    public void testUpdateCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        Long previousPoints = shoppingCartPage.getBasket().getPoints().getFirst();
        shoppingCartPage.getBasket().clickPlusButton();
        shoppingCartPage.getBasket().clickUpdateButton();
        Long updatedPoints = shoppingCartPage.getBasket().getPoints().getFirst();
        Assert.assertEquals(updatedPoints, previousPoints * 2);

        shoppingCartPage.getBasket().clickMinusButton();
        shoppingCartPage.getBasket().clickUpdateButton();
        Long finalPoints = shoppingCartPage.getBasket().getPoints().getFirst();
        Assert.assertEquals(finalPoints, previousPoints);
    }

    @Test(description = "PRPL_SC_07 - To send as gift from the cart.", priority = 7)
    public void testSendAsGiftFromCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        sendAsGiftPage = shoppingCartPage.getBasket().clickSendAsGiftButton();
        Assert.assertTrue(sendAsGiftPage.getCarousel().getCardText().contains(CARD_STRING));
    }

    @Test(description = "PRPL_SC_08 - To continue shopping from the cart.", priority = 8)
    public void testContinueShoppingFromCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        homePage = shoppingCartPage.getBasket().clickContinueShoppingButton();
        Assert.assertTrue(homePage.getTitle().contains(HOMEPAGE_TITLE));
    }

    @Test(description = "PRPL_SC_09 - To proceed to checkout from the cart.", priority = 9)
    public void testProceedToCheckoutFromCart() {
        shoppingCartPage = addBookFlow(SEARCH_QUERY);
        checkoutPage = shoppingCartPage.getBasket().clickCheckoutButton();
        Assert.assertTrue(checkoutPage.getCheckoutLogoTitle().contains(PERIPLUS_STRING));
        driver.navigate().back();
        shoppingCartPage = new ShoppingCartPage(driver);
    }


    private ShoppingCartPage addBookFlow(String searchQuery) {
        homePage.getNavbar().typeSearch(searchQuery);
        searchPage = homePage.getNavbar().clickSearch();
        productPage = searchPage.getProducts().getFirst().getProductPage();
        productPage.getProductDetailComponent().clickAddToCart();
        ISBN_EXPECTED = productPage.getProductCarouselComponent().getISBN();
        shoppingCartPage = productPage.getNavbar().clickCart();
        return shoppingCartPage;
    }
}
