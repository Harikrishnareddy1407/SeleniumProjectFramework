package com.training.mediumlevel.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.pom.OrdersPOM;
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class ViewOrderAndGenerateInvoice {
	private WebDriver driver;
	//private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private OrdersPOM ordersPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static  ExtentReports extent;
	private static ExtentTest extentTest;
	public String expectedMessageText = "Success: You have modified products!";
	public  String pageHeader;
	public  String Quantity ="45";
	

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
		ordersPOM = new OrdersPOM(driver); 
		extent = new ExtentReports("./TestReports/generateInvoice.html");
		adminUrl = properties.getProperty("adminURL");		
		//screenShot = new ScreenShot(driver); 
		// open the browser 
		driver.get(adminUrl);
		extentTest = extent.startTest("Launch Browser");
		String pageTitle=driver.getTitle();
        extentTest.log(LogStatus.PASS, pageTitle);
		extent.endTest(extentTest);
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
		extent.flush();
        extent.close();
	}
	@Test
	public void generateInvoiceForOrder() {
		
		extentTest = extent.startTest("Login to Application");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn(); 
		extentTest.log(LogStatus.PASS, "Login Successful");
	    extent.endTest(extentTest);
		//screenShot.captureScreenShot("First");	
	    
	    extentTest = extent.startTest("Navigation to Orders");
	    String OrderPagetitle = ordersPOM.navigateToOrders();
	    assertEquals("Orders",OrderPagetitle);
	    extentTest.log(LogStatus.PASS, "Successfuly Navifgated to Orders page");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("view an order");
	    boolean viewPage = ordersPOM.ViewProduct();
	    assertTrue(viewPage);
	    extentTest.log(LogStatus.PASS, "viewed order Successfully");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("genertae invoice");
	    boolean invoice =  ordersPOM.generateInvoice();	    
	    assertTrue(invoice);
	    extentTest.log(LogStatus.PASS, "invoice generated Successfully");
	    extent.endTest(extentTest);		
	}
		
	}


