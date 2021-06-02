package com.yzchat.socket.view.widget;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yzchat.socket.R;
import com.yzchat.socket.view.adapter.PLVBaseViewHolder;
import com.yzchat.socket.base.ChatBaseViewData;
import com.yzchat.socket.data.PLVCustomGiftBean;
import com.yzchat.socket.utils.ScreenUtils;
import com.yzchat.socket.view.widget.recyclerview.PLVMessageRecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.yzchat.socket.view.adapter.PLVBaseAdapter;

 

/**
 * 打赏礼物列表adapter
 */
public class PLVECRewardGiftAdapter extends PLVBaseAdapter<ChatBaseViewData, PLVBaseViewHolder<ChatBaseViewData,
        PLVECRewardGiftAdapter>> {
    private List<ChatBaseViewData> dataList;
    private View lastSelectView;

    public PLVECRewardGiftAdapter() {
        dataList = new ArrayList<>();
    }

    @Override
    public List<ChatBaseViewData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ChatBaseViewData> dataList) {
        this.dataList = dataList;
    }

    public View getLastSelectView() {
        return lastSelectView;
    }

    public void setLastSelectView(View lastSelectView) {
        this.lastSelectView = lastSelectView;
    }

    // <editor-fold defaultstate="collapsed" desc="点击事件">
    private OnViewActionListener onViewActionListener;

    public void setOnViewActionListener(OnViewActionListener listener) {
        this.onViewActionListener = listener;
    }

    public interface OnViewActionListener {
        void onRewardClick(View view, PLVCustomGiftBean giftBean);
    }

    public void callOnRewardClick(View view, PLVCustomGiftBean giftBean) {
        if (onViewActionListener != null) {
            onViewActionListener.onRewardClick(view, giftBean);
        }
    }
    // </editor-fold>

    @NonNull
    @Override
    public PLVBaseViewHolder<ChatBaseViewData, PLVECRewardGiftAdapter> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PLVECRewardGiftViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plvec_live_reward_gift_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull PLVBaseViewHolder<ChatBaseViewData, PLVECRewardGiftAdapter> holder, int position) {
        holder.processData(dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull PLVBaseViewHolder<ChatBaseViewData, PLVECRewardGiftAdapter> holder) {
        super.onViewAttachedToWindow(holder);
        //适配item的宽度及间隔
        if (holder.itemView != null && holder.itemView.getParent() instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) holder.itemView.getParent();
            final int width = recyclerView.getWidth();
            if (width > 0 && recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                final int itemWidth = ScreenUtils.dip2px(holder.itemView.getContext(),72);
                if (itemWidth * spanCount <= width) {
                    holder.itemView.getLayoutParams().width = itemWidth;
                    if (recyclerView.getItemDecorationCount() == 0) {
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                if (recyclerView.getItemDecorationCount() == 0) {
                                    int space = width - itemWidth * spanCount;
                                    recyclerView.addItemDecoration(new PLVMessageRecyclerView.GridSpacingItemDecoration(spanCount, (int) (space * 1f / (spanCount - 1)), false, 0));
                                }
                            }
                        });
                    }
                } else {
                    holder.itemView.getLayoutParams().width = width / spanCount;
                }
            }
        }
    }
}
