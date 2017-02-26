package skypiea.com.myapplication.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ${tamam} on 05/02/2017.
 */

public class StreamUtil {
    public static String getString(InputStream inputStream) {
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int length;
            byte[] arr = new byte[1024 * 1024];
            while ((length = bufferedInputStream.read(arr)) != -1) {
                byteArrayOutputStream.write(arr, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                String json = byteArrayOutputStream.toString();
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }
        }

    }
}
