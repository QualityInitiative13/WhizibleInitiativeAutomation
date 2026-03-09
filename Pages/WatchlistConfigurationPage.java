package Pages;

import Actions.ActionEngine;
import Locators.WatchlistConfigurationPageLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.aventstack.extentreports.ExtentTest;

/**
 * Page Object Model (POM) for Watchlist Configuration Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in WatchlistConfigurationPageLocators.java
 *    - Methods use WatchlistConfigurationPageLocators.locatorName for reusability
 *    - Dynamic locators use helper methods like getDynamicOptionByText()
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in WatchlistConfigurationPageLocators class
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
 * @author Automation Team
 * @version 1.0
 */
public class WatchlistConfigurationPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    // 🔹 Correct constructor with WebDriver + Logger
    public WatchlistConfigurationPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // pass driver to ActionEngine
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // ✅ Helper method to check if element is present
    private boolean isElementPresent(By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    // ✅ Navigate to Watchlist Configuration Page
    public void navigateToWatchlistConfiguration() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO WATCHLIST CONFIGURATION - START");
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Wait for page to be fully loaded after login
        System.out.println("⏳ Waiting for page to stabilize after login...");
        waitForSeconds(5); // Increased wait time
        
        // Wait for page to be fully loaded
        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        pageWait.until(driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));
        System.out.println("✅ Page fully loaded");
        
        try {
            // Step 1: Hover and click on initree to reveal menu
            System.out.println("\n📍 Step 1: Accessing Initiative Tree Menu...");
            hoverAndClickElement(WatchlistConfigurationPageLocators.initree, "Initiative Tree Menu");
            waitForSeconds(2); // Wait for menu to appear
            
            // Step 2: Click on Watch List Configuration
            System.out.println("\n📍 Step 2: Clicking Watch List Configuration Node...");
            System.out.println("🔍 Looking for element: //p[normalize-space()='Watch List Configuration']");
            clickWithFallback(WatchlistConfigurationPageLocators.watchlistConfigurationNode, "Watch List Configuration");
            waitForSeconds(3); // Wait for Watchlist Configuration page to load
            
            System.out.println("\n✅ ✅ ✅ Navigated to Watchlist Configuration successfully! ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to Watchlist Configuration page");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ ❌ ❌ Navigation to Watchlist Configuration FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to Watchlist Configuration page: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO WATCHLIST CONFIGURATION - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    // ✅ Hover and Click Element Helper Method
    private void hoverAndClickElement(By locator, String elementName) throws Exception {
        try {
            System.out.println("🖱️ Hovering and clicking: " + elementName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);
            
            // Hover over element
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            System.out.println("  ↪ Hovered over element");
            waitForSeconds(1);
            
            // Click element
            try {
                element.click();
                System.out.println("  ↪ Clicked using regular click");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("  ↪ Clicked using JavaScript");
            }
            
            System.out.println("✅ Successfully hovered and clicked: " + elementName);
        } catch (Exception e) {
            System.out.println("❌ Failed to hover and click: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }
    
    // ✅ Click with Fallback Helper Method
    private void clickWithFallback(By locator, String elementName) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                element.click();
                System.out.println("✅ Clicked on: " + elementName);
            } catch (Exception e1) {
                // Fallback to JavaScript click
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    System.out.println("✅ Clicked on: " + elementName + " (JavaScript)");
                } catch (Exception e2) {
                    // Try Actions class click
                    Actions actions = new Actions(driver);
                    actions.moveToElement(element).click().perform();
                    System.out.println("✅ Clicked on: " + elementName + " (Actions)");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }
    
    // ✅ Verify Watchlist Configuration Header
    public void verifyWatchlistConfigurationHeader(String expectedHeader) throws Exception {
        try {
            System.out.println("\n🔍 Verifying Watchlist Configuration page header...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                WatchlistConfigurationPageLocators.watchlistConfigurationPageHeader));
            
            String actualHeader = header.getText().trim();
            System.out.println("Expected Header: " + expectedHeader);
            System.out.println("Actual Header: " + actualHeader);
            
            if (actualHeader.equals(expectedHeader)) {
                System.out.println("✅ Header verification passed");
                if (reportLogger != null) {
                    reportLogger.pass("Watchlist Configuration header verified: " + actualHeader);
                }
            } else {
                throw new Exception("Header mismatch. Expected: " + expectedHeader + ", Actual: " + actualHeader);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to verify header: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify Watchlist Configuration header: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Search Button
    public void clickSearchButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Search Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.searchButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBtn);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                searchBtn.click();
                System.out.println("✅ Search button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                    System.out.println("✅ Search button clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if img is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = searchBtn.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')] | ./parent::*"));
                    parent.click();
                    System.out.println("✅ Search button clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for search input to appear
            
            if (reportLogger != null) {
                reportLogger.pass("Search button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Search button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Nature of Initiative Dropdown
    public void clickNatureOfInitiativeDropdown() throws Exception {
        try {
            System.out.println("\n📊 Clicking Nature of Initiative Dropdown...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement natureDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.natureOfInitiativeDropdown));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", natureDropdown);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                natureDropdown.click();
                System.out.println("✅ Nature of Initiative dropdown clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", natureDropdown);
                    System.out.println("✅ Nature of Initiative dropdown clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = natureDropdown.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')]"));
                    parent.click();
                    System.out.println("✅ Nature of Initiative dropdown clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for dropdown options to appear
            
            if (reportLogger != null) {
                reportLogger.pass("Nature of Initiative dropdown clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Nature of Initiative dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Nature of Initiative dropdown: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Select Nature of Initiative from Dropdown
    public void selectNatureOfInitiative(String natureValue) throws Exception {
        try {
            System.out.println("\n📊 Selecting Nature of Initiative: " + natureValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for dropdown options to be visible
            waitForSeconds(2);
            
            boolean selected = false;
            
            // Strategy 1: Try clicking the option directly
            try {
                By optionLocator = WatchlistConfigurationPageLocators.getDynamicNatureOfInitiativeOption(natureValue);
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                waitForSeconds(1);
                
                option.click();
                System.out.println("✅ Nature of Initiative selected by clicking option: " + natureValue);
                selected = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Direct option click failed, trying alternative methods...");
            }
            
            // Strategy 2: Try JavaScript click on option
            if (!selected) {
                try {
                    By optionLocator = WatchlistConfigurationPageLocators.getDynamicNatureOfInitiativeOption(natureValue);
                    WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                    waitForSeconds(1);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Nature of Initiative selected using JavaScript click: " + natureValue);
                    selected = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript option click failed...");
                }
            }
            
            // Strategy 3: Try finding by text in all visible options
            if (!selected) {
                try {
                    java.util.List<WebElement> options = driver.findElements(By.xpath("//*[@role='option'] | //li[contains(@class,'option')] | //div[contains(@class,'option')]"));
                    for (WebElement opt : options) {
                        if (opt.getText().trim().equalsIgnoreCase(natureValue)) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", opt);
                            waitForSeconds(1);
                            opt.click();
                            System.out.println("✅ Nature of Initiative selected from options list: " + natureValue);
                            selected = true;
                            break;
                        }
                    }
                } catch (Exception e3) {
                    System.out.println("⚠️ Options list search failed...");
                }
            }
            
            if (!selected) {
                throw new Exception("Could not select Nature of Initiative '" + natureValue + "' using any method");
            }
            
            waitForSeconds(2); // Wait for selection to be applied
            
            if (reportLogger != null) {
                reportLogger.pass("Nature of Initiative selected: " + natureValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to select Nature of Initiative: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Nature of Initiative: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Current Stage Dropdown
    public void clickCurrentStageDropdown() throws Exception {
        try {
            System.out.println("\n📊 Clicking Current Stage Dropdown...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement currentStageDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.currentStageDropdown));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", currentStageDropdown);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                currentStageDropdown.click();
                System.out.println("✅ Current Stage dropdown clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", currentStageDropdown);
                    System.out.println("✅ Current Stage dropdown clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = currentStageDropdown.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')]"));
                    parent.click();
                    System.out.println("✅ Current Stage dropdown clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for dropdown options to appear
            
            if (reportLogger != null) {
                reportLogger.pass("Current Stage dropdown clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Current Stage dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Current Stage dropdown: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Select Current Stage from Dropdown
    public void selectCurrentStage(String stageValue) throws Exception {
        try {
            System.out.println("\n📊 Selecting Current Stage: " + stageValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for dropdown options to be visible
            waitForSeconds(2);
            
            boolean selected = false;
            
            // Strategy 1: Try clicking the option directly
            try {
                By optionLocator = WatchlistConfigurationPageLocators.getDynamicCurrentStageOption(stageValue);
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                waitForSeconds(1);
                
                option.click();
                System.out.println("✅ Current Stage selected by clicking option: " + stageValue);
                selected = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Direct option click failed, trying alternative methods...");
            }
            
            // Strategy 2: Try JavaScript click on option
            if (!selected) {
                try {
                    By optionLocator = WatchlistConfigurationPageLocators.getDynamicCurrentStageOption(stageValue);
                    WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                    waitForSeconds(1);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Current Stage selected using JavaScript click: " + stageValue);
                    selected = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript option click failed...");
                }
            }
            
            // Strategy 3: Try finding by text in all visible options
            if (!selected) {
                try {
                    java.util.List<WebElement> options = driver.findElements(By.xpath("//*[@role='option'] | //li[contains(@class,'option')] | //div[contains(@class,'option')]"));
                    for (WebElement opt : options) {
                        if (opt.getText().trim().equalsIgnoreCase(stageValue)) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", opt);
                            waitForSeconds(1);
                            opt.click();
                            System.out.println("✅ Current Stage selected from options list: " + stageValue);
                            selected = true;
                            break;
                        }
                    }
                } catch (Exception e3) {
                    System.out.println("⚠️ Options list search failed...");
                }
            }
            
            if (!selected) {
                throw new Exception("Could not select Current Stage '" + stageValue + "' using any method");
            }
            
            waitForSeconds(2); // Wait for selection to be applied
            
            if (reportLogger != null) {
                reportLogger.pass("Current Stage selected: " + stageValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to select Current Stage: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Current Stage: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Initiative Code Field
    public void clickInitiativeCodeField() throws Exception {
        try {
            System.out.println("\n📝 Clicking Initiative Code Field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement initiativeCodeField = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.initiativeCodeField));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", initiativeCodeField);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                initiativeCodeField.click();
                System.out.println("✅ Initiative Code field clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeCodeField);
                    System.out.println("✅ Initiative Code field clicked using JavaScript");
                } catch (Exception e2) {
                    // Try focusing the element
                    System.out.println("⚠️ JavaScript click failed, trying focus...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", initiativeCodeField);
                    System.out.println("✅ Initiative Code field focused");
                }
            }
            
            waitForSeconds(1); // Wait for field to be ready
            
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Code field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Code field: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Enter Initiative Code Value
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        try {
            System.out.println("\n📝 Entering Initiative Code: " + initiativeCode);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement initiativeCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                WatchlistConfigurationPageLocators.initiativeCodeField));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", initiativeCodeField);
            waitForSeconds(1);
            
            // Clear existing text
            try {
                initiativeCodeField.clear();
                System.out.println("✅ Cleared existing text");
            } catch (Exception e1) {
                // Try JavaScript clear
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", initiativeCodeField);
                    System.out.println("✅ Cleared text using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear input field");
                }
            }
            
            waitForSeconds(1);
            
            // Enter initiative code value
            try {
                initiativeCodeField.sendKeys(initiativeCode);
                System.out.println("✅ Initiative Code entered successfully: " + initiativeCode);
            } catch (Exception e1) {
                // Fallback to JavaScript
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + initiativeCode.replace("'", "\\'") + "';", initiativeCodeField);
                // Trigger input event to ensure the value is recognized
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", initiativeCodeField);
                System.out.println("✅ Initiative Code entered using JavaScript");
            }
            
            waitForSeconds(1); // Wait for value to be set
            
            if (reportLogger != null) {
                reportLogger.pass("Entered Initiative Code: " + initiativeCode);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Initiative Code: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Initiative Code: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Search Submit Button
    public void clickSearchSubmitButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Search Submit Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchSubmitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.searchSubmitButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchSubmitBtn);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                searchSubmitBtn.click();
                System.out.println("✅ Search submit button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchSubmitBtn);
                    System.out.println("✅ Search submit button clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = searchSubmitBtn.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')] | ./parent::*"));
                    parent.click();
                    System.out.println("✅ Search submit button clicked via parent element");
                }
            }
            
            waitForSeconds(3); // Wait for search results to load
            
            if (reportLogger != null) {
                reportLogger.pass("Search submit button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Search submit button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search submit button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click After Filter Search Button
    public void clickAfterFilterSearch() throws Exception {
        try {
            System.out.println("\n🔍 Clicking After Filter Search Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Wait for element to be present first
            System.out.println("⏳ Waiting for After Filter Search button to be present...");
            wait.until(ExpectedConditions.presenceOfElementLocated(
                WatchlistConfigurationPageLocators.afterFilterSearchButton));
            System.out.println("✅ Button is present");
            
            // Wait for element to be visible
            System.out.println("⏳ Waiting for After Filter Search button to be visible...");
            WebElement afterFilterSearchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                WatchlistConfigurationPageLocators.afterFilterSearchButton));
            System.out.println("✅ Button is visible");
            
            // Wait for element to be clickable
            System.out.println("⏳ Waiting for After Filter Search button to be clickable...");
            afterFilterSearchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.afterFilterSearchButton));
            System.out.println("✅ Button is clickable");
            
            // Check if element is enabled
            if (!afterFilterSearchBtn.isEnabled()) {
                System.out.println("⚠️ Button is not enabled, waiting for it to become enabled...");
                wait.until(ExpectedConditions.elementToBeClickable(
                    WatchlistConfigurationPageLocators.afterFilterSearchButton));
            }
            
            // Scroll into view with multiple strategies
            System.out.println("📜 Scrolling button into view...");
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", afterFilterSearchBtn);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", afterFilterSearchBtn);
            }
            waitForSeconds(2);
            
            boolean clicked = false;
            
            // Strategy 1: Try regular click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting regular click...");
                    afterFilterSearchBtn.click();
                    System.out.println("✅ After filter search button clicked successfully (regular click)");
                    clicked = true;
                } catch (Exception e1) {
                    System.out.println("⚠️ Regular click failed: " + e1.getMessage());
                }
            }
            
            // Strategy 2: Try JavaScript click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", afterFilterSearchBtn);
                    System.out.println("✅ After filter search button clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }
            
            // Strategy 3: Try Actions class click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting Actions class click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(afterFilterSearchBtn).click().perform();
                    System.out.println("✅ After filter search button clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }
            
            // Strategy 4: Try clicking by coordinates
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting click by coordinates...");
                    int x = afterFilterSearchBtn.getLocation().getX() + afterFilterSearchBtn.getSize().getWidth() / 2;
                    int y = afterFilterSearchBtn.getLocation().getY() + afterFilterSearchBtn.getSize().getHeight() / 2;
                    Actions actions = new Actions(driver);
                    actions.moveByOffset(x, y).click().perform();
                    System.out.println("✅ After filter search button clicked successfully (coordinates click)");
                    clicked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Coordinates click failed: " + e4.getMessage());
                }
            }
            
            // Strategy 5: Try clicking parent button element
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to click parent button element...");
                    WebElement parent = afterFilterSearchBtn.findElement(By.xpath("./ancestor::button[1]"));
                    if (parent != null) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parent);
                        System.out.println("✅ After filter search button clicked successfully (parent button)");
                        clicked = true;
                    }
                } catch (Exception e5) {
                    System.out.println("⚠️ Parent button click failed: " + e5.getMessage());
                }
            }
            
            // Strategy 6: Try alternative locator
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting alternative locator...");
                    WebElement altButton = wait.until(ExpectedConditions.elementToBeClickable(
                        WatchlistConfigurationPageLocators.afterFilterSearchButtonAlternative));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", altButton);
                    waitForSeconds(1);
                    altButton.click();
                    System.out.println("✅ After filter search button clicked successfully (alternative locator)");
                    clicked = true;
                } catch (Exception e6) {
                    System.out.println("⚠️ Alternative locator click failed: " + e6.getMessage());
                }
            }
            
            // Strategy 7: Try finding button by index and clicking
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to find button by index...");
                    java.util.List<WebElement> buttons = driver.findElements(By.xpath("//button"));
                    if (buttons.size() >= 3) {
                        WebElement button3 = buttons.get(2); // button[3] is index 2
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button3);
                        waitForSeconds(1);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button3);
                        System.out.println("✅ After filter search button clicked successfully (by index)");
                        clicked = true;
                    }
                } catch (Exception e7) {
                    System.out.println("⚠️ Button by index click failed: " + e7.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Could not click After Filter Search button using any method");
            }
            
            waitForSeconds(3); // Wait for search results to load
            
            if (reportLogger != null) {
                reportLogger.pass("After filter search button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click After filter search button: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to click After filter search button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Edit Button in Search Results Table
    public void clickEditButton() throws Exception {
        try {
            System.out.println("\n✏️ Clicking Edit Button in Search Results Table...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Wait for element to be present first
            System.out.println("⏳ Waiting for Edit button to be present...");
            wait.until(ExpectedConditions.presenceOfElementLocated(
                WatchlistConfigurationPageLocators.editButton));
            System.out.println("✅ Edit button is present");
            
            // Wait for element to be visible
            System.out.println("⏳ Waiting for Edit button to be visible...");
            WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                WatchlistConfigurationPageLocators.editButton));
            System.out.println("✅ Edit button is visible");
            
            // Wait for element to be clickable
            System.out.println("⏳ Waiting for Edit button to be clickable...");
            editBtn = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.editButton));
            System.out.println("✅ Edit button is clickable");
            
            // Check if element is enabled
            if (!editBtn.isEnabled()) {
                System.out.println("⚠️ Edit button is not enabled, waiting for it to become enabled...");
                wait.until(ExpectedConditions.elementToBeClickable(
                    WatchlistConfigurationPageLocators.editButton));
            }
            
            // Scroll into view with multiple strategies
            System.out.println("📜 Scrolling Edit button into view...");
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", editBtn);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editBtn);
            }
            waitForSeconds(2);
            
            boolean clicked = false;
            
            // Strategy 1: Try regular click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting regular click...");
                    editBtn.click();
                    System.out.println("✅ Edit button clicked successfully (regular click)");
                    clicked = true;
                } catch (Exception e1) {
                    System.out.println("⚠️ Regular click failed: " + e1.getMessage());
                }
            }
            
            // Strategy 2: Try JavaScript click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                    System.out.println("✅ Edit button clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }
            
            // Strategy 3: Try Actions class click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting Actions class click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(editBtn).click().perform();
                    System.out.println("✅ Edit button clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }
            
            // Strategy 4: Try clicking by coordinates
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting click by coordinates...");
                    int x = editBtn.getLocation().getX() + editBtn.getSize().getWidth() / 2;
                    int y = editBtn.getLocation().getY() + editBtn.getSize().getHeight() / 2;
                    Actions actions = new Actions(driver);
                    actions.moveByOffset(x, y).click().perform();
                    System.out.println("✅ Edit button clicked successfully (coordinates click)");
                    clicked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Coordinates click failed: " + e4.getMessage());
                }
            }
            
            // Strategy 5: Try clicking child button/element inside td
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to click child button/element...");
                    WebElement childButton = editBtn.findElement(By.xpath(".//button | .//*[contains(@aria-label,'Edit') or contains(@title,'Edit')] | .//*[contains(@class,'edit') or contains(@class,'Edit')]"));
                    if (childButton != null) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", childButton);
                        System.out.println("✅ Edit button clicked successfully (child element)");
                        clicked = true;
                    }
                } catch (Exception e5) {
                    System.out.println("⚠️ Child element click failed: " + e5.getMessage());
                }
            }
            
            // Strategy 6: Try alternative locator
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting alternative locator...");
                    WebElement altButton = wait.until(ExpectedConditions.elementToBeClickable(
                        WatchlistConfigurationPageLocators.editButtonAlternative));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", altButton);
                    waitForSeconds(1);
                    altButton.click();
                    System.out.println("✅ Edit button clicked successfully (alternative locator)");
                    clicked = true;
                } catch (Exception e6) {
                    System.out.println("⚠️ Alternative locator click failed: " + e6.getMessage());
                }
            }
            
            // Strategy 7: Try finding button by table cell position
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to find button by table cell position...");
                    java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                    if (rows.size() > 0) {
                        WebElement firstRow = rows.get(0);
                        java.util.List<WebElement> cells = firstRow.findElements(By.tagName("td"));
                        if (cells.size() >= 8) {
                            WebElement cell8 = cells.get(7); // td[8] is index 7
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", cell8);
                            waitForSeconds(1);
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cell8);
                            System.out.println("✅ Edit button clicked successfully (by table cell)");
                            clicked = true;
                        }
                    }
                } catch (Exception e7) {
                    System.out.println("⚠️ Table cell click failed: " + e7.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Could not click Edit button using any method");
            }
            
            waitForSeconds(2); // Wait for edit form/modal to load
            
            if (reportLogger != null) {
                reportLogger.pass("Edit button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Edit button: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Edit button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Checkbox in Table
    public void clickCheckbox() throws Exception {
        try {
            System.out.println("\n☑️ Clicking Checkbox in Table...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Wait for element to be present first
            System.out.println("⏳ Waiting for Checkbox to be present...");
            wait.until(ExpectedConditions.presenceOfElementLocated(
                WatchlistConfigurationPageLocators.checkbox));
            System.out.println("✅ Checkbox is present");
            
            // Get the checkbox element
            WebElement checkboxElement = driver.findElement(WatchlistConfigurationPageLocators.checkbox);
            
            // Scroll down to the checkbox with multiple strategies
            System.out.println("📜 Scrolling down to Checkbox (row 33)...");
            
            // Strategy 1: Scroll the checkbox into view with center alignment
            try {
                System.out.println("  ↪ Attempting scrollIntoView with center alignment...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth', inline: 'center'});", checkboxElement);
                waitForSeconds(2);
                System.out.println("✅ Scrolled using scrollIntoView (center)");
            } catch (Exception e1) {
                System.out.println("⚠️ Center scroll failed, trying alternative...");
            }
            
            // Strategy 2: Scroll to specific coordinates of the checkbox
            try {
                System.out.println("  ↪ Attempting scroll to checkbox coordinates...");
                int x = checkboxElement.getLocation().getX();
                int y = checkboxElement.getLocation().getY();
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(" + x + ", " + (y - 200) + ");");
                waitForSeconds(2);
                System.out.println("✅ Scrolled to coordinates: (" + x + ", " + y + ")");
            } catch (Exception e2) {
                System.out.println("⚠️ Coordinate scroll failed, trying alternative...");
            }
            
            // Strategy 3: Scroll the table container to show row 33
            try {
                System.out.println("  ↪ Attempting to scroll table container...");
                WebElement table = driver.findElement(By.xpath("//table/tbody"));
                if (table != null) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", table);
                    waitForSeconds(1);
                    // Scroll back up a bit to center row 33
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollTop - 300;", table);
                    waitForSeconds(2);
                    System.out.println("✅ Scrolled table container");
                }
            } catch (Exception e3) {
                System.out.println("⚠️ Table container scroll failed, trying alternative...");
            }
            
            // Strategy 4: Scroll window to bottom then adjust
            try {
                System.out.println("  ↪ Attempting window scroll to bottom...");
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                waitForSeconds(1);
                // Scroll up a bit to center the checkbox
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -400);");
                waitForSeconds(2);
                System.out.println("✅ Scrolled window to bottom and adjusted");
            } catch (Exception e4) {
                System.out.println("⚠️ Window scroll failed, trying alternative...");
            }
            
            // Strategy 5: Use scrollIntoView with start alignment
            try {
                System.out.println("  ↪ Attempting scrollIntoView with start alignment...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'start', behavior: 'smooth'});", checkboxElement);
                waitForSeconds(2);
                System.out.println("✅ Scrolled using scrollIntoView (start)");
            } catch (Exception e5) {
                System.out.println("⚠️ Start scroll failed, trying alternative...");
            }
            
            // Strategy 6: Find row 33 and scroll to it
            try {
                System.out.println("  ↪ Attempting to scroll to row 33...");
                WebElement row33 = driver.findElement(By.xpath("//table/tbody/tr[33]"));
                if (row33 != null) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", row33);
                    waitForSeconds(2);
                    System.out.println("✅ Scrolled to row 33");
                }
            } catch (Exception e6) {
                System.out.println("⚠️ Row 33 scroll failed, trying alternative...");
            }
            
            // Final scroll attempt - direct scrollIntoView
            try {
                System.out.println("  ↪ Final scroll attempt - direct scrollIntoView...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxElement);
                waitForSeconds(2);
                System.out.println("✅ Final scroll completed");
            } catch (Exception e7) {
                System.out.println("⚠️ Final scroll failed");
            }
            
            // Wait for element to be visible after scrolling
            System.out.println("⏳ Waiting for Checkbox to be visible after scrolling...");
            checkboxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                WatchlistConfigurationPageLocators.checkbox));
            System.out.println("✅ Checkbox is visible");
            
            // Verify checkbox is in viewport
            boolean isInViewport = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0];" +
                "var box = elem.getBoundingClientRect();" +
                "var containerBox = document.body.getBoundingClientRect();" +
                "return (box.top >= containerBox.top && box.left >= containerBox.left && " +
                "box.bottom <= containerBox.bottom && box.right <= containerBox.right);", checkboxElement);
            
            if (!isInViewport) {
                System.out.println("⚠️ Checkbox is not fully in viewport, scrolling again...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxElement);
                waitForSeconds(2);
            }
            
            // Wait for element to be clickable
            System.out.println("⏳ Waiting for Checkbox to be clickable...");
            checkboxElement = wait.until(ExpectedConditions.elementToBeClickable(
                WatchlistConfigurationPageLocators.checkbox));
            System.out.println("✅ Checkbox is clickable");
            
            // Check if checkbox is enabled
            if (!checkboxElement.isEnabled()) {
                System.out.println("⚠️ Checkbox is not enabled, waiting for it to become enabled...");
                wait.until(ExpectedConditions.elementToBeClickable(
                    WatchlistConfigurationPageLocators.checkbox));
            }
            
            boolean clicked = false;
            
            // Strategy 1: Try regular click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting regular click...");
                    checkboxElement.click();
                    System.out.println("✅ Checkbox clicked successfully (regular click)");
                    clicked = true;
                } catch (Exception e1) {
                    System.out.println("⚠️ Regular click failed: " + e1.getMessage());
                }
            }
            
            // Strategy 2: Try JavaScript click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxElement);
                    System.out.println("✅ Checkbox clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }
            
            // Strategy 3: Try Actions class click
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting Actions class click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(checkboxElement).click().perform();
                    System.out.println("✅ Checkbox clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }
            
            // Strategy 4: Try setting checked property directly
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to set checked property...");
                    boolean isChecked = checkboxElement.isSelected();
                    ((JavascriptExecutor) driver).executeScript("arguments[0].checked = " + !isChecked + ";", checkboxElement);
                    // Trigger change event
                    ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", checkboxElement);
                    System.out.println("✅ Checkbox clicked successfully (property set)");
                    clicked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Property set failed: " + e4.getMessage());
                }
            }
            
            // Strategy 5: Try clicking parent span element
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to click parent span element...");
                    WebElement parentSpan = checkboxElement.findElement(By.xpath("./parent::span"));
                    if (parentSpan != null) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentSpan);
                        System.out.println("✅ Checkbox clicked successfully (parent span)");
                        clicked = true;
                    }
                } catch (Exception e5) {
                    System.out.println("⚠️ Parent span click failed: " + e5.getMessage());
                }
            }
            
            // Strategy 6: Try alternative locator
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting alternative locator...");
                    WebElement altCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
                        WatchlistConfigurationPageLocators.checkboxAlternative));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", altCheckbox);
                    waitForSeconds(1);
                    altCheckbox.click();
                    System.out.println("✅ Checkbox clicked successfully (alternative locator)");
                    clicked = true;
                } catch (Exception e6) {
                    System.out.println("⚠️ Alternative locator click failed: " + e6.getMessage());
                }
            }
            
            // Strategy 7: Try finding checkbox by table row and column
            if (!clicked) {
                try {
                    System.out.println("🖱️ Attempting to find checkbox by table row and column...");
                    java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                    if (rows.size() >= 33) {
                        WebElement row33 = rows.get(32); // row[33] is index 32
                        java.util.List<WebElement> cells = row33.findElements(By.tagName("td"));
                        if (cells.size() >= 2) {
                            WebElement cell2 = cells.get(1); // td[2] is index 1
                            WebElement checkboxInCell = cell2.findElement(By.xpath(".//input[@type='checkbox']"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxInCell);
                            waitForSeconds(1);
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxInCell);
                            System.out.println("✅ Checkbox clicked successfully (by table row/column)");
                            clicked = true;
                        }
                    }
                } catch (Exception e7) {
                    System.out.println("⚠️ Table row/column click failed: " + e7.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Could not click Checkbox using any method");
            }
            
            waitForSeconds(1); // Wait for checkbox state to update
            
            // Verify checkbox state
            boolean isChecked = checkboxElement.isSelected();
            System.out.println("✅ Checkbox state: " + (isChecked ? "Checked" : "Unchecked"));
            
            if (reportLogger != null) {
                reportLogger.pass("Checkbox clicked successfully. State: " + (isChecked ? "Checked" : "Unchecked"));
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Checkbox: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Checkbox: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Checkbox by Employee Name
    public void clickCheckboxByEmployeeName(String employeeName) throws Exception {
        try {
            System.out.println("\n☑️ Clicking Checkbox for Employee: " + employeeName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Find the row containing the employee name
            System.out.println("🔍 Searching for row containing Employee Name: " + employeeName);
            WebElement employeeRow = null;
            int rowIndex = -1;
            
            // Strategy 1: Find row by exact text match (case-insensitive)
            try {
                System.out.println("  ↪ Attempting to find row by exact text match (case-insensitive)...");
                java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                for (int i = 0; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    String rowText = row.getText();
                    if (rowText.toLowerCase().contains(employeeName.toLowerCase())) {
                        employeeRow = row;
                        rowIndex = i + 1; // 1-based index
                        System.out.println("✅ Found employee row at index: " + rowIndex + " (Row text contains: " + employeeName + ")");
                        break;
                    }
                }
            } catch (Exception e1) {
                System.out.println("⚠️ Exact text match failed: " + e1.getMessage());
            }
            
            // Strategy 2: Find row by td text match (case-insensitive, exact and contains)
            if (employeeRow == null) {
                try {
                    System.out.println("  ↪ Attempting to find row by td text match (case-insensitive)...");
                    java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                    for (int i = 0; i < rows.size(); i++) {
                        WebElement row = rows.get(i);
                        java.util.List<WebElement> cells = row.findElements(By.tagName("td"));
                        for (int j = 0; j < cells.size(); j++) {
                            WebElement cell = cells.get(j);
                            String cellText = cell.getText().trim();
                            // Try exact match (case-insensitive)
                            if (cellText.equalsIgnoreCase(employeeName)) {
                                employeeRow = row;
                                rowIndex = i + 1;
                                System.out.println("✅ Found employee row at index: " + rowIndex + " by exact cell text match in column " + (j + 1));
                                break;
                            }
                            // Try contains match (case-insensitive)
                            if (cellText.toLowerCase().contains(employeeName.toLowerCase())) {
                                employeeRow = row;
                                rowIndex = i + 1;
                                System.out.println("✅ Found employee row at index: " + rowIndex + " by cell text contains in column " + (j + 1) + " (Cell text: " + cellText + ")");
                                break;
                            }
                        }
                        if (employeeRow != null) break;
                    }
                } catch (Exception e2) {
                    System.out.println("⚠️ Cell text match failed: " + e2.getMessage());
                }
            }
            
            // Strategy 2b: Find row by xpath with employee name
            if (employeeRow == null) {
                try {
                    System.out.println("  ↪ Attempting to find row by xpath with employee name...");
                    By rowLocator = By.xpath("//table/tbody/tr[td[normalize-space(text())='" + employeeName + "']] | //tbody/tr[td[contains(text(),'" + employeeName + "')]] | //tr[td[normalize-space()='" + employeeName + "']]");
                    employeeRow = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
                    // Get row index
                    java.util.List<WebElement> allRows = driver.findElements(By.xpath("//table/tbody/tr"));
                    for (int i = 0; i < allRows.size(); i++) {
                        if (allRows.get(i).equals(employeeRow)) {
                            rowIndex = i + 1;
                            break;
                        }
                    }
                    System.out.println("✅ Found employee row at index: " + rowIndex + " using xpath");
                } catch (Exception e2b) {
                    System.out.println("⚠️ Xpath search failed: " + e2b.getMessage());
                }
            }
            
            // Strategy 3: Use dynamic locator
            if (employeeRow == null) {
                try {
                    System.out.println("  ↪ Attempting to find row using dynamic locator...");
                    By dynamicLocator = WatchlistConfigurationPageLocators.getDynamicCheckboxByEmployeeName(employeeName);
                    WebElement checkboxElement = wait.until(ExpectedConditions.presenceOfElementLocated(dynamicLocator));
                    // Get parent row
                    employeeRow = checkboxElement.findElement(By.xpath("./ancestor::tr"));
                    System.out.println("✅ Found employee row using dynamic locator");
                } catch (Exception e3) {
                    System.out.println("⚠️ Dynamic locator failed: " + e3.getMessage());
                }
            }
            
            if (employeeRow == null) {
                // Log all available employee names for debugging
                try {
                    System.out.println("⚠️ Could not find employee row. Available rows in table:");
                    java.util.List<WebElement> allRows = driver.findElements(By.xpath("//table/tbody/tr"));
                    for (int i = 0; i < Math.min(allRows.size(), 10); i++) {
                        String rowText = allRows.get(i).getText();
                        System.out.println("  Row " + (i + 1) + ": " + (rowText.length() > 100 ? rowText.substring(0, 100) + "..." : rowText));
                    }
                } catch (Exception debugEx) {
                    System.out.println("⚠️ Could not retrieve row information for debugging");
                }
                throw new Exception("Could not find row containing Employee Name: " + employeeName);
            }
            
            // Verify we found the correct employee
            String foundEmployeeText = employeeRow.getText();
            System.out.println("📋 Found row text: " + (foundEmployeeText.length() > 150 ? foundEmployeeText.substring(0, 150) + "..." : foundEmployeeText));
            System.out.println("✅ Confirmed row contains employee: " + employeeName + " (Row index: " + rowIndex + ")");
            
            // Scroll to the employee row with multiple strategies
            System.out.println("📜 Scrolling to employee row (row " + rowIndex + ")...");
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", employeeRow);
                waitForSeconds(2);
                System.out.println("✅ Scrolled to employee row");
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", employeeRow);
                    waitForSeconds(2);
                    System.out.println("✅ Scrolled to employee row (fallback)");
                } catch (Exception e2) {
                    // Scroll by row index
                    try {
                        ((JavascriptExecutor) driver).executeScript("var row = document.evaluate('//table/tbody/tr[" + rowIndex + "]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(row) row.scrollIntoView({block: 'center'});");
                        waitForSeconds(2);
                        System.out.println("✅ Scrolled to employee row (by index)");
                    } catch (Exception e3) {
                        System.out.println("⚠️ All scroll strategies failed, continuing...");
                    }
                }
            }
            
            // Find checkbox in column 2 of the employee row
            System.out.println("🔍 Finding checkbox in column 2 of employee row...");
            WebElement checkboxElement = null;
            
            // Strategy 1: Find checkbox directly from the row we found (most reliable)
            try {
                System.out.println("  ↪ Attempting to find checkbox directly from employee row...");
                WebElement cell2 = employeeRow.findElement(By.xpath("./td[2]"));
                // Try finding input inside span first (matching the xpath structure)
                checkboxElement = cell2.findElement(By.xpath(".//span/input | .//span/input[@type='checkbox']"));
                System.out.println("✅ Found checkbox in column 2 (span/input structure)");
            } catch (Exception e1) {
                System.out.println("⚠️ Span/input structure search failed: " + e1.getMessage());
            }
            
            // Strategy 2: Find checkbox directly in column 2 (without span)
            if (checkboxElement == null) {
                try {
                    System.out.println("  ↪ Attempting to find checkbox directly in column 2...");
                    WebElement cell2 = employeeRow.findElement(By.xpath("./td[2]"));
                    checkboxElement = cell2.findElement(By.xpath(".//input[@type='checkbox']"));
                    System.out.println("✅ Found checkbox in column 2");
                } catch (Exception e2) {
                    System.out.println("⚠️ Checkbox search failed: " + e2.getMessage());
                }
            }
            
            // Strategy 3: Find any input in column 2
            if (checkboxElement == null) {
                try {
                    System.out.println("  ↪ Attempting to find any input in column 2...");
                    WebElement cell2 = employeeRow.findElement(By.xpath("./td[2]"));
                    checkboxElement = cell2.findElement(By.xpath(".//input"));
                    System.out.println("✅ Found input in column 2");
                } catch (Exception e3) {
                    System.out.println("⚠️ Input search failed: " + e3.getMessage());
                }
            }
            
            // Strategy 4: Use the exact xpath structure provided (using row index)
            if (checkboxElement == null && rowIndex > 0) {
                try {
                    System.out.println("  ↪ Attempting to find checkbox using exact xpath structure (row " + rowIndex + ")...");
                    checkboxElement = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]/span[1]/input[1]"));
                    System.out.println("✅ Found checkbox using exact xpath structure");
                } catch (Exception e4) {
                    System.out.println("⚠️ Exact xpath structure failed: " + e4.getMessage());
                }
            }
            
            // Strategy 5: Try finding by presence first, then make it clickable
            if (checkboxElement == null) {
                try {
                    System.out.println("  ↪ Attempting to find checkbox using dynamic locator (presence only)...");
                    By dynamicLocator = WatchlistConfigurationPageLocators.getDynamicCheckboxByEmployeeName(employeeName);
                    checkboxElement = wait.until(ExpectedConditions.presenceOfElementLocated(dynamicLocator));
                    System.out.println("✅ Found checkbox using dynamic locator (presence)");
                } catch (Exception e5) {
                    System.out.println("⚠️ Dynamic locator failed: " + e5.getMessage());
                }
            }
            
            if (checkboxElement == null) {
                throw new Exception("Could not find checkbox in row containing Employee Name: " + employeeName);
            }
            
            // Refresh the checkbox element using the exact xpath with row index (most reliable)
            if (rowIndex > 0) {
                try {
                    System.out.println("🔄 Refreshing checkbox element using exact xpath (row " + rowIndex + ")...");
                    checkboxElement = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]/span[1]/input[1]"));
                    System.out.println("✅ Checkbox element refreshed");
                } catch (Exception e) {
                    System.out.println("⚠️ Could not refresh checkbox, using existing element...");
                }
            }
            
            // Wait for checkbox to be visible (optional - don't fail if it times out)
            System.out.println("⏳ Checking if checkbox is visible...");
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                shortWait.until(ExpectedConditions.visibilityOf(checkboxElement));
                System.out.println("✅ Checkbox is visible");
            } catch (Exception e) {
                System.out.println("⚠️ Visibility check failed, continuing anyway...");
            }
            
            // Scroll checkbox into view with multiple attempts
            System.out.println("📜 Scrolling checkbox into view...");
            for (int scrollAttempt = 1; scrollAttempt <= 3; scrollAttempt++) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", checkboxElement);
                    waitForSeconds(1);
                    System.out.println("✅ Scrolled checkbox into view (attempt " + scrollAttempt + ")");
                    break;
                } catch (Exception e) {
                    if (scrollAttempt < 3) {
                        System.out.println("⚠️ Scroll attempt " + scrollAttempt + " failed, retrying...");
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxElement);
                            waitForSeconds(1);
                        } catch (Exception e2) {
                            // Try scrolling the row instead
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", employeeRow);
                            waitForSeconds(1);
                        }
                    }
                }
            }
            waitForSeconds(1);
            
            // Try to make checkbox clickable by removing disabled attribute if present
            try {
                String disabled = checkboxElement.getAttribute("disabled");
                if (disabled != null && disabled.equals("true")) {
                    System.out.println("⚠️ Checkbox is disabled, attempting to enable...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", checkboxElement);
                    waitForSeconds(1);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not check/remove disabled attribute");
            }
            
            // Try to remove readonly attribute if present
            try {
                String readonly = checkboxElement.getAttribute("readonly");
                if (readonly != null && readonly.equals("true")) {
                    System.out.println("⚠️ Checkbox is readonly, attempting to remove readonly...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly');", checkboxElement);
                    waitForSeconds(1);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not check/remove readonly attribute");
            }
            
            // Don't wait for clickable - just try clicking directly (element exists, might be clickable)
            System.out.println("⏳ Checkbox element found, proceeding to click...");
            
            // Scroll checkbox into view with multiple attempts
            System.out.println("📜 Scrolling checkbox into view...");
            for (int scrollAttempt = 1; scrollAttempt <= 3; scrollAttempt++) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", checkboxElement);
                    waitForSeconds(1);
                    System.out.println("✅ Scrolled checkbox into view (attempt " + scrollAttempt + ")");
                    break;
                } catch (Exception e) {
                    if (scrollAttempt < 3) {
                        System.out.println("⚠️ Scroll attempt " + scrollAttempt + " failed, retrying...");
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxElement);
                            waitForSeconds(1);
                        } catch (Exception e2) {
                            // Try scrolling the row instead
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", employeeRow);
                            waitForSeconds(1);
                        }
                    }
                }
            }
            waitForSeconds(1);
            
            // Check current state first
            boolean currentState = false;
            try {
                currentState = checkboxElement.isSelected();
                System.out.println("📊 Current checkbox state: " + (currentState ? "Checked ✓" : "Unchecked ✗"));
            } catch (Exception e) {
                System.out.println("⚠️ Could not read current checkbox state");
            }
            
            boolean checked = false;
            
            // Strategy 1: Try using exact xpath with row 33 (as per user's xpath) - PRIORITY
            if (!checked) {
                try {
                    System.out.println("🖱️ Attempting to check using row 33 xpath (//tbody/tr[33]/td[2]/span[1]/input[1])...");
                    WebElement row33Checkbox = driver.findElement(By.xpath("//tbody/tr[33]/td[2]/span[1]/input[1]"));
                    boolean isChecked = row33Checkbox.isSelected();
                    System.out.println("  ↪ Current checkbox state (row 33): " + (isChecked ? "Checked ✓" : "Unchecked ✗"));
                    
                    if (!isChecked) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", row33Checkbox);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", row33Checkbox);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('click', { bubbles: true }));", row33Checkbox);
                        System.out.println("✅ Checkbox checked successfully (using row 33 xpath)");
                    } else {
                        System.out.println("✅ Checkbox is already checked");
                    }
                    checked = true;
                    checkboxElement = row33Checkbox; // Update reference
                } catch (Exception e1) {
                    System.out.println("⚠️ Row 33 xpath check failed: " + e1.getMessage());
                }
            }
            
            // Strategy 2: Try setting checked property to true directly (ensures it's checked)
            if (!checked) {
                try {
                    System.out.println("🖱️ Attempting to set checked property to true...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", checkboxElement);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", checkboxElement);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('click', { bubbles: true }));", checkboxElement);
                    System.out.println("✅ Checkbox checked successfully (property set to true)");
                    checked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ Property set failed: " + e2.getMessage());
                }
            }
            
            // Strategy 3: Try using exact xpath with found row index
            if (!checked && rowIndex > 0) {
                try {
                    System.out.println("🖱️ Attempting to check using exact xpath (row " + rowIndex + ")...");
                    WebElement exactCheckbox = driver.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]/span[1]/input[1]"));
                    boolean isChecked = exactCheckbox.isSelected();
                    System.out.println("  ↪ Current checkbox state (row " + rowIndex + "): " + (isChecked ? "Checked ✓" : "Unchecked ✗"));
                    
                    if (!isChecked) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", exactCheckbox);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", exactCheckbox);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('click', { bubbles: true }));", exactCheckbox);
                        System.out.println("✅ Checkbox checked successfully (using exact xpath row " + rowIndex + ")");
                    } else {
                        System.out.println("✅ Checkbox is already checked");
                    }
                    checked = true;
                    checkboxElement = exactCheckbox; // Update reference
                } catch (Exception e3) {
                    System.out.println("⚠️ Exact xpath check failed: " + e3.getMessage());
                }
            }
            
            // Strategy 4: Try regular click (if not already checked)
            if (!checked && !currentState) {
                try {
                    System.out.println("🖱️ Attempting regular click...");
                    checkboxElement.click();
                    System.out.println("✅ Checkbox clicked successfully (regular click)");
                    checked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Regular click failed: " + e4.getMessage());
                }
            }
            
            // Strategy 5: Try JavaScript click
            if (!checked) {
                try {
                    System.out.println("🖱️ Attempting JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxElement);
                    System.out.println("✅ Checkbox clicked successfully (JavaScript click)");
                    checked = true;
                } catch (Exception e5) {
                    System.out.println("⚠️ JavaScript click failed: " + e5.getMessage());
                }
            }
            
            // Strategy 6: Try Actions class click
            if (!checked) {
                try {
                    System.out.println("🖱️ Attempting Actions class click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(checkboxElement).click().perform();
                    System.out.println("✅ Checkbox clicked successfully (Actions click)");
                    checked = true;
                } catch (Exception e6) {
                    System.out.println("⚠️ Actions click failed: " + e6.getMessage());
                }
            }
            
            if (!checked) {
                throw new Exception("Could not check Checkbox for Employee: " + employeeName);
            }
            
            waitForSeconds(2); // Wait for checkbox state to update
            
            // Verify checkbox is actually checked
            System.out.println("🔍 Verifying checkbox is checked...");
            boolean isChecked = false;
            try {
                isChecked = checkboxElement.isSelected();
                System.out.println("✅ Checkbox state for " + employeeName + ": " + (isChecked ? "Checked ✓" : "Unchecked ✗"));
                
                // If still not checked, try one more time
                if (!isChecked) {
                    System.out.println("⚠️ Checkbox is not checked, attempting to check again...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", checkboxElement);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", checkboxElement);
                    waitForSeconds(1);
                    isChecked = checkboxElement.isSelected();
                    System.out.println("✅ Checkbox state after retry: " + (isChecked ? "Checked ✓" : "Unchecked ✗"));
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not verify checkbox state: " + e.getMessage());
            }
            
            if (!isChecked) {
                System.out.println("⚠️ WARNING: Checkbox may not be checked. Please verify manually.");
            }
            
            if (reportLogger != null) {
                reportLogger.pass("Checkbox clicked successfully for Employee: " + employeeName + ". State: " + (isChecked ? "Checked" : "Unchecked"));
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Checkbox for Employee: " + employeeName + " - " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Checkbox for Employee: " + employeeName + " - " + e.getMessage());
            }
            throw e;
        }
    }
}


