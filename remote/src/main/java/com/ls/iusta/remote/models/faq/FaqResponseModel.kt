package com.ls.iusta.remote.models.faq

import com.google.gson.annotations.SerializedName

data class FaqResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    @SerializedName("response")
    val response: List<FaqModel>
)
