package com.ls.iusta.data.repository

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.UserEntity

interface UserDataSource {
    // Remote and cache
    suspend fun login(email: String, password: String): LoginEntity
    suspend fun setAuthToken(authToken: String?)
    suspend fun getAuthToken(): String?
    suspend fun userInfo(authToken: String?): UserEntity

}
