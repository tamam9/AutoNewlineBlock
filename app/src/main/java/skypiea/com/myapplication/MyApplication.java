package skypiea.com.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by ${tamam} on 24/02/2017.
 */

public class MyApplication extends Application {

    public static Context applicationContext;
    public static Handler handler;
    public static int mainThreadID;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        handler = new Handler();
        mainThreadID = android.os.Process.myTid();
    }
}
