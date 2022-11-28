package com.ls.iusta.remote.models.worker

import com.google.gson.annotations.SerializedName
import com.ls.iusta.remote.models.terms.TermsModel

data class WorkerResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: WorkerModel
)
