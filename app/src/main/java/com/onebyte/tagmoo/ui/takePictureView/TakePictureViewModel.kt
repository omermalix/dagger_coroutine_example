package com.onebyte.tagmoo.ui.takePictureView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onebyte.tagmoo.services.dataModels.uploadPictureModels.UploadPictureResponse
import com.onebyte.tagmoo.services.repositories.Repository
import com.onebyte.tagmoo.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class TakePictureViewModel @Inject constructor() :
    BaseViewModel() {
    @Inject
    lateinit var repository: Repository

    private val uploadPictureResponse: MutableLiveData<Any> = MutableLiveData()

    fun uploadPictureResponse(): LiveData<Any> = uploadPictureResponse

    fun uploadImage(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {

            runCatching {
                repository.uploadImage(imageUrl)
            }.onSuccess {
                if(it.isSuccessful)
                    uploadPictureResponse.postValue(it.body())
                else
                    Log.e("ERROR_IMAGE_UPLOAD", it.errorBody()!!.string())
            }.onFailure {
                Log.e("ERROR_IMAGE_UPLOAD", it.message!!)
            }
        }
    }
}