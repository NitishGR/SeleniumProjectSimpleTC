package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdmimOrdersPOM {

	private WebDriver driver;
	private WebDriverWait wait;
	private int iPageNo = 1;
	private JavascriptExecutor js;

	public AdmimOrdersPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = ">")
	private WebElement nextbutton;

	// Edit Order
	@FindBy(xpath = "//ul[@id='order']/li[contains(@class,'active')]")
	private WebElement activeTab;

	@FindBy(id = "button-customer")
	private WebElement continueButton;

	@FindBy(id = "button-cart")
	private WebElement continueToPaymentButton;

	@FindBy(id = "button-payment-address")
	private WebElement continueToShippingAddrButton;

	@FindBy(id = "button-shipping-address")
	private WebElement continueToSaveOrderButton;

	@FindBy(id = "input-product")
	private WebElement adminInputProductName;

	@FindBy(xpath = "//input[@id='input-product']/following-sibling::ul/li/a")
	private List<WebElement> selectInputProductName;

	@FindBy(id = "input-quantity")
	private WebElement adminInputProductQuantity;

	@FindBy(id = "button-product-add")
	private WebElement adminAddProductButton;

	@FindBy(id = "button-save")
	private WebElement adminSaveOrderButton;

	@FindBy(xpath = "//div[contains(@class,'alert')]")
	private WebElement ordereditMessage;

	@FindBy(id = "input-shipping-method")
	private WebElement selectShippingMethod;

	@FindBy(id = "button-shipping-method")
	private WebElement applyShippingMethod;

	@FindBy(id = "tab-cart")
	private WebElement productTableView;

	@FindBy(id = "input-order-status")
	private WebElement productOrderStatus;

	@FindBy(id = "button-history")
	private WebElement addProductHistory;

	public boolean verifyOrderExists(String sUserName, String sDate) {
		boolean isRecordExists = false;
		// List<WebElement> cellUserName =
		// driver.findElements(By.xpath("//form[@id='form-order']//tbody//td[text()='"+sUserName+"']"));
		// ;;
		List<WebElement> cellOrderDatewithUserName = driver.findElements(
				By.xpath("//tbody//td[text()='" + sUserName + "']/following-sibling::td[3][text()='" + sDate + "']"));

		if (cellOrderDatewithUserName.size() > 0) {
			isRecordExists = true;
			js.executeScript("arguments[0].scrollIntoView(true);", cellOrderDatewithUserName.get(0));
		}
		return isRecordExists;
	}

	// Verify if results are in next page
	public boolean verifyifResultsinNextPage() {
		if (driver.findElements(By.xpath("//*[text()='" + iPageNo + "']/parent::li/following-sibling::li"))
				.size() != 0) {
			nextbutton.click();
			iPageNo++;
			return true;
		} else {
			return false;
		}
	}

	public void clickonEditOrder(String sUserName, String sDate) {
		List<WebElement> editButton = driver
				.findElements(By.xpath("//tbody//td[text()='" + sUserName + "']/following-sibling::td[3][text()='"
						+ sDate + "']//following-sibling::td[2]/a[@data-original-title='Edit']"));
		if (editButton.size() != 0) {
			editButton.get(0).click();
		}
	}

	public void clickonViewOrder(String sUserName, String sDate) {
		List<WebElement> editButton = driver
				.findElements(By.xpath("//tbody//td[text()='" + sUserName + "']/following-sibling::td[3][text()='"
						+ sDate + "']//following-sibling::td[2]/a[@data-original-title='View']"));
		if (editButton.size() != 0) {
			editButton.get(0).click();
		}
	}

	public String getActiveTab() {
		js.executeScript("arguments[0].scrollIntoView();", activeTab);
		return activeTab.getText().trim();
	}

	public void clickonContinueButton() {
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		continueButton.click();
	}

	public void clickonContinueToPayment() {
		wait.until(ExpectedConditions.elementToBeClickable(continueToPaymentButton));
		continueToPaymentButton.click();
	}

	public void clickonContinueToShippingAddr() {
		wait.until(ExpectedConditions.elementToBeClickable(continueToShippingAddrButton));
		continueToShippingAddrButton.click();
	}

	public void clickonContinueToSaveOrder() {
		wait.until(ExpectedConditions.elementToBeClickable(continueToSaveOrderButton));
		continueToSaveOrderButton.click();
	}

	public void removeOrderPlaced(String sProductName) {
		List<WebElement> orderList = driver.findElements(By.xpath("//tbody[@id='cart']//tr//td[contains(text(),'"
				+ sProductName + "')]//following-sibling::td/button[@data-original-title='Remove']"));
		int i = 0;
		int iOrderCount = orderList.size();

		while (i < iOrderCount) {
			orderList = driver.findElements(By.xpath("//tbody[@id='cart']//tr//td[contains(text(),'" + sProductName
					+ "')]//following-sibling::td/button[@data-original-title='Remove']"));
			WebElement wOrder = orderList.get(0);
			wOrder.click();
			wait.until(ExpectedConditions.invisibilityOf(wOrder));
			i++;
		}
	}

	public boolean verifyOrderPlacedIsRemoved(String sProductName) {
//		List<WebElement> order = driver.findElements(By.xpath("//tbody[@id='cart']//tr//td[contains(text(),'"
//				+ sProductName + "')]//following-sibling::td/button[@data-original-title='Remove']"));
		List<WebElement> order = driver
				.findElements(By.xpath("//tbody[@id='cart']//tr//td[contains(text(),'" + sProductName + "')]"));
		if (order.size() == 0) {
			return true;
		}
		return false;
	}

	public void adminEnterProductName(String sAdminProductName) {
		adminInputProductName.clear();
		adminInputProductName.sendKeys(sAdminProductName);
		if (selectInputProductName.size() != 0) {
			selectInputProductName.get(0).click();
		}
	}

	public void adminEnterProductQuantity(String sAdminProductQuantity) {
		adminInputProductQuantity.clear();
		adminInputProductQuantity.sendKeys(sAdminProductQuantity);
	}

	public void adminAddProductButton() {
		wait.until(ExpectedConditions.elementToBeClickable(adminAddProductButton));
		adminAddProductButton.click();
	}

	public boolean verifyProductAddedByAdmin(String sAdminProductName) {
		boolean isProductAddedByAdminPreset = false;
		List<WebElement> adminOrder = driver
				.findElements(By.xpath("//tbody[@id='cart']//tr//td[contains(text(),'" + sAdminProductName + "')]"));
		if (adminOrder.size() != 0) {
			isProductAddedByAdminPreset = true;
		}
		return isProductAddedByAdminPreset;
	}

	public void removeOutofStockOrders() {
		List<WebElement> removeOutofStockOrderList = driver.findElements(By.xpath(
				"//tbody[@id='cart']//tr//td//span[@class='text-danger'][text()='***']//parent::td//following-sibling::td/button"));
		int i = 0;
		int iOutofStockOrderCount = removeOutofStockOrderList.size();
		while (i < iOutofStockOrderCount) {
			removeOutofStockOrderList = driver.findElements(By.xpath(
					"//tbody[@id='cart']//tr//td//span[@class='text-danger'][text()='***']//parent::td//following-sibling::td/button"));
			WebElement wremoveOutofStockOrder = removeOutofStockOrderList.get(0);
			wremoveOutofStockOrder.click();
			wait.until(ExpectedConditions.invisibilityOf(wremoveOutofStockOrder));
			i++;
		}
	}

	public void clickonSaveOrder() {
		wait.until(ExpectedConditions.elementToBeClickable(adminSaveOrderButton));
		adminSaveOrderButton.click();
	}

	public String getEditMessage() {
		js.executeScript("arguments[0].scrollIntoView();", ordereditMessage);
		return ordereditMessage.getText().trim();
	}

	public void selectShippingMethodasFreeShiping() {
		Select options = new Select(selectShippingMethod);
		options.selectByValue("free.free");
		applyShippingMethod.click();
	}

	public void viewProductTable() {
		js.executeScript("arguments[0].scrollIntoView();", productTableView);
	}

	public boolean selectProductStatus(String sOption) {
		boolean isPresent = false;
		Select options = new Select(productOrderStatus);
		List<WebElement> optList = options.getOptions();

		for (WebElement opt : optList) {
			if (opt.getText().equals(sOption)) {
				isPresent = true;
				options.selectByVisibleText(sOption);
			}
		}
		return isPresent;
	}

	public void clickonAddProductHistory() {
		wait.until(ExpectedConditions.elementToBeClickable(addProductHistory));
		addProductHistory.click();
	}

	public String getOrderId(String sUserName, String sdate) {
		// TODO Auto-generated method stub
		String sOrderID = "";
		List<WebElement> listOrderID = driver.findElements(By.xpath("//td[6][text()='" + sdate
				+ "']/preceding-sibling::td[text()='" + sUserName + "']/preceding-sibling::td[1]"));
		if (listOrderID.size() != 0) {
			sOrderID = listOrderID.get(0).getText();
		}
		return sOrderID;
	}
}
