import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class WikipediaTests extends CoreTestCase {

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

		assertEquals(
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

		assertTrue("We found too few results!",
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
	public void testChangeScreenOrientationOnSearchResults() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		String title_before_Rotation = articlePageObject.getArticleTitle();
		this.rotateScreenLandscape();
		String title_after_Rotation = articlePageObject.getArticleTitle();

		assertEquals(
				"Article title have been changed after screen rotation",
				title_before_Rotation,
				title_after_Rotation
		);

		this.rotateScreenPortrait();
		String title_after_second_Rotation = articlePageObject.getArticleTitle();

		assertEquals(
				"Article title have been changed after screen rotation",
				title_before_Rotation,
				title_after_second_Rotation
		);
	}

	@Test
	public void testCheckSearchArticleInBackground() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);

		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Java");
		searchPageObject.waitForSearchResult("Object-oriented programming language");
		this.backGroundApp(3);
		searchPageObject.waitForSearchResult("Object-oriented programming language");
	}
}
