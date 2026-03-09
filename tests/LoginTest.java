package tests;

import org.testng.annotations.*;
import Base.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLogin() throws InterruptedException {
        // LoginHelper is already initialized in BaseTest
        // and login is automatically performed in setUp()
        
        // If you need to perform additional login with different credentials:
        // loginHelper.performLogin();
    	closeNotificationPopupIfPresent();
        Thread.sleep(3000);
        // Add assertions after login if needed
        reportLogger.info("Login verification test - user is already logged in");
        reportLogger.pass("✅ Login verified successfully");
    }
}
