package com.botmasterzzz.autotest.helpers;

public interface ApplicationManager {



    ApplicationManager getInstance();

    AutorizationHelper getAutorizationHelper();

    ClickHelper getClickHelper();

    NavigationHelper getNavigationHelper();

    PageHelper getPageHelper();

    WebDriverHelper getWebDriverHelper();



}
