package com.botmasterzzz.autotest.helpers;

import com.botmasterzzz.autotest.helpers.base.HelperWithWebDriverBase;
import com.botmasterzzz.autotest.xp.HelperXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperAutorization extends HelperWithWebDriverBase {

    private static final Logger logger = LoggerFactory.getLogger(HelperAutorization.class);
    private HelperClick click;
    private HelperXPath xPath;

    public HelperAutorization(ApplicationManager app) {
        super(app);
        click = app.getHelperClick();
        xPath = app.getHelperXPath();

    }
}
