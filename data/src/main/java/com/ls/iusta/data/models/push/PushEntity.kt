package com.ls.iusta.data.models.push


data class PushEntity(
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

