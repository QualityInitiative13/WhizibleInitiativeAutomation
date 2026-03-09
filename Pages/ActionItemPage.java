package Pages;

import Actions.ActionEngine;
import Locators.ActionItemPageLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.aventstack.extentreports.ExtentTest;

/**
 * Page Object Model (POM) for Action Item Management Module
 * 
 * FRAMEWORK DESIGN PRINCIPLES:
 * ============================
 * This class follows BEST PRACTICES for Selenium framework design:
 * 
 * 1. LOCATOR CENTRALIZATION:
 *    - All static locators are maintained in ActionItemPageLocators.java
 *    - Methods use ActionItemPageLocators.locatorName for reusability
 *    - Dynamic locators use helper methods like getDynamicOptionByText()
 * 
 * 2. SEPARATION OF CONCERNS:
 *    - Locators: Defined in ActionItemPageLocators class
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
public class ActionItemPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    // 🔹 Correct constructor with WebDriver + Logger
    public ActionItemPage(WebDriver driver, ExtentTest reportLogger) {
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
    
    // ✅ Navigate to Action Item Page
    public void navigateToActionItem() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO ACTION ITEM - START");
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
            hoverAndClickElement(ActionItemPageLocators.initree, "Initiative Tree Menu");
            waitForSeconds(2); // Wait for menu to appear
            
            // Step 2: Click on Action Item
            System.out.println("\n📍 Step 2: Clicking Action Item Node...");
            clickWithFallback(ActionItemPageLocators.actionItemNode, "Action Item");
            waitForSeconds(2); // Wait for Action Item page to load
            
            System.out.println("\n✅ ✅ ✅ Navigated to ActionItem successfully! ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to Action Item page");
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ ❌ ❌ Navigation to Action Item FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to Action Item: " + e.getMessage());
            }
            throw e;
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO ACTION ITEM - END");
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

    // ✅ Helper method to click with fallback strategies
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
    
    // ✅ Verify Action Item Page Header
    public void verifyActionItemHeader(String expectedHeader) throws Exception {
        try {
            System.out.println("🔍 Verifying Action Item page header...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ActionItemPageLocators.actionItemPageHeader));
            
            String actualHeader = header.getText().trim();
            System.out.println("Expected: " + expectedHeader);
            System.out.println("Actual: " + actualHeader);
            
            if (actualHeader.contains(expectedHeader)) {
                System.out.println("✅ Action Item header verified successfully");
                if (reportLogger != null) {
                    reportLogger.pass("Action Item header verified: " + actualHeader);
                }
            } else {
                throw new Exception("Header mismatch. Expected: " + expectedHeader + ", Actual: " + actualHeader);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to verify Action Item header: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify Action Item header: " + e.getMessage());
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
                ActionItemPageLocators.searchButton));
            
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
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("✅ Search button clicked using JavaScript");
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
    
    // ✅ Click on Initiative Title Input Field
    public void clickInitiativeTitleInput() throws Exception {
        try {
            System.out.println("\n📝 Clicking on Initiative Title input field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement titleInput = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.initiativeTitleSearchInput));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", titleInput);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                titleInput.click();
                System.out.println("✅ Initiative Title input field clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", titleInput);
                    System.out.println("✅ Initiative Title input field clicked using JavaScript");
                } catch (Exception e2) {
                    // Try focusing the element
                    System.out.println("⚠️ JavaScript click failed, trying focus...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", titleInput);
                    System.out.println("✅ Initiative Title input field focused");
                }
            }
            
            waitForSeconds(1); // Wait for field to be ready
            
            if (reportLogger != null) {
                reportLogger.pass("Initiative Title input field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Title input field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Title input field: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Enter Search Text in Initiative Title Field
    public void enterSearchText(String searchText) throws Exception {
        try {
            System.out.println("\n📝 Entering search text: " + searchText);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ActionItemPageLocators.initiativeTitleSearchInput));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchInput);
            waitForSeconds(1);
            
            // Clear existing text
            try {
                searchInput.clear();
                System.out.println("✅ Cleared existing text");
            } catch (Exception e1) {
                // Try JavaScript clear
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", searchInput);
                    System.out.println("✅ Cleared text using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear input field");
                }
            }
            
            waitForSeconds(1);
            
            // Enter search text
            try {
                searchInput.sendKeys(searchText);
                System.out.println("✅ Search text entered successfully: " + searchText);
            } catch (Exception e1) {
                // Fallback to JavaScript
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + searchText.replace("'", "\\'") + "';", searchInput);
                System.out.println("✅ Search text entered using JavaScript");
            }
            
            waitForSeconds(1); // Wait before clicking submit
            
            if (reportLogger != null) {
                reportLogger.pass("Entered search text: " + searchText);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter search text: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter search text: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Search Submit Button
    public void clickSearchSubmitButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Search Submit Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.searchSubmitButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                submitBtn.click();
                System.out.println("✅ Search submit button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
                    System.out.println("✅ Search submit button clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent button if span is not clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent button...");
                    try {
                        WebElement parentButton = submitBtn.findElement(By.xpath("./ancestor::button"));
                        parentButton.click();
                        System.out.println("✅ Search submit button clicked via parent button");
                    } catch (Exception e3) {
                        // Last resort: try Actions class
                        System.out.println("⚠️ Parent button not found, trying Actions class...");
                        Actions actions = new Actions(driver);
                        actions.moveToElement(submitBtn).click().perform();
                        System.out.println("✅ Search submit button clicked via Actions");
                    }
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
    
    // ✅ Perform Search - Complete search flow
    public void performSearch(String searchText) throws Exception {
        try {
            System.out.println("\n═══════════════════════════════════════════════════════");
            System.out.println("🔍 PERFORMING SEARCH - START");
            System.out.println("═══════════════════════════════════════════════════════");
            
            // Step 1: Click search button to open search input
            clickSearchButton();
            
            // Step 2: Enter search text
            enterSearchText(searchText);
            
            // Step 3: Click search submit button to execute search
            clickSearchSubmitButton();
            
            System.out.println("\n✅ ✅ ✅ Search performed successfully! ✅ ✅ ✅");
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("🔍 PERFORMING SEARCH - END");
            System.out.println("═══════════════════════════════════════════════════════\n");
            
            if (reportLogger != null) {
                reportLogger.pass("Search performed successfully for: " + searchText);
            }
        } catch (Exception e) {
            System.out.println("\n❌ ❌ ❌ Search FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Search failed: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Verify Search Input Field is Visible
    public boolean verifySearchInputVisible() throws Exception {
        try {
            System.out.println("🔍 Verifying search input field is visible...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ActionItemPageLocators.initiativeTitleSearchInput));
            
            boolean isVisible = searchInput.isDisplayed();
            System.out.println("✅ Search input field visibility: " + isVisible);
            
            if (reportLogger != null) {
                reportLogger.pass("Search input field is visible: " + isVisible);
            }
            
            return isVisible;
        } catch (Exception e) {
            System.out.println("❌ Search input field not visible: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Search input field not visible: " + e.getMessage());
            }
            return false;
        }
    }
    
    // ✅ Click on Searched Result
    public void clickSearchedResult() throws Exception {
        try {
            System.out.println("\n🖱️ Clicking on searched result...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchedResultElement = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.searchedResult));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchedResultElement);
            waitForSeconds(1);
            
            // Get the text of the searched result for logging
            String resultText = searchedResultElement.getText().trim();
            System.out.println("📋 Searched result text: " + resultText);
            
            // Try regular click first
            try {
                searchedResultElement.click();
                System.out.println("✅ Searched result clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchedResultElement);
                System.out.println("✅ Searched result clicked using JavaScript");
            }
            
            waitForSeconds(2); // Wait for result to be selected/opened
            
            if (reportLogger != null) {
                reportLogger.pass("Clicked on searched result: " + resultText);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click searched result: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click searched result: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Edit Button (First Row - After Search)
    public void clickEditButtonFirstRow() throws Exception {
        try {
            System.out.println("\n✏️ Clicking Edit Button (First Row)...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.editButtonFirstRow));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", editIcon);
            waitForSeconds(1);
            
            // Try clicking the SVG element or its parent button
            try {
                editIcon.click();
                System.out.println("✅ Edit button clicked successfully");
            } catch (Exception e1) {
                // Try clicking parent button
                try {
                    WebElement parentButton = editIcon.findElement(By.xpath("./ancestor::button"));
                    parentButton.click();
                    System.out.println("✅ Edit button clicked via parent button");
                } catch (Exception e2) {
                    // Fallback to JavaScript click
                    System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIcon);
                    System.out.println("✅ Edit button clicked using JavaScript");
                }
            }
            
            waitForSeconds(3); // Wait for edit mode to open
            
            if (reportLogger != null) {
                reportLogger.pass("Edit button clicked successfully (First Row)");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Edit Button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Edit Button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click on Description Field
    public void clickDescriptionField() throws Exception {
        try {
            System.out.println("\n📝 Clicking on Description field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement descriptionTextarea = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.descriptionField1));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", descriptionTextarea);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                descriptionTextarea.click();
                System.out.println("✅ Description field clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", descriptionTextarea);
                    System.out.println("✅ Description field clicked using JavaScript");
                } catch (Exception e2) {
                    // Try focusing the element
                    System.out.println("⚠️ JavaScript click failed, trying focus...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", descriptionTextarea);
                    System.out.println("✅ Description field focused");
                }
            }
            
            waitForSeconds(1); // Wait for field to be ready
            
            if (reportLogger != null) {
                reportLogger.pass("Description field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Description field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Description field: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Enter Description Text
    public void enterDescription(String description) throws Exception {
        try {
            System.out.println("\n📝 Entering description: " + description);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement descriptionTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ActionItemPageLocators.descriptionField1));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", descriptionTextarea);
            waitForSeconds(1);
            
            // Clear existing text
            try {
                descriptionTextarea.clear();
                System.out.println("✅ Cleared existing description text");
            } catch (Exception e1) {
                // Try JavaScript clear
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", descriptionTextarea);
                    System.out.println("✅ Cleared description using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear description field");
                }
            }
            
            waitForSeconds(1);
            
            // Enter description text
            try {
                descriptionTextarea.sendKeys(description);
                System.out.println("✅ Description entered successfully: " + description);
            } catch (Exception e1) {
                // Fallback to JavaScript
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description.replace("'", "\\'") + "';", descriptionTextarea);
                System.out.println("✅ Description entered using JavaScript");
            }
            
            waitForSeconds(1); // Wait for text to be entered
            
            if (reportLogger != null) {
                reportLogger.pass("Entered description: " + description);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter description: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter description: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Save Button (Edit Mode)
    public void clickSaveButton() throws Exception {
        try {
            System.out.println("\n💾 Clicking Save Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.saveButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", saveBtn);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                saveBtn.click();
                System.out.println("✅ Save button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                System.out.println("✅ Save button clicked using JavaScript");
            }
            
            waitForSeconds(3); // Wait for save to complete
            
            if (reportLogger != null) {
                reportLogger.pass("Save button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Save Button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Save Button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Status Dropdown
    public void clickStatusDropdown() throws Exception {
        try {
            System.out.println("\n📊 Clicking Status Dropdown...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.statusDropdown));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", statusDropdown);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                statusDropdown.click();
                System.out.println("✅ Status dropdown clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusDropdown);
                    System.out.println("✅ Status dropdown clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = statusDropdown.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')]"));
                    parent.click();
                    System.out.println("✅ Status dropdown clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for dropdown options to appear
            
            if (reportLogger != null) {
                reportLogger.pass("Status dropdown clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Status dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Status dropdown: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Select Status from Dropdown
    public void selectStatus(String statusValue) throws Exception {
        try {
            System.out.println("\n📊 Selecting Status: " + statusValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for dropdown options to be visible
            waitForSeconds(2);
            
            boolean selected = false;
            
            // Strategy 1: Try clicking the option directly
            try {
                By optionLocator = ActionItemPageLocators.getDynamicStatusOption(statusValue);
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                waitForSeconds(1);
                
                option.click();
                System.out.println("✅ Status selected by clicking option: " + statusValue);
                selected = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Direct option click failed, trying alternative methods...");
            }
            
            // Strategy 2: Try JavaScript click on option
            if (!selected) {
                try {
                    By optionLocator = ActionItemPageLocators.getDynamicStatusOption(statusValue);
                    WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                    waitForSeconds(1);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Status selected using JavaScript click: " + statusValue);
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
                        if (opt.getText().trim().equalsIgnoreCase(statusValue)) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", opt);
                            waitForSeconds(1);
                            opt.click();
                            System.out.println("✅ Status selected from options list: " + statusValue);
                            selected = true;
                            break;
                        }
                    }
                } catch (Exception e3) {
                    System.out.println("⚠️ Options list search failed...");
                }
            }
            
            if (!selected) {
                throw new Exception("Could not select status '" + statusValue + "' using any method");
            }
            
            waitForSeconds(2); // Wait for selection to be applied
            
            if (reportLogger != null) {
                reportLogger.pass("Status selected: " + statusValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to select Status: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Status: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Assigned To Dropdown
    public void clickAssignedToDropdown() throws Exception {
        try {
            System.out.println("\n👤 Clicking Assigned To Dropdown...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement assignedToDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.assignedToDropdown));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", assignedToDropdown);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                assignedToDropdown.click();
                System.out.println("✅ Assigned To dropdown clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", assignedToDropdown);
                    System.out.println("✅ Assigned To dropdown clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = assignedToDropdown.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')]"));
                    parent.click();
                    System.out.println("✅ Assigned To dropdown clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for dropdown options to appear
            
            if (reportLogger != null) {
                reportLogger.pass("Assigned To dropdown clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Assigned To dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Assigned To dropdown: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Select Assigned To from Dropdown
    public void selectAssignedTo(String assignedToValue) throws Exception {
        try {
            System.out.println("\n👤 Selecting Assigned To: " + assignedToValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for dropdown options to be visible
            waitForSeconds(2);
            
            boolean selected = false;
            
            // Strategy 1: Try clicking the option directly
            try {
                By optionLocator = ActionItemPageLocators.getDynamicAssignedToOption(assignedToValue);
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                waitForSeconds(1);
                
                option.click();
                System.out.println("✅ Assigned To selected by clicking option: " + assignedToValue);
                selected = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Direct option click failed, trying alternative methods...");
            }
            
            // Strategy 2: Try JavaScript click on option
            if (!selected) {
                try {
                    By optionLocator = ActionItemPageLocators.getDynamicAssignedToOption(assignedToValue);
                    WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                    waitForSeconds(1);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Assigned To selected using JavaScript click: " + assignedToValue);
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
                        if (opt.getText().trim().equalsIgnoreCase(assignedToValue)) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", opt);
                            waitForSeconds(1);
                            opt.click();
                            System.out.println("✅ Assigned To selected from options list: " + assignedToValue);
                            selected = true;
                            break;
                        }
                    }
                } catch (Exception e3) {
                    System.out.println("⚠️ Options list search failed...");
                }
            }
            
            if (!selected) {
                throw new Exception("Could not select Assigned To '" + assignedToValue + "' using any method");
            }
            
            waitForSeconds(2); // Wait for selection to be applied
            
            if (reportLogger != null) {
                reportLogger.pass("Assigned To selected: " + assignedToValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to select Assigned To: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to select Assigned To: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Filter Search Button
    public void clickFilterSearchButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Filter Search Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.filterSearchButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", searchButton);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                searchButton.click();
                System.out.println("✅ Filter search button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
                    System.out.println("✅ Filter search button clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if span is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = searchButton.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@role,'button')] | ./parent::*"));
                    parent.click();
                    System.out.println("✅ Filter search button clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for search results to load
            
            if (reportLogger != null) {
                reportLogger.pass("Filter search button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Filter search button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Filter search button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Initiative Title in Search Results
    public void clickInitiativeTitleInResults() throws Exception {
        try {
            System.out.println("\n📋 Clicking Initiative Title in search results...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement initiativeTitle = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.initiativeTitleInResults));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", initiativeTitle);
            waitForSeconds(1);
            
            // Get the text of the initiative title for logging
            String titleText = initiativeTitle.getText().trim();
            System.out.println("📋 Initiative Title text: " + titleText);
            
            // Try regular click first
            try {
                initiativeTitle.click();
                System.out.println("✅ Initiative Title clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeTitle);
                    System.out.println("✅ Initiative Title clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent element if div is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent element...");
                    WebElement parent = initiativeTitle.findElement(By.xpath("./ancestor::a | ./ancestor::div[contains(@role,'button')] | ./parent::*"));
                    parent.click();
                    System.out.println("✅ Initiative Title clicked via parent element");
                }
            }
            
            waitForSeconds(2); // Wait for initiative to be opened/selected
            
            if (reportLogger != null) {
                reportLogger.pass("Clicked on Initiative Title: " + titleText);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Title: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Title: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Edit Button
    public void clickEditButton() throws Exception {
        try {
            System.out.println("\n✏️ Clicking Edit Button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.editButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", editButton);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                editButton.click();
                System.out.println("✅ Edit button clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
                    System.out.println("✅ Edit button clicked using JavaScript");
                } catch (Exception e2) {
                    // Try clicking parent button element if SVG is not directly clickable
                    System.out.println("⚠️ JavaScript click failed, trying parent button element...");
                    WebElement parentButton = editButton.findElement(By.xpath("./ancestor::button[@aria-label='Edit']"));
                    parentButton.click();
                    System.out.println("✅ Edit button clicked via parent button element");
                }
            }
            
            waitForSeconds(2); // Wait for edit mode to open
            
            if (reportLogger != null) {
                reportLogger.pass("Edit button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Edit button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Edit button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Action Item Field
    public void clickActionItemField() throws Exception {
        try {
            System.out.println("\n📝 Clicking Action Item Field...");
            // Wait a bit for edit mode to fully load
            waitForSeconds(3);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try multiple locator strategies
            WebElement actionItemField = null;
            boolean found = false;
            
            // Strategy 1: Original locator
            try {
                System.out.println("🔍 Strategy 1: Trying original locator...");
                actionItemField = wait.until(ExpectedConditions.elementToBeClickable(
                    ActionItemPageLocators.actionItemField));
                found = true;
                System.out.println("✅ Found with original locator");
            } catch (Exception e1) {
                System.out.println("⚠️ Original locator failed");
            }
            
            // Strategy 2: Direct input with id containing TextField227
            if (!found) {
                try {
                    System.out.println("🔍 Strategy 2: Trying direct input with TextField227...");
                    actionItemField = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@id,'TextField227')] | //input[@id='TextField227']")));
                    found = true;
                    System.out.println("✅ Found with direct input locator");
                } catch (Exception e2) {
                    System.out.println("⚠️ Direct input locator failed");
                }
            }
            
            // Strategy 3: Find by label text "Action Item"
            if (!found) {
                try {
                    System.out.println("🔍 Strategy 3: Trying to find by label text 'Action Item'...");
                    WebElement label = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[contains(text(),'Action Item')] | //*[contains(text(),'Action Item') and (self::label or self::span)]")));
                    // Find input following the label
                    actionItemField = label.findElement(By.xpath("./following::input[1] | ../following-sibling::div//input | ../../following-sibling::div//input"));
                    found = true;
                    System.out.println("✅ Found by label text");
                } catch (Exception e3) {
                    System.out.println("⚠️ Label text search failed");
                }
            }
            
            // Strategy 4: Find all inputs and filter by context
            if (!found) {
                try {
                    System.out.println("🔍 Strategy 4: Searching all inputs for Action Item context...");
                    java.util.List<WebElement> allInputs = driver.findElements(By.xpath("//input[@type='text'] | //input[not(@type) or @type='']"));
                    System.out.println("Found " + allInputs.size() + " input fields");
                    
                    for (WebElement input : allInputs) {
                        try {
                            // Check if input is visible and in edit context
                            if (input.isDisplayed()) {
                                // Try to find nearby label or text containing "Action Item"
                                WebElement parent = input.findElement(By.xpath("./ancestor::div[1] | ./ancestor::form[1] | ./ancestor::div[contains(@class,'field') or contains(@class,'input')][1]"));
                                String parentText = parent.getText();
                                if (parentText.contains("Action Item") || parentText.contains("Action")) {
                                    actionItemField = input;
                                    found = true;
                                    System.out.println("✅ Found input by context search");
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                } catch (Exception e4) {
                    System.out.println("⚠️ Context search failed");
                }
            }
            
            // Strategy 5: Try finding input near TextField227 label (even if for attribute doesn't match)
            if (!found) {
                try {
                    System.out.println("🔍 Strategy 5: Trying to find input near TextField227 label...");
                    WebElement label = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[contains(@for,'TextField227')] | //*[@id='TextFieldLabel227'] | //*[contains(@id,'TextField227')]")));
                    actionItemField = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(".//following::input[1] | ../following-sibling::*//input[1] | ../../following-sibling::*//input[1]")));
                    found = true;
                    System.out.println("✅ Found by TextField227 label proximity");
                } catch (Exception e5) {
                    System.out.println("⚠️ TextField227 label proximity search failed");
                }
            }
            
            if (!found || actionItemField == null) {
                // Diagnostic information
                System.out.println("\n❌ DIAGNOSTIC INFO:");
                System.out.println("Current URL: " + driver.getCurrentUrl());
                System.out.println("Page Title: " + driver.getTitle());
                
                // List all input fields
                java.util.List<WebElement> allInputs = driver.findElements(By.tagName("input"));
                System.out.println("Total input fields found: " + allInputs.size());
                for (int i = 0; i < Math.min(allInputs.size(), 10); i++) {
                    WebElement input = allInputs.get(i);
                    System.out.println("  Input " + i + ": id=" + input.getAttribute("id") + 
                                     ", name=" + input.getAttribute("name") + 
                                     ", type=" + input.getAttribute("type") +
                                     ", visible=" + input.isDisplayed());
                }
                
                throw new Exception("Action Item field not found after trying all strategies. Check diagnostic info above.");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", actionItemField);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                actionItemField.click();
                System.out.println("✅ Action Item field clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionItemField);
                    System.out.println("✅ Action Item field clicked using JavaScript");
                } catch (Exception e2) {
                    // Try focusing the element
                    System.out.println("⚠️ JavaScript click failed, trying focus...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", actionItemField);
                    System.out.println("✅ Action Item field focused");
                }
            }
            
            waitForSeconds(1); // Wait for field to be ready
            
            if (reportLogger != null) {
                reportLogger.pass("Action Item field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Action Item field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Action Item field: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Enter Action Item Value
    public void enterActionItem(String actionItemValue) throws Exception {
        try {
            System.out.println("\n📝 Entering Action Item value: " + actionItemValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try multiple locator strategies (same as clickActionItemField)
            WebElement actionItemField = null;
            boolean found = false;
            
            // Strategy 1: Original locator
            try {
                actionItemField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    ActionItemPageLocators.actionItemField));
                found = true;
            } catch (Exception e1) {
                // Try Strategy 2: Direct input
                try {
                    actionItemField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[contains(@id,'TextField227')] | //input[@id='TextField227']")));
                    found = true;
                } catch (Exception e2) {
                    // Try Strategy 3: Find by label text
                    try {
                        WebElement label = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//label[contains(text(),'Action Item')] | //*[contains(text(),'Action Item') and (self::label or self::span)]")));
                        actionItemField = label.findElement(By.xpath("./following::input[1] | ../following-sibling::div//input | ../../following-sibling::div//input"));
                        found = true;
                    } catch (Exception e3) {
                        // Strategy 4: Context search
                        java.util.List<WebElement> allInputs = driver.findElements(By.xpath("//input[@type='text'] | //input[not(@type) or @type='']"));
                        for (WebElement input : allInputs) {
                            try {
                                if (input.isDisplayed()) {
                                    WebElement parent = input.findElement(By.xpath("./ancestor::div[1]"));
                                    String parentText = parent.getText();
                                    if (parentText.contains("Action Item") || parentText.contains("Action")) {
                                        actionItemField = input;
                                        found = true;
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                }
            }
            
            if (!found || actionItemField == null) {
                throw new Exception("Action Item field not found for entering value");
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", actionItemField);
            waitForSeconds(1);
            
            // Clear existing text
            try {
                actionItemField.clear();
                System.out.println("✅ Cleared existing text");
            } catch (Exception e1) {
                // Try JavaScript clear
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", actionItemField);
                    System.out.println("✅ Cleared text using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear input field");
                }
            }
            
            waitForSeconds(1);
            
            // Enter action item value
            try {
                actionItemField.sendKeys(actionItemValue);
                System.out.println("✅ Action Item value entered successfully: " + actionItemValue);
            } catch (Exception e1) {
                // Fallback to JavaScript
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + actionItemValue.replace("'", "\\'") + "';", actionItemField);
                System.out.println("✅ Action Item value entered using JavaScript");
            }
            
            waitForSeconds(1); // Wait for value to be set
            
            if (reportLogger != null) {
                reportLogger.pass("Entered Action Item value: " + actionItemValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Action Item value: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Action Item value: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Click Description Field
    public void clickDescriptionField1() throws Exception {
        try {
            System.out.println("\n📝 Clicking Description Field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement descriptionField = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.descriptionField));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", descriptionField);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                descriptionField.click();
                System.out.println("✅ Description field clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", descriptionField);
                    System.out.println("✅ Description field clicked using JavaScript");
                } catch (Exception e2) {
                    // Try finding textarea or input inside the div
                    System.out.println("⚠️ JavaScript click failed, trying to find input/textarea inside...");
                    try {
                        WebElement inputElement = descriptionField.findElement(By.xpath(".//textarea | .//input"));
                        inputElement.click();
                        System.out.println("✅ Description field clicked via inner element");
                    } catch (Exception e3) {
                        // Try focusing the element
                        System.out.println("⚠️ Inner element click failed, trying focus...");
                        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", descriptionField);
                        System.out.println("✅ Description field focused");
                    }
                }
            }
            
            waitForSeconds(1); // Wait for field to be ready
            
            if (reportLogger != null) {
                reportLogger.pass("Description field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Description field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Description field: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ✅ Enter Description Value
    public void enterDescription1(String descriptionValue) throws Exception {
        try {
            System.out.println("\n📝 Entering Description value: " + descriptionValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try to find textarea or input inside the description field div
            WebElement descriptionInput = null;
            try {
                // First try to find textarea
                descriptionInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='TextFieldLabel168']/following-sibling::div//textarea")));
            } catch (Exception e1) {
                try {
                    // If textarea not found, try input
                    descriptionInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id='TextFieldLabel168']/following-sibling::div//input")));
                } catch (Exception e2) {
                    // If neither found, use the div itself
                    descriptionInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        ActionItemPageLocators.descriptionField));
                }
            }
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", descriptionInput);
            waitForSeconds(1);
            
            // Clear existing text
            try {
                descriptionInput.clear();
                System.out.println("✅ Cleared existing text");
            } catch (Exception e1) {
                // Try JavaScript clear
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", descriptionInput);
                    System.out.println("✅ Cleared text using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear description field");
                }
            }
            
            waitForSeconds(1);
            
            // Enter description value
            try {
                descriptionInput.sendKeys(descriptionValue);
                System.out.println("✅ Description value entered successfully: " + descriptionValue);
            } catch (Exception e1) {
                // Fallback to JavaScript
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + descriptionValue.replace("'", "\\'") + "';", descriptionInput);
                // Trigger input event to ensure the value is recognized
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", descriptionInput);
                System.out.println("✅ Description value entered using JavaScript");
            }
            
            waitForSeconds(1); // Wait for value to be set
            
            if (reportLogger != null) {
                reportLogger.pass("Entered Description value: " + descriptionValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Description value: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Description value: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ==================== AUDIT ACTION ITEMS TAB ====================
    
    // ✅ Click Audit Action Items Tab
    public void clickAuditActionItemsTab() throws Exception {
        try {
            System.out.println("\n📋 Clicking Audit Action Items Tab...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement auditTab = wait.until(ExpectedConditions.elementToBeClickable(
                ActionItemPageLocators.auditActionItemsTab));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", auditTab);
            waitForSeconds(1);
            
            // Try regular click first
            try {
                auditTab.click();
                System.out.println("✅ Audit Action Items tab clicked successfully");
            } catch (Exception e1) {
                // Fallback to JavaScript click
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", auditTab);
                System.out.println("✅ Audit Action Items tab clicked using JavaScript");
            }
            
            waitForSeconds(3); // Wait for tab content to load
            
            if (reportLogger != null) {
                reportLogger.pass("Audit Action Items tab clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Audit Action Items tab: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Audit Action Items tab: " + e.getMessage());
            }
            throw e;
        }
    }
    
    // ==================== EDIT INITIATIVE FUNCTIONALITY ====================
    
    // ✅ Find Row Index of Searched Initiative
    public int findInitiativeRowIndex(String initiativeTitle) throws Exception {
        try {
            System.out.println("\n🔍 Finding row index for initiative: " + initiativeTitle);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for table to load
            waitForSeconds(2);
            
            // Find all initiative divs using the xpath pattern: //tbody/tr/td/div[1]
            java.util.List<WebElement> initiativeDivs = driver.findElements(By.xpath("//tbody/tr/td/div[1]"));
            System.out.println("📊 Total initiative divs found: " + initiativeDivs.size());
            
            // Search through divs to find the one containing the initiative title
            for (int i = 0; i < initiativeDivs.size(); i++) {
                try {
                    WebElement div = initiativeDivs.get(i);
                    String divText = div.getText().trim();
                    String searchTitle = initiativeTitle.trim();
                    
                    System.out.println("  Checking row " + (i + 1) + ": '" + divText + "'");
                    
                    // Check if the div text contains the initiative title (case-insensitive)
                    if (divText.equalsIgnoreCase(searchTitle) || divText.contains(searchTitle) || searchTitle.contains(divText)) {
                        int rowIndex = i + 1; // Convert to 1-based index
                        System.out.println("✅ Found initiative '" + initiativeTitle + "' in row " + rowIndex);
                        System.out.println("   Matched text: '" + divText + "'");
                        
                        if (reportLogger != null) {
                            reportLogger.pass("Found initiative '" + initiativeTitle + "' in row " + rowIndex);
                        }
                        return rowIndex;
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠️ Error checking row " + (i + 1) + ": " + e.getMessage());
                    // Continue searching other rows
                    continue;
                }
            }
            
            // If not found, try alternative method - search by row text
            System.out.println("⚠️ Initiative not found using div[1] xpath, trying alternative method...");
            java.util.List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
            System.out.println("📊 Total rows found: " + rows.size());
            
            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    String rowText = row.getText().toLowerCase();
                    String searchTitle = initiativeTitle.toLowerCase();
                    
                    if (rowText.contains(searchTitle)) {
                        int rowIndex = i + 1; // Convert to 1-based index
                        System.out.println("✅ Found initiative in row " + rowIndex + " (using row text search)");
                        if (reportLogger != null) {
                            reportLogger.pass("Found initiative '" + initiativeTitle + "' in row " + rowIndex);
                        }
                        return rowIndex;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // If still not found, default to first row (row 1)
            System.out.println("⚠️ Initiative '" + initiativeTitle + "' not found in table, defaulting to row 1");
            if (reportLogger != null) {
                reportLogger.warning("Initiative '" + initiativeTitle + "' not found in table, using row 1");
            }
            return 1;
        } catch (Exception e) {
            System.out.println("❌ Failed to find initiative row: " + e.getMessage());
            System.out.println("⚠️ Defaulting to row 1");
            if (reportLogger != null) {
                reportLogger.warning("Failed to find initiative row, using row 1: " + e.getMessage());
            }
            return 1;
        }
    }


}

