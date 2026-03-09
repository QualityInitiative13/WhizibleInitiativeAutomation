package Pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Actions.ActionEngine;
import Locators.ProgramPageLocator;
import Locators.ProjectPageLocator;

public class ProgramPage  extends ActionEngine{
	
	WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
	

    public void clickprogram1() throws Throwable {
  	   
    click(ProgramPageLocator.program1, "Program1");
     }
   

    public void clickprogram2() throws Throwable {
    	 hover(ProgramPageLocator.program1, "Program1");
  	   
    click(ProgramPageLocator.program2, "Program`2");
     }
   
    public void clicknewprogram() throws Throwable {
   	   
        click(ProgramPageLocator. creatnewprogram, " creatnewprogram");
         }

    
    public void enterAbbreviatedName(String value) {
        WebElement el = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                ProgramPageLocator.AbbreviatedName
            ));
        el.clear();
        if (value != null) {
            el.sendKeys(value);
        }
    }

    
    public void enterProgramName(String value) {
        WebElement el = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                ProgramPageLocator.Programname
            ));
        el.clear();
        if (value != null) {
            el.sendKeys(value);
        }
    }

  
    public void clicksave() throws Throwable {
    	   
        jsClick(ProgramPageLocator.save, "save");
    } 
        
    
    
    public void clickinitiative() throws Throwable {
    	   
        click(ProgramPageLocator.initiative, "initiative");
    } 
      
    
    public void clicknewmapinitiative() throws Throwable {
    	   
        click(ProgramPageLocator.mapnewinitivative, "");
    } 
     
    
    
    
    public void clickEditOnProgramCard(String program) {
        
        clickEditFromCardWithPagination(
            ProgramPageLocator.projectCards,
            ProgramPageLocator. projectNameInCard,
            ProgramPageLocator.editButtonRelative,
            ProgramPageLocator.nextPage1,
            program
        );
  }
    
  
 /*   public void selectInitiativeWithPagination(String initiativeName) {

        selectRowCheckboxWithPagination(
            ProgramPageLocator.initiativeRows,
            ProgramPageLocator.initiativeNameCell,
            ProgramPageLocator.initiativeCheckbox,
            ProgramPageLocator.nextPage,
            initiativeName
        );
    }
  */
    public String selectRandomInitiative() {

        String selectedInitiative = selectRandomInitiativeWithPagination(
                ProgramPageLocator.initiativeRows,
                ProgramPageLocator.initiativeNameCell,
                ProgramPageLocator.initiativeCheckbox,
                ProgramPageLocator.nextPage
        );

        System.out.println("🎯 Final Selected Initiative: " + selectedInitiative);

        return selectedInitiative;   // 🔥 VERY IMPORTANT
    }

   
 /*   public void selectInitiativesFromExcel(List<String> initiatives) {

        for (String initiative : initiatives) {
            selectInitiativeWithPagination(initiative);
           
        }
   
    }
 */    
  
    public boolean isAlreadyMappedToastVisible() {
        try {
            WebElement toast = webDriver.findElement(
                    By.xpath("//*[contains(text(),'already linked')]")
            );
            return toast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

  
    public void uncheckProject(String projectName) {

        By checkboxLocator = By.xpath(
            "//tr[td[contains(text(),'" + projectName + "')]]//input[@type='checkbox']"
        );

        WebElement checkbox = webDriver.findElement(checkboxLocator);

        if (checkbox.isSelected()) {
            jsClickElement(checkbox);
        }
    }

    
    public void clickeditprogram() throws Throwable {
    	   
        click(ProgramPageLocator.editprogram, "editprogram");
         }
    
    
    
    
    
    public void clicksaveIni() throws Throwable {
    	   
        click(ProgramPageLocator.saveIni, "saveIni");
         }

    public void clickassociated() throws Throwable {
 	   
        click(ProgramPageLocator.associated, "associated");
         }

    public void clickmapassociated() throws Throwable {
  	   
        click(ProgramPageLocator.mapnewinitivative, "mapnewinitivative");
         }
    
    public  void clickmapping() throws Throwable {
   	   
    	 WebElement btn = webDriver.findElement(ProgramPageLocator.mapping);
    	    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", btn);
         }
    public void clickyes() throws Throwable {
    	   
        click(ProgramPageLocator.yes, "yes");
     
         }
   
  
    public void entertitle(String shortName) {

        By locator = ProgramPageLocator.entertitle;

        // 1️⃣ Wait until input is clickable (React-safe)
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
        input.click();
        // 2️⃣ Click & clear properly (MUI requirement)
        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);

        // 3️⃣ Type value
        input.sendKeys(shortName);

        // 4️⃣ JS fallback to trigger React onChange
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].value=arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
                input, shortName
        );

       // logSuccess("Entered title in search: " + shortName);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


    public void enterprogramAssociated(String shortName) {
        type(ProgramPageLocator. enterprogramAssociated, shortName, " enterprogramAssociated");
        
    }
    
    
    public void navigateToEditProgram(String createdProgramName) throws Throwable {

        clickprogram1();
        clickprogram2();

        // Wait until program cards are loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                ProgramPageLocator.programCards
        ));

        clickEditOnProgramCard(createdProgramName);
        clickeditprogram();

        System.out.println("✅ Navigated to Edit Program: " + createdProgramName);
    } 
    
    
    public boolean isCreateProgramFormOpen() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    ProgramPageLocator.AbbreviatedName
                )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    
    
    public boolean isEditProgramPageOpen() {
        try {
            return webDriver.findElement(
                    By.xpath("//div[@aria-label='Program']//img[@alt='Program']")
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void businessgroup() {
    	
    	  click(ProgramPageLocator.click, "click");
    	  selectRandomValueFromDropdown(ProgramPageLocator.selectbusiness, "bussinessgroup");
    	
    }
    
    public void oragnizationunit() {
    	
  	  click(ProgramPageLocator.clickorg, "click");
  	  selectRandomValueFromDropdown(ProgramPageLocator.selectorganization, "bussinessgroup");
  	
  }
    
    public static String generateAbbreviatedName() {
        int random = new Random().nextInt(90) + 10;
        return "AP" + random;
    }
    public static String generateProgramName() {
        String time = new SimpleDateFormat("HHmmss").format(new Date());
        return "AP_" + time;
    }


  

 
  

   
 /*   public String mapAssociatedRandomly() {

        return actionEngine.mapRandomUntilSuccess(
                ProgramPageLocator.AssociatedRows,
                ProgramPageLocator.AssociatedNameCell,
                ProgramPageLocator.AssociatedCheckbox,

                () -> {
					try {
						clickmapping();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				},
                () -> {
					try {
						clickyes();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
        );
    }
  */

    private ActionEngine actionEngine = new ActionEngine();

    public String mapAssociatedRandomly() {

        return actionEngine.mapRandomUntilSuccess(
                ProgramPageLocator.AssociatedRows,
                ProgramPageLocator.AssociatedNameCell,
                ProgramPageLocator.AssociatedCheckbox,
                ProgramPageLocator.nextPage2,

                () -> {
                    try { clickmapping(); } catch (Throwable e) { throw new RuntimeException(e); }
                },

                () -> {
                    try { clickyes(); } catch (Throwable e) { throw new RuntimeException(e); }
                }
        );
    }



    public boolean isAssociatedProjectDisplayed(String expectedProjectName) {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        List<WebElement> cards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        ProgramPageLocator.AssociateCards)
        );

        for (WebElement card : cards) {

            String cardText = card.getText().trim();

            System.out.println("Checking Card Text: " + cardText);

            // 🔥 partial match (UI truncates text sometimes)
            if (cardText.toLowerCase().contains(expectedProjectName.toLowerCase())) {
                System.out.println("✅ Associated Project Found: " + expectedProjectName);
                return true;
            }
        }

        return false;
    }


    
    
    public void clicksearchicon() throws Throwable {
  	   
        click(ProgramPageLocator.clicksearchicon, "clicksearchicon");
         }
    
    public void clicksearchicontext() throws Throwable {
   	   
        click(ProgramPageLocator.clicksearchicon2, "clicksearchicon2");
         }
    
    
    
    public void enterAbbreviatedName1(String abbreviatedName1) throws Throwable {
    	   
    	  type(ProgramPageLocator. AbbreviatedName1, abbreviatedName1, " AbbreviatedName1");
         }
    
    
    public void enterProgramName1(String Programname)throws Throwable {
    	   
    	  type(ProgramPageLocator. Programname, Programname, "Programname");
         }
    
    
    public boolean isProgramCardDisplayed(String programName) {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        try {
            WebElement cardTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    ProgramPageLocator.programCardByName(programName)
            ));

            // go to parent card container
            WebElement fullCard = cardTitle.findElement(By.xpath("./ancestor::div[contains(@class,'MuiPaper-root')]"));

            System.out.println("✅ Program Card Found: " + fullCard.getText());
            return fullCard.isDisplayed();

        } catch (TimeoutException e) {
            System.out.println("❌ Program Card NOT Found: " + programName);
            return false;
        }
    }

    
    public String getProgramName() {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement programName = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@aria-label,'Program Name')]")
            )
        );

        String name = programName.getText().trim();
        System.out.println("Program Name = " + name);
        return name;
    }   
    
   public void   clicklockfreeze() {
	  click(ProgramPageLocator.clickonfreeze,"clickonfreeze");
   }
    
   public void entercomment(String comment ) {
	   click(ProgramPageLocator.entercomment,"entercomment");
	   typeInModal(ProgramPageLocator.entercomment,comment,"entercomment");
   }
    
   public void clickSaveButton() {

	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

	    By saveLocator =
	        By.xpath("(//button[.//span[normalize-space()='Save']])[2]");

	    WebElement saveBtn = wait.until(
	        ExpectedConditions.elementToBeClickable(saveLocator)
	    );

	    ((JavascriptExecutor) webDriver)
	            .executeScript("arguments[0].scrollIntoView(true);", saveBtn);

	    try {
	        saveBtn.click();
	    } catch (Exception e) {
	        ((JavascriptExecutor) webDriver)
	                .executeScript("arguments[0].click();", saveBtn);
	    }

	    System.out.println("✅ Save clicked");
	}
    
    public String generateAutoFreezeComment() {

        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));

        return "This comment is added automatically for freezing the program on " + timestamp;
    }
    
    
    
    
    
    
    
    
    
    public void clearField(By locator, String fieldName) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.click();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);

       // log("Cleared " + fieldName);
    }
    
    
    
    
    public void verifyAndPrintShowHistory() {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // Wait for at least one history row
        WebElement historyRow = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='table-responsive']//table//tbody//tr")
            )
        );

        Assert.assertTrue(historyRow.isDisplayed(),
                "❌ Show History row is not displayed");

        // Get all rows
        List<WebElement> rows = webDriver.findElements(
            By.xpath("//div[@class='table-responsive']//table//tbody//tr")
        );

        System.out.println("\n📜 Show History Details:");
        System.out.println("────────────────────────────────────");

        for (WebElement row : rows) {

            List<WebElement> columns = row.findElements(By.tagName("td"));

            String modifiedField = columns.get(0).getText().trim();
            String modifiedDate  = columns.get(1).getText().trim();
            String oldValue      = columns.get(2).getText().trim();
            String newValue      = columns.get(3).getText().trim();
            String modifiedBy    = columns.get(4).getText().trim();

            System.out.println("📝 Modified Field : " + modifiedField);
            System.out.println("📅 Modified Date  : " + modifiedDate);
            System.out.println("🔹 Old Value      : " + oldValue);
            System.out.println("🔸 New Value      : " + newValue);
            System.out.println("👤 Modified By    : " + modifiedBy);
            System.out.println("────────────────────────────────────");
        }
    }
    
    
    public void clickhistory() {
    	click(ProgramPageLocator.clickhistory,"clickhistory");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}  

