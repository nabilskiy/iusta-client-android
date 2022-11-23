package com.ls.iusta.data.source

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.UserCache
import com.ls.iusta.data.repository.UserDataSource
import javax.inject.Inject

class UserCacheDataSource @Inject constructor(
    private val userCache: UserCache
) : UserDataSource {

    override suspend fun login(email: String, password: String, secret_key: String): LoginEntity =
        userCache.login(email, password, secret_key)

}