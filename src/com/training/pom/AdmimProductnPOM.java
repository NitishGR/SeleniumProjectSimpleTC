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

public class AdmimProductnPOM {

	private WebDriver driver;
	private WebDriverWait wait;
	private int iPageNo = 1;
	private JavascriptExecutor js;

	public AdmimProductnPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = ">")
	private WebElement nextbutton;

	@FindBy(xpath = "//button[@id='button-filter']")
	private WebElement filterbutton;

	@FindBy(xpath = "//input[@id='input-name']")
	private WebElement inputproductName;

	@FindBy(xpath = "//input[@id='input-price']")
	private WebElement inputproductPrice;

	@FindBy(xpath = "//select[@id='input-status']")
	private WebElement selectproductStatus;

	@FindBy(xpath = "//input[@id='input-model']")
	private WebElement inputproductModel;

	@FindBy(xpath = "//input[@id='input-quantity']")
	private WebElement inputproductQuantity;

	@FindBy(xpath = "//form[@id='form-product']//tbody")
	private WebElement resulstTable;

	@FindBy(xpath = "//tbody//tr/td")
	private List<WebElement> listProductResultCol;

	@FindBy(xpath = "//tbody//tr")
	private List<WebElement> listProductResultRows;

	@FindBy(xpath = "//form[@id='form-product']//tbody/tr/td[3]")
	private List<WebElement> listProductNameCol;

	@FindBy(xpath = "//form[@id='form-product']//tbody/tr/td[4]")
	private List<WebElement> listProductModelCol;

	@FindBy(xpath = "//form[@id='form-product']//tbody/tr/td[5]")
	private List<WebElement> listProductPriceCol;

	@FindBy(xpath = "//form[@id='form-product']//tbody/tr/td[6]")
	private List<WebElement> listProductQuantityCol;

	@FindBy(xpath = "//form[@id='form-product']//tbody/tr/td[7]")
	private List<WebElement> listProductStatusCol;

	@FindBy(xpath = "//div[@class='pull-right']//a[@class='btn btn-primary']")
	private WebElement addProduct;
	
	@FindBy(xpath = "//button[@class='btn btn-primary'][@data-original-title='Save']")
	private WebElement saveProduct;
	
	

	@FindBy(xpath = "//form[@id='form-product']/ul/li[@class='active']/a")
	private WebElement addProduct_activeTab;

	@FindBy(xpath = "//a[contains(text(),'Links')]")
	private WebElement addProduct_LinksTab;

	@FindBy(xpath = "//a[contains(text(),'Data')]")
	private WebElement addProduct_DataTab;

	@FindBy(xpath = "//a[contains(text(),'Attribute')]")
	private WebElement addProduct_AttributeTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Option')]")
	private WebElement addProduct_OptionTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Recurring')]")
	private WebElement addProduct_RecurringTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Discount')]")
	private WebElement addProduct_DiscountTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Special')]")
	private WebElement addProduct_SpecialTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Image')]")
	private WebElement addProduct_ImageTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Reward Points')]")
	private WebElement addProduct_RewardPointsTab;

	@FindBy(xpath = "//form[@id='form-product']//a[contains(text(),'Design')]")
	private WebElement addProduct_DesignTab;

	@FindBy(xpath = "//form[@id='form-product']//input[@id='input-name1']")
	private WebElement addProduct_inputproductName;

	@FindBy(xpath = "//input[@id='input-meta-title1']")
	private WebElement addProduct_inputproductMetaTitle;

	@FindBy(xpath = "//input[@id='input-model']")
	private WebElement addProduct_dataTab_inputproductModel;

	@FindBy(xpath = "//input[@id='input-price']")
	private WebElement addProduct_dataTab_inputproductPrice;

	@FindBy(xpath = "//input[@id='input-quantity']")
	private WebElement addProduct_dataTab_inputproductQuantity;

	@FindBy(xpath = "//input[@id='input-category']")
	private WebElement addProduct_linksTab_selectproductCategory;

	@FindBy(xpath = "//div[@class='alert alert-success']")
	private WebElement productSave_alertMessage;
	
	
	
	// Click on filter button to get results
	public void clickonFilterButton() {
		filterbutton.click();
		iPageNo = 1;
	}

	// Enter the Product Name in filter results
	public void enterProductName(String sProductName) {
		wait.until(ExpectedConditions.elementToBeClickable(inputproductName));
		inputproductName.click();
		inputproductName.sendKeys(sProductName);
	}

	// Enter the Product Price in filter results
	public void enterProductPrice(String sProductPrice) {
		inputproductPrice.sendKeys(sProductPrice);
	}

	// Select the Product Status in filter results
	public boolean selectProductStatus(String sProductStatus) {
		boolean isPresent = false;
		Select optionsproductStatus = new Select(selectproductStatus);
		List<WebElement> availableopt = optionsproductStatus.getOptions();
		int i = 0;
		while (i < availableopt.size()) {
			if (availableopt.get(i).getText().trim().equals(sProductStatus)) {
				optionsproductStatus.selectByVisibleText(sProductStatus);
				isPresent = true;
				break;
			}
			i++;
		}
		return isPresent;
	}

	// Enter the Product Model in filter results
	public void enterProductModel(String sProductModel) {
		inputproductModel.sendKeys(sProductModel);
	}

	// Enter the Product Quantity in filter results
	public void enterProductQuantity(String sProductQuantity) {
		inputproductQuantity.sendKeys(sProductQuantity);
	}

	// Clear the selection in the filter criteria
	public void clearText() {
		inputproductName.clear();
		inputproductModel.clear();
		inputproductPrice.clear();
		inputproductQuantity.clear();
		new Select(selectproductStatus).selectByValue("*");
	}

	public void verifyResultsofFilterByProductName(String sProductName) {
		boolean isRecordExist = true;
		iPageNo = 1;
		int iNoofRows;
		String sActualProductName;
		if (listProductResultCol.size() > 1) {
			do {
				iNoofRows = listProductResultRows.size();
				System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
				int row = 0;
				while (row < iNoofRows) {
					sActualProductName = listProductNameCol.get(row).getText().trim();
					System.out.println(
							"Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Name:" + sActualProductName);
					Assert.assertTrue(sActualProductName.toLowerCase().contains(sProductName.toLowerCase()),
							"Product Name does not match..Expected:'" + sProductName + "' Actual:'" + sActualProductName
									+ "'");
					row++;
				}
				if (driver.findElements(By.xpath("//*[text()='" + iPageNo + "']/parent::li/following-sibling::li"))
						.size() != 0) {
					nextbutton.click();
				} else {
					isRecordExist = false;
				}
				iPageNo++;
			} while (isRecordExist);
		} else {
			System.out.println(listProductResultCol.get(0).getText());
		}
	}

	// Verify if record exists for the selected criteria
	public boolean recordExists() {
		if (listProductResultCol.size() > 1) {
			js.executeScript("arguments[0].scrollIntoView(true);", resulstTable);
			return true;
		} else {
			System.out.println(listProductResultCol.get(0).getText());
			return false;
		}
	}

	// Verify if results are in next page
	public boolean verifyifResultsinNextPage() {
		if (driver.findElements(By.xpath("//*[text()='" + iPageNo + "']/parent::li/following-sibling::li"))
				.size() != 0) {
			nextbutton.click();
			iPageNo++;
			js.executeScript("arguments[0].scrollIntoView(true);", resulstTable);
			return true;
		} else {
			return false;
		}
	}

	// Verify Results of Current Page filtered by Product Name
	public void verResofCurPageFilteredByProductName(String sProductName) {
		String sActualProductName;
		int iNoofRows = listProductResultRows.size();
		System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
		int row = 0;
		while (row < iNoofRows) {
			sActualProductName = listProductNameCol.get(row).getText().trim();
			System.out
					.println("Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Name:" + sActualProductName);
			Assert.assertTrue(sActualProductName.toLowerCase().contains(sProductName.toLowerCase()),
					"Product Name does not match..Expected:'" + sProductName + "' Actual:'" + sActualProductName + "'");
			row++;
		}
	}

	// Verify Results of Current Page filtered by Product Price
	public void verResofCurPageFilteredByProductPrice(String sProductPrice) {
		String sActualProductPrice;
		int iNoofRows = listProductResultRows.size();
		System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
		int row = 0;
		while (row < iNoofRows) {
			sActualProductPrice = listProductPriceCol.get(row).getText().trim();
			System.out.println(
					"Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Price:" + sActualProductPrice);
			Assert.assertTrue(sActualProductPrice.toLowerCase().contains(sProductPrice.toLowerCase()),
					"Product Price does not match..Expected:'" + sProductPrice + "' Actual:'" + sActualProductPrice
							+ "'");
			row++;
		}
	}

	// Verify Results of Current Page filtered by Product Model
	public void verResofCurPageFilteredByProductModel(String sProductModel) {
		String sActualProductModel;
		int iNoofRows = listProductResultRows.size();
		System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
		int row = 0;
		while (row < iNoofRows) {
			sActualProductModel = listProductModelCol.get(row).getText().trim();
			System.out.println(
					"Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Model:" + sActualProductModel);
			Assert.assertTrue(sActualProductModel.toLowerCase().contains(sProductModel.toLowerCase()),
					"Product Model does not match..Expected:'" + sProductModel + "' Actual:'" + sActualProductModel
							+ "'");
			row++;
		}
	}

	// Verify Results of Current Page filtered by Product Quantity
	public void verResofCurPageFilteredByProductQuantity(String sProductQuantity) {
		String sActualProductQuantity;
		int iNoofRows = listProductResultRows.size();
		System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
		int row = 0;
		while (row < iNoofRows) {
			sActualProductQuantity = listProductQuantityCol.get(row).getText().trim();
			System.out.println(
					"Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Quantity:" + sActualProductQuantity);
			Assert.assertTrue(sActualProductQuantity.toLowerCase().contains(sProductQuantity.toLowerCase()),
					"Product Quantity does not match..Expected:'" + sProductQuantity + "' Actual:'"
							+ sActualProductQuantity + "'");
			row++;
		}
	}

	// Verify Results of Current Page filtered by Product Status
	public void verResofCurPageFilteredByProductStatus(String sProductStatus) {
		String sActualProductStatus;
		int iNoofRows = listProductResultRows.size();
		System.out.println("Number of Records in Page:" + iPageNo + " is:" + iNoofRows);
		int row = 0;
		while (row < iNoofRows) {
			sActualProductStatus = listProductStatusCol.get(row).getText().trim();
			System.out.println(
					"Page No:" + iPageNo + " :: Row:" + (row + 1) + " :: Product Status:" + sActualProductStatus);
			Assert.assertTrue(sActualProductStatus.toLowerCase().contains(sProductStatus.toLowerCase()),
					"Product Status does not match..Expected:'" + sProductStatus + "' Actual:'" + sActualProductStatus
							+ "'");
			row++;
		}
	}

	// Click on Add Product button
	public void clickonAddProduct() {
		addProduct.click();
	}

	// Get the text of active tab in Add Product page
	public String getActiveTabinAddProduct() {
		return addProduct_activeTab.getText();
	}

	// Enter the Product Name in Add Product page
	public void addProduct_enterProductName(String sProductName) {
		addProduct_inputproductName.sendKeys(sProductName);
	}

	// Enter the Product Meta Title in Add Product page
	public void addProduct_enterProductMetaTitle(String sProductMetaTitle) {
		addProduct_inputproductMetaTitle.sendKeys(sProductMetaTitle);
	}

	// Enter the Product Model in Add Product page Data Tab
	public void addProduct_dataTab_enterProductModel(String sProductModel) {
		addProduct_dataTab_inputproductModel.sendKeys(sProductModel);
	}

	// Enter the Product Model in Add Product page Data Tab
	public void addProduct_dataTab_enterProductPrice(String sProductPrice) {
		addProduct_dataTab_inputproductPrice.sendKeys(sProductPrice);
	}

	// Enter the Product Model in Add Product page Data Tab
	public void addProduct_dataTab_enterProductQuantity(String sProductQuantity) {
		addProduct_dataTab_inputproductQuantity.sendKeys(sProductQuantity);
	}

	// Select the Product Category in Add Product page Links Tab
	public void addProduct_linksTab_selectProductCategory(String sProductCategory) {
		addProduct_linksTab_selectproductCategory.sendKeys(sProductCategory);
		addProduct_linksTab_selectproductCategory.findElement(By.xpath("//a[text()='"+sProductCategory+"']")).click();
	}

	// Click on Data Tab
	public void clickonaddProduct_DataTab() {
		addProduct_DataTab.click();
	}

	// Click on Links Tab
	public void clickonaddProduct_LinksTab() {
		addProduct_LinksTab.click();
	}

	// Click on Attribute Tab
	public void clickonaddProduct_AttributeTab() {
		addProduct_AttributeTab.click();
	}

	// Click on Option Tab
	public void clickonaddProduct_OptionTab() {
		addProduct_OptionTab.click();
	}

	// Click on Recurring Tab
	public void clickonaddProduct_RecurringTab() {
		addProduct_RecurringTab.click();
	}

	// Click on Discount Tab
	public void clickonaddProduct_DiscountTab() {
		addProduct_DiscountTab.click();
	}

	// Click on Special Tab
	public void clickonaddProduct_SpecialTab() {
		addProduct_SpecialTab.click();
	}

	// Click on Image Tab
	public void clickonaddProduct_ImageTab() {
		addProduct_ImageTab.click();
	}

	// Click on RewardPoints Tab
	public void clickonaddProduct_RewardPointsTab() {
		addProduct_RewardPointsTab.click();
	}

	// Click on Design Tab
	public void clickonaddProduct_DesignTab() {
		addProduct_DesignTab.click();
	}
	
	//Click on SaveProduct
	public void clickonSaveProduct() {
		saveProduct.click();
	}
	
	public String getMessage() {
		return productSave_alertMessage.getText().trim();
	}
	
	
}
