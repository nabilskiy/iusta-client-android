package com.ls.iusta.cache.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ls.iusta.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.Ticket.USER_TABLE_NAME)
data class LoginCacheEntity(

    val auth_token: String?,
    @PrimaryKey
    @ColumnInfo(name = "success")
    var success: Boolean,
    val message: String?,
    val error: String?
)
