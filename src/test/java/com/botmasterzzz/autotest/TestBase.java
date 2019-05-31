package com.botmasterzzz.autotest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase {

    @BeforeClass
    protected void beforeClass(){
        System.out.println(1);
    }

    @BeforeMethod
    protected void beforeMethod(){
        System.out.println(2);
    }
}
