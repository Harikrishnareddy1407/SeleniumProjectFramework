package com.training.mediumlevel.tests;

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
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class EditProduct {
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
	public String editTitle ="Edit Product";
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
		productsPOM = new ProductsPOM(driver); 
		extent = new ExtentReports("./TestReports/EditProduct.html");
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
	public void editProduct() {
		
		extentTest = extent.startTest("Login to Application");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn(); 
		extentTest.log(LogStatus.PASS, "Login Successful");
	    extent.endTest(extentTest);
		//screenShot.captureScreenShot("First");	
	    
	    extentTest = extent.startTest("Navigation to products");
		String pageHeader = productsPOM.navigateToProducts();
		assertEquals(pageHeader, "Products");
		extentTest.log(LogStatus.PASS, "Successfully navigated to Products Dashboard and page title is '" +  pageHeader + "'");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("Click to edit products");
	    String editPageTitle = productsPOM.editproduct();	
	    assertEquals(editPageTitle,editTitle);
	    extentTest.log(LogStatus.PASS, "Successfully navigated to edit Product page and page title is '" +  editPageTitle + "'");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("Click to Data tab");
	    Boolean clikedDataTab = productsPOM.MoveToDataTab();
	    assertTrue(clikedDataTab);
	    extentTest.log(LogStatus.PASS, "Successfully clicked on Data Tab");
	    extent.endTest(extentTest);
	    
	    extentTest = extent.startTest("Update Quantity value,save the record and validate the success message");
	    productsPOM.updateQuantityAndSave(Quantity);
	    extentTest.log(LogStatus.PASS, "Successfully updated quantity value in data tab and saved the record");
	    String actualMessageText =productsPOM.productUpdatedMessageAlert();		
		assertTrue(actualMessageText.contains(expectedMessageText)); 
		extentTest.log(LogStatus.PASS, "Product updated message is displayed as expected and the message text  is '" +  actualMessageText + "'");
	    extent.endTest(extentTest);
	    
	    //validate updated value appear for the product
	    extentTest = extent.startTest("Validate updated Quantity value is displayed in the products table");
	    String UpdatedQuantity = productsPOM.updatedProductQuanityValue();
	    assertEquals(UpdatedQuantity,Quantity);
	    extentTest.log(LogStatus.PASS, "Product Quantiy Value is updated and the updated quantity value is  '" +  UpdatedQuantity + "'");
	    extent.endTest(extentTest);	   	
	    
		
	}
		
	}


