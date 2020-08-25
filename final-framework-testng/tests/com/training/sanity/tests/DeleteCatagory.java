package com.training.sanity.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
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
import com.training.pom.CategoriesPOM;
import com.training.pom.LoginPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class DeleteCatagory {
	private WebDriver driver;
	//private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private CategoriesPOM categoriesPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	public String categoryName = "INDIAN";
	public String expectedAlertText = "Are you sure?";
	public String expectedMessageText = "Success: You have modified categories!";
	private static  ExtentReports extent;
	private static ExtentTest extentTest;
	

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);		
		extent = new ExtentReports("./TestReports/DeleteCategory.html");   // path where results are stored
        //extent = new ExtentReports(System.getProperty("./TestReports/DeleteCategory.html"));       
       //extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
        
	}

	@BeforeMethod
	public void setUp() throws Exception {
		
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver); 
		categoriesPOM = new CategoriesPOM(driver); 
		adminUrl = properties.getProperty("adminURL");		 
		screenShot = new ScreenShot(driver); 
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
	public void deleteCatagory() {
		extentTest = extent.startTest("Login to Application");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn(); 		
		extentTest.log(LogStatus.PASS, "Login Successful");
	    extent.endTest(extentTest);
		screenShot.captureScreenShot("First");
		
		extentTest = extent.startTest("Validation of Categories menu options");
		String categoryList = categoriesPOM.validateValuesUnderCatalog();
		assertTrue(categoryList.contains("Categories") && categoryList.contains("Products") && categoryList.contains("Recurring Profiles"));
		extentTest.log(LogStatus.PASS, "Categories Menu options appear as expected" );
        extent.endTest(extentTest);
        
        extentTest = extent.startTest("Validation of CategoriesDashboard Header columns");
		boolean categoryColumns =categoriesPOM.validateCategoryHeaderCloumns();
		assertTrue(categoryColumns);			
		extentTest.log(LogStatus.PASS, "Categories Dashboard has columns as expected" );
        extent.endTest(extentTest);
        
        extentTest = extent.startTest("Validation of Alert text on category deletion");
		String actualAlertText =categoriesPOM.deleteCategoryAndValidateAlert(categoryName);
		assertEquals(actualAlertText,expectedAlertText);
		extentTest.log(LogStatus.PASS, "Alert Text appear as expected on category deletion and alert text is : '" + actualAlertText + "'");
        extent.endTest(extentTest);
		
        extentTest = extent.startTest("Validation of banner message text on category deletion");
		String actualMessageText =categoriesPOM.CategoryDeletionMessageAlert();		
		assertTrue(actualMessageText.contains(expectedMessageText));	
		extentTest.log(LogStatus.PASS, "banner message appear as expected on category deletion and alert text is : '" + actualMessageText + "'");
        extent.endTest(extentTest);
		
	}
		
	}

