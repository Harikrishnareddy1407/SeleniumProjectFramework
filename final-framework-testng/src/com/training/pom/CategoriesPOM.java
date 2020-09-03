package com.training.pom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoriesPOM {
	private WebDriver driver;

	public CategoriesPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='parent']/i[@class='fa fa-tags fw']")	
	private WebElement catalog;

	@FindBy(xpath = "//li[@id='menu-catalog']/ul")
	private WebElement catalogMenu;

	@FindBy(xpath = "//a[contains(@href,'category')]")
	private WebElement categorylink;

	@FindBy(xpath = "//a[text()='Category Name']")
	private WebElement catogoryName;

	@FindBy(xpath = "//input[@id='input-name1']")
	private WebElement inputCatogoryName;
	
	

	@FindBy(xpath = "//input[@id='input-meta-title1']")
	private WebElement inputMetatagtitle;
	
	@FindBy(xpath = "//textarea[@id='input-meta-description1']")
	private WebElement inputMetatagDescription;
	
	@FindBy(xpath = "//a[text()='Data']")
	private WebElement dataTab;
	
	@FindBy(xpath = "//a[text()='Links']")
	private WebElement linksTab;
	
	@FindBy(xpath = "//a[text()='Sort Order']")
	private WebElement sortorder;

	@FindBy(xpath = "//td[text()='Action']")
	private WebElement action;

	@FindBy(xpath = "//button[@type='button']")
	private WebElement deleteButton;
	
	@FindBy(xpath = "//div[@class='container-fluid']/h1")
	private WebElement dashboardTitle;

	@FindBy(xpath = "//div[contains(text(),' Success: You have modified categories!')]")
	private WebElement DeletionMessage;
	
	@FindBy(xpath = "//div[contains(text(),' Success: You have modified categories!')]")
	private WebElement categoryAdditionMessage;		       
	
	@FindBy(xpath = "//div[contains(text(),' Success: You have modified products!')]")
	private WebElement productAdditionMessage;
	
	@FindBy(xpath = "//a[@data-original-title='Add New']")
	private WebElement addCategory;
	
	@FindBy(xpath = "//li[@id='menu-catalog']/ul/li[2]/a")
	private WebElement product;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement save;
	
	@FindBy(xpath = "//input[@id='input-model']")
	private WebElement model;
	
	@FindBy(xpath = "//input[@id='input-category']")
	private WebElement categories;
	
	@FindBy(xpath = "//h3[@class='panel-title']")
	private WebElement categoriesTable;
	
	
	


	public String validateValuesUnderCatalog() {

		Actions act = new Actions(driver);
		act.moveToElement(this.catalog).build().perform();
		List<WebElement> catalogValues = this.catalogMenu.findElements(By.tagName("li"));
		int logSize = catalogValues.size();
		String logValues = "";
		for (int i = 0; i < logSize; i++) {
			logValues = logValues + " ; " + catalogValues.get(i).getText();
		}
		return logValues;
	}

	public boolean validateCategoryHeaderCloumns() {
		this.categorylink.click();
		Boolean catagoryColumn = this.catogoryName.isDisplayed();
		Boolean sortColumn = this.sortorder.isDisplayed();
		Boolean actioncolumn = this.action.isDisplayed();
		if (catagoryColumn && sortColumn && actioncolumn) {
			return true;
		} else {
			return false;
		}

	}

	public String getlandingPagetitle()
	{
		return this.dashboardTitle.getText();
	}
	public String deleteCategoryAndValidateAlert(String categoryName) {
		driver.findElement(By.xpath("//td[(text()='" + categoryName + "')]/parent::*//input")).click();
		this.deleteButton.click();
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	public String CategoryDeletionMessageAlert() {
		String Messagetext = this.DeletionMessage.getText();
		return Messagetext;
	}	
	
	
	public String navigateToCategories()
	{
		Actions act = new Actions(driver);
		act.moveToElement(this.catalog).build().perform();
		this.categorylink.click();
		return this.categoriesTable.getText();
		
		
	}
	
	public void navigateToProducts()
	{
		Actions act = new Actions(driver);
		act.moveToElement(this.catalog).build().perform();
		this.product.click();
		
	}
	
	
	public String addNewCategory(String cName, String metaTag, String metaTagDesc)
	{
		this.addCategory.click();
		this.inputCatogoryName.sendKeys(cName);
		this.inputMetatagtitle.sendKeys(metaTag);
		this.inputMetatagDescription.sendKeys(metaTagDesc);
		this.save.click();
		String Messagetext = this.categoryAdditionMessage.getText();
		return Messagetext;
		
	}
	
	public String addNewProduct(String pName, String metaTitle,String model,String category)
	{
		this.navigateToProducts();
		this.addCategory.click();
		this.inputCatogoryName.sendKeys(pName);
		this.inputMetatagtitle.sendKeys(metaTitle);
		this.dataTab.click();
		this.model.sendKeys(model);
		this.linksTab.click();
		this.categories.sendKeys(category);
		this.save.click();
		String Messagetext = this.productAdditionMessage.getText();
		return Messagetext;
		
	}
	
	public void addNewCategoryandProduct(String cName, String metaTag, String metaTagDesc, String pName, String metaTitle,String model,String category)
	{
		this.addCategory.click();
		this.inputCatogoryName.sendKeys(cName);
		this.inputMetatagtitle.sendKeys(metaTag);
		this.inputMetatagDescription.sendKeys(metaTagDesc);
		this.save.click();
		this.navigateToProducts();
		this.addCategory.click();
		this.inputCatogoryName.sendKeys(pName);
		this.inputMetatagtitle.sendKeys(metaTitle);
		this.dataTab.click();
		this.model.sendKeys(model);
		this.linksTab.click();
		this.categories.sendKeys(category);
		this.save.click();
		
	
	}

}
