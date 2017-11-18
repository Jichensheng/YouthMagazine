package com.jcs.magazine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.HtmlUtil;
import com.jcs.magazine.util.RelativeDateFormat;
import com.jcs.magazine.widget.CircleImageView;

import java.util.List;

import static com.jcs.magazine.R.id.webView;

/**
 * author：Jics
 * 2017/9/25 15:06
 */
public class ArticalDetialAdapter extends RecyclerView.Adapter {
	public static final int TYPE_ARTICAL = 0;
	public static final int TYPE_COMMENT = 1;
	private List<CommentBean> commentBeen;
	private Context context;
	private String content;
	private String title;
	private String autore;
	private int timeOut = 4 * 1000;
	private boolean isFirst = true;
	MomentDetailAdapter.OnLongPressItemListener longPressItemListener;
	OnWebViewLoadedListener loadedListener;

	public ArticalDetialAdapter(List<CommentBean> commentBeen, Context context, String content, String title, String autore) {
		this.commentBeen = commentBeen;
		this.context = context;
		this.content = content;
		this.title = title;
		this.autore = autore;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case TYPE_ARTICAL:
				return new WebViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_artical_webview, parent, false));
			default:
				return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.holder_comment_item, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof WebViewHolder) {
			if (isFirst) {
				isFirst = false;
				initWebViewHolder((WebViewHolder) holder, position);
			}
		} else {
			initCommentHolder((CommentHolder) holder, position);
		}
	}

	private void initWebViewHolder(WebViewHolder holder, int position) {
		holder.tv_title.setText(title);
		holder.tv_author.setText("文 / " + autore);
		final AlertDialog loading = new DialogHelper(context).show(R.layout.loading);
		holder.mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				new CountDownTimer(timeOut, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}

					@Override
					public void onFinish() {
						if (loading.isShowing()) {
							loading.dismiss();
						}
					}
				}.start();// 开始计时

			}


			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//Todo 加载结束
				loading.dismiss();
				loadedListener.onWebLoaded();
			}

			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				super.onReceivedError(view, request, error);
				loading.dismiss();
			}
		});
		WebSettings settings = holder.mWebView.getSettings();
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		settings.setJavaScriptEnabled(true);
//		mWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzA4NTM3MTA2NQ==&mid=2651391185&idx=1&sn=ea61b912beff537048762bb5a5be821a");
		holder.mWebView.loadDataWithBaseURL("about:blank", HtmlUtil.fmt(content), "text/html", "utf-8", null);

	}

	@Override
	public int getItemCount() {
		return commentBeen.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		return position == 0 ? TYPE_ARTICAL : TYPE_COMMENT;
	}

	/**
	 * 评论item
	 *
	 * @param holder
	 * @param position
	 */
	private void initCommentHolder(final CommentHolder holder, final int position) {
		CommentBean commentBean = commentBeen.get(position - 1);

		holder.tv_nickname.setText(commentBean.getUser().getNick());
		Glide.with(context).load(commentBean.getUser().getHead())
//				.skipMemoryCache(true)
//				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.error(R.drawable.default_avater)
				.placeholder(R.drawable.default_avater)
				.into(new SimpleTarget<GlideDrawable>() {
					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						holder.civ_head.setImageDrawable(resource);
					}
				});
		holder.tv_public_time.setText(RelativeDateFormat.formatString(commentBean.getCreateDate()));
		holder.tv_comment.setText(commentBean.getExcerpt());
		holder.tv_praise.setText(commentBean.getPraise()+"");
		CommentBean quote = commentBean.getQuote();
		if (quote != null) {
			holder.tv_quote.setVisibility(View.VISIBLE);
			holder.tv_quote.setText(String.format("@%s %s", quote.getUser().getNick(), quote.getExcerpt()));
			holder.tv_quote.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray_more));
		} else {
			holder.tv_quote.setVisibility(View.GONE);
		}

		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longPressItemListener.onLongPress(holder.space, position-1);
				return true;
			}
		});

	}

	class WebViewHolder extends RecyclerView.ViewHolder {
		TextView tv_title, tv_author;
		WebView mWebView;

		public WebViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_author = (TextView) itemView.findViewById(R.id.tv_author);
			mWebView = (WebView) itemView.findViewById(webView);
		}
	}

	class CommentHolder extends RecyclerView.ViewHolder {
		CircleImageView civ_head;
		TextView tv_nickname, tv_public_time, tv_comment, tv_quote, tv_praise;
		View itemView, space;

		public CommentHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			space = itemView.findViewById(R.id.s_space);
			civ_head = (CircleImageView) itemView.findViewById(R.id.civ_head);
			tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
			tv_public_time = (TextView) itemView.findViewById(R.id.tv_public_time);
			tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
			tv_quote = (TextView) itemView.findViewById(R.id.tv_quote);
			tv_praise = (TextView) itemView.findViewById(R.id.tv_praise);
		}
	}

	public void setOnLongPressItemListener(MomentDetailAdapter.OnLongPressItemListener longPressItemListener) {
		this.longPressItemListener = longPressItemListener;
	}
	public interface OnWebViewLoadedListener{
		void onWebLoaded();
	}
	public void setOnWebVIewLoadedListener(OnWebViewLoadedListener loadedListener){
		this.loadedListener = loadedListener;
	}
}
