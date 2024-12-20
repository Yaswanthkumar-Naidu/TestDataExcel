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
import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.GenericLocators;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import testsettings.TestRunSettings;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;

public class Allegations {
	private static final Logger logger =LoggerFactory.getLogger(Allegations.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	
	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SCREENINGALLEGATIONS;
	
	private static final String ALLEGATION= "Allegations";
	
	@FindBy(how = How.XPATH, using = "//label[text()='Alleged Perpetrator']/..//input")
	public WebElement allegedPerpetrator;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Alleged Victim']/..//input")
	public WebElement allegedVictim;
	
	@FindBy(xpath = "//*[@title='Allegation ID']/..//lightning-formatted-text")
	WebElement scrAllegationID;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Allegation Subtype']/..//input")
	public WebElement allegationSubtype;
		
	String searchValue ="//label[text()='%s']//parent::div//span[text()='%s']";
	
	
	public Allegations(){ }
	
	public Allegations(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	@FindBy(how = How.XPATH, using = "//li[@data-target-selection-name='sfdc:CustomButton.Screening_Allegation__c.New_Screening_Folio_Allegation']//button")
	public WebElement allegationsNew;	
	
	public void navigateToScreeningAllegations	(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate To Screening Allegations");
		action.setPageActionDescription("Navigate To Screening Allegations");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String allegationsTabTD = testCaseDataSd.get("ALLEGATIONS_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, ALLEGATION, allegationsTabTD), allegationsTabTD, testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
		}
	}

	
	public void addAllegationsInfo(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Allegations");
		action.setPageActionDescription("Process Allegations");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String allegationsTabTD = testCaseDataSd.get("ALLEGATIONS_TAB").get(0);
			String allegedPerpetratorTD = testCaseDataSd.get("ALLEGED_PERPETRATOR").get(0);
			String allegedVictimTD = testCaseDataSd.get("ALLEGED_VICTIM").get(0);

			Webkeywords.instance().click(driver, genericLocators.link(driver, ALLEGATION, allegationsTabTD),  allegationsTabTD, testCaseParam,action);
			Webkeywords.instance().waitElementToBeVisible(driver, allegationsNew);
			Webkeywords.instance().click(driver, allegationsNew,testCaseDataSd.get("NEW_BTN").get(0), testCaseParam,action);
			Webkeywords.instance().waitElementToBeVisible(driver, allegationSubtype);
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("ALLEGATION_TYPE").get(0),"Allegation Type",testCaseParam,action);
			
			Webkeywords.instance().click(driver, allegationSubtype,testCaseDataSd.get("ALLEGATION_SUBTYPE").get(0), testCaseParam,action);
			WebElement we = driver.findElement(By.xpath(format(searchValue,"Allegation Subtype",testCaseDataSd.get("ALLEGATION_SUBTYPE").get(0))));	
			Webkeywords.instance().jsClick(driver,we,testCaseDataSd.get("ALLEGATION_SUBTYPE").get(0), testCaseParam,action);
			
			Webkeywords.instance().click(driver, allegedPerpetrator,SalesforceConstants.getConstantValue(allegedPerpetratorTD), testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, SalesforceConstants.getConstantValue(allegedPerpetratorTD),allegedPerpetratorTD),allegedPerpetratorTD, testCaseParam,action);
			Webkeywords.instance().click(driver, allegedVictim,SalesforceConstants.getConstantValue(allegedVictimTD), testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, SalesforceConstants.getConstantValue(allegedVictimTD),allegedVictimTD),allegedVictimTD, testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);
			//.verifyToastMessage(driver,testCaseDataSd.get("TOAST_MSG_VERIFY").get(0),testCaseParam, action);	
			Webkeywords.instance().waitElementToBeVisible(driver, scrAllegationID);
			//Webkeywords.instance().verifyElementDisplayed(driver, scrAllegationID, testCaseDataSd.get("SCR_ALLEGATIONID_VERIFY").get(0), testCaseParam, action);
			SalesforceConstants.setConstantValue("SCR_ALLEGATIONID"+pomIteration, scrAllegationID.getText());
			
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void verifyingAlligationsFields(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("verifying Alligations Fields");
		action.setPageActionDescription("verifying Alligations Fields");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
				
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Allegation Details",testCaseDataSd.get("ALLEGATIONS_DETAILS_VERIFY").get(0)), testCaseDataSd.get("ALLEGATIONS_DETAILS_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Allegation Type",testCaseDataSd.get("ALLEGATIONS_TYPE_VERIFY").get(0)), testCaseDataSd.get("ALLEGATIONS_TYPE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Allegation Subtype",testCaseDataSd.get("ALLEGATIONS_SUBTYPE_VERIFY").get(0)), testCaseDataSd.get("ALLEGATIONS_SUBTYPE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Alleged Victim",testCaseDataSd.get("ALLEGEDVICTIM_VERIFY").get(0)), testCaseDataSd.get("ALLEGEDVICTIM_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Alleged Perpetrator",testCaseDataSd.get("ALLEGEDPREPETRTOR_VERIFY").get(0)), testCaseDataSd.get("ALLEGEDPREPETRTOR_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Allegation Type Definition information",testCaseDataSd.get("ALLEGATIONSTYPEDEFINITION_VERIFY").get(0)), testCaseDataSd.get("ALLEGATIONSTYPEDEFINITION_VERIFY").get(0), testCaseParam, action);
				
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Cancel",testCaseDataSd.get("CANCEL_VERIFY").get(0)), testCaseDataSd.get("CANCEL_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);
		
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
}
