package com.ls.iusta.domain.models.tickets

data class GetTicketsRequest(
    val ticket_status: String,
    var auth_token: String
)
