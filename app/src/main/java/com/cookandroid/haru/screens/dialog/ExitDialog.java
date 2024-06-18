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

public abstract class ExitDialog extends Dialog {
    private Context context;
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;
    public ExitDialog(@NonNull Context context, View.OnClickListener cancelListener, View.OnClickListener confirmListener ) {
        super(context);
        this.context = context;
        this.mCancelListener = cancelListener;
        this.mConfirmListener = confirmListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        setDialogContentView();

        TextView cancel = (TextView) findViewById(R.id.cancel);
        TextView confirm = (TextView) findViewById(R.id.confirm);

        cancel.setOnClickListener(mCancelListener);
        confirm.setOnClickListener(mConfirmListener);
    }

    abstract protected void setDialogContentView();
}
