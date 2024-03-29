package com.yzchat.socket.view.adapter;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import com.yzchat.socket.base.ChatBaseViewData;


/**
 * 基础viewHolder
 */
public class PLVBaseViewHolder<Data extends ChatBaseViewData, Adapter extends PLVBaseAdapter> extends RecyclerView.ViewHolder {
    protected Adapter adapter;
    protected Data data;

    public PLVBaseViewHolder(View itemView, Adapter adapter) {
        super(itemView);
        this.adapter = adapter;
    }

    protected int getVHPosition() {
        int position = 0;//item 移动时 position 需更新
        for (int i = 0; i < adapter.getDataList().size(); i++) {
            Object obj = adapter.getDataList().get(i);
            if (obj == data) {
                position = i;
                break;
            }
        }
        return position;
    }

    public void processData(Data data, int position) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }
}
