package com.snoopy112.backbutton;

import static com.snoopy112.backbutton.MainActivity.isAccessibilityServiceEnabled;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;


public class AutoStart extends BroadcastReceiver
{
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent to = new Intent(context, TouchWindowService.class);
        if (isAccessibilityServiceEnabled(context, BackButtonService.class) && Settings.canDrawOverlays(context))
            context.startService(to);
    }
}