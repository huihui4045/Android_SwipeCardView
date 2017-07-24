package com.huihui.swipecardview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by gavin
 * Time 2017/7/24  17:18
 * Email:molu_clown@163.com
 */

public class SwipeCardCallBack extends ItemTouchHelper.Callback {

    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private List mDatas;


    public SwipeCardCallBack(RecyclerView mRv, RecyclerView.Adapter mAdapter, List mDatas) {
        this.mRv = mRv;
        this.mAdapter = mAdapter;
        this.mDatas = mDatas;

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END|ItemTouchHelper.UP | ItemTouchHelper.DOWN;


        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        Object object = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0,object);
        mAdapter.notifyItemMoved(viewHolder.getLayoutPosition(), 0);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX* dX + dY * dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }

        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i ++) {
            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;

            if (level >= 0) {
                if (level < CardConfig.MAX_SHOW_COUNT -1) {
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP *level - fraction * CardConfig.TRANS_Y_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction *CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction *CardConfig.SCALE_GAP));
//                    view.setAlpha((float) (1 - 0.1 * level + fraction *CardConfig.SCALE_GAP));
                } else if (level == CardConfig.MAX_SHOW_COUNT -1){ // 控制的是最下层的Item
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP *(level -1)));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * (level- 1)));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * (level -1)));
                }
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}
