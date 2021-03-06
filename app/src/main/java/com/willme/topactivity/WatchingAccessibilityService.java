package com.willme.topactivity;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Wen on 1/14/15.
 */
public class WatchingAccessibilityService extends AccessibilityService {
    private static final String TAG = "WatchingAccessibilitySe";
    private static WatchingAccessibilityService sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }


    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: "+event.getAction());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            if(SPHelper.isShowWindow(this)){
                TasksWindow.show(this, "package name:"+event.getPackageName() + "\n" + event.getClassName());
            }
        }

    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        sInstance = this;
        if(SPHelper.isShowWindow(this)){
            NotificationActionReceiver.showNotification(this, false);
        }
        sendBroadcast(new Intent(QuickSettingTileService.ACTION_UPDATE_TITLE));
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        sInstance = null;
        TasksWindow.dismiss(this);
        NotificationActionReceiver.cancelNotification(this);
        sendBroadcast(new Intent(QuickSettingTileService.ACTION_UPDATE_TITLE));
        return super.onUnbind(intent);
    }

    public static WatchingAccessibilityService getInstance(){
        return sInstance;
    }

}
