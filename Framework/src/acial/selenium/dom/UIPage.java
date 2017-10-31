package acial.selenium.dom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import acial.selenium.utils.Selenium;

public class UIPage {
	public WebDriver driver=null;
	public String Name="";
	public String Type="";
	public List<UIObject> UIObjects = new ArrayList<UIObject>();
	
	public UIObject GetBy(String guiObject) {
		return (GetBy(guiObject, ""));
	}
	public UIObject GetBy(String guiObject, String param) {
		for (int i=0;i<UIObjects.size();i++) {
			if ( ((UIObject)UIObjects.get(i)).Name.equals(guiObject) ) {
				UIObject obj = (UIObject)UIObjects.get(i);
				String[] parts = obj.Value.split(":");
				parts[1] = parts[1].replace("$1$", param);
				By by=null;
			    switch (parts[0]) {
			    	case "id" :
			    		by = By.id(parts[1]);
			    		break;
			    	case "xpath" :
			    		by = By.xpath(parts[1]);
			    		break;
			    	case "css" :
			    		by = By.cssSelector(parts[1]);	
			    		break;
			    	case "name" :
			    		by = By.name(parts[1]);	
			    		break;
			    	case "link" :
			    		by = By.linkText(parts[1]);	
			    		break;
			    }
			    obj.by = by;
				return (obj);
			}
		}
		Selenium.logger.error("Object not in respository : " + guiObject);
		return(null);
		
	}
	public UIObject Element(String guiObject) {
		return (Element(guiObject, ""));
	}
	public UIObject Element(String guiObject, String param) {
		UIObject obj = GetBy (guiObject, param);
		try {
			obj.Element = driver.findElement(obj.by);
			}
		catch (Exception e) {
			Selenium.logger.error("Element not found : " + obj.by.toString());
		}
		return (obj);
	}
}
