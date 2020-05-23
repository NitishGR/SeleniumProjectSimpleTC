package com.training.regression.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.dataproviders.LoginDataProviders;
import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class LoginExcelTest2 {
	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	int run = 1;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod
	public void setUp() throws Exception {
//		driver = DriverFactory.getDriver(DriverNames.CHROME);
//		loginPOM = new LoginPOM(driver);
//		baseUrl = properties.getProperty("baseURL");
//		screenShot = new ScreenShot(driver);
//		// open the browser
//		//driver.get(baseUrl);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		run++;
		// driver.quit();
	}

	@Test(priority = 0)
	public void TestMethod1() {
		System.out.println("Running Test Method 1");
	}
	
	@Test(dependsOnMethods = "TestMethod1")
	public void TestMethod2() {
		System.out.println("Running Test Method 2");
	}
	
	@Test(dependsOnMethods = "TestMethod2",dataProvider = "excel-inputs2", dataProviderClass = LoginDataProviders.class)
	public void loginDBTest(String userName, String password) {
		System.out.println("Run:" + run);
		System.out.println("USer Name:" + userName + "::" + "Psasword:" + password);
		// screenShot.captureScreenShot(userName);
	}

	@Test(dependsOnMethods = "loginDBTest")
	public void TestMethod3() {
		System.out.println("Running Test Method 3");
	}
	
//	@Test(dataProvider = "excel-inputs3", dataProviderClass = LoginDataProviders.class)
//	public void loginDBTest2(String userName, String password,String name) {
////		loginPOM.sendUserName(userName);
////		loginPOM.sendPassword(password);
////		loginPOM.clickLoginBtn();
////		screenShot.captureScreenShot(userName);
//		System.out.println("Run:"+run);
//		System.out.println("USer Name:" + userName + "::" + "Psasword:" + password+"::"+name);
//	}

}