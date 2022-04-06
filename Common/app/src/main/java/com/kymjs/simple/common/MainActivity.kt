package com.kymjs.simple.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kymjs.common.Log
import com.kymjs.common.function.ThreadSwitch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ThreadSwitch.singleton().io {
            Log.d("NullPointerException--io")
            ThreadSwitch.singleton().breakTask()
        }.ui {
            Log.d("NullPointerException--ui")
        }.ui {
            Log.d("NullPointerException2--ui")
        }.io {
            Log.d("NullPointerException2-io")
        }
    }
}