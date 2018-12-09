package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pages.ComputersAndPeripheralsPage;

public class HomePage {
	final static Logger logger = Logger.getLogger(HomePage.class);

	WebDriver driver;
	
	By computersAndPeripheralsLink = By.linkText("Компьютеры и периферия");
	By homePage = By.xpath("//nav[@id='header-search']//a[@class='logo']");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	ComputersAndPeripheralsPage goToComputersAndPeripherals() throws Exception {
		WebElement computersAndPeripheralsLinkElement = driver.findElement(computersAndPeripheralsLink);

		if (computersAndPeripheralsLinkElement.isDisplayed() || computersAndPeripheralsLinkElement.isEnabled()) {
			computersAndPeripheralsLinkElement.click();
			logger.info("Click on link 'Computers and peripherals'");
			return new ComputersAndPeripheralsPage(driver);
		} else {
			logger.error("Element 'Computers and peripherals' does not exist");
			return null;
		}
	}

	public boolean goToHomePage() throws Exception {
		WebElement homePageEl = driver.findElement(homePage);

		if (homePageEl.isDisplayed() || homePageEl.isEnabled()) {
			homePageEl.click();
			logger.info("Clink on link 'Back to the main page'");
			return true;
		} else {
			logger.error("Element 'Back to the main page' does not exist");
			return false;
		}
	}

	public ProductListPage goToSystemUnits() throws Exception {
		ComputersAndPeripheralsPage computersAndPeripherals = goToComputersAndPeripherals();
		if (computersAndPeripherals == null) {
			return null;
		}

		ComputerSystemsPage computerSystems = computersAndPeripherals.goToComputerSystems();
		if (computerSystems == null) {
			return null;
		}

		ProductListPage systemUnits = computerSystems.goToSystemUnits();
		if (systemUnits == null) {
			return null;
		}

		return systemUnits;
	}
}
