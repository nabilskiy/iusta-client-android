package com.ls.iusta.domain.models.user

data class User(
    var success: Boolean?,
    val message: String?,
    val error: String?,
    val id: Int?,
    val customer_id: Int?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val username: String?,
    val middlename: String?,
    val birthday: String?,
    val language: String?,
    val phone_number: String?,
    val customer_name: String?
)
