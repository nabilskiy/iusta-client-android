package com.ls.iusta.remote.models.customer

import com.google.gson.annotations.SerializedName

data class CustomerResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: List<CustomerModel>
)
