package com.ls.iusta.data.repository

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.data.models.info.FaqEntity
import com.ls.iusta.data.models.info.TermsEntity
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.data.models.user.UserEntity

interface UserRemote {
    suspend fun login(email: String, password: String): LoginEntity
    suspend fun register(
        username: String, password: String, password_confirmation: String, firstname: String,
        lastname: String, middlename: String, phone_number: String, birthday: String,
        email: String, il_customer_id: String, language: String
    ): Boolean

    suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String?,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String,
        auth_token: String?
    ): Boolean

    suspend fun resetPassword(email: String): Boolean
    suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String?
    ): Boolean

    suspend fun logout(auth_token: String?): Boolean
    suspend fun setAuthToken(authToken: String?)
    suspend fun userInfo(authToken: String?): UserEntity
    suspend fun customers(query: String): List<CustomerEntity>

    suspend fun about(auth_token: String?): List<AboutEntity>
    suspend fun faq(lang: String?, auth_token: String?): List<FaqEntity>
    suspend fun terms(lang: String?): List<TermsEntity>

}
