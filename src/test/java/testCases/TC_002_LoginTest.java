package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass
{
	@Test(groups = {"Sanity", "Master"})
	public void verify_login()
	{
		logger.info("******Starting TC002_LoginTest******");

		try
		{
			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// LoginPage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(myPropFile.getProperty("email"));	// from config.properties file. email is key.
			lp.setPassword(myPropFile.getProperty("password"));		// from config.properties file. password is key.
			lp.clickLogin();

			// MyAccountPage
			MyAccountPage myacc = new MyAccountPage(driver);
			boolean targetPage = myacc.isMyAccountPageExists();

			//	Assert.assertEquals(targetPage, true, "Login failed");
			Assert.assertTrue(targetPage);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("******Finished TC002_LoginTest******");
	}
}