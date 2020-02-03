package org.exercices;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class exercice2{
  private WebDriver driver;
  private String baseUrl = "";
  private String pwdAdmin= "";
  private String welcomeMessage="";
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
   

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	
	InputStream input = exercice2.class.getClassLoader().getResourceAsStream("config.properties");
	Properties prop = new Properties();
	prop.load(input);
	
	baseUrl = prop.getProperty("Url");
	pwdAdmin = prop.getProperty("Pwd");
	welcomeMessage = prop.getProperty("WelcomeMessage");
  	System.setProperty("webdriver.chrome.driver", prop.getProperty("ChromeDriver"));
  	driver = new ChromeDriver();

  	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.get(baseUrl);
    for (int second = 0;; second++) {
    	if (second >= 5) fail("timeout");
    	try { if (isElementPresent(By.id("logInPanelHeading"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.id("txtUsername")).clear();
    driver.findElement(By.id("txtUsername")).sendKeys("admin");
    driver.findElement(By.id("txtPassword")).clear();
    driver.findElement(By.id("txtPassword")).sendKeys(pwdAdmin);
    driver.findElement(By.id("btnLogin")).click();
    for (int second = 0;; second++) {
    	if (second >= 5) fail("timeout");
    	try { if (isElementPresent(By.id("welcome"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    assertEquals(driver.findElement(By.id("welcome")).getText(), welcomeMessage);
    assertTrue(isElementPresent(By.id("menu_admin_viewAdminModule")));
  }

  @Test (priority=1)
  public void addEmployee() throws Exception {
	  
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("menu_pim_viewPimModule"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    Thread.sleep(1);
    	driver.findElement(By.id("menu_pim_viewPimModule")).click();
    	Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_addEmployee")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("firstName"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys("IMHAH");
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys("Hassan");
	    driver.findElement(By.id("employeeId")).clear();
	    driver.findElement(By.id("employeeId")).sendKeys("9102");
	    Thread.sleep(1);
	    driver.findElement(By.id("btnSave")).click();
	    try {
	      assertEquals(driver.findElement(By.xpath("//div[@id='pdMainContainer']/div/h1")).getText(), "Informations personnelles");
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals(driver.findElement(By.xpath("//div[@id='profile-pic']/h1")).getText(), "IMHAH Hassan");
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }

  }

  @Test (priority=2)
  public void employeeDetails() throws Exception {
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("menu_pim_viewPimModule"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewPimModule")).click();
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewEmployeeList")).click();

	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("empsearch_id"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.id("empsearch_id")).clear();
	    driver.findElement(By.id("empsearch_id")).sendKeys("9102");
	    Thread.sleep(1);
	    driver.findElement(By.id("searchBtn")).click();

	    try {
	        assertTrue(isElementPresent(By.linkText("9102")));
	      } catch (Error e) {
	        verificationErrors.append(e.toString());
	      }
	    Thread.sleep(1);
	    driver.findElement(By.linkText("9102")).click();
	    
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.linkText("Informations personnelles"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    Thread.sleep(1);
	    driver.findElement(By.linkText("Informations personnelles")).click();
	    Thread.sleep(1);
	    driver.findElement(By.id("btnSave")).click();

	    driver.findElement(By.id("personal_optGender_1")).click();
	    new Select(driver.findElement(By.id("personal_cmbNation"))).selectByVisibleText("Français");
	    new Select(driver.findElement(By.id("personal_cmbMarital"))).selectByVisibleText("Marié");

	    driver.findElement(By.id("personal_DOB")).clear();
	    driver.findElement(By.id("personal_DOB")).sendKeys("1982-12-25");
	    driver.findElement(By.id("btnSave")).click();

	    
	    

  }

  @Test (priority=3)
  public void employeeAddress() throws Exception {
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("menu_pim_viewPimModule"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewPimModule")).click();
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewEmployeeList")).click();

	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("empsearch_id"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.id("empsearch_id")).clear();
	    driver.findElement(By.id("empsearch_id")).sendKeys("9102");
	    Thread.sleep(1);
	    driver.findElement(By.id("searchBtn")).click();

	    try {
	        assertTrue(isElementPresent(By.linkText("9102")));
	      } catch (Error e) {
	        verificationErrors.append(e.toString());
	      }

	    driver.findElement(By.linkText("9102")).click();
	    
	    driver.findElement(By.linkText("Coordonnées")).click();
	    driver.findElement(By.id("btnSave")).click();
	    driver.findElement(By.id("contact_street1")).click();

	    driver.findElement(By.id("contact_street1")).clear();
	    driver.findElement(By.id("contact_street1")).sendKeys("20 rue d'athènes");

	    driver.findElement(By.id("contact_city")).clear();
	    driver.findElement(By.id("contact_city")).sendKeys("91450");

	    driver.findElement(By.id("contact_province")).clear();
	    driver.findElement(By.id("contact_province")).sendKeys("Paris");

	    driver.findElement(By.id("contact_emp_zipcode")).clear();
	    driver.findElement(By.id("contact_emp_zipcode")).sendKeys("75009");

	    new Select(driver.findElement(By.id("contact_country"))).selectByVisibleText("Fidji");
	    new Select(driver.findElement(By.id("contact_country"))).selectByVisibleText("France");

	    driver.findElement(By.id("contact_emp_hm_telephone")).clear();
	    driver.findElement(By.id("contact_emp_hm_telephone")).sendKeys("0155335240");

	    driver.findElement(By.id("contact_emp_mobile")).clear();
	    driver.findElement(By.id("contact_emp_mobile")).sendKeys("0755335240");

	    driver.findElement(By.id("contact_emp_work_telephone")).clear();
	    driver.findElement(By.id("contact_emp_work_telephone")).sendKeys("0155335240");

	    driver.findElement(By.id("contact_emp_work_email")).clear();
	    driver.findElement(By.id("contact_emp_work_email")).sendKeys("test@acial.fr");
	    driver.findElement(By.id("btnSave")).click();
	    
  }

  @Test (priority=4)
  public void deleteEmployee() throws Exception {
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("menu_pim_viewPimModule"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewPimModule")).click();
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewEmployeeList")).click();

	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("empsearch_id"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }

	    driver.findElement(By.id("empsearch_id")).clear();
	    driver.findElement(By.id("empsearch_id")).sendKeys("9102");
	    Thread.sleep(1);
	    driver.findElement(By.id("searchBtn")).click();

	    try {
	        assertTrue(isElementPresent(By.linkText("9102")));
	      } catch (Error e) {
	        verificationErrors.append(e.toString());
	      }

	    driver.findElement(By.linkText("9102")).click();
	    
	    Thread.sleep(1);
	    driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
	    Thread.sleep(1);
	    driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td/input")).click();
	    
	    driver.findElement(By.id("btnDelete")).click();
	    
	    for (int second = 0;; second++) {
	    	if (second >= 5) fail("timeout");
	    	try { if (isElementPresent(By.id("dialogDeleteBtn"))) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	    driver.findElement(By.id("dialogDeleteBtn")).click();
	    try {
	        assertEquals(driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td")).getText(), "Aucun résultat");
	      } catch (Error e) {
	        verificationErrors.append(e.toString());
	      }

  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
	Thread.sleep(1);
    driver.findElement(By.id("welcome")).click();
    Thread.sleep(1);
    driver.findElement(By.linkText("Déconnexion")).click();
    assertTrue(isElementPresent(By.id("txtUsername")));
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

}
