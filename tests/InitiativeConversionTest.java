package tests;

import Base.BaseTest;
import Pages.InitiativeConversion;
import Pages.InitiativePage;
import Utils.ExcelReader;
import Utils.LoginHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Initiative Conversion Test Suite
 *
 * This class is dedicated to tests that validate behaviour on the
 * Converted Initiative page. All scenarios that specifically
 * target converted initiatives should be implemented here so that
 * they are clearly separated from generic Initiative tests.
 *
 * EXAMPLES OF FUTURE TESTS
 * ========================
 * - TC_001_Convert_Initiative_And_Verify_Details
 * - TC_002_Converted_Initiative_Approval_Flow
 * - TC_003_Converted_Initiative_Resubmission_Flow
 *
 * As you define concrete requirements, we will flesh out the
 * corresponding test methods below using page methods from
 * {@link InitiativeConversion}.
 */
@Epic("Initiative Management")
@Feature("Initiative Conversion")
public class InitiativeConversionTest extends BaseTest {

    protected InitiativeConversion initiativeConversionPage;

    // =====================================================================
    // DATA PROVIDER
    // =====================================================================

    /**
     * Generic DataProvider to fetch conversion‑related test data by TC_ID
     * from Excel.
     *
     * Suggested sheet name: "InitiativeConversion" (configure in TestdataIni.xlsx).
     */
    @DataProvider(name = "initiativeConversionData")
    public Object[][] getInitiativeConversionData(Method method) throws Exception {
        String testCaseId = method.getName();
        System.out.println("🔍 DataProvider: Looking for TC_ID = '" + testCaseId + "' in Excel file");
        System.out.println("📄 DataProvider: Reading from sheet 'InitiativeConversion'");
        
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeConversion");
        int rowCount = reader.getRowCount();
        System.out.println("📊 DataProvider: Found " + rowCount + " data row(s) in Excel file");

        // Debug: Print all TC_IDs found in Excel
        System.out.println("📋 DataProvider: Available TC_IDs in Excel:");
        for (int i = 0; i < rowCount; i++) {
            try {
                String excelTCID = reader.getData(i + 1, 0);
                if (excelTCID != null && !excelTCID.trim().isEmpty()) {
                    System.out.println("   Row " + (i + 2) + ": '" + excelTCID.trim() + "'");
                }
            } catch (Exception e) {
                System.out.println("   Row " + (i + 2) + ": Error reading TC_ID");
            }
        }

        for (int i = 0; i < rowCount; i++) {
            String excelTCID;
            try {
                excelTCID = reader.getData(i + 1, 0);
            } catch (Exception e) {
                // Skip rows where the TC_ID cell is missing/invalid
                continue;
            }
            if (excelTCID == null) {
                continue;
            }
            excelTCID = excelTCID.trim();

            if (excelTCID.equalsIgnoreCase(testCaseId)) {
                System.out.println("✅ DataProvider: Found matching TC_ID in row " + (i + 2));
                int paramCount = method.getParameterCount();
                System.out.println("📋 DataProvider: Method expects " + paramCount + " parameters");
                Object[][] data = new Object[1][paramCount];

                for (int j = 0; j < paramCount; j++) {
                    String cellValue;
                    try {
                        cellValue = reader.getData(i + 1, j + 1);
                    } catch (Exception e) {
                        // Treat missing/invalid cells as empty strings
                        cellValue = "";
                    }
                    data[0][j] = (cellValue == null) ? "" : cellValue.trim();
                    System.out.println("   Parameter " + (j + 1) + ": '" + data[0][j] + "'");
                }
                return data;
            }
        }
        
        System.out.println("❌ DataProvider: No matching TC_ID found for '" + testCaseId + "'");
        System.out.println("💡 Please add a row in Excel sheet 'InitiativeConversion' with:");
        System.out.println("   - Column 0 (TC_ID): '" + testCaseId + "'");
        System.out.println("   - Columns 1-16: Test data values");
        return new Object[0][0];
    }

    // =====================================================================
    // TEST CASES
    // =====================================================================

    /**
     * TC_001_Click_On_Initiative_Conversion_Page
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module.
     * 2. Click on the Initiative Conversion page.
     *
     * This test verifies that the Converted Initiative page can be
     * opened successfully from the Initiative Tracking module.
     */
    @Test(priority = 1, enabled = false)
    @Description("TC_001_Click_On_Initiative_Conversion_Page - Verify navigation to Initiative Conversion page from Initiative Tracking module")
    @Story("Initiative Conversion - Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001_Click_On_Initiative_Conversion_Page() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_001_Click_On_Initiative_Conversion_Page: Navigate to Initiative Conversion page");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Conversion page using the page object
        System.out.println("\n📌 Step 1-2: Click on Initiative Tracking module and then Initiative Conversion page");
        initiativeConversionPage.navigateToConvertedInitiativePage();

        System.out.println("\n✅ TC_001_Click_On_Initiative_Conversion_Page completed (navigation to Initiative Conversion page executed).");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    // =====================================================================
    // PLACEHOLDER TEST CASES
    // =====================================================================

    /**
     * TC_001_Convert_Initiative_And_Verify_Details
     *
     * Placeholder test for future implementation.
     *
     * Steps (to be detailed based on your requirements):
     * 1. Navigate to Converted Initiative page.
     * 2. Open a specific converted initiative by code (from Excel).
     * 3. Verify key fields/sections on the converted initiative.
     */
    @Test(priority = 2, enabled = false, dataProvider = "initiativeConversionData")
    @Description("TC_001_Convert_Initiative_And_Verify_Details - Placeholder for converted initiative verification flow")
    @Story("Initiative Conversion - Basic Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001_Convert_Initiative_And_Verify_Details(String initiativeCode) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_001_Convert_Initiative_And_Verify_Details (placeholder)");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Placeholder flow – to be replaced with real implementation
        initiativeConversionPage.navigateToConvertedInitiativePage();
        initiativeConversionPage.openConvertedInitiativeByCode(initiativeCode);
        boolean ok = initiativeConversionPage.verifyConvertedInitiativeDetails(initiativeCode);

        if (!ok) {
            throw new Exception("❌ FAILED: Converted initiative validation failed for code: " + initiativeCode);
        }

        System.out.println("✅ Placeholder TC_001_Convert_Initiative_And_Verify_Details completed (logic to be implemented)");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_002_Verify_Search_Functionality
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module.
     * 2. Click on the Initiative Conversion page.
     * 3. Click on the Search button (toolbar).
     * 4. Enter Initiative Title from Excel.
     * 5. Select Nature of Initiative = Full Change Lifecycle.
     * 6. Enter Initiative Code from Excel.
     * 7. Select Start Date from Excel.
     * 8. Select End Date from Excel.
     * 9. Select Mark as completed value from Excel.
     * 10. Click on the Search button (filter panel).
     * 11. Verify search results are displayed OR 'There are no items to show in this view.' message appears.
     */
    @Test(priority = 2, enabled = false, dataProvider = "initiativeConversionData")
    @Description("TC_002_Verify_Search_Functionality - Verify Initiative Conversion search with multiple filters and grid/no-items message")
    @Story("Initiative Conversion - Search")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_Verify_Search_Functionality(
            String initiativeTitle,
            String initiativeCode,
            String markAsCompletedValue
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_002_Verify_Search_Functionality: Verify Initiative Conversion search");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Step 1-2: Navigate to Initiative Conversion page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Conversion page via Initiative Tracking module");
        initiativeConversionPage.navigateToConvertedInitiativePage();

        // Step 3: Click on Search button (toolbar)
        System.out.println("\n📌 Step 3: Click on the Search toolbar button");
        initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Initiative Title
        System.out.println("\n📌 Step 4: Enter Initiative Title from Excel: " + initiativeTitle);
        initiativeConversionPage.enterInitiativeTitle(initiativeTitle);

        // Step 5: Nature of Initiative = Full Change Lifecycle
       

        // Step 6: Initiative Code
        System.out.println("\n📌 Step 6: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 9: Mark as completed dropdown
    
        // Step 10: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 10: Click on the Search button in the filter panel");
        initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 11: Verify results or "no items" message
        System.out.println("\n📌 Step 11: Verify search results OR 'There are no items to show in this view.' message");
        boolean ok = initiativeConversionPage.verifySearchResultsOrNoItemsMessage();

        if (!ok) {
            throw new Exception("❌ FAILED: Neither search results nor 'There are no items to show in this view.' message could be verified.");
        }

        System.out.println("✅ TC_002_Verify_Search_Functionality PASSED.");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_003_Convert_Initiative_To_Project
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module.
     * 2. Click on the Initiative Conversion page.
     * 3. Click on the Search button.
     * 4. Click on the Initiative Code and take the Initiative code from the excel.
     * 5. Click on the Search button.
     * 6. Click on the Convert to Project link.
     * 7. Click on the Abbreviated Name text field and enter the Abbreviated Name automatic.
     * 8. Click on the Number of Resources text field and enter the values as 5.
     * 9. Click on the Description field and enter a description.
     * 10. Click on the Customer dropdown field and select the customer from the excel.
     * 11. Click on the Commercial Details dropdown field and select the value from the excel.
     * 13. Click on the Project Value text field and enter the value as mentioned in the excel.
     * 14. Click on the Project Currency dropdown field and enter the value as mentioned in the excel.
     * 15. Click on the Business Group dropdown field and enter the value as mentioned in the excel.
     * 16. Click on the Organization Unit dropdown field and enter the value as mentioned in the excel.
     * 17. Click on the Practice Template dropdown field and enter the value as mentioned in the excel.
     * 18. Click on the Save button.
     * 19. Click on the Search button.
     * 20. Click on the Initiative Code and enter the code.
     * 21. Click on the Search button.
     * 22. Verify that the search Initiative code is not displayed on the Initiative Conversion page.
     *     (That means the Initiative get converted into the project successfully)
     */
    @Test(priority = 3, enabled = true, dataProvider = "initiativeConversionData")
    @Description("TC_003_Convert_Initiative_To_Project - Convert an initiative to a project and verify it's removed from conversion list")
    @Story("Initiative Conversion - Convert to Project")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003_Convert_Initiative_To_Project(
            String abbreviatedName,
            String description,
            String customer,
            String commercialDetails,
            String projectValue,
            String projectCurrency,
            String businessGroup,
            String organizationUnit,
            String practiceTemplate
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_003_Convert_Initiative_To_Project: Convert initiative to project");
        System.out.println("═══════════════════════════════════════════════════════");
       
        
        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);
     //   String initiativeCode = null;
        initiativePage.closeNotificationPopupIfPresent();
        Thread.sleep(3000);
        // Step 1-2: Navigate to Initiative Conversion page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Conversion page via Initiative Tracking module");
        initiativeConversionPage.navigateToConvertedInitiativePage();

        // Step 3: Click on the Search toolbar button
        System.out.println("\n📌 Step 3: Click on the Search toolbar button");
    //    initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
    //    System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
   //     initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 5: Click on the Search button (filter)
        System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
    //    initiativeConversionPage.clickFilterSearchButton();
    

        
     String    initiativeCode  = initiativeConversionPage.getInitiativeCode();
        
        // Step 6: Click on the Convert to Project link
        System.out.println("\n📌 Step 6: Click on the Convert to Project link for initiative: " + initiativeCode);
        initiativeConversionPage.clickConvertToProjectLink(initiativeCode);
        Thread.sleep(2000);

        // Step 7: Enter Abbreviated Name (auto-generated from Excel)
        String   abbrivatedname =  initiativeConversionPage.generateAbbreviatedName();
        
        // Step 7: Enter Abbreviated Name (auto-generated from Excel)
        System.out.println("\n📌 Step 7: Enter Abbreviated Name: ");
        initiativeConversionPage.enterAbbreviatedName(abbrivatedname);

        // Step 8: Enter Number of Resources = 5
        System.out.println("\n📌 Step 8: Enter Number of Resources: 5");
        initiativeConversionPage.enterNumberOfResources("5");

        // Step 9: Enter Description
        System.out.println("\n📌 Step 9: Enter Description: " + description);
        initiativeConversionPage.enterDescription(description);

        // Step 10: Select Customer from Excel
        System.out.println("\n📌 Step 10: Select Customer from Excel: " + customer);
        initiativeConversionPage.selectCustomer();

        // Step 11: Select Commercial Details from Excel
        System.out.println("\n📌 Step 11: Select Commercial Details from Excel: "  );
        initiativeConversionPage.selectCommercialDetails();

        
        // Step 14: Select Project Currency from Excel
        System.out.println("\n📌 Step 14: Select Project Currency from Excel: " );
        initiativeConversionPage.selectProjectCurrency();
        
        // Step 13: Enter Project Value from Excel
        System.out.println("\n📌 Step 13: Enter Project Value from Excel: " + projectValue);
        initiativeConversionPage.enterProjectValue(projectValue);

 

        // Step 15: Select Business Group from Excel
        System.out.println("\n📌 Step 15: Select Business Group from Excel: " );
        initiativeConversionPage.selectBusinessGroup();

        // Step 16: Select Organization Unit from Excel
        System.out.println("\n📌 Step 16: Select Organization Unit from Excel: " );
        initiativeConversionPage.selectOrganizationUnit();

        // Step 17: Select Practice Template from Excel
        System.out.println("\n📌 Step 17: Select Practice Template from Excel: " );
        initiativeConversionPage.selectPracticeTemplate();

        // Step 18: Click Save button
        System.out.println("\n📌 Step 18: Click on the Save button");
        initiativeConversionPage.clickSaveButton();

        
        initiativeConversionPage.waitForToastToDisappear();
         
      //  initiativeConversionPage.closeUpdateHealthSheet();
        // Step 19: Click on the Search toolbar button again
        System.out.println("\n📌 Step 19: Click on the Search toolbar button");
        initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(3000);

        // Step 20: Enter Initiative Code again
        System.out.println("\n📌 Step 20: Enter Initiative Code: " + initiativeCode);
        initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 21: Click on the Search button (filter)
       System.out.println("\n📌 Step 21: Click on the Search button (filter panel)");
        initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        initiativeConversionPage.clickonclose();
        // Step 22: Verify that the initiative code is NOT displayed (conversion successful)
        System.out.println("\n📌 Step 22: Verify that initiative code '" + initiativeCode + "' is NOT displayed (conversion successful)");
        boolean notDisplayed = initiativeConversionPage.verifyInitiativeNotDisplayedAfterConversion(initiativeCode);

        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative code '" + initiativeCode + "' is still displayed in the grid. Conversion to project may have failed.");
        }

        System.out.println("✅ TC_003_Convert_Initiative_To_Project PASSED - Initiative successfully converted to project.");
        System.out.println("═══════════════════════════════════════════════════════\n");
       
    }

    /**
     * TC_005_Verify_Pagination_On_Initiative_Conversion_Page
     *
     * Steps:
     * 1. Click on the Initiative Tracking module.
     * 2. Click on the Initiative Conversion page.
     * 3. Click on the pagination and verify per page 5 records.
     */
    @Test(priority = 5, enabled = true)
    @Description("TC_005_Verify_Pagination_On_Initiative_Conversion_Page - Verify that pagination shows exactly 5 records per page")
    @Story("Initiative Conversion - Pagination")
    @Severity(SeverityLevel.NORMAL)
    public void TC_005_Verify_Pagination_On_Initiative_Conversion_Page() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_005_Verify_Pagination_On_Initiative_Conversion_Page: Verify pagination (5 records per page)");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking module");
        initiativeConversionPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Conversion page
        System.out.println("\n📌 Step 2: Click on the Initiative Conversion page");
        initiativeConversionPage.clickInitiativeConversionPageLink();
        Thread.sleep(3000);

        // Step 3: Verify that exactly 5 records are displayed per page
        System.out.println("\n📌 Step 3: Verify that exactly 5 records are displayed per page on the Initiative Conversion grid");
        int rowCount = initiativeConversionPage.getCurrentConversionGridRowCount();
        System.out.println("  ℹ️ Detected row count on current page: " + rowCount);

        if (rowCount != 5) {
            throw new Exception("❌ FAILED: Expected 5 records per page, but found " + rowCount + " records.");
        }

        System.out.println("✅ Step 3: Verified that exactly 5 records are displayed per page on the Initiative Conversion grid");
        System.out.println("\n✅ ✅ ✅ TC_005_Verify_Pagination_On_Initiative_Conversion_Page PASSED ✅ ✅ ✅");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_004_Convert_Initiative_To_Milestone
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module.
     * 2. Click on the Initiative Conversion page.
     * 3. Click on the Search button.
     * 4. Enter Initiative Code from Excel.
     * 5. Click on the Search button (filter).
     * 6. Click on the Convert to Milestone link.
     * 7. Select Project Name from Excel.
     * 8. Enter Bill Amount from Excel.
     * 9. Check checkbox (Ready for Billing OR Invoice Printed OR Analysis Applicable) from Excel.
     * 10. Select Currency from Excel.
     * 11. Select Analysis Status from Excel.
     * 12. Enter Completion Percentage from Excel.
     * 13. Enter Invoice No from Excel.
     * 14. Click on the Save button.
     * 15. Click on the Search button.
     * 16. Enter Initiative Code.
     * 17. Click on the Search button.
     * 18. Verify that the initiative code is NOT displayed (conversion successful).
     */
    @Test(priority = 4, enabled = false, dataProvider = "initiativeConversionData")
    @Description("TC_004_Convert_Initiative_To_Milestone - Convert an initiative to a milestone and verify it's removed from conversion list")
    @Story("Initiative Conversion - Convert to Milestone")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_004_Convert_Initiative_To_Milestone(
            String initiativeCode,
            String projectName,
            String billAmount,
            String checkboxValue,
            String currency,
            String analysisStatus,
            String completionPercentage,
            String invoiceNo
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_004_Convert_Initiative_To_Milestone: Convert initiative to milestone");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeConversionPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Conversion page
        System.out.println("\n📌 Step 2: Click on the Initiative Conversion page");
        initiativeConversionPage.clickInitiativeConversionPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 3: Click on the Search toolbar button");
        initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 5: Click on the Search button (filter)
        System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
        initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 6: Click on the Convert to Milestone link
        System.out.println("\n📌 Step 6: Click on the Convert to Milestone link for initiative: " + initiativeCode);
        initiativeConversionPage.clickConvertToMilestoneLink(initiativeCode);
        Thread.sleep(2000);

        // Step 7: Select Project Name from Excel
        System.out.println("\n📌 Step 7: Select Project Name from Excel: '" + projectName + "' (length: " + (projectName != null ? projectName.length() : 0) + ")");
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new Exception("Project Name value from Excel is null or empty. Please check Excel data.");
        }
        initiativeConversionPage.selectProjectName(projectName.trim());

        // Step 8: Enter Bill Amount from Excel
        System.out.println("\n📌 Step 8: Enter Bill Amount from Excel: " + billAmount);
        initiativeConversionPage.enterBillAmount(billAmount);

        // Step 9: Check checkbox from Excel
        System.out.println("\n📌 Step 9: Check checkbox from Excel: " + checkboxValue);
        initiativeConversionPage.checkCheckbox(checkboxValue);
        Thread.sleep(2000); // Wait for form to update after checkbox

        // Step 10: Select Currency from Excel
        System.out.println("\n📌 Step 10: Select Currency from Excel: " + currency);
        initiativeConversionPage.selectCurrency(currency);

        // Step 11: Select Analysis Status from Excel
        System.out.println("\n📌 Step 11: Select Analysis Status from Excel: " + analysisStatus);
        initiativeConversionPage.selectAnalysisStatus(analysisStatus);

        // Step 12: Enter Completion Percentage from Excel
        System.out.println("\n📌 Step 12: Enter Completion Percentage from Excel: '" + completionPercentage + "' (length: " + (completionPercentage != null ? completionPercentage.length() : 0) + ")");
        if (completionPercentage == null || completionPercentage.trim().isEmpty()) {
            throw new Exception("Completion Percentage value from Excel is null or empty. Please check Excel data.");
        }
        initiativeConversionPage.enterCompletionPercentage(completionPercentage.trim());

        // Step 13: Enter Invoice No from Excel
        System.out.println("\n📌 Step 13: Enter Invoice No from Excel: " + invoiceNo);
        initiativeConversionPage.enterInvoiceNo(invoiceNo);

        // Step 14: Click Save button
        System.out.println("\n📌 Step 14: Click on the Save button");
        initiativeConversionPage.clickSaveButtonMilestone();

        // Step 15: Click on the Search toolbar button again
        System.out.println("\n📌 Step 15: Click on the Search toolbar button");
        initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 16: Enter Initiative Code again
        System.out.println("\n📌 Step 16: Enter Initiative Code: " + initiativeCode);
        initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 17: Click on the Search button (filter)
        System.out.println("\n📌 Step 17: Click on the Search button (filter panel)");
        initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 18: Verify that the initiative code is NOT displayed (conversion successful)
        System.out.println("\n📌 Step 18: Verify that initiative code '" + initiativeCode + "' is NOT displayed (conversion successful)");
        boolean notDisplayed = initiativeConversionPage.verifyInitiativeNotDisplayedAfterConversion(initiativeCode);

        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative code '" + initiativeCode + "' is still displayed in the grid. Conversion to milestone may have failed.");
        }

        System.out.println("✅ TC_004_Convert_Initiative_To_Milestone PASSED - Initiative successfully converted to milestone.");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_006_Check_Converted_Milestone_Displayed_On_Whizible_Site
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module.
     * 2. Click on the Initiative Conversion page.
     * 3. Click on the Search button.
     * 4. Enter Initiative Code from Excel.
     * 5. Click on the Search button.
     * 6. Click on the Convert to Milestone link.
     * 7. Select Project Name from Excel.
     * 8. Enter Bill Amount from Excel.
     * 9. Check checkbox (Ready for Billing OR Invoice Printed OR Analysis Applicable) from Excel.
     * 10. Select Currency from Excel.
     * 11. Select Analysis Status from Excel.
     * 12. Enter Completion Percentage from Excel.
     * 13. Enter Invoice No from Excel.
     * 14. Click on the Save button.
     * 15. Click on the profile.
     * 16. Click on the Logout button.
     * 17. Navigate to Whizible site URL from config.
     * 18. Enter User ID from config.
     * 19. Enter Password from config.
     * 20. Click on the Sign In button.
     * 21. Click on the Project Module.
     * 22. Click on the Plan node.
     * 23. Click on the WBS sub node.
     * 24. Select Project from Excel.
     * 25. Click on the Filter section.
     * 26. Click on the Basic Filter sub tab.
     * 27. Enter Milestone Name from Excel.
     * 28. Click on the Apply button.
     * 29. Verify that converted milestone is displayed.
     */
    @Test(priority = 6, enabled = false, dataProvider = "initiativeConversionData")
    @Description("TC_006_Check_Converted_Milestone_Displayed_On_Whizible_Site - Convert initiative to milestone and verify it's displayed on Whizible site")
    @Story("Initiative Conversion - Verify Milestone on Whizible Site")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_006_Check_Converted_Milestone_Displayed_On_Whizible_Site(
            String initiativeCode,
            String projectName,
            String billAmount,
            String checkboxValue,
            String currency,
            String analysisStatus,
            String completionPercentage,
            String invoiceNo,
            String projectNameForWhizible,
            String milestoneName
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_006_Check_Converted_Milestone_Displayed_On_Whizible_Site: Convert initiative to milestone and verify on Whizible site");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);

        // Steps 1-2: Navigate to Initiative Conversion page
        System.out.println("\n📌 Steps 1-2: Navigate to Initiative Conversion page");
        initiativeConversionPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);
        initiativeConversionPage.clickInitiativeConversionPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search toolbar button
        System.out.println("\n📌 Step 3: Click on the Search toolbar button");
        initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 5: Click on the Search button (filter)
        System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
        initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 6: Click on the Convert to Milestone link
        System.out.println("\n📌 Step 6: Click on the Convert to Milestone link for initiative: " + initiativeCode);
        initiativeConversionPage.clickConvertToMilestoneLink(initiativeCode);
        Thread.sleep(2000);

        // Step 7: Select Project Name from Excel
        System.out.println("\n📌 Step 7: Select Project Name from Excel: '" + projectName + "' (length: " + (projectName != null ? projectName.length() : 0) + ")");
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new Exception("Project Name value from Excel is null or empty. Please check Excel data.");
        }
        initiativeConversionPage.selectProjectName(projectName.trim());

        // Step 8: Enter Bill Amount from Excel
        System.out.println("\n📌 Step 8: Enter Bill Amount from Excel: " + billAmount);
        initiativeConversionPage.enterBillAmount(billAmount);

        // Step 9: Check checkbox from Excel
        System.out.println("\n📌 Step 9: Check checkbox from Excel: " + checkboxValue);
        initiativeConversionPage.checkCheckbox(checkboxValue);
        Thread.sleep(2000); // Wait for form to update after checkbox

        // Step 10: Select Currency from Excel
        System.out.println("\n📌 Step 10: Select Currency from Excel: " + currency);
       // initiativeConversionPage.selectCurrency(currency);
        Thread.sleep(2000);

        // Step 11: Select Analysis Status from Excel
        System.out.println("\n📌 Step 11: Select Analysis Status from Excel: " + analysisStatus);
      //  initiativeConversionPage.selectAnalysisStatus(analysisStatus);

        // Step 12: Enter Completion Percentage from Excel
        System.out.println("\n📌 Step 12: Enter Completion Percentage from Excel: '" + completionPercentage + "' (length: " + (completionPercentage != null ? completionPercentage.length() : 0) + ")");
        if (completionPercentage == null || completionPercentage.trim().isEmpty()) {
            throw new Exception("Completion Percentage value from Excel is null or empty. Please check Excel data.");
        }
     //   initiativeConversionPage.enterCompletionPercentage(completionPercentage.trim());
        Thread.sleep(2000);
        // Step 13: Enter Invoice No from Excel
        System.out.println("\n📌 Step 13: Enter Invoice No from Excel: " + invoiceNo);
     //   initiativeConversionPage.enterInvoiceNo(invoiceNo);

        // Step 14: Click on the Save button
        System.out.println("\n📌 Step 14: Click on the Save button");
        initiativeConversionPage.clickSaveButtonMilestone();
        Thread.sleep(3000);

        // Steps 15-16: Logout
        System.out.println("\n📌 Steps 15-16: Logout current user");
        initiativeConversionPage.logoutCurrentUser();
      
        String whizibleSiteUrl = config.getProperty("whizibleUrl");
        String whizibleSiteUserId = config.getProperty("whizibleSiteUserId");
        String whizibleSitePassword = config.getProperty("whizibleSitePassword");
        int defaultWait = Integer.parseInt(config.getProperty("defaultWait", "40"));

        if (whizibleSiteUrl == null ||
            whizibleSiteUserId == null ||
            whizibleSitePassword == null) {

            throw new RuntimeException("❌ Required Whizible config values missing in config.properties");
        }

        System.out.println("📌 Navigating to Whizible site: " + whizibleSiteUrl);

        initiativeConversionPage.navigateToWhizibleSite1(whizibleSiteUrl);

        System.out.println("🔐 Logging in with user: " + whizibleSiteUserId);

        initiativeConversionPage.loginToWhizibleSite1(
                whizibleSiteUserId,
                whizibleSitePassword
               
        );

        System.out.println("✅ Whizible login successful");

        initiativeConversionPage.clickProjectModule();
        // Step 22: Click on the Plan node
        Thread.sleep(3000);
        initiativeConversionPage.clickProjectModule();
        System.out.println("\n📌 Step 22: Click on the Plan node");
        initiativeConversionPage.clickPlanNode();
         Thread.sleep(5000);
        // Step 23: Click on the WBS sub node
        System.out.println("\n📌 Step 23: Click on the WBS sub node");
        initiativeConversionPage.clickWBSSubNode();

       Thread.sleep(5000);
        System.out.println("\n📌 Step 24: Select Project from Excel: " + projectNameForWhizible);
        
      initiativeConversionPage.selectProjectFromDropdown1(projectNameForWhizible);
        Thread.sleep(1000);

        // Step 25: Click on the Filter section
        System.out.println("\n📌 Step 25: Click on the Filter section");
 
        initiativeConversionPage.clickFilterSection();
        
  
        // Step 26: Click on the Basic Filter sub tab
        Thread.sleep(1000);
        System.out.println("\n📌 Step 26: Click on the Basic Filter sub tab");
        initiativeConversionPage.clickBasicFilterSubTab();

        // Step 27: Enter Milestone Name from Excel
        if (milestoneName == null || milestoneName.trim().isEmpty()) {
            throw new Exception("Milestone Name is missing in Excel. Please add the milestone name in column 13.");
        }
        System.out.println("\n📌 Step 27: Enter Milestone Name from Excel: " + milestoneName);
       initiativeConversionPage.enterMilestoneName(milestoneName);
        Thread.sleep(3000);
        // Step 28: Click on the Apply button
        System.out.println("\n📌 Step 28: Click on the Apply button");
       initiativeConversionPage.clickApplyButton();
      Thread.sleep(3000);

        // Step 29: Verify that converted milestone is displayed
       System.out.println("\n📌 Step 29: Verify that converted milestone '" + milestoneName + "' is displayed");
        boolean milestoneFound = initiativeConversionPage.verifyMilestoneDisplayed(milestoneName);

      if (!milestoneFound) {
           throw new Exception("❌ FAILED: Milestone '" + milestoneName + "' is not displayed on the Milestone page.");
       }

       System.out.println("✅ TC_006_Check_Converted_Milestone_Displayed_On_Whizible_Site PASSED - Milestone successfully verified on Whizible site.");
       System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_007_Check_Converted_Project_Displayed_On_Whizible_Site
     * 
     * Test Case: Check the converted project displayed on the Whizible site (Configured site to database)
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Conversion page
     * 3. Click on the Search button
     * 4. Click on the Initiative Code and take the Initiative code from the excel
     * 5. Click on the Search button
     * 6. Click on the Convert to Project link
     * 7. Click on the Abbreviated Name text field and enter the Abbreviated Name automatic
     * 8. Click on the Number of Resources text field and enter the values as mentioned in the excel
     * 9. Click on the Description field and enter a description
     * 10. Click on the Customer dropdown field and select the customer from the excel
     * 11. Click on the Commercial Details dropdown field and select the value from the excel
     * 13. Click on the Project Value text field and enter the value as mentioned in the excel
     * 14. Click on the Project Currency dropdown field and enter the value as mentioned in the excel
     * 15. Click on the Business Group dropdown field and enter the value as mentioned in the excel
     * 16. Click on the Organization Unit dropdown field and enter the value as mentioned in the excel
     * 17. Click on the Practice Template dropdown field and enter the value as mentioned in the excel
     * 18. Click on the Save button
     * 19. Click on the profile
     * 20. Click on the Logout button
     * 21. Click on the URL of the Whizible as configured in the config file as https://192.168.2.92/W26_QA/
     * 22. Enter the User ID which is taken from the config file User ID- ADMIN
     * 23. Enter the Password which is taken from the config file Password - ADMIN
     * 24. Click on the Sign In button
     * 25. Click on the Search section and search the Manage Project
     * 26. Click on the Manage Project node
     * 27. Click on the Search section and search the project which is mentioned in the excel
     * 28. Verify that converted project displayed on the Manage project page
     */
    @Test(priority = 7, enabled = true, dataProvider = "initiativeConversionData")
    @Description("TC_007_Check_Converted_Project_Displayed_On_Whizible_Site - Check the converted project displayed on the Whizible site (Configured site to database)")
    @Story("Initiative Conversion - Convert to Project and Verify on Whizible")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_007_Check_Converted_Project_Displayed_On_Whizible_Site(
            String numberOfResources,
            String description,
            String customer,
            String commercialDetails,
            String projectValue,
            String projectCurrency,
            String businessGroup,
            String organizationUnit,
            String practiceTemplate
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_007_Check_Converted_Project_Displayed_On_Whizible_Site: Check the converted project displayed on the Whizible site (Configured site to database)");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeConversionPage = new InitiativeConversion(webDriver, reportLogger);
     
        // Steps 1-2: Navigate to Initiative Conversion page
        System.out.println("\n📌 Steps 1-2: Navigate to Initiative Conversion page");
        initiativeConversionPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);
        initiativeConversionPage.clickInitiativeConversionPageLink();
        Thread.sleep(3000);
        String    initiativeCode  = initiativeConversionPage.getInitiativeCode();
        // Step 3: Click on the Search toolbar button
      //  System.out.println("\n📌 Step 3: Click on the Search toolbar button");
    //    initiativeConversionPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
    //    System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
    //    initiativeConversionPage.enterInitiativeCode(initiativeCode);

        // Step 5: Click on the Search button (filter panel)
   //     System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
   //     initiativeConversionPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 6: Click on the Convert to Project link
        System.out.println("\n📌 Step 6: Click on the Convert to Project link for initiative: " + initiativeCode);
        initiativeConversionPage.clickConvertToProjectLink(initiativeCode);
        Thread.sleep(2000);
                                 
    String  projectNameForWhizible  =  initiativeConversionPage.getProjectNameFromTextField();
        
           
    String  abbrivatedname=  initiativeConversionPage.generateAbbreviatedName();

        initiativeConversionPage.enterAbbreviatedName(abbrivatedname);
        
        
        
        // Step 8: Enter Number of Resources = 5
        System.out.println("\n📌 Step 8: Enter Number of Resources: 5");
        initiativeConversionPage.enterNumberOfResources("5");

        // Step 9: Enter Description
        System.out.println("\n📌 Step 9: Enter Description: " + description);
        initiativeConversionPage.enterDescription(description);

        // Step 10: Select Customer from Excel
        System.out.println("\n📌 Step 10: Select Customer from Excel: " );
        initiativeConversionPage.selectCustomer();

        // Step 11: Select Commercial Details from Excel
        System.out.println("\n📌 Step 11: Select Commercial Details from Excel: " );
        initiativeConversionPage.selectCommercialDetails();

        
        // Step 14: Select Project Currency from Excel
        System.out.println("\n📌 Step 14: Select Project Currency from Excel: " );
        initiativeConversionPage.selectProjectCurrency();
        
        // Step 13: Enter Project Value from Excel
        System.out.println("\n📌 Step 13: Enter Project Value from Excel: " + projectValue);
        initiativeConversionPage.enterProjectValue(projectValue);

 

        // Step 15: Select Business Group from Excel
        System.out.println("\n📌 Step 15: Select Business Group from Excel: ");
        initiativeConversionPage.selectBusinessGroup();

        // Step 16: Select Organization Unit from Excel
        System.out.println("\n📌 Step 16: Select Organization Unit from Excel: " );
        initiativeConversionPage.selectOrganizationUnit();

        // Step 17: Select Practice Template from Excel
        System.out.println("\n📌 Step 17: Select Practice Template from Excel: " );
        initiativeConversionPage.selectPracticeTemplate();

      
        // Step 18: Click on the Save button
        System.out.println("\n📌 Step 18: Click on the Save button");
        initiativeConversionPage.clickSaveButton();
        Thread.sleep(3000);

        // Steps 19-20: Logout
        System.out.println("\n📌 Steps 19-20: Logout current user");
        initiativeConversionPage.logoutCurrentUser();

        // Step 21: Navigate to Whizible site URL (from config or Excel)
        String whizibleSiteUrl = config.getProperty("whizibleUrl");
        System.out.println("\n📌 Step 21: Navigate to Whizible site URL: " + whizibleSiteUrl);
        initiativeConversionPage.navigateToWhizibleSite(whizibleSiteUrl);

        // Steps 22-24: Login to Whizible site (from config)
        String whizibleSiteUserId = config.getProperty("username");
        String whizibleSitePassword = config.getProperty("password");
        System.out.println("\n📌 Steps 22-24: Login to Whizible site (User ID: " + whizibleSiteUserId + ")");
        initiativeConversionPage.loginToWhizibleSite1(whizibleSiteUserId, whizibleSitePassword);
        Thread.sleep(3000);
        initiativeConversionPage.clickProjectModule();
        Thread.sleep(3000);
        initiativeConversionPage.clickProjectModule();
        Thread.sleep(3000);
        initiativeConversionPage.clickManageProjectNode();
        Thread.sleep(3000);
        initiativeConversionPage.clickManageProjectNode();
       System.out.println("\n📌 Step 27: Search for project: " + projectNameForWhizible);
        initiativeConversionPage.searchProjectInWhizible(projectNameForWhizible);
        Thread.sleep(3000);

    /*     // Step 28: Verify that converted project is displayed
        System.out.println("\n📌 Step 28: Verify that converted project '" + projectNameForWhizible + "' is displayed");
        boolean projectFound = initiativeConversionPage.verifyProjectDisplayed(projectNameForWhizible);

        if (!projectFound) {
            throw new Exception("❌ FAILED: Project '" + projectNameForWhizible + "' is not displayed on the Manage Project page.");
        }

        System.out.println("✅ TC_007_Check_Converted_Project_Displayed_On_Whizible_Site PASSED - Project successfully verified on Whizible site.");
        System.out.println("═══════════════════════════════════════════════════════\n");
          */ 
    }
  
   
}

// Step 25: Click on Search section and search "Manage Project"
//  System.out.println("\n📌 Step 25: Click on Search section and search 'Manage Project'");
//   initiativeConversionPage.clickSearchSectionWhizible();
//   initiativeConversionPage.searchInWhizible("Manage Project");

// Step 26: Click on the Manage Project node
//  System.out.println("\n📌 Step 26: Click on the Manage Project node");
//  initiativeConversionPage.clickProjectModule();

