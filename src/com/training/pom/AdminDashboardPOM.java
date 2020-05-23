package com.training.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminDashboardPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public AdminDashboardPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@id='button-menu']/i")
	private WebElement menubutton;
	// a[@id='button-menu']/i[contains(@class,'fa-indent')]

	@FindBy(xpath = "//ul[@id='menu']//li[@id='catalog']")
	private WebElement catlog;

	@FindBy(xpath = "//ul[@id='menu']//li[@id='sale']")
	private WebElement sales;

	@FindBy(xpath = "//li[@id='catalog']/ul[@class='collapse in']/li/a")
	private List<WebElement> showCatalogOptions;

	@FindBy(xpath = "//li[@id='sale']/ul[@class='collapse in']/li/a")
	private List<WebElement> showSalesOptions;

	@FindBy(xpath = "//li[@id='catalog']//a[(text()='Categories')]")
	private WebElement categories;

	@FindBy(xpath = "//li[@id='sale']//a[(text()='Orders')]")
	private WebElement orders;

	@FindBy(xpath = "//li[@id='catalog']//a[(text()='Products')]")
	private WebElement products;

	@FindBy(xpath = "//i[@class='fa fa-shopping-cart']/parent::div/following-sibling::div/a[text()='View more...']")
	private WebElement viewOrders;

	@FindBy(xpath = "//a/child::span[text()='Logout']")
	private WebElement logout;

	public void clickonMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(menubutton));
		if (menubutton.getAttribute("class").contains("fa-indent")) {
			menubutton.click();
		}
	}

	public void clickonCatalog() {
		wait.until(ExpectedConditions.elementToBeClickable(catlog));
		catlog.click();
	}

	public void clickonSales() {
		wait.until(ExpectedConditions.elementToBeClickable(sales));
		sales.click();
	}

	public ArrayList<String> getCatalogOptions() {
		ArrayList<String> actualCatalogOpt = new ArrayList<String>();
		for (WebElement opt : showCatalogOptions) {
			actualCatalogOpt.add(opt.getText().trim());
		}
		return actualCatalogOpt;
	}

	public ArrayList<String> getSalesOptions() {
		ArrayList<String> actualSalesOpt = new ArrayList<String>();
		for (WebElement opt : showSalesOptions) {
			actualSalesOpt.add(opt.getText().trim());
		}
		return actualSalesOpt;
	}

	public void clickonCategories() {
		wait.until(ExpectedConditions.elementToBeClickable(categories));
		categories.click();
	}

	public void clickonOrders() {
		wait.until(ExpectedConditions.elementToBeClickable(orders));
		orders.click();
	}

	public void clickonProducts() {
		wait.until(ExpectedConditions.elementToBeClickable(products));
		products.click();
	}

	public void clickonViewOrders() {
		wait.until(ExpectedConditions.elementToBeClickable(viewOrders));
		viewOrders.click();
	}

	public void clickonLogout() {
		wait.until(ExpectedConditions.elementToBeClickable(logout));
		logout.click();
	}
}
