package com.botmasterzzz.autotest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;


import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestClass extends TestBase{

    private WebDriver driver;

    @BeforeClass
    public void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--headless");
        options.setBinary(new File("/usr/bin/google-chrome"));
        driver = new ChromeDriver(options);
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void step01(){
        String title = "Botmasterzzz - Главный портал по сервису и поддержке искусственного интеллекта";
        driver.navigate().to("https://botmasterzzz.com");
        String pageTitile = driver.getTitle();
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(pageTitile));
    }
}
