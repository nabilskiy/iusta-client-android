package com.ls.iusta.data.models

data class LoginEntity(
    val auth_token: String?,
    var success: Boolean,
    val message: String?,
    val error: String?,
)
