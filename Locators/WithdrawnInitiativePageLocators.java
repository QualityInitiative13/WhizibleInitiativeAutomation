package Locators;

import org.openqa.selenium.By;

/**
 * Locators for Withdrawn Initiatives page.
 * 
 * Added for Withdrawn Initiatives feature.
 * XPaths will need to be updated based on actual UI.
 */
public class WithdrawnInitiativePageLocators {

    // Navigation: Initiative Management main node (image icon) - updated as per user XPath
    // @author Gayatri.k
    public static By initiativeManagementNode =
            By.xpath("/html/body/div/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[2]/div/span[1]/span/div");

    // Navigation: Withdrawn Initiatives page item in popup/menu (using relative/text-based XPath) // Gayatri.k
    public static By withdrawnInitiativesNode =
            By.xpath("//p[normalize-space()='Withdrawn Initiatives']");
    
    // Fallback locators for Withdrawn Initiatives menu item // Gayatri.k
    public static By[] withdrawnInitiativesNodeLocators = {
            By.xpath("//p[normalize-space()='Withdrawn Initiatives']"), // Primary: exact text match
            By.xpath("//p[contains(text(),'Withdrawn')]"), // Fallback 1: contains text
            By.xpath("//span[normalize-space()='Withdrawn Initiatives']"), // Fallback 2: span element
            By.xpath("//*[@role='button']//*[text()='Withdrawn Initiatives']"), // Fallback 3: role-based
            By.xpath("//div[@id='children-panel-container']//p[contains(text(),'Withdrawn')]"), // Fallback 4: container-based
            By.xpath("//*[contains(@class,'MuiListItem')]//p[contains(text(),'Withdrawn')]") // Fallback 5: Material-UI
    };

    // Page header (adjust text if actual label differs)
    public static By withdrawnHeader = By.xpath("//h6[contains(text(),'Withdrawn Initiatives') or contains(text(),'Withdrawn Initiative')]");

    // List view: table and rows (multiple fallback locators for compatibility)
    public static By withdrawnTable = By.xpath("//table | //div[contains(@class,'table-responsive')]//table | //div[@role='table']");
    
    // Multiple row locators (try in order)
    public static By[] withdrawnRowsLocators = {
            By.xpath("//table//tbody//tr"),
            By.xpath("//div[@class='table-responsive']//table/tbody/tr"),
            By.xpath("//div[@role='row' and not(contains(@class,'header'))]"),
            By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
            By.xpath("//table//tbody//tr[@role='row']"),
            By.xpath("//tr[contains(@class,'MuiTableRow-root')]")
    };
    
    // Primary row locator (for backward compatibility)
    public static By withdrawnRows = By.xpath("//table//tbody//tr | //div[@class='table-responsive']//table/tbody/tr");

    // Message when no records are present
    public static By noItemsMessage = By.xpath("//*[normalize-space(text())='There are no items to show in this view.']");

    // Column headers in list view
    public static By headerCells = By.xpath("//table//thead//th");

    // Search controls (specific XPaths for Withdrawn Initiatives page)
    public static By searchIcon = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img");
    
    // Search form fields
    public static By natureOfInitiativeDropdown = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[1]/div/div/span[1]");
    public static By businessGroupDropdown = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[2]/div/div/span[1]");
    public static By organizationUnitDropdown = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[3]/div/div/span[1]");
    public static By searchInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/div/input");
    public static By initiativeTitleInput = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[2]/div/div/div/input");
    public static By searchButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[3]/span/span/span");
    
    // Card View Toggle (multiple fallback locators)
    public static By[] switchToCardViewToggleLocators = {
            By.xpath("//button[@aria-label='Switch to Card view']"), // Primary: aria-label attribute
            By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeMedium.css-vubbuv > path"),
            By.xpath("//svg[contains(@class,'MuiSvgIcon-root') and contains(@class,'MuiSvgIcon-fontSizeMedium') and contains(@class,'css-vubbuv')]/path"),
            By.xpath("//div[@class='css-4io43t']"),
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button/svg/path"),
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button"),
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button/span")
    };
    
    // Primary locator (for backward compatibility)
    public static By switchToCardViewToggle = By.xpath("//button[@aria-label='Switch to Card view']");
    
    // Card View elements (multiple fallback locators)
    public static By[] cardViewLocators = {
            By.xpath("//div[contains(@class,'card')]"),
            By.xpath("//div[contains(@class,'MuiCard-root')]"),
            By.xpath("//div[contains(@class,'grid')]//div[contains(@class,'card')]"),
            By.xpath("//div[@role='grid']//div[contains(@class,'card')]"),
            By.xpath("//div[contains(@class,'MuiGrid-root')]//div[contains(@class,'MuiCard-root')]")
    };
    
    // Card content elements
    public static By cardContent = By.xpath("//div[contains(@class,'card')]//div[contains(@class,'content')] | //div[contains(@class,'MuiCardContent-root')]");
    
    // Edit View locators (multiple fallback locators for edit icon)
    // Note: The primary strategy uses CSS selector: svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path
    // This is tried first in the clickEditIcon() method
    public static By[] editIconLocators = {
            By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path"), // Primary: CSS selector for SVG path
            By.xpath("//button[@aria-label='Edit']"), // Fallback: button with aria-label='Edit'
            By.xpath("(//button[@aria-label='Edit'])[1]"), // Fallback: first button with aria-label='Edit'
            By.xpath("//div[@data-automation-key='action']//button[@aria-label='Edit']"), // Fallback: data-automation-key='action'
            By.xpath("//*[starts-with(@id,'row')]/div/div[6]/div/div/button[1]/svg/path"), // Dynamic pattern - works for any row ID (row148-0, row7-0, etc.)
            By.xpath("//*[@id=\"row148-0\"]/div/div[6]/div/div/button[1]/svg/path"), // Specific row ID row148-0
            By.xpath("//*[@id=\"row7-0\"]/div/div[6]/div/div/button[1]/svg/path"), // Specific row ID row7-0
            By.xpath("//*[starts-with(@id,'row')]/div/div[6]/div/div/button[1]/svg"), // SVG element with dynamic row
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[1]/div/div/div[2]/div/div/div/div/div/div[2]/div/div/div[6]/div/div/button[1]/svg"), // Absolute XPath with div[2]
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[1]/div/div/div[2]/div/div/div/div/div/div[5]/div/div/div[6]/div/div/button[1]/svg") // Alternative absolute fallback with div[5]
    };
    
    // Primary locator (for backward compatibility) - uses CSS selector for SVG path
    public static By editIcon = By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path");
    
    // Edit View tabs
    public static By workflowDetailsTab = By.xpath("//*[contains(text(),'Workflow Details') or contains(.,'Workflow Details')]");
    public static By workflowInformationTab = By.xpath("//*[contains(text(),'Workflow Information') or contains(.,'Workflow Information')]");
    
    // Go Back To List View button
    public static By goBackToListViewButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/button");
    
    // Resubmit functionality locators
    public static By resubmitIcon = By.xpath("//button[@aria-label='Resubmit']");
    // Fallback locators for resubmit icon
    public static By[] resubmitIconLocators = {
            By.xpath("//button[@aria-label='Resubmit']"), // Primary: aria-label attribute
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[1]/div/div/div[2]/div/div/div/div/div/div[4]/div/div/div[6]/div/div/button[4]/svg"), // Fallback: absolute XPath
            By.xpath("//button[contains(@aria-label,'Resubmit')]"), // Fallback: contains aria-label
            By.xpath("//*[starts-with(@id,'row')]/div/div[6]/div/div/button[4]") // Fallback: dynamic row pattern
    };
    public static By resubmitCommentBox = By.xpath("/html/body/div[2]/div[3]/div/div[4]/div/div/textarea[1]");
    public static By resubmitSaveButton = By.xpath("/html/body/div[2]/div[3]/div/div[2]/button/span/span/span");
    public static By resubmitOkButton = By.xpath("/html/body/div[3]/div[3]/div/div[2]/button[1]/span/span/span");
    
    // Pagination locators // Gayatri.k
    public static By paginationNextButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[2]/button[2]");
    public static By paginationPreviousButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[2]/button[1]");
    public static By paginationInfo = By.xpath("//div[contains(@class,'pagination') or contains(@class,'MuiTablePagination')]");
    
    
    
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    
    
    
}
