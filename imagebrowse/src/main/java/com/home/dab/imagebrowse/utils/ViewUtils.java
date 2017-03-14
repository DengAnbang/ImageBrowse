package com.home.dab.imagebrowse.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.home.dab.imagebrowse.bean.Location;


/**
 * Created by DAB on 2017/2/10 16:18.
 * 操作view相关的方法
 */

public class ViewUtils {
   public static int SCREE_HEIGHT = 0;
   public static int SCREE_WIDTH = 0;

    /**
     * 初始化屏幕的宽高
     *
     * @param context
     */
    public static void initScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        SCREE_HEIGHT = point.y;
        SCREE_WIDTH = point.x;
    }


    public static int getLeft(Rect viewRect) {
        //矩形中点X*2 - 矩形右边的点(因为)
        return 2 * viewRect.centerX() - viewRect.right;
    }

    public static int getTop(Rect viewRect) {
        return 2 * viewRect.centerY() - viewRect.bottom;
    }

    public static ViewGroup getBackgroundLayout(Context context, int backgroundColor) {
        FrameLayout frameLayout = new FrameLayout(context);
//        ViewGroup.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        frameLayout.setLayoutParams(params);
        frameLayout.setBackgroundColor(backgroundColor);
        return frameLayout;
    }

    public static void changeLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 测量view的位置
     * @param view
     * @param adjust
     * @return
     */
    public static Location getViewLocation(View view, boolean adjust) {
        Rect rect = new Rect();
        Location location = new Location();
//        initialView.getLocalVisibleRect (rect);
//        initialView.getDrawingRect(rect);
        if (adjust) {
            view.requestRectangleOnScreen(rect);
            location.setWidth(view.getWidth());
            location.setHeight(view.getHeight());
        } else {
            view.getGlobalVisibleRect(rect);
            location.setWidth(rect.width());
            location.setHeight(rect.height());
        }
        location.setLeft(rect.left);
        location.setTop(rect.top);
        return location;
    }
}
