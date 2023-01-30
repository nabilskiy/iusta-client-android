package com.ls.iusta.data.repository

import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.data.models.ticket.TicketEntity

interface TicketCache {
    suspend fun getTickets(): GetTicketEntity
    suspend fun getTicket(ticketId: Long): GetTicketEntity
    suspend fun saveTickets(listTickets: List<TicketEntity>)
    suspend fun getBookMarkedTickets(): List<TicketEntity>
    suspend fun setTicketBookmarked(ticketId: Long): Int
    suspend fun setTicketUnBookMarked(ticketId: Long): Int
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
