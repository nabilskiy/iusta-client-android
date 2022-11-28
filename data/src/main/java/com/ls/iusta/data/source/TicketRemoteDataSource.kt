package com.ls.iusta.data.source

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.TicketRemote
import javax.inject.Inject

class TicketRemoteDataSource @Inject constructor(
    private val ticketRemote: TicketRemote
) : TicketDataSource {
    override suspend fun getTickets(ticket_status: String, auth_token: String): List<TicketEntity> =
        ticketRemote.getTickets(ticket_status, auth_token)

    override suspend fun getTicket(ticket_status: String, auth_token: String, ticketId: Long): TicketEntity =
        ticketRemote.getTicket(ticket_status, auth_token, ticketId)

    override suspend fun saveTicket(listTickets: List<TicketEntity>) {
        throw UnsupportedOperationException("Save ticket is not supported for RemoteDataSource.")
    }

    override suspend fun getBookMarkedTickets(): List<TicketEntity> {
        throw UnsupportedOperationException("Get bookmark ticket is not supported for RemoteDataSource.")
    }

    override suspend fun setTicketBookmarked(ticketId: Long): Int {
        throw UnsupportedOperationException("Set bookmark ticket is not supported for RemoteDataSource.")
    }

    override suspend fun setTicketUnBookMarked(ticketId: Long): Int {
        throw UnsupportedOperationException("Set UnBookmark ticket is not supported for RemoteDataSource.")
    }

    override suspend fun isCached(): Boolean {
        throw UnsupportedOperationException("Cache is not supported for RemoteDataSource.")
    }
}