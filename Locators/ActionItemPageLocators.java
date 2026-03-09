package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Action Item Management Module
 * 
 * This class contains all locators used in Action Item page automation.
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
public class ActionItemPageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative tree menu in sidebar */
    public static By initree = By.xpath("//img[@alt='Initiative Management']");
    
    /** Action Item node in navigation */
    public static By actionItemNode = By.xpath("//p[normalize-space()='Action Items']");
    
    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");
    
    // ==================== PAGE HEADERS ====================
    
    /** Main page header */
    public static By pageHeader = By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 css-uqd4be']");
    
    /** Action Item page header */
    public static By actionItemPageHeader = By.xpath("//h5[text()='Action Items']");
    
    // ==================== SEARCH FUNCTIONALITY ====================
    
    /** Search button - opens search input */
    public static By searchButton = By.xpath("//img[@aria-label='Search']");
    
    /** Initiative title search input field */
    public static By initiativeTitleSearchInput = By.xpath("//input[@id='initiativeTitle']");
    
    /** Search submit button - executes the search */
    public static By searchSubmitButton = By.xpath("//span[@id='id__32']");
    
    // ==================== EDIT INITIATIVE FUNCTIONALITY ====================
    
    /** Initiative row SVG icon (for clicking on initiative row) - Row 2 */
    public static By initiativeRowIcon = By.xpath("//tbody/tr[2]/td[1]/div[1]//*[name()='svg']");
    
    /** Edit button SVG icon - Row 2 */
    public static By editButtonIcon = By.xpath("//tbody/tr[2]/td[8]/div[1]/button[1]//*[name()='svg']");
    
    /** Priority dropdown - in edit mode */
    public static By priorityDropdown = By.xpath("//select[contains(@id,'priority') or contains(@name,'priority')] | //div[contains(@id,'priority')] | //input[contains(@id,'priority')]");
    
    /** Save button in edit mode */
    public static By saveButton = By.xpath("//button[normalize-space()='Save']");
    
    /** Searched result - first result after search */
    
    /** Edit button SVG icon - First row (after search) */
    public static By editButtonFirstRow = By.xpath("//tbody/tr[1]/td[8]/div[1]/button[1]//*[name()='svg']");
    
    /** Description field textarea - in edit mode */
    public static By descriptionField1 = By.xpath("//label[text()='Description']/following-sibling::div/textarea");
    
    // ==================== AUDIT ACTION ITEMS TAB ====================
    
    /** Audit Action Items tab/button */
    public static By auditActionItemsTab = By.xpath("//button[normalize-space()='Audit Action Items']");
    
    /** Status dropdown - in Audit Action Items page */
    public static By statusDropdown = By.xpath("//span[@id='status-option']");
    
    /** Assigned To dropdown - in Audit Action Items page */
    public static By assignedToDropdown = By.xpath("//span[@id='assignedTo-option']");
    
    /** Search button - executes search after selecting filter values */
    public static By filterSearchButton = By.xpath("//span[@id='id__32']");
    
    /** Initiative title in search results - clickable element */
    public static By initiativeTitleInResults = By.xpath("//div[@class='text-wrap']");
    
    /** Searched result - first result after search (for backward compatibility) */
    public static By searchedResult = By.xpath("//div[@class='text-wrap']");
    
    /** Edit button - SVG icon inside button with aria-label='Edit' */
    public static By editButton = By.xpath("//button[@aria-label='Edit']//*[name()='svg']");
    
    /** Action Item field input - following label with for='TextField227' */
    public static By actionItemField = By.xpath("//label[@for='TextField227']/following::input[1]");
    
    /** Description field - following sibling div of element with id='TextFieldLabel168' */
    public static By descriptionField = By.xpath("//*[@id=\"TextField479\"]");
    
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for any option by normalized text
     * @param optionText The option text to search for
     * @return By locator for the option
     */
    public static By getDynamicOptionByText(String optionText) {
        return By.xpath("//*[normalize-space(text())='" + optionText + "']");
    }
    
    /**
     * Get dynamic locator for Status dropdown option
     * @param statusValue The status value (e.g., "Active", "Inactive", "Pending")
     * @return By locator for the status option
     */
    public static By getDynamicStatusOption(String statusValue) {
        return By.xpath("//*[normalize-space(text())='" + statusValue + "' and (contains(@role,'option') or contains(@class,'option') or contains(@class,'menu-item'))] | //li[normalize-space(text())='" + statusValue + "'] | //div[normalize-space(text())='" + statusValue + "' and contains(@role,'option')] | //span[normalize-space(text())='" + statusValue + "']");
    }
    
    /**
     * Get dynamic locator for Assigned To dropdown option
     * @param assignedToValue The assigned to value (e.g., "John Doe", "Jane Smith")
     * @return By locator for the assigned to option
     */
    public static By getDynamicAssignedToOption(String assignedToValue) {
        return By.xpath("//*[normalize-space(text())='" + assignedToValue + "' and (contains(@role,'option') or contains(@class,'option') or contains(@class,'menu-item'))] | //li[normalize-space(text())='" + assignedToValue + "'] | //div[normalize-space(text())='" + assignedToValue + "' and contains(@role,'option')] | //span[normalize-space(text())='" + assignedToValue + "']");
    }
  
}

