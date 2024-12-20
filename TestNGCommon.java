
package uitests.testng.common;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import cares.cwds.salesforce.common.utilities.ExcelUtils;
import cares.cwds.salesforce.common.utilities.PoiReadExcel;
import cares.cwds.salesforce.common.utilities.Util;
import cares.cwds.salesforce.constants.PrjConstants;
import cares.cwds.salesforce.constants.SalesforceConstants;
import initializescripts.InitializeTestSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import reportutilities.common.ReportCommon;
import reportutilities.common.ScreenshotCommon;
import reportutilities.extentmodel.ExtentUtilities;
import reportutilities.extentmodel.TestRunDetails;
import reportutilities.extentreport.ExtentReport;
import reportutilities.htmlmodel.TestManager;
import reportutilities.htmlreport.HTMLReports;
import reportutilities.htmlreport.InitializeHTMLReports;
import reportutilities.model.TestCaseParam;
import reusable.PageLoadTimingInterceptor;
import testsettings.TestRunSettings;

public class TestNGCommon {
	
	private static final Logger logger =LoggerFactory.getLogger(TestNGCommon.class.getName());
	
	
	protected String browser = "";
	protected WebDriver driver = null;
	
	ScreenshotCommon scm = new ScreenshotCommon();
	Util util = new Util();
	protected HashMap<String, ArrayList<String>> testCaseDataExecution = new HashMap<>();
	protected ReportCommon testStepDetails = new ReportCommon();
	protected TestCaseParam testCaseParam = new TestCaseParam();
	
	static boolean initialized = false;
	static boolean postinitialized = false;
	static LocalDateTime startTime=LocalDateTime.now();
	public static final String TESTRUN_ID  = ""; 

	@BeforeSuite
	public void testRunSetUp() throws InterruptedException
	{
		Path currentRelativePath = Paths.get("");
		String prjPath=currentRelativePath.toAbsolutePath().toString();



		InitializeTestSettings initObj = new InitializeTestSettings();
		initObj.loadConfigData(prjPath);

		SalesforceConstants.intializeAppUsers();
		SalesforceConstants.getAppUsers();
		
		ExtentReport.startReport(TestRunSettings.getResultsPath(),TestRunSettings.getEnvironment());

		InitializeHTMLReports initializeHTMLReports = new InitializeHTMLReports();
		initializeHTMLReports.initHTMLReports(prjPath,TestRunSettings.getResultsPath());
		HTMLReports htmlReports= new HTMLReports();

		htmlReports.createTCHTMLLiveSummary();		
	}


	public void endTestCase(TestCaseParam testCaseParam){

		ExtentUtilities extentUtilities = new ExtentUtilities();
		extentUtilities.endTestCase(testCaseParam.getTestCaseName(), testCaseParam.getModuleName(), testCaseParam.getBrowser());

		TestManager.
		updateTestCaseStatus
		(testCaseParam.getTestCaseName(), testCaseParam.getModuleName(), testCaseParam.getBrowser(),TestManager.HTMLTCStatus.
				PASSED
				);
		ExtentReport.endReportTear();
	}
	
	
	@AfterSuite
	public void testRunTearDown() throws InterruptedException
	{
		try {
			reportutilities.extentreport.ExtentReport extentReport = new reportutilities.extentreport.ExtentReport();
			extentReport.createExtentReportModel(TestRunSettings.getResultsPath(), TestRunDetails.getTestCaseRepository(), "Test", "Test", "Test");
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt(); // Re-interrupt the thread
		    logger.error("{} Failed to generate the extent Test Results - Generic ", e.getMessage());
		}

		try {

			reportutilities.extentreport.ExtentReport extentReportlive = new reportutilities.extentreport.ExtentReport();
			extentReportlive.endReport();
		}
		catch(Exception e)
		{
			logger.error("{} Failed to generate the Live Results",e.getMessage());
		}




		//Calculate Results
		try
		{
			ReportCommon reportcommon=new ReportCommon();
			reportcommon.calculateTestCaseResults(TestRunDetails.getTestCaseRepository());
			reportcommon.calculateModuleResults();
			reportcommon.calculateBrowserResults();
		}
		catch(Exception e)
		{
			logger.error("{} Failed to compute the test case details",e.getMessage());

		}


		//Extent Report
		try
		{
			reportutilities.extentreport.ExtentReport extentReport = new reportutilities.extentreport.ExtentReport();
			extentReport.createExtentReportModel(TestRunSettings.getResultsPath(),TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt(); // Re-interrupt the thread
		    logger.error("{} Failed to generate the extent Test Results", e.getMessage());
		}

		//ExtentReport By Category

		try
		{
			reportutilities.extentreport.ExtentReport extentReport = new reportutilities.extentreport.ExtentReport();
			extentReport.createExtentReportCategory(TestRunSettings.getResultsPath(),TestRunDetails.getTestCaseRepository(),"Test","Test","Test");
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt(); // Re-interrupt the thread
		    logger.error("{} Failed to generate the extent Test Results", e.getMessage());
		}

		try
		{
			HTMLReports htmlReport = new HTMLReports();
			htmlReport.generateReport(TestRunSettings.getResultsPath());
		}
		catch(Exception e)
		{
			logger.error("{} Failed to generate the Excel Test Results",e.getMessage());

		}		
	}

	
	
	public void initializeTestCase(TestCaseParam testCaseParam) throws InterruptedException
	{
		ExtentUtilities extentUtilities = new ExtentUtilities();

		extentUtilities.initializeNewTestCase(testCaseParam.getTestCaseName(), testCaseParam.getTestCaseDescription(), testCaseParam.getModuleName(),testCaseParam.getTestCaseCategory(), testCaseParam.getBrowser());
 }
	
	public WebDriver initializeDriver()
	{

		//WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver",
				"D:\\Apps\\chrome driver 126\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options= new ChromeOptions();
		options.setAcceptInsecureCerts(true);

		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--use-fake-ui-for-media-stream=1");

		// To run test case in head less mode
		if(TestRunSettings.getRunInHeadlessMode().equalsIgnoreCase("Yes")) {
			options.addArguments("--headless", "--window-size=1920,1200");
		}
		
		WebDriver driverNew= new ChromeDriver(options);
		driverNew.manage().deleteAllCookies();
		// Further actions based on runInHeadlessMode setting
		if (TestRunSettings.getCaptureUIPerformance().equalsIgnoreCase("Yes")){
			driverNew = PageLoadTimingInterceptor.createProxy(driverNew);
		}

		return driverNew;

	}



	public WebDriver initializeDriverIncognito()
	{
		ChromeOptions options= new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("headless");
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("--start-fullscreen");
		options.addArguments("--incognito");
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		WebDriver driverNew= new ChromeDriver(options);
		driverNew.manage().deleteAllCookies();

		return driverNew;
	}

	
	public WebDriver initializeAndroidDriver() throws IOException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("chromedriverExecutable","C:/SeleniumDrivers/chromedriver.exe");

		capabilities.setCapability("deviceName", "a52q");

		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("platformVersion", "12");

		capabilities.setCapability("browserName", "chrome");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--user-agent=Chrome/99.0.4844.73");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		return new RemoteWebDriver(new java.net.URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}

	public WebDriver initializeEdgeDriver()
	{
		System.setProperty("webdriver.edge.driver", "C:/SeleniumDrivers/msedgedriver.exe");
	
		return new EdgeDriver();
	}

	public void quitDriver(WebDriver driver)
	{
		try
		{
			driver.quit();
		}
		catch(Exception e)
		{
			logger.error("{} Unable to close Webdriver",e.getMessage());
		}
	}
	
	/**
	 * This method returns test script iterations based on executeFlag
	 * @param milestoneName - Milestone name
	 * @param testScriptUniqueIdentifier - test method name
	 * @param iterations - List
	 * @return
	 */
	
	public String[] setIterationsOnExecuteFlag(String milestoneName, String testScriptUniqueIdentifier) {
	
		TestRunSettings.setMilestoneMappingTestDataFilename(milestoneName);
		TestRunSettings.setTestNGTestMethodName(testScriptUniqueIdentifier);
		return ExcelUtils.getIterationsToExecute(testScriptUniqueIdentifier);
	}
	
	public String[] setScriptIterationFlag(String dataFileName, String moduleName,String testCaseName) {
		    String excelFileName = TestRunSettings.setTestDataFilename(dataFileName);
		    TestRunSettings.setTestNGTestMethodName(testCaseName);
		    ArrayList<String> whereClauseTestData2 = new ArrayList<>();
	        whereClauseTestData2.add("TESTCASE::" +  TestRunSettings.getTestNGTestMethodName());
	        whereClauseTestData2.add("EXECUTE_FLAG::" + TestRunSettings.getMasterScriptExecutionFlag());
	        String strFilePath = TestRunSettings.getTestDataPath() + PrjConstants.FILE_SEPARATOR + moduleName + PrjConstants.FILE_SEPARATOR + excelFileName;	
	       
	        return getIterationValue(PoiReadExcel.fetchWithCondition(strFilePath,TestRunSettings.getMasterSheetName(),whereClauseTestData2));
	}
	
	public static String[] getIterationValue(Map<String, ArrayList<String>> map) {
		for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
			if(entry.getKey().equalsIgnoreCase("SCRIPT_ITERATION")) {
				 ArrayList<String> value = entry.getValue();
				 return value.toArray(new String[0]);
			}
		}
		return new String[0];
	}
	
  }