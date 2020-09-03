package com.training.sanity.tests;

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

public class FilterProduct {
	private WebDriver driver;
	//private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private ProductsPOM productsPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static  ExtentReports extent;
	private static ExtentTest extentTest;
	public String prodName = "Integer vitae iaculis massa";	
	public String prodPrice = "515";
	public  String pageHeader;
	

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
		extent = new ExtentReports("./TestReports/FilterProduct.html");
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
	public void deleteCatagory(){
		
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
	    
	    extentTest = extent.startTest("Filter a product by name and price");
	    productsPOM.filterProduct(prodName,prodPrice);
		Boolean productNameVisible = driver.findElement(By.xpath("//td[contains(text(),'" + prodName + "')]")).isDisplayed();
		Assert.assertTrue(productNameVisible);
		Boolean productPriceVisible = driver.findElement(By.xpath("//td[contains(text(),'" + prodPrice + "')]")).isDisplayed();
		Assert.assertTrue(productPriceVisible);
		extentTest.log(LogStatus.PASS, "Product with name '" + prodName + "' and price '" + prodPrice + "' is filtered successfully and visible");
	    extent.endTest(extentTest);
		
		
	}
		
	}


