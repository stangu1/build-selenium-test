package com.build.qa.build.selenium.pageobjects.homepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;
import com.build.qa.build.selenium.utils.Color;
import com.build.qa.build.selenium.utils.Email;
import com.build.qa.build.selenium.utils.Theme;

public class CategoryDrop extends BasePage {

	private By buildThemeBody;

	public CategoryDrop(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
		buildThemeBody = By.cssSelector("body.build-theme");
	}

	public boolean onBuildTheme() {
		return wait.until(ExpectedConditions.presenceOfElementLocated(buildThemeBody)) != null;
	}

	@FindBy(id = "category-product-drop")
	private WebElement productDrop;

	@FindBy(id = "configure-product-wrap")
	private WebElement addToCart;

	@FindBy(id = "yourName")
	private WebElement yourName;

	@FindBy(id = "yourEmail")
	private WebElement yourEmail;

	@FindBy(id = "recipientName")
	private WebElement recipientName;

	@FindBy(id = "recipientEmail")
	private WebElement recipientEmail;

	@FindBy(xpath = "//*[@id='recommended-options']/div[1]/div/div[3]/a/p")
	private WebElement productTitleInCart;

	@FindBy(xpath = "//*[@id='facet-options']/li[2]/ul")
	private WebElement productColorFilter;

	@FindBy(xpath = "//*[@id='facet-options']/li[4]/ul")
	private WebElement productThemeFilter;

	@FindBy(xpath = "//*[@id='category-content']/div[2]/div/div[1]/span/span")
	private WebElement productResultCount;

	@FindBy(xpath = "//*[@id='header']/section[2]/div/div/div/a[2]/button")
	private WebElement cartBtn;

	@FindBy(xpath = "//*[@id='page-content']/div[1]/div[1]/div/section[2]/div/div[1]/table/tbody/tr[3]/td/button[1]")
	private WebElement emailbtn;

	@FindBy(xpath = "//*[@id='cart-email']/div/div/div[2]/div[2]/form/div[4]/button")
	private WebElement emailcartBtn;

	public String addProductToCartByIndex(int index) {
		WebElement element = productDrop.findElements(By.tagName("li")).get(index - 1)
				.findElement(By.xpath("/div[2]/a/div[2]/span"));
		String productTitle = element.getText();
		element.click();
		addToCart.click();
		return productTitle;

	}

	public void openCart() {
		cartBtn.click();
	}

	public void email(Email email) {
		emailbtn.click();
		yourName.sendKeys(email.getYourName());
		yourEmail.sendKeys(email.getYourEmail());
		recipientName.sendKeys(email.getRecipientName());
		recipientEmail.sendKeys(email.getRecipientEmail());
		emailcartBtn.click();
	}

	public String getProductTitleInCart() {
		return productTitleInCart.getText();

	}

	public String filterProductByColor(Color color) {
		List<WebElement> li = productColorFilter.findElements(By.tagName("li"));

		String count = "";

		for (WebElement webElement : li) {
			String name = webElement.findElement(By.tagName("label")).getAttribute("data-facet-value");
			if (name.equalsIgnoreCase(color.getName())) {
				count = webElement.findElement(By.xpath("/label/span")).getText().replace("(", "").replace(")", "");
				webElement.click();
				break;
			}

		}
		return count;

	}

	public String filterProductByTheme(Theme theme) {
		List<WebElement> li = productThemeFilter.findElements(By.tagName("li"));

		String count = "";

		for (WebElement webElement : li) {
			String name = webElement.findElement(By.tagName("label")).getAttribute("data-facet-value");
			if (name.equalsIgnoreCase(theme.getName())) {
				count = webElement.findElement(By.xpath("/label/span")).getText().replace("(", "").replace(")", "");
				webElement.click();
				break;
			}

		}
		return count;
	}

	public String getProductResultCount() {
		return productResultCount.getText().replace(",", "");
	}

}
