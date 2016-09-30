package sensation.snapread.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by Alan on 2016/9/28.
 */
public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
