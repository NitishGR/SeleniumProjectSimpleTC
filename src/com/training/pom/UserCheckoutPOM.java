package com.training.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserCheckoutPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public UserCheckoutPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='accordion-toggle'][@aria-expanded='true']")
	private WebElement activeSection;

	@FindBy(id = "button-payment-address")
	private WebElement continuePaymentAddr;

	@FindBy(id = "button-shipping-address")
	private WebElement continueShippingAddr;

	@FindBy(id = "button-shipping-method")
	private WebElement continueShippingMethod;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement selectTermsCondition;

	@FindBy(id = "button-payment-method")
	private WebElement continuePaymentMethod;

	@FindBy(id = "button-confirm")
	private WebElement confirmOrder;

	@FindBy(id = "input-email")
	private WebElement userName;

	@FindBy(id = "input-password")
	private WebElement password;

	@FindBy(id = "button-login")
	private WebElement loginBtn;
	
	public String getActiveSectioninCheckout() {
		return activeSection.getText().trim();
	}

	public void clickonContinePaymentAddr() {
		wait.until(ExpectedConditions.elementToBeClickable(continuePaymentAddr));
		continuePaymentAddr.click();
	}

	public void clickonContineShippingAddr() {
		wait.until(ExpectedConditions.elementToBeClickable(continueShippingAddr));
		continueShippingAddr.click();
	}

	public void clickonContineShippingMethod() {
		wait.until(ExpectedConditions.elementToBeClickable(continueShippingMethod));
		continueShippingMethod.click();
	}

	public void checkTermsandCond() {
		wait.until(ExpectedConditions.elementToBeClickable(selectTermsCondition));
		if (!selectTermsCondition.isSelected()) {
			selectTermsCondition.click();
		}
	}

	public void clickonContinePaymentMethod() {
		wait.until(ExpectedConditions.elementToBeClickable(continuePaymentMethod));
		continuePaymentMethod.click();
	}

	public void clickonConfirmOrder() {
		wait.until(ExpectedConditions.elementToBeClickable(confirmOrder));
		confirmOrder.click();
	}

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
}