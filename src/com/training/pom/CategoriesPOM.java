package com.training.pom;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoriesPOM {

	private WebDriver driver;

	public CategoriesPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//form[@id='form-category']//thead/tr//td[2]/a")
	private WebElement thCategoryName;

	@FindBy(xpath = "//form[@id='form-category']//thead/tr//td[3]/a")
	private WebElement thSortOrder;

	@FindBy(xpath = "//form[@id='form-category']//thead/tr//td[4]")
	private WebElement thAction;

	@FindBy(linkText = ">")
	private List<WebElement> nextbutton;

	@FindBy(xpath = "//form[@id='form-category']//tbody/tr//td[2]")
	private List<WebElement> weRowList_CategoryName;

	@FindBy(xpath = "//button[@data-original-title='Delete']")
	private WebElement deleteButton;

	@FindBy(xpath = "//div[@class='alert alert-success']")
	private WebElement deleteMessage;

	public boolean isCategoryNamePresent(String sCategoryName) {
		boolean isPresent = false;
		int row;
		int page = 1;
		System.out.println("Expected Category Name: " + sCategoryName);
		while (true) {
			row = 1;
			for (WebElement eachRow : weRowList_CategoryName) {
				System.out.println("Page: " + page + " -- Row: " + row + " -- Text: " + eachRow.getText());
				if (eachRow.getText().trim().equals(sCategoryName)) {
					isPresent = true;
					break;
				}
				row++;
			}
			if (isPresent || nextbutton.size() <= 0) {
				break;
			}
			nextbutton.get(0).click();
			page++;
		}
		return isPresent;
	}

	public String getTextCategoryName() {
		return thCategoryName.getText().trim();
	}

	public String getTextSortOrder() {
		return thSortOrder.getText().trim();
	}

	public String getTextAction() {
		return thAction.getText().trim();
	}

	public void selectCategoryName(String sCategory) {
		if (!(driver.findElement(By.xpath("//td[text()='" + sCategory + "']/preceding-sibling::td/input")))
				.isSelected()) {
			driver.findElement(By.xpath("//td[text()='" + sCategory + "']/preceding-sibling::td/input")).click();
		}
	}

	public String deleteSelectedCategory() {
		String message = "";

		// Click on Delete
		deleteButton.click();

		// Switch to alert
		Alert alert = driver.switchTo().alert();
		alert.accept();

		// Delete message
		message = deleteMessage.getText().trim();

		return message;
	}

}
