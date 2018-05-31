package com.sczy.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sczy.common.R;

/**
 * 加载对话框
 *
 * @author GuiLin
 */
public class LoadingDialog extends AlertDialog {

    private TextView tips_loading_msg;

    private CharSequence mMessage = null;

    public LoadingDialog(Context context) {
        this(context, null);
    }

    public LoadingDialog(Context context, String message) {
        this(context, message, R.style.CentaLoadingDialog);
        if (TextUtils.isEmpty(message)) {
            mMessage = getContext().getResources().getString(
                    R.string.loading);
        } else {
            mMessage = message;
        }
    }

    public LoadingDialog(Context context, String message, int theme) {
        super(context, theme);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(mMessage);
        ImageView mProcessWait = (ImageView) findViewById(R.id.process_wait);
        Glide.with(getContext()).asGif().load(R.drawable.process_wait).into(mProcessWait);

    }

    @Override
    public void setMessage(CharSequence message) {
        mMessage = message;
        if (tips_loading_msg != null) {
            tips_loading_msg.setText(message);
        }
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }
}