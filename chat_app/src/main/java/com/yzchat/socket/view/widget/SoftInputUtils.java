package com.yzchat.socket.view.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.yzchat.socket.utils.LogUtils;

import java.lang.reflect.Method;



@SuppressWarnings("unused")
public class SoftInputUtils {

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void hideSoftInput(Context activity) {
        if (activity == null) {
            return;
        }
        if (activity instanceof Activity) {
            Activity activity1=  (Activity)activity;
            if (activity1.isFinishing() || activity1.isDestroyed()) {
                return;
            }
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(((Activity) activity).getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void closeSoftInput(@Nullable Context context, final View focusView) {
        if (focusView == null) {
            return;
        }
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
        focusView.clearFocus();
    }

    public static void openSoftInput(Context context, final View focusView) {
        if (context == null) {
            return;
        }
        if (focusView == null) {
            return;
        }
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        focusView.postDelayed(() -> {
            if (focusView == null) return;
            boolean requestFocus = focusView.requestFocus();
            if (imm != null) {
                boolean show = imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
                if (!show && context instanceof Activity) {
                    ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
//                YzLog.e(context+"----openSoftInput  show= " + show + "   requestFocus= " + requestFocus + "    focusViewName= " + focusView.getClass().getSimpleName());
            }
        }, 100);


    }

    public static void showSoftKeyboard(Activity activity, View focusView) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        if (focusView == null) {
            return;
        }
        boolean requestFocus = focusView.requestFocus();
        boolean requestFocusFromTouch = focusView.requestFocusFromTouch();
        try {
            if (focusView instanceof EditText) {
                EditText editText = ((EditText) focusView);
                if (Build.VERSION.SDK_INT < 21) {
                    Class<EditText> cls = EditText.class;
                    Method method;
                    try {
                        method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                        method.setAccessible(true);
                        method.invoke(editText, false);
                    } catch (Exception e) {
                        LogUtils.e("----openSoftInput  e= " + e.toString());
                    }
                } else {
                    editText.setShowSoftInputOnFocus(false);
                }
            }
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isShowing = imm.showSoftInput(focusView, InputMethodManager.SHOW_IMPLICIT);
            if (!isShowing) {
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
            LogUtils.e("----openSoftInput  show= " + isShowing + "   requestFocusFromTouch= " + requestFocusFromTouch + "   requestFocus= " + requestFocus + "    focusViewName= " + focusView.getClass().getSimpleName());

        } catch (Exception e) {
            LogUtils.e("----openSoftInput " + e.toString());

        }

    }


    public static boolean isActive(Context context, View view) {
        if (context == null) {
            return false;
        }
        if (view == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.isActive(view);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
