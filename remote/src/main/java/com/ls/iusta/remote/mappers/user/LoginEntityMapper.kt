package com.ls.iusta.remote.mappers.user

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.user.LoginModel
import javax.inject.Inject

class LoginEntityMapper @Inject constructor(
) : EntityMapper<LoginModel, LoginEntity> {
    override fun mapFromModel(model: LoginModel): LoginEntity {
        return LoginEntity(
            auth_token = model.auth_token,
            success = model.success,
            message = model.message,
            error = model.error
        )
    }
}
