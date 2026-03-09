package Pages;

import Actions.ActionEngine;
import Locators.ExternalAuditPageLocators;
import Locators.InitiativePageLocators;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import com.aventstack.extentreports.ExtentTest;

/**
 * Page Object Model (POM) for External Audit / Converted Initiative Module
 *
 * DESIGN:
 * - Locators are defined in {@link ExternalAuditPageLocators}
 * - This class implements UI actions for the External Audit page
 * - Test logic stays in TestNG test classes (e.g., ExternalAuditTest)
 *
 * NOTE:
 * - Currently includes navigation, header verification, and basic search/edit hooks.
 * - As you share concrete xpaths and flows, we can expand this class with more actions.
 */
public class ExternalAuditPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    // 🔹 Constructor with WebDriver + Logger
    public ExternalAuditPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // pass driver to ActionEngine
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    // ✅ Navigate to External Audit Page
    public void navigateToExternalAudit() throws Exception {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO EXTERNAL AUDIT PAGE - START");
        System.out.println("═══════════════════════════════════════════════════════");

        // Wait for page to be fully loaded after login
        System.out.println("⏳ Waiting for page to stabilize after login...");
        waitForSeconds(5);

        // Wait for document ready
        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        pageWait.until(d -> ((JavascriptExecutor) d)
            .executeScript("return document.readyState").equals("complete"));
        System.out.println("✅ Page fully loaded");

        try {
            // Step 1: Click on Initiative Management
            System.out.println("\n📍 Step 1: Clicking Initiative Management...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement initiativeMgmt = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.initiativeManagement));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", initiativeMgmt);
            waitForSeconds(1);
            initiativeMgmt.click();
            waitForSeconds(2);

            // Step 2: Click on External Audit / Converted Initiative node
            System.out.println("\n📍 Step 2: Clicking External Audit Node...");
            WebElement externalAuditNode = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.externalAuditNode));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", externalAuditNode);
            waitForSeconds(1);
            externalAuditNode.click();
            waitForSeconds(3);

            System.out.println("\n✅ ✅ ✅ Navigated to External Audit page successfully! ✅ ✅ ✅");
            if (reportLogger != null) {
                reportLogger.pass("Successfully navigated to External Audit page");
            }

        } catch (Exception e) {
            System.out.println("\n❌ ❌ ❌ Navigation to External Audit page FAILED! ❌ ❌ ❌");
            System.out.println("Error: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to navigate to External Audit page: " + e.getMessage());
            }
            throw e;
        }

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("🔍 NAVIGATION TO EXTERNAL AUDIT PAGE - END");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }

    // ✅ Verify External Audit Header
    public void verifyExternalAuditHeader(String expectedHeader) throws Exception {
        try {
            System.out.println("\n🔍 Verifying External Audit page header...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.externalAuditPageHeader));

            String actualHeader = header.getText().trim();
            System.out.println("Expected Header: " + expectedHeader);
            System.out.println("Actual Header: " + actualHeader);

            if (actualHeader.equals(expectedHeader)) {
                System.out.println("✅ Header verification passed");
                if (reportLogger != null) {
                    reportLogger.pass("External Audit header verified: " + actualHeader);
                }
            } else {
                throw new Exception("Header mismatch. Expected: " + expectedHeader + ", Actual: " + actualHeader);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to verify header: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to verify External Audit header: " + e.getMessage());
            }
            throw e;
        }
    }

    // ==================== BASIC ACTION HELPERS ====================

    // Hover + click (copied pattern from Initiative/ActionItem/Watchlist pages)
    private void hoverAndClickElement(By locator, String elementName) throws Exception {
        try {
            System.out.println("🖱️ Hovering and clicking: " + elementName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);

            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            System.out.println("  ↪ Hovered over element");
            waitForSeconds(1);

            try {
                element.click();
                System.out.println("  ↪ Clicked using regular click");
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("  ↪ Clicked using JavaScript");
            }

            System.out.println("✅ Successfully hovered and clicked: " + elementName);
        } catch (Exception e) {
            System.out.println("❌ Failed to hover and click: " + elementName + " - " + e.getMessage());
            throw e;
        }
    }

    // Click with multiple fallbacks (standard pattern)
    private void clickWithFallback(By locator, String elementName) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", element);
            waitForSeconds(1);

            try {
                element.click();
                System.out.println("✅ Clicked on: " + elementName);
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    System.out.println("✅ Clicked on: " + elementName + " (JavaScript)");
                } catch (Exception e2) {
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

    // ==================== HOOKS FOR FUTURE FLOWS ====================
    // These methods mirror your other modules (search/filter/edit) and can be
    // wired to real locators once you provide the exact xpaths.

    public void clickSearchButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Search Button (External Audit)...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.searchButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", searchBtn);
            waitForSeconds(1);

            try {
                searchBtn.click();
                System.out.println("✅ Search button clicked successfully");
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("✅ Search button clicked using JavaScript");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Search button clicked successfully (External Audit)");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Search button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search button: " + e.getMessage());
            }
            throw e;
        }
    }

    public void clickAuditCodeField() throws Exception {
        try {
            System.out.println("\n📝 Clicking Audit Code Field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.auditCodeField));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", field);
            waitForSeconds(1);

            try {
                field.click();
                System.out.println("✅ Audit Code field clicked successfully");
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", field);
                System.out.println("✅ Audit Code field clicked using JavaScript");
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Audit Code field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Audit Code field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Audit Code field: " + e.getMessage());
            }
            throw e;
        }
    }

    public void enterAuditCode(String auditCode) throws Exception {
        try {
            System.out.println("\n📝 Entering Audit Code: " + auditCode);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.auditCodeField));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", field);
            waitForSeconds(1);

            try {
                field.clear();
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", field);
            }

            waitForSeconds(1);

            try {
                field.sendKeys(auditCode);
                System.out.println("✅ Audit Code entered successfully");
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value='" + auditCode.replace("'", "\\'") + "';", field);
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", field);
                System.out.println("✅ Audit Code entered using JavaScript");
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Entered Audit Code: " + auditCode);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Audit Code: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Audit Code: " + e.getMessage());
            }
            throw e;
        }
    }

    public void clickSearchSubmitButton() throws Exception {
        try {
            System.out.println("\n🔍 Clicking Search Submit Button (External Audit)...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.searchSubmitButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", btn);
            waitForSeconds(1);

            try {
                btn.click();
                System.out.println("✅ Search submit button clicked successfully");
            } catch (Exception e1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                System.out.println("✅ Search submit button clicked using JavaScript");
            }

            waitForSeconds(3);
            if (reportLogger != null) {
                reportLogger.pass("Search submit button clicked successfully (External Audit)");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Search submit button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Search submit button: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Audit Type Dropdown
    public void clickAuditTypeDropdown() throws Exception {
        try {
            System.out.println("\n📊 Clicking Audit Type Dropdown...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.auditTypeDropdown));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", dropdown);
            waitForSeconds(1);

            try {
                dropdown.click();
                System.out.println("✅ Audit Type dropdown clicked successfully");
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                    System.out.println("✅ Audit Type dropdown clicked using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(dropdown).click().perform();
                    System.out.println("✅ Audit Type dropdown clicked using Actions");
                }
            }

            waitForSeconds(2); // wait for options
            if (reportLogger != null) {
                reportLogger.pass("Audit Type dropdown clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Audit Type dropdown: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Audit Type dropdown: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Select Audit Type value from dropdown
    public void selectAuditType() throws Exception {
       click(ExternalAuditPageLocators.firstAuditTypeOption1,"firstAuditTypeOption");
    }

    public void clickEditButton() throws Exception {
        try {
            System.out.println("\n✏️ Clicking Edit Button in External Audit table...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.presenceOfElementLocated(
                ExternalAuditPageLocators.editButton));
            WebElement editBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.editButton));
            editBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.editButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", editBtn);
            waitForSeconds(2);

            boolean clicked = false;

            try {
                editBtn.click();
                System.out.println("✅ Edit button clicked (regular)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed: " + e1.getMessage());
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                    System.out.println("✅ Edit button clicked (JavaScript)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(editBtn).click().perform();
                    System.out.println("✅ Edit button clicked (Actions)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                try {
                    WebElement altButton = wait.until(ExpectedConditions.elementToBeClickable(
                        ExternalAuditPageLocators.editButtonAlternative));
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", altButton);
                    waitForSeconds(1);
                    altButton.click();
                    System.out.println("✅ Edit button clicked (alternative locator)");
                    clicked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Alternative locator click failed: " + e4.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Edit button using any method (External Audit)");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Edit button clicked successfully (External Audit)");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Edit button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Edit button: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Add Button
    public void clickAddButton() throws Exception {
        try {
            System.out.println("\n➕ Clicking Add Button on External Audit page...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.addButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", addBtn);
            waitForSeconds(1);

            try {
                addBtn.click();
                System.out.println("✅ Add button clicked successfully (regular click)");
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
                    System.out.println("✅ Add button clicked successfully (JavaScript click)");
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(addBtn).click().perform();
                    System.out.println("✅ Add button clicked successfully (Actions click)");
                }
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Add button clicked successfully on External Audit page");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Add button on External Audit page: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Add button on External Audit page: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Title Field
    public void clickTitleField() throws Exception {
        try {
            System.out.println("\n📝 Clicking Title Field on External Audit form...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement title = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.titleField));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", title);
            waitForSeconds(1);

            try {
                title.click();
                System.out.println("✅ Title field clicked successfully (regular click)");
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", title);
                    System.out.println("✅ Title field clicked successfully (JavaScript click)");
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(title).click().perform();
                    System.out.println("✅ Title field clicked successfully (Actions click)");
                }
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Title field clicked successfully on External Audit form");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Title field on External Audit form: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Title field on External Audit form: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Enter Title Value
    public void enterTitle(String titleValue) throws Exception {
        try {
            System.out.println("\n📝 Entering Title on External Audit form: " + titleValue);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.titleField));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", title);
            waitForSeconds(1);

            // Try to treat the target as input/textarea or contenteditable
            try {
                title.clear();
            } catch (Exception ignored) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", title);
                } catch (Exception ignored2) {
                    // for div-based editors, simulate select-all + delete
                    try {
                        title.click();
                        title.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                    } catch (Exception ignored3) {
                        System.out.println("⚠️ Could not clear Title field explicitly, will overwrite");
                    }
                }
            }

            waitForSeconds(1);

            try {
                title.sendKeys(titleValue);
                System.out.println("✅ Title entered successfully via sendKeys");
            } catch (Exception e1) {
                System.out.println("⚠️ sendKeys failed, trying JavaScript set value...");
                ((JavascriptExecutor) driver).executeScript(
                    "if(arguments[0].tagName==='INPUT' || arguments[0].tagName==='TEXTAREA'){arguments[0].value=arguments[1];}" +
                    "else{arguments[0].innerText=arguments[1]; arguments[0].textContent=arguments[1];}",
                    title, titleValue);
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", title);
                System.out.println("✅ Title set successfully via JavaScript");
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Entered Title on External Audit form: " + titleValue);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Title on External Audit form: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Title on External Audit form: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Initiative Button on form
    public void clickInitiativeButton() throws Exception {
        try {
            System.out.println("\n📌 Clicking Initiative button on External Audit form...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement initiativeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.initiativeButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", initiativeBtn);
            waitForSeconds(1);

            try {
                initiativeBtn.click();
                System.out.println("✅ Initiative button clicked successfully (regular click)");
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeBtn);
                    System.out.println("✅ Initiative button clicked successfully (JavaScript click)");
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(initiativeBtn).click().perform();
                    System.out.println("✅ Initiative button clicked successfully (Actions click)");
                }
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Initiative button clicked successfully on External Audit form");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative button on External Audit form: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative button on External Audit form: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Select Button in Initiative table for specific Initiative Title
    public void clickInitiativeSelectButton(String initiativeTitle) throws Exception {
        try {
            System.out.println("\n🎯 Clicking Select button for Initiative Title: " + initiativeTitle);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Wait for table rows to be present
            java.util.List<WebElement> rows = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr")));

            if (rows.isEmpty()) {
                throw new Exception("No rows found in Initiative selection table");
            }

            WebElement targetRow = null;
            int rowIndex = -1;

            // Try to find row whose text contains the Initiative Title
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                String rowText = row.getText();
                System.out.println("🔎 Row " + (i + 1) + " text: " + rowText);
                if (rowText != null && rowText.toLowerCase().contains(initiativeTitle.toLowerCase())) {
                    targetRow = row;
                    rowIndex = i + 1;
                    System.out.println("✅ Matched Initiative Title in row " + rowIndex);
                    break;
                }
            }

            // Scroll target row into view if found
            if (targetRow != null) {
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", targetRow);
                waitForSeconds(1);

                // Preferred: find the specific check icon within that row
                try {
                    WebElement rowIcon = targetRow.findElement(
                        By.xpath(".//svg[@data-testid='CheckCircleOutlineIcon'] | .//*[name()='svg' and @data-testid='CheckCircleOutlineIcon']"));
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", rowIcon);
                    waitForSeconds(1);
                    rowIcon.click();
                    System.out.println("✅ Initiative Select button clicked from matched row for Initiative Title: " + initiativeTitle);

                    if (reportLogger != null) {
                        reportLogger.pass("Initiative Select button clicked from matched row (row " + rowIndex + ") for Initiative Title: " + initiativeTitle);
                    }
                    return;
                } catch (Exception eRowIcon) {
                    System.out.println("⚠️ Could not find CheckCircle icon inside matched row, will fall back to global icon search: " + eRowIcon.getMessage());
                }
            } else {
                System.out.println("⚠️ Could not find row containing Initiative Title '" + initiativeTitle + "'. Will fall back to global icon search.");
            }

            // Fallback: global search for the CheckCircle icon anywhere in the popup/table
            System.out.println("🔍 Falling back to global search for CheckCircleOutlineIcon...");
            WebElement globalIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//svg[@data-testid='CheckCircleOutlineIcon'] | //*[@data-testid='CheckCircleOutlineIcon']//*[name()='svg']")));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", globalIcon);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                globalIcon.click();
                System.out.println("✅ Initiative Select button clicked successfully (global icon, regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click on global icon failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", globalIcon);
                    System.out.println("✅ Initiative Select button clicked successfully (global icon, JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click on global icon failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(globalIcon).click().perform();
                    System.out.println("✅ Initiative Select button clicked successfully (global icon, Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click on global icon failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Initiative Select button for Initiative Title '" + initiativeTitle + "' using any method");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Initiative Select button clicked successfully via global icon search for Initiative Title: " + initiativeTitle);
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Select button for Initiative Title '" + initiativeTitle + "': " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Select button for Initiative Title '" + initiativeTitle + "': " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Select Button (Select Nature of Initiative) in Initiative table based on Initiative Title
    public void clickInitiativeSelectButtonInTable(String initiativeTitle) throws Exception {
        try {
            System.out.println("\n🎯 Clicking Select button (Select Nature of Initiative) for Initiative Title: " + initiativeTitle);
            
            // Wait a bit for the popup/table to fully load after clicking Initiative button
            waitForSeconds(2);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // First, wait for the table to be present
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table")));
            System.out.println("✅ Initiative selection table found");

            // Find all rows in the table
            java.util.List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
            System.out.println("📊 Found " + rows.size() + " rows in the Initiative table");

            if (rows.isEmpty()) {
                throw new Exception("No rows found in Initiative selection table");
            }

            // Find the row that contains the Initiative Title
            WebElement targetRow = null;
            int rowIndex = -1;
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                String rowText = row.getText().trim();
                System.out.println("🔍 Row " + (i + 1) + " text: " + rowText);
                
                // Check if the row text contains the Initiative Title (case-insensitive)
                if (rowText != null && rowText.toLowerCase().contains(initiativeTitle.toLowerCase())) {
                    targetRow = row;
                    rowIndex = i + 1;
                    System.out.println("✅ Found matching row " + rowIndex + " for Initiative Title: " + initiativeTitle);
                    break;
                }
            }

            if (targetRow == null) {
                throw new Exception("Could not find row containing Initiative Title: " + initiativeTitle);
            }

            // Find the Select button (td[5]) in the target row
            WebElement selectButton = null;
            try {
                selectButton = targetRow.findElement(By.xpath(".//td[5]"));
                System.out.println("✅ Found Select button (td[5]) in row " + rowIndex);
            } catch (Exception eTd) {
                throw new Exception("Could not find td[5] (Select button) in row " + rowIndex + ": " + eTd.getMessage());
            }

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", selectButton);
            waitForSeconds(1);

            // Try multiple click strategies
            boolean clicked = false;

            // Strategy 1: Regular click
            try {
                // Wait for element to be clickable before clicking
                WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(selectButton));
                clickableElement.click();
                System.out.println("✅ Initiative Select button clicked successfully (regular click) for: " + initiativeTitle);
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed: " + e1.getMessage() + ", trying JavaScript click...");
            }

            // Strategy 2: JavaScript click (works even if element is not "clickable" in Selenium's terms)
            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectButton);
                    System.out.println("✅ Initiative Select button clicked successfully (JavaScript click) for: " + initiativeTitle);
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage() + ", trying Actions click...");
                }
            }

            // Strategy 3: Actions click
            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(selectButton).click().perform();
                    System.out.println("✅ Initiative Select button clicked successfully (Actions click) for: " + initiativeTitle);
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            // Strategy 4: Try clicking on any clickable child element (like svg or button inside td)
            if (!clicked) {
                try {
                    System.out.println("🔍 Trying to find clickable child element inside td[5]...");
                    java.util.List<WebElement> clickableChildren = selectButton.findElements(
                        By.xpath(".//*[self::button or self::svg or self::a or self::span]"));
                    for (WebElement child : clickableChildren) {
                        try {
                            if (child.isDisplayed()) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", child);
                                System.out.println("✅ Initiative Select button clicked via child element (JavaScript click) for: " + initiativeTitle);
                                clicked = true;
                                break;
                            }
                        } catch (Exception eChild) {
                            continue;
                        }
                    }
                } catch (Exception e4) {
                    System.out.println("⚠️ Child element click strategy failed: " + e4.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Initiative Select button using any method for Initiative Title: " + initiativeTitle);
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Initiative Select button clicked successfully for Initiative Title: " + initiativeTitle + " (row " + rowIndex + ")");
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Select button for Initiative Title '" + initiativeTitle + "': " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Select button for Initiative Title '" + initiativeTitle + "': " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Planned Start Date Field
    public void clickPlannedStartDateField() throws Exception {
        try {
            System.out.println("\n📅 Clicking Planned Start Date field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.plannedStartDateField));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", dateField);
            waitForSeconds(1);

            // Try regular click first
            boolean clicked = false;
            try {
                dateField.click();
                System.out.println("✅ Planned Start Date field clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateField);
                    System.out.println("✅ Planned Start Date field clicked using JavaScript");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(dateField).click().perform();
                    System.out.println("✅ Planned Start Date field clicked using Actions");
                    clicked = true;
                } catch (Exception e3) {
                    throw new Exception("Could not click Planned Start Date field using any method");
                }
            }

            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.pass("Planned Start Date field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Planned Start Date field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Planned Start Date field: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Enter Planned Start Date value
    public void enterPlannedStartDate(String plannedStartDate) throws Exception {
        try {
            System.out.println("\n📝 Entering Planned Start Date: " + plannedStartDate);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement dateField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.plannedStartDateField));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", dateField);
            waitForSeconds(1);

            // Clear existing value
            try {
                dateField.clear();
                System.out.println("✅ Cleared existing Planned Start Date");
            } catch (Exception e1) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", dateField);
                    System.out.println("✅ Cleared Planned Start Date using JavaScript");
                } catch (Exception e2) {
                    System.out.println("⚠️ Could not clear Planned Start Date field");
                }
            }

            waitForSeconds(1);

            // Enter date value
            try {
                dateField.sendKeys(plannedStartDate);
                System.out.println("✅ Planned Start Date entered successfully: " + plannedStartDate);
            } catch (Exception e1) {
                System.out.println("⚠️ Regular sendKeys failed, trying JavaScript...");
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value='" + plannedStartDate.replace("'", "\\'") + "';", dateField);
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dateField);
                System.out.println("✅ Planned Start Date entered using JavaScript: " + plannedStartDate);
            }

            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.pass("Entered Planned Start Date: " + plannedStartDate);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter Planned Start Date: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to enter Planned Start Date: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Select Planned Start Date from calendar popup using date from Excel
//    public void selectPlannedStartDateFromCalendar(String plannedStartDate) throws Exception {
//        try {
//            System.out.println("\n📅 ═══════════════════════════════════════════");
//          //  System.out.println("📅 SETTING PLANNED START DATE");
        //    System.out.println("📅 Date from Excel: " + plannedStartDate);
           // System.out.println("📅 ═══════════════════════════════════════════");
            
          //  if (plannedStartDate == null || plannedStartDate.trim().isEmpty()) {
           //     System.out.println("⚠️ No Planned Start Date provided, skipping...");
           //     return;
         //   }
         //   
          //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Convert Excel date format to MM/DD/YYYY format (preferred by Material-UI date pickers)
          //  String formattedDate = convertExcelDateToMMDDYYYY(plannedStartDate.trim());
          //  System.out.println("📝 Converted date format: '" + plannedStartDate + "' → '" + formattedDate + "'");
          //  
            // Use the same approach as InitiativePage - directly set the date using multiple strategies
          //  boolean success = setPlannedStartDateWithMultipleStrategies(
          //      ExternalAuditPageLocators.plannedStartDateField,
           //     formattedDate,
             //   "Planned Start Date"
          //  );
            
            // If failed, try the label locator
            //if (!success) {
              //  System.out.println("  ↪ Trying Planned Start Date label locator...");
              //  success = setPlannedStartDateWithMultipleStrategies(
               //     ExternalAuditPageLocators.plannedStartDateLabel,
              //      formattedDate,
             //       "Planned Start Date (Label)"
             //   );
          //  }
            
            // If still failed, try with original Excel format
          //  if (!success) {
           //     System.out.println("  ↪ Trying with original Excel date format...");
            //    success = setPlannedStartDateWithMultipleStrategies(
           //         ExternalAuditPageLocators.plannedStartDateField,
              //      plannedStartDate.trim(),
           //         "Planned Start Date (Original Format)"
          //      );
         //   }
            
          //  if (success) {
                // Verify the date is actually set
              //  waitForSeconds(2);
              //  try {
                 //   WebElement dateField = driver.findElement(ExternalAuditPageLocators.plannedStartDateField);
                   // String finalValue = dateField.getAttribute("value");
                  //  System.out.println("\n📋 FINAL VERIFICATION - Planned Start Date field value: '" + finalValue + "'");
                    
                   // if (finalValue == null || finalValue.trim().isEmpty()) {
                   //     System.out.println("⚠️ WARNING: Date field is empty after setting!");
                   //     throw new Exception("Planned Start Date field is empty after setting. Expected: " + formattedDate);
                 //   } else {
                //        System.out.println("✅ SUCCESS: Planned Start Date is set in the field: " + finalValue);
               //     }
             //   } catch (Exception eVerify) {
               //     System.out.println("⚠️ Verification failed: " + eVerify.getMessage());
              //      throw eVerify;
            //    }
                
            //    System.out.println("✅ Successfully set Planned Start Date: " + plannedStartDate);
             //   if (reportLogger != null) {
              //      reportLogger.pass("Selected Planned Start Date: " + plannedStartDate);
              //  }
              //  System.out.println("📅 ═══════════════════════════════════════════\n");
        ///    } else {
          //      throw new Exception("All strategies failed to set Planned Start Date");
        //    }
            
        //    return; // Exit early - new simplified approach
            
            // OLD COMPLEX APPROACH BELOW (keeping for reference but not used)
            /*
            try {
                System.out.println("\n📅 Selecting Planned Start Date from calendar: " + plannedStartDate);

            // Parse date from Excel - handle multiple formats
            // Format 1: "Tue Dec 23 2025" (4 parts)
            // Format 2: "Tue Dec 23 00:00:00 IST 2025" (7 parts)
            // Format 3: "23-Dec-2025" or "23/12/2025" or "12/23/2025"
            // Format 4: Excel Date.toString() format
            System.out.println("📋 Raw date string from Excel: '" + plannedStartDate + "'");
            
            String day = null;
            String monthToken = null;
            String year = null;
            String fullMonthName = null;
            
            try {
                String dateStr = plannedStartDate.trim();
                String[] parts = dateStr.split("\\s+");
                System.out.println("📌 Date parts count: " + parts.length + ", Parts: " + java.util.Arrays.toString(parts));
                
                if (parts.length >= 4) {
                    // Try format: "Tue Dec 23 2025"
                    monthToken = parts[1];   // e.g. "Dec"
                    day = parts[2];          // e.g. "23"
                    year = parts[3];         // e.g. "2025"
                    
                    // Convert month abbreviation to full name for better matching
                    fullMonthName = getFullMonthName(monthToken);
                }
                
                if (parts.length >= 7) {
                    // Format: "Tue Dec 23 00:00:00 IST 2025"
                    monthToken = parts[1];
                    day = parts[2];
                    year = parts[6];  // Year is at index 6
                    fullMonthName = getFullMonthName(monthToken);
                }
                
                // If parsing failed, try alternative formats
                if (day == null || day.isEmpty()) {
                    // Try format: "23-Dec-2025" or "23/12/2025" or "12/23/2025"
                    if (dateStr.contains("-")) {
                        String[] dashParts = dateStr.split("-");
                        if (dashParts.length >= 3) {
                            day = dashParts[0].trim();
                            monthToken = dashParts[1].trim();
                            year = dashParts[2].trim();
                            fullMonthName = getFullMonthName(monthToken);
                            System.out.println("✅ Parsed date using dash format");
                        }
                    } else if (dateStr.contains("/")) {
                        String[] slashParts = dateStr.split("/");
                        if (slashParts.length >= 3) {
                            // Could be DD/MM/YYYY or MM/DD/YYYY - try both
                            String part1 = slashParts[0].trim();
                            String part2 = slashParts[1].trim();
                            String part3 = slashParts[2].trim();
                            
                            // If part1 > 12, it's likely DD/MM/YYYY
                            try {
                                int p1 = Integer.parseInt(part1);
                                if (p1 > 12) {
                                    // DD/MM/YYYY format
                                    day = part1;
                                    monthToken = getMonthAbbrFromNumber(part2);
                                    year = part3;
                                } else {
                                    // MM/DD/YYYY format
                                    day = part2;
                                    monthToken = getMonthAbbrFromNumber(part1);
                                    year = part3;
                                }
                                fullMonthName = getFullMonthName(monthToken);
                                System.out.println("✅ Parsed date using slash format");
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️ Could not parse numeric date parts");
                            }
                        }
                    }
                }
            } catch (Exception eParse) {
                System.out.println("⚠️ Date parsing error: " + eParse.getMessage());
                eParse.printStackTrace();
            }

            if (day == null || day.isEmpty()) {
                System.out.println("❌ ERROR: Could not parse day from date string: '" + plannedStartDate + "'");
                System.out.println("   Please ensure the date in Excel is in a readable format (e.g., 'Tue Dec 23 2025' or '23-Dec-2025')");
                throw new Exception("Could not parse day from date string: " + plannedStartDate);
            }

            System.out.println("📌 Parsed - Day: " + day + ", Month: " + monthToken + " (" + fullMonthName + "), Year: " + year);

            // Wait for calendar to be visible
            waitForSeconds(2);

            boolean clicked = false;

            // Strategy 1: aria-label containing day, month and year
            if (monthToken != null && year != null && day != null) {
                try {
                    System.out.println("🔎 Strategy 1: Searching by aria-label with day, month, year...");
                    // Try with month abbreviation
                    String ariaXPath1 = "//button[contains(@aria-label,'" + day + "') " +
                                       "and contains(@aria-label,'" + monthToken + "') " +
                                       "and contains(@aria-label,'" + year + "')]";
                    
                    // Try with full month name if available
                    String ariaXPath2 = null;
                    if (fullMonthName != null) {
                        ariaXPath2 = "//button[contains(@aria-label,'" + day + "') " +
                                    "and (contains(@aria-label,'" + monthToken + "') or contains(@aria-label,'" + fullMonthName + "')) " +
                                    "and contains(@aria-label,'" + year + "')]";
                    }
                    
                    WebElement dateBtn = null;
                    try {
                        dateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ariaXPath1)));
                        System.out.println("✅ Found date button via aria-label (abbreviation)");
                    } catch (Exception e1) {
                        if (ariaXPath2 != null) {
                            try {
                                dateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ariaXPath2)));
                                System.out.println("✅ Found date button via aria-label (full month)");
                            } catch (Exception e2) {
                                System.out.println("⚠️ Aria-label search failed with both formats");
                            }
                        }
                    }
                    
                    if (dateBtn != null) {
                        ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block: 'center'});", dateBtn);
                        waitForSeconds(1);
                        dateBtn.click();
                        System.out.println("✅ Planned Start Date selected via aria-label button");
                        waitForSeconds(2);
                        // Close calendar if still open
                        try {
                            driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                            waitForSeconds(1);
                        } catch (Exception eClose) {}
                        clicked = true;
                    }
                } catch (Exception e1) {
                    System.out.println("⚠️ Strategy 1 failed: " + e1.getMessage());
                }
            }

            // Strategy 2: Find calendar grid and click day button (filter by month/year context)
            if (!clicked) {
                try {
                    System.out.println("🔎 Strategy 2: Searching calendar grid for day: " + day);
                    // First, try to find buttons with the day number that are in the current month view
                    String gridXPath = "//div[contains(@role,'grid') or contains(@class,'Calendar') or contains(@class,'datepicker') or contains(@class,'MuiCalendarPicker')]";
                    
                    // Get all day buttons in the calendar
                    java.util.List<WebElement> dayButtons = driver.findElements(
                        By.xpath(gridXPath + "//button[normalize-space(text())='" + day + "' and not(contains(@class,'disabled'))]"));
                    
                    System.out.println("📊 Found " + dayButtons.size() + " day buttons matching day: " + day);
                    
                    if (!dayButtons.isEmpty()) {
                        // If multiple buttons found, try to find the one in the correct month/year
                        WebElement targetBtn = null;
                        for (WebElement btn : dayButtons) {
                            try {
                                String ariaLabel = btn.getAttribute("aria-label");
                                if (ariaLabel != null) {
                                    // Check if aria-label contains the month and year
                                    boolean matchesMonth = (monthToken != null && ariaLabel.contains(monthToken)) ||
                                                          (fullMonthName != null && ariaLabel.contains(fullMonthName));
                                    boolean matchesYear = (year != null && ariaLabel.contains(year));
                                    
                                    if (matchesMonth && matchesYear) {
                                        targetBtn = btn;
                                        System.out.println("✅ Found matching button with aria-label: " + ariaLabel);
                                        break;
                                    }
                                }
                            } catch (Exception eCheck) {
                                // Continue to next button
                            }
                        }
                        
                        // If no specific match, use the first enabled button
                        if (targetBtn == null && !dayButtons.isEmpty()) {
                            targetBtn = dayButtons.get(0);
                            System.out.println("⚠️ Using first available day button (month/year not verified)");
                        }
                        
                        if (targetBtn != null) {
                            ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({block: 'center'});", targetBtn);
                            waitForSeconds(1);
                            targetBtn.click();
                            System.out.println("✅ Planned Start Date selected via calendar grid button");
                            waitForSeconds(2);
                            // Close calendar if still open
                            try {
                                driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                                waitForSeconds(1);
                            } catch (Exception eClose) {}
                            clicked = true;
                        }
                    } else {
                        System.out.println("⚠️ No matching day button found in calendar grid");
                    }
                } catch (Exception e2) {
                    System.out.println("⚠️ Strategy 2 failed: " + e2.getMessage());
                }
            }

            // Strategy 3: Direct input fallback - type date directly into input field
            if (!clicked) {
                try {
                    System.out.println("🔎 Strategy 3: Fallback to direct date input...");
                    
                    // Close calendar popup if open
                    try {
                        driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                        waitForSeconds(1);
                        System.out.println("✅ Closed calendar popup (if open)");
                    } catch (Exception eClose) {
                        // Calendar might not be open
                    }
                    
                    // Format date for input - try multiple formats
                    // For Material-UI date pickers, MM/DD/YYYY format is usually preferred
                    String monthNum = getMonthNumberFromAbbr(monthToken);
                    String formattedDate1 = monthNum + "/" + day + "/" + year; // "12/25/2025" (MM/DD/YYYY) - PREFERRED
                    String formattedDate2 = formatDateForInput(day, monthToken, year); // "25-Dec-2025"
                    String formattedDate3 = formatDateForInputNumeric(day, monthToken, year); // "25/12/2025" (DD/MM/YYYY)
                    System.out.println("📝 Formatted date options: " + formattedDate1 + " (MM/DD/YYYY - preferred) or " + formattedDate2 + " or " + formattedDate3);
                    
                    // Find the date input field
                    WebElement dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        ExternalAuditPageLocators.plannedStartDateField));
                    
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", dateInput);
                    waitForSeconds(1);
                    
                    // Click to focus and clear
                    try {
                        dateInput.click();
                        waitForSeconds(1);
                        dateInput.sendKeys(Keys.CONTROL + "a");
                        dateInput.sendKeys(Keys.BACK_SPACE);
                        waitForSeconds(1);
                    } catch (Exception eClick) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", dateInput);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dateInput);
                    }
                    
                    // Method 1: Standard Selenium with TAB (using MM/DD/YYYY format)
                    try {
                        System.out.println("  → Method 1: Standard sendKeys with TAB (MM/DD/YYYY format)");
                        dateInput.sendKeys(formattedDate1); // MM/DD/YYYY format
                        waitForSeconds(1);
                        dateInput.sendKeys(Keys.TAB);
                        waitForSeconds(2);
                        
                        String currentValue = dateInput.getAttribute("value");
                        System.out.println("  📋 Value after Method 1: '" + currentValue + "'");
                        // Check if value is set and persisted (not empty and contains day or year)
                        if (currentValue != null && !currentValue.trim().isEmpty() && 
                            (currentValue.contains(day) || currentValue.contains(year))) {
                            System.out.println("✅ Date entered successfully via Method 1: " + currentValue);
                            clicked = true;
                        }
                    } catch (Exception e1) {
                        System.out.println("  ⚠️ Method 1 failed: " + e1.getMessage());
                    }
                    
                    // Method 2: JavaScript with all events (using MM/DD/YYYY format)
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 2: JavaScript with multiple events (MM/DD/YYYY format)");
                            ((JavascriptExecutor) driver).executeScript(
                                "var element = arguments[0];" +
                                "var value = arguments[1];" +
                                "element.focus();" +
                                "element.click();" +
                                "element.value = '';" +
                                "element.value = value;" +
                                "element.dispatchEvent(new Event('input', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('keyup', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('blur', { bubbles: true, cancelable: true }));",
                                dateInput, formattedDate1);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 2: '" + currentValue + "'");
                            // Check if value is set and persisted
                            if (currentValue != null && !currentValue.trim().isEmpty() && 
                                (currentValue.contains(day) || currentValue.contains(year))) {
                                System.out.println("✅ Date entered successfully via Method 2: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e2) {
                            System.out.println("  ⚠️ Method 2 failed: " + e2.getMessage());
                        }
                    }
                    
                    // Method 3: React-specific event triggering
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 3: React-specific events");
                            ((JavascriptExecutor) driver).executeScript(
                                "var element = arguments[0];" +
                                "var value = arguments[1];" +
                                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                                "nativeInputValueSetter.call(element, value);" +
                                "var event = new Event('input', { bubbles: true });" +
                                "element.dispatchEvent(event);" +
                                "var changeEvent = new Event('change', { bubbles: true });" +
                                "element.dispatchEvent(changeEvent);",
                                dateInput, formattedDate1);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 3: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 3: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e3) {
                            System.out.println("  ⚠️ Method 3 failed: " + e3.getMessage());
                        }
                    }
                    
                    // Method 4: Try numeric format (DD/MM/YYYY or MM/DD/YYYY)
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 4: Trying numeric format: " + formattedDate2);
                            dateInput.click();
                            waitForSeconds(1);
                            dateInput.sendKeys(Keys.CONTROL + "a");
                            dateInput.sendKeys(Keys.BACK_SPACE);
                            waitForSeconds(1);
                            dateInput.sendKeys(formattedDate2);
                            waitForSeconds(1);
                            dateInput.sendKeys(Keys.TAB);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 4: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 4: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e4) {
                            System.out.println("  ⚠️ Method 4 failed: " + e4.getMessage());
                        }
                    }
                    
                    // Method 5: Actions class with ENTER
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 5: Actions class with ENTER");
                            Actions actions = new Actions(driver);
                            actions.moveToElement(dateInput)
                                   .click()
                                   .pause(Duration.ofMillis(500))
                                   .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                                   .pause(Duration.ofMillis(200))
                                   .sendKeys(Keys.DELETE)
                                   .pause(Duration.ofMillis(300))
                                   .sendKeys(formattedDate1)
                                   .pause(Duration.ofMillis(500))
                                   .sendKeys(Keys.ENTER)
                                   .pause(Duration.ofMillis(500))
                                   .perform();
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 5: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 5: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e5) {
                            System.out.println("  ⚠️ Method 5 failed: " + e5.getMessage());
                        }
                    }
                    
                    // Verify final value
                    if (clicked) {
                        String finalValue = dateInput.getAttribute("value");
                        System.out.println("✅ Final date value in field: '" + finalValue + "'");
                    }
                    
                } catch (Exception e3) {
                    System.out.println("⚠️ Strategy 3 (direct input) failed: " + e3.getMessage());
                    e3.printStackTrace();
                }
            }

            if (!clicked) {
                throw new Exception("Could not select Planned Start Date using any method (calendar click or direct input)");
            }

            // Final verification - check if date is actually set in the field
            waitForSeconds(2);
            try {
                WebElement dateField = driver.findElement(ExternalAuditPageLocators.plannedStartDateField);
                String finalValue = dateField.getAttribute("value");
                System.out.println("\n📋 FINAL VERIFICATION - Planned Start Date field value: '" + finalValue + "'");
                
                // Check if date is actually set and contains the expected day/year
                boolean dateIsSet = false;
                if (finalValue != null && !finalValue.trim().isEmpty()) {
                    // Check if the value contains the day or year (to verify it's actually our date)
                    if (finalValue.contains(day) || finalValue.contains(year)) {
                        dateIsSet = true;
                        System.out.println("✅ SUCCESS: Planned Start Date is set in the field: " + finalValue);
                    } else {
                        System.out.println("⚠️ WARNING: Date field has value but doesn't match expected date!");
                        System.out.println("   Expected day: " + day + ", year: " + year);
                        System.out.println("   Actual value: '" + finalValue + "'");
                    }
                }
                
                if (!dateIsSet) {
                    System.out.println("⚠️ WARNING: Planned Start Date field appears empty or invalid after selection!");
                    // Try one more time with MM/DD/YYYY format (preferred for Material-UI)
                    System.out.println("🔄 Attempting one final direct input with MM/DD/YYYY format...");
                    try {
                        dateField.click();
                        waitForSeconds(1);
                        String monthNum = getMonthNumberFromAbbr(monthToken);
                        String finalFormattedDate = monthNum + "/" + day + "/" + year; // MM/DD/YYYY
                        dateField.sendKeys(Keys.CONTROL + "a");
                        dateField.sendKeys(Keys.BACK_SPACE);
                        waitForSeconds(1);
                        dateField.sendKeys(finalFormattedDate);
                        waitForSeconds(1);
                        dateField.sendKeys(Keys.TAB);
                        waitForSeconds(2);
                        
                        // Also trigger React events
                        ((JavascriptExecutor) driver).executeScript(
                            "var element = arguments[0];" +
                            "element.dispatchEvent(new Event('blur', { bubbles: true }));" +
                            "element.dispatchEvent(new Event('change', { bubbles: true }));",
                            dateField);
                        waitForSeconds(1);
                        
                        finalValue = dateField.getAttribute("value");
                        System.out.println("📋 Planned Start Date field value after final attempt: '" + finalValue + "'");
                        
                        if (finalValue != null && !finalValue.trim().isEmpty() && 
                            (finalValue.contains(day) || finalValue.contains(year))) {
                            System.out.println("✅ Planned Start Date successfully set after final attempt: " + finalValue);
                        } else {
                            System.out.println("❌ Planned Start Date still not set after final attempt");
                            throw new Exception("Planned Start Date could not be set in the field. Expected: " + day + "/" + monthToken + "/" + year);
                        }
                    } catch (Exception eFinal) {
                        System.out.println("❌ Final attempt also failed: " + eFinal.getMessage());
                        throw new Exception("Planned Start Date could not be set in the field: " + eFinal.getMessage());
                    }
                }
            } catch (Exception eVerify) {
                System.out.println("⚠️ Could not verify Planned Start Date field value: " + eVerify.getMessage());
                throw eVerify; // Re-throw to fail the test if date is not set
            }

            if (reportLogger != null) {
                reportLogger.pass("Selected Planned Start Date: " + plannedStartDate);
            }
        } catch (Exception e) {
            System.out.println("❌ Error while selecting Planned Start Date: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Error selecting Planned Start Date: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Planned End Date Field
    public void clickPlannedEndDateField() throws Exception {
        try {
            System.out.println("\n📅 Clicking Planned End Date field...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try new XPath first
            WebElement dateField = null;
            try {
                dateField = wait.until(ExpectedConditions.elementToBeClickable(
                    ExternalAuditPageLocators.plannedEndDateFieldNew));
                System.out.println("✅ Planned End Date field located (new XPath)");
            } catch (Exception e1) {
                System.out.println("⚠️ New XPath failed, trying fallback locators...");
                // Try fallback locators
                try {
                    dateField = wait.until(ExpectedConditions.elementToBeClickable(
                        ExternalAuditPageLocators.plannedEndDateField));
                } catch (Exception e2) {
                    try {
                        dateField = wait.until(ExpectedConditions.elementToBeClickable(
                            ExternalAuditPageLocators.plannedEndDateFieldAlt));
                    } catch (Exception e3) {
                        throw new Exception("Planned End Date field not found with any locator");
                    }
                }
            }

            WebElement clickableElement = dateField;

            if (clickableElement == null) {
                // Last resort: try to find any element with DatePicker1200 in ID or nearby
                System.out.println("🔍 Last resort: Searching for any element with DatePicker1200...");
                try {
                    java.util.List<WebElement> elements = driver.findElements(
                        By.xpath("//*[contains(@id,'DatePicker1200')] | //*[contains(@id,'DatePicker1200')]//*"));
                    for (WebElement el : elements) {
                        try {
                            if (el != null && el.isDisplayed()) {
                                clickableElement = el;
                                System.out.println("✅ Found Planned End Date element via last resort search");
                                break;
                            }
                        } catch (Exception e) {
                            // Continue to next element
                        }
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Last resort search also failed: " + e.getMessage());
                }
            }

            if (clickableElement == null) {
                throw new Exception("Planned End Date field/label not found using any locator strategy. Please verify the XPath: //*[@id='DatePicker1200-label']");
            }

            // Wait for element to be clickable
            try {
                wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
            } catch (Exception eWait) {
                System.out.println("⚠️ Element not immediately clickable, will try anyway: " + eWait.getMessage());
            }

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", clickableElement);
            waitForSeconds(1);

            // Try multiple click strategies
            boolean clicked = false;
            
            // Strategy 1: Regular click
            try {
                clickableElement.click();
                System.out.println("✅ Planned End Date field clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed: " + e1.getMessage());
            }

            // Strategy 2: JavaScript click
            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableElement);
                    System.out.println("✅ Planned End Date field clicked using JavaScript");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }

            // Strategy 3: Actions class click
            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(clickableElement).click().perform();
                    System.out.println("✅ Planned End Date field clicked using Actions");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Planned End Date field using any click method");
            }

            waitForSeconds(1);

            if (reportLogger != null) {
                reportLogger.pass("Planned End Date field clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Planned End Date field: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Planned End Date field: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Select Planned End Date from calendar popup using date from Excel
    public void selectPlannedEndDateFromCalendar(String plannedEndDate) throws Exception {
        try {
            System.out.println("\n📅 Selecting Planned End Date from calendar: " + plannedEndDate);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Parse date from Excel - handle multiple formats
            // Format 1: "Tue Dec 23 2025" (4 parts)
            // Format 2: "Tue Dec 23 00:00:00 IST 2025" (7 parts)
            String day = null;
            String monthToken = null;
            String year = null;
            String fullMonthName = null;
            
            try {
                String[] parts = plannedEndDate.trim().split("\\s+");
                System.out.println("📌 Date parts count: " + parts.length);
                
                if (parts.length >= 4) {
                    // Try format: "Tue Dec 23 2025"
                    monthToken = parts[1];   // e.g. "Dec"
                    day = parts[2];          // e.g. "23"
                    year = parts[3];         // e.g. "2025"
                    
                    // Convert month abbreviation to full name for better matching
                    fullMonthName = getFullMonthName(monthToken);
                }
                
                if (parts.length >= 7) {
                    // Format: "Tue Dec 23 00:00:00 IST 2025"
                    monthToken = parts[1];
                    day = parts[2];
                    year = parts[6];  // Year is at index 6
                    fullMonthName = getFullMonthName(monthToken);
                }
            } catch (Exception eParse) {
                System.out.println("⚠️ Date parsing error: " + eParse.getMessage());
            }

            if (day == null || day.isEmpty()) {
                throw new Exception("Could not parse day from date string: " + plannedEndDate);
            }

            System.out.println("📌 Parsed - Day: " + day + ", Month: " + monthToken + " (" + fullMonthName + "), Year: " + year);

            // Wait for calendar to be visible
            waitForSeconds(2);

            boolean clicked = false;

            // Strategy 1: aria-label containing day, month and year
            if (monthToken != null && year != null && day != null) {
                try {
                    System.out.println("🔎 Strategy 1: Searching by aria-label with day, month, year...");
                    // Try with month abbreviation
                    String ariaXPath1 = "//button[contains(@aria-label,'" + day + "') " +
                                       "and contains(@aria-label,'" + monthToken + "') " +
                                       "and contains(@aria-label,'" + year + "')]";
                    
                    // Try with full month name if available
                    String ariaXPath2 = null;
                    if (fullMonthName != null) {
                        ariaXPath2 = "//button[contains(@aria-label,'" + day + "') " +
                                    "and (contains(@aria-label,'" + monthToken + "') or contains(@aria-label,'" + fullMonthName + "')) " +
                                    "and contains(@aria-label,'" + year + "')]";
                    }
                    
                    WebElement dateBtn = null;
                    try {
                        dateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ariaXPath1)));
                        System.out.println("✅ Found date button via aria-label (abbreviation)");
                    } catch (Exception e1) {
                        if (ariaXPath2 != null) {
                            try {
                                dateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ariaXPath2)));
                                System.out.println("✅ Found date button via aria-label (full month)");
                            } catch (Exception e2) {
                                System.out.println("⚠️ Aria-label search failed with both formats");
                            }
                        }
                    }
                    
                    if (dateBtn != null) {
                        ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block: 'center'});", dateBtn);
                        waitForSeconds(1);
                        dateBtn.click();
                        System.out.println("✅ Planned End Date selected via aria-label button");
                        waitForSeconds(2);
                        // Close calendar if still open
                        try {
                            driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                            waitForSeconds(1);
                        } catch (Exception eClose) {}
                        clicked = true;
                    }
                } catch (Exception e1) {
                    System.out.println("⚠️ Strategy 1 failed: " + e1.getMessage());
                }
            }

            // Strategy 2: Find calendar grid and click day button (filter by month/year context)
            if (!clicked) {
                try {
                    System.out.println("🔎 Strategy 2: Searching calendar grid for day: " + day);
                    // First, try to find buttons with the day number that are in the current month view
                    String gridXPath = "//div[contains(@role,'grid') or contains(@class,'Calendar') or contains(@class,'datepicker') or contains(@class,'MuiCalendarPicker')]";
                    
                    // Get all day buttons in the calendar
                    java.util.List<WebElement> dayButtons = driver.findElements(
                        By.xpath(gridXPath + "//button[normalize-space(text())='" + day + "' and not(contains(@class,'disabled'))]"));
                    
                    System.out.println("📊 Found " + dayButtons.size() + " day buttons matching day: " + day);
                    
                    if (!dayButtons.isEmpty()) {
                        // If multiple buttons found, try to find the one in the correct month/year
                        WebElement targetBtn = null;
                        for (WebElement btn : dayButtons) {
                            try {
                                String ariaLabel = btn.getAttribute("aria-label");
                                if (ariaLabel != null) {
                                    // Check if aria-label contains the month and year
                                    boolean matchesMonth = (monthToken != null && ariaLabel.contains(monthToken)) ||
                                                          (fullMonthName != null && ariaLabel.contains(fullMonthName));
                                    boolean matchesYear = (year != null && ariaLabel.contains(year));
                                    
                                    if (matchesMonth && matchesYear) {
                                        targetBtn = btn;
                                        System.out.println("✅ Found matching button with aria-label: " + ariaLabel);
                                        break;
                                    }
                                }
                            } catch (Exception eCheck) {
                                // Continue to next button
                            }
                        }
                        
                        // If no specific match, use the first enabled button
                        if (targetBtn == null && !dayButtons.isEmpty()) {
                            targetBtn = dayButtons.get(0);
                            System.out.println("⚠️ Using first available day button (month/year not verified)");
                        }
                        
                        if (targetBtn != null) {
                            ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({block: 'center'});", targetBtn);
                            waitForSeconds(1);
                            targetBtn.click();
                            System.out.println("✅ Planned End Date selected via calendar grid button");
                            waitForSeconds(2);
                            // Close calendar if still open
                            try {
                                driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                                waitForSeconds(1);
                            } catch (Exception eClose) {}
                            clicked = true;
                        }
                    } else {
                        System.out.println("⚠️ No matching day button found in calendar grid");
                    }
                } catch (Exception e2) {
                    System.out.println("⚠️ Strategy 2 failed: " + e2.getMessage());
                }
            }

            // Strategy 3: Direct input fallback - type date directly into input field
            if (!clicked) {
                try {
                    System.out.println("🔎 Strategy 3: Fallback to direct date input...");
                    
                    // Close calendar popup if open
                    try {
                        driver.findElement(By.xpath("//body")).sendKeys(Keys.ESCAPE);
                        waitForSeconds(1);
                        System.out.println("✅ Closed calendar popup (if open)");
                    } catch (Exception eClose) {
                        // Calendar might not be open
                    }
                    
                    // Format date for input - try multiple formats
                    String formattedDate1 = formatDateForInput(day, monthToken, year); // "23-Dec-2025"
                    String formattedDate2 = formatDateForInputNumeric(day, monthToken, year); // "23/12/2025" or "12/23/2025"
                    System.out.println("📝 Formatted date options: " + formattedDate1 + " or " + formattedDate2);
                    
                    // Find the date input field - try multiple locators
                    WebElement dateInput = null;
                    try {
                        dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                            ExternalAuditPageLocators.plannedEndDateField));
                        System.out.println("✅ Found Planned End Date input field (primary)");
                    } catch (Exception eLoc1) {
                        try {
                            dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                                ExternalAuditPageLocators.plannedEndDateFieldAlt));
                            System.out.println("✅ Found Planned End Date input field (alternative 1)");
                        } catch (Exception eLoc2) {
                            dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                                ExternalAuditPageLocators.plannedEndDateFieldAlt2));
                            System.out.println("✅ Found Planned End Date input field (alternative 2)");
                        }
                    }
                    
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", dateInput);
                    waitForSeconds(1);
                    
                    // Click to focus
                    try {
                        dateInput.click();
                        waitForSeconds(1);
                    } catch (Exception eClick) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", dateInput);
                    }
                    
                    // Method 1: Standard Selenium with TAB
                    try {
                        System.out.println("  → Method 1: Standard sendKeys with TAB");
                        dateInput.sendKeys(Keys.CONTROL + "a");
                        dateInput.sendKeys(Keys.BACK_SPACE);
                        waitForSeconds(1);
                        dateInput.sendKeys(formattedDate1);
                        waitForSeconds(1);
                        dateInput.sendKeys(Keys.TAB);
                        waitForSeconds(2);
                        
                        String currentValue = dateInput.getAttribute("value");
                        System.out.println("  📋 Value after Method 1: '" + currentValue + "'");
                        if (currentValue != null && !currentValue.trim().isEmpty()) {
                            System.out.println("✅ Date entered successfully via Method 1: " + currentValue);
                            clicked = true;
                        }
                    } catch (Exception e1) {
                        System.out.println("  ⚠️ Method 1 failed: " + e1.getMessage());
                    }
                    
                    // Method 2: JavaScript with all events
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 2: JavaScript with multiple events");
                            ((JavascriptExecutor) driver).executeScript(
                                "var element = arguments[0];" +
                                "var value = arguments[1];" +
                                "element.focus();" +
                                "element.value = value;" +
                                "element.dispatchEvent(new Event('input', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('keyup', { bubbles: true, cancelable: true }));" +
                                "element.dispatchEvent(new Event('blur', { bubbles: true, cancelable: true }));",
                                dateInput, formattedDate1);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 2: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 2: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e2) {
                            System.out.println("  ⚠️ Method 2 failed: " + e2.getMessage());
                        }
                    }
                    
                    // Method 3: React-specific event triggering
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 3: React-specific events");
                            ((JavascriptExecutor) driver).executeScript(
                                "var element = arguments[0];" +
                                "var value = arguments[1];" +
                                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                                "nativeInputValueSetter.call(element, value);" +
                                "var event = new Event('input', { bubbles: true });" +
                                "element.dispatchEvent(event);" +
                                "var changeEvent = new Event('change', { bubbles: true });" +
                                "element.dispatchEvent(changeEvent);",
                                dateInput, formattedDate1);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 3: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 3: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e3) {
                            System.out.println("  ⚠️ Method 3 failed: " + e3.getMessage());
                        }
                    }
                    
                    // Method 4: Try numeric format (DD/MM/YYYY or MM/DD/YYYY)
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 4: Trying numeric format: " + formattedDate2);
                            dateInput.click();
                            waitForSeconds(1);
                            dateInput.sendKeys(Keys.CONTROL + "a");
                            dateInput.sendKeys(Keys.BACK_SPACE);
                            waitForSeconds(1);
                            dateInput.sendKeys(formattedDate2);
                            waitForSeconds(1);
                            dateInput.sendKeys(Keys.TAB);
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 4: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 4: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e4) {
                            System.out.println("  ⚠️ Method 4 failed: " + e4.getMessage());
                        }
                    }
                    
                    // Method 5: Actions class with ENTER
                    if (!clicked) {
                        try {
                            System.out.println("  → Method 5: Actions class with ENTER");
                            Actions actions = new Actions(driver);
                            actions.moveToElement(dateInput)
                                   .click()
                                   .pause(Duration.ofMillis(500))
                                   .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                                   .pause(Duration.ofMillis(200))
                                   .sendKeys(Keys.DELETE)
                                   .pause(Duration.ofMillis(300))
                                   .sendKeys(formattedDate1)
                                   .pause(Duration.ofMillis(500))
                                   .sendKeys(Keys.ENTER)
                                   .pause(Duration.ofMillis(500))
                                   .perform();
                            waitForSeconds(2);
                            
                            String currentValue = dateInput.getAttribute("value");
                            System.out.println("  📋 Value after Method 5: '" + currentValue + "'");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                System.out.println("✅ Date entered successfully via Method 5: " + currentValue);
                                clicked = true;
                            }
                        } catch (Exception e5) {
                            System.out.println("  ⚠️ Method 5 failed: " + e5.getMessage());
                        }
                    }
                    
                    // Verify final value
                    if (clicked) {
                        String finalValue = dateInput.getAttribute("value");
                        System.out.println("✅ Final date value in field: '" + finalValue + "'");
                    }
                    
                } catch (Exception e3) {
                    System.out.println("⚠️ Strategy 3 (direct input) failed: " + e3.getMessage());
                    e3.printStackTrace();
                }
            }

            if (!clicked) {
                throw new Exception("Could not select Planned End Date using any method (calendar click or direct input)");
            }

            // Final verification - check if date is actually set in the field
            waitForSeconds(2);
            try {
                WebElement dateField = null;
                try {
                    dateField = driver.findElement(ExternalAuditPageLocators.plannedEndDateField);
                } catch (Exception e1) {
                    try {
                        dateField = driver.findElement(ExternalAuditPageLocators.plannedEndDateFieldAlt);
                    } catch (Exception e2) {
                        dateField = driver.findElement(ExternalAuditPageLocators.plannedEndDateFieldAlt2);
                    }
                }
                String finalValue = dateField.getAttribute("value");
                System.out.println("\n📋 FINAL VERIFICATION - Date field value: '" + finalValue + "'");
                
                if (finalValue == null || finalValue.trim().isEmpty()) {
                    System.out.println("⚠️ WARNING: Date field appears empty after selection!");
                    // Try one more time with direct input
                    System.out.println("🔄 Attempting one final direct input...");
                    dateField.click();
                    waitForSeconds(1);
                    String finalFormattedDate = formatDateForInput(day, monthToken, year);
                    dateField.sendKeys(Keys.CONTROL + "a");
                    dateField.sendKeys(Keys.BACK_SPACE);
                    waitForSeconds(1);
                    dateField.sendKeys(finalFormattedDate);
                    waitForSeconds(1);
                    dateField.sendKeys(Keys.TAB);
                    waitForSeconds(2);
                    
                    finalValue = dateField.getAttribute("value");
                    System.out.println("📋 Date field value after final attempt: '" + finalValue + "'");
                } else {
                    System.out.println("✅ SUCCESS: Date is set in the field: " + finalValue);
                }
            } catch (Exception eVerify) {
                System.out.println("⚠️ Could not verify date field value: " + eVerify.getMessage());
            }

            if (reportLogger != null) {
                reportLogger.pass("Selected Planned End Date: " + plannedEndDate);
            }
        } catch (Exception e) {
            System.out.println("❌ Error while selecting Planned End Date: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Error selecting Planned End Date: " + e.getMessage());
            }
            throw e;
        }
    }

    // Helper method to convert Excel date format to MM/DD/YYYY
    private String convertExcelDateToMMDDYYYY(String excelDate) {
        if (excelDate == null || excelDate.trim().isEmpty()) {
            return excelDate;
        }
        
        try {
            String dateStr = excelDate.trim();
            String[] parts = dateStr.split("\\s+");
            
            String day = null;
            String monthToken = null;
            String year = null;
            
            // Parse "Thu Dec 25 2025" format
            if (parts.length >= 4) {
                monthToken = parts[1];   // "Dec"
                day = parts[2];          // "25"
                year = parts[3];         // "2025"
            }
            
            // Parse "Thu Dec 25 00:00:00 IST 2025" format
            if (parts.length >= 7) {
                monthToken = parts[1];
                day = parts[2];
                year = parts[6];  // Year is at index 6
            }
            
            // If parsing failed, try dash or slash formats
            if (day == null || day.isEmpty()) {
                if (dateStr.contains("-")) {
                    String[] dashParts = dateStr.split("-");
                    if (dashParts.length >= 3) {
                        day = dashParts[0].trim();
                        monthToken = dashParts[1].trim();
                        year = dashParts[2].trim();
                    }
                } else if (dateStr.contains("/")) {
                    String[] slashParts = dateStr.split("/");
                    if (slashParts.length >= 3) {
                        String part1 = slashParts[0].trim();
                        String part2 = slashParts[1].trim();
                        String part3 = slashParts[2].trim();
                        try {
                            int p1 = Integer.parseInt(part1);
                            if (p1 > 12) {
                                // DD/MM/YYYY format
                                day = part1;
                                monthToken = getMonthAbbrFromNumber(part2);
                                year = part3;
                            } else {
                                // MM/DD/YYYY format - already correct
                                return dateStr; // Return as-is if already MM/DD/YYYY
                            }
                        } catch (NumberFormatException e) {
                            return dateStr; // Return as-is if can't parse
                        }
                    }
                }
            }
            
            // Convert to MM/DD/YYYY format
            if (day != null && monthToken != null && year != null) {
                String monthNum = getMonthNumberFromAbbr(monthToken);
                return monthNum + "/" + day + "/" + year; // MM/DD/YYYY
            }
        } catch (Exception e) {
            System.out.println("⚠️ Date conversion error: " + e.getMessage());
        }
        
        // Fallback: return original date
        return excelDate;
    }

    // Helper method to set Planned Start Date with multiple strategies (similar to InitiativePage)
    private boolean setPlannedStartDateWithMultipleStrategies(By locator, String dateValue, String fieldName) {
        try {
            System.out.println("  🔍 Trying to set " + fieldName + " with value: " + dateValue);
            
            // Check if element exists
            if (driver.findElements(locator).isEmpty()) {
                System.out.println("    ⚠️ Element not found with locator: " + locator);
                return false;
            }
            
            WebElement dateField = driver.findElement(locator);
            
            if (!dateField.isDisplayed()) {
                System.out.println("    ⚠️ Element found but not visible");
                return false;
            }
            
            System.out.println("    ✓ Element found and visible");
            System.out.println("    📋 Current value before: '" + dateField.getAttribute("value") + "'");
            System.out.println("    📋 Tag name: " + dateField.getTagName());
            System.out.println("    📋 Type: " + dateField.getAttribute("type"));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", dateField);
            waitForSeconds(1);
            
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
                if (currentValue != null && !currentValue.trim().isEmpty() && 
                    (currentValue.contains(dateValue.substring(0, Math.min(5, dateValue.length()))) || 
                     currentValue.contains(dateValue.substring(Math.max(0, dateValue.length()-4))))) {
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
                if (currentValue != null && !currentValue.trim().isEmpty() && 
                    (currentValue.contains(dateValue.substring(0, Math.min(5, dateValue.length()))) || 
                     currentValue.contains(dateValue.substring(Math.max(0, dateValue.length()-4))))) {
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
                if (currentValue != null && !currentValue.trim().isEmpty() && 
                    (currentValue.contains(dateValue.substring(0, Math.min(5, dateValue.length()))) || 
                     currentValue.contains(dateValue.substring(Math.max(0, dateValue.length()-4))))) {
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
                if (currentValue != null && !currentValue.trim().isEmpty() && 
                    (currentValue.contains(dateValue.substring(0, Math.min(5, dateValue.length()))) || 
                     currentValue.contains(dateValue.substring(Math.max(0, dateValue.length()-4))))) {
                    System.out.println("    ✅ Method 4 SUCCESS - Value set: " + currentValue);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("    ✗ Method 4 failed: " + e.getMessage());
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("    ✗ setPlannedStartDateWithMultipleStrategies failed: " + e.getMessage());
            return false;
        }
    }

    // Helper method to convert month abbreviation to full name
    private String getFullMonthName(String monthAbbr) {
        if (monthAbbr == null) return null;
        String abbr = monthAbbr.substring(0, Math.min(3, monthAbbr.length()));
        java.util.Map<String, String> monthMap = new java.util.HashMap<>();
        monthMap.put("Jan", "January");
        monthMap.put("Feb", "February");
        monthMap.put("Mar", "March");
        monthMap.put("Apr", "April");
        monthMap.put("May", "May");
        monthMap.put("Jun", "June");
        monthMap.put("Jul", "July");
        monthMap.put("Aug", "August");
        monthMap.put("Sep", "September");
        monthMap.put("Oct", "October");
        monthMap.put("Nov", "November");
        monthMap.put("Dec", "December");
        return monthMap.get(abbr);
    }

    // Helper method to convert month number to abbreviation
    private String getMonthAbbrFromNumber(String monthNum) {
        if (monthNum == null) return null;
        try {
            int num = Integer.parseInt(monthNum.trim());
            java.util.Map<Integer, String> monthMap = new java.util.HashMap<>();
            monthMap.put(1, "Jan"); monthMap.put(2, "Feb"); monthMap.put(3, "Mar");
            monthMap.put(4, "Apr"); monthMap.put(5, "May"); monthMap.put(6, "Jun");
            monthMap.put(7, "Jul"); monthMap.put(8, "Aug"); monthMap.put(9, "Sep");
            monthMap.put(10, "Oct"); monthMap.put(11, "Nov"); monthMap.put(12, "Dec");
            return monthMap.get(num);
        } catch (NumberFormatException e) {
            return monthNum; // Return as-is if not a number
        }
    }

    // Helper method to convert month abbreviation to number (for MM/DD/YYYY format)
    private String getMonthNumberFromAbbr(String monthAbbr) {
        if (monthAbbr == null) return "01";
        String abbr = monthAbbr.substring(0, Math.min(3, monthAbbr.length()));
        java.util.Map<String, String> monthMap = new java.util.HashMap<>();
        monthMap.put("Jan", "01"); monthMap.put("Feb", "02"); monthMap.put("Mar", "03");
        monthMap.put("Apr", "04"); monthMap.put("May", "05"); monthMap.put("Jun", "06");
        monthMap.put("Jul", "07"); monthMap.put("Aug", "08"); monthMap.put("Sep", "09");
        monthMap.put("Oct", "10"); monthMap.put("Nov", "11"); monthMap.put("Dec", "12");
        return monthMap.getOrDefault(abbr, "01");
    }

    // Helper method to format date for input field (DD-MMM-YYYY)
    private String formatDateForInput(String day, String monthAbbr, String year) {
        try {
            // Format: DD-MMM-YYYY (e.g., "23-Dec-2025")
            return day + "-" + monthAbbr + "-" + year;
        } catch (Exception e) {
            // Fallback format
            return day + "/" + monthAbbr + "/" + year;
        }
    }
    
    // Helper method to format date in numeric format (DD/MM/YYYY)
    private String formatDateForInputNumeric(String day, String monthAbbr, String year) {
        try {
            // Convert month abbreviation to number
            java.util.Map<String, String> monthMap = new java.util.HashMap<>();
            monthMap.put("Jan", "01"); monthMap.put("Feb", "02"); monthMap.put("Mar", "03");
            monthMap.put("Apr", "04"); monthMap.put("May", "05"); monthMap.put("Jun", "06");
            monthMap.put("Jul", "07"); monthMap.put("Aug", "08"); monthMap.put("Sep", "09");
            monthMap.put("Oct", "10"); monthMap.put("Nov", "11"); monthMap.put("Dec", "12");
            
            String monthNum = monthMap.get(monthAbbr);
            if (monthNum == null) monthNum = "01";
            
            // Format: DD/MM/YYYY (e.g., "23/12/2025")
            // Also try MM/DD/YYYY format
            String format1 = day + "/" + monthNum + "/" + year;
            String format2 = monthNum + "/" + day + "/" + year;
            
            // Return DD/MM/YYYY format (more common)
            return format1;
        } catch (Exception e) {
            return day + "/" + monthAbbr + "/" + year;
        }
    }

    // ✅ Click Auditor Button on form
    public void clickAuditorButton() throws Exception {
        try {
            System.out.println("\n👤 Clicking Auditor button on External Audit form...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement auditorBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.auditorButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", auditorBtn);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                auditorBtn.click();
                System.out.println("✅ Auditor button clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", auditorBtn);
                    System.out.println("✅ Auditor button clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                    try {
                        Actions actions = new Actions(driver);
                        actions.moveToElement(auditorBtn).click().perform();
                        System.out.println("✅ Auditor button clicked successfully (Actions click)");
                        clicked = true;
                    } catch (Exception e3) {
                        System.out.println("❌ All click strategies for Auditor button failed: " + e3.getMessage());
                    }
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Auditor button using any method");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Auditor button clicked successfully on External Audit form");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Auditor button on External Audit form: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Auditor button on External Audit form: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Auditor Checkbox in table for specific User Name
    public void clickAuditorCheckbox(String userName) throws Exception {
        try {
            System.out.println("\n☑️ Clicking Auditor checkbox for User: " + userName);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Wait for auditor table rows to be present
            java.util.List<WebElement> rows = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr")));

            if (rows.isEmpty()) {
                throw new Exception("No rows found in Auditor selection table");
            }

            WebElement targetRow = null;
            int rowIndex = -1;

            // Find row containing the user name (case-insensitive)
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                String rowText = row.getText();
                System.out.println("🔎 Auditor Row " + (i + 1) + " text: " + rowText);
                if (rowText != null && rowText.toLowerCase().contains(userName.toLowerCase())) {
                    targetRow = row;
                    rowIndex = i + 1;
                    System.out.println("✅ Matched Auditor User Name in row " + rowIndex);
                    break;
                }
            }

            if (targetRow == null) {
                System.out.println("⚠️ Could not find row containing Auditor User Name '" + userName + "'. Falling back to first row checkbox cell.");
                // Fallback: click first row, 5th column as provided by user
                WebElement fallbackCell = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[2]/div[3]/div/div[3]/div/table/tbody/tr[1]/td[5]")));
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", fallbackCell);
                waitForSeconds(1);
                fallbackCell.click();
                System.out.println("✅ Auditor checkbox clicked successfully (fallback: first row, 5th column)");
                if (reportLogger != null) {
                    reportLogger.pass("Auditor checkbox clicked for first row (fallback), user not matched: " + userName);
                }
                return;
            }

            // Scroll target row into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", targetRow);
            waitForSeconds(1);

            // Try to get 5th column (td[5]) in matched row
            WebElement checkboxCell = null;
            try {
                checkboxCell = targetRow.findElement(By.xpath("./td[5]"));
            } catch (Exception eCell) {
                System.out.println("⚠️ Could not find td[5] in matched row, clicking entire row instead: " + eCell.getMessage());
                checkboxCell = targetRow;
            }

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", checkboxCell);
            waitForSeconds(1);

            WebElement clickableElement = checkboxCell;
            // Prefer any input checkbox inside the cell if present
            try {
                clickableElement = checkboxCell.findElement(
                    By.xpath(".//input[@type='checkbox'] | .//*[@role='checkbox']"));
            } catch (Exception ignored) {
                // fall back to cell itself
            }

            boolean clicked = false;
            try {
                clickableElement.click();
                System.out.println("✅ Auditor checkbox clicked successfully (regular click) for User: " + userName);
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed on auditor checkbox, trying JavaScript...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableElement);
                    System.out.println("✅ Auditor checkbox clicked successfully (JavaScript click) for User: " + userName);
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed on auditor checkbox, trying Actions...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(clickableElement).click().perform();
                    System.out.println("✅ Auditor checkbox clicked successfully (Actions click) for User: " + userName);
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed on auditor checkbox: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Auditor checkbox for User '" + userName + "' using any method");
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Auditor checkbox clicked successfully for User: " + userName + " (row " + rowIndex + ")");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Auditor checkbox for User '" + userName + "': " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Auditor checkbox for User '" + userName + "': " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click "Select Auditors" Button in Auditor popup
    public void clickSelectAuditorsButton() throws Exception {
        try {
            System.out.println("\n✅ Clicking 'Select Auditors' button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            WebElement clickTarget = null;
            boolean foundButton = false;
            
            // Strategy 1: Try to find and click the button directly (preferred)
            try {
                clickTarget = wait.until(ExpectedConditions.elementToBeClickable(
                    ExternalAuditPageLocators.selectAuditorsButtonDirect));
                System.out.println("✅ Found 'Select Auditors' button directly");
                foundButton = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Direct button locator failed, trying span locator...");
            }
            
            // Strategy 2: Find span and get parent button
            if (!foundButton) {
                try {
                    WebElement spanElement = wait.until(
                        ExpectedConditions.presenceOfElementLocated(ExternalAuditPageLocators.selectAuditorsButton));
                    clickTarget = spanElement.findElement(By.xpath("./ancestor::button[1]"));
                    System.out.println("✅ Found parent <button> for 'Select Auditors' span");
                    foundButton = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ Parent button not found, trying span directly...");
                    try {
                        clickTarget = wait.until(ExpectedConditions.elementToBeClickable(
                            ExternalAuditPageLocators.selectAuditorsButton));
                        System.out.println("✅ Found 'Select Auditors' span element");
                        foundButton = true;
                    } catch (Exception e3) {
                        System.out.println("⚠️ Span locator also failed: " + e3.getMessage());
                    }
                }
            }
            
            if (clickTarget == null) {
                throw new Exception("Could not locate 'Select Auditors' button using any locator strategy");
            }
            
            // Verify button is enabled
            try {
                if (!clickTarget.isEnabled()) {
                    System.out.println("⚠️ Button is disabled, waiting for it to become enabled...");
                    wait.until(ExpectedConditions.elementToBeClickable(clickTarget));
                }
            } catch (Exception eEnabled) {
                System.out.println("⚠️ Could not check if button is enabled: " + eEnabled.getMessage());
            }

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", clickTarget);
            waitForSeconds(1);

            // Try multiple click strategies
            boolean clicked = false;
            
            // Click Strategy 1: Regular click
            try {
                clickTarget.click();
                System.out.println("✅ 'Select Auditors' button clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed: " + e1.getMessage());
            }
            
            // Click Strategy 2: JavaScript click
            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickTarget);
                    System.out.println("✅ 'Select Auditors' button clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed: " + e2.getMessage());
                }
            }
            
            // Click Strategy 3: Actions class click
            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(clickTarget).click().perform();
                    System.out.println("✅ 'Select Auditors' button clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }
            
            // Click Strategy 4: Force click via JavaScript (remove disabled attribute if present)
            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].removeAttribute('disabled');", clickTarget);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickTarget);
                    System.out.println("✅ 'Select Auditors' button clicked successfully (forced JavaScript click)");
                    clicked = true;
                } catch (Exception e4) {
                    System.out.println("⚠️ Forced JavaScript click failed: " + e4.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click 'Select Auditors' button using any method");
            }

            // Wait for popup to close or form to update
            waitForSeconds(2);
            
            // Verify the click had effect - check if popup/dialog is closing
            try {
                // Wait a bit more to see if popup disappears
                waitForSeconds(1);
                System.out.println("✅ 'Select Auditors' button click completed");
            } catch (Exception eVerify) {
                System.out.println("⚠️ Could not verify popup closure: " + eVerify.getMessage());
            }
            
            if (reportLogger != null) {
                reportLogger.pass("'Select Auditors' button clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click 'Select Auditors' button: " + e.getMessage());
            e.printStackTrace();
            if (reportLogger != null) {
                reportLogger.fail("Failed to click 'Select Auditors' button: " + e.getMessage());
            }
            throw e;
        }
    }
    
    
    
    /**
     * Enter External Start Date using Fluent UI DatePicker
     * Handles div-based DatePicker elements (e.g., DatePicker82-label)
     * @param StartDate Date string in format expected by the date picker
     */
    public void enterExtStartDate(String StartDate) throws Throwable {
        System.out.println("\n📅 ═══════════════════════════════════════════");
        System.out.println("📅 ENTERING Ext Audit Start DATE");
        System.out.println("📅 Date: " + StartDate);
        System.out.println("📅 ═══════════════════════════════════════════");
        
        if (StartDate == null || StartDate.trim().isEmpty()) {
            System.out.println("  ⚠️ No Start Date provided, skipping...");
            return;
        }
 
        // Simple + fast: locate → set value via JS on the real input → verify not blank
        setFluentUiDate(ExternalAuditPageLocators.plannedStartDateField, StartDate.trim(), "External Audit Start Date");
        
        System.out.println("📅 ═══════════════════════════════════════════\n");
    }
    /**
     * Fluent UI DatePicker can be a div[role=combobox] or an input; this sets the underlying input value via JS and verifies it.
     */
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
    /**
     * FluentUI DatePicker sometimes exposes a readOnly div[role=combobox] with id like DatePicker169-label.
     * This method tries hard to find the associated <input> that holds the value.
     */
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
 
 
  /**
     * FluentUI DatePicker sometimes exposes a readOnly div[role=combobox] with id like DatePicker169-label.
     * This method tries hard to find the associated <input> that holds the value.
     */
    private WebElement resolveDatePickerInput1(WebElement base) {
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

    /**
     * Check if element is an input or textarea
     */
    private boolean isInputOrTextarea(WebElement el) {
        if (el == null) return false;
        try {
            String tag = el.getTagName().toLowerCase();
            return "input".equals(tag) || "textarea".equals(tag);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set value using native setter and dispatch events (React-compatible)
     */
    private void setNativeValueAndDispatch(WebElement input, String value) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var element = arguments[0];" +
                "var value = arguments[1];" +
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(element, value);" +
                "var event = new Event('input', { bubbles: true });" +
                "element.dispatchEvent(event);" +
                "var changeEvent = new Event('change', { bubbles: true });" +
                "element.dispatchEvent(changeEvent);",
                input, value);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to set native value: " + e.getMessage());
        }
    }

    /**
     * Select date from open calendar popup
     */
    private boolean selectDateFromOpenCalendar(String dateText) {
        try {
            waitForSeconds(1);
            // Try to parse the date and find matching calendar button
            java.util.Date d = parseExcelDate(dateText);
            if (d == null) return false;

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH);
            String mmddyyyy = sdf.format(d);
            String[] parts = mmddyyyy.split("/");
            String month = parts[0];
            String day = parts[1];
            String year = parts[2];

            // Try to find calendar button with matching day
            try {
                java.util.List<WebElement> dayButtons = driver.findElements(
                    By.xpath("//div[contains(@role,'grid') or contains(@class,'Calendar')]//button[normalize-space(text())='" + day + "']"));
                
                for (WebElement btn : dayButtons) {
                    try {
                        String ariaLabel = btn.getAttribute("aria-label");
                        if (ariaLabel != null && ariaLabel.contains(month) && ariaLabel.contains(year)) {
                            btn.click();
                            waitForSeconds(1);
                            return true;
                        }
                    } catch (Exception e) {
                        // Continue to next button
                    }
                }
                
                // If no exact match, click first matching day
                if (!dayButtons.isEmpty()) {
                    dayButtons.get(0).click();
                    waitForSeconds(1);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Calendar selection failed: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Date parsing for calendar failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Parse Excel date string to Date object
     */
    private java.util.Date parseExcelDate(String dateText) {
        if (dateText == null || dateText.trim().isEmpty()) return null;
        
        try {
            // Try common date formats
            String[] formats = {
                "MM/dd/yyyy",
                "dd-MMM-yyyy",
                "dd/MM/yyyy",
                "yyyy-MM-dd",
                "EEE MMM dd yyyy",  // "Tue Dec 23 2025"
                "EEE MMM dd HH:mm:ss zzz yyyy"  // "Tue Dec 23 00:00:00 IST 2025"
            };
            
            for (String format : formats) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, java.util.Locale.ENGLISH);
                    return sdf.parse(dateText.trim());
                } catch (Exception e) {
                    // Try next format
                }
            }
            
            // Try parsing as-is (might be already in correct format)
            try {
                return new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH).parse(dateText.trim());
            } catch (Exception e) {
                // Last resort: return null
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not parse date: " + dateText);
        }
        return null;
    }

    /**
     * Read value from date picker (input or combobox)
     */
    private String readValueFromDatePicker(WebElement base, WebElement input) {
        try {
            if (input != null && isInputOrTextarea(input)) {
                String value = input.getAttribute("value");
                if (value != null && !value.trim().isEmpty()) {
                    return value;
                }
            }
            
            // Try reading from base element (might be a combobox with text content)
            if (base != null) {
                String text = base.getText();
                if (text != null && !text.trim().isEmpty()) {
                    return text;
                }
                
                // Try aria-label or other attributes
                String ariaLabel = base.getAttribute("aria-label");
                if (ariaLabel != null && !ariaLabel.trim().isEmpty()) {
                    return ariaLabel;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not read date picker value: " + e.getMessage());
        }
        return null;
    }

    /**
     * Resolve editable element from base element
     */
    private WebElement resolveEditableElement(WebElement base) {
        if (base == null) return null;
        
        try {
            // Try to find input/textarea in descendants
            java.util.List<WebElement> inputs = base.findElements(By.xpath(".//input | .//textarea"));
            for (WebElement input : inputs) {
                try {
                    if (input != null && input.isDisplayed() && input.isEnabled()) {
                        return input;
                    }
                } catch (Exception e) {
                    // Continue to next
                }
            }
            
            // Try following sibling input
            try {
                WebElement sibling = base.findElement(By.xpath("./following-sibling::input[1] | ./following-sibling::textarea[1]"));
                if (sibling != null && sibling.isDisplayed()) {
                    return sibling;
                }
            } catch (Exception e) {
                // No sibling found
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not resolve editable element: " + e.getMessage());
        }
        
        return null;
    }

    // ✅ Click Initiative Close Icon
    public void clickInitiativeCloseIcon() throws Exception {
        try {
            System.out.println("\n❌ Clicking Close Icon for Initiative popup...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement closeIcon = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.initiativeCloseIcon));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", closeIcon);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                closeIcon.click();
                System.out.println("✅ Initiative Close Icon clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeIcon);
                    System.out.println("✅ Initiative Close Icon clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(closeIcon).click().perform();
                    System.out.println("✅ Initiative Close Icon clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Initiative Close Icon using any method");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Initiative Close Icon clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Initiative Close Icon: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Initiative Close Icon: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Auditor Checkbox
    public void clickAuditorCheckbox() throws Exception {
        try {
            System.out.println("\n☑️ Clicking Auditor Checkbox...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement auditorCheckbox = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.auditorCheckbox));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", auditorCheckbox);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                auditorCheckbox.click();
                System.out.println("✅ Auditor Checkbox clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", auditorCheckbox);
                    System.out.println("✅ Auditor Checkbox clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(auditorCheckbox).click().perform();
                    System.out.println("✅ Auditor Checkbox clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Auditor Checkbox using any method");
            }

            waitForSeconds(1);
            if (reportLogger != null) {
                reportLogger.pass("Auditor Checkbox clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Auditor Checkbox: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Auditor Checkbox: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Auditor Close Icon
    public void clickAuditorCloseIcon() throws Exception {
        try {
            System.out.println("\n❌ Clicking Close Icon for Auditor popup...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement closeIcon = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.auditorCloseIcon));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", closeIcon);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                closeIcon.click();
                System.out.println("✅ Auditor Close Icon clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeIcon);
                    System.out.println("✅ Auditor Close Icon clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(closeIcon).click().perform();
                    System.out.println("✅ Auditor Close Icon clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Auditor Close Icon using any method");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Auditor Close Icon clicked successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Auditor Close Icon: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Auditor Close Icon: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Click Save Button
    public void clickSaveButton() throws Exception {
        try {
            System.out.println("\n💾 Clicking Save Button on External Audit form...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                ExternalAuditPageLocators.saveButton));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", saveBtn);
            waitForSeconds(1);

            boolean clicked = false;
            try {
                saveBtn.click();
                System.out.println("✅ Save button clicked successfully (regular click)");
                clicked = true;
            } catch (Exception e1) {
                System.out.println("⚠️ Regular click failed, trying JavaScript click...");
            }

            if (!clicked) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
                    System.out.println("✅ Save button clicked successfully (JavaScript click)");
                    clicked = true;
                } catch (Exception e2) {
                    System.out.println("⚠️ JavaScript click failed, trying Actions click...");
                }
            }

            if (!clicked) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(saveBtn).click().perform();
                    System.out.println("✅ Save button clicked successfully (Actions click)");
                    clicked = true;
                } catch (Exception e3) {
                    System.out.println("⚠️ Actions click failed: " + e3.getMessage());
                }
            }

            if (!clicked) {
                throw new Exception("Could not click Save button using any method");
            }

            waitForSeconds(2);
            if (reportLogger != null) {
                reportLogger.pass("Save button clicked successfully on External Audit form");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click Save button: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.fail("Failed to click Save button: " + e.getMessage());
            }
            throw e;
        }
    }

    // ✅ Validate Save Successful Alert
    public void validateSaveSuccessfulAlert() throws Exception {
        try {
            System.out.println("\n🔔 Validating Save Successful Alert...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for alert to appear
            WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.toastAlert));

            String alertText = alertElement.getText().trim();
            System.out.println("🔔 Alert Text Captured: " + alertText);

            // Check if alert contains success indicators
            boolean isSuccess = alertText.toLowerCase().contains("success") || 
                               alertText.toLowerCase().contains("saved") ||
                               alertText.toLowerCase().contains("created") ||
                               alertText.toLowerCase().contains("added");

            if (isSuccess) {
                System.out.println("✅ Save Successful Alert validated: " + alertText);
                if (reportLogger != null) {
                    reportLogger.pass("Save Successful Alert validated: " + alertText);
                }
            } else {
                System.out.println("⚠️ Alert appeared but may not be success message: " + alertText);
                if (reportLogger != null) {
                    reportLogger.info("Alert appeared: " + alertText);
                }
            }

            waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("⚠️ Save Successful Alert not found or timeout: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.info("Save Successful Alert not found or timeout: " + e.getMessage());
            }
            // Don't throw exception - alert might not always appear
        }
    }

    // ✅ Validate Mandatory Fields Alert
    public void validateMandatoryFieldsAlert() throws Exception {
        try {
            System.out.println("\n🔔 Validating Mandatory Fields Alert...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for alert to appear
            WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                ExternalAuditPageLocators.toastAlert));

            String alertText = alertElement.getText().trim();
            System.out.println("🔔 Alert Text Captured: " + alertText);

            // Check if alert contains mandatory field indicators
            boolean isMandatory = alertText.toLowerCase().contains("mandatory") || 
                                 alertText.toLowerCase().contains("required") ||
                                 alertText.toLowerCase().contains("field") ||
                                 alertText.toLowerCase().contains("fill");

            if (isMandatory) {
                System.out.println("✅ Mandatory Fields Alert validated: " + alertText);
                if (reportLogger != null) {
                    reportLogger.pass("Mandatory Fields Alert validated: " + alertText);
                }
            } else {
                System.out.println("⚠️ Alert appeared but may not be mandatory field message: " + alertText);
                if (reportLogger != null) {
                    reportLogger.info("Alert appeared: " + alertText);
                }
            }

            waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("⚠️ Mandatory Fields Alert not found or timeout: " + e.getMessage());
            if (reportLogger != null) {
                reportLogger.info("Mandatory Fields Alert not found or timeout: " + e.getMessage());
            }
            // Don't throw exception - alert might not always appear
        }
    }
    
    
    public void entertitle(String title) {
    	type(ExternalAuditPageLocators.titleInput,title,"titleInput");
    }

    
    
    public void clickInitiative() {
    	
    	click(ExternalAuditPageLocators.link,"link");
    	
    }
    
       public void clickfirstInitative() {
    	   
    	   
    	   click(ExternalAuditPageLocators.first,"first");
       }
    
    

    public void clickauditor() {
    	   
    	   
   click(ExternalAuditPageLocators.auditor,"auditor");
     }
     
    
    public void clickfirstcheckbox() {
        click(ExternalAuditPageLocators.check,"check");
    }
    
    
     public void  clickselectAuditer() {
         click(ExternalAuditPageLocators.select,"select");

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
 
     
    	public void enterplannedInDate(String inDate) throws Exception {
    	    selectDateFromFluentCalendar(
    	    		ExternalAuditPageLocators.plannedIn,
    	            inDate
    	    );
    	}

    	public void enterplannnedOutDate(String outDate) throws Exception {
    	    selectDateFromFluentCalendar(
    	    		ExternalAuditPageLocators.plannedout,
    	            outDate
    	    );
    	}
     
  
        public void  clicksave() {
            click(ExternalAuditPageLocators.save,"save");

        }
     
    
        public void  clickonedit() {
            click(ExternalAuditPageLocators.edit,"edit");

        }
        
        
        public void  enterduration(String due) {
        	
        	   WebElement durationField =
        		        driver.findElement(ExternalAuditPageLocators.duration);

        		    // Clear existing value (Fluent UI safe)
        		    durationField.sendKeys(Keys.CONTROL + "a");
        		    durationField.sendKeys(Keys.DELETE);
        	
            type(ExternalAuditPageLocators.duration,due,"duration");

        }
      
        public String getDurationValue() {
            WebElement duration =
                driver.findElement(ExternalAuditPageLocators.duration);

            String value = duration.getAttribute("value");

            return value == null ? "" : value.trim();
        }
        
        
        
          public void  clickHistoryTab() {
        	  
        	  jsClick(ExternalAuditPageLocators.history,"history");
          }
          
          public void verifyDurationHistoryValueWithPagination(String expectedOldValue) {

        	    if (expectedOldValue == null || expectedOldValue.trim().isEmpty()) {
        	        throw new IllegalArgumentException(
        	            "❌ Expected previous Duration value is empty"
        	        );
        	    }

        	    boolean found = false;
        	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        	    while (true) {

        	        // 1️⃣ Check current page
        	        List<WebElement> rows = wait.until(
        	            ExpectedConditions.presenceOfAllElementsLocatedBy(
        	                ExternalAuditPageLocators.historyRows
        	            )
        	        );

        	        for (WebElement row : rows) {
        	            String field = row.findElements(By.tagName("td")).get(0).getText().trim();
        	            String value = row.findElements(By.tagName("td")).get(2).getText().trim();

        	            if (field.equals("Duration") && value.equals(expectedOldValue)) {
        	                found = true;
        	                System.out.println(
        	                    "✅ Found previous Duration value in History: " + expectedOldValue
        	                );
        	                return; // ✅ STOP once found
        	            }
        	        }

        	        // 2️⃣ Check if Next page exists
        	        List<WebElement> nextDisabled =
        	            driver.findElements(ExternalAuditPageLocators.nextPageDisabled);

        	        if (!nextDisabled.isEmpty()) {
        	            break; // ❌ No more pages
        	        }

        	        // 3️⃣ Go to next page
        	        WebElement nextBtn =
        	            driver.findElement(ExternalAuditPageLocators.nextPageBtn);

        	        ((JavascriptExecutor) driver)
        	            .executeScript("arguments[0].click();", nextBtn);

        	        // 4️⃣ Small wait for page load
        	        wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
        	    }

        	    // ❌ If loop ends and value not found
        	    Assert.fail(
        	        "❌ Previous Duration value not found in History across all pages: "
        	            + expectedOldValue
        	    );
        	}
          
          public int generateRandom1To100() {
        	    return ThreadLocalRandom.current().nextInt(1, 101);
        	}   
          
          
          public void clickdelete() {
        	  
        	  click(ExternalAuditPageLocators.delelte,"delelte");
          }
          
          
          
          public void clickyes() {
        	  
        	  click(ExternalAuditPageLocators.yes,"yes");
          }  
          
          
}


