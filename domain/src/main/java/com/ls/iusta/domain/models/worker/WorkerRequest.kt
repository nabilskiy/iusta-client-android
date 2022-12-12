package com.ls.iusta.domain.models.worker

data class WorkerRequest(
    val id: Int,
    var auth_token: String?
)
