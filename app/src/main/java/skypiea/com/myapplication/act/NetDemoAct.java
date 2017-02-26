package skypiea.com.myapplication.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import skypiea.com.myapplication.net.OriginImgHelper;

public class NetDemoAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_net_demo);
//        OriginDataRequest request = new OriginDataRequest("http://op.juhe.cn/onebox/news/query", OriginDataRequest.GET, new ResponseListener() {
//            @Override
//            public void onResponse(String json) {
//                System.out.println(json+"----");
//            }
//
//            @Override
//            public void onError(String json) {
//
//            }
//        });

        ImageView imageView = new ImageView(getApplicationContext());
        OriginImgHelper originImgRequest = new OriginImgHelper();
        originImgRequest.originImgRequest("http://img.mp.itc.cn/upload/20170203/b68df0df5aaf44439897f47f0263a353_th.jpg", imageView);
        setContentView(imageView);


    }
}
