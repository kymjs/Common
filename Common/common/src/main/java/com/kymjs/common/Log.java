/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kymjs.common;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ZhangTao on 10/12/16.
 */
public class Log {
    public static boolean mLogEnable = true;
    private static String mClassname = Log.class.getName();
    private static ArrayList<String> mMethods = new ArrayList<>();

    public static void setEnable(boolean logEnable) {
        mLogEnable = logEnable;
    }

    public static void d(String tag, String msg) {
        if (mLogEnable) {
            android.util.Log.d(tag, getMsgWithLineNumber(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (mLogEnable) {
            android.util.Log.e(tag, getMsgWithLineNumber(msg));
        }

    }

    public static void i(String tag, String msg) {
        if (mLogEnable) {
            android.util.Log.i(tag, getMsgWithLineNumber(msg));
        }

    }

    public static void w(String tag, String msg) {
        if (mLogEnable) {
            android.util.Log.w(tag, getMsgWithLineNumber(msg));
        }

    }

    public static void v(String tag, String msg) {
        if (mLogEnable) {
            android.util.Log.v(tag, getMsgWithLineNumber(msg));
        }

    }

    public static void d(String msg) {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            android.util.Log.d(content[0], content[1]);
        }

    }

    public static void e(String msg) {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            android.util.Log.e(content[0], content[1]);
        }

    }

    public static void i(String msg) {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            android.util.Log.i(content[0], content[1]);
        }

    }

    public static void i() {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber("");
            android.util.Log.i(content[0], content[1]);
        }

    }

    public static void w(String msg) {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            android.util.Log.w(content[0], content[1]);
        }

    }

    public static void v(String msg) {
        if (mLogEnable) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            android.util.Log.v(content[0], content[1]);
        }

    }

    public static String getMsgWithLineNumber(String msg) {
        try {
            StackTraceElement[] e = (new Throwable()).getStackTrace();
            int var2 = e.length;

            for (StackTraceElement st : e) {
                if (!mClassname.equals(st.getClassName()) && !mMethods.contains(st.getMethodName())) {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String TAG = st.getClassName().substring(b);
                    return TAG + "->" + st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                }
            }
        } catch (Exception var8) {
        }

        return msg;
    }

    public static String[] getMsgAndTagWithLineNumber(String msg) {
        try {
            StackTraceElement[] e = (new Throwable()).getStackTrace();
            int var2 = e.length;

            for (StackTraceElement st : e) {
                if (!mClassname.equals(st.getClassName()) && !mMethods.contains(st.getMethodName())) {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String TAG = st.getClassName().substring(b);
                    String message = st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                    return new String[]{TAG, message};
                }
            }
        } catch (Exception var9) {
        }

        return new String[]{"universal tag", msg};
    }

    static {
        Method[] ms = Log.class.getDeclaredMethods();
        for (Method m : ms) {
            mMethods.add(m.getName());
        }
    }
}
