package Locators;

import org.openqa.selenium.By;

public class ProjectHealthSheetApprovelLocator {
	
	public static By program =
            By.xpath("//div[@aria-label='Program']//img[@alt='Program']");
	
	
	
	 public static By  projehealthsheetapprovel=
	         By.xpath("//p[normalize-space()='Project Health Sheet Approval']");
  
	 public static By projectname =
             By.xpath("//span[normalize-space()='Select Project']");

     public static By projectnamelist =
             By.xpath("//div[@role='listbox']//button[@role='option']");
     
     public static By rationale =
             By.xpath("//textarea[@placeholder='Enter Rationale']");
     
     public static By viewdetails =
             By.xpath("//span[contains(text(),'View Details')]");
     
     public static By approve =
             By.xpath("//button[.//span[normalize-space()='Approve']]");
}
