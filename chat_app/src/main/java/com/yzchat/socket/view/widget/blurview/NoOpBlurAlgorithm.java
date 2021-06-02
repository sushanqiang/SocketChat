package com.yzchat.socket.view.widget.blurview;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.yzchat.socket.utils.LogUtils;


class NoOpBlurAlgorithm implements BlurAlgorithm {
    private static final String TAG = "NoOpBlurAlgorithm";
    @Override
    public Bitmap blur(Bitmap bitmap, float blurRadius) {
        return bitmap;
    }

    @Override
    public void destroy() {
        LogUtils.d(TAG,"destory");
    }

    @Override
    public boolean canModifyBitmap() {
        return true;
    }

    @NonNull
    @Override
    public Bitmap.Config getSupportedBitmapConfig() {
        return Bitmap.Config.ARGB_8888;
    }
}
