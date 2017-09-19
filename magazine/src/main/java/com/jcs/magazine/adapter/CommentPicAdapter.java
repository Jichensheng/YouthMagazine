package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;

import java.util.List;

import static android.view.ViewGroup.LayoutParams;

/**
 * 图片详情
 * author：Jics
 * 2017/4/10 16:43
 */
public class CommentPicAdapter extends PagerAdapter {
	private Context mContext;
	private List<String> mUrls;
	OnPicClickListener listener;

	public CommentPicAdapter(Context mContext, List<String> mUrls) {
		this.mContext = mContext;
		this.mUrls = mUrls;
	}

	@Override
	public int getCount() {
		return mUrls.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	/**
	 * 动态生成页面
	 * @param container
	 * @param position
	 * @return
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LinearLayout linearLayout=new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setGravity(Gravity.CENTER);

		ImageView imv=new ImageView(mContext);
		LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		imv.setLayoutParams(llp);
		imv.setScaleType(ImageView.ScaleType.FIT_XY);
		imv.setAdjustViewBounds(true);
//		Picasso.with(mContext).load(mUrls.get(position)).placeholder(R.drawable.banner_default).into(imv);
		Glide.with(mContext).load(mUrls.get(position)).placeholder(R.drawable.banner_default).into(imv);
//		ImageLoaderUtil.getImageLoader(mContext).displayImage(mUrls.get(position), imv, ImageLoaderUtil.getPhotoImageOption());
		linearLayout.addView(imv);
		imv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onPicClick();
				}
			}
		});
		container.addView(linearLayout);
		return linearLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	public interface OnPicClickListener{
		void onPicClick();
	}
	public void setOnPicClickListener(OnPicClickListener listener){
		this.listener = listener;
	}
}
