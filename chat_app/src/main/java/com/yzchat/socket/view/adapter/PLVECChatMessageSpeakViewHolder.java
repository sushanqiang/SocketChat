package com.yzchat.socket.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.yzchat.socket.R;
import com.yzchat.socket.base.ChatBaseViewData;


/**
 * 发言信息viewHolder
 */
public class PLVECChatMessageSpeakViewHolder extends PLVECChatMessageCommonViewHolder<ChatBaseViewData, PLVECChatMessageAdapter> {
    private TextView chatMsgTv;

    public PLVECChatMessageSpeakViewHolder(View itemView, PLVECChatMessageAdapter adapter) {
        super(itemView, adapter);
        chatMsgTv = findViewById(R.id.chat_msg_tv);
    }

    @Override
    public void processData(ChatBaseViewData data, int position) {
        super.processData(data, position);
        chatMsgTv.setText(nickSpan.append(speakMsg));
    }
}
