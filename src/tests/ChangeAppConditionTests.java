package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
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
