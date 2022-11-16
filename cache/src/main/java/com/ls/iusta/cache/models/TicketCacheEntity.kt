package com.ls.iusta.cache.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ls.iusta.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.Ticket.TICKET_TABLE_NAME)
data class TicketCacheEntity(
    val created: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    @Embedded /* It is a nested field */
    val characterLocation: TicketLocationCacheEntity,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    @ColumnInfo(name = "is_bookmarked")
    var isBookMarked: Boolean
)

@Entity(tableName = CacheConstants.Ticket.TICKET_LOCATION_TABLE_NAME)
data class TicketLocationCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "location")
    val name: String,
    @ColumnInfo(name = "location_url")
    val url: String
)