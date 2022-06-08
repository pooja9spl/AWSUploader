package com.awsuploadlib;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;


public class CustomProgressDialog extends Dialog {

    AppCompatTextView textView;
    private Context mContext;
    private String message;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public CustomProgressDialog(Context context, int theme, String message) {
        super(context, theme);
        mContext = context;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

//        setIndeterminate(true);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.custom_progress_dialog);
        textView = findViewById(R.id.tv_message);

        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }

    }

    @Override
    public void show() {
        dismiss();
        super.show();
    }

    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}