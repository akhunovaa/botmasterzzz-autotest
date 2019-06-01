package com.botmasterzzz.autotest;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestClass extends TestBase{

    //private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeTest
    public static void createAndStartService() throws IOException {
//        service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File("/usr/bin/chromedriver"))
//                .usingAnyFreePort()
//                .build();
//        service.start();
    }

    @AfterTest
    public static void createAndStopService() {
       // service.stop();
    }


    @BeforeClass
    public void createDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
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
