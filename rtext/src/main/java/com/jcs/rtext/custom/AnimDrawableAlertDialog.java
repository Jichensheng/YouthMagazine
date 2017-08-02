package com.jcs.rtext.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.jcs.rtext.R;

public class AnimDrawableAlertDialog extends AlertDialog {

    private ImageView progressImg;
	//帧动画
    private AnimationDrawable animation;

    public AnimDrawableAlertDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_drawable_dialog_layout);

        //点击imageview外侧区域，动画不会消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        progressImg = (ImageView) findViewById(R.id.refreshing_drawable_img);
        //加载动画资源
        animation = (AnimationDrawable) progressImg.getDrawable();
    }

    /**
     * 在AlertDialog的 onStart() 生命周期里面执行开始动画
     */
    @Override
    protected void onStart() {
        super.onStart();
        animation.start();
    }

    /**
     * 在AlertDialog的onStop()生命周期里面执行停止动画
     */
    @Override
    protected void onStop() {
        super.onStop();
        animation.stop();
    }
}