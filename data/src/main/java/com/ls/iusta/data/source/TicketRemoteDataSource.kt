package com.ls.iusta.data.source

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.TicketRemote
import javax.inject.Inject

class TicketRemoteDataSource @Inject constructor(
    private val ticketRemote: TicketRemote
) : TicketDataSource {
    override suspend fun getTickets(): List<TicketEntity> =
        ticketRemote.getTickets()

    override suspend fun getTicket(ticketId: Long): TicketEntity =
        ticketRemote.getTicket(ticketId)

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