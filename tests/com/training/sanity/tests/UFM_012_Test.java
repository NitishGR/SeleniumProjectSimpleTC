package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.CategoriesPOM;
import com.training.pom.DashboardPOM;
import com.training.pom.LoginUniformAdminPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class UFM_012_Test {
	private WebDriver driver;
	private String baseUrl;
	// private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	private LoginUniformAdminPOM loginPOM;
	private DashboardPOM dashboardPOM;
	private CategoriesPOM categoriesPOM;

	@BeforeClass
	public void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);

		driver = DriverFactory.getDriver(DriverNames.CHROME);
		// loginPOM = new LoginPOM(driver);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(properties.getProperty("implicitWait")),
				TimeUnit.SECONDS);

		// Load POM
		loginPOM = new LoginUniformAdminPOM(driver);
		dashboardPOM = new DashboardPOM(driver);
		categoriesPOM = new CategoriesPOM(driver);

		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);

		// open the browser
		driver.get(baseUrl);
	}

	@AfterClass
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		// driver.quit();
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
