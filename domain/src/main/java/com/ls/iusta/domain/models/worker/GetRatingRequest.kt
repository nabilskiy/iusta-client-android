package com.ls.iusta.domain.models.worker

data class GetRatingRequest(
    val ticket_id: Long,
    var auth_token: String?
)
