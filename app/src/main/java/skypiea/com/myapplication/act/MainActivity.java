package skypiea.com.myapplication.act;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;

import skypiea.com.myapplication.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String[] stringArray = getResources().getStringArray(R.array.colleagues);
//        for (String s : stringArray
//                ) {
//            System.out.println("llll\t" + s);
//        }
    }


    public void slide_act(View view) {
        startActivity(new Intent(this, SlideAct.class));
    }

    public void custimize_indicator(View view) {
        startActivity(new Intent(this, IndicatorActCustomize.class));
    }

    public void network_act(View view) {
        startActivity(new Intent(this, NetDemoAct.class));

    }

    public void pull2refreshAct(View view) {
        startActivity(new Intent(this, Pull2RefreshAct.class));

    }

    public void fragment_life_act(View view) {
        startActivity(new Intent(this, FragmentLifeAct.class));
    }

//
//    class MPageAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
//        }
//    }

    class MViewpagerAdapter extends FragmentPagerAdapter {
        public MViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
