package EjercicioSaleforce1.A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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

import pageObjects.EditRecordPage;
import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte5 extends Base{
	
	WebDriver driver;
	
	@BeforeTest
	public void InitializeDriver() throws IOException {
		driver = initializeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "getData")
	public void SaleforceTest5(String Username, String Pass) throws IOException, InterruptedException {
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
		
		List<WebElement> le = afp.getEdits();
		
		List<WebElement> lf = afp.getFills();
		
		List<String> stringsPrevios = new ArrayList<String>();
		
		List<String> stringsNuevos = new ArrayList<String>();
		
		for(int x = 0; x<le.size(); x++)
		{
			if(le.get(x).getText().equals("Valoración") || le.get(x).getText().equals("Tipo") || le.get(x).getText().equals("Upsell Opportunity"))
			{
				for(int y = 0; y<lf.size(); y++)
				{
					if(le.get(x).getAttribute("for").equals(lf.get(y).getAttribute("id")))
					{
						String s = lf.get(y).getAttribute("id");
						
						pointElement(lf.get(y));
						
						JavascriptExecutor js = (JavascriptExecutor) driver;
				        js.executeScript("arguments[0].scrollIntoView();", lf.get(y));
						lf.get(y).click();
						
						stringsPrevios.add(driver.findElement(By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::div[1]/lightning-base-combobox-item[@aria-checked='true']")).getText());
						
						WebElement we = driver.findElement(By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::"
								+ "div[1]/lightning-base-combobox-item[3]"));
						
						stringsNuevos.add(we.getText());
						
						we.click();
					}
				}
			}
		}
		
		for(int x = 0; x<stringsNuevos.size(); x++)
		{
			Assert.assertNotEquals(stringsNuevos.get(x),stringsPrevios.get(x));
		}
		
		FillPage fp = new FillPage(driver);
		fp.getSubmit().click();
	}

	@AfterTest
	public void CloseDriver() {
		driver.close();
	}

	public void pointElement(WebElement we) {
		Actions a = new Actions(driver);

		a.moveToElement(we).build();

		a.perform();
	}

	@DataProvider
	public Object[][] getData() {
		Object[][] data = new Object[1][2];

		data[0][0] = "selenium@prueba.com";
		data[0][1] = "123456pb";

		return data;
	}
}
