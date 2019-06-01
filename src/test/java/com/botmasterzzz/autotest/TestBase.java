package com.botmasterzzz.autotest;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class TestBase {

    protected ApplicationManager applicationManager;
    protected WebDriver driver;

    @BeforeClass
    protected void beforeClass(){
       this.applicationManager = ApplicationManager.getInstance();
       driver = this.applicationManager.getWebDriverHelper().getDriver();
    }

    @AfterClass
    protected void afterClass(){
        this.applicationManager.stopAll();
    }

}
