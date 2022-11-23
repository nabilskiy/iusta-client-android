package com.ls.iusta.domain.models.auth

data class LoginRequest(
    val email: String,
    var password: String,
    val secret_key: String
)
