package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class TicketNoteResponseModel(
    var success: Boolean,
    @SerializedName("response")
    val response: String
)
