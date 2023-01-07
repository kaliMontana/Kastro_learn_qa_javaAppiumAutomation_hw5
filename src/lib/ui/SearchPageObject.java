package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
	private static final String
			SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
			SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
			SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
			SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
			SEARCH_RESULT_BY_TITLE_SUBSTRING_AND_DESCRIPTION_SUBSTRING_TPL = "//*[@class='android.widget.LinearLayout']/*[@text='{TITLE_SUBSTRING}']/../*[@text='{DESCRIPTION_SUBSTRING}']",
			SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
			SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
			SEARCH_ARTICLE_CONTAINER = "org.wikipedia:id/search_results_container";


	public SearchPageObject(AppiumDriver driver) {
		super(driver);
	}

	/*TEMPLATE METHODS*/
	private static String getResultSearchElement(String substring) {
		return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
	}

	private static String getResultSearchElement(String title_substring, String description_substring) {
		return SEARCH_RESULT_BY_TITLE_SUBSTRING_AND_DESCRIPTION_SUBSTRING_TPL.replace("{TITLE_SUBSTRING}", title_substring).replace("{DESCRIPTION_SUBSTRING}", description_substring);
	}
	/*TEMPLATE METHODS*/

	public void initSearchInput() {
		this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input before clicking search init element", 5);
		this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
	}

	public void waitForCancelButtonToAppear() {
		this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find cancel button", 5);
	}

	public void waitForCancelButtonToDisappear() {
		this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button still present!", 5);
	}

	public void clickCancelSearch() {
		this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
	}

	public void typeSearchLine(String search_line) {
		this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
	}

	public void waitForSearchResult(String substring) {
		String search_result_xpath = getResultSearchElement(substring);
		this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring, 15);
	}

	public void waitForElementByTitleAndDescription(String title_substring, String description_substring) {
		String search_result_xpath = getResultSearchElement(title_substring, description_substring);
		this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + title_substring + " and " + description_substring, 15);
	}

	public void clickByArticleWithSubstring(String substring) {
		String search_result_xpath = getResultSearchElement(substring);
		this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring, 15);
	}

	public int getAmountOfFoundArticles() {
		this.waitForElementPresent(
				By.xpath(SEARCH_RESULT_ELEMENT),
				"Cannot find anythings by the request",
				15
		);
		return this.getAmountOfElements(
				By.xpath(SEARCH_RESULT_ELEMENT));
	}

	public void waitForEmptyResultsLabel() {
		this.waitForElementPresent(
				By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
				"Cannot find empty result element",
				15
		);
	}

	public void assertThereIsNotResultsOfSearch() {
		this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We suppose not to find any results.");
	}

	public void waitForArticlesDisappear() {
		this.waitForElementNotPresent(
				By.xpath(SEARCH_ARTICLE_CONTAINER),
				"Results are still present on the page",
				5
		);
	}

	public void assertThereAreMoreThanThreeArticles() {
		this.assertElementPresentsLessThanThree(By.xpath(SEARCH_RESULT_ELEMENT), "There are very few articles", 3);
	}
}
