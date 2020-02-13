package com.onebyte.tagmoo.ui.cameraView

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.onebyte.tagmoo.R
import com.onebyte.tagmoo.databinding.ActivityCameraBinding
import com.onebyte.tagmoo.ui.base.BaseActivity
import com.onebyte.tagmoo.utils.Constants
import com.onebyte.tagmoo.utils.CustomPopups
import com.onebyte.tagmoo.utils.PrefManager
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.concurrent.Executors

class CameraActivity : BaseActivity<ActivityCameraBinding, CameraViewModel>() {
    override fun getViewModelClass(): Class<CameraViewModel> = CameraViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_camera
    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
        initView()
    }

    private fun initView() {
        if (allPermissionsGranted()) {
            cameraView.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private val executor = Executors.newSingleThreadExecutor()

    private fun setListeners() {
        ivCapture.setOnClickListener{

        }

        cameraView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = cameraView.parent as ViewGroup
            parent.removeView(cameraView)
            parent.addView(cameraView, 0)

            cameraView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)

        ivCapture.setOnClickListener {
            val file = File(externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg")

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        cameraView.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        Log.d("CameraXApp", msg)
                        cameraView.post {
                            PrefManager.savePreferences(Constants.PreferenceKeys.imageUrl, file.toString(), baseContext)
                            finish()
                        }
                    }
                })
        }

        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            // In our analysis, we care more about the latest image than
            // analyzing *every* image
            setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()

        // Build the image analysis use case and instantiate our analyzer
        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            setAnalyzer(executor, LuminosityAnalyzer())
        }

        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUseCase)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = cameraView.width / 2f
        val centerY = cameraView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(cameraView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        cameraView.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraView.post { startCamera() }
            } else {
                CustomPopups().showAlertPopup("Camera Permission",
                    "You have to give camera permission to use this feature.", this, onClick)
            }
        }
    }

    private val onClick = object: CustomPopups.OnClick{
        override fun onBackPress() {
            initView()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
