package skypiea.com.myapplication.act;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import skypiea.com.myapplication.R;
import skypiea.com.myapplication.frag.BlankFrag;
import skypiea.com.myapplication.util.LogUtils;

public class FragmentLifeAct extends AppCompatActivity implements BlankFrag.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life);
//        LogUtils.i("activity oncreate");

    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        LogUtils.i("activity oncreateview");
        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        LogUtils.i("activity onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LogUtils.i("activity onresume");
//        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_life, BlankFrag.newInstance("", "")).commit();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
