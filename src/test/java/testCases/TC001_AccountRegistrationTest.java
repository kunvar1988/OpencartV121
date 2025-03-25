package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
	@Test(groups = {"Regression", "Master"})
	public void verify_account_registration()
	{

		logger.info("******Starting TC001_AccountRegistrationTest******");

		try
		{
			HomePage hp = new HomePage(driver);

			hp.clickMyAccount();
			logger.info("Clicked on MyAccount Link");
			hp.clickRegister();
			logger.info("Clicked on Register Link");

			logger.info("Providing Customer details...");
			AccountRegistrationPage regPage = new AccountRegistrationPage(driver);

			regPage.setFirstName(randomString().toUpperCase());
			regPage.setLastName(randomString().toUpperCase());
			regPage.setEmail(randomString() + "@gmail.com");	// randomly generated the String
			regPage.setTelephone(randomNumber());

			String password = randomAlphaNumeric();

			regPage.setPassword(password);
			regPage.setConfirmPassword(password);

			regPage.setPrivacyPolicy();
			regPage.clickContinue();

			logger.info("Validating expected message...");
			String confMsg = regPage.getConfirmationMsg();

			if(confMsg.equals("Your Account Has Been Created!"))	// Correct statement, no error.
			//if(confMsg.equals("Your Account Has Been Created!!"))	// Intentionally put an failure here. inCorrect statement, with one error.
			{
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("Test failed...");
				logger.debug("Debug logs...");
				Assert.assertTrue(false);
			}
		}
		catch(Exception e)
		{

			Assert.fail();
		}

		logger.info("******Finished TC001_AccountRegistrationTest******");
	}
}