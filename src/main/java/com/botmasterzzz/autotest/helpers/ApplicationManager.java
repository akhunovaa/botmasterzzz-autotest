package com.botmasterzzz.autotest.helpers;

import com.botmasterzzz.autotest.helpers.base.BaseHelperWithWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager{

    private static final Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    private static ApplicationManager singleton;
    private BaseHelperWithWebDriver baseActionWithWebDriver;
    private List<WebDriverHelper> webDriverHelpers = new ArrayList<>();
    private ThreadLocal<WebDriverHelper> webDriverHelper = new ThreadLocal<>();

    public static ApplicationManager getInstance() {
        if (singleton == null)
            singleton = new ApplicationManager();
        return singleton;
    }

    public static ApplicationManager getCurrentInstance() {
        return singleton;
    }

    public WebDriverHelper getWebDriverHelper()
    {
        if (webDriverHelper.get() == null)
        {
            WebDriverHelper helper = new WebDriverHelper(this);
            synchronized (webDriverHelpers)
            {
                webDriverHelpers.add(helper);
            }
            webDriverHelper.set(helper);
        }
        return webDriverHelper.get();
    }


    public NavigationHelper getNavigationHelper()
    {
        return new NavigationHelper();
    }


    /**
     * Получаем driver текущего потока
     *
     * @return
     */
    public WebDriver getWebDriver()
    {
        return getWebDriverHelper().getDriver();
    }


    public String getMainWindow()
    {
        return getWebDriverHelper().getMainWindow();
    }

    public Actions getActions()
    {
        return getWebDriverHelper().getActions();
    }


    public BaseHelperWithWebDriver getBaseHelperWithWebDriver()
    {
        if (baseActionWithWebDriver == null)
        {
            baseActionWithWebDriver = new BaseHelperWithWebDriver(this);
        }
        return baseActionWithWebDriver;
    }
    /**
     * Останавливаем все запущенные браузеры
     */
    public void stopAll()
    {
        synchronized (this)
        {
            for (WebDriverHelper helper: webDriverHelpers)
            {
                try {
                    helper.close();
                } catch (Exception e) {
                    logger.error("Error when close browser", e);
                }
            }
            webDriverHelpers.clear();
            webDriverHelper = new ThreadLocal<>();
        }
    }
}
