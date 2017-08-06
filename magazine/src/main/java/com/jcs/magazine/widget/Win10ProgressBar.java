package com.jcs.magazine.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jcs.magazine.R;

public class Win10ProgressBar extends View {

    private Paint mPaint;// 绘制ProgressBar的画笔
    private float startPosition = -90f;// 动画起点角度
    private int paintSizeDp = 4;// 以dp设置画笔的粗细
    private int paintSize;// 定义ProgressBar画笔的粗细
    private Path mPath;// ProgressBar的整个路径
    private Path dst = new Path();// ProgrerssBar当前的路径
    private PathMeasure mPathMeasure; // 用于测量ProgressBar的路径
    private int mWidth, mHeight; // 画布的宽高
    private ValueAnimator valueAnimator; // ProgressBar的动画效果
    private float time;// 动画的进度
    private float d;// 画笔粗细为0时所绘的最远距离（由于本例是正方形，所以取最小 ,即：圆环直径）
    private float r;// 画笔粗细为0时圆环的半径
    private float paintOffset;// 画笔偏移量（画笔粗细可以造成绘制效果的偏差）
    private float pathLength;// ProgressBar路径的总长度
    private Context context;

    public Win10ProgressBar(Context context) {
        this(context, null);
    }

    public Win10ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Win10ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速 , 解决低版本无动画效果的问题
        paintSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paintSizeDp, context.getResources().getDisplayMetrics());
    }

    private void init() {
        // 初始化ProgressBar的画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintSize);// 设置画笔尺寸的大小
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔为圆笔
        mPaint.setAntiAlias(true);// 抗锯齿

        // 初始化ProgressBar的路径
        mPath = new Path();
        int delta= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        d = mWidth > mHeight ? mHeight-delta : mWidth-delta;// 画笔粗细为0时所绘的最远距离（由于本例是正方形，所以取最小）
        r = d / 2;
        paintOffset = paintSize / 2;// 画笔偏移量（画笔粗细可以造成绘制效果的偏差）
        RectF rectF = new RectF(paintOffset+delta/2, paintOffset+delta/2, d - paintOffset+delta/2, d - paintOffset+delta/2);
        mPath.addArc(rectF, startPosition, 359.9f);// 默认从上方开始;这里角度不能使用360f，会导致起点从0度开始而不是-90度开始

        mPathMeasure = new PathMeasure(mPath, true);
        pathLength = mPathMeasure.getLength();
        // 初始化动画效果
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000);
        valueAnimator.setRepeatCount(-1); // 无限重复
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                time = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        // setMeasuredDimension(mWidth , mHeight);
        init();
        valueAnimator.start();// 启动动画效果
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 解决起点闪烁，这样写可避免起点改变造成闪烁的问题
        if (time >= 0.95) {
            dst.reset();
            setDstPath(0);
            canvas.drawPath(dst, mPaint);
        }
        dst.reset(); //将路径重置
        int num = (int) (time / 0.1);
        float y, x;
        switch (num) {
            default:
            case 3:
                x = time - 0.3f * (1 - time);
                y = -pathLength * x * x + 2 * pathLength * x;
                setDstPath(y);
            case 2:
                x = time - 0.2f * (1 - time);
                y = -pathLength * x * x + 2 * pathLength * x;
                setDstPath(y);
            case 1:
                x = time - 0.1f * (1 - time);
                y = -pathLength * x * x + 2 * pathLength * x;
                setDstPath(y);
            case 0:
                x = time;
                y = -pathLength * x * x + 2 * pathLength * x;
                setDstPath(y);
                break;
        }
        canvas.drawPath(dst, mPaint);
    }

    private void setDstPath(float positionOnPath) {
        mPathMeasure.getSegment(positionOnPath, positionOnPath + 1, dst, true);
    }
}