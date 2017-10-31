import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

//Running test cases in order of method names in ascending order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrangeHRM {
	
	
	  private static WebDriver driver;
	  private static String baseUrl;
	  private boolean acceptNextAlert = true;
	  private static StringBuffer verificationErrors = new StringBuffer();
	  private static OrangeHRM_WebElement webElement;
	  
	  @BeforeClass
		public static void setUpBeforeClass() throws Exception {
		  	// driver = new FirefoxDriver();
		  	
		  	// System.setProperty("webdriver.chrome.driver", "D:\\DVLP\\Selenium\\chromedriver.exe");
		  	// WebDriver driver = new ChromeDriver();
		  	
		  baseUrl = "http://squash:2565/OrangeHRM";
		  
		  try {
				driver = new RemoteWebDriver(new URL("http://10.64.1.84:4444/wd/hub"), DesiredCapabilities.firefox());
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(baseUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		  
		  
		  
		  
		    
		    
		    webElement = new OrangeHRM_WebElement(driver);
		    SeUtils.driver = driver;
		    SeUtils.webElement = new OrangeHRM_WebElement(driver);
		}

	  @Test
	  public void A_Login() throws Exception {
	    
		SeUtils.Login(baseUrl, "Admin", "acial!2014");
	    assertEquals("Welcome Admin", webElement.WelcomeMenu().getText());

	  }
	  
	  @Test
	  public void B_AddEmployee() throws Exception {
		  //SeUtils.AddEmployee ("Hassan", "IMHAH", "Français", "Célibataire", "Masculin", "1968-02-15");
		  //SeUtils.AddEmployee ("Pierre", "De Rauglaudre", "Français", "Marié", "Masculin", "1970-01-15");
		  //SeUtils.AddEmployee ("Vittorio", "Capellano", "Italien", "Célibataire", "Masculin", "1968-02-15");
		  //SeUtils.AddEmployee ("Aurélie", "Rondel", "Français", "Célibataire", "Féminin", "1968-02-15");
	  }
	  
	  
	  @Test
	  public void Z_Logout() throws Exception {
		  webElement.WelcomeMenu().click();
		  webElement.LogoutMenu().click();
		  
		  
		  

		  
		  
		  assertTrue(isElementPresent(By.id("txtUsername")));

	  }
	  @AfterClass
		public static void tearDownAfterClass() throws Exception {
		  driver.quit();
		    String verificationErrorString = verificationErrors.toString();
		    if (!"".equals(verificationErrorString)) {
		      fail(verificationErrorString);
		    }
		}
	  
	  
	  

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	  

}
