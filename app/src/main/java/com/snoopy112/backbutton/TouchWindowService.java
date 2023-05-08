package com.snoopy112.backbutton;

import static com.snoopy112.backbutton.MainActivity.isAccessibilityServiceEnabled;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import com.snoopy112.backbutton.ui.component.TouchViewManager;


public class TouchWindowService extends Service {

    private static final String TAG = "TouchWindowService";

    private TouchViewManager touchViewManager;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        touchViewManager = TouchViewManager.getInstance();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean permissions = true;

        Log.d(TAG, "onStartCommand");
        if (!isAccessibilityServiceEnabled(this, BackButtonService.class))
            permissions = false;
        if (!Settings.canDrawOverlays(this))
            permissions = false;
        if (permissions)
            touchViewManager.showView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (touchViewManager != null)
            touchViewManager.removeView();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }
}