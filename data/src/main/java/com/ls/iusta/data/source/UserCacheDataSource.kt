package com.ls.iusta.data.source

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.models.UserEntity
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.UserCache
import com.ls.iusta.data.repository.UserDataSource
import javax.inject.Inject

class UserCacheDataSource @Inject constructor(
    private val userCache: UserCache
) : UserDataSource {

    override suspend fun login(email: String, password: String): LoginEntity =
        userCache.login(email, password)

    override suspend fun setAuthToken(authToken: String?) = userCache.setAuthToken(authToken)

    override suspend fun userInfo(authToken: String?): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthToken(): String? {
        TODO("Not yet implemented")
    }
}