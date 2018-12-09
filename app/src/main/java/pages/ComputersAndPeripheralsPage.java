package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ComputersAndPeripheralsPage {
	final static Logger logger = Logger.getLogger(ComputersAndPeripheralsPage.class);

	WebDriver driver;
	
	By computerSystemsPath = By.xpath("//div[@class='caption']/span[text()='Компьютерные системы']");

	public ComputersAndPeripheralsPage(WebDriver driver) {
		this.driver = driver;
	}

	public ComputerSystemsPage goToComputerSystems() throws Exception {
		WebElement computerSystemsLinkElement = driver.findElement(computerSystemsPath);

		if (computerSystemsLinkElement.isDisplayed() || computerSystemsLinkElement.isEnabled()) {
			computerSystemsLinkElement.click();
			logger.info("Click on link 'Computer systems'");
			return new ComputerSystemsPage(driver);
		} else {
			logger.error("Element 'Computer systems' does not exist");
			return null;
		}
	}

}
