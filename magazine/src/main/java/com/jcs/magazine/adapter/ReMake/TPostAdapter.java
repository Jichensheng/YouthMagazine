package com.jcs.magazine.adapter.ReMake;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.MomentActivity;
import com.jcs.magazine.bean.MomentBeanRefactor;
import com.jcs.magazine.util.RelativeDateFormat;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;

import java.util.List;

/**
 * author：Jics
 * 2017/11/21 16:34
 */
public class TPostAdapter extends BaseRefreshAdapter<MomentBeanRefactor> {
	private boolean isMyself;

	public TPostAdapter(Context context, List<MomentBeanRefactor> beanList,Boolean isMyself) {
		super(context, beanList);
		this.isMyself=isMyself;
	}

	@Override
	public BaseRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new TPostHolder(LayoutInflater.from(context).inflate(R.layout.holder_my_post, parent, false));
	}

	@Override
	public void onBindViewHolder(BaseRefreshViewHolder holder, int position) {
		if (isMyself) {
			((TPostHolder) holder).tv_btn_delet.setVisibility(View.VISIBLE);
		} else {
			((TPostHolder) holder).tv_btn_delet.setVisibility(View.GONE);
		}
		final MomentBeanRefactor mb = beanList.get(position);
		final List<String> urls = mb.getImages();

		((TPostHolder) holder).nineGridTestLayout.setUrlList(urls);
		((TPostHolder) holder).tv_public_time.setText(RelativeDateFormat.formatString(mb.getDate()));
		((TPostHolder) holder).tv_content.setText(mb.getExcerpt());
		((TPostHolder) holder).tv_comment.setText("" + mb.getComment());
		((TPostHolder) holder).tv_praise.setText("" + mb.getPraise());

		((TPostHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MomentActivity.class);
				intent.putExtra("mb", mb);
				context.startActivity(intent);
			}
		});
	}

	class TPostHolder extends BaseRefreshViewHolder {
		NineGridTestLayout nineGridTestLayout;
		TextView tv_content, tv_public_time, tv_praise, tv_comment, tv_btn_delet;

		public TPostHolder(View itemView) {
			super(itemView);
			nineGridTestLayout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
			tv_content = (TextView) itemView.findViewById(R.id.tv_content);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
			tv_btn_delet = (TextView) itemView.findViewById(R.id.tv_btn_delet);
		}
	}
}
