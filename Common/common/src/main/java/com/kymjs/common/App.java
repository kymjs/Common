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

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by ZhangTao on 10/12/16.
 */
public class App {

    public static final Application INSTANCE;

    public static final Uri ENV_URI = Uri.parse(String.format("content://%s/app_info", SystemTool.getMetaData("appmanager")));
    public static final int ENV_RELEASE = 0;
    public static final int ENV_BETA = 1;
    public static final int ENV_ALPHA = 2;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Log.e("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
    }

    public static int getEnvCode() {
        Cursor cursor = null;
        int env;
        try {
            cursor = App.INSTANCE.getContentResolver().query(ENV_URI,
                    new String[]{"env_code"}, "package_name=?",
                    new String[]{App.INSTANCE.getPackageName()}, null);
            if (cursor == null || !cursor.moveToFirst()) {
                return ENV_RELEASE;
            }

            env = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return ENV_RELEASE;
        } finally {
            FileUtils.closeIO(cursor);
        }
        return env;
    }

    public static void toast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int msgId) {
        Toast.makeText(INSTANCE, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_LONG).show();
    }
}
