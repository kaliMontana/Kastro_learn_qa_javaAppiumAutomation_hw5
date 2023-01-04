package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageObject {
	protected AppiumDriver driver;

	public MainPageObject(AppiumDriver driver) {
		this.driver = driver;
	}

	public WebElement waitForElementPresentByXpath(String xpath, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		By by = By.xpath(xpath);
		return wait.until(
				ExpectedConditions.presenceOfElementLocated(by)
		);
	}

	public WebElement waitForElementByXpathAndClick(String xpath, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresentByXpath(xpath, errorMessage, timeoutInSeconds);
		element.click();
		return element;
	}

	public WebElement waitForElementByXpathAndSendKeys(String xpath, String value, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresentByXpath(xpath, errorMessage, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.presenceOfElementLocated(by)
		);
	}

	public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
		element.click();
		return element;
	}

	public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.invisibilityOfElementLocated(by)
		);
	}

	public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
		element.clear();
		return element;
	}

}
