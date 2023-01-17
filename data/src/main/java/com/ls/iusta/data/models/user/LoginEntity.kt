package com.ls.iusta.data.models.user

data class LoginEntity(
    val auth_token: String?,
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val error: String?,
)
