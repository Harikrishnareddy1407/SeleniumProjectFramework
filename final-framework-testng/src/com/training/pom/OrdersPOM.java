package com.training.pom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	
	@FindBy(id = "search_button")
	private WebElement searchIcon;
	
	@FindBy(id = "filter_keyword")
	private WebElement searchProduct;
	
	@FindBy(xpath = "//img[contains(@data-src,'ethnic')]")
	private WebElement productHover;
	
	
	@FindBy(xpath = "//div[@class='thumbnail']/div[2]/a")
	private WebElement productQuickView;
	
	@FindBy(xpath = "//iframe[contains(@src,'route=product')]")
	private WebElement AddtoCartFrame;
	
	
	@FindBy(id = "button-cart")
	private WebElement addToCart;	
	
	@FindBy(xpath = "//a[@class='close']")
	private WebElement closeAddToCartFrame;
	
	
	@FindBy(xpath = "//i[@class='tb_icon ico-linea-ecommerce-bag']")	
	private WebElement cartIcon;
	
	
	@FindBy(xpath = "//div[@class='checkout buttons']/a[2]")
	private WebElement checkOutOrder;
	
	
	@FindBy(xpath = "//div[@class='pull-right']/a")
	private WebElement checkOut;
	
	@FindBy(xpath = "//input[@value='guest']")
	private WebElement guestCheckOut;
	
	@FindBy(id = "button-account")
	private WebElement guestContinue;
	
	@FindBy(id = "input-payment-firstname")
	private WebElement firstName;
	
	@FindBy(id = "input-payment-lastname")
	private WebElement lastName;
	
	@FindBy(id = "input-payment-email")
	private WebElement emailAddress;	
	
	@FindBy(id = "input-payment-telephone")
	private WebElement telephone;
	
	@FindBy(id = "input-payment-address-1")
	private WebElement address1;
	
	@FindBy(id = "input-payment-city")
	private WebElement city;
	
	@FindBy(id = "input-payment-zone")
	private WebElement state;
	
	@FindBy(id = "button-guest")
	private WebElement personalDetalsContinue;
	
	@FindBy(xpath = "//textarea[@name='comment']")
	private WebElement CommentText;
	

	@FindBy(xpath = "//input[@name='agree']")
	private WebElement agreeTerms;
	
	@FindBy(id = "button-shipping-method")
	private WebElement addCommentsContinue;
	

	@FindBy(id = "button-payment-method")
	private WebElement paymentMethodContinue;
	
	
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
	
	public void searchForProduct()
	{
		this.searchIcon.click();
		this.searchProduct.sendKeys("nteger Vitae Iaculis Massa");
		this.searchIcon.click();
	}
	
	public void clickProductQuickSearch()
	{
	 Actions act = new Actions(driver);
	 act.moveToElement(this.productHover).build().perform();	 
	 this.productQuickView.click();	
		
	}
	
	public void addProductToCart()
	{
		driver.switchTo().frame(this.AddtoCartFrame);
		this.addToCart.click();
		driver.switchTo().defaultContent();
		this.closeAddToCartFrame.click();
		
	}
	
	public void viewCartandCheckOut()
	{
		 Actions act = new Actions(driver);
		 act.moveToElement(this.cartIcon).build().perform();		
		this.checkOutOrder.click();	
		driver.findElement(By.id("content")).click();		
		//this.checkOut.click();
		
		
	}

	public void guestCheckOut()
	{
		
		this.guestCheckOut.click();
		this.guestContinue.click();			
		
	}
	
	public void personalDetails()
	{
		this.firstName.sendKeys("Krishna");
		this.lastName.sendKeys("reddy");
		this.emailAddress.sendKeys("harikrishnareddy88@gmail.com");
		this.telephone.sendKeys("112233443322");
		this.address1.sendKeys("Street1");
		this.city.sendKeys("Bangalore");
		Select option = new Select(this.state);
		option.selectByVisibleText("Karnataka");
		this.personalDetalsContinue.click();	
						
		
	}
	
	public void addOrderComments()
	{
		this.CommentText.sendKeys("Wish to buy this product");
		this.addCommentsContinue.click();		
		this.agreeTerms.click();
		this.paymentMethodContinue.click();
		
	}


}
