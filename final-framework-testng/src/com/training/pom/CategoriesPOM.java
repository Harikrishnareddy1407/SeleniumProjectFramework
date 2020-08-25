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

}
