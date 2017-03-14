package com.home.dab.simple;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.home.dab.imagebrowse.ImageBrowse;
import com.home.dab.imagebrowse.adapter.DefaultPagerAdapter;
import com.home.dab.imagebrowse.config.ShowConfig;
import com.home.dab.imagebrowse.interfaces.IGetShowUrl;
import com.home.dab.imagebrowse.interfaces.OnDismissBackTo;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PagerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private List<Integer> mIntegerList;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        initData();
        initView();
    }

    private void initData() {
        mIntegerList = new ArrayList<>();
        mIntegerList.add(R.mipmap.image_userd0);
        mIntegerList.add(R.mipmap.image_usera0);
        mIntegerList.add(R.mipmap.image_usera3);
        mIntegerList.add(R.mipmap.image_userc0);
        mIntegerList.add(R.mipmap.image_userd1);
        mIntegerList.add(R.mipmap.image_usere1);
        mIntegerList.add(R.mipmap.image_userc1);
        mIntegerList.add(R.mipmap.image_usere0);
        mIntegerList.add(R.mipmap.image_userf0);
        mIntegerList.add(R.mipmap.image_usera3);
        mIntegerList.add(R.mipmap.ic_launcher);
//        mPhotoAdapter.setIntegerList(mIntegerList);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mPhotoAdapter = new PhotoAdapter();
        mPhotoAdapter.setIntegerList(mIntegerList);

        mRecyclerView.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                custom(view, position);
            }
        });
    }

    private void defaultAdapter(View view, int position) {
        final ImageBrowse imageBrowse = new ImageBrowse(PagerActivity.this);
        ViewGroup inflate = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_pager_view, null);
        imageBrowse.setViewPager(inflate);
        imageBrowse.setViewPager(inflate, mIntegerList, new IGetShowUrl() {
            @Override
            public Object getUrl(int position, ImageView imageView) {
                return mIntegerList.get(position);
            }

        });
        imageBrowse.startShow(view, position, new OnDismissBackTo() {
            @Override
            public View onBackTo(int position) {
                return mGridLayoutManager.findViewByPosition(position);
            }
        });
    }

    private void custom(View view, int position) {
        final ImageBrowse imageBrowse = new ImageBrowse(PagerActivity.this,new ShowConfig() {
            @Override
            public ImageView getImageView(Context context) {
                return new PhotoView(context);
            }
        });
        ViewGroup inflate = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_pager_view, null);
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.vp_photo);
        viewPager.setAdapter(new DefaultPagerAdapter(imageBrowse.getImageViewList(mIntegerList, new IGetShowUrl() {
            @Override
            public Object getUrl(int position, ImageView imageView) {
                if (imageView instanceof PhotoView) {
                    PhotoView photoView = (PhotoView) imageView;
                    photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                        @Override
                        public void onPhotoTap(View view, float x, float y) {
                            imageBrowse.close();
                        }
                    });
                }
                return mIntegerList.get(position);
            }

        })));


        imageBrowse.setViewPager(inflate);
        imageBrowse.startShow(view, position, new OnDismissBackTo() {
            @Override
            public View onBackTo(int position) {
                return mGridLayoutManager.findViewByPosition(position);
            }
        });
    }
}
