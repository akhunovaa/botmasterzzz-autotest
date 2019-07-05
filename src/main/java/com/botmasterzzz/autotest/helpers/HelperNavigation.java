package com.botmasterzzz.autotest.helpers;

import com.botmasterzzz.autotest.helpers.base.HelperWithWebDriverBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelperNavigation extends HelperWithWebDriverBase {

    private static final Logger logger = LoggerFactory.getLogger(HelperNavigation.class);

    public HelperNavigation(ApplicationManager app) {
        super(app);

    }

    private WebDriver getDriver() {
        return app.getDriver();
    }

    private Actions getActions() {
        return app.getActions();
    }


    /**
     * Возвращает информацию о новом появившемся окне.
     *
     * @return информацию о новом появившемся окне.
     */
    public String getNewWindow() {
        for (int i = 0; i < 100; i++) {
            Set<String> windows = getWindowHandles();
            if (windows.size() > 1) {
                windows.remove(app.getHelperWebDriver().getMainWindow());
                return windows.toArray()[0].toString();
            }
        }
        return null;
    }

    /**
     * Обновляет страницу
     */
    public void refresh() {
        getDriver().navigate().refresh();
    }


    /**
     * Возвращает имя активного окна
     *
     * @return Имя активного окна
     */
    protected String getWindowHandle() {
        return getDriver().getWindowHandle();
    }

    /**
     * Возвращает список активных окон
     *
     * @return Список активных окон
     */
    protected Set<String> getWindowHandles() {
        return getDriver().getWindowHandles();
    }

    /**
     * Переключает драйвер на новое окно
     *
     * @param window Окно, на которое надо переключить драйвер
     * @return WebDriver с необходимым окном
     */
    public WebDriver switchToWindow(String window) {
        return getDriver().switchTo().window(window);
    }

    /**
     * Открывает ссылку в новом окне и задает имя окна
     *
     * @param url  Ссылка, которую надо открыть в новом окне
     * @param name Имя окна
     * @return Ммя окна
     */
    public String openInNewWindow(String url, String name) {

        ((JavascriptExecutor) getDriver()).executeScript("window.open(arguments[0],\"" + name + "\")", url);
        getDriver().getWindowHandles();
        return name;
    }

    /**
     * Открывает заданный URL
     *
     * @param url Заданный URL
     */
    public void openUrl(String url) {
        getDriver().get(url);
        acceptAlertIfPresent();
    }

    /**
     * Возвращает URL текущей страницы
     *
     * @return URL текущей страницы
     */
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Проверка наличия текста в URL текущей страницы
     *
     * @param expectedUrl Ожидаемый текст
     * @return true если текст найден, false если нет
     */
    public boolean isCurrentUrlContains(String expectedUrl) {
        logger.debug(expectedUrl);
        return getCurrentUrl().contains(expectedUrl);
    }

    /**
     * Assert на наличие текста в URL текущей страницы, если assert не пройден, то тест падает с ошибкой
     *
     * @param expectedUrl Ожидаемый текст
     * @param isContains  true - ожидаем наличие текста, false - ожидаем отсутствие текста
     */
    public void assertCurrentUrlContains(String expectedUrl, Boolean isContains) {
        assertThat(String.format("Current URL %s contains %s", (isContains ? "doesn't" : "must not"), expectedUrl),
                isCurrentUrlContains(expectedUrl), is(isContains));
    }

    /**
     * Assert на наличие текста из списка в URL текущей страницы, если в ULR не был найден хотя бы один текст из списка, то тест падает с ошибкой
     *
     * @param expectedUrls Список ожидаемых текстов
     */
    public void assertCurrentUrlContainsAnyOne(List<String> expectedUrls) {
        for (String url : expectedUrls) {
            if (isCurrentUrlContains(url)) {
                return;
            }
        }
    }

}
