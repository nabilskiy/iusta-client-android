package com.ls.iusta.domain.interactor.user

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.user.UpdatePassRequest
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePassUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<UpdatePassRequest, Flow<Boolean>> {
    override suspend fun invoke(data: UpdatePassRequest): Flow<Boolean> =
        userRepository.updatePassword(
            data.old_password,
            data.new_password,
            data.new_password_confirmation
        )
}