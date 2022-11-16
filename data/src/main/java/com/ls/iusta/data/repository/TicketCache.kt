package com.ls.iusta.data.repository

import com.ls.iusta.data.models.TicketEntity

interface TicketCache {
    suspend fun getTickets(): List<TicketEntity>
    suspend fun getTicket(ticketId: Long): TicketEntity
    suspend fun saveTickets(listTickets: List<TicketEntity>)
    suspend fun getBookMarkedTickets(): List<TicketEntity>
    suspend fun setTicketBookmarked(ticketId: Long): Int
    suspend fun setTicketUnBookMarked(ticketId: Long): Int
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
