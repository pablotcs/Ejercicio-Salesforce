package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class EditRecordPage {
	
	WebDriver driver;
	
	By record = By.xpath("//tbody/tr[1]/th/span/a");
	
	By detalles = By.xpath("//a[text()='Detalles']");
	
	By rollDown = By.xpath("//div[@class='slds-form-element__control']");
	
	By mas = By.xpath("//button[@class='slds-button slds-button_icon-border-filled']/lightning-primitive-icon");
	
	By edit = By.xpath("//span[text()='Modificar']");
	
	public EditRecordPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getRecord()
	{
		return driver.findElement(record);
	}
	
	public WebElement getDetalles()
	{
		return driver.findElement(detalles);
	}
	
	public List<WebElement> getRollDown()
	{
		return driver.findElements(rollDown);
	}
	
	public WebElement getMas()
	{
		return driver.findElement(mas);
	}
	
	public WebElement getEdit()
	{
		return driver.findElement(edit);
	}
	
	
	

}
