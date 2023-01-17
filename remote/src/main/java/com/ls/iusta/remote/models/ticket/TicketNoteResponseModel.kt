package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class TicketNoteResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val response: String
)
