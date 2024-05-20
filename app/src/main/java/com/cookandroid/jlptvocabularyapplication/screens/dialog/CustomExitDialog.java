package com.cookandroid.jlptvocabularyapplication.screens.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.databinding.DialogExitBinding;

public class CustomExitDialog extends Dialog {
    private Context context;
    private DialogExitBinding dialogExitBinding;
    private View.OnClickListener mConfirmListener;

    private View.OnClickListener mCancelListener;
    public CustomExitDialog(@NonNull Context context, View.OnClickListener cancelListener, View.OnClickListener confirmListener ) {
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

        setContentView(R.layout.dialog_exit);

        TextView cancel = (TextView) findViewById(R.id.cancel);
        TextView confirm = (TextView) findViewById(R.id.confirm);

        cancel.setOnClickListener(mCancelListener);
        confirm.setOnClickListener(mConfirmListener);
    }


}
