// Importing WebDriver class from the Selenium library.
// WebDriver is used for interacting with the browser in automation testing.
import org.openqa.selenium.WebDriver;

// Importing PageFactory class from the Selenium library.
// PageFactory is a support class in Selenium for implementing the Page Object Model (POM) pattern.
// It initializes WebElements within a page class.
import org.openqa.selenium.support.PageFactory;

// Declaring an abstract class named BasePage.
// An abstract class is a class that cannot be instantiated and can have abstract methods (methods without a body).
// BasePage will serve as a parent class for other page classes.
public abstract class BasePage {

    // Declaring a protected WebDriver variable named 'driver'.
    // The 'protected' access modifier means that this variable is accessible within the same package
    // and by subclasses in other packages.
    protected WebDriver driver;

    // Constructor for the BasePage class that takes a WebDriver object as an argument.
    // When a new instance of a class that extends BasePage is created, this constructor will be called,
    // and the passed WebDriver object will be assigned to the 'driver' variable.
    public BasePage(WebDriver driver) {
        // Assigning the passed WebDriver object to the 'driver' variable.
        this.driver = driver;

        // Initializing the WebElements of the class that extends BasePage using PageFactory.
        // 'this' refers to the current class instance, so the PageFactory will initialize the WebElements
        // defined in the current page class (which extends BasePage).
        PageFactory.initElements(driver, this);
    }
}
