package Locators;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object Locators for Initiative Management Module
 * 
 * This class contains all locators used in Initiative page automation.
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
 * @version 2.0 - Refactored for consistency
 */
public class InitiativePageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative tree menu in sidebar */
    //public static By initree = By.xpath("//div[@class='pro-sidebar collapsed']");
    public static By initree = By.xpath("//img[@alt='Initiative Management']");
    
    /** Initiative filter/detail section */
    public static By initreec = By.xpath("//div[@class='filter-detail d-flex align-items-center justify-content-start']");
    
    /** Sidebar collapsed menu trigger */
    public static By hoverMenuTrigger = By.xpath("//div[contains(@class,'pro-sidebar') and contains(@class,'collapsed')]");
    
    /** Initiative option arrow (expand/collapse) */
    public static By initiativeOption = By.xpath("//span[contains(@class,'pro-arrow')]");
    
    /** Initiative node in navigation */
   // public static By initiativeNode = By.xpath("//*[normalize-space(text())='Initiative']");
    public static By initiativeNode = By.xpath("//div[@id='children-panel-container']/div[3]/div/p");
    
    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");
    
    // ==================== PAGE HEADERS ====================
    
    /** Main page header */
    public static By pageHeader = By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 css-uqd4be']");
    
    /** Initiative page header */
    public static By Pageheaderini = By.xpath("//h5[text()='Initiative']");
    
    // ==================== FORM FIELDS ====================
    
    /** Add Initiative button */
    public static By AddIni = By.xpath("//span[@id='id__2']");
    public static By AddIniAlt1 = By.xpath("//button[@id='id__2' or @name='Add Initiative']");
    public static By AddIniAlt2 = By.xpath("//button[contains(.,'Add') or .//span[contains(.,'Add')]]");
    public static By AddIniAlt3 = By.xpath("//span[contains(normalize-space(),'Add')]/ancestor::button");
    public static By[] addButtonLocators = {
        AddIni,
        AddIniAlt1,
        AddIniAlt3,
        AddIniAlt2
    };

    /** Initiative Title input field */
 //   public static By IniTitle = By.xpath("//input[@id='TextField5']");
    
    /** Initiative Description textarea */
   public static By iniDescription = By.xpath("//textarea[@id='TextField15']");
    
    /** Business Group dropdown */
//    public static By IniBG = By.xpath("//span[@id='Dropdown20-option']");
    
    /** Operating Unit dropdown */
    public static By IniOU = By.xpath("//span[@id='Dropdown21-option']");
//    
    /** Start Date picker */
    
    
//    public static By startdate = By.xpath("//input[@id='DatePicker24-label']");
    
    /** End Date picker */
//    public static By enddate = By.xpath("//input[@id='DatePicker31-label']");
    
    /** Save as Draft button */
 //   public static By savedraft = By.xpath("//button[text()='Save as Draft']");
    
    // ==================== SUBMIT & MODAL ====================
    
    /** Primary Submit button */
 //   public static By Submit = By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/span[7]");
    
    /** Alternative Submit button locators */
 //   public static By SubmitAlt1 = By.xpath("//button[.//svg[@data-testid='SendIcon']]");
 //   public static By SubmitAlt2 = By.xpath("//span[contains(normalize-space(),'Submit')]");
 //   public static By SubmitAlt3 = By.xpath("//span[contains(text(),'Submit') and .//svg]");
    
    /** Modal popup container */
 //   public static By modalPopup = By.xpath("//div[@class='modal-105']");
    
    /** Submit comments container */
 //   public static By submitCommentsContainer = By.xpath("//div[@class='modal-105']");
    
    /** Additional notes textarea in modal */
  //  public static By additionalNotes = By.xpath("//div[@class='modal-105']//textarea");
    
    /** Popup Submit button - finds button containing span */
//    public static By popSubmit = By.xpath("//span[@id='id__296']/ancestor::button | //button[.//span[@id='id__296']]");
    
    /** Alternative popup Submit button locators */
 //   public static By popSubmitAlt1 = By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button--primary')]");
 //   public static By popSubmitAlt2 = By.xpath("//div[@class='modal-105']//button[contains(text(),'Submit')]");
 //   public static By popSubmitAlt3 = By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button')]");
//    public static By popSubmitAlt4 = By.xpath("//button[@id='id__150']");
//    public static By popSubmitAlt5 = By.xpath("//span[@id='id__296']");
    
    // ==================== FILTERS & SEARCH ====================
    
    /** Draft filter button */
  //  public static By draftFilter = By.xpath("//span[normalize-space()='Draft']");
    
    /** Inbox filter button - Primary */
 //   public static By inboxFilter = By.xpath("//span[id='FltrCountInbox'] | //span[normalize-space()='Inbox'] | //button[contains(.,'Inbox')]");
    
    /** Inbox filter button - Alternative locators */
//    public static By inboxFilterAlt1 = By.xpath("//span[id='FltrCountInbox']");
//    public static By inboxFilterAlt2 = By.xpath("//button[contains(.,'Inbox')]");
//    public static By inboxFilterAlt3 = By.xpath("//span[normalize-space()='Inbox']/parent::button");
    
    /** Watchlist filter button - Primary */
//    public static By watchlistFilter = By.xpath("//span[id='FltrCountWatchlist'] | //span[normalize-space()='Watchlist'] | //button[contains(.,'Watchlist')]");
    
    /** Watchlist filter button - Alternative locators */
 //   public static By watchlistFilterAlt1 = By.xpath("//span[id='FltrCountWatchlist']");
 //   public static By watchlistFilterAlt2 = By.xpath("//button[contains(.,'Watchlist')]");
 //   public static By watchlistFilterAlt3 = By.xpath("//span[normalize-space()='Watchlist']/parent::button");
    
    /** Search input field */
//    public static By searchInput = By.xpath("//input[@placeholder='Search']");
    
    // ==================== RECORDS & ALERTS ====================
    
    /** Total records count */
  //  public static By totalRecords = By.xpath("//span[contains(text(),'Total')]");
    
    /** Visible records count */
 //   public static By countRecords = By.xpath("//div[@class='count-info']");
    
    /** Inbox count displayed on filter badge */
//    public static By inboxCount = By.xpath("//span[id='FltrCountInbox'] | //span[normalize-space()='Inbox']/following-sibling::span | //button[contains(.,'Inbox')]//span[contains(@class,'count')]");
    
    /** Watchlist count displayed on filter badge */
//    public static By watchlistCount = By.xpath("//span[id='FltrCountWatchlist'] | //span[normalize-space()='Watchlist']/following-sibling::span | //button[contains(.,'Watchlist')]//span[contains(@class,'count')]");
    
    /** Grid rows/records in the table */
//    public static By gridRows = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row'] | //table//tbody//tr[@role='row']");
    
    /** Grid data rows (excluding headers) */
//    public static By gridDataRows = By.xpath("//div[@role='gridcell' and @col-id] | //tbody//tr[@role='row' and not(contains(@class,'header'))]");
    
    /** Pagination info */
//    public static By paginationInfo = By.xpath("//div[contains(@class,'pagination')]//span[contains(text(),'of')]");
    
    /** Records per page dropdown */
//    public static By recordsPerPageDropdown = By.xpath("//select[contains(@class,'page-size')] | //div[contains(@class,'page-size')]//select");
    
    /** Pagination forward/next page button */
//    public static By paginationForwardButton = By.xpath("//svg[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium css-vubbuv']");
          
    
    /** Pagination backward/previous page button */
    public static By paginationBackwardButton = By.xpath(
            "//button[.//svg[@data-testid='ArrowBackIcon']] | " +
            "//svg[@data-testid='ArrowBackIcon']/parent::button | " +
            "//svg[@data-testid='ArrowBackIcon']/ancestor::button | " );
            
    
    /** Toast alert message */
    public static By toastAlert = By.xpath("//div[contains(@class,'Toastify__toast')]");
    
    // ==================== CLOSE BUTTONS ====================
    
    /** Close button (standard) */
    public static By closeButton = By.xpath("//button[text()='×' or contains(text(),'×') or normalize-space()='×']");
    
    /** Close button by style */
    public static By closeButtonByStyle = By.xpath("//button[contains(@style, 'border-radius: 50%') and contains(text(),'×')]");
    
    /** Close button by text */
    public static By closeButtonByText = By.xpath("//button[contains(text(),'×')]");
    
    // ==================== MODAL SUBMIT LOCATORS (FALLBACKS) ====================
    
    /** Array of fallback locators for modal submit button - Try in order for reliability */
 /*   public static By[] modalSubmitButtonLocators = {
        By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button--primary')]"), // Most common
        By.xpath("//div[@class='modal-105']//button[contains(text(),'Submit')]"),
        By.xpath("//div[@class='modal-105']//button[contains(.,'Submit')]"),
        By.xpath("//div[@class='modal-105']//button"),  // ANY button in modal (aggressive)
        popSubmitAlt1,
        popSubmitAlt2,
        popSubmitAlt3
    };
   */ 
    /** All buttons in modal (for debugging) */
   // public static By allModalButtons = By.xpath("//div[@class='modal-105']//button");
    
    /** All spans with ID in modal (for debugging) */
  //  public static By allModalSpans = By.xpath("//div[@class='modal-105']//span[contains(@id,'id__')]");
    
    // ==================== DATE PICKER ALTERNATIVE LOCATORS ====================
    
    /** Alternative start date locator (without -label suffix) */
 //   public static By startdateAlt = By.xpath("//input[@id='DatePicker24']");
    
    /** Start date by placeholder */
//    public static By startdateByPlaceholder = By.xpath("//input[contains(@placeholder,'start') or contains(@placeholder,'Start')]");
    
    /** Alternative end date locator (without -label suffix) */
 //   public static By enddateAlt = By.xpath("//input[@id='DatePicker31']");
    
    /** Second date input (for end date) */
  //  public static By secondDateInput = By.xpath("(//input[@type='text' and contains(@id,'DatePicker')])[2]");
    
    /** End date by placeholder */
//    public static By enddateByPlaceholder = By.xpath("//input[contains(@placeholder,'end') or contains(@placeholder,'End')]");
    
    /** End date near label */
 //   public static By enddateNearLabel = By.xpath("//label[contains(text(),'End') or contains(text(),'end')]/following-sibling::input | //label[contains(text(),'End') or contains(text(),'end')]/..//input");
    
    // ==================== INBOX & WATCHLIST COUNT LOCATORS ====================
    
    /** Array of inbox count locators (fallback strategies) */
 /*   public static By[] inboxCountLocators = {
        By.xpath("//span[id='FltrCountInbox']"),
        By.xpath("//span[normalize-space()='Inbox']/following-sibling::span"),
        By.xpath("//span[normalize-space()='Inbox']/..//span[contains(@class,'count')]"),
        By.xpath("//span[normalize-space()='Inbox']/..//span[contains(@class,'badge')]"),
        By.xpath("//button[contains(.,'Inbox')]//span[contains(@class,'count')]"),
        By.xpath("//button[contains(.,'Inbox')]")  // Try button itself
    };
 */   
    /** Array of watchlist count locators (fallback strategies) */
  /*  public static By[] watchlistCountLocators = {
        By.xpath("//span[id='FltrCountWatchlist']"),
        By.xpath("//span[normalize-space()='Watchlist']/following-sibling::span"),
        By.xpath("//span[normalize-space()='Watchlist']/..//span[contains(@class,'count')]"),
        By.xpath("//span[normalize-space()='Watchlist']/..//span[contains(@class,'badge')]"),
        By.xpath("//button[contains(.,'Watchlist')]//span[contains(@class,'count')]")
    };
 */   
    // ==================== GRID ROW LOCATORS ====================
    
    /** Array of grid row locators (try in order for compatibility) */
  /*  public static By[] gridRowLocators = {
        By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr/td/div/p"),
        By.xpath("//div[@role='row' and contains(@class,'ag-row')]"),
        By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
        By.xpath("//table//tbody//tr[@role='row']"),
        By.xpath("//div[@role='gridcell']/../.."),
        By.xpath("//div[contains(@class,'data-grid')]//div[@role='row']"),
        By.xpath("//div[contains(@class,'ag-row')]"),  // Simplified AG Grid
        By.xpath("//tr[contains(@class,'ag-row')]")    // Table-based AG Grid
    };
  */  
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for NOI (Number of Initiatives) option by value
     * @param noiValue The NOI value text to search for
     * @return By locator for the specific NOI option
     */
 //   public static By getDynamicNOIOption(String noiValue) {
 //       return By.xpath("//div[@class='MuiBox-root css-ah0zvi']//td[normalize-space(text())='" + noiValue + "']");
 //   }
    
    /**
     * 

 dynamic locator for any option by normalized text
     * @param optionText The option text to search for
     * @return By locator for the option
     */
   // public static By getDynamicOptionByText(String optionText) {
    //    return By.xpath("//*[normalize-space(text())='" + optionText + "']");
   // }
    
    /**
     * Get dynamic locator for Business Group dropdown option
     * @param bgName The Business Group name
     * @return By locator for the BG option
     */
  //  public static By getDynamicBGOption(String bgName) {
  //      return By.xpath("//*[normalize-space(text())='" + bgName + "']");
 //   }
    
    /**
     * Get dynamic locator for Operating Unit dropdown option
     * @param ouName The Operating Unit name
     * @return By locator for the OU option
     */
  //  public static By getDynamicOUOption(String ouName) {
  //      return By.xpath("//*[normalize-space(text())='" + ouName + "']");
  //  }
    
    /**
     * Get dynamic locator for textarea by ID
     * @param textareaId The ID of the textarea
     * @return By locator for the textarea
     */
  //  public static By getDynamicTextareaById(String textareaId) {
 //       return By.xpath("//textarea[@id='" + textareaId + "']");
  //  }
    
    /**
     * Get dynamic locator for modal by xpath
     * @param modalXPath The XPath string for modal
     * @return By locator for the modal
     */
   // public static By getDynamicModalByXPath(String modalXPath) {
  //      return By.xpath(modalXPath);
  //  }
    
    /**
     * Get dynamic modal close button
     * @return By locator for modal close button
     */
  //  public static By getModalCloseButton() {
 //       return By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'ms-Button--icon')]");
  //  }
    
    /**
     * Get dynamic modal submit button with text
     * @param submitText The text on the submit button (e.g., "Submit")
     * @return By locator for modal submit button
     */
  /*  public static By getDynamicModalSubmitButton(String submitText) {
        return By.xpath("//button[contains(@class, 'ms-Button--primary') and .//span[normalize-space(text())='" + submitText + "']]");
    }
  */  
    // ==================== GETTER METHODS (Deprecated - Use direct access) ====================
    
    /**
     * Get Initiative Title locator
     * @return By locator for Initiative Title field
     * @deprecated Use InitiativePageLocators.IniTitle directly
     */
  //  @Deprecated
   // public static By getIniTitle() {
  //      return IniTitle;
  //  }
    
    /**
     * Get Additional Notes locator
     * @return By locator for Additional Notes textarea
     * @deprecated Use InitiativePageLocators.additionalNotes directly
     */
 //   @Deprecated
 //   public static By getAdditionalNotes() {
 //       return additionalNotes;
 //  }
    
    public static By inboxSearchButtonWithdrawn =
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div/div/div/div/div[5]/button[3]/span/span/span"); 
    
    
    public static By inboxSearchIconWithdrawn =
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img");
    
    
    public static By inboxSearchInputWithdrawn =
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div/div/div/div/div[1]/div/div[1]/div/div/div/input");
    
    
    
    public static By goBackToListViewButton =
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[1]/button");

    
    public static By withdrawInitiativeAction =
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div/div[1]/span[17]");
    
    public static By withdrawInitiativeButton = 
            By.xpath("//span[normalize-space()='Withdraw Initiative']");

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    
// public static By initree = By.xpath("//img[@alt='Initiative Management']");
    
    /** Initiative filter/detail section */
//    public static By initreec = By.xpath("//div[@class='filter-detail d-flex align-items-center justify-content-start']");
    
    /** Sidebar collapsed menu trigger */
//    public static By hoverMenuTrigger = By.xpath("//div[contains(@class,'pro-sidebar') and contains(@class,'collapsed')]");
    
    /** Initiative option arrow (expand/collapse) */
  //  public static By initiativeOption = By.xpath("//span[contains(@class,'pro-arrow')]");
    
    /** Initiative node in navigation */
   // public static By initiativeNode = By.xpath("//*[normalize-space(text())='Initiative']");
//    public static By initiativeNode = By.xpath("//div[@id='children-panel-container']/div[3]/div/p");
    
    /** All iframes on page */
   // public static By allIframes = By.tagName("iframe");
    
    // ==================== PAGE HEADERS ====================
    
    /** Main page header */
//    public static By pageHeader = By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 css-uqd4be']");
    
    /** Initiative page header */
 //   public static By Pageheaderini = By.xpath("//h5[text()='Initiative']");

    /** Cost Details header inside Cost tab */
    public static By costDetailsHeader = By.xpath(
        "//*[self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::p or self::span][normalize-space(.)='Cost Details'] | " +
        "//*[@role='heading' and normalize-space(.)='Cost Details']"
    );

    /** Monthly Distribution tab inside Cost edit drawer */
    public static By monthlyDistributionTab = By.xpath(
        "//div[contains(@class,'MuiDrawer-paper')]//button[@role='tab' and normalize-space(.)='Monthly Distribution'] | " +
        "//button[@role='tab' and normalize-space(.)='Monthly Distribution']"
    );

    /** Monthly Distribution table (inside drawer) */
    public static By monthlyDistributionTable = By.xpath(
        "//div[contains(@class,'MuiDrawer-paper')]//table[.//thead//th[normalize-space(.)='Year']] | " +
        "//table[.//thead//th[normalize-space(.)='Year'] and .//th[normalize-space(.)='October' or normalize-space(.)='November']]"
    );
    
    // ==================== FORM FIELDS ====================
    
    /** Add Initiative button - Multiple stable locator strategies (avoid dynamic IDs) */
  /*  public static By AddIni = By.xpath(
        "//span[@id='id__2'] | " +  // Original dynamic ID as fallback
        "//button[contains(@class,'ms-Button') and .//span[text()='Add']] | " +
        "//button[.//svg[contains(@class,'AddIcon') or @data-testid='AddIcon']] | " +
        "//button[@aria-label='Add'] | " +
        "//button[normalize-space()='Add'] | " +
        "//span[text()='Add']/ancestor::button | " +
        "//*[contains(@class,'add-button') or contains(@class,'btn-add')] | " +
        "//button[contains(@class,'primary')][.//span[contains(.,'Add')]]"
    );
  */  
    /** Initiative Title input field - Multiple stable locator strategies (avoid dynamic IDs) */
    public static By IniTitle = By.xpath(
        "//input[@id='TextField5'] | " +  // Original ID as fallback
        "//label[contains(text(),'Title') or contains(text(),'Initiative Title')]/following::input[1] | " +
        "//input[@placeholder='Enter initiative title' or @placeholder='Title' or contains(@placeholder,'title')] | " +
        "//input[@type='text' and @aria-label='Title'] | " +
        "//div[contains(@class,'field')]//input[@type='text'][1] | " +
        "//input[@type='text' and preceding::*[contains(text(),'Title')]][1]"
    );

    /** Initiative Description textarea - Multiple stable locator strategies (avoid dynamic IDs) */
/*    public static By iniDescription = By.xpath(
        "//textarea[@id='TextField15'] | " +  // Original ID as fallback
        "//label[contains(text(),'Description')]/following::textarea[1] | " +
        "//textarea[@placeholder='Enter description' or contains(@placeholder,'description')] | " +
        "//textarea[@aria-label='Description'] | " +
        "//div[contains(@class,'field')]//textarea[1] | " +
        "//textarea[preceding::*[contains(text(),'Description')]][1]"
    );
 /   
    /** Business Group dropdown - Multiple stable locator strategies (avoid dynamic IDs) */
    public static By IniBG = By.xpath(
        "//span[@id='Dropdown20-option'] | " +  // Original ID as fallback
        "//input[@id='TextField5']/following::span[contains(@id,'Dropdown') and contains(@id,'-option')][1] | " +
        "//textarea[@id='TextField15']/following::span[contains(@id,'Dropdown') and contains(@id,'-option')][1] | " +
        "//div[contains(@class,'ms-Dropdown')]//span[contains(@id,'option')][1] | " +
        "(//span[contains(@id,'Dropdown') and contains(@id,'-option')])[1]"
    );

    public static By CostCategory = By.xpath("//span[@id='select_Category-option']");

    /** Operating Unit dropdown - Multiple stable locator strategies (avoid dynamic IDs) */
/*    public static By IniOU = By.xpath(
        "//span[@id='Dropdown21-option'] | " +  // Original dynamic ID as fallback
        "//input[@id='TextField5']/following::span[contains(@id,'Dropdown') and contains(@id,'-option')][2] | " +
        "//textarea[@id='TextField15']/following::span[contains(@id,'Dropdown') and contains(@id,'-option')][2] | " +
        "//div[contains(@class,'ms-Dropdown')]//span[contains(@id,'option')][2] | " +
        "(//span[contains(@id,'Dropdown') and contains(@id,'-option')])[2]"
    );
 */   
    /** Start Date picker - Multiple stable locator strategies (avoid dynamic IDs) */
    public static By startdate = By.xpath(
        "//input[@id='DatePicker24-label'] | " +  // Original dynamic ID as fallback
        "//input[@id='DatePicker24'] | " +  // Without -label suffix
        "//textarea[@id='TextField15']/following::input[contains(@id,'DatePicker') and contains(@id,'-label')][1] | " +
        "//span[contains(@id,'Dropdown') and contains(@id,'-option')]/following::input[contains(@id,'DatePicker') and contains(@id,'-label')][1] | " +
        "(//input[contains(@id,'DatePicker') and contains(@id,'-label')])[1] | " +
        "(//div[@role='combobox'])[1]//input | " +  // Fluent UI date picker pattern
        "//input[@type='text' and @aria-haspopup='dialog'][1]"  // Generic date input pattern
    );

    /** End Date picker - Multiple stable locator strategies (avoid dynamic IDs) */
    public static By enddate = By.xpath(
        "//input[@id='DatePicker31-label'] | " +  // Original dynamic ID as fallback
        "//input[@id='DatePicker31'] | " +  // Without -label suffix
        "//textarea[@id='TextField15']/following::input[contains(@id,'DatePicker') and contains(@id,'-label')][2] | " +
        "//span[contains(@id,'Dropdown') and contains(@id,'-option')]/following::input[contains(@id,'DatePicker') and contains(@id,'-label')][2] | " +
        "(//input[contains(@id,'DatePicker') and contains(@id,'-label')])[2] | " +
        "(//div[@role='combobox'])[2]//input | " +  // Fluent UI date picker pattern
        "//input[@type='text' and @aria-haspopup='dialog'][2]"  // Generic date input pattern
    );
    
    /** Save as Draft button */
    public static By savedraft = By.xpath("//span[normalize-space()='Save as Draft']");
    
    // ==================== SUBMIT & MODAL ====================
    
    /** Primary Submit button */
    public static By Submit = By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div/div/span[7]");
    public static By Approve = By.xpath("//span[normalize-space()='Approve']");
    public static By PushBack = By.xpath("//span[normalize-space()='Push Back']");
    /** Alternative Submit button locators */
    public static By SubmitAlt1 = By.xpath("//button[.//svg[@data-testid='SendIcon']]");
    public static By SubmitAlt2 = By.xpath("//span[contains(normalize-space(),'Submit')]");
    public static By SubmitAlt3 = By.xpath("//span[contains(text(),'Submit') and .//svg]");
    public static By SubmitAlt4 = By.xpath("//span[normalize-space()='Submit' and .//svg[@data-testid='SendIcon']]");
    
    /** Modal popup container */
    public static By modalPopup = By.xpath("//div[@class='modal-105']");
    
    /** Submit comments container */
    public static By submitCommentsContainer = By.xpath("//div[@class='modal-105']");
    
    /** Additional notes textarea in modal */
 
    public static By additionalNotes = By.xpath("//textarea[@rows='4' and contains(@class,'ms-TextField-field')]");
    /** Popup Submit button - finds button containing span */
    public static By popSubmit = By.xpath("//span[@id='id__296']/ancestor::button | //button[.//span[@id='id__296']]");
    
    /** Alternative popup Submit button locators */
    public static By popSubmitAlt1 = By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button--primary')]");
    public static By popSubmitAlt2 = By.xpath("//div[@class='modal-105']//button[contains(text(),'Submit')]");
    public static By popSubmitAlt3 = By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button')]");
    public static By popSubmitAlt4 = By.xpath("//button[@id='id__150']");
    public static By popSubmitAlt5 = By.xpath("//span[@id='id__296']");
    
    // ==================== FILTERS & SEARCH ====================
    
    /** Draft filter button */
    public static By draftFilter = By.xpath("//span[@id='FltrCountDraft']");
    
    /** Inbox filter button - Primary */
    public static By inboxFilter = By.xpath("//span[@id='FltrCountInbox'] | //span[normalize-space()='Inbox'] | //button[contains(.,'Inbox')]");
    
    /** Inbox filter button - Alternative locators */
    public static By inboxFilterAlt1 = By.xpath("//span[@id='FltrCountInbox']");
    public static By inboxFilterAlt2 = By.xpath("//button[contains(.,'Inbox')]");
    public static By inboxFilterAlt3 = By.xpath("//span[normalize-space()='Inbox']/parent::button");
    
    /** Draft filter button - Alternative locators */
    public static By DraftFilterAlt1 = By.xpath("//span[@id='FltrCountDraft']");
    public static By DraftFilterAlt2 = By.xpath("//button[contains(.,'Draft')]");
    public static By DraftFilterAlt3 = By.xpath("//span[normalize-space()='Draft']/parent::button");
    
    /** Watchlist filter button - Primary */
    public static By watchlistFilter = By.xpath("//span[@id='FltrCountWatchlist'] | //span[normalize-space()='Watchlist'] | //button[contains(.,'Watchlist')]");
    
    /** Watchlist filter button - Alternative locators */
    public static By watchlistFilterAlt1 = By.xpath("//span[@id='FltrCountWatchlist']");
    public static By watchlistFilterAlt2 = By.xpath("//button[contains(.,'Watchlist')]");
    public static By watchlistFilterAlt3 = By.xpath("//span[normalize-space()='Watchlist']/parent::button");
    
    /** Search input field */
    public static By searchInput = By.xpath("//input[@placeholder='Search']");
    
    // ==================== RECORDS & ALERTS ====================
    
    /** Total records count */
    public static By totalRecords = By.xpath("//span[contains(text(),'Total')]");
    
    /** Visible records count */
    public static By countRecords = By.xpath("//div[@class='count-info']");
    
    /** Inbox count displayed on filter badge */
    public static By inboxCount = By.xpath("//span[@id='FltrCountInbox'] | //span[normalize-space()='Inbox']/following-sibling::span | //button[contains(.,'Inbox')]//span[contains(@class,'count')]");
    
    /** Watchlist count displayed on filter badge */
    public static By watchlistCount = By.xpath("//span[@id='FltrCountWatchlist'] | //span[normalize-space()='Watchlist']/following-sibling::span | //button[contains(.,'Watchlist')]//span[contains(@class,'count')]");
    
    /** Grid rows/records in the table */
    public static By gridRows = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row'] | //table//tbody//tr[@role='row']");
    
    /** Grid data rows (excluding headers) */
    public static By gridDataRows = By.xpath("//div[@role='gridcell' and @col-id] | //tbody//tr[@role='row' and not(contains(@class,'header'))]");
    
    /** Pagination info */
    public static By paginationInfo = By.xpath("//div[contains(@class,'pagination')]//span[contains(text(),'of')]");
    
    /** Records per page dropdown */
    public static By recordsPerPageDropdown = By.xpath("//select[contains(@class,'page-size')] | //div[contains(@class,'page-size')]//select");
    
    /** Pagination forward/next page button */
    public static By paginationForwardButton = By.xpath("//svg[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium css-vubbuv']");
          
    
    /** Pagination backward/previous page button */
 /*   public static By paginationBackwardButton = By.xpath(
            "//button[.//svg[@data-testid='ArrowBackIcon']] | " +
            "//svg[@data-testid='ArrowBackIcon']/parent::button | " +
            "//svg[@data-testid='ArrowBackIcon']/ancestor::button | " );
 */           
    
    /** Toast alert message - Multiple fallback patterns for different toast implementations */
 /*   public static By toastAlert = By.xpath(
        "//div[@role='alert'] | " +
        "//div[contains(@class,'toast')] | " +
        "//div[contains(@class,'Toast')] | " +
        "//div[contains(@class,'Toastify')] | " +
        "//div[contains(@class,'alert')] | " +
        "//div[contains(@class,'notification')] | " +
        "//div[contains(@class,'snackbar')] | " +
        "//div[contains(@class,'ms-MessageBar')] | " +
        "//div[contains(@class,'message-bar')] | " +
        "//div[contains(text(),'Please fill')] | " +
        "//div[contains(text(),'mandatory')] | " +
        "//div[contains(text(),'CheckList')] | " +
        "//span[contains(text(),'Please fill')] | " +
        "//span[contains(text(),'mandatory')] | " +
        "//span[contains(text(),'CheckList')] | " +
        "//p[contains(text(),'Please fill')] | " +
        "//p[contains(text(),'mandatory')] | " +
        "//*[contains(@class,'error-message')] | " +
        "//*[contains(@class,'validation-message')]"
    );
   */ 
    // ==================== CLOSE BUTTONS ====================
    
    /** Close button (standard) */
//    public static By closeButton = By.xpath("//button[text()='×' or contains(text(),'×') or normalize-space()='×']");
    
    /** Close button by style */
//    public static By closeButtonByStyle = By.xpath("//button[contains(@style, 'border-radius: 50%') and contains(text(),'×')]");
    
    /** Close button by text */
 //   public static By closeButtonByText = By.xpath("//button[contains(text(),'×')]");
   
    // ==================== MODAL SUBMIT LOCATORS (FALLBACKS) ====================
    
    /** Array of fallback locators for modal submit button - Try in order for reliability */
    public static By[] modalSubmitButtonLocators = {
        By.xpath("//div[@class='modal-105']//button[contains(@class,'ms-Button--primary')]"), // Most common
        By.xpath("//div[@class='modal-105']//button[contains(text(),'Submit')]"),
        By.xpath("//div[@class='modal-105']//button[contains(.,'Submit')]"),
        By.xpath("//div[@class='modal-105']//button"),  // ANY button in modal (aggressive)
        popSubmitAlt1,
        popSubmitAlt2,
        popSubmitAlt3
    };
   
    /** All buttons in modal (for debugging) */
    public static By allModalButtons = By.xpath("//div[@class='modal-105']//button");
    
    /** All spans with ID in modal (for debugging) */
    public static By allModalSpans = By.xpath("//div[@class='modal-105']//span[contains(@id,'id__')]");
    
    // ==================== DATE PICKER ALTERNATIVE LOCATORS ====================
    
    /** Alternative start date locator (without -label suffix) */
    public static By startdateAlt = By.xpath("//input[@id='DatePicker24']");

    /** Start date by placeholder */
    public static By startdateByPlaceholder = By.xpath(
        "//input[contains(@placeholder,'start') or contains(@placeholder,'Start')] | " +
        "//input[contains(@placeholder,'date') or contains(@placeholder,'Date')][1] | " +
        "(//input[@type='text' and @aria-haspopup='dialog'])[1]"
    );

    /** Alternative end date locator (without -label suffix) */
    public static By enddateAlt = By.xpath("//input[@id='DatePicker31']");

    /** Second date input (for end date) */
    public static By secondDateInput = By.xpath("(//input[@type='text' and contains(@id,'DatePicker')])[2]");

    /** End date by placeholder */
    public static By enddateByPlaceholder = By.xpath(
        "//input[contains(@placeholder,'end') or contains(@placeholder,'End')] | " +
        "//input[contains(@placeholder,'date') or contains(@placeholder,'Date')][2] | " +
        "(//input[@type='text' and @aria-haspopup='dialog'])[2]"
    );
    
    /** End date near label */
    public static By enddateNearLabel = By.xpath("//label[contains(text(),'End') or contains(text(),'end')]/following-sibling::input | //label[contains(text(),'End') or contains(text(),'end')]/..//input");
    
    // ==================== INBOX & WATCHLIST COUNT LOCATORS ====================
    
    /** Array of inbox count locators (fallback strategies) */
    public static By[] inboxCountLocators = {
        By.xpath("//span[@id='FltrCountInbox']"),
        By.xpath("//span[normalize-space()='Inbox']/following-sibling::span"),
        By.xpath("//span[normalize-space()='Inbox']/..//span[contains(@class,'count')]"),
        By.xpath("//span[normalize-space()='Inbox']/..//span[contains(@class,'badge')]"),
        By.xpath("//button[contains(.,'Inbox')]//span[contains(@class,'count')]"),
        By.xpath("//button[contains(.,'Inbox')]")  // Try button itself
    };
    /** Array of Draft count locators (fallback strategies) */
    public static By[] DraftCountLocators = {
        By.xpath("//span[@id='FltrCountDraft']"),
        By.xpath("//span[normalize-space()='Draft']/following-sibling::span"),
        By.xpath("//span[normalize-space()='Draft']/..//span[contains(@class,'count')]"),
        By.xpath("//span[normalize-space()='Draft']/..//span[contains(@class,'badge')]"),
        By.xpath("//button[contains(.,'Draft')]//span[contains(@class,'count')]"),
        By.xpath("//button[contains(.,'Draft')]")  // Try button itself
    };
    
    /** Array of watchlist count locators (fallback strategies) */
    public static By[] watchlistCountLocators = {
        By.xpath("//span[@id='FltrCountWatchlist']"),
        By.xpath("//span[normalize-space()='Watchlist']/following-sibling::span"),
        By.xpath("//span[normalize-space()='Watchlist']/..//span[contains(@class,'count')]"),
        By.xpath("//span[normalize-space()='Watchlist']/..//span[contains(@class,'badge')]"),
        By.xpath("//button[contains(.,'Watchlist')]//span[contains(@class,'count')]")
    };
    
    // ==================== GRID ROW LOCATORS ====================
    
    /** Array of grid row locators (try in order for compatibility) */
    public static By[] gridRowLocators = {
        By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr/td/div/p"),
        By.xpath("//div[@role='row' and contains(@class,'ag-row')]"),
        By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
        By.xpath("//table//tbody//tr[@role='row']"),
        By.xpath("//div[@role='gridcell']/../.."),
        By.xpath("//div[contains(@class,'data-grid')]//div[@role='row']"),
        By.xpath("//div[contains(@class,'ag-row')]"),  // Simplified AG Grid
        By.xpath("//tr[contains(@class,'ag-row')]")    // Table-based AG Grid
    };
    
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for NOI (Number of Initiatives) option by value
     * @param noiValue The NOI value text to search for
     * @return By locator for the specific NOI option
     */
 //   public static By getDynamicNOIOption(String noiValue) {
  //      return By.xpath("//div[@class='MuiBox-root css-ah0zvi']//td[normalize-space(text())='" + noiValue + "']");
 //   }
    
    /**
     * Get dynamic locator for NOI option by visible text
     */
    public static By getDynamicNOIOption() {
        // noiValue is intentionally ignored to support multi-site behavior
        return By.xpath("//table/tbody/tr[1]/td");
            
      
    }
    
    
    /**
     * Get dynamic locator for any option by normalized text
     * @param optionText The option text to search for
     * @return By locator for the option
     */
    public static By getDynamicOptionByText(String optionText) {
        return By.xpath("//*[normalize-space(text())='" + optionText + "']");
    }
    
    /**
     * Get dynamic locator for Business Group dropdown option
     * @param bgName The Business Group name
     * @return By locator for the BG option
     */
    public static By getDynamicBGOption(String bgName) {
        return By.xpath("//*[normalize-space(text())='" + bgName + "']");
    }
    
    
    /**
     * Get dynamic locator for cost  category dropdown option
     * @param cost  category name
     * @return By locator for the CC option
     */
    public static By getDynamicCCOption(String CCName) {
        return By.xpath("//*[normalize-space(text())='" + CCName + "']");
    }
    
    
    /**
     * Get dynamic locator for Operating Unit dropdown option
     * @param ouName The Operating Unit name
     * @return By locator for the OU option
     */
    public static By getDynamicOUOption(String ouName) {
        return By.xpath("//*[normalize-space(text())='" + ouName + "']");
    }
    
    /**
     * Get dynamic locator for textarea by ID
     * @param textareaId The ID of the textarea
     * @return By locator for the textarea
     */
    public static By getDynamicTextareaById(String textareaId) {
        return By.xpath("//textarea[@id='" + textareaId + "']");
    }
    
    /**
     * Get dynamic locator for modal by xpath
     * @param modalXPath The XPath string for modal
     * @return By locator for the modal
     */
    public static By getDynamicModalByXPath(String modalXPath) {
        return By.xpath(modalXPath);
    }
    
    /**
     * Get dynamic modal close button
     * @return By locator for modal close button
     */
    public static By getModalCloseButton() {
        return By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'ms-Button--icon')]");
    }
    
    /**
     * Get dynamic modal submit button with text
     * @param submitText The text on the submit button (e.g., "Submit")
     * @return By locator for modal submit button
     */
    public static By getDynamicModalSubmitButton(String submitText) {
        return By.xpath("//button[contains(@class, 'ms-Button--primary') and .//span[normalize-space(text())='" + submitText + "']]");
    }
    
    // ==================== USER PROFILE & LOGOUT ====================
    
    /** User profile/avatar menu button */
    public static By userProfileMenu = By.xpath("//span[contains(normalize-space(.),'Hi,')]");
    
    /** User profile avatar - alternative locators */
    //public static By userAvatar = By.xpath("//img[contains(@alt,'avatar') or contains(@alt,'profile') or contains(@alt,'user')] | //div[contains(@class,'MuiAvatar')]");
    public static By userAvatar = By.xpath("//span[normalize-space()='UI/UX']");
    
    
    
    /** Logout button/option */
    //public static By logoutButton = By.xpath("//span[normalize-space()='Logout'] | //button[contains(text(),'Logout')] | //a[contains(text(),'Logout')] | //li[contains(text(),'Logout')] | //div[contains(text(),'Logout')]");
    
    
    public static By logoutButton = By.xpath("//span[normalize-space()='Logout']");
    
   
    
    // ==================== SEARCH FUNCTIONALITY ====================
    
    /** Global search icon */
    public static By searchIcon = By.xpath("//svg[@data-testid='SearchIcon'] | //button[.//svg[@data-testid='SearchIcon']] | //*[contains(@class,'search-icon')] | //span[contains(@class,'search')]//svg");
    
    /** Search icon button */
    public static By searchIconButton = By.xpath("//button[.//svg[contains(@class,'search') or @data-testid='SearchIcon']] | //div[contains(@class,'search')]//button");
    
    /** Global search input field */
    public static By globalSearchInput = By.xpath("//input[@placeholder='Search' or @placeholder='Search...' or contains(@placeholder,'search')] | //input[contains(@class,'search')]");
    
    /** Search submit/execute button */
    public static By searchSubmitButton = By.xpath("//button[@type='submit' and ancestor::*[contains(@class,'search')]] | //button[.//svg[@data-testid='SearchIcon']]");
    
    /** Demand Code / Initiative Code search input field */
    public static By demandCodeInput = By.xpath("//input[@id='DemandCode']");
    
    /** Search button after entering demand code */
    public static By demandCodeSearchButton = By.xpath("//button[.//span[text()='Search']]");
    //public static By demandCodeSearchButton = By.xpath("//span[@id='id__54' and text()='Search']");
    
    public static By CloseSearchButton = By.xpath("//button[.//span[text()='Close']]");
    
    /** Prioritization Checklist Link (span 11 in the action bar) */
    public static By prioritizationChecklistLink = By.xpath("//span[normalize-space()='CheckList']");
    
    // ==================== CHECKLIST RESPONSES ====================
    
    /** Checklist Response 1 */
    public static By checklistResponse1 = By.xpath("//div[3]/input");
    
    /** Checklist Response 2 */
    public static By checklistResponse2 = By.xpath("//tr[4]/td[3]/div/div[4]/input");
    
    /** Checklist Response 3 */
    public static By checklistResponse3 = By.xpath("//tr[6]/td[3]/div/div[2]/input");
    
    /** Checklist Response 4 */
    public static By checklistResponse4 = By.xpath("//tr[8]/td[3]/div/div[5]/input");
    
    /** Checklist Save Button */
    public static By checklistSaveButton = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Show History'])[1]/following::button[1]");
    
    /** Checklist Success Alert */
   // public static By checklistSuccessAlert = By.xpath("//div[@id='11']/div");
   
    public static By checklistSuccessAlert  = By.xpath(
    		"//div[@id='11']/div |"+
            "//div[@role='alert'] | " +
            "//div[contains(@class,'toast')] | " +
            "//div[contains(@class,'Toast')] | " +
            "//div[contains(@class,'Toastify')] | " +
            "//div[contains(@class,'alert')] | " +
            "//div[contains(@class,'notification')] | " +
            "//div[contains(@class,'snackbar')] | " +
            "//div[contains(@class,'ms-MessageBar')] | " +
            "//div[contains(@class,'message-bar')] | " +
           
           
            "//div[contains(text(),'CheckList')] | " +
           
            "//span[contains(text(),'Updated')] | " +
            "//span[contains(text(),'CheckList')] | " +
         
           "//*[contains(@class,'validation-message')]"
        );
    
    
    /** Pushback Success Alert */

    public static By PushbackSuccessAlert  = By.xpath(
    		"//div[@id='9']/div |"+
            "//div[@role='alert'] | " +
            "//div[contains(@class,'toast')] | " +
            "//div[contains(@class,'Toast')] | " +
            "//div[contains(@class,'Toastify')] | " +
            "//div[contains(@class,'alert')] | " +
            "//div[contains(@class,'notification')] | " +
            "//div[contains(@class,'snackbar')] | " +
            "//div[contains(@class,'ms-MessageBar')] | " +
            "//div[contains(@class,'message-bar')] | " +
           
           
            "//div[contains(text(),'Pushback')] | " +
           
            "//span[contains(text(),'Successfully')] | " +
            "//span[contains(text(),'Pushback')] | " +
         
           "//*[contains(@class,'validation-message')]"
        );
    /** Approved Success Alert */
   
    
     public static By ApprovedSuccessAlert  = By.xpath(
     		"//div[@id='2']/div |"+
             "//div[@role='alert'] | " +
             "//div[contains(@class,'toast')] | " +
             "//div[contains(@class,'Toast')] | " +
             "//div[contains(@class,'Toastify')] | " +
             "//div[contains(@class,'alert')] | " +
             "//div[contains(@class,'notification')] | " +
             "//div[contains(@class,'snackbar')] | " +
             "//div[contains(@class,'ms-MessageBar')] | " +
             "//div[contains(@class,'message-bar')] | " +
            
            
             "//div[contains(text(),'Approved')] | " +
            
             "//span[contains(text(),'Successfully')] | " +
             "//span[contains(text(),'Approved')] | " +
          
            "//*[contains(@class,'validation-message')]"
         );
     
    
    
//public static By IniEditButton = By.xpath("//tbody/tr[1]/td[4]/div[1]/div[1]/div[1]/button[1]//*[name()='svg']");
//public static By IniEditButton = By.xpath("//tr[td[text()='ROW_TEXT']]//svg[@data-testid='EditIcon']");
//public static By IniEditButton = By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall");

/**
 * Static locator for the first edit button in initiative actions
 */
public static By IniEditButton = By.xpath("//div[contains(@class,'initiative-actions')]//button[.//*[local-name()='svg' and @data-testid='EditIcon']])[1]");

/**
 * Get dynamic locator for edit button in a specific row containing initiative code
 * @param initiativeCode The initiative code to find the edit button for
 * @return By locator for the edit button in that row
 */
public static By getEditButtonForInitiative(String initiativeCode) {
    return By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]//button//svg[contains(@class,'MuiSvgIcon-root') and contains(@class,'MuiSvgIcon-fontSizeSmall')]");
}
    
    // ==================== DISCUSSION THREAD ====================
    
    /** Discussion Thread Tab Link */
    public static By discussionThreadTab = By.linkText("Discussion Thread");
    
    /** Discussion Thread Tab - Alternative XPath */
    public static By discussionThreadTabAlt = By.xpath("//a[contains(text(),'Discussion Thread')] | //span[contains(text(),'Discussion Thread')] | //*[text()='Discussion Thread']");
    
    /** Discussion Comment Textarea */
    public static By discussionCommentTextarea = By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div[2]/textarea");
    
    /** Discussion Post Button */
    public static By discussionPostButton = By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div[2]/button");
    
    // ==================== RESOURCES TAB ====================
    
    /** Resources Tab Link */
    public static By resourcesTab = By.linkText("Resources");
    
    /** Cost Tab Link */
    public static By CostsTab = By.linkText("Costs");
    
    /** Funding Tab Link */
    public static By fundingTab = By.xpath("//a[normalize-space()='Funding']");
      
   

    /** Funding Details header inside Funding tab */
    public static By fundingDetailsHeader = By.xpath(
        "//*[self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::p or self::span][normalize-space(.)='Funding Details'] | " +
        "//*[@role='heading' and normalize-space(.)='Funding Details']"
    );

    /** Funding Add Button (scoped to Funding section when possible) */
    public static By fundingAddButton = By.xpath("//button[.//span[normalize-space()='Add']]");
        

    /** Funding Save button (inside drawer/panel) */
    public static By fundingSaveButton = By.xpath(
        "//div[contains(@class,'MuiDrawer-paper')]//button[.//span[normalize-space()='Save'] or normalize-space()='Save'] | " +
        "//button[contains(@class,'ms-Button--primary')][contains(.,'Save')] | " +
        "//button[normalize-space()='Save'] | " +
        "//span[normalize-space()='Save']/ancestor::button"
    );

    /** Funding Cost Category dropdown trigger (inside drawer/panel) */
    public static By fundingCostCategoryTrigger = By.xpath(
        "//div[contains(@class,'MuiDrawer-paper')]//*[self::label or self::span or self::p][contains(normalize-space(.),'Cost Category')]" +
            "/following::*[contains(@class,'ms-Dropdown') or @role='combobox' or self::button][1] | " +
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Cost Category')]" +
            "/following::*[contains(@class,'ms-Dropdown') or @role='combobox' or self::button][1]"
    );

    /** Funding Approved Amount input */
    public static By fundingApprovedAmount = By.xpath("//input[@type='text' and contains(@class,'ms-TextField-field')]");
       

    /** Funding Source input */
    public static By fundingSource = By.xpath(
        "//div[contains(@class,'MuiDrawer-paper')]//*[self::label or self::span or self::p][contains(normalize-space(.),'Funding Source')]" +
            "/following::input[1] | " +
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Funding Source')]/following::input[1]"
    );
    
    /** Resources Tab - Alternative XPath */
    public static By resourcesTabAlt = By.xpath("//a[contains(text(),'Resources')] | //span[contains(text(),'Resources')] | //*[text()='Resources']");
    /** Costs Tab - Alternative XPath */
    public static By CostsTabAlt = By.xpath("//a[contains(text(),'Costs')] | //span[contains(text(),'Costs')] | //*[text()='Costs']");
    /** Resources Add Button - Multiple stable locator strategies (avoid dynamic IDs like id__74) */
    public static By resourcesAddButton = By.xpath(
        "//button[contains(@class,'ms-Button') and .//span[text()='Add']] | " +
        "//button[.//svg[contains(@class,'AddIcon') or @data-testid='AddIcon']] | " +
        "//button[@aria-label='Add'] | " +
        "//button[normalize-space()='Add'] | " +
        "//span[text()='Add']/ancestor::button"
    );
    
    /** Resources Add Button - Alternative strategies */
    public static By resourcesAddButtonAlt = By.xpath(
        "//button[contains(text(),'Add')] | " +
        "//button[.//span[contains(text(),'Add')]] | " +
        "//*[@role='button'][contains(.,'Add')] | " +
        "//button[contains(@class,'primary')][.//span[contains(.,'Add')]]"
    );
    /** Cost Add Button - Multiple stable locator strategies (avoid dynamic IDs like id__74) */
    public static By CostsAddButton = By.xpath("//button[contains(@class,'ms-Button--primary') and .//span[normalize-space()='Add']]");
    	
   
    /** Costs Add Button - Alternative strategies */
    public static By CostsAddButtonAlt = By.xpath(
    		
        "//button[contains(text(),'Add')] | " +
        "//button[.//span[contains(text(),'Add')]] | " +
        "//*[@role='button'][contains(.,'Add')] | " +
        "//button[contains(@class,'primary')][.//span[contains(.,'Add')]]"
    );
    /** Skill Dropdown Trigger - Multiple strategies to find skill dropdown (avoid dynamic IDs) */
    public static By skillDropdownTrigger = By.xpath(
        "//label[contains(text(),'Skill')]/following::div[contains(@class,'ms-Dropdown')][1]//span[contains(@id,'option')] | " +
        "//label[contains(text(),'Skill')]/following::span[contains(@id,'Dropdown')][1] | " +
        "//div[contains(@class,'ms-Dropdown')]//span[contains(@id,'option')][2] | " +
        "(//span[contains(@id,'Dropdown') and contains(@id,'-option')])[2] | " +
        "//div[contains(@class,'ms-Dropdown')][2]//span[contains(@id,'option')]"
    );
    
    /** Skill Dropdown - Alternative locators */
    public static By skillDropdownTriggerAlt = By.xpath(
        "(//div[contains(@class,'ms-Dropdown')]//span[contains(@id,'option')])[last()] | " +
        "//span[contains(@id,'Dropdown') and contains(@id,'-option')][not(contains(@id,'339'))]"
    );
    
    /** Skill Dropdown - Dynamic checkbox option selector */
    public static By getSkillCheckboxOption(String skillName) {
        return By.xpath(
            "//div[contains(@class,'ms-Dropdown-items')]//button[contains(.,'" + skillName + "')] | " +
            "//div[contains(@class,'ms-Dropdown-callout')]//button[contains(.,'" + skillName + "')] | " +
            "//*[@role='listbox']//*[contains(text(),'" + skillName + "')] | " +
            "//*[@role='option'][contains(.,'" + skillName + "')] | " +
            "//button[contains(@class,'ms-Dropdown-item')][contains(.,'" + skillName + "')]"
        );
    }
    
    /** Skill Dropdown - All checkbox options (for verification) */
    public static By skillDropdownOptions = By.xpath(
        "//div[contains(@class,'ms-Dropdown-items')]//button[contains(@class,'ms-Dropdown-item')] | " +
        "//div[contains(@class,'ms-Dropdown-callout')]//button | " +
        "//*[@role='listbox']//*[@role='option']"
    );
    
    // ==================== RESOURCE DETAILS FORM ====================
    
    /** Resource In Date picker - div with role=combobox (Fluent UI DatePicker) */
    public static By resourceInDate = By.xpath(
        "(//div[contains(@id,'DatePicker') and @role='combobox'])[1] | " +
        "//div[@id='DatePicker82-label'] | " +
        "//div[@id='DatePicker290-label']"
    );
   
 //   public static By resourceInDate = By.xpath("//label[contains(text(),'Resource-In Date')]/following::div[@role='combobox'][1]"
            
   
    /** Resource In Date picker - alternative locators */
    public static By resourceInDateAlt = By.xpath(
        "(//div[contains(@class,'ms-TextField-field') and contains(@id,'DatePicker')])[1] | " +
        "(//div[@role='combobox' and contains(@id,'DatePicker')])[1]"
    );
    
    /** Resource Out Date picker - div with role=combobox (Fluent UI DatePicker) */
    public static By resourceOutDate = By.xpath(
        "(//div[contains(@id,'DatePicker') and @role='combobox'])[2] | " +
        "//div[@id='DatePicker89-label'] | " +
        "//div[@id='DatePicker297-label']"
    );
    
    /** Resource Out Date picker - alternative locators */
    public static By resourceOutDateAlt = By.xpath(
        "(//div[contains(@class,'ms-TextField-field') and contains(@id,'DatePicker')])[2] | " +
        "(//div[@role='combobox' and contains(@id,'DatePicker')])[2]"
    );
    
    /** Resource FTE input field - number input */
    public static By resourceFTE = By.xpath(
        "//input[@id='TextField304'] | " +
        "//input[@type='number' and contains(@class,'ms-TextField-field')]"
    );
    /** Cost Amount input field - number input */
    public static By CostsAmount = By.xpath(
    		
        // Prefer stable id first (if available)
        "//input[@id='TextField443'] | " +
        // Fluent UI TextField: find the ms-TextField container that contains an Amount label, then take its input
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Amount')]" +
        "/ancestor::*[contains(@class,'ms-TextField')][1]//input | " +
        // Generic label/text fallback (less preferred)
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Amount')]/following::input[1] | " +
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Amount')]/following::*[@role='spinbutton'][1] | " +
        "//input[contains(@aria-label,'Amount') or contains(@title,'Amount')]"
    );
    /** Cost Decription input field - number input */
    public static By CostsDecription = By.xpath(
        // Description is typically text/textarea, not number
        "//textarea[@id='TextField462'] | " +
        "//input[@id='TextField462'] | " +
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Description')]/following::textarea[1] | " +
        "//*[self::label or self::span or self::p][contains(normalize-space(.),'Description')]/following::input[1] | " +
        "//textarea[contains(@aria-label,'Description') or contains(@title,'Description')] | " +
        "//input[contains(@aria-label,'Description') or contains(@title,'Description')]"
    );
    /** Resource Save button */
    public static By resourceSaveButton = By.xpath(
        "//span[@id='id__336']/ancestor::button | " +
        "//span[@id='id__336'] | " +
        "//button[.//span[@id='id__336']] | " +
        "//button[contains(@class,'ms-Button--primary')][contains(.,'Save')] | " +
        "//button[normalize-space()='Save'] | " +
        "//span[text()='Save']/ancestor::button"
    );
    /** Costs Save button */
    public static By CostsSaveButton = By.xpath(
        "//span[@id='id__440']/ancestor::button | " +
        "//span[@id='id__690'] | " +
        "//button[.//span[@id='id__690']] | " +
        "//button[@type='submit' and (contains(.,'Save') or .//span[normalize-space()='Save'])] | " +
        "//button[contains(@class,'ms-Button--primary')][contains(.,'Save')] | " +
        "//button[normalize-space()='Save'] | " +
        "//span[text()='Save']/ancestor::button | " +
        "//button[contains(@aria-label,'Save') or contains(@title,'Save')]"
    );
    
    /** Resource success alert message */
    public static By resourceSuccessAlert = By.xpath(
        "//*[contains(text(),'Resource details updated successfully')] | " +
        "//div[contains(@class,'alert')][contains(.,'Resource details updated successfully')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'success')] | " +
        "//div[contains(@role,'alert')][contains(.,'Resource details updated')]"
    );
    /** Costs success alert message */
    public static By CostsSuccessAlert = By.xpath(
        "//*[contains(text(),'Cost Updated successfully!')] | " +
        "//div[contains(@class,'alert')][contains(.,'Cost Updated successfully!')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'success')] | " +
        "//div[contains(@role,'alert')][contains(.,'Cost Updated successfully!')]"
    );
    /** Resource Edit button - Edit button for latest/first record in resource table */
    public static By resourceEditButton = By.xpath(
        "//tbody/tr[1]/td[6]/button[1] | " +
        "//tbody/tr[last()]/td[6]/button[1] | " +
        "//tbody/tr[1]/td[6]/button[1]//*[name()='svg']/ancestor::button | " +
        "//button[contains(@class,'ms-Button')]//i[contains(@class,'Edit')]/ancestor::button | " +
        "//button[@title='Edit']"
    );

    /** Cost Edit button - Edit button for first/latest record in cost table */
    public static By costEditButton = By.xpath(
        "//tbody/tr[1]//button[@title='Edit' or contains(@aria-label,'Edit')] | " +
        "//tbody/tr[last()]//button[@title='Edit' or contains(@aria-label,'Edit')] | " +
        "//*[@data-testid='EditIcon']/ancestor::button | " +
        "//svg[@data-testid='EditIcon']/ancestor::button | " +
        "//button[contains(@aria-label,'Edit')] | " +
        "//button[@title='Edit']"
    );

    /** Cost Delete button - Delete button for first/latest record in cost table */
    public static By costDeleteButton = By.xpath(
        "//tbody/tr[1]//button[@title='Delete' or contains(@aria-label,'Delete')] | " +
        "//tbody/tr[last()]//button[@title='Delete' or contains(@aria-label,'Delete')] | " +
        "//*[@data-testid='DeleteIcon']/ancestor::button | " +
        "//svg[@data-testid='DeleteIcon']/ancestor::button | " +
        "//button[contains(@aria-label,'Delete')] | " +
        "//button[@title='Delete']"
    );

    /** Cost Delete Success Alert (best-effort) */
    public static By costDeleteSuccessAlert = By.xpath(
        "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cost') and contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'deleted')] | " +
        "//*[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'deleted successfully')] | " +
        "//div[contains(@role,'alert')][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'deleted')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'deleted')]"
    );
    
    /** Show History link/button */
    public static By showHistoryLink = By.xpath(
        "//button[contains(@class,'MuiTab-root') and contains(text(),'History')] | " +
        "//button[@role='tab' and contains(text(),'History')] | " +
        "//button[contains(@class,'MuiButtonBase-root') and contains(text(),'History')] | " +
        "//a[contains(text(),'Show History')] | " +
        "//button[contains(text(),'Show History')] | " +
        "//button[contains(text(),'History')]"
    );
    
    /** Resource Delete button - Delete button for first/latest record in resource table */
    public static By resourceDeleteButton = By.xpath(
        "//tbody/tr[1]/td[6]/button[2] | " +
        "//*[@data-testid='DeleteIcon']/ancestor::button | " +
        "//svg[@data-testid='DeleteIcon']/ancestor::button | " +
        "//*[contains(@class,'MuiSvgIcon-root')][@aria-label='Delete']/ancestor::button | " +
        "//button[contains(@aria-label,'Delete')] | " +
        "//button[@title='Delete']"
    );
    
    /** Resource table rows - to check if records exist */
    public static By resourceTableRows = By.xpath("//tbody/tr");
    /** Resource table rows - to check if records exist */
    public static By CostTableRows = By.xpath("//tbody/tr");
    /** No items to show message - indicates empty table */
    public static By noItemsToShowMessage = By.xpath(
        "//*[contains(text(),'There are no items to show')] | " +
        "//*[contains(text(),'No items to show')] | " +
        "//*[contains(text(),'No records')] | " +
        "//*[contains(text(),'No data')]"
    );
    
    /** Resource Delete Success Alert */
    public static By resourceDeleteSuccessAlert = By.xpath(
        "//*[contains(text(),'Resource Details Deleted Successfully')] | " +
        "//*[contains(text(),'deleted successfully')] | " +
        "//div[contains(@class,'alert')][contains(.,'Deleted')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'Deleted')] | " +
        "//div[contains(@role,'alert')][contains(.,'Deleted')]"
    );
    
    /** Delete Confirmation Yes button */
    public static By deleteConfirmYesButton = By.xpath(
        "//button[contains(@class,'ms-Button--primary')]//span[text()='Yes']/ancestor::button | " +
        "//span[@id='id__319']/ancestor::button | " +
        "//button[contains(@class,'ms-Button--primary')][.//span[text()='Yes']] | " +
        "//button[.//span[text()='Yes']] | " +
        "//button[normalize-space()='Yes'] | " +
        "//button[contains(@class,'ms-Button')][contains(.,'Yes')]"
    );
    
    /** Delete Confirmation No button */
    public static By deleteConfirmNoButton = By.xpath(
        "//span[@id='id__784']/ancestor::button | " +
        "//span[contains(@id,'id__') and text()='No']/ancestor::button | " +
        "//button[contains(@class,'ms-Button')]//span[text()='No']/ancestor::button | " +
        "//button[.//span[text()='No']] | " +
        "//button[normalize-space()='No'] | " +
        "//button[contains(@class,'ms-Button')][contains(.,'No')]"
    );
    /** Costs From Date picker - div with role=combobox (Fluent UI DatePicker) */
    public static By CostsFromDate = By.xpath(
        // Prefer label-based selection on the Cost form
        "//label[contains(normalize-space(.),'From Date')]/following::*[@role='combobox'][1] | " +
        "//label[contains(normalize-space(.),'From Date')]/following::input[1] | " +
        // Fallback to first DatePicker combobox on the cost form
        "(//div[contains(@id,'DatePicker') and @role='combobox'])[1]"
    );
    /** Costs To Date picker - div with role=combobox (Fluent UI DatePicker) */
    public static By CostsToDate = By.xpath(
        "//label[contains(normalize-space(.),'To Date')]/following::*[@role='combobox'][1] | " +
        "//label[contains(normalize-space(.),'To Date')]/following::input[1] | " +
        // Fallback to second DatePicker combobox (From Date is usually first, To Date second)
        "(//div[contains(@id,'DatePicker') and @role='combobox'])[2]"
    );
    // ==================== RESOURCE FIELD LABELS ====================
    
    /** Role field label */
    public static By resourceRoleFieldLabel = By.xpath(
        "//*[contains(text(),'Role')] | " +
        "//label[contains(text(),'Role')] | " +
        "//span[contains(text(),'Role')]"
    );
    
    /** Skills field label */
    public static By resourceSkillsFieldLabel = By.xpath(
        "//*[contains(text(),'Skills')] | " +
        "//label[contains(text(),'Skills')] | " +
        "//span[contains(text(),'Skills')]"
    );
    
    /** Resource-In Date field label */
    public static By resourceInDateFieldLabel = By.xpath(
        "//*[contains(text(),'Resource-In Date')] | " +
        "//*[contains(text(),'In Date')] | " +
        "//label[contains(text(),'In Date')] | " +
        "//span[contains(text(),'In Date')]"
    );
    
    
  
    
    
    /** Resource-Out Date field label */
    public static By resourceOutDateFieldLabel = By.xpath(
        "//*[contains(text(),'Resource-Out Date')] | " +
        "//*[contains(text(),'Out Date')] | " +
        "//label[contains(text(),'Out Date')] | " +
        "//span[contains(text(),'Out Date')]"
    );
    
    /** FTE field label */
    public static By resourceFTEFieldLabel = By.xpath(
        "//*[contains(text(),'FTE')] | " +
        "//label[contains(text(),'FTE')] | " +
        "//span[contains(text(),'FTE')]"
    );
    
    /** Mandatory field indicator (asterisk) */
    public static By mandatoryFieldIndicator = By.xpath(
        "//*[contains(@class,'required')] | " +
        "//*[contains(text(),'*')] | " +
        "//span[contains(@class,'mandatory')] | " +
        "//input[@required]"
    );
    
    // ==================== VALIDATION ERROR MESSAGES ====================
    
    /** Generic validation error message */
    public static By validationErrorMessage = By.xpath(
        "//*[contains(@class,'ms-TextField-errorMessage')] | " +
        "//*[contains(@class,'error-message')] | " +
        "//*[contains(@class,'errorMessage')] | " +
        "//*[contains(@class,'field-error')] | " +
        "//span[contains(@class,'error')] | " +
        "//*[contains(@role,'alert')]"
    );
    
    /** Role field validation error */
    public static By roleValidationError = By.xpath(
        "//label[contains(text(),'Role')]/following::*[contains(@class,'errorMessage')][1] | " +
        "//label[contains(text(),'Role')]/following::*[contains(@class,'error')][1] | " +
        "//*[contains(text(),'Role')]/following-sibling::*[contains(@class,'error')] | " +
        "//*[contains(text(),'Role is required')] | " +
        "//*[contains(text(),'Please select a role')] | " +
        "//*[contains(text(),'Role') and contains(text(),'required')]"
    );
    
    /** Skills field validation error */
    public static By skillsValidationError = By.xpath(
        "//label[contains(text(),'Skill')]/following::*[contains(@class,'errorMessage')][1] | " +
        "//label[contains(text(),'Skill')]/following::*[contains(@class,'error')][1] | " +
        "//*[contains(text(),'Skill')]/following-sibling::*[contains(@class,'error')] | " +
        "//*[contains(text(),'Skills is required')] | " +
        "//*[contains(text(),'Skill is required')] | " +
        "//*[contains(text(),'Please select')] | " +
        "//*[contains(text(),'Skill') and contains(text(),'required')]"
    );
    
    /** In Date field validation error */
    public static By inDateValidationError = By.xpath(
        "//label[contains(text(),'In Date')]/following::*[contains(@class,'errorMessage')][1] | " +
        "//label[contains(text(),'In Date')]/following::*[contains(@class,'error')][1] | " +
        "//*[contains(text(),'In Date')]/following-sibling::*[contains(@class,'error')] | " +
        "//*[contains(text(),'In Date is required')] | " +
        "//*[contains(text(),'Resource-In Date') and contains(text(),'required')] | " +
        "//*[contains(text(),'date') and contains(text(),'required')]"
    );
    
    /** Out Date field validation error */
    public static By outDateValidationError = By.xpath(
        "//label[contains(text(),'Out Date')]/following::*[contains(@class,'errorMessage')][1] | " +
        "//label[contains(text(),'Out Date')]/following::*[contains(@class,'error')][1] | " +
        "//*[contains(text(),'Out Date')]/following-sibling::*[contains(@class,'error')] | " +
        "//*[contains(text(),'Out Date is required')] | " +
        "//*[contains(text(),'Resource-Out Date') and contains(text(),'required')] | " +
        "//*[contains(text(),'date') and contains(text(),'required')]"
    );
    
    /** FTE field validation error */
    public static By fteValidationError = By.xpath(
        "//label[contains(text(),'FTE')]/following::*[contains(@class,'errorMessage')][1] | " +
        "//label[contains(text(),'FTE')]/following::*[contains(@class,'error')][1] | " +
        "//*[contains(text(),'FTE')]/following-sibling::*[contains(@class,'error')] | " +
        "//*[contains(text(),'FTE is required')] | " +
        "//*[contains(text(),'FTE') and contains(text(),'required')] | " +
        "//input[@id='TextField304']/following::*[contains(@class,'error')][1]"
    );
    
    /** Resources Dropdown Trigger - Click to open dropdown (avoid dynamic IDs) */
    public static By resourcesDropdownTrigger = By.xpath(
        "//div[contains(@class,'ms-Dropdown')]//span[contains(@id,'Dropdown') and contains(@id,'-option')] | " +
        "//div[contains(@class,'Dropdown')]//button | " +
        "//span[contains(@id,'Dropdown339-option')] | " +
        "//div[contains(@id,'Dropdown339')]"
    );
    /** Costs Dropdown Trigger - Click to open dropdown (avoid dynamic IDs) */
    public static By CostsDropdownTrigger = By.xpath(
			// Prefer button/trigger elements; avoid matching option items
    		"//span[@id='select_Category-option'] |"+
			"//div[contains(@class,'ms-Dropdown')]//button | " +
			"//div[contains(@class,'Dropdown')]//button | " +
			"//span[contains(@id,'Dropdown339-option')] | " +
			"//div[contains(@id,'Dropdown339')]"
    );
    
    /** Resources Dropdown Option - Static reference */
    public static By resourcesDropdownOption = By.xpath("//div[contains(@class,'ms-Dropdown-items')]//button[@role='option']");
    
    /** Resources Dropdown - Dynamic option selector */
    public static By getResourcesDropdownOption(String optionText) {
        return By.xpath(
            "//button[contains(@id,'Dropdown') and contains(@id,'-list')]//span[contains(text(),'" + optionText + "')] | " +
            "//div[contains(@class,'ms-Dropdown-items')]//button[contains(.,'" + optionText + "')] | " +
            "//span[contains(@id,'Dropdown') and contains(@id,'-option') and contains(text(),'" + optionText + "')] | " +
            "//*[@role='option'][contains(.,'" + optionText + "')] | " +
            "//*[@role='listbox']//*[contains(text(),'" + optionText + "')]"
        );
    }
    
    /** Cost category Dropdown Option - Static reference */
   
    public static By CostcategoryDropdownOption = By.xpath("//span[@id='select_Category-option']");
    /** CostcategoryDropdownOption  - Dynamic option selector */
    public static By getCostcategoryDropdownOption(String optionText) {
        String val = optionText == null ? "" : optionText.trim();
        String literal = toXpathLiteral(val);
        String xpath =
            "//button[contains(@id,'Dropdown') and contains(@id,'-list')]//span[normalize-space(.)=" + literal + "] | " +
            "//div[contains(@class,'ms-Dropdown-items')]//button[normalize-space(.)=" + literal + "] | " +
            "//span[contains(@id,'Dropdown') and contains(@id,'-option') and normalize-space(.)=" + literal + "] | " +
            "//*[@role='option' and normalize-space(.)=" + literal + "] | " +
            "//*[@role='listbox']//*[normalize-space(.)=" + literal + "] | " +
            "//button[@id='select_Category-list2']/span[normalize-space(.)=" + literal + "] | " +
            "//span[@id='select_Category-option' and normalize-space(.)=" + literal + "] | " +
            "//*[contains(normalize-space(.)," + literal + ") ]";
        return By.xpath(xpath);
    }

    private static String toXpathLiteral(String s) {
        if (s == null) return "''";
        if (!s.contains("'")) {
            return "'" + s + "'";
        }
        String[] parts = s.split("'");
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            sb.append("'").append(parts[i]).append("'");
            if (i != parts.length - 1) sb.append(", \"'\", ");
        }
        sb.append(")");
        return sb.toString();
    }
    /** Cost Type Option - Static reference (legacy; prefer getCostTypeRadioOption) */
    // public static By CostTypeOption = By.xpath("//span[contains(@id,'Dropdown') and contains(@id,'-option')]");
    // Fixed invalid XPath (removed stray closing bracket). Kept only as fallback/legacy.
    public static By CostTypeOption = By.xpath("//div[3]/div[3]/div/div/div[2]/div[2]/div/div[3]/div[2]/div/label[2]/input");

    /**
     * Get dynamic locator for Cost Type radio option by visible label text (from Excel)
     * Supported Excel values: "Running", "Fixed"
     * Matches case-insensitively and also matches longer UI labels (e.g. "Fixed Cost").
     */
    public static By getCostTypeRadioOption(String costTypeText) {
        String val = costTypeText == null ? "" : costTypeText.trim().toLowerCase();
        // Normalize expected to one of the two supported keys
        if ("fixed".equals(val)) val = "fixed";
        else if ("running".equals(val)) val = "running";

        String literal = toXpathLiteral(val);
        String lower = "translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";
        String xpath =
            // BEST: scope under the "Cost Type" field label then pick the option by text
            "//*[self::label or self::span or self::p][contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cost type')]" +
            "/following::label[contains(" + lower + "," + literal + ")]//input[@type='radio' or contains(@type,'radio')] | " +
            "//*[self::label or self::span or self::p][contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cost type')]" +
            "/following::*[@role='radio' and (.//*[contains(" + lower + "," + literal + ")] or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + literal + "))] | " +
            // Prefer radio input under a label containing the text (case-insensitive, contains)
            "//label[contains(" + lower + "," + literal + ")]//input[@type='radio' or contains(@type,'radio')] | " +
            "//label[.//*[contains(" + lower + "," + literal + ")]]//input[@type='radio' or contains(@type,'radio')] | " +
            // Fluent UI / aria patterns
            "//*[@role='radio' and (contains(" + lower + "," + literal + ") or .//*[contains(" + lower + "," + literal + ")] or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + literal + "))] | " +
            "//input[@type='radio' and (contains(translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + literal + ") or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + literal + "))] | " +
            // Last resort: any clickable element with the text
            "//*[self::label or self::span or self::div][contains(" + lower + "," + literal + ")]";
        return By.xpath(xpath);
    }
    /** Role  Error alert message */
    public static By RoleErrorAlert = By.xpath(
    	
        "//*[contains(text(),'Role Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Role Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Role Should not be left blank')]"
    );
    /** Skill  Error alert message */
    public static By SkillErrorAlert = By.xpath(
        "//*[contains(text(),'Skill Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Skill Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Skill Should not be left blank')]"
    );
    /** Resource In-Date  Error alert message */
    public static By ResourceInErrorAlert = By.xpath(
        "//*[contains(text(),'Resource-In date Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Resource-In date Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Resource-In date Should not be left blank')]"
    );
    
    /** Resource In-Date  Error alert message */
    public static By ResourceOutErrorAlert = By.xpath(
        "//*[contains(text(),'Resource-In date Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Resource-Out date Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'Out blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Resource-Out date Should not be left blank')]"
    );
    
    
    /** Resource In-Date  Error alert message */
    public static By ResourceFTEErrorAlert = By.xpath(
        "//*[contains(text(),'FTE Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'FTE Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'Out blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'FTE Should not be left blank')]"
    );
    
    
    /** Cost Category  Error alert message */
    public static By CostsCategoryErrorAlert = By.xpath(
        "//*[contains(text(),'Cost Category Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Cost Category Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Cost Category Should not be left blank')]"
    );
    
    /** Cost Type  Error alert message */
    public static By CostsTypeErrorAlert = By.xpath(
        "//*[contains(text(),'Cost Type') and contains(text(),'left blank')] | " +
        "//*[contains(text(),'Cost Type') and contains(text(),'not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Cost Type') and contains(.,'left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'Cost Type') and contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Cost Type') and contains(.,'left blank')]"
    );
    
    /** Amount  Error alert message */
    public static By AmountErrorAlert = By.xpath(
        "//*[contains(text(),'Amount Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Amount Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Amount Should not be left blank')]"
    );
    /** From Date  Error alert message */
    public static By FromDateErrorAlert = By.xpath(
        "//*[contains(text(),'From Date Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'From Date Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'From Date Should not be left blank')]"
    );
    
    /** To Date  Error alert message */
    public static By ToDateErrorAlert = By.xpath(
        "//*[contains(text(),'To Date Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'To Date Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'To Date Should not be left blank')]"
    );
    
    /** Description  Error alert message */
    public static By DecriptionErrorAlert = By.xpath(
        "//*[contains(text(),'Description Should not be left blank')] | " +
        "//div[contains(@class,'alert')][contains(.,'Description Should not be left blank')] | " +
        "//div[contains(@class,'ms-MessageBar')][contains(.,'left blank')] | " +
        "//div[contains(@role,'alert')][contains(.,'Description Should not be left blank')]"
    );
    // ==================== INITIATIVE CODE ====================
   
    
    /** Initiative code displayed after save (in success message or header) */
    public static By initiativeCodeAfterSave = By.xpath("//input[@id='TextField31']");
    
    /** Array of initiative code locators (fallback strategies) */
    public static By[] initiativeCodeLocators = {
    	By.xpath("//input[@id='TextField31']"),
        By.xpath("//span[contains(text(),'INI-')]"),
        By.xpath("//p[contains(text(),'INI-')]"),
        By.xpath("//div[contains(text(),'INI-') and not(contains(@class,'modal'))]"),
        By.xpath("//h5[contains(text(),'INI-')]"),
        By.xpath("//td[contains(text(),'INI-')]"),
        By.xpath("//*[@id='initiativeCode' or @name='initiativeCode']")
    };
    
    // ==================== GETTER METHODS (Deprecated - Use direct access) ====================
    
    /**
     * Get Initiative Title locator
     * @return By locator for Initiative Title field
     * @deprecated Use InitiativePageLocators.IniTitle directly
     */
    @Deprecated
    public static By getIniTitle() {
        return IniTitle;
    }
    
    /**
     * Get Additional Notes locator
     * @return By locator for Additional Notes textarea
     * @deprecated Use InitiativePageLocators.additionalNotes directly
     */
    @Deprecated
    public static By getAdditionalNotes() {
        return additionalNotes;
    }
    
    public static By getSelectedCostCategoryValue() {
        return CostcategoryDropdownOption;
    }
    
    public static By submitfinal = 
            By.xpath("//button[.//span[normalize-space()='Submit']]");
 
    
    
    public static By approvefinal = 
            By.xpath("//button[.//span[normalize-space()='Approve']]");
 
    
    
    public static By clickpush = 
            By.xpath("//button[.//span[normalize-space()='Push Back']]");
  
 // ================= DATE PICKERS =================

 // Resource-In Date (1st DatePicker)
    public static By resourceInDate1 =
    	    By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[1]");

    	public static By resourceOutDate1 =
    	    By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[2]");

    	public static By nextMonthBtn =
    	    By.xpath("//button[contains(@title,'next month')]");

    	public static By prevMonthBtn =
    	    By.xpath("//button[contains(@title,'previous month')]");

    	

       
    	public static By skillclickoption=
        	    By.xpath("//div[contains(@class,'ms-Dropdown-items')]//label[contains(@class,'ms-Checkbox-label')]");
    
//////////////////////////////////////////////////////////////////////////////////////////////////
    	public static By enter=
    	    By.xpath("//input[@placeholder='Enter Initiative Title']");


    	public static By des =
    	    By.xpath("(//textarea[contains(@class,'ms-TextField-field')])[2]");
//////////////////////////////////////////////////////////////////////////////////////////////////////
    	public static By clickbg=
        	    By.xpath("//span[normalize-space()='Select Business Group']/ancestor::div[@role='combobox']");


        	public static By clickbgoption =
        	    By.xpath("//div[@role='listbox']//button[@role='option']");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        	public static By clickou=
            	    By.xpath("//span[normalize-space()='Select Organization Unit']");


            	public static By clickouoption =
            	    By.xpath("//div[@role='listbox']//button[@role='option']");
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////   
            	public static final By PLANNED_START_DATE =
            		    By.xpath("(//div[@role='combobox' and contains(@class,'readOnlyTextField')])[1]");

            		public static final By PLANNED_END_DATE =
            		    By.xpath("(//div[@role='combobox' and contains(@class,'readOnlyTextField')])[2]");
            		
            		
            	
            
            		public static By toastMessage = By.xpath("//div[@role='alert']");  		
            		
            		//div[@role='combobox' and @id='select_Category']

            		public static By clickccf=
                    	    By.xpath("//span[normalize-space()='Select Cost Category']");		
            		
            		public static By clickccoptionfunding=
                    	    By.xpath("//div[contains(@id,'Dropdown') and @role='listbox']//button[@role='option']");		
            		
            		public static By clicksave=
                    	    By.xpath("//button[contains(@class,'ms-Button--primary')]//span[normalize-space()='Save']/ancestor::button");		
            		
            		public static By basicdetail=
                    	    By.xpath("//a[normalize-space()='Basic Details']");		
            		
            		
            		public static By  selectdocumentcatgory =
            			    By.xpath("//span[starts-with(@id,'Dropdown') and contains(@id,'-option')]");


            		public static By selectdocumentcatgoryalloption=
            		By.xpath("//div[@role='listbox']//button[@role='option'][not(.//span[text()='Select a category'])][1]");
            		
            		
            		public static By document=
                    		By.xpath("//a[normalize-space()='Document Upload']");
                    		

            		public static By uploaddocument=
                    		By.xpath("//button[normalize-space()='Upload Document']");
            		
            		
            		public static By upload=
                    		By.xpath("//input[@id='fileUpload']");
            		
            		public static By enterdescription=
                    		By.xpath("//textarea[starts-with(@id,'TextField')]");
            		
            		
            		public static By clickfinalupload=
            				By.xpath("//button[normalize-space()='Upload']");
            		
            		
            		public static By clickcc=
                    	    By.xpath("//div[@id='select_Category' and @role='combobox']");		
            	
            		public static By clickCCoption=
                    	    By.xpath("//div[@role='listbox']//button[@role='option']");
            		
            		
            		public static By clickcc1=
                    	    By.xpath("//div[@id='select_Category' and @role='combobox']");		
            	
            		public static By clickCCoption1=
                    	    By.xpath("//div[@role='listbox']//button[@role='option']");
            		
            		

            		public static By clickwithdraw=
                    	    By.xpath("//span[normalize-space()='Withdraw Initiative']");
            		
            		public static By clickedit =
            				By.xpath("//*[name()='path' and contains(@d,'M3 17.25V2')]");
            		
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    		
            		public static By Risks =
            				By.xpath("//a[normalize-space()='Risks/Action Items']");
            		
            		
            		public static By add  =
            				By.xpath("//button[.//span[normalize-space()='Add']]");
            		
            		public static By desrisk =
            				By.xpath("//textarea[starts-with(@id,'TextField') and contains(@class,'ms-TextField-field')]");
            		
            		
            		public static By impdes =
            				By.xpath("(//textarea[starts-with(@id,'TextField') and contains(@class,'ms-TextField-field')])[2]");
            		
            		public static By riskcatgory =
            				By.xpath("//span[normalize-space()='Select Risk Category']");
            		
            		
            		public static By riskcatgorylist =
            				By.xpath("//div[@role='listbox']//button[@role='option']");
            		
            		
            		public static By status =
            				By.xpath("//span[normalize-space()='Select Status']");
            		
            		public static By statuslist =
            				By.xpath("//div[@role='listbox']//button[@role='option']");
            		
            		public static By proba =
            				By.xpath("(//input[@type='number' and contains(@class,'ms-TextField-field')])[1]");
            		
            		public static By impact=
            				By.xpath("(//input[@type='number' and contains(@class,'ms-TextField-field')])[2]");
            		
            		
            		public static By noi =
            			    By.xpath("//table[contains(@class,'table-striped')]//tbody/tr[1]/td");
            			             
            		public static By dateiden=
            			    By.xpath("//div[@role='combobox' and .//span[normalize-space()='Select a date']]");
            	
            		public static By clickrisk=
            			    By.xpath("//button[.//span[normalize-space()='Save']]");
            	
            		
            		public static By roi=
            			    By.xpath("//a[normalize-space()='ROI']");
            	
            		//public static By addroi=
            		//	    By.xpath("//button[.//span[normalize-space()='Add']]");
            	
            		public static By monthdroplist=
            			    By.xpath("(//div[@role='listbox']//button[@role='option'])[2]");
            	
            		public static By monthdrop=
            			    By.xpath("//span[normalize-space()='Select Month']/ancestor::*[@role='combobox'][1]");
            	
            		
            		public static By yeardrop=
            			    By.xpath("//span[normalize-space()='Select Year']/ancestor::*[@role='combobox'][1]");
            		
            			
            		public static By yeardroplist=
            			    By.xpath("//div[@role='listbox']//button[@role='option']");
            		
            		public static By roiprojected=
            			    By.xpath("	//input[contains(@class,'MuiOutlinedInput-input')]");
            		
            		
            		public static By workflow =
            			    By.xpath("//a[normalize-space()='Workflow History']");
            		
            		
            		
            		public static By clickaction =
            			    By.xpath("//div[contains(@class,'ms-Dropdown')]");
            		
            		public static By action =
            			    By.xpath("//div[@role='listbox']//button[.//span[normalize-space()='Submitted']]");
            		
            		public static By go =
            			    By.xpath("//button[normalize-space()='Go Back To List View']");
            	
            		public static By draft =
            			    By.xpath("//span[normalize-space()='Draft']");
            		
            		
            		 public static By searchtoolbar= By.xpath("//img[@aria-label='Search']");
            		 
            		 public static By watchlist= By.xpath("//span[@id='FltrCountWatchlist']"); 
            		 
            		 public static By entercode= By.xpath("//input[@id='DemandCode']");
            		 
            		 public static By searchfin= By.xpath("//button[.//span[text()='Search']]");
            		 
            		 public static By close= By.xpath("//div[@class='search-list-container']//button[1]");
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
            		 
}
