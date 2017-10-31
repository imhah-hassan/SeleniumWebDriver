package acial.OrangeHRM;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;

import acial.selenium.utils.Selenium;


public final class Salarie {

	public static void Rechercher(String name) {
		Selenium.Page("Menu").Element("PIM").Click();
		Selenium.Page("Menu").Element("PIM_EmployeeList").Click();
		
		Selenium.Page("PIM_EmployeeList").Element("EmployeeName").Set(name);
		Selenium.Page("PIM_EmployeeList").Element("SearchBtn").Click();
	}
	public static void RechercherById(String id) {
		Selenium.Page("Menu").Element("PIM").Click();
		Selenium.Page("Menu").Element("PIM_EmployeeList").Click();
		
		Selenium.Page("PIM_EmployeeList").Element("EmployeeId").Set(id);
		Selenium.Page("PIM_EmployeeList").Element("SearchBtn").Click();
	}
	public static void Ajouter(String firstName, String lastName, String gendre, 
								String nationality, String maritalStatus, String dateOfBirth) {
		Selenium.Page("Menu").Element("PIM").Click();
		Selenium.Page("Menu").Element("PIM_AddEmployee").Click();

		Selenium.Page("PIM_AddEmployee").Element("FirstName").Set(firstName);
		Selenium.Page("PIM_AddEmployee").Element("LastName").Set(lastName);
		Selenium.Page("PIM_AddEmployee").Element("EditSaveBtn").Click();
		// Edit
		Selenium.Page("PIM_AddEmployee").Element("EditSaveBtn").Click();
		Selenium.Page("PIM_AddEmployee").Element(gendre).Click();

		Selenium.Page("PIM_AddEmployee").Element("Nationality").SelectByText(nationality);
		Selenium.Page("PIM_AddEmployee").Element("MaritalStatus").SelectByText(maritalStatus);

		Selenium.Page("PIM_AddEmployee").Element("DateOfBirth").Set(dateOfBirth);
		Selenium.Page("PIM_AddEmployee").Element("EditSaveBtn").Click();
		
	}
	public static void Ajouter(String dataFile, int lineNumber) {
		ArrayList<String[]> data = Selenium.LoadData(dataFile, 6);
		Ajouter (data.get(lineNumber)[0], data.get(lineNumber)[1], data.get(lineNumber)[2],
				data.get(lineNumber)[3], data.get(lineNumber)[4], data.get(lineNumber)[5]);
	}
	public static void Ajouter(String dataFile, int start, int end) {
		if (end==-1) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("D:\\DVLP\\OrangeHRMSe\\Data\\"+dataFile));
				int lines = start;
				while (reader.readLine() != null) lines++;
				end = lines;
			} 
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i=start; i<=end; i++)
			Ajouter(dataFile, i);
	}
	public static void Supprimer(String LastName) {
		Rechercher (LastName);
		Selenium.Page("PIM_EmployeeList").Element("SelectChkBxGeneric", "1").Click();
		Selenium.Page("PIM_EmployeeList").Element("DeleteEmployees").Click();
		Selenium.Page("PIM_EmployeeList").Element("ConfirmDeleteEmployees").Click();
	}
}
