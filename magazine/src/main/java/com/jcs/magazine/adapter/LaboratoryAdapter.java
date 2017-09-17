package com.jcs.magazine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.LaboratoryBean;
import com.jcs.magazine.util.glide.GlideRoundConnerTransform;

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
	public Object instantiateItem(final ViewGroup container, final int position) {
		final View rootView = LayoutInflater.from(context).inflate(R.layout.holder_laboratory, container, false);

		initView(position, rootView);

		//注意别忘记了
		container.addView(rootView);
		return rootView;
	}

	private void initView(final int position, View rootView) {
		ImageView imv = (ImageView) rootView.findViewById(R.id.imv_lb_cover);
        Glide.with(context)
                .load(list.get(position).getRes())
                .override(337,292)
                .transform(new GlideRoundConnerTransform(context,20)).into(imv);
//        imv.setImageResource(list.get(position).getRes());

		TextView tv_title = (TextView) rootView.findViewById(R.id.tv_lb_title);
		TextView tv_description = (TextView) rootView.findViewById(R.id.tv_lb_description);

		tv_title.setText(list.get(position).getTitle());
		tv_description.setText(list.get(position).getDescription());


		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = list.get(position).getIntent();
				if (intent != null) {
					context.startActivity(intent);
				}
			}
		});
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
