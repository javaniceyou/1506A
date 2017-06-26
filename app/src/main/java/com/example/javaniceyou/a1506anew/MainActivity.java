package com.example.javaniceyou.a1506anew;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private int currentPosition = 0;
    private ListView listview;
    private List<Integer> dataSource = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("key","jin e");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 10; i++) {
            dataSource.add(i);
        }
        //第一步：创建listview的实例
        listview = (ListView) this.findViewById(R.id.listview);
        //第二步：创建适配器的实例
       customAdapter = new CustomAdapter(MainActivity.this,dataSource);
        //第三步：绑定适配器
        listview.setAdapter(customAdapter);

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //获取到当前屏幕最后一个可见的item的位置
                int lastVisiblePosition = listview.getLastVisiblePosition();
//                int lastVisiblePosition = firstVisibleItem + visibleItemCount;
                //如果当前屏幕的最后一个条目的位置和listView的总条目数相等就说明已经
                //看完了所有的数据
                if (lastVisiblePosition+1 == totalItemCount) {
                    if (listview.getFooterViewsCount() <= 1) {
                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        View footer_view = inflater.inflate(R.layout.footer_view, null);

                        listview.addFooterView(footer_view);
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = lastVisiblePosition+1; i <= lastVisiblePosition+  10; i++) {
                        dataSource.add(i);//向数据源中添加下一批数据
                    }
/*
                    for (int i = dataSource.size(); i < dataSource.size() + 10; i++) {
                        dataSource.add(i);//向数据源中添加下一批数据
                    }
*/
                    //通知listview数据源的数据发生了变化，让listview重新加载数据
                    customAdapter.notifyDataSetChanged();
                    currentPosition = lastVisiblePosition;
                    //当下一批数据加载完毕之后让listview滚动到加载数据之前的位置
                    listview.smoothScrollToPosition(currentPosition);
                }
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
    }



    /**
     * Created by javaniceyou on 2017/6/21.
     */
    public class CustomAdapter extends BaseAdapter {
        Context context;
        List<Integer> dataSource;

        public CustomAdapter(Context context, List<Integer> dataSource) {
            this.context = context;
            this.dataSource = dataSource;
        }


        @Override
        public int getCount() {//获取item的总数,根据数据源的长度而定
            return dataSource.size();
        }

        @Override
        public Object getItem(int position) {//获取每个item的内容
            return dataSource.get(position);
        }

        @Override
        public long getItemId(int position) {//获取每个item的ID
            return position;
        }

        /**
         * 表示给每个item创建一个视图，此视图用于展示数据源中的数据
         *
         * @param position    item的位置
         * @param convertView 表示被隐藏的item的视图
         * @param parent      表示item的父视图
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_view, null);

                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(android.R.id.title);
                viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            viewHolder.title.setText("jIJIJ");

            Picasso.with(context).load("http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=28&gp=0.jpg").into(viewHolder.pic);
            return convertView;
        }
    }

    class  ViewHolder{
        TextView title;
        ImageView pic;
    }
}
