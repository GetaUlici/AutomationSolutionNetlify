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

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getUserLoggedIn().getText(), "dino", "The user was unable to login.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user 'dino' is successfully logged in");
        softAssert.assertAll();
    }

    @Test(description = "Successfully sign out a user test")
    public void signOutUserTest() {
        loginPage.loginDino();

        if (loginPage.getUserLoggedIn().getText().equals("dino")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The user '" + loginPage.getUserLoggedIn().getText() + "' was successfully logged in.");
        } else {
            softAssert.fail("The user 'dino' has not successfully log in.");
        }

        loginPage.clickSignOutButton();

        try {
            if (loginPage.getUserLoggedIn().isDisplayed()) {
                Assert.fail("Element " + loginPage.getUserLoggedIn().getText() + " is still present even if it's expected not to be.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The user Dino is successfully signed out.");
            Assert.assertTrue(true, "Element is not present as expected");
        }

        softAssert.assertAll();
    }

    @Test(description = "Entering incorrect username test")
    public void incorrectUsernameTest() {
        loginPage.clickLoginIcon();

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("rino");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!", "No error is displayed when user enters incorrect Username.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Entering incorrect password")
    public void incorrectPasswordTest() {
        loginPage.clickLoginIcon();

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("choo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!", "No error is displayed when user enters incorrect Password.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Entering incorrect username and incorrect password")
    public void incorrectUsernameAndPasswordTest() {
        loginPage.clickLoginIcon();

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("rino");
        loginPage.setPasswordField("choo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorMessage().getText(), "Incorrect username or password!", "No errors are displayed when user introduces incorrect Username ans Password.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, error message is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Blank input on username field")
    public void nullUsernameFieldTest() {
        loginPage.clickLoginIcon();

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("");
        loginPage.setPasswordField("choochoo");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorNullUsername().getText(), "Please fill in the username!", "No error is displayed when the Username field is submitted empty.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, the Username field was empty, error message is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Blank input on  password field")
    public void nullPasswordFieldTest() {
        loginPage.clickLoginIcon();

        if (loginPage.getConfirmationLogin().getText().equals("Login")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, loginPage.getConfirmationLogin().getText() + " Modal was successfully displayed.");
        } else {
            softAssert.fail("Login Modal is not displayed on the page.");
        }

        loginPage.setUserNameField("dino");
        loginPage.setPasswordField("");
        loginPage.clickLoginButton();
        assertEquals(loginPage.getErrorNullPassword().getText(), "Please fill in the password!", "No error is displayed when the Password field is submitted empty.");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user was unable to login, the Password field was empty, error message is displayed.");
        softAssert.assertAll();
    }

    @Test(description = "Clicking Reset when a user is logged in")
    public void resetLoggedUserTest() {
        loginPage.loginDino();

        if (loginPage.getUserLoggedIn().getText().equals("dino")) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The user '" + loginPage.getUserLoggedIn().getText() + "' was successfully logged in.");
        } else {
            softAssert.fail("The user 'dino' has not successfully log in.");
        }

        loginPage.clickWhenReady(loginPage.getResetButton());

        try {
            if (loginPage.getUserLoggedIn().isDisplayed()) {
                Assert.fail("Element is still present.");
            }
        } catch (NoSuchElementException e) {
            ExtentTestNGITestListener.getTest().log(Status.PASS, "The page was reset, the user Dino is signed out.");
            Assert.assertTrue(true, "Element is not present as expected");
        }
        softAssert.assertAll();
    }
}



