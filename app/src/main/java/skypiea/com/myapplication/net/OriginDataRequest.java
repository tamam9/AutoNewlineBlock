package skypiea.com.myapplication.net;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import skypiea.com.myapplication.util.StreamUtil;

/**
 * Created by ${tamam} on 05/02/2017.
 */

public class OriginDataRequest {

    public static final String POST = "POST", GET = "GET";


    private HttpURLConnection urlConnection;

    public OriginDataRequest(final String urlAddress, final String requestType, final ResponseListener responseListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(1000 * 5);
                    urlConnection.setConnectTimeout(1000 * 5);
                    urlConnection.setRequestMethod(requestType);
                    if (urlConnection.getResponseCode() == 200) {
                        responseListener.onResponse(StreamUtil.getString(urlConnection.getInputStream()).toString());
                    } else {
                        responseListener.onError(StreamUtil.getString(urlConnection.getErrorStream()).toString());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
