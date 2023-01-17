package com.ls.iusta.data.mapper.user

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.domain.models.auth.Login
import javax.inject.Inject

class LoginMapper @Inject constructor(
) : Mapper<LoginEntity, Login> {

    override fun mapFromEntity(type: LoginEntity): Login {
        return Login(
            auth_token = type.auth_token,
            success = type.success,
            message = type.message?.entries?.iterator()?.next()?.value?.get(0),
            error = type.error
        )
    }



    override fun mapToEntity(type: Login): LoginEntity {
        return LoginEntity(
            auth_token = type.auth_token,
            success = type.success,
            message = null,
            error = type.error
        )
    }
}
