package com.home.dab.simple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.home.dab.imagebrowse.ImageBrowse;
import com.home.dab.imagebrowse.config.ShowConfig;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.iv);
        final ImageView imageView1 = (ImageView) findViewById(R.id.iv1);
        final ImageView imageView2 = (ImageView) findViewById(R.id.iv2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageBrowse imageBrowse = new ImageBrowse(MainActivity.this);
                imageBrowse.startShow(imageView, R.mipmap.image_userd0);
            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageBrowse imageBrowse = new ImageBrowse(MainActivity.this, new ShowConfig() {
                    @Override
                    public void showImageView(ImageView imageView, Object url) {
                        Glide.with(imageView.getContext()).load(url)
                                .fitCenter().into(imageView);
                    }
                });
                imageBrowse.startShow(imageView1, R.mipmap.image_userd0);
            }
        });



        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageBrowse imageBrowse = new ImageBrowse(MainActivity.this);
                imageBrowse.setAdjust(false);
                imageBrowse.setShowConfig(new ShowConfig() {
                    @Override
                    public ImageView getImageView(Context context) {
                        return new PhotoView(context);
                    }
                });
                ImageView imageView3 = imageBrowse.startShow(imageView2, R.mipmap.image_userd0);
                if (imageView3 instanceof PhotoView) {
                    PhotoView photoView = (PhotoView) imageView3;
                    photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                        @Override
                        public void onPhotoTap(View view, float x, float y) {
                            imageBrowse.close();
                        }
                    });
                }

            }
        });
    }

    public void pager(View view) {
        startActivity(new Intent(this, PagerActivity.class));
    }
}
