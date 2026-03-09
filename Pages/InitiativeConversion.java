package Pages;

import Actions.ActionEngine;
import Locators.InitiativePageLocators;
import Locators.ProjectPageLocator;
import Locators.WarehousePageLocators;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * Page Object Model (POM) for the Converted Initiative page.
 *
 * This class is intended to hold all interactions related specifically
 * to the **Converted Initiative** view, so that they are clearly
 * separated from the generic `InitiativePage` and
 * `InitiativeStatusManagement` page objects.
 *
 * DESIGN GOALS
 * ============
 * - Keep only conversion‑specific behaviours in this page object.
 * - Reuse existing navigation from `InitiativeManagementPage` /
 *   `InitiativePage` wherever possible.
 * - As you convert tests that work on the converted initiative page,
 *   add the required methods here instead of to the generic pages.
 */
public class InitiativeConversion extends ActionEngine {

    private final WebDriver driver;
    private final ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger.
     *
     * @param driver       WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeConversion(WebDriver driver, ExtentTest reportLogger) {
        super();
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // =====================================================================
    // NAVIGATION & HIGH‑LEVEL WORKFLOWS
    // =====================================================================

    /**
     * Navigate to the Converted Initiative page from the Initiative
     * Management module.
     *
     * NOTE: Wire this method to the actual navigation steps once you
     * know the exact menu / card / link locators for the Converted
     * Initiative page.
     */
    public void navigateToConvertedInitiativePage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Step 1: Click on the Initiative Tracking module (left navigation)
        clickInitiativeTrackingModule();

        // Small wait to allow the child panel to expand
        waitForSeconds(2);

        // Step 2: Click on the Initiative Conversion page link/card
        clickInitiativeConversionPageLink();

        // Optional: small wait for page to load
        waitForSeconds(3);
    }

    /**
     * Click on the Initiative Tracking Module in the left navigation.
     *
     * XPath (provided by user):
     *   //*[@id="root\"]/div[2]/div/div[1]/div/div[2]/div[1]/div[1]/div/nav/ul/li[3]/div/span[1]/span/div/img
     */
    public void clickInitiativeTrackingModule() throws Exception {
        By primaryBy = By.xpath("//div[@aria-label='Initiative Tracking']//img[@alt='Initiative Tracking']");
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
                    // try next
                }
            }
            throw e;
        }
    }

    /**
     * Click on the Initiative Conversion page link/card inside the
     * Initiative Tracking module.
     *
     * XPath (provided by user):
     *   //*[@id=\"children-panel-container\"]/div[3]/div[2]/p
     *
     * NOTE: The absolute index‑based XPath above might correspond to a
     * different card (e.g. Initiative Linking). To make sure we really
     * click **Initiative Conversion**, we prefer a text‑based locator
     * and only fall back to the absolute XPath if needed.
     */
    public void clickInitiativeConversionPageLink() throws Exception {
        // Primary: text‑based locator to ensure we click the right card
        By primaryBy = By.xpath("//p[normalize-space()='Initiative Conversion']");
        // Fallback: original absolute XPath, in case text changes slightly
        By fallbackBy = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[2]/p");

        try {
            click(primaryBy, "Initiative Conversion page link (primary)");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary Initiative Conversion page locator (text‑based) failed, trying absolute XPath fallback: " + e.getMessage());
            click(fallbackBy, "Initiative Conversion page link (fallback absolute XPath)");
        }
    }

    // =====================================================================
    // SEARCH FILTER ACTIONS (TC_002)
    // =====================================================================

    /**
     * Click on the top Search button (icon) on Initiative Conversion page.
     *
     * Primary XPath (user-provided):
     *   //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[1]/div/img
     *
     * Fallback: previous Initiative Status Management search icon XPath.
     */
    public void clickSearchToolbarButton() throws Exception {
        By primaryBy = By.xpath("//img[@aria-label='Search']");
        By fallbackBy = By.xpath("//img[@aria-label='Search']");

        try {
            click(primaryBy, "Initiative Conversion - Search toolbar button (primary)");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary toolbar Search icon failed, trying fallback: " + e.getMessage());
            click(fallbackBy, "Initiative Conversion - Search toolbar button (fallback)");
        }
    }

    /**
     * Enter Initiative Title (from Excel) into Initiative Title text field.
     *
     * XPath: //*[@id="initiativeTitle"]
     */
    public void enterInitiativeTitle(String initiativeTitle) throws Exception {
        By titleBy = By.xpath("//*[@id=\"initiativeTitle\"]");
        type(titleBy, initiativeTitle, "Initiative Conversion - Initiative Title");
    }

    /**
     * Select Nature of Initiative = "Full Change Lifecycle" in Initiative Conversion filter.
     *
     * Dropdown XPath: //*[@id="noi"]/span[2]
     * Value:          span with text 'Full Change Lifecycle' inside any open listbox.
     */
    
    
    public static By nature = By.xpath("//span[@id='noi-option']");

    public static By naturelist = By.xpath("//div[@role='listbox']//button[@role='option']");

    
    public void selectNatureOfInitiative(String nature) throws Exception {
    	   click(InitiativeConversion.nature, "nature");
           selectFromList(InitiativeConversion.naturelist, nature, "naturelist");
    }

    /**
     * Enter Initiative Code (from Excel) into Initiative Code text field.
     *
     * XPath: //*[@id="initiativeCode"]
     */
  public static  By code = By.id("initiativeCode");
    
    public void enterInitiativeCode(String Code) {

       typeInModal(InitiativeConversion.code,Code,"code");
    }

    /**
     * Set Start Date using the Start Date date picker.
     *
     * Label XPath (user-provided): //*[@id="startDate-label"]
     * Input guess:                 //*[@id="startDate"] or //input[@name='startDate']
     *
     * The date string should be provided from Excel (format as expected by UI).
     */
    public void setStartDate(String startDate) throws Exception {
        if (startDate == null || startDate.trim().isEmpty()) {
            return; // nothing to set
        }
        String date = startDate.trim();

        By labelBy = By.xpath("//*[@id=\"startDate-label\"]");
        By inputIdBy = By.xpath("//*[@id=\"startDate\"]");
        By inputNameBy = By.xpath("//input[@name='startDate']");

        click(labelBy, "Initiative Conversion - Start Date label");
        waitForSeconds(1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = null;
        try {
            input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputIdBy));
        } catch (Exception e) {
            try {
                input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputNameBy));
            } catch (Exception e2) {
                System.out.println("  ⚠️ Start Date input not found by id/name; skipping date entry.");
                return;
            }
        }

        // Use JavaScript to set the value directly, to avoid non‑interactable issues
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", input);
        } catch (Exception ignore) {}

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "var evt = new Event('input', { bubbles: true });" +
                            "arguments[0].dispatchEvent(evt);" +
                            "var evt2 = new Event('change', { bubbles: true });" +
                            "arguments[0].dispatchEvent(evt2);",
                    input, date);
        } catch (Exception e) {
            System.out.println("  ⚠️ Failed to set Start Date via JS: " + e.getMessage());
        }
    }

    /**
     * Set End Date using the End Date date picker.
     *
     * Label XPath (user-provided): //*[@id="endDate-label"]
     * Input guess:                 //*[@id="endDate"] or //input[@name='endDate']
     */
    public void setEndDate(String endDate) throws Exception {
        if (endDate == null || endDate.trim().isEmpty()) {
            return; // nothing to set
        }
        String date = endDate.trim();

        By labelBy = By.xpath("//*[@id=\"endDate-label\"]");
        By inputIdBy = By.xpath("//*[@id=\"endDate\"]");
        By inputNameBy = By.xpath("//input[@name='endDate']");

        click(labelBy, "Initiative Conversion - End Date label");
        waitForSeconds(1);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = null;
        try {
            input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputIdBy));
        } catch (Exception e) {
            try {
                input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputNameBy));
            } catch (Exception e2) {
                System.out.println("  ⚠️ End Date input not found by id/name; skipping date entry.");
                return;
            }
        }

        // Use JavaScript to set the value directly, to avoid non‑interactable issues
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", input);
        } catch (Exception ignore) {}

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "var evt = new Event('input', { bubbles: true });" +
                            "arguments[0].dispatchEvent(evt);" +
                            "var evt2 = new Event('change', { bubbles: true });" +
                            "arguments[0].dispatchEvent(evt2);",
                    input, date);
        } catch (Exception e) {
            System.out.println("  ⚠️ Failed to set End Date via JS: " + e.getMessage());
        }
    }

    /**
     * Select "Mark as completed" status from dropdown using value from Excel.
     *
     * Dropdown XPath: //*[@id="CovertAsCompleted"]/span[2]
     * Option:         search within listbox for given text.
     */
    public void selectMarkAsCompleted(String valueFromExcel) throws Exception {
        if (valueFromExcel == null || valueFromExcel.trim().isEmpty()) {
            return; // skip if no value is provided
        }
        String value = valueFromExcel.trim();

        By dropdownBy = By.xpath("//*[@id=\"CovertAsCompleted\"]/span[2]");
        By optionBy = By.xpath("//div[@role='listbox']//span[normalize-space()='" + value + "']");

        click(dropdownBy, "Initiative Conversion - Mark as completed dropdown");
        waitForSeconds(1);
        click(optionBy, "Initiative Conversion - Mark as completed value: " + value);
    }

    /**
     * Click on the bottom Search button in the Initiative Conversion filter panel.
     *
     * Primary XPath: //*[@id="id__30"]
     * Fallback:      text-based Search button (in case id changes).
     */
    public void clickFilterSearchButton() throws Exception {
        By primaryBy = By.xpath("//button[.//span[normalize-space()='Search']]");
        By textBy = By.xpath("//button[.//span[normalize-space()='Search']]");

        try {
            click(primaryBy, "Initiative Conversion - Filter Search button (id__30)");
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary filter Search button (id__30) failed, trying text-based locator: " + e.getMessage());
            click(textBy, "Initiative Conversion - Filter Search button (text-based)");
        }
    }

    /**
     * Verify that search results are displayed OR "There are no items" message is shown.
     *
     * @return true if either at least one row is present OR the "no items" message appears.
     */
    public boolean verifySearchResultsOrNoItemsMessage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By tableRowsBy = By.xpath("//table//tbody/tr");
        By noItemsMsgBy = By.xpath("//*[contains(text(),'There are no items to show in this view')]");

        try {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsBy));
            if (rows != null && !rows.isEmpty()) {
                System.out.println("  ✅ Search results found: " + rows.size() + " row(s) displayed.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("  ℹ️ No table rows found after search, checking for 'no items' message...");
        }

        try {
            WebElement noItems = wait.until(ExpectedConditions.visibilityOfElementLocated(noItemsMsgBy));
            if (noItems != null && noItems.isDisplayed()) {
                System.out.println("  ✅ 'There are no items to show in this view.' message is displayed.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Neither rows nor 'no items' message could be confirmed.");
        }

        return false;
    }

    // =====================================================================
    // CONVERT TO PROJECT METHODS (TC_003)
    // =====================================================================

    /**
     * Click on the "Convert to Project" link for a specific initiative in the grid.
     *
     * XPath pattern: //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button/svg
     * We use dynamic XPath to find the row containing the initiative code.
     *
     * @param initiativeCode the initiative code to convert
     */
    public void clickConvertToProjectLink(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Primary: User-provided absolute XPath for TC_007
        By primaryAbsoluteBy = By.xpath("//tbody/tr[1]/td[6]/div[1]/button[1]//*[name()='svg']//*[name()='path' and contains(@d,'M17 11h3c1')]");
        // Secondary: User-provided text-based XPath (finds by text "Convert to Project")
        By primarySvgBy = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Convert to Project'])[6]//*[name()='svg'][1]");
        By primaryButtonBy = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Convert to Project'])[6]//button");
        By primaryContainerBy = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Convert to Project'])[6]");
        
        // Fallback 1: Absolute XPath (id root - first row)
        By absoluteSvgBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button/svg");
        By absoluteButtonBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button");
        
        // Fallback 2: Absolute XPath (starting from /html/body)
        By htmlBodySvgBy = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button/svg");
        By htmlBodyButtonBy = By.xpath("/html/body/div/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[6]/div/button");
        
        // Fallback 3: Dynamic XPath based on initiative code (row containing the code)
        String dynamicXpath = String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]//td[6]//button//svg",
                initiativeCode);
        By dynamicSvgBy = By.xpath(dynamicXpath);
        By dynamicButtonBy = By.xpath(String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]//td[6]//button",
                initiativeCode));

        // Helper method to try clicking with multiple strategies
        boolean clicked = false;
        By[] locators = {primaryAbsoluteBy, absoluteSvgBy, absoluteButtonBy, primarySvgBy, primaryButtonBy, primaryContainerBy, 
                         htmlBodySvgBy, htmlBodyButtonBy, dynamicSvgBy, dynamicButtonBy};
        String[] locatorNames = {"primary absolute SVG (TC_007)", "absolute SVG (id root)", "absolute button (id root)", 
                                 "text-based SVG", "text-based button", "text-based container",
                                 "absolute SVG (/html/body)", "absolute button (/html/body)",
                                 "dynamic SVG", "dynamic button"};
        
        for (int i = 0; i < locators.length && !clicked; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locators[i]));
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                waitForSeconds(1);
                
                // Try multiple click strategies
                boolean elementClicked = false;
                
                // Strategy 1: Regular click
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                    element.click();
                    System.out.println("  ✅ Clicked Convert to Project link using " + locatorNames[i] + " (regular click)");
                    elementClicked = true;
                } catch (Exception e1) {
                    // Strategy 2: JavaScript click
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                        System.out.println("  ✅ Clicked Convert to Project link using " + locatorNames[i] + " (JavaScript click)");
                        elementClicked = true;
                    } catch (Exception e2) {
                        // Strategy 3: Actions click
                        try {
                            Actions actions = new Actions(driver);
                            actions.moveToElement(element).click().perform();
                            System.out.println("  ✅ Clicked Convert to Project link using " + locatorNames[i] + " (Actions click)");
                            elementClicked = true;
                        } catch (Exception e3) {
                            // Strategy 4: Try clicking parent button
                            try {
                                WebElement parentButton = element.findElement(By.xpath("./ancestor-or-self::button"));
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);
                                System.out.println("  ✅ Clicked Convert to Project link using " + locatorNames[i] + " (parent button)");
                                elementClicked = true;
                            } catch (Exception e4) {
                                System.out.println("  ⚠️ All click strategies failed for " + locatorNames[i] + ", trying next locator...");
                            }
                        }
                    }
                }
                
                if (elementClicked) {
                    clicked = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Element not found with " + locatorNames[i] + ", trying next locator...");
            }
        }
        
        if (!clicked) {
            throw new Exception("Failed to click Convert to Project link using all locator strategies and click methods");
        }
        
        // Wait for popup/form to appear and verify it's accessible
        waitForSeconds(3);
        
        // Verify the form is accessible by checking for Abbreviated Name field
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By abbreviatedNameField = By.xpath("//*[@id=\"shortJobTitle\"]");
        
        boolean formFound = false;
        
        // Try default content first
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(abbreviatedNameField));
            System.out.println("  ✅ Convert to Project form detected in default content");
            formFound = true;
        } catch (Exception e) {
            System.out.println("  ⚠️ Form not found in default content, checking iframes...");
        }
        
        // If not found in default content, check iframes
        if (!formFound) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Found " + iframes.size() + " iframe(s), scanning for form...");
            
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(abbreviatedNameField));
                        System.out.println("  ✅ Convert to Project form detected in iframe #" + i);
                        formFound = true;
                        break;
                    } catch (Exception e) {
                        // Not in this iframe, continue
                    }
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!formFound) {
            throw new Exception("Convert to Project form did not appear after clicking the link. Abbreviated Name field not found in default content or any iframe.");
        }
        
        waitForSeconds(1); // Additional wait for form to fully load
    }

    /**
     * Helper method to type into a field with iframe handling.
     * Scans default content and all iframes to find the element.
     */
    private void typeWithIframeHandling(By locator, String text, String fieldName) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        boolean found = false;
        
        // Try default content first
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            element.clear();
            element.sendKeys(text);
            System.out.println("  ✅ Typed into " + fieldName + " (default content)");
            found = true;
        } catch (Exception e) {
            // Try iframes
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    try {
                        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                        element.clear();
                        element.sendKeys(text);
                        System.out.println("  ✅ Typed into " + fieldName + " (iframe #" + i + ")");
                        found = true;
                        break;
                    } catch (Exception e2) {
                        // Not in this iframe
                    }
                    driver.switchTo().defaultContent();
                } catch (Exception e3) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found) {
            throw new Exception("Could not find " + fieldName + " field in default content or any iframe");
        }
    }

    /**
     * Enter Abbreviated Name (auto-generated).
     * XPath: //*[@id="shortJobTitle"]
     */
    public  void  enterAbbreviatedName(String abbreviatedName) throws Exception {
       
        
       typeWithIframeHandling(By.xpath("//*[@id=\"shortJobTitle\"]"), abbreviatedName, "Abbreviated Name");
    }

    public static String generateAbbreviatedName() {
        int random = new Random().nextInt(90000) + 10000;
        return "AP" + random;
    }
    /**
     * Enter Number of Resources = 5.
     * XPath: //*[@id="noresours"]
     */
    public void enterNumberOfResources(String numberOfResources) throws Exception {
        typeWithIframeHandling(By.xpath("//*[@id=\"noresours\"]"), numberOfResources, "Number of Resources");
    }

    /**
     * Enter Description.
     * XPath: //*[@id="description"]
     */
    public void enterDescription(String description) throws Exception {
        typeWithIframeHandling(By.xpath("//*[@id=\"description\"]"), description, "Description");
    }

    /**
     * Select Customer from dropdown.
     * XPath: //*[@id="customerID"]/span[2]
     */
    public static By click =
            By.xpath("//span[@id='customerID-option']");

    public static By selectoption =
            By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public void selectCustomer() throws Exception {
    //    if (customerValue == null || customerValue.trim().isEmpty()) return;
    //   selectDropdownValue("//*[@id=\"customerID\"]/span[2]", customerValue, "Customer");
    	click(InitiativeConversion.click,"click");
    	selectRandomValueFromDropdown(InitiativeConversion.selectoption,"selectoption"); 
        
    }

    /**
     * Select Commercial Details from dropdown.
     * XPath: //*[@id="contractTypeID"]/span[2]
     */
    public static By click1 =
            By.xpath("//span[@id='contractTypeID-option']");

    public static By selectoption1 =
            By.xpath("//div[@role='listbox']//button[@role='option']");
    public void selectCommercialDetails() throws Exception {
      //  if (commercialDetailsValue == null || commercialDetailsValue.trim().isEmpty()) return;
     //   selectDropdownValue("//*[@id=\"contractTypeID\"]/span[2]", commercialDetailsValue, "Commercial Details");
    	click(InitiativeConversion.click1,"click1");
    	selectRandomValueFromDropdown(InitiativeConversion.selectoption1,"selectoption1"); 
    }

    /**
     * Enter Work (hrs) from Excel.
     * XPath: //*[@id="estimatedEfforts"]
     */
    public void enterWorkHours(String workHours) throws Exception {
        By hoursBy = By.xpath("//*[@id=\"estimatedEfforts\"]");
        type(hoursBy, workHours, "Work (hrs)");
    }

    /**
     * Enter Project Value from Excel.
     * XPath: //*[@id="contractValue"]
     */
    public void enterProjectValue(String projectValue) throws Exception {
        typeWithIframeHandling(By.xpath("//input[@id='contractValue']"), projectValue, "Project Value");
    }

    /**
     * Select Project Currency from dropdown.
     * XPath: //*[@id="billingCurrencyID"]/span[2]
     */
    
    public static By currency = By.xpath("//span[@id='billingCurrencyID-option']");
    
    public static By currencylist = By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public void selectProjectCurrency() throws Exception {
    	   click(InitiativeConversion.currency, "Currency");
    		selectRandomValueFromDropdown(InitiativeConversion.currencylist,"currencylist"); 
    }

    /**
     * Select Business Group from dropdown.
     * XPath: //*[@id="businessGroupID"]/span[2]
     */
  public static By bg= By.xpath("//span[@id='businessGroupID-option']");
    
    public static By bglist = By.xpath("//div[@id='businessGroupID-list']//button[@role='option'][not(.//span[text()='Select Business Group'])][1]");
    
    
    public void selectBusinessGroup() throws Exception {
    	click(InitiativeConversion.bg,"bg");
    	selectRandomValueFromDropdown(InitiativeConversion.bglist,"bglist");
    }

    /**
     * Select Organization Unit from dropdown.
     * XPath: //*[@id="locationID"]/span[2]
     */
    
 public static By ou= By.xpath("//span[@id='locationID-option']");
    
    public static By oulist = By.xpath("(//div[@role='listbox']//button[@role='option'])[2]");
    
    public void selectOrganizationUnit() throws Exception {
    	click(InitiativeConversion.ou,"ou");
    	selectRandomValueFromDropdown(InitiativeConversion.oulist,"oulist");
    }

    /**
     * Select Practice Template from dropdown.
     * XPath: //*[@id="projectType"]/span[2]
     */
    
 public static By pt= By.xpath("//span[@id='projectType-option']");
    
    public static By ptlist = By.xpath("//div[@role='listbox']//button[@role='option']");
    public void selectPracticeTemplate() throws Exception {
    	click(InitiativeConversion.pt,"pt");
    	selectRandomValueFromDropdown(InitiativeConversion.ptlist,"ptlist");
    }

    /**
     * Robust dropdown value selection helper method.
     * Handles iframes, flexible text matching, scrolling, and multiple click strategies.
     * 
     * @param dropdownXpath XPath for the dropdown trigger element
     * @param valueToSelect The value to select from the dropdown
     * @param dropdownName Name of the dropdown for logging (e.g., "Customer", "Business Group")
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
            
            if (!dropdownFound || dropdown == null) {
                throw new Exception("Could not find " + dropdownName + " dropdown in default content or any iframe");
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
                        // Try clicking parent element
                        try {
                            WebElement parent = dropdown.findElement(By.xpath("./ancestor::div[1] | ./ancestor::button[1] | ./parent::*"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parent);
                            System.out.println("  ✅ Clicked on " + dropdownName + " dropdown (parent element)");
                        } catch (Exception e3) {
                            // Try direct click as last resort
                            dropdown.click();
                            System.out.println("  ✅ Clicked on " + dropdownName + " dropdown (direct)");
                        }
                    }
                }
            } catch (Exception e) {
                throw new Exception("Failed to click " + dropdownName + " dropdown after all strategies: " + e.getMessage());
            }
            
            // Step 2: Wait for dropdown options to appear
            waitForSeconds(3);
            
            // Step 3: Check for iframes and find options
            // If dropdown was in an iframe, stay in that iframe context to find options
            if (dropdownIframeIndex >= 0) {
                driver.switchTo().frame(dropdownIframeIndex);
            } else {
                driver.switchTo().defaultContent();
            }
            
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Found " + iframes.size() + " iframe(s) after clicking dropdown");
            
            WebElement option = null;
            boolean found = false;
            int iframeIndex = -1;
            
            // Strategy 1: Try current context first (default or iframe where dropdown was found)
            try {
                List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span | //div[@role='listbox']//div | //ul[@role='listbox']//li | //div[@role='option']"));
                System.out.println("  🔍 Found " + options.size() + " options in current context");
                
                for (int i = 0; i < options.size(); i++) {
                    WebElement opt = options.get(i);
                    if (!opt.isDisplayed()) continue;
                    
                    String optionText = opt.getText().trim();
                    if (optionText.isEmpty()) {
                        optionText = opt.getAttribute("textContent");
                        if (optionText != null) optionText = optionText.trim();
                        else optionText = "";
                    }
                    
                    String normalizedOptionText = optionText.replaceAll("\\s+", " ").toLowerCase();
                    
                    // Skip placeholder options
                    if (optionText.isEmpty() || optionText.equalsIgnoreCase("Select") || 
                        optionText.equalsIgnoreCase("--Select--") || optionText.equalsIgnoreCase("Select " + dropdownName)) {
                        continue;
                    }
                    
                    // Try matching - improved logic
                    boolean isMatch = false;
                    String matchType = "";
                    if (normalizedOptionText.equals(normalizedSearchValue)) {
                        isMatch = true;
                        matchType = "exact";
                    } else if (normalizedOptionText.contains(normalizedSearchValue)) {
                        // Only use contains if the search value is at least 3 characters to avoid false matches
                        if (normalizedSearchValue.length() >= 3) {
                            isMatch = true;
                            matchType = "contains";
                        }
                    } else if (normalizedSearchValue.contains(normalizedOptionText) && normalizedOptionText.length() >= 5) {
                        // Allow reverse contains if option text is at least 5 chars (e.g., "in progress" matches "Analysis in progress")
                        isMatch = true;
                        matchType = "reverse contains";
                    } else {
                        // Last resort: Try word-based matching (split search value into words and check if any word matches)
                        String[] searchWords = normalizedSearchValue.split("\\s+");
                        for (String word : searchWords) {
                            if (word.length() >= 4 && normalizedOptionText.contains(word)) {
                                isMatch = true;
                                matchType = "word-based match";
                                break;
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
            
            // Strategy 2: Try other iframes if not found in current context
            if (!found && iframes.size() > 0) {
                // Switch back to default first
                driver.switchTo().defaultContent();
                for (int i = 0; i < iframes.size(); i++) {
                    // Skip the iframe we already checked (if dropdown was in an iframe)
                    if (dropdownIframeIndex >= 0 && i == dropdownIframeIndex) {
                        continue;
                    }
                    try {
                        driver.switchTo().frame(i);
                        System.out.println("  🔍 Scanning iframe #" + i);
                        
                        List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span | //div[@role='listbox']//div | //ul[@role='listbox']//li | //div[@role='option']"));
                        System.out.println("     Found " + options.size() + " options in iframe #" + i);
                        
                        for (WebElement opt : options) {
                            if (!opt.isDisplayed()) continue;
                            
                            String optionText = opt.getText().trim();
                            if (optionText.isEmpty()) {
                                optionText = opt.getAttribute("textContent");
                                if (optionText != null) optionText = optionText.trim();
                                else optionText = "";
                            }
                            
                            String normalizedOptionText = optionText.replaceAll("\\s+", " ").toLowerCase();
                            
                            if (optionText.isEmpty() || optionText.equalsIgnoreCase("Select") || 
                                optionText.equalsIgnoreCase("--Select--")) {
                                continue;
                            }
                            
                            boolean isMatch = false;
                            String matchType = "";
                            if (normalizedOptionText.equals(normalizedSearchValue)) {
                                isMatch = true;
                                matchType = "exact";
                            } else if (normalizedOptionText.contains(normalizedSearchValue)) {
                                // Only use contains if the search value is at least 3 characters to avoid false matches
                                if (normalizedSearchValue.length() >= 3) {
                                    isMatch = true;
                                    matchType = "contains";
                                }
                            }
                            // Removed reverse contains matching as it causes wrong selections
                            
                            if (isMatch) {
                                System.out.println("     ✅ " + matchType + " match found in iframe #" + i + ": '" + optionText + "' (searching for: '" + valueToSelect + "')");
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", opt);
                                waitForSeconds(1);
                                wait.until(ExpectedConditions.visibilityOf(opt));
                                wait.until(ExpectedConditions.elementToBeClickable(opt));
                                option = opt;
                                found = true;
                                iframeIndex = i;
                                break;
                            }
                        }
                        
                        if (found) break;
                        driver.switchTo().defaultContent();
                    } catch (Exception e) {
                        System.out.println("  ⚠️ Error scanning iframe #" + i + ": " + e.getMessage());
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
            if (!found || option == null) {
                driver.switchTo().defaultContent();
                throw new Exception("Could not find " + dropdownName + " option '" + value + "' in default content or any iframe");
            }
            
            // Step 4: Click the option using multiple strategies
            try {
                // Ensure we're in the right context
                if (iframeIndex >= 0) {
                    driver.switchTo().frame(iframeIndex);
                } else {
                    driver.switchTo().defaultContent();
                }
                
                // Refresh element to avoid stale element
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", option);
                waitForSeconds(1);
                
                // Try JavaScript click first (most reliable)
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("  ✅ Clicked " + dropdownName + " option using JavaScript");
                    waitForSeconds(1);
                } catch (Exception jsError) {
                    System.out.println("  ⚠️ JavaScript click failed, trying Actions click...");
                    try {
                        actions.moveToElement(option).click().perform();
                        System.out.println("  ✅ Clicked " + dropdownName + " option using Actions");
                        waitForSeconds(1);
                    } catch (Exception actionsError) {
                        System.out.println("  ⚠️ Actions click also failed, trying direct click...");
                        option.click();
                        System.out.println("  ✅ Clicked " + dropdownName + " option using direct click");
                        waitForSeconds(1);
                    }
                }
                
                waitForSeconds(1);
                System.out.println("  ✅ Selected " + dropdownName + ": " + value);
            } catch (Exception e) {
                System.err.println("  ❌ Error clicking option: " + e.getMessage());
                // Try one last time with JavaScript
                try {
                    if (iframeIndex >= 0) {
                        driver.switchTo().frame(iframeIndex);
                    } else {
                        driver.switchTo().defaultContent();
                    }
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("  ✅ Successfully clicked using final JavaScript attempt");
                } catch (Exception finalError) {
                    throw new Exception("Failed to click " + dropdownName + " option after all attempts: " + e.getMessage());
                }
            }
            
            // Switch back to default content
            driver.switchTo().defaultContent();
            waitForSeconds(1);
            
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            throw new Exception("Error selecting " + dropdownName + " value '" + value + "': " + e.getMessage());
        }
    }

    /**
     * Click Save button on Convert to Project form.
     * XPath: //*[@id="id__1232"]
     */
    
   
    public static By save= By.xpath("//button[.//span[normalize-space()='Save']]");
    
    
    public void clickSaveButton() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until Save button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(InitiativeConversion.save));

        click(InitiativeConversion.save, "save");

        System.out.println("✅ Save button clicked");
    }

    public void closeUpdateHealthSheet() {

	    JavascriptExecutor js = (JavascriptExecutor) webDriver;

	    js.executeScript(
	        "const el = document.elementFromPoint(50, window.innerHeight / 2);" +
	        "if (el) el.click();"
	    );
	}
    
    
    /**
     * Verify that an initiative code is NOT displayed in the Initiative Conversion grid.
     * This indicates successful conversion to project (initiative removed from conversion list).
     *
     * @param initiativeCode the initiative code to verify is NOT present
     * @return true if initiative is NOT found (conversion successful), false if still present
     */
    public boolean verifyInitiativeNotDisplayedAfterConversion(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By tableRowsBy = By.xpath("//table//tbody/tr");

        try {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsBy));
            if (rows != null && !rows.isEmpty()) {
                for (WebElement row : rows) {
                    if (row.isDisplayed() && row.getText().contains(initiativeCode)) {
                        System.out.println("  ❌ Initiative code '" + initiativeCode + "' is still displayed in the grid (conversion may have failed).");
                        return false;
                    }
                }
            }
            System.out.println("  ✅ Initiative code '" + initiativeCode + "' is NOT displayed (conversion successful).");
            return true;
        } catch (Exception e) {
            // If no rows found at all, that's also fine (empty grid = conversion successful)
            System.out.println("  ✅ No rows found in grid - initiative successfully converted (not displayed).");
            return true;
        }
    }

    /**
     * Open a specific converted initiative by its code.
     *
     * @param initiativeCode unique identifier of the converted initiative
     */
    public void openConvertedInitiativeByCode(String initiativeCode) throws Exception {
        // TODO: Implement search & open logic for converted initiatives.
        if (reportLogger != null) {
            reportLogger.info("[TODO] Open converted initiative with code: " + initiativeCode);
        }
    }

    /**
     * Verify key details on the Converted Initiative page.
     *
     * @param initiativeCode the expected initiative code
     * @return true if verification passes, false otherwise
     */
    public boolean verifyConvertedInitiativeDetails(String initiativeCode) throws Exception {
        // TODO: Implement verification of converted initiative details.
        if (reportLogger != null) {
            reportLogger.info("[TODO] Verify converted initiative details for code: " + initiativeCode);
        }
        return true;
    }

    /**
     * Perform any post‑conversion actions (e.g. approve, reject, etc.).
     * Extend this method or add more granular methods as needed.
     */
    public void performPostConversionActions() throws Exception {
        // TODO: Implement post‑conversion actions on the Converted Initiative page.
        if (reportLogger != null) {
            reportLogger.info("[TODO] Implement post‑conversion actions on Converted Initiative page.");
        }
    }

    // =====================================================================
    // PAGINATION METHODS (TC_005)
    // =====================================================================

    /**
     * Click on the 2nd page button in the pagination control on Initiative Conversion page.
     *
     * XPath (user-provided):
     *   //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/div[2]/button[2]/svg/path
     *
     * We click the ancestor button of this SVG path to ensure the real clickable element is used.
     */
    public void clickSecondPageInPagination() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);

        // Primary: button element (more reliable than SVG path)
        By buttonBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/button[2]");
        // Fallback: SVG path (user-provided)
        By svgPathBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/button[2]/svg/path");

        try {
            // Prefer clicking the button element
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonBy));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("  ✅ Clicked pagination page 2 button (JavaScript)");
            } catch (Exception e1) {
                button.click();
                System.out.println("  ✅ Clicked pagination page 2 button (direct click)");
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Primary Page 2 button locator failed, trying SVG path ancestor: " + e.getMessage());
            // Fallback: locate the SVG/path and then its ancestor button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement svgPath = wait.until(ExpectedConditions.presenceOfElementLocated(svgPathBy));
            WebElement button = svgPath.findElement(By.xpath("./ancestor::button[1]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waitForSeconds(1);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("  ✅ Clicked pagination page 2 button via SVG path ancestor (JavaScript)");
            } catch (Exception e2) {
                button.click();
                System.out.println("  ✅ Clicked pagination page 2 button via SVG path ancestor (direct click)");
            }
        }
        waitForSeconds(2);
    }

    /**
     * Get the number of data rows currently visible in the Initiative Conversion grid.
     * Used by TC_005 to verify that exactly 5 records are shown per page.
     *
     * @return the number of visible rows in the grid
     */
    public int getCurrentConversionGridRowCount() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Try standard HTML table rows first
        By tableRowsBy = By.xpath("//table//tbody/tr");
        // Fallback for AG-Grid style rows
        By agRowsBy = By.xpath("//div[contains(@class,'ag-center-cols-container')]//div[contains(@class,'ag-row') and not(contains(@class,'ag-row-position-absolute'))]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsBy));
            int count = (rows == null) ? 0 : rows.size();
            System.out.println("  ℹ️ Current Conversion grid row count (HTML table) = " + count);
            return count;
        } catch (Exception e) {
            System.out.println("  ⚠️ Table row locator failed for Conversion grid, trying AG-Grid row locator: " + e.getMessage());
            try {
                List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(agRowsBy));
                int count = (rows == null) ? 0 : rows.size();
                System.out.println("  ℹ️ Current Conversion grid row count (AG-Grid) = " + count);
                return count;
            } catch (Exception e2) {
                System.out.println("  ❌ Failed to determine Conversion grid row count: " + e2.getMessage());
                return 0;
            }
        }
    }

    // =====================================================================
    // CONVERT TO MILESTONE METHODS (TC_004)
    // =====================================================================

    /**
     * Click on the "Convert to Milestone" link for a specific initiative in the grid.
     *
     * XPath (user-provided):
     *   //*[@id="root"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[7]/div/button/svg
     *
     * @param initiativeCode the initiative code to convert
     */
    public void clickConvertToMilestoneLink(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2);

        // Primary: User-provided absolute XPath (first row)
        By primarySvgBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[7]/div/button/svg");
        By primaryButtonBy = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[7]/div/button");
        
        // Fallback: Dynamic XPath based on initiative code (row containing the code)
        String dynamicXpath = String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]//td[7]//button//svg",
                initiativeCode);
        By dynamicSvgBy = By.xpath(dynamicXpath);
        By dynamicButtonBy = By.xpath(String.format(
                "//table//tbody//tr[.//td[contains(normalize-space(),'%s')]]//td[7]//button",
                initiativeCode));

        boolean clicked = false;
        By[] locators = {primaryButtonBy, primarySvgBy, dynamicButtonBy, dynamicSvgBy};
        String[] locatorNames = {"primary button", "primary SVG", "dynamic button", "dynamic SVG"};
        
        for (int i = 0; i < locators.length && !clicked; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locators[i]));
                
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                waitForSeconds(1);
                
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                    element.click();
                    System.out.println("  ✅ Clicked Convert to Milestone link using " + locatorNames[i] + " (regular click)");
                    clicked = true;
                } catch (Exception e1) {
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                        System.out.println("  ✅ Clicked Convert to Milestone link using " + locatorNames[i] + " (JavaScript click)");
                        clicked = true;
                    } catch (Exception e2) {
                        System.out.println("  ⚠️ Failed to click with " + locatorNames[i] + ", trying next locator...");
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Element not found with " + locatorNames[i] + ", trying next locator...");
            }
        }
        
        if (!clicked) {
            throw new Exception("Failed to click Convert to Milestone link using all locator strategies");
        }
        
        // Wait for popup/form to appear and verify it's accessible
        waitForSeconds(3);
        
        // Verify the form is accessible by checking for Project Name field
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By projectNameField = By.xpath("//*[@id=\"projectName\"]/span[2]");
        
        boolean formFound = false;
        
        // Try default content first
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(projectNameField));
            System.out.println("  ✅ Convert to Milestone form detected in default content");
            formFound = true;
        } catch (Exception e) {
            System.out.println("  ⚠️ Form not found in default content, checking iframes...");
        }
        
        // If not found in default content, check iframes
        if (!formFound) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Found " + iframes.size() + " iframe(s), scanning for form...");
            
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(projectNameField));
                        System.out.println("  ✅ Convert to Milestone form detected in iframe #" + i);
                        formFound = true;
                        break;
                    } catch (Exception e) {
                        // Not in this iframe, continue
                    }
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!formFound) {
            throw new Exception("Convert to Milestone form did not appear after clicking the link. Project Name field not found in default content or any iframe.");
        }
        
        waitForSeconds(1); // Additional wait for form to fully load
    }

    /**
     * Select Project Name from dropdown.
     * XPath: //*[@id="projectName"]/span[2]
     */
       public static By projectclick = By.xpath("//span[@id='projectName-option']");
    
                        public static By projectlist= By.xpath("//div[@role='listbox']//button[@role='option']");
    
    public void selectProjectName(String projectName) throws Exception {
       // if (projectName == null || projectName.trim().isEmpty()) return;
       // selectDropdownValue("//*[@id=\"projectName\"]/span[2]", projectName, "Project Name");
    	   click(InitiativeConversion.projectclick, "Project Currency");
           selectFromList(InitiativeConversion.projectlist, projectName, "projectName");
    }

    /**
     * Enter Bill Amount.
     * XPath: //*[@id="TextField150"]
     */
    public void enterBillAmount(String billAmount) throws Exception {
        if (billAmount == null || billAmount.trim().isEmpty()) return;
        
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        // Primary locator
        By primaryBy = By.xpath("//*[@id=\"TextField150\"]");
        // Fallback locators
        By inputNameBy = By.xpath("//input[@name='TextField150' or @name='billAmount']");
        By inputPlaceholderBy = By.xpath("//input[contains(@placeholder,'Bill') or contains(@placeholder,'Amount')]");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = null;
        boolean found = false;
        
        // Try primary locator first
        By[] locators = {primaryBy, inputNameBy, inputPlaceholderBy};
        String[] locatorNames = {"id TextField150", "name attribute", "placeholder"};
        
        // Try default content first
        for (int i = 0; i < locators.length && !found; i++) {
            try {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[i]));
                System.out.println("  ✅ Found Bill Amount field using " + locatorNames[i] + " (default content)");
                found = true;
                break;
            } catch (Exception e) {
                // Try next locator
            }
        }
        
        // If not found in default content, try iframes
        if (!found) {
            System.out.println("  ⚠️ Bill Amount field not found in default content, checking iframes...");
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (int j = 0; j < locators.length && !found; j++) {
                        try {
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[j]));
                            System.out.println("  ✅ Found Bill Amount field using " + locatorNames[j] + " (iframe #" + i + ")");
                            found = true;
                            break;
                        } catch (Exception e) {
                            // Try next locator
                        }
                    }
                    if (!found) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!found || element == null) {
            throw new Exception("Could not find Bill Amount field in default content or any iframe using all locator strategies");
        }
        
        // Perform typing
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            waitForSeconds(1);
            element.clear();
            element.sendKeys(billAmount);
            System.out.println("  ✅ Typed into Bill Amount: " + billAmount);
        } catch (Exception e) {
            System.out.println("  ⚠️ Failed to type into Bill Amount directly, trying JS: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, billAmount);
            System.out.println("  ✅ Typed into Bill Amount via JS: " + billAmount);
        }
        waitForSeconds(1);
        driver.switchTo().defaultContent();
    }

    /**
     * Check checkbox based on value from Excel.
     * Options: "Ready for Billing", "Invoice Printed", "Analysis Applicable"
     * XPaths:
     *   Ready for Billing: //*[@id="readyForBilling"]
     *   Invoice Printed: //*[@id="invoicePrinted"]
     *   Analysis Applicable: //*[@id="analysisApplicable"]
     */
    public void checkCheckbox(String checkboxValue) throws Exception {
        if (checkboxValue == null || checkboxValue.trim().isEmpty()) return;
        
        String value = checkboxValue.trim();
        By checkboxBy = null;
        String checkboxName = "";
        
        if (value.equalsIgnoreCase("Ready for Billing")) {
            checkboxBy = By.xpath("//*[@id=\"readyForBilling\"]");
            checkboxName = "Ready for Billing";
        } else if (value.equalsIgnoreCase("Invoice Printed")) {
            checkboxBy = By.xpath("//*[@id=\"invoicePrinted\"]");
            checkboxName = "Invoice Printed";
        } else if (value.equalsIgnoreCase("Analysis Applicable")) {
            checkboxBy = By.xpath("//*[@id=\"analysisApplicable\"]");
            checkboxName = "Analysis Applicable";
        } else {
            throw new Exception("Unknown checkbox value: " + value + ". Expected: 'Ready for Billing', 'Invoice Printed', or 'Analysis Applicable'");
        }
        
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement checkbox = null;
        
        // Try default content first
        try {
            checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxBy));
            System.out.println("  ✅ Found " + checkboxName + " checkbox (default content)");
        } catch (Exception e) {
            // Try iframes
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxBy));
                    System.out.println("  ✅ Found " + checkboxName + " checkbox (iframe #" + i + ")");
                    break;
                } catch (Exception ex) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (checkbox == null) {
            throw new Exception("Could not find " + checkboxName + " checkbox in default content or any iframe");
        }
        
        // Check the checkbox if not already checked
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
            waitForSeconds(1);
            if (!checkbox.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                System.out.println("  ✅ Checked " + checkboxName + " checkbox");
            } else {
                System.out.println("  ℹ️ " + checkboxName + " checkbox is already checked");
            }
        } catch (Exception e) {
            throw new Exception("Failed to check " + checkboxName + " checkbox: " + e.getMessage());
        }
        
        waitForSeconds(1);
        driver.switchTo().defaultContent();
    }

    /**
     * Select Currency from dropdown.
     * XPath: //*[@id="Dropdown155"]/span[2]
     * Also tries alternative locators if primary fails.
     */
    public void selectCurrency(String currency) throws Exception {
        if (currency == null || currency.trim().isEmpty()) return;
        
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait longer for form to stabilize after checkbox
        
        // First, scan iframes to see if dropdown is there
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for Currency dropdown...");
        
        // Try multiple locator strategies in default content and iframes
        String[] dropdownXpaths = {
            "//*[@id=\"Dropdown155\"]/span[2]",  // Primary
            "//*[@id=\"Dropdown155\"]",  // Container
            "//div[@id='Dropdown155']//span[2]",  // Div with span
            "//div[contains(@id,'Dropdown155')]//span[2]",  // Flexible ID
            "//div[contains(@id,'Dropdown155')]",  // Flexible container
            "//label[contains(text(),'Currency') or contains(text(),'currency')]/following-sibling::*//span[2]",  // Label-based
            "//label[contains(text(),'Currency') or contains(text(),'currency')]/following::*[@id][1]//span[2]",  // Label following
            "//*[contains(@aria-label,'Currency') or contains(@placeholder,'Currency')]//span[2]",  // Aria/placeholder
            "//div[@role='combobox' and contains(@id,'Dropdown')]//span[2]",  // Generic combobox
            "//*[contains(@id,'Dropdown') and contains(@id,'155')]//span[2]"  // Partial ID match
        };
        
        boolean success = false;
        Exception lastException = null;
        
        // Try each XPath in default content first
        for (String xpath : dropdownXpaths) {
            try {
                System.out.println("  🔍 Trying Currency dropdown locator (default content): " + xpath);
                selectDropdownValue(xpath, currency, "Currency");
                success = true;
                break;
            } catch (Exception e) {
                lastException = e;
                // Try in iframes
                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        System.out.println("  🔍 Trying Currency dropdown locator (iframe #" + i + "): " + xpath);
                        selectDropdownValue(xpath, currency, "Currency");
                        success = true;
                        driver.switchTo().defaultContent();
                        break;
                    } catch (Exception e2) {
                        driver.switchTo().defaultContent();
                        continue;
                    }
                }
                if (success) break;
                System.out.println("  ⚠️ Locator failed in all contexts: " + e.getMessage());
            }
        }
        
        if (!success) {
            // Last attempt: try to find any dropdown near "Currency" label
            try {
                driver.switchTo().defaultContent();
                waitForSeconds(1);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                // Find Currency label and then find nearby dropdown
                By[] labelLocators = {
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'currency')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'currency')]"),
                    By.xpath("//div[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'currency')]")
                };
                
                WebElement label = null;
                for (By labelBy : labelLocators) {
                    try {
                        label = wait.until(ExpectedConditions.presenceOfElementLocated(labelBy));
                        if (label != null && label.isDisplayed()) {
                            System.out.println("  ✅ Found Currency label, searching for nearby dropdown...");
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                if (label != null) {
                    // Try to find dropdown near the label
                    By[] nearbyDropdowns = {
                        By.xpath("./following-sibling::*//span[2]"),
                        By.xpath("./following::*[@id][contains(@id,'Dropdown')]//span[2]"),
                        By.xpath("./ancestor::div[1]//span[2]"),
                        By.xpath("./parent::*//span[2]"),
                        By.xpath("./following::div[@role='combobox'][1]//span[2]")
                    };
                    
                    for (By dropdownBy : nearbyDropdowns) {
                        try {
                            WebElement dropdown = label.findElement(dropdownBy);
                            if (dropdown != null && dropdown.isDisplayed()) {
                                System.out.println("  ✅ Found Currency dropdown near label");
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
                                waitForSeconds(1);
                                dropdown.click();
                                waitForSeconds(2);
                                // Now use the standard dropdown value selection
                                // The dropdown is already clicked, so we just need to find and click the option
                                List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span | //div[@role='listbox']//div | //ul[@role='listbox']//li"));
                                for (WebElement opt : options) {
                                    if (opt.isDisplayed()) {
                                        String optText = opt.getText().trim();
                                        if (optText.equalsIgnoreCase(currency) || optText.contains(currency)) {
                                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", opt);
                                            waitForSeconds(1);
                                            opt.click();
                                            System.out.println("  ✅ Selected Currency: " + currency);
                                            success = true;
                                            break;
                                        }
                                    }
                                }
                                if (success) break;
                            }
                        } catch (Exception e2) {
                            continue;
                        }
                    }
                }
            } catch (Exception e3) {
                System.out.println("  ⚠️ Label-based approach failed: " + e3.getMessage());
            }
        }
        
        if (!success) {
            throw new Exception("Failed to select Currency using all locator strategies. Last error: " + (lastException != null ? lastException.getMessage() : "Unknown error"));
        }
    }

    /**
     * Select Analysis Status from dropdown.
     * XPath: //*[@id="Dropdown156"]/span[2]
     * Also tries alternative locators if primary fails.
     */
    public void selectAnalysisStatus(String analysisStatus) throws Exception {
        if (analysisStatus == null || analysisStatus.trim().isEmpty()) return;
        
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Wait for form to stabilize
        
        // First, scan iframes to see if dropdown is there
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("  📋 Scanning " + iframes.size() + " iframe(s) for Analysis Status dropdown...");
        
        // Try multiple locator strategies in default content and iframes
        String[] dropdownXpaths = {
            "//*[@id=\"Dropdown156\"]/span[2]",  // Primary
            "//*[@id=\"Dropdown156\"]",  // Container
            "//div[@id='Dropdown156']//span[2]",  // Div with span
            "//div[contains(@id,'Dropdown156')]//span[2]",  // Flexible ID
            "//div[contains(@id,'Dropdown156')]",  // Flexible container
            "//label[contains(text(),'Analysis') or contains(text(),'analysis')]/following-sibling::*//span[2]",  // Label-based
            "//label[contains(text(),'Analysis Status') or contains(text(),'analysis status')]/following-sibling::*//span[2]",  // Label-based (full)
            "//label[contains(text(),'Analysis') or contains(text(),'analysis')]/following::*[@id][1]//span[2]",  // Label following
            "//*[contains(@aria-label,'Analysis') or contains(@placeholder,'Analysis')]//span[2]",  // Aria/placeholder
            "//div[@role='combobox' and contains(@id,'Dropdown')]//span[2]",  // Generic combobox
            "//*[contains(@id,'Dropdown') and contains(@id,'156')]//span[2]"  // Partial ID match
        };
        
        boolean success = false;
        Exception lastException = null;
        
        // Try each XPath in default content first
        for (String xpath : dropdownXpaths) {
            try {
                System.out.println("  🔍 Trying Analysis Status dropdown locator (default content): " + xpath);
                selectDropdownValue(xpath, analysisStatus, "Analysis Status");
                success = true;
                break;
            } catch (Exception e) {
                lastException = e;
                // Try in iframes
                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        driver.switchTo().frame(i);
                        System.out.println("  🔍 Trying Analysis Status dropdown locator (iframe #" + i + "): " + xpath);
                        selectDropdownValue(xpath, analysisStatus, "Analysis Status");
                        success = true;
                        driver.switchTo().defaultContent();
                        break;
                    } catch (Exception e2) {
                        driver.switchTo().defaultContent();
                        continue;
                    }
                }
                if (success) break;
                System.out.println("  ⚠️ Locator failed in all contexts: " + e.getMessage());
            }
        }
        
        if (!success) {
            // Last attempt: try to find any dropdown near "Analysis Status" label
            try {
                driver.switchTo().defaultContent();
                waitForSeconds(1);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                // Find Analysis Status label and then find nearby dropdown
                By[] labelLocators = {
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis status')]"),
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis status')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis')]"),
                    By.xpath("//div[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis status')]"),
                    By.xpath("//div[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'analysis')]")
                };
                
                WebElement label = null;
                for (By labelBy : labelLocators) {
                    try {
                        label = wait.until(ExpectedConditions.presenceOfElementLocated(labelBy));
                        if (label != null && label.isDisplayed()) {
                            System.out.println("  ✅ Found Analysis Status label, searching for nearby dropdown...");
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                if (label != null) {
                    // Try to find dropdown near the label
                    By[] nearbyDropdowns = {
                        By.xpath("./following-sibling::*//span[2]"),
                        By.xpath("./following::*[@id][contains(@id,'Dropdown')]//span[2]"),
                        By.xpath("./ancestor::div[1]//span[2]"),
                        By.xpath("./parent::*//span[2]"),
                        By.xpath("./following::div[@role='combobox'][1]//span[2]")
                    };
                    
                    for (By dropdownBy : nearbyDropdowns) {
                        try {
                            WebElement dropdown = label.findElement(dropdownBy);
                            if (dropdown != null && dropdown.isDisplayed()) {
                                System.out.println("  ✅ Found Analysis Status dropdown near label");
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
                                waitForSeconds(1);
                                dropdown.click();
                                waitForSeconds(2);
                                // Now use the standard dropdown value selection
                                // The dropdown is already clicked, so we just need to find and click the option
                                waitForSeconds(2); // Wait for options to appear
                                List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span | //div[@role='listbox']//div | //ul[@role='listbox']//li | //div[@role='option']"));
                                System.out.println("  🔍 Found " + options.size() + " options after clicking dropdown");
                                
                                List<String> availableOptions = new java.util.ArrayList<>();
                                String normalizedSearchValue = analysisStatus.trim().replaceAll("\\s+", " ").toLowerCase();
                                
                                for (WebElement opt : options) {
                                    if (!opt.isDisplayed()) continue;
                                    
                                    String optText = opt.getText().trim();
                                    if (optText.isEmpty()) {
                                        optText = opt.getAttribute("textContent");
                                        if (optText != null) optText = optText.trim();
                                        else optText = "";
                                    }
                                    
                                    if (optText.isEmpty()) continue;
                                    availableOptions.add(optText);
                                    
                                    String normalizedOptText = optText.replaceAll("\\s+", " ").toLowerCase();
                                    
                                    // Improved matching logic
                                    boolean isMatch = false;
                                    if (normalizedOptText.equals(normalizedSearchValue)) {
                                        isMatch = true;
                                    } else if (normalizedOptText.contains(normalizedSearchValue) && normalizedSearchValue.length() >= 3) {
                                        isMatch = true;
                                    } else if (normalizedSearchValue.contains(normalizedOptText) && normalizedOptText.length() >= 3) {
                                        isMatch = true;
                                    }
                                    
                                    if (isMatch) {
                                        System.out.println("     ✅ Match found: '" + optText + "' (searching for: '" + analysisStatus + "')");
                                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", opt);
                                        waitForSeconds(1);
                                        try {
                                            opt.click();
                                        } catch (Exception e) {
                                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", opt);
                                        }
                                        System.out.println("  ✅ Selected Analysis Status: " + analysisStatus);
                                        success = true;
                                        break;
                                    }
                                }
                                
                                // If no match found, print available options
                                if (!success && !availableOptions.isEmpty()) {
                                    System.out.println("  ⚠️ No match found. Available options:");
                                    for (String opt : availableOptions) {
                                        System.out.println("     - '" + opt + "'");
                                    }
                                }
                                
                                if (success) break;
                            }
                        } catch (Exception e2) {
                            continue;
                        }
                    }
                }
            } catch (Exception e3) {
                System.out.println("  ⚠️ Label-based approach failed: " + e3.getMessage());
            }
        }
        
        if (!success) {
            throw new Exception("Failed to select Analysis Status using all locator strategies. Last error: " + (lastException != null ? lastException.getMessage() : "Unknown error"));
        }
    }

    /**
     * Enter Completion Percentage.
     * XPath: //*[@id="TextField158"]
     */
    public void enterCompletionPercentage(String percentage) throws Exception {
        if (percentage == null || percentage.trim().isEmpty()) return;
        
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        By primaryBy = By.xpath("//*[@id=\"TextField158\"]");
        By inputNameBy = By.xpath("//input[@name='TextField158' or @name='completionPercentage' or contains(@name,'completion')]");
        By inputPlaceholderBy = By.xpath("//input[contains(@placeholder,'Completion') or contains(@placeholder,'Percentage') or contains(@placeholder,'completion')]");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = null;
        boolean found = false;
        
        By[] locators = {primaryBy, inputNameBy, inputPlaceholderBy};
        String[] locatorNames = {"id TextField158", "name attribute", "placeholder"};
        
        // Try default content first
        for (int i = 0; i < locators.length && !found; i++) {
            try {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[i]));
                System.out.println("  ✅ Found Completion Percentage field using " + locatorNames[i] + " (default content)");
                found = true;
                break;
            } catch (Exception e) {
                // Try next locator
            }
        }
        
        // If not found, try iframes
        if (!found) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (int j = 0; j < locators.length && !found; j++) {
                        try {
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[j]));
                            System.out.println("  ✅ Found Completion Percentage field using " + locatorNames[j] + " (iframe #" + i + ")");
                            found = true;
                            break;
                        } catch (Exception e) {
                            // Try next locator
                        }
                    }
                    if (!found) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        // If still not found, try label-based approach
        if (!found) {
            try {
                driver.switchTo().defaultContent();
                waitForSeconds(1);
                
                // Find Completion Percentage label and then find nearby input
                By[] labelLocators = {
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'completion')]"),
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'percentage')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'completion')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'percentage')]"),
                    By.xpath("//div[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'completion')]")
                };
                
                WebElement label = null;
                for (By labelBy : labelLocators) {
                    try {
                        label = wait.until(ExpectedConditions.presenceOfElementLocated(labelBy));
                        if (label != null && label.isDisplayed()) {
                            System.out.println("  ✅ Found Completion Percentage label, searching for nearby input...");
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                if (label != null) {
                    // Try to find input near the label
                    By[] nearbyInputs = {
                        By.xpath("./following-sibling::*//input"),
                        By.xpath("./following::input[1]"),
                        By.xpath("./ancestor::div[1]//input"),
                        By.xpath("./parent::*//input"),
                        By.xpath("./following::*[@id][contains(@id,'TextField')]")
                    };
                    
                    for (By inputBy : nearbyInputs) {
                        try {
                            element = label.findElement(inputBy);
                            if (element != null && element.isDisplayed()) {
                                System.out.println("  ✅ Found Completion Percentage field near label");
                                found = true;
                                break;
                            }
                        } catch (Exception e2) {
                            continue;
                        }
                    }
                }
            } catch (Exception e3) {
                System.out.println("  ⚠️ Label-based approach failed: " + e3.getMessage());
            }
        }
        
        if (!found || element == null) {
            throw new Exception("Could not find Completion Percentage field in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            waitForSeconds(1);
            element.clear();
            // Ensure we're entering the exact value from Excel
            String valueToEnter = percentage.trim();
            element.sendKeys(valueToEnter);
            // Verify the value was entered correctly
            String enteredValue = element.getAttribute("value");
            System.out.println("  ✅ Typed into Completion Percentage: '" + valueToEnter + "' (verified: '" + enteredValue + "')");
            if (!enteredValue.equals(valueToEnter)) {
                System.out.println("  ⚠️ Warning: Entered value doesn't match expected. Retrying with JavaScript...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, valueToEnter);
                enteredValue = element.getAttribute("value");
                System.out.println("  ✅ Retried via JS. Final value: '" + enteredValue + "'");
            }
        } catch (Exception e) {
            String valueToEnter = percentage.trim();
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, valueToEnter);
            String enteredValue = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);
            System.out.println("  ✅ Typed into Completion Percentage via JS: '" + valueToEnter + "' (verified: '" + enteredValue + "')");
        }
        waitForSeconds(1);
        driver.switchTo().defaultContent();
    }

    /**
     * Enter Invoice No.
     * XPath: //*[@id="TextField163"]
     */
    public void enterInvoiceNo(String invoiceNo) throws Exception {
        if (invoiceNo == null || invoiceNo.trim().isEmpty()) return;
        
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        By primaryBy = By.xpath("//*[@id=\"TextField163\"]");
        By inputNameBy = By.xpath("//input[@name='TextField163' or @name='invoiceNo' or contains(@name,'invoice')]");
        By inputPlaceholderBy = By.xpath("//input[contains(@placeholder,'Invoice') or contains(@placeholder,'invoice')]");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = null;
        boolean found = false;
        
        By[] locators = {primaryBy, inputNameBy, inputPlaceholderBy};
        String[] locatorNames = {"id TextField163", "name attribute", "placeholder"};
        
        // Try default content first
        for (int i = 0; i < locators.length && !found; i++) {
            try {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[i]));
                System.out.println("  ✅ Found Invoice No field using " + locatorNames[i] + " (default content)");
                found = true;
                break;
            } catch (Exception e) {
                // Try next locator
            }
        }
        
        // If not found, try iframes
        if (!found) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size() && !found; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (int j = 0; j < locators.length && !found; j++) {
                        try {
                            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[j]));
                            System.out.println("  ✅ Found Invoice No field using " + locatorNames[j] + " (iframe #" + i + ")");
                            found = true;
                            break;
                        } catch (Exception e) {
                            // Try next locator
                        }
                    }
                    if (!found) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        // If still not found, try label-based approach
        if (!found) {
            try {
                driver.switchTo().defaultContent();
                waitForSeconds(1);
                
                // Find Invoice No label and then find nearby input
                By[] labelLocators = {
                    By.xpath("//label[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invoice')]"),
                    By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invoice')]"),
                    By.xpath("//div[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invoice')]")
                };
                
                WebElement label = null;
                for (By labelBy : labelLocators) {
                    try {
                        label = wait.until(ExpectedConditions.presenceOfElementLocated(labelBy));
                        if (label != null && label.isDisplayed()) {
                            System.out.println("  ✅ Found Invoice No label, searching for nearby input...");
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                
                if (label != null) {
                    // Try to find input near the label
                    By[] nearbyInputs = {
                        By.xpath("./following-sibling::*//input"),
                        By.xpath("./following::input[1]"),
                        By.xpath("./ancestor::div[1]//input"),
                        By.xpath("./parent::*//input"),
                        By.xpath("./following::*[@id][contains(@id,'TextField')]")
                    };
                    
                    for (By inputBy : nearbyInputs) {
                        try {
                            element = label.findElement(inputBy);
                            if (element != null && element.isDisplayed()) {
                                System.out.println("  ✅ Found Invoice No field near label");
                                found = true;
                                break;
                            }
                        } catch (Exception e2) {
                            continue;
                        }
                    }
                }
            } catch (Exception e3) {
                System.out.println("  ⚠️ Label-based approach failed: " + e3.getMessage());
            }
        }
        
        if (!found || element == null) {
            throw new Exception("Could not find Invoice No field in default content or any iframe");
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            waitForSeconds(1);
            element.clear();
            element.sendKeys(invoiceNo);
            System.out.println("  ✅ Typed into Invoice No: " + invoiceNo);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, invoiceNo);
            System.out.println("  ✅ Typed into Invoice No via JS: " + invoiceNo);
        }
        waitForSeconds(1);
        driver.switchTo().defaultContent();
    }

    /**
     * Click Save button on Convert to Milestone form.
     * XPath: //*[@id="id__123"]
     */
    /**
     * Click Save button for Milestone conversion.
     * XPath: //button[.//span[normalize-space(text())='Save']]
     * Also tries: //*[@id="id__123"]
     */
    public void clickSaveButtonMilestone() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        
        // Primary: User-provided XPath
        By primaryBy = By.xpath("//button[.//span[normalize-space(text())='Save']]");
        // Fallback: ID-based
        By idBy = By.xpath("//*[@id=\"id__123\"]");
        // Fallback: Alternative text-based
        By textBy = By.xpath("//button[.//span[normalize-space()='Save']]");
        // Fallback: Generic Save button
        By genericBy = By.xpath("//button[contains(.,'Save')]");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement saveButton = null;
        boolean clicked = false;
        
        By[] locators = {primaryBy, idBy, textBy, genericBy};
        String[] locatorNames = {"text-based (user-provided)", "id id__123", "text-based (alternative)", "generic Save button"};
        
        // Try default content first
        for (int i = 0; i < locators.length && !clicked; i++) {
            try {
                saveButton = wait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                if (saveButton != null && saveButton.isDisplayed()) {
                    System.out.println("  ✅ Found Save button using " + locatorNames[i] + " (default content)");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
                    waitForSeconds(1);
                    try {
                        saveButton.click();
                        System.out.println("  ✅ Clicked Save button (regular click)");
                        clicked = true;
                        break;
                    } catch (Exception e1) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                        System.out.println("  ✅ Clicked Save button (JavaScript click)");
                        clicked = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ Save button not found with " + locatorNames[i] + ", trying next...");
                continue;
            }
        }
        
        // If not found in default content, try iframes
        if (!clicked) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (int i = 0; i < iframes.size() && !clicked; i++) {
                try {
                    driver.switchTo().frame(i);
                    for (int j = 0; j < locators.length && !clicked; j++) {
                        try {
                            saveButton = wait.until(ExpectedConditions.elementToBeClickable(locators[j]));
                            if (saveButton != null && saveButton.isDisplayed()) {
                                System.out.println("  ✅ Found Save button using " + locatorNames[j] + " (iframe #" + i + ")");
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);
                                waitForSeconds(1);
                                try {
                                    saveButton.click();
                                    System.out.println("  ✅ Clicked Save button (regular click)");
                                    clicked = true;
                                    break;
                                } catch (Exception e1) {
                                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
                                    System.out.println("  ✅ Clicked Save button (JavaScript click)");
                                    clicked = true;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (!clicked) {
                        driver.switchTo().defaultContent();
                    }
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
        }
        
        if (!clicked) {
            throw new Exception("Could not find or click Save button using all locator strategies in default content or any iframe");
        }
        
        waitForSeconds(3); // Wait for save to complete
        driver.switchTo().defaultContent();
    }

    // =====================================================================
    // WHIZIBLE SITE NAVIGATION METHODS (for TC_006)
    // =====================================================================

    /**
     * Logout current user via profile icon.
     */
    public void logoutCurrentUser() throws Exception {
        driver.switchTo().defaultContent();
        Thread.sleep(1000);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            By profileIcon = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[1]/div/div[2]/div/div/span");
            By logoutButtonAbsolute = By.xpath("/html/body/div[3]/div[3]/ul/div[3]/li/span[1]");

            System.out.println("  🔍 Searching for Profile icon for logout...");
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(profileIcon));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", profile);
            Thread.sleep(500);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profile);
                System.out.println("  ✅ Clicked Profile icon using JavaScript");
            } catch (Exception e1) {
                profile.click();
                System.out.println("  ✅ Clicked Profile icon using direct click");
            }

            Thread.sleep(1500);

            System.out.println("  🔍 Searching for Logout button in profile menu...");
            
            // Multiple strategies to find logout button
            WebElement logout = null;
            
            // Strategy 1: Text-based locator (most reliable)
            try {
                By logoutByText = By.xpath("//span[contains(text(),'Logout')] | //*[normalize-space(text())='Logout'] | //li[contains(.,'Logout')]//span");
                logout = wait.until(ExpectedConditions.presenceOfElementLocated(logoutByText));
                System.out.println("   ✅ Found logout button using Strategy 1 (text-based)");
            } catch (Exception e1) {
                // Strategy 2: Try clickable text-based locator
                try {
                    By logoutByText = By.xpath("//span[contains(text(),'Logout')] | //*[normalize-space(text())='Logout']");
                    logout = wait.until(ExpectedConditions.elementToBeClickable(logoutByText));
                    System.out.println("   ✅ Found logout button using Strategy 2 (clickable text-based)");
                } catch (Exception e2) {
                    // Strategy 3: Try original absolute XPath
                    try {
                        logout = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButtonAbsolute));
                        System.out.println("   ✅ Found logout button using Strategy 3 (absolute XPath)");
                    } catch (Exception e3) {
                        // Strategy 4: Try clickable of absolute XPath
                        logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonAbsolute));
                        System.out.println("   ✅ Found logout button using Strategy 4 (clickable absolute XPath)");
                    }
                }
            }

            if (logout == null) {
                throw new Exception("Logout button not found after trying all strategies");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", logout);
            Thread.sleep(500);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
                System.out.println("  ✅ Clicked Logout button using JavaScript");
            } catch (Exception e2) {
                logout.click();
                System.out.println("  ✅ Clicked Logout button using direct click");
            }

            Thread.sleep(3000);
            System.out.println("  ✅ User successfully logged out via profile menu");

        } catch (Exception e) {
            System.err.println("  ❌ Error during logout via profile icon: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Navigate to Whizible site URL.
     * @param url The Whizible site URL from config
     */
    public void navigateToWhizibleSite(String url) throws Exception {
        driver.switchTo().defaultContent();
        driver.get(url);
        System.out.println("  ✅ Navigated to Whizible site: " + url);
        
        // Wait for page to load completely
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            System.out.println("  ⚠️ Page load check timeout, continuing anyway...");
        }
        
        Thread.sleep(3000); // Additional wait for page elements to render
    }

    /**
     * Login to Whizible site using form-based login.
     * XPath for User ID: //*[@id="txtLogin"]
     * XPath for Password: //*[@id="txtPassword"]
     * XPath for Sign In: //*[@id="loginformboxformD"]/div[2]/div[4]/button
     */
    public void loginToWhizibleSite(String userId, String password) throws Exception {
        driver.switchTo().defaultContent();
        Thread.sleep(2000); // Wait for page to stabilize

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Try multiple locator strategies for User ID field
        WebElement userIdField = null;
        By[] userIdLocators = {
            By.id("txtLogin"),
            By.xpath("//*[@id=\"txtLogin\"]"),
            By.xpath("//input[@id='txtLogin']"),
            By.xpath("//input[contains(@id,'txtLogin')]"),
            By.xpath("//input[@type='text' and contains(@name,'Login')]"),
            By.xpath("//input[@type='text' and contains(@name,'login')]")
        };
        
        System.out.println("  🔍 Searching for User ID field...");
        for (By locator : userIdLocators) {
            try {
                userIdField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (userIdField != null && userIdField.isDisplayed()) {
                    System.out.println("  ✅ Found User ID field using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // If not found in default content, check iframes
        if (userIdField == null) {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("  📋 Checking " + iframes.size() + " iframe(s) for User ID field...");
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    for (By locator : userIdLocators) {
                        try {
                            userIdField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                            if (userIdField != null && userIdField.isDisplayed()) {
                                System.out.println("  ✅ Found User ID field in iframe #" + i + " using: " + locator);
                                break;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    if (userIdField != null) break;
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                    continue;
                }
            }
            if (userIdField == null) {
                driver.switchTo().defaultContent();
            }
        }
        
        if (userIdField == null) {
            throw new Exception("Could not find User ID field (txtLogin) in default content or any iframe");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", userIdField);
        Thread.sleep(500);
        userIdField.clear();
        userIdField.sendKeys(userId);
        System.out.println("  ✅ Entered User ID: " + userId);

        // Enter Password - try multiple locator strategies
        WebElement passwordField = null;
        By[] passwordLocators = {
            By.id("txtPassword"),
            By.xpath("//*[@id=\"txtPassword\"]"),
            By.xpath("//input[@id='txtPassword']"),
            By.xpath("//input[contains(@id,'txtPassword')]"),
            By.xpath("//input[@type='password']")
        };
        
        System.out.println("  🔍 Searching for Password field...");
        for (By locator : passwordLocators) {
            try {
                passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (passwordField != null && passwordField.isDisplayed()) {
                    System.out.println("  ✅ Found Password field using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (passwordField == null) {
            throw new Exception("Could not find Password field (txtPassword)");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", passwordField);
        Thread.sleep(500);
        passwordField.clear();
        passwordField.sendKeys(password);
        System.out.println("  ✅ Entered Password");

        // Click Sign In button - try multiple locator strategies
        WebElement signInButton = null;
        By[] signInLocators = {
            By.xpath("//*[@id=\"loginformboxformD\"]/div[2]/div[4]/button"),
            By.xpath("//button[contains(text(),'Sign In') or contains(text(),'Sign in') or contains(text(),'Login')]"),
            By.xpath("//input[@type='submit']"),
            By.xpath("//button[@type='submit']")
        };
        
        System.out.println("  🔍 Searching for Sign In button...");
        for (By locator : signInLocators) {
            try {
                signInButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                if (signInButton != null && signInButton.isDisplayed()) {
                    System.out.println("  ✅ Found Sign In button using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (signInButton == null) {
            throw new Exception("Could not find Sign In button");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", signInButton);
        Thread.sleep(500);
        try {
            signInButton.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInButton);
        }
        System.out.println("  ✅ Clicked Sign In button");
        
        // Wait for login to complete
        Thread.sleep(3000);
        driver.switchTo().defaultContent();

        waitForSeconds(3);
        
    //    driver.switchTo().defaultContent();
    //    driver.manage().deleteAllCookies();
   //     driver.navigate().refresh();

     //   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(webDriver ->
            ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete")
        );

        // USERNAME
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='';", userIdField);
        js.executeScript("arguments[0].dispatchEvent(new Event('input'));", userIdField);
        userIdField.sendKeys(userId);

        // PASSWORD
        js.executeScript("arguments[0].value='';", passwordField);
        js.executeScript("arguments[0].dispatchEvent(new Event('input'));", passwordField);
        passwordField.sendKeys(password);

        // LOGIN
        js.executeScript("arguments[0].click();", signInButton);

        Thread.sleep(4000);

        // VERIFY
        if (driver.getPageSource().toLowerCase().contains("invalid")) {
            throw new Exception("❌ Invalid login for user: " + userId);
        }

        
        
        
    }

    public void navigateToWhizibleSite1(String url) {

        driver.switchTo().defaultContent();
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        System.out.println("✅ Navigated to: " + url);
    }

  
    
    public void loginToWhizibleSite1(String userId, String password) {

        driver.switchTo().defaultContent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Wait for username field
        WebElement userIdField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("txtLogin"))
        );

        userIdField.clear();
        userIdField.sendKeys(userId);

        WebElement passwordField = driver.findElement(By.id("txtPassword"));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement signInButton = driver.findElement(
                By.xpath("//*[@id='loginformboxformD']//button")
        );

        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();

        // Wait for dashboard / home page element
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("login")
        ));

        System.out.println("✅ Login successful for user: " + userId);
    }

    
    
    
    
    
    
    
    /**
     * Click on Project Module.
     * XPath: //*[@id="a_PM3"]/span
     */
    
    public static By clickproject = By.xpath("//span[normalize-space()='Projects']");
    
    public void clickProjectModule() throws Exception {
    	 click(InitiativeConversion.clickproject, "clickproject");
    }

    /**
     * Click on Plan node.
     * XPath: //*[@id="a_PM8002"]/span[2]
     */
    public static By clickplanNode = By.xpath("//span[@originalname='Plan' and normalize-space()='Plan']");
    public void clickPlanNode() throws Exception {
    	 click(InitiativeConversion.clickplanNode, "clickplanNode");
    }

    /**
     * Click on WBS sub node.
     * XPath: //*[@id="a_PM8003"]/span
     */
    public static By clickwbs = By.xpath("//span[normalize-space()='WBS']");
    public void clickWBSSubNode() throws Exception {
    	
    	 
    	 clickElementInModal(InitiativeConversion.clickwbs, "clickwbs");
    }

  //  public static By projectfromWhizible = By.xpath("");
    public static By projectfromWhizible = By.xpath("//select[@id='cboWBSProjects']");
    
	WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
    
     public void  selectProjectFromDropdown1(String projectfromWhizible) {
    	 

 	    WebElement frame = wait.until(
 	            ExpectedConditions.presenceOfElementLocated(By.id("frmNewVersion"))
 	    );

 	    driver.switchTo().frame(frame);
    	 
    	 WebElement dropdownElement = wait.until(
    	            ExpectedConditions.visibilityOfElementLocated(
    	                    InitiativeConversion.projectfromWhizible
    	            )
    	    );

    	    // 🔹 Step 3: Use Select class
    	    Select dropdown = new Select(dropdownElement);
    	    dropdown.selectByVisibleText(projectfromWhizible);

    	    System.out.println("✅ Selected project: " + projectfromWhizible);

    	    // 🔹 Step 4: Switch back
    	    driver.switchTo().defaultContent();
     }
    
    
     

    
    /**
     * Select project from dropdown.
     * XPath: //*[@id="cboWBSProjects"]
     * This is likely a standard HTML <select> element, so we'll try both standard Select and custom dropdown approaches.
     */
    public void selectProjectFromDropdown(String projectName) throws Exception {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new Exception("Project Name cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        Thread.sleep(2000); // Wait for page to stabilize
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Try multiple locator strategies for the dropdown
        By[] projectDropdownLocators = {
            By.id("cboWBSProjects"),
            By.xpath("//*[@id=\"cboWBSProjects\"]"),
            By.xpath("//*[@id='cboWBSProjects']"),
            By.xpath("//select[@id='cboWBSProjects']"),
            By.xpath("//select[contains(@id,'cboWBSProjects')]"),
            By.xpath("//select[contains(@id,'WBSProjects')]"),
            By.xpath("//select[contains(@name,'Project') or contains(@name,'project')]")
        };
        
        WebElement projectDropdown = null;
        By foundLocator = null;
        
        for (By locator : projectDropdownLocators) {
            try {
                projectDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (projectDropdown != null && projectDropdown.isDisplayed()) {
                    foundLocator = locator;
                    System.out.println("  ✅ Found Select Project dropdown using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (projectDropdown == null) {
            // Try using selectDropdownValue helper method as fallback
            System.out.println("  ⚠️ Dropdown not found with direct locators, trying selectDropdownValue helper...");
            try {
                selectDropdownValue("//*[@id=\"cboWBSProjects\"]", projectName, "Select Project");
                return;
            } catch (Exception e) {
                throw new Exception("Could not find Select Project dropdown (cboWBSProjects) using any locator strategy. Error: " + e.getMessage());
            }
        }
        
        // Try standard HTML Select approach first
        try {
            if (projectDropdown.getTagName().equalsIgnoreCase("select")) {
                org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(projectDropdown);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", projectDropdown);
                Thread.sleep(500);
                
                // Try to select by visible text
                try {
                    select.selectByVisibleText(projectName);
                    System.out.println("  ✅ Selected Project: " + projectName + " (using standard Select)");
                    Thread.sleep(1000);
                    return;
                } catch (Exception e1) {
                    // Try partial match
                    List<org.openqa.selenium.WebElement> options = select.getOptions();
                    for (org.openqa.selenium.WebElement option : options) {
                        String optionText = option.getText().trim();
                        if (optionText.equals(projectName) || optionText.contains(projectName) || projectName.contains(optionText)) {
                            select.selectByVisibleText(optionText);
                            System.out.println("  ✅ Selected Project: " + optionText + " (matched: " + projectName + ")");
                            Thread.sleep(1000);
                            return;
                        }
                    }
                    throw new Exception("Project '" + projectName + "' not found in dropdown options");
                }
            }
        } catch (Exception e) {
            System.out.println("  ⚠️ Standard Select approach failed, trying custom dropdown approach: " + e.getMessage());
        }
        
        // If not a standard select, use custom dropdown approach
        String dropdownXpath = "//*[@id=\"cboWBSProjects\"]";
        if (foundLocator != null) {
            if (foundLocator instanceof By.ById) {
                dropdownXpath = "//*[@id='" + foundLocator.toString().replace("By.id: ", "") + "']";
            } else if (foundLocator.toString().contains("xpath")) {
                dropdownXpath = foundLocator.toString().replace("By.xpath: ", "");
            }
        }
        
        selectDropdownValue(dropdownXpath, projectName, "Select Project");
    }

    /**
     * Click on Filter section.
     * XPath: //*[@id="MilestoneFilter"]/i
     */
    
    public static By initiativeIframe =
            By.xpath("//iframe[@id='frmNewVersion']");

    public static By clickfilter = By.id("MilestoneFilter");
   
    
    
    public void clickFilterSection() throws Exception {

    	   WebElement frame = wait.until(
    	            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@id='frmNewVersion']"))
    	    );

    	    driver.switchTo().frame(frame);
       	 

        // 2️⃣ Click filter
       clickElementInModal(InitiativeConversion.clickfilter, "Milestone Filter");

        // 3️⃣ Switch back
        driver.switchTo().defaultContent();
    }

    
   
    
    
    public static By clickbasic = By.xpath("//a[@id='MilestoneBasicFilter']");
    
    
    public void clickBasicFilterSubTab() throws Exception {
    	
    	   WebElement frame = wait.until(
    	            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@id='frmNewVersion']"))
    	    );

    	    driver.switchTo().frame(frame);
       	 
        // 2️⃣ Click filter
        click(InitiativeConversion.clickbasic, "clickbasicr");

        // 3️⃣ Switch back
        driver.switchTo().defaultContent();
         
    }

    /**
     * Enter Milestone Name in filter field.
     * XPath: //*[@id="txtMLFilterMileStone"]
     */
    public static By milestoneFilterTextbox = 
            By.id("txtMLFilterMileStone");
    public void enterMilestoneName(String milestoneName) throws Exception {
    	
    	  WebElement frame = wait.until(
  	            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@id='frmNewVersion']"))
  	    );

  	    driver.switchTo().frame(frame);
     	 
    	
    	   typeInModal(InitiativeConversion. milestoneFilterTextbox, milestoneName, "milestone");
    }

    /**
     * Click Apply button.
     * XPath: //*[@id="accordion"]/div[1]/button[2]
     */
    public static By clickapply = 
            By.xpath("//button[contains(@class,'btnyellow') and text()='Apply']");
    public void clickApplyButton() throws Exception {
    	driver.switchTo().defaultContent();
    	  WebElement frame = wait.until(
  	            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@id='frmNewVersion']"))
  	    );

  	    driver.switchTo().frame(frame);
     	 
        
        click(InitiativeConversion.clickapply, "clickapply");
    }

    /**
     * Verify that converted milestone is displayed on the Milestone page.
     * @param milestoneName The milestone name to verify
     * @return true if milestone is found, false otherwise
     */
    public boolean verifyMilestoneDisplayed(String milestoneName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.switchTo().defaultContent();
       
  	  WebElement frame = wait.until(
	            ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@id='frmNewVersion']"))
	    );

	    driver.switchTo().frame(frame);
   	 
        
        
        
        By tableRowsBy = By.xpath("//table[@id='wbsMStable']//tbody/tr");

        try {
            List<WebElement> rows = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsBy)
            );

            for (WebElement row : rows) {

                String firstColumnText = row.findElement(By.xpath("./td[1]"))
                                            .getText()
                                            .trim();

                if (firstColumnText.equalsIgnoreCase(milestoneName)) {

                    System.out.println("✅ Milestone '" + milestoneName + "' found");
                    driver.switchTo().defaultContent();
                    return true;
                }
            }

            System.out.println("❌ Milestone '" + milestoneName + "' not found");
            driver.switchTo().defaultContent();
            return false;

        } catch (Exception e) {

            System.out.println("⚠ Error verifying milestone: " + e.getMessage());
            driver.switchTo().defaultContent();
            return false;
        }
    }

    // =====================================================================
    // WHIZIBLE SITE METHODS FOR TC_007 (MANAGE PROJECT)
    // =====================================================================

    /**
     * Click on Search section in Whizible site.
     * This is typically a search icon or input field in the navigation.
     */
    public void clickSearchSectionWhizible() throws Exception {
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Try multiple locator strategies for search section
        By[] searchLocators = {
            By.xpath("//input[@type='search']"),
            By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]"),
            By.xpath("//*[contains(@id,'search') or contains(@id,'Search')]"),
            By.xpath("//i[contains(@class,'search') or contains(@class,'Search')]"),
            By.xpath("//*[contains(@class,'search') or contains(@class,'Search')]"),
            By.xpath("//button[contains(@aria-label,'Search') or contains(@aria-label,'search')]")
        };
        
        WebElement searchElement = null;
        for (By locator : searchLocators) {
            try {
                searchElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
                if (searchElement != null && searchElement.isDisplayed()) {
                    System.out.println("  ✅ Found Search section using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (searchElement == null) {
            throw new Exception("Could not find Search section in Whizible site using any locator strategy");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchElement);
        Thread.sleep(500);
        try {
            searchElement.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchElement);
        }
        System.out.println("  ✅ Clicked Search section");
        Thread.sleep(1000);
    }

    /**
     * Search for text in Whizible site search field.
     * @param searchText The text to search for (e.g., "Manage Project")
     */
    public void searchInWhizible(String searchText) throws Exception {
        if (searchText == null || searchText.trim().isEmpty()) {
            throw new Exception("Search text cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        Thread.sleep(1000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Try multiple locator strategies for search input
        By[] searchInputLocators = {
            By.xpath("//input[@type='search']"),
            By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]"),
            By.xpath("//input[contains(@id,'search') or contains(@id,'Search')]"),
            By.xpath("//input[@type='text' and contains(@class,'search')]")
        };
        
        WebElement searchInput = null;
        for (By locator : searchInputLocators) {
            try {
                searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (searchInput != null && searchInput.isDisplayed()) {
                    System.out.println("  ✅ Found Search input field using: " + locator);
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        if (searchInput == null) {
            throw new Exception("Could not find Search input field in Whizible site");
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchInput);
        Thread.sleep(500);
        searchInput.clear();
        searchInput.sendKeys(searchText);
        System.out.println("  ✅ Entered search text: " + searchText);
        Thread.sleep(2000); // Wait for search results to appear
    }

    /**
     * Click on Manage Project node in Whizible site.
     */
    public void clickManageProjectNode() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By manageProjectLocator = By.xpath(
                "//a[.//span[normalize-space()='Manage Projects']]"
        );

        for (int i = 0; i < 3; i++) {
            try {

                WebElement element = wait.until(
                        ExpectedConditions.presenceOfElementLocated(manageProjectLocator)
                );

                // Scroll into view
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                // JS Click (avoids stale/intercepted issues)
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);

                System.out.println("✅ JS Clicked Manage Projects node");

                // Wait for page load
                wait.until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));

                return;

            } catch (StaleElementReferenceException e) {
                System.out.println("⚠ Stale detected — retrying...");
            }
        }

        throw new RuntimeException("❌ Failed to click Manage Projects after retries");
    }

    /**
     * Search for a project in Whizible Manage Project page.
     * @param projectName The project name to search for
     */
    public void searchProjectInWhizible(String projectName) throws Exception {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new Exception("Project Name cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        
        // Click search section first
        clickSearchSectionWhizible();
        
        // Then search for the project
        searchInWhizible(projectName);
    }

    /**
     * Verify that converted project is displayed on the Manage Project page.
     * @param projectName The project name to verify
     * @return true if project is found, false otherwise
     */
    public boolean verifyProjectDisplayed(String projectName) throws Exception {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new Exception("Project Name cannot be null or empty");
        }
        
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Try multiple locator strategies for project table/grid
        By[] tableRowsBy = {
            By.xpath("//table//tbody/tr"),
            By.xpath("//tr[contains(@class,'row')]"),
            By.xpath("//div[contains(@class,'row')]"),
            By.xpath("//*[contains(text(),'" + projectName + "')]")
        };
        
        try {
            for (By locator : tableRowsBy) {
                try {
                    List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
                    if (rows != null && !rows.isEmpty()) {
                        for (WebElement row : rows) {
                            if (row.isDisplayed()) {
                                String rowText = row.getText();
                                if (rowText != null && rowText.contains(projectName)) {
                                    System.out.println("  ✅ Project '" + projectName + "' found in the grid");
                                    return true;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            // Also try direct text search
            try {
                By projectTextBy = By.xpath("//*[contains(text(),'" + projectName + "')]");
                WebElement projectElement = wait.until(ExpectedConditions.presenceOfElementLocated(projectTextBy));
                if (projectElement != null && projectElement.isDisplayed()) {
                    System.out.println("  ✅ Project '" + projectName + "' found by text search");
                    return true;
                }
            } catch (Exception e) {
                // Continue to return false
            }
            
            System.out.println("  ❌ Project '" + projectName + "' not found in the grid");
            return false;
        } catch (Exception e) {
            System.out.println("  ⚠️ Error verifying project: " + e.getMessage());
            return false;
        }
    }
    
    public static By close= By.xpath("//button[.//span[normalize-space()='Close']]");
    
    public void clickonclose() {
    	click(InitiativeConversion.close,"close");
    }
    
    
    public void waitForToastToDisappear() {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            By toastLocator = By.xpath("//div[contains(@class,'Toastify__toast')]");

            // Wait until toast appears (optional but safer)
            wait.until(ExpectedConditions.presenceOfElementLocated(toastLocator));

            // Now wait until it disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));

            System.out.println("✅ Toast message disappeared");

        } catch (Exception e) {
            System.out.println("⚠️ Toast not present or already disappeared");
        }
    }
    
    
    
    public String getInitiativeCode() {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Capture first row initiative code
            WebElement codeElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("(//table//tbody//tr[1]//td[2])[1]")
                    )
            );

            String initiativeCode = codeElement.getText().trim();

            if (initiativeCode.isEmpty()) {
                throw new RuntimeException("Initiative Code is empty in grid.");
            }

            System.out.println("✅ Captured Initiative Code from Grid: " + initiativeCode);

            return initiativeCode;

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to capture Initiative Code from Grid", e);
        }
    }
    
    public String getProjectNameFromTextField() {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement projectNameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("projectName")
                    )
            );

            String projectName = projectNameField.getAttribute("value");

            if (projectName == null || projectName.trim().isEmpty()) {
                throw new RuntimeException("❌ Project Name field is empty!");
            }

            projectName = projectName.trim();

            System.out.println("✅ Captured Project Name: " + projectName);

            return projectName;

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to capture Project Name from text field", e);
        }
    }  
    
    
    


  
 
	 public static By searcwhiz= By.xpath("//input[@id='srchprolistfield']");
	 public void searchProject(String searchproject) {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("frmNewVersion")));
		    // Switch to iframe
	

		    // Enter search text
		    typeInModal(searcwhiz, searchproject, "Search Project");
		}
	 
	 
	 
	 public static By iframeNewVersion = By.id("frmNewVersion");
	 public static By clickSearchWhiz = By.xpath("//i[@aria-label='Click For Search']");
	 public void clicksearchwhiz() {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Switch to iframe
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeNewVersion));

		    // Click search icon
		    WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(clickSearchWhiz));
		    searchIcon.click();

		    // Switch back to main page
		    driver.switchTo().defaultContent();
		} 
	 
	 
	 
	 
}
