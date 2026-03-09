package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Inbox Module
 *
 * This class contains all locators used in Inbox page automation.
 * Following Page Object Model (POM) design pattern with:
 * - Static locators for reusable elements
 * - Dynamic helper methods for parameterized locators
 * - Alternative locators for robust element finding
 *
 * @author Automation Team
 * @version 1.0
 */
public class InboxPageLocators {

    // ==================== NAVIGATION ====================

    /** Inbox section link - Updated by Shahu.D */
    public static By inboxSectionLink = By.xpath("//*[@id=\"ImFltr-Inbox\"]/a/span[2] | //*[@id='ImFltr-Inbox']/a/span[2] | //*[@id='ImFltr-Inbox']//a | //a[contains(@id,'Inbox')]//span[2] | //span[contains(text(),'Inbox')]"); // Updated by Shahu.D

    // ==================== SEARCH ====================

    /** Search icon - Updated by Shahu.D */
    public static By searchIcon = By.xpath("//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img | //*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div[2]//div//div//img | //img[contains(@alt,'Search')] | //img[@alt='Search']"); // Updated by Shahu.D

    /** Initiative Code input field - Updated by Shahu.D */
    public static By initiativeCodeInput = By.xpath("//*[@id=\"DemandCode\"] | //*[@id='DemandCode'] | //input[@id='DemandCode'] | //input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]"); // Updated by Shahu.D

    /** Final Search button - Updated by Shahu.D */
    public static By finalSearchButton = By.xpath("//*[@id=\"id__520\"] | //button[@id='id__520'] | //button[contains(text(),'Search')] | //button[.//span[text()='Search']]"); // Updated by Shahu.D

    // ==================== VALIDATION ====================

    /** Initiative code in table/list - Updated by Shahu.D */
    public static By initiativeCodeInTable = By.xpath("//div[@role='row']//div[contains(text(),'{INITIATIVE_CODE}')] | //tr//td[contains(text(),'{INITIATIVE_CODE}')] | //div[contains(@class,'row')]//div[contains(text(),'{INITIATIVE_CODE}')]"); // Updated by Shahu.D

    // ==================== DYNAMIC HELPER METHODS ====================

    /**
     * Get dynamic locator for initiative code in table
     * @param initiativeCode Initiative code to search for
     * @return By locator for the row containing the initiative code
     */
    public static By getInitiativeCodeLocator(String initiativeCode) {
        return By.xpath("//div[@role='row']//div[contains(text(),'" + initiativeCode + "')] | //tr//td[contains(text(),'" + initiativeCode + "')] | //div[contains(@class,'row')]//div[contains(text(),'" + initiativeCode + "')]"); // Updated by Shahu.D
    }
}

