package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Initiative Activate Snooze Management Module
 * 
 * This class contains all locators used in Initiative Activate Snooze page automation.
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
 * Updated by Shahu.D
 * 
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeActivateSnoozePageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative Management navigation element - Updated by Shahu.D */
    public static By initiativeManagementNav = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management'] | //img[@alt='Initiative Management'] | //div[contains(@class,'navigation')]//img[contains(@alt,'Initiative')]"); // Updated by Shahu.D
    
    /** Initiative Activate Snooze card/button - Updated by Shahu.D */
    public static By initiativeActivateSnoozeCard = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[10]/p | //*[@id=\"children-panel-container\"]//p[contains(text(),'Activate Snooze')] | //p[contains(text(),'Activate Snooze')] | //p[contains(text(),'activate snooze')]"); // Updated by Shahu.D
    
    // ==================== PAGE HEADERS ====================
    public static By searchbutton = By.xpath("//button[.//span[normalize-space()='Search']]");
    /** Initiative Activate Snooze page header - Updated by Shahu.D */
    public static By initiativeActivateSnoozePageHeader = By.xpath("//h5[contains(text(),'Activate Snooze')] | //h2[contains(text(),'Activate Snooze')] | //h2[contains(text(),'activate snooze')]"); // Updated by Shahu.D
    
    // ==================== FILTER FIELDS ====================
    
    /** Search option icon - Updated by Shahu.D */
    public static By searchOptionIcon = By.xpath("//*[@id=\"topActions\"]/div[2]/img | //img[contains(@alt,'Search')] | //img[contains(@src,'search')]"); // Updated by Shahu.D
    
    /** Active dropdown field - Updated by Shahu.D */
    public static By activeDropdown = By.xpath("//*[@id=\"isActive-option\"] | //*[@id='isActive-option'] | //span[@id='isActive-option'] | //div[@id='isActive']"); // Updated by Shahu.D
    
    /** Active dropdown value "Yes" - Updated by Shahu.D */
    public static By activeDropdownValueYes = By.xpath("//*[@id=\"isActive-list1\"]/span/span | //*[@id='isActive-list1']/span/span | //*[@id='isActive-list1']//span[contains(text(),'Yes')] | //li[contains(@id,'isActive-list1')]//span[contains(text(),'Yes')]"); // Updated by Shahu.D
    
    /** Nature of Initiative dropdown field - Updated by Shahu.D */
    public static By natureOfInitiativeDropdown = By.xpath("//*[@id=\"natureOfInitiativeId\"]/span[2] | //*[@id='natureOfInitiativeId']/span[2] | //span[@id='natureOfInitiativeId-option'] | //div[@id='natureOfInitiativeId']"); // Updated by Shahu.D
    
    /** Nature of Initiative dropdown value "StartUp Application Processing" - Updated by Shahu.D */
    public static By natureOfInitiativeDropdownValue = By.xpath("//div[@role='listbox']//button[@role='option']"); // Updated by Shahu.D
    
    /** Initiative Title text field - Updated by Shahu.D */
    public static By initiativeTitleField = By.xpath("//*[@id=\"initiativeTitle\"] | //div[@class='css-4io43t'] | //input[@id='initiativeTitle'] | //input[@name='initiativeTitle'] | //input[contains(@placeholder,'Title')] | //input[contains(@placeholder,'title')]"); // Updated by Shahu.D
    
    /** Initiative Code text field - Updated by Shahu.D */
    public static By initiativeCodeField = By.xpath("//*[@id=\"demandCode\"] | //input[@id='demandCode'] | //input[@name='demandCode'] | //input[@id='initiativeCode'] | //input[@name='initiativeCode']"); // Updated by Shahu.D
    
    /** Initiation Date field - Updated by Shahu.D */
    public static By initiationDateField = By.xpath("//*[@id=\"initiationDate\"]/div/div/div/div/i | //*[@id='initiationDate']//i | //div[@id='initiationDate'] | //input[@id='initiationDate']"); // Updated by Shahu.D
    
    /** Stage dropdown field - Updated by Shahu.D */
    public static By stageDropdown = By.xpath("//*[@id=\"StageID\"]/span[2] | //*[@id='StageID']/span[2] | //span[@id='StageID-option'] | //div[@id='StageID']"); // Updated by Shahu.D
    
    /** Stage dropdown value "Due Diligence (Preliminary)" - Updated by Shahu.D */
    public static By stageDropdownValue = By.xpath("//div[@role='listbox']//button[@role='option']"); // Updated by Shahu.D
    
    /** Created By dropdown field - Updated by Shahu.D */
    public static By createdByDropdown = By.xpath("//*[@id=\"createdBy\"]/span[2] | //*[@id='createdBy']/span[2] | //span[@id='createdBy-option'] | //div[@id='createdBy']"); // Updated by Shahu.D
    
    /** Created By dropdown value "Gayatri" - Updated by Shahu.D */
    public static By createdByDropdownValue = By.xpath("//*[@id=\"createdBy-list40\"]/span/span | //*[@id='createdBy-list40']/span/span | //*[@id='createdBy-list40']//span[contains(text(),'Gayatri')] | //li[contains(@id,'createdBy-list40')]//span"); // Updated by Shahu.D
    
    /** Search button - Updated by Shahu.D */
    public static By searchButton = By.xpath(
            // Try to locate by visible text "Search" first (covers button + span wrappers) - Updated by Shahu.D
            "//button[.//span[normalize-space()='Search']] | " +
            "//span[normalize-space()='Search'] | " +
            // Then try explicit dynamic IDs observed in UI - Updated by Shahu.D
            "//span[@id='id__642'] | " +
            "//*[@id=\"id__7028\"] | //span[@id='id__7028'] | //button[@id='id__7028'] | " +
            // Generic fallbacks - Updated by Shahu.D
            "//button[contains(text(),'Search')] | //button[@type='submit'] | //button[contains(@aria-label,'Search')] | " +
            "//span[contains(@id,'id__') and contains(@class,'button')]"
    ); // Updated by Shahu.D
    
    // ==================== INITIATIVE TABLE ====================
    
    /** Initiative table rows - Updated by Shahu.D */
    public static By initiativeTableRows = By.xpath("//div[@role='row'] | //table//tbody//tr | //tr[contains(@class,'row')]"); // Updated by Shahu.D

    /** Initiative table data rows (tbody only) - Updated by Shahu.D */
    public static By initiativeTableDataRows = By.xpath("//table//tbody//tr"); // Updated by Shahu.D

    /** Pagination next button - Updated by Shahu.D */
    public static By paginationNextButton = By.xpath(
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2] | " +
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]//*[name()='svg'] | " +
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]//*[name()='svg']//*[name()='path'] | " +
            "//button[contains(@aria-label,'Next') or contains(@title,'Next')]"
    ); // Updated by Shahu.D
    
    /** Activate Snooze button/link - Updated by Shahu.D */
    public static By activateSnoozeButton = By.xpath("//button[contains(text(),'Activate Snooze')] | //button[contains(@aria-label,'Activate Snooze')] | //a[contains(text(),'Activate Snooze')]"); // Updated by Shahu.D
    
    /** Snooze link in Initiatives table - Updated by Shahu.D */
    public static By snoozeLink = By.xpath(
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[6]/a | " +
            "//table//tbody//tr//td[6]//a[contains(text(),'Snooze') or contains(@href,'snooze')]"
    ); // Updated by Shahu.D

    /** Activate link in Initiatives table - Updated by Shahu.D */
    public static By activateLink = By.xpath(
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[6]/a | " +
            "//table//tbody//tr//td[6]//a[contains(text(),'Activate') or contains(@href,'activate')]"
    ); // Updated by Shahu.D
    
    // ==================== SNOOZE MODAL/DIALOG ====================
    
    /** Snooze modal/dialog - Updated by Shahu.D */
    public static By snoozeModal = By.xpath("//div[contains(@class,'modal')] | //div[contains(@class,'dialog')] | //div[contains(@role,'dialog')]"); // Updated by Shahu.D
    
    /** Snooze reason dropdown - Updated by Shahu.D */
    public static By snoozeReasonDropdown = By.xpath(
            "//select[@name='snoozeReason'] | //div[contains(@class,'dropdown')]//select | //*[@id='snoozeReason']"
    ); // Updated by Shahu.D
    
    /** Snooze duration input - Updated by Shahu.D */
    public static By snoozeDurationInput = By.xpath(
            "//input[@name='snoozeDuration'] | //input[@id='snoozeDuration'] | //input[@placeholder*='Duration' or @placeholder*='duration']"
    ); // Updated by Shahu.D
    
    /** Snooze comments textarea - Updated by Shahu.D */
    public static By snoozeCommentsTextarea = By.xpath(
            "//textarea[@name='snoozeComments'] | //textarea[@id='snoozeComments'] | //textarea[@placeholder*='Comment' or @placeholder*='comment']"
    ); // Updated by Shahu.D
    
    /** Confirm/Submit button in snooze modal - Updated by Shahu.D */
    public static By confirmSnoozeButton = By.xpath(
            "//button[contains(text(),'Confirm')] | //button[contains(text(),'Submit')] | //button[contains(text(),'Activate')] | //button[@type='submit']"
    ); // Updated by Shahu.D
    
    /** Snooze comment textarea (Comment box) - Updated by Shahu.D */
    public static By snoozeCommentBox = By.xpath(
            "//textarea[@id='TextField202'] | //*[@id=\"TextField202\"]"
    ); // Updated by Shahu.D
    
    /** Snooze duration / Number of Days input - Updated by Shahu.D */
    public static By snoozeDaysInput = By.xpath(
            "//input[@id='TextField207'] | //*[@id=\"TextField207\"] | //input[contains(@placeholder,'Days') or contains(@placeholder,'days')]"
    ); // Updated by Shahu.D
    
    /** Save button in Snooze modal - Updated by Shahu.D */
    public static By snoozeSaveButton = By.xpath(
            "//*[@id=\"id__199\"] | //button[@id='id__199'] | //button[.//span[normalize-space()='Save']] | //button[contains(text(),'Save')]"
    ); // Updated by Shahu.D
    
    /** Yes button on confirmation popup - Updated by Shahu.D */
    public static By snoozeConfirmYesButton = By.xpath(
            "//*[@id=\"id__212\"] | //button[@id='id__212'] | //button[.//span[normalize-space()='Yes']] | //button[contains(text(),'Yes')]"
    ); // Updated by Shahu.D
    
    /** Cancel button in snooze modal - Updated by Shahu.D */
    public static By cancelSnoozeButton = By.xpath("//button[contains(text(),'Cancel')] | //button[contains(@aria-label,'Cancel')]"); // Updated by Shahu.D
    
    // ==================== SUCCESS/ERROR MESSAGES ====================
    
    /** Success message - Updated by Shahu.D */
    public static By successMessage = By.xpath("//div[contains(@class,'success')] | //div[contains(@class,'alert-success')] | //div[contains(text(),'successfully')]"); // Updated by Shahu.D
    
    /** Error message - Updated by Shahu.D */
    public static By errorMessage = By.xpath("//div[contains(@class,'error')] | //div[contains(@class,'alert-error')] | //div[contains(text(),'error')]"); // Updated by Shahu.D
    
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for row by initiative code
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to search for
     * @return By locator for the row containing the initiative code
     */
    public static By getRowByInitiativeCode(String initiativeCode) {
        return By.xpath("//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]] | //tr[.//td[contains(text(),'" + initiativeCode + "')]] | //div[contains(text(),'" + initiativeCode + "')]"); // Updated by Shahu.D
    }
    
    /**
     * Get dynamic locator for snooze reason option
     * Updated by Shahu.D
     * @param reason Snooze reason text
     * @return By locator for the snooze reason option
     */
    public static By getSnoozeReasonOption(String reason) {
        return By.xpath("//option[contains(text(),'" + reason + "')] | //li[contains(text(),'" + reason + "')] | //div[contains(text(),'" + reason + "')]"); // Updated by Shahu.D
    }
}

