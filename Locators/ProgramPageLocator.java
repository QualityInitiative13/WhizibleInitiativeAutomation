package Locators;

import org.openqa.selenium.By;

public class ProgramPageLocator {
	
	
	
	public static By program1 =
            By.xpath("//div[@aria-label='Program']//img[@alt='Program']");

	   
	 public static By program2 =
			  By.xpath("/html[1]/body[1]/div[2]/div[3]/div[2]/p[1]");
			   
			  
    public static By creatnewprogram =
				  By.xpath("//div[normalize-space()='+']");
				   
			
    public static By AbbreviatedName =
			  By.xpath("//input[@id='abbrivatedName']");
         
    
    public static By Programname =
			  By.xpath("//input[@id='ProgramName']");
    
    public static By save =
			  By.xpath("//button[.//span[text()='Save']]");
    
 // Table rows
    public static By initiativeRows =
        By.xpath("//table/tbody/tr");

    // Initiative name cell (relative to row)
    public static By initiativeNameCell =
        By.xpath("./td[1]");

    // Checkbox inside the same row
    public static By initiativeCheckbox =
        By.xpath(".//input[@type='checkbox']");

    // Pagination next button
    public static By nextPage =
        By.xpath("//div[@role='presentation']//button[2]");

    // Save button
    public static By saveButton =
        By.xpath("//button[normalize-space()='Save']");
    
    public static By initiative =
            By.xpath("//div[normalize-space()='Initiatives']");
    
    public static By mapnewinitivative =
            By.xpath("//div[normalize-space()='+']");
    
    
    public static By projectCards =
            By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Program Name')]]");
    
       public static By projectNameInCard =
       	    By.xpath(".//div[starts-with(@aria-label,'Program Name:')]");
       // Edit button inside a card
      public static By editButtonRelative =
             By.xpath(".//button[@aria-label='More Actions']");
      
       // Pagination – Next
       public static By nextPage1 =
               By.xpath("//button[normalize-space()='Next' and not(@disabled)]");
       
       
       public static By editprogram =
               By.xpath("//span[normalize-space()='Edit Program']");
       
       
       public static By saveIni =
               By.xpath("//button[.//span[normalize-space()='Save']]");
       
  
       public static By associated =
               By.xpath("//div[contains(text(),'Associated Projects')]");
       
       
       public static By mapping =
               By.xpath("//button[.//span[normalize-space()='Save Mapping']]");
       
       
       
       public static By yes =
               By.xpath("   //div[@id='fluent-default-layer-host']//span[1]//button[1]");
       
       
    // All program cards
       public static final By programCards =
           By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Program Name')]]");

       // Program name inside a card
       public static final By programNameInCard =
           By.xpath(".//div[starts-with(@aria-label,'Program Name:')]");

       // Delete icon inside the same card
       public static final By deleteIconInCard =
           By.xpath("//button[@aria-label='Delete']");

       // Pagination Next button
      
       // Confirm delete button (dialog)
     //  public static final By confirmDelete =
    //       By.xpath("//button[@aria-label='Delete']");

       public static By initiativeCards =
    		    By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Initiative Name')]]");

       public static By initiativeTitle =
    		    By.xpath(".//div[starts-with(@aria-label,'Initiative Name')]");

       public static By initiativeDeleteIcon =
    		    By.xpath("//button[@aria-label='Delete']");
       
       
       
       
       public static By AssociateCards =
    		    By.xpath("//div[contains(@class,'MuiPaper-root') and .//div[contains(@aria-label,'Project Name:')]]");

    		public static By AssociateTitle =
    		    By.xpath(".//div[starts-with(@aria-label,'Project Name:')]");

    		public static By AssociateDeleteIcon =
    		    By.xpath(".//span[@aria-label='Delete']//button");

    		
    		
    	
    		 public static By AssociatedRows =
    			        By.xpath("//table/tbody/tr");

    			    // Initiative name cell (relative to row)
    			    public static By AssociatedNameCell =
    			        By.xpath("./td[1]");

    			    // Checkbox inside the same row
    			    public static By AssociatedCheckbox =
    			        By.xpath(".//input[@type='checkbox']");

    			    // Pagination next button
    			    public static By nextPage2 =
    			        By.xpath("//div[@role='presentation']//button[2]");
    		
    		
    			    public static By entertitle =
        			        By.xpath("//input[@id='InititiveTitle']");
        		
    			    public static By enterprogramAssociated =
        			        By.xpath("//input[@id='ProjectName']");
    			    
    			    
    			    public static By AssociateEnabledDeleteIcon =
    			            By.xpath(".//span[@aria-label='Delete']//button[not(@disabled)]");
        		
    			    public static By pageIndicator =
    			            By.xpath("//span[contains(text(),'Page') and contains(text(),'of')]");

    			    public static By nextPageBtn =
    			            By.xpath("//button[@aria-label='Go to next page']");
    			    
    			    
    			    public static By click =
        			        By.xpath("//span[@id='businessGroup-option']");
    			    
    			    public static By selectbusiness =
        			        By.xpath("//div[@role='listbox']//button[@role='option']");
    			    
    			    
    			    public static By clickorg=
        			        By.xpath("//span[@id='OrganizationUnit-option']");
    			    
    			    public static By selectorganization =
        			        By.xpath("//div[@role='listbox']//button[@role='option']");
    			    
    			    public static By toastMessage = 
    			            By.xpath("//*[contains(@class,'toast') or contains(text(),'Project')]");
    			    
    			    
    			    public static By clicksearchicon = 
    			            By.xpath("//button[contains(text(),'Search')]");
    			    
    			 
    			    public static By clicksearchicon2 = 
    			            By.xpath(" //button[.//span[text()='Search']]");
    			    
    			    public static By AbbreviatedName1 =
    						  By.xpath("//input[@id='AbbrivatedName']");
    			    
    			    public static By programname1 =
    						  By.xpath("//input[@id='ProgramName']");
    			         
    			    public static By programCardByName(String programName) {
    			        return By.xpath("//div[@aria-label='Program Name: " + programName + "']");
    			    }
    			    
    			    
    			    public static By clickonfreeze =
  						  By.xpath("//button[@aria-label='Freeze']//*[name()='svg']"); 
    			    
    			    
    			    
    			    public static By entercomment =
  						  By.xpath("//div[contains(@class,'MuiOutlinedInput-root')]//textarea[not(@aria-hidden='true')]"); 
    			    
    			    
    			 
    			    public static By clickhistory=
    						  By.xpath("  //button[@aria-label='History']"); 
      			    
      			    
    			  
    			    
    			    
    			    
    			    
    			    
       
}
