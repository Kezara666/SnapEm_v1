package com.example.snapem_v1

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * This is the component that is responsible for actual device administration.
 * It becomes the receiver when a policy is applied. It is important that we
 * subclass DeviceAdminReceiver class here and to implement its only required
 * method onEnabled().
 */
class DemoDeviceAdminReceiver : DeviceAdminReceiver() {
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    /** Called when this application is approved to be a device administrator.  */
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Toast.makeText(
            context, "device_admin_enabled",
            Toast.LENGTH_LONG
        ).show()
        Log.d(TAG, "onEnabled")
    }

    /** Called when this application is no longer the device administrator.  */
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Toast.makeText(
            context, "device_admin_disabled",
            Toast.LENGTH_LONG
        ).show()
        Log.d(TAG, "onDisabled")
    }

    override fun onPasswordChanged(context: Context, intent: Intent) {
        super.onPasswordChanged(context, intent)
        Log.d(TAG, "onPasswordChanged")
    }

    override fun onPasswordFailed(context: Context, intent: Intent) {
        super.onPasswordFailed(context, intent)
        Log.d(TAG, "onPasswordFailed")
        super.onPasswordFailed(context, intent)
        println("Password Attempt is Failed...")


        // to wake up the screen
        try {
            Thread.sleep(1000)
//            val k = Intent(context, Locations::class.java)
//            k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            k.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            context.applicationContext.startActivity(k)
//            val j = Intent(context, CameraViewFront::class.java)
//            j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            j.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            context.applicationContext.startActivity(j)



//            val j = Intent(context, CameraViewFront::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//            }
//            context.applicationContext.startActivity(j)

            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(context, CameraViewBack::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                context.applicationContext.startActivity(i)
            }, 1000)


            Handler(Looper.getMainLooper()).postDelayed({
                val j = Intent(context, CameraViewFront::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                context.applicationContext.startActivity(j)
            }, 1000)


            val serviceIntent = Intent(context, LocationService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)






        } catch (e: InterruptedException) {
            println(e)
        }


// Call it again
    }

    override fun onPasswordSucceeded(context: Context, intent: Intent) {
        super.onPasswordSucceeded(context, intent)
        Log.d(TAG, "onPasswordSucceeded")
    }

    companion object {
        const val TAG = "DemoDeviceAdminReceiver"
    }
}