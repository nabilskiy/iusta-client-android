package com.ls.iusta.cache.datasourceImpl

import com.ls.iusta.cache.dao.UserDao
import com.ls.iusta.cache.datasourceImpl.TicketCacheImpl.Companion.EXPIRATION_TIME
import com.ls.iusta.cache.mapper.LoginCacheMapper
import com.ls.iusta.cache.utils.CachePreferencesHelper
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.data.repository.UserCache
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    private val userDao: UserDao,
    private val loginCacheMapper: LoginCacheMapper,
    private val cachePreferencesHelper: CachePreferencesHelper
) : UserCache {

    override suspend fun getLoginData(): LoginEntity {
        TODO("Not yet implemented")
//        loginCacheMapper.mapFromCached(userDao.getLoginData())
    }


    override suspend fun login(email: String, password: String): LoginEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthToken(): String? {
        return cachePreferencesHelper.authToken
    }

    override suspend fun isCached(): Boolean =
        userDao.getLoginData().auth_token!!?.isNotEmpty()

    override suspend fun setLastCacheTime(lastCache: Long) {
        cachePreferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override suspend fun setAuthToken(authToken: String?) {
        cachePreferencesHelper.authToken = authToken
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return cachePreferencesHelper.lastCacheTime
    }



    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}