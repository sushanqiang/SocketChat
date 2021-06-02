package com.yzchat.socket.utils;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;

public class LogUtils {
    private static final String TAG = "LogUtils";
    public static final int TO_CONSOLE = 1;
    public static final int TO_FILE = 2;
    public static final int FROM_LOGCAT = 4;
    private static final String LOG_TEMP_FILE = "PLVCommonLog_log.txt";
    private static final String LOG_LAST_FILE = "PLVCommonLog_log_last.txt";
    private static final String LOG_NOW_FILE = "PLVCommonLog_log_now.txt";
    private static final int LOG_MAXSIZE = 1048576;
    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;
    private static final int LOG_LEVEL = 2;
    private static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath();
    private static int DEBUG_ALL = 7;
    private static final Object[] Lock = new Object[0];
    private static OutputStream mLogStream;
    private static long mFileSize;
    private static Calendar mDate = Calendar.getInstance();
    private static StringBuilder mBuffer = new StringBuilder();
    private static boolean DEBUG_MODEL = true;

    public LogUtils() {
    }


    public static void d(String var0, String var1, Object... var2) {
        if (DEBUG_MODEL) {
            d(var0, String.format(var1, var2));
        }

    }

    public static void d(String var1) {
        if (DEBUG_MODEL) {
            log(TAG, var1, 3);
        }

    }

    public static void e(String var1) {
        if (DEBUG_MODEL) {
            log(TAG, var1, 6);

        }

    }

    public static void d(String var0, String var1) {
        if (DEBUG_MODEL) {
            log(var0, var1, 3);
        }

    }

    public static void setDebug(boolean var0) {
        DEBUG_MODEL = var0;
    }

    public static void v(String var0, String var1) {
        log(var0, var1, 2);
    }

    public static void e(String var0, String var1) {
        log(var0, var1, 6);
    }

    public static void i(String var0, String var1) {
        log(var0, var1, 4);
    }

    public static void w(String var0, String var1) {
        log(var0, var1, 5);
    }

    public static void exception(Throwable var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        PrintWriter var2 = new PrintWriter(var1);
        var0.printStackTrace(var2);
        var2.close();
        String var3 = new String(var1.toByteArray());
        log("EXCEPTION", var3, 6);
    }

    private static void log(String var0, String var1, int var2) {
        if (var0 == null) {
            var0 = "TAG_NULL";
        }

        if (var1 == null) {
            var1 = "MSG_NULL";
        }

        if (var2 >= 2) {
            if ((getDebugAll() & 1) != 0) {
                LogToConsole(var0, var1, var2);
            }


        }

    }


    private static String getLogStr(String var0, String var1) {
        mDate.setTimeInMillis(System.currentTimeMillis());
        mBuffer.setLength(0);
        mBuffer.append("[");
        mBuffer.append(var0);
        mBuffer.append(" : ");
        mBuffer.append(mDate.get(2) + 1);
        mBuffer.append("-");
        mBuffer.append(mDate.get(5));
        mBuffer.append(" ");
        mBuffer.append(mDate.get(11));
        mBuffer.append(":");
        mBuffer.append(mDate.get(12));
        mBuffer.append(":");
        mBuffer.append(mDate.get(13));
        mBuffer.append(":");
        mBuffer.append(mDate.get(14));
        mBuffer.append("] ");
        mBuffer.append(var1);
        return mBuffer.toString();
    }


    private static void closeLogFileOutStream() {
        try {
            if (mLogStream != null) {
                mLogStream.close();
                mLogStream = null;
                mFileSize = 0L;
            }
        } catch (Exception var1) {
            Log.e("EXCEPTION", "EXCEPTION", var1);
        }

    }


    private static void LogToConsole(String var0, String var1, int var2) {
        switch (var2) {
            case 2:
                Log.v(var0, var1);
                break;
            case 3:
                Log.d(var0, var1);
                break;
            case 4:
                Log.i(var0, var1);
                break;
            case 5:
                Log.w(var0, var1);
                break;
            case 6:
                Log.e(var0, var1);
        }

    }


    public static int getDebugAll() {
        return DEBUG_ALL;
    }

    public static void setDebugAll(int var0) {
        DEBUG_ALL = var0;
    }


}
