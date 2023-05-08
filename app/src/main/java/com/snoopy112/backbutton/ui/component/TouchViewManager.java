package com.snoopy112.backbutton.ui.component;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import com.snoopy112.backbutton.AppContext;


public class TouchViewManager {

    private static final String TAG = "TouchViewManager";

    private static TouchViewManager touchViewManager = null;

    private TouchViewManager() {
        init();
    }

    public static TouchViewManager getInstance() {
        if (touchViewManager == null) {
            synchronized (TouchViewManager.class) {
                if (touchViewManager == null)
                    touchViewManager = new TouchViewManager();
            }
        }
        return touchViewManager;
    }

    private WindowManager.LayoutParams backButtonLayoutParams = null;
    private WindowManager windowManager = null;
    private BackBtnView backBtnView = null;

    private KeyServiceListener keyServiceListener;


    private void init() {
        backButtonLayoutParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) AppContext.appContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void removeView() {
        windowManager.removeView(backBtnView);
    }

    public void showView() {
        Log.d(TAG, "showView");
        setLayoutParams();
        showBackBtnView();
    }

    private void setLayoutParams() {
        backButtonLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        backButtonLayoutParams.format = PixelFormat.RGBA_8888;
        backButtonLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        backButtonLayoutParams.gravity = Gravity.END | Gravity.BOTTOM;
        backButtonLayoutParams.x = 35;
        backButtonLayoutParams.y = 130;
        backButtonLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        backButtonLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void showBackBtnView() {
        if (backBtnView == null)
            createBackBtnView();

        windowManager.addView(backBtnView, backButtonLayoutParams);
    }

    private void createBackBtnView() {
        backBtnView = new BackBtnView(AppContext.appContext);
        backBtnView.setmOnTouchListener(viewOnTouchListener);
    }


    private final BackBtnView.ViewOnTouchListener viewOnTouchListener = new BackBtnView.ViewOnTouchListener() {

        @Override
        public void onBackTap() {
            if (keyServiceListener != null)
                keyServiceListener.backKey();
        }

        @Override
        public void onHomeTap() {
            if (keyServiceListener != null)
                keyServiceListener.homeKey();
        }

        @Override
        public void onRecentTap() {
            if (keyServiceListener != null)
                keyServiceListener.recentKey();
        }

        @Override
        public void onDrag(float x, float y) {
            Point size = new Point();
            Display display = windowManager.getDefaultDisplay();
            display.getSize(size);
            backButtonLayoutParams.x = (int) (size.x - x);
            backButtonLayoutParams.y = (int) (size.y - y);
            windowManager.updateViewLayout(backBtnView, backButtonLayoutParams);
        }
    };

    public void setKeyServiceListener(KeyServiceListener keyServiceListener) {
        this.keyServiceListener = keyServiceListener;
    }

    public interface KeyServiceListener {
        void backKey();
        void homeKey();
        void recentKey();
    }
}