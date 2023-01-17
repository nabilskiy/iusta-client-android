package com.ls.iusta.remote.models.user


data class LoginModel (
    val auth_token: String?,
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val error: String?
)
