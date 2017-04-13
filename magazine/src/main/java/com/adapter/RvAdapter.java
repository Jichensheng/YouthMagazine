package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.activity.MomentActivity;
import com.githang.navigatordemo.R;
import com.squareup.picasso.Picasso;
import com.util.CircleTransform;
import com.util.ImageLoaderUtil;
import com.widget.CDView;
import com.widget.nine_grid.NineGridTestLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * author：Jics
 * 1、holder负责将recyclerView中的控件实例化出来
 * 2、holder中的view是Adapter的onCreatViewHolder解析布局文件生成的
 * 3、onBindViewHolder是给holder中的控件赋值的
 * 4、getItemViewType是给特定位置的holder定义类型的
 * 顺序应该是 定义不同的holder ——> 根据特定条件通过getItemViewType设定不同位置显示类型 ——>  通过onCreatViewHolder根据不同的类型生成并返回不同holder实体 ——> 在onBindViewHolder里具体给控件具体赋值
 * 2017/4/5 13:45
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private Map<Integer,Boolean> flags;
	private List<String> items;
	private Context context;
	private List<String> urls;
	private String[] mUrls = new String[]{
			"http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
			"http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
			"http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
			"http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
			"http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
			"http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
			"http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
			"http://preview.quanjing.com/ojo001/pe0060887.jpg",
			"http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};
	public RvAdapter(Context context, List<String> items) {
		this.context = context;
		this.items = items;
		flags=new HashMap<>();
		urls=new ArrayList<>();
		for (String mUrl : mUrls) {
			urls.add(mUrl);
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case 0:
				return new StoryHolder(LayoutInflater.from(context).inflate(R.layout.holder_store, parent, false));
			case 1:
				return new CDHolder(LayoutInflater.from(context).inflate(R.layout.holder_cd, parent, false));
			default:
				return new MomentHolder(LayoutInflater.from(context).inflate(R.layout.holder_moments, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		if (holder instanceof MomentHolder) {
			((MomentHolder) holder).layout.setUrlList(urls);
			((MomentHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MomentActivity.class);
					intent.putExtra("urls", (Serializable) urls);
					intent.putExtra("nickname","Jcs");
					context.startActivity(intent);
				}
			});

		} else if (holder instanceof CDHolder) {
			CDView cdView=((CDHolder) holder).cdView;
			Picasso.with(context)
					.load(urls.get(new Random().nextInt(urls.size())))
					.transform(new CircleTransform())
					.noFade()
					.into(cdView);

		} else  {

		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public int getItemViewType(int position) {
		return position % 2;
	}

	/**
	 * 状态holder
	 */
	class MomentHolder extends RecyclerView.ViewHolder {
		NineGridTestLayout layout;
		TextView tv_content;
		public MomentHolder(View itemView) {
			super(itemView);
			layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content= (TextView) itemView.findViewById(R.id.tv_content);

		}
	}

	/**
	 * 杂志封面holder
	 */
	class CDHolder extends RecyclerView.ViewHolder implements CDView.OnStopListener,CDView.OnPlayListener{
		CDView cdView;

		TextView tv_like;
		private LinearLayout ll_like;
		private ImageView imv_like;
		private boolean isLike = false;

		public CDHolder(View itemView) {
			super(itemView);
			tv_like = (TextView) itemView.findViewById(R.id.tv_like);
			cdView= (CDView) itemView.findViewById(R.id.cd_music);
			ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
			imv_like = (ImageView) itemView.findViewById(R.id.imv_like);
			ll_like.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isLike) {
						isLike = false;
						imv_like.setImageResource(R.drawable.bubble_like);
						tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) - 1));
					} else {
						isLike = true;
						imv_like.setImageResource(R.drawable.bubble_liked);
						tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) + 1));
					}
				}
			});
		}

		@Override
		public void onPlay() {
		}

		@Override
		public void onStop() {

		}
	}

	/**
	 * story viewHolder
	 */
	class StoryHolder extends RecyclerView.ViewHolder {
		TextView tv_like;
		private LinearLayout ll_like;
		private ImageView imv_like;
		private boolean isLike = false;

		public StoryHolder(View itemView) {
			super(itemView);
			tv_like = (TextView) itemView.findViewById(R.id.tv_like);
			ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
			imv_like = (ImageView) itemView.findViewById(R.id.imv_like);
			ll_like.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isLike) {
						isLike = false;
						imv_like.setImageResource(R.drawable.bubble_like);
						tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) - 1));
					} else {
						isLike = true;
						imv_like.setImageResource(R.drawable.bubble_liked);
						tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) + 1));
					}
				}
			});

		}
	}

}
