package com.botmasterzzz.autotest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class FirefoxTest {

    private WebDriver driver;

    @BeforeTest
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void setupTest() {
        driver = new FirefoxDriver();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        String title = "Botmasterzzz - Главный портал по сервису и поддержке искусственного интеллекта";
        driver.navigate().to("https://botmasterzzz.com");
        String pageTitile = driver.getTitle();
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(pageTitile));
    }
}
