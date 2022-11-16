package com.ls.iusta.data.models

data class TicketEntity(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    val characterLocation: TicketLocationEntity,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var isBookMarked: Boolean
)

data class TicketLocationEntity(
    val name: String,
    val url: String
)
