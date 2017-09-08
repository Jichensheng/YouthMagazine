package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.TalkBean;
import com.jcs.magazine.util.glide.GlideCircleTransform;
import com.jcs.magazine.widget.CDView;
import com.jcs.magazine.widget.VirticleTitleView;

import java.util.List;

/**
 * Created by liudong on 17/4/12.
 */

public class TalkListAdapter extends RecyclerView.Adapter implements CDView.OnStopListener, CDView.OnPlayListener {

	public int mActivePosition=-1;//正在播放的位置
	private Context context;
	private List<TalkBean> talkList;
	private OnClickTalkListener onClickTalkListener;

	public TalkListAdapter(Context context, List<TalkBean> talkList) {
		this.context = context;
		this.talkList = talkList;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new CDListHolder(LayoutInflater.from(context).inflate(R.layout.holder_cd, parent, false));
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CDListHolder) {

			((CDListHolder) holder).vtv_title.setTitle(talkList.get(position).getTitle());
			((CDListHolder) holder).vtv_title.setAuthors("文·"+talkList.get(position).getAuthor()
					,"音·"+talkList.get(position).getDj());
			((CDListHolder) holder).tv_excerpt.setText(talkList.get(position).getExcerpt());
			((CDListHolder) holder).tv_time.setText(talkList.get(position).getCreateTime());
			((CDListHolder) holder).tv_praise.setText("" + talkList.get(position).getPraise());


			((CDListHolder) holder).tv_excerpt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickTalkListener.onClickItem(position);
				}
			});


			CDView cdView = ((CDListHolder) holder).cdView;
			Log.e("++++++++++++", "onBindViewHolder: " + position + "     " + mActivePosition);
			//初始化position
			cdView.setPosition(position);
			//注册监听
			cdView.setOnPlayListener(this);
			cdView.setOnStopListener(this);

			/**
			 * 防止乱转
			 */
			if (position != mActivePosition) {
				if (cdView.getState()) {
					cdView.stopMusic();
				}
			} else {
				if (!cdView.getState()) {
					cdView.playMusic();
				}
			}

			/*Picasso.with(context)
					.load(talkList.get(position).getImage())
					.transform(new CircleTransform())
					.error(R.drawable.banner_default_circle)
					.placeholder(R.drawable.banner_default_circle)
					.noFade()
					.into(cdView);*/
			Glide.with(context)
					.load(talkList.get(position).getImage())
					.transform(new GlideCircleTransform(context))
					.error(R.drawable.banner_default_circle)
					.placeholder(R.drawable.banner_default_circle)
					.into(cdView);

			((CDListHolder) holder).ll_like.setOnClickListener(new View.OnClickListener() {

				private boolean isLike = false;

				@Override
				public void onClick(View v) {
					if (isLike) {
						isLike = false;
						((CDListHolder) holder).imv_like.setImageResource(R.drawable.bubble_like);
						((CDListHolder) holder).tv_praise.setText("" + (Integer.parseInt(((CDListHolder) holder).tv_praise.getText().toString()) - 1));
					} else {
						isLike = true;
						((CDListHolder) holder).imv_like.setImageResource(R.drawable.bubble_liked);
						((CDListHolder) holder).tv_praise.setText("" + (Integer.parseInt(((CDListHolder) holder).tv_praise.getText().toString()) + 1));
					}
				}
			});

		}


	}

	@Override
	public void onPlayMusic(int position) {
		if (mActivePosition != position) {
			mActivePosition = position;
			notifyDataSetChanged();
		}
		onClickTalkListener.onPlaySound(position);
	}

	@Override
	public void onStopMusic(int position) {
		if (mActivePosition != position) {
			mActivePosition = position;
			notifyDataSetChanged();
		}
		onClickTalkListener.onPlaySound(position);
	}

	class CDListHolder extends RecyclerView.ViewHolder {
		CDView cdView;
		TextView  tv_excerpt, tv_time, tv_praise;
		private LinearLayout ll_like;
		private ImageView imv_like;
		VirticleTitleView vtv_title;

		public CDListHolder(View itemView) {
			super(itemView);
			tv_excerpt = (TextView) itemView.findViewById(R.id.tv_excerpt);
			tv_time = (TextView) itemView.findViewById(R.id.tv_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
			vtv_title= (VirticleTitleView) itemView.findViewById(R.id.vtv_title);

			cdView = (CDView) itemView.findViewById(R.id.cd_music);
			ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
			imv_like = (ImageView) itemView.findViewById(R.id.imv_like);

		}

	}

	public int getmActivePosition() {
		return mActivePosition;
	}

	public void setmActivePosition(int mActivePosition) {
		this.mActivePosition = mActivePosition;
	}

	@Override
	public int getItemCount() {
		return talkList.size();
	}

	public interface OnClickTalkListener {
		void onPlaySound(int position);

		void onClickItem(int position);
	}

	public void setOnClickTalkListener(OnClickTalkListener onClickTalkListener) {
		this.onClickTalkListener = onClickTalkListener;

	}
}
