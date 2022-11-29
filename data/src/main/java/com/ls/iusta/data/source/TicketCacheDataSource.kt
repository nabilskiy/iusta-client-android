package com.ls.iusta.data.source

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import javax.inject.Inject

class TicketCacheDataSource @Inject constructor(
    private val ticketCache: TicketCache
) : TicketDataSource {
    override suspend fun getTickets(ticket_status: String, auth_token: String?): List<TicketEntity> =
        ticketCache.getTickets()

    override suspend fun getTicket(ticket_status: String, auth_token: String?, ticketId: Long): TicketEntity =
        ticketCache.getTicket(ticketId)

    override suspend fun saveTicket(listTickets: List<TicketEntity>) =
        ticketCache.saveTickets(listTickets)

    override suspend fun getBookMarkedTickets(): List<TicketEntity> =
        ticketCache.getBookMarkedTickets()

    override suspend fun setTicketBookmarked(ticketId: Long): Int =
        ticketCache.setTicketBookmarked(ticketId)

    override suspend fun setTicketUnBookMarked(ticketId: Long): Int =
        ticketCache.setTicketUnBookMarked(ticketId)

    override suspend fun isCached(): Boolean =
        ticketCache.isCached()
}