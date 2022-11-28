package com.ls.iusta.remote.models.about

import com.google.gson.annotations.SerializedName

data class AboutResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: List<AboutModel>
)
