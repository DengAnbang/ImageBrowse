package com.home.dab.imagebrowse.config;

/**
 * Created by DAB on 2017/3/11 13:57.
 */

public class ConfigSetting {
    private ShowConfig mShowConfig;
    private int mBackgroundColor;
    private boolean isClickClose = true;
    private int duration = 300;
    public ConfigSetting() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setShowConfig(ShowConfig showConfig) {
        mShowConfig = showConfig;
    }

    ShowConfig getShowConfig() {
        return mShowConfig;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public boolean isClickClose() {
        return isClickClose;
    }

    public void setClickClose(boolean clickClose) {
        isClickClose = clickClose;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
}
