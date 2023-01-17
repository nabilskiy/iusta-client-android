package com.ls.iusta.domain.interactor.auth

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.RegRequest
import com.ls.iusta.domain.models.user.Base
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<RegRequest, Flow<Base>> {
    override suspend fun invoke(data: RegRequest): Flow<Base> =
        userRepository.register(
            data.username, data.password, data.password_confirmation,
            data.firstname, data.lastname, data.middlename, data.phone_number, data.birthday,
            data.email, data.il_customer_id, data
                .language
        )
}