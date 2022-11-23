package com.ls.iusta.data.repository

import com.ls.iusta.data.models.TicketEntity

interface TicketRemote {
    suspend fun getTickets(ticket_status: String, auth_token: String, secret_key: String): List<TicketEntity>
    suspend fun getTicket(ticket_status: String, auth_token: String, secret_key: String, ticketId: Long): TicketEntity
}
