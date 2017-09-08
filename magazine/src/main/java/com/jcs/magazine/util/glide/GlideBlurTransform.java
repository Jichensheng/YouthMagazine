package com.jcs.magazine.util.glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jcs.magazine.widget.Bitmaptest;
import com.jcs.magazine.widget.FastBlur;

public class GlideBlurTransform extends BitmapTransformation {


    public GlideBlurTransform(Context context) {
        super(context);
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap toTransform,
                            int outWidth, int outHeight) {
        return FastBlur.doBlur(Bitmaptest.fitBitmap(toTransform, 100), 10, true);
    }

    @Override
    public String getId() {
        return "blur";
    }
}