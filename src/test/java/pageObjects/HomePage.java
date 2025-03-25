package pageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage
{
	// Constructor
	public HomePage(WebDriver driver)		// Constructor
	{
		super(driver);
	}

	// Locators
	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement linkMyAccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement linkRegister;

	@FindBy(linkText = "Login")
	WebElement linkLogin;

	// Action Methods:

	public void clickMyAccount()
	{
		linkMyAccount.click();
	}

	public void clickRegister()
	{
		linkRegister.click();
	}

	public void clickLogin()
	{
		linkLogin.click();
	}
}