package EjercicioSaleforce1.A;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte3 extends Base {
	
	WebDriver driver;

	@BeforeTest
	public void InitializeDriver() throws IOException {
		driver = initializeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "getData")
	public void SaleforceTest3(String Username, String Pass) throws IOException, InterruptedException 
	{
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

		FillPage afp = new FillPage(driver);
		//Try to submit without filling the 
		afp.getSubmit().click();
		
		Assert.assertTrue(afp.getError().getText().equals("Revise los siguientes campos"));
	}
	
	@AfterTest
	public void CloseDriver() 
	{
		driver.close();
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
