package com.ls.iusta.domain.models.auth

data class Login(
    val auth_token: String?,
    var success: Boolean,
    val message: String?,
    val error: String?,
)
