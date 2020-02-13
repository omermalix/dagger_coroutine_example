package com.onebyte.tagmoo.services.networkServices

import com.onebyte.tagmoo.services.dataModels.uploadPictureModels.UploadPictureResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface NetworkService {
    @POST("api/v1/app/image-ml/upload-img")
    suspend fun uploadImage(@Body body: MultipartBody.Part): Response<UploadPictureResponse>

}