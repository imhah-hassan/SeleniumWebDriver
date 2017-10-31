package acial.system.monitors;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PerformanceMonitor {
	public String committedVirtualMemorySize="";
	public String freePhysicalMemorySize="";
	public String freeSwapSpaceSize="";
	public String processCpuLoad="";
	public String processCpuTime="";
	public String systemCpuLoad="";
	public String totalPhysicalMemorySize="";
	public String totalSwapSpaceSize="";
	public PerformanceMonitor() {
		super();
		refresh ();
	}

	public void refresh () {
	  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
	  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
	    method.setAccessible(true);
	    if (method.getName().startsWith("get") 
	        && Modifier.isPublic(method.getModifiers())) {
	            Object value;
	        try {
	            value = method.invoke(operatingSystemMXBean);
	        } catch (Exception e) {
	            value = e;
	        } // try
   	
	        switch (method.getName()) {
	        	case "getCommittedVirtualMemorySize" :
	        		committedVirtualMemorySize=value.toString();
	        		break;
	        	case "getFreePhysicalMemorySize" :
	        		freePhysicalMemorySize=value.toString();
	        		break;
	        	case "getFreeSwapSpaceSize" :
	        		freeSwapSpaceSize=value.toString();
	        		break;
	        	case "getProcessCpuLoad" :
	        		processCpuLoad=value.toString();
	        		break;
	        	case "getProcessCpuTime" :
	        		processCpuTime=value.toString();
	        		break;
	        	case "getSystemCpuLoad" :
	        		systemCpuLoad=value.toString();
		        	break;
	        	case "getTotalPhysicalMemorySize" :
	        		totalPhysicalMemorySize=value.toString();
		        	break;
	        	case "getTotalSwapSpaceSize" :
	        		totalSwapSpaceSize=value.toString();
		        	break;
	        }
	    } // if
	  } // for
	}
	public String getMonitors (String separator) {
		refresh ();
		return (committedVirtualMemorySize+separator+freePhysicalMemorySize+separator+
				freeSwapSpaceSize+separator+processCpuLoad+separator+
				processCpuTime+separator+systemCpuLoad+separator+
				totalPhysicalMemorySize+separator+totalSwapSpaceSize);
		
	}
}
