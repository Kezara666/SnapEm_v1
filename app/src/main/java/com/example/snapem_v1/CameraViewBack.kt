package com.example.snapem_v1

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.hardware.Camera.PictureCallback
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraViewBack() : Activity(), SurfaceHolder.Callback, View.OnClickListener {
    var mCamera: Camera? = null
    var mPreviewRunning = false
    var cameraOpened = false
    @SuppressLint("InvalidWakeLockTag", "MissingInflatedId",)
    public override fun onCreate(icicle: Bundle?) {
        stopCamera()
        //////////////////////////


        // These flags ensure that the activity can be launched when the screen is locked.
        val window = window
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        // to wake up the screen
        val pm = applicationContext.getSystemService(POWER_SERVICE) as PowerManager
        val wakeLock = pm.newWakeLock(
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
        super.onCreate(icicle)
        Log.e(TAG, "onCreate")

        /////////////////////////////////


        //////////////////////////////////
        setContentView(R.layout.activity_camera_view_back)
        mSurfaceView = findViewById<View>(R.id.surface_camera) as SurfaceView
        mSurfaceHolder = mSurfaceView!!.holder
        mSurfaceHolder?.addCallback(this)
        if (mSurfaceHolder != null) {
            mSurfaceHolder!!.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
            mSurfaceHolder!!.setKeepScreenOn(true)
        }

        // mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.e(TAG, "onStop")
        super.onStop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        Log.e(TAG, "surfaceChanged")
        val texName = 123 // choose any integer as texture name
        val surfaceTexture = SurfaceTexture(texName)

        // XXX stopPreview() will crash if preview is not running
        if (mPreviewRunning) {
            mCamera!!.stopPreview()
        }
        val p = mCamera!!.parameters
        mCamera!!.setDisplayOrientation(90)
        try {
            mCamera!!.setPreviewTexture(surfaceTexture)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        mCamera!!.parameters = p
        mCamera!!.startPreview()
        mPreviewRunning = true
        mCamera!!.takePicture(null, null, mPictureCallback)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.e(TAG, "surfaceDestroyed")
        mCamera?.stopPreview();
         mPreviewRunning = false;
        mCamera?.release();
        stopCamera()
    }

    private var mSurfaceView: SurfaceView? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    override fun onClick(v: View) {
        mCamera!!.takePicture(null, mPictureCallback, mPictureCallback)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.e(TAG, "surfaceCreated")
        var i = findFrontFacingCamera()-1;
        if (i > 0);

        while (true) {
            try {
                mCamera?.release();
                mCamera = Camera.open(i)
                try {
                    if (mCamera != null) {
                        mCamera!!.setPreviewDisplay(holder)
                        return
                    } else {
                        // Handle the case where mCamera is null
                    }
                    return
                } catch (localIOException2: IOException) {
                    localIOException2.printStackTrace()
                    stopCamera()
                    return
                }
            } catch (localRuntimeException: RuntimeException) {
                localRuntimeException.printStackTrace()
                if (mCamera == null) continue
                stopCamera()
                mCamera = Camera.open(i)
                try {
                    if (mCamera != null) {
                        mCamera!!.setPreviewDisplay(holder)
                        return
                    } else {
                        // Handle the case where mCamera is null
                        return
                    }
                } catch (localIOException1: IOException) {
                    stopCamera()
                    localIOException1.printStackTrace()
                    return
                }
            } catch (localException: Exception) {
                if (mCamera != null) stopCamera()
                localException.printStackTrace()
                return
            }
        }
    }

    private fun stopCamera() {
        if (mCamera != null) {

			 this.mCamera!!.stopPreview(); this.mCamera!!.release(); this.mCamera = null;

            mPreviewRunning = false
        }
    }

    private fun findFrontFacingCamera(): Int {
        val i = Camera.getNumberOfCameras()
        Log.e("Object eka", i.toString())
        var j = 0
        while (true) {
            if (j >= i) return -1
            val localCameraInfo = CameraInfo()
            Camera.getCameraInfo(j, localCameraInfo)
            if (localCameraInfo.facing == 1) return j
            j++
        }
    }

    var mPictureCallback: PictureCallback =
        PictureCallback { data, camera ->
            if (data != null) {
                // Intent mIntent = new Intent();
                // mIntent.putExtra("image",imageData);
                mCamera!!.stopPreview()
                mPreviewRunning = false
                mCamera!!.release()
                try {
                    val opts = BitmapFactory.Options()
                    var bitmap = BitmapFactory.decodeByteArray(
                        data, 0,
                        data.size, opts
                    )
                    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false)
                    val width = bitmap.width
                    val height = bitmap.height
                    val newWidth = 300
                    val newHeight = 300

                    // calculate the scale - in this case = 0.4f
                    val scaleWidth = (newWidth.toFloat()) / width
                    val scaleHeight = (newHeight.toFloat()) / height

                    // createa matrix for the manipulation
                    val matrix = Matrix()
                    // resize the bit map
                    matrix.postScale(scaleWidth, scaleHeight)
                    // rotate the Bitmap
                    matrix.postRotate(90f)
                    val resizedBitmap = Bitmap.createBitmap(
                        bitmap, 0, 0,
                        width, height, matrix, true
                    )
                    val bytes = ByteArrayOutputStream()
                    resizedBitmap.compress(
                        Bitmap.CompressFormat.JPEG, 40,
                        bytes
                    )

                    val directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Snapped")
                    if (!directory.exists()) {
                        if (!directory.mkdirs()) {
                            Log.e(TAG, "Failed to create directory: $directory")

                        }
                    }

                    val filename = "${System.currentTimeMillis()}-2Backcam.jpg"
                    val outputFile = File(directory, filename)

                    try {
                        // Create the file if it doesn't exist
                        val fileCreated = outputFile.createNewFile()
                        if (!fileCreated) {
                            Log.e(TAG, "Failed to create file: $outputFile")

                        }

                        val fo = FileOutputStream(outputFile)
                        fo.write(bytes.toByteArray())
                        Log.d(TAG, "File saved: $outputFile")
                        SendToserver.back = outputFile.toString()
                    } catch (e: IOException) {
                        Log.e(TAG, "Error writing to file", e)
                    }


                    // remember close de FileOutput

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // StoreByteImage(mContext, imageData, 50,"ImageName");
                // setResult(FOTO_MODE, mIntent);
                setResult(585)
                finish()
            }
        }

    companion object {
        private val TAG = "CameraTest"
    }
}