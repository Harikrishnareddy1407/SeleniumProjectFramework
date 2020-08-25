package com.training.pom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.testng.annotations.Test;

public class ProductsPOM {
	private WebDriver driver;

	public ProductsPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='parent']/i[@class='fa fa-tags fw']")
	private WebElement catalog;

	@FindBy(xpath = "//li[@id='menu-catalog']/ul/li[2]/a")
	private WebElement product;

	@FindBy(xpath = "//div[@class='page-header']/div/h1")
	private WebElement pageTitle;

	@FindBy(id = "input-name")
	private WebElement productName;

	@FindBy(id = "button-filter")
	private WebElement filterButton;

	@FindBy(id = "input-price")
	private WebElement productprice;

	@FindBy(xpath = "//button[@class='btn btn-danger']")
	private WebElement deleteButton;

	@FindBy(xpath = "//table[@class='table table-bordered table-hover']")
	private WebElement productTable;

	@FindBy(xpath = "//div[contains(text(),' Success: You have modified products!')]")
	private WebElement DeletionMessage;

	public String navigateToProducts() {

		Actions act = new Actions(driver);
		act.moveToElement(this.catalog).build().perform();
		this.product.click();
		String pageHeader = this.pageTitle.getText();
		return pageHeader;

	}

	public void filterProduct(String pName, String Pprice) {
		this.productName.sendKeys(pName);
		this.filterButton.click();
		this.productprice.sendKeys(Pprice);
		this.filterButton.click();
		assertTrue(this.productTable.isDisplayed());
	}

	public void filterProductAndDelete(String productName) {
		this.productName.sendKeys(productName);
		this.filterButton.click();
		assertTrue(this.productTable.isDisplayed());
		driver.findElement(By.xpath("//td[(text()='" + productName + "')]/parent::*//input")).click();
		this.deleteButton.click();
		
	}

	public String deleteProductAndValidateAlert() {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println("Deletion Alert text is : " + alertText);
		alert.accept();
		return alertText;

	}

	public String productDeletionMessageAlert() {

		String Messagetext = this.DeletionMessage.getText();
		return Messagetext;
	}
	

}
