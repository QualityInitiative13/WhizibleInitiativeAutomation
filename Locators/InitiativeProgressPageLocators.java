package Locators;

import org.openqa.selenium.By;

/**
 * Locators for Initiative Progress page.
 * 
 * Added for Initiative Progress feature.
 * XPaths will need to be updated based on actual UI.
 * @author Gayatri.k
 */
public class InitiativeProgressPageLocators {

    // Navigation: Dashboard node (image icon) // Gayatri.k
    public static By dashboardNode =
            By.xpath("/html/body/div[1]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[1]/div/span[1]/span/div/img");

    // Navigation: Initiative Progress page item in popup/menu // Gayatri.k
    public static By initiativeProgressNode =
            By.xpath("/html/body/div[3]/div[3]/div[2]");
    
    // Fallback locators for Initiative Progress menu item // Gayatri.k
    public static By[] initiativeProgressNodeLocators = {
            By.xpath("/html/body/div[3]/div[3]/div[2]"), // Primary: absolute XPath
            By.xpath("//p[normalize-space()='Initiative Progress']"), // Fallback 1: text-based
            By.xpath("//p[contains(text(),'Initiative Progress')]"), // Fallback 2: contains text
            By.xpath("//p[contains(text(),'Progress')]"), // Fallback 3: contains Progress
            By.xpath("//span[normalize-space()='Initiative Progress']"), // Fallback 4: span element
            By.xpath("//*[@role='button']//*[text()='Initiative Progress']"), // Fallback 5: role-based
            By.xpath("//div[@id='children-panel-container']//p[contains(text(),'Progress')]"), // Fallback 6: container-based
            By.xpath("//*[contains(@class,'MuiListItem')]//p[contains(text(),'Progress')]") // Fallback 7: Material-UI
    };

    // Page header (adjust text if actual label differs) // Gayatri.k
    public static By initiativeProgressHeader = By.xpath("//h6[contains(text(),'Initiative Progress') or contains(text(),'Progress')]");

    // List view: table and rows (multiple fallback locators for compatibility) // Gayatri.k
    public static By progressTable = By.xpath("//table | //div[contains(@class,'table-responsive')]//table | //div[@role='table']");
    
    // Multiple row locators (try in order) // Gayatri.k
    public static By[] progressRowsLocators = {
            By.xpath("//table//tbody//tr"),
            By.xpath("//div[@class='table-responsive']//table/tbody/tr"),
            By.xpath("//div[@role='row' and not(contains(@class,'header'))]"),
            By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
            By.xpath("//table//tbody//tr[@role='row']"),
            By.xpath("//tr[contains(@class,'MuiTableRow-root')]")
    };
    
    // Primary row locator (for backward compatibility) // Gayatri.k
    public static By progressRows = By.xpath("//table//tbody//tr");

    // Search functionality // Gayatri.k
    public static By searchIcon = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[1]/img");
    public static By searchInput = By.xpath("//input[@placeholder='Search' or contains(@placeholder,'search')] | //input[@type='search']");
    
    // Project dropdown // Gayatri.k
    public static By projectDropdown = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/div/span[1]");
    
    // Search button // Gayatri.k
    public static By searchButton = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/button[3]");
    
    // Clear search button // Gayatri.k
    public static By clearSearchButton = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/button[2]");
    
    // Eye icon (View/Edit icon) // Gayatri.k
    public static By eyeIcon(int rowIndex) {
        // Find SVG with aria-label='View' in the specific row
        return By.xpath("//table/tbody/tr[" + rowIndex + "]//svg[@aria-label='View']");
    }
    
    // Edit button Action column (first row) // Gayatri.k
    public static By editButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/table/tbody/tr[1]/td[10]");
    
    // Edit button Action column by row index (dynamic) // Gayatri.k
    public static By editButtonByRow(int rowIndex) {
        return By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[2]/table/tbody/tr[" + rowIndex + "]/td[10]");
    }
    
    // Edit button Action column by project name (dynamic) - find row by project, then Action column // Gayatri.k
    public static By editButtonByProject(String project) {
        // Find row by project name, then Action column (td[10])
        return By.xpath("//tr[td[normalize-space()='" + project + "']]/td[10]");
    }
    
    // Close icon (Cross icon in dialog) // Gayatri.k
    public static By closeIcon = By.xpath("/html/body/div[3]/div[3]/div/div[1]/button/svg");

    // Pagination controls // Gayatri.k
    public static By paginationNextButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[2]");
    public static By paginationPreviousButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[1]");
    public static By paginationInfo = By.xpath("//div[contains(@class,'pagination')] | //span[contains(text(),'Page')] | //div[contains(text(),'Page')]");
}

