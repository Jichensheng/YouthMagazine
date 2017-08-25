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
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.banner.BannerView;
import com.jcs.magazine.widget.banner.BannerViewFactory;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;
import com.squareup.picasso.Picasso;

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
	private List<MomentBean> momentBeanList;
	private List<BannerItem> bannerItemList;

	public MomentListAdapter(Context context, List<MomentBean> momentBeanList, List<BannerItem> bannerItemList) {
		this.context = context;
		this.momentBeanList = momentBeanList;
		this.bannerItemList = bannerItemList;

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
			final MomentBean mb = momentBeanList.get(position - 1);

			Picasso.with(context).load(mb.getHead()).error(R.drawable.default_avater).into(((MomentListHolder) holder).civ);
			final List<String> urls = new ArrayList<>();
			for (MomentBean.ImageList imageList : mb.getImages()) {
				urls.add(imageList.getUrl());
			}
			((MomentListHolder) holder).nineGridTestLayout.setUrlList(urls);
			((MomentListHolder) holder).nick.setText(mb.getNick());
			((MomentListHolder) holder).tv_public_time.setText(mb.getDate());
			((MomentListHolder) holder).tv_content.setText(mb.getExcerpt());
			((MomentListHolder) holder).tv_comment.setText("" + mb.getComment());
			((MomentListHolder) holder).tv_praise.setText("" + mb.getPraise());

			((MomentListHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, MomentActivity.class);
					intent.putExtra("mb", mb);
					context.startActivity(intent);
				}
			});

		} else if (holder instanceof BannerHolder) {
			((BannerHolder) holder).bv_banner.setViewFactory(new BannerViewFactory());
			((BannerHolder) holder).bv_banner.setDataList(bannerItemList);
			((BannerHolder) holder).bv_banner.start();
		}
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? TYPE_BANNER : TYPE_List;
	}

	@Override
	public int getItemCount() {
		int count = 0;
		if (momentBeanList != null) {
			count += momentBeanList.size();
		}
		if (bannerItemList != null) {
			count += 1;
		}
		return count;
	}

	class MomentListHolder extends RecyclerView.ViewHolder {
		NineGridTestLayout nineGridTestLayout;
		CircleImageView civ;
		TextView nick, tv_content, tv_public_time, tv_praise, tv_comment;

		public MomentListHolder(View itemView) {
			super(itemView);
			nineGridTestLayout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			civ = (CircleImageView) itemView.findViewById(R.id.civ_head);
			nick = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
		}
	}

	class BannerHolder extends RecyclerView.ViewHolder {
		BannerView bv_banner;

		public BannerHolder(View itemView) {
			super(itemView);
			bv_banner = (BannerView) itemView.findViewById(R.id.bv_banner);

		}
	}
}
