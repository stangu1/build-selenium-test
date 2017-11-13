package com.build.qa.build.selenium.tests;

import org.junit.Assert;
import org.junit.Test;

import com.build.qa.build.selenium.framework.BaseFramework;
import com.build.qa.build.selenium.pageobjects.homepage.CategoryDrop;
import com.build.qa.build.selenium.pageobjects.homepage.HomePage;
import com.build.qa.build.selenium.utils.BathroomAccessories;
import com.build.qa.build.selenium.utils.Color;
import com.build.qa.build.selenium.utils.Email;
import com.build.qa.build.selenium.utils.Theme;

public class BuildTest extends BaseFramework {

	/**
	 * Extremely basic test that outlines some basic functionality and page
	 * objects as well as assertJ
	 */

	@Test
	public void navigateToHomePage() {

		driver.get(getConfiguration("HOMEPAGE"));

		HomePage homePage = new HomePage(driver, wait);
		homePage.closeWelcomeDialog();

		softly.assertThat(homePage.onBuildTheme()).as("The website should load up with the Build.com desktop theme.")
				.isTrue();
	}

	/**
	 * Search for the Quoizel MY1613 from the search bar
	 * 
	 * @assert: That the product page we land on is what is expected by checking
	 *          the product title
	 * @difficulty Easy
	 */

	@Test
	public void searchForProductLandsOnCorrectProduct() {

		driver.get(getConfiguration("HOMEPAGE"));

		HomePage homePage = new HomePage(driver, wait);
		homePage.closeWelcomeDialog();
		homePage.search("Quoizel MY1613");

		Assert.assertEquals("Quoizel MY1613", homePage.getProductTitle());

	}

	/**
	 * Go to the Bathroom Sinks category directly
	 * (https://www.build.com/bathroom-sinks/c108504) and add the second product
	 * on the search results (Category Drop) page to the cart.
	 * 
	 * @assert: the product that is added to the cart is what is expected
	 * @difficulty Easy-Medium
	 */
	@Test
	public void addProductToCartFromCategoryDrop() {

		driver.get(getConfiguration("HOMEPAGE") + "/bathroom-sinks/c108504");

		HomePage homePage = new HomePage(driver, wait);
		homePage.closeWelcomeDialog();

		CategoryDrop categoryDrop = new CategoryDrop(driver, wait);

		Assert.assertEquals(categoryDrop.addProductToCartByIndex(2), categoryDrop.getProductTitleInCart());
	}

	/**
	 * Add a product to the cart and email the cart to yourself, also to my
	 * email address: jgilmore+SeleniumTest@build.com Include this message in
	 * the "message field" of the email form: "This is {yourName}, sending you a
	 * cart from my automation!"
	 * 
	 * @assert that the "Cart Sent" success message is displayed after emailing
	 *         the cart
	 * @difficulty Medium-Hard
	 */
	@Test
	public void addProductToCartAndEmailIt() {

		driver.get(getConfiguration("HOMEPAGE") + "/bathroom-sinks/c108504");

		HomePage homePage = new HomePage(driver, wait);
		homePage.closeWelcomeDialog();

		CategoryDrop categoryDrop = new CategoryDrop(driver, wait);
		categoryDrop.addProductToCartByIndex(2);
		categoryDrop.openCart();

		Email email = new Email();
		email.setYourName(getConfiguration("yourName"));
		email.setYourEmail(getConfiguration("yourEmail"));
		email.setRecipientName(getConfiguration("recipientName"));
		email.setRecipientEmail(getConfiguration("recipientEmail"));
		email.setMessage(getConfiguration("message"));

		categoryDrop.email(email);

	}

	/**
	 * Go to a category drop page (such as Bathroom Faucets) and narrow by at
	 * least two filters (facets), e.g: Finish=Chromes and Theme=Modern
	 * 
	 * @assert that the correct filters are being narrowed, and the result count
	 *         is correct, such that each facet selection is narrowing the
	 *         product count.
	 * @difficulty Hard
	 */
	@Test
	public void facetNarrowBysResultInCorrectProductCounts() {

		driver.get(getConfiguration("HOMEPAGE"));

		HomePage homePage = new HomePage(driver, wait);
		homePage.closeWelcomeDialog();
		homePage.clickBathroomTab();
		homePage.clickBathroomAccessoryByName(BathroomAccessories.BathroomFaucets);

		CategoryDrop categoryDrop = new CategoryDrop(driver, wait);
		String count = categoryDrop.filterProductByColor(Color.Chromes);
		String totalCount = categoryDrop.getProductResultCount();

		Assert.assertEquals(count, totalCount);

		count = categoryDrop.filterProductByTheme(Theme.Modern);
		totalCount = categoryDrop.getProductResultCount();

		Assert.assertEquals(count, totalCount);

	}
}
