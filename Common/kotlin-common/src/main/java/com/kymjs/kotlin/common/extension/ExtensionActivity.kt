package com.kymjs.kotlin.common.extension

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by ZhangTao on 7/1/16.
 */

fun <T : View> Activity.find(id: Int): T = findViewById(id) as T

fun Activity.toast(text: CharSequence): Unit = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Activity.longToast(text: CharSequence): Unit = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
