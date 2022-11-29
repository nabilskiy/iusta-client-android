package com.ls.iusta.domain.models.auth

data class RegRequest(
    val username: String,
    val password: String,
    val password_confirmation: String,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val phone_number: String,
    val birthday: String,
    val email: String,
    val il_customer_id: String,
    val language: String
)
