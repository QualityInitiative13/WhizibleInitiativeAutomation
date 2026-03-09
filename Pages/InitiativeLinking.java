package Pages;

import Actions.ActionEngine;
import Locators.ProjectPageLocator;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for the Initiative Linking page.
 *
 * This class is intended to hold all interactions related specifically
 * to the **Initiative Linking** view, so that they are clearly
 * separated from the generic `InitiativePage` and other
 * initiative-related page objects.
 *
 * DESIGN GOALS
 * ============
 * - Keep only linking-specific behaviours in this page object.
 * - Reuse existing navigation from `InitiativeManagementPage` /
 *   `InitiativePage` wherever possible.
 * - As you convert tests that work on the initiative linking page,
 *   add the required methods here instead of to the generic pages.
 */
public class InitiativeLinking extends ActionEngine {

    private final WebDriver driver;
    private final ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger.
     *
     * @param driver       WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeLinking(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // =====================================================================
    // NAVIGATION & HIGH‑LEVEL WORKFLOWS
    // =====================================================================

    /**
     * Navigate to the Initiative Linking page from the Initiative
     * Tracking module.
     *
     * NOTE: Wire this method to the actual navigation steps once you
     * know the exact menu / card / link locators for the Initiative
     * Linking page.
     */
    public void navigateToInitiativeLinkingPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Step 1: Click on the Initiative Tracking module (left navigation)
        clickInitiativeTrackingModule();

        // Small wait to allow the child panel to expand
        waitForSeconds(2);

        // Step 2: Click on the Initiative Linking page link/card
        clickInitiativeLinkingPageLink();

        // Optional: small wait for page to load
        waitForSeconds(3);
    }

    /**
     * Click on the Initiative Tracking Module in the left navigation.
     *
     * XPath (provided by user):
     *   //*[@id="root"]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[3]/div/span[1]/span/div/img
     */
    public void clickInitiativeTrackingModule() throws Exception {
        By primaryBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[3]/div/span[1]/span/div/img");
        // Fallback: more generic locators based on visible text or classes, if needed
        By[] fallbackLocators = new By[] {
                By.xpath("//nav//li[.//span[contains(text(),'Initiative Tracking')] or .//div[contains(@aria-label,'Initiative')]]//img"),
                By.xpath("//img[contains(@alt,'Initiative') and ancestor::nav]")
        };

        try {
            click(primaryBy, "Initiative Tracking module");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary Initiative Tracking module locator failed, trying fallbacks: " + e.getMessage());
            for (By locator : fallbackLocators) {
                try {
                    click(locator, "Initiative Tracking module (fallback)");
                    return;
                } catch (Exception ignore) {
                    // Continue to next fallback
                }
            }
            throw new Exception("Failed to click Initiative Tracking module using all locator strategies: " + e.getMessage());
        }
    }

    /**
     * Click on the Initiative Linking page link.
     *
     * This method clicks on the "Initiative Linking" link/card that appears
     * after expanding the Initiative Tracking module.
     *
     * NOTE: Update the XPath once you have the actual locator for the
     * Initiative Linking page link.
     */
    public void clickInitiativeLinkingPageLink() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        // Primary: User-provided absolute XPath
        By primaryBy = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[2]/p");
        
        // Fallback: Text-based XPath (more reliable than index-based)
        By fallbackBy = By.xpath("//*[@id='children-panel-container']//p[normalize-space()='Initiative Linking']");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(primaryBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", link);
            waitForSeconds(1);
            link.click();
            System.out.println("  ✅ Clicked on: Initiative Linking page link (primary)");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary Initiative Linking page link locator failed, trying fallback: " + e.getMessage());
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(fallbackBy));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", link);
                waitForSeconds(1);
                link.click();
                System.out.println("  ✅ Clicked on: Initiative Linking page link (fallback)");
            } catch (Exception e2) {
                throw new Exception("Failed to click Initiative Linking page link using all locator strategies: " + e2.getMessage());
            }
        }
    }

    // =====================================================================
    // PAGE ELEMENT INTERACTIONS
    // =====================================================================

    /**
     * Click on the Search toolbar button (icon) on Initiative Linking page.
     * 
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/img
     */
    public static By searchicon=
            By.xpath("//img[@aria-label='Applied Filter']");

    public void clickSearchToolbarButton() throws Exception {
        click(InitiativeLinking.searchicon, "searchicon");
      
        }
       

    /**
     * Select Nature of Initiative from dropdown.
     * XPath: //*[@id="intNatureOfDemandID"]/span[2]
     */
    public void selectNatureOfInitiative(String natureOfInitiative) throws Exception {
        if (natureOfInitiative == null || natureOfInitiative.trim().isEmpty()) return;
        selectDropdownValue("//*[@id=\"intNatureOfDemandID\"]/span[2]", natureOfInitiative, "Nature of Initiative");
    }

    /**
     * Select Business Group from dropdown.
     * XPath: //*[@id="intBusinessGroupID"]/span[2]
     */
    public void selectBusinessGroup(String businessGroup) throws Exception {
        if (businessGroup == null || businessGroup.trim().isEmpty()) return;
        selectDropdownValue("//*[@id=\"intBusinessGroupID\"]/span[2]", businessGroup, "Business Group");
    }

    /**
     * Select Organization Unit from dropdown.
     * XPath: //*[@id="intOrganizationUnit"]/span[2]
     */
    public void selectOrganizationUnit(String organizationUnit) throws Exception {
        if (organizationUnit == null || organizationUnit.trim().isEmpty()) return;
        selectDropdownValue("//*[@id=\"intOrganizationUnit\"]/span[2]", organizationUnit, "Organization Unit");
    }

    /**
     * Enter Initiative Code into text field.
     * XPath: //*[@id="strDemandCode"]
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) return;
        By codeBy = By.xpath("//*[@id=\"strDemandCode\"]");
        type(codeBy, initiativeCode, "Initiative Linking - Initiative Code");
    }

    /**
     * Select Initiative Status from dropdown.
     * XPath: //*[@id="strStatusID"]/span[2]
     */
    public void selectInitiativeStatus(String initiativeStatus) throws Exception {
        if (initiativeStatus == null || initiativeStatus.trim().isEmpty()) return;
        selectDropdownValue("//*[@id=\"strStatusID\"]/span[2]", initiativeStatus, "Initiative Status");
    }

    /**
     * Enter Initiative Title into text field.
     * XPath: //*[@id="strTitle"]
     */
    public void enterInitiativeTitle(String initiativeTitle) throws Exception {
        if (initiativeTitle == null || initiativeTitle.trim().isEmpty()) return;
        By titleBy = By.xpath("//*[@id=\"strTitle\"]");
        type(titleBy, initiativeTitle, "Initiative Linking - Initiative Title");
    }

    /**
     * Click on the Search button (filter panel).
     * XPath: //*[@id="id__126"]
     */
    
    public static By searchtext=
            By.xpath("//button[.//span[text()='Search']]");
    
    
    public void clickFilterSearchButton() throws Exception {
        click(InitiativeLinking.searchtext, "searchtext");

    }

    /**
     * Robust dropdown value selection helper method.
     * Handles iframes, flexible text matching, scrolling, and multiple click strategies.
     * 
     * @param dropdownXpath XPath for the dropdown trigger element
     * @param valueToSelect The value to select from the dropdown
     * @param dropdownName Name of the dropdown for logging (e.g., "Nature of Initiative", "Business Group")
     * @throws Exception if dropdown or option cannot be found/clicked
     */
    private void selectDropdownValue(String dropdownXpath, String valueToSelect, String dropdownName) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        String value = valueToSelect.trim();
        String normalizedSearchValue = value.replaceAll("\\s+", " ").toLowerCase();
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Actions actions = new Actions(driver);
            
            // Step 1: Find and click on dropdown (scan iframes if needed)
            By dropdownBy = By.xpath(dropdownXpath);
            WebElement dropdown = null;
            boolean dropdownFound = false;
            int dropdownIframeIndex = -1;
            
            // Try default content first
            try {
                dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownBy));
                System.out.println("  ✅ Found " + dropdownName + " dropdown (default content)");
                dropdownFound = true;
            } catch (Exception e) {
                System.out.println("  ⚠️ " + dropdownName + " dropdown not found in default content, checking iframes...");
                // Scan iframes for the dropdown
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for " + dropdownName + " dropdown...");
                for (int i = 0; i < iframes.size() && !dropdownFound; i++) {
                    try {
                        driver.switchTo().frame(i);
                        try {
                            dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownBy));
                            System.out.println("  ✅ Found " + dropdownName + " dropdown (iframe #" + i + ")");
                            dropdownFound = true;
                            dropdownIframeIndex = i;
                            break;
                        } catch (Exception ex) {
                            // Not in this iframe
                        }
                        driver.switchTo().defaultContent();
                    } catch (Exception ex) {
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
            // If still not found, try alternative locator strategies
            if (!dropdownFound) {
                System.out.println("  ⚠️ Trying alternative locator strategies for " + dropdownName + " dropdown...");
                // Extract ID from XPath
                String idValue = "";
                if (dropdownXpath.contains("id=\"")) {
                    int startIdx = dropdownXpath.indexOf("id=\"") + 4;
                    int endIdx = dropdownXpath.indexOf("\"", startIdx);
                    if (endIdx > startIdx) {
                        idValue = dropdownXpath.substring(startIdx, endIdx);
                    }
                }
                
                // Try alternative XPaths
                String[] alternativeXpaths = {
                    dropdownXpath.replace("/span[2]", ""), // Try container without span
                    dropdownXpath.replace("span[2]", "span"), // Try first span
                    "//div[@id='" + idValue + "']//span[2]",
                    "//*[@id='" + idValue + "']",
                    "//div[contains(@id,'" + idValue + "')]//span[2]"
                };
                
                for (String altXpath : alternativeXpaths) {
                    if (altXpath.isEmpty()) continue;
                    try {
                        By altBy = By.xpath(altXpath);
                        dropdown = wait.until(ExpectedConditions.elementToBeClickable(altBy));
                        System.out.println("  ✅ Found " + dropdownName + " dropdown using alternative locator");
                        dropdownFound = true;
                        break;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            
            // If still not found, try alternative locator strategies
            if (!dropdownFound) {
                System.out.println("  ⚠️ Trying alternative locator strategies for " + dropdownName + " dropdown...");
                // Extract ID from XPath
                String idValue = "";
                if (dropdownXpath.contains("id=\"")) {
                    int startIdx = dropdownXpath.indexOf("id=\"") + 4;
                    int endIdx = dropdownXpath.indexOf("\"", startIdx);
                    if (endIdx > startIdx) {
                        idValue = dropdownXpath.substring(startIdx, endIdx);
                    }
                }
                
                // Try alternative XPaths
                String[] alternativeXpaths = {
                    dropdownXpath.replace("/span[2]", ""), // Try container without span
                    dropdownXpath.replace("span[2]", "span"), // Try first span
                    "//div[@id='" + idValue + "']//span[2]",
                    "//*[@id='" + idValue + "']",
                    "//div[contains(@id,'" + idValue + "')]//span[2]"
                };
                
                for (String altXpath : alternativeXpaths) {
                    if (altXpath.isEmpty()) continue;
                    try {
                        By altBy = By.xpath(altXpath);
                        dropdown = wait.until(ExpectedConditions.elementToBeClickable(altBy));
                        System.out.println("  ✅ Found " + dropdownName + " dropdown using alternative locator");
                        dropdownFound = true;
                        break;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            
            if (!dropdownFound || dropdown == null) {
                throw new Exception("Could not find " + dropdownName + " dropdown in default content or any iframe. Make sure the Search filter panel is open.");
            }
            
            // Click the dropdown with multiple strategies
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
                waitForSeconds(1);
                
                // Try Actions click first
                try {
                    actions.moveToElement(dropdown).click().perform();
                    System.out.println("  ✅ Clicked on " + dropdownName + " dropdown (Actions)");
                } catch (Exception e1) {
                    // Try JavaScript click
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                        System.out.println("  ✅ Clicked on " + dropdownName + " dropdown (JavaScript)");
                    } catch (Exception e2) {
                        // Try direct click as last resort
                        dropdown.click();
                        System.out.println("  ✅ Clicked on " + dropdownName + " dropdown (direct)");
                    }
                }
            } catch (Exception e) {
                throw new Exception("Failed to click " + dropdownName + " dropdown after all strategies: " + e.getMessage());
            }
            
            // Step 2: Wait for dropdown options to appear
            waitForSeconds(3); // Increased wait time for options to load
            
            // Step 3: Check for iframes and find options
            if (dropdownIframeIndex >= 0) {
                driver.switchTo().frame(dropdownIframeIndex);
            } else {
                driver.switchTo().defaultContent();
            }
            
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Found " + iframes.size() + " iframe(s) after clicking dropdown");
            
            WebElement option = null;
            boolean found = false;
            int optionIframeIndex = -1;
            
            // Strategy 1: Try current context first with multiple locator strategies
            try {
                // Try multiple XPath strategies to find dropdown options
                String[] optionXpaths = {
                    "//div[@role='listbox']//span",
                    "//div[@role='listbox']//div",
                    "//ul[@role='listbox']//li",
                    "//div[@role='option']",
                    "//li[@role='option']",
                    "//div[contains(@class,'option')]",
                    "//li[contains(@class,'option')]",
                    "//div[contains(@class,'MuiAutocomplete')]//li",
                    "//div[contains(@class,'MuiAutocomplete')]//div",
                    "//ul[contains(@class,'MuiAutocomplete')]//li",
                    "//*[@role='listbox']//*",
                    "//div[contains(@id,'option')]",
                    "//li[contains(@id,'option')]"
                };
                
                List<WebElement> options = new java.util.ArrayList<>();
                for (String xpath : optionXpaths) {
                    try {
                        List<WebElement> foundOptions = driver.findElements(By.xpath(xpath));
                        for (WebElement opt : foundOptions) {
                            if (!options.contains(opt)) {
                                options.add(opt);
                            }
                        }
                    } catch (Exception e) {
                        // Continue to next XPath
                    }
                }
                
                System.out.println("  🔍 Found " + options.size() + " options in current context (using multiple locators)");
                
                // Debug: Print all visible options
                if (options.size() > 0) {
                    System.out.println("  📋 Available options in dropdown:");
                    for (WebElement opt : options) {
                        if (opt.isDisplayed()) {
                            String optText = opt.getText().trim();
                            if (optText.isEmpty()) {
                                optText = opt.getAttribute("textContent");
                                if (optText != null) optText = optText.trim();
                            }
                            if (optText != null && !optText.isEmpty() && !optText.equalsIgnoreCase("Select") && 
                                !optText.equalsIgnoreCase("--Select--")) {
                                System.out.println("     - '" + optText + "'");
                            }
                        }
                    }
                }
                
                for (WebElement opt : options) {
                    if (!opt.isDisplayed()) continue;
                    
                    String optionText = opt.getText().trim();
                    if (optionText.isEmpty()) {
                        optionText = opt.getAttribute("textContent");
                        if (optionText != null) optionText = optionText.trim();
                        else optionText = "";
                    }
                    
                    String normalizedOptionText = optionText.replaceAll("\\s+", " ").toLowerCase();
                    
                    // Skip placeholder options more aggressively
                    String lowerOptionText = optionText.toLowerCase();
                    if (optionText.isEmpty() || 
                        lowerOptionText.equals("select") || 
                        lowerOptionText.equals("--select--") ||
                        lowerOptionText.startsWith("select ") ||
                        lowerOptionText.contains("select ") && lowerOptionText.length() < normalizedSearchValue.length() + 10) {
                        continue;
                    }
                    
                    // Try matching - improved logic (prioritize exact/contains, avoid matching placeholders)
                    boolean isMatch = false;
                    String matchType = "";
                    
                    // Priority 1: Exact match
                    if (normalizedOptionText.equals(normalizedSearchValue)) {
                        isMatch = true;
                        matchType = "exact";
                    } 
                    // Priority 2: Contains match (option contains search value)
                    else if (normalizedOptionText.contains(normalizedSearchValue) && normalizedSearchValue.length() >= 3) {
                        // Additional check: don't match if it's clearly a placeholder
                        if (!lowerOptionText.startsWith("select ") && !lowerOptionText.equals("select")) {
                            isMatch = true;
                            matchType = "contains";
                        }
                    } 
                    // Priority 3: Word-based matching (only if no exact/contains match found)
                    else {
                        // Only use word-based matching if search value has multiple words
                        String[] searchWords = normalizedSearchValue.split("\\s+");
                        if (searchWords.length >= 2) {
                            // Count how many words match
                            int matchingWords = 0;
                            for (String word : searchWords) {
                                if (word.length() >= 4 && normalizedOptionText.contains(word)) {
                                    matchingWords++;
                                }
                            }
                            // Require at least 2 words to match (to avoid matching "Select Business Group" with "AI And ML Group")
                            if (matchingWords >= 2 && !lowerOptionText.startsWith("select ")) {
                                isMatch = true;
                                matchType = "word-based match (" + matchingWords + " words)";
                            }
                        }
                    }
                    
                    if (isMatch) {
                        System.out.println("     ✅ " + matchType + " match found: '" + optionText + "' (searching for: '" + valueToSelect + "')");
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", opt);
                        waitForSeconds(1);
                        wait.until(ExpectedConditions.visibilityOf(opt));
                        wait.until(ExpectedConditions.elementToBeClickable(opt));
                        option = opt;
                        found = true;
                        break;
                    }
                }
                
                // If no match found, print available options for debugging
                if (!found) {
                    List<String> availableOptions = new java.util.ArrayList<>();
                    for (WebElement opt : options) {
                        if (opt.isDisplayed()) {
                            String optText = opt.getText().trim();
                            if (optText == null || optText.isEmpty()) {
                                optText = opt.getAttribute("textContent");
                                if (optText != null) optText = optText.trim();
                                else optText = "";
                            }
                            if (optText != null && !optText.isEmpty() && !optText.equalsIgnoreCase("Select") && 
                                !optText.equalsIgnoreCase("--Select--")) {
                                availableOptions.add(optText);
                            }
                        }
                    }
                    if (!availableOptions.isEmpty()) {
                        System.out.println("  ⚠️ No match found. Available options in dropdown:");
                        for (String opt : availableOptions) {
                            System.out.println("     - '" + opt + "'");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not find options in current context: " + e.getMessage());
            }
            
            // Strategy 3: If no options found and this is Organization Unit, try clicking dropdown again and waiting longer
            if (!found && (dropdownName.contains("Organization Unit") || dropdownName.contains("Organization"))) {
                System.out.println("  ⚠️ No options found for " + dropdownName + ", trying to click dropdown again and wait longer...");
                driver.switchTo().defaultContent();
                waitForSeconds(2);
                
                // Try clicking the dropdown again
                try {
                    if (dropdownIframeIndex >= 0) {
                        driver.switchTo().frame(dropdownIframeIndex);
                    }
                    WebElement dropdownRetry = wait.until(ExpectedConditions.elementToBeClickable(dropdownBy));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdownRetry);
                    waitForSeconds(1);
                    actions.moveToElement(dropdownRetry).click().perform();
                    System.out.println("  ✅ Clicked " + dropdownName + " dropdown again");
                    waitForSeconds(5); // Wait longer for options to load
                    
                    // Try finding options again
                    if (dropdownIframeIndex >= 0) {
                        driver.switchTo().frame(dropdownIframeIndex);
                    } else {
                        driver.switchTo().defaultContent();
                    }
                    
                    // Try finding options with multiple strategies again
                    String[] optionXpaths = {
                        "//div[@role='listbox']//span",
                        "//div[@role='listbox']//div",
                        "//ul[@role='listbox']//li",
                        "//div[@role='option']",
                        "//li[@role='option']",
                        "//div[contains(@class,'option')]",
                        "//li[contains(@class,'option')]",
                        "//div[contains(@class,'MuiAutocomplete')]//li",
                        "//*[@role='listbox']//*"
                    };
                    
                    List<WebElement> retryOptions = new java.util.ArrayList<>();
                    for (String xpath : optionXpaths) {
                        try {
                            List<WebElement> foundOptions = driver.findElements(By.xpath(xpath));
                            for (WebElement opt : foundOptions) {
                                if (!retryOptions.contains(opt)) {
                                    retryOptions.add(opt);
                                }
                            }
                        } catch (Exception e) {
                            // Continue
                        }
                    }
                    
                    System.out.println("  🔍 Found " + retryOptions.size() + " options after retry");
                    
                    // Try matching again
                    for (WebElement opt : retryOptions) {
                        if (!opt.isDisplayed()) continue;
                        
                        String optionText = opt.getText().trim();
                        if (optionText.isEmpty()) {
                            optionText = opt.getAttribute("textContent");
                            if (optionText != null) optionText = optionText.trim();
                            else optionText = "";
                        }
                        
                        String normalizedOptionText = optionText.replaceAll("\\s+", " ").toLowerCase();
                        
                        // Skip placeholder options more aggressively
                        String lowerOptionText = optionText.toLowerCase();
                        if (optionText.isEmpty() || 
                            lowerOptionText.equals("select") || 
                            lowerOptionText.equals("--select--") ||
                            lowerOptionText.startsWith("select ") ||
                            lowerOptionText.contains("select ") && lowerOptionText.length() < normalizedSearchValue.length() + 10) {
                            continue;
                        }
                        
                        boolean isMatch = false;
                        String matchType = "";
                        
                        // Priority 1: Exact match
                        if (normalizedOptionText.equals(normalizedSearchValue)) {
                            isMatch = true;
                            matchType = "exact";
                        } 
                        // Priority 2: Contains match
                        else if (normalizedOptionText.contains(normalizedSearchValue) && normalizedSearchValue.length() >= 3) {
                            if (!lowerOptionText.startsWith("select ") && !lowerOptionText.equals("select")) {
                                isMatch = true;
                                matchType = "contains";
                            }
                        } 
                        // Priority 3: Word-based matching
                        else {
                            String[] searchWords = normalizedSearchValue.split("\\s+");
                            if (searchWords.length >= 2) {
                                int matchingWords = 0;
                                for (String word : searchWords) {
                                    if (word.length() >= 4 && normalizedOptionText.contains(word)) {
                                        matchingWords++;
                                    }
                                }
                                if (matchingWords >= 2 && !lowerOptionText.startsWith("select ")) {
                                    isMatch = true;
                                    matchType = "word-based match (" + matchingWords + " words)";
                                }
                            }
                        }
                        
                        if (isMatch) {
                            System.out.println("     ✅ " + matchType + " match found after retry: '" + optionText + "'");
                            option = opt;
                            found = true;
                            break;
                        }
                    }
                } catch (Exception retryError) {
                    System.out.println("  ⚠️ Retry failed: " + retryError.getMessage());
                }
            }
            
            if (!found || option == null) {
                driver.switchTo().defaultContent();
                throw new Exception("Could not find " + dropdownName + " option '" + value + "' in default content or any iframe. The dropdown might be empty or the value might not exist.");
            }
            
            // Step 4: Click the option using multiple strategies
            try {
                // Switch to the iframe where the option was found, or use dropdown iframe
                if (optionIframeIndex >= 0) {
                    driver.switchTo().frame(optionIframeIndex);
                } else if (dropdownIframeIndex >= 0) {
                    driver.switchTo().frame(dropdownIframeIndex);
                } else {
                    driver.switchTo().defaultContent();
                }
                
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", option);
                waitForSeconds(1);
                
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("  ✅ Clicked " + dropdownName + " option using JavaScript");
                } catch (Exception jsError) {
                    try {
                        actions.moveToElement(option).click().perform();
                        System.out.println("  ✅ Clicked " + dropdownName + " option using Actions");
                    } catch (Exception actionsError) {
                        option.click();
                        System.out.println("  ✅ Clicked " + dropdownName + " option using direct click");
                    }
                }
                
                waitForSeconds(1);
                System.out.println("  ✅ Selected " + dropdownName + ": " + value);
            } catch (Exception e) {
                throw new Exception("Failed to click " + dropdownName + " option after all attempts: " + e.getMessage());
            }
            
            // Switch back to default content
            driver.switchTo().defaultContent();
            waitForSeconds(1);
            
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            throw new Exception("Error selecting " + dropdownName + " value '" + value + "': " + e.getMessage());
        }
    }

    // =====================================================================
    // ADD CHILD INITIATIVE ACTIONS (TC_003)
    // =====================================================================

    /**
     * Click on the Add Child link/button.
     * XPath: //button[@aria-label='Add Child']
     */
    public void clickAddChildLink() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        By primaryBy = By.xpath("//button[@aria-label='Add Child']");
        By fallbackBy = By.xpath("//button[contains(@aria-label,'Add Child') or contains(text(),'Add Child')]");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement addChildBtn = wait.until(ExpectedConditions.elementToBeClickable(primaryBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addChildBtn);
            waitForSeconds(1);
            addChildBtn.click();
            System.out.println("  ✅ Clicked on: Add Child link");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary Add Child link failed, trying fallback: " + e.getMessage());
            click(fallbackBy, "Add Child link (fallback)");
        }
        waitForSeconds(2);
    }

    /**
     * Click on the Child Initiative text field (without entering value).
     * XPath: //*[@id="TextField742"]
     */
    public void clickChildInitiativeTextField() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] fieldLocators = {
            By.xpath("//*[@id=\"TextField742\"]"),
            By.xpath("//input[@id='TextField742']"),
            By.xpath("//*[contains(@id,'TextField742')]"),
            By.xpath("//input[contains(@id,'TextField742')]"),
            By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//input[contains(@id,'TextField742')]"),
            By.xpath("//label[contains(text(),'Child Initiative') or contains(text(),'child initiative')]/following-sibling::*//input")
        };
        
        WebElement field = null;
        boolean found = false;
        
        // First, try quick find without waiting (most common case)
        for (By locator : fieldLocators) {
            try {
                field = driver.findElement(locator);
                if (field != null && field.isDisplayed()) {
                    System.out.println("  ✅ Found Child Initiative text field using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found quickly, use explicit wait with shorter timeout
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : fieldLocators) {
                try {
                    field = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (field != null && field.isDisplayed()) {
                        System.out.println("  ✅ Found Child Initiative text field using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes with shorter timeout
        if (!found) {
            System.out.println("  ⚠️ Child Initiative text field not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for Child Initiative text field...");
            
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : fieldLocators) {
                        try {
                            field = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (field != null && field.isDisplayed()) {
                                System.out.println("  ✅ Found Child Initiative text field in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || field == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Child Initiative text field in default content or any iframe. Make sure the Add Child dialog is open.");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", field);
            Thread.sleep(100); // Minimal wait for scroll
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            field.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", field);
        }
        System.out.println("  ✅ Clicked on: Child Initiative text field");
    }

    /**
     * Select Relation Type from dropdown.
     * XPath: //*[@id="relationTypeId"]/span[2]
     */
    public void selectRelationType(String relationType) throws Exception {
        if (relationType == null || relationType.trim().isEmpty()) return;
        selectDropdownValue("//*[@id=\"relationTypeId\"]/span[2]", relationType, "Relation Type");
    }

    /**
     * Enter Initiative Title into text field (in the child initiative form).
     * XPath: //*[@id="initiativeTitle"]
     */
    public void enterInitiativeTitleInChildForm(String initiativeTitle) throws Exception {
        if (initiativeTitle == null || initiativeTitle.trim().isEmpty()) return;
        By titleBy = By.xpath("//*[@id=\"initiativeTitle\"]");
        type(titleBy, initiativeTitle, "Initiative Title (child form)");
    }

    /**
     * Click on the Apply button (for Move To form).
     * XPath: //*[@id="id__289"]
     */
    public void clickApplyButtonMoveTo() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] applyButtonLocators = {
            By.xpath("//*[@id=\"id__289\"]"),
            By.xpath("//button[@id='id__289']"),
            By.xpath("//button[contains(@id,'id__289')]"),
            By.xpath("//button[.//span[normalize-space(text())='Apply']]"),
            By.xpath("//button[contains(@aria-label,'Apply')]"),
            By.xpath("//button[contains(text(),'Apply')]")
        };
        
        WebElement applyButton = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : applyButtonLocators) {
            try {
                applyButton = driver.findElement(locator);
                if (applyButton != null && applyButton.isDisplayed()) {
                    System.out.println("  ✅ Found Apply button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : applyButtonLocators) {
                try {
                    applyButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (applyButton != null && applyButton.isDisplayed()) {
                        System.out.println("  ✅ Found Apply button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Apply button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : applyButtonLocators) {
                        try {
                            applyButton = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (applyButton != null && applyButton.isDisplayed()) {
                                System.out.println("  ✅ Found Apply button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || applyButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Apply button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", applyButton);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue
        }
        
        try {
            applyButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyButton);
        }
        System.out.println("  ✅ Clicked on: Apply button");
        waitForSeconds(3); // Wait for table to load
    }

    /**
     * Click on the Apply button.
     * XPath: //*[@id="id__777"]
     */
    public void clickApplyButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        By[] applyButtonLocators = {
            By.xpath("//*[@id=\"id__777\"]"),
            By.xpath("//button[@id='id__777']"),
            By.xpath("//button[.//span[normalize-space(text())='Apply']]"),
            By.xpath("//button[contains(@aria-label,'Apply')]"),
            By.xpath("//button[contains(text(),'Apply')]"),
            By.xpath("//*[contains(@id,'id__777')]")
        };
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement applyButton = null;
        boolean found = false;
        
        // Try default content first
        for (By locator : applyButtonLocators) {
            try {
                applyButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                if (applyButton != null && applyButton.isDisplayed()) {
                    System.out.println("  ✅ Found Apply button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Apply button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for Apply button...");
            
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : applyButtonLocators) {
                        try {
                            applyButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (applyButton != null && applyButton.isDisplayed()) {
                                System.out.println("  ✅ Found Apply button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || applyButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Apply button in default content or any iframe");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", applyButton);
        waitForSeconds(1);
        try {
            applyButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyButton);
        }
        System.out.println("  ✅ Clicked on: Apply button");
        waitForSeconds(3); // Wait for table to load after Apply
    }

    /**
     * Select the checkbox (for Move To form).
     * XPath: /html/body/div[3]/div[3]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]/span/input
     */
    public void selectCheckboxMoveTo() throws Exception {
        driver.switchTo().defaultContent();
        
        // Use explicit wait for table
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr")));
            System.out.println("  ✅ Table found, looking for checkbox...");
        } catch (Exception e) {
            System.out.println("  ⚠️ Table not found yet, continuing with checkbox search...");
        }
        
        By[] checkboxLocators = {
            By.xpath("/html/body/div[3]/div[3]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]/span/input"),
            By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3']"),
            By.xpath("//td[5]/span/input"),
            By.xpath("//table//tbody//tr[2]//td[5]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr//td[5]//input[@type='checkbox']")
        };
        
        WebElement checkbox = null;
        boolean found = false;
        
        // First, quickly try to find any checkbox in table
        try {
            List<WebElement> allCheckboxes = driver.findElements(By.xpath("//table//tbody//tr//input[@type='checkbox']"));
            if (allCheckboxes.size() > 0) {
                System.out.println("  📋 Found " + allCheckboxes.size() + " checkbox(es) in table");
                checkbox = allCheckboxes.size() >= 2 ? allCheckboxes.get(1) : allCheckboxes.get(0);
                if (checkbox != null) {
                    System.out.println("  ✅ Found checkbox in table (using " + (allCheckboxes.size() >= 2 ? "second" : "first") + " checkbox)");
                    found = true;
                }
            }
        } catch (Exception e) {
            // Continue to try specific locators
        }
        
        // If not found, try specific locators with shorter timeout
        if (!found) {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (By locator : checkboxLocators) {
                try {
                    checkbox = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (checkbox != null && checkbox.isDisplayed()) {
                        System.out.println("  ✅ Found checkbox using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Checkbox not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    try {
                        List<WebElement> allCheckboxes = driver.findElements(By.xpath("//table//tbody//tr//input[@type='checkbox']"));
                        if (allCheckboxes.size() > 0) {
                            checkbox = allCheckboxes.size() >= 2 ? allCheckboxes.get(1) : allCheckboxes.get(0);
                            if (checkbox != null) {
                                System.out.println("  ✅ Found checkbox in iframe #" + i);
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        // Continue
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || checkbox == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find checkbox in default content or any iframe. Make sure the Apply button was clicked and the table is loaded.");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
            Thread.sleep(200);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        if (!checkbox.isSelected()) {
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            System.out.println("  ✅ Selected checkbox");
        } else {
            System.out.println("  ℹ️ Checkbox already selected");
        }
    }

    /**
     * Click on the Save button (for Move To form).
     * XPath: //*[@id="id__281"]
     */
    public void clickSaveButtonMoveTo() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] saveButtonLocators = {
            By.xpath("//*[@id=\"id__281\"]"),
            By.xpath("//button[@id='id__281']"),
            By.xpath("//button[contains(@id,'id__281')]"),
            By.xpath("//button[.//span[normalize-space(text())='Save']]"),
            By.xpath("//button[contains(@aria-label,'Save')]"),
            By.xpath("//button[contains(text(),'Save')]")
        };
        
        WebElement saveButton = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : saveButtonLocators) {
            try {
                saveButton = driver.findElement(locator);
                if (saveButton != null && saveButton.isDisplayed()) {
                    System.out.println("  ✅ Found Save button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : saveButtonLocators) {
                try {
                    saveButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (saveButton != null && saveButton.isDisplayed()) {
                        System.out.println("  ✅ Found Save button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Save button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : saveButtonLocators) {
                        try {
                            saveButton = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (saveButton != null && saveButton.isDisplayed()) {
                                System.out.println("  ✅ Found Save button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || saveButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Save button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            saveButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
        }
        System.out.println("  ✅ Clicked on: Save button");
        waitForSeconds(2);
    }

    /**
     * Select checkbox for the child initiative.
     * XPath: /html/body/div[3]/div[3]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]/span/input
     */
    public void selectCheckbox() throws Exception {
        driver.switchTo().defaultContent();
        
        // Use explicit wait for table (faster than fixed wait)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr")));
            System.out.println("  ✅ Table found, looking for checkbox...");
        } catch (Exception e) {
            System.out.println("  ⚠️ Table not found yet, continuing with checkbox search...");
        }
        
        By[] checkboxLocators = {
            // User-provided XPaths (prioritized)
            By.xpath("/html/body/div[2]/div[3]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]/span/input"),
            By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3']"),
            By.xpath("//td[5]/span/input"),
            // Original XPaths
            By.xpath("/html/body/div[3]/div[3]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[5]/span/input"),
            By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//table//tbody//tr[2]//td[5]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr[2]//td[5]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr[2]//td[5]//span//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr[2]//td//input[@type='checkbox']"),
            By.xpath("//div[contains(@class,'MuiDialog')]//table//tbody//tr[2]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr[2]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr//td[5]//input[@type='checkbox']"),
            By.xpath("//table//tbody//tr[contains(@class,'MuiTableRow')]//td[5]//input[@type='checkbox']"),
            By.xpath("//input[@type='checkbox' and ancestor::table]")
        };
        
        WebElement checkbox = null;
        boolean found = false;
        
        // FIRST: Quickly try to find any checkbox in table (most common case - fastest path)
        try {
            List<WebElement> allCheckboxes = driver.findElements(By.xpath("//table//tbody//tr//input[@type='checkbox']"));
            if (allCheckboxes.size() > 0) {
                System.out.println("  📋 Found " + allCheckboxes.size() + " checkbox(es) in table");
                checkbox = allCheckboxes.size() >= 2 ? allCheckboxes.get(1) : allCheckboxes.get(0);
                if (checkbox != null) {
                    System.out.println("  ✅ Found checkbox in table (using " + (allCheckboxes.size() >= 2 ? "second" : "first") + " checkbox)");
                    found = true;
                }
            }
        } catch (Exception e) {
            // Continue to try specific locators
        }
        
        // SECOND: If not found, try specific locators with shorter timeout
        if (!found) {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (By locator : checkboxLocators) {
                try {
                    checkbox = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (checkbox != null && checkbox.isDisplayed()) {
                        System.out.println("  ✅ Found checkbox using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // THIRD: If still not found, scan iframes (only if needed)
        if (!found) {
            System.out.println("  ⚠️ Checkbox not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for checkbox...");
            
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    
                    // First try finding any checkbox in table (fastest)
                    try {
                        List<WebElement> allCheckboxes = driver.findElements(By.xpath("//table//tbody//tr//input[@type='checkbox']"));
                        if (allCheckboxes.size() > 0) {
                            checkbox = allCheckboxes.size() >= 2 ? allCheckboxes.get(1) : allCheckboxes.get(0);
                            if (checkbox != null) {
                                System.out.println("  ✅ Found checkbox in iframe #" + i);
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        // Continue
                    }
                    
                    // Then try specific locators
                    if (!found) {
                        for (By locator : checkboxLocators) {
                            try {
                                checkbox = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                                if (checkbox != null && checkbox.isDisplayed()) {
                                    System.out.println("  ✅ Found checkbox in iframe #" + i + " using: " + locator);
                                    found = true;
                                    break;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || checkbox == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find checkbox in default content or any iframe. Make sure the Apply button was clicked and the table is loaded.");
        }
        
        // Scroll and click without unnecessary waits
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
            // Small wait for scroll to complete (200ms instead of 1000ms)
            Thread.sleep(200);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        if (!checkbox.isSelected()) {
            try {
                checkbox.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
            System.out.println("  ✅ Selected checkbox");
        } else {
            System.out.println("  ℹ️ Checkbox already selected");
        }
    }

    /**
     * Click on the Save button.
     * XPath: //*[@id="id__769"]
     */
    
	public static By savechild =
            By.xpath("//button[.//span[text()='Save']]");

    public void clickSaveButton() throws Exception {

        click(InitiativeLinking.savechild, "savechild");
    
    }

    /**
     * Click on the Add child button.
     * XPath: //*[@id="id__739"]
     */
    public static By add =
            By.xpath("//button[.//span[text()='Add Child']]");

    public void clickAddChildButton() throws Exception {
    	   click(InitiativeLinking.add, "add");
    }

    /**
     * Expand the Initiative (click on expand icon).
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr[1]/td[1]/button/svg
     */
    public void expandInitiative() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for page to load after Add child button
        
        // First, verify the table exists
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement table = null;
        try {
            table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody")));
            System.out.println("  ✅ Table found");
        } catch (Exception e) {
            System.out.println("  ⚠️ Table not found, trying alternative table locators...");
            try {
                table = driver.findElement(By.tagName("table"));
                System.out.println("  ✅ Table found using tag name");
            } catch (Exception e2) {
                System.out.println("  ⚠️ Could not find table: " + e2.getMessage());
            }
        }
        
        // Wait for table rows to be present
        if (table != null) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//tbody//tr")));
                List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));
                System.out.println("  📋 Found " + rows.size() + " row(s) in table");
            } catch (Exception e) {
                System.out.println("  ⚠️ No rows found in table yet, waiting...");
                waitForSeconds(2);
            }
        }
        
        By[] expandLocators = {
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr[1]/td[1]/button/svg"),
            By.xpath("//table//tbody//tr[1]//td[1]//button//svg"),
            By.xpath("//table//tbody//tr[1]//td[1]//button"),
            By.xpath("//table//tbody//tr[1]//td[1]//*[name()='svg']"),
            By.xpath("//table//tbody//tr[1]//td[1]//button[.//svg]"),
            By.xpath("//table//tbody//tr[1]//td[1]//*[contains(@class,'expand') or contains(@aria-label,'expand')]"),
            By.xpath("//button[contains(@aria-label,'expand') or contains(@aria-label,'Expand')]"),
            By.xpath("//table//tbody//tr[1]//td[1]//*")
        };
        
        WebElement expandBtn = null;
        boolean found = false;
        
        // Try default content first
        for (By locator : expandLocators) {
            try {
                expandBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                if (expandBtn != null && expandBtn.isDisplayed()) {
                    System.out.println("  ✅ Found expand button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, search ALL tables on the page
        if (!found) {
            System.out.println("  ⚠️ Expand button not found with specific locators, searching all tables on page...");
            try {
                List<WebElement> allTables = driver.findElements(By.tagName("table"));
                System.out.println("  📊 Found " + allTables.size() + " table(s) on page, searching each one...");
                
                for (int tableIndex = 0; tableIndex < allTables.size() && !found; tableIndex++) {
                    try {
                        WebElement currentTable = allTables.get(tableIndex);
                        List<WebElement> rows = currentTable.findElements(By.xpath(".//tbody//tr"));
                        System.out.println("  📋 Table #" + tableIndex + " has " + rows.size() + " row(s)");
                        
                        // Search each row in this table
                        for (int rowIndex = 0; rowIndex < rows.size() && !found; rowIndex++) {
                            try {
                                WebElement row = rows.get(rowIndex);
                                // Try to find button in first cell
                                List<WebElement> buttons = row.findElements(By.xpath(".//td[1]//button"));
                                if (buttons.size() > 0) {
                                    expandBtn = buttons.get(0);
                                    if (expandBtn != null && expandBtn.isDisplayed()) {
                                        System.out.println("  ✅ Found expand button in Table #" + tableIndex + ", Row #" + rowIndex);
                                        found = true;
                                        break;
                                    }
                                }
                                
                                // If no button, try to find any clickable element in first cell
                                if (!found) {
                                    List<WebElement> clickables = row.findElements(By.xpath(".//td[1]//*[self::button or self::svg or self::span or self::div or self::a]"));
                                    for (WebElement elem : clickables) {
                                        try {
                                            if (elem.isDisplayed() && elem.isEnabled()) {
                                                expandBtn = elem;
                                                System.out.println("  ✅ Found clickable element in Table #" + tableIndex + ", Row #" + rowIndex + ": " + elem.getTagName());
                                                found = true;
                                                break;
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
                    } catch (Exception e) {
                        System.out.println("  ⚠️ Error searching table #" + tableIndex + ": " + e.getMessage());
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not search all tables: " + e.getMessage());
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Expand button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for expand button...");
            
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    System.out.println("  🔍 Checking iframe #" + i);
                    
                    // Check if table exists in iframe
                    try {
                        driver.findElement(By.tagName("table"));
                        System.out.println("  ✅ Table found in iframe #" + i);
                    } catch (Exception e) {
                        System.out.println("  ⚠️ No table in iframe #" + i);
                    }
                    
                    for (By locator : expandLocators) {
                        try {
                            expandBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (expandBtn != null && expandBtn.isDisplayed()) {
                                System.out.println("  ✅ Found expand button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    
                    // Try finding any button in first row
                    if (!found) {
                        try {
                            List<WebElement> buttons = driver.findElements(By.xpath("//table//tbody//tr[1]//td[1]//button"));
                            if (buttons.size() > 0) {
                                expandBtn = buttons.get(0);
                                if (expandBtn != null && expandBtn.isDisplayed()) {
                                    System.out.println("  ✅ Found expand button in iframe #" + i + " (first button in first row)");
                                    found = true;
                                }
                            }
                        } catch (Exception e) {
                            // Continue
                        }
                    }
                    
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || expandBtn == null) {
            driver.switchTo().defaultContent();
            // Provide more diagnostic information
            try {
                List<WebElement> tables = driver.findElements(By.tagName("table"));
                System.out.println("  📊 Found " + tables.size() + " table(s) on page");
                if (tables.size() > 0) {
                    for (int i = 0; i < tables.size(); i++) {
                        try {
                            List<WebElement> rows = tables.get(i).findElements(By.xpath(".//tbody//tr"));
                            System.out.println("  📋 Table #" + i + " has " + rows.size() + " row(s)");
                        } catch (Exception e) {
                            System.out.println("  ⚠️ Could not count rows in table #" + i);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not analyze tables: " + e.getMessage());
            }
            throw new Exception("Could not find expand button in default content or any iframe. Please verify the table structure and that the initiative row is displayed.");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", expandBtn);
        waitForSeconds(1);
        
        try {
            expandBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", expandBtn);
        }
        System.out.println("  ✅ Clicked on: Expand Initiative button");
        waitForSeconds(2);
    }

    /**
     * Click on the Move To Link.
     * Similar to Add Child link, but for moving initiatives.
     */
    public void clickMoveToLink() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] moveToLocators = {
            By.xpath("//button[@aria-label='Move To']"),
            By.xpath("//button[contains(@aria-label,'Move To') or contains(@aria-label,'Move to')]"),
            By.xpath("//button[contains(text(),'Move To') or contains(text(),'Move to')]"),
            By.xpath("//*[contains(text(),'Move To') or contains(text(),'Move to')]")
        };
        
        WebElement moveToBtn = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : moveToLocators) {
            try {
                moveToBtn = driver.findElement(locator);
                if (moveToBtn != null && moveToBtn.isDisplayed()) {
                    System.out.println("  ✅ Found Move To link using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : moveToLocators) {
                try {
                    moveToBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (moveToBtn != null && moveToBtn.isDisplayed()) {
                        System.out.println("  ✅ Found Move To link using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        if (!found || moveToBtn == null) {
            throw new Exception("Could not find Move To link");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", moveToBtn);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue
        }
        
        try {
            moveToBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moveToBtn);
        }
        System.out.println("  ✅ Clicked on: Move To link");
        waitForSeconds(1);
    }

    /**
     * Click on the Parent Initiative text field (no value entered).
     * XPath: //*[@id="TextField252"]
     */
    public void clickParentInitiativeTextField() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] fieldLocators = {
            By.xpath("//*[@id=\"TextField252\"]"),
            By.xpath("//input[@id='TextField252']"),
            By.xpath("//*[contains(@id,'TextField252')]"),
            By.xpath("//input[contains(@id,'TextField252')]"),
            By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//input[contains(@id,'TextField252')]"),
            By.xpath("//label[contains(text(),'Parent Initiative') or contains(text(),'parent initiative')]/following-sibling::*//input")
        };
        
        WebElement field = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : fieldLocators) {
            try {
                field = driver.findElement(locator);
                if (field != null && field.isDisplayed()) {
                    System.out.println("  ✅ Found Parent Initiative text field using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : fieldLocators) {
                try {
                    field = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (field != null && field.isDisplayed()) {
                        System.out.println("  ✅ Found Parent Initiative text field using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Parent Initiative text field not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : fieldLocators) {
                        try {
                            field = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (field != null && field.isDisplayed()) {
                                System.out.println("  ✅ Found Parent Initiative text field in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || field == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Parent Initiative text field in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", field);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue
        }
        
        try {
            field.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", field);
        }
        System.out.println("  ✅ Clicked on: Parent Initiative text field");
    }

    /**
     * Click on the Move Initiative button.
     * XPath: //*[@id="id__249"]
     * Alternative: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr[4]/td[7]/div/button[2]/svg
     */
    public void clickMoveInitiativeButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Wait for page to update after Save
        
        By[] buttonLocators = {
            By.xpath("//*[@id=\"id__249\"]"),
            By.xpath("//button[@id='id__249']"),
            By.xpath("//button[contains(@id,'id__249')]"),
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr[4]/td[7]/div/button[2]/svg"),
            By.xpath("//table//tbody//tr//td[7]//div//button[2]//svg"),
            By.xpath("//table//tbody//tr//td[7]//div//button[2]"),
            By.xpath("//table//tbody//tr//td[7]//button[2]"),
            By.xpath("//table//tbody//tr//td[7]//button[contains(@aria-label,'Move') or contains(@aria-label,'move')]"),
            By.xpath("//button[contains(text(),'Move Initiative') or contains(text(),'Move initiative')]"),
            By.xpath("//button[contains(@aria-label,'Move Initiative') or contains(@aria-label,'Move initiative')]"),
            By.xpath("//*[contains(@id,'id__249')]"),
            By.xpath("//table//tbody//tr//td[7]//button"),
            By.xpath("//button[contains(@aria-label,'Move')]")
        };
        
        WebElement button = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : buttonLocators) {
            try {
                button = driver.findElement(locator);
                if (button != null && button.isDisplayed()) {
                    System.out.println("  ✅ Found Move Initiative button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, try searching in table rows
        if (!found) {
            System.out.println("  ⚠️ Move Initiative button not found with specific locators, searching in table...");
            try {
                List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
                System.out.println("  📋 Found " + rows.size() + " row(s) in table");
                for (WebElement row : rows) {
                    try {
                        // Look for button in 7th column (td[7])
                        List<WebElement> buttons = row.findElements(By.xpath(".//td[7]//button"));
                        if (buttons.size() >= 2) {
                            // Try the second button (index 1)
                            button = buttons.get(1);
                            if (button != null && button.isDisplayed()) {
                                System.out.println("  ✅ Found Move Initiative button in table row (second button in column 7)");
                                found = true;
                                break;
                            }
                        } else if (buttons.size() == 1) {
                            button = buttons.get(0);
                            if (button != null && button.isDisplayed()) {
                                System.out.println("  ✅ Found Move Initiative button in table row (only button in column 7)");
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not search table rows: " + e.getMessage());
            }
        }
        
        // If still not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : buttonLocators) {
                try {
                    button = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (button != null && button.isDisplayed()) {
                        System.out.println("  ✅ Found Move Initiative button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Move Initiative button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : buttonLocators) {
                        try {
                            button = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (button != null && button.isDisplayed()) {
                                System.out.println("  ✅ Found Move Initiative button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || button == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Move Initiative button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            Thread.sleep(200);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        System.out.println("  ✅ Clicked on: Move Initiative button");
        waitForSeconds(2);
    }

    /**
     * Click on the Remove Linking option.
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div[1]/div[6]/div/div/table/tbody/tr[2]/td[7]/div/button[2]/svg
     */
    public void clickRemoveLinkingOption() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Wait for expanded rows to be visible
        
        By[] removeLinkingLocators = {
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div[1]/div[6]/div/div/table/tbody/tr[2]/td[7]/div/button[2]/svg"),
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr[1]/td[7]/div/button[3]/svg/path"),
            By.xpath("//table//tbody//tr[2]//td[7]//div//button[2]//svg"),
            By.xpath("//table//tbody//tr[2]//td[7]//div//button[2]"),
            By.xpath("//table//tbody//tr[1]//td[7]//div//button[3]//svg//path"),
            By.xpath("//table//tbody//tr[1]//td[7]//div//button[3]//svg"),
            By.xpath("//table//tbody//tr[1]//td[7]//div//button[3]"),
            By.xpath("//table//tbody//tr//td[7]//div//button[2]"),
            By.xpath("//table//tbody//tr//td[7]//div//button[3]"),
            By.xpath("//table//tbody//tr//td[7]//button[contains(@aria-label,'Remove') or contains(@aria-label,'remove')]"),
            By.xpath("//button[contains(@aria-label,'Remove Linking') or contains(@aria-label,'remove linking')]"),
            By.xpath("//button[contains(text(),'Remove Linking') or contains(text(),'Remove linking')]")
        };
        
        WebElement removeLinkingBtn = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : removeLinkingLocators) {
            try {
                removeLinkingBtn = driver.findElement(locator);
                if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                    System.out.println("  ✅ Found Remove Linking option using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, try searching in table rows (including child/expanded rows)
        if (!found) {
            System.out.println("  ⚠️ Remove Linking option not found with specific locators, searching in table rows...");
            try {
                List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
                System.out.println("  📋 Found " + rows.size() + " row(s) in table");
                for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                    WebElement row = rows.get(rowIndex);
                    try {
                        // Look for button in 7th column (td[7])
                        List<WebElement> buttons = row.findElements(By.xpath(".//td[7]//div//button"));
                        if (buttons.size() >= 2) {
                            // Try the second button (index 1) - for tr[2]
                            removeLinkingBtn = buttons.get(1);
                            if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Remove Linking option in row #" + (rowIndex + 1) + " (second button in column 7)");
                                found = true;
                                break;
                            }
                        }
                        if (buttons.size() >= 3) {
                            // Try the third button (index 2) - for tr[1]
                            removeLinkingBtn = buttons.get(2);
                            if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Remove Linking option in row #" + (rowIndex + 1) + " (third button in column 7)");
                                found = true;
                                break;
                            }
                        } else if (buttons.size() > 0) {
                            // Try the last button
                            removeLinkingBtn = buttons.get(buttons.size() - 1);
                            if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Remove Linking option in row #" + (rowIndex + 1) + " (last button in column 7)");
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not search table rows: " + e.getMessage());
            }
        }
        
        // If still not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : removeLinkingLocators) {
                try {
                    removeLinkingBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                        System.out.println("  ✅ Found Remove Linking option using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Remove Linking option not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : removeLinkingLocators) {
                        try {
                            removeLinkingBtn = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (removeLinkingBtn != null && removeLinkingBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Remove Linking option in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || removeLinkingBtn == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Remove Linking option in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", removeLinkingBtn);
            Thread.sleep(200);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            removeLinkingBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeLinkingBtn);
        }
        System.out.println("  ✅ Clicked on: Remove Linking option");
        waitForSeconds(2); // Wait for confirmation popup to appear
    }

    /**
     * Click on the OK button on the confirmation popup page.
     * XPath: //*[@id="id__11"]
     */
    public void clickOkButtonOnConfirmationPopup() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Wait for popup to appear
        
        By[] okButtonLocators = {
            By.xpath("//*[@id=\"id__11\"]"),
            By.xpath("//button[@id='id__11']"),
            By.xpath("//button[contains(@id,'id__11')]"),
            By.xpath("//button[.//span[normalize-space(text())='OK'] or .//span[normalize-space(text())='Ok']]"),
            By.xpath("//button[contains(text(),'OK') or contains(text(),'Ok')]"),
            By.xpath("//button[contains(@aria-label,'OK') or contains(@aria-label,'Ok')]"),
            By.xpath("//*[contains(@id,'id__11')]"),
            By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//button[contains(text(),'OK') or contains(text(),'Ok')]")
        };
        
        WebElement okButton = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : okButtonLocators) {
            try {
                okButton = driver.findElement(locator);
                if (okButton != null && okButton.isDisplayed()) {
                    System.out.println("  ✅ Found OK button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : okButtonLocators) {
                try {
                    okButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (okButton != null && okButton.isDisplayed()) {
                        System.out.println("  ✅ Found OK button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ OK button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : okButtonLocators) {
                        try {
                            okButton = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (okButton != null && okButton.isDisplayed()) {
                                System.out.println("  ✅ Found OK button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || okButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find OK button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", okButton);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            okButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okButton);
        }
        System.out.println("  ✅ Clicked on: OK button");
        waitForSeconds(2); // Wait for popup to close and page to update
    }

    /**
     * Click on the expand button (for Remove Linking verification).
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div[1]/div[6]/div/div/table/tbody/tr/td[1]/button/svg
     */
    public static By clickexpand=
            By.xpath("//tbody/tr[1]/td[1]/button[1]//*[name()='svg']");

    public void clickExpandButtonForRemoveLinking() throws Exception {
    	  click(InitiativeLinking.clickexpand, "clickexpand");
    }

    /**
     * Click on the Link to Program option.
     * XPath: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr/td[7]/div/button[4]/svg
     */
    public void clickLinkToProgramOption() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] linkToProgramLocators = {
            By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div[1]/div[5]/div/div/table/tbody/tr/td[7]/div/button[4]/svg"),
            By.xpath("//table//tbody//tr//td[7]//div//button[4]//svg"),
            By.xpath("//table//tbody//tr//td[7]//div//button[4]"),
            By.xpath("//table//tbody//tr[1]//td[7]//div//button[4]//svg"),
            By.xpath("//table//tbody//tr[1]//td[7]//div//button[4]"),
            By.xpath("//button[contains(@aria-label,'Link to Program') or contains(@aria-label,'link to program')]"),
            By.xpath("//button[contains(text(),'Link to Program') or contains(text(),'Link to program')]")
        };
        
        WebElement linkToProgramBtn = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : linkToProgramLocators) {
            try {
                linkToProgramBtn = driver.findElement(locator);
                if (linkToProgramBtn != null && linkToProgramBtn.isDisplayed()) {
                    System.out.println("  ✅ Found Link to Program option using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, try searching in table rows
        if (!found) {
            System.out.println("  ⚠️ Link to Program option not found with specific locators, searching in table...");
            try {
                List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
                System.out.println("  📋 Found " + rows.size() + " row(s) in table");
                for (WebElement row : rows) {
                    try {
                        // Look for button in 7th column (td[7]), fourth button (button[4])
                        List<WebElement> buttons = row.findElements(By.xpath(".//td[7]//div//button"));
                        if (buttons.size() >= 4) {
                            // Try the fourth button (index 3)
                            linkToProgramBtn = buttons.get(3);
                            if (linkToProgramBtn != null && linkToProgramBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Link to Program option in table row (fourth button in column 7)");
                                found = true;
                                break;
                            }
                        } else if (buttons.size() > 0) {
                            // Try the last button
                            linkToProgramBtn = buttons.get(buttons.size() - 1);
                            if (linkToProgramBtn != null && linkToProgramBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Link to Program option in table row (last button in column 7)");
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Could not search table rows: " + e.getMessage());
            }
        }
        
        // If still not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : linkToProgramLocators) {
                try {
                    linkToProgramBtn = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (linkToProgramBtn != null && linkToProgramBtn.isDisplayed()) {
                        System.out.println("  ✅ Found Link to Program option using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Link to Program option not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : linkToProgramLocators) {
                        try {
                            linkToProgramBtn = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (linkToProgramBtn != null && linkToProgramBtn.isDisplayed()) {
                                System.out.println("  ✅ Found Link to Program option in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || linkToProgramBtn == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Link to Program option in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", linkToProgramBtn);
            Thread.sleep(200);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            linkToProgramBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", linkToProgramBtn);
        }
        System.out.println("  ✅ Clicked on: Link to Program option");
        waitForSeconds(2); // Wait for dialog to appear
    }

    /**
     * Select the program checkbox by program name.
     * XPath: /html/body/div[3]/div[3]/div/div[4]/table/tbody/tr[1]/td[2]/span/input
     * 
     * @param programName The name of the program to select
     */
    public void selectProgramCheckbox(String programName) throws Exception {
        if (programName == null || programName.trim().isEmpty()) {
            throw new Exception("Program name cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for dialog/table to load
        
        String searchProgramName = programName.trim();
        System.out.println("  🔍 Looking for program: '" + searchProgramName + "'");
        
        // First, try to find the table with programs
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr")));
            System.out.println("  ✅ Program table found");
        } catch (Exception e) {
            System.out.println("  ⚠️ Program table not found yet, continuing search...");
        }
        
        WebElement programCheckbox = null;
        boolean found = false;
        
        // Search for the program by name in the table (with improved matching)
        try {
            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
            System.out.println("  📋 Found " + rows.size() + " program row(s) in table");
            
            // Debug: Print all row texts to understand structure
            for (int i = 0; i < rows.size() && i < 5; i++) {
                try {
                    String rowText = rows.get(i).getText().trim();
                    if (!rowText.isEmpty()) {
                        System.out.println("    Row " + (i + 1) + " text: '" + rowText.substring(0, Math.min(100, rowText.length())) + "'");
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                WebElement row = rows.get(rowIndex);
                try {
                    String rowText = row.getText().trim();
                    // Check if this row contains the program name (case-insensitive, partial match)
                    if (rowText.toLowerCase().contains(searchProgramName.toLowerCase()) || 
                        searchProgramName.toLowerCase().contains(rowText.toLowerCase()) ||
                        rowText.equalsIgnoreCase(searchProgramName)) {
                        System.out.println("  ✅ Found matching program in row " + (rowIndex + 1) + ": '" + rowText + "'");
                        
                        // Try multiple checkbox locators in this row
                        By[] checkboxLocatorsInRow = {
                            By.xpath(".//td[2]//span//input[@type='checkbox']"),
                            By.xpath(".//td[2]//span//input"),
                            By.xpath(".//td[2]//input[@type='checkbox']"),
                            By.xpath(".//td[2]//input"),
                            By.xpath(".//td//span//input[@type='checkbox']"),
                            By.xpath(".//td//input[@type='checkbox']"),
                            By.xpath(".//input[@type='checkbox']"),
                            By.xpath(".//span//input[@type='checkbox']"),
                            By.xpath(".//input")
                        };
                        
                        for (By checkboxLocator : checkboxLocatorsInRow) {
                            try {
                                List<WebElement> checkboxes = row.findElements(checkboxLocator);
                                for (WebElement checkbox : checkboxes) {
                                    if (checkbox != null && checkbox.isDisplayed()) {
                                        String checkboxType = checkbox.getAttribute("type");
                                        if (checkboxType == null || checkboxType.equalsIgnoreCase("checkbox")) {
                                            programCheckbox = checkbox;
                                            System.out.println("  ✅ Found program checkbox in row " + (rowIndex + 1) + " using: " + checkboxLocator);
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (found) break;
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        if (found) break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Could not search program rows: " + e.getMessage());
        }
        
        // If not found, try specific XPath from user
        if (!found) {
            System.out.println("  ⚠️ Program checkbox not found by name matching, trying specific XPaths...");
            By[] checkboxLocators = {
                By.xpath("/html/body/div[3]/div[3]/div/div[4]/table/tbody/tr[1]/td[2]/span/input"),
                By.xpath("//table//tbody//tr[1]//td[2]//span//input"),
                By.xpath("//table//tbody//tr[1]//td[2]//span//input[@type='checkbox']"),
                By.xpath("//table//tbody//tr[1]//td[2]//input[@type='checkbox']"),
                By.xpath("//table//tbody//tr[1]//td[2]//input"),
                By.xpath("//table//tbody//tr//td[2]//span//input[@type='checkbox']"),
                By.xpath("//table//tbody//tr//td[2]//input[@type='checkbox']"),
                By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//table//tbody//tr//td[2]//input[@type='checkbox']")
            };
            
            for (By locator : checkboxLocators) {
                try {
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element != null && element.isDisplayed()) {
                            // Verify this checkbox is in a row containing the program name
                            try {
                                WebElement parentRow = element.findElement(By.xpath("./ancestor::tr[1]"));
                                String parentRowText = parentRow.getText().trim();
                                if (parentRowText.toLowerCase().contains(searchProgramName.toLowerCase()) || 
                                    searchProgramName.toLowerCase().contains(parentRowText.toLowerCase()) ||
                                    parentRowText.equalsIgnoreCase(searchProgramName) ||
                                    elements.size() == 1) { // If only one checkbox found, use it
                                    programCheckbox = element;
                                    System.out.println("  ✅ Found program checkbox using: " + locator);
                                    found = true;
                                    break;
                                }
                            } catch (Exception e) {
                                // If we can't find parent row, use the element if it's the only one
                                if (elements.size() == 1) {
                                    programCheckbox = element;
                                    System.out.println("  ✅ Found program checkbox using: " + locator + " (single match)");
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (found) break;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes more thoroughly
        if (!found) {
            System.out.println("  ⚠️ Program checkbox not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Found " + iframes.size() + " iframe(s)");
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    System.out.println("  🔍 Checking iframe #" + i);
                    driver.switchTo().frame(i);
                    try {
                        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
                        System.out.println("    Found " + rows.size() + " row(s) in iframe #" + i);
                        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                            WebElement row = rows.get(rowIndex);
                            String rowText = row.getText().trim();
                            if (rowText.toLowerCase().contains(searchProgramName.toLowerCase()) || 
                                searchProgramName.toLowerCase().contains(rowText.toLowerCase()) ||
                                rowText.equalsIgnoreCase(searchProgramName)) {
                                System.out.println("    ✅ Found matching program in iframe #" + i + ", row " + (rowIndex + 1));
                                List<WebElement> checkboxes = row.findElements(By.xpath(".//input[@type='checkbox'] | .//input[not(@type) or @type='']"));
                                if (checkboxes.size() > 0) {
                                    programCheckbox = checkboxes.get(0);
                                    if (programCheckbox != null) {
                                        System.out.println("  ✅ Found program checkbox in iframe #" + i + " for: " + programName);
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                        // If no match by name, try first checkbox if only one row
                        if (!found && rows.size() == 1) {
                            List<WebElement> checkboxes = rows.get(0).findElements(By.xpath(".//input[@type='checkbox'] | .//input[not(@type) or @type='']"));
                            if (checkboxes.size() > 0) {
                                programCheckbox = checkboxes.get(0);
                                System.out.println("  ✅ Found program checkbox in iframe #" + i + " (single row)");
                                found = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("    ⚠️ Error in iframe #" + i + ": " + e.getMessage());
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || programCheckbox == null) {
            driver.switchTo().defaultContent();
            // Provide helpful error message
            System.out.println("  ❌ Could not find program checkbox for: '" + searchProgramName + "'");
            System.out.println("  💡 Please verify:");
            System.out.println("     - Program name in Excel matches exactly with the program name in the dialog");
            System.out.println("     - The 'Link to Program' dialog is fully loaded");
            System.out.println("     - The program exists in the list");
            throw new Exception("Could not find program checkbox for: " + searchProgramName);
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", programCheckbox);
            Thread.sleep(300);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        // Wait a bit for checkbox to be ready
        try {
            WebDriverWait checkboxWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            checkboxWait.until(ExpectedConditions.elementToBeClickable(programCheckbox));
        } catch (Exception e) {
            System.out.println("  ⚠️ Checkbox might not be clickable yet, continuing...");
        }
        
        if (!programCheckbox.isSelected()) {
            try {
                programCheckbox.click();
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", programCheckbox);
                } catch (Exception e2) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", programCheckbox);
                }
            }
            System.out.println("  ✅ Selected program checkbox for: " + programName);
        } else {
            System.out.println("  ℹ️ Program checkbox already selected for: " + programName);
        }
        waitForSeconds(1);
    }

    /**
     * Click on the Save button (for Link to Program form).
     * XPath: //*[@id="id__284"]
     */
    public void clickSaveButtonLinkToProgram() throws Exception {
        driver.switchTo().defaultContent();
        
        By[] saveButtonLocators = {
            By.xpath("//*[@id=\"id__284\"]"),
            By.xpath("//button[@id='id__284']"),
            By.xpath("//button[contains(@id,'id__284')]"),
            By.xpath("//button[.//span[normalize-space(text())='Save']]"),
            By.xpath("//button[contains(@aria-label,'Save')]"),
            By.xpath("//button[contains(text(),'Save')]")
        };
        
        WebElement saveButton = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : saveButtonLocators) {
            try {
                saveButton = driver.findElement(locator);
                if (saveButton != null && saveButton.isDisplayed()) {
                    System.out.println("  ✅ Found Save button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : saveButtonLocators) {
                try {
                    saveButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (saveButton != null && saveButton.isDisplayed()) {
                        System.out.println("  ✅ Found Save button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ Save button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : saveButtonLocators) {
                        try {
                            saveButton = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (saveButton != null && saveButton.isDisplayed()) {
                                System.out.println("  ✅ Found Save button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || saveButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find Save button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            saveButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
        }
        System.out.println("  ✅ Clicked on: Save button");
        waitForSeconds(2);
    }

    /**
     * Click on the OK button on the confirmation popup page (for Link to Program).
     * XPath: //*[@id="id__295"]
     */
    public void clickOkButtonOnConfirmationPopupLinkToProgram() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Wait for popup to appear
        
        By[] okButtonLocators = {
            By.xpath("//*[@id=\"id__295\"]"),
            By.xpath("//button[@id='id__295']"),
            By.xpath("//button[contains(@id,'id__295')]"),
            By.xpath("//button[.//span[normalize-space(text())='OK'] or .//span[normalize-space(text())='Ok']]"),
            By.xpath("//button[contains(text(),'OK') or contains(text(),'Ok')]"),
            By.xpath("//button[contains(@aria-label,'OK') or contains(@aria-label,'Ok')]"),
            By.xpath("//*[contains(@id,'id__295')]"),
            By.xpath("//div[contains(@class,'MuiDialog') or contains(@role,'dialog')]//button[contains(text(),'OK') or contains(text(),'Ok')]")
        };
        
        WebElement okButton = null;
        boolean found = false;
        
        // First, try quick find
        for (By locator : okButtonLocators) {
            try {
                okButton = driver.findElement(locator);
                if (okButton != null && okButton.isDisplayed()) {
                    System.out.println("  ✅ Found OK button using: " + locator);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found, use explicit wait
        if (!found) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (By locator : okButtonLocators) {
                try {
                    okButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (okButton != null && okButton.isDisplayed()) {
                        System.out.println("  ✅ Found OK button using: " + locator);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        
        // If still not found, scan iframes
        if (!found) {
            System.out.println("  ⚠️ OK button not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebDriverWait iframeWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : okButtonLocators) {
                        try {
                            okButton = iframeWait.until(ExpectedConditions.elementToBeClickable(locator));
                            if (okButton != null && okButton.isDisplayed()) {
                                System.out.println("  ✅ Found OK button in iframe #" + i + " using: " + locator);
                                found = true;
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (found) break;
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || okButton == null) {
            driver.switchTo().defaultContent();
            throw new Exception("Could not find OK button in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", okButton);
            Thread.sleep(100);
        } catch (Exception e) {
            // Continue even if scroll fails
        }
        
        try {
            okButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okButton);
        }
        System.out.println("  ✅ Clicked on: OK button");
        waitForSeconds(2); // Wait for popup to close and page to update
    }

    /**
     * Verify that linked initiative is removed after expanding the initiative.
     * This method checks if the linked/child initiative is no longer displayed in the expanded section after removal.
     * Note: We check for the linked/child initiative in expanded rows (row 2+), not the parent row.
     * 
     * @param initiativeCode The initiative code to verify removal (this should be the linked/child initiative code)
     * @return true if linked initiative is removed (not found in child rows), false if still present
     */
    public boolean verifyLinkedInitiativeRemoved(String initiativeCode) throws Exception {
        if (initiativeCode == null || initiativeCode.trim().isEmpty()) {
            throw new Exception("Initiative code cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for expanded content to load and page to update
        
        // We need to check if the initiative code appears in CHILD/EXPANDED rows (row 2 or later)
        // The parent row (row 1) will always contain the initiative code, so we should ignore it
        
        boolean foundInChildRow = false;
        
        try {
            List<WebElement> allRows = driver.findElements(By.xpath("//table//tbody//tr"));
            System.out.println("  📋 Found " + allRows.size() + " row(s) in table");
            
            // Check rows starting from row 2 (child/expanded rows)
            for (int i = 1; i < allRows.size(); i++) {
                WebElement row = allRows.get(i);
                try {
                    String rowText = row.getText().trim();
                    if (rowText.contains(initiativeCode.trim())) {
                        // Check if this row is a child/expanded row
                        String rowClass = row.getAttribute("class");
                        // Check if row has child/expanded indicators or is indented
                        boolean isChildRow = (rowClass != null && (rowClass.contains("child") || rowClass.contains("expanded") || rowClass.contains("nested"))) ||
                                            row.findElements(By.xpath(".//td[1]//*[contains(@class,'indent') or contains(@style,'padding-left')]")).size() > 0;
                        
                        if (isChildRow || i >= 1) {
                            System.out.println("  ⚠️ Initiative code '" + initiativeCode + "' is still present in child/expanded row #" + (i + 1));
                            foundInChildRow = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Also try XPath-based search for child rows specifically
            if (!foundInChildRow) {
                String[] childRowXpaths = {
                    "//table//tbody//tr[position()>1]//td[contains(normalize-space(),'" + initiativeCode + "')]",
                    "//table//tbody//tr[contains(@class,'child') or contains(@class,'expanded')]//td[contains(normalize-space(),'" + initiativeCode + "')]"
                };
                
                for (String xpath : childRowXpaths) {
                    try {
                        List<WebElement> elements = driver.findElements(By.xpath(xpath));
                        for (WebElement element : elements) {
                            if (element.isDisplayed()) {
                                String text = element.getText().trim();
                                if (text.contains(initiativeCode.trim())) {
                                    System.out.println("  ⚠️ Initiative code '" + initiativeCode + "' is still present in child/expanded row");
                                    foundInChildRow = true;
                                    break;
                                }
                            }
                        }
                        if (foundInChildRow) break;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Error checking rows: " + e.getMessage());
        }
        
        if (!foundInChildRow) {
            System.out.println("  ✅ Verified: Linked initiative code '" + initiativeCode + "' has been removed (not found in child/expanded rows)");
            return true;
        } else {
            System.out.println("  ❌ Verification failed: Linked initiative code '" + initiativeCode + "' is still present in child/expanded rows");
            return false;
        }
    }

    /**
     * Verify the moved initiative is displayed after expanding the parent initiative.
     * 
     * @param movedInitiativeTitle The title of the moved initiative to verify
     * @return true if moved initiative is found, false otherwise
     */
    public boolean verifyMovedInitiativeDisplayed(String movedInitiativeTitle) throws Exception {
        if (movedInitiativeTitle == null || movedInitiativeTitle.trim().isEmpty()) {
            throw new Exception("Moved initiative title cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        // Try multiple strategies to find the moved initiative
        String[] searchXpaths = {
            "//table//tbody//tr[.//td[contains(normalize-space(),'" + movedInitiativeTitle + "')]]",
            "//table//tbody//tr//td[contains(normalize-space(),'" + movedInitiativeTitle + "')]",
            "//div[contains(text(),'" + movedInitiativeTitle + "')]",
            "//span[contains(text(),'" + movedInitiativeTitle + "')]"
        };
        
        for (String xpath : searchXpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        String text = element.getText().trim();
                        if (text.contains(movedInitiativeTitle.trim())) {
                            System.out.println("  ✅ Found moved initiative: " + movedInitiativeTitle);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        System.out.println("  ⚠️ Moved initiative not found: " + movedInitiativeTitle);
        return false;
    }

    /**
     * Verify the added child initiative is displayed after expanding the parent initiative.
     * 
     * @param childInitiativeTitle The title of the child initiative to verify
     * @return true if child initiative is found, false otherwise
     */
    public boolean verifyChildInitiativeDisplayed(String childInitiativeTitle) throws Exception {
        if (childInitiativeTitle == null || childInitiativeTitle.trim().isEmpty()) {
            throw new Exception("Child initiative title cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        waitForSeconds(2);
        
        // Try multiple strategies to find the child initiative
        String[] searchXpaths = {
            "//table//tbody//tr[.//td[contains(normalize-space(),'" + childInitiativeTitle + "')]]",
            "//table//tbody//tr//td[contains(normalize-space(),'" + childInitiativeTitle + "')]",
            "//div[contains(text(),'" + childInitiativeTitle + "')]",
            "//span[contains(text(),'" + childInitiativeTitle + "')]"
        };
        
        for (String xpath : searchXpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        String text = element.getText().trim();
                        if (text.contains(childInitiativeTitle)) {
                            System.out.println("  ✅ Verified: Child initiative '" + childInitiativeTitle + "' is displayed");
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        System.out.println("  ❌ Child initiative '" + childInitiativeTitle + "' not found after expanding parent");
        return false;
    }

    // Note: waitForSeconds() method is inherited from ActionEngine parent class
}

