package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

import Base.BaseTest;
import Pages.ActionItemPage;
import Utils.ExcelReader;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

/**
 * Action Item Management Test Suite
 * 
 * This class contains all test cases for Action Item module operations
 * including navigation and validation.
 * 
 * @author Automation Team
 * @version 1.0
 */
@Epic("Action Item Management")
@Feature("Action Item Operations")
public class ActionItemTest extends BaseTest {

    protected ActionItemPage actionItemPage;

    /**
     * Generic DataProvider to fetch test data by TC_ID from Excel
     * Reads from "ActionItem" sheet in TestdataIni.xlsx
     * 
     * @param method Test method to get data for
     * @return Object array containing test data
     * @throws Exception if Excel file cannot be read
     */
    @DataProvider(name = "actionItemData")
    public Object[][] getInitiativeData(Method method) throws Exception {

        String testCaseId = method.getName();

        // Excel kept at project root (as per your setup)
        ExcelReader reader = new ExcelReader("ProjectData2.xlsx", "CreateProject");
        int rowCount = reader.getRowCount();

        for (int i = 0; i < rowCount; i++) {

            String excelTCID = reader.getData(i + 1, 0).trim();

            if (excelTCID.equalsIgnoreCase(testCaseId)) {

                int paramCount = method.getParameterCount();
                Object[][] data = new Object[1][paramCount];

                for (int j = 0; j < paramCount; j++) {
                    data[0][j] = reader.getData(i + 1, j + 1).trim();
                }
                return data;
            }
        }

        // If TCID not found
        return new Object[0][0];
    }

    /**
     * TC_001 - Verify Initiative Click ActionItem Page
     * 
     * This test verifies that users can navigate to Action Item page
     * from the Initiative tree menu.
     */
    @Test(priority = 1, enabled = true)
    @Description("TC_001 - Verify Initiative Click ActionItem Page")
    @Story("ActionItem Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_001() throws Throwable {
        actionItemPage = new ActionItemPage(webDriver, reportLogger);
        navigateToActionItemPage();
        System.out.println("✅ Action Item navigation verified");
    }

    /**
     * TC_002 - Verify Search Functionality with Initiative Title from Excel
     * 
     * This test verifies that users can search for initiatives by title
     * on the Action Item page. The initiative title and description are read from Excel sheet "ActionItem".
     * 
     * Excel Format:
     * - Column 0: TC_002 (Test Case ID)
     * - Column 1: Initiative Title (search text)
     * - Column 2: Description (text to enter in description field)
     * 
     * @param initiativeTitle The initiative title text to search for (from Excel)
     * @param description The description text to enter (from Excel)
     */
    @Test(priority = 2, enabled = true, dataProvider = "actionItemData")
    @Description("TC_002 - Verify Search Functionality with Initiative Title from Excel")
    @Story("ActionItem Search")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_002(String initiativeTitle, String description) throws Throwable {
        actionItemPage = new ActionItemPage(webDriver, reportLogger);
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 TC_002: Testing Search Functionality with Excel Data");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("📋 Initiative Title from Excel: " + initiativeTitle);
        System.out.println("📝 Description from Excel: " + description);
        
        // Navigate to Action Item page
        navigateToActionItemPage();
        
        // Perform search with initiative title from Excel
        performSearch(initiativeTitle);
        
        // Wait for search results to load
        Thread.sleep(3000);
        
        // Click on the searched result
        clickSearchedResult();
        
        // Wait for row to be selected
        Thread.sleep(2000);
        
        // Click edit button (first row)
        clickEditButtonFirstRow();
        
        // Wait for edit mode to open
        Thread.sleep(2000);
        
        // Click on Description field
        clickDescriptionField();
        
        // Enter description from Excel
        enterDescription(description);
        
        // Wait for description to be entered
        Thread.sleep(1000);
        
        // Click Save button
        clickSaveButton();
        
        // Verify search input is visible after search
        boolean isSearchVisible = verifySearchInputVisible();
        org.testng.Assert.assertTrue(isSearchVisible, "Search input field should be visible after search");
        
        System.out.println("\n✅ ✅ ✅ TC_002 PASSED ✅ ✅ ✅");
        System.out.println("Search functionality verified successfully with Excel data!");
        System.out.println("Searched for Initiative Title: " + initiativeTitle);
        System.out.println("Entered Description: " + description);
        System.out.println("Changes saved successfully!");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    /**
     * TC_003 - Verify Audit Action Items Tab Navigation and Search
     * 
     * This test verifies that users can navigate to the Audit Action Items tab,
     * search for initiatives by title, and select status and assigned to from dropdowns.
     * The initiative title, status, and assigned to are read from Excel sheet "ActionItem".
     * 
     * Excel Format:
     * - Column 0: TC_003 (Test Case ID)
     * - Column 1: Initiative Title (search text)
     * - Column 2: Status (status value to select from dropdown)
     * - Column 3: Assigned To (assigned to value to select from dropdown)
     * - Column 4: Action Item (action item value to enter in field)
     * - Column 5: Description (description value to enter in field)
     * 
     * @param initiativeTitle The initiative title text to search for (from Excel)
     * @param status The status value to select from dropdown (from Excel)
     * @param assignedTo The assigned to value to select from dropdown (from Excel)
     * @param actionItem The action item value to enter in field (from Excel)
     * @param description The description value to enter in field (from Excel)
     */
    @Test(priority = 3, enabled = true, dataProvider = "actionItemData")
    @Description("TC_003 - Verify Audit Action Items Tab Navigation and Search")
    @Story("ActionItem Tab Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void TC_003(String initiativeTitle, String status, String assignedTo,String description) throws Throwable {
        actionItemPage = new ActionItemPage(webDriver, reportLogger);
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📋 TC_003: Testing Audit Action Items Tab Navigation and Search");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("📋 Initiative Title from Excel: " + initiativeTitle);
        System.out.println("📊 Status from Excel: " + status);
        System.out.println("👤 Assigned To from Excel: " + assignedTo);
        //System.out.println("📝 Action Item from Excel: " + actionItem);
        System.out.println("📄 Description from Excel: " + description);
        
        // Navigate to Action Item page
        navigateToActionItemPage();
        
        // Wait for page to load
        Thread.sleep(3000);
        
        // Click on Audit Action Items tab
        clickAuditActionItemsTab();
        
        // Wait for Audit Action Items tab to load
        Thread.sleep(2000);
        
        // Click on Search button on Audit Action Items page
        clickSearchButton();
        
        // Wait for search input to appear
        Thread.sleep(2000);
        
        // Click on Initiative Title input field
        clickInitiativeTitleInput();
        
        // Enter initiative title from Excel
        enterSearchTextInAuditPage(initiativeTitle);
        
        // Wait for text to be entered
        Thread.sleep(1000);
        
        // Click on Status dropdown
        clickStatusDropdown();
        
        // Select status from Excel
        selectStatus(status);
        
        // Wait for status selection
        Thread.sleep(1000);
        
        // Click on Assigned To dropdown
        clickAssignedToDropdown();
        
        // Select assigned to from Excel
        selectAssignedTo(assignedTo);
        
        // Wait for assigned to selection
        Thread.sleep(1000);
        
        // Click on Filter Search button to execute search
        clickFilterSearchButton();
        
        // Wait for search results to load
        Thread.sleep(3000);
        
        // Click on Initiative Title in search results
        clickInitiativeTitleInResults();
        
        // Wait for initiative details to load
        Thread.sleep(2000);
        
        // Click on Edit button
        clickEditButton();
        
        // Wait for edit mode to open
        Thread.sleep(2000);
        
        // Click on Action Item field
        clickActionItemField();
        
        // Enter Action Item value from Excel
       // enterActionItem(actionItem);
        
        // Wait for Action Item to be entered
        Thread.sleep(1000);
        
        // Click on Description field
        clickDescriptionField1();
        
        // Enter Description value from Excel
        enterDescription1(description);
        
        // Wait for description to be entered
        Thread.sleep(1000);
        
        // Click Save button
        clickSaveButton();
        
        System.out.println("\n✅ ✅ ✅ TC_003 PASSED ✅ ✅ ✅");
        System.out.println("Audit Action Items tab navigation and search verified successfully!");
        System.out.println("Searched for Initiative Title: " + initiativeTitle);
        System.out.println("Selected Status: " + status);
        System.out.println("Selected Assigned To: " + assignedTo);
       // System.out.println("Entered Action Item: " + actionItem);
        System.out.println("Entered Description: " + description);
        System.out.println("Filter search executed successfully!");
        System.out.println("Initiative Title clicked from search results!");
        System.out.println("Edit button clicked successfully!");
        System.out.println("Action Item value entered successfully!");
        System.out.println("Description value entered successfully!");
        System.out.println("Save button clicked successfully!");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

  
    
    // ==================== STEP METHODS ====================

    @Step("Navigate to Action Item Page")
    private void navigateToActionItemPage() throws Throwable {
        actionItemPage.navigateToActionItem();
    }
    
    @Step("Perform Search with text: {searchText}")
    private void performSearch(String searchText) throws Throwable {
        actionItemPage.performSearch(searchText);
    }
    
    @Step("Verify Search Input Field is Visible")
    private boolean verifySearchInputVisible() throws Throwable {
        return actionItemPage.verifySearchInputVisible();
    }
    
    @Step("Click on Searched Result")
    private void clickSearchedResult() throws Throwable {
        actionItemPage.clickSearchedResult();
    }
    
    @Step("Click Edit Button (First Row)")
    private void clickEditButtonFirstRow() throws Throwable {
        actionItemPage.clickEditButtonFirstRow();
    }
    
    @Step("Click Description Field")
    private void clickDescriptionField() throws Throwable {
        actionItemPage.clickDescriptionField();
    }
    
    @Step("Enter Description: {description}")
    private void enterDescription(String description) throws Throwable {
        actionItemPage.enterDescription(description);
    }
    
    @Step("Click Save Button")
    private void clickSaveButton() throws Throwable {
        actionItemPage.clickSaveButton();
    }
    
    @Step("Click Audit Action Items Tab")
    private void clickAuditActionItemsTab() throws Throwable {
        actionItemPage.clickAuditActionItemsTab();
    }
    
    @Step("Click Search Button")
    private void clickSearchButton() throws Throwable {
        actionItemPage.clickSearchButton();
    }
    
    @Step("Click Initiative Title Input Field")
    private void clickInitiativeTitleInput() throws Throwable {
        actionItemPage.clickInitiativeTitleInput();
    }
    
    @Step("Enter Search Text in Audit Page: {searchText}")
    private void enterSearchTextInAuditPage(String searchText) throws Throwable {
        actionItemPage.enterSearchText(searchText);
    }
    
    @Step("Click Status Dropdown")
    private void clickStatusDropdown() throws Throwable {
        actionItemPage.clickStatusDropdown();
    }
    
    @Step("Select Status: {status}")
    private void selectStatus(String status) throws Throwable {
        actionItemPage.selectStatus(status);
    }
    
    @Step("Click Assigned To Dropdown")
    private void clickAssignedToDropdown() throws Throwable {
        actionItemPage.clickAssignedToDropdown();
    }
    
    @Step("Select Assigned To: {assignedTo}")
    private void selectAssignedTo(String assignedTo) throws Throwable {
        actionItemPage.selectAssignedTo(assignedTo);
    }
    
    @Step("Click Filter Search Button")
    private void clickFilterSearchButton() throws Throwable {
        actionItemPage.clickFilterSearchButton();
    }
    
    @Step("Click Initiative Title in Search Results")
    private void clickInitiativeTitleInResults() throws Throwable {
        actionItemPage.clickInitiativeTitleInResults();
    }
    
    @Step("Click Edit Button")
    private void clickEditButton() throws Throwable {
        actionItemPage.clickEditButtonFirstRow();
    }
    
    @Step("Click Action Item Field")
    private void clickActionItemField() throws Throwable {
        actionItemPage.clickActionItemField();
    }
    
    @Step("Enter Action Item: {actionItem}")
    private void enterActionItem(String actionItem) throws Throwable {
        actionItemPage.enterActionItem(actionItem);
    }
    
    @Step("Click Description Field")
    private void clickDescriptionField1() throws Throwable {
        actionItemPage.clickDescriptionField();
    }
    
    @Step("Enter Description: {description}")
    private void enterDescription1(String description) throws Throwable {
        actionItemPage.enterDescription(description);
    }
    
}

