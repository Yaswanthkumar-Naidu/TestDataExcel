package cares.cwds.salesforce.pom.folio;

import static java.lang.String.format;

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
import cares.cwds.salesforce.utilities.web.Webkeywords;

public class ScreeningsInFolio {
	private static final Logger logger = LoggerFactory.getLogger(ScreeningsInFolio.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	GenericLocators genericLocators = null;

	String moduleName = ModuleConstants.FOLIO;
	String screenName = ScreenConstants.SCREENINGSINFOLIO;

	private static final String SCREENINGS_TAB = "SCREENINGS_TAB";
	private static final String SCREENINGS = "Screenings";

	public ScreeningsInFolio() {
	}

	public ScreeningsInFolio(WebDriver wDriver, TestCaseParam testCaseParam) {
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

	@FindBy(how = How.XPATH, using = "//lightning-tree-item//span[contains(@class,'tree__item-label')][text()='Screenings']")
	public WebElement screeningsTab;

	String screeningCellItem = "//*[text()='%s']//ancestor::lightning-primitive-cell-factory[@data-label='%s']";

	String columnHeader = "//table[@aria-label='%s']/thead/tr/th[@*='%s']";

	public void navigateToScreeningsInFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) throws InterruptedException {

		PageDetails action = new PageDetails();

		action.setPageActionName("Navigate to Screenings in Folio");
		action.setPageActionDescription("Navigate to Screenings in Folio");

		Webkeywords.instance().navigate(driver,
				"https://cacwds--v1sit.sandbox.lightning.force.com/lightning/r/Case/5003S00000B9QapQAF/view",
				testCaseParam, action);
		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);
		String screeningsTabTD = testCaseDataSd.get(SCREENINGS_TAB).get(0);
		Webkeywords.instance().click(driver, screeningsTab, screeningsTabTD, testCaseParam, action);
	}

	public void verifyScreeningsInFolio(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();

		action.setPageActionName("verifying Screenings in Folio");
		action.setPageActionDescription("verifying Screenings in Folio");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);

		String screeningsTabTD = testCaseDataSd.get(SCREENINGS_TAB).get(0);
		Webkeywords.instance().click(driver, screeningsTab, screeningsTabTD, testCaseParam, action);
		WebElement screeningID = driver
				.findElement(By.xpath(format(screeningCellItem, testCaseDataSd.get("SCR_ID").get(0), "Screening ID")));
		WebElement screeningName = driver.findElement(
				By.xpath(format(screeningCellItem, testCaseDataSd.get("SCR_NAME").get(0), "Screening Name")));
		Webkeywords.instance().verifyElementDisplayed(driver, screeningID, testCaseDataSd.get("SCR_ID").get(0),
				testCaseParam, action);
		Webkeywords.instance().verifyElementDisplayed(driver, screeningName, testCaseDataSd.get("SCR_NAME").get(0),
				testCaseParam, action);
	}
	public void verifyScreeningsFields(TestCaseParam testCaseParam, String scriptIteration, String pomIteration) {
		PageDetails action = new PageDetails();

		action.setPageActionName("verifying Screenings Fields");
		action.setPageActionDescription("verifying Screenings Fields");

		Map<String, ArrayList<String>> testCaseDataSd = util.getScreenTCData(screenName,
				TestRunSettings.getTestNGTestMethodName(), TestRunSettings.getTestDataPath(),
				TestRunSettings.getTestDataMappingFileName(), TestRunSettings.getTestDataMappingSheetNameSd(),
				scriptIteration, pomIteration);

		String screeningsTabTD = testCaseDataSd.get(SCREENINGS_TAB).get(0);
		Webkeywords.instance().click(driver, screeningsTab, screeningsTabTD, testCaseParam, action);
		Webkeywords.instance()
				.verifyElementDisplayed(
						driver, getElementBasedOnFlag(testCaseDataSd.get("SCR_ID_VERIFY").get(0), columnHeader,
								SCREENINGS, "Screening ID"),
						testCaseDataSd.get("SCR_ID_VERIFY").get(0), testCaseParam, action);
		Webkeywords.instance()
				.verifyElementDisplayed(
						driver, getElementBasedOnFlag(testCaseDataSd.get("SCR_NAME_VERIFY").get(0), columnHeader,
								SCREENINGS, "Screening Name"),
						testCaseDataSd.get("SCR_NAME_VERIFY").get(0), testCaseParam, action);
		Webkeywords.instance().verifyElementDisplayed(
				driver, getElementBasedOnFlag(testCaseDataSd.get("SCR_CALLDATETIME_VERIFY").get(0), columnHeader,
						SCREENINGS, "Call Date/Time"),
				testCaseDataSd.get("SCR_CALLDATETIME_VERIFY").get(0), testCaseParam, action);
		Webkeywords.instance().verifyElementDisplayed(driver,
				getElementBasedOnFlag(testCaseDataSd.get("SCR_COUNTYFINAL_VERIFY").get(0), columnHeader, SCREENINGS,
						"County Final Determination"),
				testCaseDataSd.get("SCR_COUNTYFINAL_VERIFY").get(0), testCaseParam, action);
		Webkeywords.instance().verifyElementDisplayed(driver,
				getElementBasedOnFlag(testCaseDataSd.get("SCR_APPROVALSUP_VERIFY").get(0), columnHeader, SCREENINGS,
						"Approval Supervisor"),
				testCaseDataSd.get("SCR_APPROVALSUP_VERIFY").get(0), testCaseParam, action);
		Webkeywords.instance().verifyElementDisplayed(
				driver, getElementBasedOnFlag(testCaseDataSd.get("SCR_CREATEDBY_VERIFY").get(0), columnHeader,
						SCREENINGS, "Created By"),
				testCaseDataSd.get("SCR_CREATEDBY_VERIFY").get(0), testCaseParam, action);

	}

	public WebElement getElementBasedOnFlag(String flag, String columnHeaderXpath, String tableName,
			String columnName) {
		if (!(flag.equalsIgnoreCase("n/a"))) {
			return driver.findElement(By.xpath(format(columnHeaderXpath, tableName, columnName)));
		} else
			return null;
	}
}