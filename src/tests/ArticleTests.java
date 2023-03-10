package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
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
	public void testSwipeArticle() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		searchPageObject.typeSearchLine("Appium");
		searchPageObject.clickByArticleWithSubstring("Appium");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		articlePageObject.waitForTitleElement();
		articlePageObject.swipeToFooter();
	}

	@Test
	public void testCheckElementPresent() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		String search_line = "Java";
		searchPageObject.typeSearchLine(search_line);
		searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

		ArticlePageObject articlePageObject = new ArticlePageObject(driver);
		articlePageObject.assertThereIsElementTitle();
	}
}
