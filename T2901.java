package uitests.testng.milestone3;


import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.pom.ApprovalandAuditHistory;
import cares.cwds.salesforce.pom.Home;
import cares.cwds.salesforce.pom.Login;
import cares.cwds.salesforce.pom.Logout;
import cares.cwds.salesforce.pom.RecallAndResubmitRecord;
import cares.cwds.salesforce.pom.SubmitForApproval;
import cares.cwds.salesforce.pom.SupervisorReviewPromotion;
import cares.cwds.salesforce.pom.screening.Allegations;
import cares.cwds.salesforce.pom.screening.CallBackAttempts;
import cares.cwds.salesforce.pom.screening.Documents;
import cares.cwds.salesforce.pom.screening.ERRDoc;
import cares.cwds.salesforce.pom.screening.ScreeningAddress;
import cares.cwds.salesforce.pom.screening.ScreeningApproval;
import cares.cwds.salesforce.pom.screening.ScreeningAssessments;
import cares.cwds.salesforce.pom.screening.ScreeningContactLog;
import cares.cwds.salesforce.pom.screening.ScreeningDetails;
import cares.cwds.salesforce.pom.screening.ScreeningPerson;
import cares.cwds.salesforce.pom.screening.ScreeningSafetyAlert;
import cares.cwds.salesforce.pom.screening.ScreeningTribalInquiry;
import cares.cwds.salesforce.pom.screening.ScreeningValidatePerson;
import cares.cwds.salesforce.pom.screening.ScreeningsPage;
import cares.cwds.salesforce.web.utilities.CustomException;
import cares.cwds.salesforce.web.utilities.SalesforceCommon;
import testsettings.TestRunSettings;
import uitests.testng.common.TestNGCommon;

public class T2901 extends TestNGCommon {

	String testCaseName = "To validate Title IV Placement Oversight Staff user is able to create and edit a new Service Category, Service Type and Service Name (EBP) records and validate notifications.";
	String moduleName = "SD";
	String fileName = "ScriptMasterSheet";

	public T2901(){

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

	@Test (dataProvider = "data-provider")
	public void testT2901(String scriptIteration) throws CustomException, InterruptedException {

		if(driver == null) {
			driver = initializeDriver();
		}

		Login login = new Login(driver, testCaseParam);
//		login.processLoginNew(testCaseParam, scriptIteration,"1", "LOGIN_USER");

		Home home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Screenings",testCaseParam, scriptIteration, "1");
		
		SalesforceConstants.setConstantValue("SCP_ID"+1, "SCP-000005374518");
		SalesforceConstants.setConstantValue("SCP_ID"+2, "SCP-000005374618");
		SalesforceConstants.setConstantValue("SCP_ID"+3, "SCP-000005374718");
		SalesforceConstants.setConstantValue("SCP_ID"+4, "SCP-000005374818");
		SalesforceConstants.setConstantValue("personName"+1, "AUTOTonette"+" "+"AUTOReyna");
		SalesforceConstants.setConstantValue("personName"+2, "AUTOMitchell"+" "+"AUTOHue");
		SalesforceConstants.setConstantValue("personName"+3, "AUTOJewel"+" "+"AUTOBrady");
		SalesforceConstants.setConstantValue("personName"+4, "AUTOVirgil"+" "+"AUTOKraig");
		
		SalesforceConstants.setConstantValue("SCR_ID"+1, "SCR-000002902218");
		SalesforceConstants.setConstantValue("SAR_ID"+1, "SAR-000001774118");
		
		ScreeningsPage screeningPage = new ScreeningsPage(driver, testCaseParam);   	 		
//		screeningPage.navigateToListView("Screenings", "Approved Screenings", testCaseParam, scriptIteration, "1");
//		screeningPage.checkStatusOnScreeningRecord(testCaseParam, scriptIteration,"1");
//		screeningPage.navigateToListView("Screenings", "Current Screenings", testCaseParam, scriptIteration, "1");
//		screeningPage.navigateToLatestRecord(testCaseParam, scriptIteration,"1");

		ScreeningDetails screeningDetails = new ScreeningDetails(driver, testCaseParam);
//		screeningDetails.clickNewScreening(testCaseParam, scriptIteration,"1");
//		screeningDetails.enterScreeningDetails(testCaseParam, scriptIteration,"1");
//		screeningDetails.submitScreeningDetails(testCaseParam, scriptIteration,"1");

		ScreeningPerson screeningPerson= new ScreeningPerson(driver, testCaseParam);
//		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//		screeningPerson.addScreeningPerson(testCaseParam, scriptIteration , "1");
//		screeningPerson.addScreeningPerson(testCaseParam, scriptIteration , "2");
//		screeningPerson.addScreeningPerson(testCaseParam, scriptIteration , "3");
//		screeningPerson.addScreeningPerson(testCaseParam, scriptIteration , "4");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		ScreeningAddress screeningAddress = new ScreeningAddress(driver, testCaseParam);
//		screeningAddress.navigateToScreeningAddresses(testCaseParam, scriptIteration,"1");
		screeningAddress.addScreeningAddress(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		CallBackAttempts callBackAttempts = new CallBackAttempts(driver, testCaseParam);
//		callBackAttempts.navigateToScrCallBackAttempts(testCaseParam, scriptIteration,"1");
//		callBackAttempts.addCallBackAttemptsInfo(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		Allegations allegations = new Allegations(driver, testCaseParam);
//		allegations.navigateToScreeningAllegations(testCaseParam, scriptIteration,"1");
//		allegations.addAllegationsInfo(testCaseParam, scriptIteration,"1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		ScreeningSafetyAlert screeningSafetyAlert = new ScreeningSafetyAlert(driver, testCaseParam);
//		screeningSafetyAlert.navigateToScreeningSafetyAlert(testCaseParam, scriptIteration, "1");
//		screeningSafetyAlert.addScreeningSafetyAlert(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		//Tab is available but new button is not available
		//ScreeningAssessments screeningAssessments = new ScreeningAssessments(driver, testCaseParam);
		//screeningAssessments.navigateToAssessments(testCaseParam, scriptIteration, "1");
		//screeningAssessments.createNewAssessments(testCaseParam, scriptIteration, "1");
		//SalesforceCommon.navigateToRecordURL(driver, "SCR");
	
		ScreeningTribalInquiry tribalInquiryAndCollaboration = new ScreeningTribalInquiry(driver, testCaseParam);
//		tribalInquiryAndCollaboration.navigateToScreeningContactLogs(testCaseParam, scriptIteration,"1");
//		tribalInquiryAndCollaboration.addTribalInquiryAndCollaborationInfo(testCaseParam, scriptIteration,"1");
			
		Documents doucments = new Documents(driver, testCaseParam);
//		doucments.documentSection(testCaseParam, scriptIteration, "1");
//		doucments.uploadDocument(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
		tribalInquiryAndCollaboration = new ScreeningTribalInquiry(driver, testCaseParam);
//		tribalInquiryAndCollaboration.navigateToScreeningContactLogs(testCaseParam, scriptIteration,"2");
//		tribalInquiryAndCollaboration.addTribalInquiryAndCollaborationInfo(testCaseParam, scriptIteration,"2");
			
		doucments = new Documents(driver, testCaseParam);
//		doucments.documentSection(testCaseParam, scriptIteration, "2");
//		doucments.uploadDocument(testCaseParam, scriptIteration, "2");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		ScreeningContactLog screeningContact = new ScreeningContactLog(driver, testCaseParam);
//		screeningContact.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "1");
//		screeningContact.addScreeningContactLog(testCaseParam, scriptIteration, "1");
			
		doucments = new Documents(driver, testCaseParam);
//		doucments.documentSection(testCaseParam, scriptIteration, "1");
//		doucments.uploadDocument(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		//Not required the steps
		//SupervisorReviewPromotion review= new SupervisorReviewPromotion(driver, testCaseParam);
		//review.navigateToSupervisorReviewPromotion(testCaseParam, scriptIteration, "1");
		//review.addSupervisorReviewPromotionInfo(testCaseParam, scriptIteration,"1");

		ERRDoc errDoc= new ERRDoc(driver, testCaseParam);
//		errDoc.generateERRDoc(testCaseParam,scriptIteration, "1");

		ScreeningValidatePerson validatePerson = new ScreeningValidatePerson(driver, testCaseParam);
//		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//		validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
//		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//		validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "2");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
//		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//		validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "3");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
//		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "1");
//		validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "4");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");

		ApprovalandAuditHistory approvalandAuditHistory = new ApprovalandAuditHistory(driver, testCaseParam);
//		approvalandAuditHistory.navigateToApprovalAuditHistory(testCaseParam, scriptIteration, "1");
//		approvalandAuditHistory.verifyHeaderColumnsInTable("Screening History", testCaseParam, scriptIteration, "1");

		SupervisorReviewPromotion review = new SupervisorReviewPromotion(driver, testCaseParam);
//		review.navigateToSupervisorReviewPromotion(testCaseParam, scriptIteration, "1");
//		review.addSupervisorReviewPromotionInfo(testCaseParam, scriptIteration,"1");

		SubmitForApproval approval= new SubmitForApproval (driver,testCaseParam);
//		approval.clickSubmitForApprovalInfo(testCaseParam, scriptIteration,"1");
	
		Logout objLogout = new Logout(driver, testCaseParam);
//    	objLogout.processLogout(testCaseParam, scriptIteration, "1");
//
//		login = new Login(driver, testCaseParam);
//		login.processLoginNew(testCaseParam, scriptIteration,"1", "HL_SUPERVISOR");
//
//		home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
//		home.clickOnNotificationForApproval("SCR_ID", testCaseParam,scriptIteration, "1");
//
		ScreeningApproval screeningApproval = new ScreeningApproval(driver,testCaseParam);
//		screeningApproval.rejectScreening(testCaseParam,scriptIteration,"1");

//		objLogout = new Logout(driver, testCaseParam);
//		objLogout.processLogout(testCaseParam,scriptIteration,"1");

//		login = new Login(driver, testCaseParam);
//		login.processLoginNew(testCaseParam, scriptIteration,"1", "LOGIN_USER");

//		home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
//    	home.searchRecord(testCaseParam,scriptIteration, "1");
    
    	//step 46
		screeningDetails.navigateToScreeningDetails(testCaseParam, scriptIteration, "2");
//		screeningDetails.editCallerType(testCaseParam, scriptIteration, "2");
//		screeningDetails.submitScreeningDetails(testCaseParam, scriptIteration, "2");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
//		screeningPerson = new ScreeningPerson(driver, testCaseParam);
//    	screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "5");
//   	
//    	validatePerson = new ScreeningValidatePerson(driver, testCaseParam);
//    	validatePerson.validatePersonDetails(testCaseParam, scriptIteration, "5"); 
//    	SalesforceCommon.navigateToRecordURL(driver, "SCR");

		//saftey alerts screen is disable to edit
    	
//		screeningAddress = new ScreeningAddress(driver, testCaseParam);
//		screeningAddress.navigateToScreeningAddresses(testCaseParam, scriptIteration,"2");
//		screeningAddress.editScreeningAddress(testCaseParam, scriptIteration, "2");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
		//screenig alligation ia disable to edit

//		approval= new SubmitForApproval (driver,testCaseParam);
//		approval.clickSubmitForApprovalInfo(testCaseParam, scriptIteration,"1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
		//step 49 and 50 are duplicate steps 

//		approvalandAuditHistory = new ApprovalandAuditHistory(driver, testCaseParam);
//		approvalandAuditHistory.navigateToApprovalAuditHistory(testCaseParam, scriptIteration, "1");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
//		screeningDetails= new ScreeningDetails(driver, testCaseParam);
//    	screeningDetails.navigateToScreeningDetails(testCaseParam,scriptIteration,"1");
//   	screeningDetails.verifyScreeningDetailsNonEditable(testCaseParam, scriptIteration,"1");
//   	SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
//    	screeningPerson = new ScreeningPerson(driver, testCaseParam);
//   	screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "5");
   	
//    	validatePerson = new ScreeningValidatePerson(driver, testCaseParam);
//    	validatePerson.validatePersonNonEditable(testCaseParam, scriptIteration, "6"); 
//    	SalesforceCommon.navigateToRecordURL(driver, "SCR");
    	
    	//alligation and saftey to validate non editable
		
//		objLogout = new Logout(driver, testCaseParam);
//		objLogout.processLogout(testCaseParam,scriptIteration,"1");

//		login = new Login(driver, testCaseParam);
//		login.processLoginNew(testCaseParam, scriptIteration,"1", "HL_SUPERVISOR");

//		home = new Home(driver, testCaseParam);
//		home.navigateToAppMenuPage("Screenings", testCaseParam, scriptIteration, "1");
		
//		screeningPage = new ScreeningsPage(driver, testCaseParam);   	 		
//		screeningPage.navigateToListView("Screenings", "Pending Approvals", testCaseParam, scriptIteration, "2");

//		home = new Home(driver, testCaseParam);
//		home.searchRecord(testCaseParam,scriptIteration, "1");
		//home.innerSearch(testCaseParam, scriptIteration, "1");

//	    review = new SupervisorReviewPromotion(driver, testCaseParam);
//		review.navigateToSupervisorReviewPromotion(testCaseParam, scriptIteration, "1");
		
//		home = new Home(driver, testCaseParam);
//		home.clickOnNotificationForApproval("SCR_ID", testCaseParam,scriptIteration, "1");
    	
//		screeningApproval = new ScreeningApproval(driver,testCaseParam);
//		screeningApproval.approveScreening(testCaseParam,scriptIteration,"2");
//		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		
		Documents document = new Documents(driver, testCaseParam);
//		document.navigateToDocuments(testCaseParam, scriptIteration, "2");
//		document.validateDocumentList(testCaseParam, scriptIteration, scriptIteration, "2");
		
//		home = new Home(driver, testCaseParam);
//		home.verifyNotification(testCaseParam, "Approval request for the screening is approved", SalesforceConstants.getConstantValue("SCR_ID1"));
       
      //65 assignment page not available
        
//      objLogout = new Logout(driver, testCaseParam);
//    	objLogout.processLogout(testCaseParam,scriptIteration,"1");   

     	//67 steps
		login = new Login(driver, testCaseParam);
		login.processLoginNew(testCaseParam, scriptIteration,"1", "LOGIN_USER");

		home = new Home(driver, testCaseParam);
		home.navigateToAppMenuPage("Screenings",testCaseParam, scriptIteration, "1");	
		
		screeningPage = new ScreeningsPage(driver, testCaseParam);   
		screeningPage.navigateToListView("Screenings", "Pending Approvals", testCaseParam, scriptIteration, "2");

		//Need to create one more screening record
		
		review = new SupervisorReviewPromotion(driver, testCaseParam);
		review.navigateToSupervisorReviewPromotion(testCaseParam, scriptIteration, "1");

		RecallAndResubmitRecord recall= new RecallAndResubmitRecord(driver,testCaseParam);
		recall.clickRecallAndResubmitRecordInfo(testCaseParam, scriptIteration, "1");

		screeningPage = new ScreeningsPage(driver, testCaseParam); 
		screeningPage.checkStatusOnScreeningRecord(testCaseParam, scriptIteration, "2");

		objLogout = new Logout(driver, testCaseParam);
		objLogout.processLogout(testCaseParam,scriptIteration,"1");

		//Step 72
		login = new Login(driver, testCaseParam);
		login.processLoginNew(testCaseParam, scriptIteration,"1", "HL_SUPERVISOR");

		home = new Home(driver, testCaseParam);
		home.navigateToAppMenuPage("Screenings",testCaseParam, scriptIteration, "1");
		home.searchRecord(testCaseParam,scriptIteration, "1");

		screeningDetails.navigateToScreeningDetails(testCaseParam, scriptIteration, "2");
		screeningDetails.addScreeningDetailsTest(testCaseParam, scriptIteration,"2");
		screeningDetails.submitScreeningDetails(testCaseParam, scriptIteration, "1");

		screeningPerson.navigateToScreeningPersons(testCaseParam, scriptIteration, "2");
		screeningPerson.addScreeningPerson(testCaseParam, scriptIteration, "7"); 

		ScreeningTribalInquiry tribalInquiry=new ScreeningTribalInquiry(driver, testCaseParam);
		tribalInquiry.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "2");
		tribalInquiry.addTribalInquiryAndCollaborationInfo(testCaseParam, scriptIteration, "2");

		screeningContact.navigateToScreeningContactLogs(testCaseParam, scriptIteration, "1");
		screeningContact.addScreeningContactLog(testCaseParam, scriptIteration, "2");

		//75 doubt

		//76 create new screening
		document = new Documents(driver, testCaseParam);
		document.navigateToDocuments(testCaseParam, scriptIteration,"1");
		document.uploadDocument(testCaseParam, scriptIteration, "1");  	
		SalesforceCommon.navigateToRecordURL(driver, "SCR");
		document.navigateToDocuments(testCaseParam, scriptIteration,"1");
		document.downloadDocument(testCaseParam, scriptIteration, "1");

		objLogout = new Logout(driver, testCaseParam);
		objLogout.processLogout(testCaseParam,scriptIteration,"1");  

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


