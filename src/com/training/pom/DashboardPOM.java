package com.training.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPOM {

	private WebDriver driver;

	public DashboardPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "button-menu")
	private WebElement menubutton;

	@FindBy(xpath = "//ul[@id='menu']//li[@id='catalog']")
	private WebElement catlog;

	@FindBy(xpath = "//li[@id='catalog']/ul[@class='collapse in']/li/a")
	private List<WebElement> showCatalogOptions;

	@FindBy(xpath = "//li[@id='catalog']//a[(text()='Categories')]")
	private WebElement categories;

	public void clickonMenu() {
		menubutton.click();
	}

	public void clickonCatalog() {
		catlog.click();
	}

	public ArrayList<String> getCatalogOptions() {
		ArrayList<String> actualCatalogOpt = new ArrayList<String>();
		for (WebElement opt : showCatalogOptions) {
			actualCatalogOpt.add(opt.getText().trim());
		}
		return actualCatalogOpt;
	}

	public void clickonCategories() {
		categories.click();
	}
}
