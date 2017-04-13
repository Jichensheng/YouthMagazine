package com.yzu_viewPager;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by zhy on 16/5/7.
 * 滑到底不会出现半透明黑色阴影
 */
public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
