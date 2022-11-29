package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // Remote and cache
    suspend fun login(email: String, password: String): Flow<Login>
    suspend fun register(
        username: String, password: String, password_confirmation: String, firstname: String,
        lastname: String, middlename: String, phone_number: String, birthday: String,
        email: String, il_customer_id: String, language: String
    ): Flow<Boolean>
    suspend fun userInfo(): Flow<User>
    suspend fun getAuthToken(): Flow<String?>
    suspend fun isUserLogged(): Flow<Boolean?>
    suspend fun customers(query: String): Flow<List<Customer>>

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
    ): Flow<Boolean>

    suspend fun resetPassword(email: String): Flow<Boolean>
    suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String
    ): Flow<Boolean>

    suspend fun logout(auth_token: String): Flow<Boolean>


    suspend fun about(auth_token: String): Flow<Boolean>
    suspend fun faq(lang: String, auth_token: String): Flow<Boolean>
    suspend fun terms(lang: String, auth_token: String): Flow<Boolean>




    // Cache
}