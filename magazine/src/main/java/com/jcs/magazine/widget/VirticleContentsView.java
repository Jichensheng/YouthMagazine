package com.jcs.magazine.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jcs.magazine.util.DimentionUtils;

/**
 * 竖排目录
 * author：Jics
 * 2017/8/2 09:43
 */
public class VirticleContentsView extends View {
	//文字大小
	private int chapterFontSize = 18;
	private int titleFontSize = 15;
	//文字大小px
	private int chapterFontSizePx = 18;
	private int titleFontSizePx = 16;
	//单文字框尺寸
	private int chapterRectSize;
	private int titleRectSize;
	//文字内容
	private String chapter = "你好呀";
	private String[] titles = new String[0];
	//间距问题
	private int paddingChapter = 6;
	private int paddingTitle = 6;
	//画笔
	private Paint mPaint;

	public VirticleContentsView(Context context) {
		super(context);
		init(context, null);
	}

	public VirticleContentsView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public VirticleContentsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		paddingChapter = DimentionUtils.sp2px(context, paddingChapter);
		paddingTitle = DimentionUtils.sp2px(context, paddingTitle);

		chapterFontSizePx = DimentionUtils.sp2px(context, chapterFontSize);
		titleFontSizePx = DimentionUtils.sp2px(context, titleFontSize);

		chapterRectSize = getSingleMaxLength(chapterFontSizePx);
		titleRectSize = getSingleMaxLength(titleFontSizePx);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);

	}

	/**
	 * 设置章节名称
	 *
	 * @param chapter
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
		invalidate();
	}

	/**
	 * 设置文章名称数组
	 *
	 * @param titles
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
		invalidate();
	}

	/**
	 * 设置章节字号
	 *
	 * @param chapterFontSize
	 */
	public void setChapterFontSize(int chapterFontSize) {
		this.chapterFontSize = chapterFontSize;
	}

	/**
	 * 设置文章标题字号
	 *
	 * @param titleFontSize
	 */
	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * 设置章节padding
	 *
	 * @param paddingChapter
	 */
	public void setPaddingChapter(int paddingChapter) {
		this.paddingChapter = paddingChapter;
	}

	/**
	 * 设置文章padding
	 *
	 * @param paddingTitle
	 */
	public void setPaddingTitle(int paddingTitle) {
		this.paddingTitle = paddingTitle;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
		//drawChapter
		drawChapter(canvas);
		//drawTitile
		//画布平移一个章节的宽度
		canvas.translate(chapterRectSize + paddingChapter * 3, -paddingChapter*3);
		mPaint.setTextSize(titleFontSizePx);
		mPaint.setTypeface(Typeface.DEFAULT);
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			String[] titleSingle = string2strings(title);
			int out = canvas.save();
			//列级平移
			canvas.translate(i * (titleRectSize + paddingTitle*1.5f), 0);
			for (int j = 0; j <= titleSingle.length; j++) {
				int inside = canvas.save();
				//行级平移
				canvas.translate(0, j * (titleRectSize + paddingTitle));
				if (j == 0) {
					mPaint.setColor(Color.parseColor("#b3b3b3"));
					drawCenterText(canvas, mPaint, "﹨", titleFontSizePx, paddingTitle);
					mPaint.setColor(Color.BLACK);
				} else
					drawCenterText(canvas, mPaint, titleSingle[j - 1], titleFontSizePx, paddingTitle);
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
	private void drawChapter(Canvas canvas) {
		canvas.drawCircle(chapterRectSize / 2 + paddingChapter, chapterRectSize, chapterFontSizePx / 4, mPaint);
		canvas.translate(0, chapterFontSizePx * 1.5f);
		String[] chapters = string2strings(chapter);
		mPaint.setTextSize(chapterFontSizePx);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		for (int i = 0; i < chapters.length; i++) {
			canvas.save();
			canvas.translate(0, i * (chapterRectSize + paddingChapter));
			drawCenterText(canvas, mPaint, chapters[i], chapterFontSizePx, paddingChapter);
			if (i == chapters.length - 1 && titles.length != 0) {
				//保证每一条数线的结束点一样
				int height = ((chapterRectSize + paddingChapter) * 3 * 3) + chapterFontSize;
				mPaint.setStrokeWidth(2);
				mPaint.setStyle(Paint.Style.STROKE);
				canvas.drawLine(chapterRectSize / 2 + paddingChapter, chapterFontSizePx * 2, chapterRectSize / 2 + paddingChapter, height, mPaint);
				mPaint.setStyle(Paint.Style.FILL);
			}
			canvas.restore();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = chapterRectSize + paddingChapter * 2 + (titleRectSize + paddingTitle * 2) * titles.length + paddingChapter * 3;
		//默认章节标题长度为4 五倍的高度
		int height = ((chapterRectSize + paddingChapter) * 4 * 5) + chapterFontSize;
		int maxHeight = height;
		for (int i = 0; i < titles.length; i++) {
			maxHeight = Math.max(maxHeight, (titleRectSize + paddingTitle) * (titles[i].length() + 1));
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

}
