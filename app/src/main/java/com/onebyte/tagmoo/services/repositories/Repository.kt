package com.onebyte.tagmoo.services.repositories

import com.onebyte.tagmoo.services.networkServices.NetworkService
import com.onebyte.tagmoo.services.dataModels.uploadPictureModels.UploadPictureResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(private val networkService: NetworkService) {
    suspend fun uploadImage(imageUrl: String): Response<UploadPictureResponse> {
        val file = File(imageUrl)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return networkService.uploadImage(body)
    }
}