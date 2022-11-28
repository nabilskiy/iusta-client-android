package com.ls.iusta.remote.models.terms

import com.google.gson.annotations.SerializedName

data class TermsResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: List<TermsModel>
)
