package com.ls.iusta.domain.interactor.auth

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPassUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<String, Flow<Boolean>> {
    override suspend fun invoke(pass: String): Flow<Boolean> =
        userRepository.resetPassword(pass)
}