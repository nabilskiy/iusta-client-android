package com.ls.iusta.data.source

import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.UserCache
import com.ls.iusta.data.repository.UserDataSource
import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(
    private val userCache: UserCache,
    private val userCacheDataSource: UserCacheDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    open suspend fun getDataStore(isCached: Boolean): UserDataSource {
        return if (isCached && !userCache.isExpired()) {
            return getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): UserDataSource {
        return userRemoteDataSource
    }

    fun getCacheDataSource(): UserDataSource {
        return userCacheDataSource
    }
}