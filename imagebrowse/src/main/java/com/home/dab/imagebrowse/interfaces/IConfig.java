package com.home.dab.imagebrowse.interfaces;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by DAB on 2017/3/11 13:06.
 */

public interface IConfig {
    /**
     * 显示到哪种ImageView?
     *
     * @param context
     * @return
     */
    ImageView getImageView(Context context);

    /**
     * 怎么显示到imageView上?
     *
     * @param imageView
     * @param url
     */
    void setImageView(ImageView imageView, Object url);

    /**
     * 最外层设置背景颜色(透明度等)
     *
     * @return
     */
    int getBackgroundColor();

    /**
     * 设置是否点击取消(自定义的imageView可能会拦截这个事件,这样可以在)
     *
     * @return
     */
    boolean isClickClose();

    /**
     * 动画的持续时间
     *
     * @return
     */
    int duration();
}
