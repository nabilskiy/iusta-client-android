package com.ls.iusta.domain.models.category

data class CreateTicketRequest(
    val category_id: Int,
    var auth_token: String?
)
