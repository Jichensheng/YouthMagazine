package com.jcs.magazine.util.glide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

//监听滚动来对图片加载进行判断处理

public class ImageAutoLoadScrollListener extends RecyclerView.OnScrollListener {
	private Context context;

	public ImageAutoLoadScrollListener(Context context) {
		this.context = context;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
	}

	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		switch (newState) {
			//当屏幕停止滚动，加载图片
			case SCROLL_STATE_IDLE:
				try {
					if (context != null) Glide.with(context).resumeRequests();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			//当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
			case SCROLL_STATE_DRAGGING:
				/*try {
					if (context != null) Glide.with(context).pauseRequests();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				break;
			//由于用户的操作，屏幕产生惯性滑动，停止加载图片
			case SCROLL_STATE_SETTLING:
				try {
					if (context != null) Glide.with(context).pauseRequests();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
	}
}