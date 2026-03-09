package Pages;

import Actions.ActionEngine;
import Locators.InitiativeManagementPageLocators;
import Locators.ProjectPageLocator;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

/**
 * Page Object Model (POM) for Initiative Management Module
 * 
 * This class contains methods for navigating to Initiative Management pages.
 * 
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeManagementPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    /**
     * Constructor with WebDriver and Logger
     * @param driver WebDriver instance
     * @param reportLogger ExtentTest logger instance
     */
    public InitiativeManagementPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // Updated by Shahu.D
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Navigate to Initiatives page
     * Updated by Shahu.D
     * @throws Exception if navigation fails
     */
    public void navigateToInitiativesPage() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);
            
            System.out.println("  🔍 Navigating to Initiatives page..."); // Updated by Shahu.D
            
            // Step 1: Click on Initiative Management navigation - Updated by Shahu.D
            System.out.println("  📌 Step 1: Click on Initiative Management navigation"); // Updated by Shahu.D
            WebElement initiativeNav = null;
            boolean navFound = false;
            
            By[] navLocators = {
                InitiativeManagementPageLocators.initiativeManagementNav,
                By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management']"), // Updated by Shahu.D
                By.xpath("//img[@alt='Initiative Management']"), // Updated by Shahu.D
                By.xpath("//div[contains(@class,'navigation')]//img[contains(@alt,'Initiative')]") // Updated by Shahu.D
            };
            
            for (By locator : navLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        initiativeNav = element;
                        navFound = true;
                        System.out.println("  ✅ Found Initiative Management navigation using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!navFound || initiativeNav == null) {
                throw new Exception("Initiative Management navigation not found"); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", initiativeNav);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativeNav);
                System.out.println("  ✅ Clicked Initiative Management navigation using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(initiativeNav).click().perform();
                    System.out.println("  ✅ Clicked Initiative Management navigation using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    initiativeNav.click();
                    System.out.println("  ✅ Clicked Initiative Management navigation using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Step 1 completed: Initiative Management nav clicked"); // Updated by Shahu.D
            
            // Wait for navigation menu to expand
            System.out.println("  ⏳ Waiting for navigation menu to expand..."); // Updated by Shahu.D
            waitForSeconds(2);
            
            // Step 2: Click on Initiatives page link - Updated by Shahu.D
            System.out.println("  📌 Step 2: Click on Initiatives page link"); // Updated by Shahu.D
            WebElement initiativesLink = null;
            boolean linkFound = false;
            
            By[] linkLocators = {
                InitiativeManagementPageLocators.initiativesPageLink,
                By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[1]/p"), // Updated by Shahu.D - Exact XPath
                By.xpath("//*[@id='children-panel-container']//p[contains(text(),'Initiatives')]"), // Updated by Shahu.D
                By.xpath("//p[contains(text(),'Initiatives')]") // Updated by Shahu.D
            };
            
            for (By locator : linkLocators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        initiativesLink = element;
                        linkFound = true;
                        System.out.println("  ✅ Found Initiatives page link using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            
            if (!linkFound || initiativesLink == null) {
                throw new Exception("Initiatives page link not found"); // Updated by Shahu.D
            }
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", initiativesLink);
            waitForSeconds(1);
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", initiativesLink);
                System.out.println("  ✅ Clicked Initiatives page link using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(initiativesLink).click().perform();
                    System.out.println("  ✅ Clicked Initiatives page link using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    initiativesLink.click();
                    System.out.println("  ✅ Clicked Initiatives page link using direct click"); // Updated by Shahu.D
                }
            }
            
            System.out.println("  ✅ Step 2 completed: Initiatives page link clicked"); // Updated by Shahu.D
            
            // Wait for page to load
            waitForSeconds(3);
            System.out.println("  ✅ Successfully navigated to Initiatives page"); // Updated by Shahu.D
            
        } catch (Exception e) {
            System.err.println("  ❌ Error navigating to Initiatives page: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }
    
    public void enterInitiativeCode(String title) {
        type(InitiativeManagementPageLocators.entertitle, title, "title");
    }

    
    public void clickFilterSearchButton() {
    	
    	   click(InitiativeManagementPageLocators.clickfilter, "clickfilter");
          }
    
   
    public void clickSearchToolbarButton() {
    	
 	   click(InitiativeManagementPageLocators.clicktool, "clicktool");
       }
}

