@file:JvmName("ContextTrojan")

package com.kymjs.common

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

private var applicationContext: Context? = null

fun getApplicationContext() = applicationContext

fun setContext(c: Context?) = c?.let {
    applicationContext = it
}

internal class InnerTheRouterTrojan : ContentProvider() {
    override fun onCreate(): Boolean {
        applicationContext ?: let {
            applicationContext = context
        }
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        return 0
    }
}