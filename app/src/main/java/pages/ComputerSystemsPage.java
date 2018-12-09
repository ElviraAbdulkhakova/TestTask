package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ComputerSystemsPage {
	final static Logger logger = Logger.getLogger(ComputerSystemsPage.class);

	WebDriver driver;
	
	By systemUnitsPath = By.xpath("//div[@class='caption']/span[text()='Системные блоки']");

	public ComputerSystemsPage(WebDriver driver) {
		this.driver = driver;
	}

	public ProductListPage goToSystemUnits() throws Exception {
		WebElement systemUnitsElement = driver.findElement(systemUnitsPath);

		if (systemUnitsElement.isDisplayed() || systemUnitsElement.isEnabled()) {
			systemUnitsElement.click();
			logger.info("Click on link 'System units'");
			return new ProductListPage(driver);
		} else {
			logger.error("Element 'System units' does not exist");
			return null;
		}
	}
}
