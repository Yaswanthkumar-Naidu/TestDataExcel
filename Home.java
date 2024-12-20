package cares.cwds.salesforce.pom;

import static java.lang.String.format;

import java.awt.RenderingHints.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import cares.cwds.salesforce.common.utilities.Util;
import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.GenericLocators;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;

public class Home {
	
	private static final Logger logger =LoggerFactory.getLogger(Home.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.COMMON;
	String screenName = ScreenConstants.HOME;
	public Home(){ }
	
	public Home(WebDriver wDriver,TestCaseParam testCaseParam) 
	{
		initializePage(wDriver,testCaseParam);
	}
	
	public void initializePage(WebDriver wDriver,TestCaseParam testCaseParam) 
	    {
	    	 driver = wDriver;
	         PageFactory.initElements(driver, this);
	         ReportCommon testStepLogDetails = new ReportCommon(); 
	         testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
	         genericLocators = new GenericLocators(wDriver);
	         
	    }
	
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'slds-global-actions__notifications slds-global-actions__item-action')]")
	public WebElement bellIcon;

	@FindBy(how = How.XPATH, using = "//li[@class='notification-row notification-unread unsNotificationsListRow']/a/div/div/h3[@class='notification-text-title']")
	public List<WebElement> notificationTitlesList;

	@FindBy(how = How.XPATH, using = "//li[@class='notification-row notification-unread unsNotificationsListRow']/a/div/div/span[@class='notification-text uiOutputText']")
	public List<WebElement> notificationMessagesList;

	@FindBy(how = How.XPATH, using = "//div[@data-se='app-card-container']/a")
	public WebElement selectApp;
	
	@FindBy(how = How.XPATH, using = "//div/span[contains(text(),'CWS-CARES')]")
	public WebElement txtcares;
	
	@FindBy(how = How.XPATH, using = "//div[@data-aura-class='navexAppNavMenu']")
	public WebElement appNavMenu;
	
	@FindBy(how = How.XPATH, using = "//*[@role='menu'][@aria-label='Navigation Menu']")
	public WebElement appNavMenuContainerList;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Search')]//parent::button")
	public WebElement searchRecord;
	
	String clickRecord = "//span[@title='%s']//parent::div";
	
	String pageName = "//span[contains(@class,'slds-listbox__option')][text()='%s']";
	
	String quickLink = "//strong[text()='Quick Links:']/../..//a[text()='%s']";
	
    @FindBy(how = How.XPATH, using = "//button[contains(@title,'Close')]")
	public WebElement closeButton;
    
    @FindBy(how = How.XPATH, using ="//input[@data-value='Search: All']//ancestor::lightning-grouped-combobox/../following::input[@type='search']")
    public WebElement searchTextField;
    
    @FindBy(how = How.XPATH, using ="//input[@placeholder='Search this list...']")
    public WebElement innerSearch;
    
    @FindBy(how = How.XPATH, using ="//a[@data-refid='recordId']")
    public WebElement recordID;
 
    
    //pageTitle should match the app menu item name on the Home page
	public void navigateToAppMenuPage(String pageTitle, TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to App Menu Page");
		action.setPageActionDescription("Navigate to App Menu Page");
		try {	
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String testDataPageTitle = null;
			Webkeywords.instance().waitElementToBeVisible(driver, appNavMenu);
			Webkeywords.instance().click(driver, appNavMenu,testCaseDataSd.get("APP_NAV_MENU").get(0), testCaseParam,action);
			Webkeywords.instance().waitElementToBeVisible(driver, appNavMenuContainerList);
			WebElement page = driver.findElement(By.xpath(format(pageName,pageTitle)));
			Webkeywords.instance().scrollIntoViewElement(driver,page);
			testDataPageTitle = (pageTitle.toUpperCase()).replace(" ","_");
			if(pageTitle.equalsIgnoreCase("Service Names / Evidence Based Practices"))
				testDataPageTitle = "SERVICE_NAMES";
			Webkeywords.instance().click(driver, page,testCaseDataSd.get(testDataPageTitle).get(0), testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}
	
	//name should match the quick link on the Home page
	public void navigateToQuickLinkInHome(String name, TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to Homepage QuickLink");
		action.setPageActionDescription("Navigate to Homepage QuickLink");
		try {	
			String testDataName=null;
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			testDataName = (name.toUpperCase()).replace(" ","_");
			Webkeywords.instance().click(driver, driver.findElement(By.xpath(format(quickLink,name))),testCaseDataSd.get(testDataName).get(0), testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}

	public void verifyNotificationMessage(TestCaseParam testCaseParam) throws CustomException  {
		
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Verify Notification");
		action.setPageActionDescription("Verify Notification");
	try {	
		Webkeywords.instance().click(driver, bellIcon,"", testCaseParam,action);
		
	}
	catch (Exception e)
	{
		logger.error("Failed == {} ", action.getPageActionDescription());
		exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		throw new CustomException("Error Occured",e);
	}

	}
	
	public void verifyNotification(TestCaseParam testCaseParam, String expectedTitle, String expectedMessage) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Verify Notification");
		action.setPageActionDescription("Verify Notification");
		
		try {
			Webkeywords.instance().waitElementToBeVisible(driver, bellIcon);
		Webkeywords.instance().click(driver, bellIcon,"", testCaseParam,action);
		
		List<WebElement> allNotificationTitles = notificationTitlesList;
		List<WebElement> allNotificationMessages = notificationMessagesList;
		for (int i = 0; i <= allNotificationTitles.size() - 1; i++) {
			String actualTitle = allNotificationTitles.get(i).getText();
			String actualMessage = allNotificationMessages.get(i).getText();
			if (actualTitle.equalsIgnoreCase(expectedTitle)) {				
				Assert.assertTrue(actualTitle.equalsIgnoreCase(expectedTitle), 
		                  expectedTitle + " text is present in notification list");
				break;
			}else {
				Assert.assertTrue(actualMessage.equalsIgnoreCase(expectedMessage), 
						expectedMessage + " text is not present in notification list");
				
			}
		}
		}
		
		
		catch (Exception e)
		{
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}
	
/////////////////////////////This method is already covered in the above method navigateToAppMenuPage() and can be removed///////////////////////////////
	public void selectLinkFromNavigationMenue(TestCaseParam testCaseParam,String scriptIteration, String pomIteration, String navigatorMenue) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Show Navigation Menu");
		action.setPageActionDescription("Show Navigation Menu");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Show Navigation Menu",testCaseDataSd.get("SHOWNAVIGATIONMENUE").get(0)), testCaseDataSd.get("SHOWNAVIGATIONMENUE").get(0), testCaseParam, action);
			switch (navigatorMenue) {
			case "Organizations":
				Webkeywords.instance().click(driver, genericLocators.button(driver, "Organizations",testCaseDataSd.get("ORGANIZATIONS").get(0)), testCaseDataSd.get("ORGANIZATIONS").get(0), testCaseParam, action);
				break;
			case "Provider Search":
				Webkeywords.instance().click(driver, genericLocators.button(driver, "Provider Search",testCaseDataSd.get("PROVIDERSEARCH").get(0)), testCaseDataSd.get("PROVIDERSEARCH").get(0), testCaseParam, action);
				break;
			case "Screenings":
				Webkeywords.instance().click(driver, genericLocators.button(driver, "Screenings",testCaseDataSd.get("SCREENINGS").get(0)), testCaseDataSd.get("testCaseDataSd.get(\"RECALL\").get(0)").get(0), testCaseParam, action);
				break;
			case "Home":
				Webkeywords.instance().click(driver, genericLocators.button(driver, "Home",testCaseDataSd.get("HOME_TAB").get(0)), testCaseDataSd.get("HOME_TAB").get(0), testCaseParam, action);
				break;
			case "Focused Search":
				Webkeywords.instance().click(driver, genericLocators.button(driver, "Focused Search",testCaseDataSd.get("FOCUSED_SEARCH_TAB").get(0)), testCaseDataSd.get("FOCUSED_SEARCH_TAB").get(0), testCaseParam, action);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}
	
	public void clickOnNotificationForApproval(String recordType, TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Click Bell Notification Item");
		action.setPageActionDescription("Click Bell Notification Item");
		
		try {
			WebElement notificationBell = null;
			WebElement scrIdnotification = null;
			String id;
			notificationBell = Webkeywords.instance().waitAndReturnWebElement(driver,By.xpath("//button[contains(@class,'slds-global-actions__notifications slds-global-actions__item-action')]"));
			Webkeywords.instance().waitElementClickable(driver, notificationBell);
			notificationBell.click();
			switch (recordType.toLowerCase()) {
			case "scr_id":
				id = SalesforceConstants.getConstantValue(recordType+pomIteration);
				scrIdnotification = Webkeywords.instance().waitAndReturnWebElement(driver,By.xpath("//div/span[contains(text(),'Screening ID: " + id + "')]"));
				Webkeywords.instance().waitElementClickable(driver, scrIdnotification);
				Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
				Webkeywords.instance().click(driver, scrIdnotification,testCaseDataSd.get("SCR_BELL_NOTIFICATION").get(0), testCaseParam,action);
				return;
			case "folio ref":
				id = SalesforceConstants.getConstantValue(recordType);
				WebElement folionotification = driver.findElement(By.xpath("//div/span[contains(text(),'Folio Ref.: " + id + "')]"));
				Webkeywords.instance().waitElementClickable(driver, folionotification);
				Webkeywords.instance().click(driver, folionotification,"", testCaseParam,action);
				return;
			case "family transfer id":
				WebElement ftrnotification = driver.findElement(By.xpath("//li[1]//div/span[contains(text(),'FTR-')]"));
				Webkeywords.instance().waitElementClickable(driver, ftrnotification);
				Webkeywords.instance().click(driver, ftrnotification,"", testCaseParam,action);

				return;
			case "court work item id":
				WebElement courtnotification = driver.findElement(By.xpath("//li[1]//div/span[contains(text(),'Court Work Item ID:')]"));
				Webkeywords.instance().waitElementClickable(driver, courtnotification);
				Webkeywords.instance().click(driver, courtnotification,"", testCaseParam,action);
				return;
			default:
				logger.error("Record type name not found in click notification");
			}
		} catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}

	public void searchRecord (TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Search Record");
		action.setPageActionDescription("Search Record");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		
			String recordId = SalesforceConstants.getConstantValue("SCR_ID"+pomIteration);
			Webkeywords.instance().click(driver,searchRecord ,testCaseDataSd.get("SEARCH").get(0), testCaseParam,action);
			Webkeywords.instance().setText(driver,searchTextField,recordId , testCaseParam, action);
			WebElement page = driver.findElement(By.xpath(format(clickRecord,recordId))) ;
			Webkeywords.instance().waitElementToBeVisible(driver, page);
			Webkeywords.instance().waitElementClickable(driver, page);
			Webkeywords.instance().jsClick(driver,page ,recordId, testCaseParam,action);
		
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}


		public void closeAllTabs(TestCaseParam testCaseParam) throws CustomException{

			PageDetails action = new PageDetails();
			LocalDateTime startTime= LocalDateTime.now();
			action.setPageActionName("Close All tabs");
			action.setPageActionDescription("Close All tabs");
			try {
				Webkeywords.instance().closeAllOpenTabs(driver, closeButton);
			
			}catch (Exception e)
			{
				logger.error("Failed == {}",action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);

			}
		}
		
		public void innerSearch (TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
			PageDetails action = new PageDetails();
			LocalDateTime startTime= LocalDateTime.now();
			action.setPageActionName("Inner Search Record");
			action.setPageActionDescription("Inner Search Record");
			try {
				Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
						TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
				String recordId = SalesforceConstants.getConstantValue("SCR_ID"+pomIteration);
				Webkeywords.instance().click(driver,innerSearch ,testCaseDataSd.get("INNER SEARCH").get(0), testCaseParam,action);
				Webkeywords.instance().setText(driver,innerSearch,recordId , testCaseParam, action);
				innerSearch.sendKeys(Keys.ENTER);
				Webkeywords.instance().waitElementToBeVisible(driver, recordID);
				Webkeywords.instance().jsClick(driver,recordID ,recordId, testCaseParam,action);
			
			} catch (Exception e) {
					logger.error("Failed == {} ", action.getPageActionDescription());
					exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
				}
		}
	}
