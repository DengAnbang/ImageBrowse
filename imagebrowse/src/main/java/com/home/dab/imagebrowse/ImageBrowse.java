package com.home.dab.imagebrowse;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.dab.imagebrowse.adapter.DefaultPagerAdapter;
import com.home.dab.imagebrowse.bean.Location;
import com.home.dab.imagebrowse.config.Config;
import com.home.dab.imagebrowse.config.ConfigSetting;
import com.home.dab.imagebrowse.config.ShowConfig;
import com.home.dab.imagebrowse.interfaces.IGetShowUrl;
import com.home.dab.imagebrowse.interfaces.OnDismissBackTo;
import com.home.dab.imagebrowse.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAB on 2017/3/1 10:49.
 */

public class ImageBrowse {
    private static final String TAG = "ImageBrowse";
    private ViewGroup mRootView;
    private ViewPager mViewPager;
    private boolean isDefault = true;

    private OnDismissBackTo mOnDismissBackTo;
    private boolean isOpen;
    private ViewGroup mBackgroundLayout;
    private Location mLocation;
    private boolean adjust = true;
    private ShowConfig mShowConfig;

    private void recycle() {
//        mRootView = null;
//        mViewPager = null;
        mOnDismissBackTo = null;
//        mBackgroundLayout = null;
//        mLocation = null;
//        mShowConfig = null;
//        Config.get().recycle();
    }

    public void setShowConfig(ShowConfig showConfig) {
        mShowConfig = showConfig;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isAnimationLeisure() {
        return Config.get().isAnimationLeisure();
    }

    public ImageBrowse(AppCompatActivity activity) {
        View rootView = activity.getWindow().getDecorView();
        if (rootView != null && rootView instanceof ViewGroup) {
            mRootView = (ViewGroup) rootView;
        }
        ViewUtils.initScreenSize(activity);
        mBackgroundLayout = ViewUtils.getBackgroundLayout(activity, Config.get().getBackgroundColor());
    }

    public ImageBrowse(AppCompatActivity activity, ShowConfig showConfig) {
        this(activity);
        mShowConfig = showConfig;
    }

    public ImageView startShow(View initialView, Object id) {
        if (!Config.get().isAnimationLeisure()) return null;
        ImageView mImageView = getImageView(initialView.getContext());
        mBackgroundLayout.addView(mImageView);
        mRootView.addView(mBackgroundLayout);
        if (Config.get().isClickClose()) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    close();
                }
            });
        }
        setImageView(mImageView, id);
        mLocation = ViewUtils.getViewLocation(initialView, adjust);
        mImageView.layout(mLocation.getLeft(), mLocation.getTop(), mLocation.getWidth(), mLocation.getWidth());
        open(mBackgroundLayout);
        return mImageView;
    }


    public void startShow(View initialView, int position, OnDismissBackTo onDismissBackTo) {
        isDefault = false;
        mLocation = ViewUtils.getViewLocation(initialView, true);
        mOnDismissBackTo = onDismissBackTo;
        mViewPager.setCurrentItem(position);
        open(mBackgroundLayout);
    }

    public int getShowPosition() {
        if (isDefault) return -1;
        if (mViewPager != null) {
            return mViewPager.getCurrentItem();
        }
        return -1;
    }

    public void close() {
        if (!Config.get().isAnimationLeisure()) return;
        visibility(View.GONE);
        Config.get().setAnimationLeisure(false);
        if (mOnDismissBackTo != null) {
            View view = mOnDismissBackTo.onBackTo(mViewPager.getCurrentItem());
            if (view == null) {
                mLocation = new Location(ViewUtils.SCREE_HEIGHT / 2, ViewUtils.SCREE_WIDTH / 2, 0, 0);
            } else {
                mLocation = ViewUtils.getViewLocation(view, true);
            }
        }
        if (mLocation != null) {
            Methods.closeLayout(mLocation, mBackgroundLayout).addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mBackgroundLayout.setVisibility(View.GONE);
                    Config.get().setAnimationLeisure(true);
                    visibility(View.VISIBLE);
                    recycle();
                }
            });
        }
    }

    private void open(final View targetView) {
        if (!Config.get().isAnimationLeisure()) return;
        visibility(View.GONE);
        isOpen = true;
        ValueAnimator animation = Methods.getValueAnimator(mLocation, targetView);
        Config.get().setAnimationLeisure(false);
        animation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Config.get().setAnimationLeisure(true);
                visibility(View.VISIBLE);
            }
        });
    }

    public void setViewPager(ViewGroup viewGroup) {
        if (viewGroup instanceof ViewPager) {
            mViewPager = (ViewPager) viewGroup;
            if (mViewPager.getAdapter() == null) {
                return;
            }
            //mBackgroundLayout = ViewUtils.getBackgroundLayout(viewGroup.getContext(), Config.get().getBackgroundColor());
            mBackgroundLayout.addView(viewGroup);
            mRootView.addView(mBackgroundLayout);
        } else {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof ViewPager) {
                    mViewPager = (ViewPager) viewGroup.getChildAt(i);
                    if (mViewPager.getAdapter() == null) {
                        return;
                    }
                    mBackgroundLayout = viewGroup;
                    mRootView.addView(mBackgroundLayout);
                    return;
                }
            }
        }
    }

    public void setViewPager(ViewGroup viewGroup, List urlList, IGetShowUrl getShowUrl) {
        if (viewGroup instanceof ViewPager) {
            mViewPager = (ViewPager) viewGroup;
            // mBackgroundLayout = ViewUtils.getBackgroundLayout(viewGroup.getContext(), Config.get().getBackgroundColor());
            mBackgroundLayout.addView(viewGroup);
            mRootView.addView(mBackgroundLayout);
            findViewPager(urlList, getShowUrl);
            return;
        } else {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof ViewPager) {
                    mViewPager = (ViewPager) viewGroup.getChildAt(i);
                    mBackgroundLayout = viewGroup;
                    mRootView.addView(mBackgroundLayout);
                    findViewPager(urlList, getShowUrl);
                    return;
                }
            }
        }
        throw new Resources.NotFoundException("没有找到viewpager");
    }

    public List<ImageView> getImageViewList(List urlList, IGetShowUrl getShowUrl) {
        List<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            ImageView imageView = getImageView(mRootView.getContext());
            setImageView(imageView, getShowUrl.getUrl(i, imageView));
            imageViews.add(imageView);
            if (Config.get().isClickClose()) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close();
                    }
                });
            }
        }
        return imageViews;
    }

    private void findViewPager(List urlList, IGetShowUrl getShowUrl) {

        if (mViewPager.getAdapter() == null) {
            List<ImageView> imageViews = new ArrayList<>();
            for (int i = 0; i < urlList.size(); i++) {
                ImageView imageView = getImageView(mViewPager.getContext());
                setImageView(imageView, getShowUrl.getUrl(i, imageView));
                imageViews.add(imageView);
                if (Config.get().isClickClose()) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            close();
                        }
                    });
                }
            }
            mViewPager.setAdapter(new DefaultPagerAdapter(imageViews));
        }
    }


    private void visibility(int visibility) {
        if (isDefault) {
            return;
        }
        if ((mBackgroundLayout instanceof ViewPager)) return;
        for (int i = 0; i < mBackgroundLayout.getChildCount(); i++) {
            if (!(mBackgroundLayout.getChildAt(i) instanceof ViewPager)) {
                mBackgroundLayout.getChildAt(i).setVisibility(visibility);
            }
        }

    }


    private ImageView getImageView(Context context) {
        return mShowConfig == null ? Config.get().getImageView(context) : mShowConfig.getImageView(context);
    }

    private void setImageView(ImageView imageView, Object url) {
        if (mShowConfig == null) {
            Config.get().setImageView(imageView, url);
        } else {
            mShowConfig.showImageView(imageView, url);
        }
    }

    /**
     * build模式
     *
     * @return
     */
    public static Build builder() {
        return new Build();
    }

    /**
     * build模式
     */
    public static class Build {
        private ConfigSetting mConfigSetting;

        Build() {
            mConfigSetting = new ConfigSetting();
        }

        public Build showConfig(ShowConfig showConfig) {

            mConfigSetting.setShowConfig(showConfig);
            return this;
        }

        public Build setDuration(int duration) {
            mConfigSetting.setDuration(duration);
            return this;
        }

        /**
         * 设置图片下面背景的颜色
         *
         * @param color
         * @return
         */
        public Build setBackgroundColor(int color) {
            mConfigSetting.setBackgroundColor(color);
            return this;
        }

        public void build() {
            Config.get().setConfigSetting(mConfigSetting);
        }
    }
}
