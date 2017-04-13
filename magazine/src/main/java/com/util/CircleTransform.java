package com.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;
import com.widget.CircleImageView;

/**
 * Picasso裁切圆形图
 */

public class CircleTransform implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        return BitmapUtil.createCircleBitmap(source);
    }

    @Override
    public String key() {
        return "circle";
    }
}
