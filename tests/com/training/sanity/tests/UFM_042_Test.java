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

public class UFM_042_Test {
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
	public void UFM_042_Login() {
		// Login to Uniform Site with Admin ID
		// Login to Uniform Site with Admin ID
		loginPOM.sendUserName(properties.getProperty("uniform.adminuser"));
		loginPOM.sendPassword(properties.getProperty("uniform.adminpwd"));
		loginPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_042_Login");
		String sCurrentURL = driver.getCurrentUrl();
		Assert.assertEquals(sCurrentURL.contains("dashboard"), true);
		System.out.println("Logged in to Uniform site");
	}

	@Test(dependsOnMethods = "UFM_042_Login")
	public void UFM_042_CatalogMenu() {
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
		screenShot.captureScreenShot("UFM_042_Dashboard_CatalogOpt");
		System.out.println("Expected List is present in the actual List of Catalog");

		// Click on Products
		dashboardPOM.clickonProducts();
	}

	@Test(dependsOnMethods = "UFM_042_CatalogMenu")
	public void UFM_042_AddProduct() {
		// Input Data
		String sProductName = "shirt";
		String sProductMetaTag = "Shirt for girls";
		String sProductPrice = "750.0000";
		String sProductModel = "SHG-010";
		String sProductStatus = "Enabled";
		String sProductQuantity = "50";
		String sProductCategories = "Shirt";
		String sExpectedMessage = "Success: You have modified products!";

		// Click on Add Product Icon
		productsPOM.clickonAddProduct();
		System.out.println("Active Tab: " + productsPOM.getActiveTabinAddProduct());
		// Enter Product Name
		productsPOM.addProduct_enterProductName(sProductName);
		// Enter Product Meta
		productsPOM.addProduct_enterProductMetaTitle(sProductMetaTag);
		screenShot.captureScreenShot("UFM_042_AddProducts_GeneralTab");

		// Click on Data Tab
		productsPOM.clickonaddProduct_DataTab();
		System.out.println("Active Tab: " + productsPOM.getActiveTabinAddProduct());
		// Enter Product Model
		productsPOM.addProduct_dataTab_enterProductModel(sProductModel);
		// Enter Product Price
		productsPOM.addProduct_dataTab_enterProductPrice(sProductPrice);
		// Enter Product Quantity
		productsPOM.addProduct_dataTab_enterProductQuantity(sProductQuantity);
		screenShot.captureScreenShot("UFM_042_AddProducts_DataTab");

		// Click on Links Tab
		productsPOM.clickonaddProduct_LinksTab();
		System.out.println("Active Tab: " + productsPOM.getActiveTabinAddProduct());
		// Select Categories
		productsPOM.addProduct_linksTab_selectProductCategory(sProductCategories);
		screenShot.captureScreenShot("UFM_042_AddProducts_LinksTab");

		// Click on Attribute Tab
		productsPOM.clickonaddProduct_AttributeTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_AttributeTab");

		// Click on Option Tab
		productsPOM.clickonaddProduct_OptionTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_OptionTab");

		// Click on Recurring Tab
		productsPOM.clickonaddProduct_RecurringTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_RecurringTab");

		// Click on Discount Tab
		productsPOM.clickonaddProduct_DiscountTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_DiscountTab");

		// Click on Special Tab
		productsPOM.clickonaddProduct_SpecialTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_SpecialTab");

		// Click on Image Tab
		productsPOM.clickonaddProduct_ImageTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_ImageTab");

		// Click on Rewarding Points Tab
		productsPOM.clickonaddProduct_RewardPointsTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_RewardingPointsTab");

		// Click on Design Tab
		productsPOM.clickonaddProduct_DesignTab();
		screenShot.captureScreenShot("UFM_042_AddProducts_DesignTab");

		// Click on Save product
		productsPOM.clickonSaveProduct();

		String sActualmessage = productsPOM.getMessage();
		System.out.println("Expected Message after Saving Product: " + sExpectedMessage);
		System.out.println("Actual Message after Saving Product: " + sActualmessage);
		Assert.assertTrue(sActualmessage.contains(sExpectedMessage), "Expected and Actual Message do no match");

	}

}
