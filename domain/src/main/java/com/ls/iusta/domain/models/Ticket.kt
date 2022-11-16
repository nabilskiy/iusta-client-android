package com.ls.iusta.domain.models

data class Ticket(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    val characterLocation: TicketLocation,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var isBookMarked: Boolean
)

data class TicketLocation(
    val name: String,
    val url: String
)