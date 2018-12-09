package test;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
	final static Logger logger = Logger.getLogger(BaseTest.class);

	WebDriver driver;

	@BeforeClass
	public void initializeBaseTest() throws Exception {
		String URL = "https://dns-shop.ru";
		
		logger.info("Open Chrome Browser");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		followLink(URL);
	}

	public void followLink(String URL) throws Exception{
		driver.get(URL);
		logger.info("Go to " + URL);
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	@AfterClass
	public void quit() throws Exception {
		logger.info("Close browser");
		driver.quit();
	}
}
