import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.testng.Assert.assertEquals;


// Declaring the CheckoutPage class, which extends the BasePage class.
// By extending BasePage, CheckoutPage inherits the WebDriver instance and the PageFactory initialization.
public class LoginPage extends BasePage {

    // Declaring a public WebDriverWait variable named 'wait'.
    // WebDriverWait is used for implementing explicit waits during interactions with web elements.
    public WebDriverWait wait;

    public SoftAssert softAssert;

    // Constructor for the CheckoutPage class that takes a WebDriver object as an argument.
    // This constructor calls the parent class (BasePage) constructor to initialize the WebDriver instance
    // and sets up the PageFactory to initialize the web elements on this page.
    public LoginPage(WebDriver driver) {
        // Calling the parent class (BasePage) constructor using 'super' to initialize the WebDriver.
        super(driver);

        // Initializing the WebDriverWait object with a 10-second timeout.
        // This will be used to wait for certain conditions or elements during test execution.
        wait = new WebDriverWait(driver, 10);
        softAssert = new SoftAssert();
    }

    // Locating the search bar element using the @FindBy annotation.
    // @FindBy is a Selenium annotation that helps locate elements on the web page.
    // Here, the element is being located by its 'id' attribute with the value "input-search".
    // Declare the WebElement as private to enforce encapsulation
    // This ensures that 'searchBar' cannot be accessed directly from outside this class
    @FindBy(id = "input-search")
    private WebElement searchBar;

    // A public method to set a value in the search bar.
    // This method interacts with the searchBar element and sends the text "mouse" to it.
    // Public method to interact with the private 'searchBar' element
    // Provides controlled access to the encapsulated WebElement
    public void setSearchBar(String search) {
        // Typing the word "mouse" into the search bar.
        searchBar.sendKeys(search);
    }

    // Locating the search button element using the @FindBy annotation.
    // The element is being located by its CSS selector, which identifies elements based on their classes.
    // Here, the button has the classes "btn", "btn-light", and "btn-sm".


    @FindBy(css = ".svg-inline--fa.fa-sign-in-alt.fa-w-16 ")
    private WebElement loginIcon;

    public void clickLoginIcon() {
        loginIcon.click();
    }

    @FindBy(id = "user-name")
    private WebElement userNameField;

    public void setUserNameField(String name) {
        userNameField.sendKeys(name);
    }

    @FindBy(id = "password")
    private WebElement passwordField;

    public void setPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    @FindBy(css = ".btn.btn-primary")
    private WebElement loginBtn;

    public void clickLoginButton() {
        loginBtn.click();
    }

    @FindBy(linkText = "dino")
    private WebElement userLoggedIn;

    public WebElement getUserLoggedIn() {
        return userLoggedIn;
    }

    @FindBy(css = ".error")
    private WebElement errorMessage;

    public WebElement getErrorMessage() {
        return errorMessage;
    }

    @FindBy(css = ".error")
    private WebElement errorNullUsername;

    public WebElement getErrorNullUsername() {
        return errorNullUsername;
    }

    @FindBy(css = ".error")
    private WebElement errorNullPassword;

    public WebElement getErrorNullPassword() {
        return errorNullPassword;
    }

    @FindBy(css = ".sort-products-select.form-control.form-control-sm")
    private WebElement sortBar;

    public WebElement getSortBar() {
        return sortBar;
    }

    public void selectOption(WebElement element, String option) {
        Select optionSelect = new Select(element);
        optionSelect.selectByVisibleText(option);
    }

    public void loginDino() {
        clickLoginIcon();
        setUserNameField("dino");
        setPasswordField("choochoo");
        clickLoginButton();
    }

    @FindBy(css = ".svg-inline--fa.fa-undo.fa-w-16 ")
    private WebElement resetButton;

    public void clickResetButton() {
        resetButton.click();
        //

    }

    public WebElement getResetButton() {
        return resetButton;
    }

    @FindBy(css = ".card-link")
    private List<WebElement> productElements;

    public List<WebElement> getProductElements() {
        return productElements;
    }

    @FindBy(xpath = "//span[@style='font-weight: bold; font-size: 16px;']")
    private List<WebElement> productPrices;

    public List<WebElement> getProductPrices() {
        return productPrices;
    }

    @FindBy(css = ".svg-inline--fa.fa-sign-out-alt.fa-w-16 ")
    private WebElement signOutButton;

    public void clickSignOutButton() {
        signOutButton.click();
    }

    public void clickWhenReady(WebElement locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

}