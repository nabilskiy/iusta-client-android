package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.auth.Login
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // Remote and cache
    suspend fun login(email: String, password: String, secret_key: String): Flow<Login>

    // Cache
}