package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.widget.CircleImageView;

import java.util.List;

/**
 * author：Jics
 * 2017/9/21 14:12
 */
public class FollowListAdatper extends RecyclerView.Adapter {
	private List<UserBean> list;
	private Context context;
	OnItemClickListen listen;

	public FollowListAdatper(Context context, List<UserBean> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new FollowHolder(LayoutInflater.from(context).inflate(R.layout.item_follow, parent, false));
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof FollowHolder) {
			Glide.with(context).load(list.get(position).getHead())
					.error(R.drawable.default_avater)
					.placeholder(R.drawable.default_avater)
					.into(new SimpleTarget<GlideDrawable>() {
						@Override
						public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
							((FollowHolder) holder).civ_avater.setImageDrawable(resource);
						}
					});
			((FollowHolder) holder).tv_nick.setText(list.get(position).getNick());
			((FollowHolder) holder).item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listen.onItemClick(position);
				}
			});

			String college = list.get(position).getCollege();
			college = college != null && college.length() > 0 ? college : "";
			((FollowHolder) holder).tv_college.setText(college);

			String sex = list.get(position).getSex();
			if (sex != null) {
				if (sex.equals("male")) {
					((FollowHolder) holder).iv_sex.setImageResource(R.drawable.male);
				} else
					((FollowHolder) holder).iv_sex.setImageResource(R.drawable.female);
			} else {
			}
		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class FollowHolder extends RecyclerView.ViewHolder {
		CircleImageView civ_avater;
		TextView tv_nick, tv_college;
		ImageView iv_sex;
		View item;

		public FollowHolder(View itemView) {
			super(itemView);
			item = itemView;
			civ_avater = (CircleImageView) itemView.findViewById(R.id.civ_avater);
			tv_nick = (TextView) itemView.findViewById(R.id.tv_nick);
			tv_college = (TextView) itemView.findViewById(R.id.tv_college);
			iv_sex = (ImageView) itemView.findViewById(R.id.iv_sex);
		}
	}

	public interface OnItemClickListen {
		void onItemClick(int position);
	}

	public void setOnItemClickListen(OnItemClickListen listen) {
		this.listen = listen;
	}
}
