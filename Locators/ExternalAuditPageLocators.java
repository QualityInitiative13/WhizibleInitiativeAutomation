package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for External Audit Page / Converted Initiative module
 *
 * This class contains all locators used in External Audit page automation.
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
 * NOTE:
 * - Navigation and basic structure are created now.
 * - If you share exact XPaths for the External Audit page (menu node, header, fields),
 *   we can update these locators to match your UI precisely.
 */
public class ExternalAuditPageLocators {

    // ==================== NAVIGATION ====================

    /** Initiative Management click - new XPath provided by user */
    // Xpath provided by user: /html/body/div/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[2]/div/span[1]/span/div/img
    public static By initiativeManagement = By.xpath("//img[@alt='Initiative Management']");

    /** Initiative tree menu in sidebar (same as other modules) - kept for backward compatibility */
    public static By initree = By.xpath("//img[@alt='Initiative Management']");

    /**
     * External Audit / Converted Initiative node in navigation.
     *
     * Updated as per user-provided xpath:
     *   /html/body/div[2]/div[3]/div[8]/p
     */
    public static By externalAuditNode = By.xpath("//p[normalize-space()='External Audit']");

    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");

    // ==================== PAGE HEADERS ====================

    /** Main page header (common pattern used in other modules) */
    public static By pageHeader = By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 css-uqd4be']");

    /** External Audit page header */
    public static By externalAuditPageHeader = By.xpath("//h5[normalize-space()='External Audit']");

    // ==================== SEARCH / FILTERS ====================

    /** Search button - opens search input (reused pattern) */
    public static By searchButton = By.xpath("//img[@aria-label='Search']");

    /** Search submit button - executes search after entering filter values */
    public static By searchSubmitButton = By.xpath("//button[contains(@aria-label,'Search') or contains(text(),'Search')]");

    /** Audit Type dropdown (user-provided xpath) */
    // Xpath provided by user: //*[@id="Dropdown10"]
    public static By auditTypeDropdown = By.xpath("//span[normalize-space()='Select Audit Type']/ancestor::*[@role='combobox'][1]");

    /** Example text input field for audit / initiative code (placeholder) */
    public static By auditCodeField = By.xpath("//input[@id='AuditCode']");

    // ==================== ACTION BUTTONS ====================

    /** Add button on External Audit page */
    // Xpath provided by user: /html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[1]/button/span/span/span
    public static By addButton = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[1]/button/span/span/span");
    
    /** Save button on External Audit form */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[1]/div/button
    public static By saveButton = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[1]/div/button");

    // ==================== FORM FIELDS ====================

    /** Title field on External Audit add form */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[3]/div[2]/div/div/div/div
    public static By titleField = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[3]/div[2]/div/div/div/div");

    /** Initiative button on External Audit add form */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[3]/div[3]/div/div/button/span/i
    public static By initiativeButton = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[3]/div[3]/div/div/button/span/i");

    /** Select button (select nature of initiative) in Initiative selection table - first row, column 5 */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr[1]/td[5]
    public static By initiativeSelectButton = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr[1]/td[5]");
    
    /** Close icon for Initiative popup */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/div/div[1]/button/svg
    public static By initiativeCloseIcon = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/div[1]/button/svg");

    /** Planned Start Date label */
    // Xpath provided by user: //*[@id="DatePicker693-label"]
    public static By plannedStartDateLabel = By.xpath("//div[@id='DatePicker32-label']/span");

    /** Planned Start Date field - container/input for Planned Start Date */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[2]/div/div/div/div/div/div/div/span
    public static By plannedStartDateField = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[2]/div/div/div/div/div/div/div/span");

    /** Planned End Date label */
    // Xpath provided by user: //*[@id="DatePicker1200-label"]
    public static By plannedEndDateLabel = By.xpath("//*[@id='DatePicker1200-label']");

    /** Planned End Date field - input field for Planned End Date */
    // Try to find input field near the label
    public static By plannedEndDateField = By.xpath("//*[@id='DatePicker1200-label']/following::input[1]");

    /** Planned End Date field - alternative locator (by ID without -label suffix) */
    public static By plannedEndDateFieldAlt = By.xpath("//input[@id='DatePicker1200']");

    /** Planned End Date field - alternative locator (by ancestor form control) */
    public static By plannedEndDateFieldAlt2 = By.xpath("//*[@id='DatePicker1200-label']/ancestor::div[contains(@class,'MuiFormControl')]//input");

    /** Planned End Date field - alternative locator (find by label text containing "End") */
    public static By plannedEndDateFieldAlt3 = By.xpath("//label[contains(text(),'End') or contains(text(),'end')]/following::input[1] | //label[contains(text(),'End') or contains(text(),'end')]/..//input");

    /** Planned End Date field - alternative locator (second date input after Planned Start Date) */
    public static By plannedEndDateFieldAlt4 = By.xpath("(//input[contains(@id,'DatePicker')])[2] | (//div[contains(@id,'DatePicker')])[2]");

    /** Planned End Date field - alternative locator (by span with DatePicker1200) */
    public static By plannedEndDateFieldAlt5 = By.xpath("//*[contains(@id,'DatePicker1200')]//span | //*[@id='DatePicker1200-label']/span");
    
    /** Planned End Date field - new XPath provided by user */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[3]/div/div/div/div/div/div/div/span
    public static By plannedEndDateFieldNew = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[3]/div/div/div/div/div/div/div/span");

    /** Auditor button on External Audit add form */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[1]/div/div/button/span/i
    public static By auditorButton = By.xpath("/html/body/div[2]/div[3]/div/div[3]/form/div[4]/div[1]/div/div/button/span/i");

    /** Select Auditors button in Auditor popup */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/div/div[2]/button/span
    public static By selectAuditorsButton = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/div[2]/button/span");
    
    /** Select Auditors button - direct button locator (parent of span) */
    public static By selectAuditorsButtonDirect = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/div[2]/button");
    
    /** Auditor checkbox in Auditor selection table */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr[4]/td[5]/span/input
    public static By auditorCheckbox = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr[4]/td[5]/span/input");
    
    /** Close icon for Auditor popup */
    // Xpath provided by user: /html/body/div[2]/div[3]/div/div[3]/div/div[1]/button/svg
    public static By auditorCloseIcon = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/div[1]/button/svg");
    
    /** Toast alert message (for success/mandatory field validation) */
    public static By toastAlert = By.xpath("//div[@id='4']//div");

    // ==================== TABLE ACTIONS (PLACEHOLDERS) ====================

    /** Edit button in search results table (generic robust locator) */
    public static By editButton = By.xpath(
        "//table/tbody/tr[1]/td[8] | //tbody/tr/td[8] | //td[8]//button | " +
        "//td[8]//*[contains(@aria-label,'Edit') or contains(@title,'Edit')]");

    /** Alternative locator for Edit button (icon-based) */
    public static By editButtonAlternative = By.xpath("//button[@aria-label='Edit']//*[name()='svg']");

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
     * Get dynamic locator for Audit Type dropdown option
     * @param auditTypeValue The audit type value to select (e.g., "Internal", "External")
     * @return By locator for the audit type option
     */
    public static By getDynamicStatusOption(String auditTypeValue) {
        return By.xpath(
            "//*[normalize-space(text())='" + auditTypeValue + "' and (contains(@role,'option') or contains(@class,'option') or contains(@class,'menu-item'))] " +
            "| //li[normalize-space(text())='" + auditTypeValue + "'] " +
            "| //div[normalize-space(text())='" + auditTypeValue + "' and contains(@role,'option')] " +
            "| //span[normalize-space(text())='" + auditTypeValue + "']"
        );
    }
    
     public static  By firstAuditTypeOption1 =
    	    By.xpath("//div[@role='listbox']//button[@role='option'][2]"); 
    
    public static  By titleInput  =
      By.xpath("//input[@placeholder='Enter title']");    
    
    public static  By link  =
    	      By.xpath("//div[@class='audit-form']//div[3]//div[1]//div[1]//button[1]//span[1]");    
    
    
    public static  By first =
  	      By.xpath("//body[1]/div[2]/div[3]/div[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[5]");
    
    public static  By auditor =
    	      By.xpath(" //body[1]/div[2]/div[3]/div[1]/div[3]/form[1]/div[4]/div[1]/div[1]/div[1]/button[1]/span[1]/i[1]");
    
    
    
    public static  By check =
  	      By.xpath("//tbody/tr[1]/td[5]/span[1]");
  
    
    public static  By select =
    	      By.xpath("//button[.//span[normalize-space()='Select Auditors']]");
    
 
    public static  By plannedIn =
  	      By.xpath("//div[@role='combobox' and contains(@aria-label,'planned Start date')]");
    
    
    public static  By plannedout =
    	      By.xpath("//span[normalize-space()='Select Planned End Date']");
    
  
    public static  By save =
  	      By.xpath("//button[@type='submit']");

    
    public static  By  edit =
    	      By.xpath("//tbody/tr[1]/td[7]/button[1]//*[name()='svg']");
    
    
    public static  By  duration =
  	      By.xpath("//input[@aria-label='Duration']");
  
    
    public static  By  history =
    	      By.xpath("//button[@role='tab' and normalize-space()='History']");
    
    public static  By  delelte =
  	      By.xpath("//tbody/tr[1]/td[7]/button[2]//*[name()='svg']//*[name()='path' and contains(@d,'M6 19c0 1.')]");
  
    public static  By  yes =
    	      By.xpath(" //div[@id='fluent-default-layer-host']//span[1]//button[1]");
    
 // History table rows
    public static By historyRows =
        By.xpath("//table//tbody/tr");

    // Next page button
    public static By nextPageBtn =
        By.xpath("//button[.//svg[@data-testid='ArrowForwardIcon']]");

    // Disabled Next button
    public static By nextPageDisabled =
        By.xpath("//button[@disabled]//svg[@data-testid='ArrowForwardIcon']");
}


