package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClientDemo;

import java.util.List;


/**
 * 从json获取封面图url、文章简介图url  html5 url
 * imageloder加载封面图、文章简介图
 * 跳转到html5
 */
public class YZUPageAdapter extends PagerAdapter {
    private Context context;
    private OnClickPageListener listener;
    private List<MgzCoverBean> coverBeens;

    //临时图片资源
//	int[] imgRes = { R.drawable.l_content, R.drawable.l_content2, R.drawable.l_content3, R.drawable.l_content4, R.drawable.l_content5 };
    public YZUPageAdapter(Context context, List<MgzCoverBean> coverBeens) {
        this.coverBeens = coverBeens;
        this.context = context;
    }

    public void setOnClickPageListener(OnClickPageListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View bookView = LayoutInflater.from(context).inflate(R.layout.holder_cover, container, false);
        final ImageView imv = (ImageView) bookView.findViewById(R.id.imv_yzu_cover);
        TextView tv_chief_editor = (TextView) bookView.findViewById(R.id.tv_chief_editor);
        TextView tv_vol = (TextView) bookView.findViewById(R.id.tv_vol);
        TextView tv_sub_editor = (TextView) bookView.findViewById(R.id.tv_sub_editor);
        tv_chief_editor.setText("主  编：" + coverBeens.get(position).getEditorship());
        tv_vol.setText("Vol." + coverBeens.get(position).getVol());
        tv_sub_editor.setText("副主编：" + coverBeens.get(position).getSubeditor());
		/*Picasso.with(context)
                .load(coverBeens.get(position).getImage())
                .noFade()
                .placeholder(R.drawable.l_content)
                .error(R.drawable.l_content)
                .fit()
//                .resize(BuildConfig.COVER_WIDTH, BuildConfig.COVER_HEIGHT)
                .centerCrop()
                .into(imv);*/
		Glide.with(context)
                .load(YzuClientDemo.RESOURCE_COVER_HOST +coverBeens.get(position).getImage())
                .placeholder(R.drawable.l_content)
                .error(R.drawable.l_content)
//                .dontAnimate()
//                .override(BuildConfig.COVER_WIDTH, BuildConfig.COVER_HEIGHT)
//                .centerCrop()
                .into(imv);

//		imv.setImageResource(imgRes[position]);
        bookView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coverBeens.get(position).getId() == -1) {
                } else
                    listener.onClickPage(v,imv, position);
            }
        });
        //注意别忘记了
        container.addView(bookView);
        return bookView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return coverBeens.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public interface OnClickPageListener {
        void onClickPage(View bookView,ImageView view, int position);
    }

    // 可以删除这段代码看看，数据源更新而viewpager不更新的情况
    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        // 重写这个方法，取到子Fragment的数量，用于下面的判断，以执行多少次刷新
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            // 这里利用判断执行若干次不缓存，刷新
            mChildCount--;
            // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
            return POSITION_NONE;
        }
        // 这个则是缓存不刷新视图
        return super.getItemPosition(object);
    }
}
