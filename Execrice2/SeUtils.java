import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public final class SeUtils {

	public static WebDriver driver;
	public static OrangeHRM_WebElement webElement;
	
	public static void Set (WebElement we, String value) {
		we.clear();
		we.sendKeys(value);
	}
	public static void Login (String baseUrl
			, String userName
			, String Password) {
		
		driver.get(baseUrl);
	    Set(webElement.Username(), "admin");
	    Set(webElement.Password(), "acial!2014");
  
	    webElement.Login().click();
	}
	public static void AddEmployee (String FirstName 
										, String LastName
										, String Nation
										, String Marital
										, String Gender
										, String DateOfBirth) {
		
		  driver.findElement(By.id("menu_pim_viewPimModule")).click();
		  driver.findElement(By.id("menu_pim_addEmployee")).click();
		  
		  driver.findElement(By.id("firstName")).clear();
		  driver.findElement(By.id("firstName")).sendKeys(FirstName);
		  driver.findElement(By.id("lastName")).clear();
		  driver.findElement(By.id("lastName")).sendKeys(LastName);
		  driver.findElement(By.id("btnSave")).click();
		  driver.findElement(By.id("btnSave")).click();
		  
	        switch (Gender) {
	            case "Masculin" :
	            	driver.findElement(By.id("personal_optGender_1")).click();
	                break;
	            case "Féminin" :
	            	driver.findElement(By.id("personal_optGender_2")).click();
                    break;
	            
	        }
		  
		  new Select(driver.findElement(By.id("personal_cmbNation"))).selectByVisibleText(Nation);
		  new Select(driver.findElement(By.id("personal_cmbMarital"))).selectByVisibleText(Marital);
		  driver.findElement(By.id("personal_DOB")).clear();
		  driver.findElement(By.id("personal_DOB")).sendKeys(DateOfBirth);
		  driver.findElement(By.id("btnSave")).click();
	  
	  }
}
