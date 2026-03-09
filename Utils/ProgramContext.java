package Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ProgramContext {
	 private static final String FILE_PATH = "program-context.properties";

	    public static void saveProgramName(String programName) {
	        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
	            Properties prop = new Properties();
	            prop.setProperty("programName", programName);
	            prop.store(fos, "Stored Program Name");
	        } catch (Exception e) {
	            throw new RuntimeException("Unable to save program name", e);
	        }
	    }

	    public static String loadProgramName() {
	        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
	            Properties prop = new Properties();
	            prop.load(fis);
	            return prop.getProperty("programName");
	        } catch (Exception e) {
	            throw new RuntimeException("No stored program found. Run Test 1 first.", e);
	        }
	    }
}
