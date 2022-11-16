package com.ls.iusta.data.repository

import com.ls.iusta.data.models.TicketEntity

interface TicketRemote {
    suspend fun getTickets(): List<TicketEntity>
    suspend fun getTicket(ticketId: Long): TicketEntity
}
