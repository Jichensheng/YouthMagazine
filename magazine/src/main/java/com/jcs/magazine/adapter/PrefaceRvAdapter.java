package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.widget.VirticleContentsView;

import java.util.List;

/**
 * author：Jics
 * 2017/8/2 12:27
 */
public class PrefaceRvAdapter extends RecyclerView.Adapter<PrefaceRvAdapter.PrefaceViewHolder> {
	private Context context;
	private List<ContentsBean> lists;
	private OnPreItemClickListener listener;

	public PrefaceRvAdapter(Context context, List<ContentsBean> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public PrefaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new PrefaceViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_preface, parent, false));
	}

	@Override
	public void onBindViewHolder(PrefaceViewHolder holder, final int position) {
		ContentsBean contentsBean = lists.get(position);
		List<ContentsBean.ArticlesBean> articlesBeens = contentsBean.getArticles();
		if (holder instanceof PrefaceViewHolder) {
			holder.contentsView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onClick(v,position);
				}
			});
			//设置章节
			holder.contentsView.setChapter(contentsBean.getName());
			String[] articles = new String[articlesBeens.size()];
			for (int i = 0; i < articlesBeens.size(); i++) {
				articles[i] = articlesBeens.get(i).getTitle();
			}
			//设置标题集
			holder.contentsView.setTitles(articles);
		}
	}

	@Override
	public int getItemCount() {
		return lists.size();
	}

	class PrefaceViewHolder extends RecyclerView.ViewHolder {
		VirticleContentsView contentsView;

		public PrefaceViewHolder(View itemView) {
			super(itemView);
			contentsView = (VirticleContentsView) itemView.findViewById(R.id.vc_preface);
		}
	}

	public interface OnPreItemClickListener {
		void onClick(View view, int position);
	}

	public void setOnPreItemClickListener(OnPreItemClickListener listener) {
		this.listener = listener;
	}
}
