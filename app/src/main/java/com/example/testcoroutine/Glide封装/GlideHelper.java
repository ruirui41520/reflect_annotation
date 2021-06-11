package com.example.testcoroutine.Glide封装;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.example.testcoroutine.R;

public class GlideHelper {

    public enum GlideFillMode {
        None, FitCenter, CenterCrop;

        DrawableRequestBuilder<String> build(DrawableRequestBuilder<String> source) {
            if (this == FitCenter) {
                return source.fitCenter();
            }

            if (this == CenterCrop) {
                return source.centerCrop();
            }

            return source;
        }

        BitmapRequestBuilder<String, Bitmap> build(BitmapTypeRequest<String> source) {
            if (this == FitCenter) {
                return source.fitCenter();
            }

            if (this == CenterCrop) {
                return source.centerCrop();
            }

            return source;
        }
    }

    static private RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (!isFromMemoryCache) {
            }
            return false;
        }
    };

    static private RequestListener<String, Bitmap> bitmapListener = new RequestListener<String, Bitmap>() {
        @Override
        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (!isFromMemoryCache) {
            }
            return false;
        }
    };

    public static void load(int resId, Transformation<Bitmap> transformation, ImageView imageView) {
        String url = resourceIdToUrl(imageView.getContext(), resId);
        load(url, 0, 0, transformation, imageView);
    }

    public static void load(String url, ImageView imageView) {
        load(url, 0, 0, imageView);
    }

    public static void load(String url, int placeholderResId, int errorResId, ImageView imageView) {
        load(url, placeholderResId, errorResId, null, imageView);
    }

    public static void load(String url, int placeholderResId, int errorResId, Transformation<Bitmap> transformation, ImageView imageView) {
        load(url, placeholderResId, errorResId, GlideFillMode.None, transformation, null, imageView);
    }

    public static void load(String url, int placeholderResId, int errorResId, GlideFillMode fillMode, Transformation<Bitmap> transformation, DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        Context context = imageView.getContext();
        DrawableRequestBuilder<String> builder = createDrawableTypeRequest(context, url, placeholderResId);
        builder.listener(listener);

        if (placeholderResId != 0) {
            builder.placeholder(placeholderResId);
        }

        if (errorResId != 0) {
            builder.error(errorResId);
        }

        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }

        if (diskCacheStrategy != null) {
            builder.diskCacheStrategy(diskCacheStrategy);
        }

        fillMode.build(builder);

        builder.into(imageView);
    }

    public static void load(String url, StringSignature signature, ImageView imageView) {
        Context context = imageView.getContext();
        DrawableRequestBuilder<String> builder = createDrawableTypeRequest(context, url, 0);
        builder.listener(listener);

        if (signature != null) {
            builder.signature(signature);
        }
        builder.into(imageView);
    }

    public static void load(Context context, String url, int placeholderResId, int errorResId, GlideFillMode fillMode, GlideDrawableImageViewTarget target) {
        DrawableRequestBuilder<String> builder = createDrawableTypeRequest(context, url, placeholderResId);
        fillMode.build(builder);
        builder.into(target);
    }

    public static void load(Context context, String url, int placeholderResId, Target target, boolean playAnimation) {
        DrawableRequestBuilder<String> builder = Glide.with(context).load(url);
        builder.placeholder(placeholderResId);
        builder.error(placeholderResId);
        if (!playAnimation) {
            builder.dontAnimate();
        }
        builder.into(target);
    }

    public static void load(byte[] bitmapArray, Transformation<Bitmap> transformation, ImageView imageView) {
        DrawableRequestBuilder<byte[]> builder = Glide.with(imageView.getContext())
                .load(bitmapArray);

        if (transformation != null) {
            builder.bitmapTransform(transformation);
        }
        builder.into(imageView);
    }

    public static void loadAsBitmap(Context context, String url, Target<Bitmap> target) {
        loadAsBitmap(context, url, 0, 0, target);
    }

    public static void loadAsBitmap(Context context, String url, int placeholderResId, int errorResId, Target<Bitmap> target) {
        loadAsBitmap(context, url, placeholderResId, errorResId, null, null, target);
    }

    public static void loadAsBitmap(Context context, String url, int placeholderResId, int errorResId, Transformation<Bitmap> transformation, GlideFillMode fillMode, Target<Bitmap> target) {
        BitmapTypeRequest<String> builder = createDrawableTypeRequest(context, url, 0).asBitmap();
        builder.listener(bitmapListener);

        if (transformation != null) {
            builder.transform(transformation);
        }

        if (fillMode != null) {
            fillMode.build(builder);
        }

        if (placeholderResId != 0) {
            builder.placeholder(placeholderResId);
        }

        if (errorResId != 0) {
            builder.error(errorResId);
        }

        builder.into(target);
    }

    private static String resourceIdToUrl(Context context, int resId) {
        Resources resources = context.getResources();
        return ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + resources.getResourcePackageName(resId)
                + '/' + resources.getResourceTypeName(resId)
                + '/' + resources.getResourceEntryName(resId);
    }

    private static DrawableTypeRequest<String> createDrawableTypeRequest(Context context, String url, int placeholderResId) {
        DrawableTypeRequest<String> request = null;
        if (TextUtils.isEmpty(url)) {
            int defaultResId = placeholderResId;
            if (defaultResId == 0) {
                defaultResId = R.drawable.custom_empty;
            }

            String imageUri = resourceIdToUrl(context, defaultResId);

            request = Glide.with(context)
                    .load(imageUri);
        } else {
            request = Glide.with(context)
                    .load(url);
        }

        return request;
    }
}

