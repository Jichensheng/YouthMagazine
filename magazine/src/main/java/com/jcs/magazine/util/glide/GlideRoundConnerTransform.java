package com.jcs.magazine.util.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jcs.magazine.util.DimentionUtils;

public class GlideRoundConnerTransform extends BitmapTransformation {
private float radio;
    public GlideRoundConnerTransform(Context context,int radio) {
        super(context);
//        this.radio = Resources.getSystem().getDisplayMetrics().density * radio;
        this.radio=radio;
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap toTransform,
                            int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap squared = Bitmap.createBitmap(source);
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(0,0,source.getWidth(), source.getHeight())
                , radio,radio,paint);
        return result;
    }

    @Override
    public String getId() {
        return "round";
    }
}