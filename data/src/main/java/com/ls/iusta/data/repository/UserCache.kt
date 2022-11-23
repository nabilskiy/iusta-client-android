package com.ls.iusta.data.repository

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity

interface UserCache {
    suspend fun login(email: String, password: String, secret_key: String): LoginEntity
    suspend fun getLoginData(): LoginEntity
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
