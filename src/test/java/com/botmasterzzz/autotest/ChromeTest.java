package com.botmasterzzz.autotest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class ChromeTest{

    private WebDriver driver;

    @BeforeTest
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeClass
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void step01(){
        String title = "Botmasterzzz - Главный портал по сервису и поддержке искусственного интеллекта";
        driver.navigate().to("https://botmasterzzz.com");
        String pageTitile = driver.getTitle();
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(pageTitile));
    }
}
