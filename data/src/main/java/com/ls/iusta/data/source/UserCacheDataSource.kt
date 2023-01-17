package com.ls.iusta.data.source

import com.ls.iusta.data.models.BaseModelEntity
import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.data.models.info.FaqEntity
import com.ls.iusta.data.models.info.TermsEntity
import com.ls.iusta.data.models.push.PushEntity
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.data.models.user.UserEntity
import com.ls.iusta.data.repository.UserCache
import com.ls.iusta.data.repository.UserDataSource
import javax.inject.Inject

class UserCacheDataSource @Inject constructor(
    private val userCache: UserCache
) : UserDataSource {

    override suspend fun login(email: String, password: String): LoginEntity =
        userCache.login(email, password)

    override suspend fun setAuthToken(authToken: String?) = userCache.setAuthToken(authToken)

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
    ): BaseModelEntity {
        TODO("Not yet implemented")
    }

    override suspend fun customers(query: String): List<CustomerEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String?,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String,
        auth_token: String?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout(auth_token: String?): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun about(auth_token: String?): List<AboutEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun faq(lang: String?, auth_token: String?): List<FaqEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun terms(lang: String?): List<TermsEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun userInfo(authToken: String?): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun savePushToken(push_token: String, auth_token: String?): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun notifications(auth_token: String?): List<PushEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun readPush(ids: String, auth_token: String?): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePush(ids: String, auth_token: String?): Boolean {
        TODO("Not yet implemented")
    }
}