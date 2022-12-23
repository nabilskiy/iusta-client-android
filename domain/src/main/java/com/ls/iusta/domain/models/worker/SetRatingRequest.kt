package com.ls.iusta.domain.models.worker

data class SetRatingRequest(
    val worker_rating: Int,
    val ticket_id: Long,
    var ticket_note : String?
)
