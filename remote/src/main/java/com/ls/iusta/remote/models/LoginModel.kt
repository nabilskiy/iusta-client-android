package com.ls.iusta.remote.models


data class LoginModel (
    val auth_token: String?,
    var success: Boolean,
    val message: String?,
    val error: String?
)
