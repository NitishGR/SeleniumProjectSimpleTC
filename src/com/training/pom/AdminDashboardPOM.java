package com.training.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminDashboardPOM {

	private WebDriver driver;
	private WebDriverWait wait;

	public AdminDashboardPOM(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "button-menu")
	private WebElement menubutton;

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

	public void clickonMenu() {
		menubutton.click();
	}

	public void clickonCatalog() {
		catlog.click();
	}
	
	public void clickonSales() {
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
		categories.click();
	}
	
	public void clickonOrders() {
		orders.click();
	}

	public void clickonProducts() {
		products.click();
	}
	
	public void clickonViewOrders() {
		viewOrders.click();
	}
}
