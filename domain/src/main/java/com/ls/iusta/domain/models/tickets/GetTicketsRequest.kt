package com.ls.iusta.domain.models.tickets

data class GetTicketsRequest(
    val ticket_status: Boolean,
    val pageNumber: Int?
)
