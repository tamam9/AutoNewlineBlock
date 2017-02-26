package skypiea.com.myapplication.net;

/**
 * Created by ${tamam} on 05/02/2017.
 */

public interface ResponseListener {
    public void onResponse(String json);

    public void onError(String json);
}
