package com.ls.iusta.data.models.ticket

data class CreateTicketEntity(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val response: ShortTicketEntity?
)
