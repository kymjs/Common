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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ZhangTao on 7/1/16.
 */
public class UrlUtils {
    /**
     * 默认的scheme跳转逻辑
     */
    public void defaultSchemeJump(Context context, String scheme) {
        if (!TextUtils.isEmpty(scheme)) {
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
            if (in.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(in);
            } else {
                Toast.makeText(context, R.string.common_not_install, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.common_not_install, Toast.LENGTH_SHORT).show();
        }
    }
}
