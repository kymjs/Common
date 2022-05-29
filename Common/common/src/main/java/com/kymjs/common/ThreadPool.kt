@file:JvmName("ThreadPool")

package com.kymjs.common

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

private val CORE_POOL_SIZE = max(3, min(CPU_COUNT - 1, 6))
private val BIGGER_CORE_POOL_SIZE = CPU_COUNT * 4
private val MAXIMUM_CORE_POOL_SIZE = CPU_COUNT * 8
private const val MAXIMUM_POOL_SIZE = Int.MAX_VALUE
private const val KEEP_ALIVE_SECONDS = 30L
private const val MAX_QUEUE_SIZE = 10

private const val TIMER_THREAD_NAME = "LibTimerThread"
private const val THREAD_NAME = "LibThread"

private var threadPoolExecutor = ThreadPoolExecutor(
    CORE_POOL_SIZE,
    MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS,
    TimeUnit.SECONDS, LinkedBlockingDeque(MAX_QUEUE_SIZE),
    newThreadFactory(THREAD_NAME)
).apply {
    allowCoreThreadTimeOut(true)
}
var executor: ExecutorService = BufferExecutor()
val main = Handler(Looper.getMainLooper())

private val TIMER_THREAD_POOL_EXECUTOR =
    ScheduledThreadPoolExecutor(CORE_POOL_SIZE, newThreadFactory(TIMER_THREAD_NAME))

fun newThreadFactory(threadName: String): ThreadFactory {
    return object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, threadName + " #" + mCount.getAndIncrement())
        }
    }
}

fun setThreadPoolExecutor(e: ThreadPoolExecutor?) = e?.let {
    threadPoolExecutor = it
}

fun execute(command: Runnable) {
    try {
        executor.execute(command)
    } catch (e: Exception) {
        //RejectedExecutionException if this task cannot be accepted for execution
        e.printStackTrace()
    }
}

fun executeInMainThread(command: Runnable): Boolean {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        command.run()
        return true
    } else {
        return main.post(command)
    }
}

fun schedule(command: Runnable?, delay: Long, unit: TimeUnit?): ScheduledFuture<*>? {
    try {
        return TIMER_THREAD_POOL_EXECUTOR.schedule(command, delay, unit)
    } catch (e: Exception) {
        //RejectedExecutionException if the task cannot be scheduled for execution
        //NullPointerException       if command is null
        e.printStackTrace()
    }
    return null
}

fun scheduleWithFixedDelay(
    command: Runnable?, initialDelay: Long,
    delay: Long, unit: TimeUnit?
): ScheduledFuture<*>? {
    try {
        return TIMER_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(command, initialDelay, delay, unit)
    } catch (e: Exception) {
        //RejectedExecutionException if the task cannot be scheduled for execution
        //NullPointerException       if command is null
        //IllegalArgumentException   if initDelay less than or equal to zero
        e.printStackTrace()
    }
    return null
}

fun scheduleAtFixedRate(
    command: Runnable?, initialDelay: Long,
    period: Long, unit: TimeUnit?
): ScheduledFuture<*>? {
    try {
        return TIMER_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(command, initialDelay, period, unit)
    } catch (e: Exception) {
        //参数不合法
        e.printStackTrace()
    }
    return null
}

@JvmOverloads
fun newTimerExecutor(corePoolSize: Int, threadName: String = "Executor"): ScheduledThreadPoolExecutor {
    return ScheduledThreadPoolExecutor(corePoolSize, newThreadFactory("$TIMER_THREAD_NAME-$threadName"))
}

private class BufferExecutor : ExecutorService, Executor {
    val mTasks = ArrayDeque<Runnable>()
    var mActive: Runnable? = null

    @Synchronized
    override fun execute(r: Runnable) {
        mTasks.offer(Runnable {
            try {
                r.run()
            } finally {
                scheduleNext()
            }
        })
        //mActive 不为空，表示一级队列已经满了，此刻任务应该被停留到二级队列等待调度
        if (mActive == null) {
            scheduleNext()
        }
    }

    @Synchronized
    private fun scheduleNext() {
        //线程池中正在执行的任务数
        val activeCount = threadPoolExecutor.activeCount
        //一级队列任务数
        val queueSize = threadPoolExecutor.queue.size

        //动态修改核心线程数，以适应不同场景的任务量
        when {
            mTasks.size > MAX_QUEUE_SIZE * 100 -> {
                threadPoolExecutor.corePoolSize = MAXIMUM_CORE_POOL_SIZE
            }
            mTasks.size > MAX_QUEUE_SIZE * 10 -> {
                threadPoolExecutor.corePoolSize = BIGGER_CORE_POOL_SIZE
            }
            else -> {
                threadPoolExecutor.corePoolSize = CORE_POOL_SIZE
            }
        }

        //如果一级队列没有满，且当前正在执行的线程不超过核心线程数
        if (queueSize <= MAX_QUEUE_SIZE && activeCount < threadPoolExecutor.corePoolSize) {
            //从二级队列中取任务
            if (mTasks.poll().also { mActive = it } != null) {
                //将任务加入一级队列，或有可能直接被线程池执行(Executor内部逻辑)
                threadPoolExecutor.execute(mActive)
                mActive = null
            }
        }
    }

    override fun shutdown() {
        threadPoolExecutor.shutdown()
    }

    override fun shutdownNow(): List<Runnable> {
        return threadPoolExecutor.shutdownNow()
    }

    override fun isShutdown(): Boolean {
        return threadPoolExecutor.isShutdown
    }

    override fun isTerminated(): Boolean {
        return threadPoolExecutor.isTerminated
    }

    @Throws(InterruptedException::class)
    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        return threadPoolExecutor.awaitTermination(timeout, unit)
    }

    override fun <T> submit(task: Callable<T>): Future<T> {
        return threadPoolExecutor.submit(task)
    }

    override fun <T> submit(task: Runnable, result: T): Future<T> {
        return threadPoolExecutor.submit(task, result)
    }

    override fun submit(task: Runnable): Future<*> {
        return threadPoolExecutor.submit(task)
    }

    @Throws(InterruptedException::class)
    override fun <T> invokeAll(tasks: Collection<Callable<T>?>): List<Future<T>> {
        return threadPoolExecutor.invokeAll(tasks)
    }

    @Throws(InterruptedException::class)
    override fun <T> invokeAll(tasks: Collection<Callable<T>?>, timeout: Long, unit: TimeUnit): List<Future<T>> {
        return threadPoolExecutor.invokeAll(tasks, timeout, unit)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    override fun <T> invokeAny(tasks: Collection<Callable<T>?>): T {
        return threadPoolExecutor.invokeAny(tasks)
    }

    @Throws(ExecutionException::class, InterruptedException::class, TimeoutException::class)
    override fun <T> invokeAny(tasks: Collection<Callable<T>?>, timeout: Long, unit: TimeUnit): T {
        return threadPoolExecutor.invokeAny(tasks, timeout, unit)
    }
}
