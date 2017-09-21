package com.jcs.magazine.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jcs.magazine.R;

import java.lang.reflect.Field;

public class StrokeTextView extends android.support.v7.widget.AppCompatTextView {
  
    private TextPaint m_TextPaint;
	private int mInnerColor;
    private int mOuterColor;
    private float mOuterWidth;

    public StrokeTextView(Context context, int outerColor, int innnerColor, int outerWidth) {
        super(context);  
        m_TextPaint = this.getPaint();  
        mInnerColor = innnerColor;
        mOuterColor = outerColor;
        mOuterWidth = outerWidth;

    }
  
    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        m_TextPaint = this.getPaint();  
        //获取自定义的XML属性名称  
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        //获取对应的属性值  
        mInnerColor = a.getColor(R.styleable.StrokeTextView_innnerColor,0xffffff);
        mOuterColor = a.getColor(R.styleable.StrokeTextView_outerColor,0xffffff);
        mOuterWidth = a.getDimension(R.styleable.StrokeTextView_outerWidth,1f);

        a.recycle();
          
    }  
  
    public StrokeTextView(Context context, AttributeSet attrs, int defStyle,int outerColor,int innnerColor, int outerWidth) {
        super(context, attrs, defStyle);  
        m_TextPaint = this.getPaint();  
        this.mInnerColor = innnerColor;  
        this.mOuterColor = outerColor;  
        this.mOuterWidth = outerWidth;
    }
  
    private boolean m_bDrawSideLine = true; // 默认采用描边  
  
    /** 
     *  
     */  
    @Override  
    protected void onDraw(Canvas canvas) {
        if (m_bDrawSideLine) {  
            // 描外层  
            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归  
            setTextColorUseReflection(mOuterColor);  
            m_TextPaint.setStrokeWidth(mOuterWidth); // 描边宽度
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            m_TextPaint.setFakeBoldText(true); // 外层text采用粗体  
            m_TextPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略  
            super.onDraw(canvas);  
  
            // 描内层，恢复原先的画笔  
  
            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归  
            setTextColorUseReflection(mInnerColor);  
            m_TextPaint.setStrokeWidth(0);  
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            m_TextPaint.setFakeBoldText(false);  
            m_TextPaint.setShadowLayer(0, 0, 0, 0);  
              
        }  
        super.onDraw(canvas);  
    }  
  
    /** 
     * 使用反射的方法进行字体颜色的设置 
     * @param color 
     */  
    private void setTextColorUseReflection(int color) {  
        Field textColorField;
        try {  
            textColorField = TextView.class.getDeclaredField("mCurTextColor");  
            textColorField.setAccessible(true);  
            textColorField.set(this, color);  
            textColorField.setAccessible(false);  
        } catch (NoSuchFieldException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        m_TextPaint.setColor(color);  
    }  
  
}  