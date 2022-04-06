package com.kymjs.common.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.CheckResult;
import androidx.annotation.StringRes;

/**
 * @author Oasis
 */
public class Dialogs {

    /**
     * Create an non-cancellable alert dialog builder.
     */
    @CheckResult
    public static Builder buildAlert(final Activity activity, final @StringRes int title, final @StringRes int message) {
        return buildAlert(activity, title != 0 ? activity.getText(title) : null, message != 0 ? activity.getText(message) : null);
    }

    @CheckResult
    public static Builder buildAlert(final Activity activity, final CharSequence title, final CharSequence message) {
        final Builder builder = new Builder(activity);
        builder.setCancelable(true);
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);
        return builder;
    }

    /**
     * Provide shortcuts for simpler building
     */
    public static class Builder extends AlertDialog.Builder {

        @CheckResult
        public Builder withOkButton(final Runnable task) {
            setPositiveButton(android.R.string.ok, task == null ? null : new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    task.run();
                }
            });
            return this;
        }

        @CheckResult
        public Builder withCancelButton() {
            setNegativeButton(android.R.string.cancel, null);
            return this;
        }

        public Builder(final Context context) {
            super(context);
        }
    }
}