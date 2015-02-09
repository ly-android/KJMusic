package org.kymjs.music;

import org.kymjs.kjframe.utils.CrashHandler;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Application;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KJLoger.openActivityState(true);
        KJLoger.openDebutLog(true);
        CrashHandler.create(this);
    }
}
