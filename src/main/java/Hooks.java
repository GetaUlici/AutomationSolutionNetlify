// Importing ExtentReports, ExtentTest, and ExtentSparkReporter classes from the AventStack ExtentReports library.
// These classes are used for generating detailed and customizable test reports.
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// Importing WebDriverManager from the Bonigarcia library.
// WebDriverManager automates the management of WebDriver binaries (e.g., ChromeDriver, GeckoDriver).
import io.github.bonigarcia.wdm.WebDriverManager;

// Importing WebDriver and ChromeDriver classes from the Selenium library.
// WebDriver is used for interacting with the browser, and ChromeDriver is the specific implementation for Google Chrome.
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// Importing TestNG annotations for setting up and tearing down test environments.
// @BeforeMethod and @AfterMethod annotations define methods that are run before and after each test method.
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

// Declaring a class named Hooks.
// This class contains setup and teardown methods that are commonly used across multiple test cases.
public class Hooks {

    // Declaring a public WebDriver variable named 'driver'.
    // This WebDriver instance will be used to control the browser during the tests.
    public WebDriver driver;

    // Method annotated with @BeforeMethod, indicating that it will run before each test method.
    // This method sets up the WebDriver and initializes the browser.
    @BeforeMethod
    public void setUp() {

        // Using WebDriverManager to automatically download and set up the ChromeDriver binary.
        WebDriverManager.chromedriver().setup();

        // Initializing the WebDriver instance as a ChromeDriver, which launches a new Chrome browser window.

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-search-engine-choice-screen");

        driver = new ChromeDriver(options);


        // Maximizing the browser window to ensure that all elements are visible and accessible during the test.
        driver.manage().window().maximize();

        // Navigating to the specified URL in the browser. 
        // This is the starting point for the tests, loading the web application under test.
        driver.get("https://fasttrackit-test.netlify.app/#/");
    }

    // Method annotated with @AfterMethod, indicating that it will run after each test method.
    // This method tears down the WebDriver instance and closes the browser.
    @AfterMethod
    public void tearDown() {

        // Quitting the WebDriver session, which closes all browser windows and ends the WebDriver process.
        driver.quit();
    }
}
