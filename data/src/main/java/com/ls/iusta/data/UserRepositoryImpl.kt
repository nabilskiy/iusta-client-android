package com.ls.iusta.data

import com.ls.iusta.data.mapper.LoginMapper
import com.ls.iusta.data.source.UserDataSourceFactory
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSourceFactory: UserDataSourceFactory,
    private val loginMapper: LoginMapper
) : UserRepository {

    override suspend fun login(email: String, password: String, secret_key: String): Flow<Login> = flow {
        var login = userDataSourceFactory.getRemoteDataSource().login(email, password, secret_key)
        emit(loginMapper.mapFromEntity(login))
    }

}
