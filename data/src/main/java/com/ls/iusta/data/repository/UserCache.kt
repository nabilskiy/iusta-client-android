package com.ls.iusta.data.repository

import com.ls.iusta.data.models.user.LoginEntity

interface UserCache {
    suspend fun login(email: String, password: String): LoginEntity
    suspend fun getLoginData(): LoginEntity
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
    suspend fun setAuthToken(authToken: String?)
    suspend fun getAuthToken(): String?
}
