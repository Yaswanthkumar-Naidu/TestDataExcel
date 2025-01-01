package cares.cwds.salesforce.pom.folio;

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

public class FolioDisposition {
	private static final Logger logger =LoggerFactory.getLogger(FolioDisposition.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	LocalDateTime startTime= LocalDateTime.now();


	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.FOLIODISPOSITION;
	private static final String FAILURE_MSG= "Failed == {} ";

	
	private static final String DISPOSITIONS= "Dispositions";
	
	@FindBy(how = How.XPATH, using = "//span[text()='View All']//parent::a")
	public WebElement viewAllButton;

	@FindBy(how = How.XPATH, using = "//h1[text()='Disposition']")
	public WebElement dispositionHeader;
 
	String dispositioLink = "(//table[@aria-label='Disposition']//tbody/tr[%s]//records-hoverable-link)[2]";
	
	@FindBy(how = How.XPATH, using = "(//label[text()='Closure Date']/../../following-sibling::lightning-input//input)[1]")
	public WebElement closureDate;
	  
	public FolioDisposition(){ }
	
	public FolioDisposition(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	public void navigateToFolioDispositions	(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate To Folio Dispositions");
		action.setPageActionDescription("Navigate To Folio Dispositions");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String allegationsTabFolio = testCaseDataSd.get("FOLIO_DISPOSITION_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, DISPOSITIONS, allegationsTabFolio), allegationsTabFolio, testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			
		}
	}
	
	public void editDispositionInfoFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Disposition Info Folio ");
		action.setPageActionDescription("Process Disposition Info Folio");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().pause();
			Webkeywords.instance().waitElementToBeVisible(driver, viewAllButton);
			Webkeywords.instance().click(driver, viewAllButton, testCaseDataSd.get("VIEWALL").get(0), testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, dispositionHeader);
			navigateToDispositionRecord(testCaseParam, scriptIteration, pomIteration);
				
			Webkeywords.instance().pause();
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("DISPOSTION").get(0),"Disposition",testCaseParam,action);
			Webkeywords.instance().setDateText(driver, closureDate, CommonOperations.getDate("M/d/yyyy", testCaseDataSd.get("CLOSURE_DATE").get(0)), testCaseParam, action);		
			Webkeywords.instance().selectValueInputDropdown(driver,testCaseDataSd.get("DISPOSTION_CLOSURE_REASON_TYPE").get(0),"Disposition Closure Reason Type",testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textarea(driver, "Rationale",testCaseDataSd.get("RATIONALE").get(0)),util.getRandom(testCaseDataSd.get("RATIONALE").get(0)), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);		
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void verifyingDispositionFieldsFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("verifying Alligations Fields");
		action.setPageActionDescription("verifying Alligations Fields");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
				
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Child",testCaseDataSd.get("Child_VERIFY").get(0)), testCaseDataSd.get("Child_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Disposition",testCaseDataSd.get("DISPOSITION_VERIFY").get(0)), testCaseDataSd.get("DISPOSITION_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Closure Date",testCaseDataSd.get("CLOSUREDATE_VERIFY").get(0)), testCaseDataSd.get("CLOSUREDATE_VERIFY").get(0), testCaseParam, action);

				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textarea(driver, "Rationale",testCaseDataSd.get("RATIONALE_VERIFY").get(0)), testCaseDataSd.get("RATIONALE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Cancel",testCaseDataSd.get("CANCEL_VERIFY").get(0)), testCaseDataSd.get("CANCEL_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);
				
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	
	
	public void verifyDispositionStatus(TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Verify Disposition Status");
		action.setPageActionDescription("Verify Disposition Status");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);			
		
		String value = testCaseDataSd.get("STATUS_VALUE").get(0);
	//	WebElement getNewValue = driver.findElement(By.xpath(format(newValue,value)));
	//	Webkeywords.instance().verifyTextDisplayed(driver, getNewValue,testCaseDataSd.get("NEW_VALUE").get(0), testCaseParam, action);

			
		}catch (Exception e) {
			logger.error("Failed == {} ", action.getPageActionDescription());
			
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void navigateToDispositionRecord(TestCaseParam testCaseParam,String scriptIteration, String pomIteration)throws CustomException  {
		
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Disposition Record");
		action.setPageActionDescription("Navigate to Disposition Record");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String formatedXpath = format(dispositioLink,pomIteration);
			WebElement scpIdXpath = driver.findElement(By.xpath(formatedXpath));
			Webkeywords.instance().waitElementClickable(driver, scpIdXpath);
			Webkeywords.instance().jsClick(driver,  scpIdXpath, testCaseDataSd.get("DISPOSITION_RECORD").get(0), testCaseParam,action);
			
		}catch (Exception e) {
			logger.error(FAILURE_MSG, action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}	
	}
}