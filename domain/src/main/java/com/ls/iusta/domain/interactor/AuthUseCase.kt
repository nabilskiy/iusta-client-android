package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Flow<Boolean?>> {
    override suspend fun invoke(params: Unit): Flow<Boolean?> =
        userRepository.isUserLogged()
}