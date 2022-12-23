package com.ls.iusta.data.source

import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.UserCache
import javax.inject.Inject

class TicketDataSourceFactory @Inject constructor(
    private val ticketCache: TicketCache,
    private val userCache: UserCache,
    private val ticketCacheDataSource: TicketCacheDataSource,
    private val ticketRemoteDataSource: TicketRemoteDataSource
) {
    open suspend fun getDataStore(isCached: Boolean): TicketDataSource {
        return if (isCached && !ticketCache.isExpired()) {
            return getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): TicketDataSource {
        return ticketRemoteDataSource
    }

    fun getCacheDataSource(): TicketDataSource {
        return ticketCacheDataSource
    }

    open suspend fun getAuthToken(): String? {
        return userCache.getAuthToken()
    }
}