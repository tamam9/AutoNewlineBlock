package skypiea.com.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import skypiea.com.myapplication.R;
import skypiea.com.myapplication.util.DisplayUtil;
import skypiea.com.myapplication.util.LogUtils;

/**
 * Created by ${tamam} on 04/02/2017.
 */

public class MIndicator extends RelativeLayout {
    @Bind(R.id.container)
    ViewPager container;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.indicator)
    LinearLayout indicator;
    @Bind(R.id.red_spot)
    ImageView redSpot;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private ArrayList<String> titles;
    private int count;
    private boolean isLoopable = true;
    private int redSpotDistance;
    private boolean isSliding = false;
    private Thread autoThread;
    private boolean killAutoThread = false;
    private ArrayList<String> webImgs = new ArrayList<>();
    private ArrayList<Integer> localImgs = new ArrayList<>();
    private int imgTtype;
    private final int LOCAL = 1;
    private final int WEB = 2;
    private ArrayList<String> linkUrls;
    private OnItemClickListener onItemClickListener_;

    public MIndicator(Context context) {
        super(context);
        init();
    }

    public MIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MIndicator);
        isLoopable = typedArray.getBoolean(R.styleable.MIndicator_isLoop, true);
        killAutoThread = !typedArray.getBoolean(R.styleable.MIndicator_autoLoop, true);
        init();
    }

    public MIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MIndicator);
        isLoopable = typedArray.getBoolean(R.styleable.MIndicator_isLoop, true);
        killAutoThread = !typedArray.getBoolean(R.styleable.MIndicator_autoLoop, true);
        init();
    }


    private void init() {
        View inflate = View.inflate(getContext(), R.layout.indicator_layout, this);
        ButterKnife.bind(this, inflate);
    }

    /**
     * 观察indicator layout 是否发生变化
     * 只要indicator画好了取里面元素之间的距离
     */
    private void observeIndicatorLayout() {
        indicator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                indicator.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                redSpotDistance = indicator.getChildAt(1).getLeft() - indicator.getChildAt(0).getLeft();
            }
        });
    }

    public void setLocalImage(ArrayList<String> titles, ArrayList<Integer> imgs) {
        observeIndicatorLayout();
        if (titles.size() != imgs.size()) {
            System.exit(0);
            throw new RuntimeException("title集合和img集合数量不相同");
        }
        this.titles = titles;
        count = titles.size();
        this.localImgs = imgs;
        imgTtype = LOCAL;
        load();
        autoLoad();
    }

    public void setWebImage(ArrayList<String> titles, ArrayList<String> imgs, ArrayList<String> linkUrls) {
        observeIndicatorLayout();
        if (titles.size() != imgs.size()) {
            System.exit(0);
            throw new RuntimeException("title集合和img集合数量不相同");
        }
        this.titles = titles;
        this.linkUrls = linkUrls;
        count = titles.size();
        this.webImgs = imgs;
        imgTtype = WEB;
        load();
        autoLoad();

    }


    private void load() {
        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(localImgs, webImgs);
        container.setAdapter(myViewpagerAdapter);

        title.setText(titles.get(0));

        for (int i = 0; i < count; i++) {
            indicator.addView(getRedSpotImg(i));
        }
        if (isLoopable) {
            int i = Integer.MAX_VALUE / 2;
            for (int j = 0; j < count; j++) {
                if ((i + j) % count == 0) {
                    container.setCurrentItem(i + j);
                    break;
                }

            }
        } else {
            container.setCurrentItem(0);
        }


        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("positon  " + position + ";\t\t" + "positonOffset  " + positionOffset + ";\t\t" + "positonOffsetPixels  " + positionOffsetPixels + ";\t");
                if (position % count < count - 1 || (position % count == count - 1 && positionOffset == 0)) {
                    int left = (int) (position % count * redSpotDistance + positionOffset * redSpotDistance);
//                    System.out.println("left\t" + left);
                    LayoutParams layoutParams = (LayoutParams) redSpot.getLayoutParams();
                    layoutParams.leftMargin = left;
                    redSpot.setLayoutParams(layoutParams);
                }

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles.get(position % count));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    isSliding = true;
                } else {
                    isSliding = false;
                }
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.i("onMeasure onMeasure");
    }

    private void autoLoad() {
        final Handler handler = new Handler();


        autoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!killAutoThread) {
                    if (!isSliding) {
                        SystemClock.sleep(4000);
                        if (!isSliding && !killAutoThread) {
//                            System.out.println("自动加载子线程 MIndicator\t" + i++);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    container.setCurrentItem(container.getCurrentItem() + 1);

                                }
                            });

                        }

                    }

                }
            }
        });
        autoThread.start();
    }


    private ImageView getRedSpotImg(int i) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (i != 0) {
            layoutParams.leftMargin = DisplayUtil.dip2px(getContext(), 10);
        }
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.red_spot_sl);
        return imageView;
    }

    private class MyViewpagerAdapter extends PagerAdapter {

        private ArrayList<Integer> localImgs;
        private ArrayList<String> webImgs;


        public MyViewpagerAdapter(ArrayList<Integer> localImgs, ArrayList<String> webImgs) {
            this.localImgs = localImgs;
            this.webImgs = webImgs;
        }


        @Override
        public int getCount() {
            if (isLoopable) {
                return Integer.MAX_VALUE;
            }
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = position % count;
            final ImageView imageView = new ImageView(getContext());
            if (imgTtype == LOCAL) {
                imageView.setBackgroundResource(localImgs.get(i));
            } else if (imgTtype == WEB) {
                String saveUrl = getContext().getCacheDir().toString();
                saveUrl += getFileName(webImgs.get(i));
                if (!findImgInCache(new File(saveUrl))) {
                    final String finalSaveUrl = saveUrl;
                    originImgGetBitmap(webImgs.get(i), imageView, new MIndicator.RequestDone<Bitmap>() {
                        @Override
                        public void getData(Bitmap bitmap) {
                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = new FileOutputStream(finalSaveUrl);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

                        }
                    });


                } else {
                    final String finalSaveUrl1 = saveUrl;
                    final Handler handler = new Handler();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FileInputStream fileInputStream = null;
                            try {
                                fileInputStream = new FileInputStream(finalSaveUrl1);
                                final Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(bitmap);

                                    }
                                });
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();

                }

            }
            setClickListener(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void setClickListener(ImageView imageView) {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = container.getCurrentItem();
                int i = currentItem % count;
                String s = linkUrls.get(i);
                if (!TextUtils.isEmpty(s) && onItemClickListener_ != null) {
                    onItemClickListener_.setOnItemClickListener(s);
                }
            }
        });

    }


    private boolean findImgInCache(File cacheFile) {
        File[] files = getContext().getCacheDir().listFiles();
        if (files != null) {
            for (File file : files
                    ) {
                if (file.isDirectory()) {
                    findImgInCache(file);
                } else if (file.getAbsolutePath().equals(cacheFile.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getFileName(String title) {
        int i = title.lastIndexOf("/");
        String substring = title.substring(i);
        return substring;
    }

    /**
     * 调用View者等调用完毕后必须结束 autoThread
     */
    public void stopAutoThread() {
        killAutoThread = true;
    }


    /**
     * 从网络获取图片 并回调bitmap
     *
     * @param urlAddress
     * @param imageView
     * @param requestDone
     */
    private void originImgGetBitmap(final String urlAddress, final ImageView imageView, final MIndicator.RequestDone requestDone) {

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(1000 * 5);
                    urlConnection.setConnectTimeout(1000 * 5);
                    urlConnection.setRequestMethod("GET");
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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

    private interface RequestDone<T> {
        void getData(T data);
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(String linkUrl);
    }


    public void setOnItemClickListener_(OnItemClickListener onItemClickListener_) {
        this.onItemClickListener_ = onItemClickListener_;
    }

}
