package DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import Utils.ExcelReader;

public class ProgramDataProvider {
	
	@DataProvider(name = "Program")
    public Object[][] getInitiativeData1(Method method) throws Exception {

        String testCaseId = method.getName();

        // Excel kept at project root (as per your setup)
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "CreateProgram");
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
 
	
	
	@DataProvider(name = "Initiative")
    public Object[][] getInitiativeData2(Method method) throws Exception {

        String testCaseId = method.getName();

        // Excel kept at project root (as per your setup)
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "Initiatives");
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@DataProvider(name = "Initiative")
	public Object[][] getInitiativeData(Method method) throws Exception {

	    String tcid = method.getName();
	    ExcelReader reader = new ExcelReader("ProjectData2.xlsx", "Initiatives");
	    int rowCount = reader.getRowCount();

	    for (int i = 1; i <= rowCount; i++) {

	        String excelTCID = reader.getData(i, 0);

	        if (excelTCID == null || excelTCID.trim().isEmpty()) continue;
	        if (excelTCID.equalsIgnoreCase("TCID")) continue;

	        if (excelTCID.equalsIgnoreCase(tcid)) {

	            String program = reader.getData(i, 1).trim();
	            List<String> initiatives = csvToList(reader.getData(i, 2));
	            String searchInitiative = reader.getData(i, 3).trim();
	            String deleteInitiative = reader.getData(i, 4).trim();
	            return new Object[][]{
	                { program, initiatives, searchInitiative,deleteInitiative }
	            };
	        }
	    }

	    throw new RuntimeException("❌ TCID not found: " + tcid);
	}

	public List<String> csvToList(String csv) {

	    if (csv == null || csv.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    return Arrays.stream(csv.split(","))
	                 .map(String::trim)
	                 .filter(s -> !s.isEmpty())
	                 .collect(Collectors.toList());
	}
*/

	@DataProvider(name = "Associated")
    public Object[][] getInitiativeData6(Method method) throws Exception {

        String testCaseId = method.getName();

        // Excel kept at project root (as per your setup)
        ExcelReader reader = new ExcelReader("TestdataIni.xlsx", "Associated");
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
 
	

	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

}
