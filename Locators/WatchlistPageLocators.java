package Locators;

import org.openqa.selenium.By;

/**
 * Page Object Locators for Watchlist Page
 * Updated by Shahu.D
 */
public class WatchlistPageLocators {

    /** Watchlist section link - Updated by Shahu.D */
    public static By watchlistSection = By.xpath(
            "//*[@id=\"ImFltr-Watchlist\"]/a/span[2] | " +
            "//*[@id='ImFltr-Watchlist']/a/span[2] | " +
            "//span[contains(text(),'Watchlist')]"
    ); // Updated by Shahu.D

    /** Search icon on Watchlist page - Updated by Shahu.D */
    public static By searchIcon = By.xpath(
            "//*[@id=\"root\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[2]/div/div/img | " +
            "//*[@id='root']//div[2]//div[2]//div[2]//div[2]//div//div//div[2]//div//div[2]//div//div//img | " +
            "//img[contains(@alt,'Search')] | //img[@alt='Search']"
    ); // Updated by Shahu.D

    /** Initiative Code input field in Watchlist search - Updated by Shahu.D */
    public static By initiativeCodeInputField = By.xpath(
            "//*[@id=\"DemandCode\"] | //input[@id='DemandCode'] | //input[@name='DemandCode'] | " +
            "//input[contains(@placeholder,'Initiative Code') or contains(@placeholder,'Code')]"
    ); // Updated by Shahu.D

    /** Final Search button on Watchlist page - Updated by Shahu.D */
    public static By finalSearchButton = By.xpath(
            "//*[@id=\"id__529\"] | //button[@id='id__529'] | " +
            "//div[@class='css-4io43t'] | " +
            "//button[contains(text(),'Search')] | //button[.//span[text()='Search']]"
    ); // Updated by Shahu.D

    /**
     * Dynamic locator for initiative row in Watchlist results
     * Updated by Shahu.D
     * @param initiativeCode Initiative code to search for
     * @return By locator that matches the initiative code in results
     */
    public static By getInitiativeInWatchlistLocator(String initiativeCode) {
        return By.xpath(
                "//div[@role='row' and .//span[contains(text(),'" + initiativeCode + "')]] | " +
                "//div[@role='row' and .//div[contains(text(),'" + initiativeCode + "')]] | " +
                "//td[contains(text(),'" + initiativeCode + "')]"
        ); // Updated by Shahu.D
    }
}


