package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Warehouse Management Module
 *
 * This class contains all locators used in Warehouse page automation.
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
public class WarehousePageLocators {

    // ==================== NAVIGATION ====================

    /** Warehouse navigation element - Updated by Shahu.D */
    public static By warehouseNav = By.xpath("//div[@aria-label='Warehouse']//img[@alt='Warehouse'] | //img[@alt='Warehouse'] | //div[contains(@class,'navigation')]//img[contains(@alt,'Warehouse')]"); // Updated by Shahu.D
//    public static By entertitle = By.xpath("//input[@id='initiativeCode']"); // Updated by Shahu.D
    /** Warehouse page card/button - Updated by Shahu.D */
    public static By warehouseCard = By.xpath(
            "//*[@id=\"children-panel-container\"]/div[3]/div[5]/p | " +
            "//*[@id='children-panel-container']/div[3]/div[5]/p | " +
            "//*[@id=\"children-panel-container\"]//p[contains(text(),'Warehouse')] | " +
            "//p[contains(text(),'Warehouse')]"
    ); // Updated by Shahu.D

    // ==================== PAGE HEADERS ====================

    /** Warehouse page header - Updated by Shahu.D */
    public static By warehousePageHeader = By.xpath("//h5[contains(text(),'Warehouse')] | //h2[contains(text(),'Warehouse')] | //h1[contains(text(),'Warehouse')]"); // Updated by Shahu.D

    // ==================== VIEW TOGGLE ====================

    /** Card View button - Updated by Shahu.D */
    public static By cardViewButton = By.xpath(
            "(.//*[normalize-space(text()) and normalize-space(.)='Administrators'])[1]/following::*[name()='svg'][1] | " +
            "//div[@class='css-4io43t'] | " +
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button | " +
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button//*[name()='svg'] | " +
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button//*[name()='svg']//*[name()='path'] | " +
            "//button[contains(@aria-label,'Card View') or contains(@aria-label,'card view')] | " +
            "//button[contains(@title,'Card View') or contains(@title,'card view')]"
    ); // Updated by Shahu.D

    /** View Chart button - Updated by Shahu.D */
  //  public static By viewChartButton = By.xpath(
  //          "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1] | " +
  //          "//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[1] | " +
  //          "//button[contains(@aria-label,'View Chart') or contains(@aria-label,'view chart')] | " +
  //          "//button[contains(@title,'View Chart') or contains(@title,'view chart')] | " +
  //          "//div[contains(@class,'chart') or contains(@aria-label,'Chart')]"
  //  ); // Updated by Shahu.D

    // ==================== SEARCH ====================

    public static By viewChartButton = By.xpath("//button[normalize-space()='View Charts']");
    
    
    
    /** Search icon/button - Updated by Shahu.D */
    public static By searchIcon = By.xpath(
            "//img[@alt='Search'] | " +
            "//img[contains(@alt,'Search')] | " +
            "//img[contains(@alt,'search')]"
    ); // Updated by Shahu.D

    /** Initiative Code input field - Updated by Shahu.D */
 
    //public static By initiativeCodeTxt = By.id("initiativeCode"); // Updated by Shahu.D
    public static By initiativeCodeTxt = By.xpath("//input[@id='initiativeCode']"); // Updated by Shahu.D
    /** Initiative Title input field - Updated by Shahu.D */
    public static By initiativeTitleTxt = By.id("initiativeTitle"); // Updated by Shahu.D

    /** Nature of Initiative dropdown - Updated by Shahu.D */
//    public static By natureDropdown = By.id("natureOfInitiativeID-option"); // Updated by Shahu.D
    public static By natureDropdown = By.xpath("//span[@id='natureOfInitiativeID-option']");
    /** Nature of Initiative dropdown value - Updated by Shahu.D */
  //  public static By natureValue = By.xpath(
  //          "//button[@id='natureOfInitiativeID-list1']/span/span | " +
  //          "//button[@id='natureOfInitiativeID-list1'] | " +
   //         "//*[@id='natureOfInitiativeID-list1']//span"
 //   ); // Updated by Shahu.D

    
    public static By natureValue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    
    /** Status dropdown - Updated by Shahu.D */
  // public static By statusDropdown = By.id("StatusID-option"); // Updated by Shahu.D
  public static By statusDropdown = By.xpath("//span[@id='StatusID-option']"); // Updated by Shahu.D
  
    /** Status dropdown value - Updated by Shahu.D */
/*   public static By statusValue = By.xpath(
            "//*[@id='StatusID-list1']/span/span | " +          // Primary as per latest user XPath - Updated by Shahu.D
            "//*[@id='StatusID-list2']/span/span | " +
            "//button[@id='StatusID-list1']/span/span | " +
            "//button[@id='StatusID-list2']/span/span | " +
            "//button[@id='StatusID-list2']/span | " +
            "//button[@id='StatusID-list1']/span | " +
            "//*[@id='StatusID-list2']//span | " +
            "//button[@id='StatusID-list2'] | " +
            "//button[@id='StatusID-list1'] | " +
            "//*[@id='StatusID-list1']//span"
    ); // Updated by Shahu.D
 */
  
  public static By statusValue = By.xpath("//div[@role='listbox']//span[contains(@class,'ms-Dropdown-optionText')]");
  public static By editini = By.xpath("//*[@data-testid='CreateOutlinedIcon']");
  
  
    /** Search button (to execute search) - Updated by Shahu.D */
    public static By searchButton = By.xpath(
            "//button[contains(text(),'Search')] | " +
            "//button[.//span[text()='Search']] | " +
            "//button[@type='submit'] | " +
            "//button[contains(@aria-label,'Search')]"
    ); // Updated by Shahu.D

    // ==================== EDIT & SUBMIT ====================

    /** Edit Initiative button - Updated by Shahu.D */
    public static By editInitiativeButton = By.xpath(
            "(.//*[normalize-space(text()) and normalize-space(.)='Start'])[1]/following::*[name()='svg'][1] | " +
            "//*[@id=\"row3945-4\"]/div/div[7]/div/div/button[1]/svg | " +
            "//*[@id='row3945-4']/div/div[7]/div/div/button[1]/svg | " +
            "//button[1]//svg[ancestor::div[@id='row3945-4']] | " +
            "//*[contains(@id,'row')]/div/div[7]/div/div/button[1]/svg"
    ); // Updated by Shahu.D

    /** Comment link button - Updated by Shahu.D */
    public static By commentLinkButton = By.xpath(
            "//*[@id=\"row1695-0\"]/div/div[7]/div/div/button[2]/svg/path | " + // Primary locator
            "//*[@id='row1695-0']/div/div[7]/div/div/button[2]/svg/path | " +
            "//button[2]//svg/path[ancestor::div[@id='row1695-0']] | " +
            "//*[contains(@id,'row')]/div/div[7]/div/div/button[2]/svg/path | " +
            "//button[2]/svg/path[ancestor::div[@role='row']]"
    ); // Updated by Shahu.D

    /** Comment text field in modal - Updated by Shahu.D */
    // Updated by Shahu.D - Comment text field locators with user-provided XPaths
    public static By commentTextField = By.xpath("//textarea[@placeholder='Write your comment...']"); // Updated by Shahu.D - Primary locator
    public static By commentTextFieldFallback1 = By.xpath("//textarea"); // Updated by Shahu.D - Fallback locator
    public static By commentTextFieldFallback2 = By.xpath("//div[contains(@class,'modal')]//textarea"); // Updated by Shahu.D - Additional fallback

    /** Post button in comment modal - Updated by Shahu.D */
    public static By postButton = By.xpath(
            "/html/body/div[2]/div[3]/div/div[2]/button | " +
            "//button[contains(text(),'Post')] | " +
            "//button[.//span[text()='Post']] | " +
            "//div[contains(@class,'modal')]//button[contains(text(),'Post')]"
    ); // Updated by Shahu.D

    /** Alert message for blank comment - Updated by Shahu.D */
    public static By blankCommentAlert = By.xpath(
            "//div[contains(text(),'Comment') and (contains(text(),'blank') or contains(text(),'required') or contains(text(),'empty'))] | " +
            "//span[contains(text(),'Comment') and (contains(text(),'blank') or contains(text(),'required') or contains(text(),'empty'))] | " +
            "//*[contains(text(),'should not be left blank')] | " +
            "//*[contains(text(),'Comment should not be left blank')]"
    ); // Updated by Shahu.D

    /** Save As Draft button - Updated by Shahu.D */
    public static By saveAsDraftButton = By.xpath(
            "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div[1]/span[9] | " +
            "//span[9][ancestor::div[contains(@class,'div')]] | " +
            "//button[contains(text(),'Save as Draft')] | " +
            "//span[contains(text(),'Save as Draft')] | " +
            "//button[contains(text(),'Save As Draft')]"
    ); // Updated by Shahu.D

    /** Founder Name dropdown - Updated by Shahu.D */
    public static By founderNameDropdown = By.xpath(
            "//*[@id=\"Dropdown1786\"]/span[2] | " + // Updated by Shahu.D - Primary locator
            "//*[@id='Dropdown1786']/span[2] | " + // Updated by Shahu.D
            "//*[@id='Dropdown1786'] | " + // Updated by Shahu.D
            "//span[2][ancestor::*[@id='Dropdown1786']] | " + // Updated by Shahu.D
            "//div[@id='Dropdown1786']//span[2] | " + // Updated by Shahu.D
            "//*[@id=\"Dropdown3996\"]/span[2] | " + // Updated by Shahu.D - Fallback to old ID
            "//*[@id='Dropdown3996']/span[2] | " + // Updated by Shahu.D
            "//*[@id='Dropdown3996'] | " + // Updated by Shahu.D
            "//span[2][ancestor::*[@id='Dropdown3996']] | " + // Updated by Shahu.D
            "//div[@id='Dropdown3996']//span[2]" // Updated by Shahu.D
    ); // Updated by Shahu.D

    /** Submit button - Updated by Shahu.D */
    public static By submitButton = By.xpath(
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div[1]/span[7] | " +
            "//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div[1]/span[7] | " +
            "//span[7][ancestor::div[contains(@class,'div')]] | " +
            "//button[contains(text(),'Submit')] | " +
            "//span[contains(text(),'Submit')]"
    ); // Updated by Shahu.D

    /** Checklist button - Updated by Shahu.D */
    public static By checklistButton = By.xpath(
            "//button[contains(text(),'Checklist')] | " +
            "//span[contains(text(),'Checklist')] | " +
            "//button[.//span[contains(text(),'Checklist')]] | " +
            "//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div/div/div/div/div[1]//button[contains(text(),'Checklist')] | " +
            "//div[contains(@class,'button') and contains(text(),'Checklist')]"
    ); // Updated by Shahu.D

    /** Submit Comment textbox in modal - Updated by Shahu.D */
    public static By submitCommentTextbox = By.xpath("//textarea[@id='TextField1283']"); // Updated by Shahu.D - Primary locator
    
    /** Submit Comment textbox fallback locator - Updated by Shahu.D */
    public static By submitCommentTextboxFallback = By.xpath("//label[text()='Comments for Submit']/following::textarea[1]"); // Updated by Shahu.D - Fallback locator
    
    /** Submit Comment textbox container div - Updated by Shahu.D */
    public static By submitCommentTextboxContainer = By.xpath("//div[@class='css-4io43t']"); // Updated by Shahu.D - Alternative locator
    
    /** Submit Comment textbox by old ID - Updated by Shahu.D */
    public static By submitCommentTextboxOldId = By.id("TextField1040"); // Updated by Shahu.D - Legacy ID locator

    /** Submit Comment button in modal - Updated by Shahu.D */
    public static By submitCommentButton = By.xpath("//button[.//span[text()='Submit']]"); // Updated by Shahu.D - Primary locator using text-based XPath
    
    /** Submit Comment button ID fallback - Updated by Shahu.D */
    public static By submitCommentButtonId = By.id("id__1045"); // Updated by Shahu.D - Fallback locator using ID
    
    /** Submit Comment button XPath fallback - Updated by Shahu.D */
    public static By submitCommentButtonXPath = By.xpath("//*[@id='id__1045'] | //button[@id='id__1045'] | //*[contains(@class,'button') and contains(text(),'Submit')]"); // Updated by Shahu.D - Additional fallback locator

    /** Mandatory alert for Founder Name - Updated by Shahu.D */
    public static By founderNameMandatoryAlert = By.xpath(
            "//div[contains(text(),'Founder Name') and contains(text(),'required')] | " +
            "//span[contains(text(),'Founder Name') and contains(text(),'required')] | " +
            "//*[contains(text(),'Founder Name') and (contains(text(),'required') or contains(text(),'mandatory'))] | " +
            "//div[contains(@class,'error')]//*[contains(text(),'Founder Name')]"
    ); // Updated by Shahu.D

    // ==================== DYNAMIC HELPER METHODS ====================

    /**
     * Get dynamic locator for row by identifier
     * Updated by Shahu.D
     * @param identifier Identifier to search for
     * @return By locator for the row containing the identifier
     */
    public static By getRowByIdentifier(String identifier) {
        return By.xpath("//div[@role='row' and .//div[contains(text(),'" + identifier + "')]] | //tr[.//td[contains(text(),'" + identifier + "')]]"); // Updated by Shahu.D
    }

    /**
     * Get dynamic locator for Edit Initiative button by Initiative Code
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to find edit button for
     * @return By locator for the edit button in the row containing the initiative code
     */
    public static By getEditButtonByInitiativeCode(String initiativeCode) {
        return By.xpath("//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]]//div/div[7]/div/div/button[1]/svg | " +
                "//tr[.//td[contains(text(),'" + initiativeCode + "')]]//button[1]/svg | " +
                "//*[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[1]/svg"); // Updated by Shahu.D
    }

    /**
     * Get dynamic locator for Founder Name dropdown option
     * Updated by Shahu.D
     * @param founderName Founder name to select
     * @return By locator for the founder name option
     */
    public static By getFounderNameOption(String founderName) {
        return By.xpath(
            "//li[contains(text(),'" + founderName + "')] | " +
            "//option[contains(text(),'" + founderName + "')] | " +
            "//span[contains(text(),'" + founderName + "')] | " +
            "//div[contains(text(),'" + founderName + "')] | " +
            "//*[@id='Dropdown1786']//li[contains(text(),'" + founderName + "')] | " + // Updated by Shahu.D - New ID
            "//*[@id='Dropdown3996']//li[contains(text(),'" + founderName + "')]" // Updated by Shahu.D - Old ID fallback
        ); // Updated by Shahu.D
    }

    /**
     * Get dynamic locator for Comment link button by Initiative Code
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to find comment link for
     * @return By locator for the comment link button in the row containing the initiative code
     */
    public static By getCommentLinkByInitiativeCode(String initiativeCode) {
        return By.xpath(
            "//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]]//div/div[7]/div/div/button[2]/svg/path | " +
            "//tr[.//td[contains(text(),'" + initiativeCode + "')]]//button[2]/svg/path | " +
            "//*[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[2]/svg/path | " +
            "//*[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[2]//svg | " +
            "//div[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[2]"
        ); // Updated by Shahu.D
    }

    /** History link button - Updated by Shahu.D */
    public static By historyLinkButton = By.xpath(
            "//*[@id=\"row231-0\"]/div/div[7]/div/div/button[3]/svg | " +
            "//button[3]//svg[ancestor::div[@role='row']] | " +
            "//*[contains(@id,'row')]/div/div[7]/div/div/button[3]/svg"
    ); // Updated by Shahu.D

    /** Action Taken dropdown field - Updated by Shahu.D */
    public static By actionTakenDropdown = By.xpath("//div[@id='Dropdown25']/span[2]"); // Updated by Shahu.D - Primary locator
    public static By actionTakenDropdownFallback1 = By.xpath("//*[@id='Dropdown25']/span[2]"); // Updated by Shahu.D - Fallback locator
    public static By actionTakenDropdownFallback2 = By.xpath("//*[@id='Dropdown25'] | //div[@id='Dropdown25']"); // Updated by Shahu.D - Additional fallback
    public static By actionTakenDropdownFallback3 = By.xpath("//label[contains(text(),'Action Taken')]/following::div//span[2] | //label[contains(text(),'Action Taken')]/following::*[@id[starts-with(.,'Dropdown')]]/span[2]"); // Updated by Shahu.D - Label-based fallback
    public static By actionTakenDropdownFallback4 = By.xpath("//*[contains(@id,'Dropdown') and contains(@id,'25')]/span[2]"); // Updated by Shahu.D - Dynamic ID pattern
    public static By actionTakenDropdownFallback5 = By.xpath("//*[@id='Dropdown244']/span[2] | //div[@id='Dropdown244']/span[2]"); // Updated by Shahu.D - Old ID fallback

    /** Action Taken dropdown option for "Submitted" - Updated by Shahu.D */
    public static By actionTakenSubmittedOption = By.xpath("//span[contains(@class,'ms-Dropdown-optionText') and normalize-space()='Submitted']"); // Updated by Shahu.D - Primary locator using class
    public static By actionTakenSubmittedOptionFallback1 = By.xpath("//span[normalize-space(text())='Submitted'] | //*[normalize-space(text())='Submitted']"); // Updated by Shahu.D - Text-based fallback
    public static By actionTakenSubmittedOptionFallback2 = By.xpath("//button[@id='Dropdown25-list3']/span | //*[@id='Dropdown25-list3']/span"); // Updated by Shahu.D - ID-based fallback
    public static By actionTakenSubmittedOptionFallback3 = By.xpath("//*[contains(@id,'Dropdown25-list3')]//span[contains(text(),'Submitted')] | //button[contains(@id,'Dropdown25-list3')]"); // Updated by Shahu.D - Additional fallback
    public static By actionTakenSubmittedOptionFallback4 = By.xpath("//*[@id='Dropdown244-list3']/span/span | //button[@id='Dropdown244-list3']/span"); // Updated by Shahu.D - Old ID fallback

    /**
     * Get dynamic locator for History link button by Initiative Code
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to find history link for
     * @return By locator for the history link button in the row containing the initiative code
     */
    public static By getHistoryLinkByInitiativeCode(String initiativeCode) {
        return By.xpath(
            "//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]]//div/div[7]/div/div/button[3]/svg | " +
            "//tr[.//td[contains(text(),'" + initiativeCode + "')]]//button[3]/svg | " +
            "//*[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[3]/svg | " +
            "//*[contains(text(),'" + initiativeCode + "')]/ancestor::div[@role='row']//button[3]"
        ); // Updated by Shahu.D
    }

    /** Pagination button - Updated by Shahu.D */
    public static By paginationButton = By.xpath("//*[name()='svg' and @data-testid='ArrowForwardIcon']");
          
   // Updated by Shahu.D

    /** Grid rows for counting records - Fluent UI virtualized grid - Updated by Shahu.D */
    public static By gridRows = By.xpath(
            "//div[@role='row' and .//div[@role='gridcell']] | " +
            "//div[@role='row' and .//*[@role='gridcell']]"
    ); // Updated by Shahu.D - Fluent UI virtualized grid pattern: role="row" containing role="gridcell"

    /** Grid container for scrolling - Updated by Shahu.D */
    public static By gridContainer = By.xpath(
            "//div[@role='grid'] | " +
            "//div[contains(@class,'DetailsList')] | " +
            "//div[contains(@class,'ms-DetailsList')] | " +
            "//div[@role='table']"
    ); // Updated by Shahu.D - Grid container for scrolling to ensure rows are rendered
}

