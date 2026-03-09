package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Completed Initiative Management Module
 * 
 * This class contains all locators used in Completed Initiative page automation.
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
public class CompletedInitiativePageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative Management navigation element */
    public static By initiativeManagementNav = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']");
    
    /** Completed Initiative card/button */
    public static By completedInitiativeCard = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[3]/p");
    
    // ==================== PAGE HEADERS ====================
    
    /** Completed Initiative page header (add more as needed) */
    // public static By completedInitiativePageHeader = By.xpath("//h5[text()='Completed Initiative']");
    
    // ==================== SEARCH ====================
    
    /** Search icon/button - Primary */
    public static By searchInput = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/img");
    
    /** Search input field - Alternative locators (if needed) */
    public static By searchInputAlt1 = By.xpath("//input[@placeholder='Search']");
    public static By searchInputAlt2 = By.xpath("//input[contains(@placeholder,'Search')]");
    public static By searchInputAlt3 = By.xpath("//input[@type='text' and contains(@placeholder,'Search')]");
    public static By searchInputAlt4 = By.xpath("//input[@type='search']");
    
    /** Initiative Code input field */
    public static By initiativeCodeField = By.xpath("//*[@id=\"initiativeCode\"]");
    
    /** Search button to execute search */
    public static By searchButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[3]/span/span");
    
    /** No items message when no records found */
    public static By noItemsMessage = By.xpath("//*[contains(text(),'There are no items to show in this view.')]");
    
    /** Grid rows/records container (to verify records are displayed) */
    public static By gridRows = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row'] | //table//tbody//tr[@role='row']");
    
    // ==================== DROPDOWNS ====================
    
    /** Nature of Initiative dropdown field */
    public static By natureOfInitiativeDropdown = By.xpath("//*[@id=\"natureOfInitiativeId-option\"]");
    
    /** Business Group dropdown field */
    public static By businessGroupDropdown = By.xpath("//*[@id=\"businessGroupId-option\"]");
    
    /** Organization Unit dropdown field */
    public static By organizationUnitDropdown = By.xpath("//*[@id=\"organizationUnitId-option\"]");
    
    /** All iframes on page */
    public static By allIframes = By.tagName("iframe");
    
    // ==================== DYNAMIC HELPER METHODS ====================
    
    /**
     * Get dynamic locator for Nature of Initiative dropdown value
     * @return By locator for the NOI dropdown value
     */
    public static By getNatureOfInitiativeValue() {
        return By.xpath("//*[@id=\"natureOfInitiativeId-list1\"]/span/span");
    }
    
    /**
     * Get dynamic locator for Business Group dropdown value
     * @return By locator for the BG dropdown value
     */
    public static By getBusinessGroupValue() {
        return By.xpath("//*[@id=\"businessGroupId-list1\"]/span/span");
    }
    
    /**
     * Get dynamic locator for Organization Unit dropdown value
     * @return By locator for the OU dropdown value
     */
    public static By getOrganizationUnitValue() {
        return By.xpath("//*[@id=\"organizationUnitId-list1\"]/span/span");
    }
    
    /**
     * Get dynamic locator for dropdown option by text (generic)
     * @param optionText The option text to search for
     * @return By locator for the option
     */
    public static By getDropdownOptionByText(String optionText) {
        // Try multiple xpath patterns for better matching
        return By.xpath("//*[normalize-space(text())='" + optionText + "'] | " +
                       "//*[contains(normalize-space(text()),'" + optionText + "')] | " +
                       "//span[normalize-space(text())='" + optionText + "'] | " +
                       "//span[contains(normalize-space(text()),'" + optionText + "')] | " +
                       "//div[normalize-space(text())='" + optionText + "'] | " +
                       "//div[contains(normalize-space(text()),'" + optionText + "')]");
    }
    
    /**
     * Get dynamic locator for Nature of Initiative option by text
     * Searches within the NOI dropdown list structure
     * @param noiValue The NOI value to search for
     * @return By locator for the NOI option
     */
    public static By getNOIOptionByText(String noiValue) {
        return By.xpath("//*[@id='natureOfInitiativeId-list1']//*[normalize-space(text())='" + noiValue + "'] | " +
                       "//*[@id='natureOfInitiativeId-list1']//*[contains(normalize-space(text()),'" + noiValue + "')] | " +
                       "//*[@id='natureOfInitiativeId-list1']/span/span[normalize-space(text())='" + noiValue + "']");
    }
    
    /**
     * Get dynamic locator for Business Group option by text
     * Searches within the BG dropdown list structure
     * @param bgValue The BG value to search for
     * @return By locator for the BG option
     */
    public static By getBGOptionByText(String bgValue) {
        return By.xpath("//*[@id='businessGroupId-list1']//*[normalize-space(text())='" + bgValue + "'] | " +
                       "//*[@id='businessGroupId-list1']//*[contains(normalize-space(text()),'" + bgValue + "')] | " +
                       "//*[@id='businessGroupId-list1']/span/span[normalize-space(text())='" + bgValue + "']");
    }
    
    /**
     * Get dynamic locator for Organization Unit option by text
     * Searches within the OU dropdown list structure
     * @param ouValue The OU value to search for
     * @return By locator for the OU option
     */
    public static By getOUOptionByText(String ouValue) {
        return By.xpath("//*[@id='organizationUnitId-list1']//*[normalize-space(text())='" + ouValue + "'] | " +
                       "//*[@id='organizationUnitId-list1']//*[contains(normalize-space(text()),'" + ouValue + "')] | " +
                       "//*[@id='organizationUnitId-list1']/span/span[normalize-space(text())='" + ouValue + "']");
    }
    
    // ==================== COMMENT LOCATORS ====================
    
    /** Comment link/icon on initiative row - try multiple strategies */
    public static By commentLink = By.xpath("//button[2]//svg[contains(@class,'MuiSvgIcon')] | " +
                                            "//button[2]//svg | " +
                                            "//*[@id='row89-0']/div/div[7]/div/div/button[2]/svg | " +
                                            "//div[contains(@class,'ag-row')]//button[2]//svg | " +
                                            "//tr[contains(@role,'row')]//button[2]//svg");
    
    /** Comment link by specific row ID pattern */
    public static By getCommentLinkByRow(String rowId) {
        return By.xpath("//*[@id='" + rowId + "']/div/div[7]/div/div/button[2]/svg | " +
                       "//*[@id='" + rowId + "']//button[2]//svg");
    }
    
    /** First available comment link in the grid */
    public static By firstCommentLink = By.xpath("(//button[2]//svg)[1] | " +
                                                  "(//div[contains(@class,'ag-row')]//button[2]//svg)[1] | " +
                                                  "(//tr[contains(@role,'row')]//button[2]//svg)[1]");
    
    /** Comment textarea field */
    public static By commentTextarea = By.xpath("/html/body/div[2]/div[3]/div/div[2]/textarea | " +
                                                "//textarea[contains(@placeholder,'comment') or contains(@placeholder,'Comment')] | " +
                                                "//div[contains(@class,'MuiDialog')]//textarea | " +
                                                "//div[contains(@role,'dialog')]//textarea");
    
    /** Post button for comments */
    public static By postCommentButton = By.xpath("/html/body/div[2]/div[3]/div/div[2]/button | " +
                                                  "//button[contains(text(),'Post') or contains(text(),'POST')] | " +
                                                  "//div[contains(@class,'MuiDialog')]//button[contains(text(),'Post')] | " +
                                                  "//div[contains(@role,'dialog')]//button[contains(text(),'Post')]");
    
    /** Alert message locator (for validation messages) */
    public static By getAlertMessageLocator(String message) {
        return By.xpath("//*[contains(text(),'" + message + "')]");
    }
    
    // ==================== REPLY LOCATORS ====================
    // Updated by Shahu.D
    
    /** Reply link for posted comment */
    public static By replyLink = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div[2]/button | " +
                                          "//div[contains(@class,'comment')]//button[.//svg/path] | " +
                                          "//button[contains(@aria-label,'Reply') or contains(@aria-label,'reply')] | " +
                                          "//div[contains(@class,'comment')]//button[contains(text(),'Reply')] | " +
                                          "//div[contains(@class,'MuiDialog')]//button[contains(text(),'Reply')]");
    
    /** Reply textarea field */
    public static By replyTextarea = By.xpath("/html/body/div[2]/div[3]/div/div[3]/div[2]/div[2]/textarea | " +
                                              "//div[contains(@class,'reply')]//textarea | " +
                                              "//div[contains(@class,'MuiDialog')]//textarea[contains(@placeholder,'reply') or contains(@placeholder,'Reply')]");
    
    /** Reply button to submit reply */
    public static By replyButton = By.xpath("//*[name()='path' and contains(@d,'M10 9V5l-7')]");
    // ==================== INITIATIVE STATUS MANAGEMENT LOCATORS ====================
    // Updated by Shahu.D
    
    /** Initiative Status Management page link */
    public static By initiativeStatusManagementPage = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[11]/p");
    
    public static By clickinput = By.xpath("//img[@aria-label='Search']");
    
    public static By enterinput = By.xpath("//input[@id='demandCode']");
    
    /** Action dropdown field */
    public static By actionDropdown = By.xpath("//*[@id=\"Dropdown0-option\"] | //*[@id=\"Dropdown0\"]/span[2]");
    
    /** Mark Initiatives as Complete option in dropdown */
    public static By markInitiativesAsCompleteOption = By.xpath("//*[@id=\"Dropdown0-list2\"]/span/span");
    
    /** Show button */
    public static By showButton = By.xpath("//*[@id=\"id__1\"] | //*[@id=\"filtershowbtn\"] | //button[@id='filtershowbtn'] | //button[contains(text(),'Show')]");
    
    /** Action link of initiative (generic - will be made dynamic) */
    public static By actionLink = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[5]/td[6]/button/span/svg");
    
    /** Comment box for marking initiative as complete - Updated by Shahu.D */
    public static By commentBoxForMarkComplete = By.xpath("//*[@id=\"TextField99\"] | //textarea[contains(@id,'TextField') and contains(@placeholder,'Comment')] | //div[contains(@role,'dialog')]//textarea[contains(@placeholder,'Comment')] | //textarea[contains(@id,'TextField')] | //*[@id='TextField99'] | //textarea[@id='TextField99'] | //div[contains(@role,'dialog')]//textarea | //textarea[contains(@placeholder,'comment') or contains(@placeholder,'Comment')] | //*[contains(@id,'TextField') and contains(@type,'text')] | //input[contains(@id,'TextField')]");
    
    /** Save button for marking initiative as complete - Updated by Shahu.D */
    public static By saveButtonForMarkComplete = By.xpath("//*[@id=\"id__96\"] | //button[contains(@id,'id__') and contains(text(),'Save')] | //div[contains(@role,'dialog')]//button[contains(text(),'Save')] | //button[@id='id__96'] | //*[@id='id__96'] | //button[contains(text(),'Save')] | //button[contains(text(),'SAVE')] | //div[contains(@class,'MuiDialog')]//button[contains(text(),'Save')] | //*[contains(@id,'id__') and contains(@type,'button')] | //button[contains(@aria-label,'Save')]");
    
    /** Save button on confirmation popup - Updated by Shahu.D */
    public static By saveButtonOnConfirmationPopup = By.xpath("//div[@id='fluent-default-layer-host']//span[1]//button[1]");
    
    /** Workflow history link - Updated by Shahu.D */
    public static By workflowHistoryLink = By.xpath("//*[@id=\"row640-0\"]/div/div[7]/div/div/button[3]/svg/path | //*[@id='row640-0']/div/div[7]/div/div/button[3]/svg | //*[@id='row640-0']//button[3]//svg | //button[contains(@aria-label,'Workflow') or contains(@aria-label,'workflow')]//svg | //div[@id='row640-0']//button[contains(@class,'workflow') or contains(@aria-label,'history')]");
    
    /** Action Taken dropdown field - Updated by Shahu.D */
    public static By actionTakenDropdown = By.xpath("//*[@id=\"Dropdown677\"]/span[2] | //*[@id='Dropdown677']/span[2] | //*[@id='Dropdown677'] | //div[@id='Dropdown677']//span[2] | //*[@id='Dropdown333-option'] | //*[@id='Dropdown333'] | //*[contains(@id,'Dropdown677')] | //*[@role='combobox' and contains(@id,'Dropdown')]");
    
    /** Approved option in Action Taken dropdown - Updated by Shahu.D */
    public static By approvedOption = By.xpath("//*[@id=\"Dropdown333-list1\"]/span/span | //*[@id='Dropdown333-list1']/span/span | //*[@id='Dropdown333-list1']//span[contains(text(),'Approved')] | //*[contains(@id,'Dropdown333-list')]//span[contains(text(),'Approved')] | //*[@role='option' and contains(text(),'Approved')] | //li[contains(text(),'Approved')] | //*[contains(text(),'Approved')]");
    
    /** Edit link for Completed Initiative - Updated by Shahu.D */
    public static By editLink = By.xpath("//*[@id=\"row1336-0\"]/div/div[7]/div/div/button[1]/svg/path | //*[@id='row1336-0']/div/div[7]/div/div/button[1]/svg | //*[@id='row1336-0']//button[1]//svg | //button[contains(@aria-label,'Edit') or contains(@aria-label,'edit')]//svg");
    
    /** Workflow Details button - Updated by Shahu.D */
    public static By workflowDetailsButton = By.xpath("//button[contains(text(),'Workflow Details')] | //button[contains(text(),'Workflow details')] | //*[contains(text(),'Workflow Details')]");
    
    /** Workflow Information button - Updated by Shahu.D */
    public static By workflowInformationButton = By.xpath("//button[contains(text(),'Workflow Information')] | //button[contains(text(),'Workflow information')] | //*[contains(text(),'Workflow Information')]");
    
    /** Save button (should NOT be displayed) - Updated by Shahu.D */
    public static By saveButton = By.xpath("//button[contains(text(),'Save')] | //button[contains(text(),'SAVE')] | //button[@id='save'] | //*[@id='save']");
    
    /** Go Back To List View button - Updated by Shahu.D */
    public static By goBackToListViewButton = By.xpath("//*[@id=\"IMInfopgtabs\"]/button | //button[@id='IMInfopgtabs'] | //*[@id='IMInfopgtabs']//button | //button[contains(text(),'Go Back') or contains(text(),'go back')] | //button[contains(text(),'List View') or contains(text(),'list view')]");
    
    /** Switch to Card View icon/button - Updated by Shahu.D */
    public static By switchToCardViewButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/button | //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/button/svg | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button//svg | //button[contains(@aria-label,'Card') or contains(@aria-label,'card')] | //button[contains(@aria-label,'Card') or contains(@aria-label,'card')]//svg | //button[.//svg and contains(@aria-label,'View')] | //button[.//svg and (contains(@aria-label,'Card') or contains(@aria-label,'card'))] | //div[contains(@class,'toolbar') or contains(@class,'header')]//button[.//svg] | //div[contains(@class,'MuiToolbar')]//button[.//svg] | //button[.//svg and contains(@class,'MuiIconButton')]");
    
    /** Card container - Updated by Shahu.D */
    public static By cardContainer = By.xpath("//div[contains(@class,'card')] | //div[contains(@class,'Card')] | //*[contains(@class,'card-container')] | //div[contains(@id,'card')] | //*[contains(@class,'grid') and contains(@class,'card')]");
    
    /** List container/table - Updated by Shahu.D */
    public static By listContainer = By.xpath("//table | //div[contains(@class,'table')] | //div[contains(@class,'ag-root')] | //*[contains(@id,'row')] | //div[contains(@class,'list')]");
    
    /** View Chart button - Updated by Shahu.D */
    public static By viewChartButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/h2/button | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div//h2//button | //h2//button[contains(@aria-label,'Chart') or contains(@aria-label,'chart')] | //button[contains(text(),'Chart') or contains(text(),'chart')]");
    
    /** Top 5 Nature of Initiatives chart container - Updated by Shahu.D */
    public static By top5NatureChartContainer = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2] | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div//div//div//div//div[2]//div//div[2] | //div[contains(@class,'chart')] | //div[contains(text(),'Top 5')]");
    
    /** Nature of Initiative column header - Updated by Shahu.D */
    public static By natureOfInitiativeColumnHeader = By.xpath("//*[@id=\"header1486-nature-name\"] | //*[@id='header1486-nature-name'] | //div[contains(@id,'header') and contains(@id,'nature')] | //th[contains(text(),'Nature')] | //div[contains(@class,'ag-header-cell') and contains(text(),'Nature')]");
    
    /** Nature of Initiative column values in table - Updated by Shahu.D */
    public static By natureOfInitiativeColumnValues = By.xpath("//div[@role='row']//div[@data-item-key='nature'] | //div[@role='row']//div[contains(@class,'ms-DetailsRow-cell')][3] | //td[contains(@col-id,'nature')] | //div[contains(@col-id,'nature')] | //*[contains(@id,'nature') and contains(@id,'cell')] | //table//td[contains(@class,'nature')]");
    
    /**
     * Get dynamic locator for Action link by row number
     * @param rowNumber Row number in the table (1-based)
     * @return By locator for the action link in that row
     */
    public static By getActionLinkByRow(int rowNumber) {
        return By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowNumber + "]/td[6]/button/span/svg | " +
                       "//table/tbody/tr[" + rowNumber + "]//button[contains(@aria-label,'Action') or contains(@aria-label,'action')]");
    }
    
    /**
     * Get dynamic locator for initiative row by Initiative Code
     * @param initiativeCode The initiative code to search for
     * @return By locator for the row containing that initiative code
     */
    public static By getInitiativeRowByCode(String initiativeCode) {
        return By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]] | " +
                       "//tr[td[normalize-space(text())='" + initiativeCode + "']]");
    }
    
    // ==================== ADDITIONAL LOCATORS ====================
    // Add any other Completed Initiative specific locators here as needed
    
    public static By nature = By.xpath("//span[contains(@class,'ms-Dropdown-title')]");
    
    public static By naturevalue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public static By business = By.xpath("//span[contains(@class,'ms-Dropdown-title') and normalize-space()='Select Business Group']");
    
    public static By businessvalue = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public static By org = By.xpath("//span[contains(@class,'ms-Dropdown-title') and normalize-space()='Select Organization Unit']");
    
    public static By orgvalue= By.xpath("//div[@role='listbox']//button[@role='option']");
    
  //button[normalize-space()='Reply']
    
    public static By replyButton1= By.xpath("//button[normalize-space()='Reply']");

}

