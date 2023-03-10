package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
	protected AppiumDriver driver;

	public MainPageObject(AppiumDriver driver) {
		this.driver = driver;
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

	public void swipeUp(int timeOfSwipe) {
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int x = size.width / 2;
		int start_y = (int) (size.getHeight() * 0.8);
		int end_y = (int) (size.getHeight() * 0.2);

		action
				.press(x, start_y)
				.waitAction(timeOfSwipe)
				.moveTo(x, end_y)
				.release()
				.perform();
	}

	public void swipeQuick() {
		swipeUp(200);
	}

	public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
		int already_swiped = 0;
		while (driver.findElements(by).size() == 0) {

			if (already_swiped > max_swipes) {
				waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
				return;
			}

			swipeQuick();
			already_swiped++;
		}
	}

	public void swipeElementToLeft(By by, String error_message) {
		WebElement element = waitForElementPresent(
				by,
				error_message,
				10);

		int left_x = element.getLocation().getX();
		int right_x = left_x + element.getSize().getWidth();
		int upper_y = element.getLocation().getY();
		int lower_y = upper_y + element.getSize().getHeight();
		int middle_y = (upper_y + lower_y) / 2;

		TouchAction action = new TouchAction(driver);
		action
				.press(right_x, middle_y)
				.waitAction(500)
				.moveTo(left_x, middle_y)
				.release()
				.perform();
	}

	public int getAmountOfElements(By by) {
		List elements = driver.findElements(by);
		return elements.size();
	}

	public void assertElementNotPresent(By by, String error_message) {
		int amount_of_elements = getAmountOfElements(by);
		if (amount_of_elements > 0) {
			String default_message = "An element '" + by.toString() + "' suppose to be not present";
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	public void assertElementPresentsLessThanThree(By by, String error_message, int result_amount) {
		int amount_of_elements = getAmountOfElements(by);
		if (amount_of_elements >= result_amount) {
			String default_message = "Result elements less than three " + result_amount;
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		return element.getAttribute(attribute);
	}

	public void assertElementPresent(By by, String attribute, String error_message, long timeoutIntSeconds) {
		String attribute_value = waitForElementAndGetAttribute(
				by,
				attribute,
				error_message,
				timeoutIntSeconds
		);

		if (!attribute_value.contains("_title_")) {
			String default_message = "Element 'Title' by '" + by.toString() + "' suppose to be present. \n";
			throw new AssertionError(default_message + " " + error_message);
		}
	}
}
