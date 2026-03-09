package tests;

import Base.BaseTest;
import Pages.InitiativeLinking;
import Utils.ExcelReader;
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
 * Initiative Linking Test Suite
 *
 * This class is dedicated to tests that validate behaviour on the
 * Initiative Linking page. All scenarios that specifically
 * target initiative linking should be implemented here so that
 * they are clearly separated from generic Initiative tests.
 *
 * EXAMPLES OF FUTURE TESTS
 * ========================
 * - TC_001_Link_Initiative_To_Another_Initiative
 * - TC_002_Verify_Linked_Initiative_Details
 * - TC_003_Unlink_Initiative
 *
 * As you define concrete requirements, we will flesh out the
 * corresponding test methods below using page methods from
 * {@link InitiativeLinking}.
 */
@Epic("Initiative Management")
@Feature("Initiative Linking")
public class InitiativeLinkingTest extends BaseTest {

    protected InitiativeLinking initiativeLinkingPage;

    // =====================================================================
    // DATA PROVIDER
    // =====================================================================

    /**
     * Generic DataProvider to fetch linking-related test data by TC_ID
     * from Excel.
     *
     * Suggested sheet name: "InitiativeLinking" (configure in TestdataIni.xlsx).
     */
    @DataProvider(name = "initiativeLinkingData")
    public Object[][] getInitiativeLinkingData(Method method) throws Exception {
        String testCaseId = method.getName();
        System.out.println("🔍 DataProvider: Looking for TC_ID = '" + testCaseId + "' in Excel file");
        System.out.println("📄 DataProvider: Reading from sheet 'InitiativeLinking'");
        
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeLinking");
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
        System.out.println("💡 Please add a row in Excel sheet 'InitiativeLinking' with:");
        System.out.println("   - Column 0 (TC_ID): '" + testCaseId + "'");
        System.out.println("   - Columns 1-N: Test data values");
        return new Object[0][0];
    }

    // =====================================================================
    // TEST CASES
    // =====================================================================

    /**
     * TC_001_Navigate_To_Initiative_Linking_Page
     *
     * Test Case: Navigate to the Initiative Linking page
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     */
    @Test(priority = 1, enabled = true)
    @Description("TC_001_Navigate_To_Initiative_Linking_Page - Navigate to the Initiative Linking page from Initiative Tracking module")
    @Story("Initiative Linking - Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001_Navigate_To_Initiative_Linking_Page() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_001_Navigate_To_Initiative_Linking_Page: Navigate to the Initiative Linking page");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(3000);

        System.out.println("\n✅ TC_001_Navigate_To_Initiative_Linking_Page PASSED - Successfully navigated to Initiative Linking page.");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_002_Search_Record_On_Initiative_Linking_Page
     *
     * Test Case: Search the record on the Initiative Linking page
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     * 3. Click on the Search button
     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
     * 5. Click on the Business Group dropdown field and select the value from the excel
     * 6. Click on the Organization Unit dropdown field and select the value from the excel
     * 7. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field
     * 8. Click on the Initiative Title text field and enter a Initiative Title which is mentioned in the excel field
     * 9. Click on the Search button
     */
    @Test(priority = 2, enabled = true, dataProvider = "initiativeLinkingData")
    @Description("TC_002_Search_Record_On_Initiative_Linking_Page - Search records on Initiative Linking page using filters")
    @Story("Initiative Linking - Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_Search_Record_On_Initiative_Linking_Page(
            String natureOfInitiative,
            String businessGroup,
            String organizationUnit,
            String initiativeCode,
            String initiativeTitle
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_002_Search_Record_On_Initiative_Linking_Page: Search the record on the Initiative Linking page");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button");
        try {
            initiativeLinkingPage.clickSearchToolbarButton();
        } catch (Exception e) {
            System.out.println("  ⚠️ Search button click failed, but continuing with filter fields: " + e.getMessage());
            // Wait a bit in case the panel is already open or opens differently
            Thread.sleep(3000);
        }
        Thread.sleep(2000);

        // Step 4: Select Nature of Initiative from Excel
        if (natureOfInitiative != null && !natureOfInitiative.trim().isEmpty()) {
            System.out.println("\n📌 Step 4: Select Nature of Initiative from Excel: " + natureOfInitiative);
            initiativeLinkingPage.selectNatureOfInitiative(natureOfInitiative);
            Thread.sleep(1000);
        }

        // Step 5: Select Business Group from Excel
        if (businessGroup != null && !businessGroup.trim().isEmpty()) {
            System.out.println("\n📌 Step 5: Select Business Group from Excel: " + businessGroup);
            initiativeLinkingPage.selectBusinessGroup(businessGroup);
            Thread.sleep(3000); // Increased wait time - Organization Unit might depend on Business Group
        }

        // Step 6: Select Organization Unit from Excel
        if (organizationUnit != null && !organizationUnit.trim().isEmpty()) {
            System.out.println("\n📌 Step 6: Select Organization Unit from Excel: " + organizationUnit);
            // Wait a bit more before selecting Organization Unit as it might be dependent on Business Group
            Thread.sleep(2000);
            initiativeLinkingPage.selectOrganizationUnit(organizationUnit);
            Thread.sleep(1000);
        }

        // Step 7: Enter Initiative Code from Excel
        if (initiativeCode != null && !initiativeCode.trim().isEmpty()) {
            System.out.println("\n📌 Step 7: Enter Initiative Code from Excel: " + initiativeCode);
            initiativeLinkingPage.enterInitiativeCode(initiativeCode);
        }

        // Step 8: Enter Initiative Title from Excel
        if (initiativeTitle != null && !initiativeTitle.trim().isEmpty()) {
            System.out.println("\n📌 Step 8: Enter Initiative Title from Excel: " + initiativeTitle);
            initiativeLinkingPage.enterInitiativeTitle(initiativeTitle);
        }

        // Step 9: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 9: Click on the Search button (filter panel)");
        initiativeLinkingPage.clickFilterSearchButton();
        Thread.sleep(3000);

        System.out.println("\n✅ TC_002_Search_Record_On_Initiative_Linking_Page PASSED - Search executed successfully.");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_003_Add_Child_Initiative_Against_Parent_Initiative
     *
     * Test Case: Add Child initiative against the parent initiative
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     * 3. Click on the Search button
     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
     * 5. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field
     * 6. Click on the Initiative Status dropdown field and select the values from the excel
     * 7. Click on the Search button
     * 8. Click on the Add child Link
     * 9. Just Click on the Child Initiative text field dont enter any value
     * 10. Click on the Relation Type dropdown field and select the value from the excel
     * 11. Click on the Initiative Title text field and enter the value from the excel
     * 12. Click on the Apply button
     * 13. Select the checkbox
     * 14. Click on the Save button
     * 15. Click on the Add child button
     * 16. Expand the Initiative
     * 17. Verify the added child initiative displayed after expand the parent initiative
     */
    @Test(priority = 3, enabled = true, dataProvider = "initiativeLinkingData")
    @Description("TC_003_Add_Child_Initiative_Against_Parent_Initiative - Add child initiative against parent initiative and verify")
    @Story("Initiative Linking - Add Child Initiative")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003_Add_Child_Initiative_Against_Parent_Initiative(
            String natureOfInitiative,
            String initiativeCode,
            String initiativeStatus,
            String relationType,
            String childInitiativeTitle
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_003_Add_Child_Initiative_Against_Parent_Initiative: Add Child initiative against the parent initiative");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button");
        try {
            initiativeLinkingPage.clickSearchToolbarButton();
        } catch (Exception e) {
            System.out.println("  ⚠️ Search button click failed, but continuing: " + e.getMessage());
            Thread.sleep(3000);
        }
        Thread.sleep(2000);

        // Step 4: Select Nature of Initiative from Excel
        if (natureOfInitiative != null && !natureOfInitiative.trim().isEmpty()) {
            System.out.println("\n📌 Step 4: Select Nature of Initiative from Excel: " + natureOfInitiative);
            initiativeLinkingPage.selectNatureOfInitiative(natureOfInitiative);
            Thread.sleep(1000);
        }

        // Step 5: Enter Initiative Code from Excel
        if (initiativeCode != null && !initiativeCode.trim().isEmpty()) {
            System.out.println("\n📌 Step 5: Enter Initiative Code from Excel: " + initiativeCode);
            initiativeLinkingPage.enterInitiativeCode(initiativeCode);
        }

        // Step 6: Select Initiative Status from Excel
        if (initiativeStatus != null && !initiativeStatus.trim().isEmpty()) {
            System.out.println("\n📌 Step 6: Select Initiative Status from Excel: " + initiativeStatus);
            initiativeLinkingPage.selectInitiativeStatus(initiativeStatus);
            Thread.sleep(1000);
        }

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeLinkingPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Add child Link
        System.out.println("\n📌 Step 8: Click on the Add child Link");
        initiativeLinkingPage.clickAddChildLink();
        Thread.sleep(2000);

        // Step 9: Just Click on the Child Initiative text field (don't enter any value)
        System.out.println("\n📌 Step 9: Click on the Child Initiative text field (no value entered)");
        initiativeLinkingPage.clickChildInitiativeTextField();
        Thread.sleep(1000);

        // Step 10: Select Relation Type from Excel
        if (relationType != null && !relationType.trim().isEmpty()) {
            System.out.println("\n📌 Step 10: Select Relation Type from Excel: " + relationType);
            initiativeLinkingPage.selectRelationType(relationType);
            Thread.sleep(1000);
        }

        // Step 11: Enter Initiative Title from Excel
        if (childInitiativeTitle != null && !childInitiativeTitle.trim().isEmpty()) {
            System.out.println("\n📌 Step 11: Enter Initiative Title from Excel: " + childInitiativeTitle);
            initiativeLinkingPage.enterInitiativeTitleInChildForm(childInitiativeTitle);
            Thread.sleep(1000);
        }

        // Step 12: Click on the Apply button
        System.out.println("\n📌 Step 12: Click on the Apply button");
        initiativeLinkingPage.clickApplyButton();
        Thread.sleep(2000);

        // Step 13: Select the checkbox
        System.out.println("\n📌 Step 13: Select the checkbox");
        initiativeLinkingPage.selectCheckbox();
        Thread.sleep(1000);

        // Step 14: Click on the Save button
        System.out.println("\n📌 Step 14: Click on the Save button");
        initiativeLinkingPage.clickSaveButton();
        Thread.sleep(2000);

        // Step 15: Click on the Add child button
        System.out.println("\n📌 Step 15: Click on the Add child button");
        initiativeLinkingPage.clickAddChildButton();
        Thread.sleep(2000);

        // Step 16: Expand the Initiative
        System.out.println("\n📌 Step 16: Expand the Initiative");
        initiativeLinkingPage.expandInitiative();
        Thread.sleep(2000);

        // Step 17: Verify the added child initiative displayed after expand the parent initiative
        System.out.println("\n📌 Step 17: Verify the added child initiative displayed after expand the parent initiative");
        boolean childDisplayed = initiativeLinkingPage.verifyChildInitiativeDisplayed(childInitiativeTitle);
        
        if (childDisplayed) {
            System.out.println("\n✅ TC_003_Add_Child_Initiative_Against_Parent_Initiative PASSED - Child initiative successfully added and verified.");
        } else {
            throw new Exception("TC_003_Add_Child_Initiative_Against_Parent_Initiative FAILED - Child initiative not found after expanding parent");
        }
        
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_004_Check_Move_To_Initiative
     *
     * Test Case: Check Move to Initiative
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     * 3. Click on the Search button
     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
     * 5. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field
     * 6. Click on the Initiative Status dropdown field and select the values from the excel
     * 7. Click on the Search button
     * 8. Click on the Move To Link
     * 9. Just Click on the Parent Initiative text field dont enter any value
     * 10. Click on the Relation Type dropdown field and select the value from the excel
     * 11. Click on the Initiative Title text field and enter the value from the excel
     * 12. Click on the Apply button
     * 13. Select the checkbox
     * 14. Click on the Save button
     * 15. Click on the Move Initiative button
     * 16. Expand the Initiative
     * 17. Verify that moved initiative displayed after expand the parent initiative
     */
    @Test(priority = 4, enabled = true, dataProvider = "initiativeLinkingData")
    @Description("TC_004_Check_Move_To_Initiative - Move initiative to another parent and verify")
    @Story("Initiative Linking - Move To Initiative")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_004_Check_Move_To_Initiative(
            String natureOfInitiative,
            String initiativeCode,
            String initiativeStatus,
            String relationType,
            String initiativeTitle
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_004_Check_Move_To_Initiative: Check Move to Initiative");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button");
        try {
            initiativeLinkingPage.clickSearchToolbarButton();
        } catch (Exception e) {
            System.out.println("  ⚠️ Search button click failed, but continuing: " + e.getMessage());
            Thread.sleep(3000);
        }
        Thread.sleep(2000);

        // Step 4: Select Nature of Initiative from Excel
        if (natureOfInitiative != null && !natureOfInitiative.trim().isEmpty()) {
            System.out.println("\n📌 Step 4: Select Nature of Initiative from Excel: " + natureOfInitiative);
            initiativeLinkingPage.selectNatureOfInitiative(natureOfInitiative);
            Thread.sleep(1000);
        }

        // Step 5: Enter Initiative Code from Excel
        if (initiativeCode != null && !initiativeCode.trim().isEmpty()) {
            System.out.println("\n📌 Step 5: Enter Initiative Code from Excel: " + initiativeCode);
            initiativeLinkingPage.enterInitiativeCode(initiativeCode);
            Thread.sleep(1000);
        }

        // Step 6: Select Initiative Status from Excel
        if (initiativeStatus != null && !initiativeStatus.trim().isEmpty()) {
            System.out.println("\n📌 Step 6: Select Initiative Status from Excel: " + initiativeStatus);
            initiativeLinkingPage.selectInitiativeStatus(initiativeStatus);
            Thread.sleep(1000);
        }

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeLinkingPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Move To Link
        System.out.println("\n📌 Step 8: Click on the Move To Link");
        initiativeLinkingPage.clickMoveToLink();
        Thread.sleep(2000);

        // Step 9: Click on the Parent Initiative text field (no value entered)
        System.out.println("\n📌 Step 9: Click on the Parent Initiative text field (no value entered)");
        initiativeLinkingPage.clickParentInitiativeTextField();
        Thread.sleep(1000);

        // Step 10: Select Relation Type from Excel
        if (relationType != null && !relationType.trim().isEmpty()) {
            System.out.println("\n📌 Step 10: Select Relation Type from Excel: " + relationType);
            initiativeLinkingPage.selectRelationType(relationType);
            Thread.sleep(1000);
        }

        // Step 11: Enter Initiative Title from Excel
        if (initiativeTitle != null && !initiativeTitle.trim().isEmpty()) {
            System.out.println("\n📌 Step 11: Enter Initiative Title from Excel: " + initiativeTitle);
            initiativeLinkingPage.enterInitiativeTitleInChildForm(initiativeTitle);
            Thread.sleep(1000);
        }

        // Step 12: Click on the Apply button
        System.out.println("\n📌 Step 12: Click on the Apply button");
        initiativeLinkingPage.clickApplyButtonMoveTo();
        Thread.sleep(2000);

        // Step 13: Select the checkbox
        System.out.println("\n📌 Step 13: Select the checkbox");
        initiativeLinkingPage.selectCheckboxMoveTo();
        Thread.sleep(1000);

        // Step 14: Click on the Save button
        System.out.println("\n📌 Step 14: Click on the Save button");
        initiativeLinkingPage.clickSaveButtonMoveTo();
        Thread.sleep(2000);

        // Step 15: Click on the Move Initiative button
        System.out.println("\n📌 Step 15: Click on the Move Initiative button");
        initiativeLinkingPage.clickMoveInitiativeButton();
        Thread.sleep(2000);

        // Step 16: Expand the Initiative
        System.out.println("\n📌 Step 16: Expand the Initiative");
        initiativeLinkingPage.expandInitiative();
        Thread.sleep(2000);

        // Step 17: Verify that moved initiative displayed after expand the parent initiative
        System.out.println("\n📌 Step 17: Verify that moved initiative displayed after expand the parent initiative");
        boolean isMovedInitiativeDisplayed = initiativeLinkingPage.verifyMovedInitiativeDisplayed(initiativeTitle);
        
        if (isMovedInitiativeDisplayed) {
            System.out.println("\n✅ TC_004_Check_Move_To_Initiative PASSED - Moved initiative successfully displayed and verified.");
        } else {
            throw new Exception("TC_004_Check_Move_To_Initiative FAILED - Moved initiative not found after expanding parent");
        }
        
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_005_Remove_Linking_Of_The_Initiative
     *
     * Test Case: Remove Linking of the Initiative
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     * 3. Click on the Search button
     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
     * 5. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field
     * 6. Click on the Initiative Status dropdown field and select the values from the excel
     * 7. Click on the Search button
     * 8. Click on the expand button
     * 9. Click on the Remove Linking option
     * 10. Click on the OK button on the confirmation popup page
     * 11. Click on the expand button
     * 12. Verify that linked initiative get removed after expand the initiative
     */
    @Test(priority = 5, enabled = true, dataProvider = "initiativeLinkingData")
    @Description("TC_005_Remove_Linking_Of_The_Initiative - Remove linking of initiative and verify removal")
    @Story("Initiative Linking - Remove Linking")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_005_Remove_Linking_Of_The_Initiative(
            String natureOfInitiative,
            String initiativeCode,
            String initiativeStatus,
            String relationType,
            String initiativeTitle
    ) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_005_Remove_Linking_Of_The_Initiative: Remove Linking of the Initiative");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button");
        try {
            initiativeLinkingPage.clickSearchToolbarButton();
        } catch (Exception e) {
            System.out.println("  ⚠️ Search button click failed, but continuing: " + e.getMessage());
            Thread.sleep(3000);
        }
        Thread.sleep(2000);

        // Step 4: Select Nature of Initiative from Excel
        if (natureOfInitiative != null && !natureOfInitiative.trim().isEmpty()) {
            System.out.println("\n📌 Step 4: Select Nature of Initiative from Excel: " + natureOfInitiative);
            initiativeLinkingPage.selectNatureOfInitiative(natureOfInitiative);
            Thread.sleep(1000);
        }

        // Step 5: Enter Initiative Code from Excel
        if (initiativeCode != null && !initiativeCode.trim().isEmpty()) {
            System.out.println("\n📌 Step 5: Enter Initiative Code from Excel: " + initiativeCode);
            initiativeLinkingPage.enterInitiativeCode(initiativeCode);
            Thread.sleep(1000);
        }

        // Step 6: Select Initiative Status from Excel
        if (initiativeStatus != null && !initiativeStatus.trim().isEmpty()) {
            System.out.println("\n📌 Step 6: Select Initiative Status from Excel: " + initiativeStatus);
            initiativeLinkingPage.selectInitiativeStatus(initiativeStatus);
            Thread.sleep(1000);
        }

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeLinkingPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the expand button
        System.out.println("\n📌 Step 8: Click on the expand button");
       initiativeLinkingPage.clickExpandButtonForRemoveLinking();
        Thread.sleep(2000);

        // Step 9: Click on the Remove Linking option
        System.out.println("\n📌 Step 9: Click on the Remove Linking option");
        initiativeLinkingPage.clickRemoveLinkingOption();
        Thread.sleep(2000);

        // Step 10: Click on the OK button on the confirmation popup page
        System.out.println("\n📌 Step 10: Click on the OK button on the confirmation popup page");
        initiativeLinkingPage.clickOkButtonOnConfirmationPopup();
        Thread.sleep(2000);

        // Step 11: Click on the expand button
        System.out.println("\n📌 Step 11: Click on the expand button");
        initiativeLinkingPage.clickExpandButtonForRemoveLinking();
        Thread.sleep(2000);

        // Step 12: Verify that linked initiative get removed after expand the initiative
        System.out.println("\n📌 Step 12: Verify that linked initiative get removed after expand the initiative");
        boolean isRemoved = initiativeLinkingPage.verifyLinkedInitiativeRemoved(initiativeCode);
        
        if (isRemoved) {
            System.out.println("\n✅ TC_005_Remove_Linking_Of_The_Initiative PASSED - Linked initiative successfully removed and verified.");
        } else {
            throw new Exception("TC_005_Remove_Linking_Of_The_Initiative FAILED - Linked initiative is still present in expanded section");
        }
        
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_006_Link_To_The_Program
     *
     * Test Case: Link to the program
     *
     * Steps:
     * 1. Click on the Initiative Tracking Module
     * 2. Click on the Initiative Linking page
     * 3. Click on the Search button
     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
     * 5. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field.
     * 6. Click on the Initiative Status dropdown field and select the values from the excel
     * 7. Click on the Search button
     * 8. Click on the Link to Program option.
     * 9. Select the program checkbox (Program name mentioned in the excel) and click on the save button.
     * 10. Click on the Ok button on the confirmation pop up page.
     */
    @Test(priority = 6, dataProvider = "initiativeLinkingData", enabled = true)
    @Description("TC_006_Link_To_The_Program - Link an initiative to a program")
    @Story("Initiative Linking - Link to Program")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_006_Link_To_The_Program(String natureOfInitiative, String initiativeCode, String initiativeStatus, String programName) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_006_Link_To_The_Program: Link to the program");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeLinkingPage = new InitiativeLinking(webDriver, reportLogger);

        // Step 1: Click on the Initiative Tracking Module
        System.out.println("\n📌 Step 1: Click on the Initiative Tracking Module");
        initiativeLinkingPage.clickInitiativeTrackingModule();
        Thread.sleep(2000);

        // Step 2: Click on the Initiative Linking page
        System.out.println("\n📌 Step 2: Click on the Initiative Linking page");
        initiativeLinkingPage.clickInitiativeLinkingPageLink();
        Thread.sleep(2000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button");
        initiativeLinkingPage.clickSearchToolbarButton();
        Thread.sleep(1000);

        // Step 4: Select Nature of Initiative from Excel
        System.out.println("\n📌 Step 4: Select Nature of Initiative from Excel: " + natureOfInitiative);
        initiativeLinkingPage.selectNatureOfInitiative(natureOfInitiative);
        Thread.sleep(1000);

        // Step 5: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 5: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeLinkingPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 6: Select Initiative Status from Excel
        System.out.println("\n📌 Step 6: Select Initiative Status from Excel: " + initiativeStatus);
        initiativeLinkingPage.selectInitiativeStatus(initiativeStatus);
        Thread.sleep(1000);

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeLinkingPage.clickFilterSearchButton();
        Thread.sleep(3000);
        
        // Step 8: Click on the Link to Program option
        System.out.println("\n📌 Step 8: Click on the Link to Program option");
        initiativeLinkingPage.clickLinkToProgramOption();
        Thread.sleep(2000);

        // Step 9: Select the program checkbox and click on the save button
        System.out.println("\n📌 Step 9: Select the program checkbox (Program name: " + programName + ") and click on the save button");
        if (programName == null || programName.trim().isEmpty()) {
            throw new Exception("Program name cannot be null or empty");
        }
        initiativeLinkingPage.selectProgramCheckbox(programName);
        Thread.sleep(1000);
        initiativeLinkingPage.clickSaveButtonLinkToProgram();
        Thread.sleep(2000);

        
        
        // Step 10: Click on the Ok button on the confirmation pop up page
        System.out.println("\n📌 Step 10: Click on the Ok button on the confirmation pop up page");
        initiativeLinkingPage.clickOkButtonOnConfirmationPopupLinkToProgram();
        Thread.sleep(2000);

        System.out.println("\n✅ TC_006_Link_To_The_Program completed successfully");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
}

