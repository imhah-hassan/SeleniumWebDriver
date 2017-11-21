package formation.selenium.bpi2017;

import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnitParamsRunner.class)
public class AjouterSalarie {
  private static WebDriver driver;
  private static WebDriverWait wait;

  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private static StringBuffer verificationErrors = new StringBuffer();

  
  private Object[] salaries() {
	    return new Object[]{
	                 new Object[]{"IMHAH", "Hassan"},
	                 new Object[]{"Toto", "Titi"}
	            };
	}  
  
  @BeforeClass
  public static void setUp() throws Exception {
    // Pour supprimer le message : "Chrome est contrôlé par un logiciel de test automatisé"
	ChromeOptions options = new ChromeOptions();
	options.addArguments("disable-infobars");
	options.addArguments("--start-minimized");
    driver = new ChromeDriver(options);
    wait  = new WebDriverWait(driver, 30);
	
	
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
  @Parameters(method = "salaries")
  public void a_AjouterSalarie(String nom, String prenom) throws Exception {
	  driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")).click();
	  for (int second = 0;; second++) {
	  	if (second >= 60) fail("timeout");
	  	try { if (isElementPresent(By.cssSelector("#menu_pim_viewPimModule > b"))) break; } catch (Exception e) {}
	  	Thread.sleep(1000);
	  }

	  driver.findElement(By.id("menu_pim_addEmployee")).click();
	  for (int second = 0;; second++) {
	  	if (second >= 60) fail("timeout");
	  	try { if (isElementPresent(By.id("firstName"))) break; } catch (Exception e) {}
	  	Thread.sleep(1000);
	  }

	  driver.findElement(By.id("firstName")).clear();
	  driver.findElement(By.id("firstName")).sendKeys(nom);
	  driver.findElement(By.id("lastName")).clear();
	  driver.findElement(By.id("lastName")).sendKeys(prenom);
	  driver.findElement(By.id("btnSave")).click();
	  driver.findElement(By.id("btnSave")).click();
	  driver.findElement(By.id("personal_optGender_1")).click();
	  new Select(driver.findElement(By.id("personal_cmbNation"))).selectByValue("64");

	  new Select(driver.findElement(By.id("personal_cmbMarital"))).selectByVisibleText("Single");
	  driver.findElement(By.id("personal_DOB")).click();
	  driver.findElement(By.id("personal_DOB")).clear();
	  driver.findElement(By.id("personal_DOB")).sendKeys("2000-10-15");
	  driver.findElement(By.id("btnSave")).click();
	  // ERROR: Caught exception [unknown command []]

  }
  
  @Test
  public void b_SupprimerSalarie() throws Exception {
	  for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (isElementPresent(By.cssSelector("#menu_pim_viewPimModule > b"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")).click();
		driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
		driver.findElement(By.id("empsearch_employee_name_empName")).clear();
		driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("IMHAH");
		driver.findElement(By.id("searchBtn")).click();
		driver.findElement(By.id("ohrmList_chkSelectAll")).click();
		driver.findElement(By.id("btnDelete")).click();
		driver.findElement(By.id("dialogDeleteBtn")).click();

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

	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcome")));
	    element.click();

	    element = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(@href, 'auth/logout')]"))));
	    element.click();

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
