package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Initiative Status Management Module
 * 
 * This class contains all locators used in Initiative Status Management page automation.
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
public class InitiativeStatusManagementPageLocators {

    // ==================== NAVIGATION ====================
    
    /** Initiative Management navigation element */
    public static By initiativeManagementNav = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']");
    
    /** Initiative Status Management page link */
    public static By initiativeStatusManagementPage = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[11]/p");
    
    // ==================== PAGE ELEMENTS ====================
    
    /** Action dropdown field */
    public static By actionDropdown = By.xpath("//*[@id=\"Dropdown0-option\"] | //*[@id=\"Dropdown0\"]/span[2]");
    
    /** Mark Initiatives as Complete option in dropdown */
    public static By markInitiativesAsCompleteOption = By.xpath("//*[@id=\"Dropdown0-list2\"]/span/span");
    
    /** Show button */
    public static By showButton = By.xpath("//*[@id=\"id__1\"] | //*[@id=\"filtershowbtn\"] | //button[@id='filtershowbtn'] | //button[contains(text(),'Show')]");
    
    /** Action link of initiative (generic - will be made dynamic) */
    public static By actionLink = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[5]/td[6]/button/span/svg");
    
    /** Comment box for marking initiative as complete */
    public static By commentBoxForMarkComplete = By.xpath("//*[@id=\"TextField99\"] | //textarea[contains(@id,'TextField') and contains(@placeholder,'Comment')] | //div[contains(@role,'dialog')]//textarea[contains(@placeholder,'Comment')] | //textarea[contains(@id,'TextField')] | //*[@id='TextField99'] | //textarea[@id='TextField99'] | //div[contains(@role,'dialog')]//textarea | //textarea[contains(@placeholder,'comment') or contains(@placeholder,'Comment')] | //*[contains(@id,'TextField') and contains(@type,'text')] | //input[contains(@id,'TextField')]");
    
    /** Save button for marking initiative as complete */
    public static By saveButtonForMarkComplete = By.xpath("//*[@id=\"id__96\"] | //button[contains(@id,'id__') and contains(text(),'Save')] | //div[contains(@role,'dialog')]//button[contains(text(),'Save')] | //button[@id='id__96'] | //*[@id='id__96'] | //button[contains(text(),'Save')] | //button[contains(text(),'SAVE')] | //div[contains(@class,'MuiDialog')]//button[contains(text(),'Save')] | //*[contains(@id,'id__') and contains(@type,'button')] | //button[contains(@aria-label,'Save')]");
    
    /** Save button on confirmation popup */
    public static By saveButtonOnConfirmationPopup = By.xpath("//*[@id=\"id__56\"] | //button[@id='id__56'] | //*[@id='id__56'] | //div[contains(@role,'dialog')]//button[@id='id__56'] | //div[contains(@class,'MuiDialog')]//button[@id='id__56'] | //button[contains(@id,'id__56')]");
    
    /** Workflow history link */
    public static By workflowHistoryLink = By.xpath("//*[@id=\"row640-0\"]/div/div[7]/div/div/button[3]/svg/path | //*[@id='row640-0']/div/div[7]/div/div/button[3]/svg | //*[@id='row640-0']//button[3]//svg | //button[contains(@aria-label,'Workflow') or contains(@aria-label,'workflow')]//svg | //div[@id='row640-0']//button[contains(@class,'workflow') or contains(@aria-label,'history')]");
    
    /** Action Taken dropdown field */
    public static By actionTakenDropdown = By.xpath("//*[@id=\"Dropdown677\"]/span[2] | //*[@id='Dropdown677']/span[2] | //*[@id='Dropdown677'] | //div[@id='Dropdown677']//span[2] | //*[@id='Dropdown333-option'] | //*[@id='Dropdown333'] | //*[contains(@id,'Dropdown677')] | //*[@role='combobox' and contains(@id,'Dropdown')]");
    
    /** Approved option in Action Taken dropdown */
    public static By approvedOption = By.xpath("//*[@id=\"Dropdown333-list1\"]/span/span | //*[@id='Dropdown333-list1']/span/span | //*[@id='Dropdown333-list1']//span[contains(text(),'Approved')] | //*[contains(@id,'Dropdown333-list')]//span[contains(text(),'Approved')] | //*[@role='option' and contains(text(),'Approved')] | //li[contains(text(),'Approved')] | //*[contains(text(),'Approved')]");
    
    /** Edit link for Completed Initiative */
    public static By editLink = By.xpath("//*[@id=\"row1336-0\"]/div/div[7]/div/div/button[1]/svg/path | //*[@id='row1336-0']/div/div[7]/div/div/button[1]/svg | //*[@id='row1336-0']//button[1]//svg | //button[contains(@aria-label,'Edit') or contains(@aria-label,'edit')]//svg");
    
    /** Workflow Details button */
    public static By workflowDetailsButton = By.xpath("//button[contains(text(),'Workflow Details')] | //button[contains(text(),'Workflow details')] | //*[contains(text(),'Workflow Details')]");
    
    /** Workflow Information button */
    public static By workflowInformationButton = By.xpath("//button[contains(text(),'Workflow Information')] | //button[contains(text(),'Workflow information')] | //*[contains(text(),'Workflow Information')]");
    
    /** Save button (should NOT be displayed) */
    public static By saveButton = By.xpath("//button[contains(text(),'Save')] | //button[contains(text(),'SAVE')] | //button[@id='save'] | //*[@id='save']");
    
    /** Go Back To List View button */
    public static By goBackToListViewButton = By.xpath("//*[@id=\"IMInfopgtabs\"]/button | //button[@id='IMInfopgtabs'] | //*[@id='IMInfopgtabs']//button | //button[contains(text(),'Go Back') or contains(text(),'go back')] | //button[contains(text(),'List View') or contains(text(),'list view')]");
    
    /** Switch to Card View icon/button */
    public static By switchToCardViewButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/button | //*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/button/svg | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button//svg | //button[contains(@aria-label,'Card') or contains(@aria-label,'card')] | //button[contains(@aria-label,'Card') or contains(@aria-label,'card')]//svg | //button[.//svg and contains(@aria-label,'View')] | //button[.//svg and (contains(@aria-label,'Card') or contains(@aria-label,'card'))] | //div[contains(@class,'toolbar') or contains(@class,'header')]//button[.//svg] | //div[contains(@class,'MuiToolbar')]//button[.//svg] | //button[.//svg and contains(@class,'MuiIconButton')]");
    
    /** Card container */
    public static By cardContainer = By.xpath("//div[contains(@class,'card')] | //div[contains(@class,'Card')] | //*[contains(@class,'card-container')] | //div[contains(@id,'card')] | //*[contains(@class,'grid') and contains(@class,'card')]");
    
    /** List container/table */
    public static By listContainer = By.xpath("//table | //div[contains(@class,'table')] | //div[contains(@class,'ag-root')] | //*[contains(@id,'row')] | //div[contains(@class,'list')]");
    
    /** View Chart button */
    public static By viewChartButton = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/h2/button | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div//h2//button | //h2//button[contains(@aria-label,'Chart') or contains(@aria-label,'chart')] | //button[contains(text(),'Chart') or contains(text(),'chart')]");
    
    /** Top 5 Nature of Initiatives chart container */
    public static By top5NatureChartContainer = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/div[2]/div/div[2] | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div//div//div//div//div[2]//div//div[2] | //div[contains(@class,'chart')] | //div[contains(text(),'Top 5')]");
    
    /** Nature of Initiative column header */
    public static By natureOfInitiativeColumnHeader = By.xpath("//*[@id=\"header1486-nature-name\"] | //*[@id='header1486-nature-name'] | //div[contains(@id,'header') and contains(@id,'nature')] | //th[contains(text(),'Nature')] | //div[contains(@class,'ag-header-cell') and contains(text(),'Nature')]");
    
    /** Nature of Initiative column values in table */
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
    
    /**
     * Get dynamic locator for workflow history link by Initiative Code
     * @param initiativeCode The initiative code to search for
     * @return By locator for the workflow history link in that row
     */
    public static By getWorkflowHistoryLinkByCode(String initiativeCode) {
        return By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]]//button[contains(@aria-label,'Workflow') or contains(@aria-label,'workflow')] | " +
                       "//tr[td[normalize-space(text())='" + initiativeCode + "']]//button[3]");
    }
    
    // ==================== ADDITIONAL LOCATORS ====================
    // Add any other Initiative Status Management specific locators here as needed
    
    public static By snoozecomment = By.xpath("//textarea[contains(@class,'ms-TextField-field') and @aria-invalid='false']");
    
    public static By snoozecomment1 = By.xpath("//textarea[contains(@class,'ms-TextField-field')]");
    
    public static By savebutton = By.xpath("//button[.//span[normalize-space()='Save']]");
    
    public static By savebuttonok = By.xpath("//button[.//span[normalize-space()='Ok']]");
    
    
    
    
    public static By closestatus = By.xpath("//button[.//span[normalize-space()='Close']]");
}

