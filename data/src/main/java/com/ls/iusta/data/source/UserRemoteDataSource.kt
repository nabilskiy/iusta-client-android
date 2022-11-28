package com.ls.iusta.data.source

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

    override suspend fun setAuthToken(authToken: String?) = userRemote.setAuthToken(authToken)

    override suspend fun userInfo(authToken: String?): UserEntity =
        userRemote.userInfo(authToken)

    override suspend fun getAuthToken(): String? {
        TODO("Not yet implemented")
    }
}