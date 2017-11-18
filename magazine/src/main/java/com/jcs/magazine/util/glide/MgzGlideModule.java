package com.jcs.magazine.util.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.jcs.magazine.util.LocalFileManager;

import java.io.File;

public class MgzGlideModule implements GlideModule {
  private static final int MAX_CACHE=30*1024*1024;


  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDiskCache(new InternalCacheDiskCacheFactory(context,MAX_CACHE ));
    builder .setDiskCache(new DiskCache.Factory() {
      @Override
      public DiskCache build() {
        File cacheLocation = LocalFileManager.getInstance().getCacheDir("glide-cache");
        cacheLocation.mkdirs();
        return DiskLruCacheWrapper.get(cacheLocation, MAX_CACHE);
      }
    });
  }

  @Override
  public void registerComponents(Context context, Glide glide) {
  }

}