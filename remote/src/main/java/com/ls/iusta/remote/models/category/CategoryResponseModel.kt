package com.ls.iusta.remote.models.category

import com.google.gson.annotations.SerializedName
import com.ls.iusta.remote.models.customer.CustomerModel

data class CategoryResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    @SerializedName("response")
    val response: CategoryResponseInfoModel
)
