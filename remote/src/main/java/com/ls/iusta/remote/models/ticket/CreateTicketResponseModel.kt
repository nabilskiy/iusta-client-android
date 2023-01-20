package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class CreateTicketResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    @SerializedName("response")
    val response: ShortTicketModel?
)
