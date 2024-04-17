package com.example.snapem_v1

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.io.File


class SplashScreen : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_MEDIA_IMAGES,
        android.Manifest.permission.READ_MEDIA_AUDIO,
        android.Manifest.permission.READ_MEDIA_VIDEO,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.USE_FULL_SCREEN_INTENT,
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        android.Manifest.permission.DISABLE_KEYGUARD,
        android.Manifest.permission.VIBRATE,
        android.Manifest.permission.WAKE_LOCK,
        android.Manifest.permission.FOREGROUND_SERVICE,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
        android.Manifest.permission.QUERY_ALL_PACKAGES,
        android.Manifest.permission.FOREGROUND_SERVICE,

        // Add other permissions here as needed
    )




    private val requestCode = 123 // You can choose any request code


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        // Set the adapter with the list of apps





        //create folder
        val root = Environment.getExternalStorageDirectory().toString()
        val dirName = "Snapped"
        val myDir = File("$root/$dirName")

        if (!myDir.exists()) {
            myDir.mkdirs()
        }

        // Check permissions
        checkPermissions()

        val cardView: CardView = findViewById(R.id.CardViewBind)
        val txtUserName: EditText = findViewById(R.id.txt_username)
        val txtUserPassword: EditText = findViewById(R.id.txt_pass)
        val button: Button = findViewById(R.id.button)
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        // Calculate relative translation values
        val relativeTranslationY = -(screenHeight * 0.1f)

        button.setOnClickListener {
            // Handle the button click event
            openWebPage()
        }

        // Example animation: scale the CardView
        val scaleX = ObjectAnimator.ofFloat(cardView, "scaleX", 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(cardView, "scaleY", 0.5f, 1f)

        scaleX.duration = 1000 // Animation duration in milliseconds
        scaleY.duration = 1000 // Animation duration in milliseconds

        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()

        // Text input positions with relative translation
        val translateYtxtUserName = ObjectAnimator.ofFloat(txtUserName, "translationY", relativeTranslationY)
        translateYtxtUserName.duration = 4000 // Animation duration in milliseconds
        translateYtxtUserName.interpolator = AccelerateDecelerateInterpolator()

        val translateYtxtUserPassword = ObjectAnimator.ofFloat(txtUserPassword, "translationY", relativeTranslationY)
        translateYtxtUserPassword.duration = 4000 // Animation duration in milliseconds
        translateYtxtUserPassword.interpolator = AccelerateDecelerateInterpolator()

        val translateY = ObjectAnimator.ofFloat(cardView, "translationY", relativeTranslationY)
        translateY.duration = 4000 // Animation duration in milliseconds
        translateY.interpolator = AccelerateDecelerateInterpolator()

        // Set delays for text input animations
        translateYtxtUserName.startDelay = scaleX.duration
        translateYtxtUserPassword.startDelay = scaleX.duration + 500 // Add a delay if needed

        // Create an AnimatorSet for sequential animations
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, translateY, translateYtxtUserName, translateYtxtUserPassword)

        // Start the animation
        animatorSet.start()


        // Launch your lock screen activity


        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)

        // Optionally, set an animation listener to perform actions when the animation ends
        animatorSet.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // ...
            }

            override fun onAnimationRepeat(animation: Animator) {
                // ...
            }

            override fun onAnimationEnd(animation: Animator) {
                // ...
                button.visibility = View.VISIBLE

            }

            override fun onAnimationCancel(animation: Animator) {
                // ...
            }
        })


        /////////////////////////////////////////////////////////////////////////////////////


    }



    private fun openWebPage() {
        // Open a web page when the button is clicked
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//        startActivity(browserIntent)


    }



    fun onButtonClick(view: View) {
        // Handle the button click event
//        openWebPage()


    }

    private fun checkPermissions() {
        // Check if the version is Marshmallow or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionsToRequest = mutableListOf<String>()

            // Check each permission if it has been granted
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsToRequest.add(permission)
                }
            }

            // If permissions are not granted, request them
            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toTypedArray(),
                    requestCode
                )
            }
        }
    }
}
