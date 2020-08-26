package com.training.mediumlevel.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class DeleteMultipleProducts {
	private WebDriver driver;
	//private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private ProductsPOM productsPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static  ExtentReports extent;
	private static ExtentTest extentTest;	
	public String expectedMessageText = "Success: You have modified products!";	
	public String expectedAlertText = "Are you sure?";
	public String product1= "Aprod1";
	public String product2 ="Aprod2";

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
		adminUrl = properties.getProperty("adminURL");
		extent = new ExtentReports("./TestReports/MultipleproductDeletion.html");
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
	public void deleteMultipleProducts()  {
		
		extentTest = extent.startTest("Login to Application");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn(); 
		extentTest.log(LogStatus.PASS, "Login Successful");
	    extent.endTest(extentTest);
		
	    extentTest = extent.startTest("Navigation to products");
		//productsPOM.navigateToProducts();	
		String pageHeader = productsPOM.navigateToProducts();
		assertEquals(pageHeader, "Products");
		extentTest.log(LogStatus.PASS, "Successfully navigated to Products Dashboard and page title is '" +  pageHeader + "'");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("select multiple products to delete and validate the deletion alert");	  
	    productsPOM.multipleProductDeletion();	    
	    String actualAlertText = productsPOM.deleteProductAndValidateAlert();
	    assertEquals(actualAlertText,expectedAlertText);
	    extentTest.log(LogStatus.PASS, "Deletion of product is suceesful and the alert text  is '" +  actualAlertText + "'");
		String actualMessageText =productsPOM.productDeletionMessageAlert();
		
		assertTrue(actualMessageText.contains(expectedMessageText)); 
	    extentTest.log(LogStatus.PASS, "Multiple Product deletion message is displayed as expected and the message text  is '" +  actualMessageText + "'");
	    extent.endTest(extentTest);	        
	    
		
	}
		
	}


