package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.util.DimentionUtils;

import java.util.List;

/**
 * 发表时的附加图片的预览图适配器
 * author：Jics
 * 2017/9/18 17:11
 */
public class MakePostGridAdapter extends RecyclerView.Adapter {
	public static final int CLICK_TYPE_DELET = 1;
	public static final int CLICK_TYPE_RETYR = 2;
	private static final int TYPE_PIC = 1;
	private static final int TYPE_PLUS = 2;
	private static final int MAX_COUNT = 9;
	private Context context;
	private List<String> paths;
	PostClickListener listener;

	public MakePostGridAdapter(Context context, List<String> paths) {
		this.context = context;
		this.paths = paths;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case TYPE_PIC:
				return new PicHolder(LayoutInflater.from(context).inflate(R.layout.holder_post_pic, parent, false));
			default:
				return new PlusHolder(LayoutInflater.from(context).inflate(R.layout.holder_post_plus, parent, false));

		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
			if (holder instanceof PicHolder) {
				Glide.with(context).load(paths.get(position))
						.override(DimentionUtils.dip2px(context, 100), DimentionUtils.dip2px(context, 100))
						.into(((PicHolder) holder)
								.iv_holder_pic);
				((PicHolder) holder).iv_delet.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						listener.onClickItem(CLICK_TYPE_DELET, position);
					}
				});
			} else if (holder instanceof PlusHolder) {
				if (paths.size() > 0) {
					((PlusHolder) holder).iv_retry.setImageResource(R.drawable.retry);
				}else {
					((PlusHolder) holder).iv_retry.setImageResource(R.drawable.plus);
				}
				((PlusHolder) holder).iv_retry.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						listener.onClickItem(CLICK_TYPE_RETYR, position);
					}
				});
		}

	}

	/**
	 * 少于九张图片并且是最后一个的时候显示plus
	 *
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		return position == paths.size()  ? TYPE_PLUS : TYPE_PIC;
	}

	@Override
	public int getItemCount() {
		return paths.size()<9? paths.size()+ 1:paths.size();
	}

	class PicHolder extends RecyclerView.ViewHolder {
		ImageView iv_holder_pic, iv_delet;

		public PicHolder(View itemView) {
			super(itemView);
			iv_holder_pic = (ImageView) itemView.findViewById(R.id.iv_holder_pic);
			iv_delet = (ImageView) itemView.findViewById(R.id.iv_delet);
		}
	}

	class PlusHolder extends RecyclerView.ViewHolder {
		ImageView iv_retry;

		public PlusHolder(View itemView) {
			super(itemView);
			iv_retry = (ImageView) itemView.findViewById(R.id.iv_retry);
		}
	}

	public interface PostClickListener {
		void onClickItem(int type, int position);
	}

	public void setOnPostClickListener(PostClickListener listener) {
		this.listener = listener;
	}
}
