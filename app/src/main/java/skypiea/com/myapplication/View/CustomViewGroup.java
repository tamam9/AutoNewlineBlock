package skypiea.com.myapplication.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import skypiea.com.myapplication.util.LogUtils;
import skypiea.com.myapplication.util.UIUtil;

/**
 * Created by ${tamam} on 24/02/2017.
 */

public class CustomViewGroup extends ViewGroup {
    private ArrayList<String> listNames = new ArrayList<>();
    int totalViewWidth, totalViewHeight;
    private int valideWidthSize;
    Line line = new Line();
    private boolean firstChange = true;

    public CustomViewGroup(Context context) {
        this(context, null, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.i("onlayout\t" + l + "\t" + t + "\t" + r + "\t" + b);

        int top = 0;
        for (Map.Entry<String, ArrayList<View>> entries : line.columns.entrySet()
                ) {

            if (!entries.getKey().equals("0")) {
                int i = Integer.valueOf(entries.getKey()) - 1;
                top += line.lineHeights.get(String.valueOf(i));
            }
            LogUtils.i("onlayout entreies key " + entries.getKey());
            int increateWidth = UIUtil.Dip2Pix(10);
            for (int i = 0; i < entries.getValue().size(); i++) {
                View childAt = entries.getValue().get(i);
                int left = increateWidth;
                int measuredWidth = childAt.getMeasuredWidth();
                int measureHeight = childAt.getMeasuredHeight();
                int right = left + measuredWidth;
                LogUtils.i("onlayout left " + left + " right " + right + " top " + top + " bottom " + (top + measureHeight));

                childAt.layout(left, top, right, top + measureHeight);
                increateWidth = right;
//                if (i != entries.getValue().size() - 1 && entries.getValue().size() > 1) {
//                    increateWidth += UIUtil.Dip2Pix(10);
//                }
            }
        }


    }


    private void init() {

        listNames.clear();
        initData();
        totalViewHeight = 0;
        totalViewWidth = 0;
        line = new Line();
        int viewGroupLeftMargin = UIUtil.Dip2Pix(10);
        int viewGroupRightMargin = UIUtil.Dip2Pix(10);
        totalViewWidth = viewGroupLeftMargin + viewGroupRightMargin;
        int cursurWidth = 0;
        int columnIndex = 0;
        for (String name : listNames
                ) {

            TextView textView = getTextView(name);
            textView.measure(0, 0);
            int measuredWidth = textView.getMeasuredWidth();
            int measuredHeight = textView.getMeasuredHeight();
            if (cursurWidth + measuredWidth > valideWidthSize) {
                totalViewWidth = valideWidthSize;
                cursurWidth = measuredWidth;
                line.views = new ArrayList<>();
                columnIndex++;
                line.addiew(String.valueOf(columnIndex), textView);
            } else {
                if (totalViewWidth < valideWidthSize) {
                    totalViewWidth += measuredWidth;
                }
                cursurWidth += measuredWidth;
                line.addiew(String.valueOf(columnIndex), textView);
            }


            addView(textView);
        }
        Collection<Integer> values = line.lineHeights.values();
        for (Integer iterator : values
                ) {
            totalViewHeight += iterator;
        }
        LogUtils.i("totalVIewWidth\t" + totalViewWidth);
        LogUtils.i("totalViewHeight\t" + totalViewHeight);

    }

    private void initData() {
        listNames.add("remus");
        listNames.add("R+");
//        listNames.add("Inspiri");
//        listNames.add("Badar");
//        listNames.add("K2");
//        listNames.add("remus");
//        listNames.add("R+");
//        listNames.add("Terox");
//        listNames.add("Badar");
//        listNames.add("K2");
//        listNames.add("tarux");
//        listNames.add("R+");
//        listNames.add("Terox");
//        listNames.add("Badar");
//        listNames.add("K2");
//        listNames.add("tarux");
    }

    @NonNull
    private TextView getTextView(String name) {
        TextView textView = new TextView(getContext());
        textView.setText(name);
        Random random = new Random();
        int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        textView.setTextColor(rgb);
        textView.setPadding(UIUtil.Dip2Pix(10), 0, UIUtil.Dip2Pix(10), 0);
        textView.setTextSize(UIUtil.Sp2Pix(14)/* + random.nextInt(UIUtil.Dip2Pix(10))*/);
        setRoundRectBg(textView);
        return textView;
    }

    public void setRoundRectBg(TextView textView) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.CYAN);
        gradientDrawable.setCornerRadius(UIUtil.Dip2Pix(10));
        gradientDrawable.setStroke(UIUtil.Dip2Pix(1), Color.BLACK);
        textView.setBackground(gradientDrawable);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        valideWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        init();
        if (widthMode != MeasureSpec.EXACTLY) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(totalViewWidth, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalViewHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public class Line {
        public ArrayList<View> views = new ArrayList<>();
        public LinkedHashMap<String, ArrayList<View>> columns = new LinkedHashMap<>();
        public HashMap<String, Integer> lineHeights = new HashMap<>();

        public void addiew(String column, View view) {
            views.add(view);
            columns.put(column, views);
            int measuredHeight = view.getMeasuredHeight();
            Integer integer = lineHeights.get(column);
            if (integer == null) {
                integer = 0;
            }
            if (integer < measuredHeight) {
                lineHeights.put(column, measuredHeight);
            } else {
                lineHeights.put(column, integer);
            }
        }
    }


}
