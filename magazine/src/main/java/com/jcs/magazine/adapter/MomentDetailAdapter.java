package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.picasso.CircleTransform;
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
	private List<UserBean> praiser;
	private MomentBean mb;

	public MomentDetailAdapter(Context context, MomentBean mb) {
		this.context = context;
		this.mb = mb;
		urls = new ArrayList<>();
		for (MomentBean.ImageList imageList : mb.getImages()) {
			urls.add(imageList.getUrl());
		}
		praiser = mb.getPraiser();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case MOMENT_HOLDER:
				return new MomentHolder(LayoutInflater.from(context).inflate(R.layout.holder_moment_item, parent, false));
			default:
				return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.holder_comment_item, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		//说说详情
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
			if (praiser != null) {
				for (final UserBean userBean : praiser) {
					ImageView imageView=new ImageView(context);
					LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(DimentionUtils.dip2px(context,35),DimentionUtils.dip2px(context,35));
					lp.setMargins(DimentionUtils.dip2px(context,5),0,0,0);
					Picasso.with(context).load(userBean.getHead()).transform(new CircleTransform()).error(R.drawable.default_avater).into(imageView);
					((MomentHolder) holder).ll_head_container.addView(imageView,lp);
					imageView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							UiUtil.toast(userBean.toString());
						}
					});
				}
			}

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
		LinearLayout ll_head_container;
		NineGridTestLayout nineGridTestLayout;
		CircleImageView civ;
		TextView nick, tv_content, tv_public_time, tv_praise;

		public MomentHolder(View itemView) {
			super(itemView);
			ll_head_container = (LinearLayout) itemView.findViewById(R.id.ll_head_container);
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
