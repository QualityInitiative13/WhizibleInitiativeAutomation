package tests;

import org.testng.annotations.Test;

import Base.BaseTest;
import DataProvider.ProjectDataProvider;
import Pages.HealthSheetApprovelPage;
import Pages.ProjectPage;

public class HealthSheetApprovelTest  extends BaseTest{
	
	@Test(priority=1 ,enabled=true,dataProvider = "Projectmodule",
	          dataProviderClass = ProjectDataProvider.class)
	 public void HealthSheet(String projectnamedropdown,String rationale) throws Throwable {
		 
		 HealthSheetApprovelPage   healthsheet = new HealthSheetApprovelPage();
	
		          healthsheet.clickprogram();
		          healthsheet.clickhealthsheet();
		          healthsheet.selectprojectname(projectnamedropdown);
		          healthsheet.clickviewdetails();
		          healthsheet.enterrationale(rationale);
		          healthsheet.clickapprove();
		 
	 }
	
	
}
