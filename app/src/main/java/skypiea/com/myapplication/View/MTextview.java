package skypiea.com.myapplication.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by ${tamam} on 07/02/2017.
 */

public class MTextview extends TextView {
    public MTextview(Context context) {
        super(context);
        init();

    }

    public MTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        setClickable(true);

    }

    public MTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("MTextview\tdispatchTouchEvent");
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("MTextview\tonTouchEvent");
        return super.onTouchEvent(event);
    }
}
