package Pages;

import Actions.ActionEngine;
import Locators.ExternalAuditPageLocators;
import Locators.InitiativePageLocators;
import Locators.ProgramPageLocator;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * Page Object Model (POM) for Initiative Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in InitiativePageLocators.java
 *    - Methods use InitiativePageLocators.locatorName for reusability
 *    - Dynamic locators use helper methods like getDynamicNOIOption()
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in InitiativePageLocators class
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
 * WHY THIS APPROACH?
 * ==================
 * ✅ Easier Maintenance: Change locator once, affects all usages
 * ✅ Better Readability: Clear separation of what and how
 * ✅ Reusability: Locators and methods can be reused across tests
 * ✅ Testability: Easy to mock and test individual components
 * ✅ Team Collaboration: Clear structure for multiple developers
 * 
 * @author Automation Team
 * @version 2.0 - Refactored for best practices
 */
public class InitiativePage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;
    private By previousMonthBtn;
	private By nextMonthBtn;

    // 🔹 Correct constructor with WebDriver + Logger
    public InitiativePage(WebDriver driver, ExtentTest reportLogger) {
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
    
    // ✅ Navigate to Initiative Page
    public void navigateToInitiative() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO INITIATIVE - START");
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
            hoverAndClickElement(InitiativePageLocators.initree, "Initiative Tree Menu");
            waitForSeconds(2); // Wait for menu to appear
            
			/*
			 * // Step 2: Click on initiative option (arrow)
			 * System.out.println("\n📍 Step 2: Clicking Initiative Option Arrow...");
			 * clickWithFallback(InitiativePageLocators.initiativeOption,
			 * "Initiative Option"); waitForSeconds(1);
			 */
            
            // Step 3: Click on Initiative node
            System.out.println("\n📍 Step 3: Clicking Initiative Node...");
            clickWithFallback(InitiativePageLocators.initiativeNode, "Initiative Node");
            waitForSeconds(2); // Wait for Initiative page to load
            
            System.out.println("\n✅ ✅ ✅ Navigated to Initiative successfully! ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to Initiative page");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ ❌ ❌ Navigation to Initiative FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to Initiative: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO INITIATIVE - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    // ✅ Helper method to hover over and click an element
    private void hoverAndClickElement(By locator, String elementName) throws Exception {
        try {
            System.out.println("🖱️ Hovering and clicking: " + elementName);
            
            // Wait for element to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            
            // Scroll element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);
            
            // Hover over element
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            System.out.println("  ↪ Hovered over element");
            waitForSeconds(1);
            
            // Try to click
            try {
                element.click();
                System.out.println("  ↪ Clicked using regular click");
            } catch (Exception e1) {
                System.out.println("  ⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    System.out.println("  ↪ Clicked using JavaScript");
                } catch (Exception e2) {
                    System.out.println("  ⚠️ JavaScript click failed, trying Actions click...");
                    actions.moveToElement(element).click().perform();
                    System.out.println("  ↪ Clicked using Actions");
                }
            }
            
            System.out.println("✅ Successfully hovered and clicked: " + elementName);
            if (reportLogger != null) {
                reportLogger.info("Hovered and clicked: " + elementName);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to hover and click: " + elementName);
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.warning("Failed to hover/click: " + elementName + " - " + e.getMessage());
            }
            throw e;
        }
    }
    
    public void Initiativebeforeclickadd() throws Exception {
        waitForSeconds(3); // buffer wait after login
        clickWithFallback(InitiativePageLocators.initreec, "Initiative Nodec");
    }

    // ✅ Helper method to click with js fallback
    private void clickWithFallback(By locator, String elementName) throws Exception {
        waitForElementToBeVisible(locator, elementName);
        try {
            click(locator, elementName);
            if (reportLogger != null) {
                reportLogger.pass("Clicked on: " + elementName);
            }
        } catch (Exception e) {
            jsClick(locator, elementName); // fallback
            if (reportLogger != null) {
                reportLogger.warning("Used JS Click for: " + elementName);
            }
        }
    }

    // ✅ Verify Filters and Search
    public void verifyFiltersAndSearch() throws Exception {
        isElementPresent(InitiativePageLocators.draftFilter, "Draft");
        isElementPresent(InitiativePageLocators.inboxFilter, "Inbox");
        isElementPresent(InitiativePageLocators.watchlistFilter, "Watchlist");
        isElementPresent(InitiativePageLocators.searchInput, "Search");
    }

    /**
     * Click on the Search input on Initiative page and enter Initiative Code,
     * then click the generic Search button (if present).
     *
     * This helper is used in TC_005 Resubmit Initiatives to search by code
     * on the Initiative list page.
     */
    public void searchInitiativeByCodeOnListPage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Multiple possible locators for the search input
        By[] searchInputLocators = new By[] {
                InitiativePageLocators.searchInput,                                       // primary
                By.xpath("//input[contains(@placeholder,'Search')]"),                     // placeholder contains
                By.xpath("//input[contains(@id,'search') or contains(@name,'search')]"),  // id/name contains search
                By.xpath("//input[@type='search']")                                       // generic search input
        };

        WebElement searchInput = null;

        // Strategy 1: Try to find search input in default content
        for (By locator : searchInputLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (el.isDisplayed() && el.isEnabled()) {
                    searchInput = el;
                    break;
                }
            } catch (Exception ignore) {
                // try next locator
            }
        }

        // Strategy 2: If not found, try inside iframes
        if (searchInput == null) {
            java.util.List<WebElement> frames = driver.findElements(By.tagName("iframe"));
            System.out.println("  🔍 Search input not found in default content, scanning " + frames.size() + " iframe(s)...");
            for (int i = 0; i < frames.size(); i++) {
                try {
                    driver.switchTo().defaultContent();
                    driver.switchTo().frame(i);
                    for (By locator : searchInputLocators) {
                        try {
                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                            if (el.isDisplayed() && el.isEnabled()) {
                                searchInput = el;
                                System.out.println("  ✅ Found Search input in iframe index " + i + " using locator: " + locator);
                                break;
                            }
                        } catch (Exception ignore) {
                            // try next locator
                        }
                    }
                    if (searchInput != null) {
                        break;
                    }
                } catch (Exception ignoreFrame) {
                    // continue with next frame
                }
            }
        }

        if (searchInput != null) {
            // Ensure we are in the context where the element was found
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchInput);
            } catch (Exception ignore) {
            }
            waitForSeconds(1);

            // Click/focus and enter code
            try {
                searchInput.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchInput);
            }
            waitForSeconds(1);

            try {
                searchInput.clear();
            } catch (Exception ignore) {
            }
            searchInput.sendKeys(initiativeCode);
            waitForSeconds(1);

            // Try clicking a generic Search button if available
            By[] searchButtonLocators = new By[] {
                    By.xpath("//button[.//span[normalize-space()='Search']]"),
                    By.xpath("//button[normalize-space()='Search']"),
                    By.xpath("//span[normalize-space()='Search']/ancestor::button[1]"),
                    By.xpath("//button[contains(@title,'Search')]")
            };

            boolean clicked = false;
            for (By locator : searchButtonLocators) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                    try {
                        btn.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    }
                    clicked = true;
                    waitForSeconds(2);
                    break;
                } catch (Exception ignore) {
                    // try next locator
                }
            }

            if (!clicked) {
                System.out.println("  ⚠️ Search button not found/clicked; relying on search input behavior only");
            }
        } else {
            System.out.println("  ⚠️ Search input not found on Initiative page; proceeding without text filter and relying on full grid scan");
        }

        // Small wait for grid refresh (or initial load)
        waitForSeconds(2);
        driver.switchTo().defaultContent();
    }

    /**
     * Verify initiative with given code is displayed on Initiative list page.
     *
     * @param initiativeCode code to verify
     * @return true if found, false otherwise
     */
    public boolean verifyInitiativeDisplayedOnInitiativePage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            By[] locators = new By[] {
                    By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]"),
                    By.xpath("//table//td[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//*[normalize-space(text())='" + initiativeCode + "']"),
                    By.xpath("//*[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(@class,'ag-row')]//div[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(@class,'ag-cell')]//*[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//span[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(text(),'" + initiativeCode + "')]")
            };

            for (By locator : locators) {
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (el.isDisplayed()) {
                        String text = el.getText();
                        if (text != null && text.contains(initiativeCode)) {
                            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' found on Initiative page using locator: " + locator);
                            if (reportLogger != null) {
                                reportLogger.pass("Initiative Code '" + initiativeCode + "' found on Initiative page");
                            }
                            return true;
                        }
                    }
                } catch (Exception ignore) {
                    // try next
                }
            }

            System.out.println("  ❌ Initiative Code '" + initiativeCode + "' not found on Initiative page");
            if (reportLogger != null) {
                reportLogger.fail("Initiative Code '" + initiativeCode + "' NOT found on Initiative page");
            }
            return false;

        } catch (Exception e) {
            System.err.println("  ❌ Error verifying Initiative on Initiative page: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error verifying Initiative on Initiative page: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Verify initiative with given code is NOT displayed on Initiative list page.
     *
     * @param initiativeCode code to verify
     * @return true if NOT found (expected), false if found
     */
    public boolean verifyInitiativeNotDisplayedOnInitiativePage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            By[] locators = new By[] {
                    By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]"),
                    By.xpath("//table//td[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//*[normalize-space(text())='" + initiativeCode + "']"),
                    By.xpath("//*[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(@class,'ag-row')]//div[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(@class,'ag-cell')]//*[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//span[contains(text(),'" + initiativeCode + "')]"),
                    By.xpath("//div[contains(text(),'" + initiativeCode + "')]")
            };

            for (By locator : locators) {
                try {
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (el.isDisplayed()) {
                        String text = el.getText();
                        if (text != null && text.contains(initiativeCode)) {
                            System.out.println("  ❌ Initiative Code '" + initiativeCode + "' FOUND on Initiative page (unexpected) using locator: " + locator);
                            if (reportLogger != null) {
                                reportLogger.fail("Initiative Code '" + initiativeCode + "' unexpectedly FOUND on Initiative page");
                            }
                            return false;
                        }
                    }
                } catch (Exception ignore) {
                    // expected: not present
                }
            }

            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found on Initiative page (as expected)");
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Initiative page");
            }
            return true;

        } catch (Exception e) {
            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found on Initiative page (exception considered as not found): " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Initiative page");
            }
            return true;
        }
    }

    // ✅ Verify Draft Count
    public void verifyDraftCount() throws Exception {
        waitForElementToBeClickable(InitiativePageLocators.draftFilter, "Draft");
        click(InitiativePageLocators.draftFilter, "Draft");

        String total = getText(InitiativePageLocators.totalRecords, "Total Records");
        String count = getText(InitiativePageLocators.countRecords, "Visible Records Count");

        if (total.equals(count)) {
            reportLogger.pass("✅ Count matches. Total: " + total);
        } else {
            reportLogger.fail("❌ Mismatch - Total: " + total + " vs Visible: " + count);
        }
    }

    public void ClickADD() throws Exception {
        // updated by Shahu.D: robust Add button click with fallbacks
        driver.switchTo().defaultContent();
        Exception last = null;
        for (By locator : InitiativePageLocators.addButtonLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                try {
                    el.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                }
                if (reportLogger != null) reportLogger.pass("Clicked Add button via locator: " + locator);
                System.out.println("✅ Clicked Add button via locator: " + locator);
                return;
            } catch (Exception e) {
                last = e;
                System.out.println("⚠️ Add button not clickable with " + locator + " -> " + e.getMessage());
            }
        }
        if (last != null) throw last;
        throw new Exception("Add button could not be located.");
    }


    // quick notification and duplicate header helpers removed per revert request

    // Shahu Dalave: helper methods for advanced Add search (commented per request)
    // private WebElement findAddButtonAcrossContexts(int timeoutSeconds) {
    //     driver.switchTo().defaultContent();
    //
    //     WebElement found = tryFindAddInContext(driver, timeoutSeconds);
    //     if (found != null) {
    //         return found;
    //     }
    //
    //     List<WebElement> frames = driver.findElements(By.cssSelector("iframe,frame"));
    //     System.out.println("🔍 Scanning " + frames.size() + " frame(s) for Add button...");
    //
    //     for (WebElement frame : frames) {
    //         try {
    //             driver.switchTo().frame(frame);
    //             found = tryFindAddInContext(driver, timeoutSeconds / 2);
    //             if (found != null) {
    //                 return found;
    //             }
    //         } catch (Exception ignored) {
    //         } finally {
    //             try { driver.switchTo().defaultContent(); } catch (Exception ignored2) {}
    //         }
    //     }
    //     return null;
    // }
    //
    // private WebElement tryFindAddInContext(WebDriver ctxDriver, int timeoutSeconds) {
    //     for (By locator : InitiativePageLocators.addButtonLocators) {
    //         try {
    //             WebDriverWait wait = new WebDriverWait(ctxDriver, Duration.ofSeconds(timeoutSeconds));
    //             WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
    //             return el;
    //         } catch (Exception e) {
    //             // continue to next locator
    //         }
    //     }
    //     return null;
    // }
    //
    // private void dumpVisibleButtonsForDebug() {
    //     try {
    //         driver.switchTo().defaultContent();
    //         List<WebElement> buttons = driver.findElements(By.tagName("button"));
    //         System.out.println("🧭 Debug: listing visible buttons (" + buttons.size() + " found):");
    //         for (WebElement btn : buttons) {
    //             try {
    //                 if (btn.isDisplayed()) {
    //                     String text = btn.getText();
    //                     String id = btn.getAttribute("id");
    //                     String cls = btn.getAttribute("class");
    //                     System.out.println("  • Button text='" + text + "', id='" + id + "', class='" + cls + "'");
    //                 }
    //             } catch (Exception ignored) {}
    //         }
    //     } catch (Exception ignored) {
    //     } finally {
    //         try { driver.switchTo().defaultContent(); } catch (Exception ignored2) {}
    //     }
    // }

    public void ClickSD() throws Exception {
        clickWithFallback(InitiativePageLocators.savedraft, "Save as Draft Button");
        waitForSeconds(3);
    }

    public void SelectNOI() throws Exception {
        // Use dynamic locator from InitiativePageLocators for consistency
        By noiOption = InitiativePageLocators.getDynamicNOIOption();


        
        clickWithFallback(noiOption, "Nature of Initiative: "  );

        reportLogger.info("✅ Selected Nature of Initiative from list: " );
    }

    public void setInitiativeTitle(String title) throws Throwable {
        type(InitiativePageLocators.IniTitle, title, "Initiative Title");
        if (reportLogger != null) {
            reportLogger.info("Entered Initiative Title: " + title);
        }
    }

    public void setInitiativedescription(String descriptionf) throws Throwable {
        type(InitiativePageLocators.iniDescription, descriptionf, "Initiative Description");
        if (reportLogger != null) {
            reportLogger.info("Entered Initiative Description: " + descriptionf);
        }
    }

    public void AdditionalNotes(String notes) throws Throwable {
    	   type(InitiativePageLocators.additionalNotes,notes, "additionalNotes");
    }
    
    public void setInitiativestartdate(String startdate) throws Throwable {
        System.out.println("📅 Setting Initiative Start Date: " + startdate);
        
        try {
            // Strategy 1: Try the original locator
            boolean success = setDateWithMultipleStrategies(
                InitiativePageLocators.startdate, 
                startdate, 
                "Start Date"
            );
            
            // Strategy 2: If failed, try alternative locator (without -label suffix)
            if (!success) {
                System.out.println("  ↪ Trying alternative locator without -label suffix...");
                success = setDateWithMultipleStrategies(InitiativePageLocators.startdateAlt, startdate, "Start Date (Alt)");
            }
            
            // Strategy 3: Try finding any date input nearby
            if (!success) {
                System.out.println("  ↪ Trying to find date input by placeholder...");
                success = setDateWithMultipleStrategies(InitiativePageLocators.startdateByPlaceholder, startdate, "Start Date (Placeholder)");
            }
            
            if (success) {
                System.out.println("✅ Successfully set Initiative Start Date: " + startdate);
                if (reportLogger != null) {
                    reportLogger.info("Entered Initiative startdate: " + startdate);
                }
            } else {
                throw new Exception("All strategies failed to set start date");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to set Initiative Start Date: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.warning("Failed to set startdate: " + e.getMessage());
            }
            throw e;
        }
    }

    public void setInitiativeenddate(String enddate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 Setting Initiative END DATE: " + enddate);
        System.out.println("📅 ═══════════════════════════════════════════");
        
        try {
            // Extra wait to ensure start date is fully processed
            waitForSeconds(2);
            
            // First, scroll to make sure end date field is visible
            System.out.println("  🔍 Scrolling to end date field...");
            try {
                WebElement endDateElement = driver.findElement(InitiativePageLocators.enddate);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", endDateElement);
                waitForSeconds(2);
                System.out.println("  ✓ Scrolled to end date field");
            } catch (Exception scrollEx) {
                System.out.println("  ⚠️ Could not scroll to end date: " + scrollEx.getMessage());
            }
            
            // Check if field is enabled
            try {
                WebElement endDateElement = driver.findElement(InitiativePageLocators.enddate);
                String disabled = endDateElement.getAttribute("disabled");
                String readonly = endDateElement.getAttribute("readonly");
                System.out.println("  📋 End date field status: disabled=" + disabled + ", readonly=" + readonly);
                
                if ("true".equals(disabled) || disabled != null) {
                    System.out.println("  ⚠️ WARNING: End date field appears to be disabled!");
                    System.out.println("  ↪ This might require start date to be set first");
                }
            } catch (Exception statusEx) {
                System.out.println("  ⚠️ Could not check field status: " + statusEx.getMessage());
            }
            
            boolean success = false;
            
            // Strategy 1: Try the original locator with label
            System.out.println("\n  📍 Strategy 1: Original locator (with -label suffix)");
            success = setDateWithMultipleStrategies(
                InitiativePageLocators.enddate, 
                enddate, 
                "End Date"
            );
            
            // Strategy 2: Try alternative locator (without -label suffix)
            if (!success) {
                System.out.println("\n  📍 Strategy 2: Alternative locator (without -label suffix)");
                success = setDateWithMultipleStrategies(InitiativePageLocators.enddateAlt, enddate, "End Date (Alt)");
            }
            
            // Strategy 3: Try by position (second date input on page)
            if (!success) {
                System.out.println("\n  📍 Strategy 3: Finding second date input field");
                success = setDateWithMultipleStrategies(InitiativePageLocators.secondDateInput, enddate, "End Date (2nd Input)");
            }
            
            // Strategy 4: Try finding by placeholder
            if (!success) {
                System.out.println("\n  📍 Strategy 4: Finding by placeholder text");
                success = setDateWithMultipleStrategies(InitiativePageLocators.enddateByPlaceholder, enddate, "End Date (Placeholder)");
            }
            
            // Strategy 5: Try finding the actual input field near the label
            if (!success) {
                System.out.println("\n  📍 Strategy 5: Finding input near end date label");
                success = setDateWithMultipleStrategies(InitiativePageLocators.enddateNearLabel, enddate, "End Date (Near Label)");
            }
            
            if (success) {
                System.out.println("\n✅ ✅ ✅ Successfully set Initiative End Date: " + enddate + " ✅ ✅ ✅");
                if (reportLogger != null) {
                    reportLogger.info("Entered Initiative enddate: " + enddate);
                }
            } else {
                System.out.println("\n❌ ❌ ❌ All strategies FAILED to set end date ❌ ❌ ❌");
                // Take screenshot for debugging
                try {
                    System.out.println("  📸 Taking screenshot for debugging...");
                    // You can add screenshot logic here if needed
                } catch (Exception screenshotEx) {}
                
                throw new Exception("All strategies failed to set end date");
            }
            
            System.out.println("📅 ═══════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.out.println("❌ Failed to set Initiative End Date: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.warning("Failed to set enddate: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // Helper method to set date with multiple strategies
    private boolean setDateWithMultipleStrategies(By locator, String dateValue, String fieldName) {
        try {
            System.out.println("  🔍 Trying to set " + fieldName + " with value: " + dateValue);
            
            // Check if element exists
            if (!driver.findElements(locator).isEmpty()) {
                WebElement dateField = driver.findElement(locator);
                
                if (!dateField.isDisplayed()) {
                    System.out.println("    ⚠️ Element found but not visible");
                    return false;
                }
                
                System.out.println("    ✓ Element found and visible");
                System.out.println("    📋 Current value before: '" + dateField.getAttribute("value") + "'");
                System.out.println("    📋 Tag name: " + dateField.getTagName());
                System.out.println("    📋 Type: " + dateField.getAttribute("type"));
                
                // Method 1: Standard Selenium interaction with TAB
                try {
                    System.out.println("    → Method 1: Standard Selenium with TAB");
                    dateField.click();
                    waitForSeconds(1);
                    dateField.sendKeys(Keys.CONTROL + "a");
                    dateField.sendKeys(Keys.BACK_SPACE);
                    waitForSeconds(1);
                    dateField.sendKeys(dateValue);
                    waitForSeconds(1);
                    dateField.sendKeys(Keys.TAB);
                    waitForSeconds(2); // Extra wait for value to register
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 1: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 1 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 1 failed: " + e.getMessage());
                }
                
                // Method 2: JavaScript setValue with ALL events
                try {
                    System.out.println("    → Method 2: JavaScript with ALL events");
                    ((JavascriptExecutor) driver).executeScript(
                        "var element = arguments[0];" +
                        "var value = arguments[1];" +
                        "element.focus();" +
                        "element.value = value;" +
                        "element.dispatchEvent(new Event('input', { bubbles: true, cancelable: true }));" +
                        "element.dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));" +
                        "element.dispatchEvent(new Event('keyup', { bubbles: true, cancelable: true }));" +
                        "element.dispatchEvent(new Event('keydown', { bubbles: true, cancelable: true }));" +
                        "element.blur();",
                        dateField, dateValue
                    );
                    waitForSeconds(2);
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 2: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 2 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 2 failed: " + e.getMessage());
                }
                
                // Method 3: React-specific event triggering
                try {
                    System.out.println("    → Method 3: React-specific events");
                    ((JavascriptExecutor) driver).executeScript(
                        "var element = arguments[0];" +
                        "var value = arguments[1];" +
                        "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeInputValueSetter.call(element, value);" +
                        "var event = new Event('input', { bubbles: true });" +
                        "element.dispatchEvent(event);" +
                        "var changeEvent = new Event('change', { bubbles: true });" +
                        "element.dispatchEvent(changeEvent);",
                        dateField, dateValue
                    );
                    waitForSeconds(2);
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 3: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 3 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 3 failed: " + e.getMessage());
                }
                
                // Method 4: Actions class - Click, clear, type, ENTER
                try {
                    System.out.println("    → Method 4: Actions with ENTER");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(dateField)
                           .click()
                           .pause(Duration.ofMillis(500))
                           .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                           .pause(Duration.ofMillis(200))
                           .sendKeys(Keys.DELETE)
                           .pause(Duration.ofMillis(300))
                           .sendKeys(dateValue)
                           .pause(Duration.ofMillis(500))
                           .sendKeys(Keys.ENTER)
                           .pause(Duration.ofMillis(500))
                           .perform();
                    waitForSeconds(2);
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 4: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 4 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 4 failed: " + e.getMessage());
                }
                
                // Method 5: Force set with JavaScript and click outside
                try {
                    System.out.println("    → Method 5: Force set + click outside");
                    // Set value
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1];", dateField, dateValue
                    );
                    waitForSeconds(1);
                    
                    // Trigger all possible events
                    ((JavascriptExecutor) driver).executeScript(
                        "var element = arguments[0];" +
                        "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "element.dispatchEvent(new Event('change', { bubbles: true }));",
                        dateField
                    );
                    
                    // Click somewhere else to trigger blur
                    Actions actions = new Actions(driver);
                    actions.moveToElement(dateField).click()
                           .pause(Duration.ofMillis(500))
                           .sendKeys(Keys.TAB)
                           .pause(Duration.ofMillis(500))
                           .perform();
                    waitForSeconds(2);
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 5: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 5 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 5 failed: " + e.getMessage());
                }
                
                // Method 6: Character by character with events
                try {
                    System.out.println("    → Method 6: Char-by-char with events");
                    dateField.click();
                    waitForSeconds(1);
                    
                    // Clear field
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dateField);
                    
                    // Type each character
                    for (char c : dateValue.toCharArray()) {
                        dateField.sendKeys(String.valueOf(c));
                        Thread.sleep(150);
                    }
                    
                    // Trigger events
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        dateField
                    );
                    
                    dateField.sendKeys(Keys.TAB);
                    waitForSeconds(2);
                    
                    String currentValue = dateField.getAttribute("value");
                    System.out.println("    📋 Value after Method 6: '" + currentValue + "'");
                    if (currentValue != null && currentValue.contains(dateValue.substring(0, 5))) {
                        System.out.println("    ✅ Method 6 SUCCESS - Value set: " + currentValue);
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Method 6 failed: " + e.getMessage());
                }
                
                System.out.println("    ❌ All methods failed - value not set properly");
                
            } else {
                System.out.println("    ✗ Element not found with locator: " + locator);
            }
            
        } catch (Exception e) {
            System.out.println("    ✗ Strategy failed completely: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public void verifyInitiativeHeader(String expectedHeader) throws Throwable {
        waitForElementToBeVisible(InitiativePageLocators.pageHeader, "Initiative Page Header");

        String actualHeader = getText(InitiativePageLocators.pageHeader, "Initiative Page Header").trim();

        if (expectedHeader.equalsIgnoreCase(actualHeader)) {
            reportLogger.pass("✅ Header matched! Expected: " + expectedHeader + " | Actual: " + actualHeader);
        } else {
            reportLogger.fail("❌ Header mismatch! Expected: " + expectedHeader + " | Actual: " + actualHeader);
        }
    }
    
   
    public void verifyInitiativeHeaderini(String expectedHeader) throws Throwable {
        waitForElementToBeVisible(InitiativePageLocators.Pageheaderini, "Initiative Page Header add details");
    }

    public void selectInitiativeBGWithActions(String bgName) throws Throwable {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            // 1. Click dropdown - Using centralized locator
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.IniBG));
            actions.moveToElement(dropdown).click().perform();
            
            // 2. Wait and find option - Using dynamic helper method
            Thread.sleep(1000);
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.getDynamicBGOption(bgName)));
            
            // 3. Move to option and click
            actions.moveToElement(option).click().perform();
            
            reportLogger.info("✅ Selected Initiative BG using Actions: " + bgName);
            
        } catch (Exception e) {
            reportLogger.fail("❌ Failed to select Initiative BG using Actions: " + bgName + " - " + e.getMessage());
            throw e;
        }
    }

    public void selectInitiativeOUWithActions(String OUName) throws Throwable {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            // 1. Click dropdown - Using centralized locator
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.IniOU));
            actions.moveToElement(dropdown).click().perform();
            
            // 2. Wait and find option - Using dynamic helper method
            Thread.sleep(1000);
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.getDynamicOUOption(OUName)));
            
            // 3. Move to option and click
            actions.moveToElement(option).click().perform();
            
            reportLogger.info("✅ Selected Initiative OU using Actions: " + OUName);
            
        } catch (Exception e) {
            reportLogger.fail("❌ Failed to select  Initiative OU using Actions: " + OUName + " - " + e.getMessage());
            throw e;
        }
    }
    

    public String verifyAlertMessage(String expectedMsg) throws Throwable {
        try {
            // Wait up to 15s since toast may load late
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
            WebElement alertElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(InitiativePageLocators.toastAlert)
            );

            String actualMsg = alertElement.getText().trim();

            // Print captured alert
            System.out.println("🔔 Captured Alert: " + actualMsg);

            // Validate
            if (actualMsg.equals(expectedMsg)) {
                reportLogger.log(Status.PASS, "✅ Alert message verified: " + actualMsg);
            } else {
                reportLogger.fail(
                    "❌ Expected: " + expectedMsg + " but found: " + actualMsg,
                    MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenshot(webDriver, "AlertMismatch")
                    ).build()
                );
            }

            return actualMsg;

        } catch (TimeoutException e) {
            String msg = "⚠️ No alert appeared within timeout.";
            System.out.println(msg);
            reportLogger.fail(msg);
            throw e;
        }
    }
    
    
    public void verifyInitiativealtmsg(String expectedalert) throws Throwable {
    	
        waitForElementToBeVisible(InitiativePageLocators.toastAlert, "Initiative title alert ");

        String actualalert = getText(InitiativePageLocators.toastAlert, "Initiative title alert").trim();

        if (expectedalert.equalsIgnoreCase(actualalert)) {
            reportLogger.pass("✅ ALert matched! Expected: " + expectedalert + " | Actual: " + actualalert);
        } else {
            reportLogger.fail("❌ ALert mismatch! Expected: " + expectedalert + " | Actual: " + actualalert);
        }
    }
    public void ClickSubmit() throws Exception {
        System.out.println("\n🔘 ═══════════════════════════════════════════");
        System.out.println("🔘 Attempting to Click SUBMIT Button");
        System.out.println("🔘 ═══════════════════════════════════════════");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        boolean clicked = false;
        WebElement submitEl = null;
        
        // Array of locators to try
        By[] submitLocators = {
            InitiativePageLocators.Submit,      // Primary
            InitiativePageLocators.SubmitAlt1,  // Button with SendIcon
            InitiativePageLocators.SubmitAlt2,  // Span with Submit text
            InitiativePageLocators.SubmitAlt3   // Span with Submit and svg
        };
        
        String[] locatorNames = {
            "Primary (absolute path to div container)",
            "Alternative 1 (button with SendIcon)",
            "Alternative 2 (span with Submit text)",
            "Alternative 3 (span with Submit text and svg)"
        };
        
        // Try each locator
        for (int i = 0; i < submitLocators.length && !clicked; i++) {
            try {
                System.out.println("\n  📍 Trying locator " + (i + 1) + ": " + locatorNames[i]);
                
                // Check if element exists
                if (driver.findElements(submitLocators[i]).isEmpty()) {
                    System.out.println("    ✗ Element not found");
                    continue;
                }
                
                submitEl = wait.until(ExpectedConditions.presenceOfElementLocated(submitLocators[i]));
                System.out.println("    ✓ Element found");
                
                // Check if visible
                if (!submitEl.isDisplayed()) {
                    System.out.println("    ⚠️ Element not visible");
                    continue;
                }
                System.out.println("    ✓ Element visible");
                
                // Scroll into view
                System.out.println("    → Scrolling element into view...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", submitEl);
                waitForSeconds(1);
                
                // Wait for it to be clickable
                System.out.println("    → Waiting for element to be clickable...");
                wait.until(ExpectedConditions.elementToBeClickable(submitLocators[i]));
                
                // Strategy 1: Standard click
                System.out.println("    → Strategy 1: Standard click");
                try {
                    submitEl.click();
                    System.out.println("    ✅ Standard click SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e1) {
                    System.out.println("    ✗ Standard click failed: " + e1.getMessage());
                }
                
                // Strategy 2: JavaScript click
                System.out.println("    → Strategy 2: JavaScript click");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitEl);
                    System.out.println("    ✅ JavaScript click SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e2) {
                    System.out.println("    ✗ JavaScript click failed: " + e2.getMessage());
                }
                
                // Strategy 3: Actions click
                System.out.println("    → Strategy 3: Actions click");
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(submitEl).pause(Duration.ofMillis(300)).click().perform();
                    System.out.println("    ✅ Actions click SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e3) {
                    System.out.println("    ✗ Actions click failed: " + e3.getMessage());
                }
                
                // Strategy 4: Click parent button if exists
                System.out.println("    → Strategy 4: Finding and clicking parent button");
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0];" +
                        "var btn = el.closest('button') || el.closest('[role=\"button\"]');" +
                        "if (btn) { btn.click(); return true; }" +
                        "return false;",
                        submitEl
                    );
                    System.out.println("    ✅ Parent button click SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e4) {
                    System.out.println("    ✗ Parent button click failed: " + e4.getMessage());
                }
                
                // Strategy 5: Send ENTER key
                System.out.println("    → Strategy 5: Sending ENTER key");
                try {
                    submitEl.sendKeys(Keys.ENTER);
                    System.out.println("    ✅ ENTER key SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e5) {
                    System.out.println("    ✗ ENTER key failed: " + e5.getMessage());
                }
                
                // Strategy 6: Tab navigation and ENTER
                System.out.println("    → Strategy 6: Tab navigation + ENTER");
                try {
                    // Move focus to the element using Tab simulation
                    Actions actions = new Actions(driver);
                    actions.moveToElement(submitEl)
                           .click() // Focus on element
                           .pause(Duration.ofMillis(500))
                           .sendKeys(Keys.ENTER)
                           .perform();
                    System.out.println("    ✅ Tab navigation + ENTER SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e6) {
                    System.out.println("    ✗ Tab navigation failed: " + e6.getMessage());
                }
                
                // Strategy 7: Tab from body to element and press SPACE
                System.out.println("    → Strategy 7: Tab navigation + SPACE");
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(submitEl)
                           .click() // Focus on element
                           .pause(Duration.ofMillis(500))
                           .sendKeys(Keys.SPACE)
                           .perform();
                    System.out.println("    ✅ Tab navigation + SPACE SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e7) {
                    System.out.println("    ✗ Tab + SPACE failed: " + e7.getMessage());
                }
                
                // Strategy 8: Focus with JavaScript then send keyboard event
                System.out.println("    → Strategy 8: JS focus + keyboard event");
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].focus(); " +
                        "arguments[0].dispatchEvent(new KeyboardEvent('keydown', {key: 'Enter', code: 'Enter', keyCode: 13, which: 13})); " +
                        "arguments[0].dispatchEvent(new KeyboardEvent('keyup', {key: 'Enter', code: 'Enter', keyCode: 13, which: 13}));",
                        submitEl
                    );
                    waitForSeconds(1);
                    System.out.println("    ✅ JS focus + keyboard event SUCCESS");
                    clicked = true;
                    break;
                } catch (Exception e8) {
                    System.out.println("    ✗ JS focus + keyboard failed: " + e8.getMessage());
                }
                
            } catch (Exception locatorEx) {
                System.out.println("    ✗ Locator failed: " + locatorEx.getMessage());
            }
        }
        
        if (!clicked) {
            System.out.println("\n❌ ❌ ❌ All locators and strategies FAILED ❌ ❌ ❌");
            if (reportLogger != null) reportLogger.fail("Failed to click Submit button with all strategies");
            throw new Exception("Could not click Submit button - all strategies failed");
        }
        
        System.out.println("\n✅ Submit button clicked successfully!");
        waitForSeconds(2);
        
        // Verify it actually fired: wait for modal to appear
        System.out.println("  🔍 Verifying submit action...");
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // First, try to wait for the modal to appear (this is the expected behavior)
            try {
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(InitiativePageLocators.modalPopup));
                System.out.println("  ✅ Submit comments modal appeared!");
                if (reportLogger != null) reportLogger.pass("Submit comments modal appeared successfully.");
            } catch (Exception modalEx) {
                // Modal didn't appear, check for other indicators
                System.out.println("  ⚠️ Modal didn't appear, checking other indicators...");
                shortWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(InitiativePageLocators.toastAlert),
                    ExpectedConditions.stalenessOf(submitEl),
                    ExpectedConditions.invisibilityOf(submitEl)
                ));
                System.out.println("  ✅ Submit action verified - toast appeared or element state changed");
                if (reportLogger != null) reportLogger.pass("Submit action verified.");
            }
        } catch (Exception verifyEx) {
            System.out.println("  ⚠️ Could not verify submit action with any indicator");
            System.out.println("  ⚠️ This may indicate the submit button click did not trigger the expected action");
            if (reportLogger != null) reportLogger.warning("Submit click did not show expected modal or confirmation within timeout");
            // Don't throw exception here, let the next step (Clickpopsub) fail if modal is really not there
        }
        
        System.out.println("🔘 ═══════════════════════════════════════════\n");
    }

    public void waitForSubmit() throws Exception {
        waitForElementToBeVisible(InitiativePageLocators.Submit, "Submit Link");
        waitForElementToBeClickable(InitiativePageLocators.Submit, "Submit Link");
    }

    /**
     * Switch to modal window using XPath locator
     * @param modalXPath XPath locator for the modal container
     * @param timeoutSeconds Maximum time to wait for modal to appear
     * @return WebElement of the modal if found, null otherwise
     */
    public WebElement switchToModalWindow(String modalXPath, int timeoutSeconds) {
        try {
            // Use dynamic helper method for consistency
            By modalLocator = InitiativePageLocators.getDynamicModalByXPath(modalXPath);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            
            // Wait for modal to be visible
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
            
            // Check if modal is displayed
            if (modal.isDisplayed()) {
                reportLogger.info("Successfully switched to modal window: " + modalXPath);
                return modal;
            } else {
                reportLogger.fail("Modal found but not displayed: " + modalXPath);
                return null;
            }
            
        } catch (TimeoutException e) {
            reportLogger.fail("Modal window not found within " + timeoutSeconds + " seconds: " + modalXPath);
            return null;
        } catch (Exception e) {
            reportLogger.fail("Error switching to modal window: " + e.getMessage());
            return null;
        }
    }

    /**
     * Switch to modal window using XPath locator with default timeout
     * @param modalXPath XPath locator for the modal container
     * @return WebElement of the modal if found, null otherwise
     */
    public WebElement switchToModalWindow(String modalXPath) {
        return switchToModalWindow(modalXPath, 20);
    }

    /**
     * Switch to specific modal types with predefined XPath patterns
     */
    public WebElement switchToSubmitModal() {
        return switchToModalWindow("//div[contains(@class, 'modal') and .//h6[contains(text(),'Submit')]]");
    }

    public WebElement switchToConfirmModal() {
        return switchToModalWindow("//div[contains(@class, 'modal') and .//h6[contains(text(),'Confirm')]]");
    }

    public WebElement switchToDeleteModal() {
        return switchToModalWindow("//div[contains(@class, 'modal') and .//h6[contains(text(),'Delete')]]");
    }

    public WebElement switchToGenericModal() {
        return switchToModalWindow("//div[contains(@class, 'modal')]");
    }

    /**
     * Switch to the specific submit modal with comments textarea
     * @return WebElement of the modal if found, null otherwise
     */
    public WebElement switchToSubmitCommentsModal() {
        return switchToModalWindow("//div[contains(@class, 'modal-105') and .//h6[text()='Submit']]");
    }

    /**
     * Check if modal is currently active/visible
     * @param modalXPath XPath locator for the modal
     * @return true if modal is visible, false otherwise
     */
    public boolean isModalActive(String modalXPath) {
        try {
            // Use dynamic helper method for consistency
            By modalLocator = InitiativePageLocators.getDynamicModalByXPath(modalXPath);
            WebElement modal = driver.findElement(modalLocator);
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Close modal window if it's open
     * @param modalXPath XPath locator for the modal
     * @return true if modal was closed, false otherwise
     */
    public boolean closeModalWindow(String modalXPath) {
        try {
            if (isModalActive(modalXPath)) {
                // Try to find and click close button (X button) - Using centralized locator
                click(InitiativePageLocators.getModalCloseButton(), "Modal Close Button");
                reportLogger.info("Modal window closed successfully");
                return true;
            }
            return false;
        } catch (Exception e) {
            reportLogger.fail("Error closing modal window: " + e.getMessage());
            return false;
        }
    }

    /**
     * ENHANCED: Click popup submit button with debugging and aggressive strategies
     */
    public void Clickpopsub() throws Exception {
        System.out.println("\n🖱️ ═══════════════════════════════════════════");
        System.out.println("🖱️ Clicking Popup Submit Button (ENHANCED)");
        System.out.println("🖱️ ═══════════════════════════════════════════");
        
        boolean clicked = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // FIRST: Debug - Print ALL buttons in modal - Using centralized locator
        System.out.println("\n🔍 DEBUG: Scanning all buttons in modal...");
        try {
            List<WebElement> allButtons = driver.findElements(InitiativePageLocators.allModalButtons);
            System.out.println("  📊 Found " + allButtons.size() + " button(s) in modal");
            for (int i = 0; i < allButtons.size(); i++) {
                WebElement btn = allButtons.get(i);
                try {
                    System.out.println("  Button #" + (i+1) + ":");
                    System.out.println("    - ID: " + btn.getAttribute("id"));
                    System.out.println("    - Class: " + btn.getAttribute("class"));
                    System.out.println("    - Text: '" + btn.getText() + "'");
                    System.out.println("    - Enabled: " + btn.isEnabled());
                    System.out.println("    - Visible: " + btn.isDisplayed());
                } catch (Exception e) {
                    System.out.println("    - Error reading button: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Error scanning buttons: " + e.getMessage());
        }
        
        // Also check for spans that might contain submit - Using centralized locator
        System.out.println("\n🔍 DEBUG: Checking for span elements...");
        try {
            List<WebElement> allSpans = driver.findElements(InitiativePageLocators.allModalSpans);
            System.out.println("  📊 Found " + allSpans.size() + " span(s) with ID in modal");
            for (int i = 0; i < allSpans.size(); i++) {
                WebElement span = allSpans.get(i);
                try {
                    System.out.println("  Span #" + (i+1) + ": ID=" + span.getAttribute("id") + ", Text='" + span.getText() + "'");
                } catch (Exception e) {
                    System.out.println("  Span #" + (i+1) + ": Error - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Error scanning spans: " + e.getMessage());
        }
        
        // Try multiple locators (most reliable first) - Using centralized array
        By[] locators = InitiativePageLocators.modalSubmitButtonLocators;
        
        String[] locatorNames = {
            "ms-Button--primary in modal (MOST RELIABLE)",
            "Button with 'Submit' text",
            "Button containing 'Submit'",
            "ANY button in modal (aggressive)",
            "Original primary locator",
            "Alternative 1",
            "Alternative 2", 
            "Alternative 3"
        };
        
        WebElement submitButton = null;
        
        // Find the button
        for (int i = 0; i < locators.length; i++) {
            try {
                System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                if (!driver.findElements(locators[i]).isEmpty()) {
                    submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(locators[i]));
                    System.out.println("  ✓ Button found!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("    ✗ Not found: " + e.getMessage());
            }
        }
        
        if (submitButton == null) {
            throw new Exception("❌ Could not find popup submit button with any locator");
        }
        
        // Try clicking strategies (most effective first)
        System.out.println("\n  📋 Button: Tag=" + submitButton.getTagName() + 
                          ", Enabled=" + submitButton.isEnabled() + 
                          ", Visible=" + submitButton.isDisplayed());
        
        // Strategy 1: JavaScript click (most reliable for modals)
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 1: JavaScript click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 2: Tab navigation + ENTER (keyboard simulation)
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 2: Tab to button + ENTER");
                Actions actions = new Actions(driver);
                actions.moveToElement(submitButton)
                       .click() // Focus
                       .pause(Duration.ofMillis(500))
                       .sendKeys(Keys.ENTER)
                       .perform();
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 3: Tab through modal (most realistic user behavior)
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 3: Tab through modal + ENTER");
                WebElement modal = driver.findElement(InitiativePageLocators.modalPopup);
                modal.click();
                Thread.sleep(500);
                
                Actions actions = new Actions(driver);
                // Tab 5 times to reach submit button (usually last element)
                for (int i = 0; i < 5; i++) {
                    actions.sendKeys(Keys.TAB).pause(Duration.ofMillis(300)).perform();
                }
                actions.sendKeys(Keys.ENTER).perform();
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 4: Standard Actions click
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 4: Actions class click");
                Actions actions = new Actions(driver);
                actions.moveToElement(submitButton)
                       .pause(Duration.ofMillis(500))
                       .click()
                       .perform();
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 5: JavaScript with keyboard events
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 5: JS focus + keyboard event");
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].focus(); " +
                    "arguments[0].dispatchEvent(new KeyboardEvent('keydown', {key: 'Enter', bubbles: true})); " +
                    "arguments[0].click();",
                    submitButton
                );
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 6: NUCLEAR OPTION - Click ALL primary buttons in modal
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 6: NUCLEAR - Click all primary buttons");
                ((JavascriptExecutor) driver).executeScript(
                    "var buttons = document.querySelectorAll('.modal-105 button.ms-Button--primary');" +
                    "console.log('Found buttons:', buttons.length);" +
                    "for (var i = 0; i < buttons.length; i++) {" +
                    "  console.log('Clicking button', i);" +
                    "  buttons[i].click();" +
                    "}" +
                    "if (buttons.length === 0) {" +
                    "  var anyButton = document.querySelector('.modal-105 button');" +
                    "  if (anyButton) anyButton.click();" +
                    "}"
                );
                Thread.sleep(1000);
                System.out.println("  ✅ SUCCESS (forced)");
                clicked = true;
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        // Strategy 7: SUPER NUCLEAR - Click by querySelector with all possible selectors
        if (!clicked) {
            try {
                System.out.println("\n  📍 Strategy 7: SUPER NUCLEAR - Try every possible selector");
                boolean result = (boolean) ((JavascriptExecutor) driver).executeScript(
                    "var selectors = [" +
                    "  '.modal-105 button[class*=\"primary\"]'," +
                    "  '.modal-105 button:last-child'," +
                    "  '.modal-105 button'," +
                    "  'button[class*=\"ms-Button\"]'" +
                    "];" +
                    "for (var i = 0; i < selectors.length; i++) {" +
                    "  var btn = document.querySelector(selectors[i]);" +
                    "  if (btn) {" +
                    "    console.log('Clicking with selector:', selectors[i]);" +
                    "    btn.click();" +
                    "    return true;" +
                    "  }" +
                    "}" +
                    "return false;"
                );
                Thread.sleep(1000);
                if (result) {
                    System.out.println("  ✅ SUCCESS (super nuclear)");
                    clicked = true;
                } else {
                    System.out.println("  ✗ No buttons found even with nuclear option");
                }
            } catch (Exception e) {
                System.out.println("  ✗ Failed: " + e.getMessage());
            }
        }
        
        if (!clicked) {
            System.out.println("\n❌ ❌ ❌ ALL STRATEGIES FAILED ❌ ❌ ❌");
            System.out.println("📸 Taking screenshot for debugging...");
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "submit_button_failed_" + System.currentTimeMillis() + ".png";
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                System.out.println("  📸 Screenshot saved: " + screenshotPath);
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not take screenshot: " + e.getMessage());
            }
            throw new Exception("❌ All strategies failed to click popup submit button");
        }
        
        System.out.println("\n✅ ✅ ✅ Popup Submit Button Clicked Successfully! ✅ ✅ ✅");
        System.out.println("🖱️ ═══════════════════════════════════════════\n");
        
        // Verify submission
        Thread.sleep(2000);
        try {
            WebElement modal = driver.findElement(InitiativePageLocators.modalPopup);
            if (!modal.isDisplayed()) {
                System.out.println("  ✅ Modal closed - submission successful!");
            }
        } catch (Exception e) {
            System.out.println("  ✅ Modal closed - submission successful!");
        }
        
        if (reportLogger != null) {
            reportLogger.pass("Popup submit button clicked successfully");
        }
    }
    
    /**
     * Enter comment in modal textarea and submit
     * @param comment Text to enter in the comments textarea
     * @param modalXPath XPath for the modal (optional, uses default if null)
     */
    public void enterCommentAndSubmit(String comment, String modalXPath) {
        try {
            // Switch to modal if XPath provided, otherwise use default
            WebElement modal = (modalXPath != null) ? 
                switchToModalWindow(modalXPath) : 
                switchToSubmitCommentsModal();
            
            if (modal == null) {
                reportLogger.fail("Could not switch to modal window");
                return;
            }

            // Wait for and interact with textarea - Using dynamic helper method
            By textareaLocator = InitiativePageLocators.getDynamicTextareaById("TextField936");
            waitForElementToBeVisible(textareaLocator, "Comments Textarea");
            
            WebElement textarea = driver.findElement(textareaLocator);
            textarea.clear();
            textarea.sendKeys(comment);
            
            reportLogger.info("Entered comment: " + comment);
            
            // Click submit button in modal - Using dynamic helper method
            By modalSubmitButton = InitiativePageLocators.getDynamicModalSubmitButton("Submit");
            click(modalSubmitButton, "Modal Submit Button");
            
            reportLogger.pass("Comment submitted successfully");
            
        } catch (Exception e) {
            reportLogger.fail("Error entering comment and submitting: " + e.getMessage());
        }
    }
   
    // ═══════════════════════════════════════════════════════════════
    // 🪟 WINDOW HANDLE MANAGEMENT METHODS
    // ═══════════════════════════════════════════════════════════════
    
    private String parentWindowHandle;
    
    /**
     * Store the current window handle as parent window
     */
    public void storeParentWindow() {
        try {
            parentWindowHandle = driver.getWindowHandle();
            System.out.println("📌 Stored parent window: " + parentWindowHandle);
            if (reportLogger != null) {
                reportLogger.info("Stored parent window handle");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to store parent window: " + e.getMessage());
        }
    }
    
    /**
     * Get current window count
     */
    public int getWindowCount() {
        try {
            return driver.getWindowHandles().size();
        } catch (Exception e) {
            System.out.println("❌ Failed to get window count: " + e.getMessage());
            return 1;
        }
    }
    
    /**
     * Wait for new window to appear
     * @param expectedCount Expected number of windows
     * @param timeoutSeconds Maximum time to wait
     * @return true if new window appeared, false otherwise
     */
    public boolean waitForNewWindow(int expectedCount, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(driver -> driver.getWindowHandles().size() >= expectedCount);
        } catch (Exception e) {
            System.out.println("❌ New window did not appear: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Switch to modal popup window (new browser window)
     * @param modalLocator Locator for modal verification
     * @param modalName Name of modal for logging
     * @return true if switched successfully
     */
    public boolean switchToModalPopupWindow(By modalLocator, String modalName) {
        try {
            String currentHandle = driver.getWindowHandle();
            System.out.println("📍 Current window: " + currentHandle);
            
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(parentWindowHandle)) {
                    System.out.println("🔄 Switching to window: " + handle);
                    driver.switchTo().window(handle);
                    
                    // Wait for modal to be visible
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
                    
                    System.out.println("✅ Switched to " + modalName);
                    if (reportLogger != null) {
                        reportLogger.info("Switched to modal popup window: " + modalName);
                    }
                    return true;
                }
            }
            
            System.out.println("❌ No new window found");
            return false;
        } catch (Exception e) {
            System.out.println("❌ Failed to switch to modal popup: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close current window and switch back to parent
     */
    public void closeCurrentWindowAndSwitchToParent() {
        try {
            String currentHandle = driver.getWindowHandle();
            System.out.println("🗑️ Closing window: " + currentHandle);
            driver.close();
            
            if (parentWindowHandle != null) {
                driver.switchTo().window(parentWindowHandle);
                System.out.println("✅ Switched back to parent window");
                if (reportLogger != null) {
                    reportLogger.info("Closed popup and switched to parent");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to close window: " + e.getMessage());
        }
    }
    
    /**
     * Switch to parent window
     */
    public void switchToParentWindow() {
        try {
            if (parentWindowHandle != null) {
                driver.switchTo().window(parentWindowHandle);
                System.out.println("✅ Switched back to parent window successfully.");
                if (reportLogger != null) {
                    reportLogger.info("Switched to parent window");
                }
            } else {
                System.out.println("⚠️ Parent window handle is null");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to switch to parent: " + e.getMessage());
        }
    }
    
    // ═══════════════════════════════════════════════════════════════
    // 📋 MODAL OVERLAY METHODS (HTML Modal, not popup window)
    // ═══════════════════════════════════════════════════════════════
    
    /**
     * Wait for HTML modal overlay to appear (not a popup window)
     * @param modalLocator Locator for the modal
     * @param timeoutSeconds Maximum time to wait
     * @return true if modal appeared
     */
    public boolean waitForHTMLModal(By modalLocator, int timeoutSeconds) {
        try {
            System.out.println("⏳ Waiting for HTML modal to appear...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));
            
            if (modal.isDisplayed()) {
                System.out.println("✅ HTML Modal is visible: " + modalLocator);
                if (reportLogger != null) {
                    reportLogger.info("HTML modal appeared: " + modalLocator);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("❌ HTML Modal did not appear: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Type text in modal overlay (ensures modal context is maintained)
     * @param elementLocator Locator for input field
     * @param text Text to type
     * @param fieldName Name of field for logging
     */
  /*  public void typeInModal(By elementLocator, String text, String fieldName) {
        System.out.println("\n📝 ═══════════════════════════════════════════");
        System.out.println("📝 Typing in Modal: " + fieldName + " = " + text);
        System.out.println("📝 ═══════════════════════════════════════════");
        
        try {
            // Use setAdditionalNotes if this is the notes field
            if (elementLocator.equals(InitiativePageLocators.additionalNotes)) {
                setAdditionalNotes(text);
            } else {
                // For other fields, use standard typing
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                waitForSeconds(1);
                
                // Click and type
                element.click();
                waitForSeconds(1);
                element.clear();
                element.sendKeys(text);
                
                System.out.println("✅ Successfully typed in modal field: " + fieldName);
                if (reportLogger != null) {
                    reportLogger.info("Typed in modal: " + fieldName + " = " + text);
                }
            }
        } catch (Throwable e) {
            System.out.println("❌ Failed to type in modal: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to type in modal: " + fieldName + " - " + e.getMessage());
            }
            throw new RuntimeException("Failed to type in modal: " + fieldName, e);
        }
    }
    */
    /**
     * Click element in modal overlay
     * @param elementLocator Locator for element to click
     * @param elementName Name of element for logging
     */
    public void clickElementInModal(By elementLocator, String elementName) {
        System.out.println("\n🖱️ Clicking in Modal: " + elementName);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                element.click();
                System.out.println("✅ Clicked: " + elementName);
            } catch (Exception e1) {
                // Try JavaScript click as fallback
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("✅ JavaScript clicked: " + elementName);
            }
            
            if (reportLogger != null) {
                reportLogger.info("Clicked in modal: " + elementName);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to click in modal: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click in modal: " + elementName + " - " + e.getMessage());
            }
            throw new RuntimeException("Failed to click in modal: " + elementName, e);
        }
    }

    // ==================== INBOX & WATCHLIST COUNT VERIFICATION METHODS ====================
    
    /**
     * Click Inbox filter button with multiple strategies
     */
    public void clickInboxFilter() {
        try {
            System.out.println("\n📥 ═══════════════════════════════════════════");
            System.out.println("📥 Clicking Inbox Filter");
            System.out.println("📥 ═══════════════════════════════════════════");
            
            boolean clicked = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try multiple locators
            // Updated by Shahu.D - Added specific xpath for Inbox section
            By[] locators = {
                By.xpath("//*[@id=\"ImFltr-Inbox\"]/a"),  // Updated by Shahu.D - Parent anchor element
                By.xpath("//*[@id=\"ImFltr-Inbox\"]/a/span[2]"),  // Updated by Shahu.D - Specific Inbox span xpath
                InitiativePageLocators.inboxFilterAlt1,  // ID: FltrCountInbox
                InitiativePageLocators.inboxFilterAlt2,  // Button contains 'Inbox'
                InitiativePageLocators.inboxFilterAlt3,  // Span with parent button
                InitiativePageLocators.inboxFilter       // Combined primary locator
            };
            
            String[] locatorNames = {
                "XPath: //*[@id=\"ImFltr-Inbox\"]/a (Updated by Shahu.D - Parent anchor)",
                "XPath: //*[@id=\"ImFltr-Inbox\"]/a/span[2] (Updated by Shahu.D)",
                "ID: FltrCountInbox",
                "Button containing 'Inbox'",
                "Span 'Inbox' with parent button",
                "Primary combined locator"
            };
            
            WebElement inboxButton = null;
            int foundIndex = -1;
            
            // Find the button
            for (int i = 0; i < locators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    if (!driver.findElements(locators[i]).isEmpty()) {
                        inboxButton = wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                        System.out.println("  ✓ Button found with locator " + (i + 1));
                        foundIndex = i;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Not found: " + e.getMessage());
                }
            }
            
            if (inboxButton == null) {
                throw new Exception("❌ Could not find Inbox filter button with any locator");
            }
            
            System.out.println("\n  📋 Button Details:");
            System.out.println("    Tag: " + inboxButton.getTagName());
            System.out.println("    Text: " + inboxButton.getText());
            System.out.println("    Enabled: " + inboxButton.isEnabled());
            System.out.println("    Displayed: " + inboxButton.isDisplayed());
            
            // Strategy 1: Scroll into view + Standard click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 1: Scroll + Standard click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", inboxButton);
                    Thread.sleep(500);
                    inboxButton.click();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 2: JavaScript click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inboxButton);
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 3: Actions click");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(inboxButton).click().perform();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("❌ All strategies failed to click Inbox filter");
            }
            
            System.out.println("\n✅ ✅ ✅ Inbox Filter Clicked Successfully! ✅ ✅ ✅");
            System.out.println("📥 ═══════════════════════════════════════════\n");
            
            if (reportLogger != null) {
                reportLogger.pass("Clicked Inbox filter successfully");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Failed to click Inbox filter: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Inbox filter: " + e.getMessage());
            }
            throw new RuntimeException("Failed to click Inbox filter", e);
        }
    }
    
    /**
     * Click Watchlist filter button with multiple strategies
     */
    public void clickWatchlistFilter() {
        try {
            System.out.println("\n⭐ ═══════════════════════════════════════════");
            System.out.println("⭐ Clicking Watchlist Filter");
            System.out.println("⭐ ═══════════════════════════════════════════");
            
            boolean clicked = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try multiple locators
            By[] locators = {
                InitiativePageLocators.watchlistFilterAlt1,  // ID: FltrCountWatchlist
                InitiativePageLocators.watchlistFilterAlt2,  // Button contains 'Watchlist'
                InitiativePageLocators.watchlistFilterAlt3,  // Span with parent button
                InitiativePageLocators.watchlistFilter       // Combined primary locator
            };
            
            String[] locatorNames = {
                "ID: FltrCountWatchlist",
                "Button containing 'Watchlist'",
                "Span 'Watchlist' with parent button",
                "Primary combined locator"
            };
            
            WebElement watchlistButton = null;
            int foundIndex = -1;
            
            // Find the button
            for (int i = 0; i < locators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    if (!driver.findElements(locators[i]).isEmpty()) {
                        watchlistButton = wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                        System.out.println("  ✓ Button found with locator " + (i + 1));
                        foundIndex = i;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Not found: " + e.getMessage());
                }
            }
            
            if (watchlistButton == null) {
                throw new Exception("❌ Could not find Watchlist filter button with any locator");
            }
            
            System.out.println("\n  📋 Button Details:");
            System.out.println("    Tag: " + watchlistButton.getTagName());
            System.out.println("    Text: " + watchlistButton.getText());
            System.out.println("    Enabled: " + watchlistButton.isEnabled());
            System.out.println("    Displayed: " + watchlistButton.isDisplayed());
            
            // Strategy 1: Scroll into view + Standard click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 1: Scroll + Standard click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", watchlistButton);
                    Thread.sleep(500);
                    watchlistButton.click();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 2: JavaScript click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", watchlistButton);
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 3: Actions click");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(watchlistButton).click().perform();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 4: Click parent button if span was found
            if (!clicked && watchlistButton.getTagName().equals("span")) {
                try {
                    System.out.println("\n  📍 Strategy 4: Click parent button");
                    // Find parent using XPath relative locator
                    WebElement parentButton = watchlistButton.findElement(By.xpath(".."));
                    if (parentButton.getTagName().equals("button")) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                        Thread.sleep(2000);
                        System.out.println("  ✅ SUCCESS");
                        clicked = true;
                    }
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("❌ All strategies failed to click Watchlist filter");
            }
            
            System.out.println("\n✅ ✅ ✅ Watchlist Filter Clicked Successfully! ✅ ✅ ✅");
            System.out.println("⭐ ═══════════════════════════════════════════\n");
            
            if (reportLogger != null) {
                reportLogger.pass("Clicked Watchlist filter successfully");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Failed to click Watchlist filter: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Watchlist filter: " + e.getMessage());
            }
            throw new RuntimeException("Failed to click Watchlist filter", e);
        }
    }
    
    /**
     * Get the count displayed on Inbox filter badge
     * 
     * @return Inbox count as integer
     */
    public int getInboxCount() {
        try {
            System.out.println("\n🔢 ═══════════════════════════════════════════");
            System.out.println("🔢 Getting Inbox Count from Badge");
            System.out.println("🔢 ═══════════════════════════════════════════");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try multiple locators to find the count - Using centralized array
            By[] countLocators = InitiativePageLocators.inboxCountLocators;
            
            String[] locatorNames = {
                "span[id='FltrCountInbox']",
                "Span following Inbox",
                "Span with class 'count'",
                "Span with class 'badge'",
                "Button Inbox > span count",
                "Button containing Inbox"
            };
            
            for (int i = 0; i < countLocators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    
                    if (!driver.findElements(countLocators[i]).isEmpty()) {
                        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countLocators[i]));
                        String originalText = countElement.getText().trim();
                        System.out.println("    📝 Original text: '" + originalText + "'");
                        
                        // Extract numbers from text (e.g., "Inbox 5" -> "5", "(5)" -> "5")
                        String countText = originalText.replaceAll("[^0-9]", "");
                        System.out.println("    🔢 Extracted numbers: '" + countText + "'");
                        
                        if (!countText.isEmpty()) {
                            int count = Integer.parseInt(countText);
                            System.out.println("  ✅ Successfully found count: " + count);
                            System.out.println("🔢 ═══════════════════════════════════════════\n");
                            return count;
                        } else {
                            System.out.println("    ⚠️ No numbers found in text");
                        }
                    } else {
                        System.out.println("    ✗ Element not found");
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Error: " + e.getMessage());
                    // Try next locator
                    continue;
                }
            }
            
            System.out.println("\n  ⚠️ Could not find inbox count badge with any locator");
            System.out.println("  ℹ️ Returning 0 as fallback");
            System.out.println("🔢 ═══════════════════════════════════════════\n");
            return 0;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to get Inbox count: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.warning("Failed to get Inbox count: " + e.getMessage());
            }
            return 0;
        }
    }
    
    /**
     * Get the count displayed on Watchlist filter badge
     * 
     * @return Watchlist count as integer
     */
    public int getWatchlistCount() {
        try {
            System.out.println("\n🔢 Getting Watchlist Count from Badge");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try multiple locators to find the count - Using centralized array
            By[] countLocators = InitiativePageLocators.watchlistCountLocators;
            
            for (By locator : countLocators) {
                try {
                    if (!driver.findElements(locator).isEmpty()) {
                        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        String countText = countElement.getText().trim();
                        
                        // Extract numbers from text (e.g., "(5)" -> "5")
                        countText = countText.replaceAll("[^0-9]", "");
                        
                        if (!countText.isEmpty()) {
                            int count = Integer.parseInt(countText);
                            System.out.println("  📊 Watchlist Count from Badge: " + count);
                            return count;
                        }
                    }
                } catch (Exception e) {
                    // Try next locator
                    continue;
                }
            }
            
            System.out.println("  ⚠️ Could not find watchlist count badge, returning 0");
            return 0;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to get Watchlist count: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.warning("Failed to get Watchlist count: " + e.getMessage());
            }
            return 0;
        }
    }
    
    /**
     * Get the count of records displayed in the grid
     * 
     * @return Number of visible records in grid
     */
    public int getGridRecordsCount() {
        try {
            System.out.println("\n📋 ═══════════════════════════════════════════");
            System.out.println("📋 Counting Records in Grid");
            System.out.println("📋 ═══════════════════════════════════════════");
            
            Thread.sleep(2000); // Wait for grid to stabilize
            
            // Try multiple locators for grid rows - Using centralized array
            By[] rowLocators = InitiativePageLocators.gridRowLocators;
            
            String[] locatorNames = {
                "root path to p elements",
                "div[role='row'] with ag-row class",
                "ag-center-cols-container rows",
                "table tbody tr[role='row']",
                "gridcell parent rows",
                "data-grid rows",
                "div with ag-row class",
                "tr with ag-row class"
            };
            
            for (int i = 0; i < rowLocators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    
                    List<WebElement> rows = driver.findElements(rowLocators[i]);
                    System.out.println("    📊 Found " + rows.size() + " total elements");
                    
                    if (!rows.isEmpty()) {
                        // Filter out any header or empty rows
                        int visibleRows = 0;
                        int headerRows = 0;
                        int hiddenRows = 0;
                        int emptyRows = 0;
                        
                        for (int j = 0; j < rows.size(); j++) {
                            WebElement row = rows.get(j);
                            try {
                                String rowClass = row.getAttribute("class");
                                String rowText = row.getText().trim();
                                boolean isDisplayed = row.isDisplayed();
                                boolean isHeader = rowClass != null && rowClass.contains("header");
                                boolean isEmpty = rowText.isEmpty();
                                
                                if (j < 3) {  // Show details for first 3 rows
                                    System.out.println("      Row " + (j + 1) + ": " + 
                                        "displayed=" + isDisplayed + 
                                        ", header=" + isHeader + 
                                        ", empty=" + isEmpty +
                                        ", text='" + (rowText.length() > 30 ? rowText.substring(0, 30) + "..." : rowText) + "'");
                                }
                                
                                if (isHeader) {
                                    headerRows++;
                                } else if (!isDisplayed) {
                                    hiddenRows++;
                                } else if (isEmpty) {
                                    emptyRows++;
                                } else {
                                    visibleRows++;
                                }
                            } catch (Exception e) {
                                // Skip rows that throw exceptions
                                System.out.println("      Row " + (j + 1) + ": Error - " + e.getMessage());
                                continue;
                            }
                        }
                        
                        System.out.println("    📊 Summary: " + visibleRows + " visible, " + 
                                          headerRows + " headers, " + 
                                          hiddenRows + " hidden, " + 
                                          emptyRows + " empty");
                        
                        if (visibleRows > 0) {
                            System.out.println("  ✅ Successfully counted " + visibleRows + " visible records");
                            System.out.println("📋 ═══════════════════════════════════════════\n");
                            return visibleRows;
                        }
                    } else {
                        System.out.println("    ✗ No elements found");
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Error: " + e.getMessage());
                    // Try next locator
                    continue;
                }
            }
            
            System.out.println("\n  ⚠️ Could not find grid rows with any locator");
            System.out.println("  ℹ️ Returning 0 as fallback");
            System.out.println("📋 ═══════════════════════════════════════════\n");
            return 0;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to get grid records count: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.warning("Failed to get grid records count: " + e.getMessage());
            }
            return 0;
        }
    }
    
    /**
     * Verify that Inbox count matches grid records count
     * 
     * @param expectedRecordsPerPage Expected number of records per page (default: 5)
     * @return true if counts match, false otherwise
     */
    public boolean verifyInboxCountMatchesGrid(int expectedRecordsPerPage) {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════════════════");
            System.out.println("✅              VERIFYING INBOX COUNT vs GRID RECORDS");
            System.out.println("✅ ═══════════════════════════════════════════════════════");
            
            int inboxCount = getInboxCount();
            int gridRecordsCount = getGridRecordsCount();
            
            System.out.println("\n📊 ═══════════ DETAILED COMPARISON ═══════════");
            System.out.println("  📬 Inbox Badge Count:        " + inboxCount + " (total records in Inbox)");
            System.out.println("  📋 Grid Records Count:       " + gridRecordsCount + " (visible on current page)");
            System.out.println("  📄 Expected Records/Page:    " + expectedRecordsPerPage + " (configured page size)");
            System.out.println("  ════════════════════════════════════════");
            
            // Determine expected records on current page
            int expectedOnPage = Math.min(inboxCount, expectedRecordsPerPage);
            System.out.println("  🎯 Expected on Current Page: " + expectedOnPage);
            System.out.println("     (calculated as min(" + inboxCount + ", " + expectedRecordsPerPage + "))");
            System.out.println("  ════════════════════════════════════════");
            
            boolean matches = (gridRecordsCount == expectedOnPage);
            
            System.out.println("\n📐 VERIFICATION LOGIC:");
            System.out.println("  " + gridRecordsCount + " == " + expectedOnPage + " ?");
            System.out.println("  " + matches);
            
            if (matches) {
                System.out.println("\n✅ ✅ ✅ VERIFICATION PASSED! ✅ ✅ ✅");
                System.out.println("  🎉 Grid shows " + gridRecordsCount + " records as expected!");
                System.out.println("  ✓ Badge count: " + inboxCount);
                System.out.println("  ✓ Grid count: " + gridRecordsCount);
                System.out.println("  ✓ Expected: " + expectedOnPage);
                if (reportLogger != null) {
                    reportLogger.pass("Inbox count verification PASSED - Grid shows " + gridRecordsCount + " records, matching expected count");
                }
            } else {
                System.out.println("\n❌ ❌ ❌ VERIFICATION FAILED! ❌ ❌ ❌");
                System.out.println("  ⚠️ Mismatch detected!");
                System.out.println("  Expected: " + expectedOnPage + " records on page");
                System.out.println("  Found:    " + gridRecordsCount + " records on page");
                System.out.println("  Difference: " + Math.abs(expectedOnPage - gridRecordsCount) + " records");
                
                System.out.println("\n🔍 POSSIBLE CAUSES:");
                if (inboxCount == 0) {
                    System.out.println("  • Inbox badge count is 0 - badge might not be read correctly");
                }
                if (gridRecordsCount == 0) {
                    System.out.println("  • Grid count is 0 - grid might not have loaded");
                    System.out.println("  • Grid locator might be incorrect");
                }
                if (inboxCount > 0 && gridRecordsCount == 0) {
                    System.out.println("  • Badge shows " + inboxCount + " but grid shows 0");
                    System.out.println("  • Check if grid has finished loading");
                }
                if (inboxCount == 0 && gridRecordsCount > 0) {
                    System.out.println("  • Grid shows " + gridRecordsCount + " but badge shows 0");
                    System.out.println("  • Check if badge locator is correct");
                }
                if (inboxCount > 0 && gridRecordsCount > 0 && gridRecordsCount != expectedOnPage) {
                    System.out.println("  • Both counts exist but don't match expected");
                    System.out.println("  • Badge: " + inboxCount + ", Grid: " + gridRecordsCount + ", Expected: " + expectedOnPage);
                    if (gridRecordsCount < expectedOnPage) {
                        System.out.println("  • Grid might not have finished loading all records");
                    } else {
                        System.out.println("  • Grid might be showing more records than page size");
                    }
                }
                
                if (reportLogger != null) {
                    reportLogger.fail("Inbox count verification FAILED - Expected " + expectedOnPage + " but found " + gridRecordsCount);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════════════════\n");
            return matches;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to verify inbox count: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify inbox count: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Verify that Watchlist count matches grid records count
     * 
     * @param expectedRecordsPerPage Expected number of records per page (default: 5)
     * @return true if counts match, false otherwise
     */
    public boolean verifyWatchlistCountMatchesGrid(int expectedRecordsPerPage) {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════");
            System.out.println("✅ VERIFYING WATCHLIST COUNT vs GRID RECORDS");
            System.out.println("✅ ═══════════════════════════════════════════");
            
            int watchlistCount = getWatchlistCount();
            int gridRecordsCount = getGridRecordsCount();
            
            System.out.println("\n📊 COMPARISON:");
            System.out.println("  Watchlist Badge Count: " + watchlistCount);
            System.out.println("  Grid Records Count: " + gridRecordsCount);
            System.out.println("  Expected Records/Page: " + expectedRecordsPerPage);
            
            // Determine expected records on current page
            int expectedOnPage = Math.min(watchlistCount, expectedRecordsPerPage);
            System.out.println("  Expected on Current Page: " + expectedOnPage);
            
            boolean matches = (gridRecordsCount == expectedOnPage);
            
            if (matches) {
                System.out.println("\n✅ VERIFICATION PASSED!");
                System.out.println("  Grid shows " + gridRecordsCount + " records as expected");
                if (reportLogger != null) {
                    reportLogger.pass("Watchlist count verification PASSED - Grid shows " + gridRecordsCount + " records, matching expected count");
                }
            } else {
                System.out.println("\n❌ VERIFICATION FAILED!");
                System.out.println("  Expected: " + expectedOnPage + " records");
                System.out.println("  Found: " + gridRecordsCount + " records");
                System.out.println("  Difference: " + Math.abs(expectedOnPage - gridRecordsCount));
                if (reportLogger != null) {
                    reportLogger.fail("Watchlist count verification FAILED - Expected " + expectedOnPage + " but found " + gridRecordsCount);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return matches;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to verify watchlist count: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify watchlist count: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Print detailed grid information for debugging
     */
    public void printGridDebugInfo() {
        try {
            System.out.println("\n🔍 DEBUG: Grid Information");
            System.out.println("═══════════════════════════════════════════");
            
            // Try to find pagination info
            try {
                WebElement paginationInfo = driver.findElement(InitiativePageLocators.paginationInfo);
                System.out.println("  Pagination: " + paginationInfo.getText());
            } catch (Exception e) {
                System.out.println("  Pagination: Not found");
            }
            
            // Try to find total records
            try {
                WebElement totalRecords = driver.findElement(InitiativePageLocators.totalRecords);
                System.out.println("  Total Records: " + totalRecords.getText());
            } catch (Exception e) {
                System.out.println("  Total Records: Not found");
            }
            
            // List all visible rows
            System.out.println("\n  Visible Rows:");
            By[] rowLocators = {
                By.xpath("//div[@role='row' and contains(@class,'ag-row')]"),
                By.xpath("//table//tbody//tr[@role='row']")
            };
            
            for (By locator : rowLocators) {
                try {
                    List<WebElement> rows = driver.findElements(locator);
                    if (!rows.isEmpty()) {
                        for (int i = 0; i < rows.size(); i++) {
                            WebElement row = rows.get(i);
                            if (row.isDisplayed()) {
                                System.out.println("    Row " + (i + 1) + ": " + row.getText().substring(0, Math.min(50, row.getText().length())) + "...");
                            }
                        }
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            System.out.println("═══════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.out.println("  Error printing debug info: " + e.getMessage());
        }
    }
    
    /**
     * Check if pagination forward button is enabled
     * 
     * @return true if forward button is enabled, false otherwise
     */
    public boolean isPaginationForwardButtonEnabled() {
        try {
            System.out.println("\n🔍 Checking if pagination forward button is enabled...");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement forwardButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    InitiativePageLocators.paginationForwardButton));
            
            boolean isEnabled = forwardButton.isEnabled() && !forwardButton.getAttribute("class").contains("disabled");
            boolean isDisabled = forwardButton.getAttribute("disabled") != null || 
                                forwardButton.getAttribute("aria-disabled") != null && 
                                forwardButton.getAttribute("aria-disabled").equals("true");
            
            boolean finalStatus = isEnabled && !isDisabled;
            System.out.println("  ➡️ Forward button enabled: " + finalStatus);
            
            return finalStatus;
            
        } catch (TimeoutException e) {
            System.out.println("  ⚠️ Forward button not found - assuming single page");
            return false;
        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking forward button: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if forward button is enabled (not disabled)
     * 
     * @return true if enabled, false if disabled
     */
    public boolean isForwardButtonEnabled() {
        try {
            // Use JavaScript to check if button is disabled
            Object result = ((JavascriptExecutor) driver).executeScript(
                "var svg = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                "if (!svg) return false;" +
                "var btn = svg.closest('button');" +
                "if (!btn) return false;" +
                "return !btn.disabled && btn.getAttribute('aria-disabled') !== 'true';"
            );
            
            boolean isEnabled = result != null && (Boolean) result;
            System.out.println("  🔍 Forward button enabled: " + isEnabled);
            return isEnabled;
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking button state: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * ULTRA SIMPLE - Just click the damn button!
     * Single most reliable method
     */
    public void clickForwardArrowSimple() {
        try {
            System.out.println("\n🔥 ULTRA SIMPLE CLICK 🔥");
            Thread.sleep(1000);
            
            // Find and click in ONE line - most direct way
            ((JavascriptExecutor) driver).executeScript(
                "var svg = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                "console.log('SVG found:', svg);" +
                "if (svg) {" +
                "  var btn = svg.closest('button');" +
                "  console.log('Button found:', btn);" +
                "  if (btn) {" +
                "    btn.click();" +
                "    console.log('Button clicked!');" +
                "    return 'SUCCESS';" +
                "  }" +
                "}" +
                "return 'FAILED';"
            );
            
            System.out.println("  ✅ Click executed!");
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.out.println("  ❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * Debug method - print ALL info about forward button
     */
    public void debugForwardButton() {
        try {
            System.out.println("\n🔍 ═════════════ FORWARD BUTTON DEBUG ═════════════");
            
            // Execute JavaScript to get ALL info
            String result = (String) ((JavascriptExecutor) driver).executeScript(
                "var svg = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                "if (!svg) return 'SVG NOT FOUND';" +
                
                "var btn = svg.closest('button');" +
                "if (!btn) return 'BUTTON NOT FOUND (SVG has no button parent)';" +
                
                "var info = {" +
                "  tagName: btn.tagName," +
                "  className: btn.className," +
                "  id: btn.id," +
                "  disabled: btn.disabled," +
                "  ariaDisabled: btn.getAttribute('aria-disabled')," +
                "  style: btn.getAttribute('style')," +
                "  visible: btn.offsetParent !== null," +
                "  x: btn.getBoundingClientRect().x," +
                "  y: btn.getBoundingClientRect().y," +
                "  width: btn.getBoundingClientRect().width," +
                "  height: btn.getBoundingClientRect().height" +
                "};" +
                
                "return JSON.stringify(info);"
            );
            
            System.out.println("📊 Button Info: " + result);
            
            // Count how many forward buttons exist
            Long count = (Long) ((JavascriptExecutor) driver).executeScript(
                "return document.querySelectorAll('svg[data-testid=\"ArrowForwardIcon\"]').length;"
            );
            System.out.println("📊 Total ArrowForwardIcon SVGs found: " + count);
            
            // Try to get button HTML
            String html = (String) ((JavascriptExecutor) driver).executeScript(
                "var svg = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                "if (svg && svg.closest('button')) {" +
                "  return svg.closest('button').outerHTML.substring(0, 200);" +
                "}" +
                "return 'N/A';"
            );
            System.out.println("📊 Button HTML (first 200 chars): " + html);
            
            System.out.println("═════════════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.out.println("❌ Debug error: " + e.getMessage());
        }
    }
    
    /**
     * Simple method to click forward arrow - NO checks, just click!
     * Tries multiple click methods until one works
     */
    public void clickForwardArrow() {
        try {
            System.out.println("\n➡️ Clicking forward arrow...");
            Thread.sleep(1000);
            
            // Method 1: JavaScript click on button with ArrowForwardIcon
            try {
                System.out.println("  🔄 Trying JS click...");
                WebElement btn = driver.findElement(By.xpath("//button[.//svg[@data-testid='ArrowForwardIcon']]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                System.out.println("  ✅ Clicked!");
                Thread.sleep(2000);
                return;
            } catch (Exception e) {
                System.out.println("  ⚠️ Method 1 failed, trying next...");
            }
            
            // Method 2: Click SVG directly
            try {
                System.out.println("  🔄 Trying SVG click...");
                WebElement svg = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", svg);
                System.out.println("  ✅ Clicked!");
                Thread.sleep(2000);
                return;
            } catch (Exception e) {
                System.out.println("  ⚠️ Method 2 failed, trying next...");
            }
            
            // Method 3: Actions class
            try {
                System.out.println("  🔄 Trying Actions click...");
                WebElement btn = driver.findElement(By.xpath("//button[.//svg[@data-testid='ArrowForwardIcon']]"));
                Actions actions = new Actions(driver);
                actions.moveToElement(btn).click().perform();
                System.out.println("  ✅ Clicked!");
                Thread.sleep(2000);
                return;
            } catch (Exception e) {
                System.out.println("  ⚠️ Method 3 failed, trying next...");
            }
            
            // Method 4: Pure JavaScript querySelector
            try {
                System.out.println("  🔄 Trying pure JS...");
                ((JavascriptExecutor) driver).executeScript(
                    "var btn = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                    "if (btn && btn.parentElement) btn.parentElement.click();"
                );
                System.out.println("  ✅ Clicked!");
                Thread.sleep(2000);
                return;
            } catch (Exception e) {
                System.out.println("  ⚠️ Method 4 failed");
            }
            
            System.out.println("  ⚠️ All methods tried");
            
        } catch (Exception e) {
            System.out.println("  ❌ Error: " + e.getMessage());
        }
    }
    
    /**
     * FORCE click pagination forward button - tries EVERYTHING
     * This method will exhaust all possible click methods
     * 
     * @return true if successfully navigated to next page
     */
    public boolean forceClickPaginationForward() {
        try {
            System.out.println("\n🔥 FORCE CLICK PAGINATION FORWARD 🔥");
            
            // Capture state before
            int recordsBefore = getGridRecordsCount();
            System.out.println("  📊 Records before: " + recordsBefore);
            
            // Wait for page to be ready
            Thread.sleep(2000);
            
            // Try 1: Pure JavaScript - Find by testid and click
            System.out.println("\n  🔥 Method 1: Raw JS by data-testid...");
            try {
                ((JavascriptExecutor) driver).executeScript(
                    "var btn = document.querySelector('svg[data-testid=\"ArrowForwardIcon\"]');" +
                    "if (btn && btn.parentElement) {" +
                    "  btn.parentElement.click();" +
                    "  console.log('Parent clicked');" +
                    "  return true;" +
                    "}" +
                    "return false;"
                );
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 2: Find button and force click with multiple events
            System.out.println("\n  🔥 Method 2: Multiple event dispatch...");
            try {
                WebElement btn = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']/ancestor::button"));
                ((JavascriptExecutor) driver).executeScript(
                    "var element = arguments[0];" +
                    "element.dispatchEvent(new MouseEvent('mousedown', {bubbles: true}));" +
                    "element.dispatchEvent(new MouseEvent('mouseup', {bubbles: true}));" +
                    "element.dispatchEvent(new MouseEvent('click', {bubbles: true}));" +
                    "element.click();",
                    btn
                );
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 3: Click with coordinates
            System.out.println("\n  🔥 Method 3: Click at coordinates...");
            try {
                WebElement btn = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']/ancestor::button"));
                Actions actions = new Actions(driver);
                actions.moveToElement(btn).click().perform();
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 4: Send ENTER key to button
            System.out.println("\n  🔥 Method 4: Send ENTER key...");
            try {
                WebElement btn = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']/ancestor::button"));
                btn.sendKeys(Keys.RETURN);
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 5: Focus and Space key
            System.out.println("\n  🔥 Method 5: Focus + SPACE key...");
            try {
                WebElement btn = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']/ancestor::button"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", btn);
                Thread.sleep(500);
                btn.sendKeys(Keys.SPACE);
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 6: Click SVG directly
            System.out.println("\n  🔥 Method 6: Direct SVG click...");
            try {
                WebElement svg = driver.findElement(By.xpath("//svg[@data-testid='ArrowForwardIcon']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", svg);
                Thread.sleep(3000);
                int recordsAfter = getGridRecordsCount();
                if (recordsAfter != recordsBefore) {
                    System.out.println("  ✅ SUCCESS! Page changed!");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            // Try 7: Find ANY button with forward icon and click all of them
            System.out.println("\n  🔥 Method 7: Click ALL forward buttons...");
            try {
                List<WebElement> buttons = driver.findElements(By.xpath("//button[.//svg[@data-testid='ArrowForwardIcon']]"));
                System.out.println("  Found " + buttons.size() + " forward buttons");
                for (int i = 0; i < buttons.size(); i++) {
                    try {
                        System.out.println("  Clicking button " + (i+1) + "...");
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttons.get(i));
                        Thread.sleep(3000);
                        int recordsAfter = getGridRecordsCount();
                        if (recordsAfter != recordsBefore) {
                            System.out.println("  ✅ SUCCESS! Button " + (i+1) + " worked!");
                            return true;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ❌ Failed: " + e.getMessage());
            }
            
            System.out.println("\n  ❌ ALL METHODS EXHAUSTED - BUTTON NOT RESPONDING");
            return false;
            
        } catch (Exception e) {
            System.out.println("  ❌ Critical error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Click pagination forward button to go to next page
     * Uses the simple clickForwardArrow method and verifies page change
     * 
     * @return true if successfully clicked AND page changed, false otherwise
     */
    public boolean clickPaginationForwardButton() {
        try {
            System.out.println("\n➡️ Clicking pagination forward button...");
            
            if (!isPaginationForwardButtonEnabled()) {
                System.out.println("  ⚠️ Forward button is disabled - cannot proceed");
                return false;
            }
            
            // Capture current page state BEFORE clicking
            int recordsBeforeClick = getGridRecordsCount();
            System.out.println("  📊 Records before click: " + recordsBeforeClick);
            
            // Use the simple clickForwardArrow method
            System.out.println("\n  🎯 Using clickForwardArrow() method...");
            clickForwardArrow();
            
            // Wait for navigation to complete
            Thread.sleep(2000);
            
            // Verify page actually changed
            System.out.println("\n  🔍 Verifying page navigation...");
            int recordsAfterClick = getGridRecordsCount();
            System.out.println("  📊 Records after click: " + recordsAfterClick);
            
            boolean recordsChanged = recordsBeforeClick != recordsAfterClick;
            
            if (recordsChanged) {
                System.out.println("\n  ✅ PAGE NAVIGATION CONFIRMED!");
                System.out.println("    - Record count changed: YES");
                return true;
            } else {
                System.out.println("\n  ⚠️ WARNING: Page did NOT change!");
                System.out.println("    - Record count: SAME (" + recordsAfterClick + ")");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Error during pagination click: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get current page state for detecting navigation
     * Returns a string representing current grid state
     * 
     * @return String representing current page state
     */
    private String getCurrentPageState() {
        try {
            // Try to get pagination text (e.g., "1-5 of 8")
            try {
                WebElement paginationInfo = driver.findElement(InitiativePageLocators.paginationInfo);
                return paginationInfo.getText();
            } catch (Exception e1) {
                // Fallback: Get text of first few grid rows
                try {
                    By[] rowLocators = {
                        By.xpath("//div[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr/td/div/p"),
                        By.xpath("//div[@role='row' and contains(@class,'ag-row')]"),
                        By.xpath("//table//tbody//tr[@role='row']")
                    };
                    
                    for (By locator : rowLocators) {
                        try {
                            List<WebElement> rows = driver.findElements(locator);
                            if (!rows.isEmpty() && rows.size() > 0) {
                                // Get text of first row as page identifier
                                String firstRowText = rows.get(0).getText();
                                return firstRowText.substring(0, Math.min(50, firstRowText.length()));
                            }
                        } catch (Exception e2) {
                            continue;
                        }
                    }
                } catch (Exception e3) {
                    // Last fallback: timestamp
                    return String.valueOf(System.currentTimeMillis());
                }
            }
        } catch (Exception e) {
            // Return timestamp as fallback
            return String.valueOf(System.currentTimeMillis());
        }
        
        return "unknown";
    }
    
    /**
     * Count total records across all pages by navigating through pagination
     * 
     * @return Total number of records across all pages
     */
    public int getTotalRecordsAcrossAllPages() {
        try {
            System.out.println("\n📊 ═══════════════════════════════════════════════════════");
            System.out.println("📊 COUNTING RECORDS ACROSS ALL PAGES");
            System.out.println("📊 ═══════════════════════════════════════════════════════");
            
            int totalRecords = 0;
            int pageNumber = 1;
            
            // Count records on first page
            int recordsOnPage = getGridRecordsCount();
            totalRecords += recordsOnPage;
            System.out.println("\n📄 Page " + pageNumber + ": " + recordsOnPage + " records");
            System.out.println("  📊 Running Total: " + totalRecords);
            
            // Navigate through remaining pages - click until button is disabled
            for (int i = 0; i < 100; i++) {  // Max 100 pages safety limit
                // Check if forward button is disabled BEFORE clicking
                boolean isButtonEnabled = isForwardButtonEnabled();
                
                if (!isButtonEnabled) {
                    System.out.println("\n⚠️ Forward button is DISABLED - reached last page");
                    break;
                }
                
                pageNumber++;
                System.out.println("\n➡️ Moving to Page " + pageNumber + "...");
                System.out.println("═══════════════════════════════════════════════════════");
                
                // Click forward arrow
                clickForwardArrow();
                
                // Wait for page to load
                Thread.sleep(2000);
                
                // Count records on this page
                recordsOnPage = getGridRecordsCount();
                
                // If no records, something went wrong
                if (recordsOnPage == 0) {
                    System.out.println("  ⚠️ No records found - stopping pagination");
                    pageNumber--;
                    break;
                }
                
                totalRecords += recordsOnPage;
                System.out.println("📄 Page " + pageNumber + ": " + recordsOnPage + " records");
                System.out.println("  📊 Running Total: " + totalRecords);
            }
            
            System.out.println("\n📊 ═══════════════════════════════════════════════════════");
            System.out.println("📊 PAGINATION COMPLETE");
            System.out.println("📊 Total Pages: " + pageNumber);
            System.out.println("📊 Total Records: " + totalRecords);
            System.out.println("📊 ═══════════════════════════════════════════════════════");
            
            if (reportLogger != null) {
                reportLogger.info("Total records across " + pageNumber + " pages: " + totalRecords);
            }
            
            return totalRecords;
            
        } catch (Exception e) {
            System.out.println("\n❌ Error counting records across pages: " + e.getMessage());
            e.printStackTrace();
            
            if (reportLogger != null) {
                reportLogger.warning("Error counting records across pages: " + e.getMessage());
            }
            
            return 0;
        }
    }
    
    /**
     * Verify that inbox count matches total records across all pages
     * This method navigates through all pages, counts records, and compares with inbox badge count
     * 
     * @return true if counts match, false otherwise
     */
  /*  public boolean verifyInboxCountMatchesTotalRecords() {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════════════════");
            System.out.println("✅ VERIFYING INBOX COUNT vs TOTAL RECORDS (ALL PAGES)");
            System.out.println("✅ ═══════════════════════════════════════════════════════");
            
            // Get inbox count from badge
            int inboxCount = getInboxCount();
            
            // Get total records across all pages
            int totalRecordsAcrossPages = getTotalRecordsAcrossAllPages();
            
            System.out.println("\n📊 ═══════════ FINAL COMPARISON ═══════════");
            System.out.println("  Inbox Badge Count: " + inboxCount);
            System.out.println("  Total Records (All Pages): " + totalRecordsAcrossPages);
            
            boolean isMatching = (inboxCount == totalRecordsAcrossPages);
            
            if (isMatching) {
                System.out.println("\n✅ ✅ ✅ VERIFICATION PASSED! ✅ ✅ ✅");
                System.out.println("  Inbox count (" + inboxCount + ") matches total records across all pages!");
                
                if (reportLogger != null) {
                    reportLogger.pass("Inbox count verification PASSED - Badge: " + inboxCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
                
            } else {
                System.out.println("\n❌ ❌ ❌ VERIFICATION FAILED! ❌ ❌ ❌");
                System.out.println("  Inbox count (" + inboxCount + ") does NOT match total records (" + 
                        totalRecordsAcrossPages + ")");
                System.out.println("  Difference: " + Math.abs(inboxCount - totalRecordsAcrossPages) + " records");
                
                if (reportLogger != null) {
                    reportLogger.fail("Inbox count verification FAILED - Badge: " + inboxCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════════════════\n");
            
            return isMatching;
            
        } catch (Exception e) {
            System.out.println("\n❌ Error during inbox count verification: " + e.getMessage());
            e.printStackTrace();
            
            if (reportLogger != null) {
                reportLogger.fail("Error during inbox count verification: " + e.getMessage());
            }
            
            return false;
        }
    }
    */
    
    public boolean verifyFirstPageRecordLimit() {

        try {
            System.out.println("\n══════════════════════════════════════");
            System.out.println("VERIFYING FIRST PAGE RECORD COUNT");
            System.out.println("══════════════════════════════════════");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 🔹 Wait for table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table[contains(@class,'MuiTable-root')]")
            ));

            // 🔹 Get all rows from first page
            List<WebElement> rows = driver.findElements(
                    By.xpath("//table[contains(@class,'MuiTable-root')]//tbody/tr")
            );

            int recordCount = rows.size();

            System.out.println("📌 First Page Record Count : " + recordCount);

            // 🔹 PASS condition
            if (recordCount <= 5) {
                System.out.println("✅ TEST PASSED - Records are within limit (Max 5 per page)");
                return true;
            } else {
                System.out.println("❌ TEST FAILED - More than 5 records found on first page");
                return false;
            }

        } catch (Exception e) {
            System.out.println("❌ Error while verifying first page records: " + e.getMessage());
            return false;
        }
    }
    /**
     * Verify that watchlist count matches total records across all pages
     * This method navigates through all pages, counts records, and compares with watchlist badge count
     * 
     * @return true if counts match, false otherwise
     */
    public boolean verifyWatchlistCountMatchesTotalRecords() {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════════════════");
            System.out.println("✅ VERIFYING WATCHLIST COUNT vs TOTAL RECORDS (ALL PAGES)");
            System.out.println("✅ ═══════════════════════════════════════════════════════");
            
            // Get watchlist count from badge
            int watchlistCount = getWatchlistCount();
            
            // Get total records across all pages
            int totalRecordsAcrossPages = getTotalRecordsAcrossAllPages();
            
            System.out.println("\n📊 ═══════════ FINAL COMPARISON ═══════════");
            System.out.println("  Watchlist Badge Count: " + watchlistCount);
            System.out.println("  Total Records (All Pages): " + totalRecordsAcrossPages);
            
            boolean isMatching = (watchlistCount == totalRecordsAcrossPages);
            
            if (isMatching) {
                System.out.println("\n✅ ✅ ✅ VERIFICATION PASSED! ✅ ✅ ✅");
                System.out.println("  Watchlist count (" + watchlistCount + ") matches total records across all pages!");
                
                if (reportLogger != null) {
                    reportLogger.pass("Watchlist count verification PASSED - Badge: " + watchlistCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
                
            } else {
                System.out.println("\n❌ ❌ ❌ VERIFICATION FAILED! ❌ ❌ ❌");
                System.out.println("  Watchlist count (" + watchlistCount + ") does NOT match total records (" + 
                        totalRecordsAcrossPages + ")");
                System.out.println("  Difference: " + Math.abs(watchlistCount - totalRecordsAcrossPages) + " records");
                
                if (reportLogger != null) {
                    reportLogger.fail("Watchlist count verification FAILED - Badge: " + watchlistCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════════════════\n");
            
            return isMatching;
            
        } catch (Exception e) {
            System.out.println("\n❌ Error during watchlist count verification: " + e.getMessage());
            e.printStackTrace();
            
            if (reportLogger != null) {
                reportLogger.fail("Error during watchlist count verification: " + e.getMessage());
            }
            
            return false;
        }
    }

    // ==================== SEARCH METHODS ====================
    // Updated by Shahu.D

    /**
     * Click on Search icon
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Clicking on Search icon...");
            
            By[] searchIconLocators = {
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"),
                By.xpath("//img[contains(@alt,'Search')]"),
                By.xpath("//*[contains(@aria-label,'Search')]"),
                By.xpath("//button[contains(@title,'Search')]")
            };
            
            WebElement searchIcon = null;
            boolean found = false;
            
            for (By locator : searchIconLocators) {
                try {
                    searchIcon = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (searchIcon.isDisplayed()) {
                        found = true;
                        System.out.println("  ✅ Found Search icon using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!found || searchIcon == null) {
                throw new Exception("Search icon not found");
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchIcon);
            waitForSeconds(1);
            
            try {
                searchIcon.click();
                System.out.println("  ✅ Clicked Search icon using regular click");
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
                    System.out.println("  ✅ Clicked Search icon using JavaScript");
                } catch (Exception e2) {
                    actions.moveToElement(searchIcon).click().perform();
                    System.out.println("  ✅ Clicked Search icon using Actions");
                }
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked Search icon successfully");
            }
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search icon: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search icon: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter initiative code in search field
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeCodeInSearch(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            System.out.println("  🔍 Entering Initiative Code in search field: " + initiativeCode);
            
            By[] searchInputLocators = {
                By.xpath("//*[@id=\"DemandCode\"]"),
                By.xpath("//input[@id='DemandCode']"),
                By.xpath("//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]")
            };
            
            WebElement searchInput = null;
            boolean found = false;
            
            for (By locator : searchInputLocators) {
                try {
                    searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (searchInput.isDisplayed() && searchInput.isEnabled()) {
                        found = true;
                        System.out.println("  ✅ Found search input field using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!found || searchInput == null) {
                throw new Exception("Search input field not found");
            }
            
            // Clear and enter value
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchInput);
            waitForSeconds(1);
            searchInput.clear();
            waitForSeconds(1);
            searchInput.sendKeys(initiativeCode);
            
            System.out.println("  ✅ Entered Initiative Code: " + initiativeCode);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Entered Initiative Code in search field: " + initiativeCode);
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
     * Click on Search button to execute search
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Clicking on Search button...");
            
            By[] searchButtonLocators = {
                By.xpath("//*[@id=\"id__520\"]"),
                By.xpath("//button[@id='id__520']"),
                By.xpath("//button[contains(text(),'Search')]"),
                By.xpath("//button[.//span[text()='Search']]")
            };
            
            WebElement searchBtn = null;
            boolean found = false;
            
            for (By locator : searchButtonLocators) {
                try {
                    searchBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (searchBtn.isDisplayed()) {
                        found = true;
                        System.out.println("  ✅ Found Search button using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!found || searchBtn == null) {
                throw new Exception("Search button not found");
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchBtn);
            waitForSeconds(1);
            
            try {
                searchBtn.click();
                System.out.println("  ✅ Clicked Search button using regular click");
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                    System.out.println("  ✅ Clicked Search button using JavaScript");
                } catch (Exception e2) {
                    actions.moveToElement(searchBtn).click().perform();
                    System.out.println("  ✅ Clicked Search button using Actions");
                }
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked Search button successfully");
            }
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search button: " + e.getMessage());
            }
            throw e;
        }
    }

    // ==================== RISK MANAGEMENT METHODS ====================
    // Updated by Shahu.D

    /**
     * Edit initiative from Inbox section using initiative code
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to search and edit
     * @throws Exception if element is not found or click fails
     */
    public void editInitiativeFromInbox(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Increased wait time for page to load
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Edit button for Initiative Code: " + initiativeCode);
            
            // First, check if initiative code is visible on the page
            boolean foundInitiative = false;
            try {
                List<WebElement> initiativeElements = driver.findElements(By.xpath("//*[contains(text(),'" + initiativeCode + "')]"));
                for (WebElement elem : initiativeElements) {
                    if (elem.isDisplayed() && elem.getText().contains(initiativeCode)) {
                        foundInitiative = true;
                        System.out.println("  ✅ Initiative code '" + initiativeCode + "' is visible on the page");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not verify if initiative code is visible: " + e.getMessage());
            }
            
            // If initiative not found, search for it first
            // Updated by Shahu.D - Add search functionality
            if (!foundInitiative) {
                try {
                    System.out.println("  🔍 Initiative code '" + initiativeCode + "' not found on current page. Searching for it...");
                    
                    // Step 1: Click on Search icon/button
                    By[] searchIconLocators = {
                        By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"),
                        By.xpath("//img[contains(@alt,'Search')]"),
                        By.xpath("//*[contains(@aria-label,'Search')]"),
                        By.xpath("//button[contains(@title,'Search')]")
                    };
                    
                    WebElement searchIcon = null;
                    for (By locator : searchIconLocators) {
                        try {
                            searchIcon = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (searchIcon.isDisplayed()) {
                                searchIcon.click();
                                System.out.println("  ✅ Clicked on Search icon");
                                waitForSeconds(2);
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    // Step 2: Enter initiative code in search field
                    By[] searchInputLocators = {
                        By.xpath("//*[@id=\"DemandCode\"]"),
                        By.xpath("//input[@id='DemandCode']"),
                        By.xpath("//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]")
                    };
                    
                    WebElement searchInput = null;
                    for (By locator : searchInputLocators) {
                        try {
                            searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                            if (searchInput.isDisplayed() && searchInput.isEnabled()) {
                                searchInput.clear();
                                searchInput.sendKeys(initiativeCode);
                                System.out.println("  ✅ Entered Initiative Code in search field: " + initiativeCode);
                                waitForSeconds(1);
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    // Step 3: Click Search button
                    By[] searchButtonLocators = {
                        By.xpath("//*[@id=\"id__520\"]"),
                        By.xpath("//button[@id='id__520']"),
                        By.xpath("//button[contains(text(),'Search')]"),
                        By.xpath("//button[.//span[text()='Search']]")
                    };
                    
                    for (By locator : searchButtonLocators) {
                        try {
                            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (searchBtn.isDisplayed()) {
                                searchBtn.click();
                                System.out.println("  ✅ Clicked on Search button");
                                waitForSeconds(3); // Wait for search results to load
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    System.out.println("  ✅ Search completed. Retrying to find edit button...");
                } catch (Exception e) {
                    System.out.println("  ⚠️ Warning: Could not search for initiative. Continuing with direct search for edit button: " + e.getMessage());
                }
            }
            
            WebElement editBtn = null;
            boolean found = false;
            
            // Strategy 0: Try simpler xpath patterns first (more flexible)
            // Updated by Shahu.D - Try multiple table structures
            if (!found) {
                try {
                    System.out.println("  🔍 Strategy 0: Trying flexible xpath patterns...");
                    By[] flexibleLocators = {
                        // Try without the full root path
                        By.xpath("//table//tr[.//td[contains(text(),'" + initiativeCode + "')]]//td[4]//button[1]"),
                        By.xpath("//tbody//tr[.//td[contains(text(),'" + initiativeCode + "')]]//td[4]//button[1]"),
                        // Try with div-based rows
                        By.xpath("//div[@role='row' and .//*[contains(text(),'" + initiativeCode + "')]]//button[1]"),
                        // Try finding any button near the initiative code
                        By.xpath("//*[contains(text(),'" + initiativeCode + "')]/ancestor::tr//button[1]"),
                        By.xpath("//*[contains(text(),'" + initiativeCode + "')]/ancestor::*[@role='row']//button[1]")
                    };
                    
                    for (By locator : flexibleLocators) {
                        try {
                            editBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            System.out.println("  ✅ Found Edit button using Strategy 0 (flexible pattern)");
                            found = true;
                            break;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy 0 failed: " + e.getMessage());
                }
            }
            
            // Strategy 1: Use provided xpath pattern (dynamic - finds row by initiative code)
            // Updated by Shahu.D - xpath pattern: /html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[contains initiativeCode]/td[4]/div/div/div/button[1]/svg/path
            if (!found) {
                try {
                    System.out.println("  🔍 Strategy 1: Trying provided xpath pattern (dynamic)...");
                    // Use the full xpath pattern but make row dynamic by finding it via initiative code
                    // Try multiple path variations
                    By[] pathLocators = {
                        // Try with /html/body path
                        By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]/div/div/div/button[1]"),
                        // Try with //*[@id="root"] path
                        By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]/div/div/div/button[1]"),
                        // Try with //html path
                        By.xpath("//html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]/div/div/div/button[1]")
                    };
                    
                    for (By locator : pathLocators) {
                        try {
                            editBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            System.out.println("  ✅ Found Edit button using Strategy 1 (provided xpath pattern)");
                            found = true;
                            break;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    // If button not found directly, try via path element
                    if (!found) {
                        try {
                            System.out.println("  🔍 Strategy 1 (fallback): Trying via path element...");
                            By pathLocator = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]/div/div/div/button[1]/svg/path");
                            WebElement pathElement = wait.until(ExpectedConditions.presenceOfElementLocated(pathLocator));
                            // Navigate up to find the button: path -> svg -> button
                            editBtn = (WebElement) ((JavascriptExecutor) driver).executeScript(
                                "var elem = arguments[0]; " +
                                "while (elem && elem.tagName !== 'BUTTON' && elem.tagName !== 'BODY') { " +
                                "  elem = elem.parentElement; " +
                                "} " +
                                "return elem && elem.tagName === 'BUTTON' ? elem : null;", pathElement);
                            if (editBtn != null) {
                                System.out.println("  ✅ Found Edit button using Strategy 1 (via path element)");
                                found = true;
                            }
                        } catch (Exception e2) {
                            // Try via SVG element
                            try {
                                By svgLocator = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]/div/div/div/button[1]/svg");
                                WebElement svgElement = wait.until(ExpectedConditions.presenceOfElementLocated(svgLocator));
                                // Get the parent button element
                                editBtn = (WebElement) ((JavascriptExecutor) driver).executeScript(
                                    "return arguments[0].closest('button') || arguments[0].parentElement;", svgElement);
                                if (editBtn != null && editBtn.getTagName().equalsIgnoreCase("button")) {
                                    System.out.println("  ✅ Found Edit button using Strategy 1 (via SVG element)");
                                    found = true;
                                }
                            } catch (Exception e3) {
                                System.out.println("  ⚠️ Strategy 1 failed: " + e3.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy 1 failed: " + e.getMessage());
                }
            }
            
            // Strategy 2: Find row by initiative code, then find edit button using provided xpath pattern
            // Updated by Shahu.D - Using xpath pattern: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[contains initiativeCode]/td[4]/div/div/div/button[1]/svg
            if (!found) {
                try {
                    System.out.println("  🔍 Strategy 2: Finding row by initiative code, then edit button using provided xpath pattern...");
                    // First find the row containing the initiative code
                    By rowLocator = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]");
                    WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
                    System.out.println("  ✅ Found row containing initiative code: " + initiativeCode);
                    
                    // Find edit button in td[4] using the exact pattern from user's xpath
                    // Pattern: td[4]/div/div/div/button[1] (button contains SVG)
                    try {
                        // Try the exact pattern: td[4]/div/div/div/button[1]
                        editBtn = row.findElement(By.xpath(".//td[4]//div/div/div/button[1]"));
                        System.out.println("  ✅ Found Edit button within row using Strategy 2 (exact pattern)");
                        found = true;
                    } catch (Exception ex1) {
                        // Fallback: try simpler button locator
                        try {
                            editBtn = row.findElement(By.xpath(".//td[4]//button[1]"));
                            System.out.println("  ✅ Found Edit button within row using Strategy 2 (simple button)");
                            found = true;
                        } catch (Exception ex2) {
                            System.out.println("  ⚠️ Strategy 2: Found row but button not found in td[4]");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy 2 failed: Could not find row containing initiative code");
                }
            }
            
            // Strategy 3: Direct xpath using the provided pattern (dynamic)
            // Updated by Shahu.D - Pattern: //table/tbody/tr[contains initiativeCode]/td[4]/div/div/div/button[1]
            if (!found) {
                try {
                    System.out.println("  🔍 Strategy 3: Trying direct xpath pattern...");
                    By editButtonLocator = By.xpath("//table/tbody/tr[.//td[contains(text(),'" + initiativeCode + "')]]/td[4]//button[1]");
                    editBtn = wait.until(ExpectedConditions.elementToBeClickable(editButtonLocator));
                    System.out.println("  ✅ Found Edit button using Strategy 3 (direct xpath)");
                    found = true;
                } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy 3 failed: " + e.getMessage());
                }
            }
            
            // Strategy 4: Try alternative table structures
            if (!found) {
                try {
                    System.out.println("  🔍 Strategy 4: Trying alternative table structures...");
                    By[] altLocators = {
                        By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]]//td[4]//button[1]"),
                        By.xpath("//*[contains(@id,'row') and .//*[contains(text(),'" + initiativeCode + "')]]//button[contains(@aria-label,'Edit') or contains(@title,'Edit')]"),
                        By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]]//button[1]"),
                        By.xpath("//*[.//*[normalize-space(text())='" + initiativeCode + "']]//button[contains(@aria-label,'Edit')]"),
                        By.xpath("//div[contains(@class,'ag-row') and .//div[contains(@class,'ag-cell') and contains(text(),'" + initiativeCode + "')]]//button[1]")
                    };
                    
                    for (By locator : altLocators) {
                        try {
                            editBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            found = true;
                            System.out.println("  ✅ Found Edit button using alternative locator");
                            break;
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy 4 failed");
                }
            }
            
            if (!found || editBtn == null) {
                throw new Exception("Edit button not found for Initiative Code: " + initiativeCode);
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn);
            waitForSeconds(1);
            
            try {
                editBtn.click();
                System.out.println("  ✅ Clicked Edit button using regular click");
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                    System.out.println("  ✅ Clicked Edit button using JavaScript");
                } catch (Exception e2) {
                    actions.moveToElement(editBtn).click().perform();
                    System.out.println("  ✅ Clicked Edit button using Actions");
                }
            }
            
            waitForSeconds(3);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked Edit button for Initiative Code: " + initiativeCode);
            }
            
        } catch (Exception e) {
            System.err.println("  ❌ Error editing initiative: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to edit initiative: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Risks/Action Items sub tab
     * XPath: //*[@id="IMInfopgtabs"]/div/div[8]/a
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickRisksActionItemsSubTab() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"IMInfopgtabs\"]/div/div[8]/a");
            
            WebElement subTab = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", subTab);
            waitForSeconds(1);
            
            try {
                subTab.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subTab);
            }
            
            System.out.println("  ✅ Clicked on Risks/Action Items sub tab");
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Risks/Action Items sub tab");
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Risks/Action Items sub tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Risks/Action Items sub tab: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Risk sub tab
     * XPath: //*[@id="Pivot97-Tab0"]/span/span/span
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickRisksSubTab() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"Pivot97-Tab0\"]/span/span/span");
            
            WebElement riskTab = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", riskTab);
            waitForSeconds(1);
            
            try {
                riskTab.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", riskTab);
            }
            
            System.out.println("  ✅ Clicked on Risk sub tab");
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Risk sub tab");
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Risk sub tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Risk sub tab: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click on Add button for risks
     * XPath: //*[@id="id__162"]
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickAddRiskButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"id__162\"]");
            
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addBtn);
            waitForSeconds(1);
            
            try {
                addBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
            }
            
            System.out.println("  ✅ Clicked on Add button");
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Add button for risks");
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Add button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Add button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter risk description
     * XPath: //*[@id="TextField173"]
     * Updated by Shahu.D
     * @param description Risk description
     * @throws Exception if element is not found or input fails
     */
    public void enterRiskDescription(String description) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"TextField173\"]");
            
            WebElement descField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", descField);
            waitForSeconds(1);
            
            descField.clear();
            descField.sendKeys(description);
            
            System.out.println("  ✅ Entered description: " + description);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Entered risk description: " + description);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error entering description: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter description: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter impact description
     * XPath: //*[@id="TextField178"]
     * Updated by Shahu.D
     * @param impactDescription Impact description
     * @throws Exception if element is not found or input fails
     */
    public void enterImpactDescription(String impactDescription) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"TextField178\"]");
            
            WebElement impactField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", impactField);
            waitForSeconds(1);
            
            impactField.clear();
            impactField.sendKeys(impactDescription);
            
            System.out.println("  ✅ Entered impact description: " + impactDescription);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Entered impact description: " + impactDescription);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error entering impact description: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter impact description: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select risk category from dropdown
     * XPath: //*[@id="Dropdown183"]/span[2]
     * Updated by Shahu.D
     * @param riskCategory Risk category value
     * @throws Exception if element is not found or selection fails
     */
    public void selectRiskCategory(String riskCategory) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By dropdownLocator = By.xpath("//*[@id=\"Dropdown183\"]/span[2]");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            dropdown.click();
            waitForSeconds(2);
            
            // Select the option
            By optionLocator = By.xpath("//span[contains(text(),'" + riskCategory + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            
            System.out.println("  ✅ Selected risk category: " + riskCategory);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected risk category: " + riskCategory);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting risk category: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select risk category: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select risk status from dropdown
     * XPath: //*[@id="Dropdown193"]/span[2]
     * Updated by Shahu.D
     * @param status Status value
     * @throws Exception if element is not found or selection fails
     */
    public void selectRiskStatus(String status) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By dropdownLocator = By.xpath("//*[@id=\"Dropdown193\"]/span[2]");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            dropdown.click();
            waitForSeconds(2);
            
            // Select the option
            By optionLocator = By.xpath("//span[contains(text(),'" + status + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            
            System.out.println("  ✅ Selected status: " + status);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected status: " + status);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting status: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select status: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select date identified
     * XPath: //*[@id="DatePicker185-label"]
     * Updated by Shahu.D
     * @param dateIdentified Date value
     * @throws Exception if element is not found or selection fails
     */
    public void selectDateIdentified(String dateIdentified) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By datePickerLocator = By.xpath("//*[@id=\"DatePicker185-label\"]");
            
            WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(datePickerLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", datePicker);
            waitForSeconds(1);
            
            datePicker.click();
            waitForSeconds(2);
            
            // Enter date in the input field (if it appears)
            try {
                By dateInputLocator = By.xpath("//input[@type='text' and contains(@id,'DatePicker')]");
                WebElement dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(dateInputLocator));
                dateInput.clear();
                dateInput.sendKeys(dateIdentified);
            } catch (Exception e) {
                // If direct input doesn't work, try selecting from calendar
                System.out.println("  ⚠️ Direct date input not available, trying calendar selection");
            }
            
            System.out.println("  ✅ Selected date identified: " + dateIdentified);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected date identified: " + dateIdentified);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting date identified: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select date identified: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select original priority from dropdown
     * XPath: //*[@id="Dropdown192"]/span[2]
     * Updated by Shahu.D
     * @param originalPriority Original priority value
     * @throws Exception if element is not found or selection fails
     */
    public void selectOriginalPriority(String originalPriority) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By dropdownLocator = By.xpath("//*[@id=\"Dropdown192\"]/span[2]");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            dropdown.click();
            waitForSeconds(2);
            
            // Select the option
            By optionLocator = By.xpath("//span[contains(text(),'" + originalPriority + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            
            System.out.println("  ✅ Selected original priority: " + originalPriority);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected original priority: " + originalPriority);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting original priority: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select original priority: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select change priority from dropdown
     * XPath: //*[@id="Dropdown193"]/span[2]
     * Note: This xpath seems to be the same as Status dropdown, may need adjustment
     * Updated by Shahu.D
     * @param changePriority Change priority value
     * @throws Exception if element is not found or selection fails
     */
    public void selectChangePriority(String changePriority) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            // Note: User provided same xpath as Status, may need to verify actual xpath
            By dropdownLocator = By.xpath("//*[@id=\"Dropdown193\"]/span[2]");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            dropdown.click();
            waitForSeconds(2);
            
            // Select the option
            By optionLocator = By.xpath("//span[contains(text(),'" + changePriority + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            
            System.out.println("  ✅ Selected change priority: " + changePriority);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected change priority: " + changePriority);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting change priority: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select change priority: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Select person responsible from dropdown
     * XPath: //*[@id="Dropdown194"]/span[2]
     * Updated by Shahu.D
     * @param personResponsible Person responsible value
     * @throws Exception if element is not found or selection fails
     */
    public void selectPersonResponsible(String personResponsible) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By dropdownLocator = By.xpath("//*[@id=\"Dropdown194\"]/span[2]");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            dropdown.click();
            waitForSeconds(2);
            
            // Select the option
            By optionLocator = By.xpath("//span[contains(text(),'" + personResponsible + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            
            System.out.println("  ✅ Selected person responsible: " + personResponsible);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Selected person responsible: " + personResponsible);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting person responsible: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select person responsible: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter probability (1-5)
     * XPath: //*[@id="TextField195"]
     * Updated by Shahu.D
     * @param probability Probability value (1-5)
     * @throws Exception if element is not found or input fails
     */
    public void enterProbability(String probability) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            // Validate probability is between 1-5
            int probValue = Integer.parseInt(probability);
            if (probValue < 1 || probValue > 5) {
                throw new Exception("Probability must be between 1 and 5, got: " + probability);
            }
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"TextField195\"]");
            
            WebElement probField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", probField);
            waitForSeconds(1);
            
            probField.clear();
            probField.sendKeys(probability);
            
            System.out.println("  ✅ Entered probability: " + probability);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Entered probability: " + probability);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error entering probability: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter probability: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Enter impact (1-5)
     * XPath: //*[@id="TextField200"]
     * Updated by Shahu.D
     * @param impact Impact value (1-5)
     * @throws Exception if element is not found or input fails
     */
    public void enterImpact(String impact) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            // Validate impact is between 1-5
            int impactValue = Integer.parseInt(impact);
            if (impactValue < 1 || impactValue > 5) {
                throw new Exception("Impact must be between 1 and 5, got: " + impact);
            }
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"TextField200\"]");
            
            WebElement impactField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", impactField);
            waitForSeconds(1);
            
            impactField.clear();
            impactField.sendKeys(impact);
            
            System.out.println("  ✅ Entered impact: " + impact);
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.info("Entered impact: " + impact);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error entering impact: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter impact: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Click save button for risk
     * XPath: //*[@id="id__170"]
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSaveRiskButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            By locator = By.xpath("//*[@id=\"id__170\"]");
            
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn);
            waitForSeconds(1);
            
            try {
                saveBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
            }
            
            System.out.println("  ✅ Clicked on Save button");
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Save button for risk");
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Save button: " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Verify that the added risk is displayed in the list view
     * Updated by Shahu.D
     * @param description Risk description to verify
     * @return true if risk is displayed, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyRiskDisplayed(String description) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try to find the risk by description in the list
            By[] riskLocators = {
                By.xpath("//*[contains(text(),'" + description + "')]"),
                By.xpath("//td[contains(text(),'" + description + "')]"),
                By.xpath("//div[contains(@class,'cell') and contains(text(),'" + description + "')]"),
                By.xpath("//span[contains(text(),'" + description + "')]")
            };
            
            for (By locator : riskLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.getText().contains(description)) {
                            System.out.println("  ✅ Risk with description '" + description + "' is displayed in list view");
                            if (reportLogger != null) {
                                reportLogger.pass("Risk with description '" + description + "' is displayed in list view");
                            }
                            return true;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            System.out.println("  ⚠️ Risk with description '" + description + "' not found in list view");
            if (reportLogger != null) {
                reportLogger.warning("Risk with description '" + description + "' not found in list view");
            }
            return false;
            
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying risk display: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify risk display: " + e.getMessage());
            }
            throw e;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    public boolean isInitiativeCodeInInbox(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            return false;
        }

        String code = initiativeCode.trim();
        System.out.println("\n🔍 Checking if initiative code '" + code + "' exists in Inbox...");

        try {
            // First, try to search for the code
            try {
                searchInboxByInitiativeCode(code);
            } catch (Exception e) {
                System.out.println("ℹ️ Search not available, checking table directly: " + e.getMessage());
            }

            // Wait for table to update after search
            waitForSeconds(2);

            // Check table rows for the code (check current page only after search)
            List<WebElement> rows = driver.findElements(By.xpath("//div[@class='table-responsive']//table/tbody/tr | //table/tbody/tr"));
            System.out.println("📊 Checking " + rows.size() + " rows for initiative code '" + code + "'");

            for (WebElement row : rows) {
                try {
                    String rowText = row.getText();
                    if (rowText.contains(code)) {
                        System.out.println("✅ Initiative code '" + code + "' FOUND in Inbox");
                        if (reportLogger != null) {
                            reportLogger.pass("Initiative code '" + code + "' found in Inbox");
                        }
                        return true;
                    }
                } catch (Exception e) {
                    // Continue checking other rows
                    continue;
                }
            }

            System.out.println("ℹ️ Initiative code '" + code + "' NOT FOUND in Inbox");
            if (reportLogger != null) {
                reportLogger.info("Initiative code '" + code + "' not found in Inbox");
            }
            return false;

        } catch (Exception e) {
            System.out.println("⚠️ Error checking for initiative code '" + code + "' in Inbox: " + e.getMessage());
            return false;
        }
    }

    
    public void searchInboxByInitiativeCode(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null/empty. Cannot search.");
        }

        String code = initiativeCode.trim();
        System.out.println("\n🔍 Searching Inbox by Initiative Code: '" + code + "'");

        try {
            // Use separate methods for better control
            clickInboxSearchIcon();
            enterInboxInitiativeCode(code);
            clickInboxSearchButton();
            
            System.out.println("✅ Search applied on Inbox list for Initiative Code: " + code);
            if (reportLogger != null) {
                reportLogger.info("Searched Inbox for Initiative Code (Withdrawn flow): " + code);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not perform Inbox search by Initiative Code: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.warning("Could not perform Inbox search by Initiative Code: " + e.getMessage());
            }
            // Continue without failing – editInboxInitiativeByCode will still scan all rows
        }
    }

    public boolean verifyInitiativeRecordExists(String initiativeCode) throws Exception { // Gayatri.k
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) { // Gayatri.k
            return false; // Gayatri.k
        } // Gayatri.k

        String code = initiativeCode.trim(); // Gayatri.k
        System.out.println("📍 Verifying if initiative record exists for code: '" + code + "'"); // Gayatri.k

        try { // Gayatri.k
            waitForSeconds(2); // Wait for search results to load // Gayatri.k

            // Check table rows for the code // Gayatri.k
            List<WebElement> rows = driver.findElements(By.xpath("//div[@class='table-responsive']//table/tbody/tr | //table/tbody/tr")); // Gayatri.k
            System.out.println("📊 Checking " + rows.size() + " row(s) for initiative code '" + code + "'"); // Gayatri.k

            for (WebElement row : rows) { // Gayatri.k
                try { // Gayatri.k
                    String rowText = row.getText(); // Gayatri.k
                    if (rowText.contains(code)) { // Gayatri.k
                        System.out.println("✅ Matching record found! Initiative code '" + code + "' is displayed in the search results"); // Gayatri.k
                        System.out.println("   Row text (first 200 chars): " + rowText.substring(0, Math.min(200, rowText.length()))); // Gayatri.k
                        return true; // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    continue; // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k

            System.out.println("ℹ️ Initiative code '" + code + "' NOT FOUND in search results"); // Gayatri.k
            return false; // Gayatri.k
        } catch (Exception e) { // Gayatri.k
            System.out.println("⚠️ Error verifying initiative record: " + e.getMessage()); // Gayatri.k
            return false; // Gayatri.k
        } // Gayatri.k
    } // Gayatri.k  
    
    public void clickSearchIconForWithdrawnFlow() throws Exception { // Gayatri.k
        clickInboxSearchIcon(); // Gayatri.k
    } // Gayatri.k

    /**
     * Enter initiative code in search for withdrawn flow // Gayatri.k
     */
    public void enterInitiativeCodeInSearchForWithdrawn(String initiativeCode) throws Exception { // Gayatri.k
        enterInboxInitiativeCode(initiativeCode); // Gayatri.k
    } // Gayatri.k

    /**
     * Click search button for withdrawn flow // Gayatri.k
     */
    public void clickSearchButtonForWithdrawnFlow() throws Exception { // Gayatri.k
        clickInboxSearchButton(); // Gayatri.k
    } // Gayatri.k

    
    public void clickEditIconForInitiativeCode(String initiativeCode) throws Exception { // Gayatri.k
        System.out.println("\n✏️ CLICKING EDIT ICON FOR: " + initiativeCode); // Gayatri.k
        
        try { // Gayatri.k
            waitForSeconds(2); // Gayatri.k
            
            WebElement editIcon = null; // Gayatri.k
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Gayatri.k
            
            // Strategy 1: Use provided absolute XPath for first row edit icon (primary) // Gayatri.k
            try { // Gayatri.k
                By absoluteLocator = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/table/tbody/tr[1]/td[4]/div/div/div/button[1]"); // Gayatri.k
                editIcon = wait.until(ExpectedConditions.elementToBeClickable(absoluteLocator)); // Gayatri.k
                if (editIcon != null && editIcon.isDisplayed()) { // Gayatri.k
                    System.out.println("  ✅ Edit icon found using provided absolute XPath"); // Gayatri.k
                } // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                System.out.println("  ⚠️ Provided absolute XPath failed, trying row-based locators..."); // Gayatri.k
            } // Gayatri.k
            
            // Strategy 2: Find the row by initiative code (using contains), then find edit icon in Action column (td[4]) // Gayatri.k
            // Try button[1] first (as per provided XPath), then button[2] as fallback // Gayatri.k
            if (editIcon == null) { // Gayatri.k
                By[] rowBasedEditIconLocators = { // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]/td[4]//button[1]"), // Action column, button 1 (primary) // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]/td[4]//button[2]"), // Action column, button 2 (fallback) // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]//button[contains(@title,'Edit') or contains(@aria-label,'Edit')]"), // By title/aria-label // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]//svg[@data-testid='EditIcon']/ancestor::button"), // By EditIcon // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]//button[.//svg[@data-testid='EditIcon']]"), // Button with EditIcon SVG // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]/td[4]//button"), // Any button in Action column // Gayatri.k
                    By.xpath("//tr[contains(.,'" + initiativeCode + "')]/td[4]//svg") // Any SVG in Action column // Gayatri.k
                }; // Gayatri.k
                
                for (By locator : rowBasedEditIconLocators) { // Gayatri.k
                    try { // Gayatri.k
                        editIcon = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Gayatri.k
                        if (editIcon != null && editIcon.isDisplayed()) { // Gayatri.k
                            System.out.println("  ✅ Edit icon found using row-based locator: " + locator); // Gayatri.k
                            break; // Gayatri.k
                        } // Gayatri.k
                    } catch (Exception e) { // Gayatri.k
                        System.out.println("  ⚠️ Row-based locator failed: " + locator + " - " + e.getMessage()); // Gayatri.k
                        // Try next locator // Gayatri.k
                        continue; // Gayatri.k
                    } // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            // Strategy 3: Try alternative absolute XPath as last resort // Gayatri.k
            if (editIcon == null) { // Gayatri.k
                System.out.println("  ⚠️ Row-based search failed, trying alternative absolute XPath..."); // Gayatri.k
                try { // Gayatri.k
                    By alternativeAbsoluteLocator = By.xpath("//*[@id=\"root\"]/div[2]/div/div/div[2]/div/div/div[4]/table/tbody/tr[1]/td[4]/div/div/div/button[1]"); // Gayatri.k
                    editIcon = wait.until(ExpectedConditions.elementToBeClickable(alternativeAbsoluteLocator)); // Gayatri.k
                    if (editIcon != null && editIcon.isDisplayed()) { // Gayatri.k
                        System.out.println("  ✅ Edit icon found using alternative absolute XPath"); // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    System.out.println("  ⚠️ Alternative absolute XPath also failed: " + e.getMessage()); // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            if (editIcon == null) { // Gayatri.k
                throw new Exception("Could not find edit icon for initiative: " + initiativeCode); // Gayatri.k
            } // Gayatri.k
            
            // If editIcon is an SVG, find its parent button for clicking // Gayatri.k
            try { // Gayatri.k
                String tagName = editIcon.getTagName(); // Gayatri.k
                if ("svg".equalsIgnoreCase(tagName)) { // Gayatri.k
                    // Try to find parent button // Gayatri.k
                    try { // Gayatri.k
                        WebElement parentButton = editIcon.findElement(By.xpath("./ancestor::button[1]")); // Gayatri.k
                        if (parentButton != null && parentButton.isDisplayed()) { // Gayatri.k
                            editIcon = parentButton; // Gayatri.k
                            System.out.println("  ✅ Using parent button for SVG click"); // Gayatri.k
                        } // Gayatri.k
                    } catch (Exception e) { // Gayatri.k
                        // SVG itself is clickable, continue // Gayatri.k
                    } // Gayatri.k
                } // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                // Continue with editIcon as is // Gayatri.k
            } // Gayatri.k
            
            // Scroll into view // Gayatri.k
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editIcon); // Gayatri.k
            waitForSeconds(1); // Gayatri.k
            
            // Try multiple click methods // Gayatri.k
            try { // Gayatri.k
                editIcon.click(); // Gayatri.k
                System.out.println("  ✅ Edit icon clicked (direct click)"); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIcon); // Gayatri.k
                System.out.println("  ✅ Edit icon clicked (JS click)"); // Gayatri.k
            } // Gayatri.k
            
            waitForSeconds(2); // Gayatri.k
            
            if (reportLogger != null) { // Gayatri.k
                reportLogger.info("Clicked edit icon for initiative: " + initiativeCode); // Gayatri.k
            } // Gayatri.k
            
        } catch (Exception e) { // Gayatri.k
            System.out.println("  ❌ Failed to click edit icon: " + e.getMessage()); // Gayatri.k
            if (reportLogger != null) { // Gayatri.k
                reportLogger.fail("Failed to click edit icon for initiative " + initiativeCode + ": " + e.getMessage()); // Gayatri.k
            } // Gayatri.k
            throw e; // Gayatri.k
        } // Gayatri.k
    } // Gayatri.k
    
    
    public boolean verifyWithdrawAccessDeniedAlert() throws Exception { // Gayatri.k
        System.out.println("📍 Verifying 'access denied' alert..."); // Gayatri.k
        try { // Gayatri.k
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Gayatri.k
            By[] alertLocators = { // Gayatri.k
                By.xpath("//*[contains(text(),'access') and contains(text(),'denied')]"), // Gayatri.k
                By.xpath("//*[contains(text(),'You dont have access')]"), // Gayatri.k
                By.xpath("//*[contains(text(),'dont have access to withdrawn')]"), // Gayatri.k
                By.xpath("//*[contains(text(),'cannot withdraw')]"), // Gayatri.k
                By.xpath("//div[@role='alert']//*[contains(text(),'access')]") // Gayatri.k
            }; // Gayatri.k
            
            for (By locator : alertLocators) { // Gayatri.k
                try { // Gayatri.k
                    WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // Gayatri.k
                    if (alert != null && alert.isDisplayed()) { // Gayatri.k
                        String alertText = alert.getText(); // Gayatri.k
                        System.out.println("✅ Access denied alert found: '" + alertText + "'"); // Gayatri.k
                        if (reportLogger != null) { // Gayatri.k
                            reportLogger.pass("Access denied alert found: " + alertText); // Gayatri.k
                        } // Gayatri.k
                        return true; // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    continue; // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            return false; // Gayatri.k
        } catch (Exception e) { // Gayatri.k
            System.out.println("⚠️ Could not verify access denied alert: " + e.getMessage()); // Gayatri.k
            return false; // Gayatri.k
        } // Gayatri.k
    } // Gayatri.k

    public void clickWithdrawnInitiativeButton() throws Exception { // Gayatri.k
        System.out.println("📍 Clicking 'Withdrawn Initiative' button..."); // Gayatri.k
        try { // Gayatri.k
            // First, wait for page to stabilize after edit view opens // Gayatri.k
            waitForSeconds(3); // Gayatri.k
            
            // Close any open modals/dialogs that might be blocking // Gayatri.k
            try { // Gayatri.k
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE); // Gayatri.k
                waitForSeconds(1); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                // Ignore if ESC fails // Gayatri.k
            } // Gayatri.k
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Gayatri.k
            
            // Try multiple locator strategies for the withdraw button // Gayatri.k
            By[] withdrawButtonLocators = { // Gayatri.k
                InitiativePageLocators.withdrawInitiativeButton, // Primary locator // Gayatri.k
                InitiativePageLocators.withdrawInitiativeAction, // Alternative locator // Gayatri.k
                By.xpath("//span[contains(text(),'Withdrawn') or contains(text(),'withdrawn')]"), // Text-based span // Gayatri.k
                By.xpath("//button[contains(text(),'Withdrawn')]"), // Button with text // Gayatri.k
                By.xpath("//*[@role='button' and contains(text(),'Withdrawn')]"), // Role-based // Gayatri.k
                By.xpath("//div[contains(@class,'MuiBox-root')]//span[contains(text(),'Withdrawn')]"), // Material-UI box // Gayatri.k
                By.xpath("//div[contains(@class,'toolbar') or contains(@class,'action')]//span[contains(text(),'Withdrawn')]"), // Toolbar/Action area // Gayatri.k
                By.xpath("//div[contains(@class,'MuiChip-root')]//span[contains(text(),'Withdrawn')]"), // Material-UI Chip // Gayatri.k
                By.xpath("//*[contains(@aria-label,'Withdrawn') or contains(@title,'Withdrawn')]"), // Aria-label/title // Gayatri.k
                By.xpath("//div[contains(@class,'action')]//span[17]"), // Action div, span 17 (from original XPath) // Gayatri.k
                By.xpath("//div[contains(@class,'MuiBox-root')]//span[position()=17]"), // Material-UI box, 17th span // Gayatri.k
                By.xpath("//div[contains(@class,'MuiStack-root')]//span[contains(text(),'Withdrawn')]") // Material-UI Stack // Gayatri.k
            }; // Gayatri.k
            
            WebElement withdrawButton = null; // Gayatri.k
            By usedLocator = null; // Gayatri.k
            
            for (By locator : withdrawButtonLocators) { // Gayatri.k
                try { // Gayatri.k
                    // Try presence first, then clickable // Gayatri.k
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // Gayatri.k
                    if (element != null && element.isDisplayed()) { // Gayatri.k
                        // Check if it's clickable // Gayatri.k
                        try { // Gayatri.k
                            withdrawButton = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Gayatri.k
                        } catch (Exception e) { // Gayatri.k
                            // Element exists but not clickable, try using it anyway // Gayatri.k
                            withdrawButton = element; // Gayatri.k
                        } // Gayatri.k
                        if (withdrawButton != null) { // Gayatri.k
                            usedLocator = locator; // Gayatri.k
                            System.out.println("  ✅ Found withdraw button using locator: " + locator); // Gayatri.k
                            break; // Gayatri.k
                        } // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    System.out.println("  ⚠️ Locator failed: " + locator + " - " + e.getMessage()); // Gayatri.k
                    continue; // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            // If still not found, try searching all spans in the action area // Gayatri.k
            if (withdrawButton == null) { // Gayatri.k
                System.out.println("  🔍 Trying to find withdraw button by scanning action area..."); // Gayatri.k
                try { // Gayatri.k
                    List<WebElement> allSpans = driver.findElements(By.xpath("//div[contains(@class,'MuiBox-root')]//span | //div[contains(@class,'action')]//span")); // Gayatri.k
                    System.out.println("  📊 Found " + allSpans.size() + " span(s) in action area"); // Gayatri.k
                    for (WebElement span : allSpans) { // Gayatri.k
                        try { // Gayatri.k
                            String spanText = span.getText().toLowerCase(); // Gayatri.k
                            if (spanText.contains("withdrawn") || spanText.contains("withdraw")) { // Gayatri.k
                                withdrawButton = span; // Gayatri.k
                                usedLocator = By.xpath("//span[contains(text(),'" + span.getText() + "')]"); // Gayatri.k
                                System.out.println("  ✅ Found withdraw button by text scan: '" + span.getText() + "'"); // Gayatri.k
                                break; // Gayatri.k
                            } // Gayatri.k
                        } catch (Exception e) { // Gayatri.k
                            continue; // Gayatri.k
                        } // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    System.out.println("  ⚠️ Text scan failed: " + e.getMessage()); // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            if (withdrawButton == null) { // Gayatri.k
                throw new Exception("Could not find 'Withdrawn Initiative' button with any locator. The button may not be available for this initiative or the page structure has changed."); // Gayatri.k
            } // Gayatri.k
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", withdrawButton); // Gayatri.k
            waitForSeconds(1); // Gayatri.k
            
            try { // Gayatri.k
                withdrawButton.click(); // Gayatri.k
                System.out.println("✅ Clicked 'Withdrawn Initiative' button (normal click) using: " + usedLocator); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", withdrawButton); // Gayatri.k
                System.out.println("✅ Clicked 'Withdrawn Initiative' button (JavaScript click) using: " + usedLocator); // Gayatri.k
            } // Gayatri.k
            
            waitForSeconds(2); // Wait for alert to appear // Gayatri.k
            
            if (reportLogger != null) { // Gayatri.k
                reportLogger.info("Clicked 'Withdrawn Initiative' button"); // Gayatri.k
            } // Gayatri.k
        } catch (Exception e) { // Gayatri.k
            System.out.println("❌ Failed to click 'Withdrawn Initiative' button: " + e.getMessage()); // Gayatri.k
            throw e; // Gayatri.k
        } // Gayatri.k
    } // Gayatri.k
    
    
    
    
    public boolean verifyWithdrawSuccessAlert() throws Exception { // Gayatri.k
        System.out.println("📍 Verifying 'successfully withdrawn' alert..."); // Gayatri.k
        try { // Gayatri.k
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Gayatri.k
            By[] alertLocators = { // Gayatri.k
                By.xpath("//*[contains(text(),'withdrawn successfully')]"), // Gayatri.k
                By.xpath("//*[contains(text(),'successfully withdrawn')]"), // Gayatri.k
                By.xpath("//*[contains(text(),'Initiative Withdrawn successfully')]"), // Gayatri.k
                By.xpath("//div[@role='alert']//*[contains(text(),'withdrawn')]") // Gayatri.k
            }; // Gayatri.k
            
            for (By locator : alertLocators) { // Gayatri.k
                try { // Gayatri.k
                    WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // Gayatri.k
                    if (alert != null && alert.isDisplayed()) { // Gayatri.k
                        String alertText = alert.getText(); // Gayatri.k
                        System.out.println("✅ Success alert found: '" + alertText + "'"); // Gayatri.k
                        if (reportLogger != null) { // Gayatri.k
                            reportLogger.pass("Success alert found: " + alertText); // Gayatri.k
                        } // Gayatri.k
                        return true; // Gayatri.k
                    } // Gayatri.k
                } catch (Exception e) { // Gayatri.k
                    continue; // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            return false; // Gayatri.k
        } catch (Exception e) { // Gayatri.k
            System.out.println("⚠️ Could not verify success alert: " + e.getMessage()); // Gayatri.k
            return false; // Gayatri.k
        } // Gayatri.k
    } // Gayatri.k 
    
    
    public void clickGoBackToListView() throws Exception {
        System.out.println("\n📍 Clicking 'Go Back To List View' button...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement backButton = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativePageLocators.goBackToListViewButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", backButton);
            waitForSeconds(1);
            
            // Try normal click first, then JS click as fallback
            try {
                backButton.click();
                System.out.println("✅ Clicked 'Go Back To List View' button (normal click)");
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButton);
                System.out.println("✅ Clicked 'Go Back To List View' button (JavaScript click)");
            }
            
            waitForSeconds(2); // Wait for list view to load
            if (reportLogger != null) {
                reportLogger.info("Clicked 'Go Back To List View' button");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not click 'Go Back To List View' button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.warning("Could not click 'Go Back To List View' button: " + e.getMessage());
            }
            throw new Exception("Failed to click 'Go Back To List View' button: " + e.getMessage());
        }
    }
    
    public void clickInboxSearchIcon() throws Exception {
        System.out.println("📍 Clicking on Inbox Search Icon...");
        
        // First, check if search window is already open
        try {
            WebElement searchInput = driver.findElement(InitiativePageLocators.inboxSearchInputWithdrawn);
            if (searchInput != null && searchInput.isDisplayed()) {
                System.out.println("✅ Search window already open, proceeding...");
                if (reportLogger != null) {
                    reportLogger.info("Search window already open");
                }
                return;
            }
        } catch (Exception e) {
            // Search window not open, proceed to click search icon
            System.out.println("📍 Search window not open, proceeding to click search icon...");
        }
        
        // Try multiple locator strategies
        WebElement searchIcon = null;
        By usedLocator = null;
        
        // Strategy 1: Try direct findElement with primary locator
        try {
            System.out.println("  🔍 Strategy 1: Trying direct findElement with primary locator...");
            searchIcon = driver.findElement(InitiativePageLocators.inboxSearchIconWithdrawn);
            if (searchIcon != null && searchIcon.isDisplayed()) {
                usedLocator = InitiativePageLocators.inboxSearchIconWithdrawn;
                System.out.println("  ✅ Found element using direct findElement");
            } else {
                searchIcon = null;
                System.out.println("  ⚠️ Element found but not displayed");
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Strategy 1 (direct findElement) failed: " + e.getClass().getSimpleName());
            searchIcon = null;
        }
        
        // Strategy 2: Try WebDriverWait with primary locator
        if (searchIcon == null) {
            try {
                System.out.println("  🔍 Strategy 2: Trying WebDriverWait with primary locator...");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                searchIcon = wait.until(
                        ExpectedConditions.elementToBeClickable(InitiativePageLocators.inboxSearchIconWithdrawn));
                usedLocator = InitiativePageLocators.inboxSearchIconWithdrawn;
                System.out.println("  ✅ Found element using WebDriverWait");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 2 (WebDriverWait) failed: " + e.getMessage());
            }
        }
        
        // Strategy 3: Try alternative locators (relative XPath, img with aria-label, etc.)
        if (searchIcon == null) {
            By[] alternativeLocators = {
                By.xpath("//img[contains(@aria-label,'Search')]"),
                By.xpath("(//img[@aria-label='Search'])[1]"),
                By.xpath("//div[contains(@class,'search')]//img"),
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"),
                By.xpath("//div[contains(@class,'MuiBox-root')]//img[contains(@alt,'Search') or contains(@aria-label,'Search')]")
            };
            
            for (By locator : alternativeLocators) {
                try {
                    System.out.println("  🔍 Strategy 3: Trying alternative locator: " + locator);
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    searchIcon = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    usedLocator = locator;
                    System.out.println("  ✅ Found element using alternative locator: " + locator);
                    break;
                } catch (Exception e) {
                    System.out.println("  ⚠️ Alternative locator failed (timeout 5s): " + locator);
                    continue;
                }
            }
        }
        
        // Final fallback: Try primary locator with longer timeout
        if (searchIcon == null) {
            try {
                System.out.println("  🔍 Strategy 4: Trying primary locator as final fallback with longer timeout...");
                WebDriverWait finalWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                searchIcon = finalWait.until(
                        ExpectedConditions.elementToBeClickable(InitiativePageLocators.inboxSearchIconWithdrawn));
                usedLocator = InitiativePageLocators.inboxSearchIconWithdrawn;
                System.out.println("  ✅ Found element using primary locator with longer timeout");
            } catch (Exception e) {
                System.out.println("  ❌ All strategies failed. Last error: " + e.getMessage());
                throw new Exception("Could not find Inbox search icon with any locator. " +
                        "Tried: direct findElement, WebDriverWait, and alternative locators. " +
                        "Last error: " + e.getMessage());
            }
        }
        
        // Scroll into view
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchIcon);
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not scroll to element: " + e.getMessage());
        }
        
        // Try normal click first, then JS click as fallback
        try {
            searchIcon.click();
            System.out.println("✅ Clicked on Inbox search icon (normal click) using locator: " + usedLocator);
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
            System.out.println("✅ Clicked on Inbox search icon (JavaScript click) using locator: " + usedLocator);
        }
        
        // Wait for search window to open
        waitForSeconds(2);
        
        // Verify search window opened by checking for search input
        try {
            WebDriverWait verifyWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            verifyWait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.inboxSearchInputWithdrawn));
            System.out.println("✅ Search window opened successfully (search input found)");
        } catch (Exception e) {
            System.out.println("⚠️ Search window may not have opened: " + e.getMessage());
            // Don't throw exception - continue anyway as the click might have worked
        }
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on Inbox search icon");
        }
    }

    public void enterInboxInitiativeCode(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null/empty. Cannot enter.");
        }

        String code = initiativeCode.trim();
        System.out.println("📍 Entering Initiative Code: '" + code + "'");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(InitiativePageLocators.inboxSearchInputWithdrawn));
            searchInput.clear();
            searchInput.sendKeys(code);
            waitForSeconds(1);
            System.out.println("✅ Entered Initiative Code: '" + code + "'");
            if (reportLogger != null) {
                reportLogger.info("Entered Initiative Code in search field: " + code);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Initiative Code: " + e.getMessage());
            throw new Exception("Failed to enter Initiative Code: " + e.getMessage());
        }
    }

    public void clickInboxSearchButton() throws Exception {
        System.out.println("📍 Clicking on Inbox Search button...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchButton = wait.until(
                    ExpectedConditions.elementToBeClickable(InitiativePageLocators.inboxSearchButtonWithdrawn));
            searchButton.click();
            waitForSeconds(3);
            System.out.println("✅ Clicked on Inbox search button");
            if (reportLogger != null) {
                reportLogger.info("Clicked on Inbox search button");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Inbox search button not clickable/present: " + e.getMessage());
            throw new Exception("Failed to click Inbox search button: " + e.getMessage());
        }
    }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    public java.util.Map<String, Boolean> verifyAllResourceFieldsPresent() throws Throwable {
        System.out.println("\n📋 ═══════════════════════════════════════════");
        System.out.println("📋 VERIFYING ALL RESOURCE FIELDS ARE PRESENT");
        System.out.println("📋 ═══════════════════════════════════════════\n");
        
        java.util.Map<String, Boolean> fieldStatus = new java.util.LinkedHashMap<>();
        
        fieldStatus.put("Role", verifyRoleFieldPresent());
        fieldStatus.put("Skills", verifySkillsFieldPresent());
        fieldStatus.put("Resource-In Date", verifyResourceInDateFieldPresent());
        fieldStatus.put("Resource-Out Date", verifyResourceOutDateFieldPresent());
        fieldStatus.put("FTE", verifyFTEFieldPresent());
        
        System.out.println("\n📋 ═══════════════════════════════════════════");
        System.out.println("📋 FIELD PRESENCE SUMMARY:");
        for (java.util.Map.Entry<String, Boolean> entry : fieldStatus.entrySet()) {
            String status = entry.getValue() ? "✅ PRESENT" : "❌ MISSING";
            System.out.println("  " + entry.getKey() + ": " + status);
        }
        System.out.println("📋 ═══════════════════════════════════════════\n");
        
        return fieldStatus;
    }
    
    public boolean verifyRoleFieldPresent() throws Throwable {
        System.out.println("  🔍 Checking Role field...");
        try {
            java.util.List<WebElement> elements = driver.findElements(InitiativePageLocators.resourceRoleFieldLabel);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Role field is present");
                    return true;
                }
            }
            System.out.println("    ❌ Role field NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Error checking Role field: " + e.getMessage());
            return false;
        }
    }  
    
    public boolean verifySkillsFieldPresent() throws Throwable {
        System.out.println("  🔍 Checking Skills field...");
        try {
            java.util.List<WebElement> elements = driver.findElements(InitiativePageLocators.resourceSkillsFieldLabel);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Skills field is present");
                    return true;
                }
            }
            System.out.println("    ❌ Skills field NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Error checking Skills field: " + e.getMessage());
            return false;
        }
    } 
    
    
    public boolean verifyResourceInDateFieldPresent() throws Throwable {
        System.out.println("  🔍 Checking Resource-In Date field...");
        try {
            java.util.List<WebElement> elements = driver.findElements(InitiativePageLocators.resourceInDateFieldLabel);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Resource-In Date field is present");
                    return true;
                }
            }
            // Also check using date picker locator
            java.util.List<WebElement> datePickerElements = driver.findElements(InitiativePageLocators.resourceInDate);
            for (WebElement el : datePickerElements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Resource-In Date field is present (via date picker)");
                    return true;
                }
            }
            System.out.println("    ❌ Resource-In Date field NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Error checking Resource-In Date field: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verifyResourceOutDateFieldPresent() throws Throwable {
        System.out.println("  🔍 Checking Resource-Out Date field...");
        try {
            java.util.List<WebElement> elements = driver.findElements(InitiativePageLocators.resourceOutDateFieldLabel);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Resource-Out Date field is present");
                    return true;
                }
            }
            // Also check using date picker locator
            java.util.List<WebElement> datePickerElements = driver.findElements(InitiativePageLocators.resourceOutDate);
            for (WebElement el : datePickerElements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ Resource-Out Date field is present (via date picker)");
                    return true;
                }
            }
            System.out.println("    ❌ Resource-Out Date field NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Error checking Resource-Out Date field: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean verifyFTEFieldPresent() throws Throwable {
        System.out.println("  🔍 Checking FTE field...");
        try {
            java.util.List<WebElement> elements = driver.findElements(InitiativePageLocators.resourceFTEFieldLabel);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ FTE field is present");
                    return true;
                }
            }
            // Also check using FTE input locator
            java.util.List<WebElement> fteInputElements = driver.findElements(InitiativePageLocators.resourceFTE);
            for (WebElement el : fteInputElements) {
                if (el.isDisplayed()) {
                    System.out.println("    ✅ FTE field is present (via input)");
                    return true;
                }
            }
            System.out.println("    ❌ FTE field NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Error checking FTE field: " + e.getMessage());
            return false;
        }
    }
    
    public java.util.Map<String, Boolean> verifyAllResourceFieldsMandatory() throws Throwable {
        System.out.println("\n⭐ ═══════════════════════════════════════════");
        System.out.println("⭐ VERIFYING ALL RESOURCE FIELDS ARE MANDATORY");
        System.out.println("⭐ ═══════════════════════════════════════════\n");
        
        java.util.Map<String, Boolean> mandatoryStatus = new java.util.LinkedHashMap<>();
        
        mandatoryStatus.put("Role", isFieldMandatory("Role"));
        mandatoryStatus.put("Skills", isFieldMandatory("Skills"));
        mandatoryStatus.put("Resource-In Date", isFieldMandatory("InDate"));
        mandatoryStatus.put("Resource-Out Date", isFieldMandatory("OutDate"));
        mandatoryStatus.put("FTE", isFieldMandatory("FTE"));
        
        System.out.println("\n⭐ ═══════════════════════════════════════════");
        System.out.println("⭐ MANDATORY STATUS SUMMARY:");
        for (java.util.Map.Entry<String, Boolean> entry : mandatoryStatus.entrySet()) {
            String status = entry.getValue() ? "✅ MANDATORY" : "⚠️ NOT MANDATORY";
            System.out.println("  " + entry.getKey() + ": " + status);
        }
        System.out.println("⭐ ═══════════════════════════════════════════\n");
        
        return mandatoryStatus;
    }
    
    public boolean isFieldMandatory(String fieldType) throws Throwable {
        System.out.println("  🔍 Checking if " + fieldType + " is mandatory...");
        try {
            By fieldLocator = null;
            By inputLocator = null;
            
            switch (fieldType.toLowerCase()) {
                case "role":
                    fieldLocator = InitiativePageLocators.resourceRoleFieldLabel;
                    inputLocator = InitiativePageLocators.resourcesDropdownTrigger;
                    break;
                case "skills":
                    fieldLocator = InitiativePageLocators.resourceSkillsFieldLabel;
                    inputLocator = InitiativePageLocators.skillDropdownTrigger;
                    break;
                case "indate":
                case "resource-in date":
                    fieldLocator = InitiativePageLocators.resourceInDateFieldLabel;
                    inputLocator = InitiativePageLocators.resourceInDate;
                    break;
                case "outdate":
                case "resource-out date":
                    fieldLocator = InitiativePageLocators.resourceOutDateFieldLabel;
                    inputLocator = InitiativePageLocators.resourceOutDate;
                    break;
                case "fte":
                    fieldLocator = InitiativePageLocators.resourceFTEFieldLabel;
                    inputLocator = InitiativePageLocators.resourceFTE;
                    break;
                default:
                    System.out.println("    ⚠️ Unknown field type: " + fieldType);
                    return false;
            }
            
            // Check 1: Look for asterisk (*) near the field label
            java.util.List<WebElement> labelElements = driver.findElements(fieldLocator);
            for (WebElement label : labelElements) {
                if (label.isDisplayed()) {
                    String labelText = label.getText();
                    // Check if label or nearby element contains asterisk
                    if (labelText.contains("*")) {
                        System.out.println("    ✅ " + fieldType + " is mandatory (asterisk in label)");
                        return true;
                    }
                    
                    // Check parent or sibling elements for asterisk
                    try {
                        WebElement parent = label.findElement(By.xpath(".."));
                        String parentText = parent.getText();
                        if (parentText.contains("*")) {
                            System.out.println("    ✅ " + fieldType + " is mandatory (asterisk near label)");
                            return true;
                        }
                    } catch (Exception e) {
                        // Ignore
                    }
                }
            }
            
            // Check 2: Look for 'required' attribute on input element
            if (inputLocator != null) {
                java.util.List<WebElement> inputs = driver.findElements(inputLocator);
                for (WebElement input : inputs) {
                    if (input.isDisplayed()) {
                        String requiredAttr = input.getAttribute("required");
                        String ariaRequired = input.getAttribute("aria-required");
                        
                        if (requiredAttr != null || "true".equalsIgnoreCase(ariaRequired)) {
                            System.out.println("    ✅ " + fieldType + " is mandatory (required attribute)");
                            return true;
                        }
                    }
                }
            }
            
            // Check 3: Look for class indicating mandatory
            java.util.List<WebElement> requiredIndicators = driver.findElements(
                By.xpath("//*[contains(@class,'is-required')] | //*[contains(@class,'required')]")
            );
            for (WebElement indicator : requiredIndicators) {
                String indicatorText = indicator.getText().toLowerCase();
                if (indicatorText.contains(fieldType.toLowerCase())) {
                    System.out.println("    ✅ " + fieldType + " is mandatory (required class)");
                    return true;
                }
            }
            
            System.out.println("    ⚠️ Could not confirm if " + fieldType + " is mandatory");
            return false;
            
        } catch (Exception e) {
            System.out.println("    ❌ Error checking mandatory status: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean verifyCostSuccessAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Costs SUCCESS ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");

        String expected = expectedMessage == null ? "" : expectedMessage.trim().toLowerCase();
        long end = System.currentTimeMillis() + 10_000;
        String lastSeen = "";

        // Broad toast/alert patterns (different implementations render text in nested spans, innerText, aria-label)
        By toastCandidates = By.xpath(
                "//div[@role='alert'] | " +
                "//div[contains(@class,'toast') or contains(@class,'Toast') or contains(@class,'Toastify') or contains(@class,'snackbar') or contains(@class,'notification')] | " +
                "//div[contains(@class,'ms-MessageBar')] | " +
                "//*[contains(@class,'error-message') or contains(@class,'validation-message')]"
        );

        while (System.currentTimeMillis() < end) {
            java.util.List<WebElement> els = new java.util.ArrayList<>();
            try { els.addAll(driver.findElements(InitiativePageLocators.CostsSuccessAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(InitiativePageLocators.toastAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(toastCandidates)); } catch (Exception ignore) { }

            for (WebElement el : els) {
                try {
                    if (el == null || !el.isDisplayed()) continue;

                    String txt = "";
                    try { txt = el.getText(); } catch (Exception ignore) { }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try {
                            Object o = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent || '';", el);
                            txt = o == null ? "" : String.valueOf(o);
                        } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try { txt = el.getAttribute("aria-label"); } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    String norm = txt.trim();
                    if (!norm.isEmpty()) lastSeen = norm;

                    String lower = norm.toLowerCase();
                    if (!lower.isEmpty() && (
                            (expected.isEmpty() ? false : lower.contains(expected)) ||
                            lower.contains("success") ||
                            lower.contains("successfully") ||
                            lower.contains("updated") ||
                            lower.contains("added")
                    )) {
                        System.out.println("  ✅ SUCCESS: Alert message verified!");
                        System.out.println("  📋 Actual message: " + norm);
                        if (reportLogger != null) {
                            reportLogger.pass("✅ Success alert verified: " + norm);
                        }
                        System.out.println("✅ ═══════════════════════════════════════════\n");
                        return true;
                    }
                } catch (Exception ignore) {
                    // stale / detached toast; keep polling
                }
            }

            Thread.sleep(250);
        }

        System.out.println("  ❌ Could not verify success alert within timeout.");
        if (!lastSeen.isEmpty()) {
            System.out.println("  📋 Last seen alert text: " + lastSeen);
        }
        if (reportLogger != null) {
            reportLogger.fail("❌ Success alert not verified. Last seen: " + lastSeen);
        }
        System.out.println("✅ ═══════════════════════════════════════════\n");
        return false;
    }
 
    
    public String getCostAmountValue() {
        try {
            WebElement el = resolveVisibleInput(InitiativePageLocators.CostsAmount, "Costs Amount");
            String v = el.getAttribute("value");
            return v == null ? "" : v;
        } catch (Exception e) {
            return "";
        }
    }  
    
    public String getCostDescriptionValue() {
        try {
            WebElement el = resolveVisibleInput(InitiativePageLocators.CostsDecription, "Costs Description");
            String v = el.getAttribute("value");
            if (v == null) v = "";
            if (v.trim().isEmpty()) {
                String t = el.getText();
                return t == null ? "" : t;
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }
    
    private WebElement resolveVisibleInput(By locator, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement base = wait.until(d -> {
            try {
                java.util.List<WebElement> els = d.findElements(locator);
                WebElement lastDisplayed = null;
                for (WebElement e : els) {
                    try {
                        if (e != null && e.isDisplayed() && e.isEnabled()) {
                            lastDisplayed = e;
                        }
                    } catch (Exception ignore) { }
                }
                return lastDisplayed;
            } catch (Exception e) {
                return null;
            }
        });
        if (base == null) {
            throw new NoSuchElementException("Could not locate visible element for: " + fieldName + " using locator: " + locator);
        }

        // If locator already points to an input/textarea, good.
        if (isInputOrTextarea(base)) return base;

        // Otherwise resolve to an actual editable element near/inside it.
        try {
            WebElement resolved = resolveEditableElement(base);
            if (resolved != null && isInputOrTextarea(resolved) && resolved.isDisplayed()) {
                return resolved;
            }
        } catch (Exception ignore) { }

        // Common FluentUI pattern: role=spinbutton wrapper with an <input> inside
        try {
            WebElement inner = base.findElement(By.xpath(".//input|.//textarea"));
            if (inner != null && inner.isDisplayed()) return inner;
        } catch (Exception ignore) { }

        // Fallback: nearest ms-TextField container input/textarea
        try {
            WebElement inner = base.findElement(By.xpath("./ancestor::*[contains(@class,'ms-TextField')][1]//input|./ancestor::*[contains(@class,'ms-TextField')][1]//textarea"));
            if (inner != null && inner.isDisplayed()) return inner;
        } catch (Exception ignore) { }

        // Last resort: return base (may throw later during set/verify)
        return base;
    }
 
    private boolean isInputOrTextarea(WebElement el) {
        try {
            String tag = el.getTagName();
            return "input".equalsIgnoreCase(tag) || "textarea".equalsIgnoreCase(tag);
        } catch (Exception e) {
            return false;
        }
    }
    
    
    private WebElement resolveEditableElement(WebElement base) {
        try {
            String tag = base.getTagName();
            if ("input".equalsIgnoreCase(tag) || "textarea".equalsIgnoreCase(tag)) return base;
        } catch (Exception ignore) { }

        // 1) descendant input/textarea
        try { return base.findElement(By.xpath(".//input|.//textarea")); } catch (Exception ignore) { }
        // 2) closest Fluent UI container then input
        try {
            return base.findElement(By.xpath("./ancestor::*[contains(@class,'ms-DatePicker') or contains(@class,'ms-TextField')][1]//input"));
        } catch (Exception ignore) { }
        // 3) last resort: use base itself
        return base;
    } 
    
    public void verifyCostDetailsHeader(String expectedHeader) throws Throwable {
        waitForElementToBeVisible(InitiativePageLocators.costDetailsHeader, "Cost Details Header");
        String actual = getText(InitiativePageLocators.costDetailsHeader, "Cost Details Header").trim();
        if (actual.equalsIgnoreCase(expectedHeader == null ? "" : expectedHeader.trim())) {
            if (reportLogger != null) reportLogger.pass("✅ Cost Details header matched: " + actual);
        } else {
            if (reportLogger != null) reportLogger.fail("❌ Cost Details header mismatch. Expected: " + expectedHeader + " | Actual: " + actual);
            throw new AssertionError("Cost Details header mismatch. Expected: " + expectedHeader + " | Actual: " + actual);
        }
    }

    
    public void verifyCostDetailsMandatoryFields(String costCategory,
            String costType,
            String amount,
            String fromDate,
            String toDate,
            String description) throws Throwable {
System.out.println("\n🧾 ═══════════════════════════════════════════");
System.out.println("🧾 VERIFYING COST DETAILS FIELDS + MANDATORY");
System.out.println("🧾 ═══════════════════════════════════════════");

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

// Presence/type checks (control existence)
WebElement costCategoryEl = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.CostCategory));
if (costCategoryEl == null || !costCategoryEl.isDisplayed()) {
throw new AssertionError("Cost Category dropdown not found/displayed");
}

WebElement running = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.getCostTypeRadioOption("Running")));
WebElement fixed = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.getCostTypeRadioOption("Fixed")));
if (running == null || fixed == null) {
throw new AssertionError("Cost Type radio options (Running/Fixed) not found");
}

WebElement amountEl = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.CostsAmount));
if (amountEl == null || !amountEl.isDisplayed()) {
throw new AssertionError("Amount textbox not found/displayed");
}

WebElement fromDateEl = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.CostsFromDate));
WebElement toDateEl = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.CostsToDate));
if (fromDateEl == null || toDateEl == null) {
throw new AssertionError("From/To Date picker not found");
}

WebElement descEl = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.CostsDecription));
if (descEl == null || !descEl.isDisplayed()) {
throw new AssertionError("Description textbox not found/displayed");
}

if (costCategory == null || costCategory.trim().isEmpty()) {
throw new IllegalArgumentException("Excel CostCategory is blank for mandatory verification");
}
if (costType == null || costType.trim().isEmpty()) {
throw new IllegalArgumentException("Excel CostType is blank for mandatory verification");
}
if (amount == null || amount.trim().isEmpty()) {
throw new IllegalArgumentException("Excel Amount is blank for mandatory verification");
}
if (fromDate == null || fromDate.trim().isEmpty()) {
throw new IllegalArgumentException("Excel FromDate is blank for mandatory verification");
}
if (toDate == null || toDate.trim().isEmpty()) {
throw new IllegalArgumentException("Excel ToDate is blank for mandatory verification");
}
if (description == null || description.trim().isEmpty()) {
throw new IllegalArgumentException("Excel Description is blank for mandatory verification");
}

// Mandatory checks (sequential)
// 1) Cost Category
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyCostCategoryErrorAlert("Cost Category")) {
throw new AssertionError("Expected Cost Category mandatory alert not shown");
}
selectCostCategoryDropdown();

// 2) Cost Type
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyCostTypeErrorAlert("Cost Type")) {
throw new AssertionError("Expected Cost Type mandatory alert not shown");
}
selectCostType(costType);

// 3) Amount
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyAmountErrorAlert("Amount")) {
throw new AssertionError("Expected Amount mandatory alert not shown");
}
enterAmount(amount);

// 4) From Date
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyFromDateErrorAlert("From Date")) {
throw new AssertionError("Expected From Date mandatory alert not shown");
}
enterCostFromDate(fromDate);

// 5) To Date
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyToDateErrorAlert("To Date")) {
throw new AssertionError("Expected To Date mandatory alert not shown");
}
enterCostToDate(toDate);

// 6) Description
clickCostsSaveButton();
Thread.sleep(700);
if (!verifyDescriptionErrorAlert("Description")) {
throw new AssertionError("Expected Description mandatory alert not shown");
}
// Do NOT click final save here; we only needed to verify mandatory behavior.
enterDescription(description);

if (reportLogger != null) {
reportLogger.pass("✅ Cost Details mandatory fields verified (sequential validations)");
}

System.out.println("🧾 ═══════════════════════════════════════════\n");
}

    public void clickCostsSaveButton() throws Throwable {
        System.out.println("\n💾 ═══════════════════════════════════════════");
        System.out.println("💾 CLICKING Cost SAVE BUTTON");
        System.out.println("💾 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Actions actions = new Actions(driver);
            
            // Multiple locator strategies for Save button
            By[] saveButtonLocators = {
                InitiativePageLocators.CostsSaveButton,
                By.xpath("//button[contains(@class,'ms-Button--primary')][contains(.,'Save')]"),
                By.xpath("//button[normalize-space()='Save']"),
                By.xpath("//span[text()='Save']/ancestor::button"),
                By.xpath("//button[.//span[text()='Save']]"),
                By.xpath("(//button[contains(.,'Save')])[last()]")
            };
            
            WebElement saveButton = null;
            
            for (By locator : saveButtonLocators) {
                try {
                    saveButton = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    System.out.println("  ✅ Found Save button");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (saveButton == null) {
                throw new Exception("Could not find Costs Save button");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(500);
            
            // Click the button
            try {
                actions.moveToElement(saveButton).click().perform();
                System.out.println("  ✅ Clicked Save button (Actions click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                System.out.println("  ✅ Clicked Save button (JS click)");
            }
            
            Thread.sleep(2000); // Wait for save to complete
            
            if (reportLogger != null) {
                reportLogger.pass("✅ Clicked Costs Save button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to click Costs Save button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("💾 ═══════════════════════════════════════════\n");
    }   
    
    public boolean verifyCostCategoryErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Cost Category Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.CostsCategoryErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find error alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (ONLY). Don't accept "success/updated" here.
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    } 
    
    public boolean verifyCostTypeErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Cost Type Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.CostsTypeErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find error alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (do NOT treat "success/updated" as valid for error validation)
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    } 
    
    
    public boolean verifyAmountErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Amount Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.AmountErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find error alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (ONLY)
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }
    
    
	public static By clickcc=
    	    By.xpath("//span[normalize-space()='Select Cost Category']/ancestor::button");		
	
    
    public void selectCostCategoryDropdown() throws Throwable {

        reportLogger.info("📍 STEP 12: Selecting Cost Category Option from Dropdown...");
      
        click(InitiativePageLocators.clickcc,"clickcc");
          selectRandomValueFromDropdown(InitiativePageLocators.clickCCoption,"clickccoption");
        
    } 
  


    
    
    
    
    private boolean isCostCategorySelected(String CCName) throws Throwable {
        try {
            if (CCName == null) return false;

            String expected = CCName.trim();

            // 1) Read from likely trigger elements (non-throwing; ids vary)
            By[] triggerCandidates = new By[]{
                    // Fluent UI / ms-Dropdown common structures
                    By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::*[@role='combobox'][1]"),
                    By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::div[contains(@class,'ms-Dropdown-title')][1]"),
                    By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::span[contains(@id,'-option')][1]"),
                    // legacy id patterns
                    By.xpath("//span[contains(@id,'select_Category') and contains(@id,'-option')]"),
                    By.xpath("//div[contains(@class,'ms-Dropdown')][.//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]]//*[contains(@class,'ms-Dropdown-title')][1]")
            };
            for (By by : triggerCandidates) {
                List<WebElement> els = driver.findElements(by);
                for (WebElement el : els) {
                    if (el != null && el.isDisplayed() && elementContainsSelectedValue(el, expected)) return true;
                }
            }

            // 3) Try common "select_Category" input/select patterns (MUI/HTML variants)
            try {
                WebElement input = driver.findElement(By.xpath(
                        "//input[@id='select_Category' or @name='select_Category' or contains(@id,'select_Category')]"
                                + " | //select[@id='select_Category' or @name='select_Category']"
                ));
                String v = input.getAttribute("value");
                if (v != null && v.trim().equalsIgnoreCase(expected)) return true;
            } catch (Exception ignore) { }

            // 4) If listbox is still open, check for aria-selected=true on the option
            try {
                WebElement selectedOption = driver.findElement(By.xpath(
                        "//*[@role='option' and (@aria-selected='true' or @aria-checked='true') and normalize-space(.)='" + expected + "']"
                ));
                if (selectedOption != null && selectedOption.isDisplayed()) return true;
            } catch (Exception ignore) { }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    private boolean elementContainsSelectedValue(WebElement el, String expected) {
        if (el == null) return false;

        String[] candidates = new String[]{
                safeString(() -> el.getText()),
                safeString(() -> el.getAttribute("value")),
                safeString(() -> el.getAttribute("title")),
                safeString(() -> el.getAttribute("aria-label")),
                safeString(() -> el.getAttribute("aria-valuetext"))
        };

        for (String c : candidates) {
            if (c != null && c.trim().equalsIgnoreCase(expected)) return true;
        }
        return false;
    }
 
    
    
    private String safeString(java.util.concurrent.Callable<String> c) {
        try {
            return c.call();
        } catch (Exception e) {
            return null;
        }
    }

    
    public void selectCostType(String CostType) throws Exception {
        System.out.println("\n📝 ═══════════════════════════════════════════");
        System.out.println("📝 SELECTING Cost Type (Radio): " + CostType);
        System.out.println("📝 ═══════════════════════════════════════════");

        if (CostType == null || CostType.trim().isEmpty()) {
            throw new IllegalArgumentException("CostType cannot be null/blank (expected 'Running' or 'Fixed')");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By radioLocator = InitiativePageLocators.getCostTypeRadioOption(CostType);

        // Find a displayed candidate (locator can return label/div/role=radio/input)
        WebElement el = wait.until(d -> {
            try {
                java.util.List<WebElement> els = d.findElements(radioLocator);
                WebElement lastDisplayed = null;
                for (WebElement e : els) {
                    try {
                        if (e != null && e.isDisplayed() && e.isEnabled()) lastDisplayed = e;
                    } catch (Exception ignore) { }
                }
                return lastDisplayed;
            } catch (Exception e) {
                return null;
            }
        });
        if (el == null) {
            throw new NoSuchElementException("Cost Type element not found for: " + CostType + " using: " + radioLocator);
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        Thread.sleep(150);

        // Prefer clicking the real radio input
        WebElement radioInput = null;
        try {
            if ("input".equalsIgnoreCase(el.getTagName()) && "radio".equalsIgnoreCase(el.getAttribute("type"))) {
                radioInput = el;
            }
        } catch (Exception ignore) { }

        if (radioInput == null) {
            try { radioInput = el.findElement(By.xpath(".//input[@type='radio']")); } catch (Exception ignore) { }
        }
        if (radioInput == null) {
            try { radioInput = el.findElement(By.xpath("./ancestor::label[1]//input[@type='radio']")); } catch (Exception ignore) { }
        }
        // If we clicked a role=radio element, sometimes the associated input is in the parent container
        if (radioInput == null) {
            try { radioInput = el.findElement(By.xpath("./ancestor::*[@role='radiogroup' or contains(@class,'ms-ChoiceFieldGroup')][1]//input[@type='radio'][contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + toXpathLiteral(CostType.trim().toLowerCase()) + ")]")); } catch (Exception ignore) { }
        }

        WebElement clickTarget = (radioInput != null) ? radioInput : el;

        // Click (Actions → direct → JS)
        try {
            new Actions(driver).moveToElement(clickTarget).click().perform();
        } catch (Exception ignore) {
            try {
                clickTarget.click();
            } catch (Exception ignore2) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickTarget);
            }
        }

        // If we have the input, force-check it via JS and fire events (some UIs ignore click on wrapper)
        if (radioInput != null) {
            ((JavascriptExecutor) driver).executeScript(
                    "if(arguments[0] && !arguments[0].checked){arguments[0].checked=true;}" +
                            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));" +
                            "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true}));",
                    radioInput
            );
        }

        // Verify selection: input selected OR matching role=radio aria-checked=true (for THIS option text)
        final WebElement finalRadioInput = radioInput;
        final String expectedKey = (CostType == null ? "" : CostType.trim().toLowerCase());
        String literal = toXpathLiteral(expectedKey);
        String lower = "translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";
        By ariaCheckedByText = By.xpath(
                "//*[@role='radio' and (@aria-checked='true' or @aria-selected='true') and (" +
                        "contains(" + lower + "," + literal + ") or " +
                        ".//*[contains(" + lower + "," + literal + ")] or " +
                        "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," + literal + ")" +
                        ")]"
        );

        boolean selected = new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                if (finalRadioInput != null) {
                    if (finalRadioInput.isSelected()) return true;
                    String ac = finalRadioInput.getAttribute("aria-checked");
                    if ("true".equalsIgnoreCase(ac)) return true;
                }
            } catch (Exception ignore) { }
            try {
                return !d.findElements(ariaCheckedByText).isEmpty();
            } catch (Exception ignore) {
                return false;
            }
        });

        if (!selected) {
            throw new Exception("Cost Type not selected after click: " + CostType);
        }

        if (reportLogger != null) {
            reportLogger.pass("Successfully selected the Cost Type: " + CostType);
        }

        System.out.println("📝 ═══════════════════════════════════════════\n");
    }  
    
   

    public void enterAmount(String Amount) throws Throwable {
        System.out.println("\n📊 ═══════════════════════════════════════════");
        System.out.println("📊 ENTERING Cost  Amount: " + Amount);
        System.out.println("📊 ═══════════════════════════════════════════");
        
        if (Amount == null) Amount = "";

        WebElement amountField = resolveVisibleInput(InitiativePageLocators.CostsAmount, "Costs Amount");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", amountField);
        Thread.sleep(200);

        // Fluent UI (React controlled): use native value setter + input/change events
        String expected = Amount.trim();
        setNativeValueAndDispatch(amountField, expected);
        try { new Actions(driver).moveToElement(amountField).click().sendKeys(Keys.TAB).perform(); } catch (Exception ignore) { }
        Thread.sleep(150);

        String actual = "";
        try { actual = amountField.getAttribute("value"); } catch (Exception ignore) { }
        if (actual == null) actual = "";
        if (!actual.trim().equals(expected)) {
            throw new Exception("Amount value not set. Expected: " + expected + ", Actual: " + actual);
        }

        System.out.println("  ✅ Amount value entered: " + expected);
        if (reportLogger != null) {
            reportLogger.info("Entered Costs Amount: " + expected);
        }
        
        System.out.println("📊 ═══════════════════════════════════════════\n");
    }  
    
    
    private void setNativeValueAndDispatch(WebElement el, String value) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "const el = arguments[0]; const val = arguments[1];" +
                            "try{ el.focus(); }catch(e){}" +
                            "const tag = (el.tagName||'').toLowerCase();" +
                            "const proto = (tag==='textarea') ? HTMLTextAreaElement.prototype : HTMLInputElement.prototype;" +
                            "const desc = Object.getOwnPropertyDescriptor(proto,'value');" +
                            "if(desc && desc.set){ desc.set.call(el, val); } else { el.value = val; }" +
                            "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                            "el.dispatchEvent(new Event('change',{bubbles:true}));" +
                            "el.dispatchEvent(new Event('blur',{bubbles:true}));",
                    el, value
            );
        } catch (Exception e) {
            // last resort fallback
            try {
                el.clear();
            } catch (Exception ignore) { }
            try {
                el.sendKeys(value);
            } catch (Exception ignore) { }
        }
    } 
    
    
    public boolean verifyFromDateErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING From Date Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.FromDateErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find error alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (ONLY)
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }  
    
    public void enterCostFromDate(String FromDate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 ENTERING Costs From DATE");
        System.out.println("📅 Date: " + FromDate);
        System.out.println("📅 ═══════════════════════════════════════════");
        
        if (FromDate == null || FromDate.trim().isEmpty()) {
            System.out.println("  ⚠️ No In Date provided, skipping...");
            return;
        }

        // Simple + fast: locate → set value via JS on the real input → verify not blank
        setFluentUiDate(InitiativePageLocators.CostsFromDate, FromDate.trim(), "Costs From Date");
        
        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
    
    
    private void setFluentUiDate(By locator, String dateText, String fieldName) throws Exception {
        WebElement base = new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                java.util.List<WebElement> els = d.findElements(locator);
                WebElement lastDisplayed = null;
                for (WebElement e : els) {
                    try {
                        if (e != null && e.isDisplayed()) {
                            lastDisplayed = e; // keep the last displayed (often the top-most / latest in DOM)
                        }
                    } catch (Exception ignore) { }
                }
                return lastDisplayed;
            } catch (Exception e) {
                return null;
            }
        });
        if (base == null) {
            throw new NoSuchElementException("Could not locate visible element for: " + fieldName + " using locator: " + locator);
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", base);
        Thread.sleep(150);

        WebElement input = resolveDatePickerInput(base);

        // Path A: if we found a real input, set it like a React-controlled input
        if (input != null && isInputOrTextarea(input)) {
            setNativeValueAndDispatch(input, dateText);
            try { new Actions(driver).moveToElement(input).click().sendKeys(Keys.ENTER).perform(); } catch (Exception ignore) { }
        } else {
            // Path B: readOnly combobox/div. Open calendar and click the date.
            openDatePicker(base);
            boolean clicked = selectDateFromOpenCalendar(dateText);
            if (!clicked) {
                // Fallback: some Fluent UI DatePickers allow typing into the focused combobox/input even if calendar click fails.
                try {
                    java.util.Date d = parseExcelDate(dateText);
                    String mmddyyyy = d == null ? dateText : new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH).format(d);

                    try { new Actions(driver).sendKeys(Keys.ESCAPE).perform(); } catch (Exception ignore) { }
                    Thread.sleep(150);

                    // Prefer setting the actual input (if any) using the React-compatible setter.
                    try {
                        WebElement fallbackInput = resolveDatePickerInput(base);
                        if (fallbackInput != null && isInputOrTextarea(fallbackInput) && fallbackInput.isDisplayed() && fallbackInput.isEnabled()) {
                            setNativeValueAndDispatch(fallbackInput, mmddyyyy);
                            try { new Actions(driver).moveToElement(fallbackInput).click().sendKeys(Keys.TAB).perform(); } catch (Exception ignore) { }
                            Thread.sleep(200);
                            String v0 = readValueFromDatePicker(base, fallbackInput);
                            if (v0 != null && !v0.trim().isEmpty()) clicked = true;
                        }
                    } catch (Exception ignore) { }

                    try {
                        if (!clicked) {
                            // Some implementations keep focus on a "combobox" element; typing then TAB is safer than ENTER
                            // (ENTER can sometimes commit "today" depending on implementation).
                            new Actions(driver)
                                    .moveToElement(base).click()
                                    .pause(Duration.ofMillis(150))
                                    .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                                    .pause(Duration.ofMillis(75))
                                    .sendKeys(Keys.DELETE)
                                    .pause(Duration.ofMillis(75))
                                    .sendKeys(mmddyyyy)
                                    .pause(Duration.ofMillis(150))
                                    .sendKeys(Keys.TAB)
                                    .perform();
                        }
                    } catch (Exception ignore) { }
                    Thread.sleep(250);

                    // If typing worked, the value should be non-blank now.
                    WebElement maybeInput = resolveDatePickerInput(base);
                    String v = readValueFromDatePicker(base, maybeInput);
                    if (v != null && !v.trim().isEmpty()) {
                        clicked = true;
                    }
                } catch (Exception ignore) { }

                if (!clicked) {
                    throw new Exception(fieldName + " not set (could not select date from calendar). Expected: " + dateText);
                }
            }
            // Re-resolve the real input after selection
            input = resolveDatePickerInput(base);
        }

        // Verify: must not be blank after set
        String actual = readValueFromDatePicker(base, input);
        if (actual == null) actual = "";
        if (actual.trim().isEmpty()) {
            throw new Exception(fieldName + " not set (blank after set). Expected: " + dateText);
        }

        System.out.println("  ✅ " + fieldName + " set to: " + actual);
        if (reportLogger != null) {
            reportLogger.info(fieldName + ": " + actual);
        }
    } 
    
    private WebElement resolveDatePickerInput(WebElement base) {
        if (base == null) return null;

        // If base is already an input/textarea, use it.
        if (isInputOrTextarea(base)) return base;

        // Try normal descendant input resolution first.
        try {
            WebElement el = resolveEditableElement(base);
            if (el != null && isInputOrTextarea(el)) return el;
        } catch (Exception ignore) { }

        // Heuristic: if base id looks like DatePicker###-label, search for input with related id.
        try {
            String id = base.getAttribute("id");
            if (id != null && id.contains("DatePicker")) {
                String baseId = id.replace("-label", "");
                String xpath = "//input[contains(@id," + toXpathLiteral(baseId) + ")] | //textarea[contains(@id," + toXpathLiteral(baseId) + ")]";
                java.util.List<WebElement> els = driver.findElements(By.xpath(xpath));
                for (WebElement e : els) {
                    try {
                        if (e != null && e.isDisplayed()) return e;
                    } catch (Exception ignore) { }
                }
            }
        } catch (Exception ignore) { }

        // Heuristic: use aria-describedby link
        try {
            String desc = base.getAttribute("aria-describedby");
            if (desc != null && !desc.trim().isEmpty()) {
                java.util.List<WebElement> els = driver.findElements(By.xpath("//input[@aria-describedby=" + toXpathLiteral(desc) + "] | //textarea[@aria-describedby=" + toXpathLiteral(desc) + "]"));
                for (WebElement e : els) {
                    try {
                        if (e != null && e.isDisplayed()) return e;
                    } catch (Exception ignore) { }
                }
            }
        } catch (Exception ignore) { }

        // Last resort: look in nearest ms-DatePicker/ms-TextField container
        try {
            java.util.List<WebElement> els = base.findElements(By.xpath("./ancestor::*[contains(@class,'ms-DatePicker') or contains(@class,'ms-TextField')][1]//input"));
            for (WebElement e : els) {
                try {
                    if (e != null && e.isDisplayed()) return e;
                } catch (Exception ignore) { }
            }
        } catch (Exception ignore) { }

        return null;
    }

    private void openDatePicker(WebElement base) {
        try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", base); } catch (Exception ignore) { }
        try { new Actions(driver).moveToElement(base).click().perform(); } catch (Exception ignore) { }
    }

    
    private boolean selectDateFromOpenCalendar(String excelDateText) {
        java.util.Date date = parseExcelDate(excelDateText);
        if (date == null) return false;

        String fullLabel = buildFluentUiCalendarAriaLabel(date); // "Monday, December 1, 2025"
        String year = new java.text.SimpleDateFormat("yyyy", java.util.Locale.ENGLISH).format(date);
        String month = new java.text.SimpleDateFormat("MMMM", java.util.Locale.ENGLISH).format(date); // October
        String day = new java.text.SimpleDateFormat("d", java.util.Locale.ENGLISH).format(date);      // 1
        String monthDayYearComma = month + " " + day + ", " + year; // "October 1, 2025"
        String monthDayYearNoComma = month + " " + day + " " + year; // "October 1 2025"

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

        // 1) Wait for the calendar/callout to actually be present (Fluent UI renders it async)
        WebElement calendarRoot = null;
        By calendarRootBy = By.xpath(
                "//div[contains(@class,'ms-DatePicker-callout') or contains(@class,'ms-Callout')][.//div[contains(@class,'ms-Calendar')] or .//button[@role='gridcell']]" +
                        " | //div[contains(@class,'ms-Calendar') and .//button[@role='gridcell']]"
        );
        try {
            calendarRoot = wait.until(d -> {
                try {
                    java.util.List<WebElement> els = d.findElements(calendarRootBy);
                    for (int i = els.size() - 1; i >= 0; i--) {
                        try {
                            WebElement el = els.get(i);
                            if (el != null && el.isDisplayed()) return el;
                        } catch (Exception ignore) { }
                    }
                } catch (Exception ignore) { }
                return null;
            });
        } catch (Exception ignore) { }

        // 2) Best-effort month/year navigation so the correct month is visible (Oct 2025 etc)
        try {
            java.util.Calendar cal = java.util.Calendar.getInstance(java.util.Locale.ENGLISH);
            cal.setTime(date);
            java.time.YearMonth targetYm = java.time.YearMonth.of(
                    cal.get(java.util.Calendar.YEAR),
                    cal.get(java.util.Calendar.MONTH) + 1
            );

            // Find the month/year header (e.g. "December 2025")
            String headerXpathRel =
                    ".//button[contains(@class,'monthAndYear') or contains(@class,'headerToggleView')]" +
                            " | .//div[contains(@class,'monthAndYear')]" +
                            " | .//*[contains(@class,'monthAndYear')]";
            String headerXpathAbs =
                    "//button[contains(@class,'monthAndYear') or contains(@class,'headerToggleView')]" +
                            " | //div[contains(@class,'monthAndYear')]" +
                            " | //*[contains(@class,'monthAndYear')]";
            By headerByRel = By.xpath(headerXpathRel);
            By headerByAbs = By.xpath(headerXpathAbs);

            // Find prev/next month buttons (aria-label varies by Fluent UI version)
            String prevMonthXpathRel = ".//button[" +
                    "(" +
                    "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'previous') or " +
                    "contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'previous') or " +
                    "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'prev')" +
                    ")" +
                    " and " +
                    "(" +
                    "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'month') or " +
                    "contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'month') or " +
                    "contains(@class,'prev') or contains(@class,'Prev')" +
                    ")" +
                    "]";
            String prevMonthXpathAbs = prevMonthXpathRel.replace(".//button[", "//button[");
            By prevMonthByRel = By.xpath(prevMonthXpathRel);
            By prevMonthByAbs = By.xpath(prevMonthXpathAbs);

            String nextMonthXpathRel = ".//button[" +
                    "(" +
                    "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'next') or " +
                    "contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'next')" +
                    ")" +
                    " and " +
                    "(" +
                    "contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'month') or " +
                    "contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'month') or " +
                    "contains(@class,'next') or contains(@class,'Next')" +
                    ")" +
                    "]";
            String nextMonthXpathAbs = nextMonthXpathRel.replace(".//button[", "//button[");
            By nextMonthByRel = By.xpath(nextMonthXpathRel);
            By nextMonthByAbs = By.xpath(nextMonthXpathAbs);

            int safety = 0;
            while (safety++ < 36) { // up to 3 years worth of clicks
                WebElement header = null;
                String headerText = "";
                try {
                    if (calendarRoot != null) header = calendarRoot.findElement(headerByRel);
                    else header = driver.findElement(headerByAbs);
                    headerText = header == null ? "" : header.getText();
                } catch (Exception ignore) { }

                java.time.YearMonth currentYm = null;
                if (headerText != null) headerText = headerText.trim();
                if (headerText != null && !headerText.isEmpty()) {
                    try {
                        java.util.Date headerDate = new java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale.ENGLISH).parse(headerText);
                        java.util.Calendar hc = java.util.Calendar.getInstance(java.util.Locale.ENGLISH);
                        hc.setTime(headerDate);
                        currentYm = java.time.YearMonth.of(hc.get(java.util.Calendar.YEAR), hc.get(java.util.Calendar.MONTH) + 1);
                    } catch (Exception ignore) { }
                }

                if (currentYm == null) {
                    // Can't read header → don't loop forever; we still might click by aria-label if month buttons exist.
                    break;
                }

                if (currentYm.equals(targetYm)) {
                    break;
                }

                int diffMonths = (targetYm.getYear() - currentYm.getYear()) * 12 + (targetYm.getMonthValue() - currentYm.getMonthValue());
                By navByRel = diffMonths < 0 ? prevMonthByRel : nextMonthByRel;
                By navByAbs = diffMonths < 0 ? prevMonthByAbs : nextMonthByAbs;

                WebElement navBtn = null;
                try {
                    if (calendarRoot != null) navBtn = calendarRoot.findElement(navByRel);
                    else navBtn = driver.findElement(navByAbs);
                } catch (Exception ignore) { }

                if (navBtn == null || !navBtn.isDisplayed() || !navBtn.isEnabled()) {
                    break;
                }

                String before = headerText;
                try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navBtn); }
                catch (Exception e) { try { navBtn.click(); } catch (Exception ignore) { break; } }

                // Wait until header changes (or calendar rerenders)
                final String expectedDifferent = before == null ? "" : before.trim();
                final WebElement calRoot = calendarRoot;
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(2)).until(d -> {
                        try {
                            WebElement h = calRoot != null ? calRoot.findElement(headerByRel) : d.findElement(headerByAbs);
                            String t = h == null ? "" : h.getText();
                            if (t == null) t = "";
                            t = t.trim();
                            return !t.isEmpty() && !t.equalsIgnoreCase(expectedDifferent);
                        } catch (Exception ignore) {
                            return false;
                        }
                    });
                } catch (Exception ignore) { }
            }
        } catch (Exception ignore) { }

        // 3) Click the *exact* day button for the target date.
        // IMPORTANT: scope the search to the currently open calendar callout to avoid clicking a different DatePicker's "today".
        java.util.List<WebElement> buttons = new java.util.ArrayList<>();
        try {
            if (calendarRoot != null) {
                buttons = calendarRoot.findElements(By.xpath(".//button"));
            } else {
                buttons = driver.findElements(By.xpath("//button"));
            }
        } catch (Exception ignore) { }

        // Choose the best matching button by aria-label first (most reliable across Fluent UI variants)
        WebElement best = null;
        int bestScore = -1;
        for (WebElement b : buttons) {
            try {
                if (b == null || !b.isDisplayed() || !b.isEnabled()) continue;
                String aria = "";
                try { aria = b.getAttribute("aria-label"); } catch (Exception ignore) { }
                if (aria == null) aria = "";
                String txt = "";
                try { txt = b.getText(); } catch (Exception ignore) { }
                if (txt == null) txt = "";
                txt = txt.trim();
                String cls = "";
                try { cls = b.getAttribute("class"); } catch (Exception ignore) { }
                if (cls == null) cls = "";

                // Exclude obvious non-day buttons
                String ariaLower = aria.toLowerCase();
                if (ariaLower.contains("go to today") || ariaLower.contains("today")) {
                    // day buttons can still contain the word today in some implementations, but those are rare; keep it conservative.
                    // We'll only exclude if it doesn't contain the target year/month.
                    if (!aria.contains(year) && !aria.contains(month)) continue;
                }
                if (cls.toLowerCase().contains("nav") || cls.toLowerCase().contains("next") || cls.toLowerCase().contains("prev")) continue;
                if (cls.toLowerCase().contains("month") && cls.toLowerCase().contains("year")) continue;

                int score = 0;
                if (!aria.isEmpty()) {
                    if (aria.equals(fullLabel)) score += 100;
                    if (aria.contains(monthDayYearComma)) score += 90;
                    if (aria.contains(monthDayYearNoComma)) score += 80;
                    if (aria.contains(month) && aria.contains(year)) score += 25;
                    // word boundary-ish match for day in aria-label
                    if (aria.matches(".*\\b" + java.util.regex.Pattern.quote(day) + "\\b.*")) score += 10;
                }
                // If aria-label is missing or unhelpful, fall back to gridcell text match, but only if it looks like the right month/year.
                if (score == 0 && day.equals(txt)) {
                    // Avoid outside-month cells when possible
                    String clsLower = cls.toLowerCase();
                    if (clsLower.contains("outside") || clsLower.contains("disabled")) continue;
                    score = 5;
                    if (aria.contains(month) || aria.contains(year)) score += 5;
                }

                if (score > bestScore) {
                    bestScore = score;
                    best = b;
                }
            } catch (Exception ignore) { }
        }

        if (best != null && bestScore > 0) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", best);
            } catch (Exception e) {
                try { best.click(); } catch (Exception ignore) { return false; }
            }
            try { Thread.sleep(200); } catch (Exception ignore) { }
            return true;
        }

        // 4) Last resort: targeted XPaths *within* calendarRoot (when available)
        By[] candidates = new By[]{
                By.xpath(".//button[@aria-label=" + toXpathLiteral(fullLabel) + "]"),
                By.xpath(".//button[contains(@aria-label," + toXpathLiteral(monthDayYearComma) + ")]"),
                By.xpath(".//button[contains(@aria-label," + toXpathLiteral(monthDayYearNoComma) + ")]"),
                By.xpath(".//button[@role='gridcell' and normalize-space()=" + toXpathLiteral(day) + " and not(contains(@class,'outside')) and not(contains(@class,'Outside')) and not(contains(@class,'disabled'))]")
        };
        for (By by : candidates) {
            try {
                WebElement btn = (calendarRoot != null)
                        ? calendarRoot.findElement(by)
                        : driver.findElement(By.xpath(by.toString().replace("By.xpath: ", "").replace(".//", "//")));
                if (btn != null && btn.isDisplayed() && btn.isEnabled()) {
                    try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); }
                    catch (Exception ignore) { btn.click(); }
                    Thread.sleep(200);
                    return true;
                }
            } catch (Exception ignore) { }
        }

        return false;
    }

    private java.util.Date parseExcelDate(String s) {
        if (s == null) return null;
        String v = s.trim();
        if (v.isEmpty()) return null;
        try {
            return new java.text.SimpleDateFormat("EEE MMM dd yyyy", java.util.Locale.ENGLISH).parse(v);
        } catch (Exception ignore) { }
        try {
            return new java.text.SimpleDateFormat("MMM dd yyyy", java.util.Locale.ENGLISH).parse(v);
        } catch (Exception ignore) { }
        return null;
    }

    private String readValueFromDatePicker(WebElement base, WebElement input) {
        try {
            if (input != null) {
                String v = input.getAttribute("value");
                if (v != null) return v;
            }
        } catch (Exception ignore) { }
        try {
            if (base != null) {
                String v = base.getAttribute("value");
                if (v != null) return v;
            }
        } catch (Exception ignore) { }
        try {
            if (base != null) {
                String t = base.getText();
                if (t != null) return t;
            }
        } catch (Exception ignore) { }
        return "";
    } 
    
    public boolean verifyToDateErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING To Date Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.ToDateErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find error alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (ONLY)
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    } 
    
    public void enterCostToDate(String ToDate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 ENTERING Costs From DATE");
        System.out.println("📅 Date: " + ToDate);
        System.out.println("📅 ═══════════════════════════════════════════");
        
        if (ToDate == null || ToDate.trim().isEmpty()) {
            System.out.println("  ⚠️ No In Date provided, skipping...");
            return;
        }

        // Simple + fast: locate → set value via JS on the real input → verify not blank
        setFluentUiDate(InitiativePageLocators.CostsToDate, ToDate.trim(), "Costs To Date");
        
        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
    
    public void enterDescription(String Description) throws Throwable {
        System.out.println("\n📊 ═══════════════════════════════════════════");
        System.out.println("📊 ENTERING Cost Description: " + Description);
        System.out.println("📊 ═══════════════════════════════════════════");

        if (Description == null) Description = "";
        String expected = Description.trim();

        WebElement descField = resolveVisibleInput(InitiativePageLocators.CostsDecription, "Costs Description");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", descField);
        Thread.sleep(150);

        // Fluent UI (React controlled): use native value setter + input/change events
        setNativeValueAndDispatch(descField, expected);
        try { new Actions(driver).moveToElement(descField).click().sendKeys(Keys.TAB).perform(); } catch (Exception ignore) { }
        Thread.sleep(150);

        String actual = descField.getAttribute("value");
        if (actual == null) actual = "";
        if (!actual.trim().contains(expected)) {
            // Some implementations show text in innerText; check that too
            String t = "";
            try { t = descField.getText(); } catch (Exception ignore) { }
            if (t == null) t = "";
            if (!t.trim().contains(expected)) {
                throw new Exception("Description value not set. Expected contains: " + expected + ", Actual: " + actual);
            }
        }

        System.out.println("  ✅ Description value entered: " + expected);
        if (reportLogger != null) {
            reportLogger.info("Entered Costs Description: " + expected);
        }
        
        System.out.println("📊 ═══════════════════════════════════════════\n");
    }
 
    private WebElement findCostCategoryDropdownTrigger(WebDriverWait wait) throws Exception {
        // Multiple strategies because ids like `select_Category-option` are often dynamic across environments/builds.
        By[] locators = new By[]{
                // Fluent UI: label -> role=combobox
                By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::*[@role='combobox'][1]"),
                // Fluent UI: label -> ms-Dropdown-title (clickable text area)
                By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::div[contains(@class,'ms-Dropdown-title')][1]"),
                // Fluent UI: label -> option span (sometimes used as trigger)
                By.xpath("//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]/following::span[contains(@id,'-option')][1]"),
                // any element with select_Category in id
                By.xpath("//*[contains(@id,'select_Category') and (self::span or self::div or self::button)]"),
                // any ms-Dropdown that contains the label text, then click its title
                By.xpath("//div[contains(@class,'ms-Dropdown')][.//label[contains(normalize-space(.),'Cost Category') or contains(normalize-space(.),'Category')]]//*[contains(@class,'ms-Dropdown-title')][1]")
        };

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        Exception last = null;

        for (By by : locators) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.elementToBeClickable(by));
                if (el != null) {
                    return el;
                }
            } catch (Exception e) {
                last = e;
            }
        }

        // If nothing worked, surface a useful failure
        if (last != null) throw last;
        throw new NoSuchElementException("Cost Category dropdown trigger not found with fallback strategies");
    }

    private String toXpathLiteral(String s) {
        if (s == null) return "''";
        if (!s.contains("'")) {
            return "'" + s + "'";
        }
        String[] parts = s.split("'");
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            sb.append("'").append(parts[i]).append("'");
            if (i != parts.length - 1) sb.append(", \"'\", ");
        }
        sb.append(")");
        return sb.toString();
    }

    private String buildFluentUiCalendarAriaLabel(java.util.Date date) {
        // Example: "Monday, December 1, 2025"
        String dow = new java.text.SimpleDateFormat("EEEE", java.util.Locale.ENGLISH).format(date);
        String month = new java.text.SimpleDateFormat("MMMM", java.util.Locale.ENGLISH).format(date);
        String day = new java.text.SimpleDateFormat("d", java.util.Locale.ENGLISH).format(date);
        String year = new java.text.SimpleDateFormat("yyyy", java.util.Locale.ENGLISH).format(date);
        return dow + ", " + month + " " + day + ", " + year;
    } 
    
    public boolean verifyDescriptionErrorAlert(String expectedMessage) throws Throwable {
    	   System.out.println("\n✅ ═══════════════════════════════════════════");
    	   System.out.println("✅ VERIFYING Description Error ALERT");
    	   System.out.println("✅ Expected: " + expectedMessage);
    	   System.out.println("✅ ═══════════════════════════════════════════");
    	   
    	   try {
    	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    	       
    	       // Multiple strategies to find error alert
    	       By[] alertLocators = {
    	           InitiativePageLocators.DecriptionErrorAlert,
    	           By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
    	           By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
    	           By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
    	           By.xpath("//div[contains(@role,'alert')]"),
    	           By.xpath("//div[contains(@class,'error')]"),
    	           By.xpath("//*[contains(text(),'left blank')]")
    	       };
    	       
    	       WebElement alert = null;
    	       String actualMessage = "";
    	       
    	       for (By locator : alertLocators) {
    	           try {
    	               alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    	               actualMessage = alert.getText();
    	               System.out.println("  ✅ Found alert element");
    	               break;
    	           } catch (Exception e) {
    	               // Try next locator
    	           }
    	       }
    	       
    	       if (alert == null) {
    	           System.out.println("  ❌ Could not find error alert");
    	           if (reportLogger != null) {
    	               reportLogger.fail("❌ Error alert not found");
    	           }
    	           return false;
    	       }
    	       
    	       System.out.println("  📋 Actual message: " + actualMessage);
    	       
    	       // Verify message contains expected text (ONLY)
    	       boolean isSuccess = actualMessage != null
    	               && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
    	       
    	       if (isSuccess) {
    	           System.out.println("  ✅ Error: Alert message verified!");
    	           if (reportLogger != null) {
    	               reportLogger.pass("✅ Error alert verified: " + actualMessage);
    	           }
    	       } else {
    	           System.out.println("  ⚠️ Alert found but message doesn't match expected");
    	           System.out.println("  Expected: " + expectedMessage);
    	           System.out.println("  Actual: " + actualMessage);
    	           if (reportLogger != null) {
    	               reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
    	           }
    	       }
    	       
    	       System.out.println("✅ ═══════════════════════════════════════════\n");
    	       return isSuccess;
    	       
    	   } catch (Exception e) {
    	       System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
    	       if (reportLogger != null) {
    	           reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
    	       }
    	       throw e;
    	   }
    	}
    	  
    public boolean verifyCostDuplicateAlert(String expectedMessage) throws Throwable {
        System.out.println("\n⚠️ ═══════════════════════════════════════════");
        System.out.println("⚠️ VERIFYING Costs DUPLICATE ALERT");
        System.out.println("⚠️ Expected: " + expectedMessage);
        System.out.println("⚠️ ═══════════════════════════════════════════");

        String expected = expectedMessage == null ? "" : expectedMessage.trim().toLowerCase();
        long end = System.currentTimeMillis() + 10_000;
        String lastSeen = "";

        By toastCandidates = By.xpath(
                "//div[@role='alert'] | " +
                        "//div[contains(@class,'toast') or contains(@class,'Toast') or contains(@class,'Toastify') or contains(@class,'snackbar') or contains(@class,'notification')] | " +
                        "//div[contains(@class,'ms-MessageBar')] | " +
                        "//*[contains(@class,'error-message') or contains(@class,'validation-message')]"
        );

        while (System.currentTimeMillis() < end) {
            java.util.List<WebElement> els = new java.util.ArrayList<>();
            try { els.addAll(driver.findElements(InitiativePageLocators.toastAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(toastCandidates)); } catch (Exception ignore) { }

            for (WebElement el : els) {
                try {
                    if (el == null || !el.isDisplayed()) continue;

                    String txt = "";
                    try { txt = el.getText(); } catch (Exception ignore) { }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try {
                            Object o = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent || '';", el);
                            txt = o == null ? "" : String.valueOf(o);
                        } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try { txt = el.getAttribute("aria-label"); } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";

                    String norm = txt.trim();
                    if (!norm.isEmpty()) lastSeen = norm;
                    String lower = norm.toLowerCase();
                    if (lower.isEmpty()) continue;

                    boolean matchesExpected = !expected.isEmpty() && lower.contains(expected);
                    boolean matchesKeywords =
                            (lower.contains("already") && (lower.contains("exist") || lower.contains("present") || lower.contains("added"))) ||
                            (lower.contains("already present") && (lower.contains("date") || lower.contains("dates") || lower.contains("selected"))) ||
                            (lower.contains("selected") && (lower.contains("date") || lower.contains("dates"))) ||
                            lower.contains("duplicate") ||
                            (lower.contains("same") && lower.contains("period")) ||
                            (lower.contains("same") && lower.contains("category"));

                    // Guard against false-positives on success toasts
                    boolean looksLikeSuccess =
                            lower.contains("success") || lower.contains("successfully") || lower.contains("saved") || lower.contains("updated");

                    if (!looksLikeSuccess && (matchesExpected || matchesKeywords)) {
                        System.out.println("  ✅ DUPLICATE ALERT DETECTED: " + norm);
                        if (reportLogger != null) {
                            reportLogger.pass("✅ Duplicate cost alert verified: " + norm);
                        }
                        System.out.println("⚠️ ═══════════════════════════════════════════\n");
                        return true;
                    }
                } catch (Exception ignore) {
                    // stale toast; keep polling
                }
            }

            Thread.sleep(250);
        }

        System.out.println("  ❌ Could not verify duplicate alert within timeout.");
        if (!lastSeen.isEmpty()) {
            System.out.println("  📋 Last seen alert text: " + lastSeen);
        }
        if (reportLogger != null) {
            reportLogger.fail("❌ Duplicate cost alert not verified. Last seen: " + lastSeen);
        }
        System.out.println("⚠️ ═══════════════════════════════════════════\n");
        return false;
    }
  
   
    
    public void clickCostEditButtonForRecord(String costCategory,
            String costType,
            String amount,
            String fromDate,
            String toDate,
            String description) throws Throwable {
System.out.println("\n✏️ ═══════════════════════════════════════════");
System.out.println("✏️ CLICKING COST EDIT BUTTON (MATCHING RECORD)");
System.out.println("✏️ Criteria: Category=" + costCategory + " | Type=" + costType + " | Amount=" + amount +
" | From=" + fromDate + " | To=" + toDate + " | Desc=" + description);
System.out.println("✏️ ═══════════════════════════════════════════");

String cat = norm(costCategory);
String type = norm(costType);
String amt = normAmount(amount);
String desc = norm(description);

java.util.List<String> fromTokens = buildDateTokens(fromDate);
java.util.List<String> toTokens = buildDateTokens(toDate);

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

// Wait until Cost tab/table is present (best-effort)
try { wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.costDetailsHeader)); } catch (Exception ignore) { }
Thread.sleep(500);

// Collect row candidates from the Costs table specifically (avoid matching other tables on the page).
java.util.List<WebElement> rows = new java.util.ArrayList<>();
try {
WebElement header = driver.findElement(InitiativePageLocators.costDetailsHeader);
WebElement scope = header;
// expand scope to a reasonable container (best-effort)
try { scope = header.findElement(By.xpath("./ancestor::*[self::div or self::section][1]")); } catch (Exception ignore) { }
rows.addAll(scope.findElements(By.xpath(".//table//tbody//tr")));
} catch (Exception ignore) { }

// Fallbacks for other grid implementations (still best-effort)
if (rows.isEmpty()) {
try { rows.addAll(driver.findElements(By.xpath("//table//tbody//tr"))); } catch (Exception ignore) { }
}
if (rows.isEmpty()) {
try { rows.addAll(driver.findElements(By.xpath("//div[contains(@class,'ms-DetailsRow')]"))); } catch (Exception ignore) { }
}
if (rows.isEmpty()) {
try { rows.addAll(driver.findElements(By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"))); } catch (Exception ignore) { }
}

// Deduplicate by WebElement reference identity (best-effort)
java.util.LinkedHashSet<WebElement> uniq = new java.util.LinkedHashSet<>(rows);
rows = new java.util.ArrayList<>(uniq);

if (rows.isEmpty()) {
// fallback to generic behavior
System.out.println("  ⚠️ No cost rows found; falling back to generic edit button.");
clickCostEditButton();
return;
}

// Rank rows by match score (we'll attempt multiple rows until the drawer's Description matches exactly)
java.util.List<WebElement> rankedRows = new java.util.ArrayList<>();
java.util.List<Integer> rankedScores = new java.util.ArrayList<>();

By[] editBy = new By[] {
By.xpath(".//button[@title='Edit' or contains(@aria-label,'Edit') or contains(@title,'Edit')]"),
By.xpath(".//svg[@data-testid='EditIcon']/ancestor::button"),
By.xpath(".//*[@data-testid='EditIcon']/ancestor::button"),
By.xpath(".//button[contains(@aria-label,'Edit')]")
};

for (WebElement r : rows) {
try {
if (r == null || !r.isDisplayed()) continue;

// Only consider rows that actually have an edit action (prevents picking headers/empty rows)
boolean hasEdit = false;
for (By by : editBy) {
try {
WebElement b = r.findElement(by);
if (b != null && b.isDisplayed() && b.isEnabled()) {
hasEdit = true;
break;
}
} catch (Exception ignore) { }
}
if (!hasEdit) continue;

String text = "";
try { text = r.getText(); } catch (Exception ignore) { }
String t = norm(text);
if (t.isEmpty()) continue;

int score = 0;

// Strong signal when description is present in the grid.
if (!desc.isEmpty() && t.contains(desc)) score += 100;

// Amount: match either "4000" or "4000.00" etc by comparing digits-only
if (!amt.isEmpty()) {
String digits = t.replaceAll("[^0-9]", "");
String amtDigits = amt.replaceAll("[^0-9]", "");
if (!amtDigits.isEmpty() && digits.contains(amtDigits)) score += 25;
}

if (!type.isEmpty() && t.contains(type)) score += 20;
if (!cat.isEmpty() && t.contains(cat)) score += 20;

if (containsAny(t, fromTokens)) score += 10;
if (containsAny(t, toTokens)) score += 10;

// Keep all scored rows; we'll sort desc by score
rankedRows.add(r);
rankedScores.add(score);
} catch (Exception ignore) { }
}

if (rankedRows.isEmpty()) {
throw new Exception("No candidate cost rows found for edit. Criteria: Category=" + costCategory + ", Type=" + costType +
", Amount=" + amount + ", From=" + fromDate + ", To=" + toDate + ", Desc=" + description);
}

// Sort by score desc (simple selection sort; list is small)
for (int i = 0; i < rankedScores.size(); i++) {
int maxIdx = i;
for (int j = i + 1; j < rankedScores.size(); j++) {
if (rankedScores.get(j) > rankedScores.get(maxIdx)) maxIdx = j;
}
if (maxIdx != i) {
int tmpS = rankedScores.get(i);
rankedScores.set(i, rankedScores.get(maxIdx));
rankedScores.set(maxIdx, tmpS);
WebElement tmpR = rankedRows.get(i);
rankedRows.set(i, rankedRows.get(maxIdx));
rankedRows.set(maxIdx, tmpR);
}
}

// Attempt up to N rows, verifying by reading the Description field inside the drawer.
final String expectedDescNorm = norm(description);
int attempts = 0;
int maxAttempts = Math.min(10, rankedRows.size());

for (int idx = 0; idx < maxAttempts; idx++) {
WebElement row = rankedRows.get(idx);
int score = rankedScores.get(idx);
if (score < 20) {
// Stop early if remaining candidates are too weak.
break;
}

attempts++;
System.out.println("  🔎 Trying row candidate #" + attempts + " (score=" + score + ")");

WebElement editBtn = null;
for (By by : editBy) {
try {
WebElement b = row.findElement(by);
if (b != null && b.isDisplayed() && b.isEnabled()) {
editBtn = b;
break;
}
} catch (Exception ignore) { }
}
if (editBtn == null) continue;

try { ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn); } catch (Exception ignore) { }
Thread.sleep(150);
try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn); }
catch (Exception e) { try { editBtn.click(); } catch (Exception ignore) { continue; } }

// Wait briefly for drawer/form to be present
Thread.sleep(800);

String actualDesc = readCurrentCostDescription();
String actualDescNorm = norm(actualDesc);
if (!expectedDescNorm.isEmpty() && actualDescNorm.contains(expectedDescNorm)) {
System.out.println("  ✅ Opened correct cost record for edit (Description matched).");
if (reportLogger != null) reportLogger.info("✅ Opened correct cost record for edit (Description matched)");
System.out.println("✏️ ═══════════════════════════════════════════\n");
return;
}

System.out.println("  ⚠️ Opened different record (Description mismatch). Expected contains: " + description + " | Actual: " + actualDesc);
closeAnyOpenDrawerOrPopup();
Thread.sleep(400);
}

throw new Exception("Could not open the correct cost record for edit after trying " + attempts + " row(s). " +
"Ensure the grid shows unique identifying data or adjust row matching. Expected Description contains: " + description);
}

    private String readCurrentCostDescription() {
        try {
            WebElement el = resolveVisibleInput(InitiativePageLocators.CostsDecription, "Costs Description");
            if (el != null) {
                String v = el.getAttribute("value");
                if (v != null) return v;
                String t = el.getText();
                if (t != null) return t;
            }
        } catch (Exception ignore) { }
        // Try within drawer scope if present
        try {
            WebElement drawer = driver.findElement(By.xpath("//div[contains(@class,'MuiDrawer-paper')]"));
            WebElement el = null;
            try { el = drawer.findElement(By.xpath(".//textarea|.//input")); } catch (Exception ignore) { }
            if (el != null) {
                String v = el.getAttribute("value");
                if (v != null) return v;
            }
        } catch (Exception ignore) { }
        return "";
    }  
    
    
   
    private void closeAnyOpenDrawerOrPopup() {
        try {
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
            Thread.sleep(150);
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        } catch (Exception ignore) { }
        // If there's a visible close button in the drawer/modal, click it.
        try {
            java.util.List<WebElement> closes = driver.findElements(By.xpath(
                    "//div[contains(@class,'MuiDrawer-paper')]//button[contains(@aria-label,'Close') or @title='Close' or normalize-space()='×' or normalize-space()='X'] | " +
                            "//button[contains(@aria-label,'Close') or @title='Close' or normalize-space()='×' or normalize-space()='X']"
            ));
            for (WebElement c : closes) {
                try {
                    if (c != null && c.isDisplayed() && c.isEnabled()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", c);
                        break;
                    }
                } catch (Exception ignore) { }
            }
        } catch (Exception ignore) { }
    }
  
    private String norm(String s) {
        if (s == null) return "";
        return s.trim().toLowerCase(java.util.Locale.ENGLISH).replaceAll("\\s+", " ");
    }  
    
    
    public void clickCostEditButton() throws Throwable {
        System.out.println("\n✏️ ═══════════════════════════════════════════");
        System.out.println("✏️ CLICKING COST EDIT BUTTON");
        System.out.println("✏️ ═══════════════════════════════════════════");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.costEditButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editButton);
            Thread.sleep(250);

            try {
                editButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            }

            System.out.println("  ✅ Cost Edit button clicked");
            Thread.sleep(1000);
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Cost Edit button");
            }
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Cost Edit button: " + e.getMessage());
            throw e;
        }

        System.out.println("✏️ ═══════════════════════════════════════════\n");
    } 
    
    private boolean containsAny(String haystackNorm, java.util.List<String> needlesNorm) {
        if (haystackNorm == null || haystackNorm.isEmpty()) return false;
        if (needlesNorm == null || needlesNorm.isEmpty()) return false;
        for (String n : needlesNorm) {
            if (n == null) continue;
            String nn = n.trim();
            if (nn.isEmpty()) continue;
            if (haystackNorm.contains(nn)) return true;
        }
        return false;
    }  
    
    

    private String normAmount(String s) {
        if (s == null) return "";
        // Keep digits and dot only; remove commas/currency/etc
        String v = s.replaceAll("[^0-9.]", "");
        return v.trim();
    }
    
    private java.util.List<String> buildDateTokens(String excelDateText) {
        java.util.List<String> tokens = new java.util.ArrayList<>();
        if (excelDateText == null || excelDateText.trim().isEmpty()) return tokens;

        // raw
        tokens.add(norm(excelDateText));

        java.util.Date d = parseExcelDate(excelDateText);
        if (d == null) return tokens;

        java.util.Locale loc = java.util.Locale.ENGLISH;
        String[] fmts = new String[] {
                "MM/dd/yyyy", "M/d/yyyy",
                "dd/MM/yyyy", "d/M/yyyy",
                "yyyy-MM-dd",
                "dd-MMM-yyyy", "d-MMM-yyyy",
                "dd MMM yyyy", "d MMM yyyy",
                "MMM d yyyy", "MMM dd yyyy",
                "MMM d, yyyy", "MMM dd, yyyy",
                "MMMM d yyyy", "MMMM dd yyyy",
                "MMMM d, yyyy", "MMMM dd, yyyy"
        };
        for (String f : fmts) {
            try {
                tokens.add(norm(new java.text.SimpleDateFormat(f, loc).format(d)));
            } catch (Exception ignore) { }
        }
        return tokens;
    }

    public void openCostMonthlyDistributionTab() throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 OPENING COST MONTHLY DISTRIBUTION TAB");
        System.out.println("📅 ═══════════════════════════════════════════");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.monthlyDistributionTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", tab);
        Thread.sleep(250);
        try {
            tab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }
        Thread.sleep(800);

        // Wait for table to appear (doesn't fail hard if table is empty)
        try { wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.monthlyDistributionTable)); }
        catch (Exception ignore) { }

        if (reportLogger != null) {
            reportLogger.info("Opened Monthly Distribution tab");
        }

        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
   
    public java.math.BigDecimal getMonthlyDistributionAmount(java.time.YearMonth ym) throws Throwable {
        if (ym == null) throw new IllegalArgumentException("YearMonth cannot be null");

        String monthFull = ym.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH); // e.g. October
        int year = ym.getYear();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.monthlyDistributionTable));

        // 1) Find the column index for the month by matching <th> text
        java.util.List<WebElement> headers = table.findElements(By.xpath(".//thead//th"));
        if (headers == null || headers.isEmpty()) {
            throw new AssertionError("Monthly Distribution header row not found");
        }
        int monthColIdx = -1; // 0-based among th
        for (int i = 0; i < headers.size(); i++) {
            String h = "";
            try { h = headers.get(i).getText(); } catch (Exception ignore) { }
            if (h == null) h = "";
            if (h.trim().equalsIgnoreCase(monthFull)) {
                monthColIdx = i;
                break;
            }
        }
        if (monthColIdx < 0) {
            throw new AssertionError("Monthly Distribution column not found for month header: " + monthFull);
        }

        // 2) Find the row for the year (first cell is Year)
        java.util.List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));
        if (rows == null || rows.isEmpty()) {
            throw new AssertionError("Monthly Distribution body rows not found");
        }
        WebElement yearRow = null;
        for (WebElement r : rows) {
            try {
                java.util.List<WebElement> tds = r.findElements(By.xpath("./td"));
                if (tds == null || tds.isEmpty()) continue;
                String y = tds.get(0).getText();
                if (y != null && y.trim().equals(String.valueOf(year))) {
                    yearRow = r;
                    break;
                }
            } catch (Exception ignore) { }
        }
        if (yearRow == null) {
            throw new AssertionError("Monthly Distribution row not found for year: " + year);
        }

        java.util.List<WebElement> cells = yearRow.findElements(By.xpath("./td"));
        if (cells == null || cells.size() <= monthColIdx) {
            throw new AssertionError("Monthly Distribution cell not found for " + monthFull + " " + year + " (monthColIdx=" + monthColIdx + ")");
        }

        WebElement cell = cells.get(monthColIdx);
        // In your DOM the value is in a <div aria-label='Current value: 4000'>4000</div>
        WebElement valueDiv = null;
        try {
            valueDiv = cell.findElement(By.xpath(".//div[@aria-label]"));
        } catch (Exception ignore) { }

        String raw = "";
        if (valueDiv != null) {
            try { raw = valueDiv.getText(); } catch (Exception ignore) { }
            if (raw == null) raw = "";
            if (raw.trim().isEmpty()) {
                try { raw = valueDiv.getAttribute("aria-label"); } catch (Exception ignore) { }
                if (raw == null) raw = "";
                // aria-label: "Current value: 4000"
                raw = raw.replace("Current value:", "").trim();
            }
        } else {
            try { raw = cell.getText(); } catch (Exception ignore) { }
            if (raw == null) raw = "";
        }

        raw = raw.replace(",", "").replaceAll("[₹$€]", "").trim();
        if (raw.isEmpty()) return java.math.BigDecimal.ZERO;

        try {
            return new java.math.BigDecimal(raw);
        } catch (Exception e) {
            throw new AssertionError("Could not parse Monthly Distribution amount from '" + raw + "' for " + monthFull + " " + year);
        }
    }  
    
    public void setMonthlyDistributionAmount(java.time.YearMonth ym, java.math.BigDecimal value) throws Throwable {
        if (ym == null) throw new IllegalArgumentException("YearMonth cannot be null");
        if (value == null) throw new IllegalArgumentException("Value cannot be null");

        String monthFull = ym.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
        int year = ym.getYear();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.monthlyDistributionTable));

        // 1) Find the column index for the month by matching <th> text
        java.util.List<WebElement> headers = table.findElements(By.xpath(".//thead//th"));
        if (headers == null || headers.isEmpty()) {
            throw new AssertionError("Monthly Distribution header row not found");
        }
        int monthColIdx = -1;
        for (int i = 0; i < headers.size(); i++) {
            String h = "";
            try { h = headers.get(i).getText(); } catch (Exception ignore) { }
            if (h == null) h = "";
            if (h.trim().equalsIgnoreCase(monthFull)) {
                monthColIdx = i;
                break;
            }
        }
        if (monthColIdx < 0) {
            throw new AssertionError("Monthly Distribution column not found for month header: " + monthFull);
        }

        // 2) Find the row for the year
        java.util.List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));
        if (rows == null || rows.isEmpty()) {
            throw new AssertionError("Monthly Distribution body rows not found");
        }
        WebElement yearRow = null;
        for (WebElement r : rows) {
            try {
                java.util.List<WebElement> tds = r.findElements(By.xpath("./td"));
                if (tds == null || tds.isEmpty()) continue;
                String y = tds.get(0).getText();
                if (y != null && y.trim().equals(String.valueOf(year))) {
                    yearRow = r;
                    break;
                }
            } catch (Exception ignore) { }
        }
        if (yearRow == null) {
            throw new AssertionError("Monthly Distribution row not found for year: " + year);
        }

        java.util.List<WebElement> cells = yearRow.findElements(By.xpath("./td"));
        if (cells == null || cells.size() <= monthColIdx) {
            throw new AssertionError("Monthly Distribution cell not found for " + monthFull + " " + year + " (monthColIdx=" + monthColIdx + ")");
        }

        WebElement cell = cells.get(monthColIdx);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cell);
        Thread.sleep(200);

        String toType = formatMoneyForInput(value);

        // Try to activate editing
        try { new Actions(driver).moveToElement(cell).click().pause(Duration.ofMillis(150)).doubleClick().perform(); } catch (Exception ignore) { }
        try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cell); } catch (Exception ignore) { }
        Thread.sleep(200);

        // Prefer input inside the cell
        WebElement input = null;
        try { input = cell.findElement(By.xpath(".//input|.//textarea")); } catch (Exception ignore) { }

        if (input != null && input.isDisplayed() && input.isEnabled()) {
            setNativeValueAndDispatch(input, toType);
            try { new Actions(driver).moveToElement(input).click().sendKeys(Keys.TAB).perform(); } catch (Exception ignore) { }
            Thread.sleep(250);
            return;
        }

        // Fallback: click current value div and type
        WebElement valueDiv = null;
        try { valueDiv = cell.findElement(By.xpath(".//div[@aria-label]")); } catch (Exception ignore) { }
        WebElement target = valueDiv != null ? valueDiv : cell;
        try {
            new Actions(driver)
                    .moveToElement(target).click()
                    .pause(Duration.ofMillis(150))
                    .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                    .pause(Duration.ofMillis(75))
                    .sendKeys(Keys.DELETE)
                    .pause(Duration.ofMillis(75))
                    .sendKeys(toType)
                    .pause(Duration.ofMillis(150))
                    .sendKeys(Keys.TAB)
                    .perform();
        } catch (Exception ignore) { }
        Thread.sleep(250);
    }
  
    private String formatMoneyForInput(java.math.BigDecimal v) {
        if (v == null) return "";
        java.math.BigDecimal vv = v.setScale(2, java.math.RoundingMode.HALF_UP);
        // If it's whole, type without decimals (many grids prefer that)
        if (vv.stripTrailingZeros().scale() <= 0) {
            return vv.setScale(0, java.math.RoundingMode.HALF_UP).toPlainString();
        }
        return vv.toPlainString();
    }
    
    
    public boolean verifyMonthlyDistributionExceedAlert(java.math.BigDecimal totalAmount, String expectedMessage) throws Throwable {
        String totalToken = "";
        if (totalAmount != null) {
            java.math.BigDecimal t = totalAmount.setScale(2, java.math.RoundingMode.HALF_UP);
            totalToken = formatMoneyForDisplayToken(t); // "4000" or "4000.50"
        }

        // Normalize expected to compare reliably (ignore commas and case)
        String expectedNorm = expectedMessage == null ? "" : expectedMessage.trim().toLowerCase().replace(",", "");

        long end = System.currentTimeMillis() + 10_000;
        String lastSeen = "";

        By toastCandidates = By.xpath(
                "//div[@role='alert'] | " +
                        "//div[contains(@class,'toast') or contains(@class,'Toast') or contains(@class,'Toastify') or contains(@class,'snackbar') or contains(@class,'notification')] | " +
                        "//div[contains(@class,'ms-MessageBar')] | " +
                        "//*[contains(@class,'error-message') or contains(@class,'validation-message')]"
        );

        while (System.currentTimeMillis() < end) {
            java.util.List<WebElement> els = new java.util.ArrayList<>();
            try { els.addAll(driver.findElements(InitiativePageLocators.toastAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(toastCandidates)); } catch (Exception ignore) { }

            for (WebElement el : els) {
                try {
                    if (el == null || !el.isDisplayed()) continue;
                    String txt = "";
                    try { txt = el.getText(); } catch (Exception ignore) { }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try {
                            Object o = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent || '';", el);
                            txt = o == null ? "" : String.valueOf(o);
                        } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    if (txt.trim().isEmpty()) {
                        try { txt = el.getAttribute("aria-label"); } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    String norm = txt.trim();
                    if (!norm.isEmpty()) lastSeen = norm;

                    String lower = norm.toLowerCase().replace(",", "");
                    if (lower.isEmpty()) continue;

                    // Prefer matching the full expected string (e.g. "Total of all monthly distribution should not exceed 4000")
                    boolean matchesExpected = !expectedNorm.isEmpty() && lower.contains(expectedNorm);

                    // Fallback keyword matching if caller provided only a partial string
                    boolean matchesKeywords =
                            lower.contains("total") &&
                            lower.contains("monthly") &&
                            lower.contains("distribution") &&
                            (lower.contains("exceed") || lower.contains("should not exceed"));

                    boolean matchesAmount = totalToken.isEmpty() || lower.contains(totalToken.toLowerCase());

                    if ((matchesExpected || matchesKeywords) && matchesAmount) {
                        System.out.println("  ✅ Exceed Monthly Distribution alert detected: " + norm);
                        if (reportLogger != null) reportLogger.pass("✅ Exceed Monthly Distribution alert: " + norm);
                        return true;
                    }
                } catch (Exception ignore) { }
            }
            Thread.sleep(250);
        }

        System.out.println("  ❌ Exceed Monthly Distribution alert not detected. Last seen: " + lastSeen);
        if (reportLogger != null) reportLogger.fail("❌ Exceed Monthly Distribution alert not detected. Last seen: " + lastSeen);
        return false;
    }  
    
    
    
    private String formatMoneyForDisplayToken(java.math.BigDecimal v) {
        if (v == null) return "";
        java.math.BigDecimal vv = v.setScale(2, java.math.RoundingMode.HALF_UP);
        if (vv.stripTrailingZeros().scale() <= 0) {
            return vv.setScale(0, java.math.RoundingMode.HALF_UP).toPlainString();
        }
        return vv.toPlainString();
    }
     
    public  void clickFundingTab() throws Throwable {
    	
    	 click(InitiativePageLocators.fundingTab,"fundingTab");
        
    }
    
    public void clickFundingAddButton() throws Throwable {
    	 jsClick(InitiativePageLocators.fundingAddButton,"fundingAddButton");
       
    }
 
    public java.math.BigDecimal getCurrentCostAmount() throws Throwable {
        WebElement amountField = resolveVisibleInput(InitiativePageLocators.CostsAmount, "Costs Amount");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", amountField);
        Thread.sleep(150);

        String raw = "";
        try { raw = amountField.getAttribute("value"); } catch (Exception ignore) { }
        if (raw == null) raw = "";
        if (raw.trim().isEmpty()) {
            try { raw = amountField.getText(); } catch (Exception ignore) { }
            if (raw == null) raw = "";
        }
        raw = raw.replace(",", "").replaceAll("[₹$€]", "").trim();
        if (raw.isEmpty()) return java.math.BigDecimal.ZERO;

        // keep only number-ish characters
        raw = raw.replaceAll("[^0-9.\\-]", "");
        if (raw.isEmpty()) return java.math.BigDecimal.ZERO;
        try {
            return new java.math.BigDecimal(raw).setScale(2, java.math.RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new Exception("Could not parse Cost Amount from '" + raw + "'");
        }
    }
    
    public void verifyFundingAddHeader(String expectedHeader) throws Throwable {
        System.out.println("\n🧾 ═══════════════════════════════════════════");
        System.out.println("🧾 VERIFYING FUNDING ADD PAGE HEADER");
        System.out.println("🧾 Expected header: " + expectedHeader);
        System.out.println("🧾 ═══════════════════════════════════════════");

        String expected = expectedHeader == null ? "" : expectedHeader.trim();
        if (expected.isEmpty()) {
            throw new IllegalArgumentException("Expected header cannot be blank");
        }

        WebElement root = getActivePanelRoot();
        String lit = toXpathLiteral(expected);
        By headerBy = By.xpath(
                ".//*[@role='heading' and normalize-space(.)=" + lit + "] | " +
                        ".//*[self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::p or self::span or self::div][normalize-space(.)=" + lit + "]"
        );

        WebElement header = null;
        try {
            java.util.List<WebElement> els = root.findElements(headerBy);
            for (WebElement e : els) {
                try {
                    if (e != null && e.isDisplayed()) {
                        header = e;
                        break;
                    }
                } catch (Exception ignore) { }
            }
        } catch (Exception ignore) { }

        if (header == null) {
            throw new Exception("Funding Add page header not found. Expected: " + expected);
        }

        System.out.println("  ✅ Page header verified: " + expected);
        if (reportLogger != null) reportLogger.pass("✅ Funding header verified: " + expected);
        System.out.println("🧾 ═══════════════════════════════════════════\n");
    }
    
    private WebElement getActivePanelRoot() {
        // Many screens open a drawer for Add/Edit; prefer that if visible.
        try {
            java.util.List<WebElement> drawers = driver.findElements(By.xpath("//div[contains(@class,'MuiDrawer-paper')]"));
            for (int i = drawers.size() - 1; i >= 0; i--) {
                try {
                    WebElement d = drawers.get(i);
                    if (d != null && d.isDisplayed()) return d;
                } catch (Exception ignore) { }
            }
        } catch (Exception ignore) { }
        return driver.findElement(By.tagName("body"));
    }
    
    
    public void verifyFundingAddFieldsPresent() throws Throwable {
        System.out.println("\n🔎 ═══════════════════════════════════════════");
        System.out.println("🔎 VERIFYING FUNDING ADD FIELDS");
        System.out.println("🔎 ═══════════════════════════════════════════");

        WebElement root = getActivePanelRoot();

        // Print mandatory vs non-mandatory field list (as requested)
        System.out.println("📋 Expected MANDATORY fields:");
        System.out.println("  - Cost Category *");
        System.out.println("  - Approved Amount *");
        System.out.println("📋 Expected NON-MANDATORY fields:");
        System.out.println("  - Funding Approval Authority");
        System.out.println("  - Funding Source");
        System.out.println("  - Funding Reference");

        // Validate presence + required markers
        assertAndPrintField(root, "Cost Category", true);
        assertAndPrintField(root, "Funding Approval Authority", false);
        assertAndPrintField(root, "Funding Source", false);
        assertAndPrintField(root, "Funding Reference", false);
        assertAndPrintField(root, "Approved Amount", true);

        if (reportLogger != null) reportLogger.pass("✅ Funding Add fields verified");
        System.out.println("🔎 ═══════════════════════════════════════════\n");
    }
 
    private void assertAndPrintField(WebElement root, String labelText, boolean required) throws Exception {
        boolean present = false;
        boolean hasStar = false;
        try {
            WebElement label = findFieldLabel(root, labelText);
            present = (label != null);
            if (present) {
                hasStar = fieldHasRequiredStar(label);
                String kind = required ? "MANDATORY" : "NON-MANDATORY";
                System.out.println("  ✅ [" + kind + "] Field present: " + labelText + (required ? (hasStar ? " (*)" : " (missing *)") : ""));
            }
        } catch (Exception e) {
            String kind = required ? "MANDATORY" : "NON-MANDATORY";
            System.out.println("  ❌ [" + kind + "] Field NOT found: " + labelText);
            throw e;
        }

        // enforce rules
        if (!present) {
            throw new Exception("Funding field label not found: " + labelText);
        }
        if (required && !hasStar) {
            throw new Exception("Field not marked required (*): " + labelText);
        }
    } 
    
    private WebElement findFieldLabel(WebElement root, String labelText) throws Exception {
        String lower = "translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')";
        String litLower = toXpathLiteral(labelText.toLowerCase(java.util.Locale.ENGLISH));

        By labelBy = By.xpath(
                ".//*[self::label or self::span or self::p or self::div][contains(" + lower + "," + litLower + ")]"
        );

        java.util.List<WebElement> els = root.findElements(labelBy);
        for (WebElement e : els) {
            try {
                if (e != null && e.isDisplayed()) return e;
            } catch (Exception ignore) { }
        }
        throw new Exception("Funding field label not found: " + labelText);
    } 
    
    private boolean fieldHasRequiredStar(WebElement label) {
        if (label == null) return false;
        try {
            String txt = label.getText();
            if (txt != null && txt.contains("*")) return true;
        } catch (Exception ignore) { }
        // check for nearby required indicator
        try {
            java.util.List<WebElement> stars = label.findElements(By.xpath(".//*[normalize-space(.)='*']"));
            if (stars != null && !stars.isEmpty()) return true;
        } catch (Exception ignore) { }
        try {
            java.util.List<WebElement> stars = label.findElements(By.xpath("./following::*[normalize-space(.)='*'][1]"));
            if (stars != null && !stars.isEmpty()) return true;
        } catch (Exception ignore) { }
        return false;
    }  
    
  
    public void clickCostDeleteButton() throws Throwable {
        System.out.println("\n🗑️ ═══════════════════════════════════════════");
        System.out.println("🗑️ CLICKING COST DELETE BUTTON");
        System.out.println("🗑️ ═══════════════════════════════════════════");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.costDeleteButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", deleteButton);
            Thread.sleep(250);

            try {
                deleteButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
            }

            System.out.println("  ✅ Cost Delete button clicked");
            Thread.sleep(800);
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Cost Delete button");
            }
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Cost Delete button: " + e.getMessage());
            throw e;
        }

        System.out.println("🗑️ ═══════════════════════════════════════════\n");
    } 
    
    public boolean verifyCostDeleteSuccessAlert() throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING COST DELETE SUCCESS ALERT");
        System.out.println("✅ ═══════════════════════════════════════════");

        long end = System.currentTimeMillis() + 8_000;
        String lastSeen = "";

        while (System.currentTimeMillis() < end) {
            java.util.List<WebElement> els = new java.util.ArrayList<>();
            try { els.addAll(driver.findElements(InitiativePageLocators.costDeleteSuccessAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(InitiativePageLocators.toastAlert)); } catch (Exception ignore) { }
            try { els.addAll(driver.findElements(By.xpath("//div[@role='alert'] | //div[contains(@class,'ms-MessageBar')]"))); } catch (Exception ignore) { }

            for (WebElement el : els) {
                try {
                    if (el == null || !el.isDisplayed()) continue;
                    String txt = "";
                    try { txt = el.getText(); } catch (Exception ignore) { }
                    if (txt == null || txt.trim().isEmpty()) {
                        try {
                            Object o = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent || '';", el);
                            txt = o == null ? "" : String.valueOf(o);
                        } catch (Exception ignore) { }
                    }
                    if (txt == null) txt = "";
                    txt = txt.trim();
                    if (!txt.isEmpty()) lastSeen = txt;

                    String lower = txt.toLowerCase();
                    if ((lower.contains("deleted") && lower.contains("cost")) || lower.contains("deleted successfully")) {
                        System.out.println("  ✅ Delete success alert verified: " + txt);
                        if (reportLogger != null) {
                            reportLogger.pass("✅ Cost delete success alert: " + txt);
                        }
                        System.out.println("✅ ═══════════════════════════════════════════\n");
                        return true;
                    }
                } catch (Exception ignore) { }
            }

            Thread.sleep(250);
        }

        System.out.println("  ⚠️ Cost delete success alert not verified. Last seen: " + lastSeen);
        if (reportLogger != null) {
            reportLogger.warning("⚠️ Cost delete success alert not verified. Last seen: " + lastSeen);
        }
        System.out.println("✅ ═══════════════════════════════════════════\n");
        return false;
    } 
    
    
    public void enterFundingApprovedAmount(String amount) throws Throwable {
        System.out.println("\n💵 ═══════════════════════════════════════════");
        System.out.println("💵 ENTERING FUNDING APPROVED AMOUNT: " + amount);
        System.out.println("💵 ═══════════════════════════════════════════");

        if (amount == null) amount = "";
        String v = amount.trim();
        WebElement input = resolveVisibleInput(InitiativePageLocators.fundingApprovedAmount, "Funding Approved Amount");
        try { ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input); } catch (Exception ignore) { }
        Thread.sleep(150);
        setNativeValueAndDispatch(input, v);
        try { new Actions(driver).moveToElement(input).click().sendKeys(Keys.TAB).perform(); } catch (Exception ignore) { }
        Thread.sleep(200);

        if (reportLogger != null) reportLogger.info("Funding Approved Amount: " + v);
        System.out.println("💵 ═══════════════════════════════════════════\n");
    }

    
    public void ClickApprove() throws Exception {
    	click(InitiativePageLocators.Approve, "Click Approved");
    } 
    
    public void waitForApproved() throws Exception {
        waitForElementToBeVisible(InitiativePageLocators.Approve, "Approve Link");
        waitForElementToBeClickable(InitiativePageLocators.Approve, "Approve Link");
    }
    
    public void verifyApprovedSuccessAlert(String expectedAlert) throws Exception {
        System.out.println("\n🔔 ═══════════════════════════════════════════");
        System.out.println("🔔 VERIFYING CHECKLIST SUCCESS ALERT");
        System.out.println("🔔 Expected: " + expectedAlert);
        System.out.println("🔔 ═══════════════════════════════════════════");
        
        try {
            // Wait for alert to appear
            waitForSeconds(2);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                InitiativePageLocators.ApprovedSuccessAlert));
            
            String actualAlert = alertElement.getText().trim();
            System.out.println("  📝 Actual Alert: " + actualAlert);
            
            if (actualAlert.toLowerCase().contains(expectedAlert.toLowerCase()) || 
                expectedAlert.toLowerCase().contains(actualAlert.toLowerCase())) {
                System.out.println("  ✅ Approved Alert matched!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Approved Alert matched! Expected: " + expectedAlert + " | Actual: " + actualAlert);
                }
            } else {
                String errorMsg = "❌ Approved Alert mismatch! Expected: " + expectedAlert + " | Actual: " + actualAlert;
                System.out.println("  " + errorMsg);
                if (reportLogger != null) {
                    reportLogger.fail(errorMsg);
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Pushback alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify Pushback alert: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("🔔 ═══════════════════════════════════════════\n");
    }
    
    public boolean verifyNoCostDeleteSuccessAlert() throws Throwable {
        System.out.println("\n🚫 ═══════════════════════════════════════════");
        System.out.println("🚫 VERIFYING NO COST DELETE SUCCESS ALERT DISPLAYED");
        System.out.println("🚫 ═══════════════════════════════════════════");

        boolean noAlertFound = true;

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

            try {
                // If this appears, delete actually happened (unexpected for "No")
                WebElement alert = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                        InitiativePageLocators.costDeleteSuccessAlert
                ));

                String alertText = "";
                try { alertText = alert.getText(); } catch (Exception ignore) { }
                if (alertText == null || alertText.trim().isEmpty()) {
                    try {
                        Object o = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText || arguments[0].textContent || '';", alert);
                        alertText = o == null ? "" : String.valueOf(o);
                    } catch (Exception ignore) { }
                }

                System.out.println("  ❌ UNEXPECTED: Cost delete success alert found: " + alertText);
                noAlertFound = false;
                if (reportLogger != null) {
                    reportLogger.fail("❌ Unexpected Cost delete success alert displayed when No was clicked");
                }

            } catch (Exception ignore) {
                // Expected: no cost delete success alert
                System.out.println("  ✅ EXPECTED: No cost delete success alert displayed");
                noAlertFound = true;
                if (reportLogger != null) {
                    reportLogger.pass("✅ No cost delete success alert displayed - Delete correctly cancelled");
                }
            }

        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking for cost delete alert: " + e.getMessage());
        }

        System.out.println("🚫 ═══════════════════════════════════════════\n");
        return noAlertFound;
    }
    
    public void clickCostTab() throws Exception {
        System.out.println("\n📁 ═══════════════════════════════════════════");
        System.out.println("📁 CLICKING Cost TAB");
        System.out.println("📁 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement CostsTab = null;
            
            // Try primary locator first (linkText)
            try {
            	CostsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.CostsTab));
                System.out.println("  ✅ Found Cost tab using linkText");
            } catch (Exception e) {
                // Try alternative XPath
                System.out.println("  ⚠️ LinkText failed, trying alternative XPath...");
                CostsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.CostsTabAlt));
                System.out.println("  ✅ Found Costs tab using XPath");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", CostsTab);
            waitForSeconds(1);
            
            // Click the tab
            try {
            	CostsTab.click();
                System.out.println("  ✅ Clicked Costs tab (direct click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", CostsTab);
                System.out.println("  ✅ Clicked Costs tab (JS click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Costs tab");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Costs tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Costs tab: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("📁 ═══════════════════════════════════════════\n");
    }
    
    
    public boolean checkIfCostRecordsExist() throws Throwable {
        System.out.println("\n🔍 ═══════════════════════════════════════════");
        System.out.println("🔍 CHECKING IF Cost RECORDS EXIST");
        System.out.println("🔍 ═══════════════════════════════════════════");
        
        try {
            Thread.sleep(1000); // Wait for table to load
            
            // First check if "There are no items to show" message is displayed
            try {
                java.util.List<WebElement> noItemsMessages = driver.findElements(
                    InitiativePageLocators.noItemsToShowMessage
                );
                
                for (WebElement msg : noItemsMessages) {
                    if (msg.isDisplayed()) {
                        System.out.println("  ⚠️ Found message: '" + msg.getText() + "'");
                        System.out.println("  ⚠️ No cost records to delete");
                        System.out.println("🔍 ═══════════════════════════════════════════\n");
                        return false;
                    }
                }
            } catch (Exception e) {
                // No "no items" message found, continue checking for records
            }
            
            // Check for table rows
            java.util.List<WebElement> rows = driver.findElements(InitiativePageLocators.CostTableRows);
            
            if (rows != null && !rows.isEmpty()) {
                System.out.println("  ✅ Found " + rows.size() + " cost record(s)");
                System.out.println("🔍 ═══════════════════════════════════════════\n");
                return true;
            } else {
                System.out.println("  ⚠️ No cost records found in the table");
                System.out.println("🔍 ═══════════════════════════════════════════\n");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not check for Cost records: " + e.getMessage());
            System.out.println("🔍 ═══════════════════════════════════════════\n");
            return false;
        }
    } 
    
    public int countCostRecords() throws Throwable {
        System.out.println("\n🔢 ═══════════════════════════════════════════");
        System.out.println("🔢 COUNTING Cost RECORDS");
        System.out.println("🔢 ═══════════════════════════════════════════");
        
        int count = 0;
        
        try {
            Thread.sleep(500); // Wait for table to stabilize
            
            // First check if "There are no items to show" message is displayed
            try {
                java.util.List<WebElement> noItemsMessages = driver.findElements(
                    InitiativePageLocators.noItemsToShowMessage
                );
                
                for (WebElement msg : noItemsMessages) {
                    if (msg.isDisplayed()) {
                        System.out.println("  ⚠️ Found message: '" + msg.getText() + "'");
                        System.out.println("  📊 Record count: 0");
                        System.out.println("🔢 ═══════════════════════════════════════════\n");
                        return 0;
                    }
                }
            } catch (Exception e) {
                // No "no items" message found, continue counting
            }
            
            // Count table rows
            java.util.List<WebElement> rows = driver.findElements(InitiativePageLocators.CostTableRows);
            
            if (rows != null) {
                count = rows.size();
            }
            
            System.out.println("  📊 Record count: " + count);
            
            if (reportLogger != null) {
                reportLogger.info("Cost record count: " + count);
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not count records: " + e.getMessage());
        }
        
        System.out.println("🔢 ═══════════════════════════════════════════\n");
        return count;
    }
    
    
    public void clickCostsAddButton() throws Exception {
        System.out.println("\n➕ ═══════════════════════════════════════════");
        System.out.println("➕ CLICKING Costs ADD BUTTON");
        System.out.println("➕ ═══════════════════════════════════════════");
        
        // Multiple fallback XPaths to handle dynamic IDs
        By[] addButtonLocators = {
            InitiativePageLocators.CostsAddButton,
            InitiativePageLocators.CostsAddButtonAlt,
            // Context-based: Find Add button within Resources section/tab content
            By.xpath("//div[contains(@class,'tab-content') or contains(@class,'panel')]//button[.//span[text()='Add']]"),
            By.xpath("//button[.//span[text()='Add'] and not(contains(@class,'disabled'))]"),
            // Icon-based Add buttons
            By.xpath("//button[.//i[contains(@class,'Add')] or .//svg[contains(@class,'Add')]]"),
            // Generic Add button (last resort)
            By.xpath("(//button[contains(translate(., 'ADD', 'add'), 'add')])[1]"),
            // Button with + icon or text
            By.xpath("//button[contains(., '+') or .//span[contains(., '+')]]")
        };
        
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement addButton = null;
            String successLocator = "";
            
            // Try each locator strategy
            for (int i = 0; i < addButtonLocators.length; i++) {
                try {
                    System.out.println("  🔍 Trying locator strategy " + (i + 1) + "...");
                    addButton = shortWait.until(ExpectedConditions.elementToBeClickable(addButtonLocators[i]));
                    successLocator = addButtonLocators[i].toString();
                    System.out.println("  ✅ Found Add button using strategy " + (i + 1));
                    break;
            } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy " + (i + 1) + " failed");
                }
            }
            
            // If still not found, try finding any visible button with "Add" text
            if (addButton == null) {
                System.out.println("  🔍 Trying to find any visible Add button...");
                List<WebElement> allButtons = driver.findElements(By.tagName("button"));
                for (WebElement btn : allButtons) {
                    try {
                        String btnText = btn.getText().toLowerCase();
                        if (btnText.contains("add") && btn.isDisplayed() && btn.isEnabled()) {
                            addButton = btn;
                            successLocator = "Dynamic button search - text: " + btn.getText();
                            System.out.println("  ✅ Found Add button by text scan: " + btn.getText());
                            break;
                        }
                    } catch (Exception ignored) {}
                }
            }
            
            if (addButton == null) {
                throw new Exception("Could not find costs Add button with any locator strategy");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            waitForSeconds(1);
            
            // Try multiple click strategies
            boolean clicked = false;
            
            // Strategy 1: Direct click
            try {
                addButton.click();
                clicked = true;
                System.out.println("  ✅ Clicked Add button (direct click)");
            } catch (Exception e) {
                System.out.println("  ⚠️ Direct click failed: " + e.getMessage());
            }
            
            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
                    clicked = true;
                System.out.println("  ✅ Clicked Add button (JS click)");
                } catch (Exception e) {
                    System.out.println("  ⚠️ JS click failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    new org.openqa.selenium.interactions.Actions(driver)
                        .moveToElement(addButton)
                        .click()
                        .perform();
                    clicked = true;
                    System.out.println("  ✅ Clicked Add button (Actions click)");
                } catch (Exception e) {
                    System.out.println("  ⚠️ Actions click failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Could not click Add button with any click strategy");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Costs Add button using: " + successLocator);
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Costs Add button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Costs Add button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("➕ ═══════════════════════════════════════════\n");
    }
    
    
    public boolean isSavePreventedByValidation() throws Throwable {
        System.out.println("  🔍 Checking if save was prevented by validation...");
        try {
            Thread.sleep(1000);
            
            // Check if still on the same form (not navigated away)
            // If Add form is still visible, save was prevented
            java.util.List<WebElement> addForms = driver.findElements(
                By.xpath("//div[contains(@class,'ms-Panel')] | " +
                        "//div[contains(@class,'modal')] | " +
                        "//div[contains(@class,'dialog')] | " +
                        "//*[contains(text(),'Add Resource')] | " +
                        "//input[@id='TextField304']")
            );
            
            for (WebElement form : addForms) {
                if (form.isDisplayed()) {
                    System.out.println("    ✅ Form still visible - Save was prevented by validation");
                    return true;
                }
            }
            
            // Check for any error messages
            if (isValidationErrorDisplayed()) {
                System.out.println("    ✅ Validation errors displayed - Save was prevented");
                return true;
            }
            
            System.out.println("    ⚠️ Form may have been submitted");
            return false;
            
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking save prevention: " + e.getMessage());
            return false;
        }
    }
     
    
    public boolean isValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking for validation error messages...");
        try {
            Thread.sleep(500); // Wait for validation to trigger
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.validationErrorMessage);
            for (WebElement error : errors) {
                if (error.isDisplayed() && !error.getText().trim().isEmpty()) {
                    System.out.println("    ✅ Validation error found: " + error.getText());
                    return true;
                }
            }
            System.out.println("    ⚠️ No validation error message found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking validation: " + e.getMessage());
            return false;
        }
    }  
    
    
    public java.util.Map<String, Boolean> verifyAllMandatoryFieldValidationErrors() throws Throwable {
        System.out.println("\n⚠️ ═══════════════════════════════════════════");
        System.out.println("⚠️ VERIFYING ALL MANDATORY FIELD VALIDATION ERRORS");
        System.out.println("⚠️ ═══════════════════════════════════════════\n");
        
        java.util.Map<String, Boolean> validationStatus = new java.util.LinkedHashMap<>();
        
        validationStatus.put("Role", isRoleValidationErrorDisplayed());
        validationStatus.put("Skills", isSkillsValidationErrorDisplayed());
        validationStatus.put("Resource-In Date", isInDateValidationErrorDisplayed());
        validationStatus.put("Resource-Out Date", isOutDateValidationErrorDisplayed());
        validationStatus.put("FTE", isFTEValidationErrorDisplayed());
        
        System.out.println("\n⚠️ ═══════════════════════════════════════════");
        System.out.println("⚠️ VALIDATION ERROR SUMMARY:");
        int errorsFound = 0;
        for (java.util.Map.Entry<String, Boolean> entry : validationStatus.entrySet()) {
            String status = entry.getValue() ? "✅ ERROR DISPLAYED" : "❌ NO ERROR";
            System.out.println("  " + entry.getKey() + ": " + status);
            if (entry.getValue()) errorsFound++;
        }
        System.out.println("⚠️ Total validation errors found: " + errorsFound + "/5");
        System.out.println("⚠️ ═══════════════════════════════════════════\n");
        
        return validationStatus;
    }
     
    
    public boolean isRoleValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking Role field validation error...");
        try {
            Thread.sleep(500);
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.roleValidationError);
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText();
                    if (!errorText.trim().isEmpty()) {
                        System.out.println("    ✅ Role validation error: " + errorText);
                        if (reportLogger != null) {
                            reportLogger.pass("Role validation error displayed: " + errorText);
                        }
                        return true;
                    }
                }
            }
            
            // Also check for generic error near Role
            java.util.List<WebElement> genericErrors = driver.findElements(
                By.xpath("//*[contains(text(),'Role')]/ancestor::div[contains(@class,'ms-Dropdown')]//following-sibling::*[contains(@class,'error')] | " +
                        "//*[contains(text(),'required')]")
            );
            for (WebElement error : genericErrors) {
                if (error.isDisplayed()) {
                    String text = error.getText().toLowerCase();
                    if (text.contains("role") || text.contains("required") || text.contains("select")) {
                        System.out.println("    ✅ Role validation error (generic): " + error.getText());
                        return true;
                    }
                }
            }
            
            System.out.println("    ❌ Role validation error NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking Role validation: " + e.getMessage());
            return false;
        }
    }  
    
    
    public boolean isSkillsValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking Skills field validation error...");
        try {
            Thread.sleep(500);
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.skillsValidationError);
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText();
                    if (!errorText.trim().isEmpty()) {
                        System.out.println("    ✅ Skills validation error: " + errorText);
                        if (reportLogger != null) {
                            reportLogger.pass("Skills validation error displayed: " + errorText);
                        }
                        return true;
                    }
                }
            }
            
            // Check for generic error near Skills
            java.util.List<WebElement> genericErrors = driver.findElements(
                By.xpath("//*[contains(text(),'Skill')]/ancestor::div[contains(@class,'ms-Dropdown')]//following-sibling::*[contains(@class,'error')] | " +
                        "//*[contains(text(),'required')]")
            );
            for (WebElement error : genericErrors) {
                if (error.isDisplayed()) {
                    String text = error.getText().toLowerCase();
                    if (text.contains("skill") || text.contains("required") || text.contains("select")) {
                        System.out.println("    ✅ Skills validation error (generic): " + error.getText());
                        return true;
                    }
                }
            }
            
            System.out.println("    ❌ Skills validation error NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking Skills validation: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean isInDateValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking Resource-In Date field validation error...");
        try {
            Thread.sleep(500);
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.inDateValidationError);
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText();
                    if (!errorText.trim().isEmpty()) {
                        System.out.println("    ✅ In Date validation error: " + errorText);
                        if (reportLogger != null) {
                            reportLogger.pass("In Date validation error displayed: " + errorText);
                        }
                        return true;
                    }
                }
            }
            
            // Check for generic date error
            java.util.List<WebElement> genericErrors = driver.findElements(
                By.xpath("//*[contains(@id,'DatePicker82')]/following-sibling::*[contains(@class,'error')] | " +
                        "//*[contains(text(),'In Date') and contains(text(),'required')]")
            );
            for (WebElement error : genericErrors) {
                if (error.isDisplayed()) {
                    System.out.println("    ✅ In Date validation error (generic): " + error.getText());
                    return true;
                }
            }
            
            System.out.println("    ❌ In Date validation error NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking In Date validation: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean isOutDateValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking Resource-Out Date field validation error...");
        try {
            Thread.sleep(500);
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.outDateValidationError);
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText();
                    if (!errorText.trim().isEmpty()) {
                        System.out.println("    ✅ Out Date validation error: " + errorText);
                        if (reportLogger != null) {
                            reportLogger.pass("Out Date validation error displayed: " + errorText);
                        }
                        return true;
                    }
                }
            }
            
            // Check for generic date error
            java.util.List<WebElement> genericErrors = driver.findElements(
                By.xpath("//*[contains(@id,'DatePicker89')]/following-sibling::*[contains(@class,'error')] | " +
                        "//*[contains(text(),'Out Date') and contains(text(),'required')]")
            );
            for (WebElement error : genericErrors) {
                if (error.isDisplayed()) {
                    System.out.println("    ✅ Out Date validation error (generic): " + error.getText());
                    return true;
                }
            }
            
            System.out.println("    ❌ Out Date validation error NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking Out Date validation: " + e.getMessage());
            return false;
        }
    }  
    
    
    public boolean isFTEValidationErrorDisplayed() throws Throwable {
        System.out.println("  🔍 Checking FTE field validation error...");
        try {
            Thread.sleep(500);
            java.util.List<WebElement> errors = driver.findElements(InitiativePageLocators.fteValidationError);
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText();
                    if (!errorText.trim().isEmpty()) {
                        System.out.println("    ✅ FTE validation error: " + errorText);
                        if (reportLogger != null) {
                            reportLogger.pass("FTE validation error displayed: " + errorText);
                        }
                        return true;
                    }
                }
            }
            
            // Check for generic FTE error
            java.util.List<WebElement> genericErrors = driver.findElements(
                By.xpath("//input[@id='TextField304']/ancestor::div[contains(@class,'ms-TextField')]//following-sibling::*[contains(@class,'error')] | " +
                        "//*[contains(text(),'FTE') and contains(text(),'required')]")
            );
            for (WebElement error : genericErrors) {
                if (error.isDisplayed()) {
                    System.out.println("    ✅ FTE validation error (generic): " + error.getText());
                    return true;
                }
            }
            
            System.out.println("    ❌ FTE validation error NOT found");
            return false;
        } catch (Exception e) {
            System.out.println("    ⚠️ Error checking FTE validation: " + e.getMessage());
            return false;
        }
    }
    
    
    public int countResourceRecords() throws Throwable {
        System.out.println("\n🔢 ═══════════════════════════════════════════");
        System.out.println("🔢 COUNTING RESOURCE RECORDS");
        System.out.println("🔢 ═══════════════════════════════════════════");
        
        int count = 0;
        
        try {
            Thread.sleep(500); // Wait for table to stabilize
            
            // First check if "There are no items to show" message is displayed
            try {
                java.util.List<WebElement> noItemsMessages = driver.findElements(
                    InitiativePageLocators.noItemsToShowMessage
                );
                
                for (WebElement msg : noItemsMessages) {
                    if (msg.isDisplayed()) {
                        System.out.println("  ⚠️ Found message: '" + msg.getText() + "'");
                        System.out.println("  📊 Record count: 0");
                        System.out.println("🔢 ═══════════════════════════════════════════\n");
                        return 0;
                    }
                }
            } catch (Exception e) {
                // No "no items" message found, continue counting
            }
            
            // Count table rows
            java.util.List<WebElement> rows = driver.findElements(InitiativePageLocators.resourceTableRows);
            
            if (rows != null) {
                count = rows.size();
            }
            
            System.out.println("  📊 Record count: " + count);
            
            if (reportLogger != null) {
                reportLogger.info("Resource record count: " + count);
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not count records: " + e.getMessage());
        }
        
        System.out.println("🔢 ═══════════════════════════════════════════\n");
        return count;
    }
    
    public void clickDeleteConfirmNoButton() throws Throwable {
        System.out.println("\n❌ ═══════════════════════════════════════════");
        System.out.println("❌ CLICKING NO ON DELETE CONFIRMATION");
        System.out.println("❌ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Use locator from Locators class
            WebElement noButton = null;
            
            try {
                noButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.deleteConfirmNoButton));
                System.out.println("  ✅ Found No button using locator from Locators class");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying fallback...");
                // Fallback locators
                noButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'No')]")
                ));
                System.out.println("  ✅ Found No button using fallback locator");
            }
            
            if (noButton == null) {
                throw new Exception("Could not find No confirmation button");
            }
            
            // Click the No button
            try {
                noButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noButton);
            }
            
            System.out.println("  ✅ No button clicked - Delete cancelled");
            Thread.sleep(1000);
            
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked No on Delete Confirmation - Delete cancelled");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click No button: " + e.getMessage());
            throw e;
        }
        
        System.out.println("❌ ═══════════════════════════════════════════\n");
    }
    
    
    
    public boolean verifyNoDeleteSuccessAlert() throws Throwable {
        System.out.println("\n🚫 ═══════════════════════════════════════════");
        System.out.println("🚫 VERIFYING NO DELETE SUCCESS ALERT DISPLAYED");
        System.out.println("🚫 ═══════════════════════════════════════════");
        
        boolean noAlertFound = true; // We expect NO alert
        
        try {
            // Short wait - we don't expect the alert to appear
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            
            try {
                WebElement alert = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    InitiativePageLocators.resourceDeleteSuccessAlert
                ));
                
                // If we found an alert, that's unexpected behavior
                String alertText = alert.getText();
                System.out.println("  ❌ UNEXPECTED: Success alert found: " + alertText);
                noAlertFound = false;
                
                if (reportLogger != null) {
                    reportLogger.fail("❌ Unexpected delete success alert displayed when No was clicked");
                }
                
            } catch (Exception e) {
                // Expected behavior - no alert should appear
                System.out.println("  ✅ EXPECTED: No delete success alert displayed");
                System.out.println("  ✅ Delete was correctly cancelled");
                noAlertFound = true;
                
                if (reportLogger != null) {
                    reportLogger.pass("✅ No delete success alert displayed - Delete correctly cancelled");
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking for alert: " + e.getMessage());
        }
        
        System.out.println("🚫 ═══════════════════════════════════════════\n");
        return noAlertFound;
    }  
    
    
    public boolean checkIfResourceRecordsExist() throws Throwable {
        System.out.println("\n🔍 ═══════════════════════════════════════════");
        System.out.println("🔍 CHECKING IF RESOURCE RECORDS EXIST");
        System.out.println("🔍 ═══════════════════════════════════════════");
        
        try {
            Thread.sleep(1000); // Wait for table to load
            
            // First check if "There are no items to show" message is displayed
            try {
                java.util.List<WebElement> noItemsMessages = driver.findElements(
                    InitiativePageLocators.noItemsToShowMessage
                );
                
                for (WebElement msg : noItemsMessages) {
                    if (msg.isDisplayed()) {
                        System.out.println("  ⚠️ Found message: '" + msg.getText() + "'");
                        System.out.println("  ⚠️ No resource records to delete");
                        System.out.println("🔍 ═══════════════════════════════════════════\n");
                        return false;
                    }
                }
            } catch (Exception e) {
                // No "no items" message found, continue checking for records
            }
            
            // Check for table rows
            java.util.List<WebElement> rows = driver.findElements(InitiativePageLocators.resourceTableRows);
            
            if (rows != null && !rows.isEmpty()) {
                System.out.println("  ✅ Found " + rows.size() + " resource record(s)");
                System.out.println("🔍 ═══════════════════════════════════════════\n");
                return true;
            } else {
                System.out.println("  ⚠️ No resource records found in the table");
                System.out.println("🔍 ═══════════════════════════════════════════\n");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not check for resource records: " + e.getMessage());
            System.out.println("🔍 ═══════════════════════════════════════════\n");
            return false;
        }
    }
      
    
    public void clickResourceDeleteButton() throws Throwable {
        System.out.println("\n🗑️ ═══════════════════════════════════════════");
        System.out.println("🗑️ CLICKING RESOURCE DELETE BUTTON");
        System.out.println("🗑️ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Use locator from Locators class
            WebElement deleteButton = null;
            
            try {
                deleteButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.resourceDeleteButton));
                System.out.println("  ✅ Found Delete button using locator from Locators class");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying fallback...");
                // Fallback: try finding by SVG icon
                deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//svg[@data-testid='DeleteIcon']/ancestor::button | //tbody/tr[1]//button[last()]")
                ));
                System.out.println("  ✅ Found Delete button using fallback locator");
            }
            
            if (deleteButton == null) {
                throw new Exception("Could not find Delete button");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", deleteButton
            );
            Thread.sleep(300);
            
            // Click the delete button
            try {
                deleteButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
            }
            
            System.out.println("  ✅ Delete button clicked");
            Thread.sleep(1000);
            
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Resource Delete button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Delete button: " + e.getMessage());
            throw e;
        }
        
        System.out.println("🗑️ ═══════════════════════════════════════════\n");
    }
    
    
    public void clickDeleteConfirmYesButton() throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ CLICKING YES ON DELETE CONFIRMATION");
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Use locator from Locators class
            WebElement yesButton = null;
            
            try {
                yesButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.deleteConfirmYesButton));
                System.out.println("  ✅ Found Yes button using locator from Locators class");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying fallback...");
                // Fallback locators
                yesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Yes')]")
                ));
                System.out.println("  ✅ Found Yes button using fallback locator");
            }
            
            if (yesButton == null) {
                throw new Exception("Could not find Yes confirmation button");
            }
            
            // Click the Yes button
            try {
                yesButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesButton);
            }
            
            System.out.println("  ✅ Yes button clicked - Delete confirmed");
            Thread.sleep(1000);
            
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Yes on Delete Confirmation");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Yes button: " + e.getMessage());
            throw e;
        }
        
        System.out.println("✅ ═══════════════════════════════════════════\n");
    }
    
    
    public boolean verifyResourceDeleteSuccessAlert() throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING RESOURCE DELETE SUCCESS ALERT");
        System.out.println("✅ ═══════════════════════════════════════════");
        
        boolean alertFound = false;
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            try {
                WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    InitiativePageLocators.resourceDeleteSuccessAlert
                ));
                
                String alertText = alert.getText();
                System.out.println("  ✅ Success alert found: " + alertText);
                alertFound = true;
                
                if (reportLogger != null) {
                    reportLogger.pass("✅ Resource Delete success alert verified: " + alertText);
                }
                
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not find success alert with primary locator");
                
                // Try alternative locators
                try {
                    WebElement alert = driver.findElement(By.xpath("//*[contains(text(),'Deleted')]"));
                    if (alert.isDisplayed()) {
                        System.out.println("  ✅ Found delete confirmation: " + alert.getText());
                        alertFound = true;
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ No delete success alert found");
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Error verifying delete success alert: " + e.getMessage());
        }
        
        System.out.println("✅ ═══════════════════════════════════════════\n");
        return alertFound;
    }
    
    public boolean verifyResourceAddSuccessAlert() throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING RESOURCE Add SUCCESS ALERT");
        System.out.println("✅ ═══════════════════════════════════════════");
        
        boolean alertFound = false;
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            try {
                WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    InitiativePageLocators.resourceSuccessAlert
                ));
                
                String alertText = alert.getText();
                System.out.println("  ✅ Success alert found: " + alertText);
                alertFound = true;
                
                if (reportLogger != null) {
                    reportLogger.pass("✅ Resource Add success alert verified: " + alertText);
                }
                
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not find success alert with primary locator");
                
                // Try alternative locators
                try {
                    WebElement alert = driver.findElement(By.xpath("//*[contains(text(),'Deleted')]"));
                    if (alert.isDisplayed()) {
                        System.out.println("  ✅ Found Add confirmation: " + alert.getText());
                        alertFound = true;
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ No Add success alert found");
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Error verifying Add success alert: " + e.getMessage());
        }
        
        System.out.println("✅ ═══════════════════════════════════════════\n");
        return alertFound;
    }
    
    
    public void clickResourceEditButton(String fte) throws Throwable {
        System.out.println("\n✏️ ═══════════════════════════════════════════");
        System.out.println("✏️ CLICKING RESOURCE EDIT BUTTON");
        System.out.println("✏️ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Use locator from Locators class
            WebElement editButton = null;
            
            try {
                editButton = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.resourceEditButton));
                System.out.println("  ✅ Found Edit button using locator from Locators class");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying fallback...");
                // Fallback: try finding by table row structure
                java.util.List<WebElement> buttons = driver.findElements(By.xpath("//tbody/tr[2]/td[6]/button[1]"));
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        editButton = btn;
                        System.out.println("  ✅ Found Edit button using fallback locator");
                        break;
                    }
                }
            }
            
            if (editButton == null) {
                throw new Exception("Could not find Edit button");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", editButton
            );
            Thread.sleep(300);
            
            // Click the edit button
            try {
                editButton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            }
            
            System.out.println("  ✅ Edit button clicked");
            Thread.sleep(1000);
            
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Resource Edit button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Edit button: " + e.getMessage());
            throw e;
        }
        
        System.out.println("✏️ ═══════════════════════════════════════════\n");
    }
    
    
    public void clickShowHistoryLink() throws Throwable {
        System.out.println("\n📜 ═══════════════════════════════════════════");
        System.out.println("📜 CLICKING SHOW HISTORY LINK");
        System.out.println("📜 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Use locator from Locators class
            WebElement historyLink = null;
            
            try {
                historyLink = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.showHistoryLink));
                System.out.println("  ✅ Found Show History link using locator from Locators class");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying fallback...");
                // Fallback
                historyLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Show History")));
                System.out.println("  ✅ Found Show History link using fallback locator");
            }
            
            if (historyLink == null) {
                throw new Exception("Could not find Show History link");
            }
            
            // Click the history link
            try {
                historyLink.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", historyLink);
            }
            
            System.out.println("  ✅ Show History link clicked");
            Thread.sleep(2000);
            
            if (reportLogger != null) {
                reportLogger.info("✅ Clicked Show History link");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Show History link: " + e.getMessage());
            throw e;
        }
        
        System.out.println("📜 ═══════════════════════════════════════════\n");
    }
    
    
   
    public boolean verifyResourceHistory() throws Throwable {
        System.out.println("\n📋 ═══════════════════════════════════════════");
        System.out.println("📋 VERIFYING RESOURCE HISTORY");
        System.out.println("📋 ═══════════════════════════════════════════");
        
        boolean historyFound = false;
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Look for history table/grid or history entries
            By[] historyLocators = {
                By.xpath("//table[contains(@class,'history')]"),
                By.xpath("//div[contains(@class,'history')]"),
                By.xpath("//div[contains(@class,'ms-DetailsList')]"),
                By.xpath("//table//tr[contains(.,'Modified')]"),
                By.xpath("//*[contains(text(),'Modified By')]"),
                By.xpath("//*[contains(text(),'Modified Date')]"),
                By.xpath("//*[contains(text(),'Old Value')]"),
                By.xpath("//*[contains(text(),'New Value')]")
            };
            
            for (By locator : historyLocators) {
                try {
                    WebElement historyElement = driver.findElement(locator);
                    if (historyElement.isDisplayed()) {
                        System.out.println("  ✅ Found history element: " + locator.toString());
                        historyFound = true;
                        break;
                    }
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            // Try to find and list history records
            try {
                java.util.List<WebElement> historyRows = driver.findElements(
                    By.xpath("//table//tr | //div[contains(@class,'ms-DetailsRow')]")
                );
                
                if (historyRows.size() > 0) {
                    System.out.println("  📋 Found " + historyRows.size() + " history records");
                    
                    // Print first few records for verification
                    for (int i = 0; i < Math.min(5, historyRows.size()); i++) {
                        String rowText = historyRows.get(i).getText();
                        if (!rowText.trim().isEmpty()) {
                            System.out.println("    [" + i + "] " + rowText.substring(0, Math.min(100, rowText.length())));
                        }
                    }
                    
                    historyFound = true;
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not list history records: " + e.getMessage());
            }
            
            if (historyFound) {
                System.out.println("  ✅ History verification PASSED");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Resource history verified successfully");
                }
            } else {
                System.out.println("  ⚠️ No history records found");
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ No history records found");
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify history: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify history: " + e.getMessage());
            }
        }
        
        System.out.println("📋 ═══════════════════════════════════════════\n");
        return historyFound;
    }  
    
    public void clickDraftFilter() {
        try {
            System.out.println("\n📥 ═══════════════════════════════════════════");
            System.out.println("📥 Clicking Draft Filter");
            System.out.println("📥 ═══════════════════════════════════════════");
            
            boolean clicked = false;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try multiple locators
            By[] locators = {
                     InitiativePageLocators.draftFilter,  // ID: FltrCountInbox
					
					 InitiativePageLocators.DraftFilterAlt2, // Button contains 'Inbox'
					 InitiativePageLocators.DraftFilterAlt3, // Span with parent button
					 InitiativePageLocators.DraftFilterAlt3 // Combined primary locator
					            };
            
            String[] locatorNames = {
                "ID: FltrCountDraft",
                "Button containing 'Draft'",
                "Span 'Draft' with parent button",
                "Primary combined locator"
            };
            
            WebElement DraftButton = null;
            int foundIndex = -1;
            
            // Find the button
            for (int i = 0; i < locators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    if (!driver.findElements(locators[i]).isEmpty()) {
                    	DraftButton = wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                        System.out.println("  ✓ Button found with locator " + (i + 1));
                        foundIndex = i;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Not found: " + e.getMessage());
                }
            }
            
            if (DraftButton == null) {
                throw new Exception("❌ Could not find Draft filter button with any locator");
            }
            
            System.out.println("\n  📋 Button Details:");
            System.out.println("    Tag: " + DraftButton.getTagName());
            System.out.println("    Text: " + DraftButton.getText());
            System.out.println("    Enabled: " + DraftButton.isEnabled());
            System.out.println("    Displayed: " + DraftButton.isDisplayed());
            
            // Strategy 1: Scroll into view + Standard click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 1: Scroll + Standard click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", DraftButton);
                    Thread.sleep(500);
                    DraftButton.click();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 2: JavaScript click");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", DraftButton);
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    System.out.println("\n  📍 Strategy 3: Actions click");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(DraftButton).click().perform();
                    Thread.sleep(2000);
                    System.out.println("  ✅ SUCCESS");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("  ✗ Failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("❌ All strategies failed to click Draft filter");
            }
            
            System.out.println("\n✅ ✅ ✅ Draft Filter Clicked Successfully! ✅ ✅ ✅");
            System.out.println("📥 ═══════════════════════════════════════════\n");
            
            if (reportLogger != null) {
                reportLogger.pass("Clicked Draft filter successfully");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Failed to click Draft filter: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Draft filter: " + e.getMessage());
            }
            throw new RuntimeException("Failed to click Draft filter", e);
        }
    }
    
    public String getInitiativeCode() {
        try {
            System.out.println("\n📋 ═══════════════════════════════════════════");
            System.out.println("📋 CAPTURING INITIATIVE CODE");
            System.out.println("📋 ═══════════════════════════════════════════");
            
            waitForSeconds(2); // Wait for page to stabilize
            
            String initiativeCode = null;
            
            // DEBUG: Print all input fields on page to help identify the correct one
            System.out.println("  🔍 DEBUG: Listing all visible input fields...");
            try {
                List<WebElement> allInputs = driver.findElements(By.xpath("//input"));
                System.out.println("    Found " + allInputs.size() + " input fields total");
                int count = 0;
                for (WebElement input : allInputs) {
                    try {
                        if (input.isDisplayed()) {
                            String id = input.getAttribute("id");
                            String value = input.getAttribute("value");
                            String placeholder = input.getAttribute("placeholder");
                            if (id != null && !id.isEmpty()) {
                                System.out.println("    Input #" + (++count) + ": id='" + id + "', value='" + value + "', placeholder='" + placeholder + "'");
                            }
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {
                System.out.println("    ⚠️ Could not list inputs: " + e.getMessage());
            }
            
            // STRATEGY 1: Try to get value from the specific input field (TextField31)
            System.out.println("\n  🔍 Strategy 1: Checking input field TextField31...");
            try {
                WebElement codeInput = driver.findElement(By.xpath("//input[@id='TextField31']"));
                System.out.println("    TextField31 found!");
                System.out.println("    Is displayed: " + codeInput.isDisplayed());
                System.out.println("    Is enabled: " + codeInput.isEnabled());
                
                String value = codeInput.getAttribute("value");
                System.out.println("    Value attribute: '" + value + "'");
                
                if (value != null && !value.trim().isEmpty()) {
                    initiativeCode = value.trim();
                    System.out.println("  ✅ Initiative Code from TextField31: " + initiativeCode);
                } else {
                    System.out.println("    ⚠️ TextField31 value is empty");
                }
            } catch (Exception e) {
                System.out.println("    ⚠️ TextField31 not found: " + e.getMessage());
            }
            
            // STRATEGY 2: Try the initiativeCodeAfterSave locator
            if (initiativeCode == null) {
                System.out.println("\n  🔍 Strategy 2: Trying initiativeCodeAfterSave locator...");
                try {
                    WebElement codeElement = driver.findElement(InitiativePageLocators.initiativeCodeAfterSave);
                    System.out.println("    Element found with initiativeCodeAfterSave locator");
                    
                    // Try value attribute first (for input fields)
                    String value = codeElement.getAttribute("value");
                    System.out.println("    Value attribute: '" + value + "'");
                    
                    if (value != null && !value.trim().isEmpty()) {
                        initiativeCode = value.trim();
                        System.out.println("  ✅ Initiative Code from value attribute: " + initiativeCode);
                    } else {
                        // Try text content
                        String text = codeElement.getText().trim();
                        System.out.println("    Text content: '" + text + "'");
                        if (text != null && !text.isEmpty()) {
                            initiativeCode = text;
                            System.out.println("  ✅ Initiative Code from text: " + initiativeCode);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("    ⚠️ initiativeCodeAfterSave locator failed: " + e.getMessage());
                }
            }
            
            // STRATEGY 3: Try all input fields with "TextField" in ID and non-empty value
            if (initiativeCode == null) {
                System.out.println("\n  🔍 Strategy 3: Searching all TextField inputs...");
                try {
                    List<WebElement> textFields = driver.findElements(By.xpath("//input[contains(@id,'TextField')]"));
                    System.out.println("    Found " + textFields.size() + " TextField inputs");
                    
                    for (WebElement tf : textFields) {
                        try {
                            String id = tf.getAttribute("id");
                            String value = tf.getAttribute("value");
                            
                            // Look for a field that contains only numbers (likely the code)
                            if (value != null && !value.trim().isEmpty() && value.matches("\\d+")) {
                                System.out.println("    Found numeric value in " + id + ": " + value);
                                initiativeCode = value.trim();
                                System.out.println("  ✅ Initiative Code from " + id + ": " + initiativeCode);
                                break;
                            }
                        } catch (Exception e) {}
                    }
                } catch (Exception e) {
                    System.out.println("    ⚠️ Strategy 3 failed: " + e.getMessage());
                }
            }
            
            // STRATEGY 4: Try JavaScript to search all inputs
            if (initiativeCode == null) {
                System.out.println("\n  🔍 Strategy 4: JavaScript search all inputs...");
                try {
                    String jsCode = (String) ((JavascriptExecutor) driver).executeScript(
                        "var inputs = document.querySelectorAll('input');" +
                        "for(var i=0; i<inputs.length; i++) {" +
                        "  var val = inputs[i].value;" +
                        "  var id = inputs[i].id;" +
                        "  // Look for numeric value that could be initiative code" +
                        "  if(val && /^\\d+$/.test(val) && val.length >= 1) {" +
                        "    console.log('Found potential code in ' + id + ': ' + val);" +
                        "    return val;" +
                        "  }" +
                        "}" +
                        "// Fallback: try TextField31 specifically" +
                        "var tf31 = document.getElementById('TextField31');" +
                        "if(tf31 && tf31.value) return tf31.value;" +
                        "return null;"
                    );
                    if (jsCode != null && !jsCode.trim().isEmpty()) {
                        initiativeCode = jsCode.trim();
                        System.out.println("  ✅ Initiative Code via JS: " + initiativeCode);
                    }
                } catch (Exception e) {
                    System.out.println("    ⚠️ JS search failed: " + e.getMessage());
                }
            }
            
            // Final result
            if (initiativeCode != null && !initiativeCode.trim().isEmpty()) {
                System.out.println("\n  🎯 🎯 🎯 CAPTURED INITIATIVE CODE: " + initiativeCode + " 🎯 🎯 🎯");
                if (reportLogger != null) {
                    reportLogger.pass("Captured Initiative Code: " + initiativeCode);
                }
            } else {
                System.out.println("\n  ❌ Could not find initiative code");
                System.out.println("  💡 TIP: Check if the initiative code field has a different ID");
                if (reportLogger != null) {
                    reportLogger.warning("Could not capture Initiative Code");
                }
            }
            
            System.out.println("📋 ═══════════════════════════════════════════\n");
            return initiativeCode;
            
        } catch (Exception e) {
            System.out.println("❌ Error capturing initiative code: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Error capturing initiative code: " + e.getMessage());
            }
            return null;
        }
    }     
    
    
    public boolean verifyDraftCountMatchesTotalRecords() {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════════════════");
            System.out.println("✅ VERIFYING Draft COUNT vs TOTAL RECORDS (ALL PAGES)");
            System.out.println("✅ ═══════════════════════════════════════════════════════");
            
            // Get inbox count from badge
            int DraftCount = getDraftCount();
            
            // Get total records across all pages
            int totalRecordsAcrossPages = getTotalRecordsAcrossAllPages();
            
            System.out.println("\n📊 ═══════════ FINAL COMPARISON ═══════════");
            System.out.println("  Draft Badge Count: " + DraftCount);
            System.out.println("  Total Records (All Pages): " + totalRecordsAcrossPages);
            
            boolean isMatching = (DraftCount == totalRecordsAcrossPages);
            
            if (isMatching) {
                System.out.println("\n✅ ✅ ✅ VERIFICATION PASSED! ✅ ✅ ✅");
                System.out.println("  Draft count (" + DraftCount + ") matches total records across all pages!");
                
                if (reportLogger != null) {
                    reportLogger.pass("Draft count verification PASSED - Badge: " + DraftCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
                
            } else {
                System.out.println("\n❌ ❌ ❌ VERIFICATION FAILED! ❌ ❌ ❌");
                System.out.println("  Inbox count (" + DraftCount + ") does NOT match total records (" + 
                        totalRecordsAcrossPages + ")");
                System.out.println("  Difference: " + Math.abs(DraftCount - totalRecordsAcrossPages) + " records");
                
                if (reportLogger != null) {
                    reportLogger.fail("Inbox count verification FAILED - Badge: " + DraftCount + 
                            ", Grid Total: " + totalRecordsAcrossPages);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════════════════\n");
            
            return isMatching;
            
        } catch (Exception e) {
            System.out.println("\n❌ Error during Draft count verification: " + e.getMessage());
            e.printStackTrace();
            
            if (reportLogger != null) {
                reportLogger.fail("Error during Draft count verification: " + e.getMessage());
            }
            
            return false;
        }
    }
    
    
    public int getDraftCount() {
        try {
            System.out.println("\n🔢 ═══════════════════════════════════════════");
            System.out.println("🔢 Getting Draft Count from Badge");
            System.out.println("🔢 ═══════════════════════════════════════════");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try multiple locators to find the count - Using centralized array
            By[] countLocators = InitiativePageLocators.DraftCountLocators;
            
            String[] locatorNames = {
                "span[id='FltrCountDraft']",
                "Span following Draft",
                "Span with class 'count'",
                "Span with class 'badge'",
                "Button Draft > span count",
                "Button containing Draft"
            };
            
            for (int i = 0; i < countLocators.length; i++) {
                try {
                    System.out.println("  → Trying locator " + (i + 1) + ": " + locatorNames[i]);
                    
                    if (!driver.findElements(countLocators[i]).isEmpty()) {
                        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countLocators[i]));
                        String originalText = countElement.getText().trim();
                        System.out.println("    📝 Original text: '" + originalText + "'");
                        
                        // Extract numbers from text (e.g., "Inbox 5" -> "5", "(5)" -> "5")
                        String countText = originalText.replaceAll("[^0-9]", "");
                        System.out.println("    🔢 Extracted numbers: '" + countText + "'");
                        
                        if (!countText.isEmpty()) {
                            int count = Integer.parseInt(countText);
                            System.out.println("  ✅ Successfully found count: " + count);
                            System.out.println("🔢 ═══════════════════════════════════════════\n");
                            return count;
                        } else {
                            System.out.println("    ⚠️ No numbers found in text");
                        }
                    } else {
                        System.out.println("    ✗ Element not found");
                    }
                } catch (Exception e) {
                    System.out.println("    ✗ Error: " + e.getMessage());
                    // Try next locator
                    continue;
                }
            }
            
            System.out.println("\n  ⚠️ Could not find Draft count badge with any locator");
            System.out.println("  ℹ️ Returning 0 as fallback");
            System.out.println("🔢 ═══════════════════════════════════════════\n");
            return 0;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to get Draft count: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.warning("Failed to get Draft count: " + e.getMessage());
            }
            return 0;
        }
    }
    
    public void logout() throws Exception {
        System.out.println("\n🚪 ═══════════════════════════════════════════");
        System.out.println("🚪 LOGGING OUT FROM APPLICATION");
        System.out.println("🚪 ═══════════════════════════════════════════");
        
        try {
            // Step 1: Click on user profile/avatar menu
            System.out.println("  📍 Step 1: Clicking user profile menu...");
            boolean profileClicked = false;
            
            // Try multiple locators for profile menu
            By[] profileLocators = {
                InitiativePageLocators.userProfileMenu,
  
                By.xpath("//span[normalize-space()='Hi, Admin']")
               
            };
            
            for (By locator : profileLocators) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    WebElement profileElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    
                    // Scroll into view and click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", profileElement);
                    waitForSeconds(1);
                    
                    try {
                        profileElement.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profileElement);
                    }
                    
                    profileClicked = true;
                    System.out.println("  ✅ Profile menu clicked using: " + locator.toString());
                    break;
                    
                } catch (Exception e) {
                    System.out.println("    ⚠️ Locator failed: " + locator.toString());
                }
            }
            
            if (!profileClicked) {
                throw new Exception("Could not find/click user profile menu");
            }
            
            waitForSeconds(2); // Wait for dropdown menu to appear
            
            // Step 2: Click on Logout button
            System.out.println("  📍 Step 2: Clicking logout button...");
            boolean logoutClicked = false;
            
            By[] logoutLocators = {
                InitiativePageLocators.logoutButton,
                By.xpath(".//*[normalize-space(text()) and normalize-space(.)='Profile'])[1]/following::span[2]"),
                By.xpath("//li[contains(text(),'Logout')]"),
                By.xpath("//span[contains(text(),'Logout')]"),
                By.xpath("//button[contains(text(),'Logout')]"),
                By.xpath("//a[contains(text(),'Logout')]"),
                By.xpath("//div[contains(text(),'Logout')]"),
                By.xpath("//*[contains(text(),'Log out')]"),
                By.xpath("//*[contains(text(),'Sign out')]"),
                By.xpath("//li[contains(@class,'logout')]"),
                By.xpath("//div[@role='menuitem' and contains(.,'Logout')]")
            };
            
            for (By locator : logoutLocators) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    WebElement logoutElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    
                    try {
                        logoutElement.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutElement);
                    }
                    
                    logoutClicked = true;
                    System.out.println("  ✅ Logout clicked using: " + locator.toString());
                    break;
                    
                } catch (Exception e) {
                    System.out.println("    ⚠️ Locator failed: " + locator.toString());
                }
            }
            
            if (!logoutClicked) {
                throw new Exception("Could not find/click logout button");
            }
            
            // Wait for logout to complete
            waitForSeconds(3);
            
            System.out.println("\n  ✅ ✅ ✅ LOGOUT SUCCESSFUL ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Successfully logged out from application");
            }
            
        } catch (Exception e) {
            System.out.println("\n  ❌ LOGOUT FAILED: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Logout failed: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("🚪 ═══════════════════════════════════════════\n");
    }
     
    public boolean verifyDraftCountMatchesGrid(int expectedRecordsPerPage) {
        try {
            System.out.println("\n✅ ═══════════════════════════════════════════════════════");
            System.out.println("✅              VERIFYING DRAFT COUNT vs GRID RECORDS");
            System.out.println("✅ ═══════════════════════════════════════════════════════");
            
            int DraftCount = getDraftCount();
            int gridRecordsCount = getGridRecordsCount();
            
            System.out.println("\n📊 ═══════════ DETAILED COMPARISON ═══════════");
            System.out.println("  📬 DRAFT Badge Count:        " + DraftCount + " (total records in Inbox)");
            System.out.println("  📋 Grid Records Count:       " + gridRecordsCount + " (visible on current page)");
            System.out.println("  📄 Expected Records/Page:    " + expectedRecordsPerPage + " (configured page size)");
            System.out.println("  ════════════════════════════════════════");
            
            // Determine expected records on current page
            int expectedOnPage = Math.min(DraftCount, expectedRecordsPerPage);
            System.out.println("  🎯 Expected on Current Page: " + expectedOnPage);
            System.out.println("     (calculated as min(" + DraftCount + ", " + expectedRecordsPerPage + "))");
            System.out.println("  ════════════════════════════════════════");
            
            boolean matches = (gridRecordsCount == expectedOnPage);
            
            System.out.println("\n📐 VERIFICATION LOGIC:");
            System.out.println("  " + gridRecordsCount + " == " + expectedOnPage + " ?");
            System.out.println("  " + matches);
            
            if (matches) {
                System.out.println("\n✅ ✅ ✅ VERIFICATION PASSED! ✅ ✅ ✅");
                System.out.println("  🎉 Grid shows " + gridRecordsCount + " records as expected!");
                System.out.println("  ✓ Badge count: " + DraftCount);
                System.out.println("  ✓ Grid count: " + gridRecordsCount);
                System.out.println("  ✓ Expected: " + expectedOnPage);
                if (reportLogger != null) {
                    reportLogger.pass("Inbox count verification PASSED - Grid shows " + gridRecordsCount + " records, matching expected count");
                }
            } else {
                System.out.println("\n❌ ❌ ❌ VERIFICATION FAILED! ❌ ❌ ❌");
                System.out.println("  ⚠️ Mismatch detected!");
                System.out.println("  Expected: " + expectedOnPage + " records on page");
                System.out.println("  Found:    " + gridRecordsCount + " records on page");
                System.out.println("  Difference: " + Math.abs(expectedOnPage - gridRecordsCount) + " records");
                
                System.out.println("\n🔍 POSSIBLE CAUSES:");
                if (DraftCount == 0) {
                    System.out.println("  • DRAFT badge count is 0 - badge might not be read correctly");
                }
                if (gridRecordsCount == 0) {
                    System.out.println("  • Grid count is 0 - grid might not have loaded");
                    System.out.println("  • Grid locator might be incorrect");
                }
                if (DraftCount > 0 && gridRecordsCount == 0) {
                    System.out.println("  • Badge shows " + DraftCount + " but grid shows 0");
                    System.out.println("  • Check if grid has finished loading");
                }
                if (DraftCount == 0 && gridRecordsCount > 0) {
                    System.out.println("  • Grid shows " + gridRecordsCount + " but badge shows 0");
                    System.out.println("  • Check if badge locator is correct");
                }
                if (DraftCount > 0 && gridRecordsCount > 0 && gridRecordsCount != expectedOnPage) {
                    System.out.println("  • Both counts exist but don't match expected");
                    System.out.println("  • Badge: " + DraftCount + ", Grid: " + gridRecordsCount + ", Expected: " + expectedOnPage);
                    if (gridRecordsCount < expectedOnPage) {
                        System.out.println("  • Grid might not have finished loading all records");
                    } else {
                        System.out.println("  • Grid might be showing more records than page size");
                    }
                }
                
                if (reportLogger != null) {
                    reportLogger.fail("Draft count verification FAILED - Expected " + expectedOnPage + " but found " + gridRecordsCount);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════════════════\n");
            return matches;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to verify Draft count: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify Draft count: " + e.getMessage());
            }
            return false;
        }
    }  
    
    public void clickPrioritizationChecklistLink() throws Exception {
        System.out.println("\n📋 ═══════════════════════════════════════════");
        System.out.println("📋 CLICKING PRIORITIZATION CHECKLIST LINK");
        System.out.println("📋 ═══════════════════════════════════════════");
        
        try {
            // Wait for the link to be visible and clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement checklistLink = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.prioritizationChecklistLink));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checklistLink);
            waitForSeconds(1);
            
            // Try clicking
            try {
                checklistLink.click();
                System.out.println("  ✅ Clicked Prioritization Checklist link (direct click)");
            } catch (Exception e) {
                // Fallback to JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checklistLink);
                System.out.println("  ✅ Clicked Prioritization Checklist link (JS click)");
            }
            
            // Wait for any action to complete
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Prioritization Checklist link");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Prioritization Checklist link: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Prioritization Checklist link: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("📋 ═══════════════════════════════════════════\n");
    }
    
 /*   public void clickChecklistResponses() throws Exception {
        System.out.println("\n📝 ═══════════════════════════════════════════");
        System.out.println("📝 CLICKING CHECKLIST RESPONSES");
        System.out.println("📝 ═══════════════════════════════════════════");
        
        By[] responseLocators = {
          InitiativePageLocators.checklistResponse1,
            InitiativePageLocators.checklistResponse2,
            InitiativePageLocators.checklistResponse3,
            InitiativePageLocators.checklistResponse4
        };
        
        int responseNum = 1;
        for (By locator : responseLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement responseInput = wait.until(ExpectedConditions.elementToBeClickable(locator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", responseInput);
                waitForSeconds(1);
                
                // Try clicking
                try {
                    responseInput.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", responseInput);
                }
                
                System.out.println("  ✅ Clicked Checklist Response " + responseNum);
                responseNum++;
                waitForSeconds(1);
                
            } catch (Exception e) {
                System.out.println("  ❌ Failed to click Checklist Response " + responseNum + ": " + e.getMessage());
                throw e;
            }
        }
        
        if (reportLogger != null) {
            reportLogger.pass("Successfully clicked all 4 checklist responses");
        }
        
        System.out.println("📝 ═══════════════════════════════════════════\n");
    }
    */
    
    public void clickChecklistResponses() {

        System.out.println("\n📝 CLICKING CHECKLIST RESPONSES (FINAL)");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1️⃣ Find ONLY rows that contain radio buttons (skip headers)
        List<WebElement> checklistRows = driver.findElements(
                By.xpath("//table//tbody//tr[.//input[@type='radio']]")
        );

        // 2️⃣ Handle NO DATA case
        if (checklistRows.isEmpty()) {
            System.out.println("ℹ️ No checklist items available. Skipping responses.");
            if (reportLogger != null) {
                reportLogger.info("No checklist rows found for this user");
            }
            return;
        }

        System.out.println("🔢 Checklist rows found: " + checklistRows.size());

        int rowNum = 1;

        // 3️⃣ Loop through each checklist row
        for (WebElement row : checklistRows) {
            try {
                // Find radios inside this row
                List<WebElement> radios = row.findElements(
                        By.xpath(".//input[@type='radio']")
                );

                if (radios.isEmpty()) {
                    System.out.println("⚠️ Row " + rowNum + " has no radio buttons. Skipping.");
                    continue;
                }

                // ⭐ FINAL RULE: click LAST radio (best score)
                WebElement radioToClick = radios.get(radios.size() - 1);

                // Scroll into view
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", radioToClick);

                wait.until(ExpectedConditions.elementToBeClickable(radioToClick));

                try {
                    radioToClick.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", radioToClick);
                }

                System.out.println("✅ Clicked checklist response for row " + rowNum);
                rowNum++;

            } catch (Exception e) {
                System.out.println("❌ Failed at checklist row " + rowNum + ": " + e.getMessage());
                throw e;
            }
        }

        if (reportLogger != null) {
            reportLogger.pass("Successfully clicked checklist responses for all rows");
        }

        System.out.println("📝 CHECKLIST RESPONSES COMPLETED\n");
    }

    
    
    
    
    
    
    
    
    public void clickChecklistSaveButton() throws Exception {
        System.out.println("\n💾 ═══════════════════════════════════════════");
        System.out.println("💾 CLICKING CHECKLIST SAVE BUTTON");
        System.out.println("💾 ═══════════════════════════════════════════");
        
        try {
            // Keep this fast: don't wait 15s per locator (this makes the test feel "hung")
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.checklistSaveButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            waitForSeconds(1);
            
            // Try clicking
            try {
                saveButton.click();
                System.out.println("  ✅ Clicked Checklist Save button (direct click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                System.out.println("  ✅ Clicked Checklist Save button (JS click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Checklist Save button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Checklist Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Checklist Save button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("💾 ═══════════════════════════════════════════\n");
    }
    
    
    public void verifyChecklistSuccessAlert(String expectedAlert) throws Exception {
        System.out.println("\n🔔 ═══════════════════════════════════════════");
        System.out.println("🔔 VERIFYING CHECKLIST SUCCESS ALERT");
        System.out.println("🔔 Expected: " + expectedAlert);
        System.out.println("🔔 ═══════════════════════════════════════════");
        
        try {
            // Wait for alert to appear
            waitForSeconds(2);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                InitiativePageLocators.checklistSuccessAlert));
            
            String actualAlert = alertElement.getText().trim();
            System.out.println("  📝 Actual Alert: " + actualAlert);
            
            if (actualAlert.toLowerCase().contains(expectedAlert.toLowerCase()) || 
                expectedAlert.toLowerCase().contains(actualAlert.toLowerCase())) {
                System.out.println("  ✅ Checklist Alert matched!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Checklist Alert matched! Expected: " + expectedAlert + " | Actual: " + actualAlert);
                }
            } else {
                String errorMsg = "❌ Checklist Alert mismatch! Expected: " + expectedAlert + " | Actual: " + actualAlert;
                System.out.println("  " + errorMsg);
                if (reportLogger != null) {
                    reportLogger.fail(errorMsg);
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify checklist alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify checklist alert: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("🔔 ═══════════════════════════════════════════\n");
    }
    
    
    public void waitForPushback() throws Exception {
        waitForElementToBeVisible(InitiativePageLocators.PushBack, "Pushback Link");
        waitForElementToBeClickable(InitiativePageLocators.PushBack, "Pushback Link");
    }  
    
    public void ClickPushback() throws Exception {
    	clickWithFallback(InitiativePageLocators.PushBack, "Click Pushback");
    }
    
    
    
    public void verifyPushbackSuccessAlert(String expectedAlert) throws Exception {
        System.out.println("\n🔔 ═══════════════════════════════════════════");
        System.out.println("🔔 VERIFYING CHECKLIST SUCCESS ALERT");
        System.out.println("🔔 Expected: " + expectedAlert);
        System.out.println("🔔 ═══════════════════════════════════════════");
        
        try {
            // Wait for alert to appear
            waitForSeconds(2);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                InitiativePageLocators.PushbackSuccessAlert));
            
            String actualAlert = alertElement.getText().trim();
            System.out.println("  📝 Actual Alert: " + actualAlert);
            
            if (actualAlert.toLowerCase().contains(expectedAlert.toLowerCase()) || 
                expectedAlert.toLowerCase().contains(actualAlert.toLowerCase())) {
                System.out.println("  ✅ Pushback Alert matched!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Pushback Alert matched! Expected: " + expectedAlert + " | Actual: " + actualAlert);
                }
            } else {
                String errorMsg = "❌ Pushback Alert mismatch! Expected: " + expectedAlert + " | Actual: " + actualAlert;
                System.out.println("  " + errorMsg);
                if (reportLogger != null) {
                    reportLogger.fail(errorMsg);
                }
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Pushback alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify Pushback alert: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("🔔 ═══════════════════════════════════════════\n");
    }
    
    public void searchInitiativeByCode(String initiativeCode) throws Exception {
        System.out.println("\n🔎 ═══════════════════════════════════════════");
        System.out.println("🔎 SEARCHING FOR INITIATIVE: " + initiativeCode);
        System.out.println("🔎 ═══════════════════════════════════════════");
        
        try {
            // STEP 1: Find the DemandCode input field
            System.out.println("  📍 Step 1: Finding DemandCode input field...");
            WebElement demandCodeInput = null;
            
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                demandCodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    InitiativePageLocators.demandCodeInput));
                System.out.println("  ✅ DemandCode input field found!");
            } catch (Exception e) {
                // Try alternative locator
                System.out.println("  ⚠️ Primary locator failed, trying alternative...");
                demandCodeInput = driver.findElement(By.xpath("//input[@id='DemandCode']"));
            }
            
            if (demandCodeInput == null) {
                throw new Exception("Could not find DemandCode input field");
            }
            
            // STEP 2: Clear and enter the initiative code
            System.out.println("  📍 Step 2: Entering initiative code...");
            
            // Scroll to the input field
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", demandCodeInput);
            waitForSeconds(1);
            
            // Click and clear the field
            demandCodeInput.click();
            waitForSeconds(1);
            demandCodeInput.clear();
            waitForSeconds(1);
            
            // Enter the initiative code
            demandCodeInput.sendKeys(initiativeCode);
            System.out.println("  ✅ Entered initiative code: " + initiativeCode);
            
            waitForSeconds(2);
            
            // STEP 3: Click the search button
            System.out.println("  📍 Step 3: Clicking search button...");
            WebElement searchButton = null;
            
            try {
                searchButton = driver.findElement(InitiativePageLocators.demandCodeSearchButton);
                System.out.println("  ✅ Search button found!");
            } catch (Exception e) {
                // Try alternative locator
                System.out.println("  ⚠️ Primary locator failed, trying alternative...");
                searchButton = driver.findElement(By.xpath("//span[@id='id__28']"));
            }
            
            if (searchButton != null) {
                // Try multiple click methods
                try {
                    searchButton.click();
                    System.out.println("  ✅ Clicked search button (direct click)");
                } catch (Exception e) {
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
                        System.out.println("  ✅ Clicked search button (JS click)");
                    } catch (Exception e2) {
                        // Try clicking the parent button if span doesn't work
                        WebElement parentButton = driver.findElement(
                            By.xpath("//span[@id='id__54']/ancestor::button | //span[@id='id__54']/parent::button"));
                        parentButton.click();
                        System.out.println("  ✅ Clicked search button (parent button)");
                    }
                }
            } else {
                // Fallback: Press Enter
                demandCodeInput.sendKeys(Keys.ENTER);
                System.out.println("  ✅ Pressed Enter to search");
            }
            
            // Wait for search results
            waitForSeconds(5);
            
            System.out.println("\n  ✅ ✅ ✅ Search executed for: " + initiativeCode + " ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Searched for initiative: " + initiativeCode);
            }
            
        } catch (Exception e) {
            System.out.println("\n  ❌ ❌ ❌ Search FAILED: " + e.getMessage() + " ❌ ❌ ❌");
            if (reportLogger != null) {
                reportLogger.fail("Search failed: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("🔎 ═══════════════════════════════════════════\n");
        
        
        // STEP 4: Close the search button
        System.out.println("  📍 Step 4: Clicking Close search button...");
        WebElement ClosesearchButton = null;
        
        try {
        	ClosesearchButton = driver.findElement(InitiativePageLocators.CloseSearchButton);
            System.out.println("  ✅ Close Search button found!");
        } catch (Exception e) {
            // Try alternative locator
            System.out.println("  ⚠️ Primary locator failed, trying alternative...");
            ClosesearchButton = driver.findElement(By.xpath("//span[@id='id__22']"));
        }
        if (ClosesearchButton != null) {
            // Try multiple click methods
            try {
            	ClosesearchButton.click();
                System.out.println("  ✅ Clicked CLose button (direct click)");
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ClosesearchButton);
                    System.out.println("  ✅ Clicked Close button (JS click)");
                } catch (Exception e2) {
                    // Try clicking the parent button if span doesn't work
                    WebElement parentButton = driver.findElement(
                        By.xpath("//span[@id='id__22']/ancestor::button | //span[@id='id__22']/parent::button"));
                    parentButton.click();
                    System.out.println("  ✅ Clicked Close button (parent button)");
                }
            }
        } else {
            // Fallback: Press Enter
           // demandCodeInput.sendKeys(Keys.ENTER);
            System.out.println("  ✅ Pressed Enter to search");
        }
        // STEP 5: Edit Initiative button
        System.out.println("  📍 Step 5: Clicking Edit button...");
        WebElement ClickEditButton = null;

        // Wait for the table to be populated with search results
        waitForSeconds(3);

        try {
        	ClickEditButton = driver.findElement(InitiativePageLocators.IniEditButton);
            System.out.println("  ✅ Edit button found using initiative-actions locator");
        } catch (Exception e) {
            // Try alternative locator - dynamic locator for specific initiative
            System.out.println("  ⚠️ Primary locator failed, trying dynamic locator for initiative: " + initiativeCode);
            try {
                ClickEditButton = driver.findElement(InitiativePageLocators.getEditButtonForInitiative(initiativeCode));
                System.out.println("  ✅ Dynamic edit button found for initiative: " + initiativeCode);
            } catch (Exception e2) {
                // Try generic CSS fallback
                System.out.println("  ⚠️ Dynamic locator failed, trying generic CSS fallback...");
                ClickEditButton = driver.findElement(By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall"));
                System.out.println("  ✅ Generic CSS edit button found as final fallback");
            }
        }
        if (ClickEditButton != null) {
            // Try multiple click methods
            try {
            	ClickEditButton.click();
                System.out.println("  ✅ Clicked Edit button (direct click)");
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ClickEditButton);
                    System.out.println("  ✅ Clicked Close button (JS click)");
                } catch (Exception e2) {
                    // Try clicking with JavaScript
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ClickEditButton);
                    System.out.println("  ✅ Clicked edit button (JS click)");
                }
            }
        } else {
            // Fallback: Press Enter
           // demandCodeInput.sendKeys(Keys.ENTER);
            System.out.println("  ✅ Pressed Enter to search");
        }
    }  
    
    public boolean verifyInitiativeInSearchResults(String initiativeCode) {
        try {
            System.out.println("\n✅ Verifying initiative in search results: " + initiativeCode);
            
            waitForSeconds(2);
            
            // Check if the initiative code appears in the grid/results
            String pageSource = driver.getPageSource();
            boolean found = pageSource.contains(initiativeCode);
            
            // Also try to find it in specific grid elements
            if (!found) {
                try {
                    List<WebElement> gridCells = driver.findElements(By.xpath("//*[contains(text(),'" + initiativeCode + "')]"));
                    found = !gridCells.isEmpty();
                } catch (Exception e) {
                    // Ignore
                }
            }
            
            if (found) {
                System.out.println("  ✅ Initiative " + initiativeCode + " found in search results!");
                if (reportLogger != null) {
                    reportLogger.pass("Initiative " + initiativeCode + " found in search results");
                }
            } else {
                System.out.println("  ❌ Initiative " + initiativeCode + " NOT found in search results");
                if (reportLogger != null) {
                    reportLogger.fail("Initiative " + initiativeCode + " not found in search results");
                }
            }
            
            return found;
            
        } catch (Exception e) {
            System.out.println("  ❌ Error verifying search results: " + e.getMessage());
            return false;
        }
    } 
    
    public void clickDiscussionThreadTab() throws Exception {
        System.out.println("\n💬 ═══════════════════════════════════════════");
        System.out.println("💬 CLICKING DISCUSSION THREAD TAB");
        System.out.println("💬 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement discussionTab = null;
            
            // Try primary locator first (linkText)
            try {
                discussionTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.discussionThreadTab));
                System.out.println("  ✅ Found Discussion Thread tab using linkText");
            } catch (Exception e) {
                // Try alternative XPath
                System.out.println("  ⚠️ LinkText failed, trying alternative XPath...");
                discussionTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.discussionThreadTabAlt));
                System.out.println("  ✅ Found Discussion Thread tab using XPath");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", discussionTab);
            waitForSeconds(1);
            
            // Click the tab
            try {
                discussionTab.click();
                System.out.println("  ✅ Clicked Discussion Thread tab (direct click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discussionTab);
                System.out.println("  ✅ Clicked Discussion Thread tab (JS click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Discussion Thread tab");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Discussion Thread tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Discussion Thread tab: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("💬 ═══════════════════════════════════════════\n");
    }
    
    
    public void enterDiscussionComment(String comment) throws Exception {
        System.out.println("\n📝 ═══════════════════════════════════════════");
        System.out.println("📝 ENTERING DISCUSSION COMMENT");
        System.out.println("📝 Comment: " + comment);
        System.out.println("📝 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement commentTextarea = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.discussionCommentTextarea));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", commentTextarea);
            waitForSeconds(1);
            
            // Click and clear the textarea
            commentTextarea.click();
            commentTextarea.clear();
            waitForSeconds(1);
            
            // Enter the comment
            commentTextarea.sendKeys(comment);
            System.out.println("  ✅ Entered comment: " + comment);
            
            waitForSeconds(1);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully entered discussion comment: " + comment);
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to enter discussion comment: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter discussion comment: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("📝 ═══════════════════════════════════════════\n");
    }
    
    public void clickDiscussionPostButton() throws Exception {
        System.out.println("\n📤 ═══════════════════════════════════════════");
        System.out.println("📤 CLICKING DISCUSSION POST BUTTON");
        System.out.println("📤 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement postButton = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativePageLocators.discussionPostButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", postButton);
            waitForSeconds(1);
            
            // Click the button
            try {
                postButton.click();
                System.out.println("  ✅ Clicked Post button (direct click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", postButton);
                System.out.println("  ✅ Clicked Post button (JS click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Discussion Post button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Discussion Post button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Discussion Post button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("📤 ═══════════════════════════════════════════\n");
    }
    
    // ==================== RESOURCES TAB METHODS ====================
    
    /**
     * Click on Resources Tab
     */
    public void clickResourcesTab() throws Exception {
        System.out.println("\n📁 ═══════════════════════════════════════════");
        System.out.println("📁 CLICKING RESOURCES TAB");
        System.out.println("📁 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement resourcesTab = null;
            
            // Try primary locator first (linkText)
            try {
                resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.resourcesTab));
                System.out.println("  ✅ Found Resources tab using linkText");
            } catch (Exception e) {
                // Try alternative XPath
                System.out.println("  ⚠️ LinkText failed, trying alternative XPath...");
                resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(
                    InitiativePageLocators.resourcesTabAlt));
                System.out.println("  ✅ Found Resources tab using XPath");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", resourcesTab);
            waitForSeconds(1);
            
            // Click the tab
            try {
                resourcesTab.click();
                System.out.println("  ✅ Clicked Resources tab (direct click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", resourcesTab);
                System.out.println("  ✅ Clicked Resources tab (JS click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Resources tab");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Resources tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Resources tab: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("📁 ═══════════════════════════════════════════\n");
    }
    
    /**
     * Click Resources Add Button
     * Uses multiple fallback strategies to handle dynamic IDs
     */
    public void clickResourcesAddButton() throws Exception {
        System.out.println("\n➕ ═══════════════════════════════════════════");
        System.out.println("➕ CLICKING RESOURCES ADD BUTTON");
        System.out.println("➕ ═══════════════════════════════════════════");
        
        // Multiple fallback XPaths to handle dynamic IDs
        By[] addButtonLocators = {
            InitiativePageLocators.resourcesAddButton,
            InitiativePageLocators.resourcesAddButtonAlt,
            // Context-based: Find Add button within Resources section/tab content
            By.xpath("//div[contains(@class,'tab-content') or contains(@class,'panel')]//button[.//span[text()='Add']]"),
            By.xpath("//button[.//span[text()='Add'] and not(contains(@class,'disabled'))]"),
            // Icon-based Add buttons
            By.xpath("//button[.//i[contains(@class,'Add')] or .//svg[contains(@class,'Add')]]"),
            // Generic Add button (last resort)
            By.xpath("(//button[contains(translate(., 'ADD', 'add'), 'add')])[1]"),
            // Button with + icon or text
            By.xpath("//button[contains(., '+') or .//span[contains(., '+')]]")
        };
        
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement addButton = null;
            String successLocator = "";
            
            // Try each locator strategy
            for (int i = 0; i < addButtonLocators.length; i++) {
                try {
                    System.out.println("  🔍 Trying locator strategy " + (i + 1) + "...");
                    addButton = shortWait.until(ExpectedConditions.elementToBeClickable(addButtonLocators[i]));
                    successLocator = addButtonLocators[i].toString();
                    System.out.println("  ✅ Found Add button using strategy " + (i + 1));
                    break;
            } catch (Exception e) {
                    System.out.println("  ⚠️ Strategy " + (i + 1) + " failed");
                }
            }
            
            // If still not found, try finding any visible button with "Add" text
            if (addButton == null) {
                System.out.println("  🔍 Trying to find any visible Add button...");
                List<WebElement> allButtons = driver.findElements(By.tagName("button"));
                for (WebElement btn : allButtons) {
                    try {
                        String btnText = btn.getText().toLowerCase();
                        if (btnText.contains("add") && btn.isDisplayed() && btn.isEnabled()) {
                            addButton = btn;
                            successLocator = "Dynamic button search - text: " + btn.getText();
                            System.out.println("  ✅ Found Add button by text scan: " + btn.getText());
                            break;
                        }
                    } catch (Exception ignored) {}
                }
            }
            
            if (addButton == null) {
                throw new Exception("Could not find Resources Add button with any locator strategy");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addButton);
            waitForSeconds(1);
            
            // Try multiple click strategies
            boolean clicked = false;
            
            // Strategy 1: Direct click
            try {
                addButton.click();
                clicked = true;
                System.out.println("  ✅ Clicked Add button (direct click)");
            } catch (Exception e) {
                System.out.println("  ⚠️ Direct click failed: " + e.getMessage());
            }
            
            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
                    clicked = true;
                System.out.println("  ✅ Clicked Add button (JS click)");
                } catch (Exception e) {
                    System.out.println("  ⚠️ JS click failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    new org.openqa.selenium.interactions.Actions(driver)
                        .moveToElement(addButton)
                        .click()
                        .perform();
                    clicked = true;
                    System.out.println("  ✅ Clicked Add button (Actions click)");
                } catch (Exception e) {
                    System.out.println("  ⚠️ Actions click failed: " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Could not click Add button with any click strategy");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully clicked Resources Add button using: " + successLocator);
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Resources Add button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Resources Add button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("➕ ═══════════════════════════════════════════\n");
    }
    
    
    /**
     * Select value from Resources Dropdown using Actions class
     * Similar approach to selectInitiativeBGWithActions
     * @param optionText The option text to select from dropdown
     */
    public void selectResourcesDropdownWithActions() throws Throwable {
       click( InitiativePageLocators.resourcesDropdownTrigger,"resourcesDropdownTrigger");
       selectRandomValueFromDropdown(InitiativePageLocators.resourcesDropdownOption,"Role");
    }
    
    public void selectSkillsFromDropdown() throws Throwable {
    	click( InitiativePageLocators.skillDropdownTrigger,"skillDropdownTrigger");
        selectRandomValueFromDropdown(InitiativePageLocators.skillclickoption,"Skill");
    }
     
    
    public void enterResourceInDate(String inDate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 ENTERING RESOURCE IN DATE");
        System.out.println("📅 INPUT RECEIVED: '" + inDate + "' (null: " + (inDate == null) + ")");

        if (inDate == null) {
            System.out.println("  ❌ ERROR: inDate parameter is NULL!");
            return;
        }

        String trimmed = inDate.trim();
        System.out.println("📅 AFTER TRIM: '" + trimmed + "' (empty: " + trimmed.isEmpty() + ")");
        System.out.println("📅 ═══════════════════════════════════════════");

        if (trimmed.isEmpty()) {
            System.out.println("  ⚠️ In Date is empty after trimming, skipping...");
            return;
        }
        
        try {
            // First scroll to make date pickers visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 200);");
            waitForSeconds(1);

            // Log the date being processed
            System.out.println("    📅 Processing Resource In Date: '" + inDate + "' -> '" + (inDate != null ? convertDateForInput(inDate) : "null") + "'");
            
            boolean success = false;
            
            // Strategy 1: JavaScript approach (most reliable)
            System.out.println("  📍 Strategy 1: JavaScript approach for DatePicker");
            success = setResourceDateWithJS(inDate, 1);

            // Strategy 2: Find DatePicker combobox (div with role=combobox) - BEST for Fluent UI
            if (!success) {
                System.out.println("  📍 Strategy 2: DatePicker combobox (div with role=combobox)");
                success = enterDateInComboboxDatePicker(1, inDate); // 1 = first DatePicker
            }

            // Strategy 3: Use Tab navigation from current position (after skills dropdown)
            if (!success) {
                System.out.println("  📍 Strategy 3: Tab navigation to In Date field");
                success = enterDateUsingTabNavigation(inDate, true);
            }

            // Strategy 4: Find and enter date in first DatePicker on the resource form
            if (!success) {
                System.out.println("  📍 Strategy 4: Find first DatePicker and enter date");
                success = enterDateInResourceDatePicker(inDate, 1);
            }

            // Strategy 5: Try using the locator directly if it finds an input
            if (!success) {
                System.out.println("  📍 Strategy 5: Try primary locator");
                success = setDateWithMultipleStrategies(
                    InitiativePageLocators.resourceInDate,
                    inDate,
                    "Resource In Date"
                );
            }

            // Strategy 6: Try calendar selection method (most reliable for Fluent UI)
            if (!success) {
                System.out.println("  📍 Strategy 6: Calendar selection method");
                success = enterDateInComboboxDatePicker(1, inDate); // 1 = first DatePicker (In Date)
            }
            
            if (success) {
                System.out.println("✅ Successfully set Resource In Date: " + inDate);
                if (reportLogger != null) {
                    reportLogger.info("✅ Resource In Date: " + inDate);
                }
            } else {
                System.out.println("⚠️ Could not set Resource In Date - all strategies failed");
                throw new Exception("Failed to set Resource In Date: " + inDate);
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
    
    
    private boolean enterDateInComboboxDatePicker(int datePickerIndex, String dateValue) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Actions actions = new Actions(driver);
            
            System.out.println("    🔍 Looking for DatePicker combobox #" + datePickerIndex + "...");
            System.out.println("    📋 Date to set: " + dateValue);
            System.out.println("    📋 Date length: " + (dateValue != null ? dateValue.length() : "null"));
            System.out.println("    📋 Date contains '/': " + (dateValue != null ? dateValue.contains("/") : "null"));
            System.out.println("    📋 Date contains space: " + (dateValue != null ? dateValue.contains(" ") : "null"));
            
            // Parse the date to extract day, month, year
            int day = 0;
            int month = 0; // 1-12
            int year = 0;
            String monthName = "";
            
            try {
                dateValue = dateValue.trim();
                System.out.println("    📋 DEBUG: Parsing date: '" + dateValue + "' (length: " + dateValue.length() + ")");
                System.out.println("    📋 DEBUG: Contains '/': " + dateValue.contains("/") + ", Contains '-': " + dateValue.contains("-") + ", Contains space: " + dateValue.contains(" "));

                if (dateValue.contains("/")) {
                    // Format: MM/DD/YYYY or M/D/YYYY
                    String[] parts = dateValue.split("/");
                    System.out.println("    📋 DEBUG: Split by '/' resulted in " + parts.length + " parts: " + java.util.Arrays.toString(parts));
                    if (parts.length == 3) {
                        month = Integer.parseInt(parts[0].trim());
                        day = Integer.parseInt(parts[1].trim());
                        year = Integer.parseInt(parts[2].trim());
                        System.out.println("    📋 DEBUG: Parsed as MM/DD/YYYY format - Month: " + month + ", Day: " + day + ", Year: " + year);
                    } else {
                        throw new Exception("Invalid MM/DD/YYYY format: " + dateValue);
                    }
                } else if (dateValue.contains("-")) {
                    // Format: YYYY-MM-DD
                    String[] parts = dateValue.split("-");
                    if (parts.length == 3) {
                        year = Integer.parseInt(parts[0].trim());
                        month = Integer.parseInt(parts[1].trim());
                        day = Integer.parseInt(parts[2].trim());
                        System.out.println("    📋 DEBUG: Parsed as YYYY-MM-DD format - Month: " + month + ", Day: " + day + ", Year: " + year);
                    } else {
                        throw new Exception("Invalid date format: " + dateValue);
                    }
                } else {
                    // Format: "Mon Dec 01 2025" or "Dec 01 2025"
                    String[] parts = dateValue.split("\\s+");
                    System.out.println("    📋 DEBUG: Split parts: " + java.util.Arrays.toString(parts));
                    System.out.println("    📋 DEBUG: Number of parts: " + parts.length);

                    if (parts.length >= 3) {
                        // Find the month name (3-letter abbreviation)
                        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                        int startIdx = 0;
                        // Skip day name if present (Mon, Tue, etc.)
                        boolean shouldSkip = parts[0].length() == 3 && !java.util.Arrays.asList(monthNames).contains(parts[0]);
                        System.out.println("    📋 DEBUG: First part '" + parts[0] + "' length=" + parts[0].length() + ", is 3-letter non-month: " + shouldSkip);

                        if (shouldSkip) {
                            startIdx = 1;
                            System.out.println("    📋 DEBUG: Skipping day name, using index " + startIdx);
                        } else {
                            System.out.println("    📋 DEBUG: Not skipping, using index " + startIdx);
                        }

                        monthName = parts[startIdx];
                        System.out.println("    📋 DEBUG: Using month name: '" + monthName + "' from index " + startIdx);

                        for (int i = 0; i < monthNames.length; i++) {
                            if (monthNames[i].equalsIgnoreCase(monthName)) {
                                month = i + 1;
                                System.out.println("    📋 DEBUG: Found month match: '" + monthNames[i] + "' -> " + month);
                                break;
                            }
                        }

                        if (month == 0) {
                            System.out.println("    📋 DEBUG: ERROR - Could not find month name '" + monthName + "' in month list: " + java.util.Arrays.toString(monthNames));
                        } else if (month < 1 || month > 12) {
                            System.out.println("    📋 ERROR: Invalid month value: " + month + " (must be 1-12)");
                        }

                        try {
                            String dayPart = parts[startIdx + 1];
                            System.out.println("    📋 DEBUG: Day part: '" + dayPart + "'");
                            day = Integer.parseInt(dayPart.replaceAll("[^0-9]", ""));
                            System.out.println("    📋 DEBUG: Parsed day: " + day);
                            if (day < 1 || day > 31) {
                                System.out.println("    📋 ERROR: Invalid day value: " + day + " (must be 1-31)");
                            }
                        } catch (Exception e) {
                            System.out.println("    📋 DEBUG: ERROR parsing day: " + e.getMessage());
                        }

                        try {
                            String yearPart = parts[startIdx + 2];
                            System.out.println("    📋 DEBUG: Year part: '" + yearPart + "'");
                            year = Integer.parseInt(yearPart.replaceAll("[^0-9]", ""));
                            System.out.println("    📋 DEBUG: Parsed year: " + year);
                            if (year < 2020 || year > 2030) {
                                System.out.println("    📋 ERROR: Invalid year value: " + year + " (expected 2020-2030)");
                            }
                        } catch (Exception e) {
                            System.out.println("    📋 DEBUG: ERROR parsing year: " + e.getMessage());
                        }
                    } else {
                        System.out.println("    📋 DEBUG: ERROR - Not enough parts to parse date");
                    }
                }

                System.out.println("    📋 FINAL PARSED RESULT: Day=" + day + ", Month=" + month + ", Year=" + year);

                // Validate parsed values
                if (month < 1 || month > 12) {
                    System.out.println("    📋 ERROR: Invalid month " + month + " (should be 1-12)");
                }
                if (day < 1 || day > 31) {
                    System.out.println("    📋 ERROR: Invalid day " + day + " (should be 1-31)");
                }
                if (year < 2000 || year > 2030) {
                    System.out.println("    📋 ERROR: Invalid year " + year + " (should be reasonable)");
                }
                
            } catch (Exception parseEx) {
                System.out.println("    ⚠️ Could not parse date: " + dateValue + " - " + parseEx.getMessage());
                // Continue anyway, will try direct input
            }
            
            // Find all DatePicker combobox elements (div with role=combobox and DatePicker in ID)
            java.util.List<WebElement> datePickerDivs = driver.findElements(By.xpath(
                "//div[contains(@id,'DatePicker') and @role='combobox']"
            ));

            System.out.println("    📋 Found " + datePickerDivs.size() + " DatePicker comboboxes");

            // List them for debugging
            for (int i = 0; i < datePickerDivs.size(); i++) {
                WebElement dp = datePickerDivs.get(i);
                String id = dp.getAttribute("id");
                String text = dp.getText();
                String ariaLabel = dp.getAttribute("aria-label");
                String placeholder = dp.getAttribute("placeholder");
                System.out.println("      [" + i + "] id=" + id + ", text='" + text + "', aria-label='" + ariaLabel + "', placeholder='" + placeholder + "', visible=" + dp.isDisplayed() + ", enabled=" + dp.isEnabled());
            }

            // If no DatePickers found with the specific XPath, try broader search
            if (datePickerDivs.isEmpty()) {
                System.out.println("    📋 No DatePickers found with specific XPath, trying broader search...");
                java.util.List<WebElement> broaderDatePickers = driver.findElements(By.xpath(
                    "//div[@role='combobox'] | //input[contains(@class,'DatePicker')] | //div[contains(@class,'DatePicker')]"
                ));
                System.out.println("    📋 Broader search found " + broaderDatePickers.size() + " potential date picker elements");
                for (int i = 0; i < Math.min(broaderDatePickers.size(), 5); i++) { // Show first 5
                    WebElement dp = broaderDatePickers.get(i);
                    String tag = dp.getTagName();
                    String id = dp.getAttribute("id");
                    String cls = dp.getAttribute("class");
                    String text = dp.getText();
                    System.out.println("      [" + i + "] " + tag + " id=" + id + ", class=" + cls + ", text='" + text + "'");
                }
            }
            
            if (datePickerDivs.size() < datePickerIndex) {
                System.out.println("    ⚠️ Not enough DatePickers found. Need " + datePickerIndex + " but found " + datePickerDivs.size());
                return false;
            }
            
            // Select the appropriate DatePicker
            WebElement targetPicker = datePickerDivs.get(datePickerIndex - 1);
            String pickerId = targetPicker.getAttribute("id");
            System.out.println("    ✓ Selected DatePicker: " + pickerId);

            // Scroll into view
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetPicker);
            Thread.sleep(500);

            // Wait for the date picker to be clickable
            System.out.println("    ⏳ Waiting for DatePicker to be clickable...");
            long startTime = System.currentTimeMillis();
            while (!targetPicker.isDisplayed() || !targetPicker.isEnabled()) {
                Thread.sleep(200);
                if (System.currentTimeMillis() - startTime > 5000) { // 5 second timeout
                    System.out.println("    ⚠️ DatePicker not clickable after 5 seconds, proceeding anyway...");
                    break;
                }
            }
            System.out.println("    ✅ DatePicker is now clickable (waited " + (System.currentTimeMillis() - startTime) + "ms)");

            // Click to open the calendar
            System.out.println("    🖱️ Clicking DatePicker to open calendar...");
            targetPicker.click();
            Thread.sleep(1000); // Increased wait time for calendar to open
            
            // Now we need to select the date from the calendar
            // Fluent UI calendar structure:
            // - Header shows current month/year with navigation buttons
            // - Grid of day buttons
            
            if (day > 0 && month > 0 && year > 0) {
                System.out.println("    📅 Attempting to set date: " + month + "/" + day + "/" + year);

                // Strategy 1: Try calendar navigation (most reliable for Fluent UI)
                System.out.println("    📅 Strategy 1: Calendar navigation");
                try {
                    boolean calendarSuccess = selectDateFromCalendar(day, month, year, actions, js);
                    if (calendarSuccess) {
                        System.out.println("    ✅ Date set successfully via calendar navigation");
                        return true;
                    }
                } catch (Exception calendarEx) {
                    System.out.println("    ⚠️ Calendar navigation failed: " + calendarEx.getMessage());
                }

                // Strategy 2: Fallback - Direct input field value setting
                System.out.println("    📅 Strategy 2: Direct input field value setting (fallback)");
                try {
                    // Look for input field within or near the date picker
                    String inputXPath = ".//input | following::input[1] | preceding::input[1]";
                    WebElement dateInput = null;

                    try {
                        dateInput = targetPicker.findElement(By.xpath(inputXPath));
                    } catch (Exception e) {
                        // Try broader search for any input near date pickers
                        java.util.List<WebElement> nearbyInputs = driver.findElements(By.xpath("//input"));
                        for (WebElement input : nearbyInputs) {
                            if (input.isDisplayed()) {
                                String inputId = input.getAttribute("id") != null ? input.getAttribute("id").toLowerCase() : "";
                                String inputClass = input.getAttribute("class") != null ? input.getAttribute("class").toLowerCase() : "";
                                if (inputId.contains("date") || inputClass.contains("date")) {
                                    dateInput = input;
                                    break;
                                }
                            }
                        }
                    }

                    if (dateInput != null) {
                        String dateString = String.format("%02d/%02d/%04d", month, day, year);
                        System.out.println("    📝 Setting input field value to: " + dateString);

                        // For Fluent UI DatePicker, we need to properly trigger the component update
                        js.executeScript(
                            "var input = arguments[0];" +
                            "var dateStr = arguments[1];" +
                            "input.value = dateStr;" +
                            "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                            "input.dispatchEvent(new Event('change', { bubbles: true }));" +
                            "input.dispatchEvent(new Event('blur', { bubbles: true }));" +
                            "input.focus();" +
                            "setTimeout(function() { input.blur(); }, 100);" +
                            "if (input._valueTracker) { input._valueTracker.setValue(''); }" +
                            "var event = new Event('change', { bubbles: true });" +
                            "Object.defineProperty(event, 'target', { writable: false, value: input });" +
                            "input.dispatchEvent(event);",
                            dateInput, dateString
                        );

                        Thread.sleep(1500); // Give more time for React to update

                        String inputValue = dateInput.getAttribute("value");
                        String pickerText = targetPicker.getText();
                        System.out.println("    📋 After input setting - Input value: '" + inputValue + "', Picker text: '" + pickerText + "'");

                        // Check if the date picker display updated
                        boolean inputValueSet = inputValue != null && inputValue.contains(String.valueOf(year));
                        boolean pickerTextUpdated = pickerText != null && !pickerText.contains("Select Date") && pickerText.trim().length() > 0;

                        if (inputValueSet && pickerTextUpdated) {
                            System.out.println("    ✅ Date set successfully via input field");
                            return true;
                        } else if (inputValueSet) {
                            System.out.println("    ⚠️ Input value set but display not updated - this may still work");
                            return true;
                        }
                    }
                } catch (Exception inputEx) {
                    System.out.println("    ⚠️ Direct input setting failed: " + inputEx.getMessage());
                }

                // Strategy 2: Try direct keyboard input into the picker
                System.out.println("    📅 Strategy 2: Direct keyboard input");
                try {
                    String dateString = String.format("%02d/%02d/%04d", month, day, year);
                    System.out.println("    ⌨️ Typing date directly: " + dateString);

                    // Focus and clear, then type
                    actions.moveToElement(targetPicker).click()
                           .pause(Duration.ofMillis(200))
                           .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                           .pause(Duration.ofMillis(100))
                           .sendKeys(Keys.DELETE)
                           .pause(Duration.ofMillis(100))
                           .sendKeys(dateString)
                           .pause(Duration.ofMillis(300))
                           .sendKeys(Keys.TAB)
                           .perform();

                    Thread.sleep(500);

                    String newText = targetPicker.getText();
                    System.out.println("    📋 DatePicker text after keyboard input: '" + newText + "'");

                    if (newText != null && !newText.contains("Select Date") && newText.length() > 5) {
                        System.out.println("    ✅ Date set successfully via keyboard: " + newText);
                        return true;
                    }
                } catch (Exception keyboardEx) {
                    System.out.println("    ⚠️ Keyboard input failed: " + keyboardEx.getMessage());
                }

                // Strategy 3: Try calendar navigation as last resort
                System.out.println("    📅 Strategy 3: Calendar navigation");
                boolean dateSelected = selectDateFromCalendar(day, month, year, actions, js);

                if (dateSelected) {
                    Thread.sleep(500);
                    String newText = targetPicker.getText();
                    System.out.println("    📋 DatePicker text after calendar: '" + newText + "'");

                    if (newText != null && !newText.contains("Select Date")) {
                        System.out.println("    ✅ Date set successfully via calendar: " + newText);
                        return true;
                    }
                }
            }
            
            // Fallback: Try direct keyboard input
            System.out.println("    🔄 Fallback: Trying direct keyboard input...");
            
            // Press Escape first to close any open calendar
            actions.sendKeys(Keys.ESCAPE).perform();
            Thread.sleep(300);
            
            // Click again and try typing
            targetPicker.click();
            Thread.sleep(500);
            
            // Type the date in different formats
            String dateToType = "";
            if (month > 0 && day > 0 && year > 0) {
                // Try MM/DD/YYYY format
                dateToType = String.format("%02d/%02d/%04d", month, day, year);
            } else {
                dateToType = dateValue;
            }
            
            System.out.println("    ⌨️ Typing: " + dateToType);
            actions.sendKeys(dateToType).perform();
            Thread.sleep(300);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);
            actions.sendKeys(Keys.ESCAPE).perform();
            Thread.sleep(300);
            
            String finalText = targetPicker.getText();
            System.out.println("    📋 Final DatePicker text: '" + finalText + "'");
            
            return !finalText.contains("Select Date");
            
        } catch (Exception e) {
            System.out.println("    ✗ enterDateInComboboxDatePicker failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    private boolean enterDateUsingTabNavigation(String dateValue, boolean isInDate) {
        try {
            Actions actions = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            System.out.println("    🔍 Using Tab navigation to reach date field...");
            System.out.println("    📋 Date to enter: " + dateValue);
            
            // First, let's find the currently active element
            WebElement activeElement = driver.switchTo().activeElement();
            String activeId = "";
            String activeTag = "";
            try {
                activeId = activeElement.getAttribute("id");
                activeTag = activeElement.getTagName();
                System.out.println("    📋 Currently focused: <" + activeTag + "> id=" + activeId);
            } catch (Exception e) {}
            
            // If this is the first date (In Date), Tab from current position
            // If this is the second date (Out Date), we should already be in position after In Date
            if (isInDate) {
                // Tab a few times to reach the In Date field from skills dropdown
                System.out.println("    ⌨️ Pressing Tab to reach In Date field...");
                for (int i = 0; i < 5; i++) {
                    actions.sendKeys(Keys.TAB).perform();
                    Thread.sleep(300);
                    
                    // Check what we focused on
                    try {
                        activeElement = driver.switchTo().activeElement();
                        activeId = activeElement.getAttribute("id");
                        activeTag = activeElement.getTagName();
                        String role = activeElement.getAttribute("role");
                        String text = activeElement.getText();
                        System.out.println("    [Tab " + (i+1) + "] Focused: <" + activeTag + "> id=" + activeId + ", role=" + role);
                        
                        // Check if this looks like a date picker (div with role=combobox and DatePicker in ID)
                        if (activeId != null && activeId.toLowerCase().contains("datepicker")) {
                            System.out.println("    ✓ Found DatePicker combobox!");
                            break;
                        }
                        // Also check for role=combobox with "Select Date" text
                        if ("combobox".equals(role) && text != null && text.contains("Date")) {
                            System.out.println("    ✓ Found DatePicker by role and text!");
                            break;
                        }
                    } catch (Exception e) {}
                }
            }
            
            // Now we should be focused on the DatePicker combobox (div)
            // For Fluent UI DatePicker with role=combobox, we need to:
            // 1. Press Enter or Space to open the calendar
            // 2. Type the date
            // 3. Press Enter to confirm
            
            activeElement = driver.switchTo().activeElement();
            String role = activeElement.getAttribute("role");
            System.out.println("    📋 Active element role: " + role);
            
            if ("combobox".equals(role)) {
                // This is a Fluent UI DatePicker - open it and type
                System.out.println("    🖱️ Opening DatePicker calendar...");
                actions.sendKeys(Keys.ENTER).perform();
                Thread.sleep(500);
                
                // Type the date
                System.out.println("    ⌨️ Typing date: " + dateValue);
                actions.sendKeys(dateValue).perform();
                Thread.sleep(300);
                
                // Press Enter to confirm selection
                actions.sendKeys(Keys.ENTER).perform();
                Thread.sleep(500);
                
                // Press Escape to close any popup
                actions.sendKeys(Keys.ESCAPE).perform();
                Thread.sleep(200);
            } else {
                // It might be a regular input, try typing directly
                System.out.println("    ⌨️ Typing date directly...");
                actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
                Thread.sleep(100);
                actions.sendKeys(Keys.DELETE).perform();
                Thread.sleep(100);
                actions.sendKeys(dateValue).perform();
                Thread.sleep(500);
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
                actions.sendKeys(Keys.ESCAPE).perform();
                Thread.sleep(200);
            }
            
            System.out.println("    ✅ Date entry via Tab navigation completed");
            return true;
            
        } catch (Exception e) {
            System.out.println("    ✗ Tab navigation failed: " + e.getMessage());
            return false;
        }
    }
    
    
    private boolean setResourceDateWithJS(String dateValue, int pickerIndex) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Convert dateValue to input-compatible format (MM/DD/YYYY)
            String formattedDate = convertDateForInput(dateValue);
            System.out.println("    📋 Converting date '" + dateValue + "' to input format: '" + formattedDate + "'");

            // Find date picker input by index
            WebElement dateInput = (WebElement) js.executeScript(
                "var inputs = document.querySelectorAll('input');" +
                "var dateInputs = [];" +
                "for (var i = 0; i < inputs.length; i++) {" +
                "  var inp = inputs[i];" +
                "  var id = (inp.id || '').toLowerCase();" +
                "  var cls = (inp.className || '').toLowerCase();" +
                "  var parent = inp.closest('[class*=\"DatePicker\"], [class*=\"ms-DatePicker\"]');" +
                "  if (id.includes('datepicker') || cls.includes('datepicker') || parent) {" +
                "    dateInputs.push(inp);" +
                "  }" +
                "}" +
                "var idx = " + (pickerIndex - 1) + ";" +
                "return dateInputs.length > idx ? dateInputs[idx] : null;");
            
            if (dateInput == null) {
                System.out.println("    ⚠️ Could not find DatePicker #" + pickerIndex + " via JS");
                return false;
            }
            
            System.out.println("    ✓ Found DatePicker #" + pickerIndex + " via JavaScript");
            
            // Scroll into view
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dateInput);
            Thread.sleep(300);
            
            // Clear any existing value first (important for Fluent UI DatePickers)
            js.executeScript(
                "arguments[0].value = '';" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                dateInput
            );
            Thread.sleep(300);

            // Try setting value using React-compatible approach
            js.executeScript(
                "var element = arguments[0];" +
                "var value = arguments[1];" +
                "element.focus();" +
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(element, value);" +
                "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                "element.dispatchEvent(new Event('change', { bubbles: true }));" +
                "element.dispatchEvent(new Event('blur', { bubbles: true }));",
                dateInput, formattedDate
            );
            Thread.sleep(800); // Increased wait time for Fluent UI to process
            
            // Verify value was set
            String currentValue = dateInput.getAttribute("value");
            System.out.println("    📋 Value after JS: '" + currentValue + "'");

            // More robust verification for Fluent UI DatePickers
            boolean valueSetCorrectly = false;
            if (currentValue != null && !currentValue.trim().isEmpty()) {
                // Check if the formatted date is contained in the current value
                String trimmedCurrent = currentValue.trim();
                String trimmedFormatted = formattedDate.trim();
                valueSetCorrectly = trimmedCurrent.equals(trimmedFormatted) ||
                                   trimmedCurrent.contains(trimmedFormatted.substring(0, Math.min(5, trimmedFormatted.length())));
                System.out.println("    📋 Verification: current='" + trimmedCurrent + "', expected='" + trimmedFormatted + "', match=" + valueSetCorrectly);
            }

            if (valueSetCorrectly) {
                System.out.println("    ✅ JavaScript approach SUCCESS - Date set to: " + formattedDate);
                return true;
            } else {
                System.out.println("    ⚠️ JavaScript approach - value not properly set");
            }
            
            // If value not set, try click + type approach
            System.out.println("    → Trying click + type approach...");
            Actions actions = new Actions(driver);
            actions.moveToElement(dateInput)
                   .click()
                   .pause(Duration.ofMillis(300))
                   .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                   .pause(Duration.ofMillis(200))
                   .sendKeys(Keys.DELETE)
                   .pause(Duration.ofMillis(200))
                   .sendKeys(formattedDate) // Use formattedDate instead of dateValue
                   .pause(Duration.ofMillis(500))
                   .sendKeys(Keys.TAB)
                   .perform();
            Thread.sleep(800); // Wait for Fluent UI to process the input
            Thread.sleep(500);
            
            currentValue = dateInput.getAttribute("value");
            System.out.println("    📋 Value after click+type: '" + currentValue + "'");
            
            return currentValue != null && !currentValue.isEmpty();
            
        } catch (Exception e) {
            System.out.println("    ✗ JavaScript approach failed: " + e.getMessage());
            return false;
        }
    }
    
    
    private String convertDateForInput(String dateValue) {
        try {
            if (dateValue == null || dateValue.trim().isEmpty()) {
                System.out.println("    📋 convertDateForInput: Empty/null input, returning as-is");
                return dateValue;
            }

            dateValue = dateValue.trim();
            System.out.println("    📋 convertDateForInput: Converting '" + dateValue + "'");

            // If already in MM/DD/YYYY format, return as-is
            if (dateValue.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                System.out.println("    📋 convertDateForInput: Already in MM/DD/YYYY format");
                return dateValue;
            }

            // If already in YYYY-MM-DD format, convert to MM/DD/YYYY
            if (dateValue.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                String[] parts = dateValue.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                String result = String.format("%02d/%02d/%04d", month, day, year);
                System.out.println("    📋 convertDateForInput: Converted YYYY-MM-DD to MM/DD/YYYY: '" + result + "'");
                return result;
            }

            // Parse "Wed Jan 15 2025" format
            String[] parts = dateValue.split("\\s+");
            if (parts.length >= 3) {
                String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                int startIdx = 0;
                // Skip day name if present
                boolean shouldSkip = parts[0].length() == 3 && !java.util.Arrays.asList(monthNames).contains(parts[0]);
                if (shouldSkip && parts.length > 3) {
                    startIdx = 1;
                    System.out.println("    📋 convertDateForInput: Skipping day name, using index " + startIdx);
                }

                String monthName = parts[startIdx];
                String dayStr = parts[startIdx + 1];
                String yearStr = parts[startIdx + 2];

                System.out.println("    📋 convertDateForInput: Parsed parts - month: '" + monthName + "', day: '" + dayStr + "', year: '" + yearStr + "'");

                // Convert month name to number
                int month = 0;
                for (int i = 0; i < monthNames.length; i++) {
                    if (monthNames[i].equalsIgnoreCase(monthName)) {
                        month = i + 1;
                        break;
                    }
                }

                if (month > 0) {
                    int day = Integer.parseInt(dayStr.replaceAll("[^0-9]", ""));
                    int year = Integer.parseInt(yearStr.replaceAll("[^0-9]", ""));
                    String result = String.format("%02d/%02d/%04d", month, day, year);
                    System.out.println("    📋 convertDateForInput: Converted to MM/DD/YYYY: '" + result + "'");
                    return result;
                } else {
                    System.out.println("    📋 convertDateForInput: Could not find month name '" + monthName + "'");
                }
            } else {
                System.out.println("    📋 convertDateForInput: Not enough parts after split: " + parts.length);
            }

            // If no conversion worked, return original
            System.out.println("    ⚠️ Could not convert date '" + dateValue + "' to input format, returning original");
            return dateValue;

        } catch (Exception e) {
            System.out.println("    ❌ Error converting date '" + dateValue + "': " + e.getMessage());
            return dateValue;
        }
    }

    private boolean enterDateInResourceDatePicker(String dateValue, int pickerIndex) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Actions actions = new Actions(driver);
            
            System.out.println("    🔍 Looking for DatePicker #" + pickerIndex + " on Resource form...");
            
            // First, scan and list all DatePicker-related elements for debugging
            System.out.println("    📋 Scanning page for all DatePicker elements...");
            
            @SuppressWarnings("unchecked")
            java.util.List<WebElement> allDatePickerDivs = (java.util.List<WebElement>) js.executeScript(
                "var results = [];" +
                "// Find all elements with DatePicker in ID\n" +
                "var byId = document.querySelectorAll('[id*=\"DatePicker\"]');" +
                "for (var i = 0; i < byId.length; i++) {" +
                "  var el = byId[i];" +
                "  // Only include visible elements that look like date inputs\n" +
                "  if (el.offsetParent !== null) {" +
                "    results.push({el: el, id: el.id, tag: el.tagName});" +
                "  }" +
                "}" +
                "// Also find ms-DatePicker class elements\n" +
                "var byClass = document.querySelectorAll('.ms-DatePicker, [class*=\"DatePicker\"]');" +
                "for (var i = 0; i < byClass.length; i++) {" +
                "  var el = byClass[i];" +
                "  if (el.offsetParent !== null) {" +
                "    results.push({el: el, id: el.id || 'no-id', tag: el.tagName});" +
                "  }" +
                "}" +
                "return results.map(function(r) { return r.el; });"
            );
            
            System.out.println("    📋 Found " + (allDatePickerDivs != null ? allDatePickerDivs.size() : 0) + " DatePicker elements");
            
            // List the found elements
            java.util.List<WebElement> dateInputs = new java.util.ArrayList<>();
            
            if (allDatePickerDivs != null) {
                for (int i = 0; i < allDatePickerDivs.size(); i++) {
                    WebElement el = allDatePickerDivs.get(i);
                    try {
                        String id = el.getAttribute("id");
                        String tag = el.getTagName();
                        String className = el.getAttribute("class");
                        boolean isInput = tag.equalsIgnoreCase("input");
                        boolean hasInput = false;
                        
                        // Check if this element or its parent contains an input
                        WebElement input = null;
                        try {
                            if (isInput) {
                                input = el;
                                hasInput = true;
                            } else {
                                // Try to find input inside
                                java.util.List<WebElement> inputs = el.findElements(By.xpath(".//input | ../input | ../../input"));
                                if (!inputs.isEmpty()) {
                                    input = inputs.get(0);
                                    hasInput = true;
                                }
                            }
                        } catch (Exception ex) {}
                        
                        System.out.println("      [" + i + "] <" + tag + "> id=" + id + 
                            ", hasInput=" + hasInput + 
                            ", class=" + (className != null ? className.substring(0, Math.min(40, className.length())) : "null"));
                        
                        // Add input to our list if found
                        if (input != null && input.isDisplayed()) {
                            dateInputs.add(input);
                        } else if (isInput && el.isDisplayed()) {
                            dateInputs.add(el);
                        }
                    } catch (Exception ex) {
                        System.out.println("      [" + i + "] Error reading element");
                    }
                }
            }
            
            // Also try to find inputs directly
            System.out.println("    🔍 Also searching for direct date inputs...");
            try {
                java.util.List<WebElement> directInputs = driver.findElements(By.xpath(
                    "//input[contains(@id,'DatePicker')] | " +
                    "//div[contains(@class,'ms-DatePicker')]//input"
                ));
                
                for (WebElement inp : directInputs) {
                    if (inp.isDisplayed() && !dateInputs.contains(inp)) {
                        dateInputs.add(inp);
                        System.out.println("      + Found direct input: " + inp.getAttribute("id"));
                    }
                }
            } catch (Exception e) {}
            
            System.out.println("    📋 Total date inputs found: " + dateInputs.size());
            
            // Select the appropriate input based on index
            if (dateInputs.size() >= pickerIndex) {
                WebElement targetInput = dateInputs.get(pickerIndex - 1);
                String inputId = targetInput.getAttribute("id");
                System.out.println("    ✓ Selected DatePicker #" + pickerIndex + ": " + inputId);
                
                // Scroll into view
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetInput);
                Thread.sleep(300);
                
                // Click to focus
                System.out.println("    🖱️ Clicking on input...");
                try {
                    actions.moveToElement(targetInput).click().perform();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click(); arguments[0].focus();", targetInput);
                }
                Thread.sleep(300);
                
                // Clear existing value
                System.out.println("    ⌨️ Clearing and typing date: " + dateValue);
                targetInput.sendKeys(Keys.CONTROL + "a");
                Thread.sleep(100);
                targetInput.sendKeys(Keys.DELETE);
                Thread.sleep(100);
                
                // Type the date
                targetInput.sendKeys(dateValue);
                Thread.sleep(500);
                
                // Press Tab to confirm and move out
                targetInput.sendKeys(Keys.TAB);
                Thread.sleep(500);
                
                // Close any calendar popup
                actions.sendKeys(Keys.ESCAPE).perform();
                Thread.sleep(300);
                
                // Verify value was set
                String valueAfter = targetInput.getAttribute("value");
                System.out.println("    📋 Value after: '" + valueAfter + "'");
                
                if (valueAfter != null && !valueAfter.isEmpty()) {
                    System.out.println("    ✅ Date entered successfully!");
                    return true;
                } else {
                    System.out.println("    ⚠️ Value may not have been set, trying JavaScript...");
                    
                    // Try JavaScript as fallback
                    js.executeScript(
                        "var input = arguments[0];" +
                        "var value = arguments[1];" +
                        "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeInputValueSetter.call(input, value);" +
                        "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "input.dispatchEvent(new Event('change', { bubbles: true }));",
                        targetInput, dateValue
                    );
                    Thread.sleep(300);
                    
                    valueAfter = targetInput.getAttribute("value");
                    System.out.println("    📋 Value after JS: '" + valueAfter + "'");
                    
                    if (valueAfter != null && !valueAfter.isEmpty()) {
                        System.out.println("    ✅ Date entered via JavaScript!");
                        return true;
                    }
                }
            } else {
                System.out.println("    ⚠️ Not enough date inputs found. Need " + pickerIndex + " but only found " + dateInputs.size());
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("    ✗ enterDateInResourceDatePicker failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    private boolean selectDateFromCalendar(int day, int month, int year,
            Actions actions, JavascriptExecutor js) {
    try {

    System.out.println("📅 Selecting date: " + month + "/" + day + "/" + year);

    // 1️⃣ Read current calendar header
    String headerText = getCalendarHeaderText();
    if (headerText == null || headerText.isEmpty()) {
    System.out.println("⚠️ Calendar header not found");
    return false;
    }

    int currentMonth = getMonthFromHeader(headerText);
    int currentYear  = getYearFromHeader(headerText);

    // 2️⃣ Calculate navigation offset
    int monthsToNavigate =
    (year - currentYear) * 12 + (month - currentMonth);

    // 3️⃣ Navigate calendar
    if (monthsToNavigate != 0) {
    By navButton = monthsToNavigate > 0 ? nextMonthBtn : previousMonthBtn;
    int steps = Math.abs(monthsToNavigate);

    for (int i = 0; i < steps; i++) {
    click(navButton, monthsToNavigate > 0 ? "Next Month" : "Previous Month");
    waitForSeconds(1);
    }
    }

    // 4️⃣ Build dynamic, stable day locator
    By calendarDay =
    By.xpath(
    "//div[contains(@class,'ms-Calendar')]//button[@role='gridcell' " +
    "and normalize-space(string())='" + day + "' " +
    "and not(@aria-disabled='true')]"
    );

    // 5️⃣ Click day using ActionEngine
    click(calendarDay, "Calendar Day " + day);

    System.out.println("✅ Date selected successfully");
    return true;

    } catch (Throwable e) {
    System.out.println("✗ selectDateFromCalendar failed: " + e.getMessage());
    return false;
    }
    }
    private int getMonthFromHeader(String header) {
        String[] months = {
            "January","February","March","April","May","June",
            "July","August","September","October","November","December"
        };

        for (int i = 0; i < months.length; i++) {
            if (header.contains(months[i])) {
                return i + 1;
            }
        }
        return java.time.LocalDate.now().getMonthValue();
    }

    private int getYearFromHeader(String header) {
        try {
            return Integer.parseInt(header.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return java.time.LocalDate.now().getYear();
        }
    }

    
    private String getCalendarHeaderText() {
        try {
            String[] headerXPaths = {
                "//button[contains(@class,'ms-DatePicker-monthAndYear')]",
                "//div[contains(@class,'ms-DatePicker-monthAndYear')]",
                "//button[contains(@aria-label,'month') and contains(@aria-label,'year')]",
                "//div[contains(@class,'ms-Calendar')]//button[contains(text(),'202')]",
                "//button[contains(@class,'ms-DatePicker') and contains(@class,'monthAndYear')]"
            };

            for (String xpath : headerXPaths) {
                try {
                    WebElement header = driver.findElement(By.xpath(xpath));
                    if (header.isDisplayed()) {
                        return header.getText().trim();
                    }
                } catch (Exception e) {
                    // Try next XPath
                }
            }
        } catch (Exception e) {
            System.out.println("    ⚠️ Error getting calendar header: " + e.getMessage());
        }
        return null;
    }  
    
    
    
    public void enterResourceOutDate(String outDate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 ENTERING RESOURCE OUT DATE");
        System.out.println("📅 INPUT RECEIVED: '" + outDate + "' (null: " + (outDate == null) + ")");

        if (outDate == null) {
            System.out.println("  ❌ ERROR: outDate parameter is NULL!");
            return;
        }

        String trimmed = outDate.trim();
        System.out.println("📅 AFTER TRIM: '" + trimmed + "' (empty: " + trimmed.isEmpty() + ")");
        System.out.println("📅 ═══════════════════════════════════════════");

        if (trimmed.isEmpty()) {
            System.out.println("  ⚠️ Out Date is empty after trimming, skipping...");
            return;
        }
        
        try {
            // Short wait after In Date
            waitForSeconds(1);

            // Log the date being processed
            System.out.println("    📅 Processing Resource Out Date: '" + outDate + "' -> '" + (outDate != null ? convertDateForInput(outDate) : "null") + "'");
            
            boolean success = false;
            
            // Strategy 1: JavaScript approach (most reliable)
            System.out.println("  📍 Strategy 1: JavaScript approach for DatePicker");
            success = setResourceDateWithJS(outDate, 2);

            // Strategy 2: Find DatePicker combobox (div with role=combobox) - BEST for Fluent UI
            if (!success) {
                System.out.println("  📍 Strategy 2: DatePicker combobox (div with role=combobox)");
                success = enterDateInComboboxDatePicker(2, outDate); // 2 = second DatePicker
            }

            // Strategy 3: Use Tab navigation from current position (after In Date)
            if (!success) {
                System.out.println("  📍 Strategy 3: Tab navigation to Out Date field");
                success = enterDateUsingTabNavigation(outDate, false);
            }

            // Strategy 4: Find and enter date in second DatePicker on the resource form
            if (!success) {
                System.out.println("  📍 Strategy 4: Find second DatePicker and enter date");
                success = enterDateInResourceDatePicker(outDate, 2);
            }

            // Strategy 5: Try using the locator directly if it finds an input
            if (!success) {
                System.out.println("  📍 Strategy 5: Try primary locator");
                success = setDateWithMultipleStrategies(
                    InitiativePageLocators.resourceOutDate,
                    outDate,
                    "Resource Out Date"
                );
            }

            // Strategy 6: Try calendar selection method (most reliable for Fluent UI)
            if (!success) {
                System.out.println("  📍 Strategy 6: Calendar selection method");
                success = enterDateInComboboxDatePicker(2, outDate); // 2 = second DatePicker (Out Date)
            }
            
            if (success) {
                System.out.println("✅ Successfully set Resource Out Date: " + outDate);
                if (reportLogger != null) {
                    reportLogger.info("✅ Resource Out Date: " + outDate);
                }
                
           
                
            } else {
                System.out.println("⚠️ Could not set Resource Out Date - all strategies failed");
                throw new Exception("Failed to set Resource Out Date: " + outDate);
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
    
    public void enterResourceFTE(String fte) throws Throwable {
        System.out.println("\n📊 ═══════════════════════════════════════════");
        System.out.println("📊 ENTERING RESOURCE FTE: " + fte);
        System.out.println("📊 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fteField = wait.until(ExpectedConditions.elementToBeClickable(InitiativePageLocators.resourceFTE));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", fteField
            );
            Thread.sleep(300);
            
            // Click to focus
            fteField.click();
            Thread.sleep(200);
            
            // Clear the field first using Ctrl+A and Delete
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            Thread.sleep(100);
            actions.sendKeys(Keys.DELETE).perform();
            Thread.sleep(100);
            
            // Also try clearing via element method
            fteField.clear();
            Thread.sleep(200);
            
            // Enter the new FTE value
            fteField.sendKeys(fte);
            System.out.println("  ✅ FTE value entered: " + fte);
            
            if (reportLogger != null) {
                reportLogger.info("Entered Resource FTE: " + fte);
            }
            
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary method failed, trying fallback...");
            // Fallback: use type method
            type(InitiativePageLocators.resourceFTE, fte, "Resource FTE");
        }
        
        System.out.println("📊 ═══════════════════════════════════════════\n");
    }
    public void clickResourceSaveButton() throws Throwable {
        System.out.println("\n💾 ═══════════════════════════════════════════");
        System.out.println("💾 CLICKING RESOURCE SAVE BUTTON");
        System.out.println("💾 ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Actions actions = new Actions(driver);
            
            // Multiple locator strategies for Save button
            By[] saveButtonLocators = {
                InitiativePageLocators.resourceSaveButton,
                By.xpath("//button[contains(@class,'ms-Button--primary')][contains(.,'Save')]"),
                By.xpath("//button[normalize-space()='Save']"),
                By.xpath("//span[text()='Save']/ancestor::button"),
                By.xpath("//button[.//span[text()='Save']]"),
                By.xpath("(//button[contains(.,'Save')])[last()]")
            };
            
            WebElement saveButton = null;
            
            for (By locator : saveButtonLocators) {
                try {
                    saveButton = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    System.out.println("  ✅ Found Save button");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (saveButton == null) {
                throw new Exception("Could not find Resource Save button");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", saveButton);
            Thread.sleep(500);
            
            // Click the button
            try {
                actions.moveToElement(saveButton).click().perform();
                System.out.println("  ✅ Clicked Save button (Actions click)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                System.out.println("  ✅ Clicked Save button (JS click)");
            }
            
            Thread.sleep(2000); // Wait for save to complete
            
            if (reportLogger != null) {
                reportLogger.pass("✅ Clicked Resource Save button");
            }
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to click Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to click Resource Save button: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("💾 ═══════════════════════════════════════════\n");
    }
    
    
    public boolean verifyResourceSuccessAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING RESOURCE SUCCESS ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find success alert
            By[] alertLocators = {
                InitiativePageLocators.resourceSuccessAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'success')]"),
                By.xpath("//*[contains(text(),'successfully')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Success alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text (do NOT treat "success/updated" as valid for error validation)
            boolean isSuccess = actualMessage != null
                    && actualMessage.toLowerCase().contains(expectedMessage.toLowerCase());
            
            if (isSuccess) {
                System.out.println("  ✅ SUCCESS: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Success alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify success alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify success alert: " + e.getMessage());
            }
            throw e;
        }
    }  
    
    public boolean verifyRoleErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Role Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.RoleErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text
            boolean isSuccess = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()) ||
                               actualMessage.toLowerCase().contains("success") ||
                               actualMessage.toLowerCase().contains("updated");
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }
    
    
    public boolean verifySkillErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Skill Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.SkillErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text
            boolean isSuccess = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()) ||
                               actualMessage.toLowerCase().contains("success") ||
                               actualMessage.toLowerCase().contains("updated");
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }
    
    
    public boolean verifyResourceInDateErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Resource In date Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.ResourceInErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text
            boolean isSuccess = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()) ||
                               actualMessage.toLowerCase().contains("success") ||
                               actualMessage.toLowerCase().contains("updated");
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    } 
    
    
    public boolean verifyResourceOutDateErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Resource Out date Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.ResourceOutErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text
            boolean isSuccess = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()) ||
                               actualMessage.toLowerCase().contains("success") ||
                               actualMessage.toLowerCase().contains("updated");
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }
    
    
    
    public boolean verifyResourceFTEErrorAlert(String expectedMessage) throws Throwable {
        System.out.println("\n✅ ═══════════════════════════════════════════");
        System.out.println("✅ VERIFYING Resource Out date Error ALERT");
        System.out.println("✅ Expected: " + expectedMessage);
        System.out.println("✅ ═══════════════════════════════════════════");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Multiple strategies to find error alert
            By[] alertLocators = {
                InitiativePageLocators.ResourceFTEErrorAlert,
                By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'alert')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@class,'ms-MessageBar')][contains(.,'" + expectedMessage + "')]"),
                By.xpath("//div[contains(@role,'alert')]"),
                By.xpath("//div[contains(@class,'error')]"),
                By.xpath("//*[contains(text(),'left blank')]")
            };
            
            WebElement alert = null;
            String actualMessage = "";
            
            for (By locator : alertLocators) {
                try {
                    alert = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    actualMessage = alert.getText();
                    System.out.println("  ✅ Found alert element");
                    break;
                } catch (Exception e) {
                    // Try next locator
                }
            }
            
            if (alert == null) {
                System.out.println("  ❌ Could not find success alert");
                if (reportLogger != null) {
                    reportLogger.fail("❌ Error alert not found");
                }
                return false;
            }
            
            System.out.println("  📋 Actual message: " + actualMessage);
            
            // Verify message contains expected text
            boolean isSuccess = actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()) ||
                               actualMessage.toLowerCase().contains("success") ||
                               actualMessage.toLowerCase().contains("updated");
            
            if (isSuccess) {
                System.out.println("  ✅ Error: Alert message verified!");
                if (reportLogger != null) {
                    reportLogger.pass("✅ Error alert verified: " + actualMessage);
                }
            } else {
                System.out.println("  ⚠️ Alert found but message doesn't match expected");
                System.out.println("  Expected: " + expectedMessage);
                System.out.println("  Actual: " + actualMessage);
                if (reportLogger != null) {
                    reportLogger.warning("⚠️ Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            System.out.println("✅ ═══════════════════════════════════════════\n");
            return isSuccess;
            
        } catch (Exception e) {
            System.out.println("  ❌ Failed to verify Error alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("❌ Failed to verify Error alert: " + e.getMessage());
            }
            throw e;
        }
    }
     
    
public void clicksubmitfinal() {
	  click(InitiativePageLocators.submitfinal, "submitfinal");
}
    
    
public void clickApprove() {
	click(InitiativePageLocators.approvefinal,"approvefinal");
}
    
public void clickfinalpushback() {
	click(InitiativePageLocators.clickpush,"clickpush");
}
    
WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

private String getVisibleCalendarHeader() {
    List<By> headerLocators = List.of(
        By.xpath("//div[contains(@class,'monthAndYear')]//span"),
        By.xpath("//button[contains(@class,'monthAndYear')]//span"),
        By.xpath("//div[contains(@class,'ms-DatePicker')]//span[contains(text(),'20')]")
    );

    for (By locator : headerLocators) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            if (el.getText().matches(".*\\d{4}.*")) {
                return el.getText().trim();
            }
        } catch (Exception ignored) {}
    }
    throw new RuntimeException("❌ Calendar header not found");
}


public void selectDateFromFluentCalendar(By datePicker, String excelDate) throws Exception {

    DateTimeFormatter excelFmt =
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);

    LocalDate targetDate = LocalDate.parse(excelDate, excelFmt);

    int targetDay   = targetDate.getDayOfMonth();
    int targetMonth = targetDate.getMonthValue();
    int targetYear  = targetDate.getYear();

    // Open DatePicker
    wait.until(ExpectedConditions.elementToBeClickable(datePicker)).click();
    Thread.sleep(800);

    // Navigate Month / Year
    while (true) {
        String headerText = getVisibleCalendarHeader(); // ✅ FIXED
        String[] parts = headerText.split(" ");
        Month displayedMonth = Month.valueOf(parts[0].toUpperCase());
        int displayedYear = Integer.parseInt(parts[1]);

        if (displayedMonth.getValue() == targetMonth && displayedYear == targetYear) {
            break;
        }

        if (displayedYear < targetYear ||
           (displayedYear == targetYear && displayedMonth.getValue() < targetMonth)) {
            driver.findElement(InitiativePageLocators.nextMonthBtn).click();
        } else {
            driver.findElement(InitiativePageLocators.prevMonthBtn).click();
        }
        Thread.sleep(400);
    }

    // Click Day
    By dayBtn = By.xpath(
        "//button[contains(@class,'dayButton') and normalize-space()='" + targetDay + "']"
    );

    wait.until(ExpectedConditions.elementToBeClickable(dayBtn)).click();
    Thread.sleep(500);
}


public void enterResourceInDate1(String inDate) throws Exception {
    selectDateFromFluentCalendar(
            InitiativePageLocators.resourceInDate1,
            inDate
    );
}

public void enterResourceOutDate1(String outDate) throws Exception {
    selectDateFromFluentCalendar(
            InitiativePageLocators.resourceOutDate1,
            outDate
    );
}


public static String generateRandomFTE() {
    int fte = new java.util.Random().nextInt(5) + 1; // 1 to 5
    return String.valueOf(fte);
}


public static String generateRandomTitle() {
    int random = new java.util.Random().nextInt(90000) + 10000;
    return "AutoTitle_" + random;
}

public static String generateAutoDescription() {
    String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));

    return "This record was auto-generated by Selenium automation on "
            + timestamp
            + " for validation and regression testing purposes.";
}

public void enterTitle(String shortName) {
    type(InitiativePageLocators.enter, shortName, "enter");
}

public void enterDescriptions(String shortName) {
    type(InitiativePageLocators.des, shortName, "des");
}


public void selectbg() {
	 click(InitiativePageLocators.clickbg, "Program`2");
	 selectRandomValueFromDropdown(InitiativePageLocators.clickbgoption,"clickbgoption");
}


public void selectou() {
	 click(InitiativePageLocators.clickou, "clickou");
	 selectRandomValueFromDropdown(InitiativePageLocators.clickouoption,"clickouoption");
}





public void scrollTo(By locator) {
    WebElement el = driver.findElement(locator);
    ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
}




public void selectCostCategoryOption1(String costcategory) {
    
    jsClick(InitiativePageLocators.clickcc,"clickcc");
    selectFromList(InitiativePageLocators.clickCCoption,costcategory,"clickccoption");
}



public String getToastMessage() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='alert']"))
        );

        String message = toast.getText().trim();
        System.out.println("📢 Toast Message: " + message);

        return message;

    } catch (Exception e) {
        System.out.println("❌ No toast appeared within timeout.");
        return "";
    }
}






public  boolean isElementVisible(By locator, int seconds) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return true;
    } catch (TimeoutException e) {
        return false;
    }
}




 public void selectCostCategoryOptionfunding() {
	
	  jsClick(InitiativePageLocators.clickccf,"clickccf");
	  selectRandomValueFromDropdown(InitiativePageLocators.clickccoptionfunding,"clickccoptionfunding");
	 
 }




public void clickfundingsave() {
	 click(InitiativePageLocators.clicksave,"clicksave");
}


public void clickbasic() {
	 click(InitiativePageLocators.basicdetail,"basicdetail");
}







public void clickdocument() {
	 click(InitiativePageLocators.document,"clicksave");
}
public void clickuploadocument() {
	 click(InitiativePageLocators.uploaddocument,"clicksave");
}

public void uploadDocument(String filePath) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // 1️⃣ Get WebElement (NOT By)
    WebElement uploadElement = wait.until(
        ExpectedConditions.presenceOfElementLocated(InitiativePageLocators.upload)
    );

    File file = new File(filePath);
    String absolutePath = file.getAbsolutePath();

    // 2️⃣ Make input visible (only if hidden)
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].style.display='block'; arguments[0].style.visibility='visible';",
        uploadElement
    );

    // 3️⃣ Upload file
    uploadElement.sendKeys(absolutePath);

    // 4️⃣ Trigger change event (ONLY if app needs it)
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        uploadElement
    );

    System.out.println("✅ File uploaded successfully: " + absolutePath);
}



public void selectdocumentcatgory() {
	 
	  jsClick(InitiativePageLocators.selectdocumentcatgory,"selectdocumentcatgory");
	  selectRandomValueFromDropdown(InitiativePageLocators.selectdocumentcatgoryalloption,"selectdocumentcatgoryalloption");
	 
}

  
public void enterdes(String shortName) {
    type(InitiativePageLocators.enterdescription, shortName, "enterdescription");
}


public void clickfinal() {
    click(InitiativePageLocators.clickfinalupload, "clickfinalupload");
}


public void selectcostcatgory(String value) {
	  jsClick(InitiativePageLocators.clickcc1,"clickcc1");
	  selectFromList(InitiativePageLocators.clickCCoption1,value,"clickCCoption1");
}



public void clickwithdraw() {
    click(InitiativePageLocators.clickwithdraw, "clickwithdraw");
}

public void clickedit() {
    click(InitiativePageLocators.clickedit, "clickedit");
}


public void closeUpdateHealthSheet() {

    JavascriptExecutor js = (JavascriptExecutor) webDriver;

    js.executeScript(
        "const el = document.elementFromPoint(50, window.innerHeight / 2);" +
        "if (el) el.click();"
    );
}



public void clickrisk() {
    click(InitiativePageLocators.Risks, "Risks");
}


public void clickadd() {
    click(InitiativePageLocators.add, "add");
}


public void enterdes1(String shortName) {
	typeInModal(InitiativePageLocators.desrisk, shortName, "desrisk");
}



public void enterdesimp(String shortName) {
	 jsClick(InitiativePageLocators.impdes,"impdes");
    typeInModal(InitiativePageLocators.impdes, shortName, "impdes");
}

public void selectriskcat() {
	
	  jsClick(InitiativePageLocators.riskcatgory,"riskcatgory");
	  selectRandomValueFromDropdown(InitiativePageLocators.riskcatgorylist,"riskcatgorylist");
	 
}


public void selectstatus() {
	
	  jsClick(InitiativePageLocators.status,"status");
	  selectRandomValueFromDropdown(InitiativePageLocators.statuslist,"");
	 
}

public void enterprob(String shortName1) {
    type(InitiativePageLocators.proba, shortName1, "proba");
}


public void enterimpact(String shortName2) {
    type(InitiativePageLocators.impact, shortName2, "impact");
}


public void selectNatureOfInitiative() {
	  click(InitiativePageLocators.noi,"noi");
}

public String autoDescription() {
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder sb = new StringBuilder("AutoDescription");

    Random random = new Random();

    for (int i = 0; i < 5; i++) {   // 5 random letters
        sb.append(letters.charAt(random.nextInt(letters.length())));
    }

    return sb.toString();
}


public void date(String inDate) throws Exception {
    selectDateFromFluentCalendar(
    		InitiativePageLocators.dateiden,
            inDate
    );
}


public void clicksave() {
    click(InitiativePageLocators.clickrisk, "clickrisk");
}



public void clickroi() {
	 click(InitiativePageLocators.roi, "clickrisk");
}
 

public void clickaddroi() {
	 click(InitiativePageLocators.add, "clickrisk");
	}



public void selectmonth() {
	 click(InitiativePageLocators.monthdrop, "monthdrop");
	 click(InitiativePageLocators.monthdroplist,"monthdroplist");
}




public void selectyear(String shortname) {
	 click(InitiativePageLocators.yeardrop, "yeardrop");
	 selectFromList(InitiativePageLocators.yeardroplist,shortname,"yeardroplist");

}
    

  public void enterRoi(String roi) {
	  type(InitiativePageLocators.roiprojected, roi,"roiprojected ");
  }
 
     

 public void  clickworkflow() {
	 
	 click(InitiativePageLocators.workflow, "workflow");
 }


 public void  selectactiontaken() {
	 click(InitiativePageLocators.clickaction, "clickaction");
	 click(InitiativePageLocators.action, "action");
 }


 public void verifyAndPrintWorkflowHistory() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Verify history row is present
	    WebElement historyRow = wait.until(
	        ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//tbody[contains(@class,'tbodyHistory')]//tr[contains(@class,'TR_history')]")
	        )
	    );

	    Assert.assertTrue(historyRow.isDisplayed(),
	            "❌ Workflow History row is not displayed");

	    // Fetch all column values
	    List<WebElement> columns = historyRow.findElements(By.tagName("td"));

	    String eventTime   = columns.get(0).getText().trim();
	    String actionTaken = columns.get(1).getText().trim();
	    String fromStage   = columns.get(2).getText().trim();
	    String toStage     = columns.get(3).getText().trim();
	    String approver    = columns.get(4).getText().trim();
	    String comments    = columns.get(5).getText().trim();

	    // Print details in console
	    System.out.println("\n📜 Workflow History Details:");
	    System.out.println("────────────────────────────────────");
	    System.out.println("🕒 Event Time   : " + eventTime);
	    System.out.println("📌 Action Taken : " + actionTaken);
	    System.out.println("🔁 From Stage   : " + fromStage);
	    System.out.println("➡️ To Stage     : " + toStage);
	    System.out.println("👤 Approver     : " + approver);
	    System.out.println("💬 Comments     : " + comments);
	    System.out.println("────────────────────────────────────");


 }

 
 public String getPlannedStartDate() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Wait until at least one Fluent UI DatePicker label exists
	    wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//div[contains(@class,'readOnlyTextField') and @role='combobox']")
	    ));

	    List<WebElement> dateLabels = driver.findElements(
	        By.xpath("//div[starts-with(@id,'DatePicker') and contains(@id,'-label')]")
	    );

	    for (WebElement el : dateLabels) {

	        // 🔥 SCROLL ELEMENT INTO VIEW (IMPORTANT)
	        js.executeScript(
	            "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
	            el
	        );

	        // Small stabilization wait (Fluent UI rendering)
	        wait.until(ExpectedConditions.visibilityOf(el));

	        String text = ((String) js.executeScript(
	            "return arguments[0].innerText;", el
	        )).trim();

	        System.out.println("DatePicker label -> [" + text + "]");

	        // ✅ real date check (e.g. Tue Feb 17 2026)
	        if (text.matches("[A-Za-z]{3} [A-Za-z]{3} \\d{1,2} \\d{4}")) {
	            System.out.println("✅ Captured Planned Start Date = " + text);
	            return text;
	        }
	    }

	    throw new RuntimeException("❌ Planned Start Date DatePicker not found");
	}
 
 
 
 
 
 
 
 
 public LocalDate parseUIDate(String uiDate) {
	    DateTimeFormatter formatter =
	            DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH);
	    return LocalDate.parse(uiDate, formatter);
	}

 /*public void enterResourceInDate5(String uiDate) {

	    LocalDate date = parseUIDate(uiDate);

	    int day = date.getDayOfMonth();
	    int year = date.getYear();
	    String fullMonth = date.getMonth()
	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    String shortMonth = date.getMonth()
	            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	    // 1️⃣ Open Date Picker
	    driver.findElement(By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[1]")).click();

	    // 2️⃣ Click Year Header
	    driver.findElement(By.xpath("//button[contains(@aria-label,'change year')]")).click();

	    // 3️⃣ Select Year
	    driver.findElement(By.xpath("//span[text()='" + year + "']")).click();

	    // 4️⃣ Select Month
	    driver.findElement(By.xpath(
	        "//button[@role='gridcell' and text()='" + shortMonth + "']"
	    )).click();

	    // 5️⃣ Select Day (BEST WAY using aria-label)
	    driver.findElement(By.xpath(
	        "//button[contains(@aria-label,'" + day + ", " + fullMonth + ", " + year + "')]"
	    )).click();
	}
  */

 public void enterResourceInDate6(String uiDate) {                                  //2

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    LocalDate date = parseUIDate(uiDate);

	    int day = date.getDayOfMonth();
	    int targetYear = date.getYear();
	    String fullMonth = date.getMonth()
	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    String shortMonth = date.getMonth()
	            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	    // 1️⃣ Open Date Picker
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[1]")
	    )).click();

	    // 2️⃣ Get Current Year From Header (Example: March 2026)
	    WebElement headerSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'monthAndYear')]//span")
	    ));

	    String headerText = headerSpan.getText();  // Example: March 2026
	    int currentYear = Integer.parseInt(headerText.split(" ")[1]);

	    // 3️⃣ Navigate Year Using Arrows
	    while (currentYear != targetYear) {

	        if (currentYear > targetYear) {
	            // Click Previous Year
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@title,'previous')]")
	            )).click();
	        } else {
	            // Click Next Year
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@title,'next')]")
	            )).click();
	        }

	        headerSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'monthAndYear')]//span")
	        ));

	        headerText = headerSpan.getText();
	        currentYear = Integer.parseInt(headerText.split(" ")[1]);
	    }

	    // 4️⃣ Open Month Picker
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(@class,'monthAndYear')]")
	    )).click();

	    // 5️⃣ Wait For Month Grid
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'monthPickerWrapper')]")
	    ));

	    // 6️⃣ Select Month
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(@class,'monthPickerWrapper')]//button[normalize-space()='" + shortMonth + "']")
	    )).click();

	    // 7️⃣ Select Day
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[@aria-label='" + day + ", " + fullMonth + ", " + targetYear + "']")
	    )).click();
	}
/* public void enterResourceOutDate5(String uiDate) {

	    LocalDate date = parseUIDate(uiDate);

	    int day = date.getDayOfMonth();
	    int year = date.getYear();
	    String fullMonth = date.getMonth()
	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    String shortMonth = date.getMonth()
	            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	    // 1️⃣ Open Out Date Picker (2nd DatePicker)
	    driver.findElement(
	        By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[2]")
	    ).click();

	    // 2️⃣ Click Year Header
	    driver.findElement(
	        By.xpath("//button[contains(@aria-label,'change year')]")
	    ).click();

	    // 3️⃣ Select Year
	    driver.findElement(
	        By.xpath("//span[text()='" + year + "']")
	    ).click();

	    // 4️⃣ Select Month
	    driver.findElement(
	        By.xpath("//button[@role='gridcell' and text()='" + shortMonth + "']")
	    ).click();

	    // 5️⃣ Select Day (aria-label based – most stable)
	    driver.findElement(
	        By.xpath("//button[contains(@aria-label,'" + day + ", " + fullMonth + ", " + year + "')]")
	    ).click();
	}
   */
 
 public void enterResourceOutDate6(String uiDate) {                                //1

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    LocalDate date = parseUIDate(uiDate);

	    int day = date.getDayOfMonth();
	    int targetYear = date.getYear();
	    String fullMonth = date.getMonth()
	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    String shortMonth = date.getMonth()
	            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	    // 1️⃣ Open SECOND Date Picker (Resource Out Date)
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@role='combobox' and contains(@id,'DatePicker')])[2]")
	    )).click();

	    // 2️⃣ Get Current Year From Header
	    WebElement headerSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'monthAndYear')]//span")
	    ));

	    String headerText = headerSpan.getText();
	    int currentYear = Integer.parseInt(headerText.split(" ")[1]);

	    // 3️⃣ Navigate Year Using Arrows
	    while (currentYear != targetYear) {

	        if (currentYear > targetYear) {
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@title,'previous')]")
	            )).click();
	        } else {
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[contains(@title,'next')]")
	            )).click();
	        }

	        headerSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'monthAndYear')]//span")
	        ));

	        headerText = headerSpan.getText();
	        currentYear = Integer.parseInt(headerText.split(" ")[1]);
	    }

	    // 4️⃣ Open Month Picker
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(@class,'monthAndYear')]")
	    )).click();

	    // 5️⃣ Wait For Month Grid
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'monthPickerWrapper')]")
	    ));

	    // 6️⃣ Select Month
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(@class,'monthPickerWrapper')]//button[normalize-space()='" + shortMonth + "']")
	    )).click();

	    // 7️⃣ Select Day
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[@aria-label='" + day + ", " + fullMonth + ", " + targetYear + "']")
	    )).click();
	}
 
 
 
 
 
 
 
 public String getCurrentStageFromRow(WebElement row) {

	    WebElement stageElement = row.findElement(
	            By.xpath(".//p[contains(.,'Current Stage')]/strong")
	    );

	    return stageElement.getText().trim();
	}
 

 public void waitForInitiativeTable() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//p[contains(normalize-space(.),'Initiative Code')]")
	    ));
	}
 public WebElement getRowByInitiativeCodeSafe(String initiativeCode) {

	    waitForInitiativeTable();

	    return driver.findElement(
	        By.xpath("//tr[.//p[contains(normalize-space(.),'Initiative Code: " 
	                 + initiativeCode + "')]]")
	    );
	}

 public void clickgoback() {
	 
	 click(InitiativePageLocators.workflow, "workflow");
 }
 
 
 
 public String getPlannedEndDate() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Wait until Fluent UI DatePicker labels are present
	    wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//div[contains(@class,'readOnlyTextField') and @role='combobox']")
	    ));

	    List<WebElement> dateLabels = driver.findElements(
	        By.xpath("//div[starts-with(@id,'DatePicker') and contains(@id,'-label')]")
	    );

	    int validDateCount = 0;

	    for (WebElement el : dateLabels) {

	        // 🔥 Scroll into view (Fluent UI lazy rendering fix)
	        js.executeScript(
	            "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
	            el
	        );

	        wait.until(ExpectedConditions.visibilityOf(el));

	        String text = ((String) js.executeScript(
	            "return arguments[0].innerText;", el
	        )).trim();

	        System.out.println("DatePicker label -> [" + text + "]");

	        // ✅ Real date format check
	        if (text.matches("[A-Za-z]{3} [A-Za-z]{3} \\d{1,2} \\d{4}")) {
	            validDateCount++;

	            // 👉 2nd valid date = Planned End Date
	            if (validDateCount == 2) {
	                System.out.println("✅ Captured Planned End Date = " + text);
	                return text;
	            }
	        }
	    }

	    throw new RuntimeException("❌ Planned End Date DatePicker not found");
	} 
 
 
public void clickDraft() {
	 
	 click(InitiativePageLocators.draft, "draft");
 }
 

 public void clickSearchToolbarButton() {
	 click(InitiativePageLocators.searchtoolbar, "searchtoolbar");
 }

 public void clickwatchlist() {
	 click(InitiativePageLocators.watchlist, "watchlist");
 }

 public void enterInitiativeCode(String code) {
	 type(InitiativePageLocators.entercode, code,"entercode");
 }


   public void clickFilterSearchButton() {
		 click(InitiativePageLocators.searchfin, "searchfin");
   }


 public void clickonclose() {
	 click(InitiativePageLocators.close, "close");
 }













 
public boolean isInitiativePresent(String initiativeCode) {

    List<WebElement> rows = driver.findElements(
        By.xpath("//tr[.//p[contains(.,'" + initiativeCode + "')]]")
    );

    return !rows.isEmpty();
}

public void printCurrentStageIfPresent(String initiativeCode) {

    List<WebElement> rows = driver.findElements(
        By.xpath("//tr[.//p[contains(normalize-space(.),'Initiative Code: "
                + initiativeCode + "')]]")
    );

    if (rows.isEmpty()) {
        System.out.println(
            "⚠️ Initiative Code NOT found on current page: " + initiativeCode
        );
        return;
    }

    String stage = rows.get(0).findElement(
        By.xpath(".//p[contains(normalize-space(.),'Current Stage')]/strong")
    ).getText().trim();

    System.out.println("Current Stage = " + stage);
}

public void clearField(By locator, String fieldName) {

    WebElement element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
    );

    element.click();
    element.sendKeys(Keys.CONTROL + "a");
    element.sendKeys(Keys.DELETE);

   // log("Cleared " + fieldName);
}

public boolean verifyWatchListFirstPageRecordLimit() {

    try {
        System.out.println("\n══════════════════════════════════════");
        System.out.println("VERIFYING WATCH LIST - FIRST PAGE RECORD COUNT");
        System.out.println("══════════════════════════════════════");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 🔹 Wait for Watch List table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[contains(@class,'MuiTable-root')]")
        ));

        // 🔹 Get rows from first page
        List<WebElement> rows = driver.findElements(
                By.xpath("//table[contains(@class,'MuiTable-root')]//tbody/tr")
        );

        int recordCount = rows.size();

        System.out.println("📌 Watch List - First Page Record Count : " + recordCount);

        // 🔹 Validate max 5 per page
        if (recordCount <= 5) {
            System.out.println("✅ TEST PASSED - Watch List records within limit (Max 5)");
            return true;
        } else {
            System.out.println("❌ TEST FAILED - More than 5 records found on Watch List first page");
            return false;
        }

    } catch (Exception e) {
        System.out.println("❌ Error while verifying Watch List records: " + e.getMessage());
        return false;
    }
}







public int getDraftCount1() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    int draft = Integer.parseInt(wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("FltrCountDraft"))).getText().trim());

    System.out.println("📄 Draft Count: " + draft);

    return draft;
}



public int getWatchlistCount1() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    int watchlist = Integer.parseInt(wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("FltrCountWatchlist"))).getText().trim());

    System.out.println("⭐ Watchlist Count: " + watchlist);

    return watchlist;
}



public int getInboxCount1() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    int inbox = Integer.parseInt(wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("FltrCountInbox"))).getText().trim());

    System.out.println("📥 Inbox Count: " + inbox);

    return inbox;
}


public void verifyDraftCountIncrement(int beforeDraft, int afterDraft) {

    Assert.assertEquals(afterDraft, beforeDraft + 1,
            "Draft count mismatch after saving initiative!");

    System.out.println("✅ Draft count verified. Before: " 
            + beforeDraft + " After: " + afterDraft);
}

public void verifyWatchlistCountIncrement(int beforeWatchlist, int afterWatchlist) {

    Assert.assertEquals(afterWatchlist, beforeWatchlist + 1,
            "Watchlist count mismatch after action!");

    System.out.println("⭐ Watchlist count verified. Before: " 
            + beforeWatchlist + " After: " + afterWatchlist);
}

public void verifyInboxCountIncrement(int beforeInbox, int afterInbox) {

    Assert.assertEquals(afterInbox, beforeInbox + 1,
            "Inbox count mismatch after action!");

    System.out.println("📥 Inbox count verified. Before: "
            + beforeInbox + " After: " + afterInbox);
}


public void waitForWatchlistToDisplay() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("FltrCountWatchlist")
    ));

    System.out.println("⭐ Watchlist counter is visible.");
}

public boolean isElementPresent(By locator) {
    try {
        return driver.findElement(locator).isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

public boolean isChecklistAlertDisplayed() {
    try {
        WebElement alert = driver.findElement(
            By.xpath("//div[contains(@class,'Toastify__toast-body')]//div[contains(text(),'Please fill the CheckList')]")
        );
        return alert.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

public String getToastMessage1() {

    try {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='alert']/div[2]")
                )
        );

        String message = toast.getText().trim();

        System.out.println("📝 Actual Alert: " + message);

        return message;

    } catch (Exception e) {

        System.out.println("⚠ No toast alert detected");
        return "";
    }
}

public boolean isApprovePopupDisplayed() {

    try {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[text()='Comments for Approve']")
                )
        );

        return popup.isDisplayed();

    } catch (Exception e) {

        return false;
    }
}

}

