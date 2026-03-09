package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import Base.BaseTest;
import Pages.InitiativeManagementPage;
import Pages.InitiativeReallocationPage;
import Pages.InboxPage;
import Utils.ExcelReader;
import Utils.LoginHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

/**
 * Initiative Reallocation Management Test Suite
 * 
 * This class contains all test cases for Initiative Reallocation module operations
 * including navigation, search, and validation.
 * 
 * @author Automation Team
 * @version 1.0
 */
@Epic("Initiative Management")
@Feature("Initiative Reallocation Operations")
public class InitiativeReallocationTest extends BaseTest {

    protected InitiativeReallocationPage initiativeReallocationPage;
    protected InitiativeManagementPage initiativeManagementPage; // Updated by Shahu.D
    protected InboxPage inboxPage; // Updated by Shahu.D

    /**
     * Generic DataProvider to fetch test data by TC_ID from Excel
     * Reads from "InitiativeReallocation" sheet for Initiative Reallocation tests
     * 
     * @param method Test method to get data for
     * @return Object array containing test data
     * @throws Exception if Excel file cannot be read
     */
    @DataProvider(name = "initiativeReallocationData")
    public Object[][] getInitiativeReallocationData(Method method) throws Exception {
        String testCaseId = method.getName();
        System.out.println("🔍 DataProvider: Looking for TC_ID = '" + testCaseId + "' in Excel file");
        System.out.println("📄 DataProvider: Reading from sheet 'InitiativeReallocation'");
        
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeReallocation");
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
            try {
                String excelTCID = reader.getData(i + 1, 0);
                if (excelTCID == null) {
                    continue;
                }
                excelTCID = excelTCID.trim();

                if (excelTCID.equalsIgnoreCase(testCaseId)) {
                    System.out.println("✅ DataProvider: Found matching row at index " + (i + 1));
                    int paramCount = method.getParameterCount();
                    Object[][] data = new Object[1][paramCount];

                    for (int j = 0; j < paramCount; j++) {
                        String cellValue = reader.getData(i + 1, j + 1);
                        data[0][j] = (cellValue == null || cellValue.equals("ERROR")) ? "" : cellValue.trim();
                        System.out.println("   Parameter " + (j + 1) + ": '" + data[0][j] + "'");
                    }
                    return data;
                }
            } catch (Exception e) {
                System.err.println("⚠️ DataProvider: Error reading row " + (i + 1) + ": " + e.getMessage());
                continue;
            }
        }
        
        System.err.println("❌ DataProvider: No matching row found for TC_ID = '" + testCaseId + "'");
        System.err.println("💡 Please add a row in TestdataIni.xlsx sheet 'InitiativeReallocation' with:");
        System.err.println("   - Column A (TC_ID): " + testCaseId);
        
        return new Object[0][0];
    }

    /**
     * Step method: Navigate to Initiative Reallocation Page
     */
    @Step("Navigate to Initiative Reallocation Page")
    private void navigateToInitiativeReallocationPage() throws Throwable {
        initiativeReallocationPage.navigateToInitiativeReallocationPage();
    }

    /**
     * Step method: Click Search Icon
     */
    @Step("Click Search Icon")
    private void clickSearchIcon() throws Throwable {
        initiativeReallocationPage.clickSearchIcon();
    }

    /**
     * Step method: Enter Initiative Code
     */
    @Step("Enter Initiative Code: {0}")
    private void enterInitiativeCode(String initiativeCode) throws Throwable {
        initiativeReallocationPage.enterInitiativeCode(initiativeCode);
    }

    /**
     * Step method: Click Search Button
     */
    @Step("Click Search Button")
    private void clickSearchButton() throws Throwable {
        initiativeReallocationPage.clickSearchButton();
    }

    /**
     * Step method: Verify Initiative Displayed
     */
    @Step("Verify Initiative Displayed: {0}")
    private boolean verifyInitiativeDisplayed(String initiativeCode) throws Throwable {
        return initiativeReallocationPage.verifyInitiativeDisplayed(initiativeCode);
    }

    /**
     * Step method: Click Current Approver Dropdown
     * Shahu.D
     */
    @Step("Click Current Approver Dropdown")
    private void clickCurrentApproverDropdown() throws Throwable {
        initiativeReallocationPage.clickCurrentApproverDropdown();
    }

    /**
     * Step method: Select Current Approver
     * Shahu.D
     */
    @Step("Select Current Approver: {0}")
    private void selectCurrentApprover(String approverName) throws Throwable {
        initiativeReallocationPage.selectCurrentApprover(approverName);
    }

    /**
     * Step method: Click Nature of Initiative Dropdown
     * Shahu.D
     */
    @Step("Click Nature of Initiative Dropdown")
    private void clickNatureOfInitiativeDropdown() throws Throwable {
        initiativeReallocationPage.clickNatureOfInitiativeDropdown();
    }

    /**
     * Step method: Select Nature of Initiative
     * Shahu.D
     */
    @Step("Select Nature of Initiative: {0}")
    private void selectNatureOfInitiative(String noiValue) throws Throwable {
        initiativeReallocationPage.selectNatureOfInitiative(noiValue);
    }

    /**
     * Step method: Click Business Group Dropdown
     * Shahu.D
     */
    @Step("Click Business Group Dropdown")
    private void clickBusinessGroupDropdown() throws Throwable {
        initiativeReallocationPage.clickBusinessGroupDropdown();
    }

    /**
     * Step method: Select Business Group
     * Shahu.D
     */
    @Step("Select Business Group: {0}")
    private void selectBusinessGroup(String bgValue) throws Throwable {
        initiativeReallocationPage.selectBusinessGroup(bgValue);
    }

    /**
     * Step method: Click Next Button
     * Shahu.D
     */
    @Step("Click Next Button")
    private void clickNextButton() throws Throwable {
        initiativeReallocationPage.clickNextButton();
    }

    /**
     * Step method: Click Initiative Details First Row
     * Shahu.D
     */
    @Step("Click Initiative Details First Row")
    private void clickInitiativeDetailsFirstRow() throws Throwable {
        initiativeReallocationPage.clickInitiativeDetailsFirstRow();
    }

    /**
     * Step method: Click Approver Dropdown
     * Shahu.D
     */
   

    /**
     * Step method: Select Approver
     * Shahu.D
     */
    @Step("Select Approver")
    private void selectApprover(String  Approver) throws Throwable {
        initiativeReallocationPage.selectApprover( Approver);
    }

    /**
     * Step method: Select Approver Checkbox
     * Shahu.D
     */
    @Step("Select Approver Checkbox")
    private void selectApproverCheckbox() throws Throwable {
        initiativeReallocationPage.selectApproverCheckbox();
    }

    /**
     * Step method: Click Save Button
     * Shahu.D
     */
    @Step("Click Save Button")
    private void clickSaveButton() throws Throwable {
        initiativeReallocationPage.clickSaveButton();
    }

    /**
     * Override setUp to handle custom login for TC_002
     * Updated by Shahu.D
     * @throws TimeoutException 
     */
    @BeforeMethod
    @Override
    public void setUp(Method method) throws TimeoutException {
        // Check if this is TC_002 which needs custom login - Updated by Shahu.D
        if (method.getName().equals("TC_002_Check_Reallocated_Initiative_In_Inbox")) {
            try {
                // Create test logger per method - Updated by Shahu.D
                reportLogger = extent.createTest(method.getName());
                log.info("===== Starting Test: " + method.getName() + " ====="); // Updated by Shahu.D

                // Pick browser from config - Updated by Shahu.D
                String browser = config.getProperty("browser", "chrome").toLowerCase();
                log.info("Launching browser: " + browser); // Updated by Shahu.D

                // Setup browser (same as parent) - Updated by Shahu.D
                switch (browser) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--disable-notifications");
                        webDriver = new ChromeDriver(chromeOptions);
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        webDriver = new FirefoxDriver(firefoxOptions);
                        break;
                    case "edge":
                    default:
                        try {
                            WebDriverManager.edgedriver().setup();
                            log.info("✅ WebDriverManager successfully set up EdgeDriver");
                        } catch (Exception e) {
                            log.warn("⚠️ WebDriverManager failed, using manual driver setup: " + e.getMessage());
                            System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
                        }
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.addArguments("--disable-save-password-bubble");
                        edgeOptions.addArguments("--disable-notifications");
                        edgeOptions.setExperimentalOption("prefs", new HashMap<String, Object>() {{
                            put("credentials_enable_service", false);
                            put("profile.password_manager_enabled", false);
                            put("profile.default_content_setting_values.notifications", 2);
                        }});
                        webDriver = new EdgeDriver(edgeOptions);
                        break;
                }

                webDriver.manage().window().maximize();

                // Navigate to URL from config - Updated by Shahu.D
                String appUrl = config.getProperty("url", "URLInconfig");
                webDriver.get(appUrl);
                log.info("Navigated to: " + appUrl); // Updated by Shahu.D

                // Initialize LoginHelper for custom SSO login - Updated by Shahu.D
                loginHelper = new LoginHelper(webDriver, reportLogger, config);
                
                // Perform SSO login with secondApprover credentials - Updated by Shahu.D
                String secondApproverEmail = config.getProperty("secondApproverEmail");
                String secondApproverPassword = config.getProperty("secondApproverPassword");
                
                if (secondApproverEmail != null && secondApproverPassword != null) {
                    System.out.println("🔐 Logging in with secondApprover: " + secondApproverEmail); // Updated by Shahu.D
                    loginHelper.performLogin(LoginHelper.AuthType.FORM, secondApproverEmail, secondApproverPassword); // Updated by Shahu.D
                    System.out.println("✅ Login successful with secondApprover"); // Updated by Shahu.D
                    log.info("Login successful with secondApprover: " + secondApproverEmail); // Updated by Shahu.D
                } else {
                    throw new RuntimeException("❌ secondApproverEmail or secondApproverPassword not configured in config.properties"); // Updated by Shahu.D
                }
                
                // Wait for login to complete - Updated by Shahu.D
                Thread.sleep(3000);
                
                // Close quick notification popup if present - Updated by Shahu.D
           //     dismissQuickNotificationIfPresent();
                
                // Initialize Page Objects for TC_002 - Updated by Shahu.D
                initiativeManagementPage = new InitiativeManagementPage(webDriver, reportLogger);
                initiativeReallocationPage = new InitiativeReallocationPage(webDriver, reportLogger);
                inboxPage = new InboxPage(webDriver, reportLogger);
                
            } catch (Exception e) {
                System.err.println("❌ Error in setUp for TC_002: " + e.getMessage()); // Updated by Shahu.D
                e.printStackTrace(); // Updated by Shahu.D
                throw new RuntimeException(e);
            }
        } else {
            // For other tests, use default setUp - Updated by Shahu.D
            super.setUp(method);
            // Initialize page objects for other tests - Updated by Shahu.D
            if (initiativeReallocationPage == null) {
                initiativeReallocationPage = new InitiativeReallocationPage(webDriver, reportLogger);
            }
        }
    }
 
    /**
     * DataProvider for TC_002 - Reads Initiative Code, Current Approver, Nature of Initiative, and Business Group from Excel
     * Updated by Shahu.D
     */
    @DataProvider(name = "reallocationInboxData")
    public Object[][] getReallocationInboxData(Method method) throws Exception {
        String testCaseId = method.getName();
        System.out.println("🔍 DataProvider: Looking for TC_ID = '" + testCaseId + "' in Excel file"); // Updated by Shahu.D
        System.out.println("📄 DataProvider: Reading from sheet 'InitiativeReallocation'"); // Updated by Shahu.D
        
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "InitiativeReallocation");
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
        System.err.println("💡 Please add a row in TestdataIni.xlsx sheet 'InitiativeReallocation' with:"); // Updated by Shahu.D
        System.err.println("   - Column A (TC_ID): " + testCaseId); // Updated by Shahu.D
        System.err.println("   - Column B (InitiativeCode): <initiative code>"); // Updated by Shahu.D
        System.err.println("   - Column C (CurrentApprover): <current approver name>"); // Updated by Shahu.D
        System.err.println("   - Column D (NatureOfInitiative): <nature of initiative>"); // Updated by Shahu.D
        System.err.println("   - Column E (BusinessGroup): <business group>"); // Updated by Shahu.D
        System.err.println("💡 Make sure the TC_ID in Excel EXACTLY matches: " + testCaseId); // Updated by Shahu.D
        
        return new Object[0][0];
    }

    /**
     * Step method: Click Initiative Details Row (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Click Initiative Details Row")
    private void clickInitiativeDetailsRow() throws Throwable {
        initiativeReallocationPage.clickInitiativeDetailsFirstRow(); // Updated by Shahu.D
    }

    /**
     * Step method: Select Approver Checkbox and Save (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Select Approver Checkbox and Save")
    private void selectApproverCheckboxAndSave() throws Throwable {
        initiativeReallocationPage.selectApproverCheckbox(); // Updated by Shahu.D
        initiativeReallocationPage.clickSaveButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Navigate to Initiatives Page (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Navigate to Initiatives Page")
    private void navigateToInitiativesPage() throws Throwable {
        initiativeManagementPage.navigateToInitiativesPage(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Inbox Section (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Click Inbox Section")
    private void clickInboxSection() throws Throwable {
        inboxPage.clickInboxSection(); // Updated by Shahu.D
    }

    /**
     * Step method: Click Search Icon for Inbox (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Click Search Icon for Inbox")
    private void clickInboxSearchIcon() throws Throwable {
        inboxPage.clickSearchIcon(); // Updated by Shahu.D
    }

    /**
     * Step method: Enter Initiative Code in Inbox (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Enter Initiative Code in Inbox: {0}")
    private void enterInitiativeCodeInInbox(String initiativeCode) throws Throwable {
        inboxPage.enterInitiativeCode(initiativeCode); // Updated by Shahu.D
    }

    /**
     * Step method: Click Final Search Button (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Click Final Search Button")
    private void clickFinalSearchButton() throws Throwable {
        inboxPage.clickFinalSearchButton(); // Updated by Shahu.D
    }

    /**
     * Step method: Verify Initiative in Inbox (for TC_002)
     * Updated by Shahu.D
     */
    @Step("Verify Initiative in Inbox: {0}")
    private void verifyInitiativeInInbox(String initiativeCode) throws Throwable {
        boolean found = inboxPage.verifyInitiativeInInbox(initiativeCode); // Updated by Shahu.D
        if (!found) {
            throw new Exception("Initiative Code '" + initiativeCode + "' not found in Inbox"); // Updated by Shahu.D
        }
    }

    /**
     * TC_001_InitiativeReallocation - Initiative Reallocation Workflow
     * 
     * This test verifies the complete workflow for Initiative Reallocation:
     * 1. Navigate to Initiative Reallocation page
     * 2. Select filters (Current Approver, Nature of Initiative, Business Group)
     * 3. Click Next to view initiatives
     * 4. Select an initiative and assign a new approver
     * 5. Save the changes
     * 
     * Shahu.D
     * 
     * @throws Exception if any step fails
     */
    @Test(priority = 1, enabled = true,dataProvider = "reallocationInboxData")
    @Description("TC_001_InitiativeReallocation - Initiative Reallocation Workflow")
    @Story("Initiative Reallocation Operations")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001_InitiativeReallocation(String currentapprover,String NatureofInitiative,String businessGroup,String title,String nextapprover) throws Throwable {
        try {
            initiativeReallocationPage = new InitiativeReallocationPage(webDriver, reportLogger);
            
            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🧪 TC_001_InitiativeReallocation: Initiative Reallocation Workflow");
            System.out.println("═══════════════════════════════════════════════════════");
            
            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete...");
            Thread.sleep(3000);
            
            // Step 1: Click on the Initiative Management module - Shahu.D
            System.out.println("\n📌 Step 1: Click on the Initiative Management module");
            navigateToInitiativeReallocationPage(); // Shahu.D - This includes clicking Initiative Management nav
            System.out.println("✅ Step 1 completed: Successfully clicked on Initiative Management module");
            Thread.sleep(2000);
            
            // Step 2: Click on the Initiative Reallocation page - Shahu.D
            System.out.println("\n📌 Step 2: Click on the Initiative Reallocation page");
            // Navigation is already done in Step 1, but we verify we're on the right page
            System.out.println("✅ Step 2 completed: Successfully navigated to Initiative Reallocation page");
            Thread.sleep(3000);
            
            // Step 3: Click on the Select Current Approver dropdown field - Shahu.D
            System.out.println("\n📌 Step 3: Click on the Select Current Approver dropdown field");
            System.out.println("   XPath: //*[@id=\"currentApprover\"]/span[2]"); // Shahu.D
            clickCurrentApproverDropdown(); // Shahu.D
            System.out.println("✅ Step 3 completed: Successfully clicked on Current Approver dropdown");
            Thread.sleep(2000);
            
            // Step 4: Select the value: "Abhishek Prasad (CMO)" from the Current Approver dropdown - Shahu.D
            System.out.println("\n📌 Step 4: Select 'Abhishek Prasad (CMO)' from Current Approver dropdown");
            System.out.println("   XPath: //*[@id=\"currentApprover-list1\"]/span/span"); // Shahu.D
            selectCurrentApprover(currentapprover); // Shahu.D
            System.out.println("✅ Step 4 completed: Successfully selected Current Approver");
            Thread.sleep(2000);
            
            // Step 5: Click on the Nature of Initiative dropdown field - Shahu.D
            System.out.println("\n📌 Step 5: Click on the Nature of Initiative dropdown field");
            System.out.println("   XPath: //*[@id=\"natureOfInitiativeId\"]/span[2]"); // Shahu.D
            clickNatureOfInitiativeDropdown(); // Shahu.D
            System.out.println("✅ Step 5 completed: Successfully clicked on Nature of Initiative dropdown");
            Thread.sleep(2000);
            
            // Step 6: Select the value: "StartUp Application Processing" - Shahu.D
            System.out.println("\n📌 Step 6: Select 'StartUp Application Processing' from Nature of Initiative dropdown");
            System.out.println("   XPath: //*[@id=\"natureOfInitiativeId-list1\"]/span/span"); // Shahu.D
            selectNatureOfInitiative(NatureofInitiative); // Shahu.D
            System.out.println("✅ Step 6 completed: Successfully selected Nature of Initiative");
            Thread.sleep(2000);
            
            // Step 7: Click on the Business Group dropdown field - Shahu.D
            System.out.println("\n📌 Step 7: Click on the Business Group dropdown field");
            System.out.println("   XPath: //*[@id=\"businessGroupId\"]/span[2]"); // Shahu.D
            clickBusinessGroupDropdown(); // Shahu.D
            System.out.println("✅ Step 7 completed: Successfully clicked on Business Group dropdown");
            Thread.sleep(2000);
            
            // Step 8: Select the value: "AI And ML Group" - Shahu.D
            System.out.println("\n📌 Step 8: Select 'AI And ML Group' from Business Group dropdown");
            System.out.println("   XPath: //*[@id=\"businessGroupId-list1\"]/span/span"); // Shahu.D
            selectBusinessGroup(businessGroup); // Shahu.D
            System.out.println("✅ Step 8 completed: Successfully selected Business Group");
            Thread.sleep(2000);
            
            initiativeReallocationPage.EnterInitiativeTitle(title);
            
            // Step 9: Click on the Next button - Shahu.D
            System.out.println("\n📌 Step 9: Click on the Next button");
            System.out.println("   XPath: //*[@id=\"id__873\"]"); // Shahu.D
            clickNextButton(); // Shahu.D
            System.out.println("✅ Step 9 completed: Successfully clicked on Next button");
            Thread.sleep(3000);
            
            // Step 10: Click on the Initiative Details first row value - Shahu.D
            System.out.println("\n📌 Step 10: Click on the Initiative Details first row value");
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[1]/div/div[2]/div/table/tbody/tr[1]/td"); // Shahu.D
            clickInitiativeDetailsFirstRow(); // Shahu.D
            System.out.println("✅ Step 10 completed: Successfully clicked on Initiative Details first row");
            Thread.sleep(3000);
            
          
            
            // Step 12: Select the required approver from the dropdown - Shahu.D
            System.out.println("\n📌 Step 12: Select the required approver from the dropdown");
        //    System.out.println("   XPath: //*[@id=\"Dropdown1353-list0-label\"]"); // Shahu.D
            selectApprover(nextapprover); // Shahu.D
            System.out.println("✅ Step 12 completed: Successfully selected approver");
            Thread.sleep(1000);
 
            // Step 13: Select the checkbox for that approver and click on the Save button - Shahu.D
            System.out.println("\n📌 Step 13: Select the checkbox for approver and click Save button");
            System.out.println("   Checkbox XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[2]/table/tbody/tr[1]/td[6]/div/label/div/i"); // Shahu.D
            System.out.println("   Save Button XPath: //*[@id=\"id__1350\"]"); // Shahu.D
            selectApproverCheckbox(); // Shahu.D
            Thread.sleep(1000);
            clickSaveButton(); // Shahu.D
            System.out.println("✅ Step 13 completed: Successfully selected checkbox and clicked Save button");
            Thread.sleep(3000);
            
            System.out.println("\n✅ ✅ ✅ TC_001_InitiativeReallocation PASSED ✅ ✅ ✅");
            System.out.println("Initiative Reallocation workflow completed successfully:");
            System.out.println("  1. ✅ Clicked on Initiative Management module"); // Shahu.D
            System.out.println("  2. ✅ Clicked on Initiative Reallocation page"); // Shahu.D
            System.out.println("  3. ✅ Clicked on Current Approver dropdown"); // Shahu.D
            System.out.println("  4. ✅ Selected 'Abhishek Prasad (CMO)' from Current Approver dropdown"); // Shahu.D
            System.out.println("  5. ✅ Clicked on Nature of Initiative dropdown"); // Shahu.D
            System.out.println("  6. ✅ Selected 'StartUp Application Processing' from Nature of Initiative dropdown"); // Shahu.D
            System.out.println("  7. ✅ Clicked on Business Group dropdown"); // Shahu.D
            System.out.println("  8. ✅ Selected 'AI And ML Group' from Business Group dropdown"); // Shahu.D
            System.out.println("  9. ✅ Clicked on Next button"); // Shahu.D
            System.out.println("  10. ✅ Clicked on Initiative Details first row"); // Shahu.D
            System.out.println("  11. ✅ Clicked on Approvers dropdown"); // Shahu.D
            System.out.println("  12. ✅ Selected approver from dropdown"); // Shahu.D
            System.out.println("  13. ✅ Selected checkbox and clicked Save button"); // Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n");
        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_001_InitiativeReallocation FAILED ❌ ❌ ❌");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * TC_002_Check_Reallocated_Initiative_In_Inbox
     * 
     * This test verifies that a reallocated initiative appears in the Inbox
     * for the configured approver (Aditi Rao - CTO).
     * 
     * Test Steps:
     * 1. Click on Initiative Management module
     * 2. Click on Initiative Reallocation page
     * 3. Select Current Approver (from Excel)
     * 4. Select Nature of Initiative (from Excel)
     * 5. Select Business Group (from Excel)
     * 6. Click Next button
     * 7. Click on any Initiative Details row
     * 8. Select approver from dropdown
     * 9. Tick checkbox and click Save
     * 10. Navigate to Initiatives page
     * 11. Click on Inbox section
     * 12. Click Search icon
     * 13. Enter Initiative Code (from Excel)
     * 14. Click final Search button
     * 15. Validate: Initiative appears in Inbox
     * 
     * Updated by Shahu.D
     */
    @Test(priority = 2, enabled = true, dataProvider = "reallocationInboxData")
    @Description("TC_002_Check_Reallocated_Initiative_In_Inbox - Verify reallocated initiative appears in Inbox")
    @Story("Initiative Reallocation and Inbox Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002_Check_Reallocated_Initiative_In_Inbox( String currentApprover, String natureOfInitiative, String businessGroup,String title,String nextapprover,String initiativeCode) throws Throwable { // Updated by Shahu.D
        try {
            System.out.println("\n═══════════════════════════════════════════════════════"); // Updated by Shahu.D
            System.out.println("🧪 TC_002_Check_Reallocated_Initiative_In_Inbox"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════"); // Updated by Shahu.D

            // Wait for login to complete and page to be ready
            System.out.println("⏳ Waiting for login to complete..."); // Updated by Shahu.D
            Thread.sleep(3000); // Updated by Shahu.D

            // Step 1 & 2: Navigate to Initiative Reallocation page
            System.out.println("\n📌 Step 1: Click on the Initiative Management module"); // Updated by Shahu.D
            navigateToInitiativeReallocationPage(); // Updated by Shahu.D
            System.out.println("✅ Step 1-2 completed: Successfully navigated to Initiative Reallocation page"); // Updated by Shahu.D
            
            clickCurrentApproverDropdown(); // Shahu.D
            System.out.println("✅ Step 3 completed: Successfully clicked on Current Approver dropdown");
            Thread.sleep(2000);
            
            // Step 3 & 4: Select Current Approver (from Excel) - Updated by Shahu.D
            System.out.println("\n📌 Step 3: Click on Select Current Approver dropdown"); // Updated by Shahu.D
            System.out.println("📌 Step 4: Select '" + currentApprover + "' from Current Approver dropdown (from Excel)"); // Updated by Shahu.D
            selectCurrentApprover(currentApprover); // Updated by Shahu.D
            System.out.println("✅ Step 3-4 completed: Successfully selected Current Approver: " + currentApprover); // Updated by Shahu.D

            
            clickNatureOfInitiativeDropdown(); // Shahu.D
            System.out.println("✅ Step 5 completed: Successfully clicked on Nature of Initiative dropdown");
            Thread.sleep(2000);
            
            // Step 5 & 6: Select Nature of Initiative (from Excel) - Updated by Shahu.D
            System.out.println("\n📌 Step 5: Click on Nature of Initiative dropdown"); // Updated by Shahu.D
            System.out.println("📌 Step 6: Select '" + natureOfInitiative + "' from Nature of Initiative dropdown (from Excel)"); // Updated by Shahu.D
            selectNatureOfInitiative(natureOfInitiative); // Updated by Shahu.D
            System.out.println("✅ Step 5-6 completed: Successfully selected Nature of Initiative: " + natureOfInitiative); // Updated by Shahu.D

            
            clickBusinessGroupDropdown(); // Shahu.D
            System.out.println("✅ Step 7 completed: Successfully clicked on Business Group dropdown");
            Thread.sleep(2000);
            
            
            
            // Step 7 & 8: Select Business Group (from Excel) - Updated by Shahu.D
            System.out.println("\n📌 Step 7: Click on Business Group dropdown"); // Updated by Shahu.D
            System.out.println("📌 Step 8: Select '" + businessGroup + "' from Business Group dropdown (from Excel)"); // Updated by Shahu.D
            selectBusinessGroup(businessGroup); // Updated by Shahu.D
            System.out.println("✅ Step 7-8 completed: Successfully selected Business Group: " + businessGroup); // Updated by Shahu.D

            
            initiativeReallocationPage.EnterInitiativeTitle(title);
            
            // Step 9: Click Next button
            System.out.println("\n📌 Step 9: Click on the Next button"); // Updated by Shahu.D
            System.out.println("   XPath: //button[.='Next']"); // Updated by Shahu.D
            clickNextButton(); // Updated by Shahu.D
            System.out.println("✅ Step 9 completed: Successfully clicked on Next button"); // Updated by Shahu.D

            // Step 10: Click on Initiative Details row
            System.out.println("\n📌 Step 10: Click on any Initiative Details row"); // Updated by Shahu.D
            clickInitiativeDetailsRow(); // Updated by Shahu.D
            System.out.println("✅ Step 10 completed: Successfully clicked on Initiative Details row"); // Updated by Shahu.D

            // Step 11 & 12: Select Approver
            System.out.println("\n📌 Step 11: Click on Approvers dropdown"); // Updated by Shahu.D
            System.out.println("📌 Step 12: Select any value from Approvers dropdown"); // Updated by Shahu.D
      
            selectApprover(nextapprover); // Shahu.D
         
            Thread.sleep(1000);
          
            // Step 13: Tick checkbox and click Save
            System.out.println("\n📌 Step 13: Tick the checkbox and click Save"); // Updated by Shahu.D
            selectApproverCheckboxAndSave(); // Updated by Shahu.D
            System.out.println("✅ Step 13 completed: Successfully selected checkbox and clicked Save"); // Updated by Shahu.D

            // Wait for save to complete
            Thread.sleep(3000); // Updated by Shahu.D

            // Step 14: Navigate to Initiatives page
            System.out.println("\n📌 Step 14: Navigate to Initiatives page"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"children-panel-container\"]/div[3]/div[1]/p"); // Updated by Shahu.D
            navigateToInitiativesPage(); // Updated by Shahu.D
            System.out.println("✅ Step 14 completed: Successfully navigated to Initiatives page"); // Updated by Shahu.D

            // Step 15: Click on Inbox section
            System.out.println("\n📌 Step 15: Click on Inbox section"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"ImFltr-Inbox\"]/a/span[2]"); // Updated by Shahu.D
            clickInboxSection(); // Updated by Shahu.D
            System.out.println("✅ Step 15 completed: Successfully clicked on Inbox section"); // Updated by Shahu.D

            // Step 16: Click Search icon
            System.out.println("\n📌 Step 16: Click Search icon"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"); // Updated by Shahu.D
            clickInboxSearchIcon(); // Updated by Shahu.D
            System.out.println("✅ Step 16 completed: Successfully clicked on Search icon"); // Updated by Shahu.D

            // Step 17: Enter Initiative Code
            System.out.println("\n📌 Step 17: Enter Initiative Code (from Excel): " + initiativeCode); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"DemandCode\"]"); // Updated by Shahu.D
            enterInitiativeCodeInInbox(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step 17 completed: Successfully entered Initiative Code"); // Updated by Shahu.D

            // Step 18: Click final Search button
            System.out.println("\n📌 Step 18: Click final Search button"); // Updated by Shahu.D
            System.out.println("   XPath: //*[@id=\"id__520\"]"); // Updated by Shahu.D
            clickFinalSearchButton(); // Updated by Shahu.D
            System.out.println("✅ Step 18 completed: Successfully clicked on final Search button"); // Updated by Shahu.D

            // Step 19: Validate Initiative appears in Inbox
            System.out.println("\n📌 Step 19: Validate Initiative Code '" + initiativeCode + "' appears in Inbox"); // Updated by Shahu.D
            verifyInitiativeInInbox(initiativeCode); // Updated by Shahu.D
            System.out.println("✅ Step 19 completed: Initiative Code '" + initiativeCode + "' found in Inbox"); // Updated by Shahu.D

            System.out.println("\n✅ ✅ ✅ TC_002_Check_Reallocated_Initiative_In_Inbox PASSED ✅ ✅ ✅"); // Updated by Shahu.D
            System.out.println("Reallocated initiative verification completed successfully!"); // Updated by Shahu.D
            System.out.println("═══════════════════════════════════════════════════════\n"); // Updated by Shahu.D

        } catch (Exception e) {
            System.err.println("\n❌ ❌ ❌ TC_002_Check_Reallocated_Initiative_In_Inbox FAILED ❌ ❌ ❌"); // Updated by Shahu.D
            System.err.println("Error: " + e.getMessage()); // Updated by Shahu.D
            e.printStackTrace(); // Updated by Shahu.D
            throw e; // Updated by Shahu.D
        }
    }
}

