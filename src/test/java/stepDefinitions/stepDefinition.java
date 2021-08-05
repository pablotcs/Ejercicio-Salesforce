package stepDefinitions;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import pageObjects.EditRecordPage;
import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

@RunWith(Cucumber.class)

public class stepDefinition extends Base{
	
	static List<WebElement> tabs;
	static List<WebElement> lista;
	static List<WebElement> texts;
	static List<WebElement> le;
	static List<WebElement> lf;
	static List<String> stringsPrevios;
	static List<String> stringsNuevos;
	static ArrayList<String> a;
	static FileInputStream fis;
	static XSSFWorkbook workbook;
	static int sheets;
	static TabsPage tp;
	static FillPage afp;
	static EditRecordPage erp;
	static int h;
	WebDriver driver;
	WebDriverWait w;
	
	@Given("Initialize the browser with chrome")
    public void initialize_the_browser_with_chrome() throws Throwable {
    	driver = initializeDriver();
    }

    @When("^User enters username and password and logs in$")
    public void user_enters_and_and_logs_in() throws Throwable {
    	
    	LoginPage lg = new LoginPage(driver);
    	
    	lg.getUsername().sendKeys(prop.getProperty("username"));
        
        lg.getPassword().sendKeys(prop.getProperty("pass"));
		
		lg.getLogin();
    }

    @Then("^Verify that user is succesfully logged in$")
    public void verify_that_user_is_succesfully_logged_in() throws Throwable {
        tp = new TabsPage(driver);
        
        WebDriverWait w = new WebDriverWait(driver, 3);
        
        w.until(ExpectedConditions.elementToBeClickable(tp.getWaffle()));
        
        Assert.assertTrue(ExpectedConditions.elementToBeSelected(tp.getWaffle())!= null);
    }

    @And("^Navigate to \"([^\"]*)\" site$")
    public void navigate_to_something_site(String strArg1) throws Throwable {
    	driver.get(strArg1);
    }
    
    @And("^Click on waffle, and services$")
    public void click_on_waffle_and_services() throws Throwable {
    	tp = new TabsPage(driver);
    	
    	tp.getWaffle().click();
    	
    	tp.getServices().click();
    }

    @Then("^Click on each tab, and press new and cancel if it has$")
    public void click_on_each_tab_and_press_new_and_cancel_if_it_has() throws Throwable {
    	for(int x=0; x<tabs.size(); x++)
    	{
    		WebDriverWait w = new WebDriverWait(driver, 3);
    		w.until(ExpectedConditions.elementToBeClickable(tabs.get(x)));
    		tabs.get(x).click();
    		Thread.sleep(3000L);
    		
    		if(tp.getNuevo(tabs.get(x).getText())!= null)
    		{	
    			tp.getNuevo(tabs.get(x).getText()).click();
    			
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

    @And("^Get all tabs in a list$")
    public void get_all_tabs_in_a_list() throws Throwable {
    	tabs = tp.getFirstTab();
    }
    
    @Given("Enter accounts tab")
    public void enter_accounts_tab() throws Throwable {

    	tabs = tp.getFirstTab();
		for (int x = 0; x < tabs.size(); x++) {
			if (tabs.get(x).getText().contains("Cuentas")) {
				tabs.get(x).click();
				Thread.sleep(3000L);
				h = x;
			}
		}
		afp = new FillPage(driver);
    }
    
    @And("^Close Driver$")
    public void close_driver() throws Throwable {
    	driver.close();
    }

    @When("User fills all the elements")
    public void user_fills_all_the_elements() throws Throwable {
    	for (int x = 2; x < lista.size(); x++) 
		{
    		Actions a = new Actions(driver);
    		
    		a.moveToElement(lista.get(x)).build();
    		
    		a.perform();
			
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
						wait.until(ExpectedConditions.elementToBeClickable(afp.getCal()));
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
    	for(int x=0; x<texts.size(); x++)
		{
			texts.get(x).sendKeys("desc.. asd");
		}
    }

    @Then("Submit the creation")
    public void submit_the_creation() throws Throwable {
    	afp.getSubmit().click();
    }

    @And("^Click on new$")
    public void click_on_new() throws Throwable {
    	tabs = tp.getFirstTab();
    	tp.getNuevo(tabs.get(h).getText()).click();
    }

    @And("^Get all the elements on a list$")
    public void get_all_the_elements_on_a_list() throws Throwable {
    	afp = new FillPage(driver);
    	
    	WebDriverWait w = new WebDriverWait(driver, 3);
    	w.until(ExpectedConditions.elementToBeClickable(afp.getSubmit()));
    	lista = afp.getFills();
    	texts = afp.getTexts();
    }

    @When("^Submit the failing creation$")
    public void submit_the_failing_creation() throws Throwable {
    	afp.getSubmit().click();
    }

    @Then("^Verify if the error is correct$")
    public void verify_if_the_error_is_correct() throws Throwable {
    	Assert.assertTrue(afp.getError().getText().equals("Revise los siguientes campos"));
    }
    
    @And("^Go back to previous tab$")
    public void go_back_to_previous_tab() throws Throwable {
    	Set<String> tabsManager = driver.getWindowHandles();

		Iterator<String> it = tabsManager.iterator();

		while (it.hasNext()) {
			driver.switchTo().window(it.next());
		}
    }

    @Given("^Enter Contacts tab in a new browsers tab$")
    public void enter_contacts_tab_in_a_new_browsers_tab() throws Throwable {
    	tabs = tp.getFirstTab();
		h = 0;
		for (int x = 0; x < tabs.size(); x++) {
			if (tabs.get(x).getText().contains("Contactos")) {

				String newtab = Keys.chord(Keys.CONTROL, Keys.ENTER);

				tp.getSpecificTab(x + 1).sendKeys(newtab);

				Set<String> tabsManager = driver.getWindowHandles();

				Iterator<String> it = tabsManager.iterator();

				while (it.hasNext()) {
					driver.switchTo().window(it.next());
				}

				Thread.sleep(3000L);

				h = x;

				break;
			}
		}
    }

    @When("^User fills all the contact elements$")
    public void user_fills_all_the_contact_elements() throws Throwable {
    		
    		
    	
    		for (int x = 2; x < lista.size(); x++) {
    		
    		Actions a = new Actions(driver);
    		
    		a.moveToElement(lista.get(x)).build();
    		
    		a.perform();
			if (lista.get(x).getAttribute("role") != null) {
				// Get all comboboxes in the list
				if (lista.get(x).getAttribute("role").equals("combobox")) {
					String s = lista.get(x).getAttribute("id");

					if (lista.get(x).getAttribute("placeholder").equals("Buscar Cuentas...")
							|| lista.get(x).getAttribute("placeholder").equals("Buscar Contactos...")) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].scrollIntoView();", lista.get(x));
						lista.get(x).click();

						WebDriverWait wait = new WebDriverWait(driver, 3);

						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
								"//input[@id='" + s + "']/parent::div/following-sibling::" + "div[1]/ul/li[2]")));

						driver.findElement(
								By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::div[1]/ul/li[2]"))
								.click();
					}

					else {
						WebDriverWait wait = new WebDriverWait(driver, 3);

						if (wait.until(ExpectedConditions.elementToBeClickable(lista.get(x))) != null) {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("arguments[0].scrollIntoView();", lista.get(x));
							lista.get(x).click();
						}

						if (wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//input[@id='" + s + "']/parent::div/following-sibling::"
										+ "div[1]/lightning-base-combobox-item[2]"))) != null)

							driver.findElement(By.xpath("//input[@id='" + s
									+ "']/parent::div/following-sibling::div[1]/lightning-base-combobox-item[2]"))
									.click();
					}
				}
			}

			else {
				// This is for the calendar, to not use sendKeys().
				if (lista.get(x).getAttribute("name").equals("Birthdate")) {
					WebDriverWait wait = new WebDriverWait(driver, 3);

					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView();", lista.get(x));

					lista.get(x).click();

					if (wait.until(ExpectedConditions.elementToBeClickable(afp.getBut())) != null) {
						afp.getBut().click();
						if (wait.until(ExpectedConditions.elementToBeClickable(afp.getCal())) != null)
							afp.getCal().click();
					}

				} else {

					if (lista.get(x).getAttribute("name").equals("Email")) {
						lista.get(x).sendKeys("asd123@asd.com");
					} else {
						lista.get(x).sendKeys("asd");

						lista.get(x).sendKeys("25");
					}
				}

			}
    	}
    }	

    @Then("^Get labels and elements to edit in lists$")
    public void get_labels_and_elements_to_edit_in_lists() throws Throwable {
    	WebDriverWait w = new WebDriverWait(driver, 3);
    	
    	w.until(ExpectedConditions.elementToBeClickable(afp.getSubmit()));
    	
    	le = afp.getEdits();
		
		lf = afp.getFills();
		
		stringsPrevios = new ArrayList<String>();
		
		stringsNuevos = new ArrayList<String>();
    }

    @Then("^Compare previous and new selected values$")
    public void compare_previous_and_new_selected_values() throws Throwable {
    	for(int x = 0; x<stringsNuevos.size(); x++)
		{
			Assert.assertNotEquals(stringsNuevos.get(x),stringsPrevios.get(x));
		}
    }

    @And("^Click on the record to edit$")
    public void click_on_the_record_to_edit() throws Throwable {
    	erp = new EditRecordPage(driver);
		
    	afp = new FillPage(driver);
		
		erp.getRecord().click();
    }

    @And("^Click on the arrow for more options$")
    public void click_on_the_arrow_for_more_options() throws Throwable {
    	Thread.sleep(5000L);
    	erp.getMas().click();
    }

    @And("^Click on Edit button$")
    public void click_on_edit_button() throws Throwable {
    	WebDriverWait w = new WebDriverWait(driver,3);
    	
    	if(w.until(ExpectedConditions.elementToBeClickable(erp.getEdit()))!=null) 
		{
			erp.getEdit().click();
		}
    }

    @And("^Edit those elements$")
    public void edit_those_elements() throws Throwable {
    	for(int x = 0; x<le.size(); x++)
		{
			if(le.get(x).getText().equals("Valoración") || le.get(x).getText().equals("Tipo") || le.get(x).getText().equals("Upsell Opportunity"))
			{
				for(int y = 0; y<lf.size(); y++)
				{
					if(le.get(x).getAttribute("for").equals(lf.get(y).getAttribute("id")))
					{
						String s = lf.get(y).getAttribute("id");
						
						Actions a = new Actions(driver);

						a.moveToElement(lf.get(y)).build();

						a.perform();
						
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
    }		
    
    @And("^Edit Empleados textbox$")
    public void edit_empleados_textbox() throws Throwable {
    	for(int x = 0; x<le.size(); x++)
		{
	    	if(le.get(x).getText().equals("Empleados"))
			{
	    		
	    		Actions a = new Actions(driver);
	    		
	    		a.moveToElement(le.get(x)).build();
	    		
	    		a.perform();
				for(int y = 0; y<lf.size(); y++)
				{
					//Get the element under the label
					if(le.get(x).getAttribute("for").equals(lf.get(y).getAttribute("id")))
					{
						String s = lf.get(y).getAttribute("id");
						
						
						
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
    }

    @And("^Check the error$")
    public void check_the_error() throws Throwable {
    	Assert.assertTrue(afp.getErrorEmpleado().getText().equals("Empleados: valor fuera del rango válido en campo numérico: 1431655766"));
    }
    
    @Then("^Create the different accounts$")
    public void create_the_different_accounts() throws Throwable {
    	
    	int c2 = 0;
    	for(int i=0;i<sheets;i++)
		{
			if(workbook.getSheetName(i).equalsIgnoreCase("Hoja1"))
			{
			XSSFSheet sheet=workbook.getSheetAt(i);
			//Identify Testcases coloum by scanning the entire 1st row
		
			Iterator<Row>  rows= sheet.iterator();// sheet is collection of rows
			
			Row firstrow= rows.next();
			
			Iterator<Cell> ce=firstrow.cellIterator();//row is collection of cells
			
			int k=0;
			int coloumn = 0;
			
			while(ce.hasNext())
			{
				Cell value=ce.next();
		
				if(value.getStringCellValue().equalsIgnoreCase("NombreCuenta"))
				{
					coloumn=k;
				}
			
				k++;
			}
		
			////once coloumn is identified then scan entire testcase coloum to identify purcjhase testcase row
			while(rows.hasNext())
			{
		
				Row r = rows.next();
		
				if(r.getCell(coloumn).getStringCellValue().equalsIgnoreCase("Cuenta"+r.getRowNum()))
					{
		
			////after you grab purchase testcase row = pull all the data of that row and feed into test
		
					Iterator<Cell>  cv=r.cellIterator();
			
						while(cv.hasNext())
						{
							
							Cell c= cv.next();
			
							if(c.getCellType() == Cell.CELL_TYPE_STRING)
							{
								a.add(c.getStringCellValue());
							}
							else
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
						}
						
						FillPage afp = new FillPage(driver);
						
						w = new WebDriverWait(driver, 4);
						
						tabs = tp.getFirstTab();
						
						w.until(ExpectedConditions.urlContains("Recent"));
						
						tp.getNuevo(tabs.get(h).getText()).click();
						
						w.until(ExpectedConditions.elementToBeClickable(afp.getSubmit()));
						
						lista = afp.getFills();
						
						for (int x = 2; x < lista.size(); x++) 
						{
							if (lista.get(x).getAttribute("role") == null) 
							{
								lista.get(x).sendKeys(a.get(c2));
								
								if(a.get(c2).contains("www"))
								{
									c2++;
									break;
								}
								
								c2++;
							}
						}
						
						afp.getSubmit().click();
						
						w.until(ExpectedConditions.elementToBeClickable(afp.getCancelPop()));
						
						afp.getCancelPop().click();
						
						w.until(ExpectedConditions.elementToBeClickable(tabs.get(h)));
						
						tabs.get(h).click();
					}
				}
			}
		}
    }

    @And("^Reach the Excel document$")
    public void reach_the_excel_document() throws Throwable {
    	a=new ArrayList<String>();
    	
		fis=new FileInputStream("C://Users//Pablo//Desktop//testData.xlsx");
		
		workbook=new XSSFWorkbook(fis);
	
		sheets=workbook.getNumberOfSheets();
    }
}
