package com.botmasterzzz.autotest;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import com.botmasterzzz.autotest.helpers.WebDriverHelper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class TestBase {

    protected ApplicationManager applicationManager;
    protected WebDriverHelper webDriverHelper;
    protected WebDriver driver;

    @BeforeClass
    protected void beforeClass(){
       this.applicationManager = ApplicationManager.getInstance();
       webDriverHelper = new WebDriverHelper(applicationManager);
       driver = webDriverHelper.getDriver();
    }

    @AfterClass
    protected void afterClass(){
        this.webDriverHelper.close();
    }

}
