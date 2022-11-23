package com.ls.iusta.remote

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.repository.UserRemote
import com.ls.iusta.remote.api.UserService
import com.ls.iusta.remote.mappers.LoginEntityMapper
import com.ls.iusta.remote.mappers.TicketEntityMapper
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val userService: UserService,
    private val loginEntityMapper: LoginEntityMapper
) : UserRemote {

    override suspend fun login(email: String, password: String, secret_key: String): LoginEntity =
        loginEntityMapper.mapFromModel(userService.login(email, password, secret_key))
}