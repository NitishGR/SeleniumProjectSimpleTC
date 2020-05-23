package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserLoginUniformPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public UserLoginUniformPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-email")
	private WebElement userName;

	@FindBy(id = "input-password")
	private WebElement password;

	@FindBy(xpath = "//input[@type='submit'][@value='Login']")
	private WebElement loginBtn;

	@FindBy(xpath = "//a[@class='dropdown-toggle'][@title='My Account']")
	private WebElement myAccount;

	@FindBy(xpath = "//ul//li/a[text()='Login']")
	private WebElement myAccountLogin;

	@FindBy(xpath = "//ul//li/a[text()='Logout']")
	private WebElement myAccountLogout;

	

	public void sendUserName(String userName) {
		wait.until(ExpectedConditions.visibilityOf(this.userName));
		this.userName.clear();
		this.userName.sendKeys(userName);
	}

	public void sendPassword(String password) {
		this.password.clear();
		this.password.sendKeys(password);
	}

	public void clickLoginBtn() {
		this.loginBtn.click();
	}

	public void clickonMyAccount() {
		wait.until(ExpectedConditions.elementToBeClickable(myAccount));
		myAccount.click();
	}

	public void clickoMyAccountLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(myAccountLogin));
		myAccountLogin.click();
	}

	public void clickoMyAccountLogout() {
		wait.until(ExpectedConditions.elementToBeClickable(myAccountLogout));
		myAccountLogout.click();
	}

	
}