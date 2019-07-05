package com.botmasterzzz.autotest.helpers.base;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Класс с базовым функционалом для работы с WebDriver, например открытие страницы, нахождение и действия над элементами, выполнение js и тд.
 */
public class HelperWithWebDriverBase {

    protected final ApplicationManager app;

    private static final Logger logger = LoggerFactory.getLogger(HelperWithWebDriverBase.class);
    /**
     * Конструктор класса
     *
     * @param app Application Manager
     */
    public HelperWithWebDriverBase(ApplicationManager app) {
       this.app = app;

    }

    /**
     * Получаем WebDriver текущего потока
     *
     * @return WebDriver текущего потока
     */
    private WebDriver getDriver() {
        return app.getDriver();
    }

    /**
     * Получаем Actions текущего потока
     *
     * @return Actions текущего потока
     */
    private Actions getActions() {
        return app.getActions();
    }

    /**
     * Выполняет JavaScript для элемента c исполняемым javascript (element - элемент, на котором будет выполняеться javascript)
     *
     * @param script  JavaScript
     * @param element элемент, для которого надо выполнить скрипт
     * @return Если скрипте отсутствует return возвращается null, в остальных случаях возвращаемое значение скрипта будет возвращено как Boolean, Long, String, List, WebElement или null.
     */
    protected Object executeJavaScript(String script, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) this.getDriver();
        return js.executeScript(script, element);
    }

    /**
     * Выполняет JavaScript (script исполняемый javascript)
     *
     * @param script JavaScript
     * @return Если скрипте отсутствует return возвращается null, в остальных случаях возвращаемое значение скрипта будет возвращено как Boolean, Long, String, List, WebElement или null.
     */
    public Object executeJavaScript(String script) {
        return executeJavaScript(script, null);
    }

    /**
     * Подсвечивает элемент красным цветом
     *
     * @param element Элемент, который надо подсветить
     */
    protected void highlightElement(WebElement element) {
        String param = "backgroundColor";
        String previousColor = element.getCssValue(param);
        changeCSS(element, param, "red");
        changeCSS(element, param, previousColor);
    }

    /**
     * Подсвечивает текст в элементе красным цветом
     *
     * @param element Элемент, в котором надо подсветить текст
     */
    protected void highlightElementText(WebElement element) {
        String param = "color";
        String previousColor = element.getCssValue(param);
        changeCSS(element, param, "red");
        changeCSS(element, param, previousColor);
    }

    /**
     * Изменение css свойства
     *
     * @param element      Элемент
     * @param cssParameter Свойство css
     * @param color        Цвет
     */
    private void changeCSS(WebElement element, String cssParameter, String color) {
        executeJavaScript("arguments[0].style." + cssParameter + " = '" + color + "'", element);
    }

    /**
     * Подтверждает алерт
     */
    public void acceptAlert() {
        logger.debug("acceptAlert");
        this.assertThatAlertIsPresent(true);
        getDriver().switchTo().alert().accept();
    }

    /**
     * Отклоняет alert
     */
    public void dismissAlert() {
        logger.debug("acceptAlert");
        this.assertThatAlertIsPresent(true);
        getDriver().switchTo().alert().dismiss();
    }

    /**
     * Подтвердить алерт, если присуствует
     */
    public void acceptAlertIfPresent() {
        if (isAlertPresent())
            acceptAlert();
    }

    /**
     * Проверка на присутствие алерта
     *
     * @return true - алерт пристутствует, false - алерт отсутствует
     */
    public boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    /**
     * Проверка на присутствие алерта с assert: true - присутствует, false - нет
     *
     * @param isPresent ожидается - присутствует или нет
     */
    public void assertThatAlertIsPresent(Boolean isPresent) {
        Boolean isAlertPresent = this.isAlertPresent();
        assertThat("Expected alert presentation is " + isPresent + ", but was " + isAlertPresent,
                isAlertPresent, equalTo(isPresent));
    }

    /**
     * Возвращает цвет элемента
     *
     * @param element Элемент
     * @return Цвет элемента
     */
    protected String getHighlightElement(WebElement element) {
        String param = "color";
        return element.getCssValue(param);
    }

    /**
     * Возвращает высоту элемента
     *
     * @param element Элемент
     * @return Высота элемента
     */
    protected int getHeightElement(WebElement element) {
        String param = "height";
        String height = element.getCssValue(param).replace("px", "");
        assertThat("Element's height is empty", height.trim(), not(""));
        Double number = Double.parseDouble(height);
        return number.intValue();
    }

    /**
     * Возвращает ширину элемента
     *
     * @param element Элемент
     * @return Ширина элемента
     */
    public int getWidthElement(WebElement element) {
        String param = "width";
        String width = element.getCssValue(param).replace("px", "");
        assertThat("Element's width is empty", width.trim(), not(""));
        Double number = Double.parseDouble(width);
        return number.intValue();
    }

    /**
     * Возвращает заливку элемента
     *
     * @param element Элемент
     * @return Заливка элемента
     */
    protected String getBackgroundColor(WebElement element) {
        String param = "background-color";
        return element.getCssValue(param);
    }

    /**
     * Получение координат элемента
     *
     * @param element Элемент
     * @return Координаты элемента
     */
    public Map<String, Double> getElementCoordinatesInPage(WebElement element) {
        String script = "var p = $(arguments[0]);\n" +
                "var offset = p.offset(); \n";
        String left = executeJavaScript(script + " return offset.left;", element).toString();
        String top = executeJavaScript(script + " return offset.top;", element).toString();
        String right = executeJavaScript(script + "return $(window).width() - (offset.left + $('#whatever').outerWidth(true));", element).toString();
        String bottom = executeJavaScript(script + "return $(window).height() - (offset.top + $('#whatever').outerHeight(true));", element).toString();
        Map<String, Double> coordinates = new HashMap<>();
        coordinates.put("left", Double.parseDouble(left));
        coordinates.put("top", Double.parseDouble(top));
        coordinates.put("right", Double.parseDouble(right));
        coordinates.put("bottom", Double.parseDouble(bottom));
        return coordinates;
    }

    /**
     * Возвращает высоту элемента
     *
     * @param element Элемент
     * @return Высота элемента
     */
    public int getElementClientHeight(WebElement element) {
        return Integer.parseInt(executeJavaScript("return $(arguments[0]).prop(\"clientHeight\")", element).toString());
    }

    /**
     * Возвращает ширину элемента
     *
     * @param element Элемент
     * @return Ширина элемента
     */
    public int getElementClientWidth(WebElement element) {
        return Integer.parseInt(executeJavaScript("return $(arguments[0]).prop(\"clientWidth\")", element).toString());
    }


}

