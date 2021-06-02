package com.yzchat.socket.event;

import android.text.SpannableStringBuilder;

/**
 * 自定义打赏礼物事件，用于聊天室列表显示礼物信息
 */
public class PLVCustomGiftEvent {
    private SpannableStringBuilder span;

    public PLVCustomGiftEvent(SpannableStringBuilder span) {
        this.span = span;
    }

    public SpannableStringBuilder getSpan() {
        return span;
    }
}
