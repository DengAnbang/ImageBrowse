package com.home.dab.imagebrowse.config;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by DAB on 2017/3/11 16:06.
 */

public abstract class ShowConfig {

    public ImageView getImageView(Context context) {
        return Config.get().getImageView(context);
    }

    public void showImageView(ImageView imageView, Object url) {
        Config.get().setImageView(imageView, url);
    }
}
