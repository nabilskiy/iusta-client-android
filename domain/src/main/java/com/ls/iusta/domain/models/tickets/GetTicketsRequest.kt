package com.ls.iusta.domain.models.tickets

data class GetTicketsRequest(
    val ticket_status: String,
    val pageNumber: Int?
)
