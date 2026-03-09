package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Base.BaseTest;
import DataProvider.ProgramDataProvider;
import DataProvider.ProjectDataProvider;
import Locators.ProgramPageLocator;
import Pages.ProgramPage;
import Utils.ProgramContext;

public class ProgramTest extends BaseTest {
	
	
    
	 /**
	     * TC_001_CreateProgram
	     *
	     * Test Case: CreateProgram
	     *
	     * Steps:
	     * 1. Click on the Program 
	     * 2. Click on the Program Tab
	     * 3. Click on the Search button
	     * 4. Click on the Nature of Initiative dropdown field and select the values from the excel
	     * 5. Click on the Initiative Code text field and enter a Initiative code which is mentioned in the excel field
	     * 6. Click on the Initiative Status dropdown field and select the values from the excel
	     * 7. Click on the Search button
	     * 8. Click on the Add child Link
	     * 9. Just Click on the Child Initiative text field dont enter any value
	     * 10. Click on the Relation Type dropdown field and select the value from the excel
	     * 11. Click on the Initiative Title text field and enter the value from the excel
	     * 12. Click on the Apply button
	     * 13. Select the checkbox
	     * 14. Click on the Save button
	     * 15. Click on the Add child button
	     * 16. Expand the Initiative
	     * 17. Verify the added child initiative displayed after expand the parent initiative
	     */ 

	 @Test(priority=1 ,enabled=true)
	      public void creatProgram() throws Throwable {
		 
	
	 String abbreviatedName = ProgramPage.generateAbbreviatedName();
      String  programName = ProgramPage.generateProgramName();
	    	  
	    	  ProgramPage programpage = new  ProgramPage();
	    	  
	    	  closeNotificationPopupIfPresent();
	          Thread.sleep(3000);
	          
	    	  programpage.clickprogram1();
	    	  Thread.sleep(1000);
	    	  programpage.clickprogram2();
	    	  
	    	  programpage.clicknewprogram();
	    
	    	  Thread.sleep(3000);
	    	 programpage.enterAbbreviatedName(abbreviatedName);
	    	 
	      	  log.info("Enter abbrivated name");
	    	 Thread.sleep(3000);
	    	  programpage.enterProgramName(programName);
	    	
	    	  Thread.sleep(3000);
	    	  log.info("Enter program name");
              programpage.businessgroup();
              Thread.sleep(1000);
              programpage.oragnizationunit();
	    	  
	    	  Thread.sleep(1000);
	    	  programpage.clicksave();
	    	  programpage.clickinitiative();
	    	  
			    Thread.sleep(1000);
	          programpage.clicknewmapinitiative();
	            Thread.sleep(1000);
	            
	            programpage.clicksaveIni();
	            Thread.sleep(10000);
	       
	         String selectedInitiative = programpage.selectRandomInitiative();
	            Thread.sleep(5000);
              programpage.clicksaveIni();
              
       
              Thread.sleep(5000);
              programpage.entertitle(selectedInitiative);
              Thread.sleep(3000);
             programpage.deleteInitiativeByTitle(selectedInitiative);
 
             programpage.clickassociated();
             programpage.clickmapassociated();  
       
              Thread.sleep(5000);
  
              String mappedProject = programpage.mapAssociatedRandomly();

              programpage.deleteAssociatedProjectWithPagination(mappedProject);
	 }  
	 
	 //verify the snooze function 
	
	 @Test(priority=2 ,enabled=true
	          )
      public void verifyfreeze() throws Throwable {
		 
   	  ProgramPage programpage = new  ProgramPage();
   	  
   	closeNotificationPopupIfPresent();
    Thread.sleep(3000);
    
   	  programpage.clickprogram1();
   	  Thread.sleep(1000);
   	  programpage.clickprogram2();
   	  Thread.sleep(1000);
   	  
   	  programpage.clicknewprogram();
   	  
   	  
     String  abbreviatedName = ProgramPage.generateAbbreviatedName();
     String  programName = ProgramPage.generateProgramName();
   	  
     programpage.enterAbbreviatedName(programName);
     Thread.sleep(3000);
     
	  programpage.enterProgramName(abbreviatedName);
	  Thread.sleep(3000);
 
	  programpage.clicksave();
	  
	  programpage.clicklockfreeze();
	  
      String comment=  programpage.generateAutoFreezeComment();
	 
	  programpage.entercomment(comment);
	  Thread.sleep(3000);
	  programpage.clickSaveButton();

	  System.out.println("sucessfully freeze the program");
}   
	
	 
//delete the program on cardview	 
	 
	 
 @Test(priority=3 ,enabled=true)
     public void DeleteProgram() throws Throwable {
   	  
   	  
   	  ProgramPage programpage = new  ProgramPage();
   	closeNotificationPopupIfPresent();
    Thread.sleep(3000);
   	  programpage.clickprogram1();
   	  Thread.sleep(1000);
   	  programpage.clickprogram2();
   	  Thread.sleep(1000);
   	  
     String   programname= programpage.getProgramName();
   	   
      programpage.deleteProgramWithPagination(programname);

}   	 
	 

// verify the history for prograname and abbrivatename
 
 @Test(priority=4 ,enabled=true)
 public void verifyhistoryprogram() throws Throwable {
	 ProgramPage programpage = new  ProgramPage();
  	  
	 closeNotificationPopupIfPresent();
     Thread.sleep(3000);
  	  programpage.clickprogram1();
  	  Thread.sleep(1000);
  	  programpage.clickprogram2();
  	  Thread.sleep(1000);
  	  
  	  programpage.clicknewprogram();
  	  
  	  
    String  abbreviatedName = ProgramPage.generateAbbreviatedName();
    String  programName = ProgramPage.generateProgramName();
  	  
     programpage.enterAbbreviatedName(programName);
     Thread.sleep(3000);
    
	  programpage.enterProgramName(abbreviatedName);
	  Thread.sleep(3000);

	  programpage.clicksave();
	  
	  Thread.sleep(3000);
	  programpage.clearField(ProgramPageLocator.AbbreviatedName,"AbbreviatedName" );
	  Thread.sleep(3000);
	  programpage.clearField(ProgramPageLocator.Programname,"Programname" );
	  Thread.sleep(3000);
	  
	  String  abbreviatedName1 = ProgramPage.generateAbbreviatedName();
	    String  programName1 = ProgramPage.generateProgramName();
	  programpage.enterAbbreviatedName(programName1);
	    Thread.sleep(3000);
	    
      programpage.enterProgramName(abbreviatedName1);
		Thread.sleep(3000);

     	programpage.clicksave();
    	Thread.sleep(3000);
     	programpage.clickhistory();
    	Thread.sleep(3000);
     	programpage.verifyAndPrintShowHistory();
 }
 
 
 //verify the mapInitiative   	  
//TC_001 validaton for mapinitiative without checking checkbox
 //TC_002  success for map initiative 
 //TC_003  search initiative
 @Test(priority=3 ,enabled=false,//dependsOnMethods = "verifysearch()",
	dataProvider = "Initiative",
	   dataProviderClass = ProgramDataProvider.class)
	public void Initiatives(String program,String abbrivatename)throws Throwable {

  ProgramPage programpage = new ProgramPage();
            Thread.sleep(1000);
		  
          if (!programpage.isEditProgramPageOpen()) {
                programpage.navigateToEditProgram(program);
                }
			    programpage.clickinitiative();
			    Thread.sleep(1000);
	            programpage.clicknewmapinitiative();
	            Thread.sleep(1000);
	            programpage.clicksaveIni();
	            Thread.sleep(10000);
	       
	            
	            String selectedInitiative = programpage.selectRandomInitiative();
	            Thread.sleep(5000);
                programpage.clicksaveIni();
                Thread.sleep(5000);
                programpage.entertitle(selectedInitiative);
                Thread.sleep(3000);
               programpage.deleteInitiativeByTitle(selectedInitiative);
   
			}
  
	 
	 //verify the MapAssociatedProject
	 //TC__001  validation for mapassociated without checking checkbox 
	 //TC__002   success for mapassociated 

	 @Test(    priority = 4,enabled=false,dataProvider = "Associated",
	          dataProviderClass = ProgramDataProvider.class)
			public void AssociatedProjects(String program) throws Throwable {

			    ProgramPage programpage = new ProgramPage();

			
			    if (!programpage.isEditProgramPageOpen()) {
                    programpage.navigateToEditProgram(program);
                }

			      programpage.clickassociated();
	               programpage.clickmapassociated();  
	         
	                Thread.sleep(5000);
	    
	                String mappedProject = programpage.mapAssociatedRandomly();

	                System.out.println("Mapped Project = " + mappedProject);
	                
	                
	              Assert.assertTrue(
	                       programpage.isAssociatedProjectDisplayed(mappedProject),
	                      "Mapped project is NOT displayed in Associated section"
	              );
	       
		       
	 }
			  
			
	  // verify the delete program on card view not mapped
	 @Test(priority=5 ,enabled=false)
	      public void DeleteProgram1() throws Throwable {
	    	  
	    	  
	    	  ProgramPage programpage = new  ProgramPage();
	    	  programpage.clickprogram1();
	    	  Thread.sleep(1000);
	    	  programpage.clickprogram2();
	    	  Thread.sleep(1000);
	     //     programpage.deleteProgramWithPagination(programName);
	          programpage.waitForOverlayToDisappear();
	 }   
	    	
        //    programpage.deleteAssociatedProjectWithPagination(AssociatedName);
            	  
	      	 
	 
		 
		 
}
