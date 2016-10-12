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

    private final BlockingQueue<Runnable> mPoolWorkQueue;
    private Handler handler = new Handler(Looper.getMainLooper());

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

    public interface Break extends Runnable {
    }

    private ThreadSwitch(int size) {
        this.start();
        mPoolWorkQueue = new LinkedBlockingQueue<>(size);
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
        mPoolWorkQueue.add(new Break() {
            @Override
            public void run() {
            }
        });
        return this;
    }

    @Override
    public void run() {
        super.run();
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            try {
                final Runnable task = mPoolWorkQueue.take();
                if (task != null) {
                    if (task instanceof IO) {
                        task.run();
                    } else if (task instanceof Function) {
                        handler.post(task);
                    } else if (task instanceof Break) {
                        return;
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
