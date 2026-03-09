package tests;

import Base.BaseTest;
import Pages.WithdrawnInitiativePage;
import Pages.InitiativePage;
import Utils.ExcelReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import java.io.File;

/**
 * Withdrawn Initiatives Test Suite
 * @author Gayatri.k
 */
@Epic("Initiative Management")
@Feature("Withdrawn Initiatives")
public class WithdrawnInitiativeTest extends BaseTest {

    private WithdrawnInitiativePage withdrawnInitiativePage;
    private InitiativePage initiativePage;
    
    // Cache Excel file path to avoid searching multiple times // Gayatri.k
    private static String cachedExcelPath = null;

    @Test(priority = 1, enabled = true, groups = {"Withdrawn"})
    @Description("TC_001 - Navigate to Withdrawn Initiatives page and verify list view")
    @Story("Withdrawn Initiatives Navigation")
    @Severity(SeverityLevel.NORMAL)
    public void TC_001_withdrawnNavigation() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
        withdrawnInitiativePage.verifyWithdrawnHeader();
        withdrawnInitiativePage.validateListViewColumns();
        boolean hasRecords = withdrawnInitiativePage.hasWithdrawnRecords();
        if (!hasRecords && reportLogger != null) {
            reportLogger.warning("No Withdrawn Initiatives records found");
        }
    }

    @Test(priority = 2, enabled = true, groups = {"Withdrawn"})
    @Description("TC_002 - Validate Search functionality")
    @Story("Withdrawn Initiatives Search")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_validateSearchFunctionality() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
    //    waitForSeconds(2);
        
        String[] searchData = getSearchDataFromExcel("TC_002");
        String natureOfInitiative = searchData[0];
        String businessGroup = searchData[1];
        String organizationUnit = searchData[2];
        String initiativeCode = searchData[3];
        String initiativeTitle = searchData[4];
        
        withdrawnInitiativePage.clickSearchIcon();
        if (!natureOfInitiative.isEmpty()) withdrawnInitiativePage.selectNatureOfInitiative(natureOfInitiative);
        if (!businessGroup.isEmpty()) withdrawnInitiativePage.selectBusinessGroup(businessGroup);
        if (!organizationUnit.isEmpty()) withdrawnInitiativePage.selectOrganizationUnit(organizationUnit);
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        if (!initiativeTitle.isEmpty()) withdrawnInitiativePage.enterInitiativeTitle(initiativeTitle);
        withdrawnInitiativePage.clickSearchButton();
   //     waitForSeconds(2);
        
        boolean recordFound = withdrawnInitiativePage.verifyMatchingRecord(initiativeCode);
        if (!recordFound) {
            throw new Exception("TC_002 FAILED - Record not found for code: " + initiativeCode);
        }
    }

    @Test(priority = 3, enabled = true, groups = {"Withdrawn"})
    @Description("TC_003 - Validate Switch to Card View Toggle")
    @Story("Withdrawn Initiatives Card View")
    @Severity(SeverityLevel.NORMAL)
    public void TC_003_validateCardViewToggle() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
    //    waitForSeconds(2);
        withdrawnInitiativePage.clickSwitchToCardViewToggle();
    //    waitForSeconds(2);
        boolean cardViewVerified = withdrawnInitiativePage.verifyCardViewDetails();
        if (!cardViewVerified) {
            throw new Exception("TC_003 FAILED - Card view details not verified");
        }
    }

    @Test(priority = 4, enabled = true, groups = {"Withdrawn"})
    @Description("TC_004 - Validate edit view of the initiative")
    @Story("Withdrawn Initiatives Edit View")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_004_validateEditView() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
   //     waitForSeconds(2);
        withdrawnInitiativePage.clickSearchIcon();
        String initiativeCode = getInitiativeCodeFromExcel("TC_004");
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        withdrawnInitiativePage.clickSearchButton();
    //    waitForSeconds(2);
        withdrawnInitiativePage.clickEditIcon(initiativeCode);
   //     waitForSeconds(2);
        boolean tabsVerified = withdrawnInitiativePage.verifyEditViewTabs();
        if (!tabsVerified) {
            throw new Exception("TC_004 FAILED - Edit view tabs not verified");
        }
    }

    @Test(priority = 5, enabled = true, groups = {"Withdrawn"})
    @Description("TC_005 - Verify the Flow of withdrawn through Initiatives page")
    @Story("Withdrawn Initiatives Flow")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_005_verifyWithdrawnFlow() throws Exception {
        // Step 1: Click on Initiatives // Gayatri.k
        initiativePage = new InitiativePage(webDriver, reportLogger); // Gayatri.k
        initiativePage.navigateToInitiative(); // Gayatri.k

        
        // Step 2: Click on Search // Gayatri.k
        initiativePage.clickSearchIconForWithdrawnFlow(); // Gayatri.k
  
        
        // Step 3: Click on Initiative code and take the value from xlsx // Gayatri.k
        String initiativeCode = getInitiativeCodeFromExcel("TC_005"); // Gayatri.k
        
        // Step 4: Click on Search button // Gayatri.k
        initiativePage.enterInitiativeCodeInSearchForWithdrawn(initiativeCode); // Gayatri.k
        initiativePage.clickSearchButtonForWithdrawnFlow(); // Gayatri.k
    
        
        // Step 5: Click on edit icon of the searched record if present or if not present then go to withdrawn initiative page // Gayatri.k
        boolean recordFound = initiativePage.verifyInitiativeRecordExists(initiativeCode); // Gayatri.k
        if (recordFound) { // Gayatri.k
            // Record found, click edit icon // Gayatri.k
            initiativePage.clickEditIconForInitiativeCode(initiativeCode); // Gayatri.k
      
        } else { // Gayatri.k
            // Record not found, go to withdrawn initiative page // Gayatri.k
            withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger); // Gayatri.k
            withdrawnInitiativePage.navigateToWithdrawnInitiativesPage(); // Gayatri.k
        
            withdrawnInitiativePage.clickSearchIcon(); // Gayatri.k
            withdrawnInitiativePage.enterInitiativeCode(initiativeCode); // Gayatri.k
            withdrawnInitiativePage.clickSearchButton(); // Gayatri.k
       
            
            // If present in withdrawn page, test passes // Gayatri.k
            boolean foundInWithdrawn = withdrawnInitiativePage.verifyMatchingRecord(initiativeCode); // Gayatri.k
            if (foundInWithdrawn) { // Gayatri.k
                if (reportLogger != null) { // Gayatri.k
                    reportLogger.pass("TC_005 - Initiative found in Withdrawn Initiatives page (already withdrawn)"); // Gayatri.k
                } // Gayatri.k
                return; // Gayatri.k
            } else { // Gayatri.k
                throw new Exception("TC_005 FAILED - Initiative code '" + initiativeCode + "' not found in Initiatives page or Withdrawn Initiatives page"); // Gayatri.k
            } // Gayatri.k
        } // Gayatri.k
        
        // Step 6: Click on Withdrawn initiative button // Gayatri.k
        initiativePage.clickWithdrawnInitiativeButton(); // Gayatri.k


        
        // Step 7: Verify initiative withdrawn successfully // Gayatri.k
        // Case 1: If alert comes like "You dont have access to withdrawn" then user should not be able to withdraw (converted initiative) // Gayatri.k
        boolean accessDeniedAlert = initiativePage.verifyWithdrawAccessDeniedAlert(); // Gayatri.k
        if (accessDeniedAlert) { // Gayatri.k
            if (reportLogger != null) { // Gayatri.k
                reportLogger.pass("TC_005 - Initiative is converted, cannot withdraw (expected behavior)"); // Gayatri.k
            } // Gayatri.k
            return; // Gayatri.k
        } // Gayatri.k
        
        // Case 2: If successfully withdrawn alert is displayed then click on Go to List View button // Gayatri.k
        boolean successAlert = initiativePage.verifyWithdrawSuccessAlert(); // Gayatri.k
        if (successAlert) { // Gayatri.k
            initiativePage.clickGoBackToListView(); // Gayatri.k
            
        } else { // Gayatri.k
            throw new Exception("TC_005 FAILED - Neither access denied nor success alert found after clicking withdraw"); // Gayatri.k
        } // Gayatri.k
        
        // Step 8: Click on Search icon // Gayatri.k
        initiativePage.clickSearchIconForWithdrawnFlow(); // Gayatri.k
     // Gayatri.k
        
        // Step 9: Click on Initiative code and take the value from xlsx // Gayatri.k
        // Use same initiative code from Step 3 // Gayatri.k
        
        // Step 10: Click on Search button // Gayatri.k
        initiativePage.enterInitiativeCodeInSearchForWithdrawn(initiativeCode); // Gayatri.k
        initiativePage.clickSearchButtonForWithdrawnFlow(); // Gayatri.k
 
        // Step 11: Verify the code if record is not present then code is withdrawn successfully // Gayatri.k
        boolean recordStillPresent = initiativePage.verifyInitiativeRecordExists(initiativeCode); // Gayatri.k
        if (recordStillPresent) { // Gayatri.k
            throw new Exception("TC_005 FAILED - Initiative code '" + initiativeCode + "' is still present after withdrawal. Withdrawal may have failed."); // Gayatri.k
        } else { // Gayatri.k
            // Verify it appears in Withdrawn Initiatives page // Gayatri.k
            withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger); // Gayatri.k
            withdrawnInitiativePage.navigateToWithdrawnInitiativesPage(); // Gayatri.k
     
            withdrawnInitiativePage.clickSearchIcon(); // Gayatri.k
            withdrawnInitiativePage.enterInitiativeCode(initiativeCode); // Gayatri.k
            withdrawnInitiativePage.clickSearchButton(); // Gayatri.k
       //     waitForSeconds(2); // Gayatri.k
            
            boolean foundInWithdrawnPage = withdrawnInitiativePage.verifyMatchingRecord(initiativeCode); // Gayatri.k
            if (foundInWithdrawnPage) { // Gayatri.k
                if (reportLogger != null) { // Gayatri.k
                    reportLogger.pass("TC_005 PASSED - Initiative successfully withdrawn and found in Withdrawn Initiatives page"); // Gayatri.k
                } // Gayatri.k
            } else { // Gayatri.k
                throw new Exception("TC_005 FAILED - Initiative code '" + initiativeCode + "' not found in Withdrawn Initiatives page after withdrawal"); // Gayatri.k
            } // Gayatri.k
        } // Gayatri.k
    }

    @Test(priority = 6, enabled = true, groups = {"Withdrawn"})
    @Description("TC_006 - Verify whether the withdrawn initiative is displayed on the page")
    @Story("Withdrawn Initiatives Display")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_006_verifyWithdrawnInitiativeDisplayed() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
   //     waitForSeconds(2);
        withdrawnInitiativePage.clickSearchIcon();
        String initiativeCode = getInitiativeCodeFromExcel("TC_006");
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        withdrawnInitiativePage.clickSearchButton();
   //     waitForSeconds(2);
        boolean recordFound = withdrawnInitiativePage.verifyMatchingRecord(initiativeCode);
        if (!recordFound) {
            throw new Exception("TC_006 FAILED - Initiative code '" + initiativeCode + "' not displayed");
        }
    }

    @Test(priority = 7, enabled = true, groups = {"Withdrawn"})
    @Description("TC_007 - Verify Resubmit button & check resubmitted initiative displayed on approvers inbox")
    @Story("Withdrawn Initiatives Resubmit Flow")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_007_verifyResubmitFlow() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        initiativePage = new InitiativePage(webDriver, reportLogger);
        
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
   //    waitForSeconds(3); // Increased wait for page to load
        Thread.sleep(1000);
        withdrawnInitiativePage.clickSearchIcon();
        String initiativeCode = getInitiativeCodeFromExcel("TC_007");
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        withdrawnInitiativePage.clickSearchButton();
   //     waitForSeconds(3); // Increased wait for search results to load
        
        if (!withdrawnInitiativePage.verifyMatchingRecord(initiativeCode)) {
            throw new Exception("TC_007 FAILED - Initiative code '" + initiativeCode + "' not found in withdrawn initiatives. Please ensure the initiative code exists and is in withdrawn status.");
        }
        
        withdrawnInitiativePage.clickResubmitIcon(initiativeCode);
   //     waitForSeconds(2);
        withdrawnInitiativePage.clickResubmitSaveButton();
   //     waitForSeconds(2);
        
        if (!withdrawnInitiativePage.verifyCommentAlert("Comment should not be left blank.")) {
            throw new Exception("TC_007 FAILED - Alert message not found");
        }
        
        String comment = getCommentFromExcel("TC_007");
        withdrawnInitiativePage.enterResubmitComment(comment);
        withdrawnInitiativePage.clickResubmitSaveButton();
    //    waitForSeconds(2);
        withdrawnInitiativePage.clickResubmitOkButton();
    //    waitForSeconds(10); // Wait for toast notification to disappear and popup to close
        
        // Step 13: Popup should close (handled by wait above)
    //    waitForSeconds(3);
        
        // Step 15: Click on Initiatives page
        initiativePage.navigateToInitiative();
    //    waitForSeconds(3);
        
        // Step 16-20: Search the code and verify if present (isInitiativeCodeInInbox handles search internally)
        if (!initiativePage.isInitiativeCodeInInbox(initiativeCode)) {
            throw new Exception("TC_007 FAILED - Initiative not found in approvers inbox");
        }
    }

    @Test(priority = 8, enabled = true, groups = {"Withdrawn"})
    @Description("TC_008 - Verify Pagination")
    @Story("Withdrawn Initiatives Pagination")
    @Severity(SeverityLevel.NORMAL)
    public void TC_008_verifyPagination() throws Exception {
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        
        // Step 1: Click on Withdrawn Initiatives page
        withdrawnInitiativePage.navigateToWithdrawnInitiativesPage();
    //    waitForSeconds(2);
        
        // Step 2: Click on pagination
        withdrawnInitiativePage.clickPaginationNextButton();
        
        // Step 3: Verify pagination
        if (!withdrawnInitiativePage.verifyPagination()) {
            throw new Exception("TC_008 FAILED - Pagination verification failed");
        }
        
        
    }

    /**
     * Find Excel file path (cached to avoid multiple searches) // Gayatri.k
     * @author Gayatri.k
     */
    private String findExcelFilePath() throws Exception {
        if (cachedExcelPath != null && new File(cachedExcelPath).exists()) {
            return cachedExcelPath;
        }
        
        String[] possibleNames = {"TestdataIni.xlsx", "Testdataini.xlsx", "TEstdata.xlsx"};
        String[] possibleDirs = {
                "",
                "src/test/resources/",
                System.getProperty("user.dir") + "/",
                System.getProperty("user.dir") + "/src/test/resources/"
        };
        
        StringBuilder triedPaths = new StringBuilder();
        for (String dir : possibleDirs) {
            for (String name : possibleNames) {
                String path = dir + name;
                File file = new File(path);
                triedPaths.append("  - ").append(path);
                if (file.exists()) {
                    try {
                        ExcelReader testReader = new ExcelReader(path, "WithdrawnInitiative");
                //        testReader.close();
                        cachedExcelPath = path;
                        System.out.println("✅ Found Excel file: " + path);
                        return cachedExcelPath;
                    } catch (Exception e) {
                        triedPaths.append(" (exists but cannot read: ").append(e.getMessage()).append(")");
                    }
                }
                triedPaths.append("\n");
            }
        }
        
        throw new Exception("Excel file not found. Tried paths:\n" + triedPaths.toString() + 
                "\nPlease ensure the Excel file (TestdataIni.xlsx, Testdataini.xlsx, or TEstdata.xlsx) exists in project root or src/test/resources/");
    }

    /**
     * Read Initiative Code from Excel
     * @author Gayatri.k
     */
    private String getInitiativeCodeFromExcel(String testCaseId) throws Exception {
        String excelPath = findExcelFilePath();
        ExcelReader reader = new ExcelReader(excelPath, "WithdrawnInitiative");

        try {
            int dataRowCount = reader.getRowCount();
            int colCount = reader.getColumnCount();
            int initiativeCodeColIndex = -1;

            for (int col = 0; col < colCount; col++) {
                String header = reader.getHeader(col);
                if (header != null && header.trim().replace(" ", "").toLowerCase().equals("initiativecode")) {
                    initiativeCodeColIndex = col;
                    break;
                }
            }

            if (initiativeCodeColIndex == -1) {
                throw new Exception("Could not find 'InitiativeCode' column");
            }

            for (int row = 1; row <= dataRowCount; row++) {
                String excelTCID = reader.getData(row, 0);
                if (excelTCID != null && excelTCID.trim().equalsIgnoreCase(testCaseId)) {
                    String initiativeCode = reader.getData(row, initiativeCodeColIndex);
                    if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
                        throw new Exception("Initiative Code is empty for " + testCaseId);
                    }
                    return initiativeCode.trim();
                }
            }

            throw new Exception("Test case ID '" + testCaseId + "' not found in Excel");
        } finally {
            if (reader != null) {
                try {
          //          reader.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    /**
     * Read Search Data from Excel
     * @author Gayatri.k
     */
    private String[] getSearchDataFromExcel(String testCaseId) throws Exception {
        String excelPath = findExcelFilePath();
        ExcelReader reader = new ExcelReader(excelPath, "WithdrawnInitiative");

        try {
            int dataRowCount = reader.getRowCount();
            int colCount = reader.getColumnCount();
            int natureOfInitiativeColIndex = -1;
            int businessGroupColIndex = -1;
            int organizationUnitColIndex = -1;
            int initiativeCodeColIndex = -1;
            int initiativeTitleColIndex = -1;

            for (int col = 0; col < colCount; col++) {
                String header = reader.getHeader(col);
                if (header == null || header.trim().isEmpty()) continue;
                String normalized = header.trim().replace(" ", "").toLowerCase();
                if (normalized.equals("natureofinitiative")) natureOfInitiativeColIndex = col;
                else if (normalized.equals("businessgroup")) businessGroupColIndex = col;
                else if (normalized.equals("organizationunit")) organizationUnitColIndex = col;
                else if (normalized.equals("initiativecode")) initiativeCodeColIndex = col;
                else if (normalized.equals("initiativetitle")) initiativeTitleColIndex = col;
            }

            if (initiativeCodeColIndex == -1) {
                throw new Exception("Could not find 'InitiativeCode' column");
            }

            for (int row = 1; row <= dataRowCount; row++) {
                String excelTCID = reader.getData(row, 0);
                if (excelTCID != null && excelTCID.trim().equalsIgnoreCase(testCaseId)) {
                    String natureOfInitiative = (natureOfInitiativeColIndex >= 0) ? 
                            (reader.getData(row, natureOfInitiativeColIndex) != null ? reader.getData(row, natureOfInitiativeColIndex).trim() : "") : "";
                    String businessGroup = (businessGroupColIndex >= 0) ? 
                            (reader.getData(row, businessGroupColIndex) != null ? reader.getData(row, businessGroupColIndex).trim() : "") : "";
                    String organizationUnit = (organizationUnitColIndex >= 0) ? 
                            (reader.getData(row, organizationUnitColIndex) != null ? reader.getData(row, organizationUnitColIndex).trim() : "") : "";
                    String initiativeCode = reader.getData(row, initiativeCodeColIndex);
                    String initiativeTitle = (initiativeTitleColIndex >= 0) ? 
                            (reader.getData(row, initiativeTitleColIndex) != null ? reader.getData(row, initiativeTitleColIndex).trim() : "") : "";

                    if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
                        throw new Exception("Initiative Code is empty for " + testCaseId);
                    }

                    return new String[]{natureOfInitiative, businessGroup, organizationUnit, initiativeCode.trim(), initiativeTitle};
                }
            }

            throw new Exception("Test case ID '" + testCaseId + "' not found in Excel");
        } finally {
            if (reader != null) {
                try {
            //        reader.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    /**
     * Read Comment from Excel
     * @author Gayatri.k
     */
    private String getCommentFromExcel(String testCaseId) throws Exception {
        String excelPath = findExcelFilePath();
        ExcelReader reader = new ExcelReader(excelPath, "WithdrawnInitiative");

        try {
            int dataRowCount = reader.getRowCount();
            int colCount = reader.getColumnCount();
            int commentColIndex = -1;

            for (int col = 0; col < colCount; col++) {
                String header = reader.getHeader(col);
                if (header == null || header.trim().isEmpty()) continue;
                String normalized = header.trim().replace(" ", "").toLowerCase();
                if (normalized.equals("comment") || normalized.equals("resubmitcomment")) {
                    commentColIndex = col;
                    break;
                }
            }

            if (commentColIndex == -1) {
                throw new Exception("Could not find 'Comment' column");
            }

            for (int row = 1; row <= dataRowCount; row++) {
                String excelTCID = reader.getData(row, 0);
                if (excelTCID != null && excelTCID.trim().equalsIgnoreCase(testCaseId)) {
                    String comment = reader.getData(row, commentColIndex);
                    if (comment == null || comment.trim().isEmpty()) {
                        throw new Exception("Comment is empty for " + testCaseId);
                    }
                    return comment.trim();
                }
            }

            throw new Exception("Test case ID '" + testCaseId + "' not found in Excel");
        } finally {
            if (reader != null) {
                try {
                //    reader.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }
}
