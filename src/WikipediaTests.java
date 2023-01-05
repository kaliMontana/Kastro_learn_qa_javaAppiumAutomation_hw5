import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class WikipediaTests extends CoreTestCase {
	private MainPageObject mainPageObject;


	protected void setUp() throws Exception {
		super.setUp();

		mainPageObject = new MainPageObject(driver);
	}

	@Test
	public void testSearch() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.waitForSearchResult("Object-oriented programming language");
	}

	@Test
	public void testCancelSearch() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.waitForCancelButtonToAppear();
		searchPageObject.clickCancelSearch();
		searchPageObject.waitForCancelButtonToDisappear();
	}

	@Test
	public void testCompareArticleTitle() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		String article_title = articlePageObject.getArticleTitle();

		Assert.assertEquals(
				"We see unexpected title",
				"Java (programming language)",
				article_title
		);
	}

	@Test
	public void testSwipeToArticleFooterTest() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Appium");
		searchPageObject.clickByArticleWithSubstring("Appium");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		articlePageObject.waitForTitleElement();
		articlePageObject.swipeToFooter();
	}

	@Test
	public void testSaveFirstArticleToMyList() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		articlePageObject.waitForTitleElement();
		String article_title = articlePageObject.getArticleTitle();
		String name_of_folder = "Learning programming";
		articlePageObject.addArticleToMyList(name_of_folder);
		articlePageObject.closeArticle();

		NavigationUI navigationUI = new NavigationUI(driver);
		navigationUI.clickMyList();

		MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
		myListsPageObject.openFolderByName(name_of_folder);
		myListsPageObject.swipeArticleToDelete(article_title);
	}

	@Test
	public void testAmountOfNotEmptySearchTest() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		String search_line = "Linking park discography";
		searchPageObject.typeSearchLine(search_line);
		int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

		Assert.assertTrue("We found too few results!",
				amount_of_search_results > 0
		);
	}

	@Test
	public void testAmountOfEmptySearch() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		String search_line = "dsgffgdfgfd";
		searchPageObject.typeSearchLine(search_line);
		searchPageObject.waitForEmptyResultsLabel();
		searchPageObject.assertThereIsNotResultsOfSearch();

	}

	@Test
	public void sendKeysTest() throws InterruptedException {
		WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
		element_to_init_search.click();

		WebElement element_to_enter_search_line = driver.findElementByXPath("//*[contains(@text, 'Search…')]");
		element_to_enter_search_line.sendKeys("Appium");
		sleep(6000);
	}

	@Test
	public void sendKeysWithWaitTest() {
		WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
		element_to_init_search.click();

		WebElement element_to_enter_search_line = mainPageObject.waitForElementPresentByXpath(
				"//*[contains(@text, 'Search…')]",
				"Cannot find seaerch input",
				5
		);

		element_to_enter_search_line.sendKeys("Appium");
	}

	@Test
	public void sendKeysWithWaitAndOverloadingMethodsTest() {
		WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
		element_to_init_search.click();

		WebElement element_to_enter_search_line = waitForElementPresentByXpath(
				"//*[contains(@text, 'Search…')]",
				"Cannot find search input"
		);

		element_to_enter_search_line.sendKeys("Appium");
	}


	private WebElement waitForElementPresentByXpath(String xpath, String errorMessage) {
		return mainPageObject.waitForElementPresentByXpath(xpath, errorMessage, 5);
	}

	@Test
	public void sendKeysWithWaitAndXpathConcatTest() {
		WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
		element_to_init_search.click();

		WebElement element_to_enter_search_line = waitForElementPresentByXpath(
				"//*[contains(@text, 'Search…')]",
				"Cannot find search input"
		);

		element_to_enter_search_line.sendKeys("Java");
		mainPageObject.waitForElementPresentByXpath(
				"//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
				"Cannot find 'Object-oriented programming language' topic searching by 'Java'",
				15
		);
	}

	@Test
	public void cancelSearchTest() {
		waitForElementByIdAndClick(
				"org.wikipedia:id/search_container",
				"Cannot find 'search wikipedia' input",
				5
		);

		waitForElementByIdAndClick(
				"org.wikipedia:id/search_close_btn",
				"Cannot find X to cancel search input",
				5
		);

		waitForElementNotPresent(
				"org.wikipedia:id/search_close_btn",
				"X is still present on the page",
				5
		);
	}

	private WebElement waitElementWaitForElementPresentById(String id, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		By by = By.id(id);
		return wait.until(
				ExpectedConditions.presenceOfElementLocated(by)
		);
	}

	private WebElement waitForElementByIdAndClick(String id, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitElementWaitForElementPresentById(id, errorMessage, timeoutInSeconds);
		element.click();
		return element;
	}

	private boolean waitForElementNotPresent(String id, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		By by = By.id(id);
		return wait.until(
				ExpectedConditions.invisibilityOfElementLocated(by)
		);
	}

	@Test
	public void generalTestSendKeysWithMethodsClickAndSendKeysTest() {
		mainPageObject.waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find search wikipedia input",
				5
		);

		mainPageObject.waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				"Java",
				"Cannot find search input",
				5
		);

		mainPageObject.waitForElementPresent(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
				"Cannot find 'Object-oriented programming language' topic searching by 'Java'",
				15
		);
	}

	@Test
	public void generalTestCancelSearchTest() {
		mainPageObject.waitForElementAndClick(
				By.id("org.wikipedia:id/search_container"),
				"Cannot find 'search wikipedia' input",
				5
		);

		mainPageObject.waitForElementAndClick(
				By.id("org.wikipedia:id/search_close_btn"),
				"Cannot find X to cancel search input",
				5
		);

		mainPageObject.waitForElementNotPresent(
				By.id("org.wikipedia:id/search_close_btn"),
				"X is still present on the page",
				5
		);
	}

}
