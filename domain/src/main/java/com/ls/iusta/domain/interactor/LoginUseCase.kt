package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<LoginRequest, Flow<Login>> {
    override suspend fun invoke(data: LoginRequest): Flow<Login> =
        userRepository.login(data.email, data.password)
}