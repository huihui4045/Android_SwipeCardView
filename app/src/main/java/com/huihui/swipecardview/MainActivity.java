package com.huihui.swipecardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;

import com.huihui.swipecardview.adaper.UniversalAdapter;
import com.huihui.swipecardview.adaper.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;

    private UniversalAdapter<SwipeCardBean> adapter;

    private List<SwipeCardBean> mDatas;

    private SwipeCardLayoutManager cardLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler = ((RecyclerView) findViewById(R.id.recyclerView));


        CardConfig.initConfig(this);


        mDatas = SwipeCardBean.initDatas();

        cardLayoutManager = new SwipeCardLayoutManager();

        mRecycler.setLayoutManager(cardLayoutManager);


        adapter = new UniversalAdapter<SwipeCardBean>(this, mDatas, R.layout.item_swipe_card) {
            @Override
            public void convert(ViewHolder var1, SwipeCardBean var2) {

                var1.setText(R.id.tvName, var2.getName());
                var1.setText(R.id.tvPrecent, var2.getPostition() + "/" + mDatas.size());
                Picasso.with(MainActivity.this)
                        .load(var2.getUrl())
                        .into((ImageView) var1.getView(R.id.iv));
            }
        };
        mRecycler.setAdapter(adapter);

        SwipeCardCallBack cardCallBack=new SwipeCardCallBack(mRecycler,adapter,mDatas);

        ItemTouchHelper touchHelper=new ItemTouchHelper(cardCallBack);

        touchHelper.attachToRecyclerView(mRecycler);


    }
}
