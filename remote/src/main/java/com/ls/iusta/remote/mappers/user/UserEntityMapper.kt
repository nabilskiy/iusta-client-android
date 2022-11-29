package com.ls.iusta.remote.mappers.user

import com.ls.iusta.data.models.UserEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.user.UserModel
import javax.inject.Inject

class UserEntityMapper @Inject constructor(
) : EntityMapper<UserModel, UserEntity> {
    override fun mapFromModel(type: UserModel): UserEntity {
        return UserEntity(
            success = type.success,
            message = type.message,
            error = type.error,
            id = type.id,
            customer_id = type.customer_id,
            firstname = type.firstname,
            lastname = type.lastname,
            email = type.email,
            username = type.username,
            middlename = type.middlename,
            birthday = type.birthday,
            language = type.language,
            phone_number = type.phone_number,
            customer_name = type.customer_name
        )
    }
}
