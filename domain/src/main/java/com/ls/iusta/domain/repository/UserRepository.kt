package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.customer.CustomerResponse
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.info.Faq
import com.ls.iusta.domain.models.info.Terms
import com.ls.iusta.domain.models.push.GetPush
import com.ls.iusta.domain.models.push.Push
import com.ls.iusta.domain.models.user.Base
import com.ls.iusta.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // Remote and cache
    suspend fun login(email: String, password: String): Flow<Login>
    suspend fun register(
        username: String, password: String, password_confirmation: String, firstname: String,
        lastname: String, middlename: String, phone_number: String, birthday: String,
        email: String, il_customer_id: String, language: String
    ): Flow<Base>

    suspend fun userInfo(): Flow<User>
    suspend fun getAuthToken(): Flow<String?>
    suspend fun isUserLogged(): Flow<Boolean?>
    suspend fun customers(query: String): Flow<CustomerResponse>

    suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String?,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String
    ): Flow<Base>

    suspend fun resetPassword(email: String): Flow<Base>
    suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String
    ): Flow<Base>

    suspend fun logout(): Flow<Boolean>
    suspend fun logoutForce(): Flow<Boolean>

    suspend fun about(): Flow<List<About>>
    suspend fun faq(lang: String?): Flow<List<Faq>>
    suspend fun terms(lang: String?): Flow<List<Terms>>

    suspend fun savePushToken(push_token: String): Flow<Boolean>
    suspend fun notifications(page: Int): Flow<GetPush>
    suspend fun readPush(ids: String): Flow<Boolean>
    suspend fun deletePush(ids: String): Flow<Boolean>
}