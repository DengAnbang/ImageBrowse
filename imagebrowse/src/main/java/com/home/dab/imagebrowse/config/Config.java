package com.home.dab.imagebrowse.config;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.dab.imagebrowse.interfaces.IConfig;


/**
 * Created by DAB on 2017/3/10 17:09.
 */

public class Config implements IConfig {
    private volatile static Config instance;
    private ConfigSetting mConfigSetting;
    private boolean isAnimationLeisure = true;

    public boolean isAnimationLeisure() {
        return isAnimationLeisure;
    }

    public void setAnimationLeisure(boolean animationLeisure) {
        isAnimationLeisure = animationLeisure;
    }

    public void setConfigSetting(ConfigSetting configSetting) {
        mConfigSetting = configSetting;
    }

    private Config() {

    }

    public void recycle() {
        mConfigSetting = null;
        instance = null;
    }
    private Config(ConfigSetting configSetting) {
        mConfigSetting = configSetting;
    }

    public static Config get() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    @Override
    public ImageView getImageView(Context context) {
        ImageView imageView;
        if (mConfigSetting == null) {
            imageView = new ImageView(context);
        } else {
            imageView=mConfigSetting.getShowConfig().getImageView(context);
        }

        ViewGroup.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(getBackgroundColor());
        return imageView;
    }

    @Override
    public void setImageView(ImageView imageView, Object url) {
        if (mConfigSetting != null) {
            mConfigSetting.getShowConfig().showImageView(imageView, url);
        } else {
            throw new RuntimeException("没有找到加载图片的方法");
        }
    }


    @Override
    public int getBackgroundColor() {
        if (mConfigSetting == null) {
            return 0;
        }
        return mConfigSetting.getBackgroundColor();
    }

    @Override
    public boolean isClickClose() {
        return mConfigSetting == null || mConfigSetting.isClickClose();
    }

    @Override
    public int duration() {
        return mConfigSetting == null ? 300 : mConfigSetting.getDuration();
    }


}
