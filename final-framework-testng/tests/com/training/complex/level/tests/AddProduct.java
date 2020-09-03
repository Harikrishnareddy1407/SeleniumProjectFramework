package com.training.complex.level.tests;

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
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class AddProduct {
	private WebDriver driver;
	// private String baseUrl;
	private String adminUrl;
	private LoginPOM loginPOM;
	private ProductsPOM productsPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	public String expectedMessageText = "Success: You have modified products!";
	public String editTitle = "Edit Product";
	public String prodName = "Finger Ring";
	public String metaTitle = "Finger Ring for ladies";
	public String modelName = "SKU-012";
	public String prodPrice = "500";
	public String Quantity = "50";
	public String categoryName = "Ear Rings";

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
		extent = new ExtentReports("./TestReports/AddProduct.html"); // path where results are stored
		// extent = new
		// ExtentReports(System.getProperty("./TestReports/DeleteCategory.html"));
		// extent.loadConfig(new
		// File(System.getProperty("user.dir")+"\\extent-config.xml"));

	}

	@BeforeMethod
	public void setUp() throws Exception {

		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		productsPOM = new ProductsPOM(driver);
		adminUrl = properties.getProperty("adminURL");
		
		screenShot = new ScreenShot(driver);
		// open the browser
		driver.get(adminUrl);
		extentTest = extent.startTest("Launch Browser");
		String pageTitle = driver.getTitle();
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
	public void addProduct() {
		extentTest = extent.startTest("Login to Application");
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		extentTest.log(LogStatus.PASS, "Login Successful");
		extent.endTest(extentTest);
		screenShot.captureScreenShot("First");

		extentTest = extent.startTest("Navigation to products");
		String pageHeader = productsPOM.navigateToProducts();
		assertEquals(pageHeader, "Products");
		extentTest.log(LogStatus.PASS,
				"Successfully navigated to Products Dashboard and page title is '" + pageHeader + "'");
		extent.endTest(extentTest);

		extentTest = extent.startTest("Click on Add new Product");
		extentTest.log(LogStatus.INFO, "click on Add Product button");
		String addProductPage = productsPOM.ClickAddProduct();
		assertEquals(addProductPage, "Add Product");
		extentTest.log(LogStatus.PASS,
				"Successfully navigated to add Products page and page title is '" + addProductPage + "'");
		extent.endTest(extentTest);

		extentTest = extent.startTest("enter details to Add new Product");
		extentTest.log(LogStatus.INFO, "key data Add Product ");
		productsPOM.enterProductGeneralDetails(prodName, metaTitle);
		productsPOM.enterProductDataTabDetails(modelName, prodPrice, Quantity);
		productsPOM.enterCategoriesTabDetails(categoryName);
		String actualMessageText = productsPOM.productUpdatedMessageAlert();
		assertTrue(actualMessageText.contains(expectedMessageText));
		extentTest.log(LogStatus.PASS, "Product updated message is displayed as expected and the message text  is '"
				+ actualMessageText + "'");
		extent.endTest(extentTest);

	}

}
