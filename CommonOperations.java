package uitests.testng.common;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cares.cwds.salesforce.constants.PrjConstants;

public class CommonOperations {
	
	private static final Logger logger =LoggerFactory.getLogger(CommonOperations.class.getName());
	CommonOperations(WebDriver driver) {
	    
	}

	
	public static void pageRefresh(WebDriver driver) throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(PrjConstants.DELAY);
	}
	
	public static void switchToLastTab(WebDriver driver) {
		ArrayList<String> tabList = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabList.get(tabList.size()-1));
	}
	
	public static void switchTab(WebDriver driver,int tabNumber) {
		ArrayList<String> tabList = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabList.get(tabNumber));

	}
	
	public static String getDate(String format, String whichDate) {

		if (whichDate.equalsIgnoreCase("past")) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date = new Date();
			return formatter.format(date);
		}
		else if (whichDate.equalsIgnoreCase("future")) {

			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			Date date = cal.getTime();
			return formatter.format(date);
		} else if (whichDate.equalsIgnoreCase("today")) {

			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date date = cal.getTime();
			return formatter.format(date);
		} else if (whichDate.equalsIgnoreCase("FutureDate")) {

			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, +1);
			Date date = cal.getTime();
			return formatter.format(date);
		} else if (whichDate.equalsIgnoreCase("FutureDateAdded")) {

			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, +1);
			cal.add(Calendar.DATE, +5);
			Date date = cal.getTime(); 
			return formatter.format(date);
		}
		return null;
	}
	
	public static String getTodaysDateInPST(String format) {
	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	   // Get the current date and time in PST (Pacific Standard Time)
	   ZonedDateTime currentPSTTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
	   return currentPSTTime.format(formatter);
	}
	
	public static void selectDropdownvalue(WebDriver driver,String fieldName, String fieldValue) {
		List<WebElement> listElement = dropdownList(driver,fieldName);

		if (!listElement.isEmpty()) {
			for (WebElement listValue : listElement) {
				if (listValue.getText().equalsIgnoreCase(fieldValue)) {
					listValue.click();
					break;
				}
			}
		}else {
			logger.error("{} not found", fieldValue);
		}
	}
	
	public static List<WebElement> dropdownList(WebDriver driver,String elementName) {

		List<WebElement> dropdownList1;
		
		dropdownList1 = driver.findElements(By.xpath(elementName));

		return dropdownList1;
	}
}