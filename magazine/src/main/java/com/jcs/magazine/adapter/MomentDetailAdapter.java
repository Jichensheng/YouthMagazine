package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/4/11 14:57
 */
public class MomentDetailAdapter extends RecyclerView.Adapter {
	private Context context;
	private static final int MOMENT_HOLDER = 0;
	private static final int COMMENT_HOLDER = 1;
	private List<String> urls;
	private MomentBean mb;

	public MomentDetailAdapter(Context context, MomentBean mb) {
		this.context = context;
		this.mb=mb;
		urls =new ArrayList<>();
		for (MomentBean.ImageList imageList : mb.getImages()) {
			urls.add(imageList.getUrl());
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType){
			case MOMENT_HOLDER:
				return new MomentHolder(LayoutInflater.from(context).inflate(R.layout.holder_moment_item,parent,false));
			default:
				return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.holder_comment_item,parent,false));
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof MomentHolder) {

			Picasso.with(context).load(mb.getHead()).error(R.drawable.default_avater).into(((MomentHolder) holder).civ);
			final List<String> urls = new ArrayList<>();
			for (MomentBean.ImageList imageList : mb.getImages()) {
				urls.add(imageList.getUrl());
			}
			((MomentHolder) holder).nineGridTestLayout.setUrlList(urls);
			((MomentHolder) holder).nick.setText(mb.getNick());
			((MomentHolder) holder).tv_public_time.setText(mb.getDate());
			((MomentHolder) holder).tv_content.setText(mb.getExcerpt());
			((MomentHolder) holder).tv_praise.setText("" + mb.getPraise());

		}
	}

	@Override
	public int getItemCount() {
		return 30;
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? MOMENT_HOLDER : COMMENT_HOLDER;
	}

	class MomentHolder extends RecyclerView.ViewHolder {
		NineGridTestLayout nineGridTestLayout;
		CircleImageView civ;
		TextView nick, tv_content, tv_public_time, tv_praise;

		public MomentHolder(View itemView) {
			super(itemView);
			nineGridTestLayout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			civ = (CircleImageView) itemView.findViewById(R.id.civ_head);
			nick = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
		}
	}

	class CommentHolder extends RecyclerView.ViewHolder {

		public CommentHolder(View itemView) {
			super(itemView);
		}
	}
}
