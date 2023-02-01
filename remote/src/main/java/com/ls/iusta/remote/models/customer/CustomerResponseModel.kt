package com.ls.iusta.remote.models.customer

import com.google.gson.annotations.SerializedName

data class CustomerResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    @SerializedName("response")
    val response: List<CustomerModel>?
)
