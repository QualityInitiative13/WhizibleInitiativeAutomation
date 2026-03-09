package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

import Base.BaseTest;
import Pages.WatchlistConfigurationPage;
import Utils.ExcelReader;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

/**
 * Watchlist Configuration Management Test Suite
 * 
 * This class contains all test cases for Watchlist Configuration module operations
 * including navigation and validation.
 * 
 * @author Automation Team
 * @version 1.0
 */
@Epic("Watchlist Configuration Management")
@Feature("Watchlist Configuration Operations")
public class WatchlistConfigurationTest extends BaseTest {

    protected WatchlistConfigurationPage watchlistConfigurationPage;

    /**
     * Generic DataProvider to fetch test data by TC_ID from Excel
     * Reads from "WatchlistConfiguration" sheet in TestdataIni.xlsx
     * 
     * @param method Test method to get data for
     * @return Object array containing test data
     * @throws Exception if Excel file cannot be read
     */
    @DataProvider(name = "watchlistConfigurationData")
    public Object[][] getWatchlistConfigurationData(Method method) throws Exception {
        String testCaseId = method.getName();
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "WatchlistConf");
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
        // Return default data if not found in Excel
        // Check which test case to provide default data for
        if (testCaseId.equalsIgnoreCase("TC_001")) {
            return new Object[][]{{"Strategic", "Draft", "TEST001", "John Doe"}};
        } else {
            return new Object[][]{{"Test Data"}};
        }
    }

    /**
     * TC_001 - Verify Navigation to Watchlist Configuration Page and Filter Selection
     * 
     * This test verifies that users can navigate to Watchlist Configuration page,
     * click search button, and select Nature of Initiative filter from dropdown.
     * The Nature of Initiative value is read from Excel sheet "WatchlistConf".
     * 
     * Excel Format:
     * - Column 0: TC_001 (Test Case ID)
     * - Column 1: Nature of Initiative (filter value to select from dropdown)
     * - Column 2: Current Stage (filter value to select from dropdown)
     * - Column 3: Initiative Code (code value to enter in input field)
     * - Column 4: Employee Name (employee name to find in table and click checkbox)
     * 
     * Test Steps:
     * 1. Navigate to Initiative tree menu
     * 2. Click on "Watch List Configuration" node
     * 3. Click on Search button
     * 4. Click on Nature of Initiative dropdown
     * 5. Select Nature of Initiative value from Excel
     * 6. Click on Current Stage dropdown
     * 7. Select Current Stage value from Excel
     * 8. Click on Initiative Code field
     * 9. Enter Initiative Code value from Excel
     * 10. Click on After Filter Search button to execute search
     * 11. Click on Edit button in search results table
     * 12. Find row by Employee Name and click on Checkbox in that row (column 2)
     * 
     * @param natureOfInitiative The Nature of Initiative value to select (from Excel)
     * @param currentStage The Current Stage value to select (from Excel)
     * @param initiativeCode The Initiative Code value to enter (from Excel)
     * @param employeeName The Employee Name to find in table and click checkbox (from Excel)
     */
    @Test(priority = 1, enabled = true, dataProvider = "watchlistConfigurationData")
    @Description("TC_001 - Verify Navigation to Watchlist Configuration Page and Filter Selection")
    @Story("Watchlist Configuration Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001(String natureOfInitiative, String currentStage, String initiativeCode, String employeeName) throws Throwable {
        watchlistConfigurationPage = new WatchlistConfigurationPage(webDriver, reportLogger);
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📋 TC_001: Testing Navigation to Watchlist Configuration Page and Filter Selection");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("📊 Nature of Initiative from Excel: " + natureOfInitiative);
        System.out.println("📊 Current Stage from Excel: " + currentStage);
        System.out.println("📝 Initiative Code from Excel: " + initiativeCode);
        System.out.println("👤 Employee Name from Excel: " + employeeName);
        
        // Navigate to Watchlist Configuration page
        navigateToWatchlistConfigurationPage();
        
        // Wait for page to load
        Thread.sleep(3000);
        
        // Click on Search button
        clickSearchButton();
        
        // Wait for search input to appear
        Thread.sleep(2000);
        
        // Click on Nature of Initiative dropdown
        clickNatureOfInitiativeDropdown();
        
        // Select Nature of Initiative from Excel
        selectNatureOfInitiative(natureOfInitiative);
        
        // Wait for Nature of Initiative selection
        Thread.sleep(1000);
        
        // Click on Current Stage dropdown
        clickCurrentStageDropdown();
        
        // Select Current Stage from Excel
        selectCurrentStage(currentStage);
        
        // Wait for Current Stage selection
        Thread.sleep(1000);
        
        // Click on Initiative Code field
        clickInitiativeCodeField();
        
        // Enter Initiative Code from Excel
        enterInitiativeCode(initiativeCode);
        
        // Wait for Initiative Code to be entered
        Thread.sleep(1000);
        
        // Click on After Filter Search button to execute search
        clickAfterFilterSearch();
        
        // Wait for search results to load
        Thread.sleep(3000);
        
        // Click on Edit button in search results table
        clickEditButton();
        
        // Wait for edit form/modal to load
        Thread.sleep(2000);
        
        // Click on Checkbox in table by Employee Name
        clickCheckboxByEmployeeName(employeeName);
        
        // Verify navigation was successful
        System.out.println("\n✅ ✅ ✅ TC_001 PASSED ✅ ✅ ✅");
        System.out.println("Watchlist Configuration navigation verified successfully!");
        System.out.println("Successfully clicked on 'Watch List Configuration' node");
        System.out.println("Search button clicked successfully!");
        System.out.println("Selected Nature of Initiative: " + natureOfInitiative);
        System.out.println("Selected Current Stage: " + currentStage);
        System.out.println("Entered Initiative Code: " + initiativeCode);
        System.out.println("After filter search button clicked successfully!");
        System.out.println("Edit button clicked successfully!");
        System.out.println("Checkbox clicked successfully for Employee: " + employeeName);
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    // ==================== STEP METHODS ====================

    @Step("Navigate to Watchlist Configuration Page")
    private void navigateToWatchlistConfigurationPage() throws Throwable {
        watchlistConfigurationPage.navigateToWatchlistConfiguration();
    }
    
    @Step("Click Search Button")
    private void clickSearchButton() throws Throwable {
        watchlistConfigurationPage.clickSearchButton();
    }
    
    @Step("Click Nature of Initiative Dropdown")
    private void clickNatureOfInitiativeDropdown() throws Throwable {
        watchlistConfigurationPage.clickNatureOfInitiativeDropdown();
    }
    
    @Step("Select Nature of Initiative: {natureOfInitiative}")
    private void selectNatureOfInitiative(String natureOfInitiative) throws Throwable {
        watchlistConfigurationPage.selectNatureOfInitiative(natureOfInitiative);
    }
    
    @Step("Click Current Stage Dropdown")
    private void clickCurrentStageDropdown() throws Throwable {
        watchlistConfigurationPage.clickCurrentStageDropdown();
    }
    
    @Step("Select Current Stage: {currentStage}")
    private void selectCurrentStage(String currentStage) throws Throwable {
        watchlistConfigurationPage.selectCurrentStage(currentStage);
    }
    
    @Step("Click Initiative Code Field")
    private void clickInitiativeCodeField() throws Throwable {
        watchlistConfigurationPage.clickInitiativeCodeField();
    }
    
    @Step("Enter Initiative Code: {initiativeCode}")
    private void enterInitiativeCode(String initiativeCode) throws Throwable {
        watchlistConfigurationPage.enterInitiativeCode(initiativeCode);
    }
    
    @Step("Click Search Submit Button")
    private void clickSearchSubmitButton() throws Throwable {
        watchlistConfigurationPage.clickSearchSubmitButton();
    }
    
    @Step("Click After Filter Search Button")
    private void clickAfterFilterSearch() throws Throwable {
        watchlistConfigurationPage.clickAfterFilterSearch();
    }
    
    @Step("Click Edit Button")
    private void clickEditButton() throws Throwable {
        watchlistConfigurationPage.clickEditButton();
    }
    
 //   @Step("Click Checkbox")
 //   private void clickCheckbox() throws Throwable {
        //watchlistConfigurationPage.clickCheckbox();
   // }
    
    @Step("Click Checkbox by Employee Name: {employeeName}")
    private void clickCheckboxByEmployeeName(String employeeName) throws Throwable {
        watchlistConfigurationPage.clickCheckboxByEmployeeName(employeeName);
    }
    
}

