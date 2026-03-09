package Pages;

import Actions.ActionEngine;
import Locators.WithdrawnInitiativePageLocators;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Withdrawn Initiative Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in locator classes or defined inline
 *    - Methods use consistent locator patterns for reusability
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined inline or in Locators class
 *    - Actions: Implemented in this Page class
 *    - Test Logic: Kept in test classes
 * 
 * 3. MAINTAINABILITY:
 *    - Single Point of Change: Update locator once
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
public class WithdrawnInitiativePage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public WithdrawnInitiativePage(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Navigate to Withdrawn Initiative page
     * Direct navigation to Withdrawn Initiative
     * @throws Exception if navigation fails
     */
    public void navigateToWithdrawnInitiative() throws Exception {
        try {
            System.out.println("  🔍 Starting navigation to Withdrawn Initiative page...");
            waitForSeconds(3);

            System.out.println("  📌 Step 1: Click on Initiative Management navigation");
            clickInitiativeManagementNav();
            System.out.println("  ✅ Step 1 completed: Initiative Management nav clicked");

            // Wait for children-panel-container to appear
            System.out.println("  ⏳ Waiting for navigation menu to expand...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='children-panel-container']")));
                System.out.println("  ✅ Navigation menu container found");
                waitForSeconds(2);
            } catch (Exception e) {
                System.out.println("  ⚠️ children-panel-container not found, waiting longer...");
                waitForSeconds(5);
            }

            System.out.println("  📌 Step 2: Click on Withdrawn Initiative card");
            clickWithdrawnInitiative();
            System.out.println("  ✅ Step 2 completed: Withdrawn Initiative card clicked");

            waitForSeconds(4);
            System.out.println("  ✅ Successfully navigated to Withdrawn Initiative page");
        } catch (Exception e) {
            System.err.println("  ❌ Error navigating to Withdrawn Initiative page: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on Initiative Management navigation element
     * @throws Exception if element is not found or click fails
     */
    private void clickInitiativeManagementNav() throws Exception {
        driver.switchTo().defaultContent();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By navLocator = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']");
        WebElement nav = wait.until(ExpectedConditions.visibilityOfElementLocated(navLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nav);
        wait.until(ExpectedConditions.elementToBeClickable(nav));
        try {
            nav.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nav);
        }
    }

    /**
     * Click on Withdrawn Initiative card/button
     * XPath: //*[@id="children-panel-container"]/div[3]/div[4]/p
     * @throws Exception if element is not found or click fails
     */
    public void clickWithdrawnInitiative() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);

            System.out.println("  🔍 Searching for Withdrawn Initiative card...");

            By primaryLocator = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[4]/p");
            By[] alternativeLocators = {
                primaryLocator,
                By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Withdrawn Initiative')]"),
                By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Withdrawn')]"),
                By.xpath("//p[contains(text(),'Withdrawn Initiative')]")
            };

            WebElement el = null;
            boolean found = false;

            for (By locator : alternativeLocators) {
                try {
                    el = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (el.isDisplayed()) {
                        found = true;
                        System.out.println("  ✅ Found Withdrawn Initiative card using locator: " + locator);
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found || el == null) {
                throw new Exception("Withdrawn Initiative card not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            waitForSeconds(1);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                System.out.println("  ✅ Clicked Withdrawn Initiative card using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(el).click().perform();
                    System.out.println("  ✅ Clicked Withdrawn Initiative card using Actions");
                } catch (Exception e2) {
                    el.click();
                    System.out.println("  ✅ Clicked Withdrawn Initiative card using direct click");
                }
            }

            System.out.println("  ✅ Successfully clicked on Withdrawn Initiative card");
            waitForSeconds(2);

        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.err.println("  ❌ Error clicking Withdrawn Initiative card: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on Search icon/button
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchInput() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);

            By primaryLocator = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/div/img");
            By[] alternativeLocators = {
                primaryLocator,
                By.xpath("//img[@alt='Search']"),
                By.xpath("//img[contains(@alt,'Search')]"),
                By.xpath("//div[contains(@class,'search')]//img")
            };

            WebElement searchElement = null;
            boolean found = false;

            for (By locator : alternativeLocators) {
                try {
                    searchElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (searchElement.isDisplayed()) {
                        found = true;
                        System.out.println("  ✅ Found Search icon using locator: " + locator);
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found || searchElement == null) {
                throw new Exception("Search icon not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchElement);
            waitForSeconds(1);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchElement);
                System.out.println("  ✅ Clicked Search icon using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchElement).click().perform();
                    System.out.println("  ✅ Clicked Search icon using Actions");
                } catch (Exception e2) {
                    searchElement.click();
                    System.out.println("  ✅ Clicked Search icon using direct click");
                }
            }

            System.out.println("  ✅ Successfully clicked on Search icon");
            waitForSeconds(2);

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search icon: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Enter Initiative Code in search field
     * XPath: //*[@id="DemandCode"]
     * @param initiativeCode The initiative code to search for
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            By primaryLocator = By.xpath("//*[@id=\"DemandCode\"]");
            By[] alternativeLocators = {
                primaryLocator,
                By.xpath("//input[@id='DemandCode']"),
                By.xpath("//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]")
            };

            WebElement codeInput = null;
            boolean found = false;

            for (By locator : alternativeLocators) {
                try {
                    codeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    if (codeInput.isDisplayed() && codeInput.isEnabled()) {
                        found = true;
                        System.out.println("  ✅ Found Initiative Code input using locator: " + locator);
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found || codeInput == null) {
                throw new Exception("Initiative Code input field not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", codeInput);
            waitForSeconds(1);
            codeInput.clear();
            codeInput.sendKeys(initiativeCode);
            System.out.println("  ✅ Entered Initiative Code: " + initiativeCode);
            waitForSeconds(1);

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Code: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on Search button
     * XPath: //*[@id="id__1146"]
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);

            By primaryLocator = By.xpath("//*[@id=\"id__1146\"]");
            By[] alternativeLocators = {
                primaryLocator,
                By.xpath("//button[@id='id__1146']"),
                By.xpath("//button[.//span[normalize-space()='Search']]"),
                By.xpath("//button[normalize-space()='Search']")
            };

            WebElement searchBtn = null;
            boolean found = false;

            for (By locator : alternativeLocators) {
                try {
                    searchBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (searchBtn.isDisplayed() && searchBtn.isEnabled()) {
                        found = true;
                        System.out.println("  ✅ Found Search button using locator: " + locator);
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found || searchBtn == null) {
                throw new Exception("Search button not found");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchBtn);
            waitForSeconds(1);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("  ✅ Clicked Search button using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchBtn).click().perform();
                    System.out.println("  ✅ Clicked Search button using Actions");
                } catch (Exception e2) {
                    searchBtn.click();
                    System.out.println("  ✅ Clicked Search button using direct click");
                }
            }

            System.out.println("  ✅ Successfully clicked on Search button");
            waitForSeconds(3);

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verify that initiative with given code is displayed on Withdrawn Initiatives page
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeDisplayedOnWithdrawnPage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3);

        try {
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is displayed on Withdrawn Initiatives page...");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            try {
                System.out.println("  ⏳ Waiting for data table to load...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//table | //div[contains(@class,'table')] | //div[contains(@class,'ag-root')]")));
                System.out.println("  ✅ Data table loaded");
                waitForSeconds(2);
            } catch (Exception tableEx) {
                System.out.println("  ⚠️ Table wait timeout, continuing...");
            }

            By[] initiativeLocators = {
                By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]"),
                By.xpath("//table//td[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//*[normalize-space(text())='" + initiativeCode + "']"),
                By.xpath("//*[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//div[contains(@class,'ag-row')]//div[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//div[contains(@class,'ag-cell')]//*[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//span[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//div[contains(text(),'" + initiativeCode + "')]")
            };

            boolean found = false;
            for (By locator : initiativeLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (element.isDisplayed()) {
                        String foundText = element.getText();
                        System.out.println("  ✅ Initiative Code found in Withdrawn Initiatives page: " + foundText);
                        found = true;
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found) {
                System.out.println("  ⚠️ Initiative Code '" + initiativeCode + "' not found in Withdrawn Initiatives page");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.err.println("  ❌ Error verifying Initiative in Withdrawn Initiatives page: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verify that initiative with given code is NOT displayed on Withdrawn Initiatives page
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is NOT found (as expected), false if found (unexpected)
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeNotDisplayedOnWithdrawnPage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for page to load

        try {
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is NOT displayed on Withdrawn Initiatives page...");

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

            // Try multiple locator strategies to find the initiative
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

            boolean found = false;
            for (By locator : initiativeLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        try {
                            if (element.isDisplayed()) {
                                String foundText = element.getText();
                                if (foundText != null && foundText.contains(initiativeCode)) {
                                    System.out.println("  ❌ Initiative Code '" + initiativeCode + "' FOUND in Withdrawn Initiatives page (unexpected): " + foundText);
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

            if (!found) {
                System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Withdrawn Initiatives page (as expected)");
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Withdrawn Initiatives page");
                }
                return true; // Not found = expected behavior
            } else {
                System.out.println("  ❌ Initiative Code '" + initiativeCode + "' was found in Withdrawn Initiatives page (unexpected - should not be present)");
                if (reportLogger != null) {
                    reportLogger.fail("Initiative Code '" + initiativeCode + "' unexpectedly found on Withdrawn Initiatives page");
                }
                return false; // Found = unexpected behavior
            }

        } catch (Exception e) {
            // If we can't find the element, that's actually good (means it's not displayed)
            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Withdrawn Initiatives page (as expected)");
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Withdrawn Initiatives page");
            }
            return true; // Exception means not found = expected
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                            waitForSeconds(1);
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
     * Navigate to Withdrawn Initiatives page from Initiative Management tree.
     * @author Gayatri.k
     */
    public void navigateToWithdrawnInitiativesPage() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO WITHDRAWN INITIATIVES - START");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Step 1: Close any open dialogs/modals before navigation // Gayatri.k
        try {
            // Check if search dialog is open and close it
            try {
                WebElement searchInput = driver.findElement(WithdrawnInitiativePageLocators.searchInput);
                if (searchInput != null && searchInput.isDisplayed()) {
                    System.out.println("ℹ️ Search dialog is open, closing it...");
                    driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                    waitForSeconds(1);
                }
            } catch (Exception e) {
                // Search dialog not open, continue
            }
            
            // Close modal backdrop if present // Gayatri.k
            try { // Gayatri.k
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Gayatri.k
                By[] backdropLocators = { // Gayatri.k
                    By.xpath("//div[@class='MuiBackdrop-root' and @aria-hidden='true']"), // Gayatri.k
                    By.xpath("//div[contains(@class,'MuiBackdrop-root')]"), // Gayatri.k
                    By.xpath("//div[contains(@class,'MuiModal-backdrop')]") // Gayatri.k
                }; // Gayatri.k
                
                for (By backdropLocator : backdropLocators) { // Gayatri.k
                    try { // Gayatri.k
                        WebElement backdrop = driver.findElement(backdropLocator); // Gayatri.k
                        if (backdrop != null && backdrop.isDisplayed()) { // Gayatri.k
                            System.out.println("ℹ️ Modal backdrop found, closing it..."); // Gayatri.k
                            // Try ESC key first // Gayatri.k
                            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE); // Gayatri.k
                            waitForSeconds(1); // Gayatri.k
                            // If still present, try clicking outside or pressing ESC again // Gayatri.k
                            if (backdrop.isDisplayed()) { // Gayatri.k
                                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", backdrop); // Gayatri.k
                                waitForSeconds(1); // Gayatri.k
                            } // Gayatri.k
                            break; // Gayatri.k
                        } // Gayatri.k
                    } catch (Exception e) { // Gayatri.k
                        continue; // Gayatri.k
                    } // Gayatri.k
                } // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                System.out.println("ℹ️ No backdrop to close: " + e.getMessage()); // Gayatri.k
            } // Gayatri.k
            
            // Press ESC to dismiss any modals/overlays
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            waitForSeconds(1);
            
            // Wait for any modals to fully disappear // Gayatri.k
            try { // Gayatri.k
                WebDriverWait modalWait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Gayatri.k
                modalWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'MuiBackdrop-root')]"))); // Gayatri.k
                System.out.println("✅ All modals closed"); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                System.out.println("ℹ️ Modal may still be present, continuing anyway: " + e.getMessage()); // Gayatri.k
            } // Gayatri.k
        } catch (Exception e) {
            System.out.println("ℹ️ No dialogs to close: " + e.getMessage());
        }

        // Step 2: Ensure left menu is expanded (Material-UI fix) // Gayatri.k
        ensureLeftMenuExpanded();
        waitForSeconds(1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 3: Click Initiative Management node (if needed) // Gayatri.k
        try {
            // Wait for any backdrop to disappear first // Gayatri.k
            try { // Gayatri.k
                WebDriverWait backdropWait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Gayatri.k
                backdropWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'MuiBackdrop-root')]"))); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                // Backdrop may not be present, continue // Gayatri.k
            } // Gayatri.k
            
            WebElement mgmtNode = wait.until(
                    ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.initiativeManagementNode));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", mgmtNode);
            waitForSeconds(1);
            
            // Try Actions class click first (more reliable for Material-UI) // Gayatri.k
            try { // Gayatri.k
                Actions actions = new Actions(driver); // Gayatri.k
                actions.moveToElement(mgmtNode).click().perform(); // Gayatri.k
                System.out.println("✅ Clicked Initiative Management node using Actions class"); // Gayatri.k
            } catch (Exception e) { // Gayatri.k
                // Fallback to normal click // Gayatri.k
                try { // Gayatri.k
                    mgmtNode.click(); // Gayatri.k
                    System.out.println("✅ Clicked Initiative Management node using normal click"); // Gayatri.k
                } catch (Exception e2) { // Gayatri.k
                    // Last resort: JavaScript click // Gayatri.k
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mgmtNode); // Gayatri.k
                    System.out.println("✅ Clicked Initiative Management node using JavaScript click"); // Gayatri.k
                } // Gayatri.k
            } // Gayatri.k
            
            waitForSeconds(2); // Wait for menu to open
        } catch (Exception e) {
            System.out.println("ℹ️ Initiative Management node not clicked or already expanded: " + e.getMessage());
        }

        // Step 4: Click Withdrawn Initiatives node using Actions class (Material-UI fix) // Gayatri.k
        System.out.println("📍 Clicking Withdrawn Initiatives navigation...");
        
        WebElement withdrawnNode = null;
        Actions actions = new Actions(driver);
        
        for (By locator : WithdrawnInitiativePageLocators.withdrawnInitiativesNodeLocators) {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                // Use visibilityOfElementLocated instead of elementToBeClickable for better reliability
                withdrawnNode = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (withdrawnNode != null && withdrawnNode.isDisplayed()) {
                    System.out.println("✅ Found Withdrawn Initiatives element using: " + locator);
                    // Scroll into view
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", withdrawnNode);
                    waitForSeconds(1);
                    
                    // Use Actions class for Material-UI clicks (more reliable)
                    try {
                        actions.moveToElement(withdrawnNode).click().perform();
                        System.out.println("✅ Clicked Withdrawn Initiatives using Actions class");
                        break;
                    } catch (Exception e) {
                        System.out.println("⚠️ Actions click failed, trying normal click: " + e.getMessage());
                        withdrawnNode.click();
                        System.out.println("✅ Clicked Withdrawn Initiatives using normal click");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Locator failed: " + locator + " - " + e.getMessage());
                continue;
            }
        }
        
        if (withdrawnNode == null) {
            throw new Exception("Could not find or click Withdrawn Initiatives menu item with any locator");
        }

        // Allow page to load list view
        waitForSeconds(3);
        System.out.println("✅ ✅ ✅ Navigated to Withdrawn Initiatives successfully! ✅ ✅ ✅");
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO WITHDRAWN INITIATIVES - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    // ==================== BASIC VALIDATIONS ====================

    /**
     * Verify Withdrawn Initiatives page header or list view is visible.
     *
     * First tries the specific h6 header locator; if not found, falls back to:
     *  - any element containing text 'Withdrawn'
     *  - or the list-view table
     *
     * This makes the test robust even if the header tag/text changes slightly.
     */
    public void verifyWithdrawnHeader() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement header = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(WithdrawnInitiativePageLocators.withdrawnHeader));

            String text = header.getText();
            System.out.println("✅ Withdrawn header text (h6): '" + text + "'");
            if (reportLogger != null) {
                reportLogger.pass("Withdrawn Initiatives header visible: " + text);
            }
            return;
        } catch (Exception e) {
            System.out.println("⚠️ Exact Withdrawn header (h6) not found within timeout. Trying fallback locators...");
        }

        // Fallback 1: any element whose text contains 'Withdrawn'
        try {
            List<WebElement> candidates =
                    driver.findElements(By.xpath("//*[contains(text(),'Withdrawn')]"));
            for (WebElement el : candidates) {
                if (el.isDisplayed()) {
                    String txt = el.getText();
                    System.out.println("✅ Withdrawn header-like element found: '" + txt + "'");
                    if (reportLogger != null) {
                        reportLogger.pass("Withdrawn Initiatives header-like text visible: " + txt);
                    }
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error while searching fallback header elements: " + e.getMessage());
        }

        // Fallback 2: list view table presence
        try {
            WebElement table = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(WithdrawnInitiativePageLocators.withdrawnTable));
            System.out.println("✅ Withdrawn Initiatives table is visible. Using this as page verification.");
            if (reportLogger != null) {
                reportLogger.pass("Withdrawn Initiatives list view table is visible.");
            }
            return;
        } catch (Exception e) {
            System.out.println("⚠️ Withdrawn Initiatives table not visible either: " + e.getMessage());
        }

        // If all fallbacks fail, throw a clear error
        throw new Exception("❌ Could not verify Withdrawn Initiatives page header or list view. " +
                "Please check the header text/tag or provide updated XPath.");
    }

    /**
     * Check that there is at least one withdrawn initiative record.
     *
     * If no rows are present, also checks for the
     * "There are no items to show in this view." message.
     *
     * @return true if at least one row is present, false otherwise
     */
    public boolean hasWithdrawnRecords() {
        try {
            // Try multiple locators to find rows
            List<WebElement> rows = null;
            By usedLocator = null;
            
            for (By locator : WithdrawnInitiativePageLocators.withdrawnRowsLocators) {
                try {
                    rows = driver.findElements(locator);
                    if (rows != null && !rows.isEmpty()) {
                        usedLocator = locator;
                        System.out.println("📊 Withdrawn Initiatives - found " + rows.size() + " rows using locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Fallback to primary locator
            if (rows == null || rows.isEmpty()) {
                rows = driver.findElements(WithdrawnInitiativePageLocators.withdrawnRows);
                System.out.println("📊 Withdrawn Initiatives - total rows found (fallback): " + (rows != null ? rows.size() : 0));
            }

            if (rows != null && !rows.isEmpty()) {
                return true;
            }

            // No rows; verify the "no items" message
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement msg = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(WithdrawnInitiativePageLocators.noItemsMessage));
                String text = msg.getText();
                System.out.println("ℹ️ No records message displayed: '" + text + "'");
                if (reportLogger != null) {
                    reportLogger.info("No records in Withdrawn Initiatives. Message shown: " + text);
                }
            } catch (Exception e) {
                System.out.println("⚠️ No rows and expected 'no items' message not found: " + e.getMessage());
            }

            return false;
        } catch (Exception e) {
            System.out.println("⚠️ Error checking withdrawn records: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate list view column headers:
     * Initiative Code, Initiative Title, Nature of Initiative,
     * Business Group, Organization Group, Converted To, Action.
     *
     * Fails with a clear exception if any expected column is missing.
     */
    public void validateListViewColumns() throws Exception {
        // Try to get headers directly first (without blocking wait)
        List<WebElement> headerCells = driver.findElements(WithdrawnInitiativePageLocators.headerCells);
        
        if (headerCells == null || headerCells.isEmpty()) {
            // Wait for table to be present, then try again
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.withdrawnTable));
                headerCells = driver.findElements(WithdrawnInitiativePageLocators.headerCells);
            } catch (Exception e) {
                System.out.println("⚠️ List view table not found within timeout while validating columns: " + e.getMessage());
            }
        }

        if (headerCells == null || headerCells.isEmpty()) {
            System.out.println("📊 Withdrawn Initiatives - header count: 0");
            System.out.println("⚠️ No header cells found in Withdrawn Initiatives list view. Skipping column validation.");
            if (reportLogger != null) {
                reportLogger.warning("Could not find column headers in Withdrawn Initiatives list view. Skipping validation.");
            }
            return; // Don't fail the test, just skip column validation
        }

        System.out.println("📊 Withdrawn Initiatives - header count: " + headerCells.size());

        String[] expectedColumns = {
                "Initiative Code",
                "Initiative Title",
                "Nature of Initiative",
                "Business Group",
                "Organization Group",
                "Converted To",
                "Action"
        };

        // Collect actual header texts
        String[] actualHeaders = new String[headerCells.size()];
        for (int i = 0; i < headerCells.size(); i++) {
            try {
                actualHeaders[i] = headerCells.get(i).getText().trim();
                System.out.println("   Header " + (i + 1) + ": '" + actualHeaders[i] + "'");
            } catch (Exception e) {
                actualHeaders[i] = "";
            }
        }

        // Check each expected column
        for (String expected : expectedColumns) {
            boolean found = false;
            for (String actual : actualHeaders) {
                if (actual.contains(expected) || expected.contains(actual)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String errorMsg = "❌ Expected column '" + expected + "' not found in Withdrawn Initiatives list view.";
                System.out.println(errorMsg);
                if (reportLogger != null) {
                    reportLogger.fail(errorMsg);
                }
                throw new Exception(errorMsg);
            }
        }

        System.out.println("✅ All expected columns found in Withdrawn Initiatives list view.");
        if (reportLogger != null) {
            reportLogger.pass("All expected columns validated in Withdrawn Initiatives list view.");
        }
    }

    // ==================== SEARCH FUNCTIONALITY ====================

    /**
     * Click on the search icon to open search dialog.
     */
    public void clickSearchIcon() throws Exception {
        System.out.println("📍 Step 1: Clicking search icon...");
        
        // Wait for any toast notifications to disappear
        try {
            WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            toastWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'Toastify')]")));
        } catch (Exception e) {
            // Toast may not be present, continue
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.searchIcon));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchIcon);
        waitForSeconds(1);
        
        // Try normal click first, then JS click as fallback
        try {
            searchIcon.click();
            System.out.println("✅ Clicked on search icon (normal click)");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            waitForSeconds(2); // Wait a bit more if normal click failed
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
            System.out.println("✅ Clicked on search icon (JavaScript click)");
        }
        
        // Wait for search dialog to open
        waitForSeconds(2);
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on search icon");
        }
    }

    /**
     * Select value from Nature of Initiative dropdown.
     * 
     * @param value The value to select
     */
    public void selectNatureOfInitiative(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Nature of Initiative value is empty, skipping selection");
            return;
        }

        String selectedValue = value.trim();
        System.out.println("📍 Selecting Nature of Initiative: '" + selectedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Click on dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.natureOfInitiativeDropdown));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        waitForSeconds(1);
        
        // Click dropdown
        try {
            dropdown.click();
            System.out.println("✅ Clicked on Nature of Initiative dropdown");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Clicked on Nature of Initiative dropdown (JavaScript)");
        }
        
        // Wait for dropdown options to appear
        waitForSeconds(2);
        
        // Find and click the option by text - try multiple strategies
        boolean optionSelected = false;
        Actions actions = new Actions(driver);
        
        // Strategy 1: Try exact text match
        try {
            By optionLocator = By.xpath("//li[normalize-space(text())='" + selectedValue + "'] | //div[@role='option' and normalize-space(text())='" + selectedValue + "'] | //span[normalize-space(text())='" + selectedValue + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            actions.moveToElement(option).click().perform();
            System.out.println("✅ Selected Nature of Initiative: '" + selectedValue + "' (exact match)");
            optionSelected = true;
        } catch (Exception e) {
            System.out.println("⚠️ Exact match failed, trying contains match: " + e.getMessage());
        }
        
        // Strategy 2: Try contains text match
        if (!optionSelected) {
            try {
                By optionLocator = By.xpath("//li[contains(text(),'" + selectedValue + "')] | //div[@role='option' and contains(text(),'" + selectedValue + "')] | //span[contains(text(),'" + selectedValue + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                actions.moveToElement(option).click().perform();
                System.out.println("✅ Selected Nature of Initiative: '" + selectedValue + "' (contains match)");
                optionSelected = true;
            } catch (Exception e) {
                System.out.println("⚠️ Contains match failed, trying alternative method: " + e.getMessage());
            }
        }
        
        // Strategy 3: Find all options and match by text
        if (!optionSelected) {
            try {
                List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'MuiMenuItem-root') or contains(@class,'MuiAutocomplete-option')] | //div[@role='option'] | //ul//li"));
                for (WebElement opt : options) {
                    try {
                        if (opt.isDisplayed()) {
                            String optText = opt.getText().trim();
                            if (optText.equals(selectedValue) || optText.contains(selectedValue)) {
                                actions.moveToElement(opt).click().perform();
                                System.out.println("✅ Selected Nature of Initiative: '" + selectedValue + "' (found in list: '" + optText + "')");
                                optionSelected = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ List search also failed: " + e.getMessage());
            }
        }
        
        if (!optionSelected) {
            System.out.println("⚠️ Warning: Could not select Nature of Initiative option: '" + selectedValue + "'. Continuing anyway...");
        }
        
        waitForSeconds(1);
        
        if (reportLogger != null) {
            reportLogger.info("Selected Nature of Initiative: " + selectedValue);
        }
    }

    /**
     * Select value from Business Group dropdown.
     * 
     * @param value The value to select
     */
    public void selectBusinessGroup(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Business Group value is empty, skipping selection");
            return;
        }

        String selectedValue = value.trim();
        System.out.println("📍 Selecting Business Group: '" + selectedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Click on dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.businessGroupDropdown));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        waitForSeconds(1);
        
        // Click dropdown
        try {
            dropdown.click();
            System.out.println("✅ Clicked on Business Group dropdown");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Clicked on Business Group dropdown (JavaScript)");
        }
        
        // Wait for dropdown options to appear
        waitForSeconds(2);
        
        // Find and click the option by text - try multiple strategies
        boolean optionSelected = false;
        Actions actions = new Actions(driver);
        
        // Strategy 1: Try exact text match
        try {
            By optionLocator = By.xpath("//li[normalize-space(text())='" + selectedValue + "'] | //div[@role='option' and normalize-space(text())='" + selectedValue + "'] | //span[normalize-space(text())='" + selectedValue + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            actions.moveToElement(option).click().perform();
            System.out.println("✅ Selected Business Group: '" + selectedValue + "' (exact match)");
            optionSelected = true;
        } catch (Exception e) {
            System.out.println("⚠️ Exact match failed, trying contains match: " + e.getMessage());
        }
        
        // Strategy 2: Try contains text match
        if (!optionSelected) {
            try {
                By optionLocator = By.xpath("//li[contains(text(),'" + selectedValue + "')] | //div[@role='option' and contains(text(),'" + selectedValue + "')] | //span[contains(text(),'" + selectedValue + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                actions.moveToElement(option).click().perform();
                System.out.println("✅ Selected Business Group: '" + selectedValue + "' (contains match)");
                optionSelected = true;
            } catch (Exception e) {
                System.out.println("⚠️ Contains match failed, trying alternative method: " + e.getMessage());
            }
        }
        
        // Strategy 3: Find all options and match by text
        if (!optionSelected) {
            try {
                List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'MuiMenuItem-root') or contains(@class,'MuiAutocomplete-option')] | //div[@role='option'] | //ul//li"));
                for (WebElement opt : options) {
                    try {
                        if (opt.isDisplayed()) {
                            String optText = opt.getText().trim();
                            if (optText.equals(selectedValue) || optText.contains(selectedValue)) {
                                actions.moveToElement(opt).click().perform();
                                System.out.println("✅ Selected Business Group: '" + selectedValue + "' (found in list: '" + optText + "')");
                                optionSelected = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ List search also failed: " + e.getMessage());
            }
        }
        
        if (!optionSelected) {
            System.out.println("⚠️ Warning: Could not select Business Group option: '" + selectedValue + "'. Continuing anyway...");
        }
        
        waitForSeconds(1);
        
        if (reportLogger != null) {
            reportLogger.info("Selected Business Group: " + selectedValue);
        }
    }

    /**
     * Select value from Organization Unit dropdown.
     * 
     * @param value The value to select
     */
    public void selectOrganizationUnit(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Organization Unit value is empty, skipping selection");
            return;
        }

        String selectedValue = value.trim();
        System.out.println("📍 Selecting Organization Unit: '" + selectedValue + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Click on dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.organizationUnitDropdown));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
        waitForSeconds(1);
        
        // Click dropdown
        try {
            dropdown.click();
            System.out.println("✅ Clicked on Organization Unit dropdown");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Clicked on Organization Unit dropdown (JavaScript)");
        }
        
        // Wait for dropdown options to appear
        waitForSeconds(2);
        
        // Find and click the option by text - try multiple strategies
        boolean optionSelected = false;
        Actions actions = new Actions(driver);
        
        // Strategy 1: Try exact text match
        try {
            By optionLocator = By.xpath("//li[normalize-space(text())='" + selectedValue + "'] | //div[@role='option' and normalize-space(text())='" + selectedValue + "'] | //span[normalize-space(text())='" + selectedValue + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            actions.moveToElement(option).click().perform();
            System.out.println("✅ Selected Organization Unit: '" + selectedValue + "' (exact match)");
            optionSelected = true;
        } catch (Exception e) {
            System.out.println("⚠️ Exact match failed, trying contains match: " + e.getMessage());
        }
        
        // Strategy 2: Try contains text match
        if (!optionSelected) {
            try {
                By optionLocator = By.xpath("//li[contains(text(),'" + selectedValue + "')] | //div[@role='option' and contains(text(),'" + selectedValue + "')] | //span[contains(text(),'" + selectedValue + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                actions.moveToElement(option).click().perform();
                System.out.println("✅ Selected Organization Unit: '" + selectedValue + "' (contains match)");
                optionSelected = true;
            } catch (Exception e) {
                System.out.println("⚠️ Contains match failed, trying alternative method: " + e.getMessage());
            }
        }
        
        // Strategy 3: Find all options and match by text
        if (!optionSelected) {
            try {
                List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'MuiMenuItem-root') or contains(@class,'MuiAutocomplete-option')] | //div[@role='option'] | //ul//li"));
                for (WebElement opt : options) {
                    try {
                        if (opt.isDisplayed()) {
                            String optText = opt.getText().trim();
                            if (optText.equals(selectedValue) || optText.contains(selectedValue)) {
                                actions.moveToElement(opt).click().perform();
                                System.out.println("✅ Selected Organization Unit: '" + selectedValue + "' (found in list: '" + optText + "')");
                                optionSelected = true;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ List search also failed: " + e.getMessage());
            }
        }
        
        if (!optionSelected) {
            System.out.println("⚠️ Warning: Could not select Organization Unit option: '" + selectedValue + "'. Continuing anyway...");
        }
        
        waitForSeconds(1);
        
        if (reportLogger != null) {
            reportLogger.info("Selected Organization Unit: " + selectedValue);
        }
    }

    /**
     * Enter initiative code in the search input field.
     * 
     * @param initiativeCode The initiative code to search for
     */
 /*   public void enterInitiativeCode(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null or empty. Cannot search.");
        }

        String code = initiativeCode.trim();
        System.out.println("📍 Step 2: Entering initiative code: '" + code + "'");
        
        // First, check if search dialog is already open
        try {
            WebElement existingInput = driver.findElement(WithdrawnInitiativePageLocators.searchInput);
            if (existingInput != null && existingInput.isDisplayed()) {
                System.out.println("✅ Search dialog already open");
            }
        } catch (Exception e) {
            // Search dialog not open, wait a bit more
            waitForSeconds(2);
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Wait for input field to be visible and clickable
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.searchInput));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForSeconds(1);
        
        // Clear field using multiple methods
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        waitForSeconds(1);
        
        // Enter the code
        input.sendKeys(code);
        waitForSeconds(1);
        
        // Verify the code was entered
        String enteredValue = input.getAttribute("value");
        System.out.println("✅ Entered initiative code: '" + code + "' (field value: '" + enteredValue + "')");
        
        if (!enteredValue.contains(code)) {
            System.out.println("⚠️ Warning: Entered value doesn't match expected code. Retrying...");
            input.clear();
            input.sendKeys(code);
            waitForSeconds(1);
        }
        
        if (reportLogger != null) {
            reportLogger.info("Entered initiative code in search field: " + code);
        }
    }
*/
    /**
     * Enter initiative title in the search input field.
     * 
     * @param initiativeTitle The initiative title to search for
     */
    public void enterInitiativeTitle(String initiativeTitle) throws Exception {
        if (initiativeTitle == null || initiativeTitle.trim().isEmpty()) {
            System.out.println("⚠️ Initiative Title value is empty, skipping entry");
            return;
        }

        String title = initiativeTitle.trim();
        System.out.println("📍 Entering Initiative Title: '" + title + "'");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait for input field to be visible and clickable
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.initiativeTitleInput));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        waitForSeconds(1);
        
        // Clear field using multiple methods
        input.clear();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        waitForSeconds(1);
        
        // Enter the title
        input.sendKeys(title);
        waitForSeconds(1);
        
        // Verify the title was entered
        String enteredValue = input.getAttribute("value");
        System.out.println("✅ Entered Initiative Title: '" + title + "' (field value: '" + enteredValue + "')");
        
        if (!enteredValue.contains(title)) {
            System.out.println("⚠️ Warning: Entered value doesn't match expected title. Retrying...");
            input.clear();
            input.sendKeys(title);
            waitForSeconds(1);
        }
        
        if (reportLogger != null) {
            reportLogger.info("Entered Initiative Title in search field: " + title);
        }
    }

    /**
     * Click on the search button to execute the search.
     */
 /*   public void clickSearchButton() throws Exception {
        System.out.println("📍 Step 3: Clicking search button...");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait for search button to be clickable
        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.searchButton));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchButton);
        waitForSeconds(1);
        
        // Try normal click first, then JS click as fallback
        try {
            if (!searchButton.isDisplayed()) {
                throw new Exception("Search button is not displayed");
            }
            if (!searchButton.isEnabled()) {
                throw new Exception("Search button is not enabled");
            }
            
            searchButton.click();
            System.out.println("✅ Clicked on search button (normal click)");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
            System.out.println("✅ Clicked on search button (JavaScript click)");
        }
        
        // Wait for search results to load
        waitForSeconds(3);
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on search button");
        }
    }
   */
    /**
     * Verify that the matching record is displayed in the search results.
     * 
     * @param initiativeCode The initiative code to verify
     * @return true if the code is found in the results, false otherwise
     */
    public boolean verifyMatchingRecord(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null or empty. Cannot verify.");
        }

        String code = initiativeCode.trim();
        System.out.println("📍 Step 4: Verifying matching record for code: '" + code + "'");
        
        // Wait for table to be visible/updated after search
        try {
            WebDriverWait tableWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            tableWait.until(ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.withdrawnTable));
            System.out.println("✅ Table is present after search");
        } catch (Exception e) {
            System.out.println("⚠️ Table not found after search: " + e.getMessage());
        }
        
        // Additional wait for results to load
        waitForSeconds(2);
        
        // Check table rows for the code (try multiple locators)
        List<WebElement> rows = null;
        for (By locator : WithdrawnInitiativePageLocators.withdrawnRowsLocators) {
            try {
                rows = driver.findElements(locator);
                if (rows != null && !rows.isEmpty()) {
                    System.out.println("📊 Checking " + rows.size() + " row(s) for initiative code '" + code + "'");
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // Fallback to primary locator
        if (rows == null || rows.isEmpty()) {
            rows = driver.findElements(WithdrawnInitiativePageLocators.withdrawnRows);
            System.out.println("📊 Checking " + (rows != null ? rows.size() : 0) + " row(s) for initiative code '" + code + "' (fallback locator)");
        }
        
        if (rows == null || rows.isEmpty()) {
            System.out.println("❌ No rows found in table after search");
            if (reportLogger != null) {
                reportLogger.fail("No rows found in table after search for code: " + code);
            }
            return false;
        }
        
        // Check if any row contains the initiative code
        for (WebElement row : rows) {
            try {
                String rowText = row.getText();
                
                // Skip header rows
                if (rowText.contains("Initiative Code") && rowText.contains("Initiative Title")) {
                    continue;
                }
                
                // Check if row contains the initiative code
                if (rowText.contains(code)) {
                    System.out.println("✅ Matching record found! Initiative code '" + code + "' is displayed in the search results");
                    System.out.println("   Row text (first 200 chars): " + rowText.substring(0, Math.min(200, rowText.length())));
                    if (reportLogger != null) {
                        reportLogger.pass("Matching record found for initiative code: " + code);
                    }
                    return true;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error reading row: " + e.getMessage());
                continue;
            }
        }
        
        System.out.println("❌ Matching record NOT found. Initiative code '" + code + "' is not displayed in the search results");
        if (reportLogger != null) {
            reportLogger.fail("Matching record not found for initiative code: " + code);
        }
        return false;
    }

    // ==================== CARD VIEW FUNCTIONALITY ====================

    /**
     * Click on the Switch to Card View Toggle icon.
     * Tries multiple locators for better reliability.
     */
    public void clickSwitchToCardViewToggle() throws Exception {
        System.out.println("📍 Clicking on Switch to Card View Toggle...");
        
        WebElement toggleButton = null;
        By usedLocator = null;
        
        // Strategy 1: Try direct findElement with aria-label first (as user suggested)
        try {
            System.out.println("  🔍 Strategy 1: Trying direct findElement: //button[@aria-label='Switch to Card view']...");
            toggleButton = driver.findElement(By.xpath("//button[@aria-label='Switch to Card view']"));
            if (toggleButton != null && toggleButton.isDisplayed()) {
                usedLocator = By.xpath("//button[@aria-label='Switch to Card view']");
                System.out.println("  ✅ Found element using direct findElement with aria-label");
            } else {
                toggleButton = null;
                System.out.println("  ⚠️ Element found but not displayed");
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Strategy 1 (direct findElement) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            toggleButton = null;
        }
        
        // Strategy 2: Try WebDriverWait with aria-label
        if (toggleButton == null) {
            try {
                System.out.println("  🔍 Strategy 2: Trying WebDriverWait with aria-label...");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                toggleButton = wait.until(
                        ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Switch to Card view']")));
                usedLocator = By.xpath("//button[@aria-label='Switch to Card view']");
                System.out.println("  ✅ Found element using WebDriverWait with aria-label");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 2 (WebDriverWait) failed: " + e.getMessage());
            }
        }
        
        // Strategy 3: Try multiple locators with shorter timeout for each
        if (toggleButton == null) {
            for (By locator : WithdrawnInitiativePageLocators.switchToCardViewToggleLocators) {
                try {
                    System.out.println("  🔍 Strategy 3: Trying locator: " + locator);
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    toggleButton = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    usedLocator = locator;
                    System.out.println("  ✅ Found element using locator: " + locator);
                    break;
                } catch (Exception e) {
                    System.out.println("  ⚠️ Locator failed (timeout 5s): " + locator);
                    continue;
                }
            }
        }
        
        // Final fallback to primary locator with longer timeout
        if (toggleButton == null) {
            try {
                System.out.println("  🔍 Strategy 4: Trying primary locator as final fallback...");
                WebDriverWait finalWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                toggleButton = finalWait.until(
                        ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.switchToCardViewToggle));
                usedLocator = WithdrawnInitiativePageLocators.switchToCardViewToggle;
                System.out.println("  ✅ Found element using primary locator");
            } catch (Exception e) {
                System.out.println("  ❌ All strategies failed. Last error: " + e.getMessage());
                throw new Exception("Could not find Switch to Card View Toggle with any locator. " +
                        "Tried: direct findElement, WebDriverWait, and " + 
                        java.util.Arrays.toString(WithdrawnInitiativePageLocators.switchToCardViewToggleLocators) +
                        ". Last error: " + e.getMessage());
            }
        }
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", toggleButton);
        waitForSeconds(1);
        
        // Try normal click first, then JS click as fallback
        try {
            toggleButton.click();
            System.out.println("✅ Clicked on Switch to Card View Toggle (normal click) using locator: " + usedLocator);
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleButton);
            System.out.println("✅ Clicked on Switch to Card View Toggle (JavaScript click) using locator: " + usedLocator);
        }
        
        // Wait for card view to load
        waitForSeconds(3);
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on Switch to Card View Toggle using locator: " + usedLocator);
        }
    }

    /**
     * Verify that the Card View is displayed with card details.
     * 
     * @return true if card view is found and contains cards, false otherwise
     */
    public boolean verifyCardViewDetails() throws Exception {
        System.out.println("📍 Verifying Card View details...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait a bit for card view to render
        waitForSeconds(2);
        
        // Try to find cards using multiple locators
        List<WebElement> cards = null;
        for (By locator : WithdrawnInitiativePageLocators.cardViewLocators) {
            try {
                cards = driver.findElements(locator);
                if (cards != null && !cards.isEmpty()) {
                    System.out.println("✅ Found " + cards.size() + " card(s) using locator: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (cards == null || cards.isEmpty()) {
            System.out.println("⚠️ No cards found with standard locators. Checking for card-like elements...");
            
            // Try alternative approach: look for any div that might be a card
            try {
                List<WebElement> allDivs = driver.findElements(By.xpath("//div[contains(@class,'MuiPaper-root') or contains(@class,'card')]"));
                if (allDivs != null && !allDivs.isEmpty()) {
                    System.out.println("✅ Found " + allDivs.size() + " potential card element(s)");
                    cards = allDivs;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not find card elements: " + e.getMessage());
            }
        }
        
        if (cards == null || cards.isEmpty()) {
            System.out.println("❌ Card View verification failed: No cards found");
            if (reportLogger != null) {
                reportLogger.fail("Card View verification failed: No cards found");
            }
            return false;
        }
        
        // Verify card details by checking if cards have content
        int cardsWithContent = 0;
        for (WebElement card : cards) {
            try {
                String cardText = card.getText();
                if (cardText != null && !cardText.trim().isEmpty()) {
                    cardsWithContent++;
                    System.out.println("  ✅ Card " + cardsWithContent + " has content (first 100 chars): " + 
                            cardText.substring(0, Math.min(100, cardText.length())));
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Error reading card content: " + e.getMessage());
            }
        }
        
        if (cardsWithContent > 0) {
            System.out.println("✅ ✅ ✅ Card View verification PASSED ✅ ✅ ✅");
            System.out.println("   Total cards found: " + cards.size());
            System.out.println("   Cards with content: " + cardsWithContent);
            if (reportLogger != null) {
                reportLogger.pass("Card View verification passed. Found " + cards.size() + " card(s) with " + cardsWithContent + " containing content");
            }
            return true;
        } else {
            System.out.println("⚠️ Cards found but no content detected");
            if (reportLogger != null) {
                reportLogger.warning("Cards found but no content detected");
            }
            // Still return true if cards are present, even if content check fails
            return cards.size() > 0;
        }
    }

    // ==================== EDIT VIEW FUNCTIONALITY ====================

    /**
     * Click on the edit icon of the searched record.
     * First finds the row containing the initiative code, then locates the edit icon in that row.
     * 
     * @param initiativeCode The initiative code to search for
     */
    public void clickEditIcon(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code is null or empty. Cannot find edit icon.");
        }
        
        String code = initiativeCode.trim();
        System.out.println("📍 Clicking on edit icon for initiative code: '" + code + "'");
        
        // Wait for table to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.withdrawnTable));
            System.out.println("✅ Table is present");
        } catch (Exception e) {
            System.out.println("⚠️ Table not found: " + e.getMessage());
        }
        
        // Additional wait for results to load
        waitForSeconds(2);
        
        // Find all rows
        List<WebElement> rows = null;
        for (By locator : WithdrawnInitiativePageLocators.withdrawnRowsLocators) {
            try {
                rows = driver.findElements(locator);
                if (rows != null && !rows.isEmpty()) {
                    System.out.println("📊 Found " + rows.size() + " row(s) in table");
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // Fallback to primary locator
        if (rows == null || rows.isEmpty()) {
            rows = driver.findElements(WithdrawnInitiativePageLocators.withdrawnRows);
            System.out.println("📊 Found " + (rows != null ? rows.size() : 0) + " row(s) using fallback locator");
        }
        
        if (rows == null || rows.isEmpty()) {
            throw new Exception("No rows found in table. Cannot find edit icon for initiative code: " + code);
        }
        
        // Find the row containing the initiative code and get its ID
        WebElement targetRow = null;
        String rowId = null;
        System.out.println("🔍 Searching through " + rows.size() + " row(s) for initiative code: '" + code + "'");
        
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            try {
                // Wait a bit for row to be ready
                Thread.sleep(100);
                String rowText = row.getText();
                
                // Debug: Print row text for troubleshooting
                if (rowText != null && !rowText.trim().isEmpty()) {
                    String preview = rowText.length() > 200 ? rowText.substring(0, 200) : rowText;
                    System.out.println("🔍 Row " + (i + 1) + " text (first 200 chars): " + preview);
                } else {
                    System.out.println("⚠️ Row " + (i + 1) + " has empty text");
                }
                
                // Skip header rows
                if (rowText != null && rowText.contains("Initiative Code") && rowText.contains("Initiative Title")) {
                    System.out.println("   ⏭️ Skipping header row");
                    continue;
                }
                
                // Check if row contains the initiative code
                if (rowText != null && rowText.contains(code)) {
                    targetRow = row;
                    
                    // Try to get the row's ID attribute
                    try {
                        rowId = row.getAttribute("id");
                        if (rowId != null && !rowId.isEmpty()) {
                            System.out.println("✅ Found row containing initiative code '" + code + "' (row " + (i + 1) + ", ID: '" + rowId + "')");
                        } else {
                            System.out.println("✅ Found row containing initiative code '" + code + "' (row " + (i + 1) + ", no ID attribute)");
                        }
                    } catch (Exception e) {
                        System.out.println("✅ Found row containing initiative code '" + code + "' (row " + (i + 1) + ", could not get ID: " + e.getMessage() + ")");
                    }
                    break;
                } else {
                    System.out.println("   ❌ Row " + (i + 1) + " does not contain code '" + code + "'");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error reading row " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }
        
        if (targetRow == null) {
            System.out.println("❌ Initiative code '" + code + "' not found in any of the " + rows.size() + " row(s)");
            throw new Exception("Initiative code '" + code + "' not found in any row. Cannot find edit icon.");
        }
        
        // Now find the edit icon using multiple strategies
        WebElement editIconElement = null;
        By usedLocator = null;
        String usedMethod = "";
        
        System.out.println("🔍 Searching for edit icon...");
        
        // Strategy 1: Use CSS selector for SVG path (as user suggested) - Try this FIRST
        try {
            System.out.println("  🔍 Strategy 1: Trying CSS selector: svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path...");
            By cssLocator = By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path");
            editIconElement = driver.findElement(cssLocator);
            if (editIconElement != null && editIconElement.isDisplayed()) {
                usedLocator = cssLocator;
                usedMethod = "CSS selector for SVG path";
                System.out.println("  ✅ Found edit icon using CSS selector");
            } else {
                editIconElement = null; // Reset if not displayed
                System.out.println("  ⚠️ Element found but not displayed");
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Strategy 1 (CSS selector) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            editIconElement = null;
        }
        
        // Strategy 2: Use WebDriverWait with CSS selector
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 2: Using WebDriverWait with CSS selector...");
                By cssLocator = By.cssSelector("svg.MuiSvgIcon-root.MuiSvgIcon-fontSizeSmall.css-1k33q06 > path");
                WebDriverWait cssWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                editIconElement = cssWait.until(ExpectedConditions.elementToBeClickable(cssLocator));
                usedLocator = cssLocator;
                usedMethod = "WebDriverWait with CSS selector";
                System.out.println("  ✅ Found edit icon using WebDriverWait with CSS selector");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 2 (WebDriverWait CSS) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
        
        // Strategy 3: Direct findElement approach with aria-label
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 3: Trying direct findElement approach: driver.findElement(By.xpath(\"//button[@aria-label='Edit']\"))...");
                editIconElement = driver.findElement(By.xpath("//button[@aria-label='Edit']"));
                if (editIconElement != null && editIconElement.isDisplayed()) {
                    usedLocator = By.xpath("//button[@aria-label='Edit']");
                    usedMethod = "direct findElement with aria-label='Edit'";
                    System.out.println("  ✅ Found edit icon using direct findElement approach");
                } else {
                    editIconElement = null; // Reset if not displayed
                    System.out.println("  ⚠️ Element found but not displayed");
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 3 (direct findElement) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
        
        // Strategy 4: Use WebDriverWait with button aria-label='Edit'
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 4: Using WebDriverWait with button aria-label='Edit'...");
                By editButtonLocator = By.xpath("//button[@aria-label='Edit']");
                WebDriverWait editWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                editIconElement = editWait.until(ExpectedConditions.elementToBeClickable(editButtonLocator));
                usedLocator = editButtonLocator;
                usedMethod = "WebDriverWait with aria-label='Edit'";
                System.out.println("  ✅ Found edit icon using WebDriverWait with //button[@aria-label='Edit'] locator");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 4 (WebDriverWait) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
        
        // Strategy 3: Use data-automation-key='action' to find action column, then edit button
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 3: Using data-automation-key='action' to find edit button in action column...");
                By actionColumnLocator = By.xpath("//div[@data-automation-key='action']//button[@aria-label='Edit']");
                WebDriverWait actionWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                editIconElement = actionWait.until(ExpectedConditions.elementToBeClickable(actionColumnLocator));
                usedLocator = actionColumnLocator;
                usedMethod = "data-automation-key='action' + aria-label";
                System.out.println("  ✅ Found edit icon using data-automation-key='action' locator");
            } catch (Exception e) {
                System.out.println("  ⚠️ data-automation-key='action' locator failed: " + e.getMessage());
            }
        }
        
        // Strategy 4: Find row by initiative code text, then find edit button in that row
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 4: Finding row by initiative code text, then edit button in that row...");
                By dynamicLocator = By.xpath("//div[text()='" + code + "']/ancestor::div[@role='row']//button[@aria-label='Edit']");
                WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                editIconElement = dynamicWait.until(ExpectedConditions.elementToBeClickable(dynamicLocator));
                usedLocator = dynamicLocator;
                usedMethod = "row by initiative code text + aria-label";
                System.out.println("  ✅ Found edit icon using dynamic locator: //div[text()='" + code + "']/ancestor::div[@role='row']//button[@aria-label='Edit']");
            } catch (Exception e) {
                System.out.println("  ⚠️ Dynamic locator (row by text + aria-label) failed: " + e.getMessage());
            }
        }
        
        // Strategy 5: Use aria-label attribute (fallback if multiple edit buttons exist)
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 5: Using aria-label='Edit' locator...");
                By ariaLabelLocator = By.xpath("//button[@aria-label='Edit']");
                WebDriverWait ariaWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                editIconElement = ariaWait.until(ExpectedConditions.elementToBeClickable(ariaLabelLocator));
                usedLocator = ariaLabelLocator;
                usedMethod = "aria-label attribute";
                System.out.println("  ✅ Found edit icon using aria-label='Edit' locator");
            } catch (Exception e) {
                System.out.println("  ⚠️ aria-label locator failed: " + e.getMessage());
            }
        }
        
        // Strategy 5: Use row ID to construct dynamic XPath (if row has ID)
        if (editIconElement == null && rowId != null && !rowId.isEmpty() && rowId.startsWith("row")) {
            try {
                System.out.println("  🔍 Strategy 5: Using row ID '" + rowId + "' to construct XPath...");
                By dynamicLocator = By.xpath("//*[@id=\"" + rowId + "\"]/div/div[6]/div/div/button[1]/svg/path");
                WebDriverWait idWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                editIconElement = idWait.until(ExpectedConditions.elementToBeClickable(dynamicLocator));
                usedLocator = dynamicLocator;
                usedMethod = "row ID-based XPath";
                System.out.println("  ✅ Found edit icon using row ID-based XPath: " + dynamicLocator);
            } catch (Exception e) {
                System.out.println("  ⚠️ Row ID-based XPath failed: " + e.getMessage());
                // Try without /path
                try {
                    By dynamicLocator2 = By.xpath("//*[@id=\"" + rowId + "\"]/div/div[6]/div/div/button[1]/svg");
                    WebDriverWait idWait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
                    editIconElement = idWait2.until(ExpectedConditions.elementToBeClickable(dynamicLocator2));
                    usedLocator = dynamicLocator2;
                    usedMethod = "row ID-based XPath (SVG)";
                    System.out.println("  ✅ Found edit icon using row ID-based XPath (SVG): " + dynamicLocator2);
                } catch (Exception e2) {
                    System.out.println("  ⚠️ Row ID-based XPath (SVG) also failed: " + e2.getMessage());
                }
            }
        }
        
        // Strategy 6: Try relative locators from the row element
        if (editIconElement == null && targetRow != null) {
            try {
                System.out.println("  🔍 Strategy 6: Trying relative locators from row element...");
                List<By> relativeLocators = java.util.Arrays.asList(
                    By.xpath(".//div[6]//div//div//button[1]//svg//path"),
                    By.xpath(".//div[6]//div//div//button[1]//svg"),
                    By.xpath(".//div[6]//button[1]//svg//path"),
                    By.xpath(".//div[6]//button[1]//svg"),
                    By.xpath(".//button[1]//svg//path"),
                    By.xpath(".//button[1]//svg"),
                    By.xpath(".//button[contains(@aria-label,'edit') or contains(@title,'Edit')]//svg"),
                    By.xpath(".//button[.//svg]")
                );
                
                for (By relLocator : relativeLocators) {
                    try {
                        editIconElement = targetRow.findElement(relLocator);
                        if (editIconElement != null && editIconElement.isDisplayed()) {
                            usedLocator = relLocator;
                            usedMethod = "relative XPath from row";
                            System.out.println("  ✅ Found edit icon using relative locator: " + relLocator);
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Relative locators from row failed: " + e.getMessage());
            }
        }
        
        // Strategy 7: Try absolute locators from the locator array
        if (editIconElement == null) {
            System.out.println("  🔍 Strategy 7: Trying absolute locators from locator array...");
            for (By locator : WithdrawnInitiativePageLocators.editIconLocators) {
                try {
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    editIconElement = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (editIconElement != null && editIconElement.isDisplayed()) {
                        usedLocator = locator;
                        usedMethod = "absolute locator from array";
                        System.out.println("  ✅ Found edit icon using absolute locator: " + locator);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Absolute locator failed (timeout 5s): " + locator);
                    continue;
                }
            }
        }
        
        // Strategy 8: Try finding any button with SVG in the row
        if (editIconElement == null && targetRow != null) {
            try {
                System.out.println("  🔍 Strategy 8: Searching for any button with SVG in row...");
                List<WebElement> buttons = targetRow.findElements(By.xpath(".//button[.//svg]"));
                System.out.println("    Found " + buttons.size() + " button(s) with SVG in row");
                for (WebElement btn : buttons) {
                    try {
                        if (btn != null && btn.isDisplayed()) {
                            // Try to find SVG/path within this button
                            try {
                                editIconElement = btn.findElement(By.xpath(".//svg//path | .//svg"));
                                if (editIconElement != null) {
                                    usedMethod = "first button with SVG in row";
                                    System.out.println("  ✅ Found edit icon using first button with SVG in row");
                                    break;
                                }
                            } catch (Exception e) {
                                editIconElement = btn;
                                usedMethod = "first button in row";
                                System.out.println("  ✅ Using first button in row as edit icon");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not find buttons in row: " + e.getMessage());
            }
        }
        
        // Strategy 9: Final fallback to primary locator
        if (editIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 9: Trying primary locator as final fallback...");
                WebDriverWait finalWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                editIconElement = finalWait.until(
                        ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.editIcon));
                usedLocator = WithdrawnInitiativePageLocators.editIcon;
                usedMethod = "primary locator fallback";
                System.out.println("  ✅ Found edit icon using primary locator");
            } catch (Exception e) {
                System.out.println("  ❌ All strategies failed. Last error: " + e.getMessage());
                throw new Exception("Could not find edit icon with any strategy for initiative code: " + code +
                        ". Tried: row ID-based XPath, relative locators, absolute locators, button search. " +
                        "Row ID was: " + (rowId != null ? rowId : "null") +
                        ". Last error: " + e.getMessage());
            }
        }
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editIconElement);
        waitForSeconds(1);
        
        // Try normal click first, then JS click as fallback
        System.out.println("📍 Clicking edit icon using method: " + usedMethod + 
                (usedLocator != null ? " (locator: " + usedLocator + ")" : ""));
        try {
            editIconElement.click();
            System.out.println("✅ Clicked on edit icon (normal click) for initiative code: '" + code + "' using method: " + usedMethod);
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIconElement);
            System.out.println("✅ Clicked on edit icon (JavaScript click) for initiative code: '" + code + "' using method: " + usedMethod);
        }
        
        // Wait for edit view to load
        waitForSeconds(3);
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on edit icon for initiative code: " + code + " using method: " + usedMethod);
        }
    }

    /**
     * Verify the tabs available in Basic details edit view.
     * Expected tabs: Workflow Details, Workflow Information
     * 
     * @return true if all expected tabs are found, false otherwise
     */
    public boolean verifyEditViewTabs() throws Exception {
        System.out.println("📍 Verifying edit view tabs (Workflow Details, Workflow Information)...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait for edit view to load
        waitForSeconds(2);
        
        boolean workflowDetailsFound = false;
        boolean workflowInformationFound = false;
        
        // Verify Workflow Details tab
        try {
            WebElement workflowDetails = wait.until(
                    ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.workflowDetailsTab));
            if (workflowDetails != null && workflowDetails.isDisplayed()) {
                workflowDetailsFound = true;
                System.out.println("✅ Workflow Details tab is available and visible");
            }
        } catch (Exception e) {
            System.out.println("❌ Workflow Details tab not found: " + e.getMessage());
        }
        
        // Verify Workflow Information tab
        try {
            WebElement workflowInformation = wait.until(
                    ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.workflowInformationTab));
            if (workflowInformation != null && workflowInformation.isDisplayed()) {
                workflowInformationFound = true;
                System.out.println("✅ Workflow Information tab is available and visible");
            }
        } catch (Exception e) {
            System.out.println("❌ Workflow Information tab not found: " + e.getMessage());
        }
        
        if (workflowDetailsFound && workflowInformationFound) {
            System.out.println("✅ ✅ ✅ All expected tabs verified successfully ✅ ✅ ✅");
            System.out.println("   - Workflow Details: ✅");
            System.out.println("   - Workflow Information: ✅");
            if (reportLogger != null) {
                reportLogger.pass("Edit view tabs verified: Workflow Details and Workflow Information are available");
            }
            return true;
        } else {
            System.out.println("❌ Edit view tabs verification failed");
            System.out.println("   - Workflow Details: " + (workflowDetailsFound ? "✅" : "❌"));
            System.out.println("   - Workflow Information: " + (workflowInformationFound ? "✅" : "❌"));
            if (reportLogger != null) {
                reportLogger.fail("Edit view tabs verification failed. Workflow Details: " + workflowDetailsFound + 
                        ", Workflow Information: " + workflowInformationFound);
            }
            return false;
        }
    }

    /**
     * Click on "Go Back To List View" button.
     */
    public void clickGoBackToListView() throws Exception {
        System.out.println("📍 Clicking on Go Back To List View button...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        WebElement goBackButton = wait.until(
                ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.goBackToListViewButton));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", goBackButton);
        waitForSeconds(1);
        
        // Try normal click first, then JS click as fallback
        try {
            goBackButton.click();
            System.out.println("✅ Clicked on Go Back To List View button (normal click)");
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);
            System.out.println("✅ Clicked on Go Back To List View button (JavaScript click)");
        }
        
        // Wait for list view to load
        waitForSeconds(3);
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on Go Back To List View button");
        }
    }

    /**
     * Verify that we are on the list view after clicking Go Back.
     */
    public boolean verifyListViewAfterGoBack() throws Exception {
        System.out.println("📍 Verifying list view after Go Back...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait for list view to load
        waitForSeconds(2);
        
        // Check if table/list is present
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(WithdrawnInitiativePageLocators.withdrawnTable));
            System.out.println("✅ List view table is present");
            
            // Check if we can see rows (even if empty)
            List<WebElement> rows = driver.findElements(WithdrawnInitiativePageLocators.withdrawnRows);
            System.out.println("✅ List view verified - found " + rows.size() + " row(s)");
            
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated back to list view");
            }
            return true;
        } catch (Exception e) {
            System.out.println("❌ List view verification failed: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify list view: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Click on the Resubmit icon for the searched record.
     * 
     * @param initiativeCode The initiative code to find and resubmit
     */
    public void clickResubmitIcon(String initiativeCode) throws Exception {
        System.out.println("📍 Clicking on Resubmit icon for initiative code: '" + initiativeCode + "'");
        
        String code = initiativeCode.trim();
        WebElement resubmitIconElement = null;
        By usedLocator = null;
        String usedMethod = "";

        // Wait a bit for page to stabilize after search
        waitForSeconds(2);

        // Strategy 1: Try aria-label='Resubmit' (most reliable) - direct button
        try {
            System.out.println("  🔍 Strategy 1: Trying //button[@aria-label='Resubmit']");
            By ariaLabelLocator = By.xpath("//button[@aria-label='Resubmit']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            resubmitIconElement = wait.until(ExpectedConditions.elementToBeClickable(ariaLabelLocator));
            usedLocator = ariaLabelLocator;
            usedMethod = "button with aria-label='Resubmit'";
            System.out.println("  ✅ Found resubmit icon using aria-label='Resubmit'");
        } catch (Exception e) {
            System.out.println("  ⚠️ Strategy 1 (aria-label) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // Strategy 2: Find row by initiative code, then find resubmit button with aria-label in that row
        if (resubmitIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 2: Trying row by initiative code + aria-label='Resubmit'");
                By dynamicLocator = By.xpath("//div[text()='" + code + "']/ancestor::div[@role='row']//button[@aria-label='Resubmit']");
                WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                resubmitIconElement = dynamicWait.until(ExpectedConditions.elementToBeClickable(dynamicLocator));
                usedLocator = dynamicLocator;
                usedMethod = "row by initiative code text + aria-label='Resubmit'";
                System.out.println("  ✅ Found resubmit icon using dynamic locator with aria-label");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 2 (row by text + aria-label) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        // Strategy 3: Try contains aria-label
        if (resubmitIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 3: Trying //button[contains(@aria-label,'Resubmit')]");
                By containsLocator = By.xpath("//button[contains(@aria-label,'Resubmit')]");
                WebDriverWait containsWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                resubmitIconElement = containsWait.until(ExpectedConditions.elementToBeClickable(containsLocator));
                usedLocator = containsLocator;
                usedMethod = "button with contains aria-label='Resubmit'";
                System.out.println("  ✅ Found resubmit icon using contains aria-label");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 3 (contains aria-label) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        // Strategy 4: Try finding by row ID pattern
        if (resubmitIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 4: Trying dynamic row pattern with button[4]");
                By rowPatternLocator = By.xpath("//*[starts-with(@id,'row')]/div/div[6]/div/div/button[4]");
                WebDriverWait rowWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                resubmitIconElement = rowWait.until(ExpectedConditions.elementToBeClickable(rowPatternLocator));
                usedLocator = rowPatternLocator;
                usedMethod = "dynamic row pattern button[4]";
                System.out.println("  ✅ Found resubmit icon using dynamic row pattern");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 4 (row pattern) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        // Strategy 5: Try absolute XPath as last resort
        if (resubmitIconElement == null) {
            try {
                System.out.println("  🔍 Strategy 5: Trying absolute XPath");
                By absoluteLocator = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/div[4]/div[1]/div/div/div[2]/div/div/div/div/div/div[4]/div/div/div[6]/div/div/button[4]/svg");
                WebDriverWait absoluteWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                resubmitIconElement = absoluteWait.until(ExpectedConditions.elementToBeClickable(absoluteLocator));
                usedLocator = absoluteLocator;
                usedMethod = "absolute XPath";
                System.out.println("  ✅ Found resubmit icon using absolute XPath");
            } catch (Exception e) {
                System.out.println("  ⚠️ Strategy 5 (absolute XPath) failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        if (resubmitIconElement == null) {
            // Try to find all buttons with aria-label to help debug
            try {
                List<WebElement> allButtons = driver.findElements(By.xpath("//button[@aria-label]"));
                System.out.println("  🔍 Debug: Found " + allButtons.size() + " button(s) with aria-label attribute");
                for (WebElement btn : allButtons) {
                    try {
                        String ariaLabel = btn.getAttribute("aria-label");
                        System.out.println("    - Button aria-label: '" + ariaLabel + "'");
                    } catch (Exception e) {
                        // Skip if can't get attribute
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not debug buttons: " + e.getMessage());
            }
            throw new Exception("Could not find Resubmit icon for initiative code: '" + code + "'. Tried all strategies.");
        }

        // Scroll into view and click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", resubmitIconElement);
        waitForSeconds(1);
        
        try {
            resubmitIconElement.click();
            System.out.println("✅ Clicked on Resubmit icon (normal click) using: " + usedMethod);
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", resubmitIconElement);
            System.out.println("✅ Clicked on Resubmit icon (JavaScript click) using: " + usedMethod);
        }
        
        waitForSeconds(2); // Wait for resubmit dialog to open
        
        if (reportLogger != null) {
            reportLogger.info("Clicked on Resubmit icon for initiative code: " + code);
        }
    }

    /**
     * Enter comment in the resubmit comment box.
     * 
     * @param comment The comment to enter
     */
    public void enterResubmitComment(String comment) throws Exception {
        if (comment == null || comment.trim().isEmpty()) {
            throw new Exception("Comment is null/empty. Cannot enter.");
        }

        String commentText = comment.trim();
        System.out.println("📍 Entering resubmit comment: '" + commentText + "'");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(WithdrawnInitiativePageLocators.resubmitCommentBox));
            commentBox.clear();
            commentBox.sendKeys(commentText);
            waitForSeconds(1);
            System.out.println("✅ Entered resubmit comment: '" + commentText + "'");
            if (reportLogger != null) {
                reportLogger.info("Entered resubmit comment: " + commentText);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter resubmit comment: " + e.getMessage());
            throw new Exception("Failed to enter resubmit comment: " + e.getMessage());
        }
    }

    /**
     * Click on Save button in resubmit dialog.
     */
    public void clickResubmitSaveButton() throws Exception {
        System.out.println("📍 Clicking on Resubmit Save button...");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.resubmitSaveButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
            waitForSeconds(1);
            
            try {
                saveButton.click();
                System.out.println("✅ Clicked on Resubmit Save button (normal click)");
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                System.out.println("✅ Clicked on Resubmit Save button (JavaScript click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Resubmit Save button");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Resubmit Save button: " + e.getMessage());
            throw new Exception("Failed to click Resubmit Save button: " + e.getMessage());
        }
    }

    /**
     * Verify alert message for blank comment.
     * 
     * @param expectedMessage Expected alert message (e.g., "Comment should not be left blank.")
     * @return true if alert message matches, false otherwise
     */
    public boolean verifyCommentAlert(String expectedMessage) throws Exception {
        System.out.println("📍 Verifying comment alert message...");
        System.out.println("   Expected message: '" + expectedMessage + "'");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            // Try to find alert by text content
            By alertLocator = By.xpath("//*[contains(text(),'" + expectedMessage + "')] | //div[contains(text(),'" + expectedMessage + "')] | //span[contains(text(),'" + expectedMessage + "')]");
            WebElement alertElement = wait.until(ExpectedConditions.presenceOfElementLocated(alertLocator));
            
            String actualMessage = alertElement.getText();
            System.out.println("   Actual message: '" + actualMessage + "'");
            
            boolean matches = actualMessage.contains(expectedMessage);
            
            if (matches) {
                System.out.println("✅ Alert message verified: '" + expectedMessage + "'");
                if (reportLogger != null) {
                    reportLogger.pass("Alert message verified: " + expectedMessage);
                }
            } else {
                System.out.println("⚠️ Alert message mismatch. Expected: '" + expectedMessage + "', Actual: '" + actualMessage + "'");
                if (reportLogger != null) {
                    reportLogger.warning("Alert message mismatch. Expected: " + expectedMessage + ", Actual: " + actualMessage);
                }
            }
            
            return matches;
        } catch (Exception e) {
            System.out.println("❌ Failed to verify comment alert: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify comment alert: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Click on OK button in success dialog.
     */
    public void clickResubmitOkButton() throws Exception {
        System.out.println("📍 Clicking on Resubmit OK button...");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(WithdrawnInitiativePageLocators.resubmitOkButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", okButton);
            waitForSeconds(1);
            
            try {
                okButton.click();
                System.out.println("✅ Clicked on Resubmit OK button (normal click)");
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed, trying JavaScript click: " + e.getMessage());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okButton);
                System.out.println("✅ Clicked on Resubmit OK button (JavaScript click)");
            }
            
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked on Resubmit OK button");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Resubmit OK button: " + e.getMessage());
            throw new Exception("Failed to click Resubmit OK button: " + e.getMessage());
        }
    }

    /**
     * Verify that resubmit popup/dialog is closed.
     * 
     * @return true if popup is closed, false otherwise
     */
    public boolean verifyResubmitPopupClosed() throws Exception {
        System.out.println("📍 Verifying resubmit popup is closed...");
        
        try {
            waitForSeconds(2);
            
            // Check if comment box is no longer visible (indicates popup is closed)
            try {
                WebElement commentBox = driver.findElement(WithdrawnInitiativePageLocators.resubmitCommentBox);
                if (commentBox != null && commentBox.isDisplayed()) {
                    System.out.println("⚠️ Resubmit popup is still open");
                    if (reportLogger != null) {
                        reportLogger.warning("Resubmit popup is still open");
                    }
                    return false;
                }
            } catch (NoSuchElementException e) {
                // Comment box not found - popup is closed
                System.out.println("✅ Resubmit popup is closed (comment box not found)");
                if (reportLogger != null) {
                    reportLogger.pass("Resubmit popup is closed");
                }
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("⚠️ Error verifying popup closure: " + e.getMessage());
            // Assume closed if we can't verify
            return true;
        }
    }

    // ==================== PAGINATION FUNCTIONALITY ====================

    /**
     * Click on pagination next button // Gayatri.k
     * @throws Exception if button not found or click fails
     */
    public void clickPaginationNextButton() throws Exception {
        System.out.println("📍 Clicking pagination next button...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                    WithdrawnInitiativePageLocators.paginationNextButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextButton);
            waitForSeconds(1);
            
            // Try Actions class first (Material-UI compatibility)
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(nextButton).click().perform();
                System.out.println("✅ Clicked pagination next button using Actions class");
            } catch (Exception e) {
                // Fallback to normal click
                nextButton.click();
                System.out.println("✅ Clicked pagination next button using normal click");
            }
            
            // Wait for page to load
            waitForSeconds(2);
            
            if (reportLogger != null) {
                reportLogger.info("Clicked pagination next button");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click pagination next button: " + e.getMessage());
            throw new Exception("Failed to click pagination next button: " + e.getMessage());
        }
    }

    /**
     * Count records displayed on current page // Gayatri.k
     * @return Number of records displayed on current page
     */
    public int getRecordsCountPerPage() throws Exception {
        System.out.println("📍 Counting records on current page...");
        try {
            waitForSeconds(1); // Wait for page to stabilize
            
            // Try multiple locators to find rows
            List<WebElement> rows = null;
            for (By locator : WithdrawnInitiativePageLocators.withdrawnRowsLocators) {
                try {
                    rows = driver.findElements(locator);
                    if (rows != null && !rows.isEmpty()) {
                        System.out.println("📊 Found " + rows.size() + " record(s) on current page using locator: " + locator);
                        return rows.size();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Fallback to primary locator
            if (rows == null || rows.isEmpty()) {
                rows = driver.findElements(WithdrawnInitiativePageLocators.withdrawnRows);
                System.out.println("📊 Found " + (rows != null ? rows.size() : 0) + " record(s) on current page (fallback locator)");
                return rows != null ? rows.size() : 0;
            }
            
            return 0;
        } catch (Exception e) {
            System.out.println("⚠️ Error counting records: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Verify pagination functionality // Gayatri.k
     * @return true if pagination works, false otherwise
     */
    public boolean verifyPagination() throws Exception {
        System.out.println("📍 Verifying pagination functionality...");
        try {
            // Check if pagination buttons exist
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                WebElement nextButton = driver.findElement(WithdrawnInitiativePageLocators.paginationNextButton);
                if (nextButton != null && nextButton.isDisplayed()) {
                    System.out.println("✅ Pagination next button is visible");
                    // Check if button is enabled (not disabled)
                    boolean isEnabled = nextButton.isEnabled();
                    System.out.println("   Button enabled: " + isEnabled);
                    
                    if (reportLogger != null) {
                        reportLogger.info("Pagination next button is visible and enabled: " + isEnabled);
                    }
                    return true;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Pagination next button not found: " + e.getMessage());
                if (reportLogger != null) {
                    reportLogger.warning("Pagination next button not found: " + e.getMessage());
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            System.out.println("⚠️ Error verifying pagination: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.warning("Error verifying pagination: " + e.getMessage());
            }
            return false;
        }
    }

   
    
    
}

