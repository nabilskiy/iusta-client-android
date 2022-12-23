package com.ls.iusta.domain.models.user

data class UpdatePassRequest(
    val old_password: String,
    val new_password: String,
    val new_password_confirmation: String
)
