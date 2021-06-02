package com.yzchat.socket.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.yzchat.socket.R;
import com.yzchat.socket.base.ChatBaseViewData;


/**
 * 自定义礼物打赏viewHolder
 */
public class PLVECChatMessageCustomGiftViewHolder extends  PLVECChatMessageCommonViewHolder<ChatBaseViewData, PLVECChatMessageAdapter> {
    private TextView chatMsgTv;

    public PLVECChatMessageCustomGiftViewHolder(View itemView, PLVECChatMessageAdapter adapter) {
        super(itemView, adapter);
        chatMsgTv = findViewById(R.id.chat_msg_tv);
    }

    @Override
    public void processData(ChatBaseViewData data, int position) {
        super.processData(data, position);
        chatMsgTv.setText(speakMsg);
    }
}
