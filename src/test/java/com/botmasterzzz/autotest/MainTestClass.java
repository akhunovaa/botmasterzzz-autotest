package com.botmasterzzz.autotest;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MainTestClass extends TestBase{

    @Test
    public void step01(){
        String title = "Botmasterzzz -Главный портал по сервису и поддержке искусственного интеллекта";
        driver.navigate().to("https://botmasterzzz.com");
        String pageTitile = driver.getTitle();
        assertThat(String.format("Заголовок главной страницы не соответствует '%s'", title), title.equals(pageTitile));
    }
}
