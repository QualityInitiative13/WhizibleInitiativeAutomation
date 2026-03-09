package Pages;

import Actions.ActionEngine;
import Locators.ManComPrioritizationPageLocators;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Man-Com Prioritization page.
 *
 * Structure is similar to WithdrawnInitiativePage, but dedicated to
 * Man-Com Prioritization. XPaths and flows will be refined as per UI.
 * @author Gayatri.k
 */
public class ManComPrioritizationPage extends ActionEngine {

    private final WebDriver driver;
    private final ExtentTest reportLogger;

    public ManComPrioritizationPage(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Wait for element to become stale (useful after navigation/refresh)
     */
    private void waitForElementStaleness(WebElement element, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.stalenessOf(element));
        } catch (Exception e) {
            // Element may not become stale, continue
        }
    }

    /**
     * Wait for element value to update (useful after entering text)
     */
    private void waitForElementValueUpdate(WebElement element, String expectedValue, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.textToBePresentInElementValue(element, expectedValue));
        } catch (Exception e) {
            // Value may not update as expected, continue
        }
    }

    /**
     * Wait for element to be in viewport after scrolling
     */
    private void waitForElementInViewport(WebElement element, int timeoutSeconds) {
        try {
            WebDriverWait viewportWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            viewportWait.until(webDriver -> {
                try {
                    return (Boolean) ((JavascriptExecutor) webDriver).executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                        "return (rect.top >= 0 && rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                        element);
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Element may not be in viewport, continue
        }
    }

    // ==================== NAVIGATION ====================

    /**
     * Ensure left menu is expanded before navigation (Material-UI fix) // Gayatri.k
     */
    private void ensureLeftMenuExpanded() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By[] menuToggleLocators = {
                By.xpath("//button[@aria-label='open drawer' or @aria-label='close drawer']"),
                By.xpath("//button[contains(@aria-label,'drawer')]"),
                By.xpath("//button[@data-testid='menu-toggle']"),
                By.xpath("//*[@role='button' and contains(@class,'menu')]")
            };
            
            for (By locator : menuToggleLocators) {
                try {
                    WebElement menuToggle = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (menuToggle != null && menuToggle.isDisplayed()) {
                        String ariaLabel = menuToggle.getAttribute("aria-label");
                        if (ariaLabel != null && ariaLabel.contains("close")) {
                            // Menu is open, no action needed
                            System.out.println("✅ Left menu is already expanded");
                            return;
                        } else if (ariaLabel != null && ariaLabel.contains("open")) {
                            // Menu is closed, click to open
                            menuToggle.click();
                            // Wait for menu to open by checking for close drawer button
                            try {
                                wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath("//button[@aria-label='close drawer']")));
                            } catch (Exception e) {
                                // Menu may already be open, continue
                            }
                            System.out.println("✅ Expanded left menu");
                            return;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            System.out.println("ℹ️ Menu toggle not found or menu already expanded");
        } catch (Exception e) {
            System.out.println("ℹ️ Could not ensure menu expanded: " + e.getMessage());
        }
    }

    /**
     * Navigate to Man-Com Prioritization page.
     * Steps: 1. Click on Initiative Tracking, 2. Click on Man-Com Prioritization
     * @author Gayatri.k
     */
    public void navigateToManComPrioritizationPage() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO MAN-COM PRIORITIZATION - START");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Create wait instance to reuse throughout the method
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 1: Close any open dialogs/modals before navigation // Gayatri.k
        try {
            // Check if search dialog is open and close it
            try {
                WebElement searchInput = driver.findElement(ManComPrioritizationPageLocators.searchInput);
                if (searchInput != null && searchInput.isDisplayed()) {
                    System.out.println("ℹ️ Search dialog is open, closing it...");
                    driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                    // Wait for search dialog to close
                    try {
                        wait.until(ExpectedConditions.invisibilityOf(searchInput));
                    } catch (Exception e) {
                        // Dialog may already be closed, continue
                    }
                }
            } catch (Exception e) {
                // Search dialog not open, continue
            }
            
            // Press ESC to dismiss any modals/overlays
            WebElement body = driver.findElement(By.tagName("body"));
            body.sendKeys(Keys.ESCAPE);
            // Wait briefly for any modals to close
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@role='dialog' and @aria-modal='true']"))));
            } catch (Exception e) {
                // No modal to close, continue
            }
        } catch (Exception e) {
            System.out.println("ℹ️ No dialogs to close: " + e.getMessage());
        }

        // Step 2: Ensure left menu is expanded (Material-UI fix) // Gayatri.k
        ensureLeftMenuExpanded();
        // Wait for menu to be ready
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@aria-label='close drawer'] | //nav")));
        } catch (Exception e) {
            // Menu may already be ready, continue
        }

        // Step 3: Click on Initiative Tracking node // Gayatri.k
        System.out.println("📍 Step 1: Clicking on Initiative Tracking...");
        try {
            WebElement trackingNode = wait.until(
                    ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.initiativeTrackingNode));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", trackingNode);
            // Wait for element to be in viewport
            waitForElementInViewport(trackingNode, 2);
            trackingNode.click();
            // Wait for submenu to appear after clicking Initiative Tracking
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    ManComPrioritizationPageLocators.manComPrioritizationNodeLocators[0]));
            } catch (Exception e) {
                // Submenu may appear differently, continue
            }
            System.out.println("✅ Clicked on Initiative Tracking");
        } catch (Exception e) {
            throw new Exception("Could not click on Initiative Tracking: " + e.getMessage());
        }

        // Step 4: Click on Man-Com Prioritization node // Gayatri.k
        System.out.println("📍 Step 2: Clicking on Man-Com Prioritization...");
        
        WebElement prioritizationNode = null;
        Actions actions = new Actions(driver);
        
        for (By locator : ManComPrioritizationPageLocators.manComPrioritizationNodeLocators) {
            try {
                // Use visibilityOfElementLocated instead of elementToBeClickable for better reliability
                prioritizationNode = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (prioritizationNode != null && prioritizationNode.isDisplayed()) {
                    System.out.println("✅ Found Man-Com Prioritization element using: " + locator);
                    // Scroll into view
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", prioritizationNode);
                    // Wait for element to be in viewport
                    waitForElementInViewport(prioritizationNode, 2);
                    
                    // Use Actions class for Material-UI clicks (more reliable)
                    try {
                        actions.moveToElement(prioritizationNode).click().perform();
                        System.out.println("✅ Clicked on Man-Com Prioritization using Actions class");
                        break;
                    } catch (Exception e) {
                        System.out.println("⚠️ Actions click failed, trying normal click: " + e.getMessage());
                        prioritizationNode.click();
                        System.out.println("✅ Clicked on Man-Com Prioritization using normal click");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Locator failed: " + locator + " - " + e.getMessage());
                continue;
            }
        }
        
        if (prioritizationNode == null) {
            throw new Exception("Could not find or click Man-Com Prioritization menu item with any locator");
        }

        // Wait for page to load list view - wait for table to appear
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                ManComPrioritizationPageLocators.prioritizationTable));
        } catch (Exception e) {
            // Table may load differently, continue
        }
        System.out.println("✅ ✅ ✅ Navigated to Man-Com Prioritization successfully! ✅ ✅ ✅");
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO MAN-COM PRIORITIZATION - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    // ==================== BASIC VALIDATIONS ====================

    /**
     * Verify Man-Com Prioritization page header or list view is visible.
     * @author Gayatri.k
     */
    public void verifyPrioritizationHeader() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(ManComPrioritizationPageLocators.manComPrioritizationHeader));
            System.out.println("✅ Man-Com Prioritization header found: '" + header.getText() + "'");
            if (reportLogger != null) {
                reportLogger.pass("Man-Com Prioritization page header verified: " + header.getText());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Exact header not found, checking for list view...");
            // Fallback: check if table/list is visible
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(ManComPrioritizationPageLocators.prioritizationTable));
                System.out.println("✅ Man-Com Prioritization list view is visible");
                if (reportLogger != null) {
                    reportLogger.pass("Man-Com Prioritization list view verified");
                }
            } catch (Exception e2) {
                throw new Exception("Could not verify Man-Com Prioritization page header or list view");
            }
        }
    }

    /**
     * Check if there are any records in the Man-Com Prioritization list.
     * @author Gayatri.k
     */
    public boolean hasPrioritizationRecords() throws Exception {
        try {
            List<WebElement> rows = driver.findElements(ManComPrioritizationPageLocators.prioritizationRows);
            boolean hasRecords = rows != null && !rows.isEmpty();
            System.out.println("📊 Man-Com Prioritization - found " + (rows != null ? rows.size() : 0) + " rows");
            return hasRecords;
        } catch (Exception e) {
            System.out.println("⚠️ Error checking for records: " + e.getMessage());
            return false;
        }
    }

    // ==================== SEARCH FUNCTIONALITY ====================

    /**
     * Click on search icon.
     * @author Gayatri.k
     */
    public void clickSearchIcon() throws Exception {
        System.out.println("📍 Step 1: Clicking search icon...");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchIcon = null;
        By usedLocator = null;

        // Try to find and click the search icon
        try {
            searchIcon = wait.until(ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.searchIcon));
            usedLocator = ManComPrioritizationPageLocators.searchIcon;
            System.out.println("✅ Found search icon using primary locator.");
        } catch (Exception e) {
            System.out.println("⚠️ Primary search icon locator failed, trying fallbacks: " + e.getMessage());
            // Fallback strategies for search icon if primary fails
            By[] fallbackLocators = {
                By.xpath("//img[contains(@aria-label,'Search')]"),
                By.xpath("(//img[@aria-label='Search'])[1]"),
                By.xpath("//div[contains(@class,'search')]//img"),
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img"),
                By.xpath("//div[contains(@class,'MuiBox-root')]//img[contains(@alt,'Search') or contains(@aria-label,'Search')]")
            };
            for (By locator : fallbackLocators) {
                try {
                    searchIcon = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    usedLocator = locator;
                    System.out.println("✅ Found search icon using fallback locator: " + locator);
                    break;
                } catch (Exception ex) {
                    System.out.println("⚠️ Fallback locator failed: " + locator);
                }
            }
        }

        if (searchIcon == null) {
            throw new Exception("Could not find Search icon with any locator.");
        }

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchIcon);
        waitForElementInViewport(searchIcon, 2);
        
        // Try normal click first, then JS click as fallback
        try {
            searchIcon.click();
            System.out.println("✅ Clicked on search icon (normal click)");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
            System.out.println("✅ Clicked on search icon (JavaScript click)");
        }
        
        // Wait for search dialog to open - wait for search input field to appear
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                ManComPrioritizationPageLocators.initiativeCodeInput));
        } catch (Exception e) {
            // Dialog may open differently, continue
        }
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on search icon");
        }
    }

    /**
     * Enter initiative code in the search input field.
     * @author Gayatri.k
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null or empty. Cannot search.");
        }

        String code = initiativeCode.trim();
        System.out.println("📍 Step 2: Entering initiative code: '" + code + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // First, verify the search dialog is open by checking for the input field's visibility
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ManComPrioritizationPageLocators.initiativeCodeInput));
            System.out.println("✅ Search input field is visible, proceeding.");
        } catch (Exception e) {
            System.out.println("⚠️ Search input field not visible within timeout. Retrying click on search icon.");
            clickSearchIcon();
            // Wait for search input field to appear after retry
            wait.until(ExpectedConditions.visibilityOfElementLocated(ManComPrioritizationPageLocators.initiativeCodeInput));
            System.out.println("✅ Search input field is now visible after retry.");
        }

        // Wait for input field to be clickable
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.initiativeCodeInput));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForElementInViewport(input, 2);
        
        // Clear field using multiple methods
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        // Wait for field to be cleared
        try {
            wait.until(webDriver -> input.getAttribute("value") == null || input.getAttribute("value").isEmpty());
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        // Enter the code
        input.sendKeys(code);
        // Wait for value to be entered
        waitForElementValueUpdate(input, code, 2);
        
        // Verify the code was entered
        String enteredValue = input.getAttribute("value");
        System.out.println("✅ Entered initiative code: '" + code + "' (field value: '" + enteredValue + "')");
        
        if (!enteredValue.contains(code)) {
            System.out.println("⚠️ Warning: Entered value doesn't match expected code. Retrying...");
            input.clear();
            input.sendKeys(code);
            // Wait for value to be entered
            waitForElementValueUpdate(input, code, 2);
            enteredValue = input.getAttribute("value");
            if (!enteredValue.contains(code)) {
                throw new Exception("Failed to enter initiative code '" + code + "' correctly. Actual: '" + enteredValue + "'");
            }
        }
        
        if (reportLogger != null) {
            reportLogger.info("Entered Initiative Code in search field: " + code);
        }
    }

    /**
     * Click on search button.
     * @author Gayatri.k
     */
    public void clickSearchButton() throws Exception {
        System.out.println("📍 Step 3: Clicking search button...");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.searchButton));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchButton);
        waitForElementInViewport(searchButton, 2);
        
        // Try normal click first, then JS click as fallback
        try {
            searchButton.click();
            System.out.println("✅ Clicked on search button (normal click)");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
            System.out.println("✅ Clicked on search button (JavaScript click)");
        }
        
        // Wait for search results to load - wait for table to appear or update
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                ManComPrioritizationPageLocators.prioritizationTable));
        } catch (Exception e) {
            // Table may already be present, continue
        }
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on search button");
        }
    }

    /**
     * Click on clear search button // Gayatri.k
     */
    public void clickClearSearchButton() throws Exception {
        System.out.println("📍 Clicking clear search button...");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement clearButton = wait.until(ExpectedConditions.elementToBeClickable(
                ManComPrioritizationPageLocators.clearSearchButton));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", clearButton);
        waitForElementInViewport(clearButton, 2);
        
        try {
            clearButton.click();
            System.out.println("✅ Clicked on clear search button (normal click)");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clearButton);
            System.out.println("✅ Clicked on clear search button (JavaScript click)");
        }
        
        // Wait for search fields to be cleared
        try {
            wait.until(webDriver -> {
                try {
                    WebElement input = webDriver.findElement(ManComPrioritizationPageLocators.initiativeCodeInput);
                    String value = input.getAttribute("value");
                    return value == null || value.isEmpty();
                } catch (Exception e) {
                    return true; // Field may not exist, consider cleared
                }
            });
        } catch (Exception e) {
            // Fields may already be clear, continue
        }
        if (reportLogger != null) {
            reportLogger.info("Clicked on clear search button");
        }
    }

    /**
     * Verify matching record is displayed after search.
     * @author Gayatri.k
     */
    public boolean verifyMatchingRecord(String initiativeCode) throws Exception {
        System.out.println("📍 Step 4: Verifying matching record for code: '" + initiativeCode + "'");
        
        String code = initiativeCode.trim();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for table to appear after search
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(ManComPrioritizationPageLocators.prioritizationTable));
        } catch (Exception e) {
            System.out.println("⚠️ Table not found after search: " + e.getMessage());
        }
        
        // Check rows for the initiative code
        List<WebElement> rows = driver.findElements(ManComPrioritizationPageLocators.prioritizationRows);
        System.out.println("📊 Checking " + rows.size() + " row(s) for initiative code '" + code + "'");
        
        for (WebElement row : rows) {
            try {
                String rowText = row.getText();
                if (rowText != null && rowText.contains(code)) {
                    System.out.println("✅ Matching record found! Initiative code '" + code + "' is displayed in the search results");
                    System.out.println("   Row text (first 200 chars): " + (rowText.length() > 200 ? rowText.substring(0, 200) : rowText));
                    if (reportLogger != null) {
                        reportLogger.pass("Matching record found for initiative code: " + code);
                    }
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        System.out.println("❌ Matching record not found for initiative code: '" + code + "'");
        if (reportLogger != null) {
            reportLogger.fail("Matching record not found for initiative code: " + code);
        }
        return false;
    }

    /**
     * Get row index (1-based) for initiative code in table // Gayatri.k
     */
    public int getRowIndexByInitiativeCode(String initiativeCode) throws Exception {
        String code = initiativeCode.trim();
        List<WebElement> rows = driver.findElements(ManComPrioritizationPageLocators.prioritizationRows);
        
        for (int i = 0; i < rows.size(); i++) {
            try {
                String rowText = rows.get(i).getText();
                if (rowText != null && rowText.contains(code)) {
                    System.out.println("✅ Found initiative code '" + code + "' at row index: " + (i + 1));
                    return i + 1; // Return 1-based index
                }
            } catch (Exception e) {
                continue;
            }
        }
        throw new Exception("Initiative code '" + code + "' not found in table rows");
    }

    /**
     * Enter Business Benefits in search input field // Gayatri.k
     */
    public void enterBusinessBenefits(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Business Benefits value is empty, skipping");
            return;
        }
        enterSearchInputField(ManComPrioritizationPageLocators.businessBenefitsInput, value, "Business Benefits");
    }

    /**
     * Enter Department Rating in search input field // Gayatri.k
     */
    public void enterDepartmentRating(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Department Rating value is empty, skipping");
            return;
        }
        enterSearchInputField(ManComPrioritizationPageLocators.departmentRatingInput, value, "Department Rating");
    }

    /**
     * Enter Initiative Title in search input field // Gayatri.k
     */
    public void enterInitiativeTitle(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Initiative Title value is empty, skipping");
            return;
        }
        enterSearchInputField(ManComPrioritizationPageLocators.initiativeTitleInput, value, "Initiative Title");
    }

    /**
     * Enter Man-Com Priority in search input field // Gayatri.k
     */
    public void enterManComPriority(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Man-Com Priority value is empty, skipping");
            return;
        }
        enterSearchInputField(ManComPrioritizationPageLocators.manComPriorityInput, value, "Man-Com Priority");
    }

    /**
     * Helper method to enter value in search input fields // Gayatri.k
     */
    private void enterSearchInputField(By locator, String value, String fieldName) throws Exception {
        String trimmedValue = value.trim();
        System.out.println("📍 Entering " + fieldName + ": '" + trimmedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForElementInViewport(input, 2);
        
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        // Wait for field to be cleared
        try {
            wait.until(webDriver -> input.getAttribute("value") == null || input.getAttribute("value").isEmpty());
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        input.sendKeys(trimmedValue);
        // Wait for value to be entered
        waitForElementValueUpdate(input, trimmedValue, 2);
        
        String enteredValue = input.getAttribute("value");
        System.out.println("✅ Entered " + fieldName + ": '" + trimmedValue + "' (field value: '" + enteredValue + "')");
        
        if (reportLogger != null) {
            reportLogger.info("Entered " + fieldName + ": " + trimmedValue);
        }
    }

    /**
     * Select Organization Unit from dropdown // Gayatri.k
     */
    public void selectOrganizationUnit(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Organization Unit value is empty, skipping selection");
            return;
        }

        String selectedValue = value.trim();
        System.out.println("📍 Selecting Organization Unit: '" + selectedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.organizationUnitDropdown));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        waitForElementInViewport(dropdown, 2);
        
        try {
            dropdown.click();
            System.out.println("✅ Clicked on Organization Unit dropdown");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Clicked on Organization Unit dropdown (JavaScript)");
        }
        
        // Wait for dropdown options to appear
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//li[@role='option'] | //div[@role='option']")));
        } catch (Exception e) {
            // Options may appear differently, continue
        }
        
        boolean optionSelected = false;
        Actions actions = new Actions(driver);
        
        try {
            By optionLocator = By.xpath("//li[normalize-space(text())='" + selectedValue + "'] | //div[@role='option' and normalize-space(text())='" + selectedValue + "'] | //span[normalize-space(text())='" + selectedValue + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            actions.moveToElement(option).click().perform();
            System.out.println("✅ Selected Organization Unit: '" + selectedValue + "' (exact match)");
            optionSelected = true;
        } catch (Exception e) {
            System.out.println("⚠️ Exact match failed, trying contains match: " + e.getMessage());
        }
        
        if (!optionSelected) {
            try {
                By optionLocator = By.xpath("//li[contains(text(),'" + selectedValue + "')] | //div[@role='option' and contains(text(),'" + selectedValue + "')] | //span[contains(text(),'" + selectedValue + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                actions.moveToElement(option).click().perform();
                System.out.println("✅ Selected Organization Unit: '" + selectedValue + "' (contains match)");
                optionSelected = true;
            } catch (Exception e) {
                throw new Exception("Failed to select Organization Unit '" + selectedValue + "': " + e.getMessage());
            }
        }
        
        // Wait for dropdown to close after selection
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//li[@role='option'] | //div[@role='option']")));
        } catch (Exception e) {
            // Dropdown may close differently, continue
        }
        if (reportLogger != null) {
            reportLogger.info("Selected Organization Unit: " + selectedValue);
        }
    }

    /**
     * Select Size from dropdown // Gayatri.k
     */
    public void selectSize(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Size value is empty, skipping selection");
            return;
        }

        String selectedValue = value.trim();
        System.out.println("📍 Selecting Size: '" + selectedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(ManComPrioritizationPageLocators.sizeDropdown));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        waitForElementInViewport(dropdown, 2);
        
        try {
            dropdown.click();
            System.out.println("✅ Clicked on Size dropdown");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Clicked on Size dropdown (JavaScript)");
        }
        
        // Wait for dropdown options to appear
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//li[@role='option'] | //div[@role='option']")));
        } catch (Exception e) {
            // Options may appear differently, continue
        }
        
        boolean optionSelected = false;
        Actions actions = new Actions(driver);
        
        try {
            By optionLocator = By.xpath("//li[normalize-space(text())='" + selectedValue + "'] | //div[@role='option' and normalize-space(text())='" + selectedValue + "'] | //span[normalize-space(text())='" + selectedValue + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            actions.moveToElement(option).click().perform();
            System.out.println("✅ Selected Size: '" + selectedValue + "' (exact match)");
            optionSelected = true;
        } catch (Exception e) {
            System.out.println("⚠️ Exact match failed, trying contains match: " + e.getMessage());
        }
        
        if (!optionSelected) {
            try {
                By optionLocator = By.xpath("//li[contains(text(),'" + selectedValue + "')] | //div[@role='option' and contains(text(),'" + selectedValue + "')] | //span[contains(text(),'" + selectedValue + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                actions.moveToElement(option).click().perform();
                System.out.println("✅ Selected Size: '" + selectedValue + "' (contains match)");
                optionSelected = true;
            } catch (Exception e) {
                throw new Exception("Failed to select Size '" + selectedValue + "': " + e.getMessage());
            }
        }
        
        // Wait for dropdown to close after selection
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//li[@role='option'] | //div[@role='option']")));
        } catch (Exception e) {
            // Dropdown may close differently, continue
        }
        if (reportLogger != null) {
            reportLogger.info("Selected Size: " + selectedValue);
        }
    }

    // ==================== TABLE ROW EDIT FUNCTIONALITY ====================

    /**
     * Check if a field is enabled or disabled // Gayatri.k
     */
    private boolean isFieldEnabled(By locator) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return field != null && field.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear Department Rating field in table row (leave blank) // Gayatri.k
     */
    public void clearDepartmentRatingInTable(int rowIndex) throws Exception {
        System.out.println("📍 Clearing Department Rating in row " + rowIndex + " (leaving blank)...");
        
        By locator = ManComPrioritizationPageLocators.departmentRatingTableInput(rowIndex);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForElementInViewport(input, 2);
        
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        // Wait for field to be cleared
        try {
            WebDriverWait clearWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            clearWait.until(driver -> input.getAttribute("value") == null || input.getAttribute("value").isEmpty());
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        System.out.println("✅ Cleared Department Rating field (now blank)");
        if (reportLogger != null) {
            reportLogger.info("Cleared Department Rating field");
        }
    }

    /**
     * Enter Department Rating in table row // Gayatri.k
     */
    public void enterDepartmentRatingInTable(String value, int rowIndex) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            throw new Exception("Department Rating value is empty");
        }
        String trimmedValue = value.trim();
        System.out.println("📍 Entering Department Rating in row " + rowIndex + ": '" + trimmedValue + "'");
        
        // Use the exact XPath pattern provided by user: /html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[X]/td[6]/div/input
        By locator = ManComPrioritizationPageLocators.departmentRatingTableInput(rowIndex);
        
        // Wait a bit to allow DOM to stabilize
        waitForSeconds(1);
        
        // Try multiple strategies to find and interact with the element
        WebElement input = null;
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Strategy 1: Try presence first (fastest check)
        try {
            System.out.println("  🔍 Strategy 1: Checking if element exists...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            System.out.println("  ✅ Element found, scrolling into view...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
            waitForSeconds(1);
        } catch (Exception e1) {
            System.out.println("  ⚠️ Strategy 1 failed: " + e1.getMessage());
            // Strategy 2: Try visibility
            try {
                System.out.println("  🔍 Strategy 2: Checking if element is visible...");
                input = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                System.out.println("  ✅ Element is visible, scrolling into view...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
                waitForSeconds(1);
            } catch (Exception e2) {
                System.out.println("  ⚠️ Strategy 2 failed: " + e2.getMessage());
                // Strategy 3: Try direct findElement (no wait)
                try {
                    System.out.println("  🔍 Strategy 3: Trying direct findElement...");
                    input = driver.findElement(locator);
                    System.out.println("  ✅ Element found directly, scrolling into view...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
                    waitForSeconds(1);
                } catch (Exception e3) {
                    throw new Exception("Could not find Department Rating input field for row " + rowIndex + 
                        ". Tried presence, visibility, and direct find. Last error: " + e3.getMessage());
                }
            }
        }
        
        if (input == null) {
            throw new Exception("Could not find Department Rating input field for row " + rowIndex);
        }
        
        // Now try to make it clickable/interactable
        try {
            System.out.println("  🔍 Making element clickable...");
            // Try to focus and click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", input);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
          //  waitForSeconds(0.5);
        } catch (Exception e) {
            System.out.println("  ⚠️ JavaScript click failed, trying normal click...");
            try {
                // Try normal click with wait for clickable
                input = longWait.until(ExpectedConditions.elementToBeClickable(locator));
                input.click();
            } catch (Exception e2) {
                System.out.println("  ⚠️ Normal click also failed, continuing with JavaScript value setting...");
            }
        }
        
        // Ensure element is in viewport
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
        waitForElementInViewport(input, 3);
        waitForSeconds(1);
        
        // Clear the field - handle stale element by re-finding if needed
        try {
            // Use JavaScript to clear if normal clear fails
            try {
                input.clear();
                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);
            } catch (Exception clearEx) {
                System.out.println("  ⚠️ Normal clear failed, using JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("  ⚠️ Stale element detected during clear, re-finding element...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
            try {
                input.clear();
                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);
            } catch (Exception clearEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            }
        }
        
        // Wait for field to be cleared
        try {
            WebDriverWait clearWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            clearWait.until(driver -> {
                try {
                    WebElement elem = driver.findElement(locator);
                    String val = elem.getAttribute("value");
                    return val == null || val.isEmpty();
                } catch (Exception ex) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        // Enter the value - handle stale element by re-finding if needed
        try {
            input.sendKeys(trimmedValue);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("  ⚠️ Stale element detected during sendKeys, re-finding element...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
            try {
                input.sendKeys(trimmedValue);
            } catch (Exception sendKeysEx) {
                // Use JavaScript as fallback
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, trimmedValue);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", input);
            }
        } catch (Exception e) {
            // Use JavaScript as fallback
            System.out.println("  ⚠️ Normal sendKeys failed, using JavaScript...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, trimmedValue);
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", input);
        }
        
        // Wait for value to be entered
        waitForElementValueUpdate(input, trimmedValue, 3);
        
        System.out.println("✅ Entered Department Rating: '" + trimmedValue + "'");
        if (reportLogger != null) {
            reportLogger.info("Entered Department Rating: " + trimmedValue);
        }
    }

    /**
     * Clear Man-Com Priority field in table row (leave blank) // Gayatri.k
     */
    public void clearManComPriorityInTable(int rowIndex) throws Exception {
        System.out.println("📍 Clearing Man-Com Priority in row " + rowIndex + " (leaving blank)...");
        
        By locator = ManComPrioritizationPageLocators.manComPriorityTableInput(rowIndex);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForElementInViewport(input, 2);
        
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        // Wait for field to be cleared
        try {
            WebDriverWait clearWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            clearWait.until(driver -> input.getAttribute("value") == null || input.getAttribute("value").isEmpty());
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        System.out.println("✅ Cleared Man-Com Priority field (now blank)");
        if (reportLogger != null) {
            reportLogger.info("Cleared Man-Com Priority field");
        }
    }

    /**
     * Enter Man-Com Priority in table row // Gayatri.k
     */
    public void enterManComPriorityInTable(String value, int rowIndex) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            throw new Exception("Man-Com Priority value is empty");
        }
        String trimmedValue = value.trim();
        System.out.println("📍 Entering Man-Com Priority in row " + rowIndex + ": '" + trimmedValue + "'");
        
        // Use the exact XPath pattern provided by user: /html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[X]/td[7]/div/input
        By locator = ManComPrioritizationPageLocators.manComPriorityTableInput(rowIndex);
        
        // Wait a bit after Department Rating entry to allow DOM to stabilize
        waitForSeconds(1);
        
        // Try multiple strategies to find and interact with the element
        WebElement input = null;
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Strategy 1: Try presence first (fastest check)
        try {
            System.out.println("  🔍 Strategy 1: Checking if element exists...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            System.out.println("  ✅ Element found, scrolling into view...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
            waitForSeconds(1);
        } catch (Exception e1) {
            System.out.println("  ⚠️ Strategy 1 failed: " + e1.getMessage());
            // Strategy 2: Try visibility
            try {
                System.out.println("  🔍 Strategy 2: Checking if element is visible...");
                input = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                System.out.println("  ✅ Element is visible, scrolling into view...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
                waitForSeconds(1);
            } catch (Exception e2) {
                System.out.println("  ⚠️ Strategy 2 failed: " + e2.getMessage());
                // Strategy 3: Try direct findElement (no wait)
                try {
                    System.out.println("  🔍 Strategy 3: Trying direct findElement...");
                    input = driver.findElement(locator);
                    System.out.println("  ✅ Element found directly, scrolling into view...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
                    waitForSeconds(1);
                } catch (Exception e3) {
                    throw new Exception("Could not find Man-Com Priority input field for row " + rowIndex + 
                        ". Tried presence, visibility, and direct find. Last error: " + e3.getMessage());
                }
            }
        }
        
        if (input == null) {
            throw new Exception("Could not find Man-Com Priority input field for row " + rowIndex);
        }
        
        // Now try to make it clickable/interactable
        try {
            System.out.println("  🔍 Making element clickable...");
            // Try to focus and click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", input);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
         //   waitForSeconds(0.5);
        } catch (Exception e) {
            System.out.println("  ⚠️ JavaScript click failed, trying normal click...");
            try {
                // Try normal click with wait for clickable
                input = longWait.until(ExpectedConditions.elementToBeClickable(locator));
                input.click();
            } catch (Exception e2) {
                System.out.println("  ⚠️ Normal click also failed, continuing with JavaScript value setting...");
            }
        }
        
        // Ensure element is in viewport
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", input);
        waitForElementInViewport(input, 3);
        waitForSeconds(1);
        
        // Clear the field - handle stale element by re-finding if needed
        try {
            // Use JavaScript to clear if normal clear fails
            try {
                input.clear();
                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);
            } catch (Exception clearEx) {
                System.out.println("  ⚠️ Normal clear failed, using JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("  ⚠️ Stale element detected during clear, re-finding element...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
            try {
                input.clear();
                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);
            } catch (Exception clearEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            }
        }
        
        // Wait for field to be cleared
        try {
            WebDriverWait clearWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            clearWait.until(driver -> {
                try {
                    WebElement elem = driver.findElement(locator);
                    String val = elem.getAttribute("value");
                    return val == null || val.isEmpty();
                } catch (Exception ex) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Field may already be clear, continue
        }
        
        // Enter the value - handle stale element by re-finding if needed
        try {
            input.sendKeys(trimmedValue);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            System.out.println("  ⚠️ Stale element detected during sendKeys, re-finding element...");
            input = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
            try {
                input.sendKeys(trimmedValue);
            } catch (Exception sendKeysEx) {
                // Use JavaScript as fallback
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, trimmedValue);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", input);
            }
        } catch (Exception e) {
            // Use JavaScript as fallback
            System.out.println("  ⚠️ Normal sendKeys failed, using JavaScript...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, trimmedValue);
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", input);
        }
        
        // Wait for value to be entered
        waitForElementValueUpdate(input, trimmedValue, 3);
        
        System.out.println("✅ Entered Man-Com Priority: '" + trimmedValue + "'");
        if (reportLogger != null) {
            reportLogger.info("Entered Man-Com Priority: " + trimmedValue);
        }
    }

    /**
     * Check if Department Rating field is enabled in table row // Gayatri.k
     */
    public boolean isDepartmentRatingEnabled(int rowIndex) throws Exception {
        By locator = ManComPrioritizationPageLocators.departmentRatingTableInput(rowIndex);
        return isFieldEnabled(locator);
    }

    /**
     * Check if Man-Com Priority field is enabled in table row // Gayatri.k
     */
    public boolean isManComPriorityEnabled(int rowIndex) throws Exception {
        By locator = ManComPrioritizationPageLocators.manComPriorityTableInput(rowIndex);
        return isFieldEnabled(locator);
    }

    /**
     * Verify that a disabled field cannot be entered // Gayatri.k
     * @param rowIndex The 1-based row index
     * @param fieldName The name of the field for logging
     * @return true if field is disabled and cannot be entered, false otherwise
     */
    public boolean verifyDisabledFieldCannotBeEntered(int rowIndex, String fieldName) throws Exception {
        System.out.println("📍 Verifying that " + fieldName + " field (row " + rowIndex + ") is disabled and cannot be entered...");
        
        By locator;
        if (fieldName.contains("Department Rating")) {
            locator = ManComPrioritizationPageLocators.departmentRatingTableInput(rowIndex);
        } else if (fieldName.contains("Man-Com Priority")) {
            locator = ManComPrioritizationPageLocators.manComPriorityTableInput(rowIndex);
        } else {
            throw new Exception("Unknown field name: " + fieldName);
        }
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            
            // Check if field is disabled
            boolean isEnabled = field.isEnabled();
            String disabledAttr = field.getAttribute("disabled");
            String readonlyAttr = field.getAttribute("readonly");
            
            boolean isDisabled = !isEnabled || disabledAttr != null || readonlyAttr != null;
            
            if (isDisabled) {
                System.out.println("✅ " + fieldName + " field is disabled and cannot be entered");
                System.out.println("   Field status: enabled=" + isEnabled + ", disabled attr=" + disabledAttr + ", readonly attr=" + readonlyAttr);
                
                // Get current value
                String currentValue = field.getAttribute("value");
                if (currentValue == null) currentValue = "";
                System.out.println("   Current value: '" + currentValue + "'");
                
                // Since field is disabled, it should not accept input
                // Verify by checking that isEnabled() returns false
                if (!isEnabled) {
                    System.out.println("✅ Verified: " + fieldName + " field is disabled (isEnabled() = false)");
                    if (reportLogger != null) {
                        reportLogger.pass(fieldName + " field is disabled and cannot be entered");
                    }
                    return true;
                } else if (disabledAttr != null || readonlyAttr != null) {
                    System.out.println("✅ Verified: " + fieldName + " field has disabled/readonly attribute");
                    if (reportLogger != null) {
                        reportLogger.pass(fieldName + " field is disabled and cannot be entered");
                    }
                    return true;
                }
            } else {
                System.out.println("⚠️ " + fieldName + " field is NOT disabled - cannot verify disabled behavior");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Error verifying disabled field: " + e.getMessage());
            return false;
        }
        
        return true;
    }

    /**
     * Click Save button // Gayatri.k
     */
    public void clickSaveButton() throws Exception {
        System.out.println("📍 Clicking Save button...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                ManComPrioritizationPageLocators.saveButton));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
        waitForElementInViewport(saveButton, 2);
        
        try {
            saveButton.click();
            System.out.println("✅ Clicked Save button");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
            System.out.println("✅ Clicked Save button (JavaScript)");
        }
        
        // Wait for alert message to appear after save
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            alertWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'saved') or contains(text(),'success') or contains(text(),'alert')]")));
        } catch (Exception e) {
            // Alert may appear differently, continue
        }
        if (reportLogger != null) {
            reportLogger.info("Clicked Save button");
        }
    }

    /**
     * Verify alert message // Gayatri.k
     */
    public boolean verifyAlertMessage(String expectedMessage) throws Exception {
        System.out.println("📍 Verifying alert message...");
        System.out.println("   Expected: '" + expectedMessage + "'");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try multiple locator strategies for alert/snackbar/toast notifications
            By[] alertLocators = {
                // Generic text-based locators
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//span[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//p[contains(text(),'" + expectedMessage + "')]"),
                // Snackbar/Toast notification locators (Material-UI)
                By.xpath("//div[contains(@class,'MuiSnackbar')]//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'MuiAlert')]//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'snackbar')]//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'toast')]//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'notification')]//*[contains(text(),'" + expectedMessage + "')]"),
                // Role-based locators
                By.xpath("//div[@role='alert']//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[@role='status']//*[contains(text(),'" + expectedMessage + "')]"),
                // Case-insensitive search
                By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + expectedMessage.toLowerCase() + "')]")
            };
            
            WebElement alertElement = null;
            for (By locator : alertLocators) {
                try {
                    alertElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (alertElement != null && alertElement.isDisplayed()) {
                        System.out.println("✅ Found alert element using: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    // Try next locator
                    continue;
                }
            }
            
            if (alertElement == null) {
                System.out.println("❌ Failed to find alert message: '" + expectedMessage + "'");
                // Debug: Print page source snippet to help diagnose
                try {
                    String pageSource = driver.getPageSource();
                    if (pageSource.contains("saved") || pageSource.contains("success")) {
                        System.out.println("⚠️ Page source contains 'saved' or 'success', but element not found with locators");
                    }
                } catch (Exception e) {
                    // Ignore
                }
                if (reportLogger != null) {
                    reportLogger.fail("Failed to find alert message: " + expectedMessage);
                }
                return false;
            }
            
            String actualMessage = alertElement.getText();
            System.out.println("   Actual: '" + actualMessage + "'");
            
            boolean matches = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            if (matches) {
                System.out.println("✅ Alert message verified: '" + expectedMessage + "'");
                if (reportLogger != null) {
                    reportLogger.pass("Alert message verified: " + expectedMessage);
                }
            } else {
                System.out.println("⚠️ Alert message mismatch. Expected contains: '" + expectedMessage + "', Actual: '" + actualMessage + "'");
                if (reportLogger != null) {
                    reportLogger.warning("Alert mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            return matches;
        } catch (Exception e) {
            System.out.println("❌ Failed to verify alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify alert: " + e.getMessage());
            }
            return false;
        }
    }

    // ==================== PAGINATION ====================

    /**
     * Check if pagination next button is enabled using aria-disabled attribute // Gayatri.k
     * @return true if button is enabled, false otherwise
     */
    public boolean isNextPageEnabled(By nextPageLocator) {
        try {
            WebElement next = driver.findElement(nextPageLocator);
            String ariaDisabled = next.getAttribute("aria-disabled");
            System.out.println("➡️ Next button aria-disabled = " + ariaDisabled);
            return ariaDisabled == null || ariaDisabled.equalsIgnoreCase("false");
        } catch (Exception e) {
            System.out.println("⚠️ Error checking pagination next button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click on pagination next button // Gayatri.k
     * @throws Exception if button not found or click fails
     */
    private void clickNextPage(By nextPageLocator, String elementName) throws Exception {
        System.out.println("📍 Clicking " + elementName + "...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextPageLocator));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextButton);
            waitForElementInViewport(nextButton, 2);
            
            // Use JavaScript click (more reliable for React/Material-UI)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
            System.out.println("✅ Clicked " + elementName + " using JavaScript");
            
            if (reportLogger != null) {
                reportLogger.info("Clicked " + elementName);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click " + elementName + ": " + e.getMessage());
            throw new Exception("Failed to click " + elementName + ": " + e.getMessage());
        }
    }

    /**
     * Verify pagination functionality by clicking next until disabled // Gayatri.k
     * Clicks through all pages one by one until the next button is disabled
     * Uses aria-disabled attribute and stalenessOf for React re-render detection
     * @return true if pagination works properly (reaches last page), false otherwise
     */
    public boolean verifyPaginationFunctionality() throws Exception {
        System.out.println("📍 Verifying pagination functionality - clicking through all pages one by one...");
        int maxPages = 30; // Safety limit to prevent infinite loop
        
        // Try multiple locator strategies for pagination next button
        By nextPageLocator = null;
        By[] paginationLocators = {
            ManComPrioritizationPageLocators.paginationNextButton, // Primary locator: /html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[2]
            By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/div[3]/button[2]"), // Alternative with div[1]
            By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[2]/button[2]"), // Without div[3]
            By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[2]/button[2]"), // div[1] without div[3]
            By.xpath("//button[contains(@aria-label,'Next') or contains(@title,'Next')]"), // By aria-label/title
            By.xpath("//button[contains(@aria-label,'Next page')]"), // By aria-label 'Next page'
            By.xpath("//button[.//svg[@data-testid='ArrowForwardIcon']]"), // By SVG icon
            By.xpath("//button[.//svg[contains(@class,'ArrowForward')]]"), // By SVG class
            By.xpath("//div[contains(@class,'MuiTablePagination')]//button[2]"), // Material-UI pagination button 2
            By.xpath("//div[contains(@class,'MuiTablePagination-actions')]//button[2]"), // Material-UI actions button 2
            By.xpath("//div[contains(@class,'pagination')]//button[2]") // Generic pagination button 2
        };
        
        for (By locator : paginationLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (button != null && button.isDisplayed()) {
                    nextPageLocator = locator;
                    System.out.println("✅ Found pagination next button using: " + locator);
                    break;
                }
            } catch (Exception e) {
                // Try next locator
                continue;
            }
        }
        
        if (nextPageLocator == null) {
            System.out.println("⚠️ Pagination next button not found with any locator strategy");
            if (reportLogger != null) {
                reportLogger.warning("Pagination next button not found");
            }
            return false;
        }
        
        try {
            
            System.out.println("✅ Pagination next button found, starting to click through all pages...");
            
            // Get first row as reference element for staleness check
            WebElement firstRow = null;
            try {
                List<WebElement> rows = driver.findElements(ManComPrioritizationPageLocators.prioritizationRows);
                if (!rows.isEmpty()) {
                    firstRow = rows.get(0);
                    System.out.println("📍 Using first row as reference for staleness check");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not find first row for staleness check, continuing without it");
            }
            
            int pageCount = 1;
            
            // Keep clicking next button until it becomes disabled
            while (maxPages-- > 0) {
                // Check if next button is enabled using aria-disabled
                if (!isNextPageEnabled(nextPageLocator)) {
                    System.out.println("✅ Reached last page (Page " + pageCount + "). Next button is now disabled.");
                    System.out.println("📊 Successfully navigated through " + pageCount + " page(s)");
                    if (reportLogger != null) {
                        reportLogger.pass("Pagination functionality verified. Successfully navigated through " + pageCount + " page(s). Next button is disabled on last page.");
                    }
                    return true; // Success: button is disabled (last page reached)
                }
                
                // Button is still enabled, click it to go to next page
                System.out.println("➡️ Page " + pageCount + ": Clicking next button to go to Page " + (pageCount + 1));
                
                // Click next page button
                clickNextPage(nextPageLocator, "Next Page");
                
                // Wait for React re-render using stalenessOf
                if (firstRow != null) {
                    try {
                        WebDriverWait stalenessWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        stalenessWait.until(ExpectedConditions.stalenessOf(firstRow));
                        System.out.println("✅ Page re-rendered (first row became stale)");
                        
                        // Get new first row for next iteration
                        List<WebElement> newRows = driver.findElements(ManComPrioritizationPageLocators.prioritizationRows);
                        if (!newRows.isEmpty()) {
                            firstRow = newRows.get(0);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Staleness check timeout, continuing...");
                        // Fallback: wait for table to update
                        try {
                            WebDriverWait fallbackWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                            fallbackWait.until(ExpectedConditions.presenceOfElementLocated(
                                ManComPrioritizationPageLocators.prioritizationTable));
                        } catch (Exception e2) {
                            // Table may already be present, continue
                        }
                    }
                } else {
                    // Fallback: wait for table to be present if no reference element
                    try {
                        WebDriverWait fallbackWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                        fallbackWait.until(ExpectedConditions.presenceOfElementLocated(
                            ManComPrioritizationPageLocators.prioritizationTable));
                    } catch (Exception e) {
                        // Table may already be present, continue
                    }
                }
                
                pageCount++;
                System.out.println("📍 Now on Page " + pageCount);
            }
            
            // If we reach here, we've hit the max pages limit
            System.out.println("⚠️ Reached maximum page limit (30). Pagination may have too many pages.");
            if (reportLogger != null) {
                reportLogger.warning("Pagination verification reached max page limit: 30");
            }
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error verifying pagination: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error verifying pagination: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Verify pagination controls are visible at the bottom // Gayatri.k
     * Checks if pagination controls (← Page X →) are displayed
     * @return true if pagination controls are visible, false otherwise
     */
    public boolean verifyPaginationControlsVisible() throws Exception {
        System.out.println("📍 Verifying pagination controls are visible...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Check if pagination next button exists and is visible
            try {
                WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                        ManComPrioritizationPageLocators.paginationNextButton));
                
                if (nextButton != null && nextButton.isDisplayed()) {
                    System.out.println("✅ Pagination next button is visible");
                    
                    // Also check if previous button exists (optional, but confirms pagination controls)
                    try {
                        WebElement prevButton = driver.findElement(ManComPrioritizationPageLocators.paginationPreviousButton);
                        if (prevButton != null && prevButton.isDisplayed()) {
                            System.out.println("✅ Pagination previous button is visible");
                        }
                    } catch (Exception e) {
                        System.out.println("ℹ️ Pagination previous button not found (may be on first page)");
                    }
                    
                    // Check pagination info if available
                    try {
                        WebElement paginationInfo = driver.findElement(ManComPrioritizationPageLocators.paginationInfo);
                        if (paginationInfo != null && paginationInfo.isDisplayed()) {
                            String infoText = paginationInfo.getText();
                            System.out.println("✅ Pagination info displayed: " + infoText);
                        }
                    } catch (Exception e) {
                        System.out.println("ℹ️ Pagination info not found");
                    }
                    
                    if (reportLogger != null) {
                        reportLogger.pass("Pagination controls are visible at the bottom of the page");
                    }
                    return true;
                } else {
                    System.out.println("⚠️ Pagination next button exists but is not visible");
                    if (reportLogger != null) {
                        reportLogger.warning("Pagination next button exists but is not visible");
                    }
                    return false;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Pagination controls not found: " + e.getMessage());
                System.out.println("   This may indicate that initiatives do not exceed page limit");
                if (reportLogger != null) {
                    reportLogger.warning("Pagination controls not found. Initiatives may not exceed page limit.");
                }
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Error verifying pagination controls: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error verifying pagination controls: " + e.getMessage());
            }
            return false;
        }
    }

    // ==================== HISTORY ====================

    /**
     * Click on History icon for a specific row using initiative code // Gayatri.k
     * @param initiativeCode The initiative code to find the row
     * @throws Exception if History icon not found or click fails
     */
    public void clickHistoryIcon(String initiativeCode) throws Exception {
        System.out.println("📍 Clicking History icon for initiative code: '" + initiativeCode + "'...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement historyButton = null;
            
            // Strategy 1: Find row by initiative code, then use relative xpath to find button/svg
            try {
                // First find the row containing the initiative code
                By rowLocator = By.xpath("//tr[td[normalize-space()='" + initiativeCode + "']]");
                WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
                
                // Then find the SVG inside td[8]/button
                WebElement svgElement = row.findElement(By.xpath(".//td[8]//button//svg"));
                
                // Get the parent button element
                historyButton = svgElement.findElement(By.xpath("./parent::button"));
                System.out.println("✅ Found History button via SVG parent (using initiative code)");
            } catch (Exception e) {
                System.out.println("⚠️ Strategy 1 failed: " + e.getMessage());
            }
            
            // Strategy 2: Use absolute xpath pattern with row index (if we can get row index)
            if (historyButton == null) {
                try {
                    // Get row index for the initiative code
                    int rowIndex = getRowIndexByInitiativeCode(initiativeCode);
                    By svgLocator = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[" + rowIndex + "]/td[8]/button/svg");
                    WebElement svgElement = wait.until(ExpectedConditions.presenceOfElementLocated(svgLocator));
                    
                    // Get the parent button element
                    historyButton = svgElement.findElement(By.xpath("./parent::button"));
                    System.out.println("✅ Found History button via SVG parent (using absolute xpath, row " + rowIndex + ")");
                } catch (Exception e) {
                    System.out.println("⚠️ Strategy 2 failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Try aria-label approach as fallback
            if (historyButton == null) {
                try {
                    By historyIcon = By.xpath("//tr[td[normalize-space()='" + initiativeCode + "']]//button[@aria-label='History']");
                    historyButton = wait.until(ExpectedConditions.elementToBeClickable(historyIcon));
                    System.out.println("✅ Found History button using aria-label");
                } catch (Exception e) {
                    System.out.println("⚠️ Strategy 3 failed: " + e.getMessage());
                }
            }
            
            if (historyButton == null) {
                throw new Exception("Could not find History button for initiative code '" + initiativeCode + "'");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", historyButton);
            
            // Use JavaScript click immediately (no wait)
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", historyButton);
                System.out.println("✅ Clicked History icon using JavaScript");
            } catch (Exception e) {
                // Fallback to Actions class
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(historyButton).click().perform();
                    System.out.println("✅ Clicked History icon using Actions class");
                } catch (Exception e2) {
                    // Final fallback to normal click
                    historyButton.click();
                    System.out.println("✅ Clicked History icon using normal click");
                }
            }
            
            if (reportLogger != null) {
                reportLogger.info("Clicked History icon for initiative code: " + initiativeCode);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click History icon for initiative code '" + initiativeCode + "': " + e.getMessage());
            throw new Exception("Failed to click History icon for initiative code '" + initiativeCode + "': " + e.getMessage());
        }
    }

    /**
     * Verify History functionality - checks if History dialog/modal appears // Gayatri.k
     * @return true if History dialog/modal is visible, false otherwise
     */
    public boolean verifyHistoryDialogVisible() throws Exception {
        System.out.println("📍 Verifying History dialog is visible...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            // Common locators for History dialog/modal
            By[] historyDialogLocators = {
                By.xpath("//*[contains(text(),'History') or contains(text(),'history')]"),
                By.xpath("//div[contains(@class,'dialog') or contains(@class,'modal')]//*[contains(text(),'History')]"),
                By.xpath("//h6[contains(text(),'History')]"),
                By.xpath("//h5[contains(text(),'History')]"),
                By.xpath("//div[@role='dialog']"),
                By.xpath("//div[contains(@class,'MuiDialog')]")
            };
            
            for (By locator : historyDialogLocators) {
                try {
                    WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    if (dialog != null && dialog.isDisplayed()) {
                        System.out.println("✅ History dialog is visible");
                        if (reportLogger != null) {
                            reportLogger.pass("History dialog is visible");
                        }
                        return true;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            System.out.println("⚠️ History dialog not found");
            if (reportLogger != null) {
                reportLogger.warning("History dialog not found");
            }
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error verifying History dialog: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error verifying History dialog: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Close the History dialog // Gayatri.k
     * @throws Exception if close button not found or click fails
     */
    public void closeHistoryDialog() throws Exception {
        System.out.println("📍 Closing History dialog...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Close button xpath: /html/body/div[2]/div[3]/div/div/div[2]/div[1]/div/div/span[1]
            By closeButtonLocator = By.xpath("/html/body/div[2]/div[3]/div/div/div[2]/div[1]/div/div/span[1]");
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeButton);
            waitForElementInViewport(closeButton, 2);
            
            // Use JavaScript click
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
                System.out.println("✅ Closed History dialog using JavaScript");
            } catch (Exception e) {
                // Fallback to Actions class
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(closeButton).click().perform();
                    System.out.println("✅ Closed History dialog using Actions class");
                } catch (Exception e2) {
                    // Final fallback to normal click
                    closeButton.click();
                    System.out.println("✅ Closed History dialog using normal click");
                }
            }
            
            // Wait for dialog to close
            try {
                WebDriverWait closeWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                closeWait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[@role='dialog']")));
            } catch (Exception e) {
                // Dialog may close differently, continue
            }
            
            if (reportLogger != null) {
                reportLogger.info("Closed History dialog");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to close History dialog: " + e.getMessage());
            throw new Exception("Failed to close History dialog: " + e.getMessage());
        }
    }

    /**
     * Verify History details are displayed in the History dialog // Gayatri.k
     * @return true if History details are visible, false otherwise
     */
    public boolean verifyHistoryDetails() throws Exception {
        System.out.println("📍 Verifying History details are displayed...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for dialog content to load after dialog appears
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@role='dialog']//*[text() and string-length(normalize-space(text())) > 10]")));
            } catch (Exception e) {
                // Content may load differently, continue
            }
            
            // Strategy 1: Check if dialog has meaningful content (text length)
            try {
                List<WebElement> dialogs = driver.findElements(By.xpath("//div[@role='dialog']"));
                for (WebElement dialog : dialogs) {
                    if (dialog != null && dialog.isDisplayed()) {
                        String dialogText = dialog.getText();
                        if (dialogText != null && !dialogText.trim().isEmpty() && dialogText.length() > 20) {
                            System.out.println("✅ History dialog contains content (text length: " + dialogText.length() + " chars)");
                            System.out.println("   Preview: " + (dialogText.length() > 100 ? dialogText.substring(0, 100) + "..." : dialogText));
                            if (reportLogger != null) {
                                reportLogger.pass("History details are visible (dialog has content: " + dialogText.length() + " chars)");
                            }
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not get dialog text: " + e.getMessage());
            }
            
            // Strategy 2: Look for common history detail elements (table, list, text content)
            By[] historyDetailLocators = {
                By.xpath("//div[@role='dialog']//table"),
                By.xpath("//div[@role='dialog']//tbody//tr"),
                By.xpath("//div[contains(@class,'MuiDialog')]//table"),
                By.xpath("//div[contains(@class,'MuiDialog')]//tbody//tr"),
                By.xpath("//div[@role='dialog']//div[contains(@class,'content')]"),
                By.xpath("//div[contains(@class,'dialog')]//div[contains(@class,'MuiDialogContent')]"),
                By.xpath("//div[@role='dialog']//div[contains(@class,'paper')]"),
                By.xpath("//div[@role='dialog']//*[text() and string-length(normalize-space(text())) > 10]") // Any element with meaningful text
            };
            
            for (By locator : historyDetailLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    if (elements != null && !elements.isEmpty()) {
                        // Check if at least one element has meaningful text
                        for (WebElement elem : elements) {
                            try {
                                String text = elem.getText();
                                if (text != null && !text.trim().isEmpty() && text.trim().length() > 5 && !text.trim().equalsIgnoreCase("History")) {
                                    System.out.println("✅ History details are visible (found element with text: " + 
                                        (text.length() > 50 ? text.substring(0, 50) + "..." : text) + ")");
                                    if (reportLogger != null) {
                                        reportLogger.pass("History details are visible");
                                    }
                                    return true;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Strategy 3: Check if dialog contains any visible child elements with text
            try {
                WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='dialog']")));
                if (dialog != null && dialog.isDisplayed()) {
                    List<WebElement> children = dialog.findElements(By.xpath(".//*[text() and string-length(normalize-space(text())) > 5]"));
                    if (children != null && !children.isEmpty()) {
                        System.out.println("✅ History dialog contains " + children.size() + " text element(s)");
                        if (reportLogger != null) {
                            reportLogger.pass("History details are visible (found " + children.size() + " text elements)");
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not check dialog children: " + e.getMessage());
            }
            
            System.out.println("⚠️ History details not found in dialog");
            if (reportLogger != null) {
                reportLogger.warning("History details not found");
            }
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error verifying History details: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error verifying History details: " + e.getMessage());
            }
            return false;
        }
    }
}

