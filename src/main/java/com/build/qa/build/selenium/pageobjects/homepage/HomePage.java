package com.build.qa.build.selenium.pageobjects.homepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;
import com.build.qa.build.selenium.utils.BathroomAccessories;

public class HomePage extends BasePage {

	private By buildThemeBody;

	public HomePage(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
		buildThemeBody = By.cssSelector("body.build-theme");
	}

	public boolean onBuildTheme() {
		return wait.until(ExpectedConditions.presenceOfElementLocated(buildThemeBody)) != null;
	}

	@FindBy(id = "search_txt")
	private WebElement searchTextBox;

	@FindBy(id = "heading")
	private WebElement productTitle;

	@FindBy(xpath = "//form[@id='site-search']/div/button")
	private WebElement searchButton;

	@FindBy(xpath = "//*[@id='email-subscribe-splash']/div/div/div[1]/button")
	private WebElement closeDialogBox;

	@FindBy(xpath = "//*[@id='header']/nav/div/ul/li[2]/a")
	private WebElement bathroomTab;

	@FindBy(xpath = "//*[@id='main']/div[2]/section[2]/ul")
	private WebElement bathroomAccessoryOptions;

	public void search(String searchKey) {

		searchTextBox.sendKeys(searchKey);
		searchButton.click();
	}

	public String getProductTitle() {
		return productTitle.getText();

	}

	public void closeWelcomeDialog() {

		try {
			closeDialogBox.click();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void clickBathroomTab() {
		bathroomTab.click();
	}

	public void clickBathroomAccessoryByName(BathroomAccessories bathroomAccessories) {
		List<WebElement> li = bathroomAccessoryOptions.findElements(By.tagName("li"));
		for (WebElement webElement : li) {
			String name = webElement.findElement(By.xpath("/div/div/a/p")).getText();
			if (name.equalsIgnoreCase(bathroomAccessories.getName())) {
				webElement.click();
				break;
			}
		}

	}
}
