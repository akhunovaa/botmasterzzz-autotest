package com.botmasterzzz.autotest.xp;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XpMainPage extends HelperXPath {

    private static final Logger logger = LoggerFactory.getLogger(XpMainPage.class);



    public XpMainPage(ApplicationManager app) {
        super(app);
    }

    /**
     *
     * @return панель с кнопками Авторизация, Регистрация, Обратная связь
     */
    private WebElement bNavLinks() {
        logger.info("получение элемента с кнопками b_nav_links");
        if (click().isElementPresent(By.xpath("//nav[@class='b_nav_links']"))) {
            return click().findElement(By.xpath("//nav[@class='b_nav_links']"));
        } else {
            logger.info("element not found, \n{}", "//nav[@class='b_nav_links']");
            return null;
        }
    }

}
