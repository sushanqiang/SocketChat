package com.yzchat.socket.utils;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.yzchat.socket.view.adapter.PLVViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * view初始化相关工具类
 */
public class PLVViewInitUtils {

    public static void initViewPager(FragmentManager fragmentManager, ViewPager viewPager, int selItem, Fragment... fragments) {
        List<Fragment> fragmentList = new ArrayList<>(Arrays.asList(fragments));
        PLVViewPagerAdapter pagerAdapter = new PLVViewPagerAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
        viewPager.setCurrentItem(selItem);
    }

    public static View initPopupWindow(View v, @LayoutRes int resource, final PopupWindow popupWindow, View.OnClickListener listener) {
        View root = LayoutInflater.from(v.getContext()).inflate(resource, null, false);
        popupWindow.setContentView(root);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        root.setOnClickListener(listener);
        return root;
    }


}
