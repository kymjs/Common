package com.kymjs.common;

import android.app.Activity;
import android.content.Context;
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
     * @param activity Activity
     * @return 屏幕高度
     */
    public static int getScreenH(Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity Activity
     * @return 屏幕宽度
     */
    public static int getScreenW(Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    public static int px2dip(Context context, float px) {
        Resources r = context.getResources();
        return (int) (px / r.getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 sp
     */
    public static int px2sp(Context context, float px) {
        Resources r = context.getResources();
        return (int) (px / r.getDisplayMetrics().scaledDensity);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, r.getDisplayMetrics());
        return (int) px;
    }


}
