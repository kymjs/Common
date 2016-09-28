package com.kymjs.kotlin.common.extension

import android.view.View
import android.view.ViewGroup

/**
 * Created by ZhangTao on 7/1/16.
 */

val ViewGroup.views: List<View>
    get() = (0..childCount - 1).map { getChildAt(it) }