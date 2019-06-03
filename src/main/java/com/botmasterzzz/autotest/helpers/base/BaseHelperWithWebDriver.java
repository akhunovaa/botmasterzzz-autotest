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
public class BaseHelperWithWebDriver {

    protected final ApplicationManager app;

    private static final Logger logger = LoggerFactory.getLogger(BaseHelperWithWebDriver.class);
    /**
     * Конструктор класса
     *
     * @param app Application Manager
     */
    public BaseHelperWithWebDriver(ApplicationManager app) {
       this.app = app;
    }

    /**
     * Получаем WebDriver текущего потока
     *
     * @return WebDriver текущего потока
     */
    private WebDriver getDriver() {
        return app.getWebDriver();
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
     * Возвращает найденный по локатору элемент
     *
     * @param by Локатор
     * @return Найденный по локатору элемент
     */
    public WebElement findElement(By by) {
        logger.debug(String.valueOf(by));
        WebElement element = getDriver().findElement(by);
        this.highlightElement(element);
        return element;
    }

    /**
     * Возвращает найденный по локатору элемент от родителя
     *
     * @param by     Локатор
     * @param parent Родительский элемент
     * @return Найденный по локатору элемент от родителя
     */
    public WebElement findElement(By by, WebElement parent) {
        logger.debug(String.valueOf(by));
        WebElement element = (parent != null) ? parent.findElement(by) : this.findElement(by);
        this.highlightElement(element);
        return element;
    }

    /**
     * Возвращает список найденных по локатору элементов
     *
     * @param by Локатор
     * @return Список найденных по локатору элементов
     */
    public List<WebElement> findElements(By by) {
        logger.debug(String.valueOf(by));
        return getDriver().findElements(by);
    }

    /**
     * Возвращает список найденных по локатору элементов
     *
     * @param by     Локатор элемента
     * @param parent Родительский элемент
     * @return Список найденных по локатору элементов
     */
    public List<WebElement> findElements(By by, WebElement parent) {
        logger.debug(String.valueOf(by));
        return (parent != null) ? parent.findElements(by) : this.findElements(by);
    }

    /**
     * Кликает по списку элементов, найденных по одному локатору
     *
     * @param by Локатор элемента
     */
    protected void clickElements(By by) {
        logger.debug(String.valueOf(by));
        List<WebElement> elements = this.findElements(by);
        elements.stream().filter(element -> !element.isSelected()).forEach(this::click);
    }

    /**
     * Возвращает количество найденных по локатору элементов
     *
     * @param by Локатор элемента
     * @return Количество найденных по локатору элементов
     */
    protected int countElements(By by) {
        logger.debug(String.valueOf(by));
        return findElements(by).size();
    }

    /**
     * Кликаем по найденному по локатору элементу
     *
     * @param by Локатор элемента
     */
    protected void click(By by) {
        logger.debug(String.valueOf(by));
        click(findElement(by));
    }

    /**
     * Возвращеает текстовое содержание элемента
     *
     * @param by Локатор элемента
     * @return Текстовое содержание элемента
     */
    protected String getText(By by) {
        logger.debug(String.valueOf(by));
        return findElement(by).getText();
    }

    /**
     * Возвращеает текстовое содержание элемента
     *
     * @param by     Локатор элемента
     * @param parent Родительский WebElement
     * @return Текстовое содержание элемента
     */
    protected String getText(By by, WebElement parent) {
        logger.debug(String.valueOf(by));
        return findElement(by, parent).getText().trim();
    }

    /**
     * Возвращеает текстовое содержание элемента
     *
     * @param element WebElement с текстовым содержанием
     * @return Текстовое содержание элемента
     */
    protected String getText(WebElement element) {
        logger.debug(String.valueOf(element));
        return element.getText().trim();
    }

    /**
     * Проскроллить страницу, чтобы элемент оказался наверху страницы
     *
     * @param element Искомый элемент
     */
    protected void scrollIntoViewTop(WebElement element) {
        executeJavaScript("arguments[0].scrollIntoView(true)", element);
    }

    /**
     * Проскроллить страницу, чтобы элемент оказался внизу страницы
     *
     * @param element Искомый элемент
     */
    protected void scrollIntoViewBottom(WebElement element) {
        executeJavaScript("arguments[0].scrollIntoView(false)", element);
    }

    /**
     * Проскроллить страницу, чтобы элемент оказался посередине страницы
     *
     * @param element Искомый элемент
     */
    protected void scrollIntoViewCenter(WebElement element) {
        executeJavaScript("window.scrollTo(" +
                "Math.max(0, $(arguments[0]).offset().left + $(arguments[0]).width() / 2 - $(window).width() / 2)," +
                "Math.max(0, $(arguments[0]).offset().top + $(arguments[0]).height() / 2 - $(window).height() / 2)" +
                ")", element);
    }

    /**
     * Кликаем по найденному элементу
     *
     * @param element Элемент, по которому надо кликнуть
     */
    public void click(WebElement element) {
        logger.trace(String.valueOf(element));
        this.highlightElement(element);
        try {
            element.click();
        } catch (MoveTargetOutOfBoundsException e) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    /**
     * Кликаем по найденному элементу и подтверждаем алерт, если он присутствует
     *
     * @param element Элемент, по которому надо кликнуть
     */
    public void clickAndAcceptAlertIfPresent(WebElement element) {
        logger.trace(String.valueOf(element));
        this.highlightElement(element);
        try {
            element.click();
        } catch (MoveTargetOutOfBoundsException e) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("error", e);        }
        acceptAlertIfPresent();
    }

    /**
     * Вводим текст в элемент по локатору
     *
     * @param by   Локатор
     * @param text Вводимый текст
     */
    protected void type(By by, String text) {
        WebElement element = findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Вводим текст в элемент
     *
     * @param element Элемент
     * @param text    Вводимый текст
     */
    protected void type(WebElement element, String text) {
        text = text.replaceAll("\\(", Keys.chord(Keys.SHIFT, "9"))
                .replaceAll("\\#", Keys.chord(Keys.SHIFT, "3"))
                .replaceAll("\\-", Keys.SUBTRACT.toString())
                .replaceAll("\\}", Keys.chord(Keys.SHIFT, "]"));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Вводим текст в элемент без очистки значения в поле (например, для полей с маской)
     *
     * @param element Элемент
     * @param text    Вводимый текст
     */
    protected void typeWithoutClear(WebElement element, String text) {
        element.sendKeys(text);
    }

    /**
     * Двойной клик по элементу
     *
     * @param element Элемент
     */
    protected void dblClick(WebElement element) {
        getActions().doubleClick(element).build().perform();
    }

    /**
     * Двойной клик по элементу по его локатору
     *
     * @param by Локатор
     */
    protected void dblClick(By by) {
        dblClick(findElement(by));
    }

    /**
     * Проверяет наличие элемента по локатору
     *
     * @param by Локатор
     * @return true если найден, false если нет
     */
    protected boolean isElementPresent(By by) {
        try {
            this.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Проверяет наличие элемента по локатору
     *
     * @param by     Локатор
     * @param parent Родительский элемент
     * @return true если найден, false если нет
     */
    protected boolean isElementPresent(By by, WebElement parent) {
        return !this.findElements(by, parent).isEmpty();
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
     * Эмуляция нажатия клавиш
     *
     * @param keys Клавиши, которые надо нажать
     */
    public void useKeys(CharSequence keys) {
        getActions().sendKeys(keys).build().perform();
    }

    /**
     * Нажатие клавиши и удерживание
     *
     * @param keys Клавиши, которые надо нажать
     */
    public void keyDown(CharSequence keys) {
        getActions().keyDown((Keys) keys);
        getActions().perform();
    }

    /**
     * Отпускает клавишу
     *
     * @param keys Клавиши, которые надо отпустить
     */
    public void keyUp(CharSequence keys) {
        getActions().keyDown((Keys) keys);
        getActions().perform();
    }

    /**
     * Пролистывает страницу к элементу
     *
     * @param by Локатор элемента
     */
    protected void moveToElement(By by) {
        getActions().moveToElement(findElement(by)).build().perform();
    }

    /**
     * Кликает по координатам элемента
     *
     * @param x Координата x
     * @param y Координата y
     */
    public void clickByCoordinates(int x, int y) {
        executeJavaScript(String.format("$(document.elementFromPoint(%s, %s)).click();", x, y));
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


    /**
     * Возвращает информацию о новом появившемся окне.
     *
     * @return информацию о новом появившемся окне.
     */
    public String getNewWindow() {
        for (int i = 0; i < 100; i++) {
            Set<String> windows = getWindowHandles();
            if (windows.size() > 1) {
                windows.remove(app.getMainWindow());
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
     * Получает родительский WebElement
     *
     * @param element Дочерний WebElement
     * @return Родительский WebElement
     */
    public WebElement getParent(WebElement element) {
        return findElement(By.xpath(".."), element);
    }

    /**
     * Получает родительский WebElement
     *
     * @param by Локатор дочернего элемента
     * @return Родительский WebElement
     */
    public WebElement getParent(By by) {
        return getParent(findElement(by));
    }
}

