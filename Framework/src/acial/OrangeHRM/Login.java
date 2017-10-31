package acial.OrangeHRM;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import acial.selenium.utils.Selenium;



public final class Login {

	public static void Connexion (String login, String pwd) {
		Selenium.Page("Authentication").Element("Username").Set(login);
		Selenium.Page("Authentication").Element("Password").Set(pwd);
		Selenium.Page("Authentication").Element("Login").Click();
	}
	
	public static void Navigation () {
		Selenium.Page("Menu").Element("Admin").Click();
		Selenium.Page("Menu").Element("PIM").Click();
		Selenium.Page("Menu").Element("Conge").Click();
		Selenium.Page("Menu").Element("Admin").Click();
		Selenium.Page("Menu").Element("Admin").Click();
	}
	
	public static void Connexion (String dataFile, int lineNumber) {
		ArrayList<String[]> data = Selenium.LoadData(dataFile, 2);
		Connexion ( data.get(lineNumber)[0], data.get(lineNumber)[1]);
	}
	public static void Deconnexion () {
	    Selenium.Page("Dashbord").Element("DashbordMenu").Click();
	    Selenium.Page("Home").Element("WelcomeMenu").Click();
	    Selenium.Page("Home").Element("Logout").Click();
	}
}
