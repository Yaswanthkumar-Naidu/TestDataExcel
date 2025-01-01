package uitests.testng.milestone3;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cares.cwds.salesforce.pom.Home;
import cares.cwds.salesforce.pom.Login;
import cares.cwds.salesforce.pom.Logout;
import cares.cwds.salesforce.pom.SubmitForApproval;
import cares.cwds.salesforce.pom.SupervisorReviewPromotion;
import cares.cwds.salesforce.pom.folio.FolioAllegations;
import cares.cwds.salesforce.pom.folio.FolioContactLog;
import cares.cwds.salesforce.pom.folio.FolioCorrespondence;
import cares.cwds.salesforce.pom.folio.FolioDetails;
import cares.cwds.salesforce.pom.folio.FolioDisposition;
import cares.cwds.salesforce.pom.folio.FolioDocuments;
import cares.cwds.salesforce.pom.folio.FolioHistory;
import cares.cwds.salesforce.pom.folio.FolioPerson;
import cares.cwds.salesforce.pom.folio.FolioTribalInquiry;
import cares.cwds.salesforce.pom.folio.FolioValidatePerson;
import cares.cwds.salesforce.pom.screening.Allegations;
import cares.cwds.salesforce.pom.screening.Documents;
import cares.cwds.salesforce.pom.screening.ERRDoc;
import cares.cwds.salesforce.pom.screening.ScreeningAddress;
import cares.cwds.salesforce.pom.screening.ScreeningApproval;
import cares.cwds.salesforce.pom.screening.ScreeningContactLog;
import cares.cwds.salesforce.pom.screening.ScreeningDetails;
import cares.cwds.salesforce.pom.screening.ScreeningPerson;
import cares.cwds.salesforce.pom.screening.ScreeningSafetyAlert;
import cares.cwds.salesforce.pom.screening.ScreeningTribalInquiry;
import cares.cwds.salesforce.pom.screening.ScreeningValidatePerson;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import testsettings.TestRunSettings;
import uitests.testng.common.TestNGCommon;

public class T4239 extends TestNGCommon {
	String testCaseName = "To Verify Hotline Worker or Hotline Supervisor selects Caller reason as Prospective Young Adult Re-entry into Extended Foster care.";
    String moduleName = "SD";
    String fileName = "ScriptMasterSheet";  
  
    public T4239(){
    	
    	/*This method is initially left blank for now*/
    }
    
    @BeforeMethod
    public void setUpReport() throws InterruptedException
    {
    	driver = TestRunSettings.getDriver();
        browser = TestRunSettings.getBrowser();
        testCaseParam.setTestCaseName(testCaseName);
        testCaseParam.setModuleName(moduleName);
        testCaseParam.setBrowser(browser);
        testCaseParam.setTestCaseDescription(testCaseParam.getTestCaseName());
        initializeTestCase(testCaseParam);
    }
    
    //runs testT2926 for 2 iterations. One for login_user as HLWorker. Second for HLSupervisor
    @Test (dataProvider = "data-provider")
    public void testT4239(String scriptIteration) throws CustomException, InterruptedException {
    	if(driver == null) {
    		driver = initializeDriver();
    	}
    	//Login as Hotline worker and create a screening record
    	Login login = new Login(driver, testCaseParam);
//    	login.processLoginNew(testCaseParam, scriptIteration,"1", "LOGIN_USER"); 
//
    	Home home = new Home(driver, testCaseParam);
//    	home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
//    	
//    	//Add screening details based on ScreeningDetails test data ITERATION column. Different data is passed for each of testiteration
//      	ScreeningDetails screeningDetails= new ScreeningDetails(driver, testCaseParam);
//    	screeningDetails.clickNewScreening(testCaseParam,scriptIteration,"1");
//    	screeningDetails.enterScreeningDetails(testCaseParam, scriptIteration,"1");
//    	screeningDetails.submitScreeningDetails(testCaseParam, scriptIteration, "1");
//
//    	//Adding 2 persons based on DEFAULT value. DEFAULT value for Screening persons is 2 persons (one perpetrator and one victim)
//    	ScreeningPerson screeningPerson= new ScreeningPerson(driver, testCaseParam);
//    	screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//    	screeningPerson.addScreeningPerson(testCaseParam, scriptIteration, "1");
//    	screeningPerson.addScreeningPerson(testCaseParam, scriptIteration, "2");
//    	
//    	SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        ScreeningValidatePerson validatePerson = new ScreeningValidatePerson(driver, testCaseParam);
//        screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//        validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "1");
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//        validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "2");
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//    	
//    	ScreeningAddress screeningAddress = new ScreeningAddress(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//    	screeningAddress.addScreeningAddress(testCaseParam, scriptIteration, "1");
//    	
//    	Allegations allegations = new Allegations(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//    	allegations.addAllegationsInfo(testCaseParam, scriptIteration,"1");
//    	
//    	ScreeningSafetyAlert screeningSafetyAlert= new ScreeningSafetyAlert(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//    	screeningSafetyAlert.addScreeningSafetyAlert(testCaseParam, scriptIteration, "1");
//    	
//    	ScreeningContactLog screeningContactLog = new ScreeningContactLog(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        screeningContactLog.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "1");
//        screeningContactLog.addScreeningContactLog(testCaseParam, scriptIteration , "1");
//
//    	ScreeningTribalInquiry tribalInquiryAndCollaboration = new ScreeningTribalInquiry(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        tribalInquiryAndCollaboration.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "1");
//     	tribalInquiryAndCollaboration.addTribalInquiryAndCollaborationInfo(testCaseParam, scriptIteration,"1");
//    	
//    	tribalInquiryAndCollaboration = new ScreeningTribalInquiry(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        tribalInquiryAndCollaboration.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "1");
//    	tribalInquiryAndCollaboration.addTribalInquiryAndCollaborationInfo(testCaseParam, scriptIteration,"1");
//
//    	ERRDoc errDoc= new ERRDoc(driver, testCaseParam);
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//   		errDoc.generateERRDoc(testCaseParam, scriptIteration, "1");
//   		
//    	SupervisorReviewPromotion review= new SupervisorReviewPromotion(driver, testCaseParam);  
//    	review.navigateToSupervisorReviewPromotion(testCaseParam, scriptIteration, "1");
//  		review.addSupervisorReviewPromotionInfo(testCaseParam, scriptIteration,"1");
//    	   	
    	SubmitForApproval approval= new SubmitForApproval (driver,testCaseParam);
//    	approval.clickSubmitForApprovalInfo(testCaseParam, scriptIteration,"1");
//    	
//    	Logout objLogout = new Logout(driver, testCaseParam);
//    	objLogout.processLogout(testCaseParam, scriptIteration, "1");   
//    	
//    	login = new Login(driver, testCaseParam);
//       	login.processLoginNew(testCaseParam, scriptIteration,"1", "HL_SUPERVISOR");
//	
//    	home = new Home(driver, testCaseParam);
//    	home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
//    	home.clickOnNotificationForApproval("SCR_ID", testCaseParam,scriptIteration, "1");
//    	
//     	ScreeningApproval screeningApproval = new ScreeningApproval(driver,testCaseParam);
//     	screeningApproval.approveScreening(testCaseParam,scriptIteration,"1");
//     	
//     	objLogout = new Logout(driver, testCaseParam);
//    	objLogout.processLogout(testCaseParam, scriptIteration, "1");  
//    	
//    	login = new Login(driver, testCaseParam);
//    	login.processLoginNew(testCaseParam, scriptIteration,"1", "LOGIN_USER");
//        
//    	home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
//    	home.searchRecord(testCaseParam,scriptIteration, "1");
//        
//        review= new SupervisorReviewPromotion(driver, testCaseParam);
//        review.setPrimaryWorker(testCaseParam, scriptIteration, "1");  
//        
//        objLogout = new Logout(driver, testCaseParam);
//		objLogout.processLogout(testCaseParam, scriptIteration, "1");
		
    	login = new Login(driver, testCaseParam);
    	login.processLoginNew(testCaseParam, scriptIteration,"1", "ER_WORKER");
    	
//    	home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Folio", testCaseParam, scriptIteration, "1");
//    	home.searchRecord(testCaseParam,scriptIteration, "2");
//    	
//        FolioDetails folioDetails = new FolioDetails(driver, testCaseParam);
//        folioDetails.verifyFolioRecord(testCaseParam,scriptIteration, "1");
//        
//        //Add person /alligation and validate new added person 	
//        FolioPerson folioPerson = new FolioPerson(driver, testCaseParam);
//        folioPerson.navigateToFolioPerson(testCaseParam, scriptIteration, "1");
//        folioPerson.addFolioPerson(testCaseParam, scriptIteration, "1");
//        
//        FolioAllegations folioAllegations = new FolioAllegations(driver, testCaseParam);
//        folioAllegations.addAllegationsInfoFolio(testCaseParam, scriptIteration, "1");
//        
//        FolioValidatePerson folioValidatePerson = new FolioValidatePerson(driver, testCaseParam);
//        folioValidatePerson.validatePersonDetails(testCaseParam, scriptIteration, "1");
//        
//        SalesforceCommon.navigateToRecordURL(driver, "FOLIO");
//        
//        folioPerson.navigateToFolioPerson(testCaseParam, scriptIteration, "1");
//        folioPerson.addFolioPerson(testCaseParam, scriptIteration, "2");
//        folioAllegations.addAllegationsInfoFolio(testCaseParam, scriptIteration, "1");
//        folioValidatePerson.validatePersonDetails(testCaseParam, scriptIteration, "2");
//        SalesforceCommon.navigateToRecordURL(driver, "FOLIO");
//        
//        folioAllegations = new FolioAllegations(driver, testCaseParam);
//        folioAllegations.navigateToFolioAllegations(testCaseParam, scriptIteration, "1");
//        folioAllegations.concludeAllegation(testCaseParam, scriptIteration, "1");
              
          FolioDisposition disposition= new FolioDisposition (driver, testCaseParam);
          disposition.navigateToFolioDispositions(testCaseParam, scriptIteration, "1");
          disposition.editDispositionInfoFolio(testCaseParam, scriptIteration, "1");
//        
//        FolioHistory history= new FolioHistory (driver,testCaseParam);
//        history.navigateToFolioHistory(testCaseParam, scriptIteration, "1");
//        history.addHistoryInfoFolio(testCaseParam, scriptIteration, "1");
//        history.verifyingHistoryFieldsFolio(testCaseParam, scriptIteration, "1");
//        
//        FolioContactLog contactLog= new FolioContactLog (driver,testCaseParam);
//        contactLog.navigateToFolioContactLogs(testCaseParam, scriptIteration, "1");
//        contactLog.addFolioContactLog(testCaseParam, scriptIteration, "1");
//        
//        Documents documents = new Documents(driver,testCaseParam);
//        documents.documentSection(testCaseParam, scriptIteration, "1");
//        documents.uploadDocument(testCaseParam, scriptIteration, "1");
//        SalesforceCommon.navigateToRecordURL(driver, "SCR");
//        
//        FolioDetails details = new FolioDetails (driver,testCaseParam);
//        details.navigateToFolioDetails(testCaseParam, scriptIteration, "1");
//        details.assignSupervisor(testCaseParam, scriptIteration, "1");
//         
//        approval.clickSubmitForApprovalInfo(testCaseParam, scriptIteration,"1");
//        
//        FolioTribalInquiry tribal= new FolioTribalInquiry (driver,testCaseParam);
//        tribal.addFolioTribalInquiryAndCollaboration(testCaseParam, scriptIteration, "1");
//        
//        approval.clickSubmitForApprovalInfo(testCaseParam, scriptIteration,"1");
        
        FolioCorrespondence correspondence = new FolioCorrespondence (driver,testCaseParam);
        correspondence.navigateToFolioCorrespondence(testCaseParam, scriptIteration, "1");
        correspondence.addCACIReportFolio(testCaseParam, scriptIteration, "1");
        
        FolioDocuments folioDocument = new FolioDocuments(driver,testCaseParam);
        folioDocument.generateDocument(testCaseParam, scriptIteration, "1");
        folioDocument.validateDocumentsList(testCaseParam, scriptIteration, "1");
        SalesforceCommon.navigateToRecordURL(driver, "FOLIO");
        
        correspondence = new FolioCorrespondence (driver,testCaseParam);
        correspondence.navigateToFolioCorrespondence(testCaseParam, scriptIteration, "1");
        correspondence.addCrossReportSCARFolio(testCaseParam, scriptIteration, "2");
        
        folioDocument = new FolioDocuments(driver,testCaseParam);
        folioDocument. uploadDocument(testCaseParam, scriptIteration, "1");
        
	}
    
    @DataProvider (name = "data-provider")
    public String[] dpMethod(Method method){
    	return setScriptIterationFlag(fileName,moduleName, method.getName());
   
    }
    
    @AfterMethod
    public void tearDownMethod()
    {
    	endTestCase(testCaseParam);
    	quitDriver(driver);  	
    }
}