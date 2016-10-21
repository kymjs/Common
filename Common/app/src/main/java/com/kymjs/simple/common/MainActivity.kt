package com.kymjs.simple.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kymjs.common.LogUtils
import com.kymjs.common.function.ThreadSwitch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ThreadSwitch.get().io {
            LogUtils.d("hello--io")
        }.ui {
            LogUtils.d("hello--ui")
        }.ui {
            LogUtils.d("hello2--ui")
        }.breakTask().io {
            LogUtils.d("hello2-io")
        }
    }
}