package pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import content.ProductСharacteristic;

public class ProductPage {
	final static Logger logger = Logger.getLogger(ProductPage.class);

	WebDriver driver;

	By allСharacteristics = By.linkText("Все характеристики");
	By productName = By.xpath("//h1[@data-product-param='name']");
	By productPrice = By.xpath("//span[@class='current-price-value']");
	By productGuarantee = By.xpath("//div[@class='block']//p[@class='item']");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public LinkedHashMap<String, String> getСharacteristics() throws Exception {
		LinkedHashMap<String, String> characteristics = new LinkedHashMap<String, String>();

		String guarantee;

		WebElement productNameEl;
		WebElement productPriceEl;
		WebElement productGuaranteeEl;
		WebElement allСharacteristicsEl;

		List<ProductСharacteristic> additionalСharacteristic = new ArrayList<ProductСharacteristic>();
		additionalСharacteristic.add(new ProductСharacteristic("Операционная система"));
		additionalСharacteristic.add(new ProductСharacteristic("Модель процессора"));
		additionalСharacteristic.add(new ProductСharacteristic("Количество ядер процессора"));
		additionalСharacteristic.add(new ProductСharacteristic("Частота процессора"));
		additionalСharacteristic.add(new ProductСharacteristic("Модель дискретной видеокарты", false));
		additionalСharacteristic.add(new ProductСharacteristic("Объем видеопамяти", false));
		additionalСharacteristic.add(new ProductСharacteristic("Тип оперативной памяти"));
		additionalСharacteristic.add(new ProductСharacteristic("Размер оперативной памяти"));
		additionalСharacteristic.add(new ProductСharacteristic("Суммарный объем жестких дисков (HDD)", false));
		additionalСharacteristic.add(new ProductСharacteristic("Объем твердотельного накопителя (SSD)", false));

		productNameEl = driver.findElement(productName);
		if (productNameEl.isDisplayed() || productNameEl.isEnabled()) {
			characteristics.put("Название", productNameEl.getText());
		} else {
			logger.error("Element 'Name' does not exist");
			return null;
		}

		productPriceEl = driver.findElement(productPrice);
		if (productPriceEl.isDisplayed() || productPriceEl.isEnabled()) {
			characteristics.put("Цена", productPriceEl.getText());
		} else {
			logger.error("Element 'Price' does not exist");
			return null;
		}

		productGuaranteeEl = driver.findElement(productGuarantee);
		if (productGuaranteeEl.isDisplayed() || productGuaranteeEl.isEnabled()) {
			guarantee = productGuaranteeEl.getText().split("Гарантия:")[1];
			characteristics.put("Срок гарантии", guarantee);
		} else {
			logger.error("Element 'Guarantee period' does not exist");
			return null;
		}

		allСharacteristicsEl = driver.findElement(allСharacteristics);
		if (allСharacteristicsEl.isDisplayed() || allСharacteristicsEl.isEnabled()) {
			logger.info("Click on 'all characteristics'");
			allСharacteristicsEl.click();
		} else {
			logger.error("Element 'all characteristics' does not exist");
			return null;
		}

		for (ProductСharacteristic currentСharacteristic : additionalСharacteristic) {
			currentСharacteristic = getCharacteristicValue(currentСharacteristic);
			if (currentСharacteristic.getValue() == null) {
				logger.error("Element " + currentСharacteristic.getName() + " does not exist");
				return null;
			} else if (!currentСharacteristic.getValue().equals("Missing optional element")) {
				characteristics.put(currentСharacteristic.getName(), currentСharacteristic.getValue());
			}
		}

		logger.info("---------------------------------------------------------------------");
		logger.info("Characteristics of product:");
		for (Map.Entry<String, String> characteristic : characteristics.entrySet()) {
			logger.info(characteristic.getKey() + ":" + characteristic.getValue());
		}
		logger.info("---------------------------------------------------------------------");

		return characteristics;
	}

	ProductСharacteristic getCharacteristicValue(ProductСharacteristic characteristic) throws Exception {
		WebElement characteristicEl = driver.findElement(
				By.xpath("//div[@id='characteristics']//tbody//tr[contains(.,'" + characteristic.getName() + "')]"));
		if (characteristicEl.isDisplayed() || characteristicEl.isEnabled()) {
			String temp[] = characteristicEl.getText().substring(characteristic.getName().length()).split("\n");

			characteristic.setValue(temp[1]);
		} else if (characteristic.isRequired()) {
			characteristic.setValue(null);
		} else {
			characteristic.setValue("Missing optional element");
		}
		return characteristic;
	}
}
