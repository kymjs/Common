package com.kymjs.kotlin.common.extension

import android.os.Handler
import android.os.Looper

/**
 * Created by ZhangTao on 7/1/16.
 */

fun runAsync(action: () -> Unit) {
    Thread(Runnable(action)).start()
}

fun runOnUiThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post(Runnable(action))
}

fun runDelayed(delayMillis: Long, action: () -> Unit) {
    Handler().postDelayed(Runnable(action), delayMillis)
}

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)
}