package com.ls.iusta.cache.mapper

import com.ls.iusta.cache.models.LoginCacheEntity
import com.ls.iusta.cache.models.TicketCacheEntity
import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity
import javax.inject.Inject

class LoginCacheMapper @Inject constructor(

) : CacheMapper<LoginCacheEntity, LoginEntity> {

    override fun mapFromCached(type: LoginCacheEntity): LoginEntity {
        return LoginEntity(
            auth_token = type.auth_token,
            success = type.success,
            message = type.message,
            error = type.error
        )
    }

    override fun mapToCached(type: LoginEntity): LoginCacheEntity {
        return LoginCacheEntity(
            auth_token = type.auth_token,
            success = type.success,
            message = type.message,
            error = type.error
        )
    }
}
