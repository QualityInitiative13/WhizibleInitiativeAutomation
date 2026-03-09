package Locators;

import org.openqa.selenium.By;

/**
 * Locators for Man-Com Prioritization page.
 * 
 * Added for Man-Com Prioritization feature.
 * XPaths will need to be updated based on actual UI.
 * @author Gayatri.k
 */
public class ManComPrioritizationPageLocators {

    // Navigation: Initiative Tracking node (image icon) // Gayatri.k
    public static By initiativeTrackingNode =
            By.xpath("/html/body/div[1]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[3]/div/span[1]/span/div/img");

    // Navigation: Man-Com Prioritization page item in popup/menu // Gayatri.k
    public static By manComPrioritizationNode =
            By.xpath("/html/body/div[2]/div[3]/div[3]");
    
    // Fallback locators for Man-Com Prioritization menu item // Gayatri.k
    public static By[] manComPrioritizationNodeLocators = {
            By.xpath("/html/body/div[2]/div[3]/div[3]"), // Primary: absolute XPath
            By.xpath("//p[normalize-space()='Man-Com Prioritization']"), // Fallback 1: text-based
            By.xpath("//p[contains(text(),'Man-Com Prioritization')]"), // Fallback 2: contains text
            By.xpath("//p[contains(text(),'Prioritization')]"), // Fallback 3: contains Prioritization
            By.xpath("//span[normalize-space()='Man-Com Prioritization']"), // Fallback 4: span element
            By.xpath("//*[@role='button']//*[text()='Man-Com Prioritization']"), // Fallback 5: role-based
            By.xpath("//div[@id='children-panel-container']//p[contains(text(),'Prioritization')]"), // Fallback 6: container-based
            By.xpath("//*[contains(@class,'MuiListItem')]//p[contains(text(),'Prioritization')]") // Fallback 7: Material-UI
    };

    // Page header (adjust text if actual label differs) // Gayatri.k
    public static By manComPrioritizationHeader = By.xpath("//h6[contains(text(),'Man-Com Prioritization') or contains(text(),'Prioritization')]");

    // List view: table and rows (multiple fallback locators for compatibility) // Gayatri.k
    public static By prioritizationTable = By.xpath("//table | //div[contains(@class,'table-responsive')]//table | //div[@role='table']");
    
    // Multiple row locators (try in order) // Gayatri.k
    public static By[] prioritizationRowsLocators = {
            By.xpath("//table//tbody//tr"),
            By.xpath("//div[@class='table-responsive']//table/tbody/tr"),
            By.xpath("//div[@role='row' and not(contains(@class,'header'))]"),
            By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
            By.xpath("//table//tbody//tr[@role='row']"),
            By.xpath("//tr[contains(@class,'MuiTableRow-root')]")
    };
    
    // Primary row locator (for backward compatibility) // Gayatri.k
    public static By prioritizationRows = By.xpath("//table//tbody//tr | //div[@class='table-responsive']//table/tbody/tr");

    // Message when no records are present // Gayatri.k
    public static By noItemsMessage = By.xpath("//*[normalize-space(text())='There are no items to show in this view.']");

    // Column headers in list view // Gayatri.k
    public static By headerCells = By.xpath("//table//thead//th");

    // Search controls (specific XPaths for Man-Com Prioritization page) // Gayatri.k
    public static By searchIcon = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img");
    
    // Search form fields // Gayatri.k
    public static By searchInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/div/input");
    public static By businessBenefitsInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[1]/div/div/div/input");
    public static By departmentRatingInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[2]/div/div/div/input");
    public static By initiativeCodeInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[3]/div/div/div/input");
    public static By initiativeTitleInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/div/input");
    public static By manComPriorityInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[2]/div/div/div/input");
    public static By organizationUnitDropdown = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[3]/div/div/span[1]");
    public static By sizeDropdown = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/div/div/div/div/span[1]");
    public static By searchButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[4]/button[3]/span");
    public static By clearSearchButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[4]/button[2]/span/span/span");
    
    // Card View Toggle (multiple fallback locators) // Gayatri.k
    public static By[] switchToCardViewToggleLocators = {
            By.xpath("//button[@aria-label='Switch to Card view']"), // Primary: aria-label attribute
            By.xpath("//button[contains(@aria-label,'Card view')]"), // Fallback 1
            By.xpath("//div[@class='css-4io43t']"), // Fallback 2
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button") // Fallback 3
    };
    
    // Primary locator (for backward compatibility) // Gayatri.k
    public static By switchToCardViewToggle = By.xpath("//button[@aria-label='Switch to Card view']");
    
    // Card View elements (multiple fallback locators) // Gayatri.k
    public static By[] cardViewLocators = {
            By.xpath("//div[contains(@class,'card')]"),
            By.xpath("//div[contains(@class,'MuiCard-root')]"),
            By.xpath("//div[contains(@class,'grid')]//div[contains(@class,'card')]"),
            By.xpath("//div[@role='grid']//div[contains(@class,'card')]"),
            By.xpath("//div[contains(@class,'MuiGrid-root')]//div[contains(@class,'MuiCard-root')]")
    };
    
    // Card content elements // Gayatri.k
    public static By cardContent = By.xpath("//div[contains(@class,'card')]//div[contains(@class,'content')] | //div[contains(@class,'MuiCardContent-root')]");
    
    // Table row edit fields // Gayatri.k
    // Note: These are row-specific, use dynamic XPath based on row number
    // Department Rating: tr[X]/td[6]/div/input, Man-Com Priority: tr[X]/td[7]/div/input
    public static By departmentRatingTableInput(int rowIndex) {
        return By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowIndex + "]/td[6]/div/input");
    }
    public static By manComPriorityTableInput(int rowIndex) {
        return By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowIndex + "]/td[7]/div/input");
    }
    
    // Save button // Gayatri.k
    public static By saveButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[1]/div/button/span/span/span");
    
    // Pagination locators // Gayatri.k
    public static By paginationNextButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[2]");
    public static By paginationPreviousButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[1]");
    public static By paginationInfo = By.xpath("//div[contains(@class,'pagination') or contains(@class,'MuiTablePagination')]");
    
    // History icon locator (dynamic - row-specific) // Gayatri.k
    public static By historyIcon(int rowIndex) {
        return By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowIndex + "]/td[8]");
    }
    
    // History icon button locator (alternative - for clicking the button instead of SVG) // Gayatri.k
    public static By historyIconButton(int rowIndex) {
        return By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowIndex + "]/td[8]");
    }
}

