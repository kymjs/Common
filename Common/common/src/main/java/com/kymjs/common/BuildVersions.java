package com.kymjs.common;

import android.os.Build;

/**
 * @author Oasis
 */
public class BuildVersions extends Build.VERSION_CODES {

    /**
     * Convenient helper for better granularity with @{@link android.annotation.TargetApi TargetApi}.
     * <p>
     * <p>Example code snippet:<pre>
     * BuildVersions.since(VERSION_CODES.ICE_CREAM_SANDWICH, new Runnable() { @TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH) @Override public void run() {
     * ...
     * }});</pre>
     */
    public static void since(final int level, final Runnable code) {
        if (Build.VERSION.SDK_INT >= level) code.run();
    }

    public static void until(final int level, final Runnable code) {
        if (Build.VERSION.SDK_INT <= level) code.run();
    }

    public static void between(final int lower_level, final int upper_level, final Runnable code) {
        if (Build.VERSION.SDK_INT >= lower_level && Build.VERSION.SDK_INT <= upper_level)
            code.run();
    }
}