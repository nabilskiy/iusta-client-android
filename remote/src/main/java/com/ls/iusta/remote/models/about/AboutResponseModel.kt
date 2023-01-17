package com.ls.iusta.remote.models.about

import com.google.gson.annotations.SerializedName

data class AboutResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    @SerializedName("response")
    val response: List<AboutModel>
)
