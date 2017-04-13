package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.githang.navigatordemo.R;
import com.widget.nine_grid.NineGridTestLayout;

import java.util.List;

/**
 * author：Jics
 * 2017/4/11 14:57
 */
public class MomentDetailAdapter extends RecyclerView.Adapter {
	private Context context;
	private static final int MOMENT_HOLDER = 0;
	private static final int COMMENT_HOLDER = 1;
	private List<String> url;
	public MomentDetailAdapter(Context context, List<String> urls) {
		this.context = context;
		this.url=urls;
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
			((MomentHolder) holder).nineGridTestLayout.setUrlList(url);
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
		public MomentHolder(View itemView) {
			super(itemView);
			nineGridTestLayout= (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);

		}
	}

	class CommentHolder extends RecyclerView.ViewHolder {

		public CommentHolder(View itemView) {
			super(itemView);
		}
	}
}
