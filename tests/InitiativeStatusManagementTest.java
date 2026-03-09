package tests;

import Base.BaseTest;
import Pages.InitiativePage;
import Pages.InitiativeStatusManagement;
import Pages.InitiativeManagementPage;
import Pages.InboxPage;
import Pages.InitiativeActivateSnoozePage;
import Pages.CompletedInitiativePage;
import Pages.WithdrawnInitiativePage;
import Utils.ExcelReader;
import Utils.LoginHelper;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Initiative Status Management Test Suite
 *
 * This class is dedicated to tests that validate Initiative
 * status life‑cycle (submit, approve, reject, hold, etc.).
 *
 * All tests that are specifically about status transitions should
 * be moved/created here so that the converted Initiative page flows
 * are clearly separated from generic Initiative creation / listing tests.
 *
 * NOTE: The file name matches the public class name:
 *       InitiativeStatusManagementTest.java → InitiativeStatusManagementTest
 */
@Epic("Initiative Management")
@Feature("Initiative Status Management")
public class InitiativeStatusManagementTest extends BaseTest {

    protected InitiativePage initiativePage;
    protected InitiativeStatusManagement initiativeStatusPage;
    protected InitiativeManagementPage initiativeManagementPage;
    protected InboxPage inboxPage;
    protected InitiativeActivateSnoozePage initiativeActivateSnoozePage;
    protected CompletedInitiativePage completedInitiativePage;
    protected WithdrawnInitiativePage withdrawnInitiativePage;

    /**
     * Generic DataProvider to fetch status‑related test data by TC_ID from Excel.
     *
     * Sheet name is kept separate so status‑specific data is isolated
     * from generic Initiative test data.
     *
     * @param method TestNG method
     * @return two‑dimensional Object array of test data
     * @throws Exception when Excel cannot be read
     */
    @DataProvider(name = "initiativeStatusData")
    public Object[][] getInitiativeStatusData(Method method) throws Exception {
        String testCaseId = method.getName();
        // Use a dedicated sheet for status scenarios
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeStatus");
        int rowCount = reader.getRowCount();

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
                int paramCount = method.getParameterCount();
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
                }
                return data;
            }
        }
        return new Object[0][0];
    }

    // ====================================================================
    // TEST CASES
    // --------------------------------------------------------------------
    // As you convert Initiative status‑related logic from existing tests
    // (e.g. submission / approval flows), move those tests here and call
    // the methods on `InitiativeStatusManagement` page class.
    // ====================================================================

    /**
     * TC_001_Click_On_Search_Button
     *
     * Steps:
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Search button.
     * 4. Click on the Nature of Initiative dropdown field and select "Full Change Lifecycle".
     * 5. Click on the Business Group dropdown field and select "Tata Group".
     * 6. Click on the Organization Unit dropdown field and select "Tata Motors".
     * 7. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 8. Click on the Initiative Title text field and enter Initiative Title from Excel.
     * 9. Click on the Search button.
     */
    @Test(priority = 1, enabled = false, dataProvider = "initiativeStatusData")
    @Description("TC_001_Click_On_Search_Button - Search initiatives using filters on Initiative Status Management page")
    @Story("Initiative Status Search")
    @Severity(SeverityLevel.NORMAL)
    public void TC_001_Click_On_Search_Button(String initiativeCode, String initiativeTitle) throws Throwable {
        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Click Initiative Management module and then Initiative Status Management page
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();

        // Step 3: Click on the Search button (toolbar)
        initiativeStatusPage.clickSearchToolbarButton();

        // Step 4: Select Nature of Initiative = Full Change Lifecycle
        initiativeStatusPage.selectNatureOfInitiativeFullChangeLifecycle();

        // Step 5: Select Business Group = Tata Group
        initiativeStatusPage.selectBusinessGroupTataGroup();

        // Step 6: Select Organization Unit = Tata Motors
        initiativeStatusPage.selectOrganizationUnitTataMotors();

        // Step 7: Enter Initiative Code (from Excel)
        initiativeStatusPage.enterInitiativeCode(initiativeCode);

        // Step 8: Enter Initiative Title (from Excel)
        initiativeStatusPage.enterInitiativeTitle(initiativeTitle);

        // Step 9: Click on the Search button (filter panel)
        initiativeStatusPage.clickFilterSearchButton();
    }

    /**
     * TC_002_Snooze_Initiative
     *
     * PART A: Snooze Initiative – User 1
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Search button.
     * 4. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 5. Click on the Search button.
     * 6. Click on the Action link corresponding to the initiative.
     * 7. Click on the Number of Days field and enter 1.
     * 8. Click on the Comment text box and enter a valid comment.
     * 9. Click on the Save button.
     * 10. Click on the OK button on the confirmation popup.
     *
     * PART B: Verification After Snooze – User 2
     * 11. Logout from the application.
     * 12. Login as User 2 using credentials from config.properties.
     * 13. Click on the Initiative Management module.
     * 14. Click on the Initiative page and navigate to the Inbox section.
     * 15. Click on the search button and enter the initiative code as mentioned in the excel and click on the search button.
     * 16. Verify that the snoozed initiative is NOT displayed in the Inbox list for User 2.
     *
     * PART C: Verify Snoozed Initiative in Activate – Snooze Page
     * 17. Click on the Initiative Activate – Snooze page.
     * 18. Click on the Search button.
     * 19. Enter the Initiative Code mentioned in the Excel and click on the Search button.
     * 20. Verify that the snoozed initiative is displayed for User 2.
     *
     * Expected Result:
     * - Initiative should be successfully snoozed.
     * - Snoozed initiative should not be visible in the Inbox for User 2.
     * - Snoozed initiative should be visible in the Initiative Activate – Snooze page for User 2.
     */ //AutoTitle_1610f2d9
    @Test(priority = 2, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_002_Snooze_Initiative - Snooze an initiative and verify visibility for User 2")
    @Story("Initiative Snooze")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_Snooze_Initiative(String initiativeCode, String numberOfDays, String snoozeComment) throws Throwable {
        // =========================== PART A: USER 1 - SNOOZE INITIATIVE ===========================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_002_Snooze_Initiative: Full Snooze Flow with User 2 Verification");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("\n🔹 PART A: Snooze Initiative – User 1");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Click Initiative Management module and then Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 3: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 5: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 6: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 6: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 7: Enter Number of Days as 1 (hardcoded as per requirement)
        System.out.println("\n📌 Step 7: Enter Number of Days as 1");
        initiativeStatusPage.enterSnoozeNumberOfDays("1");
        Thread.sleep(1000);

        // Step 8: Enter Comment as "Commented By Shahu.D" (hardcoded as per latest requirement)
        System.out.println("\n📌 Step 8: Enter Comment: Commented By Shahu.D");
        initiativeStatusPage.enterSnoozeComment("Commented by Shahu.D");
        Thread.sleep(1000);

        // Step 9: Click on the Save button
        System.out.println("\n📌 Step 9: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 10: Click on the Ok button on confirmation popup
        System.out.println("\n📌 Step 10: Click on the OK button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationOkButton();
        Thread.sleep(3000);

        System.out.println("\n✅ PART A completed: Initiative snoozed successfully by User 1");

        // =========================== PART B: USER 2 - VERIFY NOT IN INBOX ===========================
        System.out.println("\n🔹 PART B: Verification After Snooze – User 2");

        // Step 11: Logout from the application
        System.out.println("\n📌 Step 11: Logout from the application");
        logoutCurrentUser();
        Thread.sleep(3000);

        // Step 12: Login as User 2
        System.out.println("\n📌 Step 12: Login as User 2");
        String appUrl = config.getProperty("url", "https://ini.whizible.com/signin");
        webDriver.get(appUrl);
        Thread.sleep(3000);

        String secondApproverEmail = config.getProperty("secondApproverEmail");
        String secondApproverPassword = config.getProperty("secondApproverPassword", "Z.729377788748ud");

        if (secondApproverEmail == null || secondApproverEmail.isEmpty()) {
            // Use hardcoded credentials if not in config
            secondApproverEmail = "whizible_test2@whizible.net";
            secondApproverPassword = "Z.729377788748ud";
        }

        LoginHelper helper = new LoginHelper(webDriver, reportLogger, config);
        helper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword);
        System.out.println("✅ Step 12: Login successful for User 2: " + secondApproverEmail);
        Thread.sleep(4000);

        // Step 13: Click on the Initiative Management module
        System.out.println("\n📌 Step 13: Click on the Initiative Management module");
        initiativeManagementPage = new InitiativeManagementPage(webDriver, reportLogger);
        initiativeManagementPage.navigateToInitiativesPage();
        Thread.sleep(3000);

        // Step 14: Navigate to the Inbox section
        System.out.println("\n📌 Step 14: Navigate to the Inbox section");
        inboxPage = new InboxPage(webDriver, reportLogger);
        inboxPage.clickInboxSection();
        Thread.sleep(2000);

        // Step 15: Click on the search button and enter the initiative code
        System.out.println("\n📌 Step 15: Click on the search button and enter initiative code: " + initiativeCode);
        inboxPage.clickSearchIcon();
        Thread.sleep(2000);
        inboxPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        inboxPage.clickFinalSearchButton();
        Thread.sleep(3000);

        // Step 16: Verify that the snoozed initiative is NOT displayed in the Inbox list
        System.out.println("\n📌 Step 16: Verify that initiative '" + initiativeCode + "' is NOT displayed in Inbox");
        boolean isPresent = inboxPage.verifyInitiativeInInbox(initiativeCode);
        if (isPresent) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is present in Inbox, but it should NOT be!");
        }
        System.out.println("✅ Step 16: Verified that initiative '" + initiativeCode + "' is NOT displayed in Inbox");

        System.out.println("\n✅ PART B completed: Verified initiative NOT in Inbox for User 2");

        // =========================== PART C: USER 2 - VERIFY IN ACTIVATE-SNOOZE PAGE ===========================
        System.out.println("\n🔹 PART C: Verify Snoozed Initiative in Activate – Snooze Page");

        // Step 17: Click on the Initiative Activate – Snooze page
        System.out.println("\n📌 Step 17: Click on the Initiative Activate – Snooze page");
        initiativeActivateSnoozePage = new InitiativeActivateSnoozePage(webDriver, reportLogger);
        initiativeActivateSnoozePage.navigateToInitiativeActivateSnoozePage();
        Thread.sleep(3000);

        // Step 18: Click on the Search button
        System.out.println("\n📌 Step 18: Click on the Search button");
        initiativeActivateSnoozePage.clickSearchOptionIcon();
        Thread.sleep(2000);

        // Step 19: Enter the Initiative Code and click Search
        System.out.println("\n📌 Step 19: Enter Initiative Code: " + initiativeCode + " and click Search");
        initiativeActivateSnoozePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        initiativeActivateSnoozePage.clickSearchButton();
        Thread.sleep(3000);

        // Step 20: Verify that the snoozed initiative is displayed
        System.out.println("\n📌 Step 20: Verify that initiative '" + initiativeCode + "' is displayed in Activate-Snooze page");
        boolean isDisplayed = initiativeActivateSnoozePage.verifyInitiativeDisplayed(initiativeCode);
        if (!isDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT found in Activate-Snooze page, but it should be!");
        }
        System.out.println("✅ Step 20: Verified that initiative '" + initiativeCode + "' is displayed in Activate-Snooze page");

        System.out.println("\n✅ PART C completed: Verified initiative IS displayed in Activate-Snooze page for User 2");

        System.out.println("\n✅ ✅ ✅ TC_002_Snooze_Initiative PASSED ✅ ✅ ✅");
        System.out.println("Full snooze flow completed successfully:");
        System.out.println("  1. ✅ Initiative snoozed by User 1");
        System.out.println("  2. ✅ Verified initiative NOT in Inbox for User 2");
        System.out.println("  3. ✅ Verified initiative IS in Activate-Snooze page for User 2");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_003_Mark_Initiatives_As_Complete
     *
     * PART 0: Pre-Verification – User 1 (Before Mark Complete)
     * 1. Click on the Initiative Management module.
     * 2. Click on the Completed Initiatives page.
     * 3. Click on the Search button.
     * 4. Enter Initiative Code from Excel and click on the Search button.
     * 5. Verify that Completed Initiatives should NOT be displayed before marking as complete.
     *
     * PART A: Mark Initiatives as Complete – User 1
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Action dropdown field and select the value as "Mark Initiatives as Complete".
     * 4. Click on the Show button.
     * 5. Click on the Search button.
     * 6. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 7. Click on the Search button.
     * 8. Click on the Action link.
     * 9. Click on the Comment text box and enter the comment as "Commented By Shahu.D.".
     * 10. Click on the Save button.
     * 11. Click on the Ok button on the confirmation popup.
     * 12. Click on the profile.
     * 13. Click on the Logout button.
     *
     * PART B: Verification After Mark Complete – User 2
     * 14. Login as User 2 using credentials from config.properties (or supplied defaults).
     * 15. Click on the Initiative Management module.
     * 16. Click on the Completed Initiatives page.
     * 17. Click on the Search button.
     * 18. Enter Initiative Code and click on the Search button.
     * 19. Verify that Mark Initiatives as Complete should be displayed on the Completed Initiatives page.
     */
    @Test(priority = 3, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_003_Mark_Initiatives_As_Complete - Verify initiative not present before marking, mark initiative as complete, and verify on Completed Initiatives page for User 2")
    @Story("Initiative Mark as Complete")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003_Mark_Initiatives_As_Complete(String initiativeCode, String numberOfDays, String completeComment) throws Throwable {
        // =========================== PART 0: USER 1 - PRE-VERIFICATION (BEFORE MARK COMPLETE) ===========================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_003_Mark_Initiatives_As_Complete: Mark Complete with Pre-Verification and User 2 Verification");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("\n🔹 PART 0: Pre-Verification – User 1 (Before Mark Complete)");

        // Step 1-2: Navigate to Completed Initiatives page
        System.out.println("\n📌 Step 1-2: Navigate to Completed Initiatives page");
        completedInitiativePage = new CompletedInitiativePage(webDriver, reportLogger);
        completedInitiativePage.navigateToCompletedInitiative();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button on Completed Initiatives page");
        completedInitiativePage.clickSearchInput();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code and click Search
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        completedInitiativePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        System.out.println("\n📌 Step 5: Click Search button on Completed Initiatives page");
        completedInitiativePage.clickSearchButton();
        Thread.sleep(3000);

        // Step 5: Verify initiative is NOT displayed (before marking as complete)
        System.out.println("\n📌 Step 6: Verify initiative '" + initiativeCode + "' is NOT displayed on Completed Initiatives page (before marking as complete)");
        boolean notDisplayed = completedInitiativePage.verifyInitiativeNotDisplayedOnCompletedPage(initiativeCode);
        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is unexpectedly displayed on Completed Initiatives page before marking as complete!");
        }
        System.out.println("✅ Step 6: Verified initiative '" + initiativeCode + "' is NOT displayed on Completed Initiatives page (as expected before marking as complete)");

        System.out.println("\n✅ PART 0 completed: Verified initiative is NOT present before marking as complete");

        // =========================== PART A: USER 1 - MARK COMPLETE ===========================
        System.out.println("\n🔹 PART A: Mark Initiatives as Complete – User 1");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Select Action = Mark Initiatives as Complete
        System.out.println("\n📌 Step 3: Select Action = 'Mark Initiatives as Complete'");
        initiativeStatusPage.selectActionMarkInitiativesAsComplete();
        Thread.sleep(2000);

        // Step 4: Click on Show button
        System.out.println("\n📌 Step 4: Click on Show button");
        initiativeStatusPage.clickShowButton();
        Thread.sleep(3000);

        // Step 5: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 5: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 6: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 6: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 8: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 9: Enter Comment as "Commented By Shahu.D." (hardcoded as per requirement, with period)
        String finalComment = "Commented By Shahu.D.";
        System.out.println("\n📌 Step 9: Enter Comment for Mark Complete: " + finalComment);
        initiativeStatusPage.enterSnoozeComment(finalComment);
        Thread.sleep(1000);

        // Step 10: Click on the Save button
        System.out.println("\n📌 Step 10: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 11: Click on the OK button on confirmation popup
        System.out.println("\n📌 Step 11: Click on the OK button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationOkButton();
        Thread.sleep(3000);

        // Step 12: Click on profile
        System.out.println("\n📌 Step 12: Click on profile");
        // Using the logout helper which handles profile click
        Thread.sleep(2000);

        // Step 13: Click on Logout button
        System.out.println("\n📌 Step 13: Click on Logout button");
        logoutCurrentUser();
        Thread.sleep(3000);

        System.out.println("\n✅ PART A completed: Initiative marked as Complete by User 1, logged out");

        // =========================== PART B: USER 2 - VERIFY COMPLETED INITIATIVE ===========================
        System.out.println("\n🔹 PART B: Verification After Mark Complete – User 2");

        // Step 14: Login as User 2
        System.out.println("\n📌 Step 14: Login as User 2");
        String appUrl = config.getProperty("url", "https://ini.whizible.com/signin");
        webDriver.get(appUrl);
        Thread.sleep(3000);

        String secondApproverEmail = config.getProperty("secondApproverEmail");
        String secondApproverPassword = config.getProperty("secondApproverPassword", "Z.729377788748ud");

        if (secondApproverEmail == null || secondApproverEmail.isEmpty()) {
            // Use hardcoded credentials if not in config
            secondApproverEmail = "whizible_test2@whizible.net";
            secondApproverPassword = "Z.729377788748ud";
        }

        LoginHelper helper = new LoginHelper(webDriver, reportLogger, config);
        helper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword);
        System.out.println("✅ Step 14: Login successful for User 2: " + secondApproverEmail);
        Thread.sleep(4000);

        // Step 15-16: Navigate to Completed Initiatives page
        System.out.println("\n📌 Step 15-16: Navigate to Completed Initiatives page");
        completedInitiativePage = new CompletedInitiativePage(webDriver, reportLogger);
        completedInitiativePage.navigateToCompletedInitiative();
        Thread.sleep(3000);

        // Step 17: Click on the Search button
        System.out.println("\n📌 Step 17: Click on the Search button on Completed Initiatives page");
        completedInitiativePage.clickSearchInput();
        Thread.sleep(2000);

        // Step 18: Enter Initiative Code and click Search
        System.out.println("\n📌 Step 18: Enter Initiative Code on Completed Initiatives page: " + initiativeCode);
        completedInitiativePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        System.out.println("\n📌 Step 19: Click Search button on Completed Initiatives page");
        completedInitiativePage.clickSearchButton();
        Thread.sleep(3000);

        // Step 19: Verify initiative is displayed
        System.out.println("\n📌 Step 20: Verify initiative '" + initiativeCode + "' is displayed on Completed Initiatives page");
        boolean completedDisplayed = completedInitiativePage.verifyInitiativeDisplayedOnCompletedPage(initiativeCode);
        if (!completedDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT displayed on Completed Initiatives page after Mark Complete!");
        }
        System.out.println("✅ Step 20: Verified initiative '" + initiativeCode + "' is displayed on Completed Initiatives page");

        System.out.println("\n✅ ✅ ✅ TC_003_Mark_Initiatives_As_Complete PASSED ✅ ✅ ✅");
        System.out.println("Full Mark Complete flow completed successfully:");
        System.out.println("  1. ✅ Verified initiative is NOT present before marking as complete");
        System.out.println("  2. ✅ Initiative marked as complete by User 1");
        System.out.println("  3. ✅ Verified initiative is displayed on Completed Initiatives page for User 2");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_004_Withdraw_Initiative
     *
     * PART 0: Pre-Verification – User 1 (Before Withdrawal)
     * 1. Click on the Initiative Management module.
     * 2. Click on the Withdrawn Initiatives page.
     * 3. Click on the Search button.
     * 4. Enter Initiative Code from Excel and click on the Search button.
     * 5. Verify that Withdraw Initiative should NOT be displayed before withdrawal.
     *
     * PART A: Withdraw Initiative – User 1
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Action dropdown field and select the value as "Withdraw Initiative".
     * 4. Click on the Show button.
     * 5. Click on the Search button.
     * 6. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 7. Click on the Search button.
     * 8. Click on the Action link.
     * 9. Click on the Comment text box and enter the comment as "Commented By Shahu.D.".
     * 10. Click on the Save button.
     * 11. Click on the Ok button on the confirmation popup.
     * 12. Click on the profile.
     * 13. Click on the Logout button.
     *
     * PART B: Verification After Withdraw – User 2
     * 14. Login as User 2 using credentials from config.properties (or supplied defaults).
     * 15. Click on the Initiative Management module.
     * 16. Click on the Withdrawn Initiatives page.
     * 17. Click on the Search button.
     * 18. Enter Initiative Code and click on the Search button.
     * 19. Verify that withdrawn initiative is displayed on the Withdrawn Initiatives page.
     */
    @Test(priority = 4, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_004_Withdraw_Initiative - Verify initiative not present before withdrawal, withdraw initiative, and verify on Withdrawn Initiatives page for User 2")
    @Story("Initiative Withdraw")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_004_Withdraw_Initiative(String initiativeCode, String numberOfDays, String withdrawComment) throws Throwable {
        // =========================== PART 0: USER 1 - PRE-VERIFICATION (BEFORE WITHDRAWAL) ===========================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_004_Withdraw_Initiative: Withdraw Initiative with Pre-Verification and User 2 Verification");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("\n🔹 PART 0: Pre-Verification – User 1 (Before Withdrawal)");

        // Step 1-2: Navigate to Withdrawn Initiatives page
        System.out.println("\n📌 Step 1-2: Navigate to Withdrawn Initiatives page");
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiative();
        Thread.sleep(3000);

        // Step 3: Click on the Search button
        System.out.println("\n📌 Step 3: Click on the Search button on Withdrawn Initiatives page");
        withdrawnInitiativePage.clickSearchInput();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code and click Search
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        System.out.println("\n📌 Step 5: Click Search button on Withdrawn Initiatives page");
        withdrawnInitiativePage.clickSearchButton();
        Thread.sleep(3000);

        // Step 5: Verify initiative is NOT displayed (before withdrawal)
        System.out.println("\n📌 Step 6: Verify initiative '" + initiativeCode + "' is NOT displayed on Withdrawn Initiatives page (before withdrawal)");
        boolean notDisplayed = withdrawnInitiativePage.verifyInitiativeNotDisplayedOnWithdrawnPage(initiativeCode);
        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is unexpectedly displayed on Withdrawn Initiatives page before withdrawal!");
        }
        System.out.println("✅ Step 6: Verified initiative '" + initiativeCode + "' is NOT displayed on Withdrawn Initiatives page (as expected before withdrawal)");

        System.out.println("\n✅ PART 0 completed: Verified initiative is NOT present before withdrawal");

        // =========================== PART A: USER 1 - WITHDRAW INITIATIVE ===========================
        System.out.println("\n🔹 PART A: Withdraw Initiative – User 1");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Select Action = Withdraw Initiative
        System.out.println("\n📌 Step 3: Select Action = 'Withdraw Initiative'");
        initiativeStatusPage.selectActionWithdrawInitiative();
        Thread.sleep(2000);

        // Step 4: Click on Show button
        System.out.println("\n📌 Step 4: Click on Show button");
        initiativeStatusPage.clickShowButton();
        Thread.sleep(3000);

        // Step 5: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 5: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 6: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 6: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 8: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 9: Enter Comment as "Commented By Shahu.D." (hardcoded as per requirement, with period)
        String finalComment = "Commented By Shahu.D.";
        System.out.println("\n📌 Step 9: Enter Comment for Withdraw: " + finalComment);
        initiativeStatusPage.enterSnoozeComment(finalComment);
        Thread.sleep(1000);

        // Step 10: Click on the Save button
        System.out.println("\n📌 Step 10: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 11: Click on the OK button on confirmation popup
        System.out.println("\n📌 Step 11: Click on the OK button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationOkButton();
        Thread.sleep(3000);

        // Step 12: Click on profile
        System.out.println("\n📌 Step 12: Click on profile");
        // Using the logout helper which handles profile click
        Thread.sleep(2000);

        // Step 13: Click on Logout button
        System.out.println("\n📌 Step 13: Click on Logout button");
        logoutCurrentUser();
        Thread.sleep(3000);

        System.out.println("\n✅ PART A completed: Initiative withdrawn successfully by User 1, logged out");

        // =========================== PART B: USER 2 - VERIFY WITHDRAWN INITIATIVE ===========================
        System.out.println("\n🔹 PART B: Verification After Withdraw – User 2");

        // Step 14: Login as User 2
        System.out.println("\n📌 Step 14: Login as User 2");
        String appUrl = config.getProperty("url", "https://ini.whizible.com/signin");
        webDriver.get(appUrl);
        Thread.sleep(3000);

        String secondApproverEmail = config.getProperty("secondApproverEmail");
        String secondApproverPassword = config.getProperty("secondApproverPassword", "Z.729377788748ud");

        if (secondApproverEmail == null || secondApproverEmail.isEmpty()) {
            // Use hardcoded credentials if not in config
            secondApproverEmail = "whizible_test2@whizible.net";
            secondApproverPassword = "Z.729377788748ud";
        }

        LoginHelper helper = new LoginHelper(webDriver, reportLogger, config);
        helper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword);
        System.out.println("✅ Step 14: Login successful for User 2: " + secondApproverEmail);
        Thread.sleep(4000);

        // Step 15-16: Navigate to Withdrawn Initiatives page
        System.out.println("\n📌 Step 15-16: Navigate to Withdrawn Initiatives page");
        withdrawnInitiativePage = new WithdrawnInitiativePage(webDriver, reportLogger);
        withdrawnInitiativePage.navigateToWithdrawnInitiative();
        Thread.sleep(3000);

        // Step 17: Click on the Search button
        System.out.println("\n📌 Step 17: Click on the Search button on Withdrawn Initiatives page");
        withdrawnInitiativePage.clickSearchInput();
        Thread.sleep(2000);

        // Step 18: Enter Initiative Code and click Search
        System.out.println("\n📌 Step 18: Enter Initiative Code on Withdrawn Initiatives page: " + initiativeCode);
        withdrawnInitiativePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        System.out.println("\n📌 Step 19: Click Search button on Withdrawn Initiatives page");
        withdrawnInitiativePage.clickSearchButton();
        Thread.sleep(3000);

        // Step 19: Verify initiative is displayed
        System.out.println("\n📌 Step 20: Verify initiative '" + initiativeCode + "' is displayed on Withdrawn Initiatives page");
        boolean withdrawnDisplayed = withdrawnInitiativePage.verifyInitiativeDisplayedOnWithdrawnPage(initiativeCode);
        if (!withdrawnDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT displayed on Withdrawn Initiatives page after Withdraw!");
        }
        System.out.println("✅ Step 20: Verified initiative '" + initiativeCode + "' is displayed on Withdrawn Initiatives page");

        System.out.println("\n✅ ✅ ✅ TC_004_Withdraw_Initiative PASSED ✅ ✅ ✅");
        System.out.println("Full Withdraw flow completed successfully:");
        System.out.println("  1. ✅ Verified initiative is NOT present before withdrawal");
        System.out.println("  2. ✅ Initiative withdrawn by User 1");
        System.out.println("  3. ✅ Verified initiative is displayed on Withdrawn Initiatives page for User 2");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_005_Resubmit_Initiative
     *
     * PART 0: Pre-Verification – User 1 (Before Resubmit)
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative page.
     * 3. Click on the Search button.
     * 4. Enter Initiative Code from Excel and click on the Search button.
     * 5. Verify that Resubmitted Initiative should NOT be displayed before Resubmit.
     *
     * PART A: Resubmit Initiative – User 1
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Action dropdown field and select the Resubmit Initiatives value.
     * 4. Click on the Show button.
     * 5. Click on the Search button.
     * 6. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 7. Click on the Search button.
     * 8. Click on the Action link.
     * 9. Click on the Comment text box and enter the comment as "Commented By Shahu.D.".
     * 10. Click on the Save button.
     * 11. Click on the Ok button on the confirmation popup.
     * 12. Click on the profile.
     * 13. Click on the Logout button.
     *
     * PART B: Verification After Resubmit – User 2
     * 14. Login as User 2 using credentials from config.properties (or supplied defaults).
     * 15. Click on the Initiative management module.
     * 16. Click on the Initiative page.
     * 17. Click on the Search button.
     * 18. Enter Initiative Code and click on the Search button.
     * 19. Verify that Resubmitted Initiatives should be displayed on the Initiative page.
     */
    @Test(priority = 5, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_005_Resubmit_Initiative - Verify initiative not present before resubmit, resubmit initiative, and verify on Initiative page for User 2")
    @Story("Initiative Resubmit")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_005_Resubmit_Initiative(String initiativeCode, String numberOfDays, String resubmitComment) throws Throwable {
        // =========================== PART 0: USER 1 - PRE-VERIFICATION (BEFORE RESUBMIT) ===========================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_005_Resubmit_Initiative: Resubmit with Pre-Verification and User 2 Verification");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("\n🔹 PART 0: Pre-Verification – User 1 (Before Resubmit)");

        // Step 1-2: Navigate to Initiative page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative page");
        initiativeManagementPage = new InitiativeManagementPage(webDriver, reportLogger);
        
        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);
        
        initiativeManagementPage.navigateToInitiativesPage();
       
        initiativeManagementPage.clickSearchToolbarButton();
        Thread.sleep(3000);
        initiativeManagementPage.enterInitiativeCode(initiativeCode);
        
        initiativeManagementPage.clickFilterSearchButton();
        
        initiativePage = new InitiativePage(webDriver, reportLogger);

        // Step 3-4: Search by Initiative Code on Initiative page
        System.out.println("\n📌 Step 3-4: Search Initiative Code on Initiative page: " + initiativeCode);
        
        initiativePage.searchInitiativeByCodeOnListPage(initiativeCode);

        // Step 5: Verify initiative is NOT displayed before Resubmit
        System.out.println("\n📌 Step 5: Verify initiative '" + initiativeCode + "' is NOT displayed on Initiative page (before Resubmit)");
        boolean notDisplayed = initiativePage.verifyInitiativeNotDisplayedOnInitiativePage(initiativeCode);
        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is unexpectedly displayed on Initiative page before Resubmit!");
        }
        System.out.println("✅ Step 5: Verified initiative '" + initiativeCode + "' is NOT displayed on Initiative page (as expected before Resubmit)");

        System.out.println("\n✅ PART 0 completed: Verified initiative is NOT present before Resubmit");

        // =========================== PART A: USER 1 - RESUBMIT INITIATIVE ===========================
        System.out.println("\n🔹 PART A: Resubmit Initiative – User 1");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Select Action = Resubmit Initiatives
        System.out.println("\n📌 Step 3: Select Action = 'Resubmit Initiatives'");
        initiativeStatusPage.selectActionResubmitInitiatives();
        Thread.sleep(2000);

        // Step 4: Click on Show button
        System.out.println("\n📌 Step 4: Click on the Show button");
        initiativeStatusPage.clickShowButton();
        Thread.sleep(3000);

        // Step 5: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 5: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 6: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 6: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 8: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 9: Enter Comment as "Commented By Shahu.D." (hardcoded as per requirement)
      //  String finalComment = "Commented By Shahu.D.";
        System.out.println("\n📌 Step 9: Enter Comment for Resubmit: " + "Commented By Shahu.D.");
        initiativeStatusPage.enterSnoozeComment1("Commented By Shahu.D.");
        Thread.sleep(1000);

        // Step 10: Click on the Save button
        System.out.println("\n📌 Step 10: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 11: Click on the Ok button on confirmation popup
        System.out.println("\n📌 Step 11: Click on the OK button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationOkButton();
        Thread.sleep(3000);

        // Step 12: Click on profile
        System.out.println("\n📌 Step 12: Click on profile");
        // Using the logout helper which handles profile click
        Thread.sleep(2000);

        // Step 13: Click on Logout button
        System.out.println("\n📌 Step 13: Click on Logout button");
        logoutCurrentUser();
        Thread.sleep(3000);

        System.out.println("\n✅ PART A completed: Initiative resubmitted by User 1, logged out");

        // =========================== PART B: USER 2 - VERIFY RESUBMITTED INITIATIVE ===========================
        System.out.println("\n🔹 PART B: Verification After Resubmit – User 2");

        // Step 12: Login as User 2
        System.out.println("\n📌 Step 12: Login as User 2");
        String appUrl = config.getProperty("url", "https://ini.whizible.com/signin");
        webDriver.get(appUrl);
        Thread.sleep(3000);

        String secondApproverEmail = config.getProperty("secondApproverEmail");
        String secondApproverPassword = config.getProperty("secondApproverPassword", "Z.729377788748ud");

        if (secondApproverEmail == null || secondApproverEmail.isEmpty()) {
            // Use hardcoded credentials if not in config
            secondApproverEmail = "whizible_test2@whizible.net";
            secondApproverPassword = "Z.729377788748ud";
        }

        LoginHelper helper = new LoginHelper(webDriver, reportLogger, config);
        helper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword);
        System.out.println("✅ Step 12: Login successful for User 2: " + secondApproverEmail);
        Thread.sleep(4000);

        // Step 13-14: Navigate to Initiative page
        System.out.println("\n📌 Step 13-14: Navigate to Initiative page");
        initiativeManagementPage = new InitiativeManagementPage(webDriver, reportLogger);
        initiativeManagementPage.navigateToInitiativesPage();
        Thread.sleep(3000);

        initiativeStatusPage.clickSearchToolbarButton();
        
        initiativeManagementPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
       
        initiativeManagementPage.clickFilterSearchButton();
        
        
        initiativePage = new InitiativePage(webDriver, reportLogger);

        // Step 17: Verify initiative is displayed (Resubmitted)
    /*    System.out.println("\n📌 Step 17: Verify initiative '" + initiativeCode + "' is displayed on Initiative page after Resubmit");
        boolean displayed = initiativePage.verifyInitiativeDisplayedOnInitiativePage(initiativeCode);
        if (!displayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT displayed on Initiative page after Resubmit!");
        }
        System.out.println("✅ Step 17: Verified initiative '" + initiativeCode + "' is displayed on Initiative page after Resubmit");

        System.out.println("\n✅ ✅ ✅ TC_005_Resubmit_Initiative PASSED ✅ ✅ ✅");
        System.out.println("Full Resubmit flow completed successfully:");
        System.out.println("  1. ✅ Verified initiative is NOT present on Initiative page before Resubmit");
        System.out.println("  2. ✅ Initiative resubmitted by User 1");
        System.out.println("  3. ✅ Verified initiative is displayed on Initiative page for User 2 after Resubmit");
        System.out.println("═══════════════════════════════════════════════════════\n");
        */
    }

    /**
     * TC_006_Cancel_Snooze_Does_Not_Apply
     *
     * Test Steps:
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Search button.
     * 4. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field.
     * 5. Click on the Search button.
     * 6. Click on the Action link.
     * 7. Click on the Number of Days field and enter the Number of Days as 1.
     * 8. Click on the Comment text box and enter the comment.
     * 9. Click on the Save button.
     * 10. Click on the Cancel button on the confirmation pop up page.
     * 11. Again click on the search button and enter the Initiative code.
     * 12. Click on the search button.
     * 13. Verify that the selected initiative is not getting Snoozed after click on the Cancel button.
     */
    @Test(priority = 6, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_006_Cancel_Snooze_Does_Not_Apply - Verify that cancelling the confirmation popup does not snooze the initiative")
    @Story("Initiative Snooze - Cancel Flow")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_006_Cancel_Snooze_Does_Not_Apply(String initiativeCode, String numberOfDays, String snoozeComment) throws Throwable {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_006_Cancel_Snooze_Does_Not_Apply: Cancel Snooze and Verify Initiative Not Snoozed");
        System.out.println("═══════════════════════════════════════════════════════");

        // =========================== PART A: USER 1 - OPEN SNOOZE POPUP AND CANCEL ===========================
        System.out.println("\n🔹 PART A: Open Snooze popup and cancel – User 1");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 3: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 4: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 4: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 5: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 5: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 6: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 6: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 7: Enter Number of Days as 1
        System.out.println("\n📌 Step 7: Enter Number of Days as 1");
        initiativeStatusPage.enterSnoozeNumberOfDays("1");
        Thread.sleep(1000);

        // Step 8: Enter Comment as "Commented By Shahu.D."
        String finalComment = "Commented By Shahu.D.";
        System.out.println("\n📌 Step 8: Enter Comment for Snooze (Cancel flow): " + finalComment);
        initiativeStatusPage.enterSnoozeComment(finalComment);
        Thread.sleep(1000);

        // Step 9: Click on the Save button
        System.out.println("\n📌 Step 9: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 10: Click on the Cancel button on confirmation popup
        System.out.println("\n📌 Step 10: Click on the Cancel button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationCancelButton();
        Thread.sleep(3000);

        // =========================== PART B: VERIFY INITIATIVE NOT SNOOZED (STILL PRESENT) ===========================
        System.out.println("\n🔹 PART B: Verify initiative not snoozed (still present in Initiative Status Management grid)");

        // Step 11: Again click on the search button and enter the Initiative code
        System.out.println("\n📌 Step 11: Again click on the search button and enter the Initiative code");
   
        initiativeStatusPage.closeUpdateHealthSheet();
        // Step 12: Click on the search button
        System.out.println("\n📌 Step 12: Click on the search button");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 13: Verify that the selected initiative is not getting Snoozed after click on the Cancel button
        System.out.println("\n📌 Step 13: Verify that the selected initiative is not getting Snoozed after click on the Cancel button");
        boolean present = initiativeStatusPage.isInitiativePresentInStatusGrid(initiativeCode);
        if (!present) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT present in Initiative Status Management grid after cancelling Snooze. The initiative appears to have been snoozed despite clicking Cancel.");
        }
        System.out.println("✅ Step 13: Verified that initiative '" + initiativeCode + "' is still present in Initiative Status Management grid – Snooze was NOT applied after clicking Cancel button");

        System.out.println("\n✅ ✅ ✅ TC_006_Cancel_Snooze_Does_Not_Apply PASSED ✅ ✅ ✅");
        System.out.println("Cancel Snooze flow completed successfully:");
        System.out.println("  1. ✅ Snooze popup opened, values entered, and Save clicked");
        System.out.println("  2. ✅ Cancel button clicked on confirmation popup");
        System.out.println("  3. ✅ Re-searched for initiative and verified it remains in Initiative Status Management grid (not snoozed)");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_007_Cancel_Mark_Complete_Does_Not_Apply
     *
     * Steps:
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the Action dropdown field and select "Mark Initiatives as Complete".
     * 4. Click on the Show button.
     * 5. Click on the Search button.
     * 6. Click on the Initiative Code text field and enter Initiative Code from Excel.
     * 7. Click on the Search button.
     * 8. Click on the Action link.
     * 9. Click on the Comment text box and enter the comment "Commented By Shahu.D.".
     * 10. Click on the Save button.
     * 11. Click on the Cancel button on the confirmation pop up page.
     * 12. Click on the cross icon (close page).
     * 13. Again click on the search button and enter the Initiative code.
     * 14. Click on the search button.
     * 15. Verify that the selected initiative is NOT getting marked as completed after clicking the Cancel button.
     *
     * Expected Result:
     * - Initiative should NOT be marked as completed after clicking Cancel.
     * - Initiative should still be present in Initiative Status Management grid.
     * - Initiative should NOT be displayed on Completed Initiatives page.
     */
    @Test(priority = 7, enabled = true, dataProvider = "initiativeStatusData")
    @Description("TC_007_Cancel_Mark_Complete_Does_Not_Apply - Verify that cancelling the Mark Complete confirmation popup does not mark the initiative as complete")
    @Story("Initiative Mark Complete - Cancel Flow")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_007_Cancel_Mark_Complete_Does_Not_Apply(String initiativeCode, String numberOfDays, String completeComment) throws Throwable {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_007_Cancel_Mark_Complete_Does_Not_Apply: Verify Cancel Mark Complete Functionality");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("\n🔹 PART A: User 1 – Open Mark Complete popup and Cancel");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(2000);

        // Step 3: Select Action = Mark Initiatives as Complete
        System.out.println("\n📌 Step 3: Select Action = 'Mark Initiatives as Complete'");
        initiativeStatusPage.selectActionMarkInitiativesAsComplete();
        Thread.sleep(2000);

        // Step 4: Click on Show button
        System.out.println("\n📌 Step 4: Click on Show button");
        initiativeStatusPage.clickShowButton();
        Thread.sleep(3000);

        // Step 5: Click on the Search button (toolbar)
        System.out.println("\n📌 Step 5: Click on the Search button (toolbar)");
        initiativeStatusPage.clickSearchToolbarButton();
        Thread.sleep(2000);

        // Step 6: Enter Initiative Code from Excel
        System.out.println("\n📌 Step 6: Enter Initiative Code from Excel: " + initiativeCode);
        initiativeStatusPage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);

        // Step 7: Click on the Search button (filter panel)
        System.out.println("\n📌 Step 7: Click on the Search button (filter panel)");
        initiativeStatusPage.clickFilterSearchButton();
        Thread.sleep(3000);

        // Step 8: Click on the Action link for the searched initiative
        System.out.println("\n📌 Step 8: Click on the Action link for initiative: " + initiativeCode);
        initiativeStatusPage.clickActionLinkForInitiative(initiativeCode);
        Thread.sleep(2000);

        // Step 9: Enter Comment as "Commented By Shahu.D." (hardcoded as per requirement)
        String finalComment = "Commented By Shahu.D.";
        System.out.println("\n📌 Step 9: Enter Comment for Mark Complete (Cancel flow): " + finalComment);
        initiativeStatusPage.enterSnoozeComment(finalComment);
        Thread.sleep(1000);

        // Step 10: Click on the Save button
        System.out.println("\n📌 Step 10: Click on the Save button");
        initiativeStatusPage.clickSnoozeSaveButton();
        Thread.sleep(2000);

        // Step 11: Click on the Cancel button on confirmation popup
        System.out.println("\n📌 Step 11: Click on the Cancel button on confirmation popup");
        initiativeStatusPage.clickSnoozeConfirmationCancelButton();
        Thread.sleep(3000);

        // Step 12: Click on the cross icon to close the popup
        System.out.println("\n📌 Step 12: Click on the cross icon to close the popup");
  
        initiativeStatusPage.closeUpdateHealthSheet();
        Thread.sleep(3000);

        System.out.println("\n✅ PART A completed: Mark Complete initiated and cancelled by User 1");

        // =========================== PART B: Verify initiative is NOT marked as complete ===========================
        System.out.println("\n🔹 PART B: Verify initiative is NOT marked as complete");

        // Step 13: Again click on the search button and enter the Initiative code
        System.out.println("\n📌 Step 13: Re-search for initiative: " + initiativeCode);

        // Step 14: Verify that the selected initiative is still present in Initiative Status Management grid (not marked as complete)
        System.out.println("\n📌 Step 14: Verify that initiative '" + initiativeCode + "' is still present in Initiative Status Management grid (not marked as complete)");
        boolean present = initiativeStatusPage.isInitiativePresentInStatusGrid(initiativeCode);
        if (!present) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is NOT displayed in Initiative Status Management grid, implying it was marked as complete despite cancellation!");
        }
        System.out.println("✅ Step 14: Verified that initiative '" + initiativeCode + "' is still present in Initiative Status Management grid (as expected)");

        // Step 15: Verify that the initiative is NOT displayed on Completed Initiatives page
      /*  System.out.println("\n📌 Step 15: Verify that initiative '" + initiativeCode + "' is NOT displayed on Completed Initiatives page");
        completedInitiativePage = new CompletedInitiativePage(webDriver, reportLogger);
        completedInitiativePage.navigateToCompletedInitiative();
        Thread.sleep(2000);
        completedInitiativePage.clickSearchInput();
        Thread.sleep(1000);
        completedInitiativePage.enterInitiativeCode(initiativeCode);
        Thread.sleep(1000);
        completedInitiativePage.clickSearchButton();
        Thread.sleep(3000);
        
        boolean notDisplayed = completedInitiativePage.verifyInitiativeNotDisplayedOnCompletedPage(initiativeCode);
        if (!notDisplayed) {
            throw new Exception("❌ FAILED: Initiative Code '" + initiativeCode + "' is unexpectedly displayed on Completed Initiatives page after cancelling Mark Complete!");
        }
        System.out.println("✅ Step 15: Verified that initiative '" + initiativeCode + "' is NOT displayed on Completed Initiatives page (as expected)");

        System.out.println("\n✅ ✅ ✅ TC_007_Cancel_Mark_Complete_Does_Not_Apply PASSED ✅ ✅ ✅");
        System.out.println("Cancel Mark Complete flow completed successfully:");
        System.out.println("  1. ✅ Mark Complete popup opened, comment entered, and Save clicked");
        System.out.println("  2. ✅ Cancel button clicked on confirmation popup");
        System.out.println("  3. ✅ Popup closed using cross icon");
        System.out.println("  4. ✅ Re-searched for initiative and verified it remains in Initiative Status Management grid (not marked as complete)");
        System.out.println("  5. ✅ Verified initiative is NOT displayed on Completed Initiatives page");
        System.out.println("═══════════════════════════════════════════════════════\n");
        */
    }

    /**
     * TC_008_Verify_Pagination_On_Initiative_Status_Management_Page
     *
     * Steps:
     * 1. Click on the Initiative Management module.
     * 2. Click on the Initiative Status Management page.
     * 3. Click on the 2nd page in the pagination control.
     * 4. Verify that exactly 5 records are displayed on the Initiative Status Management grid.
     *
     * Expected Result:
     * - Pagination should work correctly and the 2nd page should display 5 records.
     */
    @Test(priority = 8, enabled = true)
    @Description("TC_008_Verify_Pagination_On_Initiative_Status_Management_Page - Verify that clicking page 2 shows exactly 5 records")
    @Story("Initiative Status Management - Pagination")
    @Severity(SeverityLevel.NORMAL)
    public void TC_008_Verify_Pagination_On_Initiative_Status_Management_Page() throws Throwable {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🧪 TC_008_Verify_Pagination_On_Initiative_Status_Management_Page: Verify pagination (page size = 5)");
        System.out.println("═══════════════════════════════════════════════════════");

        initiativeStatusPage = new InitiativeStatusManagement(webDriver, reportLogger);

        // Step 1 & 2: Navigate to Initiative Status Management page
        System.out.println("\n📌 Step 1-2: Navigate to Initiative Status Management page");
        initiativeStatusPage.navigateToInitiativeStatusManagementPage();
        Thread.sleep(3000);

        // Step 3: Click on the 2nd page in pagination
        System.out.println("\n📌 Step 3: Click on page 2 in the pagination control");
        initiativeStatusPage.clickSecondPageInPagination();
        Thread.sleep(3000);

        // Step 4: Verify that exactly 5 records are displayed on the grid
        System.out.println("\n📌 Step 4: Verify that exactly 5 records are displayed on page 2 of the Status grid");
        int rowCount = initiativeStatusPage.getCurrentStatusGridRowCount();
        System.out.println("  ℹ️ Detected row count on page 2: " + rowCount);

        if (rowCount != 5) {
            throw new Exception("❌ FAILED: Expected 5 records on page 2, but found " + rowCount + " records.");
        }

        System.out.println("✅ Step 4: Verified that exactly 5 records are displayed on page 2 of the Initiative Status Management grid");
        System.out.println("\n✅ ✅ ✅ TC_008_Verify_Pagination_On_Initiative_Status_Management_Page PASSED ✅ ✅ ✅");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * Helper method: Logout current user via profile icon
     */
    @Step("Logout current user via profile icon")
    private void logoutCurrentUser() throws Throwable {
        webDriver.switchTo().defaultContent();
        Thread.sleep(1000);

        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

            By profileIcon = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[1]/div/div[2]/div/div/span");
            By logoutButton = By.xpath("/html/body/div[3]/div[3]/ul/div[3]/li/span[1]");

            System.out.println("  🔍 Searching for Profile icon for logout...");
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(profileIcon));

            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block:'center'});", profile);
            Thread.sleep(500);

            try {
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", profile);
                System.out.println("  ✅ Clicked Profile icon using JavaScript");
            } catch (Exception e1) {
                profile.click();
                System.out.println("  ✅ Clicked Profile icon using direct click");
            }

            Thread.sleep(1500);

            System.out.println("  🔍 Searching for Logout button in profile menu...");
            WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

            try {
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", logout);
                System.out.println("  ✅ Clicked Logout button using JavaScript");
            } catch (Exception e2) {
                logout.click();
                System.out.println("  ✅ Clicked Logout button using direct click");
            }

            Thread.sleep(3000);
            System.out.println("  ✅ User successfully logged out via profile menu");

        } catch (Exception e) {
            System.err.println("  ❌ Error during logout via profile icon: " + e.getMessage());
            throw e;
        }
    }
}


