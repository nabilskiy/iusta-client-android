package com.ls.iusta.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ls.iusta.cache.models.TicketCacheEntity

@Dao
interface TicketDao {

    @Query("SELECT * FROM tickets")
    fun getTickets(): List<TicketCacheEntity>

    @Query("SELECT * FROM tickets WHERE  id = :id")
    fun getTicket(id: Long): TicketCacheEntity

    @Query("SELECT * FROM tickets WHERE is_bookmarked = 1")
    fun getBookMarkedTickets(): List<TicketCacheEntity>

    @Query("DELETE FROM tickets")
    fun clearTickets(): Int

    @Query("UPDATE tickets SET is_bookmarked = 1 WHERE id = :id")
    fun bookmarkTicket(id: Long): Int

    @Query("UPDATE tickets SET is_bookmarked = 0 WHERE id = :id")
    fun unBookmarkTicket(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTicket(vararg ticket: TicketCacheEntity) /* Accept either single item or list*/
}
