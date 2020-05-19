package com.training.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProductDetailsPOM {

	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	public UserProductDetailsPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-option376")
	WebElement selectChestSize;

	@FindBy(id = "cloud-zoom")
	WebElement productImage;
	
	@FindBy(id = "button-cart")
	WebElement addProductToCart;
	

	public boolean selectChestSize(String sChestSize) {
		wait.until(ExpectedConditions.visibilityOf(productImage));
		Select optionsforChestSize = new Select(selectChestSize);
		int index = 0;
		for (WebElement opt : optionsforChestSize.getOptions()) {
			if (opt.getText().trim().startsWith(sChestSize)) {
				optionsforChestSize.selectByIndex(index);
				return true;
			}
			index++;
		}
		return false;
	}
	
	public void addProductToCart() {
		wait.until(ExpectedConditions.elementToBeClickable(addProductToCart));
		addProductToCart.click();
	}

}