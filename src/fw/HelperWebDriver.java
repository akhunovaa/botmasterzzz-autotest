package fw;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.*;

import java.util.concurrent.TimeUnit;

public class HelperWebDriver {
    private final ApplicationManager manager;
    protected static WebDriver driver;
    protected String nameDriver;
    private String baseUrl;
    private boolean acceptNextAllert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    public HelperWebDriver(ApplicationManager manager) {
        this.manager = manager;
        driver = new FirefoxDriver();
//        driver = new InternetExplorerDriver();
//        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if ("".equals(verificationErrorString)) {
//            fail(verificationErrorString);
        }
    }
}
