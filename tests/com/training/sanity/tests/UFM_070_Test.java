package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import com.training.pom.UserAccountPOM;
import com.training.pom.UserCheckoutPOM;
import com.training.pom.UserLoginUniformPOM;
import com.training.pom.UserOrderHistoryPOM;
import com.training.pom.UserProductDetailsPOM;
import com.training.pom.UserUniformHomePOM;
import com.training.pom.AdmimOrdersPOM;
import com.training.pom.AdmimProductnPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;
import com.trianing.waits.WaitTypes;

public class UFM_070_Test {
	private WebDriver driver;
	private WebDriverWait wait;

	// private String baseUrl;
	// private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private WaitTypes waitT;

	private AdminLoginUniformPOM loginPOM;
	private AdminDashboardPOM dashboardPOM;
	private AdminCategoriesPOM categoriesPOM;
	private AdmimProductnPOM productsPOM;
	private UserLoginUniformPOM loginUserPOM;
	private UserAccountPOM userAccountPOM;
	private UserUniformHomePOM userUniformHomePOM;
	private UserProductDetailsPOM userProductInfoPOM;
	private UserCheckoutPOM userCheckoutPOM;
	private AdmimOrdersPOM adminOrderPOM;
	private UserOrderHistoryPOM userOrderHistoryPOM;

	private String sUserName;
	private String sdate;
	private String sProductName;
	private String sChestSize;
	private String sOrderId;
	private String sAdminProductName;
	private String sAdminProductQuantity;
	private String sOrderStatus;

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
		productsPOM = new AdmimProductnPOM(driver, wait);
		loginUserPOM = new UserLoginUniformPOM(driver, wait);
		userAccountPOM = new UserAccountPOM(driver, wait);
		userUniformHomePOM = new UserUniformHomePOM(driver, wait);
		userProductInfoPOM = new UserProductDetailsPOM(driver, wait);
		userCheckoutPOM = new UserCheckoutPOM(driver, wait);
		adminOrderPOM = new AdmimOrdersPOM(driver, wait);
		userOrderHistoryPOM = new UserOrderHistoryPOM(driver, wait);

		screenShot = new ScreenShot(driver);
		waitT = new WaitTypes(driver);

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
	public void UFM_070_UserAddProducttoCart() {
		// INPUT DATA
		sProductName = "REGULAR T-SHIRTS (Rust)";
		sChestSize = "34";

		// open the browser
		driver.get(properties.getProperty("uniform.user.baseURL"));
		waitT.waitForPageToBeReady();

		// To screenshot of UniformStore HomePage
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_UniformStoreHome");

		// View Product
		userUniformHomePOM.viewProduct(sProductName);
		screenShot.captureScreenShot("UFM_070_UniformStoreProducts");
		userUniformHomePOM.clickonProduct();

		// AddProduct to Cart
		waitT.waitForPageToBeReady();
		boolean isOptionPresent = userProductInfoPOM.selectChestSize(sChestSize);
		Assert.assertTrue(isOptionPresent, "Product with Chest Size " + sChestSize + "\" is not available");
		screenShot.captureScreenShot("UFM_070_ProductDetails");
		userProductInfoPOM.addProductToCart();
		waitT.waitForPageToBeReady();
	}

	@Test(dependsOnMethods = "UFM_070_UserAddProducttoCart")
	public void UFM_070_UserPurchaseProduct() {
		// Click on Cart to Checkout
		userUniformHomePOM.clickonCart();
		userUniformHomePOM.clickonCheckout();
		waitT.waitForPageToBeReady();

		// Checkout Options
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.sendUserName(properties.getProperty("uniform.user"));
		userCheckoutPOM.sendPassword(properties.getProperty("uniform.pwd"));
		userCheckoutPOM.clickLoginBtn();

		// Billing Address
		waitT.waitForPageToBeReady();
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.clickonContinePaymentAddr();

		// Shipping Address
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.clickonContineShippingAddr();

		// Shipping Method
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.clickonContineShippingMethod();

		// Payment Method
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.checkTermsandCond();
		userCheckoutPOM.clickonContinePaymentMethod();

		// Confirm Order
		System.out.println("Active Section in Checkout Page:" + userCheckoutPOM.getActiveSectioninCheckout());
		userCheckoutPOM.clickonConfirmOrder();
		wait.until(ExpectedConditions.urlContains("success"));
		screenShot.captureScreenShot("UFM_070_ConfirmOrder");
		System.out.println("Current URL:" + driver.getCurrentUrl());
		System.out.println("Title :" + driver.getTitle());
		Assert.assertTrue(driver.getCurrentUrl().contains("success"), "Order is not placed or not successful");

		// Get Username
		sUserName = userUniformHomePOM.getUserName();

		// Get Current Date
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		sdate = df.format(cal.getTime());

	}

	@Test(dependsOnMethods = "UFM_070_UserPurchaseProduct")
	public void UFM_070_UserLogout() {
		// Logout as User
		loginUserPOM.clickonMyAccount();
		loginUserPOM.clickoMyAccountLogout();
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_UserLogout");

	}

	@Test(dependsOnMethods = "UFM_070_UserLogout")
	public void UFM_070_AdminLogin() {
		// Login to Uniform Site with Admin ID
		driver.get(properties.getProperty("baseURL"));
		loginPOM.sendUserName(properties.getProperty("uniform.adminuser"));
		loginPOM.sendPassword(properties.getProperty("uniform.adminpwd"));
		loginPOM.clickLoginBtn();
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_Login");
		String sCurrentURL = driver.getCurrentUrl();
		Assert.assertEquals(sCurrentURL.contains("dashboard"), true);
		System.out.println("Logged in to Uniform site as Admin");
	}

	@Test(dependsOnMethods = "UFM_070_AdminLogin")
	public void UFM_070_AdminViewOrders() {
		// Click on Menu
		dashboardPOM.clickonMenu();

		// Click on Sales
		dashboardPOM.clickonSales();

		// Get list of Options under Sales
		ArrayList<String> actualSalesOpt = new ArrayList<String>();
		actualSalesOpt = dashboardPOM.getSalesOptions();
		System.out.println("Actual Catalog Options: " + actualSalesOpt);

		// Expected options under Sales
		ArrayList<String> expectedSalesOpt = new ArrayList<String>();

		String expectedSales = "Orders|Recurring Profiles|Returns|Gift Vouchers";
		for (String opt : expectedSales.split("\\|")) {
			actualSalesOpt.add(opt);
		}
		System.out.println("Expected Catalog Options: " + actualSalesOpt);

		// Compare - Expected vs Actual Catalog options
		Assert.assertEquals(actualSalesOpt.containsAll(expectedSalesOpt), true);
		screenShot.captureScreenShot("UFM_070_Dashboard_SalesOpt");

		// Click on Categories
		dashboardPOM.clickonOrders();
		waitT.waitForPageToBeReady();
	}

	@Test(dependsOnMethods = "UFM_070_AdminViewOrders")
	public void UFM_070_searchOrder() {
		// INPUT DATA FROM UFM_070_UserPurchaseProduct Test
		System.out.println("Product Ordered by User: " + sUserName);
		System.out.println("Product Ordered on: " + sdate);

		// Verify the if the order is present
		boolean isOrderExists = false;
		while (!isOrderExists) {
			isOrderExists = adminOrderPOM.verifyOrderExists(sUserName, sdate);
			if (isOrderExists) {
				screenShot.captureScreenShot("UFM_070_Order");
			} else
				Assert.assertEquals(adminOrderPOM.verifyifResultsinNextPage(), true,
						"Order placed by user is not present in the Admin Order results");
		}

		sOrderId = adminOrderPOM.getOrderId(sUserName, sdate);
	}

	@Test(dependsOnMethods = "UFM_070_searchOrder")
	public void UFM_070_viewOrder() {
		sAdminProductName = "SPORTS T-SHIRTS";
		sAdminProductQuantity = "1";
		sOrderStatus = "Complete";

		// Click on View Order
		adminOrderPOM.clickonViewOrder(sUserName, sdate);
		waitT.waitForPageToBeReady();

		// Select Order Status as Complete

		Assert.assertEquals(adminOrderPOM.selectProductStatus(sOrderStatus), true,
				"Product Order Status:" + sOrderStatus + " is not present");

		// Click on Add History
		adminOrderPOM.clickonAddProductHistory();

		// Verify Messages on Save
		String sMessage = adminOrderPOM.getEditMessage();
		System.out.println(sMessage);
		screenShot.captureScreenShot("UFM_070_AdminSaveOrderStatus");
		Assert.assertTrue(sMessage.contains("Success:"), "Order modified by Admin is not successful");

	}

	@Test(dependsOnMethods = "UFM_070_viewOrder")
	public void UFM_070_LogoutAdmin() {
		// click on logout
		dashboardPOM.clickonLogout();
		wait.until(ExpectedConditions.urlContains("login"));
	}

	@Test(dependsOnMethods = "UFM_070_LogoutAdmin")
	public void UFM_070_UserLogin() {
		// Login to Uniform Site with User ID
		// open the browser
		driver.get(properties.getProperty("uniform.user.baseURL"));
		waitT.waitForPageToBeReady();
		loginUserPOM.clickonMyAccount();
		loginUserPOM.clickoMyAccountLogin();
		loginUserPOM.sendUserName(properties.getProperty("uniform.user"));
		loginUserPOM.sendPassword(properties.getProperty("uniform.pwd"));
		loginUserPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_070_UserLogin_2");
		System.out.println("URL :" + driver.getCurrentUrl());

		waitT.waitForPageToBeReady();
		System.out.println("Logged in to Uniform site as User");

		// Click on View your Order History
		userAccountPOM.clickonViewOrderHistory();
		waitT.waitForPageToBeReady();
	}

	@Test(dependsOnMethods = "UFM_070_UserLogin")
	public void UFM_070_verifyOrderStatus() {
		// Input Data
		System.out.println("Order ID :" + sOrderId);
		System.out.println("Expected Order Status:" + sOrderStatus);

		// Get Order Status
		String sActualOrderStatus = userOrderHistoryPOM.getOrderStatus(sOrderId);
		System.out.println("Actual Order Status :" + sActualOrderStatus);
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_UserOrderHistory");
		Assert.assertEquals(sActualOrderStatus, sOrderStatus, "Actual and Expected Order Status do not match");

	}
}
