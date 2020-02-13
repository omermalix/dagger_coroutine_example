package com.onebyte.tagmoo.services.dataModels.errorModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    @Expose
    val code: Int,
    @SerializedName("message")
    @Expose
    val message: String


)