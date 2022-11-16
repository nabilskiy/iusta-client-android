package com.ls.iusta.data.repository

import com.ls.iusta.data.models.TicketEntity

interface TicketDataSource {
    // Remote and cache
    suspend fun getTickets(): List<TicketEntity>
    suspend fun getTicket(ticketId: Long): TicketEntity

    // Cache
    suspend fun saveTicket(listTickets: List<TicketEntity>)
    suspend fun getBookMarkedTickets(): List<TicketEntity>
    suspend fun setTicketBookmarked(ticketId: Long): Int
    suspend fun setTicketUnBookMarked(ticketId: Long): Int
    suspend fun isCached(): Boolean
}
