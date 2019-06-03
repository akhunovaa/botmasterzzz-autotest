package com.botmasterzzz.autotest.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationManager {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    private static ApplicationManager singleton;
    private HelperClick helperClick;
    private HelperDicts helperDicts;
    private HelperGrid helperGrid;
    private HelperNavigation helperNavigation;
    private HelperWebDriver helperWebDriver;
    private HelperXPath helperXPath;

    public static ApplicationManager getInstance() {
        if (singleton == null) {
            singleton = new ApplicationManager();
        }
        return singleton;
    }

    public HelperClick getHelperClick() {
        if (helperClick == null) {
            helperClick = new HelperClick(this);
        }
        return helperClick;
    }

    public HelperDicts getHelperDicts() {
        if (helperDicts == null) {
            helperDicts = new HelperDicts(this);
        }
        return helperDicts;
    }

    public HelperGrid getHelperGrid() {
        if (helperGrid == null) {
            helperGrid = new HelperGrid(this);
        }
        return helperGrid;
    }

    public HelperNavigation getHelperNavigation() {
        if (helperNavigation == null) {
            helperNavigation = new HelperNavigation(this);
        }
        return helperNavigation;
    }

    public HelperWebDriver getHelperWebDriver() {
        if (helperWebDriver == null) {
            helperWebDriver = new HelperWebDriver(this);
        }
        return helperWebDriver;
    }

    public HelperXPath getHelperXPath() {
        if (helperXPath == null) {
            helperXPath = new HelperXPath(this);
        }
        return helperXPath;
    }
}