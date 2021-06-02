package com.yzchat.socket.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ScreenUtils {
     private static int itemHeight;
    private static int itemWidth;
    /**
     * 获取当前屏幕宽度，单位是PX
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm=null;
        if (context != null) {
            dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
        }
        if (dm == null) return 0;
        return dm.widthPixels;
    }

    /**
     * 获取当前屏幕高度，单位是PX
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm=null;
        if (context != null) {
            dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
        }
        if (dm == null) return 0;
        return dm.heightPixels;
    }

    public ScreenUtils() {
    }

    public static boolean isPortrait(Context var0) {
        return var0.getResources().getConfiguration().orientation == 1;
    }

    public static boolean isLandscape(Context var0) {
        return var0.getResources().getConfiguration().orientation == 2;
    }

    public static int[] getNormalWH(Activity var0) {
        if (VERSION.SDK_INT < 14) {
            DisplayMetrics var3 = new DisplayMetrics();
            var0.getWindowManager().getDefaultDisplay().getMetrics(var3);
            return new int[]{var3.widthPixels, var3.heightPixels};
        } else {
            Point var1 = new Point();
            WindowManager var2 = var0.getWindowManager();
            var2.getDefaultDisplay().getSize(var1);
            return new int[]{var1.x, var1.y};
        }
    }

    public static void reSetStatusBar(Activity var0) {
        if (isLandscape(var0)) {
            hideStatusBar(var0);
        } else {
            setDecorVisible(var0);
        }

    }

    public static void hideStatusBar(Activity var0) {
        if (VERSION.SDK_INT < 16) {
            var0.getWindow().setFlags(1024, 1024);
        } else {
            View var1 = var0.getWindow().getDecorView();
            int var2 = 1284;
            if (VERSION.SDK_INT >= 19) {
                var2 |= 4096;
            }

            var1.setSystemUiVisibility(var2);
        }

    }

    public static void exitFullScreen(Activity var0) {
        var0.getWindow().clearFlags(1024);
    }

    public static void showStatusBar(Activity var0) {
        setDecorVisible(var0);
    }

    public static void setDecorVisible(Activity var0) {
        if (VERSION.SDK_INT < 16) {
            var0.getWindow().clearFlags(1024);
        } else {
            View var1 = var0.getWindow().getDecorView();
            byte var2 = 0;
            var1.setSystemUiVisibility(var2);
        }

    }


    public static int dip2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }

    public static int px2dip(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 / var2 + 0.5F);
    }

    public static int getItemHeight() {
        return itemHeight;
    }

    public static void setItemHeight(int var0) {
        itemHeight = var0;
    }

    public static int getItemWidth() {
        return itemWidth;
    }

    public static void setItemWidth(int var0) {
        itemWidth = var0;
    }

    public static void enterLandscape(Activity var0) {
        var0.getWindow().setFlags(1024, 1024);
    }

    public static void enterPortrait(Activity var0) {
        exitFullScreen(var0);
    }
}
