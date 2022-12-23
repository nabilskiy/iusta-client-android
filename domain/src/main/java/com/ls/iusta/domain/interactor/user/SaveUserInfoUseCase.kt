package com.ls.iusta.domain.interactor.user

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.user.SaveUserRequest
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<SaveUserRequest, Flow<Boolean>> {
    override suspend fun invoke(data: SaveUserRequest): Flow<Boolean> =
        userRepository.editUserInfo(
            data.firstname,
            data.lastname,
            data.middlename,
            data.phone_number,
            data.birthday,
            data.email,
            data.il_customer_id,
            data.language
        )
}