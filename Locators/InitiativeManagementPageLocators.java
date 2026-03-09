package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Initiative Management Module
 *
 * This class contains all locators used in Initiative Management page automation.
 * Following Page Object Model (POM) design pattern with:
 * - Static locators for reusable elements
 * - Dynamic helper methods for parameterized locators
 * - Alternative locators for robust element finding
 *
 * @author Automation Team
 * @version 1.0
 */
public class InitiativeManagementPageLocators {

    // ==================== NAVIGATION ====================

    /** Initiative Management navigation element - Updated by Shahu.D */
    public static By initiativeManagementNav = By.xpath("//div[@aria-label='Initiative Management']//img[@alt='Initiative Management'] | //img[@alt='Initiative Management'] | //div[contains(@class,'navigation')]//img[contains(@alt,'Initiative')]"); // Updated by Shahu.D

    /** Initiatives page link - Updated by Shahu.D */
    public static By initiativesPageLink = By.xpath("//*[@id=\"children-panel-container\"]/div[3]/div[1]/p | //*[@id='children-panel-container']//p[contains(text(),'Initiatives')] | //p[contains(text(),'Initiatives')]"); // Updated by Shahu.D

    // ==================== GENERAL ====================

    /** All iframes on page - Updated by Shahu.D */
    public static By allIframes = By.tagName("iframe"); // Updated by Shahu.D
    
    
    
    public static By entertitle = By.xpath("//input[@id='DemandCode']");
    
    
    public static By clickfilter = By.xpath("//button[.//span[normalize-space()='Search']]");
    
    public static By clicktool = By.xpath("//img[@aria-label='Search']");
}

