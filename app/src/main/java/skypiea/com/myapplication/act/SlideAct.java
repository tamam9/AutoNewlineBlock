package skypiea.com.myapplication.act;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import skypiea.com.myapplication.R;
import skypiea.com.myapplication.frag.HomeFrag;
import skypiea.com.myapplication.frag.SliderFrag;

public class SlideAct extends AppCompatActivity {


    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.slider)
    FrameLayout slider;

    private String CONTAINER = "CONTAINER";
    private String SLIDER = "SLIDER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.slider, SliderFrag.newInstance(null, null), CONTAINER).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFrag.newInstance(null, null), SLIDER).commit();

        int left = slider.getLeft();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) slider.getLayoutParams();
        int leftMargin = layoutParams.leftMargin;

//        System.out.println("left\t" + left);
//        System.out.println("leftMargin\t" + leftMargin);


    }


    float startX;
    float endX;


    @Override
    public void onUserInteraction() {
        System.out.println("SlideAct\tonUserInteraction\t");
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("SlideAct\tonTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                System.out.println("SlideAct\tdispatchTouchEvent\tACTION_DOWN\t" + ((FrameLayout.LayoutParams) slider.getLayoutParams()).leftMargin + "\t");
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                int moveX = (int) (endX - startX);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) slider.getLayoutParams();
                int leftMargin = layoutParams.leftMargin;
                int i = leftMargin + moveX;
                if (i >= 0) {
                    i = 0;
                } else if (i < -slider.getMeasuredWidth()) {
                    i = -slider.getMeasuredWidth();
                }
                layoutParams.leftMargin = i;
                slider.setLayoutParams(layoutParams);
                startX = endX;
                System.out.println("SlideAct\tdispatchTouchEvent\tACTION_MOVE\t" + i);

                break;
            case MotionEvent.ACTION_UP:
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) slider.getLayoutParams();
                layoutParams2 = (FrameLayout.LayoutParams) slider.getLayoutParams();
                System.out.println("SlideAct\tdispatchTouchEvent\tACTION_UP\t" + layoutParams2.leftMargin);
                if (layoutParams2.leftMargin > -slider.getMeasuredWidth() / 2) {
                    ValueAnimator dropAnimator = createDropAnimator(slider, layoutParams2.leftMargin, 0);
                    dropAnimator.start();

                } else {
                    ValueAnimator valueAnimator = createDropAnimator(slider, layoutParams2.leftMargin, -slider.getMeasuredWidth());
                    valueAnimator.start();
                }


                break;
        }
        return true;
    }


    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //   int value = (Integer)valueAnimator.getAnimatedValue();
                        int value = (Integer) valueAnimator.getAnimatedValue();// 得到的值
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = value;
                        view.setLayoutParams(layoutParams);
                    }
                }
        );
        return animator;
    }


}
