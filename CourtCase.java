package cares.cwds.salesforce.pom.courts;

import java.util.ArrayList;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.utilities.common.TestRunSettings;
import cares.cwds.salesforce.utilities.common.Util;
import cares.cwds.salesforce.utilities.reports.common.ReportCommon;
import cares.cwds.salesforce.utilities.reports.extentmodel.PageDetails;
import cares.cwds.salesforce.utilities.reports.model.TestCaseParam;
import cares.cwds.salesforce.utilities.testng.TestNGCommon;
import cares.cwds.salesforce.utilities.web.CommonOperations;
import cares.cwds.salesforce.utilities.web.GenericLocators;
import cares.cwds.salesforce.utilities.web.Webkeywords;

public class CourtCase {

	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	TestNGCommon testngCommon = new TestNGCommon();
	TestCaseParam testCaseParam = (TestCaseParam) testngCommon.getTestAttribute("testCaseParam");
	String moduleName = ModuleConstants.COURTS;
	String screenName = ScreenConstants.COURTCASE;
	

public CourtCase(){ }
	
	public CourtCase(WebDriver wDriver)
	{
		initializePage(wDriver);
	}

	public void initializePage(WebDriver wDriver) 
    {
    	 driver = wDriver;
         PageFactory.initElements(driver, this);
         ReportCommon testStepLogDetails = new ReportCommon(); 
         testStepLogDetails.logModuleAndScreenDetails( moduleName, screenName);
         genericLocators = new GenericLocators(wDriver);
    }
	
	@FindBy(how = How.XPATH, using = "(//label[text()='Start Date']/../../following-sibling::lightning-input//input)[1]")
	public WebElement startDate;

	@FindBy(how = How.XPATH, using = "(//label[text()='End Date']/../../following-sibling::lightning-input//input)[1]")
	public WebElement endDate;


	@FindBy(how = How.XPATH, using = "(//label[text()='System Closed']/../../following-sibling::lightning-input//input)[1]")
	public WebElement systemClosed;

		
	public void navigateToCourtCase(String scriptIteration, String pomIteration){

		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Court Case");
		action.setPageActionDescription("Navigate to Court Case");

		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

		Webkeywords.instance().click(driver, genericLocators.button(driver, "Court Record", testCaseDataSd.get("COURT_RECORD_EXPAND").get(0)), testCaseDataSd.get("COURT_RECORD_EXPAND").get(0),action);
		Webkeywords.instance().pauseDelay();
		Webkeywords.instance().click(driver, genericLocators.button(driver, "Court Case", testCaseDataSd.get("COURT_CASE").get(0)), testCaseDataSd.get("COURT_CASE").get(0),action);
		Webkeywords.instance().pause();
     }
	
	
	public void courtCaseInformation(String scriptIteration, String pomIteration){

		PageDetails action = new PageDetails();
		action.setPageActionName("Court Case Information");
		action.setPageActionDescription("Court Case Information");

		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
	
		String assignedSocialWorker = SalesforceConstants.getUserName(testCaseDataSd.get("ASSIGNED_SOCIAL_WORKER").get(0));
		String assignedSocialWorkerLink = testCaseDataSd.get("ASSIGNED_SOCIAL_WORKER_LINK").get(0);
	
		
		Webkeywords.instance().click(driver, genericLocators.button(driver, "New", testCaseDataSd.get("NEW_BTN").get(0)), testCaseDataSd.get("NEW_BTN").get(0),action);
		Webkeywords.instance().pause();
		
		Webkeywords.instance().setText(driver,
				genericLocators.textbox(driver, "Dependency Court Case Number", testCaseDataSd.get("DEPENDENCY_COURT_CASE_NUMBER").get(0)),
				util.getRandom(testCaseDataSd.get("DEPENDENCY_COURT_CASE_NUMBER").get(0)), action);
		Webkeywords.instance().setText(driver,
				genericLocators.textbox(driver, "Attorney Name", testCaseDataSd.get("ATTORNEY_NAME").get(0)),
				util.getRandom(testCaseDataSd.get("ATTORNEY_NAME").get(0)), action);
		Webkeywords.instance().setText(driver,
				genericLocators.textbox(driver, "Dellnquency Court Case Number", testCaseDataSd.get("DELLNQUENCY_COURT_CASE_NUMBER").get(0)),
				util.getRandom(testCaseDataSd.get("DELLNQUENCY_COURT_CASE_NUMBER").get(0)), action);
		Webkeywords.instance().setText(driver,
				genericLocators.textbox(driver, "Reason to Reopen", testCaseDataSd.get("REASON_TO_REOPEN").get(0)),
				util.getRandom(testCaseDataSd.get("REASON_TO_REOPEN").get(0)), action);

		Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Assigned Social Worker",assignedSocialWorker),assignedSocialWorker, action);
		Webkeywords.instance().click(driver, genericLocators.link(driver, assignedSocialWorker,assignedSocialWorkerLink),assignedSocialWorkerLink,action);
		
		Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("CONTACT METHOD").get(0),"Contact Method", action);
		Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("STATUS").get(0),"Status", action);
		
	
		Webkeywords.instance().setDateText(driver, startDate, CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("START_DATE").get(0)), action);		
		Webkeywords.instance().setDateText(driver, endDate, CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("END_DATE").get(0)), action);		
		Webkeywords.instance().setDateText(driver, systemClosed, CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("SYSTEM_CLOSED").get(0)), action);		
		
		Webkeywords.instance().click(driver, genericLocators.button(driver, "SAVE",testCaseDataSd.get("SAVE_BTN").get(0)),testCaseDataSd.get("SAVE_BTN").get(0),action);
		Webkeywords.instance().pause();
		WebElement courtCaseID = genericLocators.recordID(driver, "Case Ref.", testCaseDataSd.get("SAVE_BTN").get(0));
		Webkeywords.instance().waitElementToBeVisible(driver, courtCaseID);
		
     	SalesforceConstants.setConstantValue("COURT_CASE_ID"+pomIteration, courtCaseID.getText());
	
	}
}
