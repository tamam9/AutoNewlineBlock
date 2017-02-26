package skypiea.com.myapplication.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.view.View;

import skypiea.com.myapplication.MyApplication;

/**
 * Created by ${tamam} on 14/02/2017.
 */

public class UIUtil {

    public static Context getApplicationContext() {
        return MyApplication.applicationContext;
    }

    public static Handler getApplicationHandler() {
        return MyApplication.handler;
    }

    public static int Dip2Pix(float scale) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (scale * density);
    }

    public static int Sp2Pix(float scale) {
        float density = getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scale * density);
    }

    public static int Pix2Dip(int scale) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (scale / density + 0.5f);
    }

    public static int getMainThreadId() {
        return MyApplication.mainThreadID;
    }

    public static boolean isMainThread() {
        int currentThreadId = Process.myPid();
        if (getMainThreadId() == currentThreadId) {
            return true;
        } else {
            return false;
        }
    }

    public static String getString(int id) {
        return getApplicationContext().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getApplicationContext().getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getApplicationContext(), id);
    }


    public static float getDimen(int id) {
        return getApplicationContext().getResources().getDimension(id);
    }

    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getApplicationContext(), id);
    }

    public static View getLayout(int id) {
        return View.inflate(getApplicationContext(), id, null);
    }


    public static ColorStateList getColorStateList(int id) {
        return ContextCompat.getColorStateList(getApplicationContext(), id);
    }

    public static void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            getApplicationHandler().post(runnable);
        }
    }
}
