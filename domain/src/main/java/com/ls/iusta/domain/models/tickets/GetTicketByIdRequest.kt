package com.ls.iusta.domain.models.tickets

data class GetTicketByIdRequest(
    val ticket_status: String,
    val ticket_id: Long
)
