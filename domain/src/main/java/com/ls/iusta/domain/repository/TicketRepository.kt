package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.tickets.AttachmentFile
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    // Remote and cache
    suspend fun getTickets(ticket_status: String, pageNumber: Int?): Flow<List<Ticket>>
    suspend fun getTicket(ticket_status: String, ticketId: Long): Flow<Ticket>


    suspend fun categories(menu_id: Int): Flow<CategoryInfo>
    suspend fun createTicket(
        attachments: List<AttachmentFile>,
        category_id: Int,
        note: String?
    ): Flow<ShortTicket>

    suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String?
    ): Flow<ShortTicket>

    suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String?
    ): Flow<String>

    suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String?
    ): Flow<Boolean>

    suspend fun getRating(
        ticketId: Long
    ): Flow<Rating>

    suspend fun worker(
        id: Int
    ): Flow<Worker>

}