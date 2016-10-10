package sensation.snapread.model;

import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by Alan on 2016/9/28.
 */
public class MyApplication extends LitePalApplication {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
