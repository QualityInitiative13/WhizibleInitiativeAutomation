package tests;

import Base.BaseTest;
import Pages.InitiativeProgressPage;
import Utils.ExcelReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Initiative Progress Test Suite
 * @author Gayatri.k
 */
@Epic("Initiative Management")
@Feature("Initiative Progress")
public class InitiativeProgressTest extends BaseTest {

    private InitiativeProgressPage page;
    private static String cachedExcelPath = null;

    // ================= TC_001 =================

    @Test(priority = 1, groups = {"Progress"}, enabled = true)
    @Description("Navigation to the Initiative Progress")
    public void TC_001_verifyProgressNavigation() throws Exception {

        // Step 1: Click on Dashboard
        // Step 2: Click on Initiative Progress
        page = new InitiativeProgressPage(webDriver, reportLogger);
        page.navigateToInitiativeProgressPage();
        // Wait for page to load - navigation method already handles this with explicit wait

        // Expected Result: Initiative Progress page should be displayed
        
       
        page.verifyProgressHeader();
        boolean hasRecords = page.hasProgressRecords();
        System.out.println("📊 Initiative Progress page has records: " + hasRecords);
        
        System.out.println("✅ TC_001: Navigation to Initiative Progress page verified successfully");
    }

    // ================= TC_002 =================

    @Test(priority = 2, groups = {"Progress"}, enabled = true)
    @Description("Verify the search functionality")
    public void TC_002_validateSearchFunctionality() throws Exception {

        // Step 1: Click on Dashboard
        // Step 2: Click on Initiative Progress
        page = new InitiativeProgressPage(webDriver, reportLogger);
        page.navigateToInitiativeProgressPage();

        // Step 3: Click on search icon
        page.clickSearchIcon();

        // Step 4: Get data from Excel and select Project from dropdown
        String[] data = readExcelData("TC_002");
        String project = data[0]; // Project from Excel

        page.selectProject(project);

        // Step 5: Click on Search button
        page.clickSearchButton();

        // Step 6: Verify the search record should be displayed
        Assert.assertTrue(
                page.verifyMatchingRecord(project),
                "Record not found for Project: " + project
        );

        // Step 7: Click on clear search button
        page.clickClearSearchButton();
    }
    // ================= TC_003 =================

    @Test(priority = 3, groups = {"Progress"}, enabled = true)
    @Description("Edit the Project Health Sheet")
    public void TC_003_verifyEditFunctionality() throws Exception {

        // Step 1: Click on Dashboard module
        // Step 2: Click on Initiative Progress node
        page = new InitiativeProgressPage(webDriver, reportLogger);
        page.navigateToInitiativeProgressPage();

        // Step 3: Click on Search option
        page.clickSearchIcon();

        // Step 4: Click on Project dropdown field and select the project from Excel
        String[] data = readExcelData("TC_003");
        String project = data[0]; // Project from Excel

        page.selectProject(project);

        // Step 5: Click on Search button
        page.clickSearchButton();

        // Step 6: Click on the edit button of the Action column
        page.clickEditButtonByProject(project);

        System.out.println("✅ TC_003: Edit button clicked successfully for project: " + project);
    }

    // ================= TC_004 =================

    @Test(priority = 4, groups = {"Progress"})
    @Description("Verify the pagination functionality")
    public void TC_004_verifyPaginationFunctionality() throws Exception {

        // Step 1: Click on Dashboard
        // Step 2: Click on Initiative Progress
        page = new InitiativeProgressPage(webDriver, reportLogger);
        page.navigateToInitiativeProgressPage();
        // Wait for page to load - navigation method already handles this with explicit wait

        // Step 3: Click on Pagination and validate whether pagination works properly or not
        // Pass when next click is disabled (reaches last page)
        System.out.println("📍 Step 3: Verifying pagination functionality...");
        boolean paginationWorks = page.verifyPaginationFunctionality();

        Assert.assertTrue(
                paginationWorks,
                "Pagination functionality verification failed. Next button should become disabled when last page is reached."
        );

        System.out.println("✅ TC_004: Pagination functionality verified successfully");
    }

    // ================= EXCEL UTILS =================

    private String[] readExcelData(String testCaseId) throws Exception {

        String excelPath = findExcelPath();
        ExcelReader reader = new ExcelReader(excelPath, "InitiativeProgress");
        try {
            int rows = reader.getRowCount();
            System.out.println("📊 Excel file has " + rows + " data row(s)");

            for (int i = 1; i <= rows; i++) {
                String rowTCId = reader.getData(i, 0);
                System.out.println("🔍 Checking row " + i + ": TC_ID = '" + rowTCId + "'");
                if (testCaseId.equalsIgnoreCase(rowTCId)) {
                    System.out.println("✅ Found matching row for " + testCaseId + " at row " + i);
                    // TC_002, TC_003: Project
                    return new String[]{
                            reader.getData(i, 1) // Project
                    };
                }
            }
            throw new Exception("Test Case ID not found: " + testCaseId);
        } finally {
         //   reader.close();
        }
    }

    /**
     * Find Excel file path (cached to avoid multiple searches) // Gayatri.k
     * @author Gayatri.k
     */
    private String findExcelPath() throws Exception {
        if (cachedExcelPath != null && new File(cachedExcelPath).exists()) {
            return cachedExcelPath;
        }
        
        String[] possibleNames = {"TestdataIni.xlsx", "Testdataini.xlsx", "TEstdata.xlsx"};
        String[] possibleDirs = {
                "",
                "src/test/resources/",
                System.getProperty("user.dir") + "/",
                System.getProperty("user.dir") + "/src/test/resources/"
        };
        
        StringBuilder triedPaths = new StringBuilder();
        for (String dir : possibleDirs) {
            for (String name : possibleNames) {
                String path = dir + name;
                File file = new File(path);
                triedPaths.append("  - ").append(path);
                if (file.exists()) {
                    try {
                        ExcelReader testReader = new ExcelReader(path, "InitiativeProgress");
                 //       testReader.close();
                        cachedExcelPath = path;
                        System.out.println("✅ Found Excel file: " + path);
                        return cachedExcelPath;
                    } catch (Exception e) {
                        triedPaths.append(" (exists but cannot read: ").append(e.getMessage()).append(")");
                    }
                }
                triedPaths.append("\n");
            }
        }
        
        throw new Exception("Excel file not found. Tried paths:\n" + triedPaths.toString() + 
                "\nPlease ensure the Excel file (TestdataIni.xlsx, Testdataini.xlsx, or TEstdata.xlsx) exists in project root or src/test/resources/");
    }
}

