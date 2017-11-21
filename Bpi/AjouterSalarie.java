package formation.selenium.bpi2017;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AjouterSalarie {
  private static WebDriver driver;
  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private static StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public static void setUp() throws Exception {
    // Pour supprimer le message : "Chrome est contrôlé par un logiciel de test automatisé"
	ChromeOptions options = new ChromeOptions();
	options.addArguments("disable-infobars");
	options.addArguments("--start-minimized");
    driver = new ChromeDriver(options);
	
	
    baseUrl = "http://opensource.demo.orangehrmlive.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/");
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.id("txtUsername"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.id("txtUsername")).clear();
    driver.findElement(By.id("txtUsername")).sendKeys("admin");
    driver.findElement(By.id("txtPassword")).clear();
    driver.findElement(By.id("txtPassword")).sendKeys("admin");
    driver.findElement(By.id("btnLogin")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if ("Welcome Admin".equals(driver.findElement(By.id("welcome")).getText())) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

  }

  @Test
  public void a_AjouterSalarie() throws Exception {
  }
  
  @Test
  public void b_SupprimerSalarie() throws Exception {
  }
  
  @Test
  public void c_Navigation() throws Exception {
    
    driver.findElement(By.xpath("//a[@id='menu_admin_viewAdminModule']/b")).click();
    driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")).click();
    driver.findElement(By.cssSelector("#menu_leave_viewLeaveModule > b")).click();
    driver.findElement(By.cssSelector("#menu_time_viewTimeModule > b")).click();

    
  }

  @AfterClass
  public static void tearDown() throws Exception {

	    driver.findElement(By.id("welcome")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if (isElementPresent(By.xpath("//a[contains(@href, 'auth/logout')]"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.xpath("//a[contains(@href, 'auth/logout')]")).click();

	    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private static boolean isElementPresent(By by) {
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
