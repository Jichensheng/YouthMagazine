package com.jcs.magazine.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.jaeger.library.StatusBarUtil;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.MainActivity;
import com.jcs.magazine.adapter.MomentListAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.util.Bitmaptest;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.FastBlur;
import com.jcs.magazine.util.LocalFileManager;

/**
 * Created by Jcs on 2017/8/20.
 */

public class MineFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_mine, container, false);


        //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
//        StatusBarUtil.setTransparentForImageView(getActivity(), null);
//        StatusBarUtil.setTranslucentForImageView(getActivity(),(int)(255*0.1f),null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.hmm);
        blur(bitmap, imageView);
        SuperTextView superTextView= (SuperTextView) view.findViewById(R.id.stv_notation);
        superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getContext(), String.valueOf(b), Toast.LENGTH_SHORT).show();
            }
        });
        final SuperTextView stvCache= (SuperTextView) view.findViewById(R.id.stv_cache);
        stvCache.setRightString(LocalFileManager.getInstance().getCacheSize());
        stvCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocalFileManager.getInstance().cleanCache();
                        stvCache.setRightString(LocalFileManager.getInstance().getCacheSize());
                    }
                },true,0,0,"清除缓存","确定清除缓存？",true);
            }
        });
        return view;
    }

    private void blur(Bitmap bkg, ImageView view) {
        float radius = 20;
        view.setImageBitmap(FastBlur.doBlur(Bitmaptest.fitBitmap(bkg, 200), (int) radius, true));
    }
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
