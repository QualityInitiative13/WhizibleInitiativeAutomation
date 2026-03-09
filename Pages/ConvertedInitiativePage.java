package Pages;

import Actions.ActionEngine;
import Locators.ConvertedInitiativePageLocators;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;



import java.util.LinkedHashMap;

/**
 * Page Object Model (POM) for Converted Initiative Management Module
 * 
 * Added by Gayatri.Kasav - All methods specific to Converted Initiatives page
 * 
 * @author Gayatri.Kasav
 * @version 1.0
 */
public class ConvertedInitiativePage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    // Constructor with WebDriver + Logger
    public ConvertedInitiativePage(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // ==================== NAVIGATION METHODS ====================
    
    /**
     * Navigate to Converted Initiative page
     * Added by Gayatri.Kasav
     */
    public void navigateToConvertedInitiativePage() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO CONVERTED INITIATIVE - START");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        System.out.println("📍 Step 1: Clicking Initiative Management Node...");
        WebElement node = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.initiativeManagementNode));
        node.click();
        System.out.println();
        
        waitForSeconds(1);
        
        System.out.println("📍 Step 2: Clicking Converted Initiative Navigation...");
        WebElement nav = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.convertedInitiativeNav));
        nav.click();
        System.out.println();
        
        waitForSeconds(2);
        
        System.out.println("✅ ✅ ✅ Navigated to Converted Initiative successfully! ✅ ✅ ✅");
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO CONVERTED INITIATIVE - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    /**
     * Navigate to Initiative Tracking page
     * Added by Gayatri.Kasav - For Initiative Conversion flow
     */
    public void navigateToInitiativeTrackingPage() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO INITIATIVE TRACKING - START");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        try {
            System.out.println("📍 Clicking Initiative Tracking page navigation...");
            WebElement trackingPage = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.initiativeTrackingPage));
            
            // Scroll into view if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", trackingPage);
            waitForSeconds(1);
            
            // Try normal click first
            try {
                trackingPage.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("  ⚠️ Normal click intercepted, using JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", trackingPage);
            }
            
            waitForSeconds(2);
            System.out.println("✅ ✅ ✅ Navigated to Initiative Tracking page successfully! ✅ ✅ ✅");
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to Initiative Tracking page");
            }
        } catch (Exception e) {
            System.out.println("❌ ❌ ❌ Navigation to Initiative Tracking FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to Initiative Tracking: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO INITIATIVE TRACKING - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    /**
     * Click on Initiative Conversion page
     * Added by Gayatri.Kasav - For Initiative Conversion flow
     */
    public void clickInitiativeConversionPage() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 CLICKING INITIATIVE CONVERSION PAGE - START");
        System.out.println("═══════════════════════════════════════════════════════\n");
        
        try {
            System.out.println("📍 Clicking Initiative Conversion page...");
            WebElement conversionPage = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.initiativeConversionPage));
            
            // Scroll into view if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", conversionPage);
            waitForSeconds(1);
            
            // Try normal click first
            try {
                conversionPage.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("  ⚠️ Normal click intercepted, using JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", conversionPage);
            }
            
            waitForSeconds(2);
            System.out.println("✅ ✅ ✅ Clicked Initiative Conversion page successfully! ✅ ✅ ✅");
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Initiative Conversion page");
            }
        } catch (Exception e) {
            System.out.println("❌ ❌ ❌ Clicking Initiative Conversion page FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Conversion page: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 CLICKING INITIATIVE CONVERSION PAGE - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    /**
     * Verify Converted Initiative header
     * Added by Gayatri.Kasav
     */
    public void verifyConvertedInitiativeHeader(String expectedHeader) throws Exception {
        WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(ConvertedInitiativePageLocators.convertedInitiativeHeader));
        String actualHeader = header.getText();
        System.out.println("✅ Got text from: Converted Initiative Header (Added by Gayatri.Kasav) → " + actualHeader);
        if (!actualHeader.contains(expectedHeader)) {
            throw new Exception("Header mismatch. Expected: " + expectedHeader + " | Actual: " + actualHeader);
        }
    }
    
    /**
     * Close quick notification if present
     * Added by Gayatri.Kasav
     */
/*    public void closeQuickNotificationIfPresent() throws Exception {
        try {
            WebElement closeBtn = driver.findElement(ConvertedInitiativePageLocators.quickNotificationCloseButton);
            if (closeBtn != null && closeBtn.isDisplayed()) {
                closeBtn.click();
                System.out.println("✅ Closed quick notification popup");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Quick notification popup not present; proceeding");
        }
    }
 */   
    
    public void closeQuickNotificationIfPresent() {
        By closeBtnLocator = ConvertedInitiativePageLocators.quickNotificationCloseButton;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            List<WebElement> closeButtons = driver.findElements(closeBtnLocator);

            if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {

                WebElement closeBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(closeButtons.get(0))
                );

                // 🔥 Fluent UI safe click
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", closeBtn);

                // ✅ wait for modal to disappear
                wait.until(ExpectedConditions.invisibilityOf(closeBtn));

                System.out.println("✅ Closed quick notification popup");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Quick notification popup not present; proceeding");
        }
    }

    // ==================== CHARTS METHODS ====================
    
    public void openChartsSection() throws Exception {
        click(ConvertedInitiativePageLocators.chartview, "chartview");
    }
    
    public void validateChartsAgainstGrid() {

        By chartRows = By.xpath(
            "//div[contains(@class,'graphSection')]//div[contains(@class,'row mb-2')]"
        );
        By nameLocator = By.xpath(".//span[contains(@class,'iniTxt')]");
        By countLocator = By.xpath(".//div[contains(@class,'progress-bar')]");

        List<WebElement> rows = driver.findElements(chartRows);

        // ✅ Validate Top 5 present
        Assert.assertEquals(
                rows.size(),
                5,
                "❌ Expected 5 initiatives in chart, but found: " + rows.size()
        );

        System.out.println("📊 Validating Top 5 Nature of Initiative");
        System.out.println("--------------------------------");

        for (WebElement row : rows) {

            String name = row.findElement(nameLocator)
                             .getText()
                             .replace(":", "")
                             .trim();

            String countText = row.findElement(countLocator)
                                  .getText()
                                  .trim();

            // ✅ Name validation
            Assert.assertFalse(
                    name.isEmpty(),
                    "❌ Initiative name is empty in chart"
            );

            // ✅ Count validation
            Assert.assertTrue(
                    countText.matches("\\d+"),
                    "❌ Count is not numeric for: " + name
            );

            int count = Integer.parseInt(countText);

            Assert.assertTrue(
                    count > 0,
                    "❌ Count is zero/negative for: " + name
            );

            System.out.println("✔ " + name + " : " + count);
        }

        System.out.println("✅ Chart validation passed");
    }

    
    public void closeChartsSection() throws Exception {
        click(ConvertedInitiativePageLocators.closechart, "closechart");
    }
    
    // ==================== SEARCH METHODS ====================
    
    /**
     * Click search icon to open search window
     * Added by Gayatri.Kasav
     */
    public void clickSearchIcon() throws Exception {
        System.out.println("\n🔍 Clicking Search Icon...");
        try {
            // Check if search window is already open
            try {
                WebElement searchInput = driver.findElement(ConvertedInitiativePageLocators.searchInput);
                if (searchInput != null && searchInput.isDisplayed()) {
                    System.out.println("📍 Search window already open, proceeding...");
                    return;
                }
            } catch (Exception e) {
                // Search window not open, proceed to click search icon
            }
            
            System.out.println("📍 Search window not open, proceeding to click search icon...");
            
            // Try multiple locators for search icon
            By[] searchIconLocators = {
                ConvertedInitiativePageLocators.searchIcon,
                By.xpath("(//img[@aria-label='Search'])[1]"),
                By.xpath("//img[contains(@aria-label,'Search')]"),
                By.xpath("//button[contains(@aria-label,'Search')]")
            };
            
            WebElement searchIcon = null;
            for (By locator : searchIconLocators) {
                try {
                    searchIcon = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(locator));
                    if (searchIcon != null && searchIcon.isDisplayed()) {
                        System.out.println("  → Trying search icon locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (searchIcon == null) {
                throw new Exception("Search icon not found with any locator");
            }
            
            System.out.println("✅ Found search icon using locator: " + ConvertedInitiativePageLocators.searchIcon);
            
            // Try normal click first
            try {
                searchIcon.click();
                System.out.println("✅ Search icon clicked (normal click)");
            } catch (Exception e) {
                // If normal click fails, use JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
                System.out.println("✅ Search icon clicked (JavaScript click)");
            }
            
            System.out.println("✅ Search icon clicked successfully");
            
            // Wait for search window to open - wait for initiativeCode input field
            waitForSeconds(1);
            
            // Verify search window opened by checking for initiativeCode input
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='initiativeCode']")));
                System.out.println("✅ Search window opened successfully (initiativeCode input found)");
            } catch (Exception e) {
                // Also try generic search input as fallback
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.presenceOfElementLocated(ConvertedInitiativePageLocators.searchInput));
                    System.out.println("✅ Search window opened successfully (generic search input found)");
                } catch (Exception e2) {
                    System.out.println("⚠️ Search window may not have opened: " + e2.getMessage());
                    throw new Exception("Search window did not open after clicking search icon: " + e2.getMessage());
                }
            }
        } catch (Exception e) {
            throw new Exception("Failed to click search icon: " + e.getMessage());
        }
    }
    
    /**
     * Close search window
     * Added by Gayatri.Kasav
     */
    public void closeSearchWindow() throws Exception {
        try {
            WebElement closeBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.closeSearchButton));
            closeBtn.click();
            System.out.println("✅ Closed search window");
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("⚠️ Could not close search window: " + e.getMessage());
        }
    }
    
    /**
     * Click search button to execute search
     * Added by Gayatri.Kasav
     */
    public void clickSearchButton() throws Exception {
        try {
            WebElement searchBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.searchButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchBtn);
            waitForSeconds(1);
            searchBtn.click();
            System.out.println("✅ Clicked search button");
            waitForSeconds(1);
        } catch (Exception e) {
            throw new Exception("Failed to click search button: " + e.getMessage());
        }
    }
    
    public int getGridRecordsCount() throws Exception {
        // Implementation needed - return 0 for now
        return 0;
    }
    
    /**
     * Click edit icon in grid to open edit view
     * Added by Gayatri.Kasav
     */
    public void clickEditIcon() throws Exception {
        System.out.println("📍 Clicking edit icon in grid...");
        try {
            // Wait for grid to load first
            waitForSeconds(2);
            
            // Try multiple locators for edit icon
            By[] editIconLocators = {
                ConvertedInitiativePageLocators.editButtonFlexible, // Flexible xpath - first row, 7th column
                ConvertedInitiativePageLocators.editButton, // CSS selector
                By.xpath("//div[@role='row' and not(contains(@class,'header'))]//div[@col-id='actions' or @col-id='action']//button[1]"),
                By.xpath("//div[@role='row']//button[contains(@aria-label,'Edit') or contains(@title,'Edit')]"),
                By.xpath("//div[@role='gridcell']//button[.//svg]"),
                By.xpath("//div[@role='row'][1]//div[7]//button[1]"), // First row, 7th column
                By.cssSelector("button[aria-label*='Edit'], button[title*='Edit']")
            };
            
            WebElement editIcon = null;
            By usedLocator = null;
            for (By locator : editIconLocators) {
                try {
                    editIcon = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(locator));
                    if (editIcon != null && editIcon.isDisplayed()) {
                        usedLocator = locator;
                        System.out.println("  → Found edit icon using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (editIcon == null) {
                throw new Exception("Edit icon not found with any locator. Please check if grid has loaded and contains records.");
            }
            
            // Scroll to element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editIcon);
            waitForSeconds(1);
            
            // Try normal click first
            try {
                editIcon.click();
                System.out.println("✅ Edit icon clicked (normal click)");
            } catch (Exception e) {
                // If normal click fails, use JavaScript click
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIcon);
                    System.out.println("✅ Edit icon clicked (JavaScript click)");
                } catch (Exception e2) {
                    // Try clicking parent button if SVG path was found
                    try {
                        WebElement parentButton = editIcon.findElement(By.xpath("./ancestor::button"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                        System.out.println("✅ Edit icon clicked (via parent button)");
                    } catch (Exception e3) {
                        throw new Exception("Failed to click edit icon with all methods: " + e3.getMessage());
                    }
                }
            }
            
            System.out.println("✅ Edit icon clicked successfully");
            waitForSeconds(2); // Wait for edit view to load
        } catch (Exception e) {
            throw new Exception("Failed to click edit icon: " + e.getMessage());
        }
    }
    
    public void validateEditViewActions(boolean includeWorkflow) throws Exception {
        // Implementation needed
    }
    
    /**
     * Search by Nature of Initiative
     * Added by Gayatri.Kasav
     */
    public void searchByNatureOfInitiative(String natureOfInitiative) throws Exception {

    	  click(ConvertedInitiativePageLocators.natureOfInitiativeDropdown, "natureOfInitiativeDropdown");
          selectFromList(ConvertedInitiativePageLocators.natureOfInitiativeOptions, natureOfInitiative, "natureOfInitiativeOptions");
    }
    
    /**
     * Search by Business Group
     * Added by Gayatri.Kasav
     */
    public void searchByBusinessGroup(String businessGroup) throws Exception {

  	  click(ConvertedInitiativePageLocators.businessGroupDropdown, "businessGroupDropdown");
        selectFromList(ConvertedInitiativePageLocators.businessGroupOptions, businessGroup, "businessGroupOptions");
    }
    
    /**
     * Search by Organization Unit
     * Added by Gayatri.Kasav
     */
    public void searchByOrganizationUnit(String organizationUnit) throws Exception {
    	click(ConvertedInitiativePageLocators.organizationUnitDropdown, "organizationUnitDropdown");
        selectFromList(ConvertedInitiativePageLocators.organizationUnitOptions, organizationUnit, "organizationUnitOptions");
    }
    
    /**
     * Search by Initiative Code
     * Added by Gayatri.Kasav
     */
    public void searchByInitiativeCode(String initiativeCode) throws Exception {
    	   type(ConvertedInitiativePageLocators.initiativeCodeInput, initiativeCode, "initiativeCodeInput");
    }
    
    /**
     * Search by Initiative Title
     * Added by Gayatri.Kasav
     */
    public void searchByInitiativeTitle(String initiativeTitle) throws Exception {
    	   type(ConvertedInitiativePageLocators.initiativeTitleInput, initiativeTitle, "initiativeTitleInput");
    }
    
    /**
     * Search by Converted To
     * Added by Gayatri.Kasav
     */
    public void searchByConvertedTo(String convertedTo) throws Exception {
    	click(ConvertedInitiativePageLocators.convertedToDropdown, "convertedToDropdown");
        selectFromList(ConvertedInitiativePageLocators.convertedToOptions, convertedTo, "convertedToOptions");
    }
    
    /**
     * Search with all filters combined
     * Added by Gayatri.Kasav
     */
    public void searchWithAllFilters(String natureOfInitiative, String businessGroup, String organizationUnit, 
                                     String initiativeCode, String initiativeTitle, String convertedTo) throws Exception {
        // Implementation needed
    }
    
    /**
     * Clear search filters
     * Added by Gayatri.Kasav
     */
    public void clearSearch() throws Exception {
        // Implementation needed
    }
    
    // ==================== AUTHORITY METHODS ====================
    
    /**
     * Check if user has Approve/PushBack/Withdraw authority
     * Added by Gayatri.Kasav
     */
    public boolean hasApprovePushBackAuthority() throws Exception {
        System.out.println("\n📍 Checking user authority (Approve/PushBack)...");
        try {
            // Check for Approve tab
            WebElement approveTab = driver.findElement(By.xpath("//a[contains(text(),'Approve') or contains(.,'Approve')]"));
            boolean hasApprove = approveTab != null && approveTab.isDisplayed();
            if (hasApprove) {
                System.out.println("  ✅ Approve tab found - User has Approve authority");
            }
            
            // Check for Push Back tab
            WebElement pushBackTab = driver.findElement(By.xpath("//a[contains(text(),'Push Back') or contains(.,'Push Back')]"));
            boolean hasPushBack = pushBackTab != null && pushBackTab.isDisplayed();
            if (hasPushBack) {
                System.out.println("  ✅ Push Back tab found - User has PushBack authority");
            }
            
            boolean hasAuthority = hasApprove || hasPushBack;
            System.out.println("  📊 Authority Check Result: " + (hasAuthority ? "HAS authority" : "NO authority"));
            return hasAuthority;
        } catch (Exception e) {
            System.out.println("  📊 Authority Check Result: NO authority (tabs not found)");
            return false;
        }
    }
    
    // ==================== SUBTAB METHODS ====================
    
    /**
     * Check if subtab is available
     * Added by Gayatri.Kasav
     */
    public boolean isSubtabAvailable(By tabLocator, String tabName) throws Exception {
        try {
            WebElement tab = driver.findElement(tabLocator);
            boolean isAvailable = tab != null && tab.isDisplayed();
            if (isAvailable) {
                System.out.println("  ✅ " + tabName + " tab is available");
            }
            return isAvailable;
        } catch (Exception e) {
            System.out.println("  ❌ " + tabName + " tab is not available");
            return false;
        }
    }
    
    /**
     * Navigate to subtab if available
     * Added by Gayatri.Kasav
     */
    public boolean navigateToSubtabIfAvailable(By tabLocator, String tabName) throws Exception {
        if (!isSubtabAvailable(tabLocator, tabName)) {
            return false;
        }
        
        System.out.println("📍 Navigating to " + tabName + " Tab...");
        waitForSeconds(1);
        
        // First, verify we're still in edit view - if not, something went wrong
        if (!isInEditView()) {
            System.out.println("  ⚠️ WARNING: Not in edit view! May have navigated away.");
            System.out.println("  ⚠️ This should not happen - continuing anyway...");
        }
        
        // Check if modal backdrop is blocking and close it first
        try {
            List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
            for (WebElement backdrop : backdrops) {
                if (backdrop != null && backdrop.isDisplayed()) {
                    System.out.println("  ⚠️ Modal backdrop detected, closing it first...");
                    try {
                        driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                        waitForSeconds(2);
                        System.out.println("  ✅ Closed backdrop using ESC key");
                    } catch (Exception e) {
                        // Ignore
                    }
                }
            }
        } catch (Exception e) {
            // No backdrop found, proceed
        }
        
        WebElement tab = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(tabLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
        waitForSeconds(1);
        
        // Use JavaScript click to avoid interception
        try {
            tab.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("  ⚠️ Normal click intercepted, using JavaScript click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }
        
        System.out.println("  ✅ Clicked " + tabName + " Tab");
        waitForSeconds(2);
        return true;
    }
    
    // ==================== BUTTON VALIDATION METHODS ====================
    
    /**
     * Validate Add button based on authority
     * Added by Gayatri.Kasav
     * Note: Button may be visible but disabled when user has no authority
     */
    public void validateAddButton(boolean hasAuthority) throws Exception {
        System.out.println("📍 Validating Add Button (hasAuthority: " + hasAuthority + ")...");
        try {
            WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add') or contains(.,'Add')]"));
            boolean isDisplayed = addButton != null && addButton.isDisplayed();
            
            if (isDisplayed) {
                // Button is visible - check if it's enabled or disabled
                boolean isEnabled = addButton.isEnabled();
                String disabledAttr = addButton.getAttribute("disabled");
                String ariaDisabled = addButton.getAttribute("aria-disabled");
                boolean isActuallyDisabled = !isEnabled || disabledAttr != null || "true".equals(ariaDisabled);
                
                System.out.println("  ℹ️ Add button found - Displayed: " + isDisplayed + ", Enabled: " + isEnabled + ", Disabled attr: " + disabledAttr + ", Aria-disabled: " + ariaDisabled);
                
                if (hasAuthority) {
                    // User HAS authority → Button should be ENABLED
                    if (isActuallyDisabled) {
                        throw new Exception("Add button should be ENABLED (user has authority), but it is DISABLED!");
                    }
                    System.out.println("  ✅ Add button correctly ENABLED (user has authority)");
                    System.out.println("     Button text: '" + addButton.getText() + "'");
                } else {
                    // User does NOT have authority → Button should be DISABLED or NOT VISIBLE
                    if (!isActuallyDisabled) {
                        // Button is enabled but user has no authority - this is wrong
                        throw new Exception("Add button should be DISABLED or HIDDEN (user has no authority), but it is ENABLED!");
                    }
                    System.out.println("  ✅ Add button correctly DISABLED (user has no authority)");
                }
            } else {
                // Button is not displayed
                if (hasAuthority) {
                    throw new Exception("Add button should be displayed and enabled (user has authority), but it was NOT found!");
                } else {
                    System.out.println("  ✅ Add button correctly NOT displayed (user has no authority)");
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Button not found at all
            if (hasAuthority) {
                throw new Exception("Add button should be displayed and enabled (user has authority), but it was NOT found!");
            } else {
                System.out.println("  ✅ Add button correctly NOT found (user has no authority)");
            }
        }
    }
    
    /**
     * Validate Save button based on authority
     * Added by Gayatri.Kasav
     * Note: Button may be visible but disabled when user has no authority
     */
    public void validateSaveButton(boolean hasAuthority) throws Exception {
        System.out.println("📍 Validating Save Button (hasAuthority: " + hasAuthority + ")...");
        
        // Try Resources-specific Save button first, then fallback to generic
        By[] saveButtonLocators = {
            ConvertedInitiativePageLocators.resourcesSaveButton, // Resources-specific XPath
            ConvertedInitiativePageLocators.saveButton // Generic fallback
        };
        
        WebElement saveButton = null;
        By usedLocator = null;
        
        for (By locator : saveButtonLocators) {
            try {
                saveButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.presenceOfElementLocated(locator));
                if (saveButton != null && saveButton.isDisplayed()) {
                    usedLocator = locator;
                    System.out.println("  ✅ Found Save button using locator: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (saveButton == null) {
            // Button not found at all
            if (hasAuthority) {
                throw new Exception("Save button should be displayed and enabled (user has authority), but it was NOT found!");
            } else {
                System.out.println("  ✅ Save button correctly NOT found (user has no authority)");
                return;
            }
        }
        
        // Button is visible - check if it's enabled or disabled
        boolean isDisplayed = saveButton.isDisplayed();
        boolean isEnabled = saveButton.isEnabled();
        String disabledAttr = saveButton.getAttribute("disabled");
        String ariaDisabled = saveButton.getAttribute("aria-disabled");
        boolean isActuallyDisabled = !isEnabled || disabledAttr != null || "true".equals(ariaDisabled);
        
        System.out.println("  ℹ️ Save button found - Displayed: " + isDisplayed + ", Enabled: " + isEnabled + ", Disabled attr: " + disabledAttr + ", Aria-disabled: " + ariaDisabled);
        
        if (hasAuthority) {
            // User HAS authority → Button should be ENABLED (user can update and save)
            if (isActuallyDisabled) {
                throw new Exception("Save button should be ENABLED (user has approve/pushback authority - can update and save), but it is DISABLED!");
            }
            System.out.println("  ✅ Save button correctly ENABLED (user has approve/pushback authority - can update and save)");
        } else {
            // User does NOT have authority → Button should be DISABLED (user cannot update and save)
            if (!isActuallyDisabled) {
                // Button is enabled but user has no authority - this is wrong
                throw new Exception("Save button should be DISABLED (user has no approve/pushback authority - cannot update and save), but it is ENABLED!");
            }
            System.out.println("  ✅ Save button correctly DISABLED (user has no approve/pushback authority - cannot update and save)");
        }
    }
    
    // ==================== RECORD METHODS ====================
    
    /**
     * Check if records exist in list view
     * Added by Gayatri.Kasav
     */
    public boolean hasRecordsInListView() throws Exception {
        System.out.println("\n📋 ═══════════════════════════════════════════");
        System.out.println("📋 Counting Records in Grid");
        System.out.println("📋 ═══════════════════════════════════════════\n");
        
        By[] gridRowLocators = {
            By.xpath("//div[@role='row' and not(contains(@class,'header'))]"),
            By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
            By.xpath("//table//tbody//tr[@role='row']")
        };
        
        for (int i = 0; i < gridRowLocators.length; i++) {
            try {
                List<WebElement> rows = driver.findElements(gridRowLocators[i]);
                int count = rows.size();
                System.out.println("  → Trying locator " + (i + 1) + ": " + gridRowLocators[i]);
                System.out.println("    📊 Found " + count + " total elements");
                
                if (count > 0) {
                    System.out.println("    ✅ Found records");
                    System.out.println("\n📋 ═══════════════════════════════════════════\n");
                    return true;
                } else {
                    System.out.println("    ✗ No elements found");
                }
            } catch (Exception e) {
                System.out.println("    ✗ Error: " + e.getMessage());
            }
        }
        
        System.out.println("\n  ⚠️ Could not find grid rows with any locator");
        System.out.println("  ℹ️ Returning false as fallback");
        System.out.println("\n📋 ═══════════════════════════════════════════\n");
        return false;
    }
    
    /**
     * Edit first record from list view
     * Added by Gayatri.Kasav
     */
    public void editFirstRecordFromListView(By editIconLocator, String subtabName) throws Exception {
        System.out.println("  📍 Editing first record in " + subtabName + "...");
        try {
            WebElement editIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(editIconLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editIcon);
            waitForSeconds(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIcon);
            System.out.println("  ✅ Clicked edit icon for first record");
            waitForSeconds(2);
        } catch (Exception e) {
            throw new Exception("Failed to click edit icon: " + e.getMessage());
        }
    }
    
    /**
     * Close record edit modal/dialog and return to subtab list view
     * Added by Gayatri.Kasav - Used after validating Save button to close the record edit modal
     * This keeps us in the main edit view and just closes the record edit dialog
     */
    public void closeRecordEditModal() throws Exception {
        System.out.println("  📍 Closing record edit modal to return to list view...");
        try {
            // First, check if we're in a modal/dialog (record edit view)
            // Look for modal backdrops or dialog containers
            List<WebElement> modals = driver.findElements(By.xpath(
                "//div[contains(@class,'MuiModal-root') or contains(@class,'modal') or contains(@class,'dialog')]"
            ));
            
            boolean modalFound = false;
            for (WebElement modal : modals) {
                try {
                    if (modal != null && modal.isDisplayed()) {
                        modalFound = true;
                        System.out.println("  ℹ️ Record edit modal detected");
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Try to find and click Cancel/Close button within the modal (not the main edit view)
            By[] closeButtonLocators = {
                // Look for Cancel button within modal/dialog
                By.xpath("//div[contains(@class,'MuiModal-root') or contains(@class,'modal')]//button[contains(text(),'Cancel') or contains(.,'Cancel')]"),
                By.xpath("//div[contains(@class,'dialog')]//button[contains(text(),'Cancel') or contains(.,'Cancel')]"),
                // Look for Close (X) button within modal
                By.xpath("//div[contains(@class,'MuiModal-root')]//button[@aria-label='Close' or contains(@class,'close')]"),
                By.xpath("//div[contains(@class,'modal')]//button[@aria-label='Close' or contains(@class,'close')]"),
                // Generic Cancel button (but only if we're in a modal context)
                By.xpath("//button[contains(text(),'Cancel') and (ancestor::div[contains(@class,'MuiModal-root')] or ancestor::div[contains(@class,'modal')])]")
            };
            
            boolean closed = false;
            for (By locator : closeButtonLocators) {
                try {
                    WebElement closeBtn = driver.findElement(locator);
                    if (closeBtn != null && closeBtn.isDisplayed() && closeBtn.isEnabled()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeBtn);
                        waitForSeconds(1);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                        System.out.println("  ✅ Clicked Cancel/Close button to close record edit modal");
                        waitForSeconds(2);
                        closed = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // If no close button found, try ESC key (should only close modal, not main edit view)
            if (!closed) {
                try {
                    // Only press ESC if we detected a modal
                    if (modalFound) {
                        driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                        System.out.println("  ✅ Pressed ESC key to close record edit modal");
                        waitForSeconds(2);
                    } else {
                        System.out.println("  ℹ️ No modal detected - may already be in list view");
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Could not close record edit modal: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not close record edit modal: " + e.getMessage());
            // Don't throw - just continue, we might already be in list view
        }
    }
    
    /**
     * Verify we're still in the main edit view (not navigated away)
     * Added by Gayatri.Kasav - To ensure we stay in edit view when navigating between subtabs
     */
    public boolean isInEditView() {
        try {
            // Check for edit view indicators - subtabs should be visible
            List<WebElement> subtabs = driver.findElements(By.xpath(
                "//a[contains(@href,'#resources') or contains(@href,'#cost') or contains(@href,'#funding')]"
            ));
            if (subtabs.size() > 0) {
                System.out.println("  ✅ Still in edit view - subtabs are visible");
                return true;
            }
            
            // Alternative check - look for edit view specific elements
            List<WebElement> editViewElements = driver.findElements(By.xpath(
                "//div[contains(@class,'edit') or contains(@class,'form')]"
            ));
            if (editViewElements.size() > 0) {
                System.out.println("  ✅ Still in edit view - edit form elements are visible");
                return true;
            }
            
            System.out.println("  ⚠️ May have navigated away from edit view");
            return false;
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not verify edit view status: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== MONTHLY DISTRIBUTION METHODS ====================
    
    /**
     * Navigate to Monthly Distribution tab
     * Added by Gayatri.Kasav
     */
    public void navigateToMonthlyDistributionTab() throws Exception {
        System.out.println("  📍 Navigating to Monthly Distribution subtab...");
        try {
            WebElement monthlyDistTab = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.monthlyDistributionTab));
            monthlyDistTab.click();
            System.out.println("  ✅ Clicked Monthly Distribution tab");
            waitForSeconds(2);
        } catch (Exception e) {
            throw new Exception("Monthly Distribution tab not available: " + e.getMessage());
        }
    }
    
    /**
     * Validate Monthly Distribution Save button
     * Added by Gayatri.Kasav
     */
    public void validateMonthlyDistributionSaveButton(boolean hasAuthority) throws Exception {
        System.out.println("  📍 Validating Monthly Distribution Save Button (hasAuthority: " + hasAuthority + ")...");
        waitForSeconds(1);
        
        try {
            WebElement saveButton = driver.findElement(ConvertedInitiativePageLocators.monthlyDistributionSaveButton);
            boolean isDisplayed = saveButton != null && saveButton.isDisplayed();
            
            if (isDisplayed) {
                // Button is visible - check if it's enabled or disabled
                boolean isEnabled = saveButton.isEnabled();
                String disabledAttr = saveButton.getAttribute("disabled");
                String ariaDisabled = saveButton.getAttribute("aria-disabled");
                boolean isActuallyDisabled = !isEnabled || disabledAttr != null || "true".equals(ariaDisabled);
                
                System.out.println("    ℹ️ Save button found - Displayed: " + isDisplayed + ", Enabled: " + isEnabled + ", Disabled attr: " + disabledAttr + ", Aria-disabled: " + ariaDisabled);
                
                if (hasAuthority) {
                    // User HAS authority → Button should be ENABLED
                    if (isActuallyDisabled) {
                        throw new Exception("Save button should be ENABLED in Monthly Distribution (user has authority), but it is DISABLED!");
                    }
                    System.out.println("  ✅ Save button correctly ENABLED in Monthly Distribution (user has authority)");
                } else {
                    // User does NOT have authority → Button should be DISABLED or NOT VISIBLE
                    if (!isActuallyDisabled) {
                        throw new Exception("Save button should be DISABLED or HIDDEN in Monthly Distribution (user has no authority), but it is ENABLED!");
                    }
                    System.out.println("  ✅ Save button correctly DISABLED in Monthly Distribution (user has no authority)");
                }
            } else {
                // Button is not displayed
                if (hasAuthority) {
                    throw new Exception("Save button should be displayed and enabled in Monthly Distribution (user has authority), but it was NOT found!");
                } else {
                    System.out.println("  ✅ Save button correctly NOT displayed in Monthly Distribution (user has no authority)");
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Button not found at all
            if (hasAuthority) {
                throw new Exception("Save button should be displayed and enabled in Monthly Distribution (user has authority), but it was NOT found!");
            } else {
                System.out.println("  ✅ Save button correctly NOT found in Monthly Distribution (user has no authority)");
            }
        }
    }

    /**
     * ✅ Validate Upload Document and Attach URL subtabs
     * Added by Gayatri.Kasav - Navigates to Document Upload tab, then validates Upload Document and Attach URL subtabs
     * Flow: Document Upload Tab → Upload Document Subtab → Verify Upload Button → Close → Attach URL Subtab → Verify Attach URL Button → Close → Ready for Timeline
     * @param hasAuthority true if user has Approve/PushBack authority, false otherwise
     */
    public void validateDocumentUploadButtons(boolean hasAuthority) throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📍 DOCUMENT UPLOAD VALIDATION - START");
        System.out.println("📍 Validating Document Upload subtabs (hasAuthority: " + hasAuthority + ")...");
        System.out.println("═══════════════════════════════════════════════════════");
        waitForSeconds(1);
        
        // Note: Document Upload tab is already navigated by the calling test method
        // ==================== STEP 1: UPLOAD DOCUMENT SUBTAB ====================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📍 STEP 1: Upload Document Subtab");
        System.out.println("📍 Flow: Click Upload Document → Verify Upload Button → Close Drawer");
        System.out.println("═══════════════════════════════════════════════════════");
        
        boolean uploadDocumentCompleted = false;
        try {
            WebElement uploadDocSubtab = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.uploadDocumentSubtab));
            if (uploadDocSubtab != null && uploadDocSubtab.isDisplayed()) {
                System.out.println("  📍 Clicking Upload Document subtab...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", uploadDocSubtab);
                waitForSeconds(1);
                uploadDocSubtab.click();
                System.out.println("  ✅ Clicked Upload Document subtab");
                waitForSeconds(2); // Wait for drawer to open
                
                // Validate Upload button inside drawer based on authority
                System.out.println("  📍 Checking Upload button inside drawer...");
                try {
                    WebElement uploadButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.presenceOfElementLocated(ConvertedInitiativePageLocators.uploadButton));
                    
                    boolean isDisplayed = uploadButton != null && uploadButton.isDisplayed();
                    
                    if (hasAuthority) {
                        // User HAS authority → Upload button SHOULD be displayed
                        if (!isDisplayed) {
                            throw new Exception("Upload button should be displayed in Upload Document drawer (user has authority), but it was NOT found!");
                        }
                        System.out.println("  ✅ Upload button correctly displayed in drawer (user has authority)");
                    } else {
                        // User does NOT have authority → Upload button should NOT be displayed
                        if (isDisplayed) {
                            throw new Exception("Upload button should NOT be displayed in Upload Document drawer (user has no authority), but it was found!");
                        }
                        System.out.println("  ✅ Upload button correctly absent in drawer (user has no authority)");
                    }
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    if (hasAuthority) {
                        throw new Exception("Upload button should be displayed in Upload Document drawer (user has authority), but it was NOT found!");
                    } else {
                        System.out.println("  ✅ Upload button correctly absent in drawer (user has no authority)");
                    }
                }
                
                // Close Upload Document drawer - MUST close before proceeding
                System.out.println("  📍 Closing Upload Document drawer...");
                boolean drawerClosed = false;
                int maxRetries = 5;
                
                for (int retry = 0; retry < maxRetries && !drawerClosed; retry++) {
                    try {
                        System.out.println("  🔄 Attempt " + (retry + 1) + " to close Upload Document drawer...");
                        
                        // Try multiple selectors for close button (CSS classes are dynamic)
                        WebElement closeBtn = null;
                        By[] closeSelectors = {
                            ConvertedInitiativePageLocators.uploadDocumentCloseButton, // CSS path
                            ConvertedInitiativePageLocators.drawerCloseButton, // Parent button
                            By.cssSelector("div.MuiDrawer-paper button svg path"), // Generic drawer close path
                            By.cssSelector("div.MuiDrawer-paper button"), // Generic drawer close button
                            By.xpath("//div[contains(@class,'MuiDrawer-paper')]//button[contains(@aria-label,'close') or contains(@aria-label,'Close')]") // Aria label
                        };
                        
                        for (By selector : closeSelectors) {
                            try {
                                closeBtn = new WebDriverWait(driver, Duration.ofSeconds(2))
                                        .until(ExpectedConditions.presenceOfElementLocated(selector));
                                if (closeBtn != null && closeBtn.isDisplayed()) {
                                    System.out.println("    ℹ️ Found close button using selector: " + selector);
                                    break;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        
                        if (closeBtn != null) {
                            // Scroll to element
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeBtn);
                            waitForSeconds(1);
                            
                            // Try JavaScript click first
                            try {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                                System.out.println("    ✅ Clicked close button using JavaScript");
                            } catch (Exception e) {
                                // If JavaScript click fails, try normal click
                                try {
                                    closeBtn.click();
                                    System.out.println("    ✅ Clicked close button using normal click");
                                } catch (Exception e2) {
                                    // Try clicking parent SVG or button
                                    try {
                                        WebElement parentSvg = closeBtn.findElement(By.xpath("./ancestor::svg"));
                                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentSvg);
                                        System.out.println("    ✅ Clicked parent SVG");
                                    } catch (Exception e3) {
                                        WebElement parentButton = closeBtn.findElement(By.xpath("./ancestor::button"));
                                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                                        System.out.println("    ✅ Clicked parent button");
                                    }
                                }
                            }
                            
                            // Wait for drawer to close
                            waitForSeconds(3);
                            
                            // Verify drawer is actually closed by checking for backdrop
                            boolean backdropFound = false;
                            try {
                                List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                                for (WebElement backdrop : backdrops) {
                                    if (backdrop != null && backdrop.isDisplayed()) {
                                        backdropFound = true;
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                // No backdrop found
                            }
                            
                            if (!backdropFound) {
                                drawerClosed = true;
                                System.out.println("  ✅ Upload Document drawer closed successfully (no backdrop found)");
                            } else {
                                System.out.println("  ⚠️ Backdrop still present after close attempt " + (retry + 1));
                                if (retry < maxRetries - 1) {
                                    waitForSeconds(1);
                                    continue; // Retry
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("  ⚠️ Could not find/click close button (attempt " + (retry + 1) + "): " + e.getMessage());
                        if (retry < maxRetries - 1) {
                            waitForSeconds(1);
                            continue; // Retry
                        }
                    }
                }
                
                // If still not closed, try ESC key
                if (!drawerClosed) {
                    try {
                        System.out.println("  ⚠️ Close button failed, trying ESC key...");
                        driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                        waitForSeconds(3);
                        
                        // Verify after ESC
                        try {
                            List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                            boolean backdropFound = false;
                            for (WebElement backdrop : backdrops) {
                                if (backdrop != null && backdrop.isDisplayed()) {
                                    backdropFound = true;
                                    break;
                                }
                            }
                            if (!backdropFound) {
                                drawerClosed = true;
                                System.out.println("  ✅ Upload Document drawer closed using ESC key");
                            }
                        } catch (Exception e) {
                            drawerClosed = true;
                            System.out.println("  ✅ Upload Document drawer closed using ESC key");
                        }
                    } catch (Exception e) {
                        System.out.println("  ⚠️ Could not close drawer using ESC key: " + e.getMessage());
                    }
                }
                
                // CRITICAL: Verify drawer is closed before proceeding
                if (!drawerClosed) {
                    throw new Exception("❌ CRITICAL: Upload Document drawer is STILL OPEN! Cannot proceed to Attach URL. Drawer must be closed first.");
                }
                
                System.out.println("  ✅ Upload Document validation completed successfully");
                uploadDocumentCompleted = true;
                waitForSeconds(3); // Additional wait to ensure UI is ready
            } else {
                throw new Exception("Upload Document subtab not found or not displayed");
            }
        } catch (Exception e) {
            System.out.println("  ❌ ERROR in Upload Document validation: " + e.getMessage());
            System.out.println("  ⚠️ Upload Document validation failed, but continuing to Attach URL validation...");
            
            // Try to close drawer if it's still open
            try {
                System.out.println("  🔄 Attempting to close Upload Document drawer (if open)...");
                List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                boolean drawerOpen = false;
                for (WebElement backdrop : backdrops) {
                    if (backdrop != null && backdrop.isDisplayed()) {
                        drawerOpen = true;
                        break;
                    }
                }
                if (drawerOpen) {
                    System.out.println("  📍 Drawer is still open, trying to close it...");
                    driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                    waitForSeconds(3);
                    System.out.println("  ✅ Sent ESC key to close drawer");
                } else {
                    System.out.println("  ✅ Drawer is already closed");
                }
            } catch (Exception closeE) {
                System.out.println("  ⚠️ Could not close drawer: " + closeE.getMessage());
            }
            
            // Don't throw exception - continue to Attach URL validation
            uploadDocumentCompleted = false;
            waitForSeconds(2); // Wait before proceeding
        }
        
        // ==================== STEP 2: ATTACH URL SUBTAB ====================
        System.out.println("\n🔍 PROCEEDING TO ATTACH URL VALIDATION...");
        System.out.println("  ℹ️ Upload Document status: " + (uploadDocumentCompleted ? "✅ Completed" : "❌ Failed (continuing anyway)"));
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📍 STEP 2: Attach URL Subtab");
        System.out.println("📍 Flow: Click Attach URL → Verify Attach URL Button → Close Drawer");
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Wait a bit more after closing Upload Document drawer to ensure UI is ready
        System.out.println("  ⏳ Waiting for UI to be ready after closing Upload Document drawer...");
        waitForSeconds(4);
        
        // Verify we're still on Document Upload tab
        System.out.println("  🔍 Verifying Document Upload tab is still active...");
        try {
            WebElement docUploadTab = driver.findElement(ConvertedInitiativePageLocators.documentUploadTab);
            if (docUploadTab != null && docUploadTab.isDisplayed()) {
                System.out.println("  ✅ Document Upload tab is still active");
            } else {
                System.out.println("  ⚠️ Document Upload tab may not be active, attempting to navigate...");
                navigateToSubtabIfAvailable(ConvertedInitiativePageLocators.documentUploadTab, "Document Upload");
                waitForSeconds(4);
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not verify Document Upload tab status: " + e.getMessage());
            System.out.println("  🔄 Attempting to navigate to Document Upload tab...");
            try {
                navigateToSubtabIfAvailable(ConvertedInitiativePageLocators.documentUploadTab, "Document Upload");
                waitForSeconds(4);
            } catch (Exception e2) {
                System.out.println("  ⚠️ Could not navigate to Document Upload tab: " + e2.getMessage());
            }
        }
        
        try {
            // Use exact XPath provided by user
            System.out.println("  🔍 Looking for Attach URL subtab using exact XPath: " + ConvertedInitiativePageLocators.attachUrlSubtab);
            System.out.println("  📍 XPath: /html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div[2]/button[2]");
            
            // First check if element exists - try with longer wait
            System.out.println("  ⏳ Waiting for Attach URL subtab to be present...");
            List<WebElement> elements = new ArrayList<>();
            for (int attempt = 0; attempt < 5; attempt++) {
                elements = driver.findElements(ConvertedInitiativePageLocators.attachUrlSubtab);
                if (elements.size() > 0) {
                    break;
                }
                System.out.println("    ⏳ Attempt " + (attempt + 1) + ": Element not found, waiting...");
                waitForSeconds(2);
            }
            
            System.out.println("  📊 Found " + elements.size() + " element(s) matching Attach URL subtab XPath");
            
            if (elements.size() == 0) {
                // Debug: Try to find any button in that area
                System.out.println("  🔍 Debug: Searching for buttons in Document Upload area...");
                try {
                    // Try to find buttons in the specific path
                    List<WebElement> pathButtons = driver.findElements(By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div[2]//button"));
                    System.out.println("    ℹ️ Found " + pathButtons.size() + " button(s) in the specific path");
                    for (int i = 0; i < pathButtons.size(); i++) {
                        WebElement btn = pathButtons.get(i);
                        System.out.println("      Button " + (i+1) + ": visible=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled() + ", text='" + btn.getText() + "'");
                    }
                    
                    // Also try generic tabs area
                    List<WebElement> allButtons = driver.findElements(By.xpath("//div[contains(@class,'MuiTabs-root')]//button"));
                    System.out.println("    ℹ️ Found " + allButtons.size() + " button(s) in tabs area");
                    for (int i = 0; i < Math.min(allButtons.size(), 5); i++) {
                        WebElement btn = allButtons.get(i);
                        System.out.println("      Tab Button " + (i+1) + ": visible=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled() + ", text='" + btn.getText() + "'");
                    }
                } catch (Exception debugE) {
                    System.out.println("    ⚠️ Could not debug buttons: " + debugE.getMessage());
                }
                throw new Exception("Attach URL subtab element not found at XPath: " + ConvertedInitiativePageLocators.attachUrlSubtab);
            }
            
            WebElement attachUrlSubtab = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.attachUrlSubtab));
            
            if (attachUrlSubtab == null) {
                throw new Exception("Attach URL subtab element is null");
            }
            
            System.out.println("  ✅ Found Attach URL subtab element");
            System.out.println("    - Displayed: " + attachUrlSubtab.isDisplayed());
            System.out.println("    - Enabled: " + attachUrlSubtab.isEnabled());
            System.out.println("    - Text: '" + attachUrlSubtab.getText() + "'");
            
            if (!attachUrlSubtab.isDisplayed()) {
                throw new Exception("Attach URL subtab is not displayed");
            }
            
            System.out.println("  📍 Clicking Attach URL subtab...");
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", attachUrlSubtab);
            waitForSeconds(2);
            
            // Try multiple click methods
            boolean clicked = false;
            
            // Method 1: JavaScript click
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", attachUrlSubtab);
                System.out.println("  ✅ Clicked Attach URL subtab using JavaScript");
                clicked = true;
                waitForSeconds(2);
            } catch (Exception e) {
                System.out.println("  ⚠️ JavaScript click failed: " + e.getMessage());
            }
            
            // Method 2: Normal click if JavaScript didn't work
            if (!clicked) {
                try {
                    attachUrlSubtab.click();
                    System.out.println("  ✅ Clicked Attach URL subtab using normal click");
                    clicked = true;
                    waitForSeconds(2);
                } catch (Exception e) {
                    System.out.println("  ⚠️ Normal click failed: " + e.getMessage());
                }
            }
            
            // Method 3: Actions class click
            if (!clicked) {
                try {
                    org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
                    actions.moveToElement(attachUrlSubtab).click().perform();
                    System.out.println("  ✅ Clicked Attach URL subtab using Actions class");
                    clicked = true;
                    waitForSeconds(2);
                } catch (Exception e) {
                    System.out.println("  ⚠️ Actions click failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                System.out.println("  ❌ All click methods failed for Attach URL subtab");
                System.out.println("  🔍 Debug: Element details:");
                System.out.println("    - Tag: " + attachUrlSubtab.getTagName());
                System.out.println("    - Location: " + attachUrlSubtab.getLocation());
                System.out.println("    - Size: " + attachUrlSubtab.getSize());
                throw new Exception("All click methods failed for Attach URL subtab. Element found but not clickable.");
            }
            
            System.out.println("  ✅ Attach URL subtab clicked successfully");
            System.out.println("  ⏳ Waiting for Attach URL drawer to open...");
            waitForSeconds(5); // Wait for drawer to open - increased wait time
            
            // Validate Attach URL button inside drawer based on authority (using exact XPath)
            System.out.println("  📍 Checking Attach URL button inside drawer...");
            System.out.println("  🔍 Using XPath: " + ConvertedInitiativePageLocators.attachUrlButton);
            
            // First verify drawer is open by checking for backdrop
            try {
                List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                boolean drawerOpen = false;
                for (WebElement backdrop : backdrops) {
                    if (backdrop != null && backdrop.isDisplayed()) {
                        drawerOpen = true;
                        System.out.println("  ✅ Drawer is open (backdrop found)");
                        break;
                    }
                }
                if (!drawerOpen) {
                    System.out.println("  ⚠️ Drawer may not be open (no backdrop found)");
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not verify drawer status: " + e.getMessage());
            }
            
            try {
                // Check if element exists first
                List<WebElement> buttonElements = driver.findElements(ConvertedInitiativePageLocators.attachUrlButton);
                System.out.println("  📊 Found " + buttonElements.size() + " element(s) matching Attach URL button XPath");
                
                if (buttonElements.size() == 0) {
                    // Debug: Try to find any button in the drawer
                    System.out.println("  🔍 Debug: Searching for buttons in drawer...");
                    try {
                        List<WebElement> allButtons = driver.findElements(By.xpath("//div[contains(@class,'MuiDrawer-paper')]//button"));
                        System.out.println("    ℹ️ Found " + allButtons.size() + " button(s) in drawer");
                        for (int i = 0; i < allButtons.size(); i++) {
                            WebElement btn = allButtons.get(i);
                            System.out.println("      Button " + (i+1) + ": visible=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled() + ", text='" + btn.getText() + "'");
                        }
                    } catch (Exception debugE) {
                        System.out.println("    ⚠️ Could not debug buttons: " + debugE.getMessage());
                    }
                    
                    if (hasAuthority) {
                        throw new Exception("Attach URL button should be displayed in Attach URL drawer (user has authority), but it was NOT found at XPath: " + ConvertedInitiativePageLocators.attachUrlButton);
                    } else {
                        System.out.println("  ✅ Attach URL button correctly absent in drawer (user has no authority)");
                        // Continue to close drawer even if button is not present
                    }
                } else {
                    // Element found, continue with validation
                    WebElement attachUrlButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.presenceOfElementLocated(ConvertedInitiativePageLocators.attachUrlButton));
                    
                    if (attachUrlButton == null) {
                        if (hasAuthority) {
                            throw new Exception("Attach URL button element is null (user has authority)");
                        } else {
                            System.out.println("  ✅ Attach URL button correctly absent in drawer (user has no authority)");
                        }
                    } else {
                
                        boolean isDisplayed = attachUrlButton.isDisplayed();
                        System.out.println("  📊 Attach URL button status:");
                        System.out.println("    - Found: true");
                        System.out.println("    - Displayed: " + isDisplayed);
                        System.out.println("    - Enabled: " + attachUrlButton.isEnabled());
                        System.out.println("    - Text: '" + attachUrlButton.getText() + "'");
                        
                        if (hasAuthority) {
                            // User HAS authority → Attach URL button SHOULD be displayed
                            if (!isDisplayed) {
                                throw new Exception("Attach URL button should be displayed in Attach URL drawer (user has authority), but it was NOT displayed!");
                            }
                            System.out.println("  ✅ Attach URL button correctly displayed in drawer (user has authority)");
                        } else {
                            // User does NOT have authority → Attach URL button should NOT be displayed
                            if (isDisplayed) {
                                throw new Exception("Attach URL button should NOT be displayed in Attach URL drawer (user has no authority), but it was found and displayed!");
                            }
                            System.out.println("  ✅ Attach URL button correctly absent in drawer (user has no authority)");
                        }
                    }
                }
            } catch (org.openqa.selenium.TimeoutException e) {
                if (hasAuthority) {
                    throw new Exception("Timeout waiting for Attach URL button in drawer (user has authority): " + e.getMessage());
                } else {
                    System.out.println("  ✅ Attach URL button correctly absent in drawer (user has no authority) - timeout expected");
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                if (hasAuthority) {
                    throw new Exception("Attach URL button should be displayed in Attach URL drawer (user has authority), but it was NOT found!");
                } else {
                    System.out.println("  ✅ Attach URL button correctly absent in drawer (user has no authority)");
                }
            }
            
            // Close Attach URL drawer using exact XPath provided by user
            System.out.println("  📍 Closing Attach URL drawer using exact XPath...");
            boolean drawerClosed = false;
            int maxRetries = 5;
            
            for (int retry = 0; retry < maxRetries && !drawerClosed; retry++) {
                try {
                    System.out.println("  🔄 Attempt " + (retry + 1) + " to close Attach URL drawer...");
                    
                    // Use exact XPath for close button
                    WebElement closeBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.attachUrlCloseButton));
                    
                    if (closeBtn != null && closeBtn.isDisplayed()) {
                        // Scroll to element
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeBtn);
                        waitForSeconds(1);
                        
                        // Try JavaScript click first
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                            System.out.println("    ✅ Clicked close button using JavaScript");
                        } catch (Exception e) {
                            // If JavaScript click fails, try clicking parent button
                            try {
                                WebElement parentButton = closeBtn.findElement(By.xpath("./ancestor::button"));
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                                System.out.println("    ✅ Clicked parent button using JavaScript");
                            } catch (Exception e2) {
                                // Try clicking parent SVG
                                try {
                                    WebElement parentSvg = closeBtn.findElement(By.xpath("./ancestor::svg"));
                                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentSvg);
                                    System.out.println("    ✅ Clicked parent SVG using JavaScript");
                                } catch (Exception e3) {
                                    throw new Exception("Failed to click close button: " + e3.getMessage());
                                }
                            }
                        }
                        
                        // Wait for drawer to close
                        waitForSeconds(3);
                        
                        // Verify drawer is actually closed by checking for backdrop
                        boolean backdropFound = false;
                        try {
                            List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                            for (WebElement backdrop : backdrops) {
                                if (backdrop != null && backdrop.isDisplayed()) {
                                    backdropFound = true;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            // No backdrop found
                        }
                        
                        if (!backdropFound) {
                            drawerClosed = true;
                            System.out.println("  ✅ Attach URL drawer closed successfully (no backdrop found)");
                        } else {
                            System.out.println("  ⚠️ Backdrop still present after close attempt " + (retry + 1));
                            if (retry < maxRetries - 1) {
                                waitForSeconds(1);
                                continue; // Retry
                            }
                        }
                    } else {
                        throw new Exception("Close button not found or not displayed");
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Could not find/click close button (attempt " + (retry + 1) + "): " + e.getMessage());
                    if (retry < maxRetries - 1) {
                        waitForSeconds(1);
                        continue; // Retry
                    }
                }
            }
            
            // If still not closed, try ESC key
            if (!drawerClosed) {
                try {
                    System.out.println("  ⚠️ Close button failed, trying ESC key...");
                    driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                    waitForSeconds(3);
                    
                    // Verify after ESC
                    try {
                        List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
                        boolean backdropFound = false;
                        for (WebElement backdrop : backdrops) {
                            if (backdrop != null && backdrop.isDisplayed()) {
                                backdropFound = true;
                                break;
                            }
                        }
                        if (!backdropFound) {
                            drawerClosed = true;
                            System.out.println("  ✅ Attach URL drawer closed using ESC key");
                        }
                    } catch (Exception e) {
                        drawerClosed = true;
                        System.out.println("  ✅ Attach URL drawer closed using ESC key");
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Could not close drawer using ESC key: " + e.getMessage());
                }
            }
            
            // CRITICAL: Verify drawer is closed before proceeding to Timeline
            if (!drawerClosed) {
                throw new Exception("❌ CRITICAL: Attach URL drawer is STILL OPEN! Cannot proceed to Timeline. Drawer must be closed first.");
            }
            
            waitForSeconds(2); // Additional wait
            
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("  ❌ Timeout waiting for Attach URL subtab to appear");
            System.out.println("  🔍 Debug: Checking if element exists in DOM...");
            try {
                List<WebElement> checkElements = driver.findElements(ConvertedInitiativePageLocators.attachUrlSubtab);
                System.out.println("    ℹ️ Elements found in DOM: " + checkElements.size());
                if (checkElements.size() > 0) {
                    WebElement elem = checkElements.get(0);
                    System.out.println("    ℹ️ Element exists but may not be clickable:");
                    System.out.println("      - Displayed: " + elem.isDisplayed());
                    System.out.println("      - Enabled: " + elem.isEnabled());
                    System.out.println("      - Text: '" + elem.getText() + "'");
                }
            } catch (Exception debugE) {
                System.out.println("    ⚠️ Could not debug: " + debugE.getMessage());
            }
            throw new Exception("Timeout waiting for Attach URL subtab to appear: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  ❌ Error details: " + e.getMessage());
            System.out.println("  🔍 Stack trace:");
            e.printStackTrace();
            throw new Exception("Failed to validate Attach URL subtab: " + e.getMessage(), e);
        }
        
        // ==================== STEP 3: NAVIGATE TO TIMELINE TAB ====================
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("📍 STEP 3: Navigating to Timeline Tab");
        System.out.println("📍 Flow: After closing Attach URL drawer → Navigate to Timeline tab");
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Final check to ensure all drawers are closed
        waitForSeconds(2);
        try {
            List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') and contains(@class,'MuiModal-backdrop')]"));
            boolean hasBackdrop = false;
            for (WebElement backdrop : backdrops) {
                if (backdrop != null && backdrop.isDisplayed()) {
                    hasBackdrop = true;
                    break;
                }
            }
            if (!hasBackdrop) {
                System.out.println("  ✅ All drawers closed, ready to navigate to Timeline tab");
            } else {
                System.out.println("  ⚠️ Backdrop still present, closing it...");
                driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                waitForSeconds(2);
            }
        } catch (Exception e) {
            System.out.println("  ✅ No backdrop found, ready for Timeline tab");
        }
        
        // Navigate to Timeline tab
        try {
            System.out.println("  🔍 Looking for Timeline tab...");
            WebElement timelineTab = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(ConvertedInitiativePageLocators.timelineTab));
            
            if (timelineTab != null && timelineTab.isDisplayed()) {
                System.out.println("  ✅ Found Timeline tab");
                System.out.println("  📍 Clicking Timeline tab...");
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", timelineTab);
                waitForSeconds(1);
                
                // Try JavaScript click first
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", timelineTab);
                    System.out.println("  ✅ Clicked Timeline tab using JavaScript");
                } catch (Exception e) {
                    // Fallback to normal click
                    try {
                        timelineTab.click();
                        System.out.println("  ✅ Clicked Timeline tab using normal click");
                    } catch (Exception e2) {
                        throw new Exception("Failed to click Timeline tab: " + e2.getMessage());
                    }
                }
                
                waitForSeconds(3); // Wait for Timeline tab to load
                System.out.println("  ✅ Successfully navigated to Timeline tab");
                
                if (reportLogger != null) {
                    reportLogger.pass("Successfully navigated to Timeline tab after Document Upload validation");
                }
            } else {
                throw new Exception("Timeline tab not found or not displayed");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("  ❌ Timeout waiting for Timeline tab to appear");
            throw new Exception("Timeout waiting for Timeline tab: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  ❌ Error navigating to Timeline tab: " + e.getMessage());
            throw new Exception("Failed to navigate to Timeline tab: " + e.getMessage());
        }
        
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("✅ DOCUMENT UPLOAD VALIDATION - COMPLETED");
        System.out.println("✅ Flow Summary:");
        System.out.println("   1. ✅ Upload Document subtab clicked");
        System.out.println("   2. ✅ Upload button verified");
        System.out.println("   3. ✅ Upload Document drawer closed");
        System.out.println("   4. ✅ Attach URL subtab clicked");
        System.out.println("   5. ✅ Attach URL button verified");
        System.out.println("   6. ✅ Attach URL drawer closed");
        System.out.println("   7. ✅ Timeline tab navigated");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    /**
     * Check if records are present on Initiative Conversion page
     * Added by Gayatri.Kasav - For TC_007
     */
    public boolean hasRecordsOnConversionPage() throws Exception {
        System.out.println("  🔍 Checking if records are present on Initiative Conversion page...");
        try {
            // Look for table rows
            List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
            int rowCount = rows.size();
            System.out.println("  📊 Found " + rowCount + " record(s) on Initiative Conversion page");
            return rowCount > 0;
        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking records: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Find row by initiative code and click Convert To Project or Convert To Milestone
     * Added by Gayatri.Kasav - For TC_007
     * @param initiativeCode The initiative code to search for
     * @param convertedTo The ConvertedTo value ("Project" or "Milestone")
     */
    public void convertInitiativeByCode(String initiativeCode, String convertedTo) throws Exception {
        System.out.println("  📍 Converting initiative with code: '" + initiativeCode + "' to: '" + convertedTo + "'");
        System.out.println("  🔍 ConvertedTo value received: '" + convertedTo + "'");
        System.out.println("  🔍 ConvertedTo trimmed: '" + (convertedTo != null ? convertedTo.trim() : "null") + "'");
        System.out.println("  🔍 ConvertedTo equalsIgnoreCase('Project'): " + (convertedTo != null && convertedTo.trim().equalsIgnoreCase("Project")));
        System.out.println("  🔍 ConvertedTo equalsIgnoreCase('Milestone'): " + (convertedTo != null && convertedTo.trim().equalsIgnoreCase("Milestone")));
        
        // Wait for table to load
        waitForSeconds(2);
        
        // Find the row containing the initiative code
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        System.out.println("  📊 Total rows found: " + rows.size());
        
        if (rows.size() == 0) {
            throw new Exception("No rows found in the table on Initiative Conversion page");
        }
        
        int targetRowIndex = -1;
        WebElement targetRow = null;
        
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            try {
                String rowText = row.getText();
                System.out.println("    Row " + (i + 1) + " text: " + rowText.substring(0, Math.min(100, rowText.length())));
                if (rowText.contains(initiativeCode)) {
                    targetRowIndex = i + 1; // XPath uses 1-based index
                    targetRow = row;
                    System.out.println("  ✅ Found initiative code '" + initiativeCode + "' in row " + targetRowIndex);
                    break;
                }
            } catch (Exception e) {
                System.out.println("    ⚠️ Error reading row " + (i + 1) + ": " + e.getMessage());
                continue;
            }
        }
        
        if (targetRowIndex == -1) {
            System.out.println("  🔍 Debug: Listing all rows to find the issue...");
            for (int i = 0; i < rows.size(); i++) {
                try {
                    String rowText = rows.get(i).getText();
                    System.out.println("    Row " + (i + 1) + ": " + rowText);
                } catch (Exception e) {
                    System.out.println("    Row " + (i + 1) + ": (error reading)");
                }
            }
            throw new Exception("Initiative code '" + initiativeCode + "' not found in any row on Initiative Conversion page");
        }
        
        // Debug: Check the structure of the target row
        System.out.println("  🔍 Debug: Analyzing row " + targetRowIndex + " structure...");
        try {
            List<WebElement> cells = targetRow.findElements(By.tagName("td"));
            System.out.println("    ℹ️ Found " + cells.size() + " cells in row");
            for (int i = 0; i < cells.size(); i++) {
                try {
                    String cellText = cells.get(i).getText();
                    System.out.println("      Cell " + (i + 1) + " (td[" + (i + 1) + "]): '" + cellText.substring(0, Math.min(50, cellText.length())) + "'");
                } catch (Exception e) {
                    System.out.println("      Cell " + (i + 1) + ": (error reading)");
                }
            }
        } catch (Exception e) {
            System.out.println("    ⚠️ Could not analyze row structure: " + e.getMessage());
        }
        
        // Click the appropriate convert button based on ConvertedTo value
        if (convertedTo != null && convertedTo.trim().equalsIgnoreCase("Project")) {
            System.out.println("  📍 Clicking Convert To Project button for row " + targetRowIndex);
            
            // Try multiple locators for Convert To Project button
            By[] convertButtonLocators = {
                // User-provided XPath (using root id and dynamic row index)
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[6]/div/button/svg"),
                // Try button element (parent of SVG)
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[6]/div/button"),
                // Try absolute XPath with button
                By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[6]/div/button"),
                // Try SVG element
                By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[6]/div/button/svg"),
                // Try path element (original)
                By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[6]/div/button/svg/path"),
                // Generic button in td[6]
                By.xpath("//table/tbody/tr[" + targetRowIndex + "]/td[6]//button"),
                // Generic button with text containing "Project"
                By.xpath("//table/tbody/tr[" + targetRowIndex + "]//button[contains(@aria-label,'Project') or contains(.,'Project')]")
            };
            
            WebElement button = null;
            By usedLocator = null;
            
            for (By locator : convertButtonLocators) {
                try {
                    button = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (button != null && button.isDisplayed()) {
                        usedLocator = locator;
                        System.out.println("    ✅ Found Convert To Project button using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (button == null) {
                // Debug: List all buttons in the row
                System.out.println("    🔍 Debug: Listing all buttons in row " + targetRowIndex);
                try {
                    List<WebElement> allButtons = driver.findElements(By.xpath("//table/tbody/tr[" + targetRowIndex + "]//button"));
                    System.out.println("      ℹ️ Found " + allButtons.size() + " button(s) in row");
                    for (int i = 0; i < allButtons.size(); i++) {
                        WebElement btn = allButtons.get(i);
                        System.out.println("        Button " + (i+1) + ": visible=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled() + ", text='" + btn.getText() + "'");
                    }
                } catch (Exception debugE) {
                    System.out.println("      ⚠️ Could not debug buttons: " + debugE.getMessage());
                }
                throw new Exception("Convert To Project button not found for row " + targetRowIndex);
            }
            
            // Verify button is enabled before clicking
            if (!button.isEnabled()) {
                System.out.println("  ⚠️ Convert To Project button is disabled");
                throw new Exception("Convert To Project button is disabled - cannot click");
            }
            
            System.out.println("  ✅ Convert To Project button is enabled and ready to click");
            System.out.println("  📋 Button location: " + button.getLocation());
            System.out.println("  📋 Button size: " + button.getSize());
            
            // Scroll and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waitForSeconds(1);
            
            // Verify button is still visible and enabled
            System.out.println("  🔍 Button state before click: visible=" + button.isDisplayed() + ", enabled=" + button.isEnabled());
            
            // Try Actions class click first (most reliable for Material-UI buttons)
            boolean clickSuccessful = false;
            try {
                System.out.println("  📍 Attempting Actions class click on Convert To Project button...");
                org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
                actions.moveToElement(button).click().perform();
                System.out.println("  ✅ Clicked Convert To Project button using Actions class");
                clickSuccessful = true;
            } catch (Exception e) {
                System.out.println("  ⚠️ Actions click failed: " + e.getMessage());
                // Try JavaScript click
                try {
                    System.out.println("  📍 Attempting JavaScript click on Convert To Project button...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                    System.out.println("  ✅ Clicked Convert To Project button using JavaScript");
                    clickSuccessful = true;
                } catch (Exception e2) {
                    System.out.println("  ⚠️ JavaScript click failed: " + e2.getMessage());
                    // Try normal click
                    try {
                        System.out.println("  📍 Attempting normal click on Convert To Project button...");
                        button.click();
                        System.out.println("  ✅ Clicked Convert To Project button using normal click");
                        clickSuccessful = true;
                    } catch (Exception e3) {
                        System.out.println("  ⚠️ Normal click failed: " + e3.getMessage());
                        // Try clicking parent button if we clicked on svg/path
                        try {
                            System.out.println("  📍 Attempting parent button click...");
                            WebElement parentButton = button.findElement(By.xpath("./ancestor::button"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                            System.out.println("  ✅ Clicked Convert To Project button via parent");
                            clickSuccessful = true;
                        } catch (Exception e4) {
                            throw new Exception("Failed to click Convert To Project button: " + e4.getMessage());
                        }
                    }
                }
            }
            
            if (!clickSuccessful) {
                throw new Exception("All click methods failed for Convert To Project button");
            }
            
            // Verify click registered - check if button state changed
            waitForSeconds(1);
            try {
                boolean stillVisible = button.isDisplayed();
                boolean stillEnabled = button.isEnabled();
                System.out.println("  🔍 Button state after click: visible=" + stillVisible + ", enabled=" + stillEnabled);
            } catch (Exception e) {
                System.out.println("  ℹ️ Button may have been removed/changed after click (this is normal)");
            }
            
            // Wait for response (alert or drawer)
            System.out.println("  ⏳ Waiting for response after clicking Convert To Project button...");
            waitForSeconds(5); // Increased wait time to allow page to respond
        } else if (convertedTo != null && convertedTo.trim().equalsIgnoreCase("Milestone")) {
            System.out.println("  📍 Clicking Convert To Milestone button for row " + targetRowIndex);
            
            // Try multiple locators for Convert To Milestone button
            By[] convertButtonLocators = {
                // User-provided XPath pattern (using root id and dynamic row index) - for Milestone use td[7]
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[7]/div/button/svg"),
                // Try button element (parent of SVG)
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[7]/div/button"),
                // Try absolute XPath with button
                By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[7]/div/button"),
                // Try SVG element
                By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + targetRowIndex + "]/td[7]/div/button/svg"),
                // Generic button in td[7]
                By.xpath("//table/tbody/tr[" + targetRowIndex + "]/td[7]//button"),
                // Generic button with text containing "Milestone"
                By.xpath("//table/tbody/tr[" + targetRowIndex + "]//button[contains(@aria-label,'Milestone') or contains(.,'Milestone')]")
            };
            
            WebElement button = null;
            By usedLocator = null;
            
            for (By locator : convertButtonLocators) {
                try {
                    button = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (button != null && button.isDisplayed()) {
                        usedLocator = locator;
                        System.out.println("    ✅ Found Convert To Milestone button using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (button == null) {
                // Debug: List all buttons in the row
                System.out.println("    🔍 Debug: Listing all buttons in row " + targetRowIndex);
                try {
                    List<WebElement> allButtons = driver.findElements(By.xpath("//table/tbody/tr[" + targetRowIndex + "]//button"));
                    System.out.println("      ℹ️ Found " + allButtons.size() + " button(s) in row");
                    for (int i = 0; i < allButtons.size(); i++) {
                        WebElement btn = allButtons.get(i);
                        System.out.println("        Button " + (i+1) + ": visible=" + btn.isDisplayed() + ", enabled=" + btn.isEnabled() + ", text='" + btn.getText() + "'");
                    }
                } catch (Exception debugE) {
                    System.out.println("      ⚠️ Could not debug buttons: " + debugE.getMessage());
                }
                throw new Exception("Convert To Milestone button not found for row " + targetRowIndex);
            }
            
            // Verify button is enabled before clicking
            if (!button.isEnabled()) {
                System.out.println("  ⚠️ Convert To Milestone button is disabled");
                throw new Exception("Convert To Milestone button is disabled - cannot click");
            }
            
            System.out.println("  ✅ Convert To Milestone button is enabled and ready to click");
            
            // Scroll and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waitForSeconds(1);
            
            // Try JavaScript click first
            boolean clickSuccessful = false;
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("  ✅ Clicked Convert To Milestone button using JavaScript");
                clickSuccessful = true;
            } catch (Exception e) {
                System.out.println("  ⚠️ JavaScript click failed: " + e.getMessage());
                // Try normal click
                try {
                    button.click();
                    System.out.println("  ✅ Clicked Convert To Milestone button using normal click");
                    clickSuccessful = true;
                } catch (Exception e2) {
                    System.out.println("  ⚠️ Normal click failed: " + e2.getMessage());
                    // Try clicking parent button if we clicked on svg
                    try {
                        WebElement parentButton = button.findElement(By.xpath("./ancestor::button"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                        System.out.println("  ✅ Clicked Convert To Milestone button via parent");
                        clickSuccessful = true;
                    } catch (Exception e3) {
                        throw new Exception("Failed to click Convert To Milestone button: " + e3.getMessage());
                    }
                }
            }
            
            if (!clickSuccessful) {
                throw new Exception("All click methods failed for Convert To Milestone button");
            }
            
            // Wait for response (alert or drawer)
            System.out.println("  ⏳ Waiting for response after clicking Convert To Milestone button...");
            waitForSeconds(3);
        } else {
            throw new Exception("Invalid ConvertedTo value: '" + convertedTo + "'. Expected 'Project' or 'Milestone'");
        }
        
        // Check for alert after clicking convert button
        System.out.println("  🔍 Checking for alert after clicking Convert To " + convertedTo + " button...");
        System.out.println("  📋 ConvertedTo value used for button click: '" + convertedTo + "'");
        System.out.println("  ✅ Button click completed - Now checking for response...");
        
        // Wait a bit more for any response
        waitForSeconds(3);
        
        boolean alertPresent = checkForDatabaseConnectionAlert();
        
        if (alertPresent) {
            System.out.println("  ⚠️ Alert detected: 'Connection to WhizibleSEM Database is not set or WhizibleSEM Database is not updated.'");
            System.out.println("  ✅ Validation: Alert is displayed - User cannot convert initiative (as expected)");
            System.out.println("  ✅ Conversion is blocked - Initiative NOT converted");
            
            // Close the alert
            closeAlertIfPresent();
            
            if (reportLogger != null) {
                reportLogger.pass("Alert detected after clicking Convert To " + convertedTo + " - Conversion blocked as expected");
            }
        } else {
            System.out.println("  ✅ No alert detected");
            System.out.println("  📋 Expected behavior: Since no alert appeared, drawer section SHOULD open");
            System.out.println("  🔍 Checking if drawer section opened...");
            
            // Check if drawer/modal opened
            boolean drawerOpened = checkIfDrawerOpened();
            if (drawerOpened) {
                System.out.println("  ✅ Drawer section opened successfully");
                System.out.println("  ✅ Validation PASSED: No alert - Drawer opened - Conversion can proceed");
                System.out.println("  ✅ Expected behavior confirmed: Drawer opened when no alert was present");
                
                if (reportLogger != null) {
                    reportLogger.pass("Drawer opened after clicking Convert To " + convertedTo + " - Conversion can proceed (as expected)");
                }
            } else {
                System.out.println("  ❌ Drawer section did NOT open");
                System.out.println("  ⚠️ ISSUE: No alert detected BUT drawer also did not open");
                System.out.println("  📋 Expected: If no alert, drawer SHOULD open");
                System.out.println("  📋 Actual: No alert AND no drawer");
                System.out.println("  🔍 Debug: Checking page state after click...");
                
                // Additional debugging
                try {
                    // Check for any visible modals/dialogs
                    List<WebElement> allModals = driver.findElements(By.xpath("//div[contains(@class,'modal') or contains(@class,'dialog') or contains(@class,'drawer') or contains(@class,'MuiModal') or contains(@class,'MuiDialog') or contains(@class,'MuiDrawer')]"));
                    System.out.println("    ℹ️ Found " + allModals.size() + " modal/dialog/drawer elements on page");
                    for (int i = 0; i < Math.min(allModals.size(), 5); i++) {
                        try {
                            WebElement modal = allModals.get(i);
                            System.out.println("      Modal " + (i+1) + ": visible=" + modal.isDisplayed() + ", class=" + modal.getAttribute("class"));
                        } catch (Exception e) {
                            // Skip
                        }
                    }
                    
                    // Check for any success/error messages
                    List<WebElement> messages = driver.findElements(By.xpath("//*[contains(text(),'success') or contains(text(),'error') or contains(text(),'converted') or contains(text(),'Conversion')]"));
                    System.out.println("    ℹ️ Found " + messages.size() + " message elements");
                    for (int i = 0; i < Math.min(messages.size(), 3); i++) {
                        try {
                            WebElement msg = messages.get(i);
                            if (msg.isDisplayed()) {
                                System.out.println("      Message " + (i+1) + ": '" + msg.getText().substring(0, Math.min(100, msg.getText().length())) + "'");
                            }
                        } catch (Exception e) {
                            // Skip
                        }
                    }
                    
                    // Check page title or URL changes
                    String currentUrl = driver.getCurrentUrl();
                    String pageTitle = driver.getTitle();
                    System.out.println("    ℹ️ Current URL: " + currentUrl);
                    System.out.println("    ℹ️ Page title: " + pageTitle);
                } catch (Exception e) {
                    System.out.println("    ⚠️ Error during debug: " + e.getMessage());
                }
                
                System.out.println("  ❌ VALIDATION FAILED: Button was clicked, no alert appeared, but drawer also did not open");
                System.out.println("  ⚠️ Expected behavior: If no alert → Drawer should open");
                System.out.println("  ⚠️ Actual behavior: No alert AND no drawer");
                System.out.println("  ℹ️ Possible reasons:");
                System.out.println("     - The click didn't register properly");
                System.out.println("     - The page needs more time to respond");
                System.out.println("     - The conversion requires additional conditions");
                System.out.println("     - The button action is handled differently");
                System.out.println("     - There might be a silent error preventing drawer from opening");
                
                // This should be treated as a failure since drawer should open when no alert
                throw new Exception("Validation failed: No alert detected but drawer section did NOT open. Expected: If no alert, drawer should open. Actual: No alert AND no drawer.");
            }
        }
    }
    
    /**
     * Check for database connection alert
     * Added by Gayatri.Kasav - For TC_007
     * @return true if alert is present, false otherwise
     */
    public boolean checkForDatabaseConnectionAlert() throws Exception {
        String expectedAlertText = "Connection to WhizibleSEM Database is not set or WhizibleSEM Database is not updated.";
        String shortAlertText = "WhizibleSEM Database";
        
        try {
            // Wait a bit for alert to appear
            waitForSeconds(2);
            
            System.out.println("    🔍 Searching for alert with text: '" + expectedAlertText + "'");
            
            // Try multiple ways to find the alert
            // Method 1: Check for alert dialog with full text
            try {
                WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'" + expectedAlertText + "')]")));
                if (alert != null && alert.isDisplayed()) {
                    System.out.println("    ✅ Alert found using full text search");
                    System.out.println("    📋 Alert text: '" + alert.getText() + "'");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("    ℹ️ Full text search did not find alert");
            }
            
            // Method 2: Check for alert with partial text
            try {
                WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'WhizibleSEM Database')]")));
                if (alert != null && alert.isDisplayed()) {
                    String alertText = alert.getText();
                    System.out.println("    ✅ Alert found using partial text search");
                    System.out.println("    📋 Alert text: '" + alertText + "'");
                    if (alertText.contains("Connection to WhizibleSEM Database") || alertText.contains("not set") || alertText.contains("not updated")) {
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.println("    ℹ️ Partial text search did not find alert");
            }
            
            // Method 3: Check page source for alert text
            String pageSource = driver.getPageSource();
            if (pageSource.contains(expectedAlertText)) {
                System.out.println("    ✅ Alert text found in page source (full text)");
                return true;
            }
            if (pageSource.contains(shortAlertText) && (pageSource.contains("not set") || pageSource.contains("not updated"))) {
                System.out.println("    ✅ Alert text found in page source (partial match)");
                return true;
            }
            
            // Method 4: Check for common alert/dialog elements
            By[] alertLocators = {
                By.xpath("//div[contains(@class,'alert')]"),
                By.xpath("//div[contains(@class,'MuiAlert-root')]"),
                By.xpath("//div[contains(@class,'MuiSnackbar-root')]"),
                By.xpath("//div[contains(@class,'dialog')]"),
                By.xpath("//div[contains(@class,'MuiDialog-root')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//*[contains(text(),'WhizibleSEM')]"),
                By.xpath("//*[contains(text(),'Database')]")
            };
            
            for (By locator : alertLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element != null && element.isDisplayed()) {
                            String elementText = element.getText();
                            if (elementText.contains("WhizibleSEM Database") || 
                                elementText.contains("not set") || 
                                elementText.contains("not updated") ||
                                elementText.contains(expectedAlertText)) {
                                System.out.println("    ✅ Alert found using locator: " + locator);
                                System.out.println("    📋 Alert text: '" + elementText + "'");
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            System.out.println("    ℹ️ No alert found after checking all methods");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking for alert: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close alert if present
     * Added by Gayatri.Kasav - For TC_007
     */
    public void closeAlertIfPresent() throws Exception {
        try {
            // Try to find and click OK/Close button in alert
            By[] closeButtonLocators = {
                By.xpath("//button[contains(text(),'OK') or contains(text(),'Close') or contains(text(),'×')]"),
                By.xpath("//div[contains(@class,'alert')]//button"),
                By.xpath("//div[contains(@class,'MuiAlert-root')]//button"),
                By.xpath("//div[contains(@class,'dialog')]//button[contains(text(),'OK')]")
            };
            
            for (By locator : closeButtonLocators) {
                try {
                    WebElement closeBtn = driver.findElement(locator);
                    if (closeBtn != null && closeBtn.isDisplayed()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
                        System.out.println("    ✅ Closed alert");
                        waitForSeconds(1);
                        return;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // If no close button found, try ESC key
            driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            waitForSeconds(1);
            System.out.println("    ✅ Closed alert using ESC key");
        } catch (Exception e) {
            System.out.println("    ⚠️ Could not close alert: " + e.getMessage());
        }
    }
    
    /**
     * Check if drawer/modal opened after clicking convert button
     * Added by Gayatri.Kasav - For TC_007
     * @return true if drawer is open, false otherwise
     */
    public boolean checkIfDrawerOpened() throws Exception {
        try {
            System.out.println("    🔍 Searching for drawer/modal elements...");
            System.out.println("    ⏳ Waiting for drawer to appear (up to 10 seconds)...");
            
            // Wait and check multiple times for drawer to appear
            for (int attempt = 0; attempt < 5; attempt++) {
                waitForSeconds(2);
                System.out.println("      Attempt " + (attempt + 1) + "/5: Checking for drawer...");
                
                // Check for drawer/modal elements
                By[] drawerLocators = {
                    By.xpath("//div[contains(@class,'MuiDrawer-root')]"),
                    By.xpath("//div[contains(@class,'MuiDrawer-paper')]"),
                    By.xpath("//div[contains(@class,'MuiModal-root')]"),
                    By.xpath("//div[contains(@class,'MuiDialog-root')]"),
                    By.xpath("//div[contains(@class,'MuiDialog-container')]"),
                    By.xpath("//div[contains(@class,'MuiDialog-paper')]"),
                    By.xpath("//div[contains(@class,'drawer')]"),
                    By.xpath("//div[contains(@class,'modal')]"),
                    By.xpath("//div[@role='dialog']"),
                    By.xpath("//div[@role='presentation']"),
                    By.xpath("//div[contains(@class,'MuiPaper-root') and contains(@class,'MuiDrawer-paper')]"),
                    By.xpath("//div[contains(@class,'MuiPaper-root') and contains(@class,'MuiDialog-paper')]")
                };
                
                for (By locator : drawerLocators) {
                    try {
                        List<WebElement> drawers = driver.findElements(locator);
                        for (WebElement drawer : drawers) {
                            if (drawer != null) {
                                try {
                                    if (drawer.isDisplayed()) {
                                        System.out.println("    ✅ Drawer found using locator: " + locator);
                                        System.out.println("    📋 Drawer class: " + drawer.getAttribute("class"));
                                        System.out.println("    📋 Drawer text preview: " + drawer.getText().substring(0, Math.min(100, drawer.getText().length())));
                                        return true;
                                    }
                                } catch (Exception e) {
                                    // Element might be stale, continue
                                    continue;
                                }
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                // Check for backdrop (indicates modal/drawer is open)
                try {
                    List<WebElement> backdrops = driver.findElements(By.xpath("//div[contains(@class,'MuiBackdrop-root') or contains(@class,'backdrop') or contains(@class,'MuiModal-backdrop')]"));
                    for (WebElement backdrop : backdrops) {
                        if (backdrop != null) {
                            try {
                                if (backdrop.isDisplayed()) {
                                    System.out.println("    ✅ Backdrop found - Drawer is open");
                                    System.out.println("    📋 Backdrop class: " + backdrop.getAttribute("class"));
                                    return true;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
                
                // Check for any visible overlay
                try {
                    List<WebElement> overlays = driver.findElements(By.xpath("//div[contains(@class,'overlay') or contains(@class,'MuiOverlay')]"));
                    for (WebElement overlay : overlays) {
                        if (overlay != null) {
                            try {
                                if (overlay.isDisplayed()) {
                                    System.out.println("    ✅ Overlay found - Drawer might be open");
                                    return true;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
                
                // Check page source for drawer-related text
                try {
                    String pageSource = driver.getPageSource();
                    if (pageSource.contains("MuiDrawer") || pageSource.contains("MuiDialog") || pageSource.contains("MuiModal")) {
                        // Check if any drawer elements are actually visible
                        List<WebElement> allDrawers = driver.findElements(By.xpath("//*[contains(@class,'MuiDrawer') or contains(@class,'MuiDialog') or contains(@class,'MuiModal')]"));
                        for (WebElement drawer : allDrawers) {
                            try {
                                if (drawer != null && drawer.isDisplayed()) {
                                    System.out.println("    ✅ Drawer found via page source check");
                                    return true;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            System.out.println("    ℹ️ No drawer found after checking all methods and waiting");
            System.out.println("    🔍 Final check: Listing all potential drawer elements...");
            try {
                List<WebElement> allPotentialDrawers = driver.findElements(By.xpath("//div[contains(@class,'Mui')]"));
                int visibleCount = 0;
                for (WebElement elem : allPotentialDrawers) {
                    try {
                        if (elem != null && elem.isDisplayed()) {
                            String className = elem.getAttribute("class");
                            if (className != null && (className.contains("Drawer") || className.contains("Dialog") || className.contains("Modal"))) {
                                visibleCount++;
                                System.out.println("      Found potential drawer: " + className);
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                System.out.println("    ℹ️ Total visible MUI drawer/dialog/modal elements: " + visibleCount);
            } catch (Exception e) {
                System.out.println("    ⚠️ Could not list drawer elements: " + e.getMessage());
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking for drawer: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isInitiativeDisplayedByCode(String initiativeCode) {

        By gridRows = By.xpath("//table//tbody//tr");

        List<WebElement> rows = driver.findElements(gridRows);

        if (rows.isEmpty()) {
            System.out.println("❌ No rows found in search result grid");
            return false;
        }

        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText.contains(initiativeCode)) {
                System.out.println("✅ Initiative found with code: " + initiativeCode);
                return true;
            }
        }

        System.out.println("❌ Initiative NOT found with code: " + initiativeCode);
        return false;
    }

    
    public void finalsearch() {
        click(ConvertedInitiativePageLocators.search, "search");
    }
    
    public boolean validateInitiativeRowUsingAutomationKeys(
            String expCode,
            String expTitle,
            String expNature,
            String expBG,
            String expOU,
            String expConvertedTo) {

        // All rows
        By rowsLocator = By.xpath("//div[@data-automationid='DetailsRow']");
        List<WebElement> rows = driver.findElements(rowsLocator);

        if (rows.isEmpty()) {
            System.out.println("❌ No rows found in initiative grid");
            return false;
        }

        for (WebElement row : rows) {

            String code = row.findElement(
                    By.xpath(".//div[@data-automation-key='code']//div"))
                    .getText().trim();

            if (!code.equals(expCode)) {
                continue; // check next row
            }

            System.out.println("🔎 Validating row for Initiative Code: " + expCode);

            String title = row.findElement(
                    By.xpath(".//div[@data-automation-key='title']//span"))
                    .getText().trim();

            String nature = row.findElement(
                    By.xpath(".//div[@data-automation-key='nature']//div"))
                    .getText().trim();

            String bg = row.findElement(
                    By.xpath(".//div[@data-automation-key='businessgroup']//div"))
                    .getText().trim();

            String ou = row.findElement(
                    By.xpath(".//div[@data-automation-key='group']//div"))
                    .getText().trim();

            String convertedTo = row.findElement(
                    By.xpath(".//div[@data-automation-key='convertedTo']//span"))
                    .getText().trim();

            // ✅ ASSERTIONS
            Assert.assertEquals(title, expTitle, "❌ Initiative Title mismatch");
            Assert.assertEquals(nature, expNature, "❌ Nature of Initiative mismatch");
            Assert.assertEquals(bg, expBG, "❌ Business Group mismatch");
            Assert.assertEquals(ou, expOU, "❌ Organization Unit mismatch");
            Assert.assertEquals(convertedTo, expConvertedTo, "❌ Converted To mismatch");

            System.out.println("✅ Grid validation PASSED for code: " + expCode);
            return true;
        }

        System.out.println("❌ Initiative code not found in grid: " + expCode);
        return false;
    }

    
}
