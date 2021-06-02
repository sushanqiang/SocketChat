package com.yzchat.socket.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;

import java.lang.ref.WeakReference;

public final class ToastUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId = -1;
    private static int gravity = 81;
    private static int xOffset = 0;
    private static int yOffset;
    private static int bgColor;
    private static int bgResource;
    private static int msgColor;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setGravity(int var0, int var1, int var2) {
        gravity = var0;
        xOffset = var1;
        yOffset = var2;
    }

    public static void setBgColor(@ColorInt int var0) {
        bgColor = var0;
    }

    public static void setBgResource(@DrawableRes int var0) {
        bgResource = var0;
    }

    public static void setMsgColor(@ColorInt int var0) {
        msgColor = var0;
    }

    public static void showShort(@NonNull CharSequence var0) {
        show((CharSequence) var0, 0);
    }

    public static void showShort(@StringRes int var0) {
        show(var0, 0);
    }

    public static void showShort(@StringRes int var0, Object... var1) {
        show(var0, 0, var1);
    }

    public static void showShort(String var0, Object... var1) {
        show(var0, 0, var1);
    }

    public static void showLong(@NonNull CharSequence var0) {
        show((CharSequence) var0, 1);
    }

    public static void showLong(@StringRes int var0) {
        show(var0, 1);
    }

    public static void showLong(@StringRes int var0, Object... var1) {
        show(var0, 1, var1);
    }

    public static void showLong(String var0, Object... var1) {
        show(var0, 1, var1);
    }


    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }

    }

    private static void show(@StringRes int var0, int var1) {
        show((CharSequence) Utils.getApp().getResources().getText(var0).toString(), var1);
    }

    private static void show(@StringRes int var0, int var1, Object... var2) {
        show((CharSequence) String.format(Utils.getApp().getResources().getString(var0), var2), var1);
    }

    private static void show(String var0, int var1, Object... var2) {
        if (!TextUtils.isEmpty(var0))
            show((CharSequence) String.format(var0, var2), var1);
    }

    private static void show(final CharSequence var0, final int var1) {
        HANDLER.post(new Runnable() {
            public void run() {
                ToastUtils.cancel();
                ToastUtils.sToast = Toast.makeText(Utils.getApp(), var0, var1);
                ToastUtils.setBgAndGravity();
                ToastUtils.sToast.show();
            }
        });
    }

    private static void show(final View var0, final int var1) {
        HANDLER.post(new Runnable() {
            public void run() {
                ToastUtils.cancel();
                ToastUtils.sToast = new Toast(Utils.getApp());
                ToastUtils.sToast.setView(var0);
                ToastUtils.sToast.setDuration(var1);
                ToastUtils.setBgAndGravity();
                ToastUtils.sToast.show();
            }
        });
    }

    private static void setBgAndGravity() {
        View var0 = sToast.getView();
        if (var0 != null) {
            if (bgResource != -1) {
                var0.setBackgroundResource(bgResource);
            } else if (bgColor != -16777217) {
                Drawable var1 = var0.getBackground();
                if (var1 != null) {
                    var1.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    ViewCompat.setBackground(var0, new ColorDrawable(bgColor));
                }
            }
        }

        sToast.setGravity(gravity, xOffset, yOffset);
    }


    static {
        yOffset = (int) ((double) (64.0F * Utils.getApp().getResources().getDisplayMetrics().density) + 0.5D);
        bgColor = -16777217;
        bgResource = -1;
        msgColor = -16777217;
    }
}
