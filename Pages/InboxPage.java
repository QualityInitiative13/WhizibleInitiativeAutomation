package Pages;

import Actions.ActionEngine;
import Locators.InboxPageLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

/**
 * Page Object Model (POM) for Inbox Module
 * 
 * This class contains methods for interacting with Inbox page elements.
 * 
 * @author Automation Team
 * @version 1.0
 */
public class InboxPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InboxPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // Updated by Shahu.D
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Click on Inbox section
     * Updated by Shahu.D
     * @throws Exception if click fails
     */
    public void clickInboxSection() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Inbox section..."); // Updated by Shahu.D
            
            WebElement inboxLink = null;
            boolean found = false;
            
            By[] inboxLocators = {
                InboxPageLocators.inboxSectionLink,
                By.xpath("//*[@id=\"ImFltr-Inbox\"]/a/span[2]"), // Updated by Shahu.D - Exact XPath
                By.xpath("//*[@id='ImFltr-Inbox']/a/span[2]"), // Updated by Shahu.D
                By.xpath("//*[@id='ImFltr-Inbox']//a"), // Updated by Shahu.D
                By.xpath("//a[contains(@id,'Inbox')]//span[2]"), // Updated by Shahu.D
                By.xpath("//span[contains(text(),'Inbox')]") // Updated by Shahu.D
            };
            
            for (By locator : inboxLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        inboxLink = element;
                        found = true;
                        System.out.println("  ✅ Found Inbox section using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || inboxLink == null) {
                throw new Exception("Inbox section not found"); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", inboxLink);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inboxLink);
                System.out.println("  ✅ Clicked Inbox section using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(inboxLink).click().perform();
                    System.out.println("  ✅ Clicked Inbox section using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    inboxLink.click();
                    System.out.println("  ✅ Clicked Inbox section using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Inbox section"); // Updated by Shahu.D
            waitForSeconds(2); // Updated by Shahu.D
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Inbox section: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Click on Search icon
     * Updated by Shahu.D
     * @throws Exception if click fails
     */
    public void clickSearchIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Search icon..."); // Updated by Shahu.D
            
            WebElement searchIcon = null;
            boolean found = false;
            
            By[] searchIconLocators = {
                InboxPageLocators.searchIcon,
                By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"), // Updated by Shahu.D - Exact XPath
                By.xpath("//*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div[2]//div//div//img"), // Updated by Shahu.D
                By.xpath("//img[contains(@alt,'Search')]"), // Updated by Shahu.D
                By.xpath("//img[@alt='Search']") // Updated by Shahu.D
            };
            
            for (By locator : searchIconLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        searchIcon = element;
                        found = true;
                        System.out.println("  ✅ Found Search icon using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || searchIcon == null) {
                throw new Exception("Search icon not found"); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchIcon);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
                System.out.println("  ✅ Clicked Search icon using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchIcon).click().perform();
                    System.out.println("  ✅ Clicked Search icon using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    searchIcon.click();
                    System.out.println("  ✅ Clicked Search icon using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Search icon"); // Updated by Shahu.D
            waitForSeconds(2); // Updated by Shahu.D - Wait for search panel to open
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search icon: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Enter Initiative Code in search field
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to search for
     * @throws Exception if input fails
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            
            System.out.println("  🔍 Searching for Initiative Code input field..."); // Updated by Shahu.D
            System.out.println("  📝 Initiative Code to enter: " + initiativeCode); // Updated by Shahu.D
            
            WebElement codeInput = null;
            boolean found = false;
            
            By[] inputLocators = {
                InboxPageLocators.initiativeCodeInput,
                By.xpath("//*[@id=\"DemandCode\"]"), // Updated by Shahu.D - Exact XPath
                By.xpath("//*[@id='DemandCode']"), // Updated by Shahu.D
                By.xpath("//input[@id='DemandCode']"), // Updated by Shahu.D
                By.xpath("//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]") // Updated by Shahu.D
            };
            
            for (By locator : inputLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        codeInput = element;
                        found = true;
                        System.out.println("  ✅ Found Initiative Code input using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || codeInput == null) {
                throw new Exception("Initiative Code input field not found"); // Updated by Shahu.D
            }
            
            // Clear and enter value
            codeInput.clear();
            waitForSeconds(1);
            codeInput.sendKeys(initiativeCode);
            System.out.println("  ✅ Entered Initiative Code: " + initiativeCode); // Updated by Shahu.D
            waitForSeconds(1); // Updated by Shahu.D
            
        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Code: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Click on Final Search button
     * Updated by Shahu.D
     * @throws Exception if click fails
     */
    public void clickFinalSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Searching for Final Search button..."); // Updated by Shahu.D
            
            WebElement searchBtn = null;
            boolean found = false;
            
            By[] searchBtnLocators = {
                InboxPageLocators.finalSearchButton,
                By.xpath("//*[@id=\"id__520\"]"), // Updated by Shahu.D - Exact XPath
                By.xpath("//button[@id='id__520']"), // Updated by Shahu.D
                By.xpath("//button[contains(text(),'Search')]"), // Updated by Shahu.D
                By.xpath("//button[.//span[text()='Search']]") // Updated by Shahu.D
            };
            
            for (By locator : searchBtnLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        searchBtn = element;
                        found = true;
                        System.out.println("  ✅ Found Final Search button using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!found || searchBtn == null) {
                throw new Exception("Final Search button not found"); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchBtn);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
                System.out.println("  ✅ Clicked Final Search button using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(searchBtn).click().perform();
                    System.out.println("  ✅ Clicked Final Search button using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    searchBtn.click();
                    System.out.println("  ✅ Clicked Final Search button using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Successfully clicked on Final Search button"); // Updated by Shahu.D
            waitForSeconds(3); // Updated by Shahu.D - Wait for search results to load
            
        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Final Search button: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Verify Initiative appears in Inbox
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to verify
     * @return true if initiative is found, false otherwise
     * @throws Exception if verification fails
     */
    public boolean verifyInitiativeInInbox(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            
            System.out.println("  🔍 Verifying Initiative Code '" + initiativeCode + "' in Inbox..."); // Updated by Shahu.D
            
            By initiativeLocator = InboxPageLocators.getInitiativeCodeLocator(initiativeCode);
            
            try {
                WebElement initiativeElement = wait.until(ExpectedConditions.presenceOfElementLocated(initiativeLocator));
                if (initiativeElement.isDisplayed()) {
                    String foundText = initiativeElement.getText();
                    System.out.println("  ✅ Initiative Code found in Inbox: " + foundText); // Updated by Shahu.D
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("  ⚠️ Initiative Code not found in Inbox: " + ex.getMessage()); // Updated by Shahu.D
                return false;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("  ❌ Error verifying Initiative in Inbox: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
}

