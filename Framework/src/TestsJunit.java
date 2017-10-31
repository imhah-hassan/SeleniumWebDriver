import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import acial.OrangeHRM.Admin;
import acial.OrangeHRM.Conges;
import acial.OrangeHRM.Login;
import acial.OrangeHRM.Salarie;
import acial.selenium.dom.UIObject;
import acial.selenium.utils.Selenium;


public class TestsJunit {
  
  @BeforeClass
  public static void setUp() throws Exception {
      Selenium.Initialize();
      Login.Connexion("login.csv", 1);
  }

  
  @Ignore
  @Test
  public void AjouterConge() {
	 Conges.AjouterConges();

  }
  
  @Test
  public void AjouterSalarie() {
	  Salarie.Ajouter("Salarie.csv", 1, -1);
  }
  @Ignore
  @Test
  public void RechercherSalarie() throws Exception {
	  Salarie.Rechercher("Vittorio");
	  
	  Assert.assertEquals(Selenium.Page("PIM_EmployeeList").Element("Ligne1_Nom").Element.getText(), "Vittorio");

  }
  @Ignore
  @Test
  public void InfosSociete() {
	  Admin.Organization_GeneralInformation("Organisation.csv", 1);
	  
	  UIObject SaveEditButton = Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationEditSaveButton");
	  Selenium.WaitForValue(SaveEditButton, "Éditer");
	  SaveEditButton.Click();
	  assertEquals(Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationName").Value(), "ACIAL");
	  Selenium.WaitForValue(SaveEditButton, "Sauver");
	  SaveEditButton.Click();
  }
  @AfterClass
  public static void tearDown() throws Exception {
	  Login.Deconnexion();
	  Selenium.driver.quit();
  }
}
