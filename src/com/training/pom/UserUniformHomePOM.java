package com.training.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserUniformHomePOM {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	private WebElement wProduct;

	public UserUniformHomePOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='cart']/button")
	WebElement cartHome;

	@FindBy(xpath = "//div[@id='cart']//strong[contains(text(),'Checkout')]")
	WebElement checkout;

	@FindBy(xpath = "//a[@title='My Account']//span[contains(@class,'hidden')]")
	WebElement wUserName;

	public void viewProduct(String sProductName) {
		wProduct = driver.findElement(By.xpath("//div[@id='featured-grid']//a/img[@title='" + sProductName + "']"));
		js.executeScript("arguments[0].scrollIntoView(true);", wProduct);
	}

	public void clickonProduct() {
		wait.until(ExpectedConditions.elementToBeClickable(wProduct));
		wProduct.click();
	}

	public void clickonCart() {
		wait.until(ExpectedConditions.elementToBeClickable(cartHome));
		cartHome.click();
	}

	public void clickonCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable(checkout));
		checkout.click();
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return wUserName.getText().trim();
	}
}