package skypiea.com.myapplication.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import skypiea.com.myapplication.R;
import skypiea.com.myapplication.util.UIUtil;

/**
 * Created by ${tamam} on 08/02/2017.
 */

public class Pull2RefreshListview extends ListView implements AbsListView.OnScrollListener {

    int headerHeight;
    private Refresh_More refresh_more;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private boolean allowActionMove;
    private TextView refreshTime;
    private TextView refreshTitle;
    private ImageView indicator;
    private ProgressBar progress;

    public Pull2RefreshListview(Context context) {
        super(context);
        init();
    }

    public Pull2RefreshListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pull2RefreshListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        setHeader();
        refreshTime = (TextView) header.findViewById(R.id.refresh_time);
        refreshTitle = (TextView) header.findViewById(R.id.refresh_title);
        indicator = (ImageView) header.findViewById(R.id.indicator);
        progress = (ProgressBar) header.findViewById(R.id.progress);
        setOnScrollListener(this);
        setRefreshTime();

    }

    private void setRefreshTime() {
        long longData = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String format = simpleDateFormat.format(longData);
        refreshTime.setText(format);
    }


    View header;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    private void setHeader() {
        header = View.inflate(getContext(), R.layout.pull2refresh_header, null);
        addHeaderView(header);
        header.measure(100, 100);
        headerHeight = header.getMeasuredHeight();
        System.out.println("measuredHeight\t" + headerHeight);
        header.setPadding(0, -headerHeight, 0, 0);


    }

    private void 最傻瓜式求view的高度() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    SystemClock.sleep(20);
                    i++;
                    int height = header.getHeight();
                    int measuredHeight = header.getMeasuredHeight();
                    if (height != 0) {
                        System.out.println("height\t" + height + "\t" + i);
                        return;
                    } else if (measuredHeight != 0) {
                        System.out.println("measuredHeight\t" + measuredHeight + "\t" + i);
                        return;
                    }
                }
            }
        }).start();
    }

    public void setRefreshListener(Refresh_More refresh_more) {
        this.refresh_more = refresh_more;
    }

    public interface Refresh_More {
        public Result refresh();

        public void loadMore();
    }

    public enum Result {
        empty, emptyMore, networkError, success, nothing;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getFirstVisiblePosition() == 0) {
            System.out.println("getFirstVisiblePosition  0\t");
            ;
        }
    }

    Result refresh;
    Handler handler = new Handler();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getFirstVisiblePosition() == 0) {
                    startX = ev.getX();
                    startY = ev.getY();
                    allowActionMove = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();
                float moveX = endX - startX;
                float moveY = endY - startY;
                if (allowActionMove && moveY > 0) {

                    if (Math.abs(moveY) > Math.abs(moveX) && Math.abs(moveX) < 6 && header.getPaddingTop() < 0) {
                        System.out.println("move vertical orientation\t" + moveX);
                        header.setPadding(0, (int) (header.getPaddingTop() + moveY), 0, 0);

                    }
                    if (header.getPaddingTop() >= 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setVisibility(VISIBLE);
                                        indicator.setVisibility(GONE);
                                    }
                                });
                                allowActionMove = false;
                                SystemClock.sleep(2000);
                                refresh = refresh_more.refresh();
                                if (refresh == Result.success) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            setRefreshTime();
                                            progress.setVisibility(GONE);
                                            indicator.setVisibility(VISIBLE);
                                        }
                                    });
                                    refresh = Result.nothing;
                                    allowActionMove = false;

                                } else {
                                    allowActionMove = true;
                                }


                            }
                        }).start();

                    }
                    startX = endX;
                    startY = endY;
                }

                break;
            case MotionEvent.ACTION_UP:
                startX = 0;
                startY = 0;
                allowActionMove = false;
//                header.setPadding(0, -headerHeight, 0, 0);
                translationY(getPaddingTop());
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public void translationY(int fromY) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(fromY, -headerHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                header.setPadding(0, animatedValue, 0, 0);
            }
        });
        valueAnimator.start();
    }

}
