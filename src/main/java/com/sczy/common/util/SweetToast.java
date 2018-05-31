package com.sczy.common.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.sczy.common.R;

/**
 * @author SC16004984
 * @date 2018/5/31 0031.
 */

public class SweetToast {

    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int WARN = 4;


    public static void init(Context context) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 100);
    }

    public static void show(CharSequence text) {
        show(ToastUtils.showCustomShort(R.layout.toast_message),SUCCESS,text);
    }


    public static void show(int type, CharSequence text) {
        show(ToastUtils.showCustomShort(R.layout.toast_message),type,text);
    }

    private static void show(View view,int type,CharSequence text) {
        ImageView mTipImg = view.findViewById(R.id.msg_img);
        TextView mTipText = view.findViewById(R.id.msg_text);
        switch (type) {
            case SUCCESS:
                mTipImg.setBackgroundResource(R.drawable.toast_success);
                break;
            case ERROR:
                mTipImg.setBackgroundResource(R.drawable.toast_error);
                break;
            case WARN:
                mTipImg.setBackgroundResource(R.drawable.toast_warn);
                break;
            default:
                mTipImg.setBackgroundResource(R.drawable.toast_success);
        }
        mTipText.setText(text);
    }


}
