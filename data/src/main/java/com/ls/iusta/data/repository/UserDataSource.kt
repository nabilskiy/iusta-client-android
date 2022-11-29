package com.ls.iusta.data.repository

import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.UserEntity

interface UserDataSource {
    // Remote and cache
    suspend fun login(email: String, password: String): LoginEntity
    suspend fun register(
        username: String, password: String, password_confirmation: String, firstname: String,
        lastname: String, middlename: String, phone_number: String, birthday: String,
        email: String, il_customer_id: String, language: String
    ): Boolean

    suspend fun setAuthToken(authToken: String?)
    suspend fun getAuthToken(): String?
    suspend fun userInfo(authToken: String?): UserEntity
    suspend fun customers(query: String): List<CustomerEntity>

    suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String,
        auth_token: String
    ): Boolean

    suspend fun resetPassword(email: String): Boolean
    suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String
    ): Boolean

    suspend fun logout(auth_token: String): Boolean


    suspend fun about(auth_token: String): Boolean
    suspend fun faq(lang: String, auth_token: String): Boolean
    suspend fun terms(lang: String, auth_token: String): Boolean

}
