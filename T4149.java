package uitests.testng.milestone15;

import java.awt.AWTException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import cares.cwds.salesforce.constants.SalesforceConstants;
import cares.cwds.salesforce.pom.Home;
import cares.cwds.salesforce.pom.Login;
import cares.cwds.salesforce.pom.courts.CourtCase;
import cares.cwds.salesforce.pom.courts.CourtHearings;
import cares.cwds.salesforce.pom.courts.CourtMinuteOrder;
import cares.cwds.salesforce.pom.courts.CourtOfficerNotes;
import cares.cwds.salesforce.pom.courts.CourtReports;
import cares.cwds.salesforce.pom.courts.CourtWorkItems;
import cares.cwds.salesforce.pom.courts.DocumentDistributions;
import cares.cwds.salesforce.pom.courts.Participants;
import cares.cwds.salesforce.pom.person.PersonAddresses;
import cares.cwds.salesforce.pom.referral.ReleasesOfInformationAndConsentForm;
import cares.cwds.salesforce.pom.referralcase.CasePersons;
import cares.cwds.salesforce.pom.referralcase.placement.ChildLocations;
import cares.cwds.salesforce.pom.referralcase.placement.LegalAuthority;
import cares.cwds.salesforce.pom.referralcase.placement.Placement;
import cares.cwds.salesforce.pom.referralcase.placement.PlacementProviderSearch;
import cares.cwds.salesforce.pom.referralcase.placement.Removal;
import cares.cwds.salesforce.utilities.testng.TestNGCommon;
import cares.cwds.salesforce.utilities.testng.TestNGListener;
import cares.cwds.salesforce.utilities.web.CommonOperations;
import cares.cwds.salesforce.utilities.web.SalesforceCommon;
import uitests.testng.datasetup.CaseDataSetup1;


	/*****
	 * Traceability*************
	 * CARESV1-233 Courts: File Documents & Send to Parties
	 * 
	 * CARESV1-230 Courts: Document Court Notes in CWS-CARES
	 * 
	 * CARESV1-229 Courts: Receive & Record Court Results and Orders in CWS-CARES
	 * 
	 * CARESV1-216 Courts: Create Court Report
	 * 
	 * CARESV1-180 Courts: Calendar Request
	 * 
	 * CARESV1-148 Courts: Document Detention Hearing Notice
	 *
	 */
	
	/*****
	 * Created By: SIT PaaS Automation Team
	
	 * Created Date:01/17/2025
	
	 * Modified By: SIT PaaS Automation Team
	
	 * Modified Date:02/25/2025
	 * 
	 */

@Listeners(TestNGListener.class)
public class T4149  extends TestNGCommon{
	String testCaseName = "Court Hearing Framework_Verify users should be able to document Court Officer Notes in hearing";
	String moduleName = "cares";
	String fileName = "ScriptMasterSheet"; 
	private static final String Petition_Title ="Findings and Orders/Minute order received"; 

	@BeforeMethod
	public void setUpReport()
	{
		setTestAttributes("testCaseParam",testCaseParam);
	}

	@Test(dataProvider = "data-provider")
	public void testT4149_preReq(String scriptIteration) throws AWTException, InterruptedException{
		testCaseParam.setTestNGTestMethodName("testT4149");
		driver = (WebDriver) getTestAttribute("driver");

		/** PreReq Data ***/
		//Read Investigative Case record from data sheet
		//SalesforceConstants.setConstantValue("CASE_ID1",readOutputSheet("Case", true, "Contra"));
		SalesforceConstants.setConstantValue("CASE_ID1",null);

		//Running preData setup scenario if case record is null from output data sheet
		if(Objects.isNull(SalesforceConstants.getConstantValue("CASE_ID1")) ){
			testCaseParam.setTestNGTestMethodName("testCaseDataSetup1");
			CaseDataSetup1 caseDataSetup = new CaseDataSetup1();
			caseDataSetup.testCaseDataSetup1("1");
			testCaseParam.setTestNGTestMethodName("testT4149");
		}

		///////////////////////// Login As CM Worker/////////////////////////////////////////////////////

		Login login = new Login(driver);
		login.processLoginNew( scriptIteration,SalesforceConstants.POMITERATION1, SalesforceConstants.LOGINUSER); 

		System.out.println("only SetUP and Pre Req T4149");
		
		//Search record
		Home home = new Home(driver);
		home.closeAllTabs();
		home.navigateToAppMenuPage("CARES Items", scriptIteration, SalesforceConstants.POMITERATION1);
		home.searchConstantMethod("Case", scriptIteration, SalesforceConstants.POMITERATION1);

		///////////////////Pre-Condition ///////////////////////////////
		//Add Legal Guardian
		CasePersons caseReferralPersons = new CasePersons(driver);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		SalesforceCommon.scrollDown(driver);
		caseReferralPersons.navigateToInvestigativeCaseFolioPersons( scriptIteration, SalesforceConstants.POMITERATION1);
		caseReferralPersons.addCaseFolioPerson( scriptIteration, SalesforceConstants.POMITERATION1);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);

		//Read Case Persons
		caseReferralPersons.navigateToInvestigativeCaseFolioPersons(scriptIteration, SalesforceConstants.POMITERATION2);
		caseReferralPersons.getCaseFolioPersonDetails();
		String focusChildName= SalesforceConstants.getObjectValue("CASE_PERSON_MAP").get("FocusChild1")[1];
		String victim= SalesforceConstants.getObjectValue("CASE_PERSON_MAP").get("Victim1")[1];

		//Add person addresses to Focus Child
		caseReferralPersons.navigateToCaseFolioPersonName(focusChildName,scriptIteration, SalesforceConstants.POMITERATION2);
		PersonAddresses personAddress = new PersonAddresses(driver);
		personAddress.navigateToAddressesTab(scriptIteration, SalesforceConstants.POMITERATION1);
		personAddress.addPersonAddress(scriptIteration,SalesforceConstants.POMITERATION1);
		
		//Add person addresses to Victim
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		caseReferralPersons.navigateToInvestigativeCaseFolioPersons(scriptIteration, SalesforceConstants.POMITERATION2);
		caseReferralPersons.navigateToCaseFolioPersonName(victim,scriptIteration, SalesforceConstants.POMITERATION2);
		personAddress.navigateToAddressesTab(scriptIteration, SalesforceConstants.POMITERATION1);
		personAddress.addPersonAddress(scriptIteration,SalesforceConstants.POMITERATION1);

		//Create Provider Search Placement
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		PlacementProviderSearch  providerSearch = new PlacementProviderSearch (driver);
		providerSearch.navigateToProviderSearch(scriptIteration,SalesforceConstants.POMITERATION1);
		providerSearch.searchProvderType(scriptIteration,SalesforceConstants.POMITERATION1);
		providerSearch.enterNewPlacementDetails(scriptIteration,SalesforceConstants.POMITERATION1);

		//Navigate to placement
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		Placement placement = new Placement(driver);
		placement.navigateToPlacement(scriptIteration, SalesforceConstants.POMITERATION1);

		//Create removal record
		Removal removal = new Removal(driver);
		removal.navigateToRemovalTab(scriptIteration, SalesforceConstants.POMITERATION1);
		removal.addRemovalDetails(scriptIteration, SalesforceConstants.POMITERATION1);

		//Create child location
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		placement.navigateToPlacement(scriptIteration, SalesforceConstants.POMITERATION1);
		ChildLocations childLocations = new ChildLocations(driver);
		childLocations.clickNewChildLocationType(scriptIteration, SalesforceConstants.POMITERATION1);
		childLocations.enterChildLocationPlacementDetails(scriptIteration, SalesforceConstants.POMITERATION1);

		//write to excel sheet
	}
	
	@Test (dataProvider = "data-provider")
	public void testT4149(String scriptIteration) throws AWTException, InterruptedException{
		//read from excel
		
		driver = (WebDriver) getTestAttribute("driver");

		Login login = new Login(driver);
		login.processLoginNew( scriptIteration,SalesforceConstants.POMITERATION1, SalesforceConstants.LOGINUSER); 

		System.out.println("only script execution");
		
		//Search record
		Home home = new Home(driver);
		home.closeAllTabs();
		home.navigateToAppMenuPage("CARES Items", scriptIteration, SalesforceConstants.POMITERATION1);
		home.searchConstantMethod("Case", scriptIteration, SalesforceConstants.POMITERATION1);

		//Create court case
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		CourtCase courtCase = new CourtCase(driver);
		courtCase.navigateToCourtCase(scriptIteration, SalesforceConstants.POMITERATION1);
		courtCase.courtCaseInformation(scriptIteration, SalesforceConstants.POMITERATION1);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);

		//Navigate to court case record
		courtCase.navigateToCourtCase(scriptIteration, SalesforceConstants.POMITERATION1);
		courtCase.navigateToCourtCaseRecord(scriptIteration, SalesforceConstants.POMITERATION2);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);

		//Navigate and verify court work item related list
		CourtWorkItems courtWorkItem = new CourtWorkItems(driver);
		courtWorkItem.navigateToCourtWorkItems(scriptIteration, SalesforceConstants.POMITERATION1);
		courtWorkItem.verifyCourtWorkItemsRelatedList(scriptIteration, SalesforceConstants.POMITERATION1);

		//Create Hearing Details
		CourtHearings hearing = new CourtHearings(driver);
		hearing.enterHearingDetails(scriptIteration, SalesforceConstants.POMITERATION1);

		//Navigate to Participant and add Focus Child
		Participants participants = new Participants(driver);
		participants.navigateToParticipants(scriptIteration, SalesforceConstants.POMITERATION1);
		participants.verifyParticipantsRelatedList(scriptIteration, SalesforceConstants.POMITERATION2);
		participants.addFocusChild(scriptIteration, SalesforceConstants.POMITERATION2);
		SalesforceConstants.getCurrentPageUrl(driver,"HEARINGCOURTWORKITEM");

     	//Verify Participant for children and workitem header
		participants.navigateToParticipants(scriptIteration, SalesforceConstants.POMITERATION1);
		participants.verifyHeaderColumnsInParticipantsForChildren(scriptIteration, SalesforceConstants.POMITERATION2);
		participants.addPickParticipant(scriptIteration, SalesforceConstants.POMITERATION2);
		participants.verifyHeaderColumnsInParticipantsForWorkItem(scriptIteration, SalesforceConstants.POMITERATION2);

		//Navigate to court work item
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		courtWorkItem = new CourtWorkItems(driver);
		courtWorkItem.navigateToCourtWorkItems(scriptIteration, SalesforceConstants.POMITERATION1);

		//Navigate to hearing record
		hearing = new CourtHearings(driver);
		hearing.navigateToHearingRecord(scriptIteration, SalesforceConstants.POMITERATION1);
		hearing.verifySectionsInHearingDetails(scriptIteration, SalesforceConstants.POMITERATION1);

		//Edit Hearing Detail
		hearing = new CourtHearings(driver);
		hearing.navigateToHearingDetailTab(scriptIteration, SalesforceConstants.POMITERATION2);
		hearing.editHearingRecord(scriptIteration, SalesforceConstants.POMITERATION2);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);	
		hearing.navigateToHearingDetailTab(scriptIteration, SalesforceConstants.POMITERATION2);
		hearing.editHearingRecord(scriptIteration, SalesforceConstants.POMITERATION3);
		
		//Navigate to court minute record and validate new button
		CourtMinuteOrder courtMinuteOrder = new CourtMinuteOrder(driver);
		courtMinuteOrder.navigateToCourtMinuteOrder(scriptIteration, SalesforceConstants.POMITERATION1);
		courtMinuteOrder.validateNewBtn();

		//Add court minute order
		courtMinuteOrder.navigateToCourtMinuteOrderPage(scriptIteration, SalesforceConstants.POMITERATION1);
		courtMinuteOrder.addCourtMinuteOrderPageInformation(scriptIteration, SalesforceConstants.POMITERATION1);
		courtMinuteOrder.getCourtMiniteOrderID(scriptIteration, SalesforceConstants.POMITERATION1);

		//Verify notification
		home = new Home(driver);
		String name = SalesforceConstants.getObjectValue("CASE_PERSON_MAP").get("Victim1")[1];
		String cwiID = 	SalesforceConstants.getConstantValue("HEARING_ID1");
		home.verifyCourtNotification(Petition_Title,"Findings and Orders/Minute Orders have been received for petition null for following children that is available on " + cwiID + " for : " + name.replace(" ","  "));

		//Edit Court Minute Order
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		courtMinuteOrder.navigateToCourtMinuteOrder(scriptIteration, SalesforceConstants.POMITERATION1);
		courtMinuteOrder.navigateToExistingCourtMinuteOrderRecord(scriptIteration, SalesforceConstants.POMITERATION2);
		courtMinuteOrder.addCourtMinuteOrderPageInformation(scriptIteration, SalesforceConstants.POMITERATION2);

		//Navigate to placement
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		Placement placement = new Placement(driver);
		placement.navigateToPlacement(scriptIteration, SalesforceConstants.POMITERATION1);

		//Navigate to Removal record
		Removal removal = new Removal(driver);
		removal.navigateToRemovalTab(scriptIteration, SalesforceConstants.POMITERATION1);
		removal.navigateToRemovalRecord(scriptIteration, SalesforceConstants.POMITERATION1);

		//Create new legal authority
		LegalAuthority legalAuthority = new LegalAuthority(driver);
		legalAuthority.navigateToLegalAuthorityTab(scriptIteration,SalesforceConstants.POMITERATION1);
		legalAuthority.clickNewLegalAuthority(scriptIteration,SalesforceConstants.POMITERATION1);
		legalAuthority.addLegalInfo(scriptIteration,SalesforceConstants.POMITERATION1);
		
		//Validate Legal Authority Headers
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		placement.navigateToPlacement(scriptIteration, SalesforceConstants.POMITERATION1);
		removal.navigateToRemovalTab(scriptIteration, SalesforceConstants.POMITERATION1);
		removal.navigateToRemovalRecord(scriptIteration, SalesforceConstants.POMITERATION1);
		legalAuthority.navigateToLegalAuthorityTab(scriptIteration,SalesforceConstants.POMITERATION1);
		legalAuthority.verifyingLegalAuthorityHeaders(scriptIteration,SalesforceConstants.POMITERATION1);

		//Navigate and edit court minute order
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		courtMinuteOrder = new CourtMinuteOrder(driver);
		courtMinuteOrder.navigateToCourtMinuteOrder(scriptIteration, SalesforceConstants.POMITERATION1);
		courtMinuteOrder.navigateToExistingCourtMinuteOrderRecord(scriptIteration,SalesforceConstants.POMITERATION3);
		courtMinuteOrder.addCourtMinuteOrderPageInformation(scriptIteration,SalesforceConstants.POMITERATION3);
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);

		//Navigate to court work item
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		CourtWorkItems courtWorkItems = new CourtWorkItems(driver);
		courtWorkItems.navigateToCourtWorkItems(scriptIteration, SalesforceConstants.POMITERATION1);

		//Add Court officer notes
		hearing = new CourtHearings(driver);
		hearing.navigateToHearingRecord(scriptIteration, SalesforceConstants.POMITERATION4);
		CourtOfficerNotes courtOfficerNotes = new CourtOfficerNotes(driver);
		courtOfficerNotes.navigateToCourtOfficerNotes(scriptIteration, SalesforceConstants.POMITERATION1);
		courtOfficerNotes.addCourtOfficerNotesPageDetails(scriptIteration, SalesforceConstants.POMITERATION1);

		//Navigate to document distribution and add court document information
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		DocumentDistributions documentDistributions = new DocumentDistributions(driver);
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION1);    
		documentDistributions.addDistributionType(scriptIteration, SalesforceConstants.POMITERATION1);
		documentDistributions.addCourtDocumentsInformation(scriptIteration, SalesforceConstants.POMITERATION1);

		//Add distribution type and notice information - Court Work Item Participants
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION1);
		documentDistributions.addDistributionType(scriptIteration, SalesforceConstants.POMITERATION2);
		documentDistributions.addNoticeInformation(scriptIteration, SalesforceConstants.POMITERATION2);

		//Verify notice record - Court Work Item Participants
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION1);
		documentDistributions.navigateToNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION2);
		documentDistributions.verfiyNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION2);
			
		//Add distribution type and notice information - District Attorney
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION3);
		documentDistributions.addDistributionType(scriptIteration, SalesforceConstants.POMITERATION3);
		documentDistributions.addNoticeInformation(scriptIteration, SalesforceConstants.POMITERATION3);

		//Verify notice record - District Attorney - District Attorney
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION3);
		documentDistributions.navigateToNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION3);
		documentDistributions.verfiyNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION3);
		
		//Add distribution type and notice information - Probate Court
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION4); 
		documentDistributions.addDistributionType(scriptIteration, SalesforceConstants.POMITERATION4);
		documentDistributions.addNoticeInformation(scriptIteration, SalesforceConstants.POMITERATION4);

		//Verify notice record - Probate Court
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION4); 
		documentDistributions.navigateToNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION4);
		documentDistributions.verfiyNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION4);	
		
		//Edit distribution type and notice information - Tribe
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION5); 
		documentDistributions.addDistributionType(scriptIteration, SalesforceConstants.POMITERATION5);
		documentDistributions.addNoticeInformation(scriptIteration, SalesforceConstants.POMITERATION5);

		//Verify notice record - Tribe
		documentDistributions.navigateToDocumentDistributions(scriptIteration, SalesforceConstants.POMITERATION5); 
		documentDistributions.navigateToNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION5);
		documentDistributions.verfiyNoticeRecord(scriptIteration, SalesforceConstants.POMITERATION5);

		//Verify sections in hearing detail
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		hearing = new CourtHearings(driver);
		hearing.navigateToHearingDetailTab(scriptIteration, SalesforceConstants.POMITERATION5);
		hearing.verifySectionsInHearingDetails(scriptIteration, SalesforceConstants.POMITERATION5);

		//Add court report information
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.HEARINGCOURTWORKITEMURL);
		CourtReports courtReports = new CourtReports(driver);
		courtReports.navigateToCourtReports(scriptIteration, SalesforceConstants.POMITERATION1);
		courtReports.addCourtReportPageInformation(scriptIteration, SalesforceConstants.POMITERATION1);

		//Add release information and consent form details  
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		ReleasesOfInformationAndConsentForm releasesOfInformationAndConsentForm = new ReleasesOfInformationAndConsentForm(driver);
		releasesOfInformationAndConsentForm.navigateToReleaseOfInformationAndConsentForm(scriptIteration, SalesforceConstants.POMITERATION1);
		releasesOfInformationAndConsentForm.addReleaseOfInformationAndConsentFormDetails(scriptIteration, SalesforceConstants.POMITERATION1); 
		
		//Validate headers
		SalesforceCommon.navigateToRecordURL(driver, SalesforceConstants.CASEURL);
		releasesOfInformationAndConsentForm.navigateToReleaseOfInformationAndConsentForm(scriptIteration, SalesforceConstants.POMITERATION1);
		releasesOfInformationAndConsentForm.navigateToReleaseInformationAndConsentFormRecord(scriptIteration, SalesforceConstants.POMITERATION1);
		releasesOfInformationAndConsentForm.verfiyReleaseInformationAndConsentForm(scriptIteration, SalesforceConstants.POMITERATION1);

	}

	@DataProvider (name = "data-provider")
	public String[] dpMethod(Method method){
		testCaseParam.setTestCaseName(method.getName() + "_"+ testCaseName + CommonOperations.getTodaysDateInPST("ns"));
		testCaseParam.setModuleName(moduleName);
		testCaseParam.setBrowser(browser);
		testCaseParam.setTestCaseDescription(testCaseParam.getTestCaseName());
		testCaseParam.setTestCaseDescription(testCaseParam.getTestCaseName());
		testCaseParam.setTestNGTestMethodName(method.getName());
		return setScriptIterationFlag(fileName,moduleName, method.getName());

	}
}