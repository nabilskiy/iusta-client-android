package com.ls.iusta.data.repository

import com.ls.iusta.data.models.category.CategoryInfoEntity
import com.ls.iusta.data.models.ticket.AttachmentFileEntity
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.data.models.ticket.TicketEntity
import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.data.models.worker.WorkerEntity

interface TicketDataSource {
    // Remote and cache
    suspend fun getTickets(ticket_status: String, auth_token: String?): List<TicketEntity>
    suspend fun getTicket(ticket_status: String, auth_token: String?, ticketId: Long): TicketEntity

    suspend fun categories(menu_id: Int, auth_token: String?): CategoryInfoEntity
    suspend fun createTicket(attachments: List<AttachmentFileEntity>, category_id: Int, note: String?, auth_token: String?): ShortTicketEntity
    suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): ShortTicketEntity

    suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): String

    suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String?,
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


    // Cache
    suspend fun saveTicket(listTickets: List<TicketEntity>)
    suspend fun getBookMarkedTickets(): List<TicketEntity>
    suspend fun setTicketBookmarked(ticketId: Long): Int
    suspend fun setTicketUnBookMarked(ticketId: Long): Int
    suspend fun isCached(): Boolean
}
