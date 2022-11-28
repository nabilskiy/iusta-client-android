package com.ls.iusta.remote

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.UserEntity
import com.ls.iusta.data.repository.UserRemote
import com.ls.iusta.remote.api.UserService
import com.ls.iusta.remote.mappers.LoginEntityMapper
import com.ls.iusta.remote.mappers.UserEntityMapper
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val userService: UserService,
    private val loginEntityMapper: LoginEntityMapper,
    private val userEntityMapper: UserEntityMapper
) : UserRemote {

    override suspend fun login(email: String, password: String): LoginEntity =
        loginEntityMapper.mapFromModel(userService.login(email, password))

    override suspend fun setAuthToken(authToken: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun userInfo(authToken: String?): UserEntity =
        userEntityMapper.mapFromModel(userService.userInfo(authToken))

}