package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import content.SortingType;
import utils.KeyboardHelper;

public class ProductListPage {
	final static Logger logger = Logger.getLogger(ProductListPage.class);

	WebDriver driver;

	KeyboardHelper keyboardHelper;

	By sortMenu = By.xpath("//button[@class='dropdown' and contains(.,'Сортировать:')]");
	By product;
	By nearestShops = By.xpath("//i[@data-role='close-near-shops-container']");
	By endOfList = By.xpath("//span[@class=' item edge' and contains(.,'В конец')]");
	By currentPage = By.xpath("//span[@class='item active']");

	WebElement sortMenuBtn;

	public ProductListPage(WebDriver driver) {
		this.driver = driver;
		this.keyboardHelper = new KeyboardHelper();
	}

	public boolean sortingByType(SortingType type) throws Exception {
		sortMenuBtn = driver.findElement(sortMenu);

		if (sortMenuBtn.isDisplayed() || sortMenuBtn.isEnabled()) {
			SortingType currentSortType = getCurrentSortType(sortMenuBtn.getText());
			if (currentSortType == null) {
				logger.error("Can not identify current sorting type");
				return false;
			}

			if (currentSortType.equals(type)) {
				logger.info("Required string type is selected");
				return true;
			} else {
				sortMenuBtn.click();
				logger.info("Open sort menu");
				WebElement sortTypeEl = driver.findElement(By.linkText(type.getValue()));
				if (sortTypeEl.isDisplayed() || sortTypeEl.isEnabled()) {
					sortTypeEl.click();
					logger.info("Select sort: " + type.getValue());
					return true;
				} else {
					logger.error("Element '" + currentSortType.getValue() + "' does not exist");
					return false;
				}
			}
		} else {
			logger.error("Element 'Sort menu' does not exist");
			return false;
		}
	}

	public SortingType getCurrentSortType(String value) throws Exception {
		value = value.split("Сортировать:")[1];
		for (SortingType type : SortingType.values()) {
			if (value.contains(type.getValue().toLowerCase())) {
				return type;
			}
		}
		return null;
	}

	public ProductPage chooseProductByNumber(int reqNumber) throws Exception {
		int i = reqNumber;
		int count = getCountProducts();
		if (count < i) {
			logger.error("Current quantity of elements on the page is less than requested quantity");
			return null;
		}
		if (i < 0) {
			i = count + i + 1;
			if (i < 0) {
				WebElement currentPageEl = driver.findElement(currentPage);
				int number = Integer.valueOf(currentPageEl.getText());
				number--;
				WebElement prevPage = driver.findElement(By.xpath("//span[@data-page-number='" + number + "']"));
				if (prevPage.isDisplayed() || prevPage.isEnabled()) {
					prevPage.click();
					Thread.sleep(2000);
					i = getCountProducts() + reqNumber + count + 1;
				} else {
					logger.error("Page with requested element is not found");
					return null;
				}
			}
		}

		WebElement nearestShopsEl = driver.findElement(nearestShops);
		if (nearestShopsEl.isDisplayed() || nearestShopsEl.isEnabled()) {
			logger.info("Close popup window 'nearest shops'");
			nearestShopsEl.click();
			Thread.sleep(1000);

			driver.manage().deleteCookieNamed("selected-near-shop");
		}

		product = By.xpath(
				"//div[@data-position-index='" + i + "']//div[@class='title']//a[@data-role='product-cart-link']");
		WebElement productEl = driver.findElement(product);
		if (productEl.isDisplayed() || productEl.isEnabled()) {
			productEl.click();
			logger.info("Go to product page");
			return new ProductPage(driver);
		} else
			return null;
	}

	public boolean goToEndOfList() throws Exception {
		keyboardHelper.pressPageDown();
		Thread.sleep(2000);

		WebElement endOfListEl = driver.findElement(endOfList);
		if (endOfListEl.isDisplayed() || endOfListEl.isEnabled()) {
			endOfListEl.click();
			logger.info("Click on 'End of list' element");
			Thread.sleep(1000);
			return true;
		} else {
			logger.error("Element 'End of list' does not exist");
			return false;
		}
	}

	int getCountProducts() throws Exception {
		return driver.findElements(By.xpath("//div[@data-id='catalog-item']")).size();
	}

}
