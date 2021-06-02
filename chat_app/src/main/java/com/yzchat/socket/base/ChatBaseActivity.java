package com.yzchat.socket.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 基础activity
 */
public class ChatBaseActivity extends AppCompatActivity {
    // <editor-fold defaultstate="collapsed" desc="成员变量">
    private final static int APP_STATUS_KILLED = 0; // 表示应用是被杀死后在启动的
    private final static int APP_STATUS_RUNNING = 1; // 表示应用时正常的启动流程
    private static int APP_STATUS = APP_STATUS_KILLED; // 记录App的启动状态
    protected boolean isCreateSuccess;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="处理异常启动时的相关方法">
    private String getLaunchActivityName() {
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(getPackageName());
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(resolveIntent, 0);
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            return resolveInfos.get(0).activityInfo.name;
        }
        return null;
    }

    private int getTaskActivityCount() {
        ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        if (am == null)
            return -1;
        try {
            // getBusinessProtocol the info from the currently running task
            List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(1);
            if (taskInfos != null && !taskInfos.isEmpty()) {
                return taskInfos.get(0).numActivities;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean restartApp() {
        try {
            Intent intent = new Intent(this, Class.forName(getLaunchActivityName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Activity方法">
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
            savedInstanceState.putParcelable("android:fragments", null);
        }
        super.onCreate(savedInstanceState);
        isCreateSuccess = false;
        boolean launchActivityItBaseActivity = false;
        try {
            launchActivityItBaseActivity = getLaunchActivityName() != null && ChatBaseActivity.class.isAssignableFrom(Class.forName(getLaunchActivityName()));//父/等
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!launchActivityItBaseActivity || (getClass().getName().equals(getLaunchActivityName()) && getTaskActivityCount() < 2)) {
            APP_STATUS = APP_STATUS_RUNNING;
        }
        if (APP_STATUS == APP_STATUS_KILLED && restartApp()) { // 非正常启动流程，直接重新初始化应用界面
            return;
        }
        isCreateSuccess = true;
//        setTransparentForWindowAndDarkIcon(this,false);
        initOrientationManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isCreateSuccess = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    //新增的findViewById()方法，用于兼容support 25
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) super.findViewById(id);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="页面方向管理器">
    private void initOrientationManager() {

    }

    /**
     * 是否开启重力感应屏幕旋转
     *
     * @return true表示开启屏幕旋转，false表示关闭屏幕旋转
     */
    protected boolean enableRotationObserver() {
        return false;
    }
    // </editor-fold>
    /**
     * 设置透明
     */
    public static void setTransparentForWindowAndDarkIcon(Activity activity, boolean isHideNavigationBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            int systemUiVisibility;

            if (isHideNavigationBar) {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            } else {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                systemUiVisibility = systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                setMIUIStatusBarDarkIcon(activity, true);
                setMeizuStatusBarDarkIcon(activity, true);
            }
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(systemUiVisibility);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 修改 MIUI V6  以上状态栏颜色
     */
    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
