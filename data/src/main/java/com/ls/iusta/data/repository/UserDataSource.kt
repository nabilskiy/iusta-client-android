package com.ls.iusta.data.repository

import com.ls.iusta.data.models.LoginEntity

interface UserDataSource {
    // Remote and cache
    suspend fun login(email: String, password: String, secret_key: String): LoginEntity

}
