package com.botmasterzzz.autotest.helpers;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Класс для инициалиизации и начала работы webdriver
 */
public class HelperWebDriver {

    private static final Logger logger = LoggerFactory.getLogger(HelperWebDriver.class);

    private ApplicationManager app;

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String mainWindow;
    private Actions actions;
    private Connection connection;

    /**
     * Инициализирует драйвер, выставляет время ожидания, максимально раскрывает окно.
     *
     * @param app ApplicationManager
     */
    public HelperWebDriver(ApplicationManager app) {

        this.app = app;
        try {
//            driver = this.getChromeDriver();
            driver = startDriver();
        } catch (IOException e) {
            logger.error("Could not catch the Chrome driver", e);
        }
        logger.info("Browser starting... ");

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        maximizeWindow();
        logger.info("done!");
    }


    /**
     * Возвращает webdriver
     *
     * @return Текущий webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Раскрывает окно браузера на весь экран
     */
    private void maximizeWindow() {
        getDriver().manage().window().maximize();
    }


    /**
     * Переключение на главное окно в потоке
     */
    public void switchToMainThreadWindow() {
        Set<String> windows = getDriver().getWindowHandles();
        if (windows.size() > 0) {
            assertThat("Can't find thread main window. It must be was closed"
                            + "\nMain window handle = " + mainWindow,
                    windows.contains(mainWindow));

            for (String window : getDriver().getWindowHandles()) {
                if (!window.equals(mainWindow))
                    getDriver().switchTo().window(window).close();
            }
        } else {
            assertThat("Can't find thread window. It must be was closed", windows.size() > 0);
        }
        getDriver().switchTo().window(mainWindow);
    }


    /**
     * Получение главного окна.
     *
     * @return Handler главного окна
     */
    public String getMainWindow() {
        return mainWindow;
    }

    /**
     * Получение Actions
     *
     * @return Actions текущего вебдрайвера
     */
    public Actions getActions() {
        if (actions == null) {
            actions = new Actions(driver);
        }
        return actions;
    }

    /**
     * Закрывает браузер и все, что связано с ним:<br>
     * 1) Соединение с отладчиком.<br>
     * 2) Каталог для скачивания файлов.<br>
     * 3) Web-driver.<br>
     */
    public void stop() {
        try {
            driver.close();
            driver.switchTo().alert().accept();
            driver.quit();
        } catch (NoAlertPresentException e) {
            logger.debug("Алерт на странице при закрытии браузера не найден");
        } catch (UnreachableBrowserException e) {
            logger.debug("Браузер уже закрыт");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return WebDriver по указанному в System.getenv("driver") типу
     * @throws IOException
     */
    private WebDriver startDriver() throws IOException {
//        for (String browser : System.getenv("browsers").split(", ")) {
        WebDriver ldriver = initChromeDriver();
        switch (System.getenv("browser")) {
            case "CHROME":
                ldriver = initChromeDriver();
                break;
            case "SAFARI":
                ldriver = initSafariDriver();
                break;
            case "FIREFOX":
                ldriver = initFirefoxDriver();
                break;
            case "IE":
                ldriver = initIEDriver();
                break;
        }
        ldriver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        ldriver.manage().window().maximize();
        return ldriver;
    }


    /**
     * Получение connection с удаленным дебаггером
     *
     * @return Connection с удаленным дебаггером
     */
    public Connection getConnection() {
        return connection;
    }


    /**
     * Получение chrome driver с настройками
     *
     * @return chrome driver с настройками
     * @throws IOException
     */
    private WebDriver initChromeDriver() throws IOException {
        WebDriver idriver;

//        System.setProperty("webdriver.chrome.driver", "src/test/resources/mac/chromedriver");
//        File chromeDriver = new File("src/test/resources/mac/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        File chromeDriver = new File("/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
//        ChromeDriver idr = new ChromeDriver(options);
        idriver = new ChromeDriver(options);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chromeDriver)
                .usingAnyFreePort()
                .build();
        service.start();

        return idriver;
    }

    private WebDriver initSafariDriver() {
        WebDriver idriver = new SafariDriver();

        return idriver;
    }

    private WebDriver initFirefoxDriver() {
        WebDriver idriver = new FirefoxDriver();

        return idriver;

    }

    private WebDriver initIEDriver() {
        WebDriver idriver = new InternetExplorerDriver();

        return idriver;
    }
}
