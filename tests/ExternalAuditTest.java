package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

import Base.BaseTest;
import Pages.ExternalAuditPage;
import Utils.ExcelReader;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

/**
 * External Audit / Converted Initiative Test Suite
 *
 * This class contains all test cases for External Audit page operations,
 * separated from the main Initiative tests.
 *
 * Data is read from a dedicated sheet in TestdataIni.xlsx so it is
 * completely isolated from existing Initiative flows.
 */
@Epic("External Audit / Converted Initiative")
@Feature("External Audit Operations")
public class ExternalAuditTest extends BaseTest {

    protected ExternalAuditPage externalAuditPage;

    /**
     * Generic DataProvider to fetch test data by TC_ID from Excel
     * Reads from "ExternalAudit" sheet in TestdataIni.xlsx
     *
     * Excel format (example) - Sheet: "External Audit"
     *  - Column 0: TC_001 / TC_002 (Test Case ID)
     *  - For TC_001: (not currently using Excel)
     *  - For TC_002:
     *       Column 1: Audit Type value to select from dropdown
     *       Column 2: Title (form title to enter)
     *       Column 3: Initiative Title (text to match in Initiative table before clicking Select)
     *       Column 4: Planned Start Date (date to enter in Planned Start Date field)
     *       Column 5: Auditor User Name (to select in Auditor table)
     *       Column 6: Planned End Date (date to enter in Planned End Date field)
     */
    @DataProvider(name = "externalAuditData")
    public Object[][] getExternalAuditData(Method method) throws Exception {
        String testCaseId = method.getName();
        // Sheet name as specified by user: "External Audit"
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "External Audit");
        int rowCount = reader.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String excelTCID = reader.getData(i + 1, 0).trim();

            if (excelTCID.equalsIgnoreCase(testCaseId)) {
                int paramCount = method.getParameterCount();
                Object[][] data = new Object[1][paramCount];

                for (int j = 0; j < paramCount; j++) {
                    String cellValue = reader.getData(i + 1, j + 1);
                    data[0][j] = (cellValue == null) ? "" : cellValue.trim();
                }
                return data;
            }
        }

        // Fallback default data if TC_ID row not found
        if (testCaseId.equalsIgnoreCase("TC_001")) {
            // no parameters
            return new Object[0][0];
        } else if (testCaseId.equalsIgnoreCase("TC_002")) {
            // default: Audit Type + Initiative Title
            return new Object[][]{{"External Audit Type", "Default Initiative Title"}};
        }

        return new Object[0][0];
    }

    /**
     * TC_001 - Navigate to External Audit Page
     *
     * Steps:
     * 1. Click on the Initiative Management module.
     * 2. Click on the External Audit Page node.
     */
    @Test(priority = 1, enabled = true, dataProvider = "externalAuditData")
    @Description("TC_001 - Navigate to External Audit Page (Initiative Management -> External Audit)")
    @Story("External Audit Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001(String title,String inDate ,String outDate) throws Throwable {
        externalAuditPage = new ExternalAuditPage(webDriver, reportLogger);
        closeNotificationPopupIfPresent();
        Thread.sleep(3000);
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📋 TC_001: Navigate to External Audit Page");
        System.out.println("═══════════════════════════════════════════════════════");

        // 1 & 2: Click Initiative Management module and then External Audit node
        navigateToExternalAuditPage();
        
        clickAddButton();
        
        // 3. Click on the Audit Type dropdown
        clickAuditTypeDropdown();

        // 4. Select Audit Type value from dropdown (from Excel)
        externalAuditPage.selectAuditType();
        
        
        externalAuditPage.entertitle(title);
        
        externalAuditPage.clickInitiative();
        
        externalAuditPage.clickfirstInitative();
        
        externalAuditPage.clickauditor();
          
        externalAuditPage.clickfirstcheckbox();
        
        externalAuditPage.clickselectAuditer();
           Thread.sleep(3000);
        externalAuditPage.enterplannedInDate(inDate);
          Thread.sleep(3000);
        externalAuditPage.enterplannnedOutDate(outDate);
          Thread.sleep(3000);
        externalAuditPage.clickSaveButton();
        
        System.out.println("\n✅ ✅ ✅ TC_001 PASSED ✅ ✅ ✅");
        System.out.println("Successfully clicked on Initiative Management module and External Audit Page node");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_002 - Modify details and verify moidfied hisory
     *
     * Steps (current scope):
     *  1. Navigate to External Audit page.
     *  2. Click on the Add button.
     *  3. Click on the Audit Type field.
     *  4. Select value from Audit Type dropdown using data from TestdataIni.xlsx
     *     - Sheet: "External Audit"
     *     - Test Case: TC_002
     *     - Column 1: Audit Type
     *
     * XPath for Add button (user provided):
     *  //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[1]/button
     *
     * XPath for Audit Type dropdown (user provided):
     *  //*[@id="Dropdown10"]
     */
    @Test(priority = 2, enabled = true)
    @Description("TC_002 - Add the External Audit (navigate, click Add, click Audit Type and select value from Excel)")
    @Story("External Audit Add Flow - Add with Audit Type from Excel")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002() throws Throwable {
        externalAuditPage = new ExternalAuditPage(webDriver, reportLogger);

        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📋 TC_002: Add the External Audit - Click Add & Select Audit Type");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("📊 Test Data from Excel (TC_002, Sheet 'External Audit'):");
        closeNotificationPopupIfPresent();
        Thread.sleep(3000);

        // 1. Navigate to External Audit page
        navigateToExternalAuditPage();

        externalAuditPage.clickonedit();
        
        
        String oldDuration = externalAuditPage.getDurationValue();
        System.out.println("🔹 Old Duration: " + oldDuration);
        
        int randomDuration = externalAuditPage.generateRandom1To100();

        externalAuditPage.enterduration(String.valueOf(randomDuration));

        System.out.println("Generated Random Duration: " + randomDuration);
        
        externalAuditPage.clicksave();
        Thread.sleep(3000);
        externalAuditPage.clickHistoryTab();
   
      Thread.sleep(3000);
       externalAuditPage.verifyDurationHistoryValueWithPagination(oldDuration);
        
        
        
        System.out.println("\n✅ ✅ ✅ TC_002 PASSED ✅ ✅ ✅");
        System.out.println("");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    
    @Test(priority = 3, enabled = true)
    @Description("TC_003 - Add the External Audit (navigate, click Add, click Audit Type and select value from Excel)")
    @Story("External Audit Add Flow - Add with Audit Type from Excel")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003() throws Throwable {
        externalAuditPage = new ExternalAuditPage(webDriver, reportLogger);

        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📋 TC_002: Add the External Audit - Click Add & Select Audit Type");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("📊 Test Data from Excel (TC_002, Sheet 'External Audit'):");
        
        closeNotificationPopupIfPresent();
        Thread.sleep(3000);
        // 1. Navigate to External Audit page
        navigateToExternalAuditPage();

        externalAuditPage.clickdelete();
        
        externalAuditPage.clickyes();
       
      
        System.out.println("\n✅ ✅ ✅ TC_003PASSED ✅ ✅ ✅");
        System.out.println("");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    

    // ==================== STEP METHODS ====================

    @Step("Navigate to External Audit Page")
    private void navigateToExternalAuditPage() throws Throwable {
        externalAuditPage.navigateToExternalAudit();
    }

    @Step("Verify External Audit Header: {expectedHeader}")
    private void verifyExternalAuditHeader(String expectedHeader) throws Throwable {
        externalAuditPage.verifyExternalAuditHeader(expectedHeader);
    }

    // Hooks ready for future tests (search, filter, edit) – kept separate
    @Step("Click Search Button (External Audit)")
    private void clickSearchButton() throws Throwable {
        externalAuditPage.clickSearchButton();
    }

    @Step("Click Audit Code Field")
    private void clickAuditCodeField() throws Throwable {
        externalAuditPage.clickAuditCodeField();
    }

    @Step("Enter Audit Code: {auditCode}")
    private void enterAuditCode(String auditCode) throws Throwable {
        externalAuditPage.enterAuditCode(auditCode);
    }

    @Step("Click Search Submit Button (External Audit)")
    private void clickSearchSubmitButton() throws Throwable {
        externalAuditPage.clickSearchSubmitButton();
    }

    @Step("Click Edit Button (External Audit)")
    private void clickEditButton() throws Throwable {
        externalAuditPage.clickEditButton();
    }

    @Step("Click Add Button (External Audit)")
    private void clickAddButton() throws Throwable {
        externalAuditPage.clickAddButton();
    }

    @Step("Click Audit Type Dropdown (External Audit)")
    private void clickAuditTypeDropdown() throws Throwable {
        externalAuditPage.clickAuditTypeDropdown();
    }

  

    @Step("Click Initiative Button (External Audit)")
    private void clickInitiativeButton() throws Throwable {
        externalAuditPage.clickInitiativeButton();
    }

    @Step("Click Initiative Select Button in Table (External Audit) for Initiative: {initiativeTitle}")
    private void clickInitiativeSelectButtonInTable(String initiativeTitle) throws Throwable {
        externalAuditPage.clickInitiativeSelectButtonInTable(initiativeTitle);
    }

    @Step("Click Auditor Button (External Audit)")
   private void clickAuditorButton() throws Throwable {
       externalAuditPage.clickAddButton();
    }
}

