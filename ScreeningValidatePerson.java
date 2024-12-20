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
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;

public class ScreeningValidatePerson {

	private static final Logger logger =LoggerFactory.getLogger(ScreeningValidatePerson.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	LocalDateTime startTime= LocalDateTime.now();

	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SCREENINGVALIDATEPERSON;
	private static final String FAILURE_MSG= "Failed == {} ";

	public ScreeningValidatePerson(){ }
	
	public ScreeningValidatePerson(WebDriver wDriver,TestCaseParam testCaseParam) 
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
	
	@FindBy(how = How.XPATH, using = "//p[@title='Screening ID']//parent::div//a")
	public WebElement clickScreeningIDLink;
	
	@FindBy(how = How.XPATH, using = "//*[@title='Validated Person Name']/..//following::p//slot//descendant::slot//span//slot")
	public WebElement  validatedPersonName;
	
	@FindBy(how = How.XPATH, using = "//span[text()='View All']//parent::a")
	public WebElement viewAllButton;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Screening Persons']")
	public WebElement ScreenPersonHeader;

    @FindBy(how = How.XPATH, using = "//span[text()='Screening Person ID']//parent::a")
	public WebElement ScreeningPersonTitle;
	
    String scrPersonLink = "(//slot[text()='%s']/../../..//parent::a)[2]";
    
    String validatedPerson = "//a[@title='%s']";
	
	//Pass Screening Person Id that is saved in SalesforceConstant in the format SCP_ID+screenIterationNum
	public void validatePersonDetails(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		action.setPageActionName("Validate Screening Person");
		action.setPageActionDescription("Validate Screening Person");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String personId = SalesforceConstants.getConstantValue(testCaseDataSd.get("SCP_PERSON_ID").get(0));
			String scpPersonId = extractPersonText(personId);
			
						
			Webkeywords.instance().scrollIntoViewElement(driver, ScreeningPersonTitle);
			Webkeywords.instance().click(driver, viewAllButton, testCaseDataSd.get("VIEWALL").get(0), testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, ScreenPersonHeader);
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			navigateToScreeningPerson(scpPersonId, testCaseParam, scriptIteration, pomIteration);
			navigateToValidatePersonTab(testCaseParam,scriptIteration, pomIteration);
			
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Search",testCaseDataSd.get("SEARCH_BTN").get(0)),  testCaseDataSd.get("SEARCH_BTN").get(0), testCaseParam, action);	
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("SEXATBIRTH").get(0),"Sex at Birth",testCaseParam,action);
			Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("GENDER_IDENTITY").get(0),"Gender Identity",testCaseParam,action);
			
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New Person",testCaseDataSd.get("NEWPERSON_BTN").get(0)), testCaseDataSd.get("NEWPERSON_BTN").get(0), testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, genericLocators.textOnPage(driver, "Validated Person Name",testCaseDataSd.get("VALIDATEPERSON_NAME").get(0)));
			} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void navigateToScreeningPerson(String scpPersonId, TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
	
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Screening Person");
		action.setPageActionDescription("Navigate to Screening Person");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String formatedXpath = format(scrPersonLink,scpPersonId);
			WebElement scpIdXpath = driver.findElement(By.xpath(formatedXpath));
			Webkeywords.instance().waitElementClickable(driver, scpIdXpath);
			Webkeywords.instance().jsClick(driver,  scpIdXpath, testCaseDataSd.get("SCP_PERSON_ID").get(0), testCaseParam,action);
			
		}catch (Exception e) {
			logger.error(FAILURE_MSG, action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}	
	}
		
	public void navigateToValidatePersonTab(TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Validate Person");
		action.setPageActionDescription("Navigate to Validate Person");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().waitElementToBeVisible(driver, genericLocators.button(driver, "Validate Person",testCaseDataSd.get("VALIDATEPERSON_TAB").get(0)));
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Validate Person",testCaseDataSd.get("VALIDATEPERSON_TAB").get(0)), testCaseDataSd.get("VALIDATEPERSON_TAB").get(0), testCaseParam,action);
		}catch (Exception e) {
			logger.error(FAILURE_MSG, action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}	
	}
	
	public String extractPersonText(String value) {
		String st = value;
		String personId = "";
		int startIndex = st.indexOf("SCP-");
		if (startIndex != -1) {
			
			int endIndex = st.indexOf(" ",startIndex);
			if (endIndex == -1 ) {
			endIndex = st.length();
			}
			personId = st.substring(startIndex, endIndex);
			logger.info("Screening Person ID is {}" , personId);
		}
		return personId	;
	}
	
	public void editCreatedScreeningPerson(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		action.setPageActionName("Process Add Screening Person");
		action.setPageActionDescription("Process Add Screening Person");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName,TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("SEXATBIRTH").get(0),"Sex at Birth",testCaseParam,action);
			Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get("GENDER_IDENTITY").get(0),"Gender Identity",testCaseParam,action);
	
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);


		}catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}

	public void navigateToValidatedPerson(TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
	PageDetails action = new PageDetails();
	action.setPageActionName("Navigate to Validated Person");
	action.setPageActionDescription("Navigate to Validated Person");
	try {
		Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
		Webkeywords.instance().click(driver,  driver.findElement(By.xpath(format(validatedPerson,SalesforceConstants.getConstantValue("personName"+pomIteration)))), testCaseDataSd.get("VALIDATEPERSON_TAB").get(0), testCaseParam,action);
	}catch (Exception e) {
		logger.error(FAILURE_MSG, action.getPageActionDescription());
		exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
	}	

	}

public void validatePersonNonEditable(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		action.setPageActionName("Validate Screening Person Non Editable");
		action.setPageActionDescription("Validate Screening Person Non Editable");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String personId = SalesforceConstants.getConstantValue(testCaseDataSd.get("SCP_PERSON_ID").get(0));
			String scpPersonId = extractPersonText(personId);
			
						
			Webkeywords.instance().scrollIntoViewElement(driver, ScreeningPersonTitle);
			Webkeywords.instance().click(driver, viewAllButton, testCaseDataSd.get("VIEWALL").get(0), testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, ScreenPersonHeader);
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			navigateToScreeningPerson(scpPersonId, testCaseParam, scriptIteration, pomIteration);
			navigateToValidatePersonTab(testCaseParam,scriptIteration, pomIteration);
			
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Search",testCaseDataSd.get("SEARCH_BTN").get(0)),  testCaseDataSd.get("SEARCH_BTN").get(0), testCaseParam, action);		
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New Person",testCaseDataSd.get("NEWPERSON_BTN").get(0)), testCaseDataSd.get("NEWPERSON_BTN").get(0), testCaseParam, action);
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
}
