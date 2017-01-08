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

package com.kymjs.common.function;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 线程切换工具类
 * Created by ZhangTao on 9/1/16.
 */
public class ThreadSwitch extends Thread {
    private static final int DEFAULT_SIZE = 8;
    private volatile boolean isBreak = false;

    private final BlockingQueue<Runnable> mPoolWorkQueue;
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static class Holder {
        private static final ThreadSwitch INSTANCE = new ThreadSwitch(200);
    }


    public static ThreadSwitch singleton() {
        return Holder.INSTANCE;
    }

    public static ThreadSwitch get() {
        return new ThreadSwitch(DEFAULT_SIZE);
    }

    public static ThreadSwitch get(int size) {
        return new ThreadSwitch(size);
    }

////////////////////////////////////////////////////////////////////////////////////

    public interface UI extends Runnable {
    }

    public interface IO extends Runnable {
    }

    private ThreadSwitch(int size) {
        this.start();
        mPoolWorkQueue = new LinkedBlockingQueue<>(size);
    }

    public ThreadSwitch io(final IO func) {
        mPoolWorkQueue.add(func);
        return this;
    }

    public ThreadSwitch ui(final UI func) {
        mPoolWorkQueue.add(func);
        return this;
    }

    public ThreadSwitch breakTask() {
        isBreak = true;
        return this;
    }

    @Override
    public void run() {
        super.run();
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            try {
                if (mPoolWorkQueue != null) {
                    if (isBreak) {
                        isBreak = false;
                        mPoolWorkQueue.clear();
                        if (this != Holder.INSTANCE) {
                            return;
                        }
                    }
                    final Runnable task = mPoolWorkQueue.take();
                    if (task != null) {
                        if (task instanceof IO) {
                            task.run();
                        } else if (task instanceof UI) {
                            handler.post(task);
                        }
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
