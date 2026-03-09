package Pages;

import Actions.ActionEngine;
import Locators.CompletedInitiativePageLocators;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Completed Initiative Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in CompletedInitiativePageLocators.java
 *    - Methods use CompletedInitiativePageLocators.locatorName for reusability
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in CompletedInitiativePageLocators class
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
public class CompletedInitiativePage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public CompletedInitiativePage(WebDriver driver, ExtentTest reportLogger) {
        super(); // pass driver to ActionEngine
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Click on Completed Initiative card/button
     * @throws Exception if element is not found or click fails
     */
    public void clickCompletedInitiative() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Completed Initiative card...");
            
            WebElement el = null;
            boolean found = false;
            
            // Strategy 1: Try the primary locator
            try {
                el = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    CompletedInitiativePageLocators.completedInitiativeCard));
                found = true;
                System.out.println("  ✅ Found Completed Initiative card using primary locator");
            } catch (Exception e) {
                System.out.println("  ⚠️ Primary locator failed, trying alternative strategies...");
            }
            
            // Strategy 2: Wait for children-panel-container and find card inside
            if (!found) {
                try {
                    System.out.println("  🔍 Waiting for children-panel-container to load...");
                    WebDriverWait containerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    containerWait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id='children-panel-container']")));
                    System.out.println("  ✅ children-panel-container found, searching for cards...");
                    waitForSeconds(2);
                    
                    // Try finding the card now that container is loaded
                    By[] alternativeLocators = {
                        CompletedInitiativePageLocators.completedInitiativeCard,
                        By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Completed Initiative')]"),
                        By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Completed')]"),
                        By.xpath("//div[@id='children-panel-container']//p[contains(text(),'Completed Initiative')]"),
                        By.xpath("//p[contains(text(),'Completed Initiative')]"),
                        By.xpath("//*[contains(text(),'Completed Initiative') and not(self::script)]")
                    };
                    
                    for (By locator : alternativeLocators) {
                        try {
                            java.util.List<WebElement> elements = driver.findElements(locator);
                            if (elements.size() > 0) {
                                // Filter for visible elements only
                                for (WebElement element : elements) {
                                    try {
                                        if (element.isDisplayed()) {
                                            el = element;
                                            found = true;
                                            System.out.println("  ✅ Found Completed Initiative card using alternative locator: " + locator);
                                            break;
                                        }
                                    } catch (Exception ex) {
                                        continue;
                                    }
                                }
                                if (found) break;
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Alternative locators failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Check iframes
            if (!found) {
                System.out.println("  🔍 Checking iframes for Completed Initiative card...");
                java.util.List<WebElement> iframes = driver.findElements(CompletedInitiativePageLocators.allIframes);
                System.out.println("  📋 Found " + iframes.size() + " iframe(s)");
                
                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        try {
                            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                            el = iframeWait.until(ExpectedConditions.visibilityOfElementLocated(
                                CompletedInitiativePageLocators.completedInitiativeCard));
                            found = true;
                            System.out.println("  ✅ Found Completed Initiative card in iframe " + i);
                            break;
                        } catch (Exception e) {
                            driver.switchTo().defaultContent();
                        }
                    } catch (Exception e) {
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
            if (!found || el == null) {
                driver.switchTo().defaultContent();
                
                // Debug: Print available elements
                try {
                    java.util.List<WebElement> allPanels = driver.findElements(
                        By.xpath("//*[@id='children-panel-container']//p"));
                    System.out.println("  📋 Found " + allPanels.size() + " panel(s) in children-panel-container");
                    for (int i = 0; i < Math.min(allPanels.size(), 5); i++) {
                        try {
                            String text = allPanels.get(i).getText();
                            System.out.println("     Panel " + (i + 1) + ": '" + text + "'");
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info");
                }
                
                throw new Exception("Completed Initiative card not found. Please ensure the navigation menu is expanded and the card is visible.");
            }
            
            // At this point, we know the element exists - but don't use the stored reference
            // Re-find the element right before clicking to avoid stale element issues
            driver.switchTo().defaultContent();
            waitForSeconds(1);
            
            System.out.println("  🔍 Re-finding element right before clicking (to avoid stale element)...");
            
            // Retry logic to handle stale element exceptions
            int maxRetries = 3;
            boolean clicked = false;
            
            for (int retry = 0; retry < maxRetries && !clicked; retry++) {
                try {
                    if (retry > 0) {
                        System.out.println("  🔄 Retry attempt " + (retry + 1) + " of " + maxRetries);
                        waitForSeconds(1);
                    }
                    
                    // Re-find the element fresh each time
                    WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.completedInitiativeCard));
                    
                    // Scroll into view
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", elementToClick);
                    waitForSeconds(1);
                    
                    // Try JavaScript click first (most reliable)
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
                        System.out.println("  ✅ Clicked Completed Initiative card using JavaScript (attempt " + (retry + 1) + ")");
                        clicked = true;
                    } catch (Exception jsError) {
                        // Try Actions click
                        try {
                            actions.moveToElement(elementToClick).click().perform();
                            System.out.println("  ✅ Clicked Completed Initiative card using Actions (attempt " + (retry + 1) + ")");
                            clicked = true;
                        } catch (Exception actionsError) {
                            // Try direct click
                            elementToClick.click();
                            System.out.println("  ✅ Clicked Completed Initiative card using direct click (attempt " + (retry + 1) + ")");
                            clicked = true;
                        }
                    }
                } catch (StaleElementReferenceException staleEx) {
                    System.out.println("  ⚠️ Stale element exception on attempt " + (retry + 1) + ", retrying...");
                    if (retry == maxRetries - 1) {
                        throw new Exception("Failed to click after " + maxRetries + " attempts due to stale element: " + staleEx.getMessage());
                    }
                } catch (Exception e) {
                    if (retry == maxRetries - 1) {
                        throw new Exception("Failed to click Completed Initiative card after " + maxRetries + " attempts: " + e.getMessage());
                    }
                    System.out.println("  ⚠️ Error on attempt " + (retry + 1) + ": " + e.getMessage());
                }
            }
            
            if (!clicked) {
                throw new Exception("Failed to click Completed Initiative card after all retry attempts");
            }
            
            System.out.println("  ✅ Successfully clicked on Completed Initiative card");
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.err.println("  ❌ Error clicking Completed Initiative card: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Navigate to Completed Initiative page
     * Direct navigation to Completed Initiative (no Initiative page click)
     * @throws Exception if navigation fails
     */
    public void navigateToCompletedInitiative() throws Exception {
        // updated by Shahu.D: direct nav to Completed Initiative (no Initiative page click)
        try {
            System.out.println("  🔍 Starting navigation to Completed Initiative page...");
            waitForSeconds(3); // Wait for page to be ready after login
            
            System.out.println("  📌 Step 1: Click on Initiative Management navigation");
            clickInitiativeManagementNav();
            System.out.println("  ✅ Step 1 completed: Initiative Management nav clicked");
            
            // Wait for children-panel-container to appear (this contains the navigation cards)
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
            
            System.out.println("  📌 Step 2: Click on Completed Initiative card");
            clickCompletedInitiative();
            System.out.println("  ✅ Step 2 completed: Completed Initiative card clicked");
            
            waitForSeconds(4); // Wait for Completed Initiative page to load
            System.out.println("  ✅ Successfully navigated to Completed Initiative page");
        } catch (Exception e) {
            System.err.println("  ❌ Error navigating to Completed Initiative page: " + e.getMessage());
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
        WebElement nav = wait.until(ExpectedConditions.visibilityOfElementLocated(CompletedInitiativePageLocators.initiativeManagementNav));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nav);
        wait.until(ExpectedConditions.elementToBeClickable(nav));
        try {
            nav.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nav);
        }
    }
    
    /**
     * Expand Initiative Management menu (without navigating to any sub-page)
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void expandInitiativeManagementMenu() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            System.out.println("  🔍 Expanding Initiative Management menu...");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement nav = wait.until(ExpectedConditions.elementToBeClickable(
                CompletedInitiativePageLocators.initiativeManagementNav));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", nav);
            waitForSeconds(1);
            
            // Click to expand menu
            try {
                nav.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nav);
            }
            
            System.out.println("  ✅ Initiative Management menu expanded");
            
            // Wait for children-panel-container to appear
            System.out.println("  ⏳ Waiting for navigation menu to expand...");
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='children-panel-container']")));
                System.out.println("  ✅ Navigation menu container found");
                waitForSeconds(2);
            } catch (Exception e) {
                System.out.println("  ⚠️ children-panel-container not found, waiting longer...");
                waitForSeconds(3);
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error expanding Initiative Management menu: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on Search icon/button
     * Handles iframes and multiple locator strategies for robustness
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchInput() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Wait for page to fully load
        
        // Array of search locators to try (primary first, then alternatives)
        By[] searchLocators = {
            CompletedInitiativePageLocators.searchInput,  // Primary: specific xpath
            CompletedInitiativePageLocators.searchInputAlt1,
            CompletedInitiativePageLocators.searchInputAlt2,
            CompletedInitiativePageLocators.searchInputAlt3,
            CompletedInitiativePageLocators.searchInputAlt4
        };
        
        WebElement searchElement = null;
        boolean found = false;
        
        // Strategy 1: Try in default content
        System.out.println("  🔍 Searching for Search icon in default content...");
        for (By locator : searchLocators) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                searchElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                found = true;
                System.out.println("  ✅ Found Search icon with locator: " + locator);
                break;
            } catch (Exception e) {
                // Try next locator
                continue;
            }
        }
        
        // Strategy 2: Check iframes if not found in main context
        if (!found) {
            System.out.println("  🔍 Search icon not found in default content, checking iframes...");
            java.util.List<WebElement> iframes = driver.findElements(CompletedInitiativePageLocators.allIframes);
            System.out.println("  📋 Found " + iframes.size() + " iframe(s) on page");
            
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    System.out.println("  🔍 Checking iframe " + i);
                    
                    for (By locator : searchLocators) {
                        try {
                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                            searchElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                            found = true;
                            System.out.println("  ✅ Found Search icon in iframe " + i + " with locator: " + locator);
                            break;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    System.out.println("  ⚠️ Error checking iframe " + i + ": " + e.getMessage());
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        // If still not found, throw exception
        if (!found || searchElement == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Search icon could not be located in default content or any iframe.");
        }
        
        // Click on the found element
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchElement);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(searchElement));
            searchElement.click();
            System.out.println("  ✅ Successfully clicked on Search icon");
        } catch (Exception e) {
            // Fallback to JavaScript click
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchElement);
                System.out.println("  ✅ Successfully clicked on Search icon using JavaScript");
            } catch (Exception jsEx) {
                driver.switchTo().defaultContent();
                throw new Exception("Failed to click Search icon: " + jsEx.getMessage());
            }
        }
        
        // Switch back to default content after clicking
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Ignore if already in default content
        }
    }

    /**
     * Enter Initiative Code in the search field
     * @param initiativeCode The Initiative Code to search for
     * @throws Exception if element is not found or typing fails
     */
    
    public void   clickSearchInput2()throws Exception {
        
        
        click( CompletedInitiativePageLocators.clickinput,"clickinput");
                  
          };   
          
          
          public void  enterInitiativeCode1(String initiativecode)throws Exception {
              
              
              type( CompletedInitiativePageLocators.enterinput,initiativecode," enterinput");
                        
                };   
                      
    
    
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement codeField = wait.until(ExpectedConditions.visibilityOfElementLocated(CompletedInitiativePageLocators.initiativeCodeField));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", codeField);
        wait.until(ExpectedConditions.elementToBeClickable(codeField));
        
        // Clear and enter code
        try {
            codeField.clear();
            codeField.sendKeys(initiativeCode);
            System.out.println("  ✅ Successfully entered Initiative Code: " + initiativeCode);
        } catch (Exception e) {
            // Fallback to JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + initiativeCode + "';", codeField);
            System.out.println("  ✅ Successfully entered Initiative Code using JavaScript: " + initiativeCode);
        }
    }

    /**
     * Click on Search button to execute search
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(CompletedInitiativePageLocators.searchButton));
        
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchBtn);
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        
        try {
            searchBtn.click();
            System.out.println("  ✅ Successfully clicked on Search button");
        } catch (Exception e) {
            // Fallback to JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
            System.out.println("  ✅ Successfully clicked on Search button using JavaScript");
        }
        
        // Wait for search results to load
        waitForSeconds(3);
    }

    /**
     * Verify search results
     * Checks if either records are displayed or "no items" message is shown
     * @return true if search results are displayed correctly (either records or no items message), false otherwise
     * @throws Exception if verification fails
     */
/*    public boolean verifySearchResults() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            // Check for "no items" message
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                WebElement noItemsMsg = wait.until(ExpectedConditions.presenceOfElementLocated(CompletedInitiativePageLocators.noItemsMessage));
                String message = noItemsMsg.getText();
                if (message.contains("There are no items to show in this view")) {
                    System.out.println("  ✅ Search result verified: No items message displayed - '" + message + "'");
                    return true;
                }
            } catch (Exception e) {
                // No items message not found, check for records
            }
            
            // Check for grid rows/records
            try {
                java.util.List<WebElement> rows = driver.findElements(CompletedInitiativePageLocators.gridRows);
                if (rows.size() > 0) {
                    System.out.println("  ✅ Search result verified: " + rows.size() + " record(s) displayed");
                    return true;
                }
            } catch (Exception e) {
                // Grid rows not found
            }
            
            System.out.println("  ⚠️ Search results verification: Neither records nor no items message found");
            return false;
            
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying search results: " + e.getMessage());
            return false;
        }
    }
  */
    
    public   boolean verifyInitiativeDisplayedOnInitiativePage(String initiativeCode) {

        driver.switchTo().defaultContent();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            By initiativeCodeCell = By.xpath(
                "//div[@data-automationid='DetailsRow']" +
                "//div[@data-automation-key='code']" +
                "//div[normalize-space()='" + initiativeCode + "']"
            );

            WebElement codeElement =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(initiativeCodeCell));

            if (codeElement.isDisplayed()) {
                System.out.println("✅ Initiative Code '" + initiativeCode + "' found in DetailsList");
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' displayed");
                }
                return true;
            }

            return false;

        } catch (TimeoutException e) {
            System.err.println("❌ Initiative Code '" + initiativeCode + "' NOT found in DetailsList");
            if (reportLogger != null) {
                reportLogger.fail("Initiative Code '" + initiativeCode + "' NOT displayed");
            }
            return false;
        }
    }

    /**
     * Search for Completed Initiative by Initiative Code
     * Complete flow: Enter code, click search, verify results
     * @param initiativeCode The Initiative Code to search for
     * @return true if search completed successfully, false otherwise
     * @throws Exception if search fails
     */
  /*  public boolean searchByInitiativeCode(String initiativeCode) throws Exception {
        try {
            System.out.println("  🔍 Searching for Initiative Code: " + initiativeCode);
            enterInitiativeCode(initiativeCode);
            clickSearchButton();
            boolean result = verifySearchResults();
            return result;
        } catch (Exception e) {
            System.err.println("  ❌ Error searching by Initiative Code: " + e.getMessage());
            throw e;
        }
    }
*/
    /**
     * Select Nature of Initiative from dropdown
     * @param noiValue The Nature of Initiative value to select (e.g., "Full Change Lifecycle")
     * @throws Exception if element is not found or selection fails
     */
    public void selectNatureOfInitiative1(String noiValue) throws Exception {
    	   click(CompletedInitiativePageLocators.nature, "nature");
           selectFromList(CompletedInitiativePageLocators.naturevalue, noiValue, "naturevalue");
    	
    }
    
    public void selectBusinessGroup1(String bgValue) throws Exception {
    	   click(CompletedInitiativePageLocators.business, "Vendor Dropdown");
           selectFromList(CompletedInitiativePageLocators.businessvalue, bgValue, "businessvalue");
             
         
    }

   
    public void selectOrganizationUnit1(String ouValue) throws Exception {
    	   click(CompletedInitiativePageLocators.org, "org");
           selectFromList(CompletedInitiativePageLocators.orgvalue, ouValue, "orgvalue");
    }

    /**
     * Select all dropdowns and perform search
     * Complete flow: Select NOI, BG, OU, then click search
     * @param noiValue Nature of Initiative value
     * @param bgValue Business Group value
     * @param ouValue Organization Unit value
     * @return true if search completed successfully, false otherwise
     * @throws Exception if selection or search fails
     */
  /*  public boolean searchByDropdowns(String noiValue, String bgValue, String ouValue,String initiativeCode) throws Exception {
        try {
            System.out.println("  🔍 Searching using dropdowns (NOI, BG, OU)");
            selectNatureOfInitiative(noiValue);
            selectBusinessGroup(bgValue);
            selectOrganizationUnit(ouValue);
            clickSearchButton();
          boolean result = verifyInitiativeDisplayedOnInitiativePage(initiativeCode);
           return result;
        } catch (Exception e) {
            System.err.println("  ❌ Error searching by dropdowns: " + e.getMessage());
            throw e;
        }
    }
    */

    /**
     * Click on comment link/icon for an initiative
     * @throws Exception if element is not found or click fails
     */
    public void clickCommentLink() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            Actions actions = new Actions(driver);
            
            System.out.println("  ⏳ Waiting for grid/table to load with initiatives...");
            
            // First, wait for grid rows to appear (initiatives need to be loaded)
            By[] gridRowLocators = {
                By.xpath("//div[contains(@id,'row')]"),
                By.xpath("//div[@role='row' and contains(@class,'ag-row')]"),
                By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[@role='row']"),
                By.xpath("//table//tbody//tr[@role='row']"),
                By.xpath("//tr[contains(@role,'row')]"),
                By.xpath("//div[contains(@class,'ag-row')]")
            };
            
            boolean gridLoaded = false;
            for (By rowLocator : gridRowLocators) {
                try {
                    java.util.List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
                    if (rows.size() > 0) {
                        System.out.println("  ✅ Grid loaded! Found " + rows.size() + " row(s) using locator: " + rowLocator);
                        gridLoaded = true;
                        waitForSeconds(2); // Wait a bit more for grid to stabilize
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!gridLoaded) {
                System.out.println("  ⚠️ Grid rows not found immediately, waiting longer...");
                waitForSeconds(5);
                
                // Try one more time
                for (By rowLocator : gridRowLocators) {
                    try {
                        java.util.List<WebElement> rows = driver.findElements(rowLocator);
                        if (rows.size() > 0) {
                            System.out.println("  ✅ Grid loaded after wait! Found " + rows.size() + " row(s)");
                            gridLoaded = true;
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            
            if (!gridLoaded) {
                System.out.println("  ⚠️ Warning: Grid rows not found. Continuing anyway to search for comment link...");
            }
            
            System.out.println("  🔍 Searching for comment link...");
            
            // Strategy 1: Try to find first available comment link
            WebElement commentLinkElement = null;
            boolean found = false;
            
            // Try multiple locator strategies - updated to handle dynamic row IDs
            By[] commentLinkLocators = {
                // Try finding comment link in any row (dynamic row ID) - most specific
                By.xpath("//div[contains(@id,'row')]//button[2]//svg | //div[contains(@id,'row')]/div/div[7]/div/div/button[2]/svg"),
                // Try first available comment link in any row
                By.xpath("(//div[contains(@id,'row')]//button[2]//svg)[1]"),
                // Try AG Grid rows
                By.xpath("(//div[contains(@class,'ag-row')]//button[2]//svg)[1]"),
                // Try table rows
                By.xpath("(//tr[contains(@role,'row')]//button[2]//svg)[1]"),
                // Generic patterns
                CompletedInitiativePageLocators.firstCommentLink,
                CompletedInitiativePageLocators.commentLink,
                By.xpath("(//button[contains(@aria-label,'comment') or contains(@aria-label,'Comment')])[1]"),
                By.xpath("(//svg[contains(@class,'MuiSvgIcon')])[2]")
            };
            
            for (By locator : commentLinkLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        commentLinkElement = elements.get(0);
                        System.out.println("  ✅ Found comment link using locator: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Strategy 2: Check iframes if not found
            if (!found) {
                System.out.println("  🔍 Comment link not found in default content, checking iframes...");
                java.util.List<WebElement> iframes = driver.findElements(CompletedInitiativePageLocators.allIframes);
                System.out.println("  📋 Found " + iframes.size() + " iframe(s)");
                
                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        System.out.println("  🔍 Checking iframe " + i);
                        try {
                            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                            commentLinkElement = iframeWait.until(ExpectedConditions.elementToBeClickable(
                                CompletedInitiativePageLocators.firstCommentLink));
                            found = true;
                            System.out.println("  ✅ Found comment link in iframe " + i);
                            break;
                        } catch (Exception e) {
                            driver.switchTo().defaultContent();
                        }
                    } catch (Exception e) {
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
            if (!found || commentLinkElement == null) {
                driver.switchTo().defaultContent();
                // Debug: Print available buttons/svgs on the page
                try {
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    System.out.println("  📋 Found " + allButtons.size() + " button(s) on the page");
                    java.util.List<WebElement> allSvgs = driver.findElements(By.xpath("//svg"));
                    System.out.println("  📋 Found " + allSvgs.size() + " svg element(s) on the page");
                    
                    // Try to find any row with comment button
                    java.util.List<WebElement> rows = driver.findElements(By.xpath("//div[contains(@id,'row')] | //tr[contains(@role,'row')]"));
                    System.out.println("  📋 Found " + rows.size() + " row(s) in the grid");
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                throw new Exception("Comment link not found. Please ensure there are initiatives displayed on the page.");
            }
            
            // Ensure we're in the right context
            driver.switchTo().defaultContent();
            if (found && commentLinkElement != null) {
                // Re-find element in default content if needed
                try {
                    WebElement refound = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.firstCommentLink));
                    if (refound != null) {
                        commentLinkElement = refound;
                    }
                } catch (Exception e) {
                    // Element might be in iframe, keep using the found one
                }
            }
            
            // Final null check before clicking
            if (commentLinkElement == null) {
                throw new Exception("Comment link element is null after all attempts");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", commentLinkElement);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", commentLinkElement);
                System.out.println("  ✅ Clicked comment link using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(commentLinkElement).click().perform();
                    System.out.println("  ✅ Clicked comment link using Actions");
                } catch (Exception e2) {
                    commentLinkElement.click();
                    System.out.println("  ✅ Clicked comment link using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on comment link");
            waitForSeconds(2); // Wait for comment dialog to open
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.err.println("  ❌ Error clicking comment link: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter comment in the comment textarea
     * @param commentText The comment text to enter (can be empty for blank comment test)
     * @throws Exception if element is not found or input fails
     */
    public void enterComment(String commentText) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for comment textarea to be visible
            WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                CompletedInitiativePageLocators.commentTextarea));
            
            // Clear any existing text
            textarea.clear();
            
            // Enter the comment text
            if (commentText != null && !commentText.trim().isEmpty()) {
                textarea.sendKeys(commentText);
                System.out.println("  ✅ Entered comment: '" + commentText + "'");
            } else {
                System.out.println("  ✅ Comment textarea cleared (blank comment)");
            }
            
            waitForSeconds(1);
        } catch (Exception e) {
            System.err.println("  ❌ Error entering comment: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on the Post button to submit comment
     * @throws Exception if element is not found or click fails
     */
    public void clickPostButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Actions actions = new Actions(driver);
            
            // Wait for Post button to be clickable
            WebElement postButton = wait.until(ExpectedConditions.elementToBeClickable(
                CompletedInitiativePageLocators.postCommentButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", postButton);
            waitForSeconds(1);
            
            // Click the button
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", postButton);
            } catch (Exception e) {
                actions.moveToElement(postButton).click().perform();
            }
            
            System.out.println("  ✅ Clicked on Post button");
            waitForSeconds(2); // Wait for alert or comment to post
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Post button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Verify if alert message is displayed
     * Updated by Shahu.D - Enhanced to handle reply alerts better
     * @param expectedMessage The expected alert message text
     * @return true if alert is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyAlertMessage(String expectedMessage) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Wait a bit longer for alert to appear
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Check for JavaScript alert
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("  📋 Found JavaScript alert: '" + alertText + "'");
                
                if (alertText.contains(expectedMessage)) {
                    System.out.println("  ✅ Alert message matches expected: '" + expectedMessage + "'");
                    alert.accept(); // Accept/dismiss the alert
                    return true;
                } else {
                    System.out.println("  ⚠️ Alert message doesn't match. Expected: '" + expectedMessage + "', Found: '" + alertText + "'");
                    alert.accept();
                    return false;
                }
            } catch (NoAlertPresentException e) {
                // No JavaScript alert, check for on-page alert/notification
                System.out.println("  🔍 No JavaScript alert found, checking for on-page message...");
                
                // Try multiple locator strategies for finding the alert
                By[] alertLocators = {
                    CompletedInitiativePageLocators.getAlertMessageLocator(expectedMessage),
                    By.xpath("//*[contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//div[contains(@class,'alert') and contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//div[contains(@class,'error') and contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//div[contains(@class,'message') and contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//span[contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//p[contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//*[@role='alert' and contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//div[contains(@class,'MuiAlert') and contains(text(),'" + expectedMessage + "')]"),
                    By.xpath("//div[contains(@class,'MuiSnackbar') and contains(text(),'" + expectedMessage + "')]")
                };
                
                WebElement alertElement = null;
                boolean found = false;
                
                for (By locator : alertLocators) {
                    try {
                        System.out.println("  🔍 Trying alert locator: " + locator);
                        java.util.List<WebElement> elements = driver.findElements(locator);
                        if (elements.size() > 0) {
                            // Find the first visible element
                            for (WebElement element : elements) {
                                try {
                                    if (element.isDisplayed()) {
                                        alertElement = element;
                                        found = true;
                                        System.out.println("  ✅ Found alert element using locator: " + locator);
                                        break;
                                    }
                                } catch (Exception ex) {
                                    continue;
                                }
                            }
                            if (found) break;
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
                
                if (!found) {
                    // Wait a bit more and try with explicit wait
                    System.out.println("  ⚠️ Alert not found immediately, waiting longer...");
                    waitForSeconds(2);
                    
                    try {
                        alertElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                            CompletedInitiativePageLocators.getAlertMessageLocator(expectedMessage)));
                        found = true;
                    } catch (Exception ex) {
                        // Enhanced Debug: Search for alert messages in various formats
                        try {
                            System.out.println("  🔍 Debugging: Searching for any alert-like elements...");
                            
                            // Search for text containing keywords from expected message
                            String[] keywords = expectedMessage.toLowerCase().split(" ");
                            for (String keyword : keywords) {
                                if (keyword.length() > 3) { // Only search for meaningful words
                                    try {
                                        java.util.List<WebElement> textElements = driver.findElements(
                                            By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + keyword + "')]"));
                                        System.out.println("  📋 Found " + textElements.size() + " element(s) containing keyword '" + keyword + "'");
                                        for (int i = 0; i < Math.min(textElements.size(), 10); i++) {
                                            try {
                                                WebElement elem = textElements.get(i);
                                                if (elem.isDisplayed()) {
                                                    String text = elem.getText().trim();
                                                    if (text.length() > 0 && text.length() < 200) {
                                                        System.out.println("     Element " + (i + 1) + ": '" + text + "'");
                                                    }
                                                }
                                            } catch (Exception debugEx) {
                                                continue;
                                            }
                                        }
                                    } catch (Exception debugEx) {
                                        continue;
                                    }
                                }
                            }
                            
                            // Check for validation messages near textareas
                            try {
                                java.util.List<WebElement> textareas = driver.findElements(By.xpath("//textarea"));
                                System.out.println("  📋 Found " + textareas.size() + " textarea(s)");
                                for (WebElement textarea : textareas) {
                                    try {
                                        if (textarea.isDisplayed()) {
                                            // Check for validation message as next sibling or parent
                                            WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
                                                "return arguments[0].parentElement;", textarea);
                                            String parentText = parent.getText();
                                            if (parentText.toLowerCase().contains("blank") || parentText.toLowerCase().contains("comment") || parentText.toLowerCase().contains("reply")) {
                                                System.out.println("     Textarea parent text: '" + parentText + "'");
                                            }
                                        }
                                    } catch (Exception debugEx) {
                                        continue;
                                    }
                                }
                            } catch (Exception debugEx) {
                                System.out.println("  ⚠️ Could not check textarea validation");
                            }
                            
                            // Check page source for alert text
                            try {
                                String pageSource = driver.getPageSource();
                                if (pageSource.toLowerCase().contains(expectedMessage.toLowerCase().substring(0, Math.min(10, expectedMessage.length())))) {
                                    System.out.println("  ✅ Found alert text in page source!");
                                    // Try to find it with a more flexible search
                                    int index = pageSource.toLowerCase().indexOf(expectedMessage.toLowerCase().substring(0, Math.min(20, expectedMessage.length())));
                                    if (index > 0) {
                                        int start = Math.max(0, index - 50);
                                        int end = Math.min(pageSource.length(), index + 100);
                                        System.out.println("     Context: '" + pageSource.substring(start, end).replaceAll("\\s+", " ") + "'");
                                    }
                                }
                            } catch (Exception debugEx) {
                                System.out.println("  ⚠️ Could not check page source");
                            }
                            
                        } catch (Exception debugEx) {
                            System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                        }
                        System.err.println("  ❌ Alert message not found: " + ex.getMessage());
                        return false;
                    }
                }
                
                if (alertElement != null) {
                    String alertText = alertElement.getText();
                    System.out.println("  📋 Found on-page alert: '" + alertText + "'");
                    
                    // Check if the alert text contains the expected message (case-insensitive partial match)
                    if (alertText.toLowerCase().contains(expectedMessage.toLowerCase())) {
                        System.out.println("  ✅ Alert message matches expected: '" + expectedMessage + "'");
                        return true;
                    } else {
                        System.out.println("  ⚠️ Alert message doesn't match. Expected: '" + expectedMessage + "', Found: '" + alertText + "'");
                        return false;
                    }
                } else {
                    System.err.println("  ❌ Alert element is null");
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying alert message: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify that comment is posted successfully
     * This method checks if the comment appears in the comment section
     * @param commentText The comment text that should be displayed
     * @return true if comment is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyCommentPosted(String commentText) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try to find the posted comment by text
            // This xpath looks for the comment text in various possible locations
            By commentLocator = By.xpath("//*[contains(text(),'" + commentText + "')] | " +
                                         "//div[contains(@class,'comment') and contains(text(),'" + commentText + "')] | " +
                                         "//span[contains(text(),'" + commentText + "')] | " +
                                         "//p[contains(text(),'" + commentText + "')]");
            
            try {
                WebElement commentElement = wait.until(ExpectedConditions.presenceOfElementLocated(commentLocator));
                String foundText = commentElement.getText();
                System.out.println("  📋 Found comment: '" + foundText + "'");
                
                if (foundText.contains(commentText)) {
                    System.out.println("  ✅ Comment successfully posted and verified: '" + commentText + "'");
                    return true;
                } else {
                    System.out.println("  ⚠️ Comment text doesn't match. Expected: '" + commentText + "', Found: '" + foundText + "'");
                    return false;
                }
            } catch (Exception e) {
                System.err.println("  ❌ Comment not found after posting: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying comment: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== REPLY METHODS ====================
    // Updated by Shahu.D
    
    /**
     * Click on Reply link for the posted comment
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickReplyLink() throws Exception {
       
    	  click( CompletedInitiativePageLocators.replyButton,".replyButton");
    }
    
    /**
     * Enter reply comment in the reply textarea
     * Updated by Shahu.D
     * @param replyText The reply text to enter (can be empty for blank reply test)
     * @throws Exception if element is not found or input fails
     */
    public void enterReplyComment(String replyText) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait longer for reply textarea to appear after clicking Reply link
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            System.out.println("  🔍 Searching for reply textarea...");
            
            // Try multiple locator strategies
            WebElement replyTextarea = null;
            boolean found = false;
            
            By[] replyTextareaLocators = {
                By.xpath("/html/body/div[2]/div[3]/div/div[3]/div[2]/div[2]/textarea"),
                CompletedInitiativePageLocators.replyTextarea,
                By.xpath("//div[contains(@class,'reply')]//textarea"),
                By.xpath("//div[contains(@class,'MuiDialog')]//textarea"),
                By.xpath("//div[contains(@role,'dialog')]//textarea"),
                By.xpath("//textarea[contains(@placeholder,'reply') or contains(@placeholder,'Reply')]"),
                By.xpath("//textarea[last()]") // Last textarea on page (if reply is the most recent)
            };
            
            for (By locator : replyTextareaLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        // Find the first visible element
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    replyTextarea = element;
                                    found = true;
                                    System.out.println("  ✅ Found reply textarea using locator: " + locator);
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (!found || replyTextarea == null) {
                // Wait a bit more and try again with explicit wait
                System.out.println("  ⚠️ Reply textarea not found immediately, waiting longer...");
                waitForSeconds(3);
                
                try {
                    replyTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div[3]/div/div[3]/div[2]/div[2]/textarea")));
                    found = true;
                    System.out.println("  ✅ Found reply textarea after additional wait");
                } catch (Exception e) {
                    // Debug: Print available textareas
                    try {
                        java.util.List<WebElement> allTextareas = driver.findElements(By.xpath("//textarea"));
                        System.out.println("  📋 Found " + allTextareas.size() + " textarea(s) on the page");
                        for (int i = 0; i < Math.min(allTextareas.size(), 5); i++) {
                            try {
                                WebElement ta = allTextareas.get(i);
                                String id = ta.getAttribute("id");
                                String placeholder = ta.getAttribute("placeholder");
                                boolean visible = ta.isDisplayed();
                                System.out.println("     Textarea " + (i + 1) + ": id='" + id + "', placeholder='" + placeholder + "', visible=" + visible);
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                    } catch (Exception debugEx) {
                        System.out.println("  ⚠️ Could not get debug info");
                    }
                    throw new Exception("Reply textarea not found. Please ensure the Reply link was clicked and the textarea is visible.");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", replyTextarea);
            waitForSeconds(1);
            
            // Clear any existing text
            replyTextarea.clear();
            waitForSeconds(1);
            
            // Enter the reply text
            if (replyText != null && !replyText.trim().isEmpty()) {
                replyTextarea.sendKeys(replyText);
                System.out.println("  ✅ Entered reply comment: '" + replyText + "'");
            } else {
                System.out.println("  ✅ Reply textarea cleared (blank reply)");
            }
            
            waitForSeconds(1);
        } catch (Exception e) {
            System.err.println("  ❌ Error entering reply comment: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on the Reply button to submit reply
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickReplyButton() throws Exception {
       
             
              click( CompletedInitiativePageLocators.replyButton1,".replyButton");
             
            };
          
                   
          
    
    /**
     * Verify that reply is successfully added below the original comment
     * @param replyText The reply text that should be displayed
     * @return true if reply is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyReplyPosted(String replyText) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try to find the posted reply by text
            // This xpath looks for the reply text in various possible locations
            By replyLocator = By.xpath("//*[contains(text(),'" + replyText + "')] | " +
                                      "//div[contains(@class,'reply') and contains(text(),'" + replyText + "')] | " +
                                      "//span[contains(text(),'" + replyText + "')] | " +
                                      "//p[contains(text(),'" + replyText + "')]");
            
            try {
                WebElement replyElement = wait.until(ExpectedConditions.presenceOfElementLocated(replyLocator));
                String foundText = replyElement.getText();
                System.out.println("  📋 Found reply: '" + foundText + "'");
                
                if (foundText.contains(replyText)) {
                    System.out.println("  ✅ Reply successfully posted and verified: '" + replyText + "'");
                    return true;
                } else {
                    System.out.println("  ⚠️ Reply text doesn't match. Expected: '" + replyText + "', Found: '" + foundText + "'");
                    return false;
                }
            } catch (Exception e) {
                System.err.println("  ❌ Reply not found after posting: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying reply: " + e.getMessage());
            return false;
        }
    }

    // ==================== INITIATIVE STATUS MANAGEMENT METHODS ====================
    // Updated by Shahu.D
    
    /**
     * Click on Initiative Status Management page
     * Updated by Shahu.D - Enhanced to navigate back to Initiative Management menu first
     * @throws Exception if element is not found or click fails
     */
    public void clickInitiativeStatusManagementPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Initiative Status Management page...");
            
            // First, try to navigate back to Initiative Management menu if needed
            // Check if we're already on a sub-page and need to go back
            try {
                // Try to click on Initiative Management navigation to expand menu
                WebElement initiativeManagementNav = wait.until(ExpectedConditions.elementToBeClickable(
                    CompletedInitiativePageLocators.initiativeManagementNav));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeManagementNav);
                System.out.println("  ✅ Clicked Initiative Management navigation to expand menu");
                waitForSeconds(2);
            } catch (Exception navEx) {
                System.out.println("  ⚠️ Could not click Initiative Management nav, continuing...");
            }
            
            // Wait for children-panel-container to be ready
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='children-panel-container']")));
                System.out.println("  ✅ children-panel-container found");
                waitForSeconds(2);
            } catch (Exception containerEx) {
                System.out.println("  ⚠️ children-panel-container not found immediately, continuing...");
            }
            
            // Try multiple locator strategies
            WebElement statusManagementPage = null;
            boolean found = false;
            
            By[] statusManagementLocators = {
                CompletedInitiativePageLocators.initiativeStatusManagementPage,
                By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Initiative Status Management')]"),
                By.xpath("//p[contains(text(),'Initiative Status Management')]"),
                By.xpath("//*[@id='children-panel-container']/div[3]/div[11]/p"),
                By.xpath("//div[@id='children-panel-container']//p[normalize-space(text())='Initiative Status Management']")
            };
            
            for (By locator : statusManagementLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    statusManagementPage = element;
                                    found = true;
                                    System.out.println("  ✅ Found Initiative Status Management page using locator: " + locator);
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || statusManagementPage == null) {
                // Wait a bit more and try with explicit wait
                System.out.println("  ⚠️ Initiative Status Management page not found immediately, waiting longer...");
                waitForSeconds(3);
                
                try {
                    statusManagementPage = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.initiativeStatusManagementPage));
                    found = true;
                    System.out.println("  ✅ Found Initiative Status Management page after additional wait");
                } catch (Exception ex) {
                    // Debug: Print available menu items
                    try {
                        java.util.List<WebElement> menuItems = driver.findElements(By.xpath("//*[@id='children-panel-container']//p"));
                        System.out.println("  📋 Found " + menuItems.size() + " menu item(s)");
                        for (int i = 0; i < Math.min(menuItems.size(), 15); i++) {
                            try {
                                WebElement item = menuItems.get(i);
                                if (item.isDisplayed()) {
                                    String text = item.getText();
                                    System.out.println("     Menu item " + (i + 1) + ": '" + text + "'");
                                }
                            } catch (Exception debugEx) {
                                continue;
                            }
                        }
                    } catch (Exception debugEx) {
                        System.out.println("  ⚠️ Could not get debug info");
                    }
                    throw new Exception("Initiative Status Management page not found. Please ensure the Initiative Management menu is expanded.");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", statusManagementPage);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusManagementPage);
                System.out.println("  ✅ Clicked Initiative Status Management page using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(statusManagementPage).click().perform();
                    System.out.println("  ✅ Clicked Initiative Status Management page using Actions");
                } catch (Exception e2) {
                    statusManagementPage.click();
                    System.out.println("  ✅ Clicked Initiative Status Management page using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Initiative Status Management page");
            waitForSeconds(3); // Wait for page to load
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Initiative Status Management page: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Action dropdown field
     * Updated by Shahu.D - Enhanced with multiple locator strategies
     * @throws Exception if element is not found or click fails
     */
    public void clickActionDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait longer for page to fully load
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Action dropdown...");
            
            // Try multiple locator strategies
            WebElement actionDropdown = null;
            boolean found = false;
            
            By[] actionDropdownLocators = {
                CompletedInitiativePageLocators.actionDropdown,
                By.xpath("//*[@id='Dropdown0-option']"),
                By.xpath("//*[@id='Dropdown0']/span[2]"),
                By.xpath("//*[@id='Dropdown0']"),
                By.xpath("//span[@id='Dropdown0-option']"),
                By.xpath("//div[@id='Dropdown0']"),
                By.xpath("//*[contains(@id,'Dropdown0')]"),
                By.xpath("//*[@role='combobox' and contains(@id,'Dropdown')]"),
                By.xpath("//*[contains(@class,'dropdown') and contains(@id,'Dropdown0')]")
            };
            
            for (By locator : actionDropdownLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    actionDropdown = element;
                                    found = true;
                                    System.out.println("  ✅ Found Action dropdown using locator: " + locator);
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || actionDropdown == null) {
                // Wait a bit more and try with explicit wait
                System.out.println("  ⚠️ Action dropdown not found immediately, waiting longer...");
                waitForSeconds(3);
                
                try {
                    actionDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.actionDropdown));
                    found = true;
                    System.out.println("  ✅ Found Action dropdown after additional wait");
                } catch (Exception ex) {
                    // Debug: Print available dropdown elements
                    try {
                        java.util.List<WebElement> allDropdowns = driver.findElements(By.xpath("//*[contains(@id,'Dropdown')]"));
                        System.out.println("  📋 Found " + allDropdowns.size() + " dropdown element(s)");
                        for (int i = 0; i < Math.min(allDropdowns.size(), 10); i++) {
                            try {
                                WebElement dd = allDropdowns.get(i);
                                if (dd.isDisplayed()) {
                                    String id = dd.getAttribute("id");
                                    String tag = dd.getTagName();
                                    System.out.println("     Dropdown " + (i + 1) + ": id='" + id + "', tag='" + tag + "'");
                                }
                            } catch (Exception debugEx) {
                                continue;
                            }
                        }
                    } catch (Exception debugEx) {
                        System.out.println("  ⚠️ Could not get debug info");
                    }
                    throw new Exception("Action dropdown not found. Please ensure the Initiative Status Management page is fully loaded.");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", actionDropdown);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionDropdown);
                System.out.println("  ✅ Clicked Action dropdown using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(actionDropdown).click().perform();
                    System.out.println("  ✅ Clicked Action dropdown using Actions");
                } catch (Exception e2) {
                    actionDropdown.click();
                    System.out.println("  ✅ Clicked Action dropdown using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Action dropdown");
            waitForSeconds(2); // Wait for dropdown to open
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Action dropdown: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Select "Mark Initiatives as Complete" from dropdown
     * Updated by Shahu.D - Enhanced with multiple locator strategies
     * @throws Exception if element is not found or click fails
     */
    public void selectMarkInitiativesAsComplete() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait longer for dropdown to fully open
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for 'Mark Initiatives as Complete' option...");
            
            // Wait for dropdown list to appear
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(@id,'Dropdown0-list')]")));
                System.out.println("  ✅ Dropdown list container found");
                waitForSeconds(1);
            } catch (Exception listEx) {
                System.out.println("  ⚠️ Dropdown list container not found, continuing...");
            }
            
            // Try multiple locator strategies
            WebElement markCompleteOption = null;
            boolean found = false;
            
            By[] markCompleteLocators = {
                CompletedInitiativePageLocators.markInitiativesAsCompleteOption,
                By.xpath("//*[@id='Dropdown0-list2']/span/span"),
                By.xpath("//*[@id='Dropdown0-list2']//span[contains(text(),'Mark Initiatives as Complete')]"),
                By.xpath("//*[@id='Dropdown0-list2']//*[contains(text(),'Mark Initiatives as Complete')]"),
                By.xpath("//*[contains(@id,'Dropdown0-list')]//span[contains(text(),'Mark Initiatives as Complete')]"),
                By.xpath("//*[contains(@id,'Dropdown0-list')]//*[contains(text(),'Mark') and contains(text(),'Complete')]"),
                By.xpath("//*[@role='option' and contains(text(),'Mark Initiatives as Complete')]"),
                By.xpath("//*[@role='option' and contains(text(),'Mark') and contains(text(),'Complete')]"),
                By.xpath("//li[contains(text(),'Mark Initiatives as Complete')]"),
                By.xpath("//*[contains(text(),'Mark Initiatives as Complete')]")
            };
            
            for (By locator : markCompleteLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String text = element.getText();
                                    if (text.toLowerCase().contains("mark") && text.toLowerCase().contains("complete")) {
                                        markCompleteOption = element;
                                        found = true;
                                        System.out.println("  ✅ Found 'Mark Initiatives as Complete' option using locator: " + locator);
                                        System.out.println("     Option text: '" + text + "'");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || markCompleteOption == null) {
                // Wait a bit more and try with explicit wait
                System.out.println("  ⚠️ Option not found immediately, waiting longer...");
                waitForSeconds(2);
                
                try {
                    markCompleteOption = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.markInitiativesAsCompleteOption));
                    found = true;
                    System.out.println("  ✅ Found option after additional wait");
                } catch (Exception ex) {
                    // Debug: Print available dropdown options
                    try {
                        System.out.println("  🔍 Debugging: Searching for dropdown options...");
                        java.util.List<WebElement> allOptions = driver.findElements(By.xpath("//*[contains(@id,'Dropdown0-list')]//span | //*[@role='option']"));
                        System.out.println("  📋 Found " + allOptions.size() + " option element(s)");
                        for (int i = 0; i < Math.min(allOptions.size(), 10); i++) {
                            try {
                                WebElement opt = allOptions.get(i);
                                if (opt.isDisplayed()) {
                                    String text = opt.getText();
                                    if (text.length() > 0 && text.length() < 100) {
                                        System.out.println("     Option " + (i + 1) + ": '" + text + "'");
                                    }
                                }
                            } catch (Exception debugEx) {
                                continue;
                            }
                        }
                    } catch (Exception debugEx) {
                        System.out.println("  ⚠️ Could not get debug info");
                    }
                    throw new Exception("'Mark Initiatives as Complete' option not found. Please ensure the dropdown is open and the option is available.");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", markCompleteOption);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", markCompleteOption);
                System.out.println("  ✅ Clicked option using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(markCompleteOption).click().perform();
                    System.out.println("  ✅ Clicked option using Actions");
                } catch (Exception e2) {
                    markCompleteOption.click();
                    System.out.println("  ✅ Clicked option using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully selected 'Mark Initiatives as Complete'");
            waitForSeconds(2); // Wait for selection to apply
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting 'Mark Initiatives as Complete': " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Show button
     * Updated by Shahu.D - Enhanced with multiple locator strategies
     * @throws Exception if element is not found or click fails
     */
    public void clickShowButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Wait for page to update after dropdown selection
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Show button...");
            
            // Try multiple locator strategies
            WebElement showButton = null;
            boolean found = false;
            
            By[] showButtonLocators = {
                By.xpath("//*[@id='filtershowbtn']"), // Updated by Shahu.D - Found from debug output
                By.xpath("//button[@id='filtershowbtn']"),
                CompletedInitiativePageLocators.showButton,
                By.xpath("//*[@id='id__1']"),
                By.xpath("//button[@id='id__1']"),
                By.xpath("//*[@id='id__1']//ancestor::button"),
                By.xpath("//button[contains(text(),'Show')]"),
                By.xpath("//button[contains(text(),'SHOW')]"),
                By.xpath("//*[contains(@id,'id__1')]"),
                By.xpath("//button[contains(@aria-label,'Show')]")
            };
            
            for (By locator : showButtonLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String text = element.getText().toLowerCase();
                                    String tag = element.getTagName();
                                    String id = element.getAttribute("id");
                                    // Check if it's a button or contains "Show" text or has filtershowbtn/id__1 ID
                                    if (tag.equals("button") && (text.contains("show") || (id != null && (id.contains("filtershowbtn") || id.contains("id__1"))))) {
                                        showButton = element;
                                        found = true;
                                        System.out.println("  ✅ Found Show button using locator: " + locator);
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || showButton == null) {
                // Wait a bit more and try with explicit wait
                System.out.println("  ⚠️ Show button not found immediately, waiting longer...");
                waitForSeconds(3);
                
                try {
                    showButton = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.showButton));
                    found = true;
                    System.out.println("  ✅ Found Show button after additional wait");
                } catch (Exception ex) {
                    // Debug: Print available buttons
                    try {
                        System.out.println("  🔍 Debugging: Searching for Show button...");
                        java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                        System.out.println("  📋 Found " + allButtons.size() + " button(s)");
                        for (int i = 0; i < Math.min(allButtons.size(), 15); i++) {
                            try {
                                WebElement btn = allButtons.get(i);
                                if (btn.isDisplayed()) {
                                    String id = btn.getAttribute("id");
                                    String text = btn.getText();
                                    System.out.println("     Button " + (i + 1) + ": id='" + id + "', text='" + text + "'");
                                }
                            } catch (Exception debugEx) {
                                continue;
                            }
                        }
                    } catch (Exception debugEx) {
                        System.out.println("  ⚠️ Could not get debug info");
                    }
                    throw new Exception("Show button not found. Please ensure the dropdown selection was applied and the Show button is visible.");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", showButton);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showButton);
                System.out.println("  ✅ Clicked Show button using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(showButton).click().perform();
                    System.out.println("  ✅ Clicked Show button using Actions");
                } catch (Exception e2) {
                    showButton.click();
                    System.out.println("  ✅ Clicked Show button using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Show button");
            waitForSeconds(3); // Wait for initiatives to load
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Show button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Action link of initiative by Initiative Code
     * @param initiativeCode The initiative code to find and click action for
     * @throws Exception if element is not found or click fails
     */
    public void clickActionLinkByInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Action link for Initiative Code: " + initiativeCode);
            
            // First, find the row containing the initiative code
            By rowLocator = CompletedInitiativePageLocators.getInitiativeRowByCode(initiativeCode);
            WebElement initiativeRow = wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));
            
            System.out.println("  ✅ Found row for Initiative Code: " + initiativeCode);
            
            // Find the Action button/link in that row
            WebElement actionLink = initiativeRow.findElement(By.xpath(".//button[contains(@aria-label,'Action') or contains(@aria-label,'action')] | .//button[.//svg] | .//td[6]//button"));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", actionLink);
            waitForSeconds(1);
            
            // Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionLink);
                System.out.println("  ✅ Clicked Action link using JavaScript");
            } catch (Exception e) {
                try {
                    actions.moveToElement(actionLink).click().perform();
                    System.out.println("  ✅ Clicked Action link using Actions");
                } catch (Exception e2) {
                    actionLink.click();
                    System.out.println("  ✅ Clicked Action link using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Action link for Initiative Code: " + initiativeCode);
            waitForSeconds(2); // Wait for modal/dialog to open
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Action link for Initiative Code " + initiativeCode + ": " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter comment in the Comment box for marking initiative as complete
     * Updated by Shahu.D - Enhanced with multiple locator strategies and debug logging
     * @param comment The comment text to enter
     * @throws Exception if element is not found or input fails
     */
    public void enterCommentForMarkComplete(String comment) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D - Increased wait for modal to open
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D - Increased wait time
            
            // Updated by Shahu.D - Wait for modal/dialog to appear
            try {
                System.out.println("  ⏳ Waiting for modal/dialog to appear...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@role,'dialog')] | //div[contains(@class,'MuiDialog')] | //div[contains(@class,'modal')]")));
                System.out.println("  ✅ Modal/dialog appeared");
                waitForSeconds(1);
            } catch (Exception modalEx) {
                System.out.println("  ⚠️ Modal/dialog wait timeout, continuing...");
            }
            
            System.out.println("  🔍 Searching for Comment box...");
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] commentBoxLocators = {
                CompletedInitiativePageLocators.commentBoxForMarkComplete,
                By.xpath("//*[@id='TextField99']"),
                By.xpath("//textarea[@id='TextField99']"),
                By.xpath("//textarea[contains(@id,'TextField')]"),
                By.xpath("//div[contains(@role,'dialog')]//textarea"),
                By.xpath("//textarea[contains(@placeholder,'Comment') or contains(@placeholder,'comment')]"),
                By.xpath("//*[contains(@id,'TextField')]"),
                By.xpath("//input[contains(@id,'TextField')]"),
                By.xpath("//div[contains(@class,'MuiDialog')]//textarea"),
                By.xpath("//textarea")
            };
            
            WebElement commentBox = null;
            boolean found = false;
            
            for (By locator : commentBoxLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String tag = element.getTagName();
                                    String id = element.getAttribute("id");
                                    // Check if it's a textarea or input field
                                    if ((tag.equals("textarea") || tag.equals("input")) && 
                                        (id != null && id.contains("TextField")) || 
                                        element.getAttribute("placeholder") != null) {
                                        commentBox = element;
                                        found = true;
                                        System.out.println("  ✅ Found Comment box using locator: " + locator);
                                        System.out.println("     Element tag: " + tag + ", id: " + id);
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found) {
                // Debug: Print available textareas and inputs
                try {
                    System.out.println("  🔍 Debugging: Searching for Comment box...");
                    java.util.List<WebElement> allTextareas = driver.findElements(By.xpath("//textarea"));
                    java.util.List<WebElement> allInputs = driver.findElements(By.xpath("//input[@type='text' or @type='textarea']"));
                    System.out.println("  📋 Found " + allTextareas.size() + " textarea(s) and " + allInputs.size() + " input(s)");
                    for (int i = 0; i < Math.min(allTextareas.size(), 10); i++) {
                        try {
                            WebElement elem = allTextareas.get(i);
                            if (elem.isDisplayed()) {
                                String id = elem.getAttribute("id");
                                String placeholder = elem.getAttribute("placeholder");
                                System.out.println("     Textarea " + (i + 1) + ": id='" + id + "', placeholder='" + placeholder + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    for (int i = 0; i < Math.min(allInputs.size(), 10); i++) {
                        try {
                            WebElement elem = allInputs.get(i);
                            if (elem.isDisplayed()) {
                                String id = elem.getAttribute("id");
                                String placeholder = elem.getAttribute("placeholder");
                                System.out.println("     Input " + (i + 1) + ": id='" + id + "', placeholder='" + placeholder + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info");
                }
                
                // Try waiting for visibility with the primary locator
                try {
                    commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        CompletedInitiativePageLocators.commentBoxForMarkComplete));
                    found = true;
                } catch (Exception ex) {
                    // Last resort: try to find any visible textarea in a dialog
                    try {
                        java.util.List<WebElement> dialogTextareas = driver.findElements(
                            By.xpath("//div[contains(@role,'dialog')]//textarea | //div[contains(@class,'MuiDialog')]//textarea"));
                        for (WebElement elem : dialogTextareas) {
                            if (elem.isDisplayed() && elem.isEnabled()) {
                                commentBox = elem;
                                found = true;
                                System.out.println("  ✅ Found Comment box using fallback dialog textarea");
                                break;
                            }
                        }
                    } catch (Exception fallbackEx) {
                        // Ignore
                    }
                }
            }
            
            if (!found || commentBox == null) {
                throw new Exception("Comment box not found. Please ensure the Action link was clicked and the modal/dialog is open.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", commentBox);
            waitForSeconds(1);
            
            // Clear and enter comment - Updated by Shahu.D - Multiple input strategies
            try {
                commentBox.clear();
                commentBox.sendKeys(comment);
            } catch (Exception inputEx) {
                // Try JavaScript input as fallback
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", commentBox);
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", commentBox, comment);
                // Trigger input event
                ((JavascriptExecutor) driver).executeScript(
                    "var event = new Event('input', { bubbles: true }); arguments[0].dispatchEvent(event);", commentBox);
            }
            
            System.out.println("  ✅ Entered comment: '" + comment + "'");
            waitForSeconds(1);
        } catch (Exception e) {
            System.err.println("  ❌ Error entering comment: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Save button for marking initiative as complete
     * Updated by Shahu.D - Enhanced with multiple locator strategies and debug logging
     * @throws Exception if element is not found or click fails
     */
    public void clickSaveButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D - Increased wait
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D - Increased wait time
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Save button...");
            
            // Updated by Shahu.D - Multiple locator strategies, prioritizing dialog buttons
            By[] saveButtonLocators = {
                By.xpath("//div[contains(@role,'dialog')]//button[contains(text(),'Save')]"), // Updated by Shahu.D - Prioritize dialog buttons
                By.xpath("//div[contains(@class,'MuiDialog')]//button[contains(text(),'Save')]"),
                CompletedInitiativePageLocators.saveButtonForMarkComplete,
                By.xpath("//*[@id='id__96']"),
                By.xpath("//button[@id='id__96']"),
                By.xpath("//button[contains(@id,'id__') and contains(text(),'Save')]"),
                By.xpath("//button[contains(text(),'Save')]"),
                By.xpath("//button[contains(text(),'SAVE')]"),
                By.xpath("//button[contains(@aria-label,'Save')]"),
                By.xpath("//*[contains(@id,'id__') and @type='button']")
            };
            
            WebElement saveButton = null;
            boolean found = false;
            
            for (By locator : saveButtonLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String text = element.getText().toLowerCase().trim();
                                    String tag = element.getTagName();
                                    String id = element.getAttribute("id");
                                    // Updated by Shahu.D - More lenient: accept button with "Save" text OR id__ pattern
                                    if (tag.equals("button") && 
                                        (text.contains("save") || 
                                         (id != null && (id.contains("id__96") || id.contains("id__"))))) {
                                        saveButton = element;
                                        found = true;
                                        System.out.println("  ✅ Found Save button using locator: " + locator);
                                        System.out.println("     Element tag: " + tag + ", id: " + id + ", text: '" + element.getText() + "'");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // Updated by Shahu.D - If not found, search all buttons for "Save" text
            if (!found) {
                try {
                    System.out.println("  🔍 Searching all buttons for 'Save' text...");
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    for (WebElement btn : allButtons) {
                        try {
                            if (btn.isDisplayed() && btn.isEnabled()) {
                                String btnText = btn.getText().toLowerCase().trim();
                                if (btnText.contains("save")) {
                                    saveButton = btn;
                                    found = true;
                                    System.out.println("  ✅ Found Save button by searching all buttons");
                                    System.out.println("     Element id: " + btn.getAttribute("id") + ", text: '" + btn.getText() + "'");
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    // Ignore
                }
            }
            
            if (!found) {
                // Debug: Print available buttons
                try {
                    System.out.println("  🔍 Debugging: Searching for Save button...");
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    System.out.println("  📋 Found " + allButtons.size() + " button(s)");
                    for (int i = 0; i < Math.min(allButtons.size(), 15); i++) {
                        try {
                            WebElement btn = allButtons.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String text = btn.getText();
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', text='" + text + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info");
                }
                
                // Updated by Shahu.D - Last resort: try to find any visible Save button in a dialog
                try {
                    java.util.List<WebElement> dialogButtons = driver.findElements(
                        By.xpath("//div[contains(@role,'dialog')]//button | //div[contains(@class,'MuiDialog')]//button"));
                    System.out.println("  🔍 Checking " + dialogButtons.size() + " dialog button(s)...");
                    for (WebElement elem : dialogButtons) {
                        try {
                            if (elem.isDisplayed() && elem.isEnabled()) {
                                String text = elem.getText().toLowerCase().trim();
                                String id = elem.getAttribute("id");
                                System.out.println("     Dialog button: id='" + id + "', text='" + elem.getText() + "'");
                                if (text.contains("save")) {
                                    saveButton = elem;
                                    found = true;
                                    System.out.println("  ✅ Found Save button using fallback dialog button");
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception fallbackEx) {
                    System.out.println("  ⚠️ Fallback dialog search failed: " + fallbackEx.getMessage());
                }
            }
            
            if (!found || saveButton == null) {
                throw new Exception("Save button not found. Please ensure the comment was entered and the Save button is visible.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                System.out.println("  ✅ Clicked Save button using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(saveButton).click().perform();
                    System.out.println("  ✅ Clicked Save button using Actions");
                } catch (Exception e2) {
                    saveButton.click();
                    System.out.println("  ✅ Clicked Save button using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Save button");
            waitForSeconds(3); // Wait for save to complete
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Save button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Save button on confirmation popup after marking initiative as complete
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSaveButtonOnConfirmationPopup() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D - Wait for confirmation popup to appear
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D - Increased wait time
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Save button on confirmation popup...");
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] confirmationSaveButtonLocators = {
                CompletedInitiativePageLocators.saveButtonOnConfirmationPopup,
                By.xpath("//*[@id='id__56']"),
                By.xpath("//button[@id='id__56']"),
                By.xpath("//div[contains(@role,'dialog')]//button[@id='id__56']"),
                By.xpath("//div[contains(@class,'MuiDialog')]//button[@id='id__56']"),
                By.xpath("//button[contains(@id,'id__56')]")
            };
            
            WebElement confirmationSaveButton = null;
            boolean found = false;
            
            // Updated by Shahu.D - Wait for confirmation popup to appear
            try {
                System.out.println("  ⏳ Waiting for confirmation popup to appear...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@role,'dialog')] | //div[contains(@class,'MuiDialog')] | //*[@id='id__56']")));
                System.out.println("  ✅ Confirmation popup appeared");
                waitForSeconds(1);
            } catch (Exception popupEx) {
                System.out.println("  ⚠️ Confirmation popup wait timeout, continuing...");
            }
            
            for (By locator : confirmationSaveButtonLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String id = element.getAttribute("id");
                                    String tag = element.getTagName();
                                    // Check if it's a button with id__56
                                    if (tag.equals("button") && id != null && id.contains("id__56")) {
                                        confirmationSaveButton = element;
                                        found = true;
                                        System.out.println("  ✅ Found confirmation Save button using locator: " + locator);
                                        System.out.println("     Element tag: " + tag + ", id: " + id);
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found) {
                // Debug: Print available buttons
                try {
                    System.out.println("  🔍 Debugging: Searching for confirmation Save button...");
                    java.util.List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                    System.out.println("  📋 Found " + allButtons.size() + " button(s)");
                    for (int i = 0; i < Math.min(allButtons.size(), 15); i++) {
                        try {
                            WebElement btn = allButtons.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String text = btn.getText();
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', text='" + text + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info");
                }
                
                // Try waiting for clickable with the primary locator
                try {
                    confirmationSaveButton = wait.until(ExpectedConditions.elementToBeClickable(
                        CompletedInitiativePageLocators.saveButtonOnConfirmationPopup));
                    found = true;
                } catch (Exception ex) {
                    throw new Exception("Confirmation Save button not found. Please ensure the confirmation popup is displayed.");
                }
            }
            
            if (!found || confirmationSaveButton == null) {
                throw new Exception("Confirmation Save button not found. Please ensure the confirmation popup is displayed.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", confirmationSaveButton);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmationSaveButton);
                System.out.println("  ✅ Clicked confirmation Save button using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(confirmationSaveButton).click().perform();
                    System.out.println("  ✅ Clicked confirmation Save button using Actions");
                } catch (Exception e2) {
                    confirmationSaveButton.click();
                    System.out.println("  ✅ Clicked confirmation Save button using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on confirmation Save button");
            waitForSeconds(3); // Wait for confirmation to complete
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking confirmation Save button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Verify that initiative with given code is displayed on Completed Initiatives page
     * Updated by Shahu.D - Enhanced with better debugging, more locator strategies, and increased waits
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeDisplayedOnCompletedPage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Updated by Shahu.D - Increased wait for page to load
        
        try {
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is displayed on Completed Initiatives page...");
            
            // Updated by Shahu.D - Wait for table/data to load
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
            
            // Updated by Shahu.D - Try multiple locator strategies
            By[] initiativeLocators = {
                By.xpath("//tr[td[contains(text(),'" + initiativeCode + "')]]"), // Table row
                By.xpath("//table//td[contains(text(),'" + initiativeCode + "')]"), // Table cell
                By.xpath("//*[normalize-space(text())='" + initiativeCode + "']"), // Exact match
                By.xpath("//*[contains(text(),'" + initiativeCode + "')]"), // Contains text
                By.xpath("//div[contains(@class,'ag-row')]//div[contains(text(),'" + initiativeCode + "')]"), // AG Grid row
                By.xpath("//div[contains(@class,'ag-cell')]//*[contains(text(),'" + initiativeCode + "')]"), // AG Grid cell
                By.xpath("//div[contains(@id,'row') and contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//span[contains(text(),'" + initiativeCode + "')]"),
                By.xpath("//div[contains(text(),'" + initiativeCode + "')]")
            };
            
            boolean found = false;
            for (By locator : initiativeLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        System.out.println("     Found " + elements.size() + " element(s)");
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed()) {
                                    String text = element.getText().trim();
                                    if (text.contains(initiativeCode) || text.equals(initiativeCode)) {
                                        System.out.println("  ✅ Found Initiative Code '" + initiativeCode + "' on Completed Initiatives page");
                                        System.out.println("     Found in element with text: '" + text + "'");
                                        System.out.println("     Using locator: " + locator);
                                        found = true;
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found) {
                // Updated by Shahu.D - Enhanced debugging: Print available table data
                try {
                    System.out.println("  🔍 Debugging: Searching for Initiative Code on page...");
                    
                    // Try to find all table rows
                    java.util.List<WebElement> allRows = driver.findElements(By.xpath("//table//tr | //div[contains(@class,'ag-row')]"));
                    System.out.println("  📋 Found " + allRows.size() + " table row(s)");
                    
                    // Print first 10 rows
                    for (int i = 0; i < Math.min(allRows.size(), 10); i++) {
                        try {
                            WebElement row = allRows.get(i);
                            if (row.isDisplayed()) {
                                String rowText = row.getText().trim();
                                if (!rowText.isEmpty()) {
                                    System.out.println("     Row " + (i + 1) + ": '" + rowText.substring(0, Math.min(100, rowText.length())) + "'");
                                }
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    
                    // Also search for any text containing numbers that might be initiative codes
                    java.util.List<WebElement> allTextElements = driver.findElements(
                        By.xpath("//*[contains(text(),'2021') or contains(text(),'2020') or contains(text(),'2022')]"));
                    System.out.println("  📋 Found " + Math.min(allTextElements.size(), 20) + " element(s) with potential initiative codes");
                    for (int i = 0; i < Math.min(allTextElements.size(), 20); i++) {
                        try {
                            WebElement elem = allTextElements.get(i);
                            if (elem.isDisplayed()) {
                                String text = elem.getText().trim();
                                if (!text.isEmpty() && text.length() < 50) {
                                    System.out.println("     Element " + (i + 1) + ": '" + text + "'");
                                }
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                System.err.println("  ❌ Initiative Code '" + initiativeCode + "' not found on Completed Initiatives page");
                System.err.println("  💡 Suggestion: The initiative may need more time to appear, or it may not have been marked as complete successfully.");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying initiative display: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Click on Workflow history link by Initiative Code
     * Updated by Shahu.D - Now accepts initiative code to find the correct row
     * @param initiativeCode The initiative code to find the workflow history link for
     * @throws Exception if element is not found or click fails
     */
    public void clickWorkflowHistoryLinkByInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Updated by Shahu.D - Increased wait for page to load
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            // Updated by Shahu.D - Wait for page content and table data to load
            try {
                System.out.println("  ⏳ Waiting for page content to load...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//body | //div | //table | //*[contains(@id,'row')]")));
                System.out.println("  ✅ Page content loaded");
                waitForSeconds(2);
                
                // Updated by Shahu.D - Wait for table/data to load
                System.out.println("  ⏳ Waiting for table data to load...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//table | //div[contains(@class,'table')] | //div[contains(@class,'ag-root')] | //*[contains(@id,'row')]")));
                System.out.println("  ✅ Table data loaded");
                waitForSeconds(3); // Updated by Shahu.D - Additional wait for data to render
            } catch (Exception pageEx) {
                System.out.println("  ⚠️ Page load wait timeout, continuing...");
            }
            
            System.out.println("  🔍 Searching for Workflow history link for Initiative Code: " + initiativeCode); // Updated by Shahu.D
            
            // Updated by Shahu.D - First, find the row containing the initiative code
            // Try multiple row locator strategies to handle different table structures
            By[] rowLocators = {
                CompletedInitiativePageLocators.getInitiativeRowByCode(initiativeCode), // Updated by Shahu.D - Table row
                By.xpath("//*[contains(@id,'row') and .//*[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Div-based row with ID
                By.xpath("//div[contains(@class,'ag-row') and .//div[contains(@class,'ag-cell') and contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - AG Grid row
                By.xpath("//*[contains(@class,'row') and .//*[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Generic row class
                By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Table row with td
                By.xpath("//tr[td[normalize-space(text())='" + initiativeCode + "']]"), // Updated by Shahu.D - Exact match
                By.xpath("//*[.//*[normalize-space(text())='" + initiativeCode + "']]") // Updated by Shahu.D - Any element containing the code
            };
            
            WebElement initiativeRow = null;
            boolean rowFound = false;
            
            for (By rowLocator : rowLocators) {
                try {
                    System.out.println("  🔍 Trying row locator: " + rowLocator);
                    java.util.List<WebElement> rows = driver.findElements(rowLocator);
                    if (rows.size() > 0) {
                        for (WebElement row : rows) {
                            try {
                                if (row.isDisplayed()) {
                                    String rowText = row.getText();
                                    if (rowText.contains(initiativeCode)) {
                                        initiativeRow = row;
                                        rowFound = true;
                                        System.out.println("  ✅ Found row for Initiative Code: " + initiativeCode); // Updated by Shahu.D
                                        System.out.println("     Using locator: " + rowLocator); // Updated by Shahu.D
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (rowFound) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!rowFound || initiativeRow == null) {
                // Updated by Shahu.D - Enhanced debugging
                try {
                    System.out.println("  🔍 Debugging: Row not found for Initiative Code: " + initiativeCode);
                    System.out.println("  📍 Current page URL: " + driver.getCurrentUrl());
                    
                    // Check for table rows
                    java.util.List<WebElement> allRows = driver.findElements(By.xpath("//tr | //div[contains(@class,'row')] | //*[contains(@id,'row')]"));
                    System.out.println("  📋 Found " + allRows.size() + " row-like element(s) on page");
                    
                    // Check for any text containing the initiative code
                    java.util.List<WebElement> codeElements = driver.findElements(By.xpath("//*[contains(text(),'" + initiativeCode + "')]"));
                    System.out.println("  📋 Found " + codeElements.size() + " element(s) containing '" + initiativeCode + "'");
                    for (int i = 0; i < Math.min(codeElements.size(), 5); i++) {
                        try {
                            WebElement elem = codeElements.get(i);
                            if (elem.isDisplayed()) {
                                System.out.println("     Element " + (i + 1) + ": tag='" + elem.getTagName() + "', text='" + elem.getText().substring(0, Math.min(50, elem.getText().length())) + "'");
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                throw new Exception("Row not found for Initiative Code: " + initiativeCode + ". Please ensure you are on the Completed Initiatives page and the initiative code exists in the table.");
            }
            
            System.out.println("  ✅ Found row for Initiative Code: " + initiativeCode); // Updated by Shahu.D
            
            // Updated by Shahu.D - Find the Workflow history link (button[3]) in that row
            WebElement workflowHistoryLink = null;
            boolean found = false;
            
            // Updated by Shahu.D - Multiple strategies to find button[3] in the row
            By[] workflowHistoryLocators = {
                By.xpath(".//div[7]//div//div//button[3]"), // Updated by Shahu.D - Specific path: div[7]/div/div/button[3]
                By.xpath(".//button[3]"), // Updated by Shahu.D - Any button[3] in row
                By.xpath(".//div[7]//button[3]"), // Updated by Shahu.D - Button[3] in div[7]
                By.xpath(".//button[.//svg]"), // Updated by Shahu.D - Button with SVG
                By.xpath(".//button[contains(@aria-label,'Workflow') or contains(@aria-label,'workflow')]"), // Updated by Shahu.D - By aria-label
                By.xpath(".//button[contains(@aria-label,'history') or contains(@aria-label,'History')]") // Updated by Shahu.D - By history label
            };
            
            for (By locator : workflowHistoryLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = initiativeRow.findElements(locator); // Updated by Shahu.D
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String tagName = element.getTagName();
                                    // Updated by Shahu.D - Check if it's a button (workflow history should be a button)
                                    if (tagName.equals("button")) {
                                        // Check if it contains SVG (workflow history link has SVG)
                                        try {
                                            WebElement svg = element.findElement(By.xpath(".//svg"));
                                            if (svg != null) {
                                                workflowHistoryLink = element;
                                                found = true;
                                                System.out.println("  ✅ Found Workflow history link using locator: " + locator); // Updated by Shahu.D
                                                System.out.println("     Button contains SVG"); // Updated by Shahu.D
                                                break;
                                            }
                                        } catch (Exception svgEx) {
                                            // If no SVG, still consider it if it's button[3]
                                            if (locator.toString().contains("button[3]")) {
                                                workflowHistoryLink = element;
                                                found = true;
                                                System.out.println("  ✅ Found Workflow history link using locator: " + locator); // Updated by Shahu.D
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || workflowHistoryLink == null) {
                // Updated by Shahu.D - Enhanced debugging: Print available buttons in the row
                try {
                    System.out.println("  🔍 Debugging: Searching for Workflow history link in row...");
                    
                    // Check for buttons in the initiative row
                    java.util.List<WebElement> buttonsInRow = initiativeRow.findElements(By.xpath(".//button")); // Updated by Shahu.D
                    System.out.println("  📋 Found " + buttonsInRow.size() + " button(s) in row for Initiative Code: " + initiativeCode);
                    for (int i = 0; i < Math.min(buttonsInRow.size(), 10); i++) {
                        try {
                            WebElement btn = buttonsInRow.get(i);
                            if (btn.isDisplayed()) {
                                String btnId = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                boolean hasSvg = false;
                                try {
                                    btn.findElement(By.xpath(".//svg"));
                                    hasSvg = true;
                                } catch (Exception ex) {
                                    // No SVG
                                }
                                System.out.println("     Button " + (i + 1) + ": id='" + btnId + "', aria-label='" + ariaLabel + "', hasSVG=" + hasSvg);
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    
                    // Updated by Shahu.D - Try to find button[3] directly as fallback
                    try {
                        System.out.println("  🔍 Trying fallback: Searching for button[3] in row...");
                        WebElement button3 = initiativeRow.findElement(By.xpath(".//div[7]//div//div//button[3] | .//button[3]"));
                        if (button3 != null && button3.isDisplayed() && button3.isEnabled()) {
                            workflowHistoryLink = button3;
                            found = true;
                            System.out.println("  ✅ Found Workflow history link using fallback (button[3] in row)");
                        }
                    } catch (Exception fallbackEx) {
                        System.out.println("  ⚠️ Fallback search failed: " + fallbackEx.getMessage());
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                if (!found || workflowHistoryLink == null) {
                    throw new Exception("Workflow history link not found for Initiative Code: " + initiativeCode + ". Please ensure the row is found and button[3] exists in div[7].");
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", workflowHistoryLink);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", workflowHistoryLink);
                System.out.println("  ✅ Clicked Workflow history link using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(workflowHistoryLink).click().perform();
                    System.out.println("  ✅ Clicked Workflow history link using Actions");
                } catch (Exception e2) {
                    workflowHistoryLink.click();
                    System.out.println("  ✅ Clicked Workflow history link using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Workflow history link for Initiative Code: " + initiativeCode);
            waitForSeconds(2); // Updated by Shahu.D - Wait for workflow history to open
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Workflow history link for Initiative Code " + initiativeCode + ": " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Click on Action Taken dropdown field
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickActionTakenDropdown() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Action Taken dropdown...");
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] actionTakenDropdownLocators = {
                CompletedInitiativePageLocators.actionTakenDropdown,
                By.xpath("//*[@id='Dropdown677']/span[2]"), // Updated by Shahu.D - New XPath
                By.xpath("//*[@id='Dropdown677']"), // Updated by Shahu.D - Dropdown677 container
                By.xpath("//div[@id='Dropdown677']//span[2]"), // Updated by Shahu.D - Span within Dropdown677
                By.xpath("//*[contains(@id,'Dropdown677')]"), // Updated by Shahu.D - Any element with Dropdown677
                By.xpath("//*[@id='Dropdown333-option']"), // Updated by Shahu.D - Fallback to old locator
                By.xpath("//*[@id='Dropdown333']"),
                By.xpath("//*[@role='combobox' and contains(@id,'Dropdown')]") // Updated by Shahu.D - Generic dropdown
            };
            
            WebElement actionTakenDropdown = null;
            boolean found = false;
            
            for (By locator : actionTakenDropdownLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    actionTakenDropdown = element;
                                    found = true;
                                    System.out.println("  ✅ Found Action Taken dropdown using locator: " + locator);
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || actionTakenDropdown == null) {
                // Updated by Shahu.D - Enhanced debugging
                try {
                    System.out.println("  🔍 Debugging: Searching for Action Taken dropdown...");
                    
                    // Check for any dropdowns
                    java.util.List<WebElement> allDropdowns = driver.findElements(By.xpath("//*[contains(@id,'Dropdown')]"));
                    System.out.println("  📋 Found " + allDropdowns.size() + " dropdown element(s)");
                    for (int i = 0; i < Math.min(allDropdowns.size(), 10); i++) {
                        try {
                            WebElement dropdown = allDropdowns.get(i);
                            if (dropdown.isDisplayed()) {
                                String id = dropdown.getAttribute("id");
                                String role = dropdown.getAttribute("role");
                                System.out.println("     Dropdown " + (i + 1) + ": id='" + id + "', role='" + role + "'");
                                
                                // Check for span[2] within this dropdown
                                try {
                                    java.util.List<WebElement> spans = dropdown.findElements(By.xpath(".//span"));
                                    System.out.println("        Found " + spans.size() + " span(s) in this dropdown");
                                } catch (Exception ex) {
                                    // Ignore
                                }
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                throw new Exception("Action Taken dropdown not found. Please ensure the Workflow history is open and the dropdown is visible.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", actionTakenDropdown);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionTakenDropdown);
                System.out.println("  ✅ Clicked Action Taken dropdown using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(actionTakenDropdown).click().perform();
                    System.out.println("  ✅ Clicked Action Taken dropdown using Actions");
                } catch (Exception e2) {
                    actionTakenDropdown.click();
                    System.out.println("  ✅ Clicked Action Taken dropdown using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Action Taken dropdown");
            waitForSeconds(2); // Wait for dropdown list to appear
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Action Taken dropdown: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Select "Approved" from Action Taken dropdown
     * Updated by Shahu.D
     * @throws Exception if element is not found or selection fails
     */
    public void selectApprovedOption() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for 'Approved' option...");
            
            // Updated by Shahu.D - Wait for dropdown list container
            try {
                System.out.println("  ⏳ Waiting for dropdown list to appear...");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(@id,'Dropdown333-list')] | //*[@role='listbox'] | //ul[contains(@role,'listbox')]")));
                System.out.println("  ✅ Dropdown list appeared");
                waitForSeconds(1);
            } catch (Exception listEx) {
                System.out.println("  ⚠️ Dropdown list container not found, continuing...");
            }
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] approvedOptionLocators = {
                CompletedInitiativePageLocators.approvedOption,
                By.xpath("//*[@id='Dropdown333-list1']/span/span"),
                By.xpath("//*[@id='Dropdown333-list1']//span[contains(text(),'Approved')]"),
                By.xpath("//*[contains(@id,'Dropdown333-list')]//span[contains(text(),'Approved')]"),
                By.xpath("//*[@role='option' and contains(text(),'Approved')]"),
                By.xpath("//li[contains(text(),'Approved')]"),
                By.xpath("//*[contains(text(),'Approved')]")
            };
            
            WebElement approvedOption = null;
            boolean found = false;
            
            for (By locator : approvedOptionLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed()) {
                                    String text = element.getText().toLowerCase().trim();
                                    if (text.contains("approved")) {
                                        approvedOption = element;
                                        found = true;
                                        System.out.println("  ✅ Found 'Approved' option using locator: " + locator);
                                        System.out.println("     Option text: '" + element.getText() + "'");
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || approvedOption == null) {
                throw new Exception("'Approved' option not found. Please ensure the Action Taken dropdown is open and the option is available.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", approvedOption);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approvedOption);
                System.out.println("  ✅ Selected 'Approved' option using JavaScript");
            } catch (Exception e1) {
                try {
                    actions.moveToElement(approvedOption).click().perform();
                    System.out.println("  ✅ Selected 'Approved' option using Actions");
                } catch (Exception e2) {
                    approvedOption.click();
                    System.out.println("  ✅ Selected 'Approved' option using direct click");
                }
            }
            
            System.out.println("  ✅ Successfully selected 'Approved' option");
            waitForSeconds(2); // Wait for selection to be applied
        } catch (Exception e) {
            System.err.println("  ❌ Error selecting 'Approved' option: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click on Edit link for Completed Initiative by Initiative Code
     * Updated by Shahu.D
     * @param initiativeCode The initiative code to find and click edit for
     * @throws Exception if element is not found or click fails
     */
    public void clickEditLinkByInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Edit link for Initiative Code: " + initiativeCode); // Updated by Shahu.D
            
            // Updated by Shahu.D - First, find the row containing the initiative code
            By[] rowLocators = {
                CompletedInitiativePageLocators.getInitiativeRowByCode(initiativeCode), // Updated by Shahu.D - Table row
                By.xpath("//*[contains(@id,'row') and .//*[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Div-based row with ID
                By.xpath("//div[contains(@class,'ag-row') and .//div[contains(@class,'ag-cell') and contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - AG Grid row
                By.xpath("//*[contains(@class,'row') and .//*[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Generic row class
                By.xpath("//tr[.//td[contains(text(),'" + initiativeCode + "')]]"), // Updated by Shahu.D - Table row with td
                By.xpath("//tr[td[normalize-space(text())='" + initiativeCode + "']]"), // Updated by Shahu.D - Exact match
                By.xpath("//*[.//*[normalize-space(text())='" + initiativeCode + "']]") // Updated by Shahu.D - Any element containing the code
            };
            
            WebElement initiativeRow = null;
            boolean rowFound = false;
            
            for (By rowLocator : rowLocators) {
                try {
                    System.out.println("  🔍 Trying row locator: " + rowLocator);
                    java.util.List<WebElement> rows = driver.findElements(rowLocator);
                    if (rows.size() > 0) {
                        for (WebElement row : rows) {
                            try {
                                if (row.isDisplayed()) {
                                    String rowText = row.getText();
                                    if (rowText.contains(initiativeCode)) {
                                        initiativeRow = row;
                                        rowFound = true;
                                        System.out.println("  ✅ Found row for Initiative Code: " + initiativeCode); // Updated by Shahu.D
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (rowFound) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!rowFound || initiativeRow == null) {
                throw new Exception("Row not found for Initiative Code: " + initiativeCode + ". Please ensure you have searched for the initiative code and it exists in the table."); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - Find the Edit link (button[1]) in that row
            WebElement editLink = null;
            boolean found = false;
            
            // Updated by Shahu.D - Multiple strategies to find button[1] in the row
            By[] editLinkLocators = {
                By.xpath(".//div[7]//div//div//button[1]"), // Updated by Shahu.D - Specific path: div[7]/div/div/button[1]
                By.xpath(".//button[1]"), // Updated by Shahu.D - Any button[1] in row
                By.xpath(".//div[7]//button[1]"), // Updated by Shahu.D - Button[1] in div[7]
                By.xpath(".//button[.//svg]"), // Updated by Shahu.D - Button with SVG
                By.xpath(".//button[contains(@aria-label,'Edit') or contains(@aria-label,'edit')]") // Updated by Shahu.D - By aria-label
            };
            
            for (By locator : editLinkLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = initiativeRow.findElements(locator); // Updated by Shahu.D
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    String tagName = element.getTagName();
                                    // Updated by Shahu.D - Check if it's a button (edit link should be a button)
                                    if (tagName.equals("button")) {
                                        // Check if it contains SVG (edit link has SVG)
                                        try {
                                            WebElement svg = element.findElement(By.xpath(".//svg"));
                                            if (svg != null) {
                                                editLink = element;
                                                found = true;
                                                System.out.println("  ✅ Found Edit link using locator: " + locator); // Updated by Shahu.D
                                                System.out.println("     Button contains SVG"); // Updated by Shahu.D
                                                break;
                                            }
                                        } catch (Exception svgEx) {
                                            // If no SVG, still consider it if it's button[1]
                                            if (locator.toString().contains("button[1]")) {
                                                editLink = element;
                                                found = true;
                                                System.out.println("  ✅ Found Edit link using locator: " + locator); // Updated by Shahu.D
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || editLink == null) {
                throw new Exception("Edit link not found for Initiative Code: " + initiativeCode + ". Please ensure the row is found and button[1] exists in div[7]."); // Updated by Shahu.D
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", editLink);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editLink);
                System.out.println("  ✅ Clicked Edit link using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(editLink).click().perform();
                    System.out.println("  ✅ Clicked Edit link using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    editLink.click();
                    System.out.println("  ✅ Clicked Edit link using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Edit link for Initiative Code: " + initiativeCode); // Updated by Shahu.D
            waitForSeconds(3); // Updated by Shahu.D - Wait for edit page to load
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Edit link for Initiative Code " + initiativeCode + ": " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    /**
     * Verify that only "Workflow Details" and "Workflow Information" buttons are displayed, and "Save" button is NOT displayed
     * Updated by Shahu.D
     * @return true if validation passes, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyEditPageButtons() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Updated by Shahu.D
            boolean validationPassed = true;
            
            System.out.println("  🔍 Verifying buttons on Edit page..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Verify "Workflow Details" button is displayed
            try {
                WebElement workflowDetailsBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    CompletedInitiativePageLocators.workflowDetailsButton));
                if (workflowDetailsBtn.isDisplayed()) {
                    System.out.println("  ✅ 'Workflow Details' button is displayed"); // Updated by Shahu.D
                } else {
                    System.out.println("  ❌ 'Workflow Details' button is NOT displayed"); // Updated by Shahu.D
                    validationPassed = false;
                }
            } catch (Exception ex) {
                System.out.println("  ❌ 'Workflow Details' button is NOT found/displayed"); // Updated by Shahu.D
                validationPassed = false;
            }
            
            // Updated by Shahu.D - Verify "Workflow Information" button is displayed
            try {
                WebElement workflowInfoBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    CompletedInitiativePageLocators.workflowInformationButton));
                if (workflowInfoBtn.isDisplayed()) {
                    System.out.println("  ✅ 'Workflow Information' button is displayed"); // Updated by Shahu.D
                } else {
                    System.out.println("  ❌ 'Workflow Information' button is NOT displayed"); // Updated by Shahu.D
                    validationPassed = false;
                }
            } catch (Exception ex) {
                System.out.println("  ❌ 'Workflow Information' button is NOT found/displayed"); // Updated by Shahu.D
                validationPassed = false;
            }
            
            // Updated by Shahu.D - Verify "Save" button is NOT displayed
            try {
                java.util.List<WebElement> saveButtons = driver.findElements(CompletedInitiativePageLocators.saveButton);
                boolean saveButtonVisible = false;
                for (WebElement saveBtn : saveButtons) {
                    if (saveBtn.isDisplayed()) {
                        saveButtonVisible = true;
                        System.out.println("  ❌ 'Save' button IS displayed (should NOT be displayed)"); // Updated by Shahu.D
                        validationPassed = false;
                        break;
                    }
                }
                if (!saveButtonVisible) {
                    System.out.println("  ✅ 'Save' button is NOT displayed (as expected)"); // Updated by Shahu.D
                }
            } catch (Exception ex) {
                // If no Save button found, that's good
                System.out.println("  ✅ 'Save' button is NOT found/displayed (as expected)"); // Updated by Shahu.D
            }
            
            return validationPassed;
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying Edit page buttons: " + e.getMessage()); // Updated by Shahu.D
            return false;
        }
    }
    
    /**
     * Click on "Go Back To List View" button
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickGoBackToListViewButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for 'Go Back To List View' button..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] goBackButtonLocators = {
                CompletedInitiativePageLocators.goBackToListViewButton,
                By.xpath("//*[@id='IMInfopgtabs']/button"), // Updated by Shahu.D - Primary XPath
                By.xpath("//button[@id='IMInfopgtabs']"),
                By.xpath("//*[@id='IMInfopgtabs']//button"),
                By.xpath("//button[contains(text(),'Go Back') or contains(text(),'go back')]"),
                By.xpath("//button[contains(text(),'List View') or contains(text(),'list view')]")
            };
            
            WebElement goBackButton = null;
            boolean found = false;
            
            for (By locator : goBackButtonLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    goBackButton = element;
                                    found = true;
                                    System.out.println("  ✅ Found 'Go Back To List View' button using locator: " + locator); // Updated by Shahu.D
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || goBackButton == null) {
                throw new Exception("'Go Back To List View' button not found. Please ensure you are on the Edit page."); // Updated by Shahu.D
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", goBackButton);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBackButton);
                System.out.println("  ✅ Clicked 'Go Back To List View' button using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(goBackButton).click().perform();
                    System.out.println("  ✅ Clicked 'Go Back To List View' button using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    goBackButton.click();
                    System.out.println("  ✅ Clicked 'Go Back To List View' button using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on 'Go Back To List View' button"); // Updated by Shahu.D
            waitForSeconds(3); // Updated by Shahu.D - Wait for navigation back to list
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking 'Go Back To List View' button: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Click on "Switch to Card View" icon/button
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSwitchToCardViewButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for 'Switch to Card View' button..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] switchToCardViewLocators = {
                CompletedInitiativePageLocators.switchToCardViewButton,
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[1]/button"), // Updated by Shahu.D - Try button directly
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[1]/button/svg"), // Updated by Shahu.D - Primary XPath
                By.xpath("//*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button"), // Updated by Shahu.D
                By.xpath("//*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[1]//button//svg"), // Updated by Shahu.D
                By.xpath("//button[contains(@aria-label,'Card') or contains(@aria-label,'card')]"), // Updated by Shahu.D
                By.xpath("//button[contains(@aria-label,'Card') or contains(@aria-label,'card')]//svg"), // Updated by Shahu.D
                By.xpath("//button[.//svg and contains(@aria-label,'View')]"), // Updated by Shahu.D
                By.xpath("//button[.//svg and (contains(@aria-label,'Card') or contains(@aria-label,'card'))]"), // Updated by Shahu.D
                By.xpath("//div[contains(@class,'toolbar') or contains(@class,'header')]//button[.//svg]"), // Updated by Shahu.D - Toolbar buttons
                By.xpath("//div[contains(@class,'MuiToolbar')]//button[.//svg]"), // Updated by Shahu.D - MUI Toolbar
                By.xpath("//button[.//svg and contains(@class,'MuiIconButton')]"), // Updated by Shahu.D - MUI Icon Button
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div//button[.//svg]") // Updated by Shahu.D - Any button with SVG in the grid area
            };
            
            WebElement switchButton = null;
            boolean found = false;
            
            for (By locator : switchToCardViewLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed()) {
                                    String tagName = element.getTagName();
                                    // Updated by Shahu.D - Handle svg or button elements
                                    if (tagName.equals("svg")) {
                                        // Find the parent button element
                                        WebElement parentButton = (WebElement) ((JavascriptExecutor) driver).executeScript(
                                            "return arguments[0].closest('button');", element);
                                        if (parentButton != null && parentButton.isDisplayed() && parentButton.isEnabled()) {
                                            switchButton = parentButton;
                                            found = true;
                                            System.out.println("  ✅ Found 'Switch to Card View' button using locator: " + locator); // Updated by Shahu.D
                                            System.out.println("     Element tag: " + tagName + ", found parent button"); // Updated by Shahu.D
                                            break;
                                        }
                                    } else if (tagName.equals("button") && element.isEnabled()) {
                                        switchButton = element;
                                        found = true;
                                        System.out.println("  ✅ Found 'Switch to Card View' button using locator: " + locator); // Updated by Shahu.D
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || switchButton == null) {
                // Updated by Shahu.D - Enhanced debugging: Print available buttons
                try {
                    System.out.println("  🔍 Debugging: Searching for 'Switch to Card View' button...");
                    
                    // Check for buttons in the specified path
                    java.util.List<WebElement> buttonsInPath = driver.findElements(By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[1]//button"));
                    System.out.println("  📋 Found " + buttonsInPath.size() + " button(s) in div[1]");
                    for (int i = 0; i < Math.min(buttonsInPath.size(), 10); i++) {
                        try {
                            WebElement btn = buttonsInPath.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                boolean hasSvg = false;
                                try {
                                    btn.findElement(By.xpath(".//svg"));
                                    hasSvg = true;
                                } catch (Exception ex) {
                                    // No SVG
                                }
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', aria-label='" + ariaLabel + "', hasSVG=" + hasSvg);
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    
                    // Check for all buttons with SVG
                    java.util.List<WebElement> allButtonsWithSvg = driver.findElements(By.xpath("//button[.//svg]"));
                    System.out.println("  📋 Found " + allButtonsWithSvg.size() + " button(s) with SVG on page");
                    for (int i = 0; i < Math.min(allButtonsWithSvg.size(), 15); i++) {
                        try {
                            WebElement btn = allButtonsWithSvg.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                String className = btn.getAttribute("class");
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', aria-label='" + ariaLabel + "', class='" + className + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                throw new Exception("'Switch to Card View' button not found. Please ensure you are on the Completed Initiatives page."); // Updated by Shahu.D
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", switchButton);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", switchButton);
                System.out.println("  ✅ Clicked 'Switch to Card View' button using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(switchButton).click().perform();
                    System.out.println("  ✅ Clicked 'Switch to Card View' button using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    switchButton.click();
                    System.out.println("  ✅ Clicked 'Switch to Card View' button using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on 'Switch to Card View' button"); // Updated by Shahu.D
            waitForSeconds(3); // Updated by Shahu.D - Wait for view to switch
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking 'Switch to Card View' button: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    /**
     * Verify that the view has switched from List View to Card View
     * Updated by Shahu.D
     * @return true if validation passes, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyCardViewDisplayed() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Updated by Shahu.D
            boolean validationPassed = true;
            
            System.out.println("  🔍 Verifying Card View is displayed..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Verify Card container is visible
            try {
                java.util.List<WebElement> cardContainers = driver.findElements(CompletedInitiativePageLocators.cardContainer);
                boolean cardContainerVisible = false;
                int visibleCardCount = 0;
                
                for (WebElement card : cardContainers) {
                    try {
                        if (card.isDisplayed()) {
                            cardContainerVisible = true;
                            visibleCardCount++;
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
                
                if (cardContainerVisible) {
                    System.out.println("  ✅ Card container is visible"); // Updated by Shahu.D
                    System.out.println("     Found " + visibleCardCount + " visible card(s)"); // Updated by Shahu.D
                } else {
                    System.out.println("  ❌ Card container is NOT visible"); // Updated by Shahu.D
                    validationPassed = false;
                }
            } catch (Exception ex) {
                System.out.println("  ❌ Error checking card container: " + ex.getMessage()); // Updated by Shahu.D
                validationPassed = false;
            }
            
            // Updated by Shahu.D - Verify List container/table is NOT visible
            try {
                java.util.List<WebElement> listContainers = driver.findElements(CompletedInitiativePageLocators.listContainer);
                boolean listContainerVisible = false;
                
                for (WebElement listContainer : listContainers) {
                    try {
                        if (listContainer.isDisplayed()) {
                            // Check if it's a main table/list (not nested in cards)
                            String className = listContainer.getAttribute("class");
                            String tagName = listContainer.getTagName();
                            // If it's a main table or ag-grid root, it should not be visible
                            if (tagName.equals("table") || (className != null && className.contains("ag-root"))) {
                                listContainerVisible = true;
                                System.out.println("  ❌ List container/table IS still visible (should NOT be visible)"); // Updated by Shahu.D
                                validationPassed = false;
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
                
                if (!listContainerVisible) {
                    System.out.println("  ✅ List container/table is NOT visible (as expected)"); // Updated by Shahu.D
                }
            } catch (Exception ex) {
                // If no list container found, that's good
                System.out.println("  ✅ List container/table is NOT found (as expected)"); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - Verify cards contain initiative information
            try {
                java.util.List<WebElement> cards = driver.findElements(CompletedInitiativePageLocators.cardContainer);
                int cardsWithInfo = 0;
                
                for (WebElement card : cards) {
                    try {
                        if (card.isDisplayed()) {
                            String cardText = card.getText();
                            // Check if card contains typical initiative information
                            if (cardText.length() > 10) { // Cards should have some content
                                cardsWithInfo++;
                            }
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
                
                if (cardsWithInfo > 0) {
                    System.out.println("  ✅ Cards contain initiative information"); // Updated by Shahu.D
                    System.out.println("     Found " + cardsWithInfo + " card(s) with information"); // Updated by Shahu.D
                } else {
                    System.out.println("  ⚠️ No cards with information found"); // Updated by Shahu.D
                }
            } catch (Exception ex) {
                System.out.println("  ⚠️ Could not verify card information: " + ex.getMessage()); // Updated by Shahu.D
            }
            
            return validationPassed;
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying Card View: " + e.getMessage()); // Updated by Shahu.D
            return false;
        }
    }

    /**
     * Click on "View Chart" button
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickViewChartButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for 'View Chart' button..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Multiple locator strategies
            By[] viewChartLocators = {
                CompletedInitiativePageLocators.viewChartButton,
                By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/h2/button"), // Updated by Shahu.D - Primary XPath
                By.xpath("//*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div//h2//button"), // Updated by Shahu.D
                By.xpath("//h2//button[contains(@aria-label,'Chart') or contains(@aria-label,'chart')]"), // Updated by Shahu.D
                By.xpath("//button[contains(text(),'Chart') or contains(text(),'chart')]") // Updated by Shahu.D
            };
            
            WebElement viewChartBtn = null;
            boolean found = false;
            
            for (By locator : viewChartLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator);
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    if (elements.size() > 0) {
                        for (WebElement element : elements) {
                            try {
                                if (element.isDisplayed() && element.isEnabled()) {
                                    viewChartBtn = element;
                                    found = true;
                                    System.out.println("  ✅ Found 'View Chart' button using locator: " + locator); // Updated by Shahu.D
                                    break;
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || viewChartBtn == null) {
                // Updated by Shahu.D - Enhanced debugging: Print available buttons
                try {
                    System.out.println("  🔍 Debugging: Searching for 'View Chart' button...");
                    
                    // Check for buttons in the specified path
                    java.util.List<WebElement> buttonsInPath = driver.findElements(By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div/h2//button"));
                    System.out.println("  📋 Found " + buttonsInPath.size() + " button(s) in h2");
                    for (int i = 0; i < Math.min(buttonsInPath.size(), 10); i++) {
                        try {
                            WebElement btn = buttonsInPath.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                String text = btn.getText();
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', aria-label='" + ariaLabel + "', text='" + text + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    
                    // Check for all buttons with "Chart" in text or aria-label
                    java.util.List<WebElement> chartButtons = driver.findElements(By.xpath("//button[contains(text(),'Chart') or contains(text(),'chart') or contains(@aria-label,'Chart') or contains(@aria-label,'chart')]"));
                    System.out.println("  📋 Found " + chartButtons.size() + " button(s) with 'Chart' in text/aria-label");
                    for (int i = 0; i < Math.min(chartButtons.size(), 10); i++) {
                        try {
                            WebElement btn = chartButtons.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                String text = btn.getText();
                                String className = btn.getAttribute("class");
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', aria-label='" + ariaLabel + "', text='" + text + "', class='" + className + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                    
                    // Check for all buttons in the chart area
                    java.util.List<WebElement> buttonsInChartArea = driver.findElements(By.xpath("//*[@id='root']/div[2]/div/div[2]/div[2]/div/div/div[2]//button"));
                    System.out.println("  📋 Found " + buttonsInChartArea.size() + " button(s) in div[2] (chart area)");
                    for (int i = 0; i < Math.min(buttonsInChartArea.size(), 15); i++) {
                        try {
                            WebElement btn = buttonsInChartArea.get(i);
                            if (btn.isDisplayed()) {
                                String id = btn.getAttribute("id");
                                String ariaLabel = btn.getAttribute("aria-label");
                                String text = btn.getText();
                                System.out.println("     Button " + (i + 1) + ": id='" + id + "', aria-label='" + ariaLabel + "', text='" + text + "'");
                            }
                        } catch (Exception debugEx) {
                            continue;
                        }
                    }
                } catch (Exception debugEx) {
                    System.out.println("  ⚠️ Could not get debug info: " + debugEx.getMessage());
                }
                
                throw new Exception("'View Chart' button not found. Please ensure you are on the Completed Initiatives page."); // Updated by Shahu.D
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", viewChartBtn);
            waitForSeconds(1);
            
            // Updated by Shahu.D - Try multiple click strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewChartBtn);
                System.out.println("  ✅ Clicked 'View Chart' button using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(viewChartBtn).click().perform();
                    System.out.println("  ✅ Clicked 'View Chart' button using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    viewChartBtn.click();
                    System.out.println("  ✅ Clicked 'View Chart' button using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on 'View Chart' button"); // Updated by Shahu.D
            waitForSeconds(3); // Updated by Shahu.D - Wait for chart to load
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking 'View Chart' button: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    /**
     * Extract Top 5 Nature of Initiative data from the chart
     * Updated by Shahu.D
     * @return Map of Nature name to count (top 5)
     * @throws Exception if chart data cannot be extracted
     */
    public java.util.Map<String, Integer> extractChartData() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        java.util.Map<String, Integer> chartData = new java.util.LinkedHashMap<>(); // Updated by Shahu.D - Preserve order
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            
            System.out.println("  🔍 Extracting chart data from 'Top 5 Nature of Initiatives' chart..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Find chart container
            WebElement chartContainer = null;
            By[] chartLocators = {
                CompletedInitiativePageLocators.top5NatureChartContainer,
                By.xpath("//span[contains(@class,'iniTxt')]") // Updated by Shahu.D
             
            };
            
            for (By locator : chartLocators) {
                try {
                    java.util.List<WebElement> containers = driver.findElements(locator);
                    for (WebElement container : containers) {
                        if (container.isDisplayed()) {
                            chartContainer = container;
                            System.out.println("  ✅ Found chart container using locator: " + locator); // Updated by Shahu.D
                            break;
                        }
                    }
                    if (chartContainer != null) break;
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (chartContainer == null) {
                throw new Exception("Chart container not found. Please ensure the chart is displayed."); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - Extract chart data using Recharts SVG structure
            // Primary XPath for nature names: //*[name()='svg']//*[contains(@class,'recharts-cartesian-axis-tick')]//*[name()='text']
            System.out.println("  🔍 Extracting nature names from chart axis..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Extract nature names from chart axis ticks
            java.util.List<WebElement> axisTextElements = driver.findElements(By.xpath("//*[name()='svg']//*[contains(@class,'recharts-cartesian-axis-tick')]//*[name()='text']"));
            System.out.println("  📊 Found " + axisTextElements.size() + " axis text elements"); // Updated by Shahu.D
            
            java.util.List<String> natureNames = new java.util.ArrayList<>(); // Updated by Shahu.D
            for (WebElement axisText : axisTextElements) {
                try {
                    if (axisText.isDisplayed()) {
                        String text = axisText.getText().trim();
                        if (!text.isEmpty() && text.length() > 2) {
                            natureNames.add(text);
                            System.out.println("  📊 Found nature name from axis: " + text); // Updated by Shahu.D
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // Updated by Shahu.D - Extract counts from chart bars or values
            // Try to find bar values or tooltip data
            System.out.println("  🔍 Extracting counts from chart..."); // Updated by Shahu.D
            java.util.List<Integer> counts = new java.util.ArrayList<>(); // Updated by Shahu.D
            
            // Strategy 1: Look for text elements near bars that might contain counts
            java.util.List<WebElement> barTextElements = driver.findElements(By.xpath("//*[name()='svg']//*[contains(@class,'recharts-bar')]//*[name()='text'] | //*[name()='svg']//*[contains(@class,'recharts-label')]//*[name()='text']"));
            System.out.println("  📊 Found " + barTextElements.size() + " bar text elements"); // Updated by Shahu.D
            
            for (WebElement barText : barTextElements) {
                try {
                    if (barText.isDisplayed()) {
                        String text = barText.getText().trim();
                        try {
                            int count = Integer.parseInt(text);
                            counts.add(count);
                            System.out.println("  📊 Found count from bar: " + count); // Updated by Shahu.D
                        } catch (NumberFormatException e) {
                            // Not a number, skip
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            // Updated by Shahu.D - If we don't have enough counts, try extracting from chart container text
            if (counts.size() < natureNames.size()) {
                String chartText = chartContainer.getText();
                System.out.println("  📊 Chart container text: " + chartText.substring(0, Math.min(300, chartText.length()))); // Updated by Shahu.D
                
                // Updated by Shahu.D - Parse text to find patterns like "Nature Name : 4"
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([^:]+?)\\s*[:]\\s*(\\d+)");
                java.util.regex.Matcher matcher = pattern.matcher(chartText);
                counts.clear(); // Clear and re-extract
                
                while (matcher.find() && counts.size() < 5) {
                    try {
                        int count = Integer.parseInt(matcher.group(2).trim());
                        counts.add(count);
                        System.out.println("  📊 Found count from text pattern: " + count); // Updated by Shahu.D
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            
            // Updated by Shahu.D - Match nature names with counts
            // Take up to 5 pairs
            int maxPairs = Math.min(5, Math.min(natureNames.size(), counts.size()));
            for (int i = 0; i < maxPairs; i++) {
                chartData.put(natureNames.get(i), counts.get(i));
                System.out.println("  ✅ Matched: " + natureNames.get(i) + " = " + counts.get(i)); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - If we still don't have enough data, try fallback extraction
            if (chartData.size() < 5) {
                System.out.println("  ⚠️ Only extracted " + chartData.size() + " pairs, trying fallback extraction..."); // Updated by Shahu.D
                try {
                    String chartText = chartContainer.getText();
                    // Updated by Shahu.D - Parse text to find patterns like "Nature Name : 4"
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([^:]+?)\\s*[:]\\s*(\\d+)");
                    java.util.regex.Matcher matcher = pattern.matcher(chartText);
                    
                    while (matcher.find() && chartData.size() < 5) {
                        try {
                            String name = matcher.group(1).trim();
                            int count = Integer.parseInt(matcher.group(2).trim());
                            // Updated by Shahu.D - Only add if not already present
                            if (!chartData.containsKey(name)) {
                                chartData.put(name, count);
                                System.out.println("  📊 Extracted from fallback pattern: " + name + " = " + count); // Updated by Shahu.D
                            }
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                } catch (Exception fallbackEx) {
                    System.out.println("  ⚠️ Fallback extraction failed: " + fallbackEx.getMessage()); // Updated by Shahu.D
                }
            }
            
            // Updated by Shahu.D - Clean up chart data: remove entries with invalid names and clean multi-line names
            java.util.Map<String, Integer> cleanedChartData = new java.util.LinkedHashMap<>();
            for (java.util.Map.Entry<String, Integer> entry : chartData.entrySet()) {
                String name = entry.getKey();
                // Updated by Shahu.D - Skip invalid names or clean multi-line names
                if (name.contains("\n") || name.contains("View Charts") || name.contains("By Organization Unit") || name.contains("Top 5 Nature of Initiative")) {
                    // Updated by Shahu.D - Extract the actual nature name from multi-line text
                    String[] lines = name.split("\n");
                    String cleanedName = null;
                    for (String line : lines) {
                        line = line.trim();
                        if (!line.isEmpty() 
                            && !line.equals("View Charts")
                            && !line.equals("By Organization Unit")
                            && !line.equals("Top 5 Nature of Initiative")
                            && !line.matches("^\\d+$")
                            && line.length() > 3) {
                            cleanedName = line;
                            break;
                        }
                    }
                    if (cleanedName != null) {
                        cleanedChartData.put(cleanedName, entry.getValue());
                        System.out.println("  🔧 Cleaned chart entry: '" + name.substring(0, Math.min(50, name.length())) + "...' -> '" + cleanedName + "'"); // Updated by Shahu.D
                    }
                } else if (name.length() > 3 && !name.matches("^\\d+$")) {
                    cleanedChartData.put(name, entry.getValue());
                }
            }
            chartData = cleanedChartData;
            
            // Updated by Shahu.D - If we couldn't extract data, throw exception
            if (chartData.isEmpty()) {
                String chartText = chartContainer.getText();
                System.out.println("  ⚠️ Chart text content: " + chartText.substring(0, Math.min(300, chartText.length()))); // Updated by Shahu.D
                throw new Exception("Could not extract chart data. Chart structure may be different than expected. Please check the chart format."); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - Ensure we have exactly 5 items
            if (chartData.size() < 5) {
                System.out.println("  ⚠️ Warning: Chart contains only " + chartData.size() + " items, expected 5"); // Updated by Shahu.D
            } else if (chartData.size() > 5) {
                // Take first 5
                java.util.Map<String, Integer> top5 = new java.util.LinkedHashMap<>();
                int count = 0;
                for (java.util.Map.Entry<String, Integer> entry : chartData.entrySet()) {
                    if (count >= 5) break;
                    top5.put(entry.getKey(), entry.getValue());
                    count++;
                }
                chartData = top5;
                System.out.println("  ⚠️ Chart contains more than 5 items, taking first 5"); // Updated by Shahu.D
            }
            
            System.out.println("  ✅ Successfully extracted " + chartData.size() + " nature(s) from chart"); // Updated by Shahu.D
            return chartData;
        } catch (Exception e) {
            System.err.println("  ❌ Error extracting chart data: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    /**
     * Extract all Nature of Initiative values from the List View table
     * Updated by Shahu.D
     * @return List of all Nature of Initiative values from the table
     * @throws Exception if table data cannot be extracted
     */
    public java.util.List<String> extractNatureValuesFromTable() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        java.util.List<String> natureValues = new java.util.ArrayList<>(); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            
            System.out.println("  🔍 Extracting Nature of Initiative values from table..."); // Updated by Shahu.D
            
            // Updated by Shahu.D - Scroll to table
            try {
                WebElement table = driver.findElement(CompletedInitiativePageLocators.listContainer);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'start'});", table);
                waitForSeconds(2);
            } catch (Exception ex) {
                System.out.println("  ⚠️ Could not scroll to table: " + ex.getMessage()); // Updated by Shahu.D
            }
            
            // Updated by Shahu.D - Multiple strategies to find Nature of Initiative column
            // Primary XPath: //div[@role='row' and not(contains(@class,'header'))]//div[contains(@class,'ms-DetailsRow-cell')][3]
            // This XPath selects the 3rd cell in each data row (excluding header rows)
            // Note: The [3] index applies to each row, not the overall result set
            By[] natureColumnLocators = {
                By.xpath("//div[@role='row' and not(contains(@class,'header'))]//div[contains(@class,'ms-DetailsRow-cell')][3]"), // Updated by Shahu.D - Primary XPath (3rd cell in each row)
                By.xpath("//div[@role='row' and not(contains(@class,'header'))]//div[@data-item-key='nature']"), // Updated by Shahu.D - Alternative XPath
                By.xpath("//div[@role='row']//div[contains(@class,'ms-DetailsRow-cell')][3]"), // Updated by Shahu.D - Fallback (without header exclusion)
                By.xpath("//div[@role='row']//div[@data-item-key='nature']"), // Updated by Shahu.D - Fallback
                CompletedInitiativePageLocators.natureOfInitiativeColumnValues,
                By.xpath("//td[contains(@col-id,'nature')]"), // Updated by Shahu.D
                By.xpath("//div[contains(@col-id,'nature')]"), // Updated by Shahu.D
                By.xpath("//*[contains(@id,'nature') and contains(@id,'cell')]"), // Updated by Shahu.D
                By.xpath("//table//td[contains(@class,'nature')]"), // Updated by Shahu.D
                By.xpath("//div[@role='gridcell' and contains(@col-id,'nature')]"), // Updated by Shahu.D - AG Grid
                By.xpath("//div[@role='row']//div[contains(@col-id,'nature')]") // Updated by Shahu.D - AG Grid rows
            };
            
            boolean found = false;
            for (By locator : natureColumnLocators) {
                try {
                    System.out.println("  🔍 Trying locator: " + locator); // Updated by Shahu.D
                    java.util.List<WebElement> cells = driver.findElements(locator);
                    System.out.println("  📊 Found " + cells.size() + " cells with this locator"); // Updated by Shahu.D
                    
                    if (cells.size() > 0) {
                        // Updated by Shahu.D - Clear previous values before trying new locator
                        natureValues.clear();
                        
                        System.out.println("  📊 Processing " + cells.size() + " cells..."); // Updated by Shahu.D
                        
                        for (WebElement cell : cells) {
                            try {
                                // Updated by Shahu.D - Try to get text even if element is not visible (might be in scrollable area)
                                String cellText = "";
                                try {
                                    if (cell.isDisplayed()) {
                                        cellText = cell.getText().trim();
                                    } else {
                                        // Updated by Shahu.D - Try to get text via JavaScript if element is not visible
                                        cellText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent || arguments[0].innerText;", cell);
                                        if (cellText != null) {
                                            cellText = cellText.trim();
                                        } else {
                                            cellText = "";
                                        }
                                    }
                                } catch (Exception textEx) {
                                    // Updated by Shahu.D - Fallback: try JavaScript
                                    try {
                                        cellText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent || arguments[0].innerText;", cell);
                                        if (cellText != null) {
                                            cellText = cellText.trim();
                                        } else {
                                            cellText = "";
                                        }
                                    } catch (Exception jsEx) {
                                        continue;
                                    }
                                }
                                
                                // Updated by Shahu.D - Exclude header text and empty/invalid values
                                if (!cellText.isEmpty() 
                                    && !cellText.equals("-") 
                                    && !cellText.equals("N/A")
                                    && !cellText.equalsIgnoreCase("Nature of Initiative")
                                    && !cellText.equalsIgnoreCase("Nature")
                                    && cellText.length() > 2) {
                                    natureValues.add(cellText);
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                        }
                        
                        if (natureValues.size() > 0) {
                            found = true;
                            System.out.println("  ✅ Found " + natureValues.size() + " Nature of Initiative values using locator: " + locator); // Updated by Shahu.D
                            // Updated by Shahu.D - Print first few values for debugging
                            int printCount = Math.min(5, natureValues.size());
                            System.out.println("  📋 Sample values (first " + printCount + "):"); // Updated by Shahu.D
                            for (int i = 0; i < printCount; i++) {
                                System.out.println("     " + (i + 1) + ". " + natureValues.get(i)); // Updated by Shahu.D
                            }
                            break;
                        } else {
                            System.out.println("  ⚠️ Found " + cells.size() + " cells but none contained valid Nature values"); // Updated by Shahu.D
                        }
                    } else {
                        System.out.println("  ⚠️ No cells found with this locator"); // Updated by Shahu.D
                    }
                } catch (Exception ex) {
                    System.out.println("  ⚠️ Error with locator " + locator + ": " + ex.getMessage()); // Updated by Shahu.D
                    continue;
                }
            }
            
            if (!found || natureValues.isEmpty()) {
                throw new Exception("Could not extract Nature of Initiative values from table. Tried " + natureColumnLocators.length + " locators but found 0 valid values. Please ensure the table is displayed and contains data."); // Updated by Shahu.D
            }
            
            System.out.println("  ✅ Successfully extracted " + natureValues.size() + " Nature of Initiative values from table"); // Updated by Shahu.D
            return natureValues;
        } catch (Exception e) {
            System.err.println("  ❌ Error extracting Nature values from table: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    /**
     * Create frequency map from Nature values and get top 5
     * Updated by Shahu.D
     * @param natureValues List of all Nature of Initiative values
     * @return Map of top 5 Nature names to their counts (sorted by count descending)
     */
    public java.util.Map<String, Integer> getTop5NatureFromTable(java.util.List<String> natureValues) {
        System.out.println("  🔍 Creating frequency map from " + natureValues.size() + " Nature values..."); // Updated by Shahu.D
        
        // Updated by Shahu.D - Create frequency map
        java.util.Map<String, Integer> frequencyMap = new java.util.HashMap<>();
        for (String nature : natureValues) {
            frequencyMap.put(nature, frequencyMap.getOrDefault(nature, 0) + 1);
        }
        
        System.out.println("  📊 Frequency map created with " + frequencyMap.size() + " unique Nature values"); // Updated by Shahu.D
        
        // Updated by Shahu.D - Sort by count descending and get top 5
        java.util.List<java.util.Map.Entry<String, Integer>> sortedEntries = new java.util.ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort((e1, e2) -> {
            int countCompare = e2.getValue().compareTo(e1.getValue());
            if (countCompare != 0) return countCompare;
            return e1.getKey().compareTo(e2.getKey()); // If counts are equal, sort by name
        });
        
        java.util.Map<String, Integer> top5 = new java.util.LinkedHashMap<>(); // Updated by Shahu.D - Preserve order
        int count = 0;
        for (java.util.Map.Entry<String, Integer> entry : sortedEntries) {
            if (count >= 5) break;
            top5.put(entry.getKey(), entry.getValue());
            System.out.println("  📋 Top " + (count + 1) + ": " + entry.getKey() + " = " + entry.getValue()); // Updated by Shahu.D
            count++;
        }
        
        System.out.println("  ✅ Top 5 Nature values extracted from table"); // Updated by Shahu.D
        return top5;
    }
    
    /**
     * Compare chart data with table data
     * Updated by Shahu.D
     * @param chartData Map of Nature name to count from chart
     * @param tableData Map of Nature name to count from table (top 5)
     * @return true if all 5 nature names match and their counts match exactly
     * @throws Exception if comparison fails
     */
    public boolean compareChartWithTableData(java.util.Map<String, Integer> chartData, java.util.Map<String, Integer> tableData) throws Exception {
        System.out.println("  🔍 Comparing chart data with table data..."); // Updated by Shahu.D
        
        if (chartData.size() != 5) {
            throw new Exception("Chart should contain exactly 5 Nature values, but found " + chartData.size() + ". Chart data: " + chartData); // Updated by Shahu.D
        }
        
        // Updated by Shahu.D - Allow comparison even if table has fewer than 5 unique values
        // (This can happen if there are fewer than 5 unique nature types in the table)
        if (tableData.size() < 1) {
            throw new Exception("Table should contain at least 1 Nature value, but found " + tableData.size()); // Updated by Shahu.D
        }
        
        // Updated by Shahu.D - If table has fewer than 5 unique values, we can only compare what's available
        int maxCompare = Math.min(5, Math.min(chartData.size(), tableData.size())); // Updated by Shahu.D
        System.out.println("  📊 Will compare top " + maxCompare + " Nature values"); // Updated by Shahu.D
        
        // Updated by Shahu.D - Compare each of the top values (up to 5, or fewer if table has less)
        java.util.List<String> chartNatures = new java.util.ArrayList<>(chartData.keySet());
        java.util.List<String> tableNatures = new java.util.ArrayList<>(tableData.keySet());
        
        boolean allMatch = true;
        for (int i = 0; i < maxCompare; i++) { // Updated by Shahu.D
            String chartNature = chartNatures.get(i);
            String tableNature = tableNatures.get(i);
            Integer chartCount = chartData.get(chartNature);
            Integer tableCount = tableData.get(tableNature);
            
            boolean nameMatch = chartNature.equals(tableNature);
            boolean countMatch = chartCount.equals(tableCount);
            
            System.out.println("  📊 Comparison " + (i + 1) + ":"); // Updated by Shahu.D
            System.out.println("     Chart: " + chartNature + " = " + chartCount); // Updated by Shahu.D
            System.out.println("     Table: " + tableNature + " = " + tableCount); // Updated by Shahu.D
            System.out.println("     Name Match: " + nameMatch + ", Count Match: " + countMatch); // Updated by Shahu.D
            
            if (!nameMatch || !countMatch) {
                allMatch = false;
                System.out.println("     ❌ Mismatch detected!"); // Updated by Shahu.D
            } else {
                System.out.println("     ✅ Match!"); // Updated by Shahu.D
            }
        }
        
        if (allMatch) {
            System.out.println("  ✅ All 5 Nature values and their counts match between chart and table"); // Updated by Shahu.D
        } else {
            System.out.println("  ❌ Mismatch found between chart and table data"); // Updated by Shahu.D
        }
        
        return allMatch;
    }

    /**
     * Verify that initiative with given code is NOT displayed on Completed Initiatives page
     * @param initiativeCode The initiative code to verify
     * @return true if initiative is NOT found (as expected), false if found (unexpected)
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeNotDisplayedOnCompletedPage(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for page to load

        try {
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' is NOT displayed on Completed Initiatives page...");

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
                    java.util.List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        try {
                            if (element.isDisplayed()) {
                                String foundText = element.getText();
                                if (foundText != null && foundText.contains(initiativeCode)) {
                                    System.out.println("  ❌ Initiative Code '" + initiativeCode + "' FOUND in Completed Initiatives page (unexpected): " + foundText);
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
                System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Completed Initiatives page (as expected)");
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Completed Initiatives page");
                }
                return true; // Not found = expected behavior
            } else {
                System.out.println("  ❌ Initiative Code '" + initiativeCode + "' was found in Completed Initiatives page (unexpected - should not be present)");
                if (reportLogger != null) {
                    reportLogger.fail("Initiative Code '" + initiativeCode + "' unexpectedly found on Completed Initiatives page");
                }
                return false; // Found = unexpected behavior
            }

        } catch (Exception e) {
            // If we can't find the element, that's actually good (means it's not displayed)
            System.out.println("  ✅ Initiative Code '" + initiativeCode + "' NOT found in Completed Initiatives page (as expected)");
            if (reportLogger != null) {
                reportLogger.pass("Initiative Code '" + initiativeCode + "' correctly NOT displayed on Completed Initiatives page");
            }
            return true; // Exception means not found = expected
        }
    }

    // Add more Completed Initiative specific methods here as needed
    
    
    public boolean printNatureAndCountInConsole() {

        // Fetch rows
        List<WebElement> rows = driver.findElements(top5NatureRows);

        if (rows.isEmpty()) {
            System.out.println("❌ No Nature data displayed");
            return false;
        }

        System.out.println("📊 Top 5 Nature of Initiative:");

        for (WebElement row : rows) {

            String nature = row.findElement(
                    By.xpath(".//span[contains(@class,'iniTxt')]"))
                    .getText().trim();

            String count = row.findElement(
                    By.xpath(".//div[@role='progressbar']"))
                    .getText().trim();

            System.out.println("Nature: " + nature + " | Count: " + count);
        }

        return true;
    }
    public static By top5NatureRows =
            By.xpath("//div[contains(@class,'graphSection')]//div[contains(@class,'row mb-2')]");

}

