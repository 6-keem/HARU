package com.cookandroid.jlptvocabularyapplication.screens.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cookandroid.jlptvocabularyapplication.R;

public class ErrorDialog extends Dialog {
    private Context context;
    private View.OnClickListener mConfirmListener;
    public ErrorDialog(@NonNull Context context, View.OnClickListener confirmListener ) {
        super(context);
        this.context = context;
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

        setContentView(R.layout.dialog_error);

        TextView confirm = (TextView) findViewById(R.id.confirm);
        confirm.setOnClickListener(mConfirmListener);
    }


}
