package cares.cwds.salesforce.pom.screening;

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

import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;
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

	@FindBy(how = How.XPATH, using = "//*[@id=\"173589001766660-1062\"]/slot/div/div/div[1]/div/c-kreator-input-rich-text-area/lightning-input-rich-text/div/div/div/div[2]/div[1]")
	public WebElement narrative;

	@FindBy(how = How.XPATH, using = "//*[@id=\"input-1102\"]")
	public WebElement datetime;

	@FindBy(how = How.XPATH, using = "//*[@id=\"173589001766660-1062\"]/slot/div/div/div[3]/div/div/div[1]/div[1]/c-kreator-input-date-time/lightning-input/lightning-datetimepicker/div/fieldset/div[1]/div/div/lightning-datepicker/div[1]/div/lightning-calendar/div/lightning-focus-trap/slot/button")
	public WebElement dateToday;

	@FindBy(how = How.XPATH, using = "//*[@id=\"combobox-button-1111\"]")
	public WebElement RFCdd;

	@FindBy(how = How.XPATH, using = "//*[@id=\"combobox-button-1111-1-1111\"]/span[2]/span")
	public WebElement RFDddValue;

	@FindBy(how = How.XPATH, using = "//*[@id=\"input-1119\"]")
	public WebElement scName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"1735890017691233-1124\"]/slot/div/div/div[1]/div/c-kreator-input-selection-radio/lightning-radio-group/fieldset/div/span[3]/label/span[1]")
	public WebElement callerType;

	@FindBy(how = How.XPATH, using = "//*[@id=\"combobox-button-1260\"]")
	public WebElement callbackReqDD;

	@FindBy(how = How.XPATH, using = "//*[@id=\"combobox-button-1260-2-1260\"]/span[2]")
	public WebElement CBRddVal;

	@FindBy(how = How.XPATH, using = "//*[@id=\"brandBand_5\"]/div/div/div/div/div[2]/span/c-cares_-generic-l-w-c-container/c-cares_screening-intake-details-flow/div/div/div/div[5]/div/div/div/c-kreator-action-buttons/button/div/div")
	public WebElement save;

	
	public void SCscreeNew1(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException   {
		
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();	
		action.setPageActionName("Process Initial Screening");
		action.setPageActionDescription("Process Initial Screening");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.getTestCaseName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);


			String narrativeTD = testCaseDataSd.get("SCREENING_NARRATIVE").get(0);
			String datetimeTD = testCaseDataSd.get("CALL_DT").get(0);
			String dateTodayTD = testCaseDataSd.get("CALL_DT_TODAY").get(0);
			String RFCddTD = testCaseDataSd.get("RFCdd").get(0);
			String RFDddValueTD = testCaseDataSd.get("RFDddValue").get(0);
			String scNameTD = testCaseDataSd.get("scName").get(0);
			String callerTypeTD = testCaseDataSd.get("callerType").get(0);
			String callbackReqDDTD = testCaseDataSd.get("callbackReqDD").get(0);
			String CBRddValTD = testCaseDataSd.get("CBRddVal").get(0);
			String saveTD = testCaseDataSd.get("save").get(0);

		
			Webkeywords.instance().setText(driver,  narrative,narrativeTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  datetime,datetimeTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  dateToday,dateTodayTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  RFCdd,RFCddTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  RFDddValue,RFDddValueTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  scName,scNameTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  callerType,callerTypeTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  callbackReqDD,callbackReqDDTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  CBRddVal,CBRddValTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  save,saveTD, testCaseParam,action);
			
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
			throw e;
		}
	}
}
