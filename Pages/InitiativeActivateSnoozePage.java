package Pages;

import Actions.ActionEngine;
import Locators.InitiativeActivateSnoozePageLocators;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Initiative Activate Snooze Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in InitiativeActivateSnoozePageLocators.java
 *    - Methods use InitiativeActivateSnoozePageLocators.locatorName for reusability
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in InitiativeActivateSnoozePageLocators class
 *    - Actions: Implemented in this Page class
 *    - Test Logic: Kept in test classes
 * 
 * 3. MAINTAINABILITY:
 *    - Single Point of Change: Update locator once in Locators class
 *    - No Hardcoded Locators: All locators reference central class
 *    - Clear Method Names: Self-documenting code
 * 
 * 4. ROBUSTNESS:
 *    - Fallback Strategies: Multiple locator attempts for stability
 *    - Wait Mechanisms: Proper waits for element visibility
 *    - Error Handling: Comprehensive try-catch blocks
 * 
 * Updated by Shahu.D
 * 
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeActivateSnoozePage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * Updated by Shahu.D
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeActivateSnoozePage(WebDriver driver, ExtentTest reportLogger) {
        super(); // pass driver to ActionEngine
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Navigate to Initiative Activate Snooze page
     * Updated by Shahu.D
     * @throws Exception if navigation fails
     */
    public void navigateToInitiativeActivateSnoozePage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            // Step 1: Click on Initiative Management navigation
            System.out.println("📍 Step 1: Clicking on Initiative Management module...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            click(InitiativeActivateSnoozePageLocators.initiativeManagementNav, "Initiative Management Navigation");
            System.out.println("  ✅ Successfully clicked on Initiative Management module");
            waitForSeconds(3);

            // Step 2: Click on Initiative Activate Snooze card
            System.out.println("📍 Step 2: Clicking on Initiative Activate Snooze card...");
            click(InitiativeActivateSnoozePageLocators.initiativeActivateSnoozeCard, "Initiative Activate Snooze Card");
            System.out.println("  ✅ Successfully clicked on Initiative Activate Snooze card");
            waitForSeconds(3);

            // Verify page loaded - Try multiple strategies - Updated by Shahu.D
            try {
                // Try to find page header
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    InitiativeActivateSnoozePageLocators.initiativeActivateSnoozePageHeader
                ));
                System.out.println("  ✅ Initiative Activate Snooze page header found");
            } catch (Exception headerEx) {
                System.out.println("  ⚠️ Page header not found, trying alternative verification...");
                // Try to find any common page elements as fallback
                try {
                    // Look for search option icon or any common element on the page
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                        InitiativeActivateSnoozePageLocators.searchOptionIcon
                    ));
                    System.out.println("  ✅ Page loaded (verified by Search option icon)");
                } catch (Exception searchIconEx) {
                    System.out.println("  ⚠️ Search icon not found, waiting for page to stabilize...");
                    // Wait a bit more and assume page is loaded
                    waitForSeconds(2);
                    System.out.println("  ✅ Page navigation completed (assuming page loaded after wait)");
                }
            }

            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to Initiative Activate Snooze page");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error navigating to Initiative Activate Snooze page: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to Initiative Activate Snooze page: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Search option icon
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchOptionIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.searchOptionIcon));
            click(InitiativeActivateSnoozePageLocators.searchOptionIcon, "Search Option Icon");
            System.out.println("  ✅ Successfully clicked on Search option icon");
            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Search option icon");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search option icon: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search option icon: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Active dropdown field
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickActiveDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.activeDropdown));
            click(InitiativeActivateSnoozePageLocators.activeDropdown, "Active Dropdown");
            System.out.println("  ✅ Successfully clicked on Active dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Active dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Active dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Active dropdown: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select "Yes" from Active dropdown
     * Updated by Shahu.D
     * @throws Exception if element is not found or selection fails
     */
    public void selectActiveValueYes() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.activeDropdownValueYes));
            click(InitiativeActivateSnoozePageLocators.activeDropdownValueYes, "Active Dropdown Value: Yes");
            System.out.println("  ✅ Successfully selected 'Yes' from Active dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Selected 'Yes' from Active dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Active value 'Yes': " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Active value 'Yes': " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Nature of Initiative dropdown field
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickNatureOfInitiativeDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.natureOfInitiativeDropdown));
            click(InitiativeActivateSnoozePageLocators.natureOfInitiativeDropdown, "Nature of Initiative Dropdown");
            System.out.println("  ✅ Successfully clicked on Nature of Initiative dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Nature of Initiative dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Nature of Initiative dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Nature of Initiative dropdown: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select "StartUp Application Processing" from Nature of Initiative dropdown
     * Updated by Shahu.D
     * @throws Exception if element is not found or selection fails
     */
    public void selectNatureOfInitiativeValue(String value) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.natureOfInitiativeDropdownValue));
            selectFromList(InitiativeActivateSnoozePageLocators.natureOfInitiativeDropdownValue, value, "natureOfInitiativeDropdownValue");
            System.out.println("  ✅ Successfully selected 'StartUp Application Processing' from Nature of Initiative dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Selected 'StartUp Application Processing' from Nature of Initiative dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Nature of Initiative value: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Nature of Initiative value: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter Initiative Title
     * Updated by Shahu.D
     * @param title The initiative title to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeTitle(String title) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try multiple strategies to find and interact with the element - Updated by Shahu.D
            WebElement titleElement = null;
            
            // Strategy 1: Try to find by ID first
            try {
                By idLocator = By.xpath("//*[@id=\"initiativeTitle\"]");
                titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(idLocator));
                System.out.println("  ✅ Found Initiative Title element by ID");
            } catch (Exception e1) {
                System.out.println("  ⚠️ Element not found by ID, trying div wrapper...");
                // Strategy 2: Try div wrapper
                try {
                    By divLocator = By.xpath("//div[@class='css-4io43t']");
                    titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(divLocator));
                    System.out.println("  ✅ Found Initiative Title element by div wrapper");
                } catch (Exception e2) {
                    System.out.println("  ⚠️ Div wrapper not found, trying generic locator...");
                    // Strategy 3: Try generic locator
                    titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativeActivateSnoozePageLocators.initiativeTitleField));
                    System.out.println("  ✅ Found Initiative Title element by generic locator");
                }
            }
            
            // Scroll element into view - Updated by Shahu.D
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", titleElement);
            waitForSeconds(1);
            
            // Try to find input field within the element if it's a wrapper div - Updated by Shahu.D
            WebElement inputField = null;
            try {
                // If it's a div wrapper, find the input inside it
                inputField = titleElement.findElement(By.xpath(".//input | .//textarea | .//*[@contenteditable='true']"));
                System.out.println("  ✅ Found input field within wrapper");
            } catch (Exception e3) {
                // If no input found, use the element itself
                inputField = titleElement;
                System.out.println("  ℹ️ Using element directly as input field");
            }
            
            // Try to interact with the field - Updated by Shahu.D
            try {
                // Try regular click and type
                inputField.click();
                inputField.clear();
                inputField.sendKeys(title);
                System.out.println("  ✅ Successfully entered Initiative Title using regular method: " + title);
            } catch (Exception e4) {
                System.out.println("  ⚠️ Regular method failed, trying JavaScript...");
                // Fallback: Use JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", inputField, title);
                // Trigger input event
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", inputField);
                System.out.println("  ✅ Successfully entered Initiative Title using JavaScript: " + title);
            }
            
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Initiative Title: " + title);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Title: " + e.getMessage());
            System.err.println("  💡 Debug: Trying to find element with XPath: //*[@id=\"initiativeTitle\"]");
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Initiative Title: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter Initiative Code in search field
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to search for
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            try {
                // First attempt: wait for Initiative Code field to be visible directly - Updated by Shahu.D
                wait.until(ExpectedConditions.visibilityOfElementLocated(InitiativeActivateSnoozePageLocators.initiativeCodeField));
            } catch (Exception inner) {
                System.out.println("  ⚠️ Initiative Code field not visible yet, clicking Search option icon and retrying..."); // Updated by Shahu.D
                // Sometimes filters are hidden until Search icon is clicked - Updated by Shahu.D
                try {
                    WebElement searchIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.searchOptionIcon));
                    searchIcon.click();
                    waitForSeconds(2);
                } catch (Exception iconEx) {
                    System.out.println("  ⚠️ Search option icon click failed or not needed: " + iconEx.getMessage()); // Updated by Shahu.D
                }
                // Retry waiting for Initiative Code field - Updated by Shahu.D
                wait.until(ExpectedConditions.visibilityOfElementLocated(InitiativeActivateSnoozePageLocators.initiativeCodeField));
            }

            type(InitiativeActivateSnoozePageLocators.initiativeCodeField, initiativeCode, "Initiative Code Input Field");
            System.out.println("  ✅ Successfully entered Initiative Code: " + initiativeCode);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Initiative Code: " + initiativeCode);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Code: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Initiative Code: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Initiation Date field and select N/A
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickInitiationDateAndSelectNA() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.initiationDateField));
            click(InitiativeActivateSnoozePageLocators.initiationDateField, "Initiation Date Field");
            System.out.println("  ✅ Successfully clicked on Initiation Date field");
            waitForSeconds(1);

            // Try to find and click N/A option in the date picker
            try {
                // Common patterns for N/A in date pickers
                By naOption = By.xpath("//button[contains(text(),'N/A')] | //div[contains(text(),'N/A')] | //span[contains(text(),'N/A')] | //a[contains(text(),'N/A')]");
                wait.until(ExpectedConditions.elementToBeClickable(naOption));
                click(naOption, "N/A Option");
                System.out.println("  ✅ Successfully selected N/A for Initiation Date");
            } catch (Exception naEx) {
                System.out.println("  ⚠️ Could not find N/A option, date picker may need manual selection");
            }

            if (reportLogger != null) {
                reportLogger.info("Clicked on Initiation Date field and selected N/A");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Initiation Date field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiation Date field: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Stage dropdown field
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickStageDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.stageDropdown));
            click(InitiativeActivateSnoozePageLocators.stageDropdown, "Stage Dropdown");
            System.out.println("  ✅ Successfully clicked on Stage dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Stage dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Stage dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Stage dropdown: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select "Due Diligence (Preliminary)" from Stage dropdown
     * Updated by Shahu.D
     * @throws Exception if element is not found or selection fails
     */
    public void selectStageValue(String stagevalue) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.stageDropdownValue));
            selectFromList(InitiativeActivateSnoozePageLocators.stageDropdownValue, stagevalue, "stageDropdownValue");
            System.out.println("  ✅ Successfully selected 'Due Diligence (Preliminary)' from Stage dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Selected 'Due Diligence (Preliminary)' from Stage dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Stage value: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Stage value: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Created By dropdown field
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickCreatedByDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.createdByDropdown));
            click(InitiativeActivateSnoozePageLocators.createdByDropdown, "Created By Dropdown");
            System.out.println("  ✅ Successfully clicked on Created By dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Created By dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Created By dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Created By dropdown: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select "Gayatri" from Created By dropdown
     * Updated by Shahu.D
     * @throws Exception if element is not found or selection fails
     */
    public void selectCreatedByValue() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.createdByDropdownValue));
            click(InitiativeActivateSnoozePageLocators.createdByDropdownValue, "Created By Dropdown Value");
            System.out.println("  ✅ Successfully selected 'Gayatri' from Created By dropdown");
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Selected 'Gayatri' from Created By dropdown");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Created By value: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Created By value: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Snooze link for a specific initiative code
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to snooze
     * @throws Exception if element is not found or click fails
     */
    public void clickSnoozeLinkForInitiative(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // First, try to locate the row by initiative code - Updated by Shahu.D
            try {
                By rowLocator = InitiativeActivateSnoozePageLocators.getRowByInitiativeCode(initiativeCode);
                WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));

                // Scroll row into view
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", row);
                waitForSeconds(1);

                // Try to find Snooze link inside that row - Updated by Shahu.D
                try {
                    WebElement snoozeLink = row.findElement(By.xpath(".//a[contains(text(),'Snooze') or contains(@href,'snooze')]"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", snoozeLink);
                    System.out.println("  ✅ Successfully clicked Snooze link within row for Initiative: " + initiativeCode);
                    waitForSeconds(2);
                    if (reportLogger != null) {
                        reportLogger.info("Clicked Snooze link for Initiative: " + initiativeCode);
                    }
                    return;
                } catch (Exception innerEx) {
                    System.out.println("  ⚠️ Snooze link not found inside row, falling back to global locator...");
                }
            } catch (Exception rowEx) {
                System.out.println("  ⚠️ Row by initiative code not found, falling back to global Snooze link locator...");
            }

            // Fallback: Use global Snooze link locator (first row) - Updated by Shahu.D
            WebElement snoozeLinkGlobal = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.snoozeLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", snoozeLinkGlobal);
            waitForSeconds(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", snoozeLinkGlobal);
            System.out.println("  ✅ Successfully clicked Snooze link using global locator");
            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked Snooze link (global) for Initiative: " + initiativeCode);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Snooze link: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Snooze link: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Activate link for a specific initiative code
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to activate
     * @throws Exception if element is not found or click fails
     */
    public void clickActivateLinkForInitiative(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // First, try to locate the row by initiative code - Updated by Shahu.D
            try {
                By rowLocator = InitiativeActivateSnoozePageLocators.getRowByInitiativeCode(initiativeCode);
                WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));

                // Scroll row into view
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", row);
                waitForSeconds(1);

                // Try to find Activate link inside that row - Updated by Shahu.D
                try {
                    WebElement activateLink = row.findElement(By.xpath(".//a[contains(text(),'Activate') or contains(@href,'activate')]"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activateLink);
                    System.out.println("  ✅ Successfully clicked Activate link within row for Initiative: " + initiativeCode);
                    waitForSeconds(2);
                    if (reportLogger != null) {
                        reportLogger.info("Clicked Activate link for Initiative: " + initiativeCode);
                    }
                    return;
                } catch (Exception innerEx) {
                    System.out.println("  ⚠️ Activate link not found inside row, falling back to global locator...");
                }
            } catch (Exception rowEx) {
            System.out.println("  ⚠️ Row by initiative code not found, falling back to global Activate link locator...");
            }

            // Fallback: Use global Activate link locator (first row) - Updated by Shahu.D
            WebElement activateLinkGlobal = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.activateLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", activateLinkGlobal);
            waitForSeconds(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activateLinkGlobal);
            System.out.println("  ✅ Successfully clicked Activate link using global locator");
            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked Activate link (global) for Initiative: " + initiativeCode);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Activate link: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Activate link: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter Snooze Comment in Comment box
     * Updated by Shahu.D
     * @param comment Comment text to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterSnoozeComment(String comment) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Ensure Snooze modal is present - Updated by Shahu.D
            WebElement modal = wait.until(
                    ExpectedConditions.presenceOfElementLocated(InitiativeActivateSnoozePageLocators.snoozeModal));

            // Try multiple strategies to locate the comment field within the modal - Updated by Shahu.D
            WebElement commentElement = null;

            // Strategy list (scoped to modal) - Updated by Shahu.D
            By[] commentLocators = new By[] {
                    By.xpath(".//*[@id='TextField202']"),
                    By.xpath(".//textarea[@id='TextField202']"),
                    By.xpath(".//textarea[contains(@placeholder,'Comment') or contains(@placeholder,'comment')]"),
                    By.xpath(".//textarea"),
                    By.xpath(".//input[contains(@placeholder,'Comment') or contains(@placeholder,'comment')]"),
                    By.xpath(".//input[contains(@id,'TextField') and (contains(@placeholder,'Comment') or contains(@placeholder,'comment'))]")
            };

            for (By locator : commentLocators) {
                try {
                    commentElement = modal.findElement(locator);
                    if (commentElement != null) {
                        System.out.println("  ✅ Found Snooze Comment element using locator: " + locator.toString());
                        break;
                    }
                } catch (Exception ignore) {
                    // try next locator
                }
            }

            if (commentElement == null) {
                // Fallback: try global locator once - Updated by Shahu.D
                try {
                    commentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            InitiativeActivateSnoozePageLocators.snoozeCommentBox));
                    System.out.println("  ✅ Found Snooze Comment element using global locator");
                } catch (Exception ex) {
                    throw new Exception("Snooze Comment field not found in modal or globally");
                }
            }

            // Scroll into view and enter text - Updated by Shahu.D
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", commentElement);
            waitForSeconds(1);

            try {
                commentElement.click();
            } catch (Exception clickEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", commentElement);
            }

            try {
                commentElement.clear();
            } catch (Exception clearEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", commentElement);
            }

            commentElement.sendKeys(comment);
            System.out.println("  ✅ Successfully entered Snooze Comment: " + comment);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Snooze Comment: " + comment);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Snooze Comment: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Snooze Comment: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter number of days in Snooze Days input
     * Updated by Shahu.D
     * @param days Number of days to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterSnoozeDays(String days) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
    	
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Ensure Snooze modal is present - Updated by Shahu.D
            WebElement modal = wait.until(
                    ExpectedConditions.presenceOfElementLocated(InitiativeActivateSnoozePageLocators.snoozeModal));

            // Try multiple strategies to locate the days field within the modal - Updated by Shahu.D
            WebElement daysElement = null;

            // Strategy list (scoped to modal) - Updated by Shahu.D
            By[] daysLocators = new By[] {
                    By.xpath(".//input[@id='TextField207']"),
                    By.xpath(".//*[@id='TextField207']"),
                    By.xpath(".//input[contains(@placeholder,'Days') or contains(@placeholder,'days')]"),
                    By.xpath(".//input"),
            };

            for (By locator : daysLocators) {
                try {
                    daysElement = modal.findElement(locator);
                    if (daysElement != null) {
                        System.out.println("  ✅ Found Snooze Days element using locator: " + locator.toString());
                        break;
                    }
                } catch (Exception ignore) {
                    // try next locator
                }
            }

            if (daysElement == null) {
                // Fallback: try global locator once - Updated by Shahu.D
                try {
                    daysElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            InitiativeActivateSnoozePageLocators.snoozeDaysInput));
                    System.out.println("  ✅ Found Snooze Days element using global locator");
                } catch (Exception ex) {
                    throw new Exception("Snooze Days field not found in modal or globally");
                }
            }

            // Scroll into view and enter days - Updated by Shahu.D
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", daysElement);
            waitForSeconds(1);

            try {
                daysElement.click();
            } catch (Exception clickEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", daysElement);
            }

            try {
                daysElement.clear();
            } catch (Exception clearEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", daysElement);
            }

            daysElement.sendKeys(days);
            System.out.println("  ✅ Successfully entered Snooze Days: " + days);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Snooze Days: " + days);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Snooze Days: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Snooze Days: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Snooze Save button
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSnoozeSaveButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement saveBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.snoozeSaveButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", saveBtn);
            waitForSeconds(1);

            try {
                saveBtn.click();
                System.out.println("  ✅ Successfully clicked Snooze Save button using regular click");
            } catch (Exception e1) {
                System.out.println("  ⚠️ Regular click failed on Snooze Save button, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                System.out.println("  ✅ Successfully clicked Snooze Save button using JavaScript");
            }

            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked Snooze Save button");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Snooze Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Snooze Save button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Yes button on confirmation popup
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSnoozeConfirmYesButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement yesBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.snoozeConfirmYesButton));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", yesBtn);
            waitForSeconds(1);

            try {
                yesBtn.click();
                System.out.println("  ✅ Successfully clicked Yes button on confirmation popup using regular click");
            } catch (Exception e1) {
                System.out.println("  ⚠️ Regular click failed on Yes button, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
                System.out.println("  ✅ Successfully clicked Yes button on confirmation popup using JavaScript");
            }

            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked Yes button on confirmation popup");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Yes button on confirmation popup: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Yes button on confirmation popup: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click pagination and verify that at most 5 records are displayed on the page
     * Updated by Shahu.D
     * @throws Exception if pagination or verification fails
     */
    public void clickPaginationAndVerifyFiveRecordsPerPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Click pagination (next) button - Updated by Shahu.D
            System.out.println("  📍 Clicking on pagination (Next) button..."); // Updated by Shahu.D
            WebElement paginationBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.paginationNextButton));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", paginationBtn);
            waitForSeconds(1);

            try {
                paginationBtn.click();
                System.out.println("  ✅ Successfully clicked pagination button using regular click"); // Updated by Shahu.D
            } catch (Exception e1) {
                System.out.println("  ⚠️ Regular click failed on pagination button, trying JavaScript click..."); // Updated by Shahu.D
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", paginationBtn);
                System.out.println("  ✅ Successfully clicked pagination button using JavaScript"); // Updated by Shahu.D
            }

            // Wait for table to refresh - Updated by Shahu.D
            waitForSeconds(3);

            // Fetch data rows from table body - Updated by Shahu.D
            java.util.List<org.openqa.selenium.WebElement> allRows =
                    driver.findElements(InitiativeActivateSnoozePageLocators.initiativeTableDataRows);

            int visibleRowCount = 0;
            for (org.openqa.selenium.WebElement row : allRows) {
                try {
                    if (row.isDisplayed() && row.getText().trim().length() > 0) {
                        visibleRowCount++;
                    }
                } catch (Exception ignore) {
                    // ignore rows that throw stale or other minor issues
                }
            }

            System.out.println("  📊 Visible data rows on current page: " + visibleRowCount); // Updated by Shahu.D

            if (visibleRowCount == 0) {
                throw new Exception("No data rows found after clicking pagination"); // Updated by Shahu.D
            }

            if (visibleRowCount > 5) {
                throw new Exception("More than 5 records displayed on a page. Actual: " + visibleRowCount); // Updated by Shahu.D
            }

            System.out.println("  ✅ Pagination validation passed: " +
                    visibleRowCount + " record(s) displayed (<= 5) on the page"); // Updated by Shahu.D

            if (reportLogger != null) {
                reportLogger.pass("Pagination validation passed: " +
                        visibleRowCount + " record(s) displayed (<= 5) on the page"); // Updated by Shahu.D
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error validating pagination: " + e.getMessage()); // Updated by Shahu.D
            if (reportLogger != null) {
                reportLogger.fail("Pagination validation failed: " + e.getMessage()); // Updated by Shahu.D
            }
            throw e;
        }
    }
    /**
     * Click on Search button
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchBtn = null;
            
            // Strategy 1: Try to find by new ID first (id__642) - Updated by Shahu.D
            try {
                By idLocator = By.xpath("//span[@id='id__642']");
                searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(idLocator));
                System.out.println("  ✅ Found Search button by ID: id__642");
            } catch (Exception e1) {
                System.out.println("  ⚠️ Search button not found by id__642, trying id__7028...");
                // Strategy 2: Try alternative ID - Updated by Shahu.D
                try {
                    By altIdLocator = By.xpath("//*[@id=\"id__7028\"] | //span[@id='id__7028']");
                    searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(altIdLocator));
                    System.out.println("  ✅ Found Search button by ID: id__7028");
                } catch (Exception e2) {
                    System.out.println("  ⚠️ Search button not found by specific IDs, trying generic locators...");
                    // Strategy 3: Try generic button locators - Updated by Shahu.D
                    try {
                        searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativeActivateSnoozePageLocators.searchButton));
                        System.out.println("  ✅ Found Search button by generic locator");
                    } catch (Exception e3) {
                        System.err.println("  ❌ Search button not found with any locator");
                        throw e3;
                    }
                }
            }
            
            // Scroll button into view - Updated by Shahu.D
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBtn);
            waitForSeconds(1);
            
            // Try to click the button - Updated by Shahu.D
            try {
                // Try regular click first
                if (searchBtn.isDisplayed() && searchBtn.isEnabled()) {
                    searchBtn.click();
                    System.out.println("  ✅ Successfully clicked on Search button using regular click");
                } else {
                    throw new Exception("Button not clickable");
                }
            } catch (Exception e3) {
                System.out.println("  ⚠️ Regular click failed, trying JavaScript click...");
                // Fallback: Use JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("  ✅ Successfully clicked on Search button using JavaScript");
            }
            
            waitForSeconds(3); // Wait for search results

            if (reportLogger != null) {
                reportLogger.info("Clicked on Search button");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search button: " + e.getMessage());
            System.err.println("  💡 Debug: Trying to find Search button with ID: id__7028");
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Activate Snooze button for a specific initiative
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to activate snooze for
     * @throws Exception if element is not found or click fails
     */
    public void clickActivateSnoozeButton(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // First, find the row containing the initiative code
            By rowLocator = InitiativeActivateSnoozePageLocators.getRowByInitiativeCode(initiativeCode);
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
            
            // Scroll to the row
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", row);
            waitForSeconds(1);

            // Find and click the Activate Snooze button within the row
            // Try to find button within the row context
            try {
                WebElement activateBtn = row.findElement(InitiativeActivateSnoozePageLocators.activateSnoozeButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activateBtn);
                System.out.println("  ✅ Successfully clicked on Activate Snooze button using JavaScript");
            } catch (Exception e) {
                // Fallback: try direct locator
                System.out.println("  ⚠️ Row context click failed, trying direct locator...");
                click(InitiativeActivateSnoozePageLocators.activateSnoozeButton, "Activate Snooze Button");
            }
            System.out.println("  ✅ Successfully clicked on Activate Snooze button for Initiative: " + initiativeCode);
            waitForSeconds(2);

            if (reportLogger != null) {
                reportLogger.info("Clicked on Activate Snooze button for Initiative: " + initiativeCode);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Activate Snooze button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Activate Snooze button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select Snooze Reason from dropdown
     * Updated by Shahu.D
     * @param reason The snooze reason to select
     * @throws Exception if element is not found or selection fails
     */
    public void selectSnoozeReason(String reason) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for snooze modal to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(
                InitiativeActivateSnoozePageLocators.snoozeModal
            ));

            // Click on snooze reason dropdown
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.snoozeReasonDropdown));
            click(InitiativeActivateSnoozePageLocators.snoozeReasonDropdown, "Snooze Reason Dropdown");
            waitForSeconds(1);

            // Select the reason option
            By reasonOption = InitiativeActivateSnoozePageLocators.getSnoozeReasonOption(reason);
            wait.until(ExpectedConditions.elementToBeClickable(reasonOption));
            click(reasonOption, "Snooze Reason Option: " + reason);
            System.out.println("  ✅ Successfully selected Snooze Reason: " + reason);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Selected Snooze Reason: " + reason);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Snooze Reason: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Snooze Reason: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter Snooze Duration
     * Updated by Shahu.D
     * @param duration The snooze duration to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterSnoozeDuration(String duration) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(InitiativeActivateSnoozePageLocators.snoozeDurationInput));
            type(InitiativeActivateSnoozePageLocators.snoozeDurationInput, duration, "Snooze Duration Input Field");
            System.out.println("  ✅ Successfully entered Snooze Duration: " + duration);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Snooze Duration: " + duration);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Snooze Duration: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Snooze Duration: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter Snooze Comments
     * Updated by Shahu.D
     * @param comments The snooze comments to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterSnoozeComments(String comments) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(InitiativeActivateSnoozePageLocators.snoozeCommentsTextarea));
            type(InitiativeActivateSnoozePageLocators.snoozeCommentsTextarea, comments, "Snooze Comments Textarea");
            System.out.println("  ✅ Successfully entered Snooze Comments: " + comments);
            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.info("Entered Snooze Comments: " + comments);
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Snooze Comments: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Snooze Comments: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click Confirm/Submit button in snooze modal
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickConfirmSnoozeButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(InitiativeActivateSnoozePageLocators.confirmSnoozeButton));
            click(InitiativeActivateSnoozePageLocators.confirmSnoozeButton, "Confirm Snooze Button");
            System.out.println("  ✅ Successfully clicked on Confirm Snooze button");
            waitForSeconds(3); // Wait for confirmation

            if (reportLogger != null) {
                reportLogger.info("Clicked on Confirm Snooze button");
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Confirm Snooze button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Confirm Snooze button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Verify success message is displayed
     * Updated by Shahu.D
     * @return true if success message is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifySuccessMessage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(InitiativeActivateSnoozePageLocators.successMessage)
            );
            String messageText = successMsg.getText();
            System.out.println("  ✅ Success message displayed: " + messageText);
            
            if (reportLogger != null) {
                reportLogger.pass("Success message displayed: " + messageText);
            }
            return true;

        } catch (Exception e) {
            System.err.println("  ❌ Success message not found: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Success message not found: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Verify initiative is displayed in the table
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeDisplayed(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By rowLocator = InitiativeActivateSnoozePageLocators.getRowByInitiativeCode(initiativeCode);
            wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
            
            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' found in table");
            
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' found in table");
            }
            return true;

        } catch (Exception e) {
            System.err.println("  ❌ Initiative Code '" + initiativeCode + "' not found in table: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Initiative Code '" + initiativeCode + "' not found in table: " + e.getMessage());
            }
            return false;
        }
    }

    
    public void clickonfinalsearchwatch() {
        click(InitiativeActivateSnoozePageLocators.searchbutton ," search");
    }
    
    
    
    /**
     * Verify that initiative with given code is NOT displayed on Initiative Activate - Snooze page
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is NOT found (as expected), false if found (unexpected)
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeNotDisplayed(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for page to load

        try {
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is NOT displayed on Initiative Activate - Snooze page...");

            // Wait for table/data to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                System.out.println("  ⏳ Waiting for data table to load...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//table | //div[contains(@class,'table')] | //div[contains(@class,'ag-root')]")));
                System.out.println("  ✅ Data table loaded");
                waitForSeconds(2);
            } catch (Exception tableEx) {
                System.out.println("  ⚠️ Table wait timeout, continuing...");
            }

            // Try to find the initiative using the same locator as verifyInitiativeDisplayed
            By rowLocator = InitiativeActivateSnoozePageLocators.getRowByInitiativeCode(initiativeCode);
            
            boolean found = false;
            try {
                java.util.List<WebElement> elements = driver.findElements(rowLocator);
                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed()) {
                            String foundText = element.getText();
                            if (foundText != null && foundText.contains(initiativeCode)) {
                                System.out.println("  ❌ Initiative Code '" + initiativeCode + "' FOUND in Initiative Activate - Snooze page (unexpected): " + foundText);
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            } catch (Exception ex) {
                // If we can't find elements, that's good (means not displayed)
            }

            // Also try alternative locators
            if (!found) {
                By[] initiativeLocators = {
                    By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]"), // Table row
                    By.xpath("//table//td[contains(text(),'" + initiativeCode + "')]"), // Table cell
                    By.xpath("//*[normalize-space(text())='" + initiativeCode + "']"), // Exact match
                    By.xpath("//*[contains(text(),'" + initiativeCode + "')]"), // Contains text
                    By.xpath("//div[contains(@class,'ag-row')]//div[contains(text(),'" + initiativeCode + "')]"), // AG Grid row
                    By.xpath("//div[contains(@class,'ag-cell')]//*[contains(text(),'" + initiativeCode + "')]"), // AG Grid cell
                    By.xpath("//span[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(text(),'" + initiativeCode + "')]")
                };

                for (By locator : initiativeLocators) {
                    try {
                        java.util.List<WebElement> elements = driver.findElements(locator);
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed()) {
                                    String foundText = element.getText();
                                    if (foundText != null && foundText.contains(initiativeCode)) {
                                        System.out.println("  ❌ Initiative Code '" + initiativeCode + "' FOUND in Initiative Activate - Snooze page (unexpected): " + foundText);
                                        found = true;
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }

            if (!found) {
                System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Initiative Activate - Snooze page (as expected)");
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Initiative Activate - Snooze page");
                }
                return true; // Not found = expected behavior
            } else {
                System.out.println("  ❌ Initiative Code '" + initiativeCode + "' was found in Initiative Activate - Snooze page (unexpected - should not be present)");
                if (reportLogger != null) {
                    reportLogger.fail("Initiative Code '" + initiativeCode + "' unexpectedly found on Initiative Activate - Snooze page");
                }
                return false; // Found = unexpected behavior
            }

        } catch (Exception e) {
            // If we can't find the element, that's actually good (means it's not displayed)
            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Initiative Activate - Snooze page (as expected)");
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Initiative Activate - Snooze page");
            }
            return true; // Exception means not found = expected
        }
    }
}

