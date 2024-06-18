package com.cookandroid.haru.screens.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cookandroid.haru.R;

public class ApplicationExitDialog extends ExitDialog {
    public ApplicationExitDialog(@NonNull Context context, View.OnClickListener cancelListener, View.OnClickListener confirmListener ) {
        super(context,cancelListener, confirmListener);
    }

    @Override
    protected void setDialogContentView() {
        setContentView(R.layout.dialog_app_exit);
    }
}
