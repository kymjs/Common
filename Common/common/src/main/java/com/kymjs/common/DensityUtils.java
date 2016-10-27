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

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by ZhangTao on 6/29/16.
 */
public class DensityUtils {

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenH() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenW() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, Resources.getSystem().getDisplayMetrics());
        return (int) px;
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    public static int px2dip(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 sp
     */
    public static int px2sp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(float spValue) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, Resources.getSystem().getDisplayMetrics());
        return (int) px;
    }


}
