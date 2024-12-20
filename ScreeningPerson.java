package cares.cwds.salesforce.pom.screening;


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
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;
import uitests.testng.common.CommonOperations;

import static java.lang.String.format;

public class ScreeningPerson {

	private static final Logger logger =LoggerFactory.getLogger(ScreeningPerson.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	ReportCommon reportDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	LocalDateTime startTime= LocalDateTime.now();

	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SCREENINGPERSONS;
	public ScreeningPerson(){ }

	public ScreeningPerson(WebDriver wDriver,TestCaseParam testCaseParam) 
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

	@FindBy(how = How.XPATH, using = "(//th[@data-label='Screening Person ID']//a)['%s']")
	public WebElement validatePerson;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Date of Birth']//following::lightning-input")
	public WebElement dateOfBirth;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Information']")
	public WebElement informationSection;
	
	@FindBy(how = How.XPATH, using = "//p[text()='Role']")
	public WebElement role;

	private String screeningPersonID = "//span[@title='%s']//ancestor::td//preceding-sibling::td//lightning-primitive-cell-factory[@data-label='Last Name']//span[text()='%s']//ancestor::td//preceding-sibling::td//lightning-primitive-cell-factory[@data-label='First Name']//span[text()='%s']//ancestor::td//preceding-sibling::th/lightning-primitive-cell-factory[@data-label='Screening Person ID']";

	public void navigateToScreeningPersons(TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Screening Persons");
		action.setPageActionDescription("Navigate to Screening Persons");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

			Webkeywords.instance().waitElementToBeVisible(driver,  genericLocators.button(driver, "Persons",testCaseDataSd.get("PERSONS_TAB").get(0)));
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Persons",testCaseDataSd.get("PERSONS_TAB").get(0)),testCaseDataSd.get("PERSONS_TAB").get(0), testCaseParam,action);

		}catch (Exception e) {
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}

	public void addScreeningPerson(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		action.setPageActionName("Process Add Screening Person");
		action.setPageActionDescription("Process Add Screening Person");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName,TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String role = testCaseDataSd.get("PERSON_ROLE").get(0);
			String lname = util.getRandom(testCaseDataSd.get("PERSON_LASTNAME").get(0));
			String fname = util.getRandom(testCaseDataSd.get("PERSON_FIRSTNAME").get(0));

			Webkeywords.instance().waitElementToBeVisible(driver, genericLocators.textbox(driver, "First Name",testCaseDataSd.get("PERSON_FIRSTNAME").get(0)));
			Webkeywords.instance().selectValueInputDropdown(driver,role,"Role",testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "First Name",testCaseDataSd.get("PERSON_FIRSTNAME").get(0)),fname, testCaseParam, action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Last Name",testCaseDataSd.get("PERSON_LASTNAME").get(0)), lname, testCaseParam, action);
			Webkeywords.instance().setDateText(driver, dateOfBirth, CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("DATE_OF_BIRTH").get(0)), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);

			WebElement screeningPersonId =  driver.findElement(By.xpath(format(screeningPersonID,role,lname,fname)));
			Webkeywords.instance().waitElementToBeVisible(driver, screeningPersonId);
			
			SalesforceConstants.setConstantValue("SCP_ID"+pomIteration, screeningPersonId.getText());
			SalesforceConstants.setConstantValue("personName"+pomIteration, fname+" "+lname);
					

		}catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void validateScreeningPersonPage(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		action.setPageActionName("Validate New Screening Page");
		action.setPageActionDescription("Validate New Screening Page");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Role", testCaseDataSd.get("ROLE_VERIFY").get(0)), testCaseDataSd.get("ROLE_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "First Name", testCaseDataSd.get("FIRST_NAME_VERIFY").get(0)), testCaseDataSd.get("FIRST_NAME_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Last Name", testCaseDataSd.get("LAST_NAME_VERIFY").get(0)), testCaseDataSd.get("LAST_NAME_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, dateOfBirth, testCaseDataSd.get("DATE_OF_BIRTH_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Approximate Age", testCaseDataSd.get("APPROXIMATE_AGE_VERIFY").get(0)), testCaseDataSd.get("APPROXIMATE_AGE_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "County", testCaseDataSd.get("COUNTY_VERIFY").get(0)), testCaseDataSd.get("COUNTY_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Collateral Type", testCaseDataSd.get("COLLATERAL_TYPE_VERIFY").get(0)), testCaseDataSd.get("COLLATERAL_TYPE_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().scrollUpPageToTheTop(driver);
		}catch (Exception e){
			logger.error("Failed == {} ", action.getPageActionDescription());
			reportDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			throw new CustomException("Error Occured",e);
		}
	}
		
}
