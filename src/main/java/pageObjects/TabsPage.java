package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class TabsPage {
	
	public WebDriver driver;
	
	By waffle = By.cssSelector("div[class='slds-icon-waffle']");
	
	By services = By.xpath("//p[text()='Servicio']");
	
	By tab = By.xpath("//nav/div/one-app-nav-bar-item-root");
	
	By nuevo = By.cssSelector("div[title*='Nuevo']");
	
	By nuevoInfo = By.cssSelector("div[title='Nuevo informe']");
	
	By nuevoPanel = By.cssSelector("div[title='Nuevo panel']");
	
	By cancelar = By.xpath("//button[text()='Cancelar']");
	
	By cancelar2 = By.xpath("//button[@title='Cancelar']");
	
	
	
	public TabsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getWaffle()
	{
		return driver.findElement(waffle);
	}
	
	public WebElement getCancelar()
	{
		if(driver.findElements(cancelar).size()>0)
		{
			return driver.findElement(cancelar);
		}
		else
			return driver.findElement(cancelar2);
	}
	
	public WebElement getNuevo(String str)
	{
		WebElement we = null;
		
		if(driver.findElements(nuevo).size()>0)
		{
			if(str.contains("Informes"))
			{
				we = driver.findElement(nuevoInfo);
			}
			else if(str.contains("Paneles"))
			{
				we = driver.findElement(nuevoPanel);
			}
			else
			we = driver.findElement(nuevo);
		}
		return we;
	}
	
	public WebElement getServices()
	{
		return driver.findElement(services);
	}
	
	public List<WebElement> getFirstTab()
	{
		List<WebElement> lista = null;
		lista = driver.findElements(tab);
		return lista;
	}
	
	public WebElement getSpecificTab(int a)
	{
		return driver.findElement(By.xpath("//nav/div/one-app-nav-bar-item-root[" +a+"]/a"));
	}
	
	
	

}
