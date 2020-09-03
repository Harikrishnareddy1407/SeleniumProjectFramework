package com.training.complex.level.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.training.dataproviders.RetailDataProviders;
import com.training.generics.ScreenShot;
import com.training.pom.CategoriesPOM;
import com.training.pom.LoginPOM;
import com.training.pom.OrdersPOM;
import com.training.pom.ProductsPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

import org.testng.annotations.Test;

public class addCategoryAndProducts {
	private static WebDriver driver;
	// private String baseUrl;
	private static String adminUrl;
	private static String guestUrl;
	private static LoginPOM loginPOM;
	private static ProductsPOM productsPOM;
	private static OrdersPOM ordersPOM;
	private static CategoriesPOM categoriesPOM;
	private static Properties properties;
	private ScreenShot screenShot;
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	public String productAdditionMessageText = "Success: You have modified products!";
	public String categoryAdditionMessageText = "Success: You have modified categories!";
	public String editTitle = "Edit Product";
	public String Quantity = "45";

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		productsPOM = new ProductsPOM(driver);
		ordersPOM = new OrdersPOM(driver);
		categoriesPOM = new CategoriesPOM(driver);
		extent = new ExtentReports("./TestReports/AddCategoriesAndProducts.html");
		adminUrl = properties.getProperty("adminURL");
		guestUrl = properties.getProperty("retailGuestURL");
		driver.get(adminUrl);
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
	}

	@BeforeMethod
	public void setUp() throws Exception {

		// screenShot = new ScreenShot(driver);
		// open the browser

	}

	@AfterClass
	public void tearDown() throws Exception {
		Thread.sleep(1000);

		driver.quit();
		extent.flush();
		extent.close();
	}

	// To verify whether  application allows admin to create multiple category & add
	// product on the created category
	@Test(dataProvider = "excel-inputs", dataProviderClass = RetailDataProviders.class)
	public void addcategoryAndProducts(String cName, String metaTag, String metaTagDesc, String pName, String metaTitle,
			String model, String category) {
		extentTest = extent.startTest("Navigate to categories");
		String categoryTable = categoriesPOM.navigateToCategories();
		assertEquals(categoryTable, "Category List");
		extentTest.log(LogStatus.PASS, "Navigation to categories is  Successful ");
		extent.endTest(extentTest);

		extentTest = extent.startTest("Add New category");
		extentTest.log(LogStatus.INFO, " Adding new Category with category name as '" + cName + "' metaTag as '"
				+ metaTag + "' metaTagDescription as '" + metaTagDesc + "' ");
		String categoryAdditionMessage = categoriesPOM.addNewCategory(cName, metaTag, metaTagDesc);
		assertTrue(categoryAdditionMessage.contains(categoryAdditionMessageText));
		extentTest.log(LogStatus.PASS, "New category is created successfully with category name as '" + cName
				+ "' metaTag as '" + metaTag + "' metaTagDescription as '" + metaTagDesc + "' ");
		extent.endTest(extentTest);

		extentTest = extent.startTest("Add New product");
		extentTest.log(LogStatus.INFO, " Adding new product with product name as '" + pName + "' metatitle as '"
				+ metaTitle + "' model as '" + model + "' Category as '" + category + "' ");
		String productAdditionMessage = categoriesPOM.addNewProduct(pName, metaTitle, model, category);
		assertTrue(productAdditionMessage.contains(productAdditionMessageText));
		extentTest.log(LogStatus.PASS, "New product addition  is  Successful with product name as '" + pName
				+ "' metatitle as '" + metaTitle + "' model as '" + model + "' Category as '" + category + "' ");
		extent.endTest(extentTest);
		// categoriesPOM.addNewCategoryandProduct(cName,metaTag,metaTagDesc,pName,metaTitle,model,category);

	}

}