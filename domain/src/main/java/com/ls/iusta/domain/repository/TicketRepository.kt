package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    // Remote and cache
    suspend fun getTickets(): Flow<List<Ticket>>
    suspend fun getTicket(ticketId: Long): Flow<Ticket>

    // Cache
    suspend fun saveTickets(listTickets: List<Ticket>)
    suspend fun getBookMarkedTickets(): Flow<List<Ticket>>
    suspend fun setTicketBookmarked(ticketId: Long): Flow<Int>
    suspend fun setTicketUnBookMarked(ticketId: Long): Flow<Int>
}