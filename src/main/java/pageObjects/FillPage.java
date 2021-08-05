package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class FillPage {
	
public WebDriver driver;

	String s = "";
	
	By cancelPopup = By.xpath("//button[@title='Cerrar']");
	
	By calen = By.xpath("//table/tbody/tr[2]/td[@data-value='2021-09-09']");
	
	By button = By.xpath("//button[@title='Mes siguiente']");
	
	By fill = By.xpath("//input[contains(@id,'input')]");
	
	By submit = By.xpath("//button[@name='SaveEdit']");
	
	By text = By.xpath("//textarea");
	
	By errorMessage = By.xpath("//div[@class='genericNotification']/strong");
	
	By errorEmpleado = By.xpath("//div[contains(@id,'help-message')]");
	
	By fillEdit = By.xpath("//label[contains(@for,'input')]");
	
	public FillPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getCancelPop()
	{
		return driver.findElement(cancelPopup);
	}
	
	public List<WebElement> getFills()
	{
		return driver.findElements(fill);
	}
	
	public List<WebElement> getEdits()
	{
		return driver.findElements(fillEdit);
	}
	
	public List<WebElement> getTexts()

	{
		return driver.findElements(text);
	}
	
	public WebElement getSubmit()
	{
		return driver.findElement(submit);
	}
	
	public WebElement getError()
		{
			return driver.findElement(errorMessage);
		}
	
	public WebElement getCal()
	{
		return driver.findElement(calen);
	}
	
	public WebElement getBut()
	{
		return driver.findElement(button);
	}
	
	public WebElement getErrorEmpleado()
	{
		return driver.findElement(errorEmpleado);
	}
}
