package EjercicioSaleforce1.A;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte1 extends Base{

	WebDriver driver;
	
    @BeforeTest
    public void InitializeDriver() throws IOException
    {
        driver = initializeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Test
    public void SaleforceTest1() throws IOException, InterruptedException
    {
    	// Navigate to salesforce login page
    	driver.get(prop.getProperty("url"));
    	
    	// Fill textboxes with correct credentials
        LoginPage lp = new LoginPage(driver);
        
        lp.getUsername().sendKeys(prop.getProperty("username"));
        
        lp.getPassword().sendKeys(prop.getProperty("pass"));
        
        //Clicks Login -- search for 'Services' and click it
        TabsPage tp = lp.getLogin();
    	
        tp.getWaffle().click();
    	tp.getServices().click();
    	
    	List<WebElement> tabs = tp.getFirstTab();
    	
    	for(int x=0; x<tabs.size(); x++)
    	{
    		Thread.sleep(3000L);
    		tabs.get(x).click();
    		Thread.sleep(3000L);
    		
    		if(tp.getNuevo(tabs.get(x).getText())!= null)
    		{	
    			tp.getNuevo(tabs.get(x).getText()).click();
    			
    			Thread.sleep(3000L);
    			
    			if(x==5)
    			{
    				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Generador de informes']")));
    				
    				tp.getCancelar().click();
    				
    				driver.switchTo().parentFrame();
    			}
    			
    			else if(x==6)
    			{
    				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='dashboard']")));
    				
    				tp.getCancelar().click();
    				
    				driver.switchTo().parentFrame();
    			}
    			
    			else
    			{
    				tp.getCancelar().click();
    			}
    		}
    		
    	}
    }
    
    
    
    @AfterTest
    public void CloseDriver()
    {
    	//driver.close();
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
