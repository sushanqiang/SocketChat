package com.yzchat.socket.view.widget;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.yzchat.socket.R;
import com.yzchat.socket.utils.PLVViewInitUtils;


public class PLVECChatImgScanPopupView {
    private PopupWindow popupWindow;
    private ImageView chatImgIv;

    public void showImgScanLayout(View v, String imgUrl) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            popupWindow.setClippingEnabled(false);

            View view = PLVViewInitUtils.initPopupWindow(v, R.layout.plvec_chat_img_scan_layout, popupWindow, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hide();
                }
            });
            chatImgIv = view.findViewById(R.id.chat_img_iv);
        }
         popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
    }

    public void hide() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
