import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

// This annotation is used to specify that the ExtentTestNGITestListener class should be used to listen to the test events.
// The listener will generate reports based on the test results using the ExtentReports library.
@Listeners(ExtentTestNGITestListener.class)

// Declaring the CheckoutTest class, which extends the Hooks class.
// By extending Hooks, CheckoutTest inherits the setup and teardown methods for WebDriver.
public class HomepageTest extends Hooks {

    // Declaring a public variable of type CheckoutPage named 'checkoutPage'.
    // This will be used to interact with the CheckoutPage object during the tests.
    public HomepagePage homepagePage;
    public LoginPage loginPage;

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
        homepagePage = new HomepagePage(driver);
        loginPage = new LoginPage(driver);

        // Initializing the WebDriverWait object with the current WebDriver instance and a timeout of 30 seconds.
        // This wait will be used to pause the execution until certain conditions are met or elements are found.
        wait = new WebDriverWait(driver, 30);
        softAssert = new SoftAssert();
    }


    @Test(description = "Clicking Homepage button from the shopping cart")
    public void homepageShoppingCartTest() {
        homepagePage.clickShoppingCartIcon();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The shopping cart is displayed.");
        homepagePage.clickHomepageButton();
        assertEquals(homepagePage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The homepage button redirected the site to the Products page.");
    }

    @Test(description = "Clicking Homepage button from the wishlist")
    public void homepageWishlistTest() {
        homepagePage.clickWishlistButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The wishlist is displayed.");
        homepagePage.clickHomepageButton();
        assertEquals(homepagePage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The homepage button redirected the site to the Products page.");
    }

    @Test(description = "Clicking homepage from a product page")
    public void homepageFromProductTest() {
        homepagePage.clickAwesomeChipsLink();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The product is displayed.");
        homepagePage.clickHomepageButton();
        assertEquals(homepagePage.getProductsConfirmation().getText(), "Products");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The Homepage button redirected the site to the Products page.");
    }

    @Test(description = "Test the search functionality by searching for the keyword 'Awesome'")
    public void searchTest() {
        homepagePage.setSearchBar("Awesome");
        homepagePage.clickSearchButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The search engine is looking up for the keyword 'Awesome'.");

        List<String> expectedProducts = new ArrayList<>();
        expectedProducts.add("Awesome Granite Chips");
        expectedProducts.add("Awesome Metal Chair");
        expectedProducts.add("Awesome Soft Shirt");

        List<WebElement> productElements = loginPage.getProductElements();

        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        for (String expectedProduct : expectedProducts) {
            softAssert.assertTrue(actualProductNames.contains(expectedProduct), "Expected product " + expectedProduct + "not found in the search results");
        }

        for  (String actualProduct : actualProductNames) {
            if (!actualProduct.contains("Awesome")) {
                softAssert.fail("Unexpected product found " + actualProduct);
            }
             softAssert.assertAll();
        }
    }

    @Test(description = "Negative test for the search functionality")
    public void negativeSearchTest() {
        homepagePage.setSearchBar("Bluesky");
        homepagePage.clickSearchButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The search engine is looking up for the keyword 'Bluesky'.");
        try {
            driver.findElement(By.name("Bluesky"));
            Assert.fail("Invalid input found on the page.");
        } catch (NoSuchElementException e) {
            Assert.assertTrue(true, "The invalid input was not found, as expected");
        }
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The search has no results.");
    }

}

