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
import org.testng.annotations.Test;

public class OrdersPOM {
	private WebDriver driver;

	public OrdersPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//i[@class='fa fa-shopping-cart fw']")
	private WebElement salesOption;

	@FindBy(xpath = "//*[@id='menu-sale']/ul/li[1]/a")
	private WebElement orderOption;

	@FindBy(xpath = "//table/tbody/tr[1]/td[8]/a[1]")
	private WebElement viewProduct;

	@FindBy(id = "button-invoice")
	private WebElement genrateInvoiceButton;

	@FindBy(xpath = "//h3[text()=' Options']")
	private WebElement viewpageObject;

	@FindBy(xpath = "//td[contains(text(),'INV')]")
	private WebElement invoiceNumber;

	public String navigateToOrders() {
		Actions act = new Actions(driver);
		act.moveToElement(this.salesOption).build().perform();
		this.orderOption.click();
		String pageTitle = driver.getTitle();
		return pageTitle;
	}

	public boolean ViewProduct() {
		this.viewProduct.click();
		if (this.viewpageObject.isDisplayed()) {
			return true;
		} else
			return false;
	}

	public boolean generateInvoice() {
		this.genrateInvoiceButton.click();
		if (this.invoiceNumber.isDisplayed()) {
			return true;
		} else
			return false;
	}

}
