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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.EditRecordPage;
import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte6 extends Base{
	
	WebDriver driver;

	@BeforeTest
	public void InitializeDriver() throws IOException, InterruptedException {
		driver = initializeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "getData")
	public void SaleforceTest6(String Username, String Pass) throws IOException, InterruptedException {
		// Navigate to salesforce login page
		driver.get(prop.getProperty("url"));

		// Fill textboxes with correct credentials
		LoginPage lp = new LoginPage(driver);
		lp.getUsername().sendKeys(Username);
		lp.getPassword().sendKeys(Pass);

		// Clicks Login -- search for 'Services' and click it
		TabsPage tp = lp.getLogin();

		tp.getWaffle().click();
		tp.getServices().click();
		
		// Get all the tabs and select Accounts
		List<WebElement> tabs = tp.getFirstTab();
		for (int x = 0; x < tabs.size(); x++) {
			if (tabs.get(x).getText().contains("Cuentas")) {
				tabs.get(x).click();
				Thread.sleep(3000L);
			}
		}
		
		EditRecordPage erp = new EditRecordPage(driver);
		
		erp.getRecord().click();
		
		Thread.sleep(3000L);
		
		WebDriverWait w = new WebDriverWait(driver,2);
		
		erp.getMas().click();
		
		if(w.until(ExpectedConditions.elementToBeClickable(erp.getEdit()))!=null) 
		{
			erp.getEdit().click();
		}
		
		FillPage afp = new FillPage(driver);
		
		w.until(ExpectedConditions.elementToBeClickable(afp.getSubmit()));
		
		//Get all de labels of the page
		List<WebElement> le = afp.getEdits();
		//Get all the elements fillable in the page
		List<WebElement> lf = afp.getFills();
		
		for(int x = 0; x<le.size(); x++)
		{
			//Check the "Empleados" label
			if(le.get(x).getText().equals("Empleados"))
			{
				for(int y = 0; y<lf.size(); y++)
				{
					//Get the element under the label
					if(le.get(x).getAttribute("for").equals(lf.get(y).getAttribute("id")))
					{
						String s = lf.get(y).getAttribute("id");
						
						pointElement(lf.get(y));
						
						JavascriptExecutor js = (JavascriptExecutor) driver;
				        js.executeScript("arguments[0].scrollIntoView();", lf.get(y));
						lf.get(y).click();
						
						WebElement we = driver.findElement(By.xpath("//input[@id='" + s + "']"));
						
						we.clear();
						
						we.sendKeys("1431655766");
					}
				}
			}
		}
		
		afp.getSubmit().click();
		
		//Check if the error is the correct one.. 
		Assert.assertTrue(afp.getErrorEmpleado().getText().equals("Empleados: valor fuera del rango válido en campo numérico: 1431655766"));
		
		System.out.println("El error fue el esperado");
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
