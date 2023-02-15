package com.ls.iusta.domain.interactor.auth

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.user.Base
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPassUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<String, Flow<Base>> {
    override suspend fun invoke(pass: String): Flow<Base> =
        userRepository.resetPassword(pass)
}