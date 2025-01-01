package cares.cwds.salesforce.pom.folio;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.GenericLocators;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import cares.cwds.salesforce.web.utilities.Webkeywords;
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;

public class FolioDocuments {
	private static final Logger logger =LoggerFactory.getLogger(FolioDocuments.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.FOLIODOCUMENTS;
		
	private static final String FAILURE_MSG= "Failed == {} ";
	
	@FindBy(how = How.XPATH, using = "//label[text()='Document Type']//parent::div//button")
	public WebElement documentType;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Document Type']//parent::div//lightning-base-combobox-item/span/span")
	public List<WebElement> documentTypeList;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'file-selector')]//span[@part='button']")
	public WebElement uploadfiles;


public FolioDocuments(){ }
	
	public FolioDocuments(WebDriver wDriver,TestCaseParam testCaseParam) {
		initializePage(wDriver,testCaseParam);
	}
	
	public void initializePage(WebDriver wDriver,TestCaseParam testCaseParam) {
	    	 driver = wDriver;
	         PageFactory.initElements(driver, this);
	         ReportCommon testStepLogDetails = new ReportCommon(); 
	         testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
	         genericLocators = new GenericLocators(wDriver);
	        		 
	         
	    }
	
	@FindBy(how = How.XPATH, using = "//span[text()='Show actions']//parent::button")
	public WebElement showaction;
	
	public void navigateToDocuments(TestCaseParam testCaseParam, String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to Folio Documents");
		action.setPageActionDescription("Navigate to Folio Documents");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Documents",testCaseDataSd.get("DOCUMENTS_TAB").get(0)),testCaseDataSd.get("DOCUMENTS_TAB").get(0), testCaseParam, action);
		}catch (Exception e) {
			logger.error(FAILURE_MSG , action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void generateDocument(TestCaseParam testCaseParam, String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Generate Document");
		action.setPageActionDescription("Generate Document");
		try {
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().click(driver,genericLocators.button(driver, "Generate Document",testCaseDataSd.get("GENERATE_BTN").get(0)) ,testCaseDataSd.get("GENERATE_BTN").get(0), testCaseParam,action);		
			Webkeywords.instance().refresh(driver, testCaseParam, action);
			Webkeywords.instance().pause();
		}catch (Exception e) {
			logger.error(FAILURE_MSG , action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	
	public void validateDocumentsList(TestCaseParam testCaseParam, String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Generate Document");
		action.setPageActionDescription("Generate Document");
		try {
		
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().click(driver,genericLocators.link(driver, "Documents",testCaseDataSd.get("DOCUMENTS_TAB").get(0)) ,testCaseDataSd.get("DOCUMENTS_TAB").get(0), testCaseParam,action);		
			Webkeywords.instance().pause();
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "SOC 832 (Notice of CACI).pdf",testCaseDataSd.get("SOC_832_VERIFY").get(0)), testCaseDataSd.get("SOC_832_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "SOC 834 (Request for Grievance).pdf",testCaseDataSd.get("SOC_834_VERIFY").get(0)), testCaseDataSd.get("SOC_834_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "BCIA 8583 (CACI Index Form).pdf",testCaseDataSd.get("BCIA_8583_VERIFY").get(0)), testCaseDataSd.get("BCIA_8583_VERIFY").get(0), testCaseParam, action);
			//Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "SOC 833 (Grievance Procedure).pdf",testCaseDataSd.get("SOC_833_VERIFY").get(0)), testCaseDataSd.get("SOC_833_VERIFY").get(0), testCaseParam, action);
		}catch (Exception e) {
			logger.error(FAILURE_MSG , action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	

	public void validateDocumentActions(TestCaseParam testCaseParam, String documentName, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Documents");
		action.setPageActionDescription("Process Documents");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, documentName,testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0)), testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, showaction,testCaseDataSd.get("SHOW_ACTIONS").get(0), testCaseParam, action);
			String downloadTD = testCaseDataSd.get("DOWNLOAD").get(0);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.link(driver, "Download",downloadTD), downloadTD, testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.link(driver, "Print",testCaseDataSd.get("PRINT").get(0)), testCaseDataSd.get("PRINT").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.link(driver, "View",testCaseDataSd.get("VIEW").get(0)), testCaseDataSd.get("VIEW").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.link(driver, "Delete",testCaseDataSd.get("DELETE").get(0)), testCaseDataSd.get("DELETE").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Download",downloadTD),downloadTD, testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Print",downloadTD),downloadTD, testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "View",downloadTD),downloadTD, testCaseParam, action);
		  
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void downloadDocument(TestCaseParam testCaseParam, String documentName, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Process Download Documents");
		action.setPageActionDescription("Process Download Documents");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, documentName,testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0)), testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, showaction,testCaseDataSd.get("SHOW_ACTIONS").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Download",testCaseDataSd.get("DOWNLOAD").get(0)),testCaseDataSd.get("DOWNLOAD").get(0), testCaseParam, action);		  
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void viewDocument(TestCaseParam testCaseParam, String documentName, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("View Document");
		action.setPageActionDescription("View Document");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, documentName,testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0)), testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, showaction,testCaseDataSd.get("SHOW_ACTIONS").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "View",testCaseDataSd.get("VIEW").get(0)),testCaseDataSd.get("VIEW").get(0), testCaseParam, action);	
			Webkeywords.instance().click(driver, genericLocators.button(driver, "View",testCaseDataSd.get("CLOSE").get(0)),testCaseDataSd.get("CLOSE").get(0), testCaseParam, action);		  
		
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void printDocument(TestCaseParam testCaseParam, String documentName, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Print Document");
		action.setPageActionDescription("Print Document");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, documentName,testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0)), testCaseDataSd.get("ERR_DOCUMENT_NAME").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, showaction,testCaseDataSd.get("SHOW_ACTIONS").get(0), testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.link(driver, "Print",testCaseDataSd.get("PRINT").get(0)),testCaseDataSd.get("PRINT").get(0), testCaseParam, action);		  
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Print",testCaseDataSd.get("PRINT_DOCUMENT").get(0)),testCaseDataSd.get("PRINT_DOCUMENT").get(0), testCaseParam, action);	
		    
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void uploadDocument(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Upload Document");
		action.setPageActionDescription("Upload New Document");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			Webkeywords.instance().click(driver,genericLocators.link(driver, "Documents",testCaseDataSd.get("DOCUMENTS_TAB").get(0)) ,testCaseDataSd.get("DOCUMENTS_TAB").get(0), testCaseParam,action);		
			Webkeywords.instance().scrollUpPageToTheTop(driver);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "New", testCaseDataSd.get("DOCUMENTS_NEWBTN").get(0)), testCaseDataSd.get("DOCUMENTS_NEWBTN").get(0), testCaseParam, action);
			Webkeywords.instance().pause();
			Webkeywords.instance().selectInputDropdownValue(driver,testCaseDataSd.get("DOCUMENT_CATEGORY").get(0),"Document Category",testCaseParam,action);
			Webkeywords.instance().selectDropdownValueByElement(driver,documentType,documentTypeList,testCaseDataSd.get("DOCUMENT_TYPE").get(0),"Document Type",testCaseParam,action);
			Webkeywords.instance().selectInputDropdownValue(driver,testCaseDataSd.get("DOCUMENT_STATUS").get(0),"Document Status",testCaseParam,action);
			Webkeywords.instance().setText(driver, genericLocators.textarea(driver, "Description",testCaseDataSd.get("DESCRIPTION").get(0)), "AutoDescription_" + util.getRandom(testCaseDataSd.get("DESCRIPTION").get(0)), testCaseParam, action);
			Webkeywords.instance().jsClick(driver,uploadfiles );
			
			String filePath = TestRunSettings.getUploadFilePath() + "\\"+ testCaseDataSd.get("UPLOAD_DOCUMENT").get(0);

		    uploadFile(filePath);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE").get(0)),testCaseDataSd.get("SAVE").get(0), testCaseParam, action);
		} catch (Exception e) {
				logger.error(FAILURE_MSG, action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void uploadFile(String path) throws AWTException{

		StringSelection strSel = new StringSelection(path);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(strSel, null);

		Robot rb = new Robot();
		rb.delay(2000);
		rb.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		rb.keyPress(java.awt.event.KeyEvent.VK_V);
		rb.keyRelease(java.awt.event.KeyEvent.VK_V);
		rb.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
		rb.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		rb.delay(4000);
		rb.keyRelease(java.awt.event.KeyEvent.VK_ENTER);

	}
}
