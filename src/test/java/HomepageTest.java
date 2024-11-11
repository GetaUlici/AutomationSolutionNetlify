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
import java.util.Collections;
import java.util.Comparator;
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
        homepagePage = new HomepagePage(driver);
        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPage(driver);

        // Initializing the WebDriverWait object with the current WebDriver instance and a timeout of 30 seconds.
        // This wait will be used to pause the execution until certain conditions are met or elements are found.
        wait = new WebDriverWait(driver, 30);
        softAssert = new SoftAssert();
    }


    @Test(description = "Clicking Homepage button from the shopping cart")
    public void homepageShoppingCartTest() {
        homepagePage.clickShoppingCartIcon();

        if (checkoutPage.getValidationCart().getText().equals("Your cart")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, checkoutPage.getValidationCart().getText() + " is displayed.");
        } else {
            softAssert.fail(checkoutPage.getValidationCart().getText() + " is not displayed.");
        }

        homepagePage.clickHomepageButton();
        assertEquals(homepagePage.getProductsConfirmation().getText(), "Products", "The Homepage button is not directing the user to Products page.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The homepage button directed the user to the Products page.");
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

        List<WebElement> productElements = homepagePage.getProductElements();

        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        for (String expectedProduct : expectedProducts) {
            softAssert.assertTrue(actualProductNames.contains(expectedProduct), "Expected product " + expectedProduct + "not found in the search results");
        }

        for (String actualProduct : actualProductNames) {
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
            if (homepagePage.getMiscProduct().isDisplayed()) {
                Assert.fail("Element 'Bluesky' is still present.");
            }
        } catch (NoSuchElementException e) {
            Assert.assertTrue(true, "Element 'Bluesky' is not displayed, as expected.");
        }
    }

    @Test(description = "Sorting test by Name in alphabetical order")
    public void sortAlphabeticalOrderTest() {
        homepagePage.selectOption(homepagePage.getSortBar(), "Sort by name (A to Z)");
        List<WebElement> productElements = homepagePage.getProductElements();
        List<String> actualProductNames = new ArrayList<>();

        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        expectedProductNames.sort(Comparator.naturalOrder());

        Assert.assertEquals(actualProductNames, expectedProductNames, "The products are not sorted in alphabetical order.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The products are sorted in alphabetical order, from A to Z, as expected.");
    }

    @Test(description = "Sorting test by Name in reverse alphabetical order")
    public void sortTest() {
        homepagePage.selectOption(homepagePage.getSortBar(), "Sort by name (Z to A)");
        List<WebElement> productElements = homepagePage.getProductElements();
        List<String> actualProductNames = new ArrayList<>();

        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        expectedProductNames.sort(Comparator.reverseOrder());

        assertEquals(actualProductNames, expectedProductNames, "The products are not sorted in reverse alphabetical order");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The products are sorted in reverse alphabetical order, from Z to A, as expected.");
    }

    @Test(description = "Sorting test by Ascending Price, from Low to High")
    public void sortingTestAscendingPrice() {
        homepagePage.selectOption(homepagePage.getSortBar(), "Sort by price (low to high)");
        List<WebElement> productPrices = homepagePage.getProductPrices();
        List<Double> actualProductPrices = new ArrayList<>();

        for (WebElement productPrice : productPrices) {
            String priceText = productPrice.getText().replace("$", "");
            actualProductPrices.add(Double.parseDouble(priceText));
        }

        List<Double> expectedPrices = new ArrayList<>(actualProductPrices);
        Collections.sort(expectedPrices);
        Assert.assertEquals(actualProductPrices, expectedPrices, "The products are not sorted by ascending prices.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The products are sorted by ascending prices, from Low to High");
    }

    @Test(description = "Sorting test by Descending price, from High to Low")
    public void sortTestDescending() {
        homepagePage.selectOption(homepagePage.getSortBar(), "Sort by price (high to low)");
        List<WebElement> productPrices = homepagePage.getProductPrices();
        List<Double> actualProductPrices = new ArrayList<>();

        for (WebElement productPrice : productPrices) {
            String priceText = productPrice.getText().replace("$", "");
            actualProductPrices.add(Double.parseDouble(priceText));
        }

        List<Double> expectedPrices = new ArrayList<>(actualProductPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());
        Assert.assertEquals(actualProductPrices, expectedPrices, "The products are not sorted by descending prices.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The products are sorted by descending prices, from High to Low");
    }

    @Test(description = "Clicking Help button")
    public void helpButtonTest() {
        homepagePage.clickHelpButton();
        assertEquals(homepagePage.getConfirmationHelp().getText(), "Help", "When the user is clicking 'Help' button, the Help modal is not displayed");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "When the user clicks 'Help' button, the Help modal with the Valid usernames and Passwords is displayed.");
    }
}

