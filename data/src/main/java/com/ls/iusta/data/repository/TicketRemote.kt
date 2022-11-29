package com.ls.iusta.data.repository

import com.ls.iusta.data.models.*

interface TicketRemote {
    suspend fun getTickets(ticket_status: String, auth_token: String?): List<TicketEntity>
    suspend fun getTicket(ticket_status: String, auth_token: String?, ticketId: Long): TicketEntity

    suspend fun categories(menu_id: String, auth_token: String?): CategoryInfoEntity
    suspend fun createTicket(category_id: String, auth_token: String?): ShortTicketEntity
    suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): ShortTicketEntity

    suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): String

    suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Boolean

    suspend fun getRating(
        ticketId: Long,
        auth_token: String?
    ): RatingEntity

    suspend fun worker(
        id: Int,
        auth_token: String?
    ): WorkerEntity

}
