package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Initiative Reallocation Management Module
 * 
 * This class contains all locators used in Initiative Reallocation page automation.
 * Following Page Object Model (POM) design pattern with:
 * - Static locators for reusable elements
 * - Dynamic helper methods for parameterized locators
 * - Alternative locators for robust element finding
 * 
 * DESIGN PRINCIPLES:
 * 1. Keep all static/reusable locators here
 * 2. Use helper methods (getDynamic*) for parameterized locators
 * 3. Group related locators with clear comments
 * 4. Provide alternative locators for unstable elements
 * 
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeReallocationPageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative Management navigation element */
    public static By initiativeManagementNav = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']");
    
    /** Initiative Reallocation card/button */
    public static By initiativeReallocationCard = By.xpath("//*[@id=\"children-panel-container\"]//p[contains(text(),'Initiative Reallocation')] | //p[contains(text(),'Initiative Reallocation')]");
    
    // ==================== PAGE HEADERS ====================
    
    /** Initiative Reallocation page header */
    public static By initiativeReallocationPageHeader = By.xpath("//h5[contains(text(),'Initiative Reallocation')] | //h2[contains(text(),'Initiative Reallocation')]");
    
    // ==================== FILTER FIELDS ====================
    
    /** Select Current Approver dropdown field*/
    public static By currentApproverDropdown = By.xpath("//span[contains(@class,'ms-Dropdown-title') and normalize-space()='Select Current Approver']");
    
    /** Current Approver dropdown value - Shahu.D */
    public static By currentApproverValue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    /** Nature of Initiative dropdown field - Shahu.D */
    public static By natureOfInitiativeDropdown = By.xpath("//*[@id=\"natureOfInitiativeId\"]/span[2] | //*[@id='natureOfInitiativeId']/span[2] | //span[@id='natureOfInitiativeId-option'] | //div[@id='natureOfInitiativeId']");
    
    /** Nature of Initiative dropdown value - Shahu.D */
    public static By natureOfInitiativeValue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    /** Business Group dropdown field - Shahu.D */
    public static By businessGroupDropdown = By.xpath("//*[@id=\"businessGroupId\"]/span[2] | //*[@id='businessGroupId']/span[2] | //span[@id='businessGroupId-option'] | //div[@id='businessGroupId']");
    
    /** Business Group dropdown value - Shahu.D */
    public static By businessGroupValue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public static By initiativetitle = By.xpath("//input[@id='initiativeTitle']");
    
    /** Next button - Shahu.D */
    public static By nextButton = By.xpath("//button[.//span[text()='Next']] | //*[@id=\"id__873\"] | //button[@id='id__873'] | //button[contains(text(),'Next')] | //span[text()='Next']/ancestor::button");
    
    // ==================== INITIATIVE DETAILS TABLE ====================
    
    /** Initiative Details first row - Shahu.D */
    public static By initiativeDetailsFirstRow = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[1]/div/div[2]/div/table/tbody/tr[1]/td | //table//tbody//tr[1]//td | //div[@role='row'][1]");
    
    /** Approver dropdown field - Shahu.D */
    public static By approverDropdown = By.xpath("//span[contains(@class,'ms-Dropdown-title') and normalize-space()='Select Approver']");
    
    /** Approver dropdown value - Shahu.D */
    public static By approverDropdownValue = By.xpath("//div[@role='listbox']//span[contains(@class,'ms-Dropdown-optionText')]");
    
    /** Approver selection checkbox - Shahu.D */
    public static By approverCheckbox = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[2]/table/tbody/tr[1]/td[6]/div/label/div/i | //table//tbody//tr[1]//td[6]//label//input[@type='checkbox'] | //input[@type='checkbox']");
    
    /** Save button - Shahu.D */
    public static By saveButton = By.xpath("//*[@id=\"id__250\"] | //button[@id='id__250'] | //*[@id=\"id__1350\"] | //button[@id='id__1350'] | //button[contains(text(),'Save')] | //span[contains(text(),'Save')]/ancestor::button");
    
    // ==================== SEARCH ====================
    
    /** Search icon/button - Primary */
    public static By searchInput = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/img | //img[contains(@alt,'Search')]");
    
    /** Search input field */
    public static By searchInputField = By.xpath("//input[@placeholder='Search'] | //input[contains(@placeholder,'Search')] | //input[@type='search']");
    
    /** Initiative Code input field */
    public static By initiativeCodeField = By.xpath("//*[@id=\"initiativeCode\"] | //input[@id='initiativeCode']");
    
    /** Search button to execute search */
    public static By searchButton = By.xpath("//button[contains(text(),'Search')] | //button[@type='submit'] | //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[3]");
    
    // ==================== TABLE/GRID ====================
    
    /** Grid rows/records container */
    public static By gridRows = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row'] | //table//tbody//tr[@role='row'] | //div[@role='row']");
    
    /** No items message when no records found */
    public static By noItemsMessage = By.xpath("//*[contains(text(),'There are no items to show in this view.')] | //*[contains(text(),'No items')]");
    
    // ==================== DROPDOWNS ====================
    
    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");
    
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for row by initiative code
     * @param initiativeCode Initiative code to search for
     * @return By locator for the row containing the initiative code
     */
    public static By getRowByInitiativeCode(String initiativeCode) {
        return By.xpath("//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]] | //tr[.//td[contains(text(),'" + initiativeCode + "')]]");
    }
    
    /**
     * Get dynamic locator for action button in a row
     * @param rowIndex Row index (1-based)
     * @return By locator for the action button
     */
    public static By getActionButtonByRow(int rowIndex) {
        return By.xpath("//div[@role='row'][" + rowIndex + "]//button[contains(@aria-label,'Action')] | //tr[" + rowIndex + "]//button[contains(@aria-label,'Action')]");
    }
}

