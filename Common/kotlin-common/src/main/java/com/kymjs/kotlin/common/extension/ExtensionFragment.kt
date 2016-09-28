package com.kymjs.kotlin.common.extension

import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by ZhangTao on 7/1/16.
 */
fun Fragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)

fun Fragment.toast(text: CharSequence) = activity.toast(text)

fun Fragment.longToast(text: CharSequence) = activity.longToast(text)

fun android.app.Fragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)

fun android.app.Fragment.toast(text: CharSequence) = activity.toast(text)

fun android.app.Fragment.longToast(text: CharSequence) = activity.longToast(text)

val Fragment.rootView: View?
    get() = getView()

fun <T : View> Fragment.find(id: Int): T = rootView?.findViewById(id) as T

val android.app.Fragment.rootView: View?
    get() = getView()

fun <T : View> android.app.Fragment.find(id: Int): T = rootView?.findViewById(id) as T