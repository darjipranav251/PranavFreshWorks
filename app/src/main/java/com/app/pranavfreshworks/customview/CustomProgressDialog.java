package com.app.pranavfreshworks.customview;

import android.app.Dialog;
import android.content.Context;

import com.app.pranavfreshworks.R;

public class CustomProgressDialog {

    private static Dialog dialog;
    private static boolean isShowProgress;

    public CustomProgressDialog(Context context) {
        this(context, true);
    }

    public CustomProgressDialog(Context context, boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_progress);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setCancelable(false);
    }

    public static void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            if (isShowProgress) {
                dialog.show();
            }
        }
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public  static Dialog getDialogObject() {
        return dialog;
    }
}