package com.botmasterzzz.autotest.xp;

import com.botmasterzzz.autotest.helpers.ApplicationManager;
import com.botmasterzzz.autotest.helpers.HelperClick;
import com.botmasterzzz.autotest.helpers.HelperNavigation;
import com.botmasterzzz.autotest.helpers.base.HelperWithWebDriverBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperXPath extends HelperWithWebDriverBase {

    private static final Logger logger = LoggerFactory.getLogger(HelperXPath.class);

    public HelperXPath(ApplicationManager app) {
        super(app);
    }

    protected HelperClick click() {
        return app.getHelperClick();
    }

    protected HelperNavigation navi() {
        return app.getHelperNavigation();
    }
}
