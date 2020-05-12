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

public class UFM_011_Test {
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
		driver.quit();
	}

	@Test(priority = 0)
	public void UFM_011_Login() {
		// Login to Uniform Site with Admin ID
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_011_Login");
		String sCurrentURL = driver.getCurrentUrl();
		Assert.assertEquals(sCurrentURL.contains("dashboard"), true);
		System.out.println("Logged in to Uniform site");
	}

	@Test(dependsOnMethods = "UFM_011_Login")
	public void UFM_011_CatalogMenu() {
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
		screenShot.captureScreenShot("UFM_011_Dashboard_CatalogOpt");

		// Click on Categories
		dashboardPOM.clickonCategories();
	}

	@Test(dependsOnMethods = "UFM_011_CatalogMenu")
	public void UFM_011_CategoriesList() {
		// Check if Category Name, Sort Order & Actions are displayed
		Assert.assertEquals(categoriesPOM.getTextCategoryName(), "Category Name");
		System.out.println("'Category Name' is displayed");
		Assert.assertEquals(categoriesPOM.getTextSortOrder(), "Sort Order");
		System.out.println("'Sort Order' is displayed");
		Assert.assertEquals(categoriesPOM.getTextAction(), "Action");
		System.out.println("'Action' is displayed");
		screenShot.captureScreenShot("UFM_011_CategoryList");
	}
}
