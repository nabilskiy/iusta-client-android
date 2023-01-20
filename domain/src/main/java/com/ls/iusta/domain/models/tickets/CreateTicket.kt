package com.ls.iusta.domain.models.tickets

data class CreateTicket(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val response: ShortTicket?
)
