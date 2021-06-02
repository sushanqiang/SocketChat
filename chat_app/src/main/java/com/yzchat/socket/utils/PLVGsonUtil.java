package com.yzchat.socket.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PLVGsonUtil {
    private static final String TAG = "PLVGsonUtil";
    private static Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public PLVGsonUtil() {
    }

    public static <T> T fromJson(Class<T> var0, String var1) {
        if (TextUtils.isEmpty(var1)) {
            return null;
        } else {
            try {
                Object var2 = gson.fromJson(var1, var0);
                return  (T)var2;
            } catch (Exception var3) {
                LogUtils.e("PLVGsonUtil", var3.getMessage());
                return null;
            }
        }
    }

    public static <T> T fromJson(Class<T> var0, JsonElement var1) {
        if (var1 == null) {
            return null;
        } else {
            Object var2 = gson.fromJson(var1, var0);
            gson.fromJson(var1, new Type() {
            });
            return (T) var2;
        }
    }

    public static <T> List<T> fromJson(String var0) {
        if (TextUtils.isEmpty(var0)) {
            return null;
        } else {
            List var1 = (List)gson.fromJson(var0, (new TypeToken<List<T>>() {
            }).getType());
            return var1;
        }
    }

    public static <T> List<T> fromJson(JsonElement var0) {
        if (var0 == null) {
            return null;
        } else {
            List var1 = (List)gson.fromJson(var0, (new TypeToken<List<T>>() {
            }).getType());
            return var1;
        }
    }

    public static <T> Map<String, T> fromJsonMap(String var0, Type var1) {
        if (TextUtils.isEmpty(var0)) {
            return null;
        } else {
            Map var2 = (Map)gson.fromJson(var0, var1);
            String var3 = "";

            String var5;
            for(Iterator var4 = var2.keySet().iterator(); var4.hasNext(); var3 = var3 + "key:\n" + var5 + "\nvalue:\n" + var2.get(var5) + "-----\n") {
                var5 = (String)var4.next();
            }

            LogUtils.e("PLVGsonUtil", "map is " + var3);
            return var2;
        }
    }

    public static <T> String toJson(T var0) {
        if (var0 == null) {
            return "";
        } else {
            String var1 = gson.toJson(var0);
            return var1;
        }
    }

    public static <T> String toSerialJson(T var0) {
        Gson var1 = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().enableComplexMapKeySerialization().create();
        String var2 = var1.toJson(var0);
        LogUtils.d("PLVGsonUtil", "toJson===========》：" + var2);
        return var2;
    }

    public static <T> String toJsonSimple(T var0) {
        Gson var1 = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        String var2 = var1.toJson(var0);
        var2 = var2.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
        LogUtils.d("PLVGsonUtil", "toJson===========》：" + var2);
        return var2;
    }
}
