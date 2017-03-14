package com.home.dab.imagebrowse;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.home.dab.imagebrowse.bean.Location;
import com.home.dab.imagebrowse.config.Config;
import com.home.dab.imagebrowse.utils.ViewUtils;



/**
 * Created by DAB on 2017/3/12 15:53.
 */

public class Methods {
    @NonNull
    public static ValueAnimator getValueAnimator(View initialView, final View targetView) {
        Rect viewRect = new Rect();
        initialView.getGlobalVisibleRect(viewRect);
        ViewUtils.changeLayoutParams(targetView, viewRect.width(), viewRect.height());
        targetView.layout(ViewUtils.getLeft(viewRect), ViewUtils.getTop(viewRect), viewRect.width(), viewRect.height());
        ObjectAnimator.ofFloat(targetView, "translationX", targetView.getX(), 0).setDuration(Config.get().duration()).start();
        ObjectAnimator.ofFloat(targetView, "translationY", targetView.getY(), 0).setDuration(Config.get().duration()).start();
        float scale = (float) viewRect.width() / ViewUtils.SCREE_WIDTH;
        ValueAnimator animation = ValueAnimator.ofFloat(scale, 1);
        animation.setDuration(Config.get().duration());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewUtils.changeLayoutParams(targetView, (int) ((ViewUtils.SCREE_WIDTH) * value), (int) ((ViewUtils.SCREE_HEIGHT) * value));
            }
        });
        animation.start();
        return animation;
    }

    @NonNull
    public static ValueAnimator getValueAnimator(Location location, final View targetView) {
        final float[] size = new float[2];
        ViewUtils.changeLayoutParams(targetView, location.getWidth(), location.getHeight());
        targetView.layout(location.getLeft(), location.getTop(), location.getWidth(), location.getHeight());
        ObjectAnimator.ofFloat(targetView, "translationX", targetView.getX(), 0).setDuration(Config.get().duration()).start();
        ObjectAnimator.ofFloat(targetView, "translationY", targetView.getY(), 0).setDuration(Config.get().duration()).start();
        ValueAnimator animation = ValueAnimator.ofFloat((float) location.getWidth() / ViewUtils.SCREE_WIDTH, 1);
        animation.setDuration(Config.get().duration());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                size[0] = ((ViewUtils.SCREE_WIDTH) * value);
            }
        });
        animation.start();
        ValueAnimator animation1 = ValueAnimator.ofFloat((float) location.getHeight() / ViewUtils.SCREE_HEIGHT, 1);
        animation1.setDuration(Config.get().duration());
        animation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                size[1] = ((ViewUtils.SCREE_HEIGHT) * value);
                ViewUtils.changeLayoutParams(targetView, (int) size[0], (int) size[1]);
            }
        });
        animation1.start();


        return animation;
    }

    @NonNull
    public static ValueAnimator closeLayout(Location location, final ViewGroup backgroundLayout) {
        ObjectAnimator.ofFloat(backgroundLayout, "translationX", 0, location.getLeft()).setDuration(Config.get().duration()).start();
        ObjectAnimator.ofFloat(backgroundLayout, "translationY", 0, location.getTop()).setDuration(Config.get().duration()).start();
        final float[] size = new float[2];
        ValueAnimator animation = ValueAnimator.ofFloat(1, (float) location.getWidth() / ViewUtils.SCREE_WIDTH);
        animation.setDuration(Config.get().duration());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float widthScale = (float) animation.getAnimatedValue();
                size[0] = ((ViewUtils.SCREE_WIDTH) * widthScale);
            }
        });
        animation.start();
        ValueAnimator animation1 = ValueAnimator.ofFloat(1, (float) location.getHeight() / ViewUtils.SCREE_HEIGHT);
        animation1.setDuration(Config.get().duration());
        animation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float heightScale = (float) animation.getAnimatedValue();
                size[1] = ((ViewUtils.SCREE_HEIGHT) * heightScale);
//                Log.e(TAG, "onAnimationUpdate: heightScale" + heightScale);
                backgroundLayout.setBackgroundColor(Color.TRANSPARENT);
                ViewUtils.changeLayoutParams(backgroundLayout.getChildAt(0), (int) size[0], (int) size[1]);
            }
        });
        animation1.start();
        return animation;
    }
}
