package com.ls.iusta.data.mapper

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.domain.models.auth.Login
import javax.inject.Inject

class LoginMapper @Inject constructor(
) : Mapper<LoginEntity, Login> {

    override fun mapFromEntity(type: LoginEntity): Login {
        return Login(
            auth_token = type.auth_token,
            success = type.success,
            message = type.message,
            error = type.error
        )
    }

    override fun mapToEntity(type: Login): LoginEntity {
        return LoginEntity(
            auth_token = type.auth_token,
            success = type.success,
            message = type.message,
            error = type.error
        )
    }
}
