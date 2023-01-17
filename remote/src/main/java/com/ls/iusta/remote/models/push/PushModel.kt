package com.ls.iusta.remote.models.push


data class PushModel(
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

