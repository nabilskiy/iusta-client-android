package com.ls.iusta.domain.models.user

data class SaveUserRequest(
    val firstname: String,
    val lastname: String,
    val middlename: String?,
    val phone_number: String,
    val birthday: String,
    val email: String,
    val il_customer_id: String,
    val language: String
)
