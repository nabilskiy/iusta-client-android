package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Flow<User>> {
    override suspend fun invoke(params: Unit): Flow<User> =
        userRepository.userInfo()
}