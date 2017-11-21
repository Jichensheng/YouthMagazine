package com.jcs.magazine.adapter.ReMake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * author：Jics
 * 17/4/12 10:54
 */

public abstract class BaseRefreshAdapter<T> extends RecyclerView.Adapter<BaseRefreshViewHolder> {
	protected Context context;
	protected List<T> beanList;

	public BaseRefreshAdapter(Context context, List<T> beanList ) {
		this.context = context;
		this.beanList = beanList;
	}

	@Override
	public int getItemCount() {
		return beanList.size();
	}

}
