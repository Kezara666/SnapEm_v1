package com.example.snapem_v1

import android.app.Activity
import android.os.Bundle


class LockScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the view for your lock screen
        setContentView(R.layout.activity_lock_screen)
        // Implement lock screen logic here
    }
}