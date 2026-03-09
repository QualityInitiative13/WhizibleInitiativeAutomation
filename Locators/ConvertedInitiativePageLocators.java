package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Converted Initiative Management Module
 * 
 * Added by Gayatri.Kasav - All locators specific to Converted Initiatives page
 * 
 * @author Gayatri.Kasav
 * @version 1.0
 */
public class ConvertedInitiativePageLocators {

    // ==================== NAVIGATION ====================
    
    /** Added by Gayatri.Kasav - Initiative management node for converted navigation */
    public static By initiativeManagementNode = By.xpath("//img[@alt='Initiative Management']");
    
    /** Added by Gayatri.Kasav - Converted initiative navigation item */
    public static By convertedInitiativeNav = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[2]/p");
    
    /** Added by Gayatri.Kasav - Initiative Tracking page navigation - Updated XPath provided by user */
    public static By initiativeTrackingPage = By.xpath("/html/body/div/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[3]/div/span[1]/span/div/img");
    
    /** Added by Gayatri.Kasav - Initiative Conversion page navigation - Updated XPath provided by user */
    public static By initiativeConversionPage = By.xpath("/html/body/div[2]/div[3]/div[1]/p");
    
    /** Added by Gayatri.Kasav - Convert To Project button (for matching initiative code row) */
    public static By convertToProjectButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button/svg/path");
    
    /** Added by Gayatri.Kasav - Convert To Milestone button (for matching initiative code row) */
    public static By convertToMilestoneButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[7]/div/button/svg");
    
    // ==================== PAGE HEADERS ====================
    
    /** Added by Gayatri.Kasav - Converted initiative page header */
    public static By convertedInitiativeHeader = By.xpath("//p[contains(normalize-space(),'Converted Initiative')]");
    
    // ==================== QUICK NOTIFICATION ====================
    
    /** Added by Gayatri.Kasav - Quick notification popup close button */
    public static By quickNotificationCloseButton = By.xpath("//div[contains(@class,'Notifications') or contains(@class,'modal')]//button[.='×' or .//span='×']");
    
    // ==================== CHARTS ====================
    
    /** Added by Gayatri.Kasav - Charts toggle button (View Charts) with fallbacks */
    public static By chartsToggle = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/h2/button"); // primary absolute
    public static By chartsToggleAlt1 = By.xpath("//button[normalize-space()='View Charts']");
    public static By chartsToggleAlt2 = By.xpath("//h2[contains(normalize-space(),'View Charts')]/following::button[1]");
    public static By chartsToggleAlt3 = By.xpath("//h2[contains(normalize-space(),'View Charts')]/ancestor::div[contains(@class,'MuiAccordion') or contains(@class,'accordion') or contains(@class,'collapse')]/descendant::button[1]");
    public static By chartsToggleAlt4 = By.xpath("//h2[contains(normalize-space(),'View Charts')]/parent::div//button");
    
    /** Added by Gayatri.Kasav - Chart headers/sections */
    public static By chartByOUHeader = By.xpath("//h2[contains(.,'By Organization Unit')]");
    public static By chartTop5NOIHeader = By.xpath("//h2[contains(.,'Top 5 Nature of Initiative')]");
    public static By chartConvertedHeader = By.xpath("//h2[contains(.,'Converted to Project') or contains(.,'Converted to Project / Milestone / Not Converted')]");
    
    /** Added by Gayatri.Kasav - Generic chart container (fallback) */
    public static By chartContainers = By.xpath("//div[contains(@class,'chart') or contains(@class,'Chart') or contains(@class,'graph')]");
    
    // ==================== SEARCH ====================
    
    /** Added by Gayatri.Kasav - Search trigger and filters */
    public static By searchTrigger = By.xpath("(//span[@class='ms-Button-flexContainer flexContainer-201'])[3]");
    public static By searchIcon = By.xpath("(//img[@aria-label='Search'])[1]");
    public static By searchInput = By.xpath("//input[@placeholder='Search']");
    
    /** Added by Gayatri.Kasav - Search button to execute search after selecting filter */
    public static By searchButton = By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/div/div/div[2]/div/div[3]/button[3]/span/span/span");
    
    /** Added by Gayatri.Kasav - Clear search button */
    public static By clearSearchButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[2]/span/span/span");
    
    /** Added by Gayatri.Kasav - Close search window button */
    public static By closeSearchButton = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[1]/span/span/span");
    
    // ==================== SEARCH FILTERS ====================
    
    /** Added by Gayatri.Kasav - Nature of Initiative dropdown click trigger */
    public static By natureOfInitiativeDropdown = By.xpath("(//span[@id='natureOfInitiativeId-option'])[1]");
    
    /** Added by Gayatri.Kasav - Nature of Initiative dropdown value options (for selection) */
    public static By natureOfInitiativeOptions = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    /** Added by Gayatri.Kasav - Nature of Initiative cells (for validation) and dropdown context */
    public static By natureOfInitiativeCells = By.xpath("//*[@id='natureOfInitiativeId-list3']/span");
    
    /** Added by Gayatri.Kasav - Business Group dropdown click trigger */
    public static By businessGroupDropdown = By.xpath("(//span[@id='businessGroupId-option'])[1]");
    
    /** Added by Gayatri.Kasav - Business Group dropdown value options (for selection) */
    public static By businessGroupOptions = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    /** Added by Gayatri.Kasav - Organization Unit dropdown click trigger */
    public static By organizationUnitDropdown = By.xpath("(//span[@id='organizationUnitId-option'])[1]");
    
    /** Added by Gayatri.Kasav - Organization Unit dropdown value options (for selection) */
    public static By organizationUnitOptions = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    /** Added by Gayatri.Kasav - Initiative Code input field */
    public static By initiativeCodeInput = By.xpath("//input[@id='initiativeCode']");
    
    /** Added by Gayatri.Kasav - Initiative Title input field (placeholder - will be updated when XPath provided) */
    public static By initiativeTitleInput = By.xpath("//input[@placeholder='Initiative Title' or @name='initiativeTitle' or @id='initiativeTitle']");
    
    /** Added by Gayatri.Kasav - Converted To dropdown click trigger */
    public static By convertedToDropdown = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div[3]/div/div/span[1]");
    
    /** Added by Gayatri.Kasav - Converted To dropdown value options (for selection) */
    public static By convertedToOptions = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public static By dropdownBody = By.xpath("(//body)[1]");
    
    // ==================== GRID ====================
    
    /** Added by Gayatri.Kasav - Grid rows locator (primary) */
    public static By gridRows = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row'] | //table//tbody//tr[@role='row']");
    
    /** Grid data rows (excluding headers) - reused from InitiativePageLocators */
    public static By gridDataRows = By.xpath("//div[@role='gridcell' and @col-id] | //tbody//tr[@role='row' and not(contains(@class,'header'))]");
    
    // ==================== EMPTY STATE MESSAGE ====================
    
    /** Added by Gayatri.Kasav - Message displayed when there are no items to show */
    public static By noItemsMessage = By.xpath("//*[contains(text(),'There are no items to show in this view.') or contains(text(),'no items to show') or contains(text(),'No items') or contains(text(),'no data') or contains(text(),'No data') or contains(text(),'No records') or contains(text(),'no records')]");
    
    // ==================== EDIT VIEW ====================
    
    /** Added by Gayatri.Kasav - Edit icon SVG path (CSS selector from user) */
    public static By editIconPath = By.cssSelector("#row555-0 > div > div:nth-child(7) > div > div > button:nth-child(1) > svg > path");
    
    /** Added by Gayatri.Kasav - Edit button (parent of SVG) - CSS selector */
    public static By editButton = By.cssSelector("#row555-0 > div > div:nth-child(7) > div > div > button:nth-child(1)");
    
    /** Added by Gayatri.Kasav - Edit icon SVG (fallback) */
    public static By editIcon = By.cssSelector("#row555-0 > div > div:nth-child(7) > div > div > button:nth-child(1) > svg");
    
    /** Added by Gayatri.Kasav - Edit button flexible xpath (doesn't rely on specific row ID) */
    public static By editButtonFlexible = By.xpath("//div[@role='row']//div[7]//button[1]");
    
    // ==================== EDIT VIEW ACTIONS ====================
    
    /** Added by Gayatri.Kasav - Workflow Details tab/button in Edit View */
    public static By workflowDetailsButton = By.xpath("//*[contains(text(),'Workflow Details') or contains(.,'Workflow Details')]");
    
    /** Added by Gayatri.Kasav - Workflow Information tab/button in Edit View */
    public static By workflowInformationButton = By.xpath("//*[contains(text(),'Workflow Information') or contains(.,'Workflow Information')]");
    
    /** Added by Gayatri.Kasav - Manage Workflow tab/button in Edit View */
    public static By manageWorkflowButton = By.xpath("//*[contains(text(),'Manage Workflow') or contains(.,'Manage Workflow')]");
    
    /** Added by Gayatri.Kasav - CheckList tab/button in Edit View */
    public static By checklistButton = By.xpath("//*[contains(text(),'CheckList') or contains(text(),'Checklist') or contains(.,'CheckList') or contains(.,'Checklist')]");
    
    /** Added by Gayatri.Kasav - Go Back to List View button/action in Edit View */
    public static By goBackToListViewButton = By.xpath("//button[contains(text(),'Go Back to List View') or contains(text(),'Go Back') or contains(.,'Go Back to List View') or contains(.,'Back to List')]");
    
    /** Added by Gayatri.Kasav - Actions button/menu in Edit View */
    public static By actionsButton = By.xpath("//button[contains(text(),'Actions') or contains(@aria-label,'Actions') or contains(.,'Actions')]");
    
    /** Added by Gayatri.Kasav - Save button (should NOT exist in Edit View) */
    public static By saveButton = By.xpath("//button[contains(text(),'Save') or contains(@aria-label,'Save') or contains(.,'Save')]");
    
    /** Added by Gayatri.Kasav - Save button in Resources edit modal - Exact XPath provided by user */
    public static By resourcesSaveButton = By.xpath("/html/body/div[2]/div[3]/div/div/div[2]/div[2]/div[1]/div/button/span/span/span");
    
    // ==================== INBOX ====================
    
    /** Added by Gayatri.Kasav - Inbox Initiative Node */
    public static By inboxInitiativeNode = By.xpath("/html/body/div[2]/div[3]/div[1]/p");
    
    /** Added by Gayatri.Kasav - Inbox Search Icon */
    public static By inboxSearchIcon = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img");
    
    /** Added by Gayatri.Kasav - Inbox Initiative Code Input Field */
    public static By inboxInitiativeCodeInput = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div/div/div/div/div[1]/div/div[1]/div/div/div/input");
    
    /** Added by Gayatri.Kasav - Inbox Search Button */
    public static By inboxSearchButton = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div/div/div/div/div[5]/button[3]/span");
    
    // ==================== EDIT VIEW ADDITIONAL TABS (When in Inbox) ====================
    
    /** Added by Gayatri.Kasav - Approve tab/button in Edit View (when initiative is in inbox) */
    public static By approveButton = By.xpath("//*[contains(text(),'Approve') or contains(.,'Approve')]");
    
    /** Added by Gayatri.Kasav - Push Back tab/button in Edit View (when initiative is in inbox) */
    public static By pushBackButton = By.xpath("//*[contains(text(),'Push Back') or contains(.,'Push Back')]");
    
    /** Added by Gayatri.Kasav - Withdraw Initiative tab/button in Edit View (when initiative is in inbox) */
    public static By withdrawInitiativeButton = By.xpath("//*[contains(text(),'Withdraw Initiative') or contains(.,'Withdraw Initiative') or contains(text(),'Withdraw')]");
    
    // ==================== RESOURCES TAB ====================
    
    /** Added by Gayatri.Kasav - Resources tab */
    public static By resourcesTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[3]/a");
    
    /** Added by Gayatri.Kasav - Add button in Resources tab (should NOT be displayed for converted initiatives without authority) */
    public static By addButton = By.xpath("//button[contains(text(),'Add') or contains(.,'Add') or contains(@aria-label,'Add')]");
    
    // ==================== SUBTABS ====================
    
    /** Added by Gayatri.Kasav - Cost tab */
    public static By costTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[4]/a");
    
    /** Added by Gayatri.Kasav - Funding tab */
    public static By fundingTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[5]/a");
    
    /** Added by Gayatri.Kasav - ROI tab */
    public static By roiTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[6]/a");
    
    /** Added by Gayatri.Kasav - Risk/Action Items tab */
    public static By riskActionItemsTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[8]/a");
    
    /** Added by Gayatri.Kasav - Risk sub-tab (inside Risk/Action Items) */
    public static By riskSubTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[1]/div/div/div[1]/button[1]/span/span/span");
    
    /** Added by Gayatri.Kasav - Action Items sub-tab (inside Risk/Action Items) */
    public static By actionItemsSubTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[1]/div/div/div[1]/button[2]/span/span/span");
    
    /** Added by Gayatri.Kasav - Document Upload tab */
    public static By documentUploadTab = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[9]/a");
    
    /** Added by Gayatri.Kasav - Upload Document subtab (inside Document Upload tab) */
    public static By uploadDocumentSubtab = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div[2]/button[1]");
    
    /** Added by Gayatri.Kasav - Attach URL subtab (inside Document Upload tab) - Exact XPath provided by user */
    public static By attachUrlSubtab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div[2]/button[2]");
    
    /** Added by Gayatri.Kasav - Upload button (inside Upload Document subtab drawer) */
    public static By uploadButton = By.xpath("//button[contains(text(),'Upload') or contains(.,'Upload')]");
    
    /** Added by Gayatri.Kasav - Attach URL button (inside Attach URL subtab drawer) - Exact XPath provided by user */
    public static By attachUrlButton = By.xpath("/html/body/div[2]/div[3]/div/div[2]/button");
    
    /** Added by Gayatri.Kasav - Close button for Upload Document subtab drawer - Using more robust selector */
    public static By uploadDocumentCloseButton = By.cssSelector("body > div.MuiDrawer-root.MuiDrawer-modal.MuiModal-root > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-elevation16.MuiDrawer-paper.MuiDrawer-paperAnchorRight > div > div.MuiBox-root > button > svg > path");
    
    /** Added by Gayatri.Kasav - Close button for Attach URL subtab drawer - Exact XPath provided by user */
    public static By attachUrlCloseButton = By.xpath("/html/body/div[2]/div[3]/div/div[1]/button/svg/path");
    
    /** Added by Gayatri.Kasav - Fallback close button (parent button) for drawer */
    public static By drawerCloseButton = By.cssSelector("body > div.MuiDrawer-root.MuiDrawer-modal.MuiModal-root > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-elevation16.MuiDrawer-paper.MuiDrawer-paperAnchorRight > div > div.MuiBox-root > button");
    
    /** Added by Gayatri.Kasav - Timeline tab */
    public static By timelineTab = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[1]/div/div[10]/a");
    
    // ==================== SUBTAB EDIT ICONS ====================
    
    /** Added by Gayatri.Kasav - Resources tab edit icon (first record) - Updated XPath provided by user */
    public static By resourcesEditIcon = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/table/tbody/tr[1]/td[6]/button[1]/svg");
    
    /** Added by Gayatri.Kasav - Cost tab edit icon (first record) */
    public static By costEditIcon = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/table/tbody/tr/td[5]/button[1]/svg");
    
    /** Added by Gayatri.Kasav - Funding tab edit icon (first record) */
    public static By fundingEditIcon = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[2]/table/tbody/tr/td[4]/button[1]/svg/path");
    
    /** Added by Gayatri.Kasav - ROI tab edit icon (first record) */
    public static By roiEditIcon = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[2]/table/tbody/tr/td[4]/button[1]/svg/path");
    
    /** Added by Gayatri.Kasav - Risk tab edit icon (first record) */
    public static By riskEditIcon = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[2]/div/div[3]/table/tbody/tr/td[8]/div/button[1]/svg");
    
    /** Added by Gayatri.Kasav - Action Items tab edit icon (first record) */
    public static By actionItemsEditIcon = By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div/div[2]/div/div[2]/table/tbody/tr/td[6]/div/button[1]/svg");
    
    /** Added by Gayatri.Kasav - Timeline tab edit icon (first record) - Generic pattern */
    public static By timelineEditIcon = By.xpath("//table//tbody//tr[1]//button[1]//svg | //div[@role='row'][1]//button[1]//svg");
    
    // ==================== MONTHLY DISTRIBUTION SUBTAB ====================
    
    /** Added by Gayatri.Kasav - Monthly Distribution subtab (in Cost/ROI edit view) */
    public static By monthlyDistributionTab = By.xpath("//*[contains(text(),'Monthly Distribution') or contains(.,'Monthly Distribution')]");
    
    /** Added by Gayatri.Kasav - Save button in Monthly Distribution (Cost/ROI edit view) */
    public static By monthlyDistributionSaveButton = By.xpath("/html/body/div[3]/div[3]/div/div[2]/div/div/div/button[2]");
    
    
    public static By chartview = By.xpath("//button[normalize-space()='View Charts']");
    
    public static By closechart = By.xpath("//button[normalize-space()='View Charts']");
    
    
    public static By search= By.xpath("//button[.//span[normalize-space()='Search']]");
}

