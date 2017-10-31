import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class OrangeHRM_WebElement {
	 public static WebDriver driver;
	
	 public OrangeHRM_WebElement(WebDriver _driver) {
		 driver = _driver;
	 }
	 
	 public WebElement Username() {
	      return driver.findElement(By.id("txtUsername"));
	 }
	 public WebElement Password() {
	      return driver.findElement(By.id("txtPassword"));
	 }  
	 
	 public WebElement WelcomeMenu() {
	      return driver.findElement(By.id("welcome"));
	 }  
	 public WebElement Login() {
	      return driver.findElement(By.id("btnLogin"));
	 } 
	 
	 public WebElement LogoutMenu() {
		 return driver.findElement(By.xpath("//li[3]/a"));
	 }
}
