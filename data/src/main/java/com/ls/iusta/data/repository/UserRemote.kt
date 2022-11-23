package com.ls.iusta.data.repository

import com.ls.iusta.data.models.LoginEntity
import com.ls.iusta.data.models.TicketEntity

interface UserRemote {
    suspend fun login(email: String, password: String, secret_key: String): LoginEntity
}
