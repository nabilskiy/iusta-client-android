package com.ls.iusta.data.source

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.data.repository.UserDataSource
import com.ls.iusta.data.repository.UserRemote
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userRemote: UserRemote
) : UserDataSource {

    override suspend fun login(email: String, password: String, secret_key: String): LoginEntity =
        userRemote.login(email, password, secret_key)

}