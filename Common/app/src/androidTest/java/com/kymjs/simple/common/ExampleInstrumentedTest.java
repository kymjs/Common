package com.kymjs.simple.common;

import android.support.test.runner.AndroidJUnit4;

import com.kymjs.common.Log;
import com.kymjs.common.function.ThreadSwitch;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        ThreadSwitch.singleton()
                .io(new ThreadSwitch.IO() {
                    @Override
                    public void run() {
                        Log.d("NullPointerException--io");
                        ThreadSwitch.singleton().breakTask();
                    }
                })
                .ui(new ThreadSwitch.Function() {
                    @Override
                    public void run() {
                        Log.d("NullPointerException--ui");
                    }
                })
                .ui(new ThreadSwitch.Function() {
                    @Override
                    public void run() {
                        Log.d("NullPointerException2--ui");
                    }
                })
                .io(new ThreadSwitch.IO() {
                    @Override
                    public void run() {
                        Log.d("NullPointerException2-io");
                    }
                });
    }
}
