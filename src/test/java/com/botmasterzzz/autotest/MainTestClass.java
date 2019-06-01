package com.botmasterzzz.autotest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestClass extends TestBase{

    @Test
    public void step01(){
        String title = "Botmasterzzz - Главный портал по сервису и поддержке искусственного интеллекта";
        WebDriver driver = applicationManager.getWebDriver();
        driver.navigate().to("https://botmasterzzz.com");
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(driver.getTitle()));
    }
}
