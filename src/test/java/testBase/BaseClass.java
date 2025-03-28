package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	//public static WebDriver driver; 	//for capture screenshot make it static other wise remove static
	public WebDriver driver;
	public Logger logger; // For Log4j
	public Properties myPropFile;

	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException
	{
		// Loading config.properties file:
		FileReader file = new FileReader(".//src/test/resources/config.properties");
		myPropFile = new Properties();
		myPropFile.load(file);


		logger = LogManager.getLogger(this.getClass());

		// For Remote Execution:
		if(myPropFile.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WINDOWS);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching OS");
				return;
			}

			// Browser

			switch (br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome");				
			break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge");				
			break;
			case "firefox":capabilities.setBrowserName("firefox");				
			break;

			default: System.out.println("No matching browser");
			return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}

		// For Local Execution:
		if(myPropFile.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch (br.toLowerCase())
			{
			case "chrome": driver = new ChromeDriver();
			break;
			case "edge": driver = new EdgeDriver();
			break;

			case "firefox": driver = new FirefoxDriver();
			break;

			default: System.out.println("Invalid browser name..");
			return;
			}
		}

		driver.manage().deleteAllCookies(); // Delete all the cookies
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(myPropFile.getProperty("appURL"));	// Reading URL from the config.properties file
		// driver.get("https://tutorialsninja.com/demo/");		// hard coded URL
		driver.manage().window().maximize();
	}

	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.quit();
	}

	// For random String
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}

	// For random Numbers
	public String randomNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	// For random Alpha-Numeric
	public String randomAlphaNumeric()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString + "@" + generatedNumber);
	}

	public String captureScreen(String tname) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}