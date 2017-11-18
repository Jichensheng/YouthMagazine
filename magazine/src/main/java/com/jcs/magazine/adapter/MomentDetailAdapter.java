package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.util.RelativeDateFormat;
import com.jcs.magazine.widget.CircleImageView;

import java.util.List;

/**
 * author：Jics
 * 2017/4/11 14:57
 */
public class MomentDetailAdapter extends RecyclerView.Adapter {
	private Context context;
	private List<CommentBean> commentBeen;//评论者
	OnLongPressItemListener longPressItemListener;

	public MomentDetailAdapter(Context context, List<CommentBean> commentBeen) {
		this.context = context;
		this.commentBeen = commentBeen;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.holder_comment_item, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		//说说详情
		initCommentHolder((CommentHolder) holder, position );
	}

	/**
	 * 评论item
	 *
	 * @param holder
	 * @param position
	 */
	private void initCommentHolder(final CommentHolder holder, final int position) {
		CommentBean commentBean = commentBeen.get(position);

		holder.tv_nickname.setText(commentBean.getUser().getNick());
		Glide.with(context).load(commentBean.getUser().getHead())
//				.skipMemoryCache(true)
//				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.error(R.drawable.default_avater)
				.placeholder(R.drawable.default_avater)
				.into(new SimpleTarget<GlideDrawable>() {
					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						holder.civ_head.setImageDrawable(resource);
					}
				});
		holder.tv_public_time.setText(RelativeDateFormat.formatString(commentBean.getCreateDate()));
		holder.tv_comment.setText(commentBean.getExcerpt());
		holder.tv_praise.setText(commentBean.getPraise()+"");
		CommentBean quote = commentBean.getQuote();
		if (quote != null) {
			holder.tv_quote.setVisibility(View.VISIBLE);
			holder.tv_quote.setText(String.format("@%s %s", quote.getUser().getNick(), quote.getExcerpt()));
			holder.tv_quote.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray_more));
		} else {
			holder.tv_quote.setVisibility(View.GONE);
		}

		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longPressItemListener.onLongPress(holder.space, position);
				return true;
			}
		});

	}


	@Override
	public int getItemCount() {
		return commentBeen.size();
	}


	class CommentHolder extends RecyclerView.ViewHolder {
		CircleImageView civ_head;
		TextView tv_nickname, tv_public_time, tv_comment, tv_quote, tv_praise;
		View itemView, space;

		public CommentHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			space = itemView.findViewById(R.id.s_space);
			civ_head = (CircleImageView) itemView.findViewById(R.id.civ_head);
			tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
			tv_quote = (TextView) itemView.findViewById(R.id.tv_quote);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
		}
	}

	public interface OnLongPressItemListener {
		void onLongPress(View itemView, int position);
	}

	public void setOnLongPressItemListener(OnLongPressItemListener longPressItemListener) {
		this.longPressItemListener = longPressItemListener;
	}
}
