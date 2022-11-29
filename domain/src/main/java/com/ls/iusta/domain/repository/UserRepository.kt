package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // Remote and cache
    suspend fun login(email: String, password: String): Flow<Login>
    suspend fun userInfo(): Flow<User>
    suspend fun getAuthToken(): Flow<String?>
    suspend fun isUserLogged(): Flow<Boolean?>

    // Cache
}