package com.botmasterzzz.autotest.helpers;

import com.botmasterzzz.autotest.helpers.base.HelperWithWebDriverBase;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HelperClick extends HelperWithWebDriverBase {

    private static final Logger logger = LoggerFactory.getLogger(HelperClick.class);
    public HelperClick(ApplicationManager app) {
        super(app);

    }

    private WebDriver getDriver() {
        return app.getDriver();
    }

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
        click(element);
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
    public boolean isElementPresent(By by) {
        try {
            this.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     *
     */
//    public boolean isElementPresent(WebElement element) {
//        return element.
//    }

    /**
     * Проверяет наличие элемента по локатору
     *
     * @param by     Локатор
     * @param parent Родительский элемент
     * @return true если найден, false если нет
     */
    public boolean isElementPresent(By by, WebElement parent) {
        return !this.findElements(by, parent).isEmpty();
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
