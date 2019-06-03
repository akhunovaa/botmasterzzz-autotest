package fw.impl;

import fw.ApplicationManager;

public class ApplicationManagerImplementation implements ApplicationManager {

    static ApplicationManager singleton;

    public static ApplicationManager getInstance() {
        if (singleton == null) {
            singleton = new ApplicationManagerImplementation();
        }
        return singleton;
    }
}
