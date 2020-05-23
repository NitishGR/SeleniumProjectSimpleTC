package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserOrderHistoryPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public UserOrderHistoryPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='content']//tbody//tr")
	private List<WebElement> listOrderRow;

	@FindBy(xpath = "//tbody//td[1]")
	private WebElement OrderIDCol;

	@FindBy(xpath = "//tbody//td[4]")
	private WebElement OrderCol;

	@FindBy(id = "input-comment")
	private WebElement returnReason;

	@FindBy(xpath = "//input[@name='agree'][@type='checkbox']")
	private WebElement selectagree;

	@FindBy(xpath = "//input[@value='Submit'][@type='submit']")
	private WebElement submitbtn;

	@FindBy(xpath = "//div[@id='content']//p[1]")
	private WebElement message1;

	@FindBy(xpath = "//div[@id='content']//p[2]")
	private WebElement message2;

	public String getOrderStatus(String sOrderID) {
		String sOrderStatus = "";
		for (WebElement ele : listOrderRow) {
			System.out.println("Inside For Loop:" + ele.getText());
			// check if the order ID is present
			if (ele.findElement(By.xpath("//tbody//td[1]")).getText().trim().contains(sOrderStatus)) {
				// Get the Order Status
				System.out.println("Inside for - inside if:" + ele.findElement(By.xpath("//tbody//td[1]")).getText());
				sOrderStatus = ele.findElement(By.xpath("//tbody//td[4]")).getText().trim();
				System.out.println("Inside for - inside if:" + sOrderStatus);
				break;
			}
		}
		return sOrderStatus;
	}

	public boolean clickOnViewOrder(String sDate) {
		boolean isPresent = false;
		List<WebElement> viewOrderList = driver.findElements(By.xpath(
				"//table//tbody//td[text()='" + sDate + "']/following-sibling::td//a[@data-original-title='View']"));
		if (viewOrderList.size() != 0) {
			isPresent = true;
			viewOrderList.get(0).click();
		}
		return isPresent;
	}

	public boolean clickOnReturnProduct(String sProductName) {
		boolean isPresent = false;
		List<WebElement> returnProductList = driver.findElements(By.xpath("//table//tbody//td[contains(text(),'"
				+ sProductName + "')]//following-sibling::td/a[@data-original-title='Return']"));
		if (returnProductList.size() != 0) {
			isPresent = true;
			returnProductList.get(0).click();
		}
		return isPresent;
	}

	public void selectReturnReason(String sReturnReasonOption) {
		// TODO Auto-generated method stub
		WebElement ele = driver
				.findElement(By.xpath("//label[contains(.,'" + sReturnReasonOption + "')]/input[@type='radio']"));
		if (!ele.isSelected()) {
			ele.click();
		}

	}

	public void selectisProductOpened(String isOptionPresent) {
		// TODO Auto-generated method stub
		WebElement ele = driver.findElement(
				By.xpath("//label[contains(text(),'Product is opened')]//following-sibling::div//label[contains(.,'"
						+ isOptionPresent + "')]/input[@type='radio']"));
		if (!ele.isSelected()) {
			ele.click();
		}
	}

	public void returnReasonComment(String sReturnReasonComment) {
		returnReason.clear();
		returnReason.sendKeys(sReturnReasonComment);
	}

	public void selectAgreetoReturnProduct() {
		// TODO Auto-generated method stub
		if (!selectagree.isSelected()) {
			selectagree.click();
		}
	}

	public void clickonSubmitButton() {
		submitbtn.click();
	}

	public String getMessage1() {
		return message1.getText().trim();
	}

	public String getMessage2() {
		return message2.getText().trim();
	}
}