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
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 17/4/12 10:54
 */

public class MPostListAdapter extends RecyclerView.Adapter {
	private Context context;
	private List<MomentBean> momentBeanList;

	public MPostListAdapter(Context context, List<MomentBean> momentBeanList) {
		this.context = context;
		this.momentBeanList = momentBeanList;

	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.holder_my_post, parent, false);
		return new MomentListHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof MomentListHolder) {
			final MomentBean mb = momentBeanList.get(position );
			final List<String> urls = new ArrayList<>();
			for (MomentBean.ImageList imageList : mb.getImages()) {
				urls.add(imageList.getUrl());
			}
			((MomentListHolder) holder).nineGridTestLayout.setUrlList(urls);
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

		}
	}


	@Override
	public int getItemCount() {
		return momentBeanList.size();
	}

	class MomentListHolder extends RecyclerView.ViewHolder {
		NineGridTestLayout nineGridTestLayout;
		TextView tv_content, tv_public_time, tv_praise, tv_comment;

		public MomentListHolder(View itemView) {
			super(itemView);
			nineGridTestLayout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
		}
	}

}
