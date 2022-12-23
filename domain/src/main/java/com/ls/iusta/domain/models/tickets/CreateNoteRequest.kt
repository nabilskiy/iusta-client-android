package com.ls.iusta.domain.models.tickets

data class CreateNoteRequest(
    val ticketId: Long,
    val ticketNote: String?
)
