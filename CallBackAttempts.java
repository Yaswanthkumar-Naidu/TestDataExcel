package cares.cwds.salesforce.pom.screening;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cares.cwds.salesforce.common.utilities.Util;
import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.GenericLocators;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import testsettings.TestRunSettings;
import uitests.testng.common.CommonOperations;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;

public class CallBackAttempts {
	private static final Logger logger =LoggerFactory.getLogger(CallBackAttempts.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.COMMON;
	String screenName = ScreenConstants.CALLBACKATTEMPTS;
	
	private static final String CALLBACK_ATTEMPTS= "Call Back Attempts";
	private static final String METHOD= "Method";
	private static final String STATUS= "Status";
	private static final String COMMENTS= "Comments";
	
	public CallBackAttempts(){ }
	
	public CallBackAttempts(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	@FindBy(how = How.XPATH, using = "(//label[text()='Call Back Date/Time']/../../following-sibling::lightning-input//input)[1]")
	public WebElement callBackDateTime;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Staff Person']/..//input")
	public WebElement staffPerson;
	
	@FindBy(how = How.XPATH, using = "(//label[text()='Staff Person']/..//child::div//following-sibling::div)[2]//ul//li//span[2]//child::span[1]//span")
	public List<WebElement> staffPersonList;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Method']/../../following-sibling::lightning-combobox//button")
	public WebElement methodClick;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Method']/../..//following-sibling::lightning-combobox//span/span")
	public List<WebElement> methodList;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Status']/../../following-sibling::lightning-combobox//button")
	public WebElement statusClick;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Status']/../..//following-sibling::lightning-combobox//span/span")
	public List<WebElement> statusList;
	
	public void navigateToScrCallBackAttempts(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate  Call Back Attempts");
		action.setPageActionDescription("Navigate Call Back Attempts");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			String callBackAttemptsTAB = testCaseDataSd.get("CALLBACK_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, CALLBACK_ATTEMPTS,callBackAttemptsTAB),callBackAttemptsTAB, testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
		}
	}
	
	public void addCallBackAttemptsInfo(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Call Back Attempts");
		action.setPageActionDescription("Process Call Back Attempts");
		try {
			
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			
			String newBtn = testCaseDataSd.get("NEW_BTN").get(0);
			String callBackDate = testCaseDataSd.get("CALLBACK_DATE_TIME").get(0);
			String staffPersonTD = testCaseDataSd.get("STAFF_PERSONS").get(0);
			String method = testCaseDataSd.get("METHOD").get(0);
			String callBackRecipient = testCaseDataSd.get("CALLBACK_RECIPIENT").get(0);
			String status = testCaseDataSd.get("STATUS").get(0);
			String comments = testCaseDataSd.get("COMMENTS").get(0);
			String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
			
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().waitElementToBeVisible(driver, genericLocators.button(driver, "New",newBtn));
			Webkeywords.instance().click(driver,genericLocators.button(driver, "New",newBtn),newBtn, testCaseParam, action);
			Webkeywords.instance().setDateText(driver, callBackDateTime, CommonOperations.getDate("M/d/yyyy",callBackDate), testCaseParam, action);
					
			Webkeywords.instance().click(driver,staffPerson , staffPersonTD, testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, staffPersonTD,staffPersonTD),staffPersonTD, testCaseParam,action);
		
			Webkeywords.instance().selectDropdownValueByElement(driver, methodClick, methodList,method,method,testCaseParam,action);

			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Call Back Recipient" ,callBackRecipient), callBackRecipient, testCaseParam,action);
			
			Webkeywords.instance().selectDropdownValueByElement(driver,statusClick,statusList,status,status,testCaseParam,action);
						
			Webkeywords.instance().setText(driver, genericLocators.textarea(driver, COMMENTS ,comments), comments, testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save" ,saveBtn),saveBtn, testCaseParam,action);
	    	}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
		}
	}
	
	public void verifyingCallBackAttemptsFields(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("verifying Call Back Attempts");
		action.setPageActionDescription("verifying Call Back Attempts");
		try {
			
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Information",testCaseDataSd.get("INFORMATION_VERIFY").get(0)), testCaseDataSd.get("INFORMATION_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Call Back Date/Time",testCaseDataSd.get("CALLBACKDATETIME_VERIFY").get(0)), testCaseDataSd.get("CALLBACKDATETIME_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Staff Person", testCaseDataSd.get("STAFFPERSON_VERIFY").get(0)), testCaseDataSd.get("STAFFPERSON_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Call Back Recipient",testCaseDataSd.get("CALLBACKRECIPIENT_VERIFY").get(0)), testCaseDataSd.get("CALLBACKRECIPIENT_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, METHOD, testCaseDataSd.get("METHOD_VERIFY").get(0)), testCaseDataSd.get("METHOD_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, STATUS,testCaseDataSd.get("STATUS_VERIFY").get(0)), testCaseDataSd.get("STATUS_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textarea(driver, COMMENTS,testCaseDataSd.get("COMMENTS_VERIFY").get(0)), testCaseDataSd.get("COMMENTS_VERIFY").get(0), testCaseParam, action);
				//------------------------------------- Sensitive & Sealed ------------------------------------
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Sensitive & Sealed",testCaseDataSd.get("SENSITIVEANDSEALED_VERIFY").get(0)), testCaseDataSd.get("SENSITIVEANDSEALED_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.checkbox(driver, "Sensitive",testCaseDataSd.get("SENSITIVE_VERIFY").get(0)), testCaseDataSd.get("SENSITIVE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.checkbox(driver, "Sealed",testCaseDataSd.get("SEALED_VERIFY").get(0)), testCaseDataSd.get("SEALED_VERIFY").get(0), testCaseParam, action);
				//------------------------------------- System Information ------------------------------------
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "System Information",testCaseDataSd.get("SYSTEMINFO_VERIFY").get(0)), testCaseDataSd.get("SYSTEMINFO_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created By",testCaseDataSd.get("CREATEDBY_VERIFY").get(0)), testCaseDataSd.get("CREATEDBY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created Date", testCaseDataSd.get("CREATEDDATE_VERIFY").get(0)), testCaseDataSd.get("CREATEDDATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Modified Date",testCaseDataSd.get("MODIFIEDDATE_VERIFY").get(0)), testCaseDataSd.get("MODIFIEDDATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Last Modified By",testCaseDataSd.get("LASTMODIFIEDDATE_VERIFY").get(0)), testCaseDataSd.get("LASTMODIFIEDDATE_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Cancel",testCaseDataSd.get("CANCEL_VERIFY").get(0)), testCaseDataSd.get("CANCEL_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save and New",testCaseDataSd.get("SAVEANDNEW_VERIFY").get(0)), testCaseDataSd.get("SAVEANDNEW_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);
				
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
}
