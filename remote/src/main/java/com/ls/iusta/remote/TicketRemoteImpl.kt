package com.ls.iusta.remote

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.remote.api.TicketService
import com.ls.iusta.remote.mappers.TicketEntityMapper
import javax.inject.Inject

class TicketRemoteImpl @Inject constructor(
    private val ticketService: TicketService,
    private val ticketEntityMapper: TicketEntityMapper
) : TicketRemote {
    override suspend fun getTickets(): List<TicketEntity> =
        ticketService.getTickets().tikets.map {
            ticketEntityMapper.mapFromModel(it)
        }

    override suspend fun getTicket(ticketId: Long): TicketEntity =
        ticketEntityMapper.mapFromModel(ticketService.getTicket(ticketId))
}