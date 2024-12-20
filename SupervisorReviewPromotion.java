package cares.cwds.salesforce.pom;

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
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import testsettings.TestRunSettings;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;

public class SupervisorReviewPromotion {
	private static final Logger logger =LoggerFactory.getLogger(SupervisorReviewPromotion.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SUPERVISORREVIEWPROMOTION;
	
	private static final String SUPERVISORREVIEW_PROMOTION= "Supervisor Review/Promotion";
	private static final String APPROVALSUPERVISOR= "Approval Supervisor";
	private static final String COUNTYFINALDETERMINATION= "County Final Determination";
	private static final String PATHWAY= "Pathway";
	private static final String SAVE= "Save";
	
	@FindBy(how = How.XPATH, using = "//label[text()='Primary Worker']/..//input")
	public WebElement primaryWorkerDD;
	
	@FindBy(xpath = "//*[text()='Folio Ref.']/..//a/span")
	WebElement folioRefID;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@aria-label, 'Office')]")
	public WebElement officeDD;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@aria-label, 'Unit')]")
	public WebElement unitDD;
	
	public SupervisorReviewPromotion(){ }
	
	public SupervisorReviewPromotion(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	
	
	public void navigateToSupervisorReviewPromotion(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to Supervisor Review Promotion");
		action.setPageActionDescription("Navigate to Supervisor Review Promotion");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);				
			
		
			Webkeywords.instance().waitElementToBeVisible(driver, genericLocators.link(driver, SUPERVISORREVIEW_PROMOTION, testCaseDataSd.get("SUPERVISOR_REVIEW_TAB").get(0)));
			Webkeywords.instance().click(driver, genericLocators.link(driver, SUPERVISORREVIEW_PROMOTION, testCaseDataSd.get("SUPERVISOR_REVIEW_TAB").get(0)), testCaseDataSd.get("SUPERVISOR_REVIEW_TAB").get(0), testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			 throw new CustomException(e.getMessage());
		}
	}
	
	
	
	public void addSupervisorReviewPromotionInfo(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Supervisor Review Promotion");
		action.setPageActionDescription("Process Supervisor Review Promotion");
		try {
			
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

			String approvalSupervisor = SalesforceConstants.getUserName(testCaseDataSd.get("APPROVAL_SUPERVISOR").get(0));
			String approvalSupervisorLink = testCaseDataSd.get("APPROVAL_SUPERVISOR_LINK").get(0);
			String countyFinalDetermination = testCaseDataSd.get("COUNTY_FINAL_DETERMINATION").get(0);
			String pathway = testCaseDataSd.get("PATHWAY").get(0);
			String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
			
			Webkeywords.instance().scrollUpPageToTheTop(driver);		
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, APPROVALSUPERVISOR,approvalSupervisor),approvalSupervisor, testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, approvalSupervisor,approvalSupervisorLink),approvalSupervisorLink,
					testCaseParam,action);
			Webkeywords.instance().selectValueInputDropdown(driver,countyFinalDetermination,COUNTYFINALDETERMINATION,testCaseParam,action);	
			Webkeywords.instance().selectValueInputDropdown(driver,pathway,PATHWAY,testCaseParam,action);
		
			Webkeywords.instance().click(driver, genericLocators.button(driver, SAVE,saveBtn), saveBtn, testCaseParam, action);
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			 throw new CustomException(e.getMessage());
		}
	}
	
	public void setPrimaryWorker(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Set Primary Worker");
		action.setPageActionDescription("Set Primary Worker");
		try {
			
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);

			Webkeywords.instance().click(driver, genericLocators.link(driver, SUPERVISORREVIEW_PROMOTION,testCaseDataSd.get("SUPERVISOR_REVIEW_TAB").get(0)), testCaseDataSd.get("SUPERVISOR_REVIEW_TAB").get(0), testCaseParam,action);
			String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
			String primaryWorkerTD = testCaseDataSd.get("PRIMARY_WORKER").get(0);
			String primaryWorker = "";
			if(!(testCaseDataSd.get("PRIMARY_WORKER").get(0).equalsIgnoreCase("n/a"))) 
				 primaryWorker = SalesforceConstants.getUserName(testCaseDataSd.get("PRIMARY_WORKER").get(0));
			else
				 primaryWorker = testCaseDataSd.get("PRIMARY_WORKER").get(0);
			
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("COUNTY").get(0),"County",testCaseParam,action);	

			Webkeywords.instance().click(driver, officeDD,testCaseDataSd.get("OFFICE").get(0), testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Office",testCaseDataSd.get("OFFICE").get(0)),testCaseDataSd.get("OFFICE").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, testCaseDataSd.get("OFFICE").get(0),testCaseDataSd.get("OFFICE").get(0)), testCaseDataSd.get("OFFICE").get(0), testCaseParam,action);
			
			Webkeywords.instance().click(driver, unitDD,testCaseDataSd.get("UNIT").get(0), testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Unit",testCaseDataSd.get("UNIT").get(0)),testCaseDataSd.get("UNIT").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, testCaseDataSd.get("UNIT").get(0),testCaseDataSd.get("UNIT").get(0)), testCaseDataSd.get("UNIT").get(0), testCaseParam,action);

			Webkeywords.instance().click(driver, primaryWorkerDD,primaryWorkerTD, testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, primaryWorker,primaryWorkerTD), primaryWorkerTD, testCaseParam,action);
		
			Webkeywords.instance().click(driver, genericLocators.button(driver, SAVE,saveBtn), saveBtn, testCaseParam, action);
			SalesforceCommon.verifyToastMessage(driver,testCaseDataSd.get("TOAST_MSG_VERIFY").get(0),testCaseParam, action);
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().refresh(driver, testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver,folioRefID );
		   	Webkeywords.instance().verifyElementDisplayed(driver, folioRefID, testCaseDataSd.get("FOLIOREF_ID_VERIFY").get(0), testCaseParam, action);
			SalesforceConstants.setConstantValue("FOLIO ID", folioRefID.getText());
			SalesforceCommon.captureRecordURL(driver,"FOLIO");  
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			 throw new CustomException(e.getMessage());
		}
	}
}
