package com.kymjs.simple.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kymjs.common.Log
import com.kymjs.common.function.ThreadSwitch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ThreadSwitch.singleton().io {
            Log.d("hello--io")
            ThreadSwitch.singleton().breakTask()
        }.ui {
            Log.d("hello--ui")
        }.ui {
            Log.d("hello2--ui")
        }.io {
            Log.d("hello2-io")
        }
    }
}