package com.jcs.magazine.widget.nine_grid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.CommentPicActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 作者：HMY
 */
public class NineGridTestLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
        Picasso.with(mContext).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        return false;
    }

    /**
     *    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
     .showImageForEmptyUri(R.drawable.banner_default).showImageOnFail(R.drawable.banner_default)
     .showImageOnLoading(R.drawable.banner_default)
     .extraForDownloader(extra)
     .bitmapConfig(Bitmap.Config.RGB_565).build();
     * @param imageView
     * @param url
     */
    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        Picasso.with(mContext).load(url).placeholder(R.drawable.banner_default).into(imageView);
    }

    /**
     *
     * @param i 第几张图
     * @param url 链接
     * @param urlList 所有链接
     * @param imageView 改图的imageview
	 */
    @Override
    protected void onClickImage(int i, String url, List<String> urlList,RatioImageView imageView) {

        Intent intent=new Intent(mContext, CommentPicActivity.class);
        intent.putExtra("urls", (Serializable) urlList);
        intent.putExtra("index",i);
        mContext.startActivity(intent);
    }
}
