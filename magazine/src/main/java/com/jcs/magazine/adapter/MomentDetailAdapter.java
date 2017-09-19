package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.glide.GlideCircleTransform;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;

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
	private List<UserBean> praiser;//赞文章的前三名
	private List<CommentBean> commentBeen;//评论者
	private boolean first;
	private MomentBean mb;

	public MomentDetailAdapter(Context context, MomentBean mb, List<CommentBean> commentBeen) {
		this.context = context;
		this.mb = mb;
		praiser = mb.getPraiser();
		this.commentBeen = commentBeen;
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
			initMomentHolder((MomentHolder) holder);
		} else if (holder instanceof CommentHolder) {
			initCommentHolder((CommentHolder) holder, position - 1);
		}
	}

	private void initCommentHolder(CommentHolder holder, int position) {
		CommentBean commentBean = commentBeen.get(position);

		holder.tv_nickname.setText(commentBean.getNick());
		Glide.with(context).load(commentBean.getHead())
//				.skipMemoryCache(true)
//				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.error(R.drawable.default_avater)
				.placeholder(R.drawable.default_avater)
				.into(holder.civ_head);
		holder.tv_public_time.setText(commentBean.getDate());
		holder.tv_comment.setText(commentBean.getExcerpt());
		holder.tv_praise.setText(commentBean.getPraise());
		CommentBean quote = commentBean.getQuote();
		if (quote != null) {
            holder.tv_quote.setVisibility(View.VISIBLE);
			holder.tv_quote.setText(String.format("#%s#%s", quote.getNick(), quote.getExcerpt()));
			holder.tv_quote.setBackgroundColor(ContextCompat.getColor(context,R.color.light_gray_more));
		}else {
			holder.tv_quote.setVisibility(View.GONE);
		}

	}


	private void initMomentHolder(MomentHolder holder) {
		//			Picasso.with(context).load(mb.getHead()).error(R.drawable.default_avater).into(((MomentHolder) holder).civ);
		Glide.with(context).load(mb.getHead()).error(R.drawable.default_avater).into(holder.civ);
		//用户已登录且是自己发的帖子
		if (LoginUserHelper.getInstance().isLogined()
				&& LoginUserHelper.getInstance().getUser().getUid().equals(mb.getUid())) {
			holder.tv_btn.setText("删除");
			// TODO: 2017/9/14 删除逻辑
		} else {
			holder.tv_btn.setText("+关注");
			// TODO: 2017/9/14 关注逻辑
		}

		//urls是九宫格图片
		final List<String> urls = new ArrayList<>();
		for (MomentBean.ImageList imageList : mb.getImages()) {
			urls.add(imageList.getUrl());
		}
		holder.nineGridTestLayout.setUrlList(urls);
		holder.nick.setText(mb.getNick());
		holder.tv_public_time.setText(mb.getDate());
		holder.tv_content.setText(mb.getExcerpt());
		holder.tv_praise.setText("" + mb.getPraise());
		if (praiser != null && !first) {
			for (final UserBean userBean : praiser) {
				ImageView imageView = new ImageView(context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DimentionUtils.dip2px(context, 35), DimentionUtils.dip2px(context, 35));
				lp.setMargins(DimentionUtils.dip2px(context, 5), 0, 0, 0);
//					Picasso.with(context).load(userBean.getHead()).transform(new CircleTransform()).error(R.drawable.default_avater).into(imageView);
				Glide.with(context).load(userBean.getHead())
						.transform(new GlideCircleTransform(context))
						.placeholder(R.drawable.default_avater)
						.error(R.drawable.default_avater)
						.into(imageView);
				holder.ll_head_container.addView(imageView, lp);
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						UiUtil.toast(userBean.toString());
					}
				});
			}
			first = true;
		}
	}

	@Override
	public int getItemCount() {
		return 1 + commentBeen.size();
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? MOMENT_HOLDER : COMMENT_HOLDER;
	}

	class MomentHolder extends RecyclerView.ViewHolder {
		LinearLayout ll_head_container;
		NineGridTestLayout nineGridTestLayout;
		CircleImageView civ;
		TextView nick, tv_content, tv_public_time, tv_praise, tv_btn;

		public MomentHolder(View itemView) {
			super(itemView);
			ll_head_container = (LinearLayout) itemView.findViewById(R.id.ll_head_container);
			nineGridTestLayout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			civ = (CircleImageView) itemView.findViewById(R.id.civ_head);
			nick = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_btn = (TextView) itemView.findViewById(R.id.tv_btn);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
		}
	}

	class CommentHolder extends RecyclerView.ViewHolder {
		CircleImageView civ_head;
		TextView tv_nickname, tv_public_time, tv_comment, tv_quote, tv_praise;

		public CommentHolder(View itemView) {
			super(itemView);
			civ_head = (CircleImageView) itemView.findViewById(R.id.civ_head);
			tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
			tv_quote = (TextView) itemView.findViewById(R.id.tv_quote);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
		}
	}
}
