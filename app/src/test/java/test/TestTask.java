package test;

import static org.testng.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import content.SortingType;
import pages.HomePage;
import pages.ProductPage;
import pages.ProductListPage;

public class TestTask extends BaseTest {
	final static Logger logger = Logger.getLogger(TestTask.class);

	WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		driver = getDriver();
	}

	@Test
	public void test() throws Exception {
		HomePage page = new HomePage(driver);

		ProductListPage systemUnits = page.goToSystemUnits();
		assertTrue((systemUnits != null), "Can not go to the page");

		assertTrue(systemUnits.sortingByType(SortingType.DESCENDING_PRICE),
				"Can not sort");

		ProductPage firstProduct = systemUnits.chooseProductByNumber(3);
		assertTrue((firstProduct != null), "Can not go to product details page");

		LinkedHashMap<String, String> firstProductСharacteristics = firstProduct.getСharacteristics();
		assertTrue((firstProductСharacteristics != null), "Can not define product characteristics");

		assertTrue(page.goToHomePage(), "Can not go to main page");

		systemUnits = page.goToSystemUnits();
		assertTrue(systemUnits.sortingByType(SortingType.ASCENDING_PRICE),
				"Can not sort");

		assertTrue(systemUnits.goToEndOfList(), "Can not go to end of the list");

		ProductPage secondProduct = systemUnits.chooseProductByNumber(-3);
		assertTrue((secondProduct != null), "Can not go to product details page");

		LinkedHashMap<String, String> secondProductСharacteristics = secondProduct.getСharacteristics();
		assertTrue((secondProductСharacteristics != null), "Can not define product characteristics");

		assertTrue(firstProductСharacteristics.equals(secondProductСharacteristics),
				"Сharacteristics of products are different");
	}

}
