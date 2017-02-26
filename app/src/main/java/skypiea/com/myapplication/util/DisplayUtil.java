package skypiea.com.myapplication.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by ${tamam} on 03/02/2017.
 */

public class DisplayUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机字体大小从 SP 转成为 PX
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2Px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static void showToast(final Context context, final String title) {
        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(context, title, Toast.LENGTH_LONG).show();
        } else {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, title, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
