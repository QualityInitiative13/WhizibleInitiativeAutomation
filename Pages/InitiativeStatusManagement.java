package Pages;

import Actions.ActionEngine;
import Locators.InitiativeStatusManagementPageLocators;
import Locators.ProjectPageLocator;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Initiative Status Management
 *
 * This class is intended to hold all interactions related to
 * Initiative status life‑cycle (e.g. submit, approve, hold, reject).
 *
 * NOTE:
 * - Keep ONLY status‑related behaviours in this page object.
 * - Navigation to the generic Initiative list or creation pages should
 *   continue to live in existing `InitiativePage` / `InitiativeManagementPage`
 *   classes.
 *
 * You can gradually move converted / refactored methods from
 * `InitiativePage` into this class as the status workflow
 * is separated.
 *
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeStatusManagement extends ActionEngine {

    private final WebDriver driver;
    private final ExtentTest reportLogger;
    private Integer popupIframeIndex = null; // Track which iframe contains the popup

    /**
     * Constructor with WebDriver and Logger.
     *
     * @param driver       WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeStatusManagement(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // ====================================================================
    // NAVIGATION
    // ====================================================================

    /**
     * Click on Initiative Management module and then on
     * Initiative Status Management page link from the left navigation.
     */
    public void navigateToInitiativeStatusManagementPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);

            // Step 1: Click on Initiative Management navigation (module)
            By[] navLocators = new By[]{
                    // Re‑use the same strategies as InitiativeManagementPage
                    Locators.InitiativeManagementPageLocators.initiativeManagementNav,
                    By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']"),
                    By.xpath("//img[@alt='Initiative Management']"),
                    By.xpath("//div[contains(@class,'navigation')]//img[contains(@alt,'Initiative')]")
            };

            WebElement initiativeNav = null;
            for (By locator : navLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        initiativeNav = element;
                        break;
                    }
                } catch (Exception ignore) {
                    // try next locator
                }
            }

            if (initiativeNav == null) {
                throw new Exception("Initiative Management navigation not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", initiativeNav);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeNav);
            } catch (Exception e1) {
                try {
                    actions.moveToElement(initiativeNav).click().perform();
                } catch (Exception e2) {
                    initiativeNav.click();
                }
            }

            // Wait for child panel to expand
            waitForSeconds(2);

            // Step 2: Click on Initiative Status Management page link
            // XPath provided by user: //*[@id="children-panel-container"]/div[3]/div[11]/p
            By statusPageLinkBy = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[11]/p");
            WebElement statusPageLink = wait.until(ExpectedConditions.elementToBeClickable(statusPageLinkBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", statusPageLink);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusPageLink);
            } catch (Exception e1) {
                try {
                    actions.moveToElement(statusPageLink).click().perform();
                } catch (Exception e2) {
                    statusPageLink.click();
                }
            }

            waitForSeconds(3);

        } catch (Exception e) {
            log.error("Error navigating to Initiative Status Management page", e);
            throw e;
        }
    }

    // ====================================================================
    // SEARCH PANEL INTERACTIONS
    // ====================================================================

    /**
     * Select action "Resubmit Initiatives" from Action dropdown.
     *
     * XPaths provided by user:
     *  - Dropdown: //*[@id="Dropdown773"]/span[2]
     *  - Value:    //*[@id="Dropdown438-list4"]/span/span
     *
     * Reuses the same robust dropdown‑opening strategy as other actions.
     */
    public void selectActionResubmitInitiatives() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Primary: label‑based locator for Action dropdown
            By primaryDropdown = By.xpath("//label[text()='Action']/following::div[@role='combobox'][1]");

            // Fallbacks: id‑based and generic Action dropdowns
            By[] fallbackDropdowns = new By[]{
                    By.xpath("//*[@id=\"Dropdown773\"]/span[2]"),
                    By.xpath("//label[contains(normalize-space(),'Action')]/following::*[self::div or self::button][1]"),
                    By.xpath("//div[contains(@class,'css')][.//label[contains(normalize-space(),'Action')]]"),
                    By.xpath("//button[contains(@aria-haspopup,'listbox') and .//span[contains(normalize-space(),'Action')]]")
            };

            WebElement dropdown = null;
            try {
                dropdown = wait.until(ExpectedConditions.elementToBeClickable(primaryDropdown));
            } catch (Exception e) {
                log.warn("Primary Action dropdown locator failed for Resubmit, trying fallbacks", e);
                for (By locator : fallbackDropdowns) {
                    try {
                        dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                        if (dropdown != null) {
                            break;
                        }
                    } catch (Exception ignore) {
                        // try next locator
                    }
                }
            }

            if (dropdown == null) {
                throw new Exception("Action dropdown for 'Resubmit Initiatives' not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            } catch (Exception e1) {
                dropdown.click();
            }

            // Wait briefly for options to appear
            waitForSeconds(2);
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[@role='listbox'] | //ul[@role='listbox'] | //*[contains(@id,'list')]")));
            } catch (Exception ignore) {
                // continue even if generic listbox locator isn't found
            }

            // Primary: user‑provided value XPath for "Resubmit Initiatives"
            By primaryValue = By.xpath("//*[@id=\"Dropdown438-list4\"]/span/span");

            // Fallbacks: text‑based options
            By[] valueLocators = new By[]{
                    primaryValue,
                    By.xpath("//div[@role='option' and normalize-space()='Resubmit Initiatives']"),
                    By.xpath("//span[normalize-space()='Resubmit Initiatives']"),
                    By.xpath("//li[normalize-space()='Resubmit Initiatives']"),
                    By.xpath("//*[contains(@id,'list')]//span[normalize-space()='Resubmit Initiatives']"),
                    By.xpath("//*[contains(@id,'list')]//*[normalize-space()='Resubmit Initiatives']")
            };

            WebElement valueEl = null;
            for (By locator : valueLocators) {
                try {
                    valueEl = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (valueEl != null && valueEl.isDisplayed()) {
                        break;
                    }
                } catch (Exception ignore) {
                    // try next
                }
            }

            if (valueEl == null) {
                throw new Exception("'Resubmit Initiatives' option not found in Action dropdown");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", valueEl);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", valueEl);
            } catch (Exception e1) {
                valueEl.click();
            }

        } catch (Exception e) {
            log.error("Failed to select 'Resubmit Initiatives' from Action dropdown", e);
            throw e;
        }
    }

    /**
     * Select action "Withdraw Initiative" from Action dropdown.
     *
     * Primary locators use the exact XPaths provided by user:
     *  - Dropdown: //*[@id="Dropdown773"]/span[2]
     *  - Value:    //*[@id="Dropdown2-list3"]/span/span (updated)
     *
     * Text-based locators are kept as fallbacks for stability.
     */
    public void selectActionWithdrawInitiative() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Primary: new, more robust label-based locator (from user)
            By primaryDropdown = By.xpath("//label[text()='Action']/following::div[@role='combobox'][1]");

            // Fallbacks: id-based and generic Action dropdowns
            By[] fallbackDropdowns = new By[]{
                    By.xpath("//*[@id=\"Dropdown773\"]/span[2]"),
                    By.xpath("//label[contains(normalize-space(),'Action')]/following::*[self::div or self::button][1]"),
                    By.xpath("//div[contains(@class,'css')][.//label[contains(normalize-space(),'Action')]]"),
                    By.xpath("//button[contains(@aria-haspopup,'listbox') and .//span[contains(normalize-space(),'Action')]]")
            };

            WebElement dropdown = null;
            try {
                dropdown = wait.until(ExpectedConditions.elementToBeClickable(primaryDropdown));
            } catch (Exception e) {
                log.warn("Primary Action dropdown locator failed, trying fallbacks", e);
                for (By locator : fallbackDropdowns) {
                    try {
                        dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                        if (dropdown != null) {
                            break;
                        }
                    } catch (Exception ignore) {
                        // try next locator
                    }
                }
            }

            if (dropdown == null) {
                throw new Exception("Action dropdown for 'Withdraw Initiative' not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            } catch (Exception e1) {
                dropdown.click();
            }

            // IMPORTANT: Wait for dropdown options to appear after clicking
            System.out.println("  ⏳ Waiting for dropdown options to appear...");
            waitForSeconds(2); // Wait for dropdown list to open
            Thread.sleep(1000); // Additional wait for options to render

            // Wait for dropdown list/popup to be visible
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@role='listbox'] | //ul[@role='listbox'] | //*[contains(@id,'list')]")));
                System.out.println("  ✅ Dropdown list is visible");
            } catch (Exception e) {
                System.out.println("  ⚠️ Dropdown list wait timeout, continuing...");
            }

            // Primary: user-provided value XPath (updated to Dropdown2-list3)
            // First, wait for the Dropdown2-list3 container to be present
            try {
                System.out.println("  🔍 Waiting for Dropdown2-list3 container...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id=\"Dropdown2-list3\"]")));
                System.out.println("  ✅ Dropdown2-list3 container found");
                waitForSeconds(1); // Additional wait for content to render
            } catch (Exception e) {
                System.out.println("  ⚠️ Dropdown2-list3 container wait timeout, continuing...");
            }

            By primaryValue = By.xpath("//*[@id=\"Dropdown2-list3\"]/span/span");
            System.out.println("  🔍 Trying PRIMARY locator: " + primaryValue);

            // Fallbacks: text-based options and old XPath
            By[] valueLocators = new By[]{
                    primaryValue,
                    By.xpath("//*[@id=\"Dropdown997-list3\"]/span/span"), // Old XPath as fallback
                    By.xpath("//div[@role='option' and normalize-space()='Withdraw Initiative']"),
                    By.xpath("//span[normalize-space()='Withdraw Initiative']"),
                    By.xpath("//li[normalize-space()='Withdraw Initiative']"),
                    By.xpath("//*[contains(@id,'list')]//span[normalize-space()='Withdraw Initiative']"),
                    By.xpath("//*[contains(@id,'list')]//*[normalize-space()='Withdraw Initiative']")
            };

            WebElement valueEl = null;
            for (By locator : valueLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    // Use longer timeout for primary locator
                    WebDriverWait locatorWait = (locator == primaryValue) 
                        ? new WebDriverWait(driver, Duration.ofSeconds(10))
                        : wait;
                    valueEl = locatorWait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (valueEl != null && valueEl.isDisplayed()) {
                        System.out.println("  ✅ Found 'Withdraw Initiative' option using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Locator failed: " + locator + " - " + e.getMessage());
                    // try next
                }
            }

            // If still not found, try searching through all visible options
            if (valueEl == null) {
                System.out.println("  🔍 All direct locators failed, searching through all visible options...");
                try {
                    // Try to find options within Dropdown2-list3 container first
                    List<WebElement> containerOptions = driver.findElements(
                        By.xpath("//*[@id=\"Dropdown2-list3\"]//*[@role='option'] | //*[@id=\"Dropdown2-list3\"]//span | //*[@id=\"Dropdown2-list3\"]//li"));
                    System.out.println("  📋 Found " + containerOptions.size() + " option(s) in Dropdown2-list3 container");
                    
                    if (containerOptions.size() > 0) {
                        for (int i = 0; i < containerOptions.size(); i++) {
                            WebElement opt = containerOptions.get(i);
                            try {
                                String optionText = opt.getText();
                                System.out.println("     Option " + (i + 1) + ": '" + optionText + "'");
                                if (optionText != null) {
                                    String normalized = optionText.trim().toLowerCase();
                                    if (normalized.equals("withdraw initiative") || 
                                        normalized.contains("withdraw") || 
                                        normalized.equals("withdraw initiative")) {
                                        valueEl = opt;
                                        System.out.println("  ✅ Found 'Withdraw Initiative' by searching through Dropdown2-list3 container options");
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    
                    // If still not found, try all options in the page
                    if (valueEl == null) {
                        List<WebElement> allOptions = driver.findElements(
                            By.xpath("//div[@role='option'] | //li[@role='option'] | //*[@role='option'] | //*[contains(@id,'list')]//span | //*[contains(@id,'list')]//li"));
                        System.out.println("  📋 Found " + allOptions.size() + " total option(s) in dropdown");
                        
                        for (int i = 0; i < allOptions.size(); i++) {
                            WebElement opt = allOptions.get(i);
                            try {
                                if (!opt.isDisplayed()) continue;
                                String optionText = opt.getText();
                                System.out.println("     Option " + (i + 1) + ": '" + optionText + "'");
                                if (optionText != null) {
                                    String normalized = optionText.trim().toLowerCase();
                                    if (normalized.equals("withdraw initiative") || 
                                        normalized.contains("withdraw") ||
                                        (normalized.contains("withdraw") && normalized.contains("initiative"))) {
                                        valueEl = opt;
                                        System.out.println("  ✅ Found 'Withdraw Initiative' by searching through all visible options");
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    
                    // Last resort: try by index if list3 means 3rd option
                    if (valueEl == null) {
                        System.out.println("  🔍 Trying to find option by index (list3 might mean 3rd option)...");
                        try {
                            List<WebElement> indexedOptions = driver.findElements(
                                By.xpath("//*[contains(@id,'list')]//*[@role='option'] | //*[contains(@id,'list')]//span | //*[contains(@id,'list')]//li"));
                            if (indexedOptions.size() >= 3) {
                                valueEl = indexedOptions.get(2); // 3rd option (0-indexed)
                                System.out.println("  ✅ Found option by index (3rd option)");
                            }
                        } catch (Exception e) {
                            System.out.println("  ⚠️ Could not find option by index: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Could not search through options: " + e.getMessage());
                }
            }

            if (valueEl == null) {
                throw new Exception("'Withdraw Initiative' option not found in Action dropdown after trying all locators and searching through visible options");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", valueEl);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", valueEl);
            } catch (Exception e1) {
                valueEl.click();
            }

        } catch (Exception e) {
            log.error("Failed to select 'Withdraw Initiative' from Action dropdown", e);
            throw e;
        }
    }

    /**
     * Select action "Mark Initiatives as Complete" from Action dropdown.
     *
     * Primary locators use the exact XPaths provided by user:
     *  - Dropdown: //*[@id="Dropdown773"]/span[2]
     *  - Value:    //*[@id="Dropdown773-list2"]/span/span
     *
     * Text-based locators are kept as fallbacks for stability.
     */
    public void selectActionMarkInitiativesAsComplete() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Primary: new, more robust label-based locator (from user)
            By primaryDropdown = By.xpath("//label[text()='Action']/following::div[@role='combobox'][1]");

            // Fallbacks: id-based and generic Action dropdowns
            By[] fallbackDropdowns = new By[]{
                    By.xpath("//*[@id=\"Dropdown773\"]/span[2]"),
                    By.xpath("//label[contains(normalize-space(),'Action')]/following::*[self::div or self::button][1]"),
                    By.xpath("//div[contains(@class,'css')][.//label[contains(normalize-space(),'Action')]]"),
                    By.xpath("//button[contains(@aria-haspopup,'listbox') and .//span[contains(normalize-space(),'Action')]]")
            };

            WebElement dropdown = null;
            try {
                dropdown = wait.until(ExpectedConditions.elementToBeClickable(primaryDropdown));
            } catch (Exception e) {
                log.warn("Primary Action dropdown locator (Dropdown773) failed, trying fallbacks", e);
                for (By locator : fallbackDropdowns) {
                    try {
                        dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                        if (dropdown != null) {
                            break;
                        }
                    } catch (Exception ignore) {
                        // try next locator
                    }
                }
            }

            if (dropdown == null) {
                throw new Exception("Action dropdown for 'Mark Initiatives as Complete' not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            } catch (Exception e1) {
                dropdown.click();
            }

            // Primary: user-provided value XPath
            By primaryValue = By.xpath("//*[@id=\"Dropdown773-list2\"]/span/span");

            // Fallbacks: text-based options
            By[] valueLocators = new By[]{
                    primaryValue,
                    By.xpath("//div[@role='option' and normalize-space()='Mark Initiatives as Complete']"),
                    By.xpath("//span[normalize-space()='Mark Initiatives as Complete']"),
                    By.xpath("//li[normalize-space()='Mark Initiatives as Complete']")
            };

            WebElement valueEl = null;
            for (By locator : valueLocators) {
                try {
                    valueEl = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (valueEl != null) {
                        break;
                    }
                } catch (Exception ignore) {
                    // try next
                }
            }

            if (valueEl == null) {
                throw new Exception("'Mark Initiatives as Complete' option not found in Action dropdown");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", valueEl);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", valueEl);
            } catch (Exception e1) {
                valueEl.click();
            }

        } catch (Exception e) {
            log.error("Failed to select 'Mark Initiatives as Complete' from Action dropdown", e);
            throw e;
        }
    }

    /**
     * Click on the Show button (used after selecting Action = Mark Initiatives as Complete).
     *
     * Primary: user-provided XPath //*[@id="id__774"]
     * Fallbacks: text‑based locators.
     */
    public void clickShowButton() throws Exception {
        By primaryBy = By.xpath("//*[@id=\"id__774\"]");
        By textBy = By.xpath("//button[.//span[normalize-space()='Show'] or normalize-space()='Show']");
        By altBy = By.xpath("//span[normalize-space()='Show']/ancestor::button[1]");

        try {
            clickElementWithWait(primaryBy, "Show button (id__774)");
        } catch (Exception e) {
            log.warn("Primary Show button locator (id__774) failed, trying text-based locators", e);
            try {
                clickElementWithWait(textBy, "Show button (text-based)");
            } catch (Exception e2) {
                log.warn("Text-based Show button locator failed, trying ancestor::button fallback", e2);
                clickElementWithWait(altBy, "Show button (alternative ancestor::button)");
            }
        }
    }

    /**
     * Click on the top Search button (icon) on Initiative Status Management page.
     *
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img
     */
    public void clickSearchToolbarButton() throws Exception {
        By searchIconBy = By.xpath("//img[@aria-label='Search']");
        clickElementWithWait(searchIconBy, "Search toolbar button");
    }

    /**
     * Select Nature of Initiative = "Full Change Lifecycle".
     *
     * Dropdown XPath: //*[@id="natureofDemandID"]/span[2]
     * Value XPath:    //*[@id="natureofDemandID-list3"]/span/span
     */
    public void selectNatureOfInitiativeFullChangeLifecycle() throws Exception {
        By dropdownBy = By.xpath("//*[@id=\"natureofDemandID\"]/span[2]");
        By valueBy = By.xpath("//*[@id=\"natureofDemandID-list3\"]/span/span");
        selectDropdownValue(dropdownBy, valueBy, "Nature of Initiative - Full Change Lifecycle");
    }

    /**
     * Select Business Group = "Tata Group".
     *
     * Dropdown XPath: //*[@id="businessGroupId"]/span[2]
     * Value XPath:    //*[@id="businessGroupId-list4"]/span/span
     */
    public void selectBusinessGroupTataGroup() throws Exception {
        By dropdownBy = By.xpath("//*[@id=\"businessGroupId\"]/span[2]");
        By valueBy = By.xpath("//*[@id=\"businessGroupId-list4\"]/span/span");
        selectDropdownValue(dropdownBy, valueBy, "Business Group - Tata Group");
    }

    /**
     * Select Organization Unit = "Tata Motors".
     *
     * Dropdown XPath: //*[@id="organizationUnitId"]/span[2]
     * Value XPath:    //*[@id="organizationUnitId-list2"]/span/span
     */
    public void selectOrganizationUnitTataMotors() throws Exception {
        By dropdownBy = By.xpath("//*[@id=\"organizationUnitId\"]/span[2]");
        By valueBy = By.xpath("//*[@id=\"organizationUnitId-list2\"]/span/span");
        selectDropdownValue(dropdownBy, valueBy, "Organization Unit - Tata Motors");
    }

    /**
     * Enter Initiative Code (from Excel) into Initiative Code text field.
     *
     * XPath: //*[@id="demandCode"]
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        // Reset iframe index when interacting with main page
        resetPopupIframeIndex();
        By codeBy = By.xpath("//*[@id=\"demandCode\"]");
        typeIntoField(codeBy, initiativeCode, "Initiative Code");
    }

    /**
     * Enter Initiative Title (from Excel) into Initiative Title text field.
     *
     * XPath: //*[@id="title"]
     */
    public void enterInitiativeTitle(String initiativeTitle) throws Exception {
        By titleBy = By.xpath("//*[@id=\"title\"]");
        typeIntoField(titleBy, initiativeTitle, "Initiative Title");
    }

    /**
     * Click on the bottom Search button in the filter panel.
     *
     * Primary XPath: //button[.//span[normalize-space()='Search']]
     * (Fallback to legacy id-based XPath if needed)
     */
    public void clickFilterSearchButton() throws Exception {
        // New, more robust locator suggested by user
        By primaryBy = By.xpath("//button[.//span[normalize-space()='Search']]");
        // Legacy locator as fallback (in case DOM differs in some environments)
        By fallbackBy = By.xpath("//*[@id=\"id__578\"]");

        try {
            clickElementWithWait(primaryBy, "Filter Search button (text-based)");
        } catch (Exception primaryEx) {
            log.warn("Primary Search button locator failed, trying fallback id-based locator", primaryEx);
            clickElementWithWait(fallbackBy, "Filter Search button (fallback id__578)");
        }
    }

    /**
     * Click on the 2nd page number in the pagination control on Initiative Status Management page.
     *
     * Primary XPath provided by user:
     *   //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]/span/svg/path
     *
     * We click the ancestor button of this SVG path to ensure the real clickable element is used.
     */
    public void clickSecondPageInPagination() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        By svgPathBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]/span/svg/path");
        By buttonBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]");

        try {
            // Prefer clicking the button element
            clickElementWithWait(buttonBy, "Pagination - Page 2 button");
        } catch (Exception e) {
            // Fallback: locate the SVG/path and then its ancestor button
            log.warn("Primary Page 2 button locator failed, trying SVG path ancestor", e);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement svgPath = wait.until(ExpectedConditions.presenceOfElementLocated(svgPathBy));
            WebElement button = svgPath.findElement(By.xpath("./ancestor::button[1]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (Exception e2) {
                button.click();
            }
        }
        waitForSeconds(2);
    }

    /**
     * Get the number of data rows currently visible in the Initiative Status grid.
     * Used by TC_008 to verify that exactly 5 records are shown per page.
     */
    public int getCurrentStatusGridRowCount() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Try standard HTML table rows first
        By tableRowsBy = By.xpath("//table//tbody/tr");
        // Fallback for AG-Grid style rows
        By agRowsBy = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[contains(@class,'ag-row') and not(contains(@class,'ag-row-position-absolute'))]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            java.util.List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsBy));
            int count = (rows == null) ? 0 : rows.size();
            System.out.println("  ℹ️ Current Status grid row count (HTML table) = " + count);
            return count;
        } catch (Exception e) {
            log.warn("Table row locator failed for Status grid, trying AG-Grid row locator", e);
            try {
                java.util.List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(agRowsBy));
                int count = (rows == null) ? 0 : rows.size();
                System.out.println("  ℹ️ Current Status grid row count (AG-Grid) = " + count);
                return count;
            } catch (Exception e2) {
                log.error("Failed to determine Status grid row count", e2);
                return 0;
            }
        }
    }

    // ====================================================================
    // INTERNAL HELPER METHODS
    // ====================================================================

    private void clickElementWithWait(By locator, String elementName) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = null;
            
            // If we know which iframe has the popup, try that first
            if (popupIframeIndex != null) {
                try {
                    driver.switchTo().defaultContent();
                    driver.switchTo().frame(popupIframeIndex);
                    element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    log.info("Found element in known popup iframe " + popupIframeIndex + " for: " + elementName);
                } catch (Exception e0) {
                    log.warn("Element not found in known popup iframe " + popupIframeIndex + ", trying default content", e0);
                    driver.switchTo().defaultContent();
                }
            }
            
            // Try default content if not found in popup iframe
            if (element == null) {
                try {
                    driver.switchTo().defaultContent();
                    element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                } catch (Exception e1) {
                    // If not found, try scanning iframes
                    log.warn("Element not found in default content, scanning iframes for: " + elementName);
                    driver.switchTo().defaultContent();
                    waitForSeconds(1);
                    
                    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                    for (int i = 0; i < iframes.size(); i++) {
                        try {
                            driver.switchTo().frame(i);
                            waitForSeconds(1);
                            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            log.info("Found element in iframe " + i + " for: " + elementName);
                            if (popupIframeIndex == null) {
                                popupIframeIndex = i; // Store iframe index for future interactions
                            }
                            break;
                        } catch (Exception e2) {
                            driver.switchTo().defaultContent();
                        }
                    }
                    
                    if (element == null) {
                        driver.switchTo().defaultContent();
                        throw e1; // Re-throw original exception if not found in any iframe
                    }
                }
            }
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception e1) {
                element.click();
            }
        } catch (Exception e) {
            log.error("Failed to click element: " + elementName, e);
            // Don't switch back to default content if we're in a popup iframe - stay there
            if (popupIframeIndex == null) {
                driver.switchTo().defaultContent();
            }
            throw e;
        }
    }

    private void selectDropdownValue(By dropdownBy, By valueBy, String description) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            } catch (Exception e1) {
                dropdown.click();
            }

            WebElement value = wait.until(ExpectedConditions.elementToBeClickable(valueBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", value);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", value);
            } catch (Exception e1) {
                value.click();
            }

        } catch (Exception e) {
            log.error("Failed to select dropdown value for: " + description, e);
            throw e;
        }
    }

    private void typeIntoField(By locator, String text, String fieldName) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement field = null;
            
            // If we know which iframe has the popup, try that first
            if (popupIframeIndex != null) {
                try {
                    driver.switchTo().defaultContent();
                    driver.switchTo().frame(popupIframeIndex);
                    field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    log.info("Found element in known popup iframe " + popupIframeIndex + " for: " + fieldName);
                } catch (Exception e0) {
                    log.warn("Element not found in known popup iframe " + popupIframeIndex + ", trying default content", e0);
                    driver.switchTo().defaultContent();
                }
            }
            
            // Try default content if not found in popup iframe
            if (field == null) {
                try {
                    driver.switchTo().defaultContent();
                    field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                } catch (Exception e1) {
                    // If not found, try scanning iframes
                    log.warn("Element not found in default content, scanning iframes for: " + fieldName);
                    driver.switchTo().defaultContent();
                    waitForSeconds(1);
                    
                    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                    for (int i = 0; i < iframes.size(); i++) {
                        try {
                            driver.switchTo().frame(i);
                            waitForSeconds(1);
                            field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                            log.info("Found element in iframe " + i + " for: " + fieldName);
                            if (popupIframeIndex == null) {
                                popupIframeIndex = i; // Store iframe index for future interactions
                            }
                            break;
                        } catch (Exception e2) {
                            driver.switchTo().defaultContent();
                        }
                    }
                    
                    if (field == null) {
                        driver.switchTo().defaultContent();
                        throw e1; // Re-throw original exception if not found in any iframe
                    }
                }
            }
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", field);
            waitForSeconds(1);
            field.clear();
            field.sendKeys(text);
        } catch (Exception e) {
            log.error("Failed to type into field: " + fieldName, e);
            // Don't switch back to default content if we're in a popup iframe - stay there
            if (popupIframeIndex == null) {
                driver.switchTo().defaultContent();
            }
            throw e;
        }
    }

    // ====================================================================
    // SNOOZE ACTION (TC_002)
    // ====================================================================

    /**
     * Click on the Action link for the row that contains the given Initiative Code.
     *
     * Uses a text-based locator on the grid row instead of a hard-coded row index,
     * so it will click the Action button even if the Initiative is not on the first row.
     *
     * Example XPath (built dynamically):
     * //table//tbody//tr[.//td[contains(normalize-space(),'20210534')]]//td[6]//button
     */
    public void clickActionLinkForInitiative(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        String dynamicXpath = String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]//td[6]//button",
                initiativeCode);
        By actionLinkBy = By.xpath(dynamicXpath);
        clickElementWithWait(actionLinkBy, "Action link for initiative code: " + initiativeCode);
        
        // Wait for popup to appear - check for any of the common popup elements
        waitForSnoozePopupToAppear();
    }
    
    /**
     * Wait for the Snooze/Resubmit/Withdraw/Mark Complete popup to appear.
     * This method checks for common popup elements to ensure the popup has loaded.
     * If the popup is found in an iframe, it stores the iframe index for subsequent interactions.
     */
    private void waitForSnoozePopupToAppear() throws Exception {
        driver.switchTo().defaultContent();
        popupIframeIndex = null; // Reset iframe index
        waitForSeconds(2);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Try to find any of the common popup elements
        By[] popupIndicators = new By[] {
            // Number of Days field (for Snooze)
            By.xpath("//label[contains(text(),'Number of Days')]"),
            By.xpath("//input[@id='TextField495']"),
            By.xpath("//input[@id='TextField386']"),
            // Comment field (any action)
            By.xpath("//*[@id='TextField501']"),
            By.xpath("//*[@id='TextField1089']"),
            By.xpath("//*[@id='TextField852']"),
            By.xpath("//*[@id='TextField349']"),
            // Save button (any action)
            By.xpath("//*[@id='id__498']"),
            By.xpath("//*[@id='id__1086']"),
            By.xpath("//*[@id='id__849']"),
            By.xpath("//*[@id='id__341']"),
            // Generic popup indicators
            By.xpath("//div[contains(@class,'modal') or contains(@role,'dialog')]"),
            By.xpath("//button[.//span[normalize-space()='Save']]")
        };
        
        boolean popupFound = false;
        for (By indicator : popupIndicators) {
            try {
                // Try default content first
                wait.until(ExpectedConditions.presenceOfElementLocated(indicator));
                popupFound = true;
                log.info("Popup detected in default content using indicator: " + indicator);
                break;
            } catch (Exception e) {
                // Try scanning iframes
                try {
                    List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                    for (int i = 0; i < iframes.size(); i++) {
                        try {
                            driver.switchTo().frame(i);
                            wait.until(ExpectedConditions.presenceOfElementLocated(indicator));
                            popupFound = true;
                            popupIframeIndex = i; // Store the iframe index
                            log.info("Popup detected in iframe " + i + " using indicator: " + indicator + " - staying in this iframe");
                            // DON'T switch back to default content - stay in the iframe
                            break;
                        } catch (Exception e2) {
                            driver.switchTo().defaultContent();
                        }
                    }
                    if (popupFound) break;
                } catch (Exception e3) {
                    // Continue to next indicator
                }
            }
        }
        
        if (!popupFound) {
            log.warn("Popup not detected after waiting, but continuing anyway...");
            driver.switchTo().defaultContent();
        } else if (popupIframeIndex == null) {
            // Popup found in default content
            log.info("Popup found in default content, staying in default content");
        }
        
        waitForSeconds(1);
    }
    
    /**
     * Reset the popup iframe index. Call this when the popup is closed or when navigating away from the popup.
     */
    private void resetPopupIframeIndex() {
        popupIframeIndex = null;
        driver.switchTo().defaultContent();
        log.debug("Reset popup iframe index and switched to default content");
    }

    /**
     * Check whether a row for the given Initiative Code is present in the Initiative Status grid.
     * This is used in TC_006 to verify that the initiative has NOT been snoozed after cancelling.
     *
     * @param initiativeCode initiative identifier to search for
     * @return true if a matching row is present, false otherwise
     */
    public boolean isInitiativePresentInStatusGrid(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        String rowXpath = String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]",
                initiativeCode);
        By rowLocator = By.xpath(rowXpath);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            java.util.List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
            for (WebElement row : rows) {
                try {
                    if (row.isDisplayed()) {
                        String text = row.getText();
                        if (text != null && text.contains(initiativeCode)) {
                            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' row present in Initiative Status grid");
                            return true;
                        }
                    }
                } catch (Exception ignore) {
                    // continue checking other rows
                }
            }
            System.out.println("  ❌ Initiative Code '" + initiativeCode + "' row NOT found in Initiative Status grid");
            return false;
        } catch (Exception e) {
            System.out.println("  ❌ Initiative Code '" + initiativeCode + "' row NOT found in Initiative Status grid: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    /**
     * Enter number of days for Snooze.
     *
     * Primary XPath (from user): //label[contains(text(),'Number of Days')]/following::input[@type='number'][1]
     * Fallbacks: id-based and absolute XPaths.
     */
    public void enterSnoozeNumberOfDays(String days) throws Exception {
        By primaryBy = By.xpath("//label[contains(text(),'Number of Days')]/following::input[@type='number'][1]");
        By idPrimaryBy = By.xpath("//input[@id='TextField495']");
        By idFallback1By = By.xpath("//input[@id='TextField386']");
        By absoluteBy = By.xpath("/html/body/div[3]/div[3]/div/div[4]/div[1]/div/div/div/div/div/input");
        By labelNormalizeBy = By.xpath("//label[normalize-space()='Number of Days']/following::input[@type='number'][1]");
        By idFallback2By = By.xpath("//*[@id=\"TextField344\"]");
        try {
            typeIntoField(primaryBy, days, "Snooze Number of Days (label contains text)");
        } catch (Exception e) {
            log.warn("Primary label-based Number of Days locator failed, trying TextField495", e);
            try {
                typeIntoField(idPrimaryBy, days, "Snooze Number of Days (id=TextField495)");
            } catch (Exception e2) {
                log.warn("TextField495 locator failed, trying TextField386 fallback", e2);
                try {
                    typeIntoField(idFallback1By, days, "Snooze Number of Days (id=TextField386)");
                } catch (Exception e3) {
                    log.warn("TextField386 locator failed, trying absolute XPath locator", e3);
                    try {
                        typeIntoField(absoluteBy, days, "Snooze Number of Days (absolute XPath)");
                    } catch (Exception e4) {
                        log.warn("Absolute Number of Days locator failed, trying normalize-space label-based locator", e4);
                        try {
                            typeIntoField(labelNormalizeBy, days, "Snooze Number of Days (label normalize-space)");
                        } catch (Exception e5) {
                            log.warn("Normalize-space label-based Number of Days locator failed, trying legacy id-based fallback", e5);
                            typeIntoField(idFallback2By, days, "Snooze Number of Days (legacy id=TextField344)");
                        }
                    }
                }
            }
        }
    }

    /**
     * Enter comment for Snooze.
     *
     * Primary: id-based locator for current popup
     * Fallbacks: label-based and other id/textarea patterns (to support older flows).
     */
    public void enterSnoozeComment(String comment) throws Exception {
        // Primary for Resubmit flow
   
    	  type(InitiativeStatusManagementPageLocators.snoozecomment, comment, "comment");
    	  
       
    }

    public void enterSnoozeComment1(String comment) throws Exception {
        // Primary for Resubmit flow
   
    	  type(InitiativeStatusManagementPageLocators.snoozecomment1, comment, "comment");
    	  
       
    }
    
    /**
     * Click on the Save button on Snooze / Mark Complete popup.
     *
     * Primary: user-provided id for current popup (id__849)
     * Fallbacks: text-based "Save" and legacy id (id__341).
     */
    public void clickSnoozeSaveButton() throws Exception {
    	 click(InitiativeStatusManagementPageLocators.savebutton ,"savebutton");
    }

    /**
     * Click on the Ok button on confirmation popup.
     *
     * Primary: user-provided id for current popup (id__859)
     * Fallbacks: text-based "Ok" and legacy id (id__374).
     */
    public void clickSnoozeConfirmationOkButton() throws Exception {
   	 click(InitiativeStatusManagementPageLocators.savebuttonok ,"savebutton");
    }

    /**
     * Click on the Cancel button on the Snooze / Resubmit / Mark Complete confirmation popup.
     *
     * Primary: absolute XPath for Mark Complete confirmation popup (user-provided)
     * Fallback: id-based locator (id__48) and text-based "Cancel" button.
     */
    public void clickSnoozeConfirmationCancelButton() throws Exception {
        // Primary: absolute XPath for Mark Complete confirmation popup (user-provided for TC_007)
        By markCompleteCancelBy = By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div[2]/div/div[2]/div[2]/div/span[2]/button/span/span/span");
        // Fallback: id-based locator (id__48) - used for Snooze/Resubmit
        By idCancelBy = By.xpath("//*[@id=\"id__48\"]");
        // Fallback: text-based "Cancel" button
        By textCancelBy = By.xpath("//button[.//span[normalize-space()='Cancel'] or normalize-space()='Cancel']");
        
        try {
            clickElementWithWait(markCompleteCancelBy, "Cancel button (Mark Complete confirmation - absolute XPath)");
        } catch (Exception e1) {
            log.warn("Mark Complete Cancel button locator failed, trying id-based locator (id__48)", e1);
            try {
                clickElementWithWait(idCancelBy, "Cancel button (id=id__48)");
            } catch (Exception e2) {
                log.warn("Cancel button locator (id__48) failed, trying text-based 'Cancel' button", e2);
                clickElementWithWait(textCancelBy, "Cancel button (text-based)");
            }
        }
        // Reset iframe index after popup closes
        waitForSeconds(2);
        resetPopupIframeIndex();
    }

    /**
     * Click on the cross (X) icon to close the Snooze / Resubmit / Mark Complete popup.
     *
     * Primary: absolute XPath for Mark Complete popup (user-provided for TC_007)
     * Fallback: CSS selector and XPath equivalents, generic close buttons.
     */
    public void clickSnoozePopupCloseIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        // Primary: XPath for Mark Complete popup close icon (user-provided for TC_007)
        // New robust locator:
        //   //div[@class='d-flex justify-content-between align-items-center']//span[@class='ms-Button-flexContainer flexContainer-196']//*[name()='svg']
        By markCompleteCloseBy = By.xpath("//div[@class='d-flex justify-content-between align-items-center']//span[@class='ms-Button-flexContainer flexContainer-196']//*[name()='svg']");
        
        // CSS selector provided by user (for Snooze/Resubmit)
        By primaryCssBy = By.cssSelector("div.d-flex.justify-content-between.align-items-center > button.ms-Button.ms-Button--icon.root-282 > span.ms-Button-flexContainer.flexContainer-191 > svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeMedium.css-vubbuv > path");
        
        // XPath equivalent of the CSS selector
        By primaryXPathBy = By.xpath("//div[contains(@class,'d-flex') and contains(@class,'justify-content-between') and contains(@class,'align-items-center')]/button[contains(@class,'ms-Button') and contains(@class,'ms-Button--icon') and contains(@class,'root-282')]/span[contains(@class,'ms-Button-flexContainer') and contains(@class,'flexContainer-191')]/svg[contains(@class,'MuiSvgIcon-root') and contains(@class,'MuiSvgIcon-fontSizeMedium') and contains(@class,'css-vubbuv')]/path");
        
        // Try to find parent button to click (more reliable than clicking path directly)
        By parentButtonBy = By.xpath("//div[contains(@class,'d-flex') and contains(@class,'justify-content-between') and contains(@class,'align-items-center')]/button[contains(@class,'ms-Button') and contains(@class,'ms-Button--icon') and contains(@class,'root-282')]");
        
        By[] fallbackLocators = new By[] {
                // Previous SVG-based locators
                By.xpath("//svg[@data-testid='CloseIcon']"),
                By.xpath("//button[.//svg[@data-testid='CloseIcon']]"),
                // Previous absolute XPath as fallback
                By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/button/span/svg/path"),
                By.xpath("/html/body/div[2]/div[3]/div/div[1]/div/button/span/svg"),
                By.xpath("//div[contains(@class,'modal') or contains(@role,'dialog')]//button[.='×' or contains(text(),'×')]"),
                By.xpath("//button[@aria-label='Close' or contains(@title,'Close')]"),
                By.xpath("//button[contains(@class,'close') or contains(@class,'ms-Dialog-button--close')]")
        };

        WebElement closeEl = null;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        boolean foundInIframe = false;
        
        // Try primary absolute XPath for Mark Complete popup first (user-provided for TC_007)
        try {
            closeEl = wait.until(ExpectedConditions.elementToBeClickable(markCompleteCloseBy));
            // If we found the SVG element, find its parent button to click
            try {
                closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
            } catch (Exception e) {
                // If no button parent, keep the SVG element
            }
            System.out.println("  ✅ Found popup close icon using Mark Complete absolute XPath");
        } catch (Exception e0) {
            // Try primary CSS selector (user-provided for Snooze/Resubmit)
            try {
                closeEl = wait.until(ExpectedConditions.elementToBeClickable(primaryCssBy));
                // If we found the path element, find its parent button to click
                try {
                    closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
                } catch (Exception e) {
                    // If no button parent, try parent button directly
                    try {
                        closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                    } catch (Exception e2) {
                        // Keep the path element if button not found
                    }
                }
                System.out.println("  ✅ Found popup close icon using primary CSS selector");
            } catch (Exception e1) {
                // Try XPath equivalent
                try {
                    closeEl = wait.until(ExpectedConditions.elementToBeClickable(primaryXPathBy));
                    try {
                        closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
                    } catch (Exception e) {
                        try {
                            closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                        } catch (Exception e2) {
                            // Keep the path element if button not found
                        }
                    }
                    System.out.println("  ✅ Found popup close icon using primary XPath equivalent");
                } catch (Exception e2) {
                    // Try parent button directly
                    try {
                        closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                        System.out.println("  ✅ Found popup close button using parent button locator");
                    } catch (Exception e3) {
                        // All primary locators failed, will try iframe scanning
                        log.warn("All primary close icon locators failed in default content, scanning iframes");
                    }
                }
            }
        }
        
        // If not found in default content, try scanning iframes
        if (closeEl == null) {
            log.warn("Primary close icon locators failed in default content, scanning iframes");
            // Try scanning iframes
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    waitForSeconds(1);
                    try {
                        closeEl = wait.until(ExpectedConditions.elementToBeClickable(markCompleteCloseBy));
                        try {
                            closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
                        } catch (Exception e4) {
                            // Keep the SVG element
                        }
                        System.out.println("  ✅ Found popup close icon in iframe " + i + " using Mark Complete absolute XPath");
                        foundInIframe = true;
                        break;
                    } catch (Exception e2) {
                        try {
                            closeEl = wait.until(ExpectedConditions.elementToBeClickable(primaryCssBy));
                            try {
                                closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
                            } catch (Exception e4) {
                                try {
                                    closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                                } catch (Exception e5) {
                                    // Keep the path element
                                }
                            }
                            System.out.println("  ✅ Found popup close icon in iframe " + i + " using primary CSS selector");
                            foundInIframe = true;
                            break;
                        } catch (Exception e3) {
                            try {
                                closeEl = wait.until(ExpectedConditions.elementToBeClickable(primaryXPathBy));
                                try {
                                    closeEl = closeEl.findElement(By.xpath("./ancestor::button[1]"));
                                } catch (Exception e6) {
                                    try {
                                        closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                                    } catch (Exception e7) {
                                        // Keep the path element
                                    }
                                }
                                System.out.println("  ✅ Found popup close icon in iframe " + i + " using primary XPath");
                                foundInIframe = true;
                                break;
                            } catch (Exception e8) {
                                try {
                                    closeEl = wait.until(ExpectedConditions.elementToBeClickable(parentButtonBy));
                                    System.out.println("  ✅ Found popup close button in iframe " + i + " using parent button");
                                    foundInIframe = true;
                                    break;
                                } catch (Exception e9) {
                                    // Continue to next iframe
                                }
                            }
                        }
                    }
                } catch (Exception e10) {
                    driver.switchTo().defaultContent();
                }
            }
            
            if (!foundInIframe) {
                driver.switchTo().defaultContent();
                log.warn("Primary close icon locators failed, trying fallbacks");
                for (By loc : fallbackLocators) {
                    try {
                        closeEl = wait.until(ExpectedConditions.elementToBeClickable(loc));
                        if (closeEl != null && closeEl.isDisplayed()) {
                            System.out.println("  ✅ Found popup close button using fallback locator: " + loc);
                            break;
                        }
                    } catch (Exception ignore) {
                        // try next
                    }
                }
                
                // If still not found, try fallbacks in iframes
                if (closeEl == null) {
                    for (int i = 0; i < iframes.size(); i++) {
                        try {
                            driver.switchTo().frame(i);
                            waitForSeconds(1);
                            for (By loc : fallbackLocators) {
                                try {
                                    closeEl = wait.until(ExpectedConditions.elementToBeClickable(loc));
                                    if (closeEl != null && closeEl.isDisplayed()) {
                                        System.out.println("  ✅ Found popup close button in iframe " + i + " using fallback locator: " + loc);
                                        break;
                                    }
                                } catch (Exception ignore) {
                                    // try next
                                }
                            }
                            if (closeEl != null) break;
                        } catch (Exception e2) {
                            driver.switchTo().defaultContent();
                        }
                    }
                }
            }
        }

        if (closeEl == null) {
            throw new Exception("Close (X) icon/button for Snooze/Resubmit popup not found");
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeEl);
        waitForSeconds(1);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeEl);
        } catch (Exception e) {
            closeEl.click();
        }

        waitForSeconds(2); // allow popup to close
        // Reset iframe index after popup closes
        resetPopupIframeIndex();
    }

    /**
     * Example placeholder:
     * Navigate to a specific Initiative record by code and open status panel.
     *
     * @param initiativeCode Initiative unique code
     */
    public void openInitiativeStatus(String initiativeCode) throws Exception {
        // To be implemented later if a dedicated "status view" exists.
        log.info("openInitiativeStatus() placeholder called. Code: " + initiativeCode);
    }

    /**
     * Example placeholder:
     * Change status of the Initiative and persist the change.
     *
     * @param newStatus New status value (e.g. SUBMITTED / APPROVED / ON_HOLD)
     */
    public void changeInitiativeStatus(String newStatus) throws Exception {
        // To be implemented later for actual status transitions.
        log.info("changeInitiativeStatus() placeholder called. Status: " + newStatus);
    }

    /**
     * Example placeholder:
     * Verify that Initiative status matches the expected value.
     *
     * @param expectedStatus Expected status
     * @return true if status matches, otherwise false
     */
    public boolean verifyInitiativeStatus(String expectedStatus) throws Exception {
        // To be implemented later with real verification.
        log.info("verifyInitiativeStatus() placeholder called. Expected: " + expectedStatus);
        return false;
    }
    
    public void closeUpdateHealthSheet() {

	    JavascriptExecutor js = (JavascriptExecutor) webDriver;

	    js.executeScript(
	        "const el = document.elementFromPoint(50, window.innerHeight / 2);" +
	        "if (el) el.click();"
	    );
    }
    
   public void   closestatus() {
	   click(InitiativeStatusManagementPageLocators.closestatus,"closestatus");
   }
    
    
    
}


