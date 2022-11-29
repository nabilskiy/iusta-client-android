package com.ls.iusta.data

import com.ls.iusta.data.mapper.LoginMapper
import com.ls.iusta.data.mapper.UserMapper
import com.ls.iusta.data.source.UserDataSourceFactory
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSourceFactory: UserDataSourceFactory,
    private val loginMapper: LoginMapper,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun login(email: String, password: String): Flow<Login> = flow {
        var login = userDataSourceFactory.getRemoteDataSource().login(email, password)
        userDataSourceFactory.getCacheDataSource().setAuthToken(login.auth_token)
        emit(loginMapper.mapFromEntity(login))
    }

    override suspend fun userInfo(): Flow<User> = flow {
        var user = userDataSourceFactory.getRemoteDataSource().userInfo(userDataSourceFactory.getAuthToken())
        emit(userMapper.mapFromEntity(user))
    }

    override suspend fun isUserLogged(): Flow<Boolean?> = flow {
        var isLogged = userDataSourceFactory.getAuthToken()?.isNotEmpty()
        emit(isLogged)
    }

    override suspend fun getAuthToken(): Flow<String?>  = flow {
        var token = userDataSourceFactory.getAuthToken()
        emit(token)
    }
}
