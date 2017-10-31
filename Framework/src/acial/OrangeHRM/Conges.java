package acial.OrangeHRM;
import java.util.ArrayList;

import acial.selenium.utils.Selenium;



public final class Conges {
	public static void AjouterConges () {
		
		Selenium.Page("Menu").Element("Conge").Click();
		Selenium.Page("Menu").Element("AtribuerConge").Click();


		Selenium.Page("AttribuerConges").Element("NomEmploye").Set("Olivier Lahousse");
		Selenium.Page("AttribuerConges").Element("TypeAbsence").SelectByText("Congé annuel");
		
		
		
		// driver.findElement(By.id("assignleave_txtFromDate")).sendKeys("2016-07-14");
		
		
		// driver.findElement(By.id("assignleave_txtToDate")).sendKeys("2016-07-16");
	}
	
	
}
