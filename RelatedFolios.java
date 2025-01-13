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

import cares.cwds.salesforce.constants.ModuleConstants;
import cares.cwds.salesforce.constants.ScreenConstants;
import cares.cwds.salesforce.utilities.common.TestRunSettings;
import cares.cwds.salesforce.utilities.common.Util;
import cares.cwds.salesforce.utilities.reports.common.ReportCommon;
import cares.cwds.salesforce.utilities.reports.extentmodel.PageDetails;
import cares.cwds.salesforce.utilities.reports.model.TestCaseParam;

import cares.cwds.salesforce.utilities.web.GenericLocators;
import cares.cwds.salesforce.utilities.web.SalesforceCommon;
import cares.cwds.salesforce.utilities.web.Webkeywords;

public class RelatedFolios {
	private static final Logger logger =LoggerFactory.getLogger(RelatedFolios.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.RELATEDFOLIOS;

	private static final String RELATEDFOLIOS_TAB = "RELATED_FOLIOS_TAB";
	private static final String RELATED_FOLIOS = "Related Folios";
	
	public RelatedFolios(){ }
	
	public RelatedFolios(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	@FindBy(how = How.XPATH, using = "//label[text()='Associated Folio Ref.']/..//input")
	public WebElement associatedFolioRefDropdown;
	
	String associatedFolioRefListItem = "//li[@class='slds-listbox__item'][@aria-label='%s']";

	String associatedFolioRefTableCell = "//a[text()='%s']//ancestor::lightning-primitive-cell-factory[@data-label='Associated Folio Ref.']";
	
	public void navigateToRelatedFolios(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws InterruptedException  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("Navigate to Related Folios");
		action.setPageActionDescription("Navigate to Related Folios");
		
			Webkeywords.instance().navigate(driver,  "https://cacwds--v1sit.sandbox.lightning.force.com/lightning/r/Case/5003S00000B9QapQAF/view", testCaseParam,action);
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(), TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String relatedFoliosTabTD = testCaseDataSd.get(RELATEDFOLIOS_TAB).get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, RELATED_FOLIOS,relatedFoliosTabTD),relatedFoliosTabTD, testCaseParam,action);	
	}
	

	public void associateRelatedFolio(TestCaseParam testCaseParam,String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();
		
		action.setPageActionName("Associate Folio");
		action.setPageActionDescription("Associate Folio");
		
	
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
			String relatedFoliosTabTD = testCaseDataSd.get(RELATEDFOLIOS_TAB).get(0);
			String associatedFolioRefTD = testCaseDataSd.get("ASSOCIATED_FOLIOREF_DD").get(0);

			Webkeywords.instance().click(driver, genericLocators.link(driver, RELATED_FOLIOS,relatedFoliosTabTD),relatedFoliosTabTD, testCaseParam,action);
			Webkeywords.instance().click(driver, associatedFolioRefDropdown,associatedFolioRefTD, testCaseParam,action);
			Webkeywords.instance().click(driver,  driver.findElement(By.xpath(format(associatedFolioRefListItem,associatedFolioRefTD))),associatedFolioRefTD, testCaseParam,action);
			Webkeywords.instance().click(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_BTN").get(0)), testCaseDataSd.get("SAVE_BTN").get(0), testCaseParam, action);
			SalesforceCommon.verifyToastMessage(driver,testCaseDataSd.get("TOAST_MSG_VERIFY").get(0),testCaseParam, action);	
			WebElement associatedFolioRefTableCellLink =  driver.findElement(By.xpath(format(associatedFolioRefTableCell,associatedFolioRefTD)));
			Webkeywords.instance().verifyElementDisplayed(driver, associatedFolioRefTableCellLink, testCaseDataSd.get("ASSOCIATED_FOLIOREFCELL_VERIFY").get(0), testCaseParam, action);
	}
	
	public void verifyRelatedFolioFields(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.setPageActionName("verifying Related Folio Fields");
		action.setPageActionDescription("verifying Related Folio Fields");
		
		try {
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, TestRunSettings.getTestNGTestMethodName(),TestRunSettings.getTestDataPath(),
					TestRunSettings.getTestDataMappingFileName() ,TestRunSettings.getTestDataMappingSheetNameSd(),scriptIteration,pomIteration);
				
			String relatedFoliosTabTD = testCaseDataSd.get(RELATEDFOLIOS_TAB).get(0);
			Webkeywords.instance().click(driver, genericLocators.link(driver, RELATED_FOLIOS,relatedFoliosTabTD),relatedFoliosTabTD, testCaseParam,action);
			Webkeywords.instance().verifyElementDisplayed(driver, associatedFolioRefDropdown ,testCaseDataSd.get("ASSOCIATED_FOLIOREF_DD_VERIFY").get(0), testCaseParam, action);
			Webkeywords.instance().verifyElementDisplayed(driver, genericLocators.button(driver, "Save",testCaseDataSd.get("SAVE_VERIFY").get(0)), testCaseDataSd.get("SAVE_VERIFY").get(0), testCaseParam, action);
		
		} catch (Exception e) {
				logger.error("Failed == {} ", action.getPageActionDescription());
				exceptionDetails.logExceptionDetails(driver, testCaseParam, action.getPageActionName(), action.getPageActionDescription(), startTime,e);
			}
	}
}