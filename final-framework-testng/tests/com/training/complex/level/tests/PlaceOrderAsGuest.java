package com.training.complex.level.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.training.generics.ScreenShot;
import com.training.pom.CategoriesPOM;
import com.training.pom.LoginPOM;
import com.training.pom.OrdersPOM;
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import org.testng.annotations.Test;

public class PlaceOrderAsGuest {
	private WebDriver driver;
	// private String baseUrl;
	private String adminUrl;
	private String guestUrl;
	private LoginPOM loginPOM;
	private ProductsPOM productsPOM;
	private OrdersPOM ordersPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	public String expectedMessageText = "Success: You have modified products!";
	public String editTitle = "Edit Product";
	public String Quantity = "45";

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		productsPOM = new ProductsPOM(driver);
		ordersPOM = new OrdersPOM(driver);
		extent = new ExtentReports("./TestReports/EditProduct.html");
		adminUrl = properties.getProperty("adminURL");
		guestUrl = properties.getProperty("retailGuestURL");
		// screenShot = new ScreenShot(driver);
		// open the browser

	}

	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
		extent.flush();
		extent.close();
	}

	@Test
	public void placeOrder() throws InterruptedException {

		extentTest = extent.startTest("Login to Application");
		driver.get(guestUrl);		
		ordersPOM.searchForProduct();
		ordersPOM.clickProductQuickSearch();
		ordersPOM.addProductToCart();
		Thread.sleep(12000);
		ordersPOM.viewCartandCheckOut();
		ordersPOM.guestCheckOut();
		ordersPOM.personalDetails();
		ordersPOM.addOrderComments();
		extent.endTest(extentTest);
		driver.get(adminUrl);
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		extentTest = extent.startTest("Navigation to Orders");
		String OrderPagetitle = ordersPOM.navigateToOrders();
		assertEquals("Orders", OrderPagetitle);
		extentTest.log(LogStatus.PASS, "Successfuly Navifgated to Orders page");
		extent.endTest(extentTest);
		

		
	}

}
