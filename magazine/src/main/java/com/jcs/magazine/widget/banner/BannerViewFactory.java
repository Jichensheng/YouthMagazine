package com.jcs.magazine.widget.banner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.bean.BannerItem;

public  class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
		@Override
		public View create(BannerItem item, int position, ViewGroup container) {
			ImageView iv = new ImageView(container.getContext());
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			Picasso.with(container.getContext()).load(item.getImage()).into(iv);
			Glide.with(container.getContext()).load(item.getImage()).into(iv);
			return iv;
		}
	}