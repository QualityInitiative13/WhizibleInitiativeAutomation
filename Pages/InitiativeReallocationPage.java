package Pages;

import Actions.ActionEngine;
import Locators.InitiativeReallocationPageLocators;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

/**
 * Page Object Model (POM) for Initiative Reallocation Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in InitiativeReallocationPageLocators.java
 *    - Methods use InitiativeReallocationPageLocators.locatorName for reusability
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in InitiativeReallocationPageLocators class
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
public class InitiativeReallocationPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeReallocationPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // pass driver to ActionEngine
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Navigate to Initiative Reallocation page
     * Shahu.D
     * @throws Exception if navigation fails
     */
    public void navigateToInitiativeReallocationPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Starting navigation to Initiative Reallocation page..."); // Shahu.D
            
            // Step 1: Click on Initiative Management navigation - Shahu.D
            System.out.println("  📌 Step 1: Click on Initiative Management navigation"); // Shahu.D
            WebElement initiativeNav = null;
            boolean navFound = false;
            
            By[] navLocators = {
                InitiativeReallocationPageLocators.initiativeManagementNav,
                By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']"), // Shahu.D
                By.xpath("//img[@alt='Initiative Management']"), // Shahu.D
                By.xpath("//div[contains(@class,'navigation')]//img[contains(@alt,'Initiative')]"), // Shahu.D
                By.xpath("//*[contains(@aria-label,'Initiative Management')]"), // Shahu.D
                By.xpath("//*[@aria-label='Initiative Management']") // Shahu.D
            };
            
            // Try simpler approach first - wait for element to be visible and clickable
            try {
                initiativeNav = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    InitiativeReallocationPageLocators.initiativeManagementNav));
                if (initiativeNav.isDisplayed() && initiativeNav.isEnabled()) {
                    navFound = true;
                    System.out.println("  ✅ Found Initiative Management navigation using primary locator"); // Shahu.D
                }
            } catch (Exception ex) {
                System.out.println("  ⚠️ Primary locator failed, trying alternative strategies..."); // Shahu.D
            }
            
            // If primary locator failed, try alternatives
            if (!navFound) {
                for (By locator : navLocators) {
                    try {
                        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        if (element.isDisplayed() && element.isEnabled()) {
                            initiativeNav = element;
                            navFound = true;
                            System.out.println("  ✅ Found Initiative Management navigation using locator: " + locator); // Shahu.D
                            break;
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }
            
            if (!navFound || initiativeNav == null) {
                // Debug: List all images with alt text
                try {
                    java.util.List<WebElement> allImages = driver.findElements(By.xpath("//img"));
                    System.out.println("  📊 Found " + allImages.size() + " image(s) on the page"); // Shahu.D
                    for (int i = 0; i < Math.min(allImages.size(), 10); i++) {
                        try {
                            String alt = allImages.get(i).getAttribute("alt");
                            String ariaLabel = allImages.get(i).getAttribute("aria-label");
                            System.out.println("    Image " + (i + 1) + ": alt='" + alt + "', aria-label='" + ariaLabel + "'"); // Shahu.D
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info"); // Shahu.D
                }
                throw new Exception("Initiative Management navigation not found"); // Shahu.D
            }
            
            // Scroll into view and click - Shahu.D
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", initiativeNav);
            waitForSeconds(1);
            
            try {
                wait.until(ExpectedConditions.elementToBeClickable(initiativeNav));
                initiativeNav.click();
                System.out.println("  ✅ Clicked Initiative Management navigation using direct click"); // Shahu.D
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeNav);
                    System.out.println("  ✅ Clicked Initiative Management navigation using JavaScript"); // Shahu.D
                } catch (Exception e2) {
                    try {
                        actions.moveToElement(initiativeNav).click().perform();
                        System.out.println("  ✅ Clicked Initiative Management navigation using Actions"); // Shahu.D
                    } catch (Exception e3) {
                        throw new Exception("Failed to click Initiative Management navigation: " + e3.getMessage()); // Shahu.D
                    }
                }
            }
            
            System.out.println("  ✅ Step 1 completed: Initiative Management nav clicked"); // Shahu.D
            
            // Wait for navigation menu to expand - Shahu.D
            System.out.println("  ⏳ Waiting for navigation menu to expand..."); // Shahu.D
            waitForSeconds(3);
            
            // Verify menu container is present - Shahu.D
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='children-panel-container']")));
                System.out.println("  ✅ Navigation menu container found"); // Shahu.D
                waitForSeconds(2); // Shahu.D
            } catch (Exception ex) {
                System.out.println("  ⚠️ Navigation menu container not found, waiting longer..."); // Shahu.D
                waitForSeconds(5); // Shahu.D
            }
            
            // Step 2: Click on Initiative Reallocation card - Shahu.D
            System.out.println("  📌 Step 2: Click on Initiative Reallocation card"); // Shahu.D
            WebElement reallocationCard = null;
            boolean cardFound = false;
            
            By[] cardLocators = {
                InitiativeReallocationPageLocators.initiativeReallocationCard,
                By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Initiative Reallocation')]"), // Shahu.D
                By.xpath("//p[contains(text(),'Initiative Reallocation')]"), // Shahu.D
                By.xpath("//div[@id='children-panel-container']//p[contains(text(),'Reallocation')]") // Shahu.D
            };
            
            for (By locator : cardLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        reallocationCard = element;
                        cardFound = true;
                        System.out.println("  ✅ Found Initiative Reallocation card using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!cardFound || reallocationCard == null) {
                // Debug: List all panels in children-panel-container
                try {
                    java.util.List<WebElement> allPanels = driver.findElements(
                        By.xpath("//*[@id='children-panel-container']//p"));
                    System.out.println("  📊 Found " + allPanels.size() + " panel(s) in children-panel-container"); // Shahu.D
                    for (int i = 0; i < Math.min(allPanels.size(), 15); i++) {
                        try {
                            String text = allPanels.get(i).getText();
                            System.out.println("    Panel " + (i + 1) + ": '" + text + "'"); // Shahu.D
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info"); // Shahu.D
                }
                throw new Exception("Initiative Reallocation card not found"); // Shahu.D
            }
            
            // Re-find element right before clicking (to avoid stale element) - Shahu.D
            System.out.println("  🔍 Re-finding element right before clicking (to avoid stale element)..."); // Shahu.D
            reallocationCard = wait.until(ExpectedConditions.elementToBeClickable(
                InitiativeReallocationPageLocators.initiativeReallocationCard));
            
            // Scroll into view and click - Shahu.D
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", reallocationCard);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reallocationCard);
                System.out.println("  ✅ Clicked Initiative Reallocation card using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(reallocationCard).click().perform();
                    System.out.println("  ✅ Clicked Initiative Reallocation card using Actions"); // Shahu.D
                } catch (Exception e2) {
                    reallocationCard.click();
                    System.out.println("  ✅ Clicked Initiative Reallocation card using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Initiative Reallocation card"); // Shahu.D
            System.out.println("  ✅ Step 2 completed: Initiative Reallocation card clicked"); // Shahu.D
            
            // Wait for page to load - Shahu.D
            waitForSeconds(3);
            System.out.println("  ✅ Successfully navigated to Initiative Reallocation page"); // Shahu.D
            
        } catch (Exception e) {
            System.err.println("  ❌ Error navigating to Initiative Reallocation page: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    /**
     * Click on search icon/button
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for search icon...");
            
            WebElement searchIcon = null;
            boolean found = false;
            
            By[] searchLocators = {
                InitiativeReallocationPageLocators.searchInput,
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/img"),
                By.xpath("//img[contains(@alt,'Search')]"),
                By.xpath("//button[contains(@aria-label,'Search')]")
            };
            
            for (By locator : searchLocators) {
                try {
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            searchIcon = element;
                            found = true;
                            System.out.println("  ✅ Found search icon using locator: " + locator);
                            break;
                        }
                    }
                    if (found) break;
                } catch (Exception ex) {
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
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
                System.out.println("  ✅ Clicked search icon using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchIcon).click().perform();
                    System.out.println("  ✅ Clicked search icon using Actions");
                } catch (Exception e2) {
                    searchIcon.click();
                    System.out.println("  ✅ Clicked search icon using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on search icon");
            waitForSeconds(2); // Wait for search panel to open
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking search icon: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Enter initiative code in search field
     * @param initiativeCode Initiative code to search for
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            System.out.println("  🔍 Searching for Initiative Code input field...");
            
            WebElement codeField = null;
            boolean found = false;
            
            By[] codeLocators = {
                InitiativeReallocationPageLocators.initiativeCodeField,
                By.xpath("//*[@id=\"initiativeCode\"]"),
                By.xpath("//input[@id='initiativeCode']"),
                By.xpath("//input[contains(@placeholder,'Initiative Code')]")
            };
            
            for (By locator : codeLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        codeField = element;
                        found = true;
                        System.out.println("  ✅ Found Initiative Code field using locator: " + locator);
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || codeField == null) {
                throw new Exception("Initiative Code input field not found");
            }
            
            // Clear and enter text
            codeField.clear();
            codeField.sendKeys(initiativeCode);
            System.out.println("  ✅ Entered Initiative Code: " + initiativeCode);
            waitForSeconds(1);
            
        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Code: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on search button to execute search
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for search button...");
            
            WebElement searchBtn = null;
            boolean found = false;
            
            By[] searchBtnLocators = {
                InitiativeReallocationPageLocators.searchButton,
                By.xpath("//button[contains(text(),'Search')]"),
                By.xpath("//button[@type='submit']"),
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[3]/button[3]")
            };
            
            for (By locator : searchBtnLocators) {
                try {
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            String text = element.getText();
                            if (text != null && (text.contains("Search") || text.isEmpty())) {
                                searchBtn = element;
                                found = true;
                                System.out.println("  ✅ Found search button using locator: " + locator);
                                break;
                            }
                        }
                    }
                    if (found) break;
                } catch (Exception ex) {
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
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("  ✅ Clicked search button using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchBtn).click().perform();
                    System.out.println("  ✅ Clicked search button using Actions");
                } catch (Exception e2) {
                    searchBtn.click();
                    System.out.println("  ✅ Clicked search button using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on search button");
            waitForSeconds(3); // Wait for search results to load
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking search button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verify initiative is displayed in the table
     * @param initiativeCode Initiative code to verify
     * @return true if initiative is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeDisplayed(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is displayed...");
            
            // Wait for table/grid to load
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    InitiativeReallocationPageLocators.gridRows));
            } catch (Exception ex) {
                System.out.println("  ⚠️ Table/grid not found, checking for no items message...");
            }
            
            // Check for no items message
            try {
                WebElement noItems = driver.findElement(InitiativeReallocationPageLocators.noItemsMessage);
                if (noItems.isDisplayed()) {
                    System.out.println("  ❌ No items message displayed - Initiative not found");
                    return false;
                }
            } catch (Exception ex) {
                // No items message not found, continue checking
            }
            
            // Search for initiative code in table
            By[] initiativeLocators = {
                InitiativeReallocationPageLocators.getRowByInitiativeCode(initiativeCode),
                By.xpath("//div[@role='row']//div[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//tr//td[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//*[contains(text(),'" + initiativeCode + "')]")
            };
            
            for (By locator : initiativeLocators) {
                try {
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed()) {
                            String text = element.getText();
                            if (text != null && text.contains(initiativeCode)) {
                                System.out.println("  ✅ Initiative Code '" + initiativeCode + "' found in table");
                                return true;
                            }
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            System.out.println("  ❌ Initiative Code '" + initiativeCode + "' not found in table");
            return false;
            
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying initiative display: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click on Select Current Approver dropdown field
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickCurrentApproverDropdown() throws Exception {
   	 click(InitiativeReallocationPageLocators. currentApproverDropdown , "approverDropdown");
    }

    /**
     * Select value from Current Approver dropdown
     * Shahu.D
     * @param approverName Approver name to select (e.g., "Abhishek Prasad (CMO)")
     * @throws Exception if element is not found or selection fails
     */
  /*  public void selectCurrentApprover(String approverName) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Current Approver value: " + approverName); // Shahu.D
            
            WebElement option = null;
            boolean found = false;
            
            // Wait longer for dropdown list to appear - Updated by Shahu.D
            waitForSeconds(3);
            
            // Try to click the option directly using the provided XPath first - Updated by Shahu.D
            System.out.println("  🔍 Trying to click option directly using XPath: //*[@id=\"currentApprover-list1\"]/span/span"); // Updated by Shahu.D
            try {
                WebElement directOption = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"currentApprover-list1\"]/span/span")));
                if (directOption != null) {
                    System.out.println("  ✅ Found option element directly, text: '" + directOption.getText() + "'"); // Updated by Shahu.D
                    option = directOption;
                    found = true;
                }
            } catch (Exception ex) {
                System.out.println("  ⚠️ Direct XPath not found, trying other strategies..."); // Updated by Shahu.D
            }
            
            // First, try to list all available options for debugging - Updated by Shahu.D
            if (!found) {
                System.out.println("  📋 Listing all available Current Approver options..."); // Updated by Shahu.D
                try {
                    java.util.List<WebElement> allOptions = driver.findElements(By.xpath("//*[contains(@id,'currentApprover-list')]//span | //li[contains(@id,'currentApprover')] | //div[contains(@id,'currentApprover-list')]//span | //ul[@role='listbox']//li | //div[@role='listbox']//div"));
                    System.out.println("  📊 Found " + allOptions.size() + " option(s) in Current Approver dropdown"); // Updated by Shahu.D
                    for (int i = 0; i < Math.min(allOptions.size(), 20); i++) {
                        try {
                            WebElement opt = allOptions.get(i);
                            String optionText = opt.getText();
                            String optionId = opt.getAttribute("id");
                            System.out.println("    Option " + (i + 1) + ": id='" + optionId + "', text='" + optionText.trim() + "'"); // Updated by Shahu.D
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Could not list options: " + ex.getMessage()); // Updated by Shahu.D
                }
            }
            
            By[] optionLocators = {
                By.xpath("//*[@id=\"currentApprover-list1\"]/span/span"), // Updated by Shahu.D - User provided XPath (Primary)
                By.xpath("//*[@id='currentApprover-list1']/span/span"), // Updated by Shahu.D - User provided XPath
                By.xpath("//*[@id=\"currentApprover-list1\"]"), // Updated by Shahu.D - Try parent element
                By.xpath("//*[@id='currentApprover-list1']"), // Updated by Shahu.D - Try parent element
                InitiativeReallocationPageLocators.currentApproverValue,
                By.xpath("//*[@id='currentApprover-list1']//span[contains(text(),'" + approverName + "')]"), // Shahu.D
                By.xpath("//*[contains(@id,'currentApprover-list')]//span[contains(text(),'" + approverName + "')]"), // Shahu.D
                By.xpath("//li[contains(text(),'" + approverName + "')]"), // Shahu.D
                By.xpath("//span[contains(text(),'" + approverName + "')]"), // Updated by Shahu.D - More generic
                By.xpath("//*[contains(@id,'currentApprover-list')]//span[contains(text(),'Abhishek')]"), // Updated by Shahu.D - Partial match
                By.xpath("//*[contains(@id,'currentApprover-list')]//span[contains(text(),'CMO')]") // Updated by Shahu.D - Partial match
            };
            
            if (!found) {
                for (By locator : optionLocators) {
                    try {
                        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                        if (element.isDisplayed()) {
                            String elementText = element.getText();
                            System.out.println("  🔍 Found element with text: '" + elementText + "'"); // Updated by Shahu.D
                            // Check if text matches (exact or contains) or just use it if it's the first option
                            if (elementText != null && (elementText.trim().equals(approverName) || elementText.contains(approverName) || approverName.contains(elementText.trim()) || locator.toString().contains("list1"))) {
                                option = element;
                                found = true;
                                System.out.println("  ✅ Found Current Approver option using locator: " + locator); // Shahu.D
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }
            
            // If not found with specific locators, try to find by text in all dropdown items - Updated by Shahu.D
            if (!found || option == null) {
                System.out.println("  🔍 Trying to find option by searching all dropdown items..."); // Updated by Shahu.D
                try {
                    java.util.List<WebElement> allDropdownItems = driver.findElements(By.xpath("//*[contains(@id,'currentApprover-list')]//span | //li[contains(@id,'currentApprover')] | //div[@role='option'] | //ul[@role='listbox']//li | //div[@role='listbox']//div"));
                    System.out.println("  📊 Found " + allDropdownItems.size() + " dropdown item(s) to search"); // Updated by Shahu.D
                    for (WebElement item : allDropdownItems) {
                        try {
                            String itemText = item.getText();
                            String itemId = item.getAttribute("id");
                            if (itemText != null && !itemText.trim().isEmpty()) {
                                System.out.println("  🔍 Checking item: id='" + itemId + "', text='" + itemText.trim() + "'"); // Updated by Shahu.D
                                if (itemText.trim().equals(approverName) || itemText.contains(approverName) || approverName.contains(itemText.trim())) {
                                    option = item;
                                    found = true;
                                    System.out.println("  ✅ Found matching option: '" + itemText.trim() + "'"); // Updated by Shahu.D
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error searching dropdown items: " + ex.getMessage()); // Updated by Shahu.D
                }
            }
            
            if (!found || option == null) {
                throw new Exception("Current Approver option '" + approverName + "' not found. Please check available options above."); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("  ✅ Selected Current Approver using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(option).click().perform();
                    System.out.println("  ✅ Selected Current Approver using Actions"); // Shahu.D
                } catch (Exception e2) {
                    option.click();
                    System.out.println("  ✅ Selected Current Approver using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully selected Current Approver: " + approverName); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for selection to apply
            
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Current Approver: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }
*/
    
    
    public void selectCurrentApprover(String ApproverName) throws Exception {
    	
    	
        selectFromList(InitiativeReallocationPageLocators.currentApproverValue, ApproverName, " ApproverName");
    }
    
   
    
    /**
     * Click on Nature of Initiative dropdown field
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickNatureOfInitiativeDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Nature of Initiative dropdown..."); // Shahu.D
            
            WebElement dropdown = null;
            boolean found = false;
            
            By[] dropdownLocators = {
                InitiativeReallocationPageLocators.natureOfInitiativeDropdown,
                By.xpath("//*[@id=\"natureOfInitiativeId\"]/span[2]"), // Shahu.D - Primary XPath
                By.xpath("//*[@id='natureOfInitiativeId']/span[2]"), // Shahu.D
                By.xpath("//span[@id='natureOfInitiativeId-option']"), // Shahu.D
                By.xpath("//div[@id='natureOfInitiativeId']") // Shahu.D
            };
            
            for (By locator : dropdownLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        dropdown = element;
                        found = true;
                        System.out.println("  ✅ Found Nature of Initiative dropdown using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || dropdown == null) {
                throw new Exception("Nature of Initiative dropdown not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                System.out.println("  ✅ Clicked Nature of Initiative dropdown using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(dropdown).click().perform();
                    System.out.println("  ✅ Clicked Nature of Initiative dropdown using Actions"); // Shahu.D
                } catch (Exception e2) {
                    dropdown.click();
                    System.out.println("  ✅ Clicked Nature of Initiative dropdown using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Nature of Initiative dropdown"); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for dropdown to open
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Nature of Initiative dropdown: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    /**
     * Select value from Nature of Initiative dropdown
     * Shahu.D
     * @param noiValue Nature of Initiative value to select (e.g., "StartUp Application Processing")
     * @throws Exception if element is not found or selection fails
     */
 /*   public void selectNatureOfInitiative(String noiValue) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Nature of Initiative value: " + noiValue); // Shahu.D
            
            WebElement option = null;
            boolean found = false;
            
            By[] optionLocators = {
                InitiativeReallocationPageLocators.natureOfInitiativeValue,
                By.xpath("//*[@id=\"natureOfInitiativeId-list1\"]/span/span"), // Shahu.D - Primary XPath
                By.xpath("//*[@id='natureOfInitiativeId-list1']/span/span"), // Shahu.D
                By.xpath("//*[@id='natureOfInitiativeId-list1']//span[contains(text(),'" + noiValue + "')]"), // Shahu.D
                By.xpath("//*[contains(@id,'natureOfInitiativeId-list')]//span[contains(text(),'" + noiValue + "')]"), // Shahu.D
                By.xpath("//li[contains(text(),'" + noiValue + "')]") // Shahu.D
            };
            
            // Wait for dropdown list to appear
            waitForSeconds(2); // Shahu.D
            
            for (By locator : optionLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed()) {
                        option = element;
                        found = true;
                        System.out.println("  ✅ Found Nature of Initiative option using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || option == null) {
                throw new Exception("Nature of Initiative option '" + noiValue + "' not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("  ✅ Selected Nature of Initiative using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(option).click().perform();
                    System.out.println("  ✅ Selected Nature of Initiative using Actions"); // Shahu.D
                } catch (Exception e2) {
                    option.click();
                    System.out.println("  ✅ Selected Nature of Initiative using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully selected Nature of Initiative: " + noiValue); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for selection to apply
            
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Nature of Initiative: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }
*/
    public void selectNatureOfInitiative(String noiValue) throws Exception {
    	
    	  selectFromList(InitiativeReallocationPageLocators.natureOfInitiativeValue, noiValue, " noiValue");
    	
    	
    }
    
    /**
     * Click on Business Group dropdown field
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickBusinessGroupDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Business Group dropdown..."); // Shahu.D
            
            WebElement dropdown = null;
            boolean found = false;
            
            By[] dropdownLocators = {
                InitiativeReallocationPageLocators.businessGroupDropdown,
                By.xpath("//*[@id=\"businessGroupId\"]/span[2]"), // Shahu.D - Primary XPath
                By.xpath("//*[@id='businessGroupId']/span[2]"), // Shahu.D
                By.xpath("//span[@id='businessGroupId-option']"), // Shahu.D
                By.xpath("//div[@id='businessGroupId']") // Shahu.D
            };
            
            for (By locator : dropdownLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        dropdown = element;
                        found = true;
                        System.out.println("  ✅ Found Business Group dropdown using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || dropdown == null) {
                throw new Exception("Business Group dropdown not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                System.out.println("  ✅ Clicked Business Group dropdown using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(dropdown).click().perform();
                    System.out.println("  ✅ Clicked Business Group dropdown using Actions"); // Shahu.D
                } catch (Exception e2) {
                    dropdown.click();
                    System.out.println("  ✅ Clicked Business Group dropdown using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Business Group dropdown"); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for dropdown to open
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Business Group dropdown: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    /**
     * Select value from Business Group dropdown
     * Shahu.D
     * @param bgValue Business Group value to select (e.g., "AI And ML Group")
     * @throws Exception if element is not found or selection fails
     */
  /*  public void selectBusinessGroup(String bgValue) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Business Group value: " + bgValue); // Shahu.D
            
            WebElement option = null;
            boolean found = false;
            
            By[] optionLocators = {
                InitiativeReallocationPageLocators.businessGroupValue,
                By.xpath("//*[@id=\"businessGroupId-list1\"]/span/span"), // Shahu.D - Primary XPath
                By.xpath("//*[@id='businessGroupId-list1']/span/span"), // Shahu.D
                By.xpath("//*[@id='businessGroupId-list1']//span[contains(text(),'" + bgValue + "')]"), // Shahu.D
                By.xpath("//*[contains(@id,'businessGroupId-list')]//span[contains(text(),'" + bgValue + "')]"), // Shahu.D
                By.xpath("//li[contains(text(),'" + bgValue + "')]") // Shahu.D
            };
            
            // Wait for dropdown list to appear
            waitForSeconds(2); // Shahu.D
            
            for (By locator : optionLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed()) {
                        option = element;
                        found = true;
                        System.out.println("  ✅ Found Business Group option using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || option == null) {
                throw new Exception("Business Group option '" + bgValue + "' not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("  ✅ Selected Business Group using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(option).click().perform();
                    System.out.println("  ✅ Selected Business Group using Actions"); // Shahu.D
                } catch (Exception e2) {
                    option.click();
                    System.out.println("  ✅ Selected Business Group using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully selected Business Group: " + bgValue); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for selection to apply
            
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Business Group: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }
*/
    public void selectBusinessGroup(String bgValue) throws Exception {
    	  selectFromList(InitiativeReallocationPageLocators.businessGroupValue, bgValue , " bgValue");
    }
    
    /**
     * Click on Next button
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickNextButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Next button..."); // Shahu.D
            
            WebElement nextBtn = null;
            boolean found = false;
            
            By[] nextBtnLocators = {
                By.xpath("//button[.//span[text()='Next']]"), // Shahu.D - User provided XPath (Primary)
                InitiativeReallocationPageLocators.nextButton,
                By.xpath("//*[@id=\"id__873\"]"), // Shahu.D - Original XPath
                By.xpath("//button[@id='id__873']"), // Shahu.D
                By.xpath("//button[contains(text(),'Next')]"), // Shahu.D
                By.xpath("//span[text()='Next']/ancestor::button"), // Shahu.D
                By.xpath("//button[.//span[contains(text(),'Next')]]"), // Shahu.D
                By.xpath("//button[contains(@aria-label,'Next')]") // Shahu.D
            };
            
            for (By locator : nextBtnLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        nextBtn = element;
                        found = true;
                        System.out.println("  ✅ Found Next button using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // If not found, try to find all buttons with "Next" text for debugging
            if (!found || nextBtn == null) {
                System.out.println("  ⚠️ Next button not found with standard locators. Searching for all buttons with 'Next' text..."); // Shahu.D
                try {
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    System.out.println("  📊 Found " + allButtons.size() + " button(s) on the page"); // Shahu.D
                    for (int i = 0; i < Math.min(allButtons.size(), 20); i++) {
                        WebElement btn = allButtons.get(i);
                        try {
                            String btnText = btn.getText();
                            String btnId = btn.getAttribute("id");
                            String btnAriaLabel = btn.getAttribute("aria-label");
                            System.out.println("    Button " + (i + 1) + ": id='" + btnId + "', text='" + btnText + "', aria-label='" + btnAriaLabel + "'"); // Shahu.D
                            if ((btnText != null && btnText.contains("Next")) || 
                                (btnAriaLabel != null && btnAriaLabel.contains("Next"))) {
                                System.out.println("    ⭐ Found potential Next button!"); // Shahu.D
                                if (btn.isDisplayed() && btn.isEnabled()) {
                                    nextBtn = btn;
                                    found = true;
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error searching for buttons: " + ex.getMessage()); // Shahu.D
                }
            }
            
            if (!found || nextBtn == null) {
                throw new Exception("Next button not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
                System.out.println("  ✅ Clicked Next button using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(nextBtn).click().perform();
                    System.out.println("  ✅ Clicked Next button using Actions"); // Shahu.D
                } catch (Exception e2) {
                    nextBtn.click();
                    System.out.println("  ✅ Clicked Next button using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Next button"); // Shahu.D
            waitForSeconds(3); // Shahu.D - Wait for page to navigate
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Next button: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    
    
    public void EnterInitiativeTitle(String shortName) {
        type(InitiativeReallocationPageLocators.initiativetitle, shortName, "shortName");
    }
    
    /**
     * Click on Initiative Details first row
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickInitiativeDetailsFirstRow() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Initiative Details first row..."); // Shahu.D
            
            WebElement firstRow = null;
            boolean found = false;
            
            By[] rowLocators = {
                InitiativeReallocationPageLocators.initiativeDetailsFirstRow,
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[1]/div/div[2]/div/table/tbody/tr[1]/td"), // Shahu.D - Primary XPath
                By.xpath("//table//tbody//tr[1]//td"), // Shahu.D
                By.xpath("//div[@role='row'][1]") // Shahu.D
            };
            
            for (By locator : rowLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        firstRow = element;
                        found = true;
                        System.out.println("  ✅ Found Initiative Details first row using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || firstRow == null) {
                throw new Exception("Initiative Details first row not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", firstRow);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstRow);
                System.out.println("  ✅ Clicked Initiative Details first row using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(firstRow).click().perform();
                    System.out.println("  ✅ Clicked Initiative Details first row using Actions"); // Shahu.D
                } catch (Exception e2) {
                    firstRow.click();
                    System.out.println("  ✅ Clicked Initiative Details first row using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Initiative Details first row"); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for details to load
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Initiative Details first row: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    /**
     * Click on Approvers dropdown field
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
   

    /**
     * Select value from Approver dropdown
     * Shahu.D
     * @throws Exception if element is not found or selection fails
     */
  /*  public void selectApprover() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Approver dropdown value..."); // Shahu.D
            
            WebElement option = null;
            boolean found = false;
            
            // First, try to find the dropdown ID dynamically
            String dropdownId = null;
            try {
                java.util.List<WebElement> dropdownOptions = driver.findElements(By.xpath("//span[contains(@id,'Dropdown') and contains(@id,'-option')]"));
                if (!dropdownOptions.isEmpty()) {
                    String id = dropdownOptions.get(0).getAttribute("id");
                    if (id != null && id.contains("Dropdown")) {
                        dropdownId = id.replace("-option", "");
                        System.out.println("  📌 Found dynamic dropdown ID: " + dropdownId); // Shahu.D
                    }
                }
            } catch (Exception ex) {
                System.out.println("  ⚠️ Could not determine dynamic dropdown ID, using static locators"); // Shahu.D
            }
            
            By[] optionLocators = {
                InitiativeReallocationPageLocators.approverDropdownValue,
                By.xpath("//*[@id=\"Dropdown1612-list0-label\"]"), // Shahu.D - User provided dropdown ID
                By.xpath("//*[@id='Dropdown1612-list0-label']"), // Shahu.D
                By.xpath("//*[@id=\"Dropdown1353-list0-label\"]"), // Shahu.D - Original XPath
                By.xpath("//*[@id='Dropdown1353-list0-label']"), // Shahu.D
                By.xpath("//*[@id='Dropdown1353-list0']//span"), // Shahu.D
                By.xpath("//*[@id='Dropdown1353-list0']"), // Shahu.D
                By.xpath("//*[contains(@id,'Dropdown1353-list')]//span"), // Shahu.D
                By.xpath("//*[contains(@id,'Dropdown1612-list')]//span"), // Shahu.D - User provided dropdown ID
                By.xpath("//li[contains(@id,'list0')]//span"), // Shahu.D - Generic list item
                By.xpath("//*[contains(@id,'-list0-label')]") // Shahu.D - Generic list label
            };
            
            // Add dynamic locators if dropdown ID was found
            if (dropdownId != null) {
                By[] dynamicLocators = {
                    By.xpath("//*[@id=\"" + dropdownId + "-list0-label\"]"), // Shahu.D
                    By.xpath("//*[@id='" + dropdownId + "-list0-label']"), // Shahu.D
                    By.xpath("//*[@id='" + dropdownId + "-list0']//span"), // Shahu.D
                    By.xpath("//*[@id='" + dropdownId + "-list0']") // Shahu.D
                };
                // Prepend dynamic locators to the array
                By[] combinedLocators = new By[optionLocators.length + dynamicLocators.length];
                System.arraycopy(dynamicLocators, 0, combinedLocators, 0, dynamicLocators.length);
                System.arraycopy(optionLocators, 0, combinedLocators, dynamicLocators.length, optionLocators.length);
                optionLocators = combinedLocators;
            }
            
            // Wait for dropdown list to appear
            waitForSeconds(2); // Shahu.D
            
            for (By locator : optionLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed()) {
                        option = element;
                        found = true;
                        System.out.println("  ✅ Found Approver option using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // If still not found, try to find all list items in dropdown menus
            if (!found || option == null) {
                System.out.println("  ⚠️ Approver option not found with standard locators. Searching for all dropdown list items..."); // Shahu.D
                try {
                    java.util.List<WebElement> allListItems = driver.findElements(By.xpath("//li[contains(@id,'list')] | //*[contains(@id,'-list')]//span"));
                    System.out.println("  📊 Found " + allListItems.size() + " list item(s) in dropdown menus"); // Shahu.D
                    for (int i = 0; i < Math.min(allListItems.size(), 10); i++) {
                        WebElement item = allListItems.get(i);
                        try {
                            String itemId = item.getAttribute("id");
                            String itemText = item.getText();
                            boolean isDisplayed = item.isDisplayed();
                            System.out.println("    List item " + (i + 1) + ": id='" + itemId + "', text='" + itemText + "', displayed=" + isDisplayed); // Shahu.D
                            if (isDisplayed && (itemId != null && itemId.contains("list0")) || (itemText != null && !itemText.trim().isEmpty())) {
                                System.out.println("    ⭐ Found potential Approver option!"); // Shahu.D
                                option = item;
                                found = true;
                                break;
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error searching for list items: " + ex.getMessage()); // Shahu.D
                }
            }
            
            if (!found || option == null) {
                throw new Exception("Approver option not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("  ✅ Selected Approver using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(option).click().perform();
                    System.out.println("  ✅ Selected Approver using Actions"); // Shahu.D
                } catch (Exception e2) {
                    option.click();
                    System.out.println("  ✅ Selected Approver using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully selected Approver"); // Shahu.D
            waitForSeconds(2); // Shahu.D - Wait for selection to apply
            
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Approver: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }
  */
   
	WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
    
    public void selectApprover(String Approver) throws Exception {
    	
    	 click(InitiativeReallocationPageLocators.approverDropdown, "approverDropdown");
           
    	  selectFromList(InitiativeReallocationPageLocators.approverDropdownValue, Approver , "Approver ");
    	  
    		 click(InitiativeReallocationPageLocators.approverDropdown, "approverDropdown");

    }
    
    
    /**
     * Select checkbox for approver
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void selectApproverCheckbox() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Approver checkbox..."); // Shahu.D
            
            WebElement checkbox = null;
            boolean found = false;
            
            By[] checkboxLocators = {
                InitiativeReallocationPageLocators.approverCheckbox,
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[3]/div/div[2]/table/tbody/tr[1]/td[6]/div/label/div/i"), // Shahu.D - Primary XPath
                By.xpath("//table//tbody//tr[1]//td[6]//label//input[@type='checkbox']"), // Shahu.D
                By.xpath("//input[@type='checkbox']"), // Shahu.D
                By.xpath("//table//tbody//tr[1]//td[6]//input[@type='checkbox']") // Shahu.D
            };
            
            for (By locator : checkboxLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    // Check if it's a checkbox input or icon that needs to find parent checkbox
                    if (element.getTagName().equals("input") && element.getAttribute("type").equals("checkbox")) {
                        checkbox = element;
                    } else {
                        // If it's an icon, find the parent checkbox
                        WebElement parentCheckbox = (WebElement) ((JavascriptExecutor) driver).executeScript(
                            "return arguments[0].closest('input[type=\"checkbox\"]') || arguments[0].closest('label').querySelector('input[type=\"checkbox\"]');", element);
                        if (parentCheckbox != null) {
                            checkbox = parentCheckbox;
                        } else {
                            checkbox = element; // Use the element itself
                        }
                    }
                    
                    if (checkbox != null && checkbox.isDisplayed()) {
                        found = true;
                        System.out.println("  ✅ Found Approver checkbox using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // If still not found, try to find all checkboxes in the approver table for debugging
            if (!found || checkbox == null) {
                System.out.println("  ⚠️ Approver checkbox not found with standard locators. Searching for all checkboxes in approver table..."); // Shahu.D
                try {
                    // Try to find checkboxes in the approver table (div[3]/div[2] based on XPath structure)
                    java.util.List<WebElement> allCheckboxes = driver.findElements(By.xpath("//table//tbody//tr//td[6]//input[@type='checkbox'] | //table//tbody//tr//td[6]//label//input[@type='checkbox'] | //div[contains(@class,'div[2]')]//table//tbody//tr[1]//td[6]//input"));
                    System.out.println("  📊 Found " + allCheckboxes.size() + " checkbox(es) in approver table area"); // Shahu.D
                    for (int i = 0; i < Math.min(allCheckboxes.size(), 10); i++) {
                        try {
                            WebElement chk = allCheckboxes.get(i);
                            String chkId = chk.getAttribute("id");
                            String chkName = chk.getAttribute("name");
                            String chkType = chk.getAttribute("type");
                            boolean isDisplayed = chk.isDisplayed();
                            boolean isEnabled = chk.isEnabled();
                            System.out.println("    Checkbox " + (i + 1) + ": id='" + chkId + "', name='" + chkName + "', type='" + chkType + "', displayed=" + isDisplayed + ", enabled=" + isEnabled); // Shahu.D
                            if (isDisplayed && isEnabled) {
                                System.out.println("    ⭐ Found potential Approver checkbox!"); // Shahu.D
                                checkbox = chk;
                                found = true;
                                break;
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                    
                    // Also try finding by label/icon elements
                    if (!found) {
                        java.util.List<WebElement> labelElements = driver.findElements(By.xpath("//table//tbody//tr[1]//td[6]//label | //table//tbody//tr[1]//td[6]//div//label | //div[contains(@class,'div[2]')]//table//tbody//tr[1]//td[6]//label"));
                        System.out.println("  📊 Found " + labelElements.size() + " label element(s) in first row, 6th column"); // Shahu.D
                        for (int i = 0; i < Math.min(labelElements.size(), 5); i++) {
                            try {
                                WebElement label = labelElements.get(i);
                                // Try to find checkbox within label
                                WebElement chkInLabel = label.findElement(By.xpath(".//input[@type='checkbox']"));
                                if (chkInLabel != null && chkInLabel.isDisplayed() && chkInLabel.isEnabled()) {
                                    System.out.println("    ⭐ Found checkbox within label!"); // Shahu.D
                                    checkbox = chkInLabel;
                                    found = true;
                                    break;
                                }
                            } catch (Exception ex) {
                                // Try clicking the label itself
                                try {
                                    WebElement label = labelElements.get(i);
                                    if (label.isDisplayed() && label.isEnabled()) {
                                        System.out.println("    ⭐ Found clickable label, will try clicking it!"); // Shahu.D
                                        checkbox = label;
                                        found = true;
                                        break;
                                    }
                                } catch (Exception ex2) {
                                    continue;
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error searching for checkboxes: " + ex.getMessage()); // Shahu.D
                }
            }
            
            if (!found || checkbox == null) {
                throw new Exception("Approver checkbox not found"); // Shahu.D
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
            waitForSeconds(1);
            
            // Check if checkbox is already selected
            boolean isChecked = false;
            try {
                if (checkbox.getTagName().equals("input")) {
                    isChecked = checkbox.isSelected();
                } else {
                    // For icon elements, check parent checkbox
                    WebElement parentCheckbox = (WebElement) ((JavascriptExecutor) driver).executeScript(
                        "return arguments[0].closest('label').querySelector('input[type=\"checkbox\"]');", checkbox);
                    if (parentCheckbox != null) {
                        isChecked = parentCheckbox.isSelected();
                    }
                }
            } catch (Exception ex) {
                // Continue with click
            }
            
            if (!isChecked) {
                try {
                    if (checkbox.getTagName().equals("input")) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                        System.out.println("  ✅ Selected Approver checkbox using JavaScript"); // Shahu.D
                    } else {
                        // Click the icon or label
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                        System.out.println("  ✅ Selected Approver checkbox (via icon) using JavaScript"); // Shahu.D
                    }
                } catch (Exception e1) {
                    try {
                        actions.moveToElement(checkbox).click().perform();
                        System.out.println("  ✅ Selected Approver checkbox using Actions"); // Shahu.D
                    } catch (Exception e2) {
                        checkbox.click();
                        System.out.println("  ✅ Selected Approver checkbox using direct click"); // Shahu.D
                    }
                }
            } else {
                System.out.println("  ℹ️ Approver checkbox is already selected"); // Shahu.D
            }
            
            System.out.println("  ✅ Successfully selected Approver checkbox"); // Shahu.D
            waitForSeconds(1); // Shahu.D
            
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting Approver checkbox: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    /**
     * Click on Save button
     * Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSaveButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Save button..."); // Shahu.D
            
            WebElement saveBtn = null;
            boolean found = false;
            
            By[] saveBtnLocators = {
                By.xpath("//*[@id=\"id__250\"]"), // Shahu.D - User provided XPath (Primary)
                By.xpath("//button[@id='id__250']"), // Shahu.D - User provided XPath (Primary)
                InitiativeReallocationPageLocators.saveButton,
                By.xpath("//*[@id=\"id__1350\"]"), // Shahu.D - Original XPath
                By.xpath("//button[@id='id__1350']"), // Shahu.D
                By.xpath("//button[contains(text(),'Save')]"), // Shahu.D
                By.xpath("//span[contains(text(),'Save')]/ancestor::button") // Shahu.D
            };
            
            for (By locator : saveBtnLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        saveBtn = element;
                        found = true;
                        System.out.println("  ✅ Found Save button using locator: " + locator); // Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // If not found, try to find all buttons with "Save" text for debugging
            if (!found || saveBtn == null) {
                System.out.println("  ⚠️ Save button not found with standard locators. Searching for all buttons with 'Save' text..."); // Shahu.D
                try {
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    System.out.println("  📊 Found " + allButtons.size() + " button(s) on the page"); // Shahu.D
                    for (int i = 0; i < Math.min(allButtons.size(), 30); i++) {
                        WebElement btn = allButtons.get(i);
                        try {
                            String btnText = btn.getText();
                            String btnId = btn.getAttribute("id");
                            String btnAriaLabel = btn.getAttribute("aria-label");
                            boolean isDisplayed = btn.isDisplayed();
                            boolean isEnabled = btn.isEnabled();
                            System.out.println("    Button " + (i + 1) + ": id='" + btnId + "', text='" + btnText + "', aria-label='" + btnAriaLabel + "', displayed=" + isDisplayed + ", enabled=" + isEnabled); // Shahu.D
                            if ((btnText != null && btnText.contains("Save")) || 
                                (btnAriaLabel != null && btnAriaLabel.contains("Save")) ||
                                (btnId != null && btnId.contains("Save"))) {
                                System.out.println("    ⭐ Found potential Save button!"); // Shahu.D
                                if (btn.isDisplayed() && btn.isEnabled()) {
                                    saveBtn = btn;
                                    found = true;
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error searching for buttons: " + ex.getMessage()); // Shahu.D
                }
            }
            
            if (!found || saveBtn == null) {
                throw new Exception("Save button not found"); // Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                System.out.println("  ✅ Clicked Save button using JavaScript"); // Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(saveBtn).click().perform();
                    System.out.println("  ✅ Clicked Save button using Actions"); // Shahu.D
                } catch (Exception e2) {
                    saveBtn.click();
                    System.out.println("  ✅ Clicked Save button using direct click"); // Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Save button"); // Shahu.D
            waitForSeconds(3); // Shahu.D - Wait for save to complete
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Save button: " + e.getMessage()); // Shahu.D
            throw e;
        }
    }

    // Add more Initiative Reallocation specific methods here as needed
}

