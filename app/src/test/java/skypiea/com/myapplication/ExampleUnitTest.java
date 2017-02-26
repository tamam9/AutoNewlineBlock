package skypiea.com.myapplication;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        ArrayList<String> list = new ArrayList<String >(1);
        if (list != null && !list.isEmpty()) {
            System.out.println("entity");
        }else {
            System.out.println("empty");
        }

    }
}