package cares.cwds.salesforce.pom.courts;


import static java.lang.String.format;

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

public class CourtHearings {
	private static final Logger logger =LoggerFactory.getLogger(CourtHearings.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	TestNGCommon testngCommon = new TestNGCommon();
	TestCaseParam testCaseParam = (TestCaseParam) testngCommon.getTestAttribute("testCaseParam");

	String moduleName = ModuleConstants.COURTS;
	String screenName = ScreenConstants.COURTHEARINGS;
	
	public CourtHearings(WebDriver wDriver)
	{
		initializePage(wDriver);
	}
	
	public void initializePage(WebDriver wDriver) 
	    {
			logger.info(this.getClass().getName());
	    	 driver = wDriver;
	         PageFactory.initElements(driver, this);
	         ReportCommon testStepLogDetails = new ReportCommon(); 
	         testStepLogDetails.logModuleAndScreenDetails( moduleName, screenName);
	         genericLocators = new GenericLocators(wDriver);
	    }
	
	@FindBy(how = How.XPATH, using = "//*[@apiname='New_Hearing_Investigative']//button")
	public WebElement hearingsNewBtn;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Hearings']//parent::span[@class='view-all-label']")
	public WebElement hearingViewAllButton;
	
	String hearingLink = "(//span[text()='%s']";
	
	
	public void enterHearingDetails( String scriptIteration, String pomIteration){

		PageDetails action = new PageDetails();
		action.setPageActionName("Enter Hearing Details");
		action.setPageActionDescription("Enter Hearing Details");
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			
			String typeTD = testCaseDataSd.get("TYPE").get(0);
			String hearingSubtypeTD = testCaseDataSd.get("SUBTYPE").get(0);
			String hearingDateTimeTD = testCaseDataSd.get("HEARING_DATE_TIME").get(0);
			String otherHearingSubtypeTD = testCaseDataSd.get("OTHER_HEARING_SUBTYPE").get(0);
			String statusTD = testCaseDataSd.get("STATUS").get(0);
			String notesTD = testCaseDataSd.get("NOTES").get(0);
			String saveBtn =testCaseDataSd.get("SAVE_BTN").get(0);
			String hearingtIDTD =testCaseDataSd.get("CAPTURE_HEARING_ID").get(0);

			Webkeywords.instance().waitElementClickable(driver, hearingsNewBtn);
			Webkeywords.instance().click(driver, hearingsNewBtn, testCaseDataSd.get("NEW_BTN").get(0),action);
			
			Webkeywords.instance().selectValueInputDropdown(driver,typeTD,"Type",action);
			Webkeywords.instance().selectValueInputDropdown(driver,hearingSubtypeTD,"Hearing Subtype",action);
			Webkeywords.instance().setDateText(driver, genericLocators.datetextbox(driver, "Hearing Date and Time",hearingDateTimeTD), CommonOperations.getDate("M/d/yyyy", hearingDateTimeTD), action);			
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Other Hearing Subtype",otherHearingSubtypeTD),otherHearingSubtypeTD, action);
			Webkeywords.instance().selectValueInputDropdown(driver,statusTD,"Status",action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Notes",notesTD),notesTD, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",saveBtn), saveBtn, action);
			Webkeywords.instance().pause();
			WebElement hearingID= genericLocators.recordID(driver, "Court Work Item ID", hearingtIDTD);
			
			SalesforceConstants.setConstantValue("HEARING_ID"+pomIteration, hearingID.getText());
	}
	
	public void navigateToHearingRecord(String scriptIteration, String pomIteration){
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Hearing Record");
		action.setPageActionDescription("Navigate to Hearing Record");
		
		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		
		String hearingId = SalesforceConstants.getConstantValue(testCaseDataSd.get("HEARING_ID").get(0));
		Webkeywords.instance().scrollIntoViewElement(driver, hearingViewAllButton);
		Webkeywords.instance().click(driver, hearingViewAllButton, testCaseDataSd.get("VIEWALL").get(0), action);
		Webkeywords.instance().refreshPage(driver);
		Webkeywords.instance().pause();
		String formatedXpath = format(hearingLink,hearingId);
		WebElement hearingIdXpath = driver.findElement(By.xpath(formatedXpath));
		Webkeywords.instance().waitElementClickable(driver, hearingIdXpath);
		Webkeywords.instance().jsClick(driver, hearingIdXpath, testCaseDataSd.get("HEARING_ID").get(0),action);
		Webkeywords.instance().pause();	
	}
	
	public void editHearingRecord(String scriptIteration, String pomIteration){
		PageDetails action = new PageDetails();
		action.setPageActionName("Edit Hearing Record");
		action.setPageActionDescription("Edit Hearing Record");

		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		String editSaveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
		
		Webkeywords.instance().setDateText(driver, genericLocators.datetextbox(driver, "Hearing Date and Time",testCaseDataSd.get("HEARING_DATE_TIME").get(0)), CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("HEARING_DATE_TIME").get(0)), action);			
		Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("STATUS").get(0),"Status",action);
		
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Official Next Hearing Date and Time",testCaseDataSd.get("OFFICIAL_NEXT_HEARING_DATE_AND_TIME_VERIFY").get(0)), testCaseDataSd.get("OFFICIAL_NEXT_HEARING_DATE_AND_TIME_VERIFY").get(0), action);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Reason for Contested",testCaseDataSd.get("REASON_FOR_CONTESTED_VERIFY").get(0)), testCaseDataSd.get("REASON_FOR_CONTESTED_VERIFY").get(0), action);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Contested by",testCaseDataSd.get("CONTESTEDBY_VERIFY").get(0)), testCaseDataSd.get("CONTESTEDBY_VERIFY").get(0), action);
		
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Reason for Continuance",testCaseDataSd.get("REASON_FOR_CONTINUANCE_VERIFY").get(0)), testCaseDataSd.get("REASON_FOR_CONTINUANCE_VERIFY").get(0), action);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Continued hearing requested by",testCaseDataSd.get("CONTINUANCE_HEARING_REQUESTBY_VERIFY").get(0)), testCaseDataSd.get("CONTINUANCE_HEARING_REQUESTBY_VERIFY").get(0), action);
		
		Webkeywords.instance().setDateText(driver, genericLocators.datetextbox(driver, "Official Next Hearing Date and Time",testCaseDataSd.get("OFFICIAL_NEXT_HEARING_DATE_AND_TIME").get(0)), CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("OFFICIAL_NEXT_HEARING_DATE_AND_TIME").get(0)), action);			
		Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Reason for Contested",testCaseDataSd.get("REASON_FOR_CONTESTED").get(0)), testCaseDataSd.get("REASON_FOR_CONTESTED").get(0), action);			
		Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Contested by",testCaseDataSd.get("CONTESTEDBY").get(0)), testCaseDataSd.get("CONTESTEDBY").get(0), action);			
		
		Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("REASON_FOR_CONTESTED").get(0),"Reason for Contested",action);
		Webkeywords.instance().setText(driver, genericLocators.textOnPage(driver, "Continued hearing requested by",testCaseDataSd.get("CONTINUANCE_HEARING_REQUESTBY_VERIFY").get(0)), testCaseDataSd.get("CONTINUANCE_HEARING_REQUESTBY_VERIFY").get(0), action);
	
		Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Reason Hearing was Vacated",testCaseDataSd.get("REASON_HEARING_WAS_VACATED").get(0)), testCaseDataSd.get("REASON_HEARING_WAS_VACATED").get(0), action);			
		Webkeywords.instance().setDateText(driver, genericLocators.datetextbox(driver, "Date Hearing was Vacated by the Court",testCaseDataSd.get("DATE_HEARING_WAS_VACATED").get(0)), CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("DATE_HEARING_WAS_VACATED").get(0)), action);			

		Webkeywords.instance().click(driver,genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)),editSaveBtn, action);	
	}
	
	public void verifySectionsInHearingDetails(String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();
		action.setPageActionName("Verify Sections");
		action.setPageActionDescription("Verify Sections");
		
		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Hearing Information",testCaseDataSd.get("HEARING_INFORMATION").get(0)), testCaseDataSd.get("HEARING_INFORMATION").get(0), action);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Superior Court Information",testCaseDataSd.get("SUPERIOR_COURT_INFORMATION").get(0)), testCaseDataSd.get("SUPERIOR_COURT_INFORMATION").get(0), action);
		Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Superior Court Address Information",testCaseDataSd.get("SUPERIOR_COURT_ADDRESS_INFORMATION").get(0)), testCaseDataSd.get("SUPERIOR_COURT_ADDRESS_INFORMATION").get(0), action);

	}
	
	public void navigateToHearingDetailTab(String scriptIteration, String pomIteration){
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Hearing Details");
		action.setPageActionDescription("Navigate to Hearing Details");
		
		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		
		Webkeywords.instance().click(driver,genericLocators.button(driver, "Hearing Details", testCaseDataSd.get("HEARING_DETAILS)_TAB").get(0)), testCaseDataSd.get("HEARING_DETAILS)_TAB").get(0), action);			
	}
}