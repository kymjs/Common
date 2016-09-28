package com.kymjs.kotlin.common

import android.os.Environment
import java.io.File

/**
 * Created by ZhangTao on 6/29/16.
 */
class FileUtils {

    val hsveSDCard: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    val sdCard: File
        get() = Environment.getExternalStorageDirectory()
}
