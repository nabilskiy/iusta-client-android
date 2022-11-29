package com.ls.iusta.data.source

import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.models.UserEntity
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.data.repository.UserDataSource
import com.ls.iusta.data.repository.UserRemote
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userRemote: UserRemote
) : UserDataSource {

    override suspend fun login(email: String, password: String): LoginEntity =
        userRemote.login(email, password)

    override suspend fun register(
        username: String,
        password: String,
        password_confirmation: String,
        firstname: String,
        lastname: String,
        middlename: String,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String
    ): Boolean =
        userRemote.register(
            username,
            password,
            password_confirmation,
            firstname,
            lastname,
            middlename,
            phone_number,
            birthday,
            email,
            il_customer_id,
            language
        )

    override suspend fun setAuthToken(authToken: String?) =
        userRemote.setAuthToken(authToken)

    override suspend fun userInfo(authToken: String?): UserEntity =
        userRemote.userInfo(authToken)

    override suspend fun customers(query: String): List<CustomerEntity> =
        userRemote.customers(query)

    override suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String,
        auth_token: String
    ): Boolean = userRemote.editUserInfo(
        firstname,
        lastname,
        middlename,
        phone_number,
        birthday,
        email,
        il_customer_id,
        language,
        auth_token
    )

    override suspend fun resetPassword(email: String): Boolean =
        userRemote.resetPassword(email)

    override suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String
    ): Boolean =
        userRemote.updatePassword(old_password, new_password, new_password_confirmation, auth_token)

    override suspend fun logout(auth_token: String): Boolean =
        userRemote.logout(auth_token)

    override suspend fun about(auth_token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun faq(lang: String, auth_token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun terms(lang: String, auth_token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthToken(): String? {
        TODO("Not yet implemented")
    }
}