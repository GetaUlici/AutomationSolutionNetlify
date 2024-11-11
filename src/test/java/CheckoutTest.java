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
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product " + checkoutPage.getAwesomeChipsProduct().getText() + " is found in the shopping cart.");
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

        if (checkoutPage.getValidationCart().getText().equals("Your cart")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, checkoutPage.getValidationCart().getText() + " is displayed.");
        } else {
            softAssert.fail(checkoutPage.getValidationCart().getText() + " is not displayed.");
        }

        assertEquals(checkoutPage.getIncredibleConcreteProduct().getText(), "Incredible Concrete Hat", "'Incredible Concrete Hat' is not displayed to the shopping cart");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Incredible Concrete Hat product is displayed on the shopping cart.");
        softAssert.assertAll();
    }

    @Test(description = "Removing a product from the shopping cart")
    public void removingProductFromShoppingCartTest() {
        checkoutPage.clickIncredibleConcreteLink();
        checkoutPage.clickCartIcon();
        checkoutPage.clickShoppingCartIcon();

        if (checkoutPage.getIncredibleConcreteProduct().getText().equals("Incredible Concrete Hat")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, checkoutPage.getIncredibleConcreteProduct().getText() + " was successfully added to the cart.");
        } else {
            softAssert.fail(checkoutPage.getIncredibleConcreteProduct().getText() + " is not found in the cart.");
        }

        checkoutPage.clickTrashElement();
        assertEquals(checkoutPage.getValidationDeleting().getText(), "How about adding some products in your cart?", "'Incredible Concrete Hat' was not successfully removed from the cart.");
        assertEquals(checkoutPage.getValidationCart().getText(), "Your cart", "The user is not directed to the Cart after deleting a product.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "'Incredible Concrete Hat' product was deleted and it's not displayed any more.");
        softAssert.assertAll();
    }

    @Test(description = "Increase the amount of a product")
    public void increasedAmountTest() {
        checkoutPage.addProductToCart();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPrice());
        double expectedTotal = checkoutPage.productPrice() * 2;
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product after quantity increase should be: " + expectedTotal);
        checkoutPage.clickPlusOne();
        assertEquals(checkoutPage.productPrice(), expectedTotal, "The total price of the increased amount is not displayed correctly.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The price of the product matches the expected total " + checkoutPage.productPrice() + " = " + expectedTotal);
    }

    @Test(description = "Calculate the Total price for a product")
    public void totalPriceForAProduct() {
        checkoutPage.addProductToCart();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is: " + checkoutPage.productPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The tax price of the product is: " + checkoutPage.taxPrice());
        double expectedTotal = checkoutPage.productPrice() + checkoutPage.taxPrice();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The actual total price of the product is: " + checkoutPage.totalPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The expected total price of the product is: " + expectedTotal);
        assertEquals(checkoutPage.totalPrice(), expectedTotal, "The total price of the product (price + tax price) is not displayed correctly.");
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
        assertEquals(checkoutPage.productPrice(), expectedTotal, "The total price of the two products: " + checkoutPage.getAwesomeShirt().getText() + " and " + checkoutPage.getAwesomeChipsProduct().getText() + " is not displayed correctly.");
        softAssert.assertAll();
    }

    @Test(description = "Purchase a product from the Wishlist")
    public void purchaseFromWishlistTest() {
        checkoutPage.addProductToWishlist();

        if (checkoutPage.getShoppingCartBadge().getText().equals("1")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Wishlist Badge was updated with success, the product 'Awesome Granite Chips' was added to the Wishlist.");
        } else {
            softAssert.fail("The Wishlist Badge was not updated correctly.");
        }

        checkoutPage.clickHomepageButton();
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product added successfully in the Shopping cart: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product: " + checkoutPage.getAwesomeChipsProduct().getText() + " is not found");
        }

        checkoutPage.clickCheckoutButton();

        if (checkoutPage.getCheckoutPage().getText().equals("Your information")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "After clicking the Checkout button, the Checkout Page is displayed.");
        } else {
            softAssert.fail("The Checkout Page is not displayed even if the user clicks on Checkout button.");
        }

        checkoutPage.addDeliveryDetailsToOrder();
        checkoutPage.clickContinueCheckoutButton();

        if (checkoutPage.getOrderSummary().getText().equals("Order summary")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "After clicking Continue checkout button, 'Order summary' is displayed.");
        } else {
            softAssert.fail("The Order summary page was not successfully displayed.");
        }

        checkoutPage.clickCompleteYourOrderButton();
        assertEquals(checkoutPage.getSuccessMessage().getText(), "Order complete", "No confirmation message for completing order displayed.");
        assertEquals(checkoutPage.getOrderConfirmation().getText(), "Thank you for your order!", "The order wasn't successfully placed.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The order has been successfully placed.");
        softAssert.assertAll();
    }

    @Test(description = "Verify 'Continue shopping' functionality")
    public void continueShoppingTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product " + checkoutPage.getAwesomeChipsProduct().getText() + " is found in the shopping cart.");
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found.");
        }

        if (checkoutPage.getValidationCart().getText().equals("Your cart")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, checkoutPage.getValidationCart().getText() + " is displayed.");
        } else {
            softAssert.fail(checkoutPage.getValidationCart().getText() + " is not displayed.");
        }

        checkoutPage.clickContinueShoppingButton();
        assertEquals(checkoutPage.getProductsConfirmation().getText(), "Products", "The 'Continue button' does not direct the user to Products page");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "Products page is displayed and the user is able to continue shopping.");
        softAssert.assertAll();
    }

    @Test(description = "Cancel the order from the Checkout page")
    public void cancelTheOrderTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product " + checkoutPage.getAwesomeChipsProduct().getText() + " is found in the shopping cart.");
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + "is not found");
        }

        checkoutPage.clickCheckoutButton();

        if (checkoutPage.getCheckoutPage().getText().equals("Your information")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "After clicking the Checkout button, the Checkout Page is displayed.");
        } else {
            softAssert.fail("The Checkout Page is not displayed even if the user clicks on Checkout button.");
        }

        checkoutPage.clickCancelButton();

        if (checkoutPage.getValidationCart().getText().equals("Your cart")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, checkoutPage.getValidationCart().getText() + " is displayed.");
        } else {
            softAssert.fail(checkoutPage.getValidationCart().getText() + " is not displayed.");
        }
        assertEquals(checkoutPage.getAwesomeChipsProduct().getText(), "Awesome Granite Chips", "The selected product " + checkoutPage.getAwesomeChipsProduct().getText() + " is not displayed to the Shopping cart as expected.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The 'Awesome Granite Chips' product is displayed to the Shopping Cart page again.");
        softAssert.assertAll();
    }

    @Test(description = "Clicking reset when a product is added to the shopping cart")
    public void shoppingCartResetTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
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

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found in the Wishlist: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
        }

        checkoutPage.clickResetButton();

        try {
            if (checkoutPage.getAwesomeChipsProduct().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The page was reset, the product was removed from the wishlist.");
            Assert.assertTrue(true, "Element is not present in the Wishlist as expected");
        }

        softAssert.assertAll();
    }

    @Test(description = "Validation of 'First Name' field on Checkout page")
    public void firstNameValidationTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found in the shopping cart: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
        }

        checkoutPage.clickCheckoutButton();
        checkoutPage.setFirstNameField("");
        checkoutPage.setLastNameField("Amariei");
        checkoutPage.setAddressField("Acasa la Floresti");
        checkoutPage.clickContinueCheckoutButton();
        Assert.assertEquals(checkoutPage.getValidationFirstField().getText(), "First Name is required", "When the required field 'First name' is submitted empty, no error is returned.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "When 'First Name' field is submitted empty, the error: '" + checkoutPage.getValidationFirstField().getText() + "' is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Validation of 'Last Name' field on Checkout page")
    public void lastNameValidationTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found in the shopping cart: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
        }

        checkoutPage.clickCheckoutButton();
        checkoutPage.setFirstNameField("Vasile");
        checkoutPage.setLastNameField("");
        checkoutPage.setAddressField("Acasa la Floresti");
        checkoutPage.clickContinueCheckoutButton();
        Assert.assertEquals(checkoutPage.getValidationLastField().getText(), "Last Name is required", "When the required field 'Last Name' is submitted empty, no error is returned.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "When 'Last Name' field is submitted empty, the error: '" + checkoutPage.getValidationLastField().getText() + "' is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Validation of 'Address' field on Checkout page")
    public void addressValidationTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found in the shopping cart: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
        }

        checkoutPage.clickCheckoutButton();
        checkoutPage.setFirstNameField("Vasile");
        checkoutPage.setLastNameField("Ionache");
        checkoutPage.setAddressField("");
        checkoutPage.clickContinueCheckoutButton();
        Assert.assertEquals(checkoutPage.getValidationAddressField().getText(), "Address is required", "When the required field 'Address' is submitted empty, no error is returned.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "When the required 'Address' field is left empty, the error: '" + checkoutPage.getValidationAddressField().getText() + "' is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "When two fields are left empty on Checkout form, two errors should be displayed")
    public void twoFieldsEmptyTest() {
        checkoutPage.addAwesomeChipsToCart();

        if (checkoutPage.getAwesomeChipsProduct().getText().equals("Awesome Granite Chips")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "Product found in the shopping cart: " + checkoutPage.getAwesomeChipsProduct().getText());
        } else {
            softAssert.fail("Shopping cart was not updated correctly, the product " + checkoutPage.getAwesomeChipsProduct().getText() + " was not found");
        }

        checkoutPage.clickCheckoutButton();
        checkoutPage.setFirstNameField("");
        checkoutPage.setLastNameField("");
        checkoutPage.setAddressField("Catch me if you can");
        checkoutPage.clickContinueCheckoutButton();

        if (checkoutPage.getValidationFirstField().getText().equals("First Name is required")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "When 'First Name' field is left empty, the error '" + checkoutPage.getValidationFirstField().getText() + "' is displayed.");
        } else {
            softAssert.fail("When the required field 'First Name' is submitted empty, no error is returned.");
        }

        if (checkoutPage.getValidationLastField().getText().equals("Last Name is required")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "When 'Last Name' field is left empty, the error: '" + checkoutPage.getValidationLastField().getText() + "' is displayed.");
        } else {
            softAssert.fail("When the required field 'Last Name' is submitted empty, no error is returned.");
        }

        softAssert.assertAll();
    }
}

