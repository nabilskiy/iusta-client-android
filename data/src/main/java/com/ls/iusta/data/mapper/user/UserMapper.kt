package com.ls.iusta.data.mapper.user

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.user.UserEntity
import com.ls.iusta.domain.models.user.User
import javax.inject.Inject

class UserMapper @Inject constructor(
) : Mapper<UserEntity, User> {

    override fun mapFromEntity(type: UserEntity): User {
        return User(
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
            customer_name = type.customer_name,
            unread_messages = type.unread_messages
        )
    }

    override fun mapToEntity(type: User): UserEntity {
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
            customer_name = type.customer_name,
            unread_messages = type.unread_messages
        )
    }
}
