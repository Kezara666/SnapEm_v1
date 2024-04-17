package com.example.snapem_v1;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null) {
                String foregroundAppPackageName = event.getPackageName().toString();
                // Check if the foreground app needs to be locked
                if (foregroundAppPackageName.equals("com.example.snapped_v1")) {
                    // Launch your lock screen activity
                    Intent lockIntent = new Intent(this, LockScreenActivity.class);
                    lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(lockIntent);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Respond to interruptions if needed
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("AccessibilityService", "Service connected");
    }

}
