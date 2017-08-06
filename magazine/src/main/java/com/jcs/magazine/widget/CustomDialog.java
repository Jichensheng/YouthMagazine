package com.jcs.magazine.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseApplication;


/**
 * @describe 自定义居中弹出dialog
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private int layoutResID;
    private int[] listenedItems;
    private OnCenterItemClickListener listener;
    /**
     *
     * @param layoutResID 布局id
     * @param listenedItems 因为是动态绑定布局，为了做到高扩展性
     * 所以要给布局里需要设定事件的空间提供出来
     */
    public CustomDialog(int layoutResID, int[] listenedItems) {
        super(BaseApplication.getInstance(), R.style.CustomDialog);
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    public interface OnCenterItemClickListener {

        void OnCenterItemClick(CustomDialog dialog, View view);

    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnCenterItemClick(this, view);
    }
}