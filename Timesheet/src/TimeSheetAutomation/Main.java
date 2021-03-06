package TimeSheetAutomation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
	
	public static void main(String[] args) throws IOException {

    	System.setProperty("webdriver.chrome.driver", "./chromedriver");
    	
    	String workOrderString = "";
    	if (args.length >2 && args[2]!=null) {
    		workOrderString = args[2];
    	}
    //	User user = new User(args[0], args[1], workOrderString);
   	User user = new User("yourUserName", "yourPassword", "yourWorkOrder(optional)");
	    		
        WebDriver driver = new ChromeDriver();
        TimeSheet timeSheet = new TimeSheet(driver, user);
        
        timeSheet.login();
        
        boolean isWaitForTimeSheetDone = false;
        try {
        	isWaitForTimeSheetDone = timeSheet.waitForTimeSheet(0);
        } catch (InterruptedException e){
        	e.printStackTrace();
        }

        if (isWaitForTimeSheetDone){
        	boolean isFillTimesheetSuccess = timeSheet.fillTimeSheet(); 

        	if (!timeSheet.checkIf40Hrs()){
        		System.out.println("Not filling up 40 hours");
        	} else if (isFillTimesheetSuccess && timeSheet.checkIf40Hrs()){
        		timeSheet.submitTimesheet();
        		System.out.println("TimeSheet Completed");
        		
            	driver.quit(); 
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}            	
        		System.exit(0);
        	} else {
        		System.out.println("TimeSheet Not Completed");
        	}
        }
    	driver.quit();   
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    	
		System.exit(1);


    }
}
