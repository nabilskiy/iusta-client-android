package com.ls.iusta.remote.models

import com.google.gson.annotations.SerializedName

data class TicketResponseModel(
    @SerializedName("results")
    val tikets: List<TicketModel>
)
