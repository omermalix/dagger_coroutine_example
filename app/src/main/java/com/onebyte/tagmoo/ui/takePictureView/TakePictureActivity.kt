package com.onebyte.tagmoo.ui.takePictureView

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.onebyte.tagmoo.R
import com.onebyte.tagmoo.databinding.ActivityTakePictureBinding
import com.onebyte.tagmoo.services.dataModels.uploadPictureModels.UploadPictureResponse
import com.onebyte.tagmoo.ui.base.BaseActivity
import com.onebyte.tagmoo.ui.cameraView.CameraActivity
import com.onebyte.tagmoo.utils.*
import kotlinx.android.synthetic.main.activity_take_picture.*

class TakePictureActivity : BaseActivity<ActivityTakePictureBinding, TakePictureViewModel>() {
    private var imageUrl = ""

    override fun getViewModelClass(): Class<TakePictureViewModel> = TakePictureViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_take_picture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
        getIntentData()
        initView()
        viewModelObservable()
    }

    private fun viewModelObservable() {
        viewModel.uploadPictureResponse().observe(this){
            val res = it as UploadPictureResponse
            Log.e("UPLOADED_IMAGE_URL", res.body.image)
            Snackbar.make(window.decorView.rootView, "Picture uploaded successfully.", Snackbar.LENGTH_LONG).show()
            navigateActivityWithBoolExtra(
                TakePictureActivity::class.java, this, Constants.PreferenceKeys.compare,
                value = true, isFinish = true
            )
        }
    }

    private fun getIntentData() {
        binding.isCompare = intent.getBooleanExtra(Constants.PreferenceKeys.compare, false)
    }

    private fun initView() {
        binding.isShowImage = false
        PrefManager.removePrefrence(this, Constants.PreferenceKeys.imageUrl)
    }

    private fun setListeners() {
        tvRetake.setOnClickListener {
            navigateActivity(CameraActivity::class.java, this, false)
        }
        tvSubmit.setOnClickListener {
            if(!binding.isCompare!!) {
                viewModel.uploadImage(imageUrl)
            } else {
                Snackbar.make(it, "Picture is comparing...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        imageUrl = PrefManager.loadPreferences(this, Constants.PreferenceKeys.imageUrl)!!
        if(imageUrl != "0"){
            binding.isShowImage = true
            ImageLoadAdapter.loadImage(ivShow, imageUrl)
        }
    }
}
