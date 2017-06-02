package com.panku.zhengtest.springview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.springview)
    SpringView springview;
    private List<String> mDatas = new ArrayList<String>();
    private RecyclerViewAdapter adapter;

    //仿美团的动画效果
    private int[] pullAnimSrcs = new int[]{R.drawable.mt_pull, R.drawable.mt_pull01, R.drawable.mt_pull02, R.drawable.mt_pull03, R.drawable.mt_pull04, R.drawable.mt_pull05};
    private int[] refreshAnimSrcs = new int[]{R.drawable.mt_refreshing01, R.drawable.mt_refreshing02, R.drawable.mt_refreshing03, R.drawable.mt_refreshing04, R.drawable.mt_refreshing05, R.drawable.mt_refreshing06};
    private int[] loadingAnimSrcs = new int[]{R.drawable.mt_loading01, R.drawable.mt_loading02};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, mDatas);
        recycle.setAdapter(adapter);
        recycle.setItemAnimator(new DefaultItemAnimator());//动画

        //设置下拉刷新 上拉加载更多
        refresh();

        //下拉刷新加载更多第一种样式
//        springview.setHeader(new AliHeader(this, R.drawable.ali, true));   //参数为：logo图片资源，是否显示文字
//        springview.setFooter(new AliFooter(this, true));

        //下拉刷新加载更多第二种样式
//        springview.setHeader(new RotationHeader(this));
//        springview.setFooter(new RotationFooter(this));


        //下拉刷新加载更多第三种样式
        springview.setHeader(new DefaultHeader(this));
        springview.setFooter(new DefaultFooter(this));


        //下拉刷新加载更多第四种样式
//        springview.setHeader(new MeituanHeader(this, pullAnimSrcs, refreshAnimSrcs));
//        springview.setFooter(new MeituanFooter(this, loadingAnimSrcs));
    }

    private void refresh() {
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //刷新   这里模拟一下
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新或加载更多完成后调用
                        springview.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                //加载更多  这里模拟一下
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新或加载更多完成后调用
                        springview.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mDatas.add(i + "我是item");
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SampleViewHolder> {
        //将数据源传进来
        private List<String> results;
        private Context context;

        public RecyclerViewAdapter(Context context, List<String> results) {
            this.results = results;
            this.context = context;

        }

        //初始化view  返回ViewHolder
        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = View.inflate(context, R.layout.item, null);
            return new SampleViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(SampleViewHolder holder, int position) {
            //设置内容
            holder.tv.setText(results.get(position));
            if (position % 2 == 1) {
                holder.tv.setBackgroundColor(Color.parseColor("#e3f1fc"));
                holder.tv.setTextColor(Color.parseColor("#9dd2fc"));
            } else {
                holder.tv.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tv.setTextColor(Color.parseColor("#cccccc"));
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        //ViewHolder中维护了view   也就是item
        public class SampleViewHolder extends RecyclerView.ViewHolder {

            public final TextView tv;

            public SampleViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_text);
            }
        }
    }

}
