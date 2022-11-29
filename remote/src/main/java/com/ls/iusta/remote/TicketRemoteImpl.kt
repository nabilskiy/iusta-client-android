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
    override suspend fun getTickets(ticket_status: String, auth_token: String?): List<TicketEntity> =
        ticketService.getTickets(ticket_status, auth_token).response.map {
            ticketEntityMapper.mapFromModel(it)
        }

    override suspend fun getTicket(ticket_status: String, auth_token: String?, ticketId: Long): TicketEntity =
        ticketEntityMapper.mapFromModel(ticketService.getTicket(ticket_status, auth_token, ticketId).response[0])
}