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

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by ZhangTao on 7/1/16.
 */

fun <T : View> Activity.find(id: Int): T = findViewById(id) as T

fun Activity.toast(text: CharSequence): Unit = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Activity.longToast(text: CharSequence): Unit = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
