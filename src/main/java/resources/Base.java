package resources;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Base {

	protected WebDriver driver;
	
	public Properties prop = new Properties();
	
	public WebDriver initializeDriver() throws IOException {

		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Pablo\\eclipse-workspace\\A\\src\\main\\java\\resources\\data.properties");
		
		

		prop.load(fis);
		
		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pablo\\Desktop\\Selenium\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			
            options.addArguments("--disable-notifications");
            
            driver = new ChromeDriver(options); //create chromedriver using as argument options created
		}

		else if (browserName == "firefox") {
			// code for firefox..
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return driver;
	}

}
