package com.botmasterzzz.autotest;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestClass extends TestBase{

    protected ApplicationManager applicationManager;
    protected WebDriver driver;

    @BeforeTest
    public void beforeTest(){
        this.applicationManager = ApplicationManager.getInstance();
        driver = this.applicationManager.getWebDriverHelper().getDriver();
    }

    @Test
    public void step01(){
        String title = "Botmasterzzz - Главный портал по сервису и поддержке искусственного интеллекта";
        driver.navigate().to("https://botmasterzzz.com");
        String pageTitile = driver.getTitle();
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(pageTitile));
    }

    @AfterTest
    public void afterTest(){
        applicationManager.stopAll();
    }
}
