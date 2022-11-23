package com.ls.iusta.remote.models

import com.google.gson.annotations.SerializedName

data class TicketResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: List<TicketModel>
)
