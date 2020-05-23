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

import com.training.dataproviders.LoginDataProviders;
import com.training.dataproviders.UniformDataProviders;
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

public class UFM_071_Test {
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

	@Test(dataProvider = "return-product", dataProviderClass = UniformDataProviders.class)
	public void loginDBTest(String sOrderNo, String sReturnReasonOption, String isProductOpened,
			String sReturnReasonComment) {
		// INPUT DATA TO ORDER PRODUCT
		sProductName = "REGULAR T-SHIRTS (Rust)";
		sChestSize = "34";

		// INPUT DATA FROM EXCEL
		System.out.println("Input data from Excel");
		System.out.println("Order Number:" + sOrderNo);
		System.out.println("Return Reason:" + sReturnReasonOption);
		System.out.println("Is Product Opened:" + isProductOpened);
		System.out.println("Return Reason Commnet:" + sReturnReasonComment);

		// Login to Uniform Site with User ID
		// open the browser
		System.out.println("Open Uniform Site");
		driver.get(properties.getProperty("uniform.user.baseURL"));
		waitT.waitForPageToBeReady();

		// Click on MyAccount
		System.out.println("Enter Crendentials");
		loginUserPOM.clickonMyAccount();
		loginUserPOM.clickoMyAccountLogin();
		// Enter UserName
		loginUserPOM.sendUserName(properties.getProperty("uniform.user"));
		// Enter Password
		loginUserPOM.sendPassword(properties.getProperty("uniform.pwd"));
		// Click on Login Button
		loginUserPOM.clickLoginBtn();
		screenShot.captureScreenShot("UFM_071_UserLogin_Order" + sOrderNo);
		System.out.println("URL :" + driver.getCurrentUrl());
		waitT.waitForPageToBeReady();
		System.out.println("Logged in to Uniform site as User");

		// Get Username
		sUserName = userUniformHomePOM.getUserName();

		// Click on Uniform Store
		userAccountPOM.clickoUniformStore();
		waitT.waitForPageToBeReady();

		// To screenshot of UniformStore HomePage
		screenShot.captureScreenShot("UFM_071_UniformStoreHome_Order" + sOrderNo);

		// View Product
		userUniformHomePOM.viewProduct(sProductName);
		screenShot.captureScreenShot("UFM_071_UniformStoreProducts_Order" + sOrderNo);
		userUniformHomePOM.clickonProduct();

		// AddProduct to Cart
		System.out.println("Add Product to Cart");
		waitT.waitForPageToBeReady();
		boolean isOptionPresent = userProductInfoPOM.selectChestSize(sChestSize);
		Assert.assertTrue(isOptionPresent, "Product with Chest Size " + sChestSize + "\" is not available");
		screenShot.captureScreenShot("UFM_071_ProductDetails_Order" + sOrderNo);
		userProductInfoPOM.addProductToCart();
		waitT.waitForPageToBeReady();

		// Click on Cart to Checkout
		userUniformHomePOM.clickonCart();
		userUniformHomePOM.clickonCheckout();

		// Billing Address
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
		waitT.waitForPageToBeReady();
		System.out.println("Order " + sProductName + " Placed");
		screenShot.captureScreenShot("UFM_071_ConfirmOrder_Order" + sOrderNo);
		System.out.println("Current URL:" + driver.getCurrentUrl());
		System.out.println("Title :" + driver.getTitle());
		Assert.assertTrue(driver.getCurrentUrl().contains("success"), "Order is not placed or not successful");

		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		sdate = df.format(cal.getTime());

		// Click on My Account
		userUniformHomePOM.clickonMyAccount();
		// Click on Order History
		userUniformHomePOM.clickoMyAccountOrderHistory();
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_UserOrderHistory_Order" + sOrderNo);

		// Click on View Order
		System.out.println("View Order History");
		Assert.assertEquals(userOrderHistoryPOM.clickOnViewOrder(sdate), true, "No Products ordered on " + sdate);
		waitT.waitForPageToBeReady();

		// Click on Return Product
		System.out.println("Return Product");
		Assert.assertEquals(userOrderHistoryPOM.clickOnReturnProduct(sProductName), true,
				"Product Ordered " + sProductName + " is not present in the order list");
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_070_UserReturnProductPage_Order" + sOrderNo);

		// Select Product Return
		userOrderHistoryPOM.selectReturnReason(sReturnReasonOption);
		// Select Product Return
		userOrderHistoryPOM.selectisProductOpened(isProductOpened);
		// Enter Product Return Commnets
		userOrderHistoryPOM.returnReasonComment(sReturnReasonComment);
		// Select Agree to Terms and Conditions
		userOrderHistoryPOM.selectAgreetoReturnProduct();
		screenShot.captureScreenShot("UFM_071_ReturnProductt_Order" + sOrderNo);
		// Click on Submit
		userOrderHistoryPOM.clickonSubmitButton();
		System.out.println("Current URL:" + driver.getCurrentUrl());
		System.out.println("Title :" + driver.getTitle());
		Assert.assertTrue(driver.getCurrentUrl().contains("success"), "Product is not returned");
		System.out.println("Product Returned");

		// Get Message
		String expectedMessage1 = "Thank you for submitting your return request";
		String expectedMessage2 = "You will be notified via e-mail as to the status of your request";
		System.out.println("Expected Message 1:" + expectedMessage1);
		System.out.println("Expected Message 2:" + expectedMessage2);

		String actualMessage1 = userOrderHistoryPOM.getMessage1();
		String actualMessage2 = userOrderHistoryPOM.getMessage2();
		System.out.println("Actual Message 1:" + actualMessage1);
		System.out.println("Actual Message 2:" + actualMessage2);
		screenShot.captureScreenShot("UFM_071_ReturnProductMessage_Order" + sOrderNo);

		Assert.assertTrue(actualMessage1.contains(expectedMessage1), "Actual and Expected Message 1 is different");
		Assert.assertTrue(actualMessage2.contains(expectedMessage2), "Actual and Expected Message 2 is different");

		// Logout as User
		System.out.println("Logout as User");
		loginUserPOM.clickonMyAccount();
		loginUserPOM.clickoMyAccountLogout();
		waitT.waitForPageToBeReady();
		screenShot.captureScreenShot("UFM_071_UserLogout_Order" + sOrderNo);
		waitT.waitForPageToBeReady();

	}
}
