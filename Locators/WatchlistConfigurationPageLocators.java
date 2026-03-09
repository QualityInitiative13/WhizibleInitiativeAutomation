
package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Watchlist Configuration Module
 * 
 * This class contains all locators used in Watchlist Configuration page automation.
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
public class WatchlistConfigurationPageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative tree menu in sidebar */
    public static By initree = By.xpath("//img[@alt='Initiative Management']");
    
    /** Watchlist Configuration node in navigation */
    public static By watchlistConfigurationNode = By.xpath("//p[normalize-space()='Watch List Configuration']");
    
    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");
    
    // ==================== PAGE HEADERS ====================
    
    /** Main page header */
    public static By pageHeader = By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 css-uqd4be']");
    
    /** Watchlist Configuration page header */
    public static By watchlistConfigurationPageHeader = By.xpath("//h5[text()='Watchlist Configuration']");
    
    // ==================== SEARCH FUNCTIONALITY ====================
    
    /** Search button - opens search input */
    public static By searchButton = By.xpath("//img[@aria-label='Search']");
    
    /** Search submit button - executes search after entering filter values */
    public static By searchSubmitButton = By.xpath("//span[@id='id__267']");
    
    /** After filter search button - executes search after applying filters */
    public static By afterFilterSearchButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[4]/button[3]");
    
    /** Alternative locator for After filter search button - by button index in container */
    public static By afterFilterSearchButtonAlternative = By.xpath("//div[contains(@class,'MuiGrid') or contains(@class,'container')]//button[3] | //button[contains(@aria-label,'Search') or contains(text(),'Search')] | //button[position()=3]");
    
    // ==================== FILTER DROPDOWNS ====================
    
    /** Nature of Initiative filter dropdown */
    public static By natureOfInitiativeDropdown = By.xpath("//span[@id='natureOfInitiativeId-option']");
    
    /** Current stage filter dropdown */
    public static By currentStageDropdown = By.xpath("//span[@id='stageOfApprovalId-option']");
    
    // ==================== SEARCH INPUT FIELDS ====================
    
    /** Initiative Code input field */
    public static By initiativeCodeField = By.xpath("//input[@id='DemandCode']");
    
    // ==================== TABLE ACTIONS ====================
    
    /** Edit button in search results table - first row, column 8 */
    public static By editButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/table/tbody/tr/td[8]");
    
    /** Alternative locator for Edit button - by table row and column */
    public static By editButtonAlternative = By.xpath("//table/tbody/tr[1]/td[8] | //tbody/tr/td[8] | //td[8]//button | //td[8]//*[contains(@aria-label,'Edit') or contains(@title,'Edit')]");
    
    /** Checkbox in table - row 33, column 2 (using relative xpath) */
    public static By checkbox = By.xpath("//tbody/tr[33]/td[2]/span[1]/input[1]");
    
    /** Alternative locator for Checkbox - absolute xpath (fallback) */
    public static By checkboxAlternative = By.xpath("/html/body/div[3]/div[3]/div[3]/table/tbody/tr[33]/td[2]/span/input");
    
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
     * Get dynamic locator for Nature of Initiative dropdown option
     * @param natureValue The nature of initiative value (e.g., "Strategic", "Operational")
     * @return By locator for the nature of initiative option
     */
    public static By getDynamicNatureOfInitiativeOption(String natureValue) {
        return By.xpath("//*[normalize-space(text())='" + natureValue + "' and (contains(@role,'option') or contains(@class,'option') or contains(@class,'menu-item'))] | //li[normalize-space(text())='" + natureValue + "'] | //div[normalize-space(text())='" + natureValue + "' and contains(@role,'option')] | //span[normalize-space(text())='" + natureValue + "']");
    }
    
    /**
     * Get dynamic locator for Current stage dropdown option
     * @param stageValue The current stage value (e.g., "Draft", "Approved", "Pending")
     * @return By locator for the current stage option
     */
    public static By getDynamicCurrentStageOption(String stageValue) {
        return By.xpath("//*[normalize-space(text())='" + stageValue + "' and (contains(@role,'option') or contains(@class,'option') or contains(@class,'menu-item'))] | //li[normalize-space(text())='" + stageValue + "'] | //div[normalize-space(text())='" + stageValue + "' and contains(@role,'option')] | //span[normalize-space(text())='" + stageValue + "']");
    }
    
    /**
     * Get dynamic locator for checkbox by Employee Name
     * Finds the row containing the employee name and returns locator for checkbox in column 2
     * @param employeeName The employee name to search for in the table
     * @return By locator for the checkbox in the row containing the employee name
     */
    public static By getDynamicCheckboxByEmployeeName(String employeeName) {
        return By.xpath("//table/tbody/tr[td[normalize-space(text())='" + employeeName + "']]/td[2]//span/input[@type='checkbox'] | " +
                       "//table/tbody/tr[td[normalize-space(text())='" + employeeName + "']]/td[2]//input[@type='checkbox'] | " +
                       "//tbody/tr[td[contains(text(),'" + employeeName + "')]]/td[2]//span/input | " +
                       "//tbody/tr[td[contains(text(),'" + employeeName + "')]]/td[2]//input | " +
                       "//tr[td[normalize-space()='" + employeeName + "']]/td[2]//span/input | " +
                       "//tr[td[normalize-space()='" + employeeName + "']]/td[2]//input");
    }
  
}

