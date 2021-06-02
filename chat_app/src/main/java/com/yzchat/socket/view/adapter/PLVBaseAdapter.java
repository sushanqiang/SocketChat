package com.yzchat.socket.view.adapter;


import androidx.recyclerview.widget.RecyclerView;

import com.yzchat.socket.base.ChatBaseViewData;

import java.util.List;


/**
 * 基础adapter
 */
public abstract class PLVBaseAdapter<Data extends ChatBaseViewData, Holder extends PLVBaseViewHolder> extends RecyclerView.Adapter<Holder> {

    public abstract List<Data> getDataList();
}
