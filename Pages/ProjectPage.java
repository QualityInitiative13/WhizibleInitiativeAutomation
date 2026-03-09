package Pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import Actions.ActionEngine;
import Locators.InitiativePageLocators;
import Locators.ProgramPageLocator;
import Locators.ProjectPageLocator;

   

public class ProjectPage extends  ActionEngine {
	WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
	
	          public void clickprogram() throws Throwable {
	        	   
	          click(ProjectPageLocator.program, "Program");
	           }
	        
	          
	         public void clickproject() throws Throwable {
	        	 hover(ProjectPageLocator.program, "Program");
	           click(ProjectPageLocator.project, "Projects");
	       
	         }
	        
	        public void  clickcreatnewproject() {
	        click(ProjectPageLocator.createNewProject, "Create New Project");
	        
	        }
	        
	        public void enterAbbreviatedName(String shortName) {
	        	clearField(ProjectPageLocator.abbreviatedName, "Abbreviated Name");
	            type(ProjectPageLocator.abbreviatedName, shortName, "Abbreviated Name");
	        }

	        public void enterProjectName(String name) {
	        	clearField(ProjectPageLocator.projectName, "projectName");
	            type(ProjectPageLocator.projectName, name, "Project Name");
	        }

	        public void enterWorkHours(String hours) {
	            type(ProjectPageLocator.workHour, hours, "Work Hours");
	        }

	        // ---------- DROPDOWNS ----------
   
	       public void selectVendor() {
	            click(ProjectPageLocator.vendorDropdown, "Vendor Dropdown");
	            selectRandomValueFromDropdown(ProjectPageLocator.vendorList, "Vendor List");
	        }

	        public void selectProjectCurrency() {
	            click(ProjectPageLocator.projectCurrency, "Project Currency");
	            selectRandomValueFromDropdown(ProjectPageLocator.projectCurrencyList, "Currency List");
	        }

	        public void selectBusinessGroup() {
	            click(ProjectPageLocator.businessGroup, "Business Group");
	            selectRandomValueFromDropdown(ProjectPageLocator.businessGroupList, "Business Group List");
	        }

	        public void selectOrganizationUni() {
	            click(ProjectPageLocator.organizationUnit, "Organization Unit");
	            selectRandomValueFromDropdown(ProjectPageLocator.organizationUnitList,  "Organization Unit List");
	        }

	        public void selectPractice() {
	            click(ProjectPageLocator.practice, "Practice");
	            selectRandomValueFromDropdown(ProjectPageLocator.practiceList, "Practice List");
	        }
	      
	        
	      //  public void selectStartDate(String month, String day) {
	      //      click(ProjectPageLocator.startDate, "Start Date");

	      //      selectDateGeneric(
	       //         ProjectPageLocator.calendarMonths,
	        //        ProjectPageLocator.calendarDays,
	        //        month,
	        //        day,
	        //        "Start Date Calendar"
	        //    );
	      //  }

	      
	    //    public void selectEndDate(String months, String days) throws InterruptedException {
	           
	     //       click(ProjectPageLocator.endDate, "End Date Calendar");
	     //       Thread.sleep(2000);
	     //   	forceFocus(ProjectPageLocator.calendarMonths);
	        	
	      //           Thread.sleep(2000);
	      //             selectDateGeneric(
	      //              ProjectPageLocator.calendarMonths,
	    //            ProjectPageLocator.calendarDays,
	     //               months,
	     //               days,
	     //               "End Date Calendar"
	    //        );
	    //    }

	      //  private void forceFocus(By calendarContainer) {
	      //      WebElement cal = wait.until(
	      //          ExpectedConditions.visibilityOfElementLocated(calendarContainer));

	      //      ((JavascriptExecutor) webDriver)
	      //          .executeScript("arguments[0].focus();", cal);
	      //  }
	       
	    
	        private void openCalendar(By dateInput) {

	            if (webDriver == null) {
	                throw new IllegalStateException("WebDriver is NULL");
	            }

	            WebElement input =
	                wait.until(ExpectedConditions.presenceOfElementLocated(dateInput));

	            ((JavascriptExecutor) webDriver)
	                .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	            ((JavascriptExecutor) webDriver)
	                .executeScript("arguments[0].click();", input);

	         
	          //  waitForElementToBeVisible(ProjectPageLocator.calendarRoot, "Calendar");
	           // waitForSeconds(1);
	        }

	        
	        private void openYearRangeView() {

	            // Day → Month
	        //    jsClick(ProjectPageLocator.monthYearHeader, "Month-Year Header");
	       //     waitForSeconds(1);

	            // Month → Year range
	            jsClick(ProjectPageLocator.yearswitchbutton, "Year Header");
	            waitForSeconds(1);
	        }
	      
	       
	        
	        
	        public void selectYear(String targetYear) {

	            int year = Integer.parseInt(targetYear);

	            while (true) {

	                WebElement header = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(
	                        ProjectPageLocator.yearGridheader
	                    )
	                );

	                String[] range = header.getAttribute("aria-label").split("-");
	                int start = Integer.parseInt(range[0].trim());
	                int end = Integer.parseInt(range[1].trim());

	                if (year >= start && year <= end) {
	                    click(ProjectPageLocator.yearCells(targetYear), "Year " + targetYear);
	                    break;
	                }

	                if (year < start) {
	                    click(ProjectPageLocator.prevArrow, "Previous Year");
	                } else {
	                    click(ProjectPageLocator.nextArrow, "Next Year");
	                }
	            }
	        }


	       
	        private void selectMonth(String month) {

	            String shortMonth = month.substring(0, 3);

	            for (WebElement m : webDriver.findElements(ProjectPageLocator.monthCells)) {
	                if (m.getText().equalsIgnoreCase(shortMonth)) {
	                    m.click();
	                    waitForSeconds(1);
	                    return;
	                }
	            }
	            throw new RuntimeException("Month not found: " + month);
	        }

	        
	        
	        
	        
	        private void selectDay(String day) {

	            for (WebElement d : webDriver.findElements(ProjectPageLocator.dayCells)) {
	                if (d.getText().equals(day)) {
	                    ((JavascriptExecutor) webDriver)
	                        .executeScript("arguments[0].click();", d);
	                    return;
	                }
	            }
	            throw new RuntimeException("Day not found: " + day);
	        }

	        
	        
	   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
	        
	        public void selectDate(By dateInput,
                   String year,
                    String month,
                    String day,
                    String name) {

                 openCalendar(dateInput);
                         openYearRangeView();
                          selectYear(year);
                         selectMonth(month);
                             selectDay(day);

	        }

	  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	        public void selectStartDate(String year,String month, String day) {
	            selectDate(
	                ProjectPageLocator.startDateInput,
	                year,
	                month,
	                day,
	                "Start Date"
	            );
	        }

	        public void selectEndDate(String year, String month, String day) {
	            selectDate(
	               ProjectPageLocator.endDateInput,
	               year,
	                month,
	             day,
	               "End Date"
	          );
	        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	        public void selectStartDate1(String year,String month, String day) {
	            selectDate(
	                ProjectPageLocator.startDateInput1,
	                year,
	                month,
	                day,
	                "Start Date"
	            );
	        }
	        public void selectEndDate1(String year, String month, String day) {
	            selectDate(
	               ProjectPageLocator.endDateInput1,
	               year,
	                month,
	             day,
	               "End Date"
	          );
	        }
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        

			public void clickSave() {
	            click(ProjectPageLocator.saveButton, "Save Button");
	        }
	        
	        public void clickSerach() {
	            click(ProjectPageLocator.search, "Search Button");
	        }
	        
	        public void clickSerachfinal() {
	            click(ProjectPageLocator.searchfinal, "Search Button");
	        }
	        
	      public void clickEditOnProjectCard(String projectName) {
	        
	            clickEditFromCardWithPagination(
	                ProjectPageLocator.projectCards,
	                ProjectPageLocator. projectNameInCard,
	                ProjectPageLocator.editButtonRelative,
	                ProjectPageLocator.nextPage,
	                projectName
	            );
	      }
	      
	       
	   /*    public void clickEditOnProjectCard(String projectName) {
	            clickEditFromCardWithPagination(
	                ProjectPageLocator.projectCardContainer,
	                ProjectPageLocator.projectNameInCard,
	                ProjectPageLocator.cardMoreActionsButton,
	                ProjectPageLocator.nextPage,
	                projectName
	            );
	        }
	   */     
	        public void clickeditproject() {
	            click(ProjectPageLocator.editproject, "edit project");
	        }
	           
	        public void  clickdefinemilestone() {
		        click(ProjectPageLocator.definemilestone, "Create New Project");
		        
		        }
	        public void  clickaddnew() {
		        click(ProjectPageLocator.addnew, "Create New Project");
		        
		        }  
	        
	        public void milestone(String shortName) {
	            type(ProjectPageLocator.milestone, shortName, "milestonbe");
	        }
	        
	  //      public void selectStartDate1(String months, String days) {
	  //          click(ProjectPageLocator.startDate1, "Start Date");

	   //         selectDateGeneric(
	   //             ProjectPageLocator.calendarMonths1,
	   //             ProjectPageLocator.calendarDays1,
	    //            months,
	    //            days,
	    //            "Start Date Calendar"
	    //        );
	   //     }

	        
	        
	   //     public void selectEndDate1(String month, String day) {

	            // 1️⃣ Click END DATE field (this is the only difference)
	    //        click(ProjectPageLocator.endDate1, "End Date Calendar");

	            // 2️⃣ Reuse the SAME generic method
	    //        selectDateGeneric(
	     //           ProjectPageLocator.calendarMonths1,
	      //          ProjectPageLocator.calendarDays1,
	      //          month,
	      //          day,
	      //          "End Date Calendar"
	      //      );
	     //   }
	        
	        
	        public void selectmilestonestatus(String practiceName) {
	            click(ProjectPageLocator.milestonestatus, "milestone");
	            selectFromList(ProjectPageLocator.milestonestatuslist, practiceName, "milestonelist");
	        }
	        
	      //  public void enterwork(String hours) {
	        //    type(ProjectPageLocator.work, hours, "Work ");
	      //  }
	        
	        public void enterwork(String hours) {

	            WebElement el = webDriver.findElement(ProjectPageLocator.work);

	            ((JavascriptExecutor) webDriver)
	                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);

	            el.clear();
	            el.click();
	            el.sendKeys(hours);

	            log.info("✅ Typed into: Work -> " + hours);
	        }

	        
	        public void  clicksavemilestone() throws Throwable {
	        	
		        click(ProjectPageLocator.savemilestone, "savemilestone");
		     
		    //    By toast = By.xpath("//span[contains(text(),'Milestone Saved Successfully')]");
	        //    wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

	            // 2️⃣ locate cross button (SVG-based)
	      //      WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
	       //         By.xpath("//button[.//*[name()='svg']]")
	       //     ));

	            // 3️⃣ JS click (bypasses overlay & z-index issues)
	      //     JavascriptExecutor js = (JavascriptExecutor) webDriver;
	     //      js.executeScript("arguments[0].click();", closeBtn);
	            
	       //    Actions actions = new Actions(webDriver);

	        // move mouse to left side and click
	      //  actions.moveByOffset(300, 0)   // move left by 300 pixels
	      //         .click()
	       //        .perform();

	        }
	        
	        
	    /*    public void  clickcross() throws Throwable {
	        	
	        	By toast = By.xpath("//span[contains(text(),'Milestone Saved Successfully')]");

	        	// wait until toast disappears
	        	wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

	       	WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
	        		    By.xpath("//button[@aria-label='Close']")
	        		));

	        		new Actions(webDriver)
	        		    .moveToElement(closeBtn)
	        		    .pause(Duration.ofMillis(300))
	        		    .click()
	      	    .perform();
           

	         click(ProjectPageLocator.crosssavemilestone, "crosssavemilestone");
		        
		        }
	        */
	        
	    /*    public void clickcross() {

	            // 1️⃣ wait until success toast disappears
	            By toast = By.xpath("//span[contains(text(),'Milestone Saved Successfully')]");
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

	            // 2️⃣ locate cross button (SVG-based)
	            WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//button[.//*[name()='svg']]")
	            ));

	            // 3️⃣ JS click (bypasses overlay & z-index issues)
	            JavascriptExecutor js = (JavascriptExecutor) webDriver;
	            js.executeScript("arguments[0].click();", closeBtn);
	        }
       */
	        
	        public void  clickupdateactualcost() {
		        click(ProjectPageLocator.updateactucalcost, "updateactualcost");
		        
		        }
	        
	        public void  clickaddnewupdateactucalcost() {
		        click(ProjectPageLocator.addnewupdateactucalcost , "addnewupdateactucalcost");
		        
		        }
	        public void selectcostcategoryr(String vendorName) {
	            click(ProjectPageLocator.costcategory, "Vendor Dropdown");
	            selectFromList(ProjectPageLocator.costcategorylist, vendorName, "Vendor List");
	        }
	        
	        
	        public void enteramount(String amount) {
	            type(ProjectPageLocator.amount, amount, "amount ");
	        }
	        
	        public void  clicksaveupdatecost() {
		        click(ProjectPageLocator.saveupdatecost , "saveupdatecost");
		        

	        }
			       
		        
		     
	        
	        public void  clickupdateproject() {
		        click(ProjectPageLocator.updateprojectsheet , "updateprojectsheet");
		        
		        }
	        
	        public void  clickaddnewupdateproject() {
		        click(ProjectPageLocator.addnewupdateproject , "addnewupdateproject");
		        
		        }
	        
	     //  public void  clickgeneratecress() {
	      //  	By toast = By.xpath("//span[contains(text(),'Saved Successfully') or contains(text(),'Successfully')]");
	      //  	wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

		  //      click(ProjectPageLocator.Generatecress , "Generatecress");
		        
		       
		        
		  //      }  
		       
	        public void clickgeneratecress() {

	            // wait for any success toast
	            By toast = By.xpath("//span[contains(text(),'Saved Successfully') or contains(text(),'Successfully')]");
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

	            WebElement generateBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
	                ProjectPageLocator.Generatecress
	            ));

	            JavascriptExecutor js = (JavascriptExecutor) webDriver;

	            js.executeScript("arguments[0].scrollIntoView({block:'center'});", generateBtn);
	            js.executeScript("arguments[0].click();", generateBtn);
	        }
  
		         
	      ////////////////////////////////////////////////////////////////////////////  
	    /*    public void entercostvalue(String value) {
	            type(ProjectPageLocator.ccostvalue, value, "ccost");
	        }
	        
	        
	        public void entercostdes(String value) {

                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

                                     WebElement el = wait.until(
                   ExpectedConditions.elementToBeClickable(ProjectPageLocator.ccostdes)
                                        );

                                                         el.click();
                                            el.sendKeys(Keys.CONTROL, "a");
                                            el.sendKeys(Keys.DELETE);
                                                el.sendKeys(value);
                                          el.sendKeys(Keys.TAB);   // 🔑 IMPORTANT for React to commit value
}

	    /////////////////////////////////////////////////////////////////////////////////////////    
	        
	        public void enterrisk(String value) {
	            type(ProjectPageLocator.rrisk, value, "risk");
	        }
	        
	        
	        public void enterrriskdes(String value) {
	            type(ProjectPageLocator.rriskdes, value, "rriskdes");
	        }
	     /////////////////////////////////////////////////////////////////////////   
	        
	        
	        public void entereffort(String value) {
	            type(ProjectPageLocator.eeffort, value, "eeffort");
	        }
	        
	        
	      /*  public void entereffortdes(String value) {
	          //  type(ProjectPageLocator.eeffortdes, value, "eeffortdes");
	        	 typeInModalField(ProjectPageLocator.eeffortdes, value, "E - Effort");
	       }
	      //////////////////////////////////////////////////////////////////////// 
	       
              public void entereffortdes(String value) {

                    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

                                         WebElement el = wait.until(
                       ExpectedConditions.elementToBeClickable(ProjectPageLocator.eeffort)
                                            );

                                                             el.click();
                                                el.sendKeys(Keys.CONTROL, "a");
                                                el.sendKeys(Keys.DELETE);
                                                    el.sendKeys(value);
                                              el.sendKeys(Keys.TAB);   // 🔑 IMPORTANT for React to commit value
}


	        public void entersche(String value) {
	            type(ProjectPageLocator.sche, value, "sche");
	        }
	        
	        
	        public void enterschedes(String value) {
	            type(ProjectPageLocator.schedes, value, "schedes");
	        }
	        //////////////////////////////////////////////////////////////////////////
	        
	        public void enterscope(String value) {
	            type(ProjectPageLocator.scope, value, "scope");
	        }
	        
	        
	        public void enterscopedes(String value) {
	            type(ProjectPageLocator.scopedes, value, "scopedes");
	        }
	   */    
	        
	     public void fillCressRow(String rowName, String value, String description) {

	            clearAndTypeReact(
	                ProjectPageLocator.cressValueByRowName(rowName),
	                value,
	                rowName + " Value"
	            );

	            clearAndTypeReact(
	                ProjectPageLocator.cressDescByRowName(rowName),
	                description,
	                rowName + " Description"
	            );
	        }
        
	      
	     
	  /*    public void clickbypixel() {
	    	   Actions actions = new Actions(webDriver);
		        actions.moveByOffset(300, 0)   // move left by 300 pixels
	               .click()
	               .perform();
	       }
	  */   
	      public void closeUpdateHealthSheet() {

	    	    JavascriptExecutor js = (JavascriptExecutor) webDriver;

	    	    js.executeScript(
	    	        "const el = document.elementFromPoint(50, window.innerHeight / 2);" +
	    	        "if (el) el.click();"
	    	    );
	    	}

	      public void  clicksavesheet() {
		        click(ProjectPageLocator.savehealthsheet, "savehealthsheet");
		      
		        
		        } 
	  
	        
	        public void  clickmilestoneprocess() {
		        click(ProjectPageLocator.milestoneprocess , "milestoneprocess");
		        
		        } 
	        
	        public void  clickaddnewmilestone() {
		        click(ProjectPageLocator.addmilestoneprocess , "addmilestoneprocess");
		        
		        }
	        

	        public void  clickupdatemilestone() {
		        click(ProjectPageLocator. updatemilestone , "updatemilestone");
		        
		        }
	        
	        public void entepercnetage(String per) {
	            type(ProjectPageLocator.percentageprocess, per, "percentageprocess");
	        }
	        
	        
	        
	        public void  clicksavemilestoneprocess() {
		        click(ProjectPageLocator. savemilestoneprocess , "updatemilestone");
		        
		        }
	        
	        

		      public void  clickmanagehealth() {
			        click(ProjectPageLocator.manageHealth, "manageHealth");
			      
			        
			        } 
		      
		      public void  clicksaveandapprove() {
			        click(ProjectPageLocator.saveandapprove, "saveandapprove");
			      
			        
			        } 
		      
		      public void  clickhealthsheet() throws Throwable {
		    	     hover(ProjectPageLocator. projehealthsheetapprovel, " projehealthsheetapprovel");
			       click(ProjectPageLocator. projehealthsheetapprovel, " projehealthsheetapprovel");
			      
			        
		     } 
		      
		      
		  /*   public void clickhealthsheet() {

		    	    // Hover Program
		    	    WebElement program =
		    	        new WebDriverWait(webDriver, Duration.ofSeconds(10))
		    	            .until(ExpectedConditions.visibilityOfElementLocated(ProjectPageLocator.program));

		    	    Actions actions = new Actions(webDriver);
		    	    actions.moveToElement(program).pause(Duration.ofMillis(300)).perform();

		    	    // Wait for Health Sheet option
		    	    WebElement healthSheet =
		    	        new WebDriverWait(webDriver, Duration.ofSeconds(10))
		    	            .until(ExpectedConditions.visibilityOfElementLocated(
		    	                ProjectPageLocator.projehealthsheetapprovel));

		    	    // REAL mouse click
		    	    actions.moveToElement(healthSheet)
		    	           .pause(Duration.ofMillis(200))
		    	           .click()
		    	           .perform();
		    	}
       */
		      
		      public void selectprojectname(String projectname) {
		            click(ProjectPageLocator.projectname, "projectname");
		            selectFromList(ProjectPageLocator.projectnamelist, projectname, "projectnamelist");
		        }
		      
		      public void  clickviewdetails() {
			        click(ProjectPageLocator. viewdetails, " viewdetails");
			       
			        } 
		      
		      public void enterrationale(String comment) {
		            type(ProjectPageLocator.rationale, comment, "rationale");
		        }
		      
		      
		     
		      public void  clickapprove() {
			        click(ProjectPageLocator.approve, " approve");
			      
			        } 
		     
		      public void  clickmanagemile() {
			        click(ProjectPageLocator.managedefinemilestone, " managedefinemilestone");
			      
			        } 
		      
		      public void clearAndEnterText(By locator, String value) {
		    	WebElement field =   wait.until(ExpectedConditions.elementToBeClickable(locator));
		    	    field.click();

		    	    field.sendKeys(Keys.CONTROL + "a");  // select all
		    	    field.sendKeys(Keys.DELETE);         // delete existing text
		    	    field.sendKeys(value);               // enter new text
		    	}
		      
		      public void  clickhistory() {
			        click(ProjectPageLocator.history, " history");
			      
			        } 
		      
		      public void entersearchmilestone(String value) {
		            type(ProjectPageLocator. milestoneenter, value, " milestoneenter");
		            
		        }
		      
		      public void  clicksearchhealthsheet() {
			        click(ProjectPageLocator.searchhealthsheet, "searchhealthsheet");
			      
			        } 
		      
		      public void selectStartDate2(String year,String month, String day) {
		            selectDate(
		                ProjectPageLocator.fromdate,
		                year,
		                month,
		                day,
		                "from Date"
		            );
		        }
		        public void selectEndDate2(String year, String month, String day) {
		            selectDate(
		               ProjectPageLocator.todate,
		               year,
		                month,
		             day,
		               "to Date"
		          );
		        }
		        
		 
		        
		        public void  clicksearchactucalcost() {
			        click(ProjectPageLocator.searchactucalcost, "searchactucalcost");
			      
			        }  
		      
		        
		        public void  clicksearchactucalcostfinal() {
			        click(ProjectPageLocator.searchactucalcostfinal, "searchactucalcostfinal");
			      
			        }  
		        
		        
		      
		        public  boolean isCreateProjectFormOpen() {
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
		        
		        
		        public void navigateToEditProject(String projectName) throws Throwable {

		            clickprogram();
		            clickproject();
		            Thread.sleep(1000);   // ideally replace with explicit wait later
		            clickEditOnProjectCard(projectName);
		            clickeditproject();

		            log.info("✅ Navigated to Edit Project for: " + projectName);
		        }

		        
		        public void navigateToEditProjectIfNeeded(String projectName) throws Throwable {

		            if (!isEditProjectPageOpen(projectName)) {
		                navigateToEditProject(projectName);
		            }
		        }

		        
		        public boolean isEditProjectPageOpen(String projectName) {
		            try {
		                return webDriver.findElement(
		                    By.xpath("//h1[text()='Edit Project']")
		                ).isDisplayed();
		            } catch (Exception e) {
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
		        
		        
		        public void openDefineMilestone() {
		            clickdefinemilestone();
		            clickaddnew();
		        }

		      //  public void saveMilestoneExpectAlert() throws Throwable {
		     //       clicksavemilestone();
		     //   }

		        public void fillMilestoneBasic(
		                String milestone,
		                String syear, String smonth, String sday,
		                String eyear, String emonth, String eday,
		                String status,
		                String work) {

		            milestone(milestone);
		            selectStartDate1(syear, smonth, sday);
		            selectEndDate1(eyear, emonth, eday);
		            selectmilestonestatus(status);
		            enterwork(work);
		        }

		       
		        
		        public WebElement clickEnabledGenerateCress() {

		            WebElement btn = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            ProjectPageLocator.enabledGenerateCressLinks
		                    )
		            );

		            jsScrollIntoView(btn);
		            btn.click();
		            return btn;   // return for staleness wait
		        }
   
		        
		        public void clickSaveHealthSheet() {

		            WebElement saveBtn = wait.until(
		                    ExpectedConditions.elementToBeClickable(
		                            ProjectPageLocator.savehealthsheet
		                    )
		            );

		            ((JavascriptExecutor) webDriver).executeScript(
		                    "arguments[0].scrollIntoView({block:'center'});",
		                    saveBtn
		            );

		            ((JavascriptExecutor) webDriver).executeScript(
		                    "arguments[0].click();",
		                    saveBtn
		            );

		            waitForToastToDisappear();
		          //  waitForSaveComplete();
		        }

		        
		      
 
		        
		     

		        
		        public void waitForSaveComplete() {

		            wait.until(driver -> {
		                WebElement saveBtn =
		                        driver.findElement(ProjectPageLocator.savehealthsheet);

		                return saveBtn.isEnabled();
		            });
		        }

		        
		        public WebElement clickEnabledGenerateCressSafely() {

		            WebElement generateBtn = clickEnabledGenerateCress();

		            if (generateBtn == null) {
		                return null;
		            }

		            // Scroll properly
		            ((JavascriptExecutor) webDriver).executeScript(
		                    "arguments[0].scrollIntoView({block:'center'});",
		                    generateBtn
		            );

		            // Wait until no overlay is blocking
		            wait.until(ExpectedConditions.elementToBeClickable(generateBtn));

		            // JS click to avoid interception
		            jsClick(generateBtn);

		            // Wait for CRESS form to open
		            wait.until(ExpectedConditions.visibilityOfElementLocated(
		                    ProjectPageLocator.savehealthsheet
		            ));

		            return generateBtn;
		        }
 
		        public void generateAndFillAllCress(String[][] cressRows) throws InterruptedException {

		            while (clickNextGenerateCress() ) {

		                // Trigger validation once
		                clickSaveHealthSheet();
		                checkMandatoryValidationIfPresent();

		                // Fill all 5 rows
		                for (String[] row : cressRows) {

		                    fillCressRow(row[0], row[1], row[2]);
		                    clickSaveHealthSheet();
		                 //   waitForToastToDisappear();
		                }

		          
		            }
		            Thread.sleep(3000);
		        	closeUpdateHealthSheet();
		            //System.out.println("🎉 All CRESS cycles completed successfully");
		        }


		        public void checkMandatoryValidationIfPresent() {

		            try {
		                WebElement validation = new WebDriverWait(webDriver, Duration.ofSeconds(3))
		                        .until(ExpectedConditions.visibilityOfElementLocated(
		                                ProjectPageLocator.mandatoryError
		                        ));

		                System.out.println("⚠ Validation shown: " + validation.getText());

		            } catch (TimeoutException e) {
		                // Validation did not appear – this is OK
		                System.out.println("ℹ No validation shown");
		            }
		        }
  
		        
		        public void waitForToastToDisappear() {

		            try {
		                // If toast appears, wait for it to go
		                WebDriverWait shortWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
		                shortWait.until(ExpectedConditions.visibilityOfElementLocated(
		                        ProjectPageLocator.toastContainer
		                ));

		                wait.until(ExpectedConditions.invisibilityOfElementLocated(
		                        ProjectPageLocator.toastContainer
		                ));

		                System.out.println("ℹ Toast handled");

		            } catch (TimeoutException e) {
		                // No toast shown – OK
		            }
		        }

		        
		        
		    
		        
		        public boolean isElementPresent(By locator) {
		            try {
		                webDriver.findElement(locator);
		                return true;
		            } catch (NoSuchElementException e) {
		                return false;
		            }
		        }
		        
		        
		        
		        
		       

		        
		        public void waitForCressCycleToFinish() {

		            // Health sheet auto-closes
		            wait.until(ExpectedConditions.invisibilityOfElementLocated(
		                    ProjectPageLocator.savehealthsheet
		            ));

		            // Pending list visible again
		            wait.until(ExpectedConditions.visibilityOfElementLocated(
		                    ProjectPageLocator.enabledGenerateCressLinks
		            ));

		            System.out.println("✅ CRESS Pending table refreshed");
		        }

		    /*    public boolean clickNextGenerateCress() {

		        	
		        	
		        	
		            List<WebElement> buttons =
		                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
		                            ProjectPageLocator.enabledGenerateCressLinks
		                    ));

		            for (WebElement btn : buttons) {

		                if (btn.isDisplayed() && btn.isEnabled()) {

		                    jsScrollIntoView(btn);
		                    jsClick(btn);

		                    // Wait for health sheet to open
		                    wait.until(ExpectedConditions.visibilityOfElementLocated(
		                            ProjectPageLocator.savehealthsheet
		                    ));

		                    System.out.println("✅ Clicked next Generate CRESS");
		                    return true;
		                }
		                
		            }

		            return false;
		           
		        }

                */
		  
		      
		        public boolean clickNextGenerateCress() {

		            // 🔹 Ensure modal / toast / table finished updating
		            waitForToastToDisappear();
		      //      waitForCressTableToStabilize();

		            // 🔹 NOW check for enabled Generate CRESS links
		            List<WebElement> buttons =
		                    webDriver.findElements(ProjectPageLocator.enabledGenerateCressLinks);

		            if (buttons.isEmpty()) {
		                System.out.println("ℹ No Generate CRESS available");
		                return false;
		            }

		            WebElement btn = buttons.get(0);

		            try {
		                jsScrollIntoView(btn);
		                jsClick(btn);

		                // Wait for health sheet to open
		                wait.until(ExpectedConditions.visibilityOfElementLocated(
		                        ProjectPageLocator.savehealthsheet
		                ));

		                System.out.println("✅ Clicked Generate CRESS");
		                return true;

		            } catch (Exception e) {
		                System.out.println("❌ Click failed, retry next cycle");
		                return false;
		               
		            }
		        }

		    
		        public boolean isNoCressItemsShown() {
		            try {
		                return webDriver.findElement(
		                        ProjectPageLocator.noCressItemsText
		                ).isDisplayed();
		            } catch (NoSuchElementException e) {
		                return false;
		            }
		        }

		        public void saveMilestoneExpectAlert() throws Throwable {
		            clicksavemilestone();
		            waitForToastToDisappear();   // or alert locator if you have
		        }

		        public void EditProjectExpectAlert() throws Throwable {
		        	  clickSave();
		         //   waitForToastToDisappear();   // or alert locator if you have
		        }
		        
		        
		        
		        
		        public List<String> getSearchResultMilestones() {

		            List<WebElement> cards = wait.until(
		                    ExpectedConditions.presenceOfAllElementsLocatedBy(
		                            ProjectPageLocator.milestoneCardTitles
		                    )
		            );

		            return cards.stream()
		                    .map(e -> e.getText().trim())
		                    .collect(Collectors.toList());
		        }

		        
		        public void waitForToastToDisappear(By toastLocator, int timeout) {
		            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(timeout));
		            wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
		        }       
		        
		        
		        
		        public static String generateProjectName() {
		            return "Autoproject" + get3DigitNumber();
		        }

		        // Output: AP234 (short & clean)
		        public static String generateAbbreviatedName(String projectName) {
		            return "AP" + projectName.replaceAll("\\D", "");
		        }

		        private static String get3DigitNumber() {
		            return String.valueOf((int)(Math.random() * 900) + 100);
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
		                    webDriver.findElement(InitiativePageLocators.nextMonthBtn).click();
		                } else {
		                    webDriver.findElement(InitiativePageLocators.prevMonthBtn).click();
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
		        
		        public void selectStartDates(String inDate) throws Exception {
		            selectDateFromFluentCalendar(
		                    ProjectPageLocator.startDateInput,
		                    inDate
		            );
		        }

		        public void selectEndDates(String outDate) throws Exception {
		            selectDateFromFluentCalendar(
		            		ProjectPageLocator .endDateInput,
		                    outDate
		            );
		        }   
		        
		        
		        public void selectStartDates1(String inDate) throws Exception {
		            selectDateFromFluentCalendar(
		                    ProjectPageLocator.startDateInput1,
		                    inDate
		            );
		        }

		        public void selectEndDates1(String outDate) throws Exception {
		            selectDateFromFluentCalendar(
		            		ProjectPageLocator .endDateInput1,
		                    outDate
		            );
		        }          
		        
		        
		        public void selectStartDates2(String inDate) throws Exception {
		            selectDateFromFluentCalendar(
		                    ProjectPageLocator.fromdate,
		                    inDate
		            );
		        }

		        public void selectEndDates2(String outDate) throws Exception {
		            selectDateFromFluentCalendar(
		            		ProjectPageLocator .todate,
		                    outDate
		            );
		        }          
 
		        
		        
		public void clicklockfreeze(){
		        	
		        }
		        
		        
		        
		        
		        
		        
		        

}
