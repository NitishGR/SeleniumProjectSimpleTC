package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserAccountPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public UserAccountPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='Uniform Store']")
	private WebElement uniformStore;
	
	@FindBy(xpath = "//div[@id='content']//a[contains(text(),'View your order history')]")
	private WebElement userOrderHistory ; 
	
	public void clickoUniformStore() {
		wait.until(ExpectedConditions.elementToBeClickable(uniformStore));
		uniformStore.click();
	}
	
	public void clickonViewOrderHistory() {
		wait.until(ExpectedConditions.elementToBeClickable(userOrderHistory));
		userOrderHistory.click();
	}
}