package com.yzchat.socket.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzchat.socket.R;
import com.yzchat.socket.base.ChatBaseViewData;


/**
 * 图片信息viewHolder
 */
public class PLVECChatMessageImgViewHolder extends  PLVECChatMessageCommonViewHolder<ChatBaseViewData, PLVECChatMessageAdapter> {
    private ImageView chatImgIv;
    private TextView chatMsgTv;

    public PLVECChatMessageImgViewHolder(View itemView, final PLVECChatMessageAdapter adapter) {
        super(itemView, adapter);
        chatImgIv = findViewById(R.id.chat_img_iv);
        chatImgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.callOnChatImgClick(v, chatImgUrl);
            }
        });
        chatMsgTv = findViewById(R.id.chat_msg_tv);
    }

    @Override
    public void processData(ChatBaseViewData data, int position) {
        super.processData(data, position);
        fitChatImgWH(chatImgWidth, chatImgHeight, chatImgIv, 64, 36);
//        PLVImageLoader.getInstance().loadImage(itemView.getContext(), chatImgUrl,
//                R.drawable.plvec_img_site, R.drawable.plvec_img_site, chatImgIv);
        chatMsgTv.setText(nickSpan);
    }
}
