package com.laka.libui.imageload;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.laka.libui.R;
import com.laka.libutils.screen.ScreenUtils;

/**
 * @Author:summer
 * @Date:2019/1/11
 * @Description: Glide 工具类（使用Glide 4.X）
 */
public class ImageLoader {

    public static void displayImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment)
                .asBitmap()
                .load(url)
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    public static void displayImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity)
                .asBitmap()
                .load(url)
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 异步加载图片，加载成功后可对bitmap进行处理
     */
    public static void loadImage(Context context, String uri, final ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });
    }

    /**
     * 下载图片
     */
    public static void loadImage(Context context, String uri, final ImageLoadListener listener) {
        GlideApp.with(context)
                .asBitmap()
                .load(uri)
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        listener.onSuccess(bitmap);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        listener.onFail(errorDrawable);
                    }
                });
    }

    public static interface ImageLoadListener {
        void onSuccess(Bitmap bitmap);

        void onFail(Drawable drawable);
    }

    /**
     * 加载圆角图片
     */
    public static void displayFilletImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(ScreenUtils.dp2px(5))))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void displayFilletImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(ScreenUtils.dp2px(5))))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void displayCircleImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void displayCircleImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 高斯模糊
     */
    public static void displayBlurImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().transform(new GlideBlurTransformation(fragment.getContext())))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }

    /**
     * 高斯模糊
     */
    public static void displayBlurImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().transform(new GlideBlurTransformation(activity)))
                .placeholder(R.drawable.img_degault)
                .error(R.drawable.img_degault)
                .into(imageView);
    }


}
