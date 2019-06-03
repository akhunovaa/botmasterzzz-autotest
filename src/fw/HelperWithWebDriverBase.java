package fw;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HelperWithWebDriverBase {
    protected final ApplicationManager manager;
    private WebDriver driver;

    public HelperWithWebDriverBase(ApplicationManager manager) {
        this.manager = manager;
        driver = manager.getHelperWebDriver().getDriver();
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void type(WebElement element, String string) {
//        clear
//        todo Перенос в helperClick по мере реализации
    }
}
