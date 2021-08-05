package EjercicioSaleforce1.A;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte2 extends Base {

	WebDriver driver;
	
    @BeforeTest
    public void InitializeDriver() throws IOException
    {
        driver = initializeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Test
	public void SaleforceTest2() throws IOException, InterruptedException {
		// Navigate to salesforce login page
		driver.get(prop.getProperty("url"));

		// Fill textboxes with correct credentials
		LoginPage lp = new LoginPage(driver);
		
		lp.getUsername().sendKeys(prop.getProperty("username"));
        
        lp.getPassword().sendKeys(prop.getProperty("pass"));

		// Clicks Login -- search for 'Services' and click it
		TabsPage tp = lp.getLogin();

		tp.getWaffle().click();
		tp.getServices().click();
		
		// Get all the tabs and select Accounts
		List<WebElement> tabs = tp.getFirstTab();
		int h = 0;
		for (int x = 0; x < tabs.size(); x++) {
			if (tabs.get(x).getText().contains("Cuentas")) {
				tabs.get(x).click();
				Thread.sleep(3000L);
				h = x;
			}
		}

		tp.getNuevo(tabs.get(h).getText()).click();

		Thread.sleep(3000L);

		FillPage afp = new FillPage(driver);

		List<WebElement> lista = afp.getFills();

		//Go inside the list of all the elements to fill
		
		for (int x = 2; x < lista.size(); x++) 
		{
			
			this.pointElement(lista.get(x));
			
			if (lista.get(x).getAttribute("role") != null) 
			{
				// Get all comboboxes in the list
				if (lista.get(x).getAttribute("role").equals("combobox")) 
				{
					String s = lista.get(x).getAttribute("id");
					
					if (lista.get(x).getAttribute("placeholder").equals("Buscar Cuentas...")) 
					{
						JavascriptExecutor js = (JavascriptExecutor) driver;
				        js.executeScript("arguments[0].scrollIntoView();", lista.get(x));
						lista.get(x).click();
						
						WebDriverWait wait = new WebDriverWait(driver, 3);

						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
								"//input[@id='" + s + "']/parent::div/following-sibling::" + "div[1]/ul/li[2]")));

						driver.findElement(By
								.xpath("//input[@id='" + s + "']/parent::div/following-sibling::" + "div[1]/ul/li[2]"))
								.click();
					} 
					else 
					{
						WebDriverWait wait = new WebDriverWait(driver, 3);
						
						if(wait.until(ExpectedConditions.elementToBeClickable(lista.get(x))) != null)
						{
						JavascriptExecutor js = (JavascriptExecutor) driver;
				        js.executeScript("arguments[0].scrollIntoView();", lista.get(x));
						lista.get(x).click();
						}
						
						if(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::"
								+ "div[1]/lightning-base-combobox-item[2]")))!= null)
						
						driver.findElement(By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::"
								+ "div[1]/lightning-base-combobox-item[2]")).click();
					}
				}
			} 
			
			else
			{
				//This is for the calendar, to not use sendKeys().
				if(lista.get(x).getAttribute("name").equals("SLAExpirationDate__c"))
				{
					WebDriverWait wait = new WebDriverWait(driver, 3);
					
					JavascriptExecutor js = (JavascriptExecutor) driver;
			        js.executeScript("arguments[0].scrollIntoView();", lista.get(x));
			        
			        lista.get(x).click();
			        
					if(wait.until(ExpectedConditions.elementToBeClickable(afp.getBut()))!= null)
					{
						afp.getBut().click();
						if(wait.until(ExpectedConditions.elementToBeClickable(afp.getCal()))!=null)
						afp.getCal().click();
					}
				}
				else
				{
				lista.get(x).sendKeys("asd");
				
				lista.get(x).sendKeys("25");
				}
			}
		}
		
		//For filling the camps that are outside the previous list
		List<WebElement> texts = afp.getTexts();
		
		for(int x=0; x<texts.size(); x++)
		{
			texts.get(x).sendKeys("desc.. asd");
		}
		
		afp.getSubmit().click();
	}

	@AfterTest
	public void CloseDriver() 
	{
		driver.close();
	}

	public void pointElement(WebElement we)
	{
		Actions a = new Actions(driver);
		
		a.moveToElement(we).build();
		
		a.perform();
	}
	
	@DataProvider
	public Object[][] getData() 
	{
		Object[][] data = new Object[1][2];

		data[0][0] = "selenium@prueba.com";
		data[0][1] = "123456pb";

		return data;
	}

}
