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

import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;
import uitests.testng.common.CommonOperations;
import cares.cwds.salesforce.common.utilities.Util;
import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.ScreenConstants;

public class ScDetails {

	private static final Logger logger =LoggerFactory.getLogger(ScDetails.class.getName());

	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	ReportCommon testStepDetails = new ReportCommon();
	Util util = new Util();

	String moduleName = ModuleConstants.COMMON;
	String screenName = ScreenConstants.SCREENINGDETAILS;
	
	private static final String FAILED_FORMAT = "Failed ==> {} {} ";

	public ScDetails(){ }
	
	public ScDetails(WebDriver _driver,TestCaseParam testCaseParam)   { 
		
		initializePage(_driver,testCaseParam);
		}
	
	public void initializePage(WebDriver _driver,TestCaseParam testCaseParam) 
	{
		driver = _driver;
		PageFactory.initElements(driver, this);
		ReportCommon testStepLogDetails = new ReportCommon(); 
		testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
	}

	@FindBy(xpath = "//lightning-input-rich-text[@data-name='Call_Narrative__c']//div[@part='rich-text-editor-textarea']//div")
	public WebElement narrativeClick;
	
	@FindBy(how = How.XPATH, using = "//div[@role='textbox' and @aria-label='Compose text']")
	public WebElement narrative;

	@FindBy(how = How.XPATH, using = "(//label[text()='Call Date and Time']/../../following-sibling::lightning-input//input)[1]")
	public WebElement callDateAndTime;

	@FindBy(how = How.XPATH, using = "(//label[text()='Reason for the Call']//ancestor::c//lightning-combobox//lightning-icon//span)[1]")
	public WebElement RFCdd;

	public String RFDddValue  = "//span[text()='%s']";

	@FindBy(how = How.XPATH, using = "//label[text()='Screening Name']//ancestor::c-kreator-input-text//lightning-input//input")
	public WebElement scName;

	@FindBy(how = How.XPATH, using = "//label[@class='slds-radio__label']/span[text()='Non-Mandated Reporter']")
	public WebElement callerType;

	@FindBy(how = How.XPATH, using = "(//label[text()='Call back Required']//ancestor::c-kreator-input-selection-picklist//lightning-combobox//lightning-icon//span)[1]")
	public WebElement callbackReqDD;

	public String CBRddVal = "//span[text()='%s']";
	
	@FindBy(how = How.XPATH, using = "//div[text()='Save and Proceed']")
	public WebElement save;

	
	public void SCscreeNew1(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException   {
		
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();	
		action.setPageActionName("Process Initial Screening");
		action.setPageActionDescription("Process Initial Screening");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestCaseName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

			String narrativeTD = testCaseDataSd.get("SCREENING_NARRATIVE").get(0);
			String datetime = testCaseDataSd.get("CALL_DATE_AND_TIME").get(0);
			//String dateTodayTD = testCaseDataSd.get("CALL_DT_TODAY").get(0);
			String RFCddTD = testCaseDataSd.get("RFCdd").get(0);
			String RFDddValueTD = testCaseDataSd.get("RFDddValue").get(0);
			String scNameTD = testCaseDataSd.get("scName").get(0);
			String callerTypeTD = testCaseDataSd.get("callerType").get(0);
			String callbackReqDDTD = testCaseDataSd.get("callbackReqDD").get(0);
			String CBRddValTD = testCaseDataSd.get("CBRddVal").get(0);
			String saveTD = testCaseDataSd.get("save").get(0);

			Webkeywords.instance().click(driver,  narrativeClick,narrativeTD, testCaseParam,action);
			Webkeywords.instance().setText(driver,  narrative,narrativeTD, testCaseParam,action);
			Webkeywords.instance().setDateText(driver, callDateAndTime, CommonOperations.getDate("M/d/yyyy", datetime), testCaseParam, action);		
			Webkeywords.instance().click(driver,  RFCdd,RFCddTD, testCaseParam,action);
			
			WebElement ddVal =  driver.findElement(By.xpath(format(RFDddValue,RFDddValueTD)));
			Webkeywords.instance().click(driver,  ddVal,RFDddValueTD, testCaseParam,action);
			
			Webkeywords.instance().click(driver,  scName,scNameTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  callerType,callerTypeTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  callbackReqDD,callbackReqDDTD, testCaseParam,action);
			
			WebElement val =  driver.findElement(By.xpath(format(CBRddVal,RFDddValueTD)));
			Webkeywords.instance().click(driver,  val,CBRddValTD, testCaseParam,action);
			
			Webkeywords.instance().click(driver,  save,saveTD, testCaseParam,action);
			
		}
		catch (Exception e)
		{
			logger.error(FAILED_FORMAT,action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
			throw e;
		}
	}
}
