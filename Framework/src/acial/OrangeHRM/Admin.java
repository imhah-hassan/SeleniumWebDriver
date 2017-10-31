package acial.OrangeHRM;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acial.selenium.utils.Selenium;


public final class Admin {
	public static void Organization_GeneralInformation(String name, String phone, String email, String street1, String street2, String city, String zipCode, String country) {
		Selenium.Page("Menu").Element("Admin").Click();
		Selenium.Page("Menu").Element("Admin_Organization").Click();
		Selenium.Page("Menu").Element("Admin_Organization_GeneralInformation").Click();

		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationEditSaveButton").Click();
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationName").Set(name);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationPhone").Set(phone);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationEmail").Set(email);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationStreet1").Set(street1);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationStreet2").Set(street2);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationCity").Set(city);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationZipCode").Set(zipCode);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationCountry").SelectByText(country);
		Selenium.Page("Admin_Organization_GeneralInformation").Element("OrganizationEditSaveButton").Click();
	}
	
	public static void Organization_GeneralInformation(String dataFile, int lineNumber) {
		ArrayList<String[]> data = Selenium.LoadData(dataFile, 8);
		Organization_GeneralInformation (data.get(lineNumber)[0], data.get(lineNumber)[1], data.get(lineNumber)[2],
				data.get(lineNumber)[3], data.get(lineNumber)[4], data.get(lineNumber)[5],
				data.get(lineNumber)[6], data.get(lineNumber)[7]);
	}
	public static void Organization_GeneralInformation(String dataFile, int start, int end) {
		if (end==-1) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("D:\\DVLP\\OrangeHRMSe\\Data\\"+dataFile));
				int lines = 0;
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
			Organization_GeneralInformation(dataFile, i);
	}
	public static void Supprimer() {
	}
}
