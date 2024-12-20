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
import reportutilities.common.ReportCommon;
import reportutilities.extentmodel.PageDetails;
import reportutilities.model.TestCaseParam;
import testsettings.TestRunSettings;


public class ScreeningAddress {
	private static final Logger logger =LoggerFactory.getLogger(ScreeningAddress.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	LocalDateTime startTime= LocalDateTime.now();
	String moduleName = ModuleConstants.SCREENING;
	String screenName = ScreenConstants.SCREENINGADDRESSES;
	
	@FindBy(how = How.XPATH, using = "//p[text()='Screening Address ID']//parent::div//lightning-formatted-text")
	public WebElement screeningAddressID;
	

    String scrAddressLink = "//slot[text()='%s']/../../..//parent::a";
	
	public ScreeningAddress(){ }
	
	public ScreeningAddress(WebDriver wDriver,TestCaseParam testCaseParam) 
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
	
	@FindBy(how=How.XPATH,using ="//legend[text() ='User Entered Address:']//parent::fieldset//span[@part='indicator']")
	WebElement userEnteredAddress;
	
	@FindBy(how = How.XPATH, using = "//span[text()='View All']//parent::div/span")
	public WebElement viewAllButton;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Screening Addresses']")
	public WebElement screeningAddressHeader;
	
	@FindBy(how = How.XPATH, using = "//p[text()='Screening Address ID']")
	public WebElement scrAddressIDHeader;
	

	
	public void navigateToScreeningAddresses(TestCaseParam testCaseParam, String scriptIteration, String pomIteration)throws CustomException  {
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate to Screening Addresses");
		action.setPageActionDescription("Navigate to Screening Addresses");
		try {

			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String addressTabTD= testCaseDataSd.get("ADDRESSES_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Addresses" ,addressTabTD),addressTabTD, testCaseParam,action);
		}catch (Exception e) {
			logger.error("Failed== {}",action.getPageActionDescription());
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		}
	}
	

	public void addScreeningAddress(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException {

		PageDetails action = new PageDetails();
		action.setPageActionName("Process Add Screening Address");
		action.setPageActionDescription("Process Add Screening Address");
		try {

			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String newBtn = testCaseDataSd.get("NEW_BTN").get(0);
			String addressType = testCaseDataSd.get("ADDRESS_TYPE").get(0);
			String addressLine1 = testCaseDataSd.get("ADDRESS_LINE1").get(0);
			String city = testCaseDataSd.get("CITY").get(0);
			String zipCode = testCaseDataSd.get("ZIP_CODE").get(0);
			String validateAddressBtn = testCaseDataSd.get("VALIDATE_ADDRESS_BTN").get(0);
			String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
			String userAddress = testCaseDataSd.get("USER_ENTERED_ADDRESS").get(0);
			
			String addressTabTD= testCaseDataSd.get("ADDRESSES_TAB").get(0);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Addresses" ,addressTabTD),addressTabTD, testCaseParam,action);
			Webkeywords.instance().click(driver,genericLocators.button(driver, "New" ,newBtn),newBtn, testCaseParam, action);
			Webkeywords.instance().selectValueInputDropdown(driver, addressType, "Address Type", testCaseParam, action);
			Webkeywords.instance().setText(driver, genericLocators.searchbox(driver,  " Address Line 1" ,addressLine1),util.getRandom(addressLine1), testCaseParam, action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "City" ,city), city, testCaseParam, action);
			Webkeywords.instance().setText(driver, genericLocators.textbox(driver, "Zip Code",zipCode), zipCode, testCaseParam, action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Validate Address" ,validateAddressBtn), validateAddressBtn, testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, userEnteredAddress);
			Webkeywords.instance().click(driver,userEnteredAddress, userAddress, testCaseParam, action);
			Webkeywords.instance().click(driver,genericLocators.button(driver, "Save",saveBtn),saveBtn, testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver, screeningAddressID);
			SalesforceConstants.setConstantValue("SAR_ID"+pomIteration, screeningAddressID.getText());
			
		  } catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		  } 

	}

	public void verifyingScreeningAddress(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException {
		PageDetails action = new PageDetails();
		action.setPageActionName("verifying Screening Address");
		action.setPageActionDescription("verifying Screening Address");
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
				
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Address Type",testCaseDataSd.get("ADDRESS_TYPE_VERIFY").get(0)), testCaseDataSd.get("ADDRESS_TYPE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Associate To",testCaseDataSd.get("ASSOCIATE_TO_VERIFY").get(0)), testCaseDataSd.get("ASSOCIATE_TO_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textarea(driver, "Comments About Child's Address",testCaseDataSd.get("COMMENTS_ABOUT_CHILD_VERIFY").get(0)), testCaseDataSd.get("COMMENTS_ABOUT_CHILD_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Address Information",testCaseDataSd.get("ADDRESS_INFO_VERIFY").get(0)), testCaseDataSd.get("ADDRESS_INFO_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Address Validation",testCaseDataSd.get("ADDRESS_VALIDATION_VERIFY").get(0)), testCaseDataSd.get("ADDRESS_VALIDATION_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Address Line 1",testCaseDataSd.get("ADDRESS_LINE1_VERIFY").get(0)), testCaseDataSd.get("ADDRESS_LINE1_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Address Line 2",testCaseDataSd.get("ADDRESS_LINE2_VERIFY").get(0)), testCaseDataSd.get("ADDRESS_LINE2_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "City", testCaseDataSd.get("CITY_VERIFY").get(0)), testCaseDataSd.get("CITY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "State", testCaseDataSd.get("STATE_VERIFY").get(0)), testCaseDataSd.get("STATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.dropdown(driver, "Country",testCaseDataSd.get("COUNTRY_VERIFY").get(0)), testCaseDataSd.get("COUNTRY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.textbox(driver, "Zip Code", testCaseDataSd.get("ZIPCODE_VERIFY").get(0)), testCaseDataSd.get("ZIPCODE_VERIFY").get(0), testCaseParam, action);

				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Validate Address",testCaseDataSd.get("VALIDATEADDRESS_VERIFY").get(0)), testCaseDataSd.get("VALIDATEADDRESS_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "System Information",testCaseDataSd.get("SYSTEM_INFO_VERIFY").get(0)), testCaseDataSd.get("SYSTEM_INFO_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created By",testCaseDataSd.get("CREATEDBY_VERIFY").get(0)), testCaseDataSd.get("CREATEDBY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Created Date", testCaseDataSd.get("CREATED_DATE_VERIFY").get(0)), testCaseDataSd.get("CREATED_DATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Modified Date",testCaseDataSd.get("MODIFIED_DATE_VERIFY").get(0)), testCaseDataSd.get("MODIFIED_DATE_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyTextDisplayed(driver, genericLocators.textOnPage(driver, "Last Modified By",testCaseDataSd.get("LAST_MODIFIEDBY_VERIFY").get(0)), testCaseDataSd.get("LAST_MODIFIEDBY_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Cancel",testCaseDataSd.get("CANCEL_VERIFY").get(0)), testCaseDataSd.get("CANCEL_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save and New",testCaseDataSd.get("SAVE_NEW_VERIFY").get(0)), testCaseDataSd.get("SAVE_NEW_VERIFY").get(0), testCaseParam, action);
				Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);

		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
	
	public void editScreeningAddress(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) throws CustomException {

		PageDetails action = new PageDetails();
		action.setPageActionName("Process edit Screening Address");
		action.setPageActionDescription("Process edit Screening Address");
		try {

			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			
			String addressType = testCaseDataSd.get("ADDRESS_TYPE").get(0);
			String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
			
			String addressId = SalesforceConstants.getConstantValue(testCaseDataSd.get("SAR_ADDRESS_ID").get(0));
				
			Webkeywords.instance().click(driver, viewAllButton, testCaseDataSd.get("VIEWALL").get(0), testCaseParam, action);
			Webkeywords.instance().waitElementToBeVisible(driver,screeningAddressHeader);
			
			WebElement sarIdXpath = driver.findElement(By.xpath(format(scrAddressLink,addressId)));
			Webkeywords.instance().waitElementClickable(driver, sarIdXpath);
			Webkeywords.instance().jsClick(driver,  sarIdXpath, testCaseDataSd.get("SAR_ADDRESS_ID").get(0), testCaseParam,action);

			Webkeywords.instance().waitElementToBeVisible(driver, scrAddressIDHeader);
			Webkeywords.instance().selectValueInputDropdown(driver, addressType, "Address Type", testCaseParam, action);
			Webkeywords.instance().click(driver,genericLocators.button(driver, "Save",saveBtn),saveBtn, testCaseParam, action);
			
		  } catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
		  } 
	}
}
