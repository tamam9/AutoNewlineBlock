package skypiea.com.myapplication.act;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import skypiea.com.myapplication.R;
import skypiea.com.myapplication.View.MIndicator;
import skypiea.com.myapplication.util.DisplayUtil;

public class IndicatorActCustomize extends AppCompatActivity {


    @Bind(R.id.mcontainer)
    MIndicator mcontainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_act_customize);
        ButterKnife.bind(this);

        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<String> linkUrls = new ArrayList<>();
        ArrayList<String> webImgs = new ArrayList<>();
        webImgs.add("http://img.mp.itc.cn/upload/20170203/b68df0df5aaf44439897f47f0263a353_th.jpg");
        webImgs.add("http://img0.imgtn.bdimg.com/it/u=862796872,3271520264&fm=23&gp=0.jpg");
        webImgs.add("http://img2.imgtn.bdimg.com/it/u=2645831278,3922525934&fm=23&gp=0.jpg");
        for (int i = 0; i < webImgs.size(); i++) {
            titles.add("title\t" + i);
            linkUrls.add("linkUrl\t" + i);
        }
        mcontainer.setWebImage(titles, webImgs, linkUrls);
        mcontainer.setOnItemClickListener_(new MIndicator.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(String linkUrl) {
                DisplayUtil.showToast(getApplicationContext(), linkUrl);
            }
        });


    }


}
