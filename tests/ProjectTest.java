package tests;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



import Base.BaseTest;
import DataProvider.ProjectDataProvider;
import Locators.ProjectPageLocator;
import Pages.ProgramPage;
import Pages.ProjectPage;
import org.testng.Assert;

public class ProjectTest extends BaseTest {  
	

	//verify the create project   //Test steps
	// TC__001 alert - abbriviated nmae 
	// TC__002alert - project name 
	// TC__003alert - vendor 
	// TC_004alert - startdate
	// TC__005alert - enddate
	// TC__006 alert - hour 
	// TC__007 alert  currency
	// TC__008 alert  bussinessgroup
	// TC__008 alert  organization unit
	// TC__009 alert  bussinessgroup
	// TC__0010 aler  pratice
	// TC__0011 alert  success
	    @Test(priority=1 ,enabled=true,dataProvider = "Projectmodule",
	          dataProviderClass = ProjectDataProvider.class)
	    public void createProjectTest(
	            String startyear,
	            String endyear,
	            String workHours,
	            String milestone,
	            String  milestonestatus,                   //  String syear,String eyear
	            String work,String update,
	            String c1, String c1Value, String c1Desc,
		        String c2, String c2Value, String c2Desc,
		        String c3, String c3Value, String c3Desc,
		        String c4, String c4Value, String c4Desc,
		        String c5, String c5Value, String c5Desc
	           )
	 
	                         throws Throwable {
	        ProjectPage projectPage = new ProjectPage();
	        projectPage.clickprogram();
            projectPage.clickproject();
            projectPage.clickcreatnewproject();
            
            
            String projectName = ProjectPage.generateProjectName();        // Autoproject234
	        String abbreviatedName = ProjectPage.generateAbbreviatedName(projectName); // AP234

	        projectPage.clickSave();                          //alert message
	        Thread.sleep(3000);
	        projectPage.enterAbbreviatedName(abbreviatedName);
	        projectPage.clickSave();                          //alert message
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.enterProjectName(projectName); 
	        Thread.sleep(3000);
	        projectPage.clickSave();                         //alert message
	        Thread.sleep(3000);                                                
	        projectPage.selectVendor();
	        Thread.sleep(3000);
	  //      projectPage.clickSave();                         //alert messsage
	        Thread.sleep(3000);
	        projectPage.selectStartDates(startyear);   
	   
	  //      projectPage.clickSave();                     //   alert message
	          Thread.sleep(3000);
	        projectPage.selectEndDates(endyear);
	        
	        Thread.sleep(3000);
	        projectPage.clickSave();                       //alert message
	        projectPage.enterWorkHours(workHours);
	        
	        Thread.sleep(1000);
	        projectPage.clickSave();                      //alert message
	        Thread.sleep(3000);
	        projectPage.selectProjectCurrency();
	        
	        projectPage.clickSave();
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.selectBusinessGroup();
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.clickSave();                    //alert message
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.selectOrganizationUni();
	        
	        projectPage.clickSave();
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.selectPractice();
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        Thread.sleep(3000);
	       projectPage.clickSave();                  //alert success
	       projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	       
	          projectPage.clickdefinemilestone();
	         projectPage.clickaddnew();
	         projectPage.saveMilestoneExpectAlert(); // verify the alert 
	         
	         projectPage.clickaddnew();
	         projectPage.milestone(milestone);
	         Thread.sleep(3000);
	        projectPage.selectStartDates1(startyear);
	        Thread.sleep(3000);
	        projectPage.selectEndDates1(endyear);
	        Thread.sleep(2000);
	        projectPage.saveMilestoneExpectAlert();   // verify the altert
	        
	        projectPage.waitForToastToDisappear(
	                ProjectPageLocator.toastLocator, 10);
	        
	        projectPage.selectmilestonestatus(milestonestatus);
	        Thread.sleep(3000);
	        
	        projectPage.saveMilestoneExpectAlert();;    //verify the altert
	        Thread.sleep(3000);
	        projectPage.enterwork(work);
	        Thread.sleep(3000);
	        
	        projectPage.saveMilestoneExpectAlert();   //verify the alert
	        Thread.sleep(3000);
	        projectPage. closeUpdateHealthSheet();
	        Thread.sleep(3000);
	        projectPage.clickmanagemile();
	        Thread.sleep(3000);
	        projectPage.saveMilestoneExpectAlert();  // verify alert 
		     projectPage.clearAndEnterText(ProjectPageLocator.updatefield, update);
		     
		     projectPage.saveMilestoneExpectAlert();    //update successfully
		      Thread.sleep(3000);
		     projectPage. closeUpdateHealthSheet();
		      Thread.sleep(3000);
	         projectPage.clickSerach();
	         
	        // projectPage.entersearchmilestone(searchmilstone);
	         projectPage.entersearchmilestone(update);
	         projectPage.clickSerachfinal();
	       

	        System.out.println("smoke and sanity passes for define milestone");
	        
	        List<String> results = projectPage.getSearchResultMilestones();

	        Assert.assertTrue(
	                results.size() > 0,
	                "❌ No results shown for search: " + update
	        );

	        for (String actual : results) {
	            Assert.assertTrue(
	                    actual.equalsIgnoreCase(update),
	                    "❌ Search mismatch | Expected: "
	                            + update + " | Found: " + actual
	            );
	        }        
	       System.out.println("Smoke and Santiy and Regression passes for create project");
	       
	  /*   projectPage.clickupdateproject();
		    projectPage.clickaddnewupdateproject();
		    projectPage.clickgeneratecress();
		    Thread.sleep(5000);
		    projectPage.clicksavesheet();
		    Thread.sleep(5000);
		    projectPage.fillCressRow(c1, c1Value, c1Desc);
		    Thread.sleep(5000);
		    projectPage.clicksavesheet();
		    Thread.sleep(5000);
		    projectPage.fillCressRow(c2, c2Value, c2Desc);
		    Thread.sleep(3000);
		    projectPage.clicksavesheet();
		    Thread.sleep(5000);
		    projectPage.fillCressRow(c3, c3Value, c3Desc);
		    Thread.sleep(5000);
		    projectPage.clicksavesheet();
		    Thread.sleep(5000);
		    projectPage.fillCressRow(c4, c4Value, c4Desc);
		    Thread.sleep(5000);
		    projectPage.clicksavesheet();
		    Thread.sleep(5000);
		    projectPage.fillCressRow(c5, c5Value, c5Desc);
		      Thread.sleep(5000);
		    projectPage.clicksavesheet();
		    Thread.sleep(3000);
		    projectPage.closeUpdateHealthSheet();
		    Thread.sleep(3000);
		    projectPage.clicksearchhealthsheet();
		    
		     projectPage.selectStartDates2(startyear);
		      Thread.sleep(1000);
		      projectPage.selectEndDates2(endyear);
		      projectPage.clicksearchhealthsheet();  
	    */ 
	    }
      
	   
	 // verify the define milestone & Pagination   //run alone
        //TC__001 altert for milestone
      // Tc __002 altert for startdate
      // Tc __003 alter for enddate
      //TC__004 altert for milestoneprocess
    // Tc_005 altert for work hour
    // Tc_006  sucess
	// Tc_007 alert for update sucessfully 
   // TC_008  alert edit and update mile stone
	//Tc_007 search
	  @Test(priority=2,enabled=false,dataProvider = "Projectmodule",
	          dataProviderClass = ProjectDataProvider.class,groups= {"smoke","sanity"})
	 public void Definemilestone(String Projectname,String milestone,String syear,String startmonths,String startdays,String eyear,String endmonths,String endays,String milestonestatus,String work,String update ,String searchmilstone) throws Throwable {
	        
	        // 1️⃣ Create Page Object
	        ProjectPage projectPage = new ProjectPage();
	      
	        // 2️⃣ Navigate to Projects page
	         Thread.sleep(1000);
	      
	     //  projectPage.navigateToEditProject(Projectname);
	         
                if (!projectPage.isEditProgramPageOpen()) {
                	 projectPage.navigateToEditProject(Projectname);
                }

	         projectPage.clickdefinemilestone();
	         projectPage.clickaddnew();
	         projectPage.saveMilestoneExpectAlert(); // verify the alert 
	         
	         projectPage.clickaddnew();
	         projectPage.milestone(milestone);
	         Thread.sleep(3000);
	        projectPage.selectStartDate1(syear,startmonths,startdays);
	        Thread.sleep(3000);
	        projectPage.selectEndDate1(eyear,endmonths, endays);
	        Thread.sleep(2000);
	        projectPage.saveMilestoneExpectAlert();   // verify the altert
	        
	        
	        projectPage.selectmilestonestatus(milestonestatus);
	        Thread.sleep(3000);
	        
	        projectPage.saveMilestoneExpectAlert();;    //verify the altert
	        Thread.sleep(3000);
	        projectPage.enterwork(work);
	        Thread.sleep(3000);
	        
	        projectPage.saveMilestoneExpectAlert();   //verify the alert
	        Thread.sleep(3000);
	        projectPage. closeUpdateHealthSheet();
	        Thread.sleep(3000);
	        projectPage.clickmanagemile();
	        Thread.sleep(3000);
	        projectPage.saveMilestoneExpectAlert();  // verify alert 
		     projectPage.clearAndEnterText(ProjectPageLocator.updatefield, update);
		     
		     projectPage.saveMilestoneExpectAlert();    //update successfully
		      Thread.sleep(3000);
		     projectPage. closeUpdateHealthSheet();
		      Thread.sleep(3000);
	         projectPage.clickSerach();
	         
	        // projectPage.entersearchmilestone(searchmilstone);
	         projectPage.entersearchmilestone(update);
	         projectPage.clickSerachfinal();
	       

	        System.out.println("smoke and sanity passes for define milestone");
	        
	        List<String> results = projectPage.getSearchResultMilestones();

	        Assert.assertTrue(
	                results.size() > 0,
	                "❌ No results shown for search: " + update
	        );

	        for (String actual : results) {
	            Assert.assertTrue(
	                    actual.equalsIgnoreCase(update),
	                    "❌ Search mismatch | Expected: "
	                            + update + " | Found: " + actual
	            );
	        }        
	 }
	    
	//verify the upadteprojectHealthsheetsheetb     
			 //Tc__001 altert - enter cost 
			 //Tc__001 altert - enter risk
			 //Tc__001 altert - enter effort
			 //Tc__001 altert - enter schedule
			 //Tc__001 altert - enter scope
			 //Tc__001 altert - success
			 @Test(
				        priority = 3,
				        dataProvider = "Projectmodule",
				        dataProviderClass = ProjectDataProvider.class,enabled=false,
				        groups = {"smoke","sanity"}
				)
				public void updateprojecthealthsheet(String projectname,
				        String c1, String c1Value, String c1Desc,
				        String c2, String c2Value, String c2Desc,
				        String c3, String c3Value, String c3Desc,
				        String c4, String c4Value, String c4Desc,
				        String c5, String c5Value, String c5Desc,String syear, String startmonths,String startdays,
					      String  eyear, String endmonths,  String enday
				) throws Throwable {

				    ProjectPage projectPage = new ProjectPage();

				    if (!projectPage.isEditProgramPageOpen()) {
				        projectPage.navigateToEditProject(projectname);
				    }

				    projectPage.clickupdateproject();
				    projectPage.clickaddnewupdateproject();

				    String[][] cressData = {
				            {c1, c1Value, c1Desc},
				            {c2, c2Value, c2Desc},
				            {c3, c3Value, c3Desc},
				            {c4, c4Value, c4Desc},
				            {c5, c5Value, c5Desc}
				    };

				    projectPage.generateAndFillAllCress(cressData);
				    Thread.sleep(3000);
				    projectPage.clicksearchhealthsheet();
				    projectPage.selectStartDate2(syear,startmonths,startdays);
				    Thread.sleep(1000);
				    projectPage.selectEndDate2(eyear,endmonths, enday);
				    projectPage.clicksearchhealthsheet();  
		
				    System.out.println("✅ All CRESS generated and filled successfully");
				}

			 
           //verify the updateactucalcost     
		  // TC__001 alert milestone should not be blank
		  // TC__002  alert amount should not be blank
		  // TC__003   sucess
		  // TC__004   search-  dropdownname and start and end date of project
		  @Test(priority=4,enabled=false ,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		      public void updateactucalcost(String Projectname,String costcategory,String amount,
		    		  String costcategory1,String syear,String startmonths,String startdays,String eyear, String endmonths, String enday) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		      
		        // 2️⃣ Navigate to Projects page
		        if (!projectPage.isEditProgramPageOpen()) {
               	 projectPage.navigateToEditProject(Projectname);
               }
		          projectPage.clickupdateactualcost();
		           projectPage.clickaddnewupdateactucalcost();
		           projectPage.clicksaveupdatecost();
		          projectPage.selectcostcategoryr(costcategory);
		          System.out.println(costcategory);
		          projectPage.clicksaveupdatecost();
		           projectPage.enteramount(amount);
		        projectPage.clicksaveupdatecost();
		        Thread.sleep(1000);
		       projectPage.closeUpdateHealthSheet();
                   Thread.sleep(5000);
                 projectPage.clicksearchactucalcost();       //search
			     projectPage.selectcostcategoryr(costcategory1);
			     Thread.sleep(3000);
			    projectPage.selectStartDate2(syear,startmonths,startdays);
			    projectPage.selectEndDate2(eyear,endmonths, enday);
			    projectPage.clicksearchactucalcostfinal();
		   //
		    
		 }
		  
		  
		  // verify the milestone process      //run alone
			// TC__001 alert for percent should not be blank
			// TC__002 milestone process save sucessfully
		  @Test(priority=8,enabled=false,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		  public void milestoneprocess(String projectname,String percent) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		      
		        // 2️⃣ Navigate to Projects page
		        if (!projectPage.isEditProgramPageOpen()) {
	               	 projectPage.navigateToEditProject(projectname);
	               }
		        projectPage.clickmilestoneprocess();
		        projectPage.clickaddnewmilestone();
		        projectPage.clickupdatemilestone();
		        projectPage.clearField(ProjectPageLocator.percentageprocess, percent);
		        Thread.sleep(3000);
		        projectPage.clicksavemilestoneprocess();
		        Thread.sleep(3000);
		        projectPage.entepercnetage(percent);
		        Thread.sleep(3000);
		        projectPage.clicksavemilestoneprocess();
		        Thread.sleep(2000);   
		        projectPage. closeUpdateHealthSheet();
		 }
		  
		   
		  //verify the project  health sheet aprrovel 
		  //TC_001 alert rationale 
		  //TC 001 approved successfully
		  @Test(priority=15,enabled=false,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		  public void aprrovehealthsheet (String projectname,String projectnamedrop,String rationale) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		      
		        // 2️⃣ Navigate to Projects page
		        // 2️⃣ Navigate to Projects page
		        if (!projectPage.isEditProgramPageOpen()) {
	               	 projectPage.navigateToEditProject(projectname);
	               }
		        projectPage.clickupdateproject();
		        projectPage.clickmanagehealth();
                Thread.sleep(1000);
		        projectPage.clicksaveandapprove();
		        projectPage.closeUpdateHealthSheet();
		        Thread.sleep(3000);
		        projectPage.clickprogram();
		       Thread.sleep(2000);
		        projectPage.clickhealthsheet();
		        Thread.sleep(3000);
		       projectPage.selectprojectname(projectnamedrop);
		       projectPage.clickviewdetails();
		       projectPage.clickapprove();
		       Thread.sleep(3000);
		        projectPage.enterrationale(rationale);
		        Thread.sleep(1000);
		        projectPage.clickapprove();
		    
		 }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//********************************************************************************************************************************** 
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@		

		  
           //verify the delete for updateprojecthealthsheet 
		  
		  
		  @Test(priority=17,enabled=false,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		      public void updathealthsheetdelete(String Projectname,String start ,String end) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		      
		        // 2️⃣ Navigate to Projects page
		      projectPage.clickprogram();
		      projectPage.clickproject();
		      Thread.sleep(1000);
		         projectPage.clickEditOnProjectCard(Projectname);
		          projectPage.clickeditproject();
		          projectPage.clickupdateproject();
		          Thread.sleep(1000);
		          projectPage.deleteCardByStartAndEndDate(start, end);
		         
		  }
		  
		  
		   //verify the delete for definemilestone 
		  @Test(priority=18,enabled=false,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		      public void definemilestonedelete(String Projectname,String deletemilstone) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		        
		        projectPage.clickprogram();
			      projectPage.clickproject();
			      Thread.sleep(1000);
			         projectPage.clickEditOnProjectCard(Projectname);
			          projectPage.clickeditproject();
			          projectPage.clickdefinemilestone();
		        projectPage.deleteMilestoneWithPagination(deletemilstone);
		      
		    
		         
		  } 
		  
		  
		// verify the  delete updateactucalcost
		  @Test(priority=19,enabled=false,dataProvider = "Projectmodule",
		          dataProviderClass = ProjectDataProvider.class)
		      public void updateactucalcostdelete(String Projectname, String costcategoryd,  String amountd) throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		        
		        projectPage.clickprogram();
			      projectPage.clickproject();
			      Thread.sleep(1000);
			         projectPage.clickEditOnProjectCard(Projectname);
			          projectPage.clickeditproject();
			          projectPage.clickdefinemilestone();
			          projectPage.clickupdateactualcost();
			         
			      projectPage.deleteCostByCategoryAndAmount(costcategoryd, amountd);
		      
		    
		         
		  } 
		  
		  
		//verify the edit project
		 @Test(enabled=false)
		    public void editproject() throws Throwable {
		        
		        // 1️⃣ Create Page Object
		        ProjectPage projectPage = new ProjectPage();
		      
		        // 2️⃣ Navigate to Projects page
		        projectPage.clickprogram();
		        Thread.sleep(1000);
		        projectPage.clickproject();
		        Thread.sleep(1000);
		        projectPage.clickEditOnProjectCard("Final Test Project");
		        projectPage.clickeditproject();
		        
		 }   
	 
		 
		//verify the upadteprojectHealthsheetsheetb     
		 //Tc__001 altert - enter cost 
		 //Tc__001 altert - enter risk
		 //Tc__001 altert - enter effort
		 //Tc__001 altert - enter schedule
		 //Tc__001 altert - enter scope
		 //Tc__001 altert - success
	/*	 @Test(priority = 4, enabled = true,
			      dataProvider = "Projectmodule",
			      dataProviderClass = ProjectDataProvider.class,
			      groups = {"smoke","sanity"})
			public void updateprojecthealthsheet(
			        String projectname,

			        String c1, String c1Value, String c1Desc,
			        String c2, String c2Value, String c2Desc,
			        String c3, String c3Value, String c3Desc,
			        String c4, String c4Value, String c4Desc,
			        String c5, String c5Value, String c5Desc, String syear, String startmonths,String startdays,
			      String  eyear, String endmonths,  String enday
			) throws Throwable {

			    ProjectPage projectPage = new ProjectPage();

			    if (!projectPage.isEditProgramPageOpen()) {
                	 projectPage.navigateToEditProject(projectname);
                }
                Thread.sleep(1000);
			    projectPage.clickupdateproject();
			    projectPage.clickaddnewupdateproject();
			    projectPage.clickgeneratecress();
			    Thread.sleep(5000);
			    projectPage.clicksavesheet();
			    Thread.sleep(5000);
			    projectPage.fillCressRow(c1, c1Value, c1Desc);
			    Thread.sleep(5000);
			    projectPage.clicksavesheet();
			    Thread.sleep(5000);
			    projectPage.fillCressRow(c2, c2Value, c2Desc);
			    Thread.sleep(3000);
			    projectPage.clicksavesheet();
			    Thread.sleep(5000);
			    projectPage.fillCressRow(c3, c3Value, c3Desc);
			    Thread.sleep(5000);
			    projectPage.clicksavesheet();
			    Thread.sleep(5000);
			    projectPage.fillCressRow(c4, c4Value, c4Desc);
			    Thread.sleep(5000);
			    projectPage.clicksavesheet();
			    Thread.sleep(5000);
			    projectPage.fillCressRow(c5, c5Value, c5Desc);
			      Thread.sleep(5000);
			    projectPage.clicksavesheet();
			    Thread.sleep(3000);
			    projectPage.closeUpdateHealthSheet();
			    Thread.sleep(3000);
			    projectPage.clicksearchhealthsheet();
			      projectPage.selectStartDate2(syear,startmonths,startdays);
			      Thread.sleep(1000);
			      projectPage.selectEndDate2(eyear,endmonths, enday);
			      projectPage.clicksearchhealthsheet();  
			    System.out.println("✅ Smoke & sanity passed for updateprojecthealthsheet");
			}
         */
		 
		//verify the modified define milestone  and  verify searchfuntionality  
		  
		  //Tc__001 altert --milestone update sucessfully without modifying 
		  // TC 002  alert -updated sucessfully 
		  // TC 003  search milestone
		
		  
			 @Test(priority=3,enabled=false,dataProvider = "Projectmodule",
			          dataProviderClass = ProjectDataProvider.class)
			    public void modifyandsearchdefinemilestone(String projectname,String update,String searchmilestone,String deletemilestone) throws Throwable {
			        
			        // 1️⃣ Create Page Object
			        ProjectPage projectPage = new ProjectPage();
			        
			        // 2️⃣ Navigate to Projects page
			        projectPage.navigateToEditProject(projectname);
				       projectPage.clickdefinemilestone();
				       projectPage.clickmanagemile();
				       projectPage.clicksavemilestone();  // verify alert 
				       projectPage.clearAndEnterText(ProjectPageLocator.updatefield, update);
				       projectPage.clicksavemilestone();
				       Thread.sleep(1000);
				       projectPage. closeUpdateHealthSheet();
			          projectPage.clickSerach();
			         projectPage.entersearchmilestone(searchmilestone);
			         projectPage.clickSerachfinal();
			         Thread.sleep(2000);
			         projectPage.clickSerach();
			         projectPage.deleteMilestoneWithPagination(deletemilestone);
			     
			 }
	  
		 
			 
			 //verify the updateprojecthealthsheet searchfuntionality      //run alone
			 @Test(priority=5,enabled=false,dataProvider = "Projectmodule",
			          dataProviderClass = ProjectDataProvider.class)
			    public void updatehealthsheetsearchanddelete(String projectname,String syear,String startmonths,String startdays,String eyear,String endmonths,String enday,String start ,String end) throws Throwable {
			        
			        // 1️⃣ Create Page Object
			        ProjectPage projectPage = new ProjectPage();
			        
			        // 2️⃣ Navigate to Projects page
			        projectPage.navigateToEditProject(projectname);
				       projectPage.clickupdateproject();
				      projectPage.clicksearchhealthsheet();
				      projectPage.selectStartDate2(syear,startmonths,startdays);
				      projectPage.selectEndDate2(eyear,endmonths, enday);
				      projectPage.clicksearchhealthsheet();  
				      projectPage.deleteCardByStartAndEndDate(start, end);
			 }
			 
				//verify the update actucal cost searchfuntionality      //run alone
			 @Test(priority=7,enabled=false,dataProvider = "Projectmodule",
			          dataProviderClass = ProjectDataProvider.class)
			    public void updateactucalcostsearch(String projectname,String costcategory,String syear,String startmonths,String startdays,String eyear,String endmonths,String enday,String costcategoryd, String amountd) throws Throwable {
			        
			        // 1️⃣ Create Page Object
			        ProjectPage projectPage = new ProjectPage();
			        
			        // 2️⃣ Navigate to Projects page
			           projectPage.navigateToEditProject(projectname);
				       projectPage.clickupdateactualcost();
				       projectPage.clicksearchactucalcost();
				       projectPage.selectcostcategoryr(costcategory);
				      projectPage.selectStartDate2(syear,startmonths,startdays);
				      projectPage.selectEndDate2(eyear,endmonths, enday);
				      projectPage.clicksearchactucalcostfinal();
				     projectPage.deleteCostByCategoryAndAmount(costcategoryd, amountd);
				
			 }      
		   
			 
			 
			 
 }
