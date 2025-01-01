package cares.cwds.salesforce.pom.folio;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import cares.cwds.salesforce.web.utilities.Webkeywords;
import testsettings.TestRunSettings;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;

public class FolioCorrespondence {
	private static final Logger logger =LoggerFactory.getLogger(FolioCorrespondence.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.FOLIOCORRESPONDENCE;
	
	private static final String CORRESPONDENCE= "Correspondences";
	
	@FindBy(how = How.XPATH, using = "//label[text()='Perpetrator']/..//input")
	public WebElement perparator;
	
	public FolioCorrespondence(){ }
	
	public FolioCorrespondence(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	public void navigateToFolioCorrespondence(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate To Folio Correspondence");
		action.setPageActionDescription("Navigate To Folio Correspondence");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String correspondenceTabFolio = testCaseDataSd.get("FOLIO_CORRESPONDENCE_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, CORRESPONDENCE, correspondenceTabFolio), correspondenceTabFolio, testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
		}
	}
	
	public void addCACIReportFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process CACI Report Info Folio ");
		action.setPageActionDescription("Process CACI Report Info Folio");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().pause();
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New",testCaseDataSd.get("NEW_BTN").get(0)),testCaseDataSd.get("NEW_BTN").get(0), testCaseParam,action);
			Webkeywords.instance().waitElementClickable(driver, genericLocators.radiobutton(driver, "CACI Report", testCaseDataSd.get("CACI_REPORT").get(0)));
			Webkeywords.instance().click(driver, genericLocators.radiobutton(driver, "CACI Report", testCaseDataSd.get("CACI_REPORT").get(0)), testCaseDataSd.get("CACI_REPORT").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Next",testCaseDataSd.get("NEXT_BTN").get(0)),testCaseDataSd.get("NEXT_BTN").get(0), testCaseParam,action);
			Webkeywords.instance().pause();
			
			String perparatorTD = testCaseDataSd.get("PERPARATOR").get(0);
			Webkeywords.instance().click(driver, perparator,SalesforceConstants.getConstantValue(perparatorTD), testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, SalesforceConstants.getConstantValue(perparatorTD),perparatorTD),perparatorTD, testCaseParam,action);
			
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Name or Unit Within County?",testCaseDataSd.get("NAME_UNIT_WITHIN_COUNTY").get(0)),util.getRandom(testCaseDataSd.get("NAME_UNIT_WITHIN_COUNTY").get(0)), testCaseParam, action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Return Address Within County?",testCaseDataSd.get("RETURN_ADDRESS_WITHIN_COUNTY").get(0)),util.getRandom(testCaseDataSd.get("RETURN_ADDRESS_WITHIN_COUNTY").get(0)), testCaseParam, action);
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("PHOTO_TAKEN_OFSUBSTANTIATED_ALLEGATION").get(0),"Photo taken of Substantiated Allegation?",testCaseParam,action);
			Webkeywords.instance().scrollIntoViewElement(driver,  genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)));
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);		
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void addCrossReportSCARFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process CrossRepor SCAR  Info Folio ");
		action.setPageActionDescription("Process CrossRepor SCAR Info Folio");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().pause();
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New",testCaseDataSd.get("NEW_BTN").get(0)),testCaseDataSd.get("NEW_BTN").get(0), testCaseParam,action);
			Webkeywords.instance().waitElementClickable(driver, genericLocators.radiobutton(driver, "Cross Report - Suspected Child Abuse Report (SCAR)", testCaseDataSd.get("CROSSREPORT_SCAR").get(0)));
			
			Webkeywords.instance().click(driver, genericLocators.radiobutton(driver, "Cross Report - Suspected Child Abuse Report (SCAR)", testCaseDataSd.get("CROSSREPORT_SCAR").get(0)), testCaseDataSd.get("CROSSREPORT_SCAR").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Next",testCaseDataSd.get("NEXT_BTN").get(0)),testCaseDataSd.get("NEXT_BTN").get(0), testCaseParam,action);
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("STATUS").get(0),"Status",testCaseParam,action);
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("AGENCY_TYPE").get(0),"Agency Type",testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Agency Name", testCaseDataSd.get("AGENCY_NAME").get(0)), util.getRandom(testCaseDataSd.get("AGENCY_NAME").get(0)), testCaseParam, action);
			Webkeywords.instance().scrollIntoViewElement(driver,  genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)));
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);			
		}	
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
}