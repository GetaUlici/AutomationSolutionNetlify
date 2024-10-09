import com.aventstack.extentreports.Status;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

// This annotation is used to specify that the ExtentTestNGITestListener class should be used to listen to the test events.
// The listener will generate reports based on the test results using the ExtentReports library.
@Listeners(ExtentTestNGITestListener.class)

// Declaring the CheckoutTest class, which extends the Hooks class.
// By extending Hooks, CheckoutTest inherits the setup and teardown methods for WebDriver.
public class CheckoutTest extends Hooks {

    // Declaring a public variable of type CheckoutPage named 'checkoutPage'.
    // This will be used to interact with the CheckoutPage object during the tests.
    public CheckoutPage checkoutPage;

    // Declaring a public variable of type WebDriverWait named 'wait'.
    // WebDriverWait is used to explicitly wait for certain conditions or elements during test execution.
    public WebDriverWait wait;

    public SoftAssert softAssert;

    // Method annotated with @BeforeMethod, indicating that it will run before each test method.
    // This method is used to set up the page objects and other necessary components before each test.
    @BeforeMethod
    public void SetupPageObject() {

        // Initializing the checkoutPage object with the current WebDriver instance.
        // This allows the test methods to interact with elements on the checkout page.
        checkoutPage = new CheckoutPage(driver);

        // Initializing the WebDriverWait object with the current WebDriver instance and a timeout of 30 seconds.
        // This wait will be used to pause the execution until certain conditions are met or elements are found.
        wait = new WebDriverWait(driver, 30);
        softAssert = new SoftAssert();
    }

    @Test(description = "Purchasing a simple product from a guest user")
    public void checkoutTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " is not found");
        }

        checkoutPage.clickCheckoutButton();
        checkoutPage.addDeliveryDetailsToOrder();
        checkoutPage.clickContinueCheckoutButton();
        checkoutPage.clickCompleteYourOrderButton();

        if (checkoutPage.getSuccessMessage().getText().equals("Order complete")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The order was placed successfully.");
        } else {
            softAssert.fail("The order was not successfully placed.");
        }
        softAssert.assertAll();
    }

    @Test(description = "Adding a product to wishlist")
    public void wishlistTest() {
        checkoutPage.addProductToWishlist();
        if (checkoutPage.getShoppingCartBadge().getText().equals("1")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Shopping Cart badge was updated with success.");
        } else {
            softAssert.fail("Shopping cart was not updated correctly");
        }

        checkoutPage.clickShoppingCartBadge();
        assertEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips", "The product " + checkoutPage.getAwesomeChipsProduct().getText() + " is not displayed in the Wishlist.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The product " + checkoutPage.getAwesomeChipsProduct().getText() + " was found in the Wishlist.");
        softAssert.assertAll();
    }

    @Test(description = "Removing a product from wishlist")
    public void removeProductFromWishlist() {
        checkoutPage.addProductToWishlist();
        if (checkoutPage.getShoppingCartBadge().getText().equals("1")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Shopping Cart badge was updated with success.");
        } else {
            softAssert.fail("Shopping cart badge was not updated correctly.");
        }
        checkoutPage.clickShoppingCartBadge();
        checkoutPage.clickBrokenHeartIcon();
        try {
            if (checkoutPage.getAwesomeChipsProduct().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Awesome Granite Product was removed from the Wishlist.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
        softAssert.assertAll();
    }

    @Test(description = "Adding a product to the shopping cart")
    public void addingProductTosShoppingCartTest() {
        checkoutPage.clickIncredibleConcreteLink();
        checkoutPage.clickCartIcon();
        checkoutPage.clickShoppingCartIcon();
        assertEquals(checkoutPage.getValidationCart().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The cart is displayed.");
        assertEquals(checkoutPage.getIncredibleConcreteProduct().getText(), "Incredible Concrete Hat");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Incredible Concrete Hat product is displayed on the checkout cart.");
    }

    @Test(description = "Removing a product from the shopping cart")
    public void removingProductFromShoppingCartTest() {
        checkoutPage.clickIncredibleConcreteLink();
        checkoutPage.clickCartIcon();
        checkoutPage.clickShoppingCartIcon();
        assertEquals(checkoutPage.getValidationCart().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The cart is displayed.");
        assertEquals(checkoutPage.getIncredibleConcreteProduct().getText(), "Incredible Concrete Hat");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Incredible Concrete Hat product is displayed on the checkout cart.");
        checkoutPage.clickTrashElement();
        assertEquals(checkoutPage.getValidationDeleting().getText(), "How about adding some products in your cart?");
        assertEquals(checkoutPage.getValidationCart().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Incredible Concrete Hat product was deleted and is not displayed any more.");
    }

    @Test(description = "Increase the amount of a product")
    public void increasedAmountTest() {
        checkoutPage.addProductToCart();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPrice());
        double expectedTotal = checkoutPage.productPrice() * 2;
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product after quantity increase should be: " + expectedTotal);
        checkoutPage.clickPlusOne();
        assertEquals(checkoutPage.productPrice(), expectedTotal);
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The price of the product matches the expected total " + checkoutPage.productPrice() + " = " + expectedTotal);
    }

    @Test(description = "Calculate the Total price for a product")
    public void totalPriceForAProduct() {
        checkoutPage.addProductToCart();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The tax price of the product is: " + checkoutPage.taxPrice());
        System.out.println(checkoutPage.totalPrice());
        double expectedTotal = checkoutPage.productPrice() + checkoutPage.taxPrice();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The actual total price of the product is: " + checkoutPage.totalPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The expected total price of the product is: " + expectedTotal);
        assertEquals(checkoutPage.totalPrice(), expectedTotal);
    }

    @Test(description = "Calculate the Total price for two a products")
    public void totalPriceForTwoProducts() {
        checkoutPage.addProductToCart();
        if (checkoutPage.getAwesomeShirt().getText().equals("Awesome Soft Shirt")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product added to the cart " + checkoutPage.getAwesomeShirt().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeShirt().getText() + "is not found");
        }
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPriceShirt());
        checkoutPage.clickHomepageButton();
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product added to the cart " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }

      checkoutPage.clickShoppingCartIcon();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPriceChips());
        double expectedTotal = checkoutPage.productPriceShirt() + checkoutPage.productPriceChips();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The actual total of the products is: " + checkoutPage.productPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The expected total of the products is: " + (checkoutPage.productPriceShirt() + checkoutPage.productPriceChips()));
        assertEquals(checkoutPage.productPrice(), expectedTotal);
        softAssert.assertAll();
    }

    @Test(description = "Purchase a product from the wishlist")
    public void purchaseFromWishlistTest() {
        checkoutPage.addProductToWishlist();

        if (checkoutPage.getShoppingCartBadge().getText().equals("1")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Shopping Cart badge was updated with success.");
        } else {
            softAssert.fail("Shopping cart was not updated correctly");
        }

        checkoutPage.clickHomepageButton();
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }

        checkoutPage.clickCheckoutButton();
        assertEquals(checkoutPage.getCheckoutPage().getText(), "Your information");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The checkout page was displayed.");
        checkoutPage.addDeliveryDetailsToOrder();
        checkoutPage.clickContinueCheckoutButton();
        assertEquals(checkoutPage.getOrderSummary().getText(), "Order summary");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Order summary is displayed.");
        checkoutPage.clickCompleteYourOrderButton();
        assertEquals(checkoutPage.getSuccessMessage().getText(), "Order complete");
        assertEquals(checkoutPage.getOrderConfirmation().getText(), "Thank you for your order!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The order has been placed.");
        softAssert.assertAll();
    }

    @Test(description = "Verify 'Continue shopping' functionality")
    public void continueShoppingTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }
        assertEquals(checkoutPage.getCartConfirmation().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The shopping cart is displayed");
        checkoutPage.clickContinueShoppingButton();
        assertEquals(checkoutPage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Products page is displayed and the user is able to continue shopping.");
        softAssert.assertAll();
    }

    @Test(description = "Cancel the order from the checkout")
    public void cancelTheOrderTest() {
        checkoutPage.addAwesomeChipsToCart();
        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }
        assertEquals(checkoutPage.getCartConfirmation().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The shopping cart is displayed");
        checkoutPage.clickCheckoutButton();
        assertEquals(checkoutPage.getCheckoutPage().getText(), "Your information");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The checkout page was displayed.");
        checkoutPage.clickCancelButton();
        assertEquals(checkoutPage.getCartConfirmation().getText(), "Your cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Cart page was displayed");
        assertEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The 'Awesome Granite Chips' product is displayed on the shopping cart page again.");
        softAssert.assertAll();
    }

    @Test(description = "Clicking Homepage button from the shopping cart")
    public void homepageShoppingCartTest() {
        checkoutPage.clickShoppingCartIcon();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The shopping cart is displayed.");
        checkoutPage.clickHomepageButton();
        assertEquals(checkoutPage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The homepage button redirected the site to the Products page.");
    }

    @Test(description = "Clicking Homepage button from the wishlist")
    public void homepageWishlistTest() {
        checkoutPage.clickWishlistButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The wishlist is displayed.");
        checkoutPage.clickHomepageButton();
        assertEquals(checkoutPage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The homepage button redirected the site to the Products page.");
    }

    @Test(description = "Clicking homepage from a product page")
    public void homepageFromProductTest() {
        checkoutPage.clickAwesomeChipsLink();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The product is displayed.");
        checkoutPage.clickHomepageButton();
        assertEquals(checkoutPage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Homepage button redirected the site to the Products page.");
    }

    @Test(description = "Clicking Help button")
    public void helpButtonTest() {
        checkoutPage.clickHelpButton();
        assertEquals(checkoutPage.getConfirmationHelp().getText(), "Help");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Help modal with the Valid usernames is displayed.");
    }

    @Test(description = "Clicking reset when a product is added to the shopping cart")
    public void shoppingCartResetTest() {
        checkoutPage.addAwesomeChipsToCart();
        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }
        checkoutPage.clickResetButton();
        try {
            if (checkoutPage.getAwesomeChipsProduct().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The page was reset, the product was removed from the shopping cart.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
        softAssert.assertAll();
    }

    @Test(description = "Clicking reset when a product is added to the wishlist")
    public void wishlistResetTest() {
        checkoutPage.addProductToWishlist();
        checkoutPage.clickWishlistButton();
        assertEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips");//soft assert cu if
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Awesome granite chips was added to the wishlist.");
        checkoutPage.clickResetButton();
        try {
            if (checkoutPage.getAwesomeChipsProduct().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The page was reset, the product was removed from the wishlist.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
    }
}

