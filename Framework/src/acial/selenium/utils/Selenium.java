package acial.selenium.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;


import acial.selenium.dom.UIObject;
import acial.selenium.dom.UIPage;

public final class Selenium {
	public static WebDriver driver = null;
	public static List<UIPage> GuiPages = new ArrayList<UIPage>();
	public final static Logger logger = Logger.getLogger("OrangeHRM");
	
	private static String baseUrl="";
	public static String browser="";
	private static int implicitWait=20;
	private static int explicitWait=30;
	private static String driversPath="";
	private enum Browsers {
	    Remote, Firefox, IE, Chrome, HtmlUnit;
	}
	private static String remote="";
	public static String dataPath="";
	private static String domFile="";

	public static void Initialize() throws IOException {
		
		Selenium.logger.debug( "Start" );
		
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);
	    baseUrl = prop.getProperty("BaseUrl");
	    browser = prop.getProperty("Browser");
		Selenium.logger.debug( "Browser : " + browser );
	    implicitWait = Integer.parseInt(prop.getProperty("ImplicitWait"));
	    explicitWait = Integer.parseInt(prop.getProperty("ExplicitWait"));
	    driversPath = prop.getProperty("DriversPath");
	    remote= prop.getProperty("Remote");
	    dataPath = prop.getProperty("DataPath");
	    domFile = prop.getProperty("DomFile");
	    SetDriver();
	    try {
	    	driver.get(baseUrl);
		}
	    catch (Exception e) {
	    	// Contournement pour éviter les erreurs javascript
	    	// avec HtmlUnitDriver en Remote
	    	if (e.getCause().getClass().equals(ScreenshotException.class))
	    		logger.error("Javascript Error : " + e.getMessage());
	    	else
	    		throw e;
	    }
		logger.debug("Current URL :" + driver.getCurrentUrl());
	}
	public static void Destroy() throws IOException {
		driver.close();
		driver.quit();
		driver = null;
		Selenium.logger.debug( "End" );
	}
	public static UIPage Page(String page) {
		for (int i=0;i<GuiPages.size();i++) {
			if ( ((UIPage)GuiPages.get(i)).Name.equals(page) ) {
				UIPage p = (UIPage)GuiPages.get(i);
				p.driver = driver;
				Selenium.logger.debug("Page : " + p.Name);
				return (p);
			}
		}
		return(null);
	}
	public static void SetDriver () {
		if (driver==null)
		{
			Browsers selectedBrowser = Browsers.valueOf(browser);
			switch(selectedBrowser) {
			    case Firefox:
			    	if ((remote==null) || (remote.isEmpty())) {
			    		DesiredCapabilities caps = new DesiredCapabilities();
			    		// caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			    		// caps.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			    		 
			    		driver = new FirefoxDriver(caps);
			    	}
			    	else {
						DesiredCapabilities capabilities = DesiredCapabilities.firefox();
						capabilities.setBrowserName("firefox");
						capabilities.setPlatform(Platform.WINDOWS);
						try {
							driver = new RemoteWebDriver(new URL(remote), capabilities);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
			    	}
			        break;
			    case Chrome:
			        System.setProperty("webdriver.chrome.driver", driversPath + "chromedriver.exe");
			    	if ((remote==null) || (remote.isEmpty())) {
				        driver = new ChromeDriver();
			        }
			    	else {
						DesiredCapabilities capabilities = DesiredCapabilities.chrome();
						capabilities.setBrowserName("chrome");
						capabilities.setPlatform(Platform.WINDOWS);
						try {
							driver = new RemoteWebDriver(new URL(remote), capabilities);
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
			    	}
			        break;
			    case IE:
			        System.setProperty("webdriver.ie.driver", driversPath + "IEDriverServer.exe");
			        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			        capabilities.setCapability("ignoreZoomSetting", true);
			        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			    	if ((remote==null) || (remote.isEmpty())) {
				    	driver = new InternetExplorerDriver(capabilities);
			        }
			    	else {
			    		capabilities.setBrowserName("internet explorer");
			    		capabilities.setPlatform(Platform.WINDOWS);
						try {
							driver = new RemoteWebDriver(new URL(remote), capabilities);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
			    	}
			        break;
			    case HtmlUnit:
			    	System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "fatal");
			    	Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
			    	Logger.getLogger("org.apache.http").setLevel(Level.OFF);
			    	if ((remote==null) || (remote.isEmpty())) {
			    		driver = new HtmlUnitDriverExtended(true);
			    	}
			    	else {
			    		// Impossible de désactiver les erreurs javascript en remote
			    		// Contournement avec la gestion des exceptions
						capabilities = DesiredCapabilities.htmlUnit();
						capabilities.setJavascriptEnabled(true);
						capabilities.setPlatform(Platform.WINDOWS);
						try {
							driver = new RemoteWebDriver(new URL(remote), capabilities);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
			    	}
			        break;
			    default:
		    		driver = new FirefoxDriver();
			    	break;
			}
		    driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		    driver.manage().window().maximize();
		    LoadGuiPages ();
		}
	}
	public static void LoadGuiPages () {
		File fXmlFile = new File(domFile);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("page");
			for (int temp = 0; temp < nList.getLength(); temp++) {
		        Node nNode = nList.item(temp);
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		           Element eElement = (Element) nNode;
		           
		           UIPage page = new UIPage();
		           page.Name = eElement.getElementsByTagName("name").item(0).getTextContent();
		           page.Type= eElement.getElementsByTagName("type").item(0).getTextContent();
		           GuiPages.add(page);
		           
		           NodeList objects = eElement.getElementsByTagName("object");
		           for (int k = 0; k < objects.getLength(); k++) {
		            		Node nobj = objects.item(k);
		            		Element objelem = (Element) nobj;
		            		
		            		UIObject guiObject = new UIObject();
		            		guiObject.Name = objelem.getElementsByTagName("name").item(0).getTextContent();
		            		guiObject.Type = objelem.getElementsByTagName("type").item(0).getTextContent();
		            		guiObject.Value = objelem.getElementsByTagName("value").item(0).getTextContent();
		            		page.UIObjects.add(guiObject);
		            	}
		            	
		           }
		    }
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
		} catch (SAXException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private static Iterable<CSVRecord> GetData (String dataFile) {
		CSVFormat csvFormat = CSVFormat.newFormat(';');
		CSVParser csvParser;
		try {
			csvParser = new CSVParser(new BufferedReader(new FileReader(dataFile)), csvFormat);
			List<CSVRecord> records = csvParser.getRecords();
			csvParser.close();
			return (records);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return(null);
		}
	}
	public static ArrayList<String[]> LoadData(String dataFile, int columnsSize) {
		String[] columns = new String[columnsSize];
		ArrayList<String[]> data = new ArrayList<String[]>();
		Iterable<CSVRecord> logins = Selenium.GetData (dataPath + dataFile) ;
		for (CSVRecord record : logins ) {
			columns = new String[columnsSize];
			for (int j=0;j<columnsSize;j++) 
				columns[j] = record.get(j);
		    data.add(columns);
		}
		return (data);
	}
    public static boolean isElementPresent(UIObject obj) {
	    try {
	    		
	    		obj.Element = Selenium.driver.findElement(obj.by);
	    		return obj.Element.isDisplayed();
		    } catch (NoSuchElementException e) {
		      return false;
    		} catch (Exception e) {
				Selenium.logger.error(e.getMessage());
  		        return false;
    		}
  }
    public static void WaitForElement(UIObject obj) {
		Selenium.logger.debug( "Waiting for element " + obj.Name + ". " + obj.by.toString());
		for (int second = 0; second<implicitWait; second++) {
	        if (isElementPresent(obj)) {
	    		Selenium.logger.debug( "Element found " + obj.Name + ". " + obj.by.toString());
	        	break;
	        }
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Selenium.logger.error(e.getMessage());
			}
		}
    }
    
    public static void WaitForElementClickable(UIObject obj) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(obj.by));
		Selenium.logger.debug( "Element found " + obj.Name + ". " + obj.by.toString());
    }
	
    public static void WaitForText(UIObject obj, String text) {
		Selenium.logger.debug( "Waiting for text " + obj.Element.getText() + ". Expected : " + text);
		for (int second = 0; second<implicitWait; second++) {
	        if (obj.Element.getText().equals(text)) {
	    		Selenium.logger.debug( "Text found " + obj.Element.getText()  + ". " + obj.by.toString());
	        	break;
	        }
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Selenium.logger.error(e.getMessage());
			}
		}
  }
    public static void WaitForValue(UIObject obj, String text) {
		Selenium.logger.debug( "Waiting for value " + obj.Element.getAttribute("value") + ". Expected : " + text);
		for (int second = 0; second<implicitWait; second++) {
	        if (obj.Element.getAttribute("value").equals(text)) {
	    		Selenium.logger.debug( "Value found " + obj.Element.getAttribute("value")  + ". " + obj.by.toString());
	        	break;
	        }
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Selenium.logger.error(e.getMessage());
			}
		}
  }

}
