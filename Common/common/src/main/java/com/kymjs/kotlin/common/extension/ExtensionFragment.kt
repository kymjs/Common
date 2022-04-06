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

package com.kymjs.kotlin.common.extension

import android.preference.PreferenceManager
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by ZhangTao on 7/1/16.
 */
fun Fragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)

fun Fragment.toast(text: CharSequence) = activity?.toast(text)

fun Fragment.longToast(text: CharSequence) = activity?.longToast(text)

fun android.app.Fragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)

fun android.app.Fragment.toast(text: CharSequence) = activity.toast(text)

fun android.app.Fragment.longToast(text: CharSequence) = activity.longToast(text)

val Fragment.rootView: View?
    get() = getView()

fun <T : View> Fragment.find(id: Int): T = rootView?.findViewById(id) as T

val android.app.Fragment.rootView: View?
    get() = getView()

fun <T : View> android.app.Fragment.find(id: Int): T = rootView?.findViewById(id) as T