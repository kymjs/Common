package com.kymjs.simple.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kymjs.common.Log
import com.kymjs.common.function.ThreadSwitch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ThreadSwitch.get().io {
            Log.d("hello--io")
        }.ui {
            Log.d("hello--ui")
        }.ui {
            Log.d("hello2--ui")
        }.breakTask().io {
            Log.d("hello2-io")
        }
    }
}