package com.ls.iusta.cache.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ls.iusta.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.Ticket.TICKET_TABLE_NAME)
data class TicketCacheEntity(
    val archive_flag: String,
    val create_by : String,
    val create_time : String,
    val customer_id : String,
    val customer_user_id : String?,
    val dynamic_category_id : String,
    val dynamic_category_name :String,
    @PrimaryKey
    val id : String,
    val owner_user_name : String,
    val responsible_user_id :String,
    val service_id : String,
    val ticket_lock_id : String,
    val ticket_priority_id :String,
    val ticket_state_id : String,
    val ticket_state_name : String,
    val title : String,
    val tn : String,
    val type_id : String,
    val user_id : String,
    val category_name : String,
//    @ColumnInfo(name = "is_bookmarked")
//    var isBookMarked: Boolean
)
