package com.ls.iusta.remote.models

import com.google.gson.annotations.SerializedName

data class TicketModel(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    @SerializedName("location")
    val characterLocation: TicketLocationModel,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var isBookMarked: Boolean = false
)

data class TicketLocationModel(
    val name: String,
    val url: String
)
