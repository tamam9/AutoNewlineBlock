package skypiea.com.myapplication.act;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import skypiea.com.myapplication.R;
import skypiea.com.myapplication.View.Pull2RefreshListview;
import skypiea.com.myapplication.util.ListUtil;

/**
 * Created by ${tamam} on 08/02/2017.
 */

public class Pull2RefreshAct extends AppCompatActivity implements Pull2RefreshListview.Refresh_More {
    @Bind(R.id.listview)
    Pull2RefreshListview listview;
    ArrayList<String> goods = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull2refresh_act);
        ButterKnife.bind(this);
        loadData(0, 20);
        myAdapter = new MyAdapter(goods, getApplicationContext());
        listview.setAdapter(myAdapter);
        listview.setRefreshListener(this);
    }

    private void loadData(int start, int size) {
        for (int i = start; i < start + size; i++) {
            goods.add(refreshVersion + "\tgood_" + i);
        }
    }

    public void moreData() {

        loadData(goods.size(), 20);
        myAdapter.notifyDataSetChanged();
    }

    public void more(View view) {
        moreData();
    }

    public void refresh(View view) {
        refresh();
    }

    int refreshVersion;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public Pull2RefreshListview.Result refresh() {
        refreshVersion = (int) (Math.random() * 10) + 1;
        goods.clear();
        for (int i = 0; i < 20; i++) {
            goods.add(refreshVersion + "\tgood_" + i);
        }
        handler.sendEmptyMessage(0);
        return Pull2RefreshListview.Result.success;
    }

    @Override
    public void loadMore() {

    }

    class MyAdapter extends BaseAdapter {

        private final Context context;
        private ArrayList<String> list = new ArrayList<>();

        public MyAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            if (ListUtil.isListEmpty(list)) {
                return null;
            }
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.pull2refresh_item, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String item = list.get(position);
            viewHolder.title.setText(item);

            return convertView;
        }


        class ViewHolder {

            public TextView title;
        }

    }
}
