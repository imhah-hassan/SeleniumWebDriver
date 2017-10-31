
import java.io.IOException;

import acial.OrangeHRM.Login;
import acial.selenium.utils.Selenium;
import acial.system.monitors.PerformanceMonitor;

public class Main {
	public static long mes_login=0, mes_navigation=0, mes_logout=0;
	public static void main(String[] args) throws InterruptedException {
		// Monitor;Browser;mes_login;mes_navigation;mes_logout;
		// CommitedVirtualMemory;FreePhysicalMemory;FreeSwapSpace;ProcessCpuLoad;
		// ProcessCpuTime;SystemCpuLoad;TotalPhysicalMemory;TotalSwapSpace
	        try {
				Selenium.Initialize();
				long start = System.currentTimeMillis();
				Login.Connexion ("admin", "test");
				PerformanceMonitor perfMon = new PerformanceMonitor();
				mes_login = System.currentTimeMillis() - start;
				Selenium.logger.info ("Monitor;"+Selenium.browser+";"+mes_login +";;;"+perfMon.getMonitors(";"));

				start = System.currentTimeMillis();
				// Salarie.Ajouter("Salarie.csv", 1, 10);
				Login.Navigation();
				mes_navigation = System.currentTimeMillis() - start;
				Selenium.logger.info ("Monitor;"+Selenium.browser+";;"+mes_navigation+";"+perfMon.getMonitors(";"));
	
				// Salarie.Supprimer ("Nom");
	
				start = System.currentTimeMillis();
	  	  		Login.Deconnexion();
				mes_logout = System.currentTimeMillis() - start;
				Selenium.logger.info ("Monitor;"+Selenium.browser+";;;"+mes_logout+";"+perfMon.getMonitors(";"));
				Selenium.Destroy();
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Thread.sleep(8000);
		
	}

}
