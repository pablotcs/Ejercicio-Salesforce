package EjercicioSaleforce1.A;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.FillPage;
import pageObjects.LoginPage;
import pageObjects.TabsPage;
import resources.Base;

public class TestParte7 extends Base{

	static List<WebElement> lista;
	WebDriver driver;
	WebDriverWait w;
	int c = 0;
	
    @BeforeTest
    public void InitializeDriver() throws IOException
    {
        driver = initializeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Test
    public void SaleforceTest7() throws IOException, InterruptedException
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
		int h = 0;
		for (int x = 0; x < tabs.size(); x++) {
			if (tabs.get(x).getText().contains("Cuentas")) {
				tabs.get(x).click();
				
				h = x;
			}
		}

    	ArrayList<String> a=new ArrayList<String>();
    	
		FileInputStream fis=new FileInputStream("C://Users//Pablo//Desktop//testData.xlsx");
		
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
	
		int sheets=workbook.getNumberOfSheets();
		
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
								lista.get(x).sendKeys(a.get(c));
								
								if(a.get(c).contains("www"))
								{
									c++;
									break;
								}
								
								c++;
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
    
    @AfterTest
    public void CloseDriver()
    {
    	driver.close();
    }
    

}
