package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.AdminCategoriesPOM;
import com.training.pom.AdminDashboardPOM;
import com.training.pom.AdminLoginUniformPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class UFM_012_Test {
	private WebDriver driver;
	private WebDriverWait wait;
	private String baseUrl;
	// private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	private AdminLoginUniformPOM loginPOM;
	private AdminDashboardPOM dashboardPOM;
	private AdminCategoriesPOM categoriesPOM;

	@BeforeClass
	public void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);

		driver = DriverFactory.getDriver(DriverNames.CHROME);
		// loginPOM = new LoginPOM(driver);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(properties.getProperty("implicitWait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Integer.parseInt(properties.getProperty("explicitWait")));

		// Load POM
		loginPOM = new AdminLoginUniformPOM(driver);
		dashboardPOM = new AdminDashboardPOM(driver, wait);
		categoriesPOM = new AdminCategoriesPOM(driver);

		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);

		// open the browser
		driver.get(baseUrl);
	}

	@AfterClass
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test(priority = 0)
	public void UFM_012_Login() {
		// Login to Uniform Site with Admin ID
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_012_Login");
		String sCurrentURL = driver.getCurrentUrl();
		Assert.assertEquals(sCurrentURL.contains("dashboard"), true);
		System.out.println("Logged in to Uniform site");
	}

	@Test(dependsOnMethods = "UFM_012_Login")
	public void UFM_012_CatalogMenu() {
		// Click on Menu
		dashboardPOM.clickonMenu();

		// Click on Catalog
		dashboardPOM.clickonCatalog();

		// Get list of Options under Catalog
		ArrayList<String> actualCatalogOpt = new ArrayList<String>();
		actualCatalogOpt = dashboardPOM.getCatalogOptions();
		System.out.println("Actual Catalog Options: " + actualCatalogOpt);

		// Expected options under Catalog
		ArrayList<String> expectedCatalogOpt = new ArrayList<String>();
		String expectedCatalog = "Categories|Products|Recurring Profiles|Filters|Attributes|Options";
		for (String opt : expectedCatalog.split("\\|")) {
			expectedCatalogOpt.add(opt);
		}
		System.out.println("Expected Catalog Options: " + expectedCatalogOpt);

		// Compare - Expected vs Actual Catalog options
		Assert.assertEquals(actualCatalogOpt.containsAll(expectedCatalogOpt), true);
		screenShot.captureScreenShot("UFM_012_Dashboard_CatalogOpt");
		System.out.println("Expected List is present in the actual List of Catalog");

		// Click on Categories
		dashboardPOM.clickonCategories();
	}

	@Test(dependsOnMethods = "UFM_012_CatalogMenu")
	public void UFM_012_CatagoriesList() {
		// Check if Category Name, Sort Order & Actions are displayed
		Assert.assertEquals(categoriesPOM.getTextCategoryName(), "Category Name", "'Category Name' is not displayed");
		Assert.assertEquals(categoriesPOM.getTextSortOrder(), "Sort Order", "'Sort Order' is not displayed");
		Assert.assertEquals(categoriesPOM.getTextAction(), "Action", "'Action' is not displayed");
		screenShot.captureScreenShot("UFM_012_CategoryList");

	}

	@Test(dependsOnMethods = "UFM_012_CatagoriesList")
	public void UFM_012_deleteCategory() {
		// Name of category you want to delete
		String sCategory = "New Uniforms";
		String expectedDeleteMessage = "Success: You have modified categories!";

		// Check if Category Name is present in the table
		Assert.assertEquals(categoriesPOM.isCategoryNamePresent(sCategory), true,
				sCategory + " - is not present in the category list table");
		System.out.println("Category Name '" + sCategory + "' is present in the table");

		// select the category Name you want to delete
		categoriesPOM.selectCategoryName(sCategory);
		screenShot.captureScreenShot("UFM_012_Category");

		// Delete the category Name
		String deleteMessage = categoriesPOM.deleteSelectedCategory();
		System.out.println("Actual Delete Message :" + deleteMessage);
		screenShot.captureScreenShot("UFM_012_DeleteMessage");
		Assert.assertTrue(deleteMessage.contains(expectedDeleteMessage));
	}
}
