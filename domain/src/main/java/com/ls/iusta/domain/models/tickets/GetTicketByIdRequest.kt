package com.ls.iusta.domain.models.tickets

data class GetTicketByIdRequest(
    val ticket_status: String,
    var auth_token: String,
    val secret_key: String,
    val ticket_id: Long
)
