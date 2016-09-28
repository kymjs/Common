package com.kymjs.kotlin.common.extension

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by ZhangTao on 7/1/16.
 */
fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

inline fun android.app.Fragment.dp(value: Int): Int = activity.dp(value)

inline fun android.app.Fragment.sp(value: Int): Int = activity.sp(value)

inline fun Fragment.dp(value: Int): Int = activity.dp(value)

inline fun Fragment.sp(value: Int): Int = activity.sp(value)

inline fun View.dp(value: Int): Int = context.dp(value)

inline fun View.sp(value: Int): Int = context.sp(value)