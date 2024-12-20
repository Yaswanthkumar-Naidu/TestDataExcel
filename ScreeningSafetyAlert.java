package cares.cwds.salesforce.pom.screening;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cares.cwds.salesforce.common.utilities.Util;
import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.GenericLocators;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;

public class ScreeningSafetyAlert {
	private static final Logger logger = LoggerFactory.getLogger(ScreeningSafetyAlert.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SCREENINGSAFETYALERTS;
	
	@FindBy(how = How.XPATH, using = "//li[@data-target-selection-name='sfdc:StandardButton.Safety_Alert__c.New']//button")
	public WebElement safetyAlertNew;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Safety Alert']/..//input")
	public WebElement safetyAlert;
	
	String sefetyAlertValue ="//label[text()='%s']//parent::div//span[text()='%s']";

	public ScreeningSafetyAlert() {
	}

	public ScreeningSafetyAlert(WebDriver wDriver, TestCaseParam testCaseParam) {
		initializePage(wDriver, testCaseParam);
	}

	public void initializePage(WebDriver wDriver, TestCaseParam testCaseParam) {
		driver = wDriver;
		PageFactory.initElements(driver, this);
		ReportCommon testStepLogDetails = new ReportCommon();
		testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
		genericLocators = new GenericLocators(wDriver);
	}

	private static final String SAFETY_ALERT= "Safety Alert";
	
	public void navigateToScreeningSafetyAlert(TestCaseParam testCaseParam,  String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to Safety Alert");
		action.setPageActionDescription("Navigate to Safety Alert");
		try {

			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String safetyAlertsTabTD = testCaseDataSd.get("SAFETY_ALERTS_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Safety Alerts",safetyAlertsTabTD),safetyAlertsTabTD, testCaseParam,action);
		}
		catch (Exception e){
			logger.error("Failed == {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}

	
	public void addScreeningSafetyAlert(TestCaseParam testCaseParam,  String scriptIteration, String pomIteration)
			throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime = LocalDateTime.now();
		action.setPageActionName("Add Safety Alert");
		action.setPageActionDescription("Add Safety Alert");

		try {

			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

			String safetyAlertsTabTD = testCaseDataSd.get("SAFETY_ALERTS_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Safety Alerts",safetyAlertsTabTD),safetyAlertsTabTD, testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New",testCaseDataSd.get("NEW_BTN").get(0)),testCaseDataSd.get("NEW_BTN").get(0), testCaseParam,action);
			
			Webkeywords.instance().waitElementToBeVisible(driver, safetyAlert);
			Webkeywords.instance().click(driver, safetyAlert,testCaseDataSd.get("SAFETY_ALERT").get(0), testCaseParam,action);
			WebElement we = driver.findElement(By.xpath(format(sefetyAlertValue,"Safety Alert",testCaseDataSd.get("SAFETY_ALERT").get(0))));	
			Webkeywords.instance().jsClick(driver,we,testCaseDataSd.get("SAFETY_ALERT").get(0), testCaseParam,action);
		
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_BTN").get(0)),testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);
			
			SalesforceCommon.verifyToastMessage(driver,testCaseDataSd.get("TOAST_MSG_VERIFY").get(0),testCaseParam, action);			
		} catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(),
					action.getPageActionDescription(), startTime, e);
		} 

	}
	
	public void verifyingSaftyAlertsFields(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException{
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("verifying Safty Alerts Fields");
		action.setPageActionDescription("verifying Safty Alerts Fields");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		
				
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textOnPage(driver, SAFETY_ALERT,testCaseDataSd.get("SAFETY_ALERTS_VERIFY").get(0)), testCaseDataSd.get("SAFETY_ALERTS_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Sensitive & Sealed",testCaseDataSd.get("SENSITIVE_SEALED_VERIFY").get(0)), testCaseDataSd.get("SENSITIVE_SEALED_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.checkbox(driver, "Sensitive",testCaseDataSd.get("SENSITIVE_VERIFY").get(0)), testCaseDataSd.get("SENSITIVE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.checkbox(driver, "Sealed",testCaseDataSd.get("SEALED_VERIFY").get(0)), testCaseDataSd.get("SEALED_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "System Information", testCaseDataSd.get("SYSTEMINFO_VERIFY").get(0)), testCaseDataSd.get("SYSTEMINFO_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created By",testCaseDataSd.get("CREATEDBY_VERIFY").get(0)), testCaseDataSd.get("CREATEDBY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created Date",testCaseDataSd.get("CREATEDDATE_VERIFY").get(0)), testCaseDataSd.get("CREATEDDATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Modified Date",testCaseDataSd.get("MODIFIEDDATE_VERIFY").get(0)), testCaseDataSd.get("MODIFIEDDATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Last Modified By",testCaseDataSd.get("LASTMODIFIEDBY_VERIFY").get(0)), testCaseDataSd.get("LASTMODIFIEDBY_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Cancel",testCaseDataSd.get("CANCEL_VERIFY").get(0)), testCaseDataSd.get("CANCEL_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);
			
			
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}

	}
}
