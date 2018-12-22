package fr.acial.exercices;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class exercice4{
  private WebDriver driver;
  private String baseUrl = "https://www.universitedutest.com/OrangeHRM";
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  public void loginLogout() throws InterruptedException  {

	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get(baseUrl);
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (isElementPresent(By.id("logInPanelHeading"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.id("txtUsername")).clear();
	    driver.findElement(By.id("txtUsername")).sendKeys("admin");
	    driver.findElement(By.id("txtPassword")).clear();
	    driver.findElement(By.id("txtPassword")).sendKeys("Paris$2018");
	    driver.findElement(By.id("btnLogin")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (isElementPresent(By.id("welcome"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    assertEquals(driver.findElement(By.id("welcome")).getText(), "Welcome Admin");
	    assertTrue(isElementPresent(By.id("menu_admin_viewAdminModule")));
	    driver.findElement(By.id("welcome")).click();
	    

	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (isElementPresent(By.xpath("//div[@id='welcome-menu']/ul/li[3]/a"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    driver.findElement(By.xpath("//div[@id='welcome-menu']/ul/li[3]/a")).click();
	    assertTrue(isElementPresent(By.id("txtUsername")));
	  	driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
  }
  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	  
  }

  // @Test
  public void chromeTest() throws Exception {
	  // http://chromedriver.chromium.org/downloads
	  	System.setProperty("webdriver.chrome.driver", "D:\\Formation\\drivers\\chromedriver.exe");
	  	driver = new ChromeDriver();

	  	loginLogout();

  }

  // @Test
  public void firefoxTest() throws Exception {
	  	//	https://github.com/mozilla/geckodriver/releases
	  
	  	System.setProperty("webdriver.gecko.driver", "D:\\Formation\\drivers\\geckodriver.exe");
	  	driver = new FirefoxDriver();

	  	loginLogout();

  }

  // @Test
  public void ieTest() throws Exception {
	  	//	https://www.seleniumhq.org/download/
	  
	  	System.setProperty("webdriver.ie.driver", "D:\\Formation\\drivers\\IEDriverServer.exe");
	  	// FAILED: ieTest
	  	// org.openqa.selenium.SessionNotCreatedException: Unexpected error launching Internet Explorer. 
	  	// Protected Mode settings are not the same for all zones. 
	  	// Enable Protected Mode must be set to the same value (enabled or disabled) for all zones.
	  	// driver = new InternetExplorerDriver();
	  	
	  	DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
	  	caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

	  	// Initialize InternetExplorerDriver Instance using new capability.
	  	driver = new InternetExplorerDriver(caps);
	  	

	  	loginLogout();
  }
  // @Test
  public void remoteTest() throws Exception {
	  // https://www.seleniumhq.org/download/
	  // run java -jar selenium-server-standalone-3.141.59.jar
	  driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
	  loginLogout();
	  
  }
  
  @Test
  public void gridTest() throws Exception {
	  // https://www.seleniumhq.org/download/
	  // run java -jar selenium-server-standalone-3.141.59.jar -role hub 
	  // java  -jar selenium-server-standalone-3.141.59.jar -role webdriver -hub http://localhost:4444/grid/register -port 5566
	  // http://localhost:4444/grid/console

	  driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
	  loginLogout();
	  
  }
  
  
  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
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
