package com.example.snapem_v1

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException

class Locations() : AppCompatActivity(), LocationListener {
    private var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //////////////////////////////////////////////////////////////////
        val window = window
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        // to wake up the screen
        val pm = applicationContext.getSystemService(POWER_SERVICE) as PowerManager
        @SuppressLint("InvalidWakeLockTag") val wakeLock = pm.newWakeLock(
            (PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP),
            "TAG"
        )
        wakeLock.acquire()

        // to release the screen lock
        val keyguardManager =
            applicationContext.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        val keyguardLock = keyguardManager.newKeyguardLock("TAG")
        keyguardLock.disableKeyguard()
        //////////////////////////
        Log.e("methana location eka", "onCreate")
        //////////////////////////////////////////////////////////////////
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
            return
        }

        // Get last known location
        val lastKnownLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            // Use the last known location
            val latitude = lastKnownLocation.latitude
            val longitude = lastKnownLocation.longitude
            Log.e("Location case eka", "methana location eka$latitude,$longitude")
            // ...
        }

        // Register location listener
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
    }

    private val got_Location = false
    override fun onLocationChanged(location: Location) {
        // Called when the location is updated
        val latitude = location.latitude
        val longitude = location.longitude
        println("methana location eka change$latitude,$longitude")
        finish()
        val value = "$latitude,$longitude"
        SendToserver.location = value
        try {
            SendToserver.hereValues()
        } catch (e: IOException) {
            println(e)
        }
        //SendToserver.uploadEvrything();

        // ...
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    companion object {
        private val TAG = "Location"
        private val PERMISSION_REQUEST_CODE = 123
    }
}