package Pages;

import Actions.ActionEngine;
import Locators.ProgramPageLocator;
import Locators.ProjectHealthSheetApprovelLocator;
import Locators.ProjectPageLocator;

public class HealthSheetApprovelPage extends ActionEngine{
	
	
	
	 public void clickprogram() throws Throwable {
	  	   
		    click(ProjectHealthSheetApprovelLocator.program, "Program");
		     }
		   

	  public void  clickhealthsheet() throws Throwable {
 	     hover(ProjectHealthSheetApprovelLocator. projehealthsheetapprovel, " projehealthsheetapprovel");
	       click(ProjectPageLocator. projehealthsheetapprovel, " projehealthsheetapprovel");
	  }
	 
	  public void selectprojectname(String projectname) {
          click(ProjectHealthSheetApprovelLocator.projectname, "projectname");
          selectFromList(ProjectHealthSheetApprovelLocator.projectnamelist, projectname, "projectnamelist");
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
}
