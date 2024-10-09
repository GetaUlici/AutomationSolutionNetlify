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
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

// This annotation is used to specify that the ExtentTestNGITestListener class should be used to listen to the test events.
// The listener will generate reports based on the test results using the ExtentReports library.
@Listeners(ExtentTestNGITestListener.class)

// Declaring the CheckoutTest class, which extends the Hooks class.
// By extending Hooks, CheckoutTest inherits the setup and teardown methods for WebDriver.
public class LoginTest extends Hooks {

    // Declaring a public variable of type CheckoutPage named 'checkoutPage'.
    // This will be used to interact with the CheckoutPage object during the tests.
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
        loginPage = new LoginPage(driver);

        // Initializing the WebDriverWait object with the current WebDriver instance and a timeout of 30 seconds.
        // This wait will be used to pause the execution until certain conditions are met or elements are found.
        wait = new WebDriverWait(driver, 30);
        softAssert = new SoftAssert();
    }

    @Test(description = "Login test")
    public void loginTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getUserLoggedIn().getText(), "dino");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user 'dino' is logged in");
    }

    @Test(description = "Successfully sign out a user")
    public void signOutUserTest() {
        loginPage.loginDino();
//        assertEquals(loginPage.getUserLoggedIn().getText(), "dino");
//        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user Dino is logged in.");
        loginPage.clickSignOutButton();
        try {
            if (loginPage.getUserLoggedIn().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The user Dino is signed out.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
    }

    @Test(description = "Entering incorrect username")
    public void incorrectUsernameTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("rino");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
    }

    @Test(description = "Entering incorrect password")
    public void incorrectPasswordTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("choo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
    }

    @Test(description = "Entering incorrect username and incorrect password")
    public void incorrectUsernameAndPasswordTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("rino");
        loginPage.setPasswordField("choo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
    }

    @Test(description = "Null username field")
    public void nullUsernameFieldTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorNullUsername().getText(), "Please fill in the username!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, the Username field was empty, error message is displayed.");
    }

    @Test(description = "Null password field")
    public void nullPasswordFieldTest() {
        loginPage.clickLoginIcon();
        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorNullPassword().getText(), "Please fill in the password!");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, the Password field was empty, error message is displayed.");
    }

    @Test(description = "Clicking Reset when a user is logged in")
    public void resetLoggedUserTest() {
        loginPage.loginDino();
        //soft assert dino?
        loginPage.clickWhenReady(loginPage.getResetButton());
        try {
            if (loginPage.getUserLoggedIn().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The page was reset, the user Dino is signed out.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
    }

    @Test(description = "Sorting test by name")
    public void sortTest() throws InterruptedException {
        loginPage.selectOption(loginPage.getSortBar(), "Sort by name (Z to A)");
        List<WebElement> productElements = loginPage.getProductElements();
        List<String> actualProductNames = new ArrayList<>();

        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        expectedProductNames.sort(Comparator.reverseOrder());

        assertEquals(actualProductNames, expectedProductNames, "The products are not sorted in reverse alphabetical order");
    }

    @Test(description = "Second Sorting test by name ")
    public void sortTestSecond() throws InterruptedException {
        loginPage.selectOption(loginPage.getSortBar(), "Sort by name (A to Z)");
        List<WebElement> productElements = loginPage.getProductElements();
        List<String> actualProductNames = new ArrayList<>();

        for (WebElement productElement : productElements) {
            actualProductNames.add(productElement.getText());
        }

        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        expectedProductNames.sort(Comparator.naturalOrder());

        Assert.assertEquals(actualProductNames, expectedProductNames, "The products are not sorted in alphabetical order.");
    }

    @Test(description = "Sorting test Ascending Price")
    public void sortingTestAscending() throws InterruptedException {
        loginPage.selectOption(loginPage.getSortBar(), "Sort by price (low to high)");
        List<WebElement> productPrices = loginPage.getProductPrices();
        List<Double> actualProductPrices = new ArrayList<>();

        for (WebElement productPrice : productPrices) {
            String priceText = productPrice.getText().replace("$", "");
            actualProductPrices.add(Double.parseDouble(priceText));
        }
        System.out.println(actualProductPrices);

        List<Double> expectedPrices = new ArrayList<>(actualProductPrices);
        Collections.sort(expectedPrices);
        Assert.assertEquals(actualProductPrices, expectedPrices, "The prices are not ascending");

    }

    @Test(description = "Sorting test Descending price")
    public void sortTestDescending() throws InterruptedException {
        loginPage.selectOption(loginPage.getSortBar(), "Sort by price (high to low)");
        List<WebElement> productPrices = loginPage.getProductPrices();
        List<Double> actualProductPrices = new ArrayList<>();

        for (WebElement productPrice : productPrices) {
            String priceText = productPrice.getText().replace("$", "");
            actualProductPrices.add(Double.parseDouble(priceText));
        }
        System.out.println(actualProductPrices);

        List<Double> expectedPrices = new ArrayList<>(actualProductPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());
        Assert.assertEquals(actualProductPrices, expectedPrices, "The prices are not descending");

    }


}



