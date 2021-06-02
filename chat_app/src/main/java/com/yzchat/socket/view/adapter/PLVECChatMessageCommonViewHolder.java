package com.yzchat.socket.view.adapter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.yzchat.socket.base.ChatBaseViewData;
import com.yzchat.socket.data.PLVSocketUserConstant;


/**
 * 聊天室信息共同viewHolder
 */
public class PLVECChatMessageCommonViewHolder<Data extends ChatBaseViewData, Adapter extends PLVBaseAdapter>
        extends PLVChatMessageBaseViewHolder<Data, Adapter> {
    protected SpannableStringBuilder nickSpan;

    public PLVECChatMessageCommonViewHolder(View itemView, Adapter adapter) {
        super(itemView, adapter);
    }

    private void resetParams() {
        nickSpan = null;
    }

    @Override
    public void processData(Data data, int position) {
        super.processData(data, position);
        resetParams();
        generateNickSpan();
    }

    private void generateNickSpan() {
        if (nickName==null){
            nickName="用户";
        }
        if (TextUtils.isEmpty(nickName)) {
            return;
        }
        nickSpan = new SpannableStringBuilder(nickName);
        nickSpan.append(": ");
        nickSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD16B")), 0, nickSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (PLVSocketUserConstant.USERTYPE_TEACHER.equals(userType)) {
            insertActorToNickSpan(Color.parseColor("#F09343"));
        } else if (PLVSocketUserConstant.USERTYPE_ASSISTANT.equals(userType)) {
            insertActorToNickSpan(Color.parseColor("#598FE5"));
        } else if (PLVSocketUserConstant.USERTYPE_GUEST.equals(userType)) {
            insertActorToNickSpan(Color.parseColor("#EB6165"));
        } else if (PLVSocketUserConstant.USERTYPE_MANAGER.equals(userType)) {
            insertActorToNickSpan(Color.parseColor("#33BBC5"));
        }
    }

    private void insertActorToNickSpan(int bgColor) {
        if (TextUtils.isEmpty(userType) || TextUtils.isEmpty(actor)) {
            return;
        }
        nickSpan.insert(0, userType);

    }
}
