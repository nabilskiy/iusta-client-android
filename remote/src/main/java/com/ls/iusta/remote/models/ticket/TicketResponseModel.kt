package com.ls.iusta.remote.models.ticket

import com.google.gson.annotations.SerializedName

data class TicketResponseModel(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    var pageNumber: Int,
    var perPage: Int,
    var pageSize: Int,
    var totalPagesCount: Int,
    val response: List<TicketModel>
)
