package DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import Utils.ExcelReader;

public class ProjectDataProvider {
	/*@DataProvider(name = "Projectmodule")
    public Object[][] getInitiativeData(Method method) throws Exception {

        String testCaseId = method.getName();

        // Excel kept at project root (as per your setup)
        ExcelReader reader = new ExcelReader("ProjectData2.xlsx", "CreateProject");
        int rowCount = reader.getRowCount();

        for (int i = 0; i < rowCount; i++) {

            String excelTCID = reader.getData(i + 1, 0).trim();

            if (excelTCID.equalsIgnoreCase(testCaseId)) {

                int paramCount = method.getParameterCount();
                Object[][] data = new Object[1][paramCount];

                for (int j = 0; j < paramCount; j++) {
                    data[0][j] = reader.getData(i + 1, j + 1).trim();
                }
                return data;
            }
        }

        // If TCID not found
        return new Object[0][0];
    }
  */
  
	@DataProvider(name = "Projectmodule")
	public Object[][] getProjectData(Method method) throws Exception {

	    String testCaseId = method.getName();   // e.g. createProjectTest, Definemilestone, etc.

	    ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "Project1");
	    int rowCount = reader.getRowCount();

	    for (int i = 0; i < rowCount; i++) {

	        String excelTCID = reader.getData(i + 1, 0);

	        // ✅ 1. Skip completely blank physical rows
	        if (excelTCID == null || excelTCID.trim().isEmpty()) {
	            continue;
	        }

	        // ✅ 2. Skip header rows like: TCID | ProjectName | ...
	        if (excelTCID.trim().equalsIgnoreCase("TCID")) {
	            continue;
	        }

	        // ✅ 3. Match TCID with test method name
	        if (excelTCID.trim().equalsIgnoreCase(testCaseId)) {

	            int paramCount = method.getParameterCount();
	            Object[][] data = new Object[1][paramCount];

	            for (int j = 0; j < paramCount; j++) {

	                // Excel column index = j + 1  (since col 0 = TCID)
	                String cellValue = reader.getData(i + 1, j + 1);

	                // ✅ Accept empty cells safely
	                if (cellValue == null) {
	                    data[0][j] = "";
	                } else {
	                    data[0][j] = cellValue.trim();
	                }
	            }

	            return data;
	        }
	    }

	    // ❌ Fail fast if TCID not found (prevents silent skips)
	    throw new RuntimeException(
	        "❌ No data found in Excel sheet [CreateProject1] for TCID: " + testCaseId
	    );
	}

  
  
	

	
	
	
	

	
	
	
}


