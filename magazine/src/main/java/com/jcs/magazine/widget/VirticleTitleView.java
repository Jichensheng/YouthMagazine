package com.jcs.magazine.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jcs.magazine.util.DimentionUtils;

/**
 * 竖排目录
 * author：Jics
 * 2017/8/2 09:43
 */
public class VirticleTitleView extends View {
	private static final int MAX_COUNT = 5;
	//文字大小
	private int titleFontSize = 25;
	private int authorFontSize = 15;
	//文字大小px
	private int titleFontSizePx = 25;
	private int authorFontSizePx = 16;
	//单文字框尺寸
	private int titleRectSize;
	private int authorRectSize;
	//文字内容
	private String title = "扬大青年";
	private String[] authors = null;
	//间距问题
	private int paddingTitle = 6;
	private int paddingAuthor = 2;
	//画笔
	private Paint mPaint;

	//超过五个字并且激活状态下就开始动画
	private float deltaY;
	private boolean isNeedScroll;
	private boolean isOnAttachedToWindow;
	private ValueAnimator virticleAnimator = null;

	public VirticleTitleView(Context context) {
		super(context);
		init(context, null);
	}


	public VirticleTitleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public VirticleTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		paddingTitle = DimentionUtils.sp2px(context, paddingTitle);
		paddingAuthor = DimentionUtils.sp2px(context, paddingAuthor);

		titleFontSizePx = DimentionUtils.sp2px(context, titleFontSize);
		authorFontSizePx = DimentionUtils.sp2px(context, authorFontSize);

		titleRectSize = getSingleMaxLength(titleFontSizePx);
		authorRectSize = getSingleMaxLength(authorFontSizePx);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);


	}

	private void initAnimator() {
		if (title.length() > MAX_COUNT) {
			isNeedScroll = true;
			final int totalDeltaY = (titleRectSize + paddingTitle) * (title.length() - MAX_COUNT);
			virticleAnimator = ValueAnimator.ofFloat(0, 1f);
			virticleAnimator.setDuration((title.length() - MAX_COUNT) * 1000);
//			virticleAnimator.setRepeatCount(ValueAnimator.INFINITE);
			virticleAnimator.setRepeatMode(ValueAnimator.REVERSE);
			virticleAnimator.setInterpolator(new LinearInterpolator());
			virticleAnimator.setStartDelay(1000);
			virticleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					deltaY = totalDeltaY * animation.getAnimatedFraction();
					invalidate();
				}

			});
			virticleAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {
						animation.start();
				}

				@Override
				public void onAnimationCancel(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}
			});
		}
	}

	public void startAnimator() {
		if (virticleAnimator != null) {
			virticleAnimator.start();
		}
	}

	public void stopAnimator() {
		if (virticleAnimator != null) {
			virticleAnimator.removeAllUpdateListeners();
			virticleAnimator = null;
			deltaY = 0;
			invalidate();
		}
	}

	/**
	 * 标题字号
	 *
	 * @return
	 */
	public int getTitleFontSize() {
		return titleFontSize;
	}

	/**
	 * 标题字号
	 *
	 * @param titleFontSize
	 */
	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * 作者字号
	 *
	 * @return
	 */
	public int getAuthorFontSize() {
		return authorFontSize;
	}

	/**
	 * 作者字号
	 *
	 * @param authorFontSize
	 */
	public void setAuthorFontSize(int authorFontSize) {
		this.authorFontSize = authorFontSize;
	}


	/**
	 * 设置文章标题
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
		initAnimator();
		invalidate();
	}

	/**
	 * 设置作者名字
	 *
	 * @param authors
	 */
	public void setAuthors(String... authors) {
		int size = authors.length;
		this.authors = new String[size];
		for (int i = 0; i < authors.length; i++) {
			this.authors[i] = authors[i];
		}
		invalidate();
	}

	public String getTitle() {
		return title;
	}

	public String[] getAuthors() {
		return authors;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
		//drawTitle
		drawTitle(canvas);
		//drawTitile
		//画布平移一个章节的宽度
		canvas.translate(titleRectSize + paddingTitle * 3, -paddingTitle);
		mPaint.setTextSize(authorFontSizePx);
		mPaint.setTypeface(Typeface.DEFAULT);
		for (int i = 0; authors != null && i < authors.length; i++) {
			String author = authors[i];
			String[] authorSingle = string2strings(author);
			int out = canvas.save();
			int  tempPadding=0;
			//是汉字就间距大一点，否则间距小一点
			if((author.charAt(0) >= 0x4e00)&&(author.charAt(0) <= 0x9fbb)) {
				tempPadding=paddingAuthor;
			}
			//列级平移
			canvas.translate(i * (authorRectSize + tempPadding * 2f), 0);
			for (int j = 0; j <= authorSingle.length; j++) {
				int inside = canvas.save();
				//行级平移
				canvas.translate(0, j * (authorRectSize + tempPadding));
				if (j == 0) {
					mPaint.setColor(Color.parseColor("#9d9d9d"));
					drawCenterText(canvas, mPaint, " ", authorFontSizePx, tempPadding);
//					mPaint.setColor(Color.BLACK);
				} else
					drawCenterText(canvas, mPaint, authorSingle[j - 1], authorFontSizePx, tempPadding);
				canvas.restoreToCount(inside);
			}
			canvas.restoreToCount(out);
		}
	}

	/**
	 * 画章节题目
	 *
	 * @param canvas
	 */
	private void drawTitle(Canvas canvas) {
		String[] titles = string2strings(title);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(titleFontSizePx);
		canvas.save();
		if (isNeedScroll) {
			canvas.translate(0, -deltaY);
		} else
			canvas.translate(0, 0);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		for (int i = 0; i < titles.length; i++) {
			int  tempPadding=0;
			//是汉字就间距大一点，否则间距小一点
			if((titles[i].charAt(0) >= 0x4e00)&&(titles[i].charAt(0) <= 0x9fbb)) {
				tempPadding=paddingTitle;
			}
			canvas.save();
			canvas.translate(0, i * (titleRectSize + tempPadding));
			drawCenterText(canvas, mPaint, titles[i], titleFontSizePx, tempPadding);

			canvas.restore();
		}
		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = titleRectSize + paddingTitle * 2 + (authorRectSize + paddingAuthor * 2) * authors.length + paddingTitle * 3;
		//默认章节标题长度为4 五倍的高度
		int height = (titleRectSize + paddingTitle) * MAX_COUNT;
		int maxHeight = height;
		for (int i = 0; i < authors.length; i++) {
			maxHeight = Math.max(maxHeight, (authorRectSize + paddingAuthor) * (authors[i].length() + 1));
		}

		setMeasuredDimension(width, maxHeight);
	}

	/**
	 * String转char[]
	 *
	 * @param string
	 * @return
	 */
	private String[] string2strings(String string) {
		String[] strings;
		if (string != null) {
			strings = new String[string.length()];
			for (int i = 0; i < string.length(); i++) {
				char temp = string.charAt(i) == '—' ? '|' : (string.charAt(i) == '《' || string.charAt(i) == '<' ? '﹁' : (string.charAt(i) == '》' || string.charAt(i) == '>' ? '﹂' : string.charAt(i)));
				strings[i] = String.valueOf(temp);
			}
			return strings;
		} else
			return new String[0];
	}

	/**
	 * 单文字大小
	 *
	 * @param fontSize
	 * @return
	 */
	private int getSingleMaxLength(int fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		String string = "国";
		Rect rect = new Rect();
		//通过画笔获得文字的边框
		paint.getTextBounds(string, 0, string.length(), rect);
		int textWidth = rect.width();
		int textHeight = rect.height();
		return Math.max(textWidth, textHeight);
	}

	private void drawCenterText(Canvas canvas, Paint textPaint, String text, int wordSize, int padding) {
		Rect rect = new Rect(0, 0, wordSize + padding * 2, wordSize + padding);
		textPaint.setTextAlign(Paint.Align.CENTER);

		Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
		//文字框距顶部文字基线的距离
		float top = fontMetrics.top;
		//文字框底部距文字基线的距离
		float bottom = fontMetrics.bottom;
		int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);

		canvas.drawText(text, rect.centerX(), centerY, textPaint);
	}

	@Override
	protected void onDetachedFromWindow() {
		stopAnimator();
		super.onDetachedFromWindow();
	}

	@Override
	protected void onAttachedToWindow() {
		startAnimator();
		super.onAttachedToWindow();
	}
}
