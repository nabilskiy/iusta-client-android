package com.ls.iusta.domain.models.push


data class Push(
    val id: Int,
    val contact_id: Int,
    val ticket_id: Long?,
    val title: String,
    val text: String,
    val message_id: String?,
    val read: Int,
    val read_at: String?,
    val created_at: String
)

