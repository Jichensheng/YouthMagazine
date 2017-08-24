package com.jcs.magazine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.MomentActivity;
import com.jcs.magazine.bean.BannerItem;
import com.jcs.magazine.mock.MockConfig;
import com.jcs.magazine.widget.banner.BannerView;
import com.jcs.magazine.widget.banner.BannerViewFactory;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 17/4/12 10:54
 */

public class MomentListAdapter extends RecyclerView.Adapter {
	private static final int TYPE_BANNER = 1;
	private static final int TYPE_List = 2;
	private Context context;
	private List<String> urls;
	private List<BannerItem> listB;

	public MomentListAdapter(Context context) {
		this.context = context;
		//说说图片列表
		//Todo 文字是死的还要在此获取
		urls = new ArrayList<>();
		for (String mUrl : MockConfig.URLS) {
			urls.add(mUrl);
		}
		//banner数据
		listB = new ArrayList<>();
		for (int i = 0; i < MockConfig.URLS.length; i++) {
			BannerItem item = new BannerItem();
			//图片
			item.image = MockConfig.URLS[i];
			//标题
			item.title = MockConfig.BANNER_TITLES[i];
			listB.add(item);
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		switch (viewType) {
			case TYPE_BANNER:
				view = LayoutInflater.from(context).inflate(R.layout.holder_banner, parent, false);
				return new BannerHolder(view);
			default:
				view = LayoutInflater.from(context).inflate(R.layout.holder_moments, parent, false);
				return new MomentListHolder(view);

		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof MomentListHolder) {
			((MomentListHolder) holder).layout.setUrlList(urls);
		} else if(holder instanceof BannerHolder){
			((BannerHolder) holder).bv_banner.setViewFactory(new BannerViewFactory());
			((BannerHolder) holder).bv_banner.setDataList(listB);
			((BannerHolder) holder).bv_banner.start();
		}
	}

	@Override
	public int getItemViewType(int position) {
		switch (position) {
			case 0:
				return TYPE_BANNER;
			default:
				return TYPE_List;
		}
	}

	@Override
	public int getItemCount() {
		return 10+1;
	}

	class MomentListHolder extends RecyclerView.ViewHolder {
		NineGridTestLayout layout;
		TextView tv_content;

		public MomentListHolder(View itemView) {
			super(itemView);
			layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			tv_content.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, MomentActivity.class);
					intent.putExtra("urls", (Serializable) urls);
					intent.putExtra("nickname", "Jcs");
					context.startActivity(intent);
				}
			});
		}
	}

	class BannerHolder extends RecyclerView.ViewHolder {
		BannerView bv_banner;
		public BannerHolder(View itemView) {
			super(itemView);
			bv_banner= (BannerView) itemView.findViewById(R.id.bv_banner);

		}
	}
}
