package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.LaboratoryBean;

import java.util.List;


/**
 * 从json获取封面图url、文章简介图url  html5 url
 * imageloder加载封面图、文章简介图
 * 跳转到html5
 */
public class LaboratoryAdapter extends PagerAdapter {
	private Context context;
	private OnClickPageListener listener;
	private List<LaboratoryBean> list;

	//临时图片资源
	public LaboratoryAdapter(Context context, List<LaboratoryBean> list) {
		this.context = context;
		this.list = list;
	}

	public void setOnClickPageListener(OnClickPageListener listener) {
		this.listener = listener;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final View bookView = LayoutInflater.from(context).inflate(R.layout.holder_laboratory, container, false);
		final ImageView imv = (ImageView) bookView.findViewById(R.id.imv_yzu_lab);
		bookView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClickPage(bookView, position);
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
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	public interface OnClickPageListener {
		void onClickPage(View view, int position);
	}

}
