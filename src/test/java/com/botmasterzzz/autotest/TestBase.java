package com.botmasterzzz.autotest;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase {

    protected ApplicationManager applicationManager;

    @BeforeClass
    protected void beforeClass(){
       this.applicationManager = ApplicationManager.getInstance();
    }

    @AfterClass
    protected void afterClass(){
        applicationManager.getWebDriver().close();
    }

    @BeforeMethod
    protected void beforeMethod(){
        System.out.println(2);
    }
}
