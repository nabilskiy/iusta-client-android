package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class TicketResponseModel(
    var success: Boolean,
    var pageNumber: Int,
    var perPage: Int,
    var pageSize: Int,
    var totalPagesCount: Int,
    @SerializedName("response")
    val response: List<TicketModel>
)
