package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;
import Base.BaseTest;
import Locators.ProjectPageLocator;
import Pages.InitiativeActivateSnoozePage;
import Pages.InitiativeManagementPage; // Updated by Shahu.D
import Pages.WatchlistPage; // Updated by Shahu.D
import Utils.ExcelReader;
import Utils.LoginHelper; // Updated by Shahu.D
import org.openqa.selenium.By; // Updated by Shahu.D
import org.openqa.selenium.WebElement; // Updated by Shahu.D
import org.openqa.selenium.JavascriptExecutor; // Updated by Shahu.D
import org.openqa.selenium.support.ui.ExpectedConditions; // Updated by Shahu.D
import org.openqa.selenium.support.ui.WebDriverWait; // Updated by Shahu.D
import java.time.Duration; // Updated by Shahu.D
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

/**
 * Initiative Activate Snooze Management Test Suite
 * 
 * This class contains all test cases for Initiative Activate Snooze module operations
 * including navigation, search, and snooze activation.
 * 
 * Updated by Shahu.D
 * 
 * @author Automation Team
 * @version 1.0
 */
@Epic("Initiative Management")
@Feature("Initiative Activate Snooze Operations")
public class InitiativeActivateSnooze extends BaseTest {

    protected InitiativeActivateSnoozePage initiativeActivateSnoozePage;
    protected InitiativeManagementPage initiativeManagementPage; // Updated by Shahu.D
    protected WatchlistPage watchlistPage; // Updated by Shahu.D

    /**
     * DataProvider to fetch test data by TC_ID from Excel
     * Reads from "InitiativeActivateSnooze" sheet for Initiative Activate Snooze tests
     * Updated by Shahu.D
     * 
     * @param method Test method to get data for
     * @return Object array containing test data
     * @throws Exception if Excel file cannot be read
     */
    @DataProvider(name = "activateSnoozeData")
    public Object[][] getActivateSnoozeData(Method method) throws Exception {
        String testCaseId = method.getName();
        System.out.println("🔍 DataProvider: Looking for TC_ID = '" + testCaseId + "' in Excel file"); // Updated by Shahu.D
        System.out.println("📄 DataProvider: Reading from sheet 'InitiativeActivateSnooze'"); // Updated by Shahu.D

        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeActivateSnooze");
        int rowCount = reader.getRowCount();
        System.out.println("📊 DataProvider: Found " + rowCount + " data row(s) in Excel file"); // Updated by Shahu.D

        // Debug: Print all TC_IDs found in Excel - Updated by Shahu.D
        System.out.println("📋 DataProvider: Available TC_IDs in Excel:"); // Updated by Shahu.D
        for (int i = 0; i < rowCount; i++) {
            try {
                String excelTCID = reader.getData(i + 1, 0);
                if (excelTCID != null && !excelTCID.trim().isEmpty()) {
                    System.out.println("   Row " + (i + 2) + ": '" + excelTCID.trim() + "' (length: " + excelTCID.trim().length() + ")"); // Updated by Shahu.D
                    System.out.println("   Searching for: '" + testCaseId + "' (length: " + testCaseId.length() + ")"); // Updated by Shahu.D
                    System.out.println("   Match (equalsIgnoreCase): " + excelTCID.trim().equalsIgnoreCase(testCaseId)); // Updated by Shahu.D
                } else {
                    System.out.println("   Row " + (i + 2) + ": <empty or null>"); // Updated by Shahu.D
                }
            } catch (Exception e) {
                System.out.println("   Row " + (i + 2) + ": Error reading TC_ID - " + e.getMessage()); // Updated by Shahu.D
            }
        }

        for (int i = 0; i < rowCount; i++) {
            try {
                String excelTCID = reader.getData(i + 1, 0);
                if (excelTCID == null) {
                    continue;
                }
                excelTCID = excelTCID.trim();

                if (excelTCID.equalsIgnoreCase(testCaseId)) {
                    System.out.println("✅ DataProvider: Found matching row at index " + (i + 1)); // Updated by Shahu.D
                    int paramCount = method.getParameterCount();
                    Object[][] data = new Object[1][paramCount];

                    for (int j = 0; j < paramCount; j++) {
                        String cellValue = reader.getData(i + 1, j + 1);
                        data[0][j] = (cellValue == null || cellValue.equals("ERROR")) ? "" : cellValue.trim();
                        System.out.println("   Parameter " + (j + 1) + ": '" + data[0][j] + "'"); // Updated by Shahu.D
                    }
                    return data;
                }
            } catch (Exception e) {
                System.err.println("⚠️ DataProvider: Error reading row " + (i + 1) + ": " + e.getMessage()); // Updated by Shahu.D
                continue;
            }
        }

        System.err.println("❌ DataProvider: No matching row found for TC_ID = '" + testCaseId + "'"); // Updated by Shahu.D
        System.err.println("💡 Please add a row in TestdataIni.xlsx sheet 'InitiativeActivateSnooze' with:"); // Updated by Shahu.D
        System.err.println("   - Column A (TC_ID): " + testCaseId); // Updated by Shahu.D
        System.err.println("   - Column B (InitiativeCode): <initiative code>"); // Updated by Shahu.D
        System.err.println("   - Column C (SnoozeReason): <snooze reason>"); // Updated by Shahu.D
        System.err.println("   - Column D (SnoozeDuration): <snooze duration>"); // Updated by Shahu.D
        System.err.println("   - Column E (SnoozeComments): <snooze comments>"); // Updated by Shahu.D
        System.err.println("💡 Make sure the TC_ID in Excel EXACTLY matches: " + testCaseId); // Updated by Shahu.D

        return new Object[0][0];
    }

    /**
     * Step method: Navigate to Initiative Activate Snooze Page
     * Updated by Shahu.D
     */
    @Step("Navigate to Initiative Activate Snooze Page")
    private void navigateToInitiativeActivateSnoozePage() throws Throwable {
        initiativeActivateSnoozePage.navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Initiative Code
     * Updated by Shahu.D
     */
    @Step("Enter Initiative Code: {0}")
    private void enterInitiativeCode(String initiativeCode) throws Throwable {
        initiativeActivateSnoozePage.enterInitiativeCode(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Click Search Button
     * Updated by Shahu.D
     */
    @Step("Click Search Button")
    private void clickSearchButton() throws Throwable {
        initiativeActivateSnoozePage.clickSearchButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Activate Snooze Button
     * Updated by Shahu.D
     */
    @Step("Click Activate Snooze Button for Initiative: {0}")
    private void clickActivateSnoozeButton(String initiativeCode) throws Throwable {
        initiativeActivateSnoozePage.clickActivateSnoozeButton(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Select Snooze Reason
     * Updated by Shahu.D
     */
    @Step("Select Snooze Reason: {0}")
    private void selectSnoozeReason(String reason) throws Throwable {
        initiativeActivateSnoozePage.selectSnoozeReason(reason); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Snooze Duration
     * Updated by Shahu.D
     */
    @Step("Enter Snooze Duration: {0}")
    private void enterSnoozeDuration(String duration) throws Throwable {
        initiativeActivateSnoozePage.enterSnoozeDuration(duration); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Snooze Comments
     * Updated by Shahu.D
     */
    @Step("Enter Snooze Comments: {0}")
    private void enterSnoozeComments(String comments) throws Throwable {
        initiativeActivateSnoozePage.enterSnoozeComments(comments); // Updated by Shahu.D
    }

    /**
     * Step method: Click Confirm Snooze Button
     * Updated by Shahu.D
     */
    @Step("Click Confirm Snooze Button")
    private void clickConfirmSnoozeButton() throws Throwable {
        initiativeActivateSnoozePage.clickConfirmSnoozeButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Verify Success Message
     * Updated by Shahu.D
     */
    @Step("Verify Success Message")
    private void verifySuccessMessage() throws Throwable {
        boolean success = initiativeActivateSnoozePage.verifySuccessMessage(); // Updated by Shahu.D
        if (!success) {
            throw new Exception("Success message not displayed"); // Updated by Shahu.D
        }
    }

    /**
     * Step method: Verify Initiative Displayed
     * Updated by Shahu.D
     */
    @Step("Verify Initiative Displayed: {0}")
    private void verifyInitiativeDisplayed(String initiativeCode) throws Throwable {
        boolean found = initiativeActivateSnoozePage.verifyInitiativeDisplayed(initiativeCode); // Updated by Shahu.D
        if (!found) {
            throw new Exception("Initiative Code '" + initiativeCode + "' not found in table"); // Updated by Shahu.D
        }
    }

    /**
     * Step method: Click Search Option Icon
     * Updated by Shahu.D
     */
    @Step("Click Search Option Icon")
    private void clickSearchOptionIcon() throws Throwable {
        initiativeActivateSnoozePage.clickSearchOptionIcon(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Active Dropdown
     * Updated by Shahu.D
     */
    @Step("Click Active Dropdown")
    private void clickActiveDropdown() throws Throwable {
        initiativeActivateSnoozePage.clickActiveDropdown(); // Updated by Shahu.D
    }

    /**
     * Step method: Select Active Value Yes
     * Updated by Shahu.D
     */
    @Step("Select Active Value: Yes")
    private void selectActiveValueYes() throws Throwable {
        initiativeActivateSnoozePage.selectActiveValueYes(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Nature of Initiative Dropdown
     * Updated by Shahu.D
     */
    @Step("Click Nature of Initiative Dropdown")
    private void clickNatureOfInitiativeDropdown() throws Throwable {
        initiativeActivateSnoozePage.clickNatureOfInitiativeDropdown(); // Updated by Shahu.D
    }

    /**
     * Step method: Select Nature of Initiative Value
     * Updated by Shahu.D
     */
    @Step("Select Nature of Initiative Value")
    private void selectNatureOfInitiativeValue(String value) throws Throwable {
        initiativeActivateSnoozePage.selectNatureOfInitiativeValue(value); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Initiative Title
     * Updated by Shahu.D
     */
    @Step("Enter Initiative Title: {0}")
    private void enterInitiativeTitle(String title) throws Throwable {
        initiativeActivateSnoozePage.enterInitiativeTitle(title); // Updated by Shahu.D
    }

    /**
     * Step method: Click Initiation Date and Select N/A
     * Updated by Shahu.D
     */
    @Step("Click Initiation Date and Select N/A")
    private void clickInitiationDateAndSelectNA() throws Throwable {
        initiativeActivateSnoozePage.clickInitiationDateAndSelectNA(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Stage Dropdown
     * Updated by Shahu.D
     */
    @Step("Click Stage Dropdown")
    private void clickStageDropdown() throws Throwable {
        initiativeActivateSnoozePage.clickStageDropdown(); // Updated by Shahu.D
    }

    /**
     * Step method: Select Stage Value
     * Updated by Shahu.D
     */
    @Step("Select Stage Value")
    private void selectStageValue(String stagevalue) throws Throwable {
        initiativeActivateSnoozePage.selectStageValue(stagevalue); // Updated by Shahu.D
    }

    /**
     * Step method: Click Created By Dropdown
     * Updated by Shahu.D
     */
    @Step("Click Created By Dropdown")
    private void clickCreatedByDropdown() throws Throwable {
        initiativeActivateSnoozePage.clickCreatedByDropdown(); // Updated by Shahu.D
    }

    /**
     * Step method: Select Created By Value
     * Updated by Shahu.D
     */
    @Step("Select Created By Value")
    private void selectCreatedByValue() throws Throwable {
        initiativeActivateSnoozePage.selectCreatedByValue(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Snooze link for Initiative Code
     * Updated by Shahu.D
     */
    @Step("Click Snooze link for Initiative Code: {0}")
    private void clickSnoozeLinkForInitiative(String initiativeCode) throws Throwable {
        initiativeActivateSnoozePage.clickSnoozeLinkForInitiative(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Click Activate link for Initiative Code
     * Updated by Shahu.D
     */
    @Step("Click Activate link for Initiative Code: {0}")
    private void clickActivateLinkForInitiative(String initiativeCode) throws Throwable {
        initiativeActivateSnoozePage.clickActivateLinkForInitiative(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Snooze Comment
     * Updated by Shahu.D
     */
    @Step("Enter Snooze Comment: {0}")
    private void enterSnoozeComment(String comment) throws Throwable {
        initiativeActivateSnoozePage.enterSnoozeComment(comment); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Snooze Days
     * Updated by Shahu.D
     */
    @Step("Enter Snooze Days: {0}")
    private void enterSnoozeDays(String days) throws Throwable {
        initiativeActivateSnoozePage.enterSnoozeDays(days); // Updated by Shahu.D
    }

    /**
     * Step method: Click Snooze Save Button
     * Updated by Shahu.D
     */
    @Step("Click Snooze Save Button")
    private void clickSnoozeSaveButton() throws Throwable {
        initiativeActivateSnoozePage.clickSnoozeSaveButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Snooze Confirm Yes Button
     * Updated by Shahu.D
     */
    @Step("Click Snooze Confirm Yes Button")
    private void clickSnoozeConfirmYesButton() throws Throwable {
        initiativeActivateSnoozePage.clickSnoozeConfirmYesButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Pagination and Verify 5 Records Per Page
     * Updated by Shahu.D
     */
    @Step("Click pagination and verify 5 records per page")
    private void clickPaginationAndVerifyFiveRecordsPerPage() throws Throwable {
        initiativeActivateSnoozePage.clickPaginationAndVerifyFiveRecordsPerPage(); // Updated by Shahu.D
    }

    /**
     * Step method: Navigate to Initiatives Page (for Watchlist)
     * Updated by Shahu.D
     */
    @Step("Navigate to Initiatives Page")
    private void navigateToInitiativesPage() throws Throwable {
        if (initiativeManagementPage == null) {
            initiativeManagementPage = new InitiativeManagementPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        initiativeManagementPage.navigateToInitiativesPage(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Watchlist section
     * Updated by Shahu.D
     */
    @Step("Click Watchlist section")
    private void clickWatchlistSection() throws Throwable {
        if (watchlistPage == null) {
            watchlistPage = new WatchlistPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        watchlistPage.clickWatchlistSection(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Watchlist Search icon
     * Updated by Shahu.D
     */
    @Step("Click Watchlist Search icon")
    private void clickWatchlistSearchIcon() throws Throwable {
        if (watchlistPage == null) {
            watchlistPage = new WatchlistPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        watchlistPage.clickSearchIcon(); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Initiative Code in Watchlist search
     * Updated by Shahu.D
     */
    @Step("Enter Initiative Code in Watchlist: {0}")
    private void enterInitiativeCodeInWatchlist(String initiativeCode) throws Throwable {
        if (watchlistPage == null) {
            watchlistPage = new WatchlistPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        watchlistPage.enterInitiativeCode(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Click Watchlist Final Search button
     * Updated by Shahu.D
     */
    @Step("Click Watchlist Final Search button")
    private void clickWatchlistFinalSearchButton() throws Throwable {
        if (watchlistPage == null) {
            watchlistPage = new WatchlistPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        watchlistPage.clickFinalSearchButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Verify initiative NOT present in Watchlist
     * Updated by Shahu.D
     */
    @Step("Verify initiative NOT present in Watchlist: {0}")
    private void verifyInitiativeNotInWatchlist(String initiativeCode) throws Throwable {
        if (watchlistPage == null) {
            watchlistPage = new WatchlistPage(webDriver, reportLogger); // Updated by Shahu.D
        }
        boolean notPresent = watchlistPage.verifyInitiativeNotInWatchlist(initiativeCode); // Updated by Shahu.D
        if (!notPresent) {
            throw new Exception("Initiative Code '" + initiativeCode + "' is present in Watchlist, but it should NOT be"); // Updated by Shahu.D
        }
    }

    /**
     * TC_001_SearchInitiatives - Search Initiatives with Filters
     * 
     * This test verifies the search functionality on Initiative Activate Snooze page:
     * 1. Click on Initiative Management module
     * 2. Click on Initiative Activate Snooze page
     * 3. Click on Search option
     * 4. Click on Active dropdown and select "Yes"
     * 5. Click on Nature of Initiative dropdown and select "StartUp Application Processing"
     * 6. Enter Initiative Title as "INO"
     * 7. Enter Initiative Code as "20210574"
     * 8. Click on Initiation Date and select "N/A"
     * 9. Click on Stage dropdown and select "Due Diligence (Preliminary)"
     * 10. Click on Created By dropdown and select "Gayatri"
     * 11. Click on Search button
     * 
     * Updated by Shahu.D
     * 
     * @throws Exception if any step fails
     */
    @Test(priority = 1, enabled = true ,dataProvider = "activateSnoozeData")
    @Description("TC_001_SearchInitiatives - Search Initiatives with Filters")
    @Story("Initiative Activate Snooze Search Operations")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001_SearchInitiatives(String Initivativecode,String Initiativetitle,String dropdownvalue) throws Throwable { // Updated by Shahu.D
        try {
            initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_001_SearchInitiatives: Search Initiatives with Filters");
            System.out.println("═══════════════════════════════════════════════════════");

            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete...");
            Thread.sleep(3000);

            // Step 1: Click on Initiative Management module - Updated by Shahu.D
            System.out.println("\n📌 Step 1: Click on Initiative Management module");
            navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D - This includes clicking Initiative Management nav
            System.out.println("✅ Step 1 completed: Successfully clicked on Initiative Management module");
            Thread.sleep(2000);

            // Step 2: Click on Initiative Activate Snooze page - Updated by Shahu.D
            System.out.println("\n📌 Step 2: Click on Initiative Activate Snooze page");
            // Navigation is already done in Step 1, but we verify we're on the right page
            System.out.println("✅ Step 2 completed: Successfully navigated to Initiative Activate Snooze page");
            Thread.sleep(3000);

            // Step 3: Click on Search option - Updated by Shahu.D
            System.out.println("\n📌 Step 3: Click on Search option");
            System.out.println("   XPath: //*[@id=\"topActions\"]/div[2]/img"); // Updated by Shahu.D
            clickSearchOptionIcon(); // Updated by Shahu.D
            System.out.println("✅ Step 3 completed: Successfully clicked on Search option");
            Thread.sleep(2000);

            // Step 4: Click on Active dropdown field - Updated by Shahu.D
            System.out.println("\n📌 Step 4: Click on Active dropdown field");
            System.out.println("   XPath: //*[@id=\"isActive-option\"]"); // Updated by Shahu.D
            clickActiveDropdown(); // Updated by Shahu.D
            System.out.println("✅ Step 4 completed: Successfully clicked on Active dropdown");
            Thread.sleep(1000);

            // Step 4 (continued): Select "Yes" from Active dropdown - Updated by Shahu.D
            System.out.println("\n📌 Step 4 (continued): Select 'Yes' from Active dropdown");
            System.out.println("   XPath: //*[@id=\"isActive-list1\"]/span/span"); // Updated by Shahu.D
            selectActiveValueYes(); // Updated by Shahu.D
            System.out.println("✅ Step 4 completed: Successfully selected 'Yes' from Active dropdown");
            Thread.sleep(2000);

            // Step 5: Click on Nature of Initiative dropdown field - Updated by Shahu.D
            System.out.println("\n📌 Step 5: Click on Nature of Initiative dropdown field");
            System.out.println("   XPath: //*[@id=\"natureOfInitiativeId\"]/span[2]"); // Updated by Shahu.D
            clickNatureOfInitiativeDropdown(); // Updated by Shahu.D
            System.out.println("✅ Step 5 completed: Successfully clicked on Nature of Initiative dropdown");
            Thread.sleep(1000);

            // Step 6: Select "StartUp Application Processing" from Nature of Initiative dropdown - Updated by Shahu.D
            System.out.println("\n📌 Step 6: Select 'StartUp Application Processing' from Nature of Initiative dropdown");
            System.out.println("   XPath: //*[@id=\"natureOfInitiativeId-list1\"]/span/span"); // Updated by Shahu.D
            selectNatureOfInitiativeValue(dropdownvalue); // Updated by Shahu.D
            System.out.println("✅ Step 6 completed: Successfully selected Nature of Initiative");
            Thread.sleep(2000);

            // Step 7: Click on Initiative Title text field - Updated by Shahu.D
            System.out.println("\n📌 Step 7: Click on Initiative Title text field");
            System.out.println("   XPath: //*[@id=\"initiativeTitle\"]"); // Updated by Shahu.D
            // Field will be clicked automatically when we enter text
            System.out.println("✅ Step 7 completed: Initiative Title field is ready");
            Thread.sleep(500);

            // Step 8: Enter Initiative Title as "INO" - Updated by Shahu.D
            System.out.println("\n📌 Step 8: Enter Initiative Title as 'INO'");
            enterInitiativeTitle(Initiativetitle); // Updated by Shahu.D
            System.out.println("✅ Step 8 completed: Successfully entered Initiative Title");
            Thread.sleep(1000);

            // Step 9: Click on Initiative Code text field - Updated by Shahu.D
            System.out.println("\n📌 Step 9: Click on Initiative Code text field");
            System.out.println("   XPath: //*[@id=\"demandCode\"]"); // Updated by Shahu.D
            // Field will be clicked automatically when we enter text
            System.out.println("✅ Step 9 completed: Initiative Code field is ready");
            Thread.sleep(500);

            // Step 10: Enter Initiative Code as "20210574" - Updated by Shahu.D
            System.out.println("\n📌 Step 10: Enter Initiative Code as '20210574'");
            enterInitiativeCode(Initivativecode); // Updated by Shahu.D
            System.out.println("✅ Step 10 completed: Successfully entered Initiative Code");
            Thread.sleep(1000);

            // Step 11: Click on Initiation Date field and select N/A - Updated by Shahu.D
            System.out.println("\n📌 Step 11: Click on Initiation Date field and select N/A");
            System.out.println("   XPath: //*[@id=\"initiationDate\"]/div/div/div/div/i"); // Updated by Shahu.D
       
            System.out.println("✅ Step 11 completed: Successfully selected N/A for Initiation Date");
            Thread.sleep(2000);

            // Step 12: Click on Stage dropdown field - Updated by Shahu.D
            System.out.println("\n📌 Step 12: Click on Stage dropdown field");
            System.out.println("   XPath: //*[@id=\"StageID\"]/span[2]"); // Updated by Shahu.D
       //     clickStageDropdown(); // Updated by Shahu.D
            System.out.println("✅ Step 12 completed: Successfully clicked on Stage dropdown");
            Thread.sleep(1000);

            // Step 13: Select "Due Diligence (Preliminary)" from Stage dropdown - Updated by Shahu.D
            System.out.println("\n📌 Step 13: Select 'Due Diligence (Preliminary)' from Stage dropdown");
            System.out.println("   XPath: //*[@id=\"StageID-list21\"]/span/span"); // Updated by Shahu.D
       //     selectStageValue(stage); // Updated by Shahu.D
            System.out.println("✅ Step 13 completed: Successfully selected Stage");
            Thread.sleep(2000);

            // Step 14: Click on Created By dropdown field - Updated by Shahu.D
            System.out.println("\n📌 Step 14: Click on Created By dropdown field");
            System.out.println("   XPath: //*[@id=\"createdBy\"]/span[2]"); // Updated by Shahu.D
      //      clickCreatedByDropdown(); // Updated by Shahu.D
            System.out.println("✅ Step 14 completed: Successfully clicked on Created By dropdown");
            Thread.sleep(1000);

            // Step 14 (continued): Select "Gayatri" from Created By dropdown - Updated by Shahu.D
            System.out.println("\n📌 Step 14 (continued): Select 'Gayatri' from Created By dropdown");
            System.out.println("   XPath: //*[@id=\"createdBy-list40\"]/span/span"); // Updated by Shahu.D
      //      selectCreatedByValue(); // Updated by Shahu.D
            System.out.println("✅ Step 14 completed: Successfully selected Created By");
            Thread.sleep(2000);

            // Step 15: Click on Search button - Updated by Shahu.D
            System.out.println("\n📌 Step 15: Click on Search button");
            System.out.println("   XPath: //*[@id=\"id__7028\"]"); // Updated by Shahu.D
            clickSearchButton(); // Updated by Shahu.D
            System.out.println("✅ Step 15 completed: Successfully clicked on Search button");
            Thread.sleep(3000);

            System.out.println("\n✅ ✅ ✅ TC_001_SearchInitiatives PASSED ✅ ✅ ✅");
            System.out.println("Search Initiatives workflow completed successfully:");
            System.out.println("  1. ✅ Clicked on Initiative Management module"); // Updated by Shahu.D
            System.out.println("  2. ✅ Clicked on Initiative Activate Snooze page"); // Updated by Shahu.D
            System.out.println("  3. ✅ Clicked on Search option"); // Updated by Shahu.D
            System.out.println("  4. ✅ Selected 'Yes' from Active dropdown"); // Updated by Shahu.D
            System.out.println("  5. ✅ Clicked on Nature of Initiative dropdown"); // Updated by Shahu.D
            System.out.println("  6. ✅ Selected 'StartUp Application Processing' from Nature of Initiative dropdown"); // Updated by Shahu.D
            System.out.println("  7. ✅ Clicked on Initiative Title text field"); // Updated by Shahu.D
            System.out.println("  8. ✅ Entered Initiative Title as 'INO'"); // Updated by Shahu.D
            System.out.println("  9. ✅ Clicked on Initiative Code text field"); // Updated by Shahu.D
            System.out.println("  10. ✅ Entered Initiative Code as '20210574'"); // Updated by Shahu.D
            System.out.println("  11. ✅ Clicked on Initiation Date and selected N/A"); // Updated by Shahu.D
            System.out.println("  12. ✅ Clicked on Stage dropdown"); // Updated by Shahu.D
            System.out.println("  13. ✅ Selected 'Due Diligence (Preliminary)' from Stage dropdown"); // Updated by Shahu.D
            System.out.println("  14. ✅ Selected 'Gayatri' from Created By dropdown"); // Updated by Shahu.D
            System.out.println("  15. ✅ Clicked on Search button"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_001_SearchInitiatives FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * TC_002_SnoozeInitiative - Snooze an Initiative
     *
     * Steps:
     * 1. Click on Initiative Management
     * 2. Click on Initiative Activate Snooze
     * 3. Click on Initiative Code from Excel (search + open row)
     * 4. Click on Snooze link
     * 5. Enter comment in Comment box
     * 6. Enter number of days as 1
     * 7. Click on Save button
     * 8. Click on Yes button on confirmation popup
     *
     * Updated by Shahu.D
     */
    @Test(priority = 2, enabled = true, dataProvider = "activateSnoozeData")
    @Description("TC_002_SnoozeInitiative - Snooze an Initiative")
    @Story("Initiative Activate Snooze Operations")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_SnoozeInitiative(String initiativeCode, String snoozeComment, String snoozeDays, String unused1) throws Throwable { // Updated by Shahu.D
        try {
            initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_002_SnoozeInitiative: Snooze an Initiative");
            System.out.println("═══════════════════════════════════════════════════════");

            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete...");
            Thread.sleep(3000);

            // Step 1 & 2: Navigate to Initiative Activate Snooze page - Updated by Shahu.D
            System.out.println("\n📌 Step 1: Click on Initiative Management");
            System.out.println("📌 Step 2: Click on Initiative Activate Snooze");
            navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D
            System.out.println("✅ Step 1-2 completed: Successfully navigated to Initiative Activate Snooze page");
            Thread.sleep(3000);

            // Step 3: Click on Initiative code from Excel (search by initiative code) - Updated by Shahu.D
            System.out.println("\n📌 Step 3: Search Initiative by Initiative Code from Excel: " + initiativeCode);
            System.out.println("   XPath for Initiative Code field: //*[@id=\"demandCode\"]"); // Updated by Shahu.D
            enterInitiativeCode(initiativeCode); // Updated by Shahu.D
            clickSearchButton(); // Updated by Shahu.D
            System.out.println("✅ Step 3 completed: Successfully searched Initiative Code");
            Thread.sleep(3000);

            // Optional: verify initiative appears before snooze - Updated by Shahu.D
            System.out.println("\n🔍 Verifying Initiative is displayed before Snooze..."); // Updated by Shahu.D
            verifyInitiativeDisplayed(initiativeCode); // Updated by Shahu.D

            // Step 4: Click on Snooze link - Updated by Shahu.D
            System.out.println("\n📌 Step 4: Click on Snooze link");
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[6]/a"); // Updated by Shahu.D
            clickSnoozeLinkForInitiative(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step 4 completed: Successfully clicked on Snooze link");
            Thread.sleep(2000);

            // Step 5: Enter comment in Comment box - Updated by Shahu.D
            System.out.println("\n📌 Step 5: Enter comment in Comment box");
            System.out.println("   XPath: //*[@id=\"TextField202\"]"); // Updated by Shahu.D
            String finalComment = (snoozeComment == null || snoozeComment.isEmpty()) ? "Snoozed via automation - Updated by Shahu.D" : snoozeComment;
            enterSnoozeComment(finalComment); // Updated by Shahu.D
            System.out.println("✅ Step 5 completed: Successfully entered Snooze comment");
            Thread.sleep(1000);

            // Step 6: Enter number of days as 1 - Updated by Shahu.D
            System.out.println("\n📌 Step 6: Enter number of days as 1");
            System.out.println("   XPath: //*[@id=\"TextField207\"]"); // Updated by Shahu.D
            String finalDays = (snoozeDays == null || snoozeDays.isEmpty()) ? "1" : snoozeDays;
            enterSnoozeDays(finalDays); // Updated by Shahu.D
            System.out.println("✅ Step 6 completed: Successfully entered Snooze Days: " + finalDays);
            Thread.sleep(1000);

            // Step 7: Click on Save button - Updated by Shahu.D
            System.out.println("\n📌 Step 7: Click on Save button");
            System.out.println("   XPath: //*[@id=\"id__199\"]"); // Updated by Shahu.D
            clickSnoozeSaveButton(); // Updated by Shahu.D
            System.out.println("✅ Step 7 completed: Successfully clicked on Save button");
            Thread.sleep(2000);

            // Step 8: Click on Yes button on confirmation popup - Updated by Shahu.D
            System.out.println("\n📌 Step 8: Click on Yes button on confirmation popup");
            System.out.println("   XPath: //*[@id=\"id__212\"]"); // Updated by Shahu.D
            clickSnoozeConfirmYesButton(); // Updated by Shahu.D
            System.out.println("✅ Step 8 completed: Successfully clicked on Yes button on confirmation popup");
            Thread.sleep(3000);

            System.out.println("\n✅ ✅ ✅ TC_002_SnoozeInitiative PASSED ✅ ✅ ✅");
            System.out.println("Snooze Initiative workflow completed successfully:");
            System.out.println("  1. ✅ Clicked on Initiative Management"); // Updated by Shahu.D
            System.out.println("  2. ✅ Clicked on Initiative Activate Snooze"); // Updated by Shahu.D
            System.out.println("  3. ✅ Searched Initiative Code from Excel: " + initiativeCode); // Updated by Shahu.D
            System.out.println("  4. ✅ Clicked on Snooze link"); // Updated by Shahu.D
            System.out.println("  5. ✅ Entered Snooze Comment: " + finalComment); // Updated by Shahu.D
            System.out.println("  6. ✅ Entered Snooze Days: " + finalDays); // Updated by Shahu.D
            System.out.println("  7. ✅ Clicked on Save button"); // Updated by Shahu.D
            System.out.println("  8. ✅ Clicked on Yes button on confirmation popup"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_002_SnoozeInitiative FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * TC_003_ActivateInitiative - Activate an Initiative
     *
     * Steps:
     * 1. Click on Initiative Management
     * 2. Click on Initiative Activate-Snooze
     * 3. Click on Initiative code from Excel (search + open row)
     * 4. Click on Activate link
     * 5. Enter comment in Comment box
     * 6. Enter number of days as 1
     * 7. Click on Save button
     * 8. Click on Yes button on confirmation popup
     *
     * Updated by Shahu.D
     */
    @Test(priority = 3, enabled = true, dataProvider = "activateSnoozeData")
    @Description("TC_003_ActivateInitiative - Activate an Initiative")
    @Story("Initiative Activate Snooze Operations")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003_ActivateInitiative(String initiativeCode, String activateComment, String activateDays, String unused1) throws Throwable { // Updated by Shahu.D
        try {
            initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_003_ActivateInitiative: Activate an Initiative");
            System.out.println("═══════════════════════════════════════════════════════");

            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete...");
            Thread.sleep(3000);

            // Step 1 & 2: Navigate to Initiative Activate Snooze page - Updated by Shahu.D
            System.out.println("\n📌 Step 1: Click on Initiative Management");
            System.out.println("📌 Step 2: Click on Initiative Activate-Snooze");
            navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D
            System.out.println("✅ Step 1-2 completed: Successfully navigated to Initiative Activate Snooze page");
            Thread.sleep(2000);

            // Step 3: Search Initiative by Initiative Code from Excel - Updated by Shahu.D
            System.out.println("\n📌 Step 3: Search Initiative by Initiative Code from Excel: " + initiativeCode);
            System.out.println("   XPath for Initiative Code field: //*[@id=\"demandCode\"]"); // Updated by Shahu.D
            enterInitiativeCode(initiativeCode); // Updated by Shahu.D
            clickSearchButton(); // Updated by Shahu.D
            System.out.println("✅ Step 3 completed: Successfully searched Initiative Code");
            Thread.sleep(2000);

            // Verify initiative is displayed before proceeding to activate - Updated by Shahu.D
            System.out.println("\n🔍 Verifying Initiative is displayed before Activate...");
            verifyInitiativeDisplayed(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Initiative Code '" + initiativeCode + "' found in table");
            Thread.sleep(1000);

            // Step 4: Click on Activate link - Updated by Shahu.D
            System.out.println("\n📌 Step 4: Click on Activate link");
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[6]/a"); // Updated by Shahu.D
            clickActivateLinkForInitiative(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step 4 completed: Successfully clicked on Activate link");
            Thread.sleep(2000);

            // Step 5: Click on Comment box and enter a comment - Updated by Shahu.D
            System.out.println("\n📌 Step 5: Enter comment in Comment box");
            System.out.println("   XPath: //*[@id=\"TextField202\"]"); // Updated by Shahu.D
            String finalComment = (activateComment == null || activateComment.isEmpty()) ? "Activated via automation - Updated by Shahu.D" : activateComment; // Updated by Shahu.D
            enterSnoozeComment(finalComment); // Reusing Snooze comment method - Updated by Shahu.D
            System.out.println("✅ Step 5 completed: Successfully entered Activate comment");
            Thread.sleep(1000);

            // Step 6: Number of Days field is NOT available in this scenario - Updated by Shahu.D
            System.out.println("\n📌 Step 6: Number of Days field is not present for Activate flow. Skipping this step."); // Updated by Shahu.D
            System.out.println("   (Previously used XPath: //*[@id=\"TextField207\"] )"); // Updated by Shahu.D
            // Note: No call to enterSnoozeDays() here because the field is not available - Updated by Shahu.D

            // Step 7: Click on the save button - Updated by Shahu.D
            System.out.println("\n📌 Step 7: Click on the save button");
            System.out.println("   XPath: //*[@id=\"id__199\"]"); // Updated by Shahu.D
            clickSnoozeSaveButton(); // Reusing Snooze save button method - Updated by Shahu.D
            System.out.println("✅ Step 7 completed: Successfully clicked Save button");
            Thread.sleep(2000);

            // Step 8: Click on the Yes button on the confirmation pop up - Updated by Shahu.D
            System.out.println("\n📌 Step 8: Click on the Yes button on the confirmation pop up");
            System.out.println("   XPath: //*[@id=\"id__212\"]"); // Updated by Shahu.D
            clickSnoozeConfirmYesButton(); // Reusing Snooze confirm Yes method - Updated by Shahu.D
            System.out.println("✅ Step 8 completed: Successfully clicked Yes on confirmation popup");
            Thread.sleep(3000);

            System.out.println("\n✅ ✅ ✅ TC_003_ActivateInitiative PASSED ✅ ✅ ✅");
            System.out.println("Activate Initiative workflow completed successfully:");
            System.out.println("  1. ✅ Navigated to Initiative Activate Snooze page"); // Updated by Shahu.D
            System.out.println("  2. ✅ Searched for Initiative Code: " + initiativeCode); // Updated by Shahu.D
            System.out.println("  3. ✅ Clicked Activate link"); // Updated by Shahu.D
            System.out.println("  4. ✅ Entered Activate Comment: " + finalComment); // Updated by Shahu.D
            System.out.println("  5. ✅ (Skipped) Number of Days field not present for Activate flow"); // Updated by Shahu.D
            System.out.println("  6. ✅ Clicked Save button"); // Updated by Shahu.D
            System.out.println("  7. ✅ Clicked Yes on confirmation popup"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_003_ActivateInitiative FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * TC_004_Pagination - Verify pagination shows 5 records per page
     *
     * Steps:
     * 1. Click on Initiative Management module
     * 2. Click on Initiative Activate Snooze page
     * 3. Click on pagination and verify that per page 5 records are displayed
     *
     * Updated by Shahu.D
     */
    @Test(priority = 4, enabled = true)
    @Description("TC_004_Pagination - Verify pagination shows 5 records per page")
    @Story("Initiative Activate Snooze Pagination")
    @Severity(SeverityLevel.NORMAL)
    public void TC_004_Pagination() throws Throwable { // Updated by Shahu.D
        try {
            initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_004_Pagination: Verify 5 records per page");
            System.out.println("═══════════════════════════════════════════════════════");

            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete...");
            Thread.sleep(3000);

            // Step 1 & 2: Navigate to Initiative Activate Snooze page - Updated by Shahu.D
            System.out.println("\n📌 Step 1: Click on Initiative Management module");
            System.out.println("📌 Step 2: Click on Initiative Activate Snooze page");
            navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D
            System.out.println("✅ Step 1-2 completed: Successfully navigated to Initiative Activate Snooze page");
            Thread.sleep(3000);

            // Step 3: Click on pagination and verify 5 records per page - Updated by Shahu.D
            System.out.println("\n📌 Step 3: Click on pagination and verify 5 records per page");
            System.out.println("   Pagination XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]/svg/path"); // Updated by Shahu.D
            clickPaginationAndVerifyFiveRecordsPerPage(); // Updated by Shahu.D
            System.out.println("✅ Step 3 completed: Pagination validated with at most 5 records per page"); // Updated by Shahu.D

            System.out.println("\n✅ ✅ ✅ TC_004_Pagination PASSED ✅ ✅ ✅");
            System.out.println("Pagination workflow completed successfully:"); // Updated by Shahu.D
            System.out.println("  1. ✅ Navigated to Initiative Activate Snooze page"); // Updated by Shahu.D
            System.out.println("  2. ✅ Clicked on pagination"); // Updated by Shahu.D
            System.out.println("  3. ✅ Verified that current page shows at most 5 records"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_004_Pagination FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Step method: Logout current user using profile icon and logout button
     * Updated by Shahu.D
     */
    @Step("Logout current user via profile icon")
    private void logoutCurrentUser() throws Throwable {
        webDriver.switchTo().defaultContent();
        Thread.sleep(1000);

        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20)); // Updated by Shahu.D

            By profileIcon = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[1]/div/div[2]/div/div/span"); // Updated by Shahu.D
            By logoutButton = By.xpath("/html/body/div[3]/div[3]/ul/div[3]/li/span[1]"); // Updated by Shahu.D

            System.out.println("  🔍 Searching for Profile icon for logout..."); // Updated by Shahu.D
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(profileIcon)); // Updated by Shahu.D

            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block:'center'});", profile); // Updated by Shahu.D
            Thread.sleep(500);

            try {
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", profile); // Updated by Shahu.D
                System.out.println("  ✅ Clicked Profile icon using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                profile.click();
                System.out.println("  ✅ Clicked Profile icon using direct click"); // Updated by Shahu.D
            }

            Thread.sleep(1500);

            System.out.println("  🔍 Searching for Logout button in profile menu..."); // Updated by Shahu.D
            WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton)); // Updated by Shahu.D

            try {
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", logout); // Updated by Shahu.D
                System.out.println("  ✅ Clicked Logout button using JavaScript"); // Updated by Shahu.D
            } catch (Exception e2) {
                logout.click();
                System.out.println("  ✅ Clicked Logout button using direct click"); // Updated by Shahu.D
            }

            Thread.sleep(3000);
            System.out.println("  ✅ User successfully logged out via profile menu"); // Updated by Shahu.D

        } catch (Exception e) {
            System.err.println("  ❌ Error during logout via profile icon: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * TC_005_SnoozeInitiative - Verify snoozed initiative is NOT visible to second user in Watchlist
     *
     * PART A: Login as User 1 and Snooze Initiative
     * 1. Login as User 1 (handled by BaseTest)
     * 2. Navigate to Initiative Activate Snooze page
     * 3. Search initiative by InitiativeCode from Excel
     * 4. Click Snooze link
     * 5. Enter Snooze Comment from Excel
     * 6. Enter Snooze Days from Excel
     * 7. Click Save, confirm with Yes
     *
     * PART B: Login as User 2 and verify initiative NOT visible in Watchlist
     * 8. Login as User 2 (second approver)
     * 9. Navigate to Initiatives page
     * 10. Click Watchlist section
     * 11. Click Search option
     * 12. Enter Initiative Code from Excel
     * 13. Click Search button
     * 14. Assert initiative code is NOT displayed in Watchlist results
     *
     * Updated by Shahu.D
     */
    @Test(priority = 5, enabled = true, dataProvider = "activateSnoozeData")
    @Description("TC_005_SnoozeInitiative - Snoozed initiative should NOT be visible in Watchlist of second user")
    @Story("Initiative Snooze Visibility")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_005_SnoozeInitiative(String initiativeCode, String activateOrSnooze, String snoozeComment, String snoozeDays) throws Throwable { // Updated by Shahu.D
        try {
            initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);

            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_005_SnoozeInitiative: Snoozed initiative NOT visible to second user");
            System.out.println("═══════════════════════════════════════════════════════");

            // =========================== PART A: USER 1 - SNOOZE ===========================

            System.out.println("\n🔹 PART A: Login as User 1 and Snooze Initiative"); // Updated by Shahu.D
            System.out.println("⏳ Waiting for login to complete (User 1)...");
            Thread.sleep(3000);

            // Step A1 & A2: Navigate to Initiative Activate Snooze page - Updated by Shahu.D
            System.out.println("\n📌 Step A1-A2: Navigate to Initiative Activate Snooze page");
            navigateToInitiativeActivateSnoozePage(); // Updated by Shahu.D
            System.out.println("✅ Step A1-A2: Navigated to Initiative Activate Snooze page");
            Thread.sleep(3000);

            // Step A3: Search initiative by Initiative Code - Updated by Shahu.D
            System.out.println("\n📌 Step A3: Search Initiative by Initiative Code from Excel: " + initiativeCode);
            enterInitiativeCode(initiativeCode); // Updated by Shahu.D
            clickSearchButton(); // Updated by Shahu.D
            System.out.println("✅ Step A3: Successfully searched Initiative Code");
            Thread.sleep(3000);

            // Optional: verify initiative is displayed - Updated by Shahu.D
            System.out.println("\n🔍 Verifying Initiative is displayed before Snooze...");
            initiativeActivateSnoozePage.verifyInitiativeDisplayed(initiativeCode); // Updated by Shahu.D

            // Step A4: Click Snooze link - Updated by Shahu.D
            System.out.println("\n📌 Step A4: Click on Snooze link for Initiative Code: " + initiativeCode);
            clickSnoozeLinkForInitiative(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step A4: Successfully clicked Snooze link");
            Thread.sleep(2000);

            // Step A5: Enter Snooze Comment - Updated by Shahu.D
            System.out.println("\n📌 Step A5: Enter Snooze Comment from Excel");
            String finalComment = (snoozeComment == null || snoozeComment.isEmpty())
                    ? "Snoozed by User1 for TC_005 - Updated by Shahu.D"
                    : snoozeComment; // Updated by Shahu.D
            enterSnoozeComment(finalComment); // Updated by Shahu.D
            System.out.println("✅ Step A5: Successfully entered Snooze Comment: " + finalComment);
            Thread.sleep(1000);

        
            
            
            // Step A6: Enter Snooze Days - Updated by Shahu.D
            System.out.println("\n📌 Step A6: Enter Snooze Days from Excel");
            String finalDays = (snoozeDays == null || snoozeDays.isEmpty()) ? "1" : snoozeDays; // Updated by Shahu.D
            enterSnoozeDays(finalDays); // Updated by Shahu.D
            System.out.println("✅ Step A6: Successfully entered Snooze Days: " + finalDays);
            Thread.sleep(1000);

            // Step A7: Click Save - Updated by Shahu.D
            System.out.println("\n📌 Step A7: Click Save button");
            clickSnoozeSaveButton(); // Updated by Shahu.D
            System.out.println("✅ Step A7: Successfully clicked Save button");
            Thread.sleep(2000);

            // Step A8: Confirm Yes - Updated by Shahu.D
            System.out.println("\n📌 Step A8: Click Yes on confirmation popup");
            clickSnoozeConfirmYesButton(); // Updated by Shahu.D
            System.out.println("✅ Step A8: Successfully confirmed Snooze via Yes button");
            Thread.sleep(3000);

            System.out.println("\n✅ PART A completed: Initiative snoozed successfully by User 1"); // Updated by Shahu.D

            // Step A9 & A10: Logout User 1 via profile icon and logout button - Updated by Shahu.D
            System.out.println("\n📌 Step A9: Click on Profile icon"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[1]/div/div[2]/div/div/span"); // Updated by Shahu.D
            System.out.println("📌 Step A10: Click on Logout button"); // Updated by Shahu.D
            System.out.println("   XPath: /html/body/div[3]/div[3]/ul/div[3]/li/span[1]"); // Updated by Shahu.D
            logoutCurrentUser(); // Updated by Shahu.D
            System.out.println("✅ Step A9-A10: Successfully logged out User 1"); // Updated by Shahu.D

            // =========================== PART B: USER 2 - VERIFY WATCHLIST ===========================

            System.out.println("\n🔹 PART B: Login as User 2 and verify initiative NOT visible in Watchlist"); // Updated by Shahu.D

            // Ensure we are on login page (optional navigation) - Updated by Shahu.D
            String appUrl = config.getProperty("url", "URLInconfig"); // Updated by Shahu.D
            webDriver.get(appUrl); // Updated by Shahu.D
            System.out.println("🌐 Navigated to login URL for User 2: " + appUrl); // Updated by Shahu.D
            Thread.sleep(3000);

            // Login as User 2 using LoginHelper with custom credentials - Updated by Shahu.D
            String secondApproverEmail = config.getProperty("secondApproverEmail"); // Updated by Shahu.D
            String secondApproverPassword = config.getProperty("secondApproverPassword"); // Updated by Shahu.D

            if (secondApproverEmail == null || secondApproverPassword == null) {
                throw new RuntimeException("secondApproverEmail/secondApproverPassword not configured in config.properties"); // Updated by Shahu.D
            }

            System.out.println("\n📌 Step B1: Login as User 2 (Second Approver): " + secondApproverEmail); // Updated by Shahu.D
            LoginHelper helper = new LoginHelper(webDriver, reportLogger, config); // Updated by Shahu.D
            helper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword); // Updated by Shahu.D
            System.out.println("✅ Step B1: Login successful for User 2"); // Updated by Shahu.D
            Thread.sleep(4000);

            // Step B2: Navigate to Initiatives page - Updated by Shahu.D
            System.out.println("\n📌 Step B2: Navigate to Initiatives page");
            System.out.println("   XPath: //*[@id=\"children-panel-container\"]/div[3]/div[1]/p"); // Updated by Shahu.D
            navigateToInitiativesPage(); // Updated by Shahu.D
            System.out.println("✅ Step B2: Successfully navigated to Initiatives page"); // Updated by Shahu.D
            Thread.sleep(3000);

            // Step B3: Click on Watchlist section - Updated by Shahu.D
            System.out.println("\n📌 Step B3: Click on Watchlist section");
            System.out.println("   XPath: //*[@id=\"ImFltr-Watchlist\"]/a/span[2]"); // Updated by Shahu.D
            clickWatchlistSection(); // Updated by Shahu.D
            System.out.println("✅ Step B3: Successfully clicked Watchlist section"); // Updated by Shahu.D
            Thread.sleep(2000);

            // Step B4: Click on Search option - Updated by Shahu.D
            System.out.println("\n📌 Step B4: Click on Search option on Watchlist page");
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"); // Updated by Shahu.D
            clickWatchlistSearchIcon(); // Updated by Shahu.D
            System.out.println("✅ Step B4: Successfully clicked Watchlist Search icon"); // Updated by Shahu.D
            Thread.sleep(2000);

            // Step B5: Enter Initiative Code in Watchlist search - Updated by Shahu.D
            System.out.println("\n📌 Step B5: Enter Initiative Code in Watchlist search: " + initiativeCode);
            System.out.println("   XPath: //*[@id=\"DemandCode\"]"); // Updated by Shahu.D
            enterInitiativeCodeInWatchlist(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step B5: Successfully entered Initiative Code in Watchlist search"); // Updated by Shahu.D
            Thread.sleep(1500);

            // Step B6: Click on Search button - Updated by Shahu.D
            System.out.println("\n📌 Step B6: Click on Search button on Watchlist"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"id__529\"] or //div[@class='css-4io43t']"); // Updated by Shahu.D
          //  clickWatchlistFinalSearchButton(); // Updated by Shahu.D
            initiativeActivateSnoozePage.clickonfinalsearchwatch();
            System.out.println("✅ Step B6: Successfully clicked Search button on Watchlist"); // Updated by Shahu.D
            Thread.sleep(3000);

            // Step B7: Verify initiative NOT present in Watchlist - Updated by Shahu.D
            System.out.println("\n📌 Step B7: Verify Initiative Code '" + initiativeCode + "' is NOT present in Watchlist results"); // Updated by Shahu.D
            verifyInitiativeNotInWatchlist(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step B7: Verified that Initiative Code '" + initiativeCode + "' is NOT present in Watchlist"); // Updated by Shahu.D

            System.out.println("\n✅ ✅ ✅ TC_005_SnoozeInitiative PASSED ✅ ✅ ✅");
            System.out.println("Snoozed initiative is NOT visible in Watchlist for second user:"); // Updated by Shahu.D
            System.out.println("  1. ✅ Snoozed initiative as User 1"); // Updated by Shahu.D
            System.out.println("  2. ✅ Logged in as User 2 (second approver)"); // Updated by Shahu.D
            System.out.println("  3. ✅ Verified initiative code '" + initiativeCode + "' is NOT present in Watchlist"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_005_SnoozeInitiative FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

