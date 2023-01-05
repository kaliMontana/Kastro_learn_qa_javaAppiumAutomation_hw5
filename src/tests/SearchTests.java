package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
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
	public void testAmountOfNotEmptySearch() {
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
	public void testCancelSearchEx3() {
		SearchPageObject searchPageObject = new SearchPageObject(driver);
		searchPageObject.initSearchInput();
		String search_line = "Encarta";
		searchPageObject.typeSearchLine(search_line);
		searchPageObject.getAmountOfFoundArticles();
		searchPageObject.waitForCancelButtonToAppear();
		searchPageObject.clickCancelSearch();
		searchPageObject.waitForArticlesDisappear();
	}
}
