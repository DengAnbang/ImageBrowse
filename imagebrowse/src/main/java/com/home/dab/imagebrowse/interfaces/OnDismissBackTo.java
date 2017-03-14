package com.home.dab.imagebrowse.interfaces;

import android.view.View;

/**
 * Created by DAB on 2017/3/2 14:16.
 */

public interface OnDismissBackTo {
    /**
     *
     * 关闭的时候,动画回到那个view上
     * @param position
     * @return 如果返回null, 就会退回到屏幕中间
     */
    View onBackTo(int position);
}
