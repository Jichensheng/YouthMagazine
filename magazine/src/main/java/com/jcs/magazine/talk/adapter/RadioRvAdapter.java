package com.jcs.magazine.talk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.TalkContentsBean;

import java.util.List;

public class RadioRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private OnArtItemClickListener onArtItemClickListener;
	private List<TalkContentsBean.ArticlesBean> items;
	private Context context;

	public RadioRvAdapter(Context context, List<TalkContentsBean.ArticlesBean> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new StoryHolder(LayoutInflater.from(context).inflate(R.layout.holder_radio_list, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof StoryHolder) {
			((StoryHolder) holder).tv_title.setText(items.get(position).getTitle());
			((StoryHolder) holder).tv_athor.setText("文 / "+items.get(position).getAuthor());
			((StoryHolder) holder).tv_excerpt.setText(items.get(position).getExcerpt());
			((StoryHolder) holder).tv_praise.setText(""+items.get(position).getPraise());
			((StoryHolder) holder).ll_content.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onArtItemClickListener.onItemClick(v, position);
				}
			});
			/*Picasso.with(context)
					.load(items.get(position).getImage())
					.placeholder(R.mipmap.cover)
					.error(R.mipmap.cover)
					.fit().centerCrop()
					.into(((StoryHolder) holder).imv_cover);*/
			Glide.with(context)
					.load(items.get(position).getImage())
					.placeholder(R.mipmap.cover)
					.error(R.mipmap.cover)
					.centerCrop()
					.into(((StoryHolder) holder).imv_cover);
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	/**
	 * story viewHolder
	 */
	class StoryHolder extends RecyclerView.ViewHolder {
		LinearLayout ll_content;
		TextView tv_title;
		TextView tv_athor;
		//封面图
		ImageView imv_cover;
		TextView tv_excerpt;

		//喜欢的数量
		TextView tv_praise;
		private LinearLayout ll_like;
		private ImageView imv_like;
		private boolean isLike = false;

		public StoryHolder(final View itemView) {
			super(itemView);
			ll_content= (LinearLayout) itemView.findViewById(R.id.ll_content);
			imv_cover = (ImageView) itemView.findViewById(R.id.imv_cover);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_athor = (TextView) itemView.findViewById(R.id.tv_athor);
			tv_excerpt = (TextView) itemView.findViewById(R.id.tv_excerpt);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
			ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
			imv_like = (ImageView) itemView.findViewById(R.id.imv_like);
			ll_like.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isLike) {
						isLike = false;
						imv_like.setImageResource(R.drawable.bubble_like);
						tv_praise.setText("" + (Integer.parseInt(tv_praise.getText().toString()) - 1));
					} else {
						isLike = true;
						imv_like.setImageResource(R.drawable.bubble_liked);
						tv_praise.setText("" + (Integer.parseInt(tv_praise.getText().toString()) + 1));
					}
				}
			});



		}
	}

	public interface OnArtItemClickListener {
		void onItemClick(View view, int position);

		void onHeartClick();

		void onShareClick();
	}

	public void setOnArtItemClickListener(OnArtItemClickListener onArtItemClickListener) {
		this.onArtItemClickListener = onArtItemClickListener;
	}
}