package com.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.githang.navigatordemo.R;


/**
 * 从json获取封面图url、文章简介图url  html5 url
 * imageloder加载封面图、文章简介图
 * 跳转到html5
 */
public class YZUPageAdapter extends PagerAdapter {
	private Context context;
	private OnClickPageListener listener;
	//临时图片资源
	int[] imgRes = { R.drawable.l_content, R.drawable.l_content2, R.drawable.l_content3, R.drawable.l_content4, R.drawable.l_content5 };
	public YZUPageAdapter(Context context){
		this.context=context;
	}	
	public void setOnClickPageListener(OnClickPageListener listener){
		this.listener=listener;
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View bookView =LayoutInflater.from(context).inflate(R.layout.holder_cover, container, false);
		ImageView imv= (ImageView) bookView.findViewById(R.id.imv_yzu_cover);

		imv.setImageResource(imgRes[position]);
		bookView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClickPage(""+position);
			}
		});
		container.addView(bookView);
		return bookView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return imgRes.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}
	
	public interface OnClickPageListener{
		void onClickPage(String position);
	}

}
