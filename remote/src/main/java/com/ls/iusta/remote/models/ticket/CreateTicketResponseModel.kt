package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class CreateTicketResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: ShortTicketModel
)
