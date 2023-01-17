package com.ls.iusta.cache.mapper

import com.ls.iusta.cache.models.LoginCacheEntity
import com.ls.iusta.data.models.user.LoginEntity
import javax.inject.Inject

class LoginCacheMapper @Inject constructor(

) : CacheMapper<LoginCacheEntity, LoginEntity> {

    override fun mapFromCached(type: LoginCacheEntity): LoginEntity {
        TODO("Not yet implemented")
    }

    override fun mapToCached(type: LoginEntity): LoginCacheEntity {
        TODO("Not yet implemented")
    }
}
