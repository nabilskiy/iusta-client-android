package com.ls.iusta.remote.models.faq

import com.google.gson.annotations.SerializedName

data class FaqResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: List<FaqModel>
)
