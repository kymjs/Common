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

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.kymjs.common.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;


/**
 * 线程切换工具类
 * Created by ZhangTao on 9/1/16.
 */


/**
 * 只在API 21以上时才出问题, 也就是使用 LinkedTransferQueue 队列时会出问题
 * <p>
 * run方法中,如果调用了mPoolWorkQueue.clear();后马上调用mPoolWorkQueue.take();
 * 很大程度上会抛空指针异常说 mPoolWorkQueue 为空
 * <p>
 * 但是如果在clear()方法之后,随便打印一条log
 * 再调用mPoolWorkQueue.take();
 * 抛空指针异常的概率会大大下降。
 * <p>
 * 空指针不是必现的
 */
public class ThreadSwitch extends Thread {
    private static final int DEFAULT_SIZE = 8;
    volatile private boolean isBreak = false;

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

    public interface Function extends Runnable {
    }

    public interface IO extends Runnable {
    }

    private ThreadSwitch(int size) {
        this.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPoolWorkQueue = new LinkedTransferQueue<>();
        } else {
            mPoolWorkQueue = new LinkedBlockingQueue<>(size);
        }
    }

    public ThreadSwitch io(final IO func) {
        mPoolWorkQueue.add(func);
        return this;
    }

    public ThreadSwitch ui(final Function func) {
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
                if (isBreak) {
                    isBreak = false;
                    mPoolWorkQueue.clear();
                    Log.d("========" + getName());
                    if (this != Holder.INSTANCE) {
                        return;
                    }
                }
                final Runnable task = mPoolWorkQueue.take();
                if (task != null) {
                    if (task instanceof IO) {
                        task.run();
                    } else if (task instanceof Function) {
                        handler.post(task);
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
