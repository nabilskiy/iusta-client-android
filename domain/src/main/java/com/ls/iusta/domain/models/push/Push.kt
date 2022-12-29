package com.ls.iusta.domain.models.push


data class Push(
    val id: Int,
    val worker_id: Int,
    val ticket_id: Int,
    val title: String,
    val text: String,
    val message_id: String?,
    val read: Int,
    val read_at: String?,
    val created_at: String
)

