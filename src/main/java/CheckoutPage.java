import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import static java.lang.Double.parseDouble;
import static org.testng.Assert.assertEquals;


// Declaring the CheckoutPage class, which extends the BasePage class.
// By extending BasePage, CheckoutPage inherits the WebDriver instance and the PageFactory initialization.
public class CheckoutPage extends BasePage {

    // Declaring a public WebDriverWait variable named 'wait'.
    // WebDriverWait is used for implementing explicit waits during interactions with web elements.
    public WebDriverWait wait;

    public SoftAssert softAssert;

    // Constructor for the CheckoutPage class that takes a WebDriver object as an argument.
    // This constructor calls the parent class (BasePage) constructor to initialize the WebDriver instance
    // and sets up the PageFactory to initialize the web elements on this page.
    public CheckoutPage(WebDriver driver) {
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

    @FindBy(linkText = "Awesome Granite Chips")
    private WebElement awesomeChipsProduct;

    public void clickAwesomeChipsLink() {
        awesomeChipsProduct.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-cart-plus.fa-w-18.fa-3x ")
    private WebElement cartIcon;

    public void clickCartIcon() {
        cartIcon.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-shopping-cart.fa-w-18 ")
    private WebElement shoppingCartIcon;

    public void clickShoppingCartIcon() {
        shoppingCartIcon.click();
    }

    public WebElement getShoppingCartIcon() {
        return shoppingCartIcon;
    }

    @FindBy(css = ".btn.btn-success")
    private WebElement checkoutButton;

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    public void setFirstNameField(String first) {
        firstNameField.sendKeys(first);
    }

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    public void setLastNameField(String last) {
        lastNameField.sendKeys(last);
    }

    @FindBy(id = "address")
    private WebElement addressField;

    public void setAddressField(String address) {
        addressField.sendKeys(address);
    }

    @FindBy(css = ".btn.btn-success")
    private WebElement continueCheckoutButton;

    public void clickContinueCheckoutButton() {
        continueCheckoutButton.click();
    }

    @FindBy(css = ".btn.btn-success")
    private WebElement completeYourOrderButton;

    public void clickCompleteYourOrderButton() {
        completeYourOrderButton.click();
    }

    @FindBy(css = ".text-muted")
    private WebElement successMessage;

    public WebElement getSuccessMessage() {
        return successMessage;
    }

    @FindBy(css = ".svg-inline--fa.fa-sign-in-alt.fa-w-16 ")
    private WebElement loginButton;

    public void clickLoginButton() {
        loginButton.click();
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

    public void clickLoginBtn() {
        loginBtn.click();
    }

    @FindBy(linkText = "dino")
    private WebElement userLoggedIn;

    public WebElement getUserLoggedIn() {
        return userLoggedIn;
    }


    @FindBy(css = ".svg-inline--fa.fa-heart.fa-w-16 ")
    private WebElement wishlistButton;

    public void clickWishlistButton() {
        wishlistButton.click();
    }

    @FindBy(css = ".row.row-cols-xl-4.row-cols-lg-3.row-cols-md-2.row-cols-sm-2.row-cols-1")
    private WebElement SuccessValidation;

    public WebElement getSuccessValidation() {
        return SuccessValidation;
    }

    @FindBy(linkText = "Incredible Concrete Hat")
    private WebElement incredibleConcreteProduct;

    public void clickIncredibleConcreteLink() {
        incredibleConcreteProduct.click();
    }

    public WebElement getIncredibleConcreteProduct() {
        return incredibleConcreteProduct;
    }

    @FindBy(css = ".text-muted")
    private WebElement validationCart;

    public WebElement getValidationCart() {
        return validationCart;
    }

    @FindBy(css = ".svg-inline--fa.fa-trash.fa-w-14 ")
    private WebElement trashElement;

    public void clickTrashElement() {
        trashElement.click();
    }

    @FindBy(css = ".text-center.container")
    private WebElement validationDeleting;

    public WebElement getValidationDeleting() {
        return validationDeleting;
    }

    @FindBy(css = ".error")
    private WebElement errorMessage;

    public WebElement getErrorMessage() {
        return errorMessage;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    @FindBy(linkText = "Awesome Soft Shirt")
    private WebElement awesomeShirt;

    public void clickAwesomeShirt() {
        awesomeShirt.click();
    }

    public WebElement getAwesomeShirt() {
        return awesomeShirt;
    }

    @FindBy(css = ".svg-inline--fa.fa-cart-plus.fa-w-18.fa-3x ")
    private WebElement add2Cart;

    public void clickAdd2Cart() {
        add2Cart.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-shopping-cart.fa-w-18 ")
    private WebElement cartBtn;

    public void clickCartBtn() {
        cartBtn.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-plus-circle.fa-w-16 ")
    private WebElement plusOne;

    public void clickPlusOne() {
        plusOne.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-shopping-bag.fa-w-14.fa-3x.brand-logo")
    private WebElement homepageButton;

    public void clickHomepageButton() {
        homepageButton.click();
    }

    @FindBy(css = ".text-center")
    private WebElement orderConfirmation;

    public WebElement getOrderConfirmation() {
        return orderConfirmation;
    }

    @FindBy(css = ".btn.btn-danger")
    private WebElement continueShoppingButton;

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
    }

    @FindBy(css = ".btn.btn-danger")
    private WebElement cancelButton;

    public void clickCancelButton() {
        cancelButton.click();
    }

    @FindBy(css = ".text-muted")
    private WebElement cartConfirmation;

    public WebElement getCartConfirmation() {
        return cartConfirmation;
    }

    @FindBy(css = ".svg-inline--fa.fa-heart.fa-w-16.fa-3x ")
    private WebElement heartIcon;

    public void clickHeartIcon() {
        heartIcon.click();
    }

    @FindBy(css = ".fa-layers-counter.shopping_cart_badge")
    private WebElement shoppingCartBadge;

    public WebElement getShoppingCartBadge() {
        return shoppingCartBadge;
    }

    public void clickShoppingCartBadge() {
        shoppingCartBadge.click();
    }

    public WebElement getAwesomeChipsProduct() {
        return awesomeChipsProduct;
    }

    @FindBy(css = ".svg-inline--fa.fa-heart-broken.fa-w-16.fa-2x ")
    private WebElement brokenHeartIcon;

    public void clickBrokenHeartIcon() {
        brokenHeartIcon.click();
    }

    @FindBy(css = ".text-muted")
    private WebElement productsConfirmation;

    public WebElement getProductsConfirmation() {
        return productsConfirmation;
    }

    @FindBy(css = ".svg-inline--fa.fa-undo.fa-w-16 ")
    private WebElement resetButton;

    public void clickResetButton() {
        resetButton.click();
    }

    @FindBy(css = ".text-muted")
    private WebElement checkoutPage;

    public WebElement getCheckoutPage() {
        return checkoutPage;
    }

    @FindBy(css = ".text-muted")
    private WebElement orderSummary;

    public WebElement getOrderSummary() {
        return orderSummary;
    }

    public void addProductToWishlist() {
        clickAwesomeChipsLink();
        clickHeartIcon();

    }

    public void addProductToCart() {
        clickAwesomeShirt();
        clickAdd2Cart();
        clickCartBtn();
    }

    public void addAwesomeChipsToCart() {
        clickAwesomeChipsLink();
        clickCartIcon();
        clickShoppingCartIcon();
    }

    @FindBy(xpath = "(//td[@class='amount'])[1]")
    private WebElement itemPrice;

    public double productPrice() {
        String amountValue = itemPrice.getText();
        String cleanAmountValue = amountValue.replace("$", "");
        return Double.parseDouble(cleanAmountValue);
    }

    @FindBy(xpath = "(//td[@class='amount'])[2]")
    private WebElement taxPrice;

    public double taxPrice() {
        String taxValue = taxPrice.getText();
        String cleanTaxValue = taxValue.replace("$", "");
        return Double.parseDouble(cleanTaxValue);
    }

    @FindBy(xpath = "(//td[@class='amount'])[3]")
    private WebElement totalPrice;

    public double totalPrice() {
        String totalValue = totalPrice.getText();
        String cleanTotalValue = totalValue.replace("$", "");
        return Double.parseDouble(cleanTotalValue);
    }

    @FindBy(xpath = "(//div[text()='15.99'])[1]")
    public WebElement productPriceChips;


    public double productPriceChips() {
        String priceChipsValue = productPriceChips.getText();
        String cleanPriceChipsValue = priceChipsValue.replace("$", "");
        return Double.parseDouble(cleanPriceChipsValue);
    }

    @FindBy(xpath = "(//div[text()='29.99'])[1]")
    private WebElement productPriceShirt;

    @FindBy(css = ".error")
    private WebElement validationFirstField;

    public WebElement getValidationFirstField() {
        return validationFirstField;
    }

    @FindBy(css = ".error")
    private WebElement validationLastField;

    public WebElement getValidationLastField() {
        return validationLastField;
    }

    @FindBy(css = ".error")
    private WebElement validationAddressField;

    public WebElement getValidationAddressField() {
        return validationAddressField;
    }

    public double productPriceShirt() {
        String priceShirtValue = productPriceShirt.getText();
        String cleanShirtValue = priceShirtValue.replace("$", "");
        return Double.parseDouble(cleanShirtValue);
    }

    public void addDeliveryDetailsToOrder() {
        setFirstNameField("Ioan");
        setLastNameField("Amariei");
        setAddressField("Acasa la Floresti");
    }

    public void loginDino() {
        clickLoginButton();
        setUserNameField("dino");
        setPasswordField("choochoo");
        clickLoginBtn();
        assertEquals(getUserLoggedIn().getText(), "dino");
        ExtentTestNGITestListener.getTest().log(Status.PASS, "The user Dino is logged in.");
    }

}