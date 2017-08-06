package com.jcs.magazine.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import com.jcs.magazine.R;

/**
 * Created by Jcs on 2017/8/5.
 */

public class DialogHelper {
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;

    }

    /**
     * 最全的dialog
     *
     * @param listenerOk
     * @param cancelable
     * @param viewId
     * @param style
     * @param title
     * @param content
     * @param negative
     * @return
     */
    public AlertDialog show(DialogInterface.OnClickListener listenerOk,
                            boolean cancelable, int viewId,
                            @StyleRes int style, String title, String content, boolean negative
    ) {
        AlertDialog.Builder dialog;
        //带样式的
        if (style != 0) {
            dialog = new AlertDialog.Builder(context, R.style.CustomDialog);
        } else
            dialog = new AlertDialog.Builder(context);
        if (title != null) {
            dialog.setTitle(title);
        }
        if (content != null) {
            dialog.setMessage(content);
        }
        dialog.setCancelable(cancelable);
        if (listenerOk != null) {
            dialog.setPositiveButton("确定", listenerOk);
        }
        if (negative) {
            dialog.setNegativeButton("取消", null);
        }
        if (viewId != 0) {
            dialog.setView(viewId);
        }
        return dialog.show();
    }

    /**
     * 背景透明的dialog,适合loading
     *
     * @param viewId
     * @return
     */
    public AlertDialog show(int viewId) {
        return show(null, false, viewId, R.style.CustomDialog, null, null,false);
    }

    /**
     * 按返回键可以消失的普通dialog
     *
     * @param listenerOk ok键盘回调事件
     * @param title
     * @param content
     * @return
     */
    public AlertDialog show(DialogInterface.OnClickListener listenerOk
            , String title, String content) {
        return show(listenerOk, true, 0, 0, title, content,true);
    }

}
