package cares.cwds.salesforce.pom.folio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
import cares.cwds.salesforce.utilities.web.Webkeywords;

public class FolioDetails {
	private static final Logger logger = LoggerFactory.getLogger(FolioDetails.class.getName());
	private static final String PATHWAY = "Pathway";
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;
	LocalDateTime startTime = LocalDateTime.now();

	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.FOLIODETAILS;

	private static final String DETAILS = "Details";

	@FindBy(xpath = "//*[text()='Folio Ref.']/..//lightning-formatted-text")
	WebElement folioRefID;

	@FindBy(xpath = "//*[text()='UnMark Sensitive Reason']")
	WebElement unMarkSensitiveReasonSection;

	@FindBy(xpath = "//div[@data-key='warning']")
	WebElement warningPopup;

	@FindBy(xpath = "//span[text()='Close']")
	WebElement warningCloseBtn;

	public FolioDetails() {
	}

	public FolioDetails(WebDriver wDriver, TestCaseParam testCaseParam) {
		initializePage(wDriver, testCaseParam);
	}

	public void initializePage(WebDriver wDriver, TestCaseParam testCaseParam) {
		logger.info(this.getClass().getName());
		driver = wDriver;
		PageFactory.initElements(driver, this);
		ReportCommon testStepLogDetails = new ReportCommon();
		testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
		genericLocators = new GenericLocators(wDriver);
	}

	public void navigateToFolioDetails(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();
		action.setPageActionName("Navigate To Folio Details");
		action.setPageActionDescription("Navigate To Folio Details");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);
		String detailsTabFolio = testCaseDataSd.get("FOLIO_DETAILS_TAB").get(0);
		Webkeywords.instance().click(driver, genericLocators.link(driver, DETAILS, detailsTabFolio), detailsTabFolio,
				testCaseParam, action);
	}

	public void assignSupervisor(TestCaseParam testCaseParam, String scriptIteration, String pomIteration)
			 {

		PageDetails action = new PageDetails();
		action.setPageActionName("Assign Supervisor");
		action.setPageActionDescription("Assign Supervisor");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);

		String approvalSupervisor = "Erwupervisor3 Auto";
		String approvalSupervisorLink = testCaseDataSd.get("APPROVAL_SUPERVISOR_LINK").get(0);
		String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);
		String pathway = testCaseDataSd.get("PATHWAY").get(0);

		Webkeywords.instance().scrollIntoViewElement(driver, unMarkSensitiveReasonSection);
		Webkeywords.instance().click(driver, genericLocators.textbox(driver, "Approval Supervisor", approvalSupervisor),
				approvalSupervisor, testCaseParam, action);
		WebElement search = driver.findElement(By.xpath("//input[@placeholder='Search Users...']"));
		search.sendKeys(approvalSupervisor);
		Webkeywords.instance().click(driver, genericLocators.link(driver, approvalSupervisor, approvalSupervisorLink),
				approvalSupervisorLink, testCaseParam, action);
		Webkeywords.instance().pause();
		Webkeywords.instance().scrollUpPageToTheTop(driver);
		Webkeywords.instance().selectValueInputDropdown(driver, pathway, PATHWAY, testCaseParam, action);
		Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", saveBtn), saveBtn, testCaseParam,
				action);
	}

	public void addDetailsInfoFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {

		PageDetails action = new PageDetails();
		action.setPageActionName("Process Allegations Info Folio ");
		action.setPageActionDescription("Process Allegations Info Folio");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);
		String saveBtn = testCaseDataSd.get("SAVE_BTN").get(0);

		Webkeywords.instance().selectValueInputDropdown(driver, testCaseDataSd.get(PATHWAY).get(0), PATHWAY,
				testCaseParam, action);
		Webkeywords.instance().click(driver, genericLocators.button(driver, "Save", saveBtn), saveBtn, testCaseParam,
				action);
	}

	public void verifyFolioRecord(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();
		action.setPageActionName("Verify Folio Record");
		action.setPageActionDescription("Verify Folio Record");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);
		Webkeywords.instance().verifyTextDisplayed(driver, folioRefID, testCaseDataSd.get("FOLIOREF_VERIFY").get(0),
				testCaseParam, action);
	}
}