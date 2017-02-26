package skypiea.com.myapplication.net;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ${tamam} on 05/02/2017.
 */

public class OriginImgHelper {

    private HttpURLConnection urlConnection;
    Handler handler = new Handler();
    private Bitmap bitmap;

    public void originImgRequest(final String urlAddress, final ImageView imageView, final ImageView.ScaleType... scaleTypes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(1000 * 5);
                    urlConnection.setConnectTimeout(1000 * 5);
                    urlConnection.setRequestMethod("GET");
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                imageView.setScaleType(scaleTypes.length > 0 ? scaleTypes[0] : ImageView.ScaleType.FIT_XY);
                            }
                        });


                    } else {
                        System.out.println("图片加载失败");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void originImgCallBack(final String urlAddress, final ImageView imageView, final RequestDone requestDone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(1000 * 5);
                    urlConnection.setConnectTimeout(1000 * 5);
                    urlConnection.setRequestMethod("GET");
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        requestDone.getData(bitmap);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                        });


                    } else {
                        System.out.println("图片加载失败");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface RequestDone<T> {
        public void getData(T data);
    }

}
