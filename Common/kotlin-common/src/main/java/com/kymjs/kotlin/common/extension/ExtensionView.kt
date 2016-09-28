package com.kymjs.kotlin.common.extension

import android.view.View

/**
 * Created by ZhangTao on 7/1/16.
 */
fun <T : View> View.find(id: Int): T = findViewById(id) as T