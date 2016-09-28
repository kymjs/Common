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
