package Pages;

import Actions.ActionEngine;
import Locators.WatchlistPageLocators;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model (POM) for Watchlist Page
 * Updated by Shahu.D
 */
public class WatchlistPage extends ActionEngine {

    private WebDriver driver;
    private ExtentTest reportLogger;

    public WatchlistPage(WebDriver driver, ExtentTest reportLogger) {
        super(); // Updated by Shahu.D
        this.driver = driver;
        this.reportLogger = reportLogger;
    }

    /**
     * Click on Watchlist section
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickWatchlistSection() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);

            System.out.println("  🔍 Searching for Watchlist section..."); // Updated by Shahu.D

            WebElement watchlistLink = null;
            boolean found = false;

            By[] locators = {
                    WatchlistPageLocators.watchlistSection,
                    By.xpath("//*[@id=\"ImFltr-Watchlist\"]/a/span[2]"),
                    By.xpath("//*[@id='ImFltr-Watchlist']/a/span[2]"),
                    By.xpath("//*[@id='ImFltr-Watchlist']//a"),
                    By.xpath("//span[contains(text(),'Watchlist')]")
            };

            for (By locator : locators) {
                try {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    if (element.isDisplayed() && element.isEnabled()) {
                        watchlistLink = element;
                        found = true;
                        System.out.println("  ✅ Found Watchlist section using locator: " + locator); // Updated by Shahu.D
                        break;
                    }
                } catch (Exception ex) {
                    continue;
                }
            }

            if (!found || watchlistLink == null) {
                throw new Exception("Watchlist section not found"); // Updated by Shahu.D
            }

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", watchlistLink);
            waitForSeconds(1);

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", watchlistLink);
                System.out.println("  ✅ Clicked Watchlist section using JavaScript"); // Updated by Shahu.D
            } catch (Exception e1) {
                try {
                    actions.moveToElement(watchlistLink).click().perform();
                    System.out.println("  ✅ Clicked Watchlist section using Actions"); // Updated by Shahu.D
                } catch (Exception e2) {
                    watchlistLink.click();
                    System.out.println("  ✅ Clicked Watchlist section using direct click"); // Updated by Shahu.D
                }
            }

            System.out.println("  ✅ Successfully clicked on Watchlist section"); // Updated by Shahu.D
            waitForSeconds(2);

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Watchlist section: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Click on Search icon
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickSearchIcon() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(2); // Updated by Shahu.D

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);

            System.out.println("  🔍 Searching for Search icon on Watchlist page..."); // Updated by Shahu.D

            WebElement searchIcon = null;
            boolean found = false;

            By[] locators = {
                    WatchlistPageLocators.searchIcon,
                    By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img"),
                    By.xpath("//img[contains(@alt,'Search')]"),
                    By.xpath("//img[@alt='Search']")
            };

            for (By locator : locators) {
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
                throw new Exception("Search icon not found on Watchlist page"); // Updated by Shahu.D
            }

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", searchIcon);
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

            System.out.println("  ✅ Successfully clicked on Search icon (Watchlist)"); // Updated by Shahu.D
            waitForSeconds(2);

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Search icon on Watchlist: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Enter Initiative Code in Watchlist search field
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to search for
     * @throws Exception if element is not found or input fails
     */
    public void enterInitiativeCode(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Updated by Shahu.D

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D

            System.out.println("  🔍 Searching for Initiative Code input field on Watchlist..."); // Updated by Shahu.D
            System.out.println("  📝 Initiative Code to enter: " + initiativeCode); // Updated by Shahu.D

            WebElement codeInput = null;
            boolean found = false;

            By[] inputLocators = {
                    WatchlistPageLocators.initiativeCodeInputField,
                    By.xpath("//*[@id=\"DemandCode\"]"),
                    By.xpath("//*[@id='DemandCode']"),
                    By.xpath("//input[@id='DemandCode']"),
                    By.xpath("//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]")
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
                throw new Exception("Initiative Code input field not found on Watchlist page"); // Updated by Shahu.D
            }

            codeInput.clear();
            waitForSeconds(1);
            codeInput.sendKeys(initiativeCode);
            System.out.println("  ✅ Entered Initiative Code on Watchlist: " + initiativeCode); // Updated by Shahu.D
            waitForSeconds(1);

        } catch (Exception e) {
            System.err.println("  ❌ Error entering Initiative Code on Watchlist: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Click on Final Search button on Watchlist page
     * Updated by Shahu.D
     * @throws Exception if element is not found or click fails
     */
    public void clickFinalSearchButton() throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(1); // Updated by Shahu.D

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated by Shahu.D
            Actions actions = new Actions(driver);

            System.out.println("  🔍 Searching for Final Search button on Watchlist..."); // Updated by Shahu.D

            WebElement searchBtn = null;
            boolean found = false;

            By[] searchBtnLocators = {
                    WatchlistPageLocators.finalSearchButton,
                    By.xpath("//*[@id=\"id__529\"]"),
                    By.xpath("//button[@id='id__529']"),
                    By.xpath("//button[contains(text(),'Search')]"),
                    By.xpath("//button[.//span[text()='Search']]")
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
                throw new Exception("Final Search button not found on Watchlist page"); // Updated by Shahu.D
            }

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", searchBtn);
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

            System.out.println("  ✅ Successfully clicked on Final Search button (Watchlist)"); // Updated by Shahu.D
            waitForSeconds(3);

        } catch (Exception e) {
            System.err.println("  ❌ Error clicking Final Search button on Watchlist: " + e.getMessage()); // Updated by Shahu.D
            throw e;
        }
    }

    /**
     * Verify that initiative is NOT present in Watchlist results
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to verify
     * @return true if initiative is NOT present, false if present
     */
    public boolean verifyInitiativeNotInWatchlist(String initiativeCode) throws Exception {
        driver.switchTo().defaultContent();
        waitForSeconds(3); // Wait for search results

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Short wait
            By rowLocator = WatchlistPageLocators.getInitiativeInWatchlistLocator(initiativeCode);

            List<WebElement> rows;
            try {
                rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
            } catch (Exception timeout) {
                // If timeout, treat as no rows found (which is expected)
                System.out.println("  ✅ No rows found for Initiative Code '" + initiativeCode + "' in Watchlist (timeout)"); // Updated by Shahu.D
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' is NOT present in Watchlist (as expected)");
                }
                return true;
            }

            int visibleCount = 0;
            for (WebElement row : rows) {
                try {
                    if (row.isDisplayed() && row.getText().contains(initiativeCode)) {
                        visibleCount++;
                    }
                } catch (Exception ignore) {
                }
            }

            if (visibleCount == 0) {
                System.out.println("  ✅ Initiative Code '" + initiativeCode + "' is NOT present in Watchlist results"); // Updated by Shahu.D
                if (reportLogger != null) {
                    reportLogger.pass("Initiative Code '" + initiativeCode + "' is NOT present in Watchlist results (as expected)");
                }
                return true;
            } else {
                System.out.println("  ❌ Initiative Code '" + initiativeCode + "' IS present in Watchlist results (" + visibleCount + " row(s))"); // Updated by Shahu.D
                if (reportLogger != null) {
                    reportLogger.fail("Initiative Code '" + initiativeCode + "' IS present in Watchlist results (" + visibleCount + " row(s))");
                }
                return false;
            }

        } catch (Exception e) {
            System.err.println("  ❌ Error verifying initiative in Watchlist: " + e.getMessage()); // Updated by Shahu.D
            return false;
        }
    }
}


