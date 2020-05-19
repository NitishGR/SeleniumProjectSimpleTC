package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.AdminCategoriesPOM;
import com.training.pom.AdminDashboardPOM;
import com.training.pom.AdminLoginUniformPOM;
import com.training.pom.AdmimProductnPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class UFM_041_Test {
	private WebDriver driver;
	private WebDriverWait wait;

	private String baseUrl;
	// private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	private AdminLoginUniformPOM loginPOM;
	private AdminDashboardPOM dashboardPOM;
	private AdminCategoriesPOM categoriesPOM;
	private AdmimProductnPOM productsPOM;

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
		dashboardPOM = new AdminDashboardPOM(driver, null);
		categoriesPOM = new AdminCategoriesPOM(driver);
		productsPOM = new AdmimProductnPOM(driver, wait);

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

	@BeforeMethod(alwaysRun = true)
	public void BeforeTestMethod(Method result) {
		System.out.println("Executing Method  : " + result.getName());
	}

	@AfterMethod
	public void AfterTestMethod(ITestResult result) {
		System.out.println("Executed Method : " + result.getMethod().getMethodName());
		if (result.getStatus() == ITestResult.SUCCESS) {
			System.out.println("Result : Test Passed Successfully");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Result : Test Execution Failed");
		} else {
			System.out.println(result.getThrowable().getMessage());
		}
	}

	@Test(priority = 0)
	public void UFM_041_Login() {
		// Login to Uniform Site with Admin ID
		loginPOM.sendUserName(properties.getProperty("uniform.adminuser"));
		loginPOM.sendPassword(properties.getProperty("uniform.adminpwd"));
		loginPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_041_Login");
		String sCurrentURL = driver.getCurrentUrl();
		Assert.assertEquals(sCurrentURL.contains("dashboard"), true);
		System.out.println("Logged in to Uniform site");
	}

	@Test(dependsOnMethods = "UFM_041_Login")
	public void UFM_041_CatalogMenu() {
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
		screenShot.captureScreenShot("UFM_041_Dashboard_CatalogOpt");
		System.out.println("Expected List is present in the actual List of Catalog");

		// Click on Products
		dashboardPOM.clickonProducts();
	}

	@Test(dependsOnMethods = "UFM_041_CatalogMenu")
	public void UFM_041_ProductList() {
		// Input Data
		String sProductName = "shirt";
		String sProductPrice = "750.0000";
		String sProductModel = "SHG-010";
		String sProductStatus = "Enabled";
		String sProductQuantity = "150";

		System.out.println("Verify Results based on Product Name");
		System.out.println("Expected Product Name:" + sProductName);
		// Clear all the search criteria
		productsPOM.clearText();
		// Enter Product Name
		productsPOM.enterProductName(sProductName);
		// Click on Filter
		productsPOM.clickonFilterButton();
		int ipage = 1;
		// Verify results of product by Product Name
		boolean isRecordPresent = productsPOM.recordExists();
		while (isRecordPresent) {
			screenShot.captureScreenShot("UFM_041_Product_FilterByProductNamePage" + ipage);
			productsPOM.verResofCurPageFilteredByProductName(sProductName);
			isRecordPresent = productsPOM.verifyifResultsinNextPage();
			ipage++;
		}

		System.out.println("*************************************");
		System.out.println("Verify Results based on Product Price");
		System.out.println("Expected Product Price:" + sProductPrice);
		// Clear all the search criteria
		productsPOM.clearText();
		// Enter Product Price
		productsPOM.enterProductPrice(sProductPrice);
		// Click on Filter
		productsPOM.clickonFilterButton();
		ipage = 1;
		// Verify results of product by Product Name
		isRecordPresent = productsPOM.recordExists();
		while (isRecordPresent) {
			screenShot.captureScreenShot("UFM_041_Product_FilterByProductPricePage" + ipage);
			productsPOM.verResofCurPageFilteredByProductPrice(sProductPrice);
			isRecordPresent = productsPOM.verifyifResultsinNextPage();
			ipage++;
		}

		System.out.println("*************************************");
		System.out.println("Verify Results based on Product Model");
		System.out.println("Expected Product Model:" + sProductModel);
		// Clear all the search criteria
		productsPOM.clearText();
		// Enter Product Model
		productsPOM.enterProductModel(sProductModel);
		// Click on Filter
		productsPOM.clickonFilterButton();
		ipage = 1;
		// Verify results of product by Product Model
		isRecordPresent = productsPOM.recordExists();
		while (isRecordPresent) {
			screenShot.captureScreenShot("UFM_041_Product_FilterByProductModelPage" + ipage);
			productsPOM.verResofCurPageFilteredByProductModel(sProductModel);
			isRecordPresent = productsPOM.verifyifResultsinNextPage();
			ipage++;
		}

		System.out.println("*************************************");
		System.out.println("Verify Results based on Product Status");
		System.out.println("Expected Product Status:" + sProductStatus);
		// Clear all the search criteria
		productsPOM.clearText();
		// Enter Product Model
		productsPOM.selectProductStatus(sProductStatus);
		// Click on Filter
		productsPOM.clickonFilterButton();
		ipage = 1;
		// Verify results of product by Product Status
		isRecordPresent = productsPOM.recordExists();
		while (isRecordPresent) {
			screenShot.captureScreenShot("UFM_041_Product_FilterByProductStatusPage" + ipage);
			productsPOM.verResofCurPageFilteredByProductStatus(sProductStatus);
			isRecordPresent = productsPOM.verifyifResultsinNextPage();
			ipage++;
		}

		System.out.println("*************************************");
		System.out.println("Verify Results based on Product Quantity");
		System.out.println("Expected Product Quantity:" + sProductQuantity);
		// Clear all the search criteria
		productsPOM.clearText();
		// Enter Product Quantity
		productsPOM.enterProductQuantity(sProductQuantity);
		// Click on Filter
		productsPOM.clickonFilterButton();
		ipage = 1;
		// Verify results of product by Product Quantity
		isRecordPresent = productsPOM.recordExists();
		while (isRecordPresent) {
			screenShot.captureScreenShot("UFM_041_Product_FilterByProductQuantityPage" + ipage);
			productsPOM.verResofCurPageFilteredByProductQuantity(sProductQuantity);
			isRecordPresent = productsPOM.verifyifResultsinNextPage();
			ipage++;
		}

	}

}
