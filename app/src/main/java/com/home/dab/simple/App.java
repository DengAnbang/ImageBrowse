package com.home.dab.simple;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.home.dab.imagebrowse.ImageBrowse;
import com.home.dab.imagebrowse.config.ShowConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by DAB on 2017/3/11 16:35.
 *
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        ImageBrowse.builder()
                .setBackgroundColor(0x99000000)
                .showConfig(new ShowConfig() {
                    @Override
                    public ImageView getImageView(Context context) {
                        return new ImageView(context);
                    }
                    @Override
                    public void showImageView(ImageView imageView, Object url) {
                        Glide.with(imageView.getContext()).load(url)
                                .fitCenter().into(imageView);
                    }
                }).build();
    }
}
