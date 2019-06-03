package com.botmasterzzz.autotest.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperWithWebDriverBase {

    private static final Logger logger = LoggerFactory.getLogger(HelperWithWebDriverBase.class);
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
