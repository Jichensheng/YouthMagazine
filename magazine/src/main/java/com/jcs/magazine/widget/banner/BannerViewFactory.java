package com.jcs.magazine.widget.banner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcs.magazine.bean.BannerItem;
import com.squareup.picasso.Picasso;

public  class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
		@Override
		public View create(BannerItem item, int position, ViewGroup container) {
			ImageView iv = new ImageView(container.getContext());
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			Picasso.with(container.getContext()).load(item.image).into(iv);
			return iv;
		}
	}