package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.tickets.*
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    // Remote and cache
    suspend fun getTickets(ticket_status: Boolean, pageNumber: Int?): Flow<GetTicket>
    suspend fun getTicket(ticketId: Long): Flow<GetTicket>


    suspend fun categories(menu_id: Int): Flow<CategoryInfo>
    suspend fun createTicket(
        attachments: List<AttachmentFile>,
        category_id: Int,
        note: String?
    ): Flow<CreateTicket>

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