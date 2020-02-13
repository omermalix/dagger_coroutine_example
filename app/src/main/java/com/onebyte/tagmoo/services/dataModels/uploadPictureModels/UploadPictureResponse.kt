package com.onebyte.tagmoo.services.dataModels.uploadPictureModels

import com.onebyte.tagmoo.services.dataModels.errorModel.Status

data class UploadPictureResponse(
    var status: Status,
    var body: UploadPictureBody
)