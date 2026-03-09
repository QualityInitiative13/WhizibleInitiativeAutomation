package Locators;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProjectPageLocator {

	public static By program =
            By.xpath("//div[@aria-label='Program']//img[@alt='Program']");

    public static By project =
            By.xpath("//p[normalize-space()='Projects']");

    public static By createNewProject =
            By.xpath("//div[normalize-space()='Create New Project']");

    public static By abbreviatedName =
            By.xpath("//input[@id='shortJobTitle']");

    public static By projectName =
            By.xpath("//input[@id='projectName']");

    public static By vendorDropdown =
            By.xpath("//span[@id='vendorID-option']");

    public static By vendorList =
            By.xpath("//span[contains(@class,'ms-Dropdown-optionText')]");

//    public static By startDate =
   //         By.xpath("//div[@id='expectedStartDate-label']");
    
 //   public static By endDate =
  //          By.xpath("//div[@id='expectedEndDate-label']");
   
 //   public static By calendarMonths =
   //         By.xpath("//button[@role='gridcell' and @aria-label]");

   // public static By calendarDays =
    //        By.xpath("//table[@role='grid']//button[contains(@class,'dayButton') and not(@aria-hidden='true')]");
    
    
 // Date inputs
    public static final By startDateInput =
            By.xpath("//div[@id='expectedStartDate-label']");
    
    public static final By startDateInput1 =
            By.xpath("//div[@id='plannedCompletionDate-label']");


    public static final By endDateInput =
            By.xpath("//div[@id='expectedEndDate-label']");
    
    public static final By endDateInput1 =
            By.xpath("//div[@id='actualCompletionDate-label']");


    // Calendar root (only visible AFTER opening)
    public static final By calendarRoot =
            By.xpath("//div[contains(@class,'root-') and .//table[@role='grid']]");

    public static final By yearswitchbutton =
            By.xpath("//button[contains(@class,'currentItemButton')]");

  
    // Navigation arrows
    public static final By prevArrow =
            By.xpath("//button[contains(@title,'Previous year range')]");

    public static final By nextArrow =
            By.xpath("//button[contains(@title,'Next year range')]");
    
    
    public static final By yearGridheader =
    	    By.xpath("//div[@role='grid' and contains(@aria-label,'-')]");


    // Year cells
 //   public static final By yearCells =
  //          By.xpath("//div[@role='grid']//button[@role='gridcell']");
    
    
 // ✅ Dynamic year cell (single target year)
    public static By yearCells(String year) {
        return By.xpath(
            "//div[@role='grid' and contains(@aria-label,'-')]" +
            "//button[@role='gridcell' and normalize-space(text())='" + year + "']"
        );
    }

    // Month cells (Jan–Dec)
    public static final By monthCells =
            By.xpath("//button[@role='gridcell' and @aria-label]");

    // Day cells
    public static final By dayCells =
            By.xpath("//table[@role='grid']//button[contains(@class,'dayButton') and not(@aria-hidden='true')]");

    
   
    
  public static By workHour =
            By.xpath("//input[@id='estimatedEfforts']");

    public static By projectCurrency =
            By.xpath("//span[@id='baseCurrency-option']");

    public static By projectCurrencyList =
            By.xpath("//div[@role='listbox']//button[@role='option']");

    public static By businessGroup =
            By.xpath("//span[@id='businessGroupID-option']");

    public static By businessGroupList =
            By.xpath("//div[@role='listbox']//button[@role='option']");

    public static By organizationUnit =
            By.xpath("//span[@id='locationID-option']");

    public static By organizationUnitList =
            By.xpath("//div[@role='listbox']//button[@role='option']");

    public static By practice =
            By.xpath("//span[@id='projectTypeID-option']");

    public static By practiceList =
            By.xpath("//div[@role='listbox']//button[@role='option']");

    public static By saveButton =
            By.xpath("//button[.//span[normalize-space()='Save']]");

    public static By search =
            By.xpath("//button[@aria-label='Search']");

    public static By searchfinal =
            By.xpath("//button[@type='button' and .//span[normalize-space()='Search']]");
//////////////////////////////////////////////////////////////////////////////////////////////////////
    public static By projectCards =
         By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Project Name')]]");
 
    public static By projectNameInCard =
    	    By.xpath(".//div[starts-with(@aria-label,'Project Name:')]");
    // Edit button inside a card
   public static By editButtonRelative =
          By.xpath(".//button[@aria-label='More Actions']");

 // Card container
  //  public static By projectCardContainer =
  //      By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Project Name')]]");

    // Project name INSIDE card
  //  public static By projectNameInCard =
  //      By.xpath(".//div[starts-with(@aria-label,'Project Name:')]");

    // 3-dot (More Actions) button INSIDE card
 //   public static By cardMoreActionsButton =
 //       By.xpath("//div[@class='container']//div[1]//div[1]//div[1]//div[2]//button[4]//*[name()='svg']");
    
    // Pagination – Next
    public static By nextPage =
            By.xpath("//button[normalize-space()='Next']");
    
    
   
   ///////////////////////////////////////////////////////////////////////////////////////////
    public static By editproject =
            By.xpath("//span[normalize-space()='Edit Project']");
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    public static By definemilestone =
            By.xpath("//div[contains(text(),'Define Milestone')]");
    
    public static By addnew =
            By.xpath("//div[normalize-space()='+']");
    
    public static By milestone =
            By.xpath("//input[@id='mileStone']");
   
    public static By startDate1 =
            By.xpath("//div[@id='plannedCompletionDate-label']");
    
    public static By endDate1 =
            By.xpath("//div[@id='actualCompletionDate-label']");
    
    public static By calendarMonths1 =
            By.xpath("//div[@role='grid']//button[@role='gridcell']");

    public static By calendarDays1 =
            By.xpath("//table[@role='grid']//button[contains(@class,'dayButton')]");
    
    public static By milestonestatus =
            By.xpath("//span[@id='milestoneStatus-option']");
    
    public static By milestonestatuslist =
            By.xpath("//span[contains(@class,'ms-Dropdown-optionText')]");
    
    public static By work =
            By.xpath("//input[@id='plannedEfforts']");
    
    
    public static By savemilestone =
            By.xpath("//button[.//span[normalize-space()='Save']]");
    
  //  public static By crosssavemilestone =
  //          By.xpath("//button[@aria-label='Close']//*[name()='svg']");
    
    
    
    public static By updateactucalcost =
            By.xpath("//div[normalize-space()='Update Actual Cost']");
    
    public static By addnewupdateactucalcost =
            By.xpath("//div[normalize-space()='+']");
    
    
    public static By costcategory =
            By.xpath("//span[@id='costCategory-option']");
    
    public static By costcategorylist =
            By.xpath("//div[@role='listbox']//span[contains(@class,'ms-Dropdown-optionText')]");
    
    public static By amount =
            By.xpath("//input[@id='amount']");
    
    public static By saveupdatecost =
            By.xpath("//button[@type='submit']");
    
    public static By updateprojectsheet =
            By.xpath("//div[contains(text(),'Update Project Health Sheet')]");
    
    public static By addnewupdateproject=
            By.xpath("//div[normalize-space()='+']");
    
  //  public static By Generatecress=
   //         By.xpath("//a[normalize-space()='Generate CRESS']");
    public static By Generatecress =
    	    By.xpath("//a[normalize-space()='Generate CRESS' and not(contains(@class,'disabled'))]");

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
  /*  public static By ccostvalue=
            By.xpath("//tr[.//td[normalize-space()='E - Effort']]//input");
    
    public static By ccostdes=
            By.xpath("//tr[.//td[normalize-space()='E - Effort']]//textarea");
    
    
    public static By rrisk =
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[2]/td[2]/div[1]/input[1]");
    
    public static By rriskdes =
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[2]/td[3]/div[1]/textarea[1]");
    
    
    public static By eeffort =
            By.xpath("//tr[.//td[normalize-space()='E - Effort']]//input");
    
    public static By eeffortdes=
            By.xpath("//tr[.//td[normalize-space()='E - Effort']]//textarea");
    
  
    public static By sche=
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[4]/td[2]/div[1]/input[1]");
    
   
    
    public static By schedes =
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[4]/td[3]/div[1]/textarea[1]");
    
    
    public static By scope =
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[5]/td[2]/div[1]/input[1]");
    
    public static By  scopedes=
            By.xpath("//body[1]/div[3]/div[3]/div[2]/div[5]/table[1]/tbody[1]/tr[5]/td[3]/div[1]/textarea[1]");
    */
    
    public static By cressValueByRowName(String rowName) {
        return By.xpath("//tr[.//td[normalize-space()='" + rowName + "']]//input");
    }

    public static By cressDescByRowName(String rowName) {
        return By.xpath("//tr[.//td[normalize-space()='" + rowName + "']]//textarea");
    }

   public static By  savehealthsheet=
            By.xpath("//button[.//span[normalize-space()='Save']]");
    
  
   
    public static By  milestoneprocess=
            By.xpath("//div[contains(text(),'Milestone Progress')]");
    
    public static By  addmilestoneprocess=
            By.xpath("//div[normalize-space()='+']");
    
    public static By  updatemilestone=
            By.xpath("//tbody/tr[1]/td[3]/a[1]");
    
    public static By  percentageprocess=
            By.xpath("//input[@type='text']");
    
    public static By  savemilestoneprocess=
            By.xpath("//button[.//span[normalize-space()='Save']]");
    
    public static By  manageHealth=
            By.xpath("//span[contains(text(),'Manage Health')]");
    
      public static By  saveandapprove=
         By.xpath("//button[contains(@class,'ms-Button') and .//span[contains(text(),'Save and Send')]]");
    
      public static By  projehealthsheetapprovel=
    	         By.xpath("//p[normalize-space()='Project Health Sheet Approval']");
      
      public static By projectname =
              By.xpath("//span[normalize-space()='Select Project']");

      public static By projectnamelist =
              By.xpath("//div[@role='listbox']//button[@role='option']");
      
      
      public static By viewdetails =
              By.xpath("//span[contains(text(),'View Details')]");
      
      public static By rationale =
              By.xpath("//textarea[@placeholder='Enter Rationale']");

      public static By approve =
              By.xpath("//button[.//span[normalize-space()='Approve']]");
      
      public static By managedefinemilestone =
              By.xpath("//button[.//span[normalize-space()='Manage']]");
      
      
      public static By updatefield =
              By.xpath("//input[@id='mileStone']");
      
      public static By history =
              By.xpath("//button[normalize-space()='History']");
      
      public static By milestoneenter =
              By.xpath("//input[@id='Milestone']");
      
      public static By milestoneCards =
    		    By.xpath("//div[contains(@class,'MuiPaper-root')]");

      
      public static By milestoneTitle =
    		    By.xpath(".//p | .//div[@aria-label]");

      
      public static By milestoneDeleteIcon =
    		    By.xpath(".//span[@aria-label='Delete']//button | .//button[@aria-label='Delete']");
      
      
      
      

      
      public static By costTitle =
    		    By.xpath(".//*[contains(text(),'Cost Category')]");
      
      
   // cost card
      public static By costCards =
          By.xpath("//div[contains(@class,'MuiPaper-root') and .//button[@aria-label='Delete']]");

      // delete icon inside card
      public static By costDeleteIcon =
          By.xpath(".//button[@aria-label='Delete']");

     
      public static By searchhealthsheet =
  		    By.xpath("//span[normalize-space()='Search']");
      
      
      public static By fromdate =
    		    By.xpath("//div[@id='fromDate-label']");
        
      
      public static By todate =
    		    By.xpath("//div[@id='toDate-label']");
        
      public static By searchactucalcost =
    		    By.xpath("//span[normalize-space()='Search']");
      
      
      public static By searchactucalcostfinal =
    		    By.xpath("//button[.//span[normalize-space()='Search']]");
      
      
    ///////////////////////////////////////////////////////////// //////////////////////////////////////////////////////   
      
      public static By costCardsd =
    	        By.xpath("//button[@aria-label='Delete']/ancestor::div[@style[contains(.,'border-radius: 8px')]]");

    	public static By costCategoryd =
    	        By.xpath(".//span[@aria-label]");

    	public static By amountd =
    	        By.xpath(".//span[contains(normalize-space(.),'Amount')]");

    	public static By deleteIcond =
    	        By.xpath(".//button[@aria-label='Delete']");

    	public static By confirmDeleted =
    	        By.xpath("//div[@role='dialog']//button[contains(text(),'Delete')]");
    	
    	
    	
    	 public static By startDate =
    	            By.xpath(".//span[contains(normalize-space(),'Start Date')]");

    	    // End Date span inside card
    	    public static By endDate =
    	            By.xpath(".//span[contains(normalize-space(),'End Date')]");

    	    // Card container (root)
    	    public static By cardContainer =
    	            By.xpath("./ancestor::div[contains(@style,'border-radius')]");

    	    // Delete button (may be disabled)
    	    public static By deleteButton =
    	            By.xpath(".//button[@aria-label='Delete']");

    	    // Disabled delete button (business rule)
    	    public static By disabledDeleteButton =
    	            By.xpath(".//button[@aria-label='Delete' and @disabled]");
    	
    	    public static By deleteWrapper =
    	            By.xpath(".//span[@aria-label and contains(@aria-label,'Delete')]");
 
    	    
    	    public static By enabledDeleteButton =
    	            By.xpath(".//span[@aria-label='Delete']//button[not(@disabled)]");

    	 // Close icon of CRESS sheet (adjust if your aria-label differs)
    	    public static By closeCress =
    	            By.xpath("//button[contains(@aria-label,'Close')]");

    	    // Any mandatory validation message
    	    public static By mandatoryError =
    	            By.xpath("//*[contains(text(),'Mandatory') or contains(text(),'required')]");

    	    public static By toastContainer =
    	            By.xpath("//div[contains(@class,'Toastify__toast-container')]");

    	 //   public static By enabledGenerateCressLinks =
    	     //       By.xpath("//a[normalize-space()='Generate CRESS' and not(contains(@class,'disabled'))]");
    	    public static By enabledGenerateCressLinks =
    	            By.xpath("//tbody//a[normalize-space()='Generate CRESS']");

    	    public static By noCressItemsText =
    	            By.xpath("//*[contains(text(),'There are no items to show')]");
    	    
    	
    	    
    	    public static By milestoneCardTitles =
            By.xpath("//span[starts-with(@aria-label,'Milestone:')]");


    	    public static By toastLocator =
    	            By.xpath("//div[contains(@class,'Toastify__toast')]");


    	    
    	    
}
      
      
      
      
      
      
      
      



