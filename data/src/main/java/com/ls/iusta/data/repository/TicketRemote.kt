package com.ls.iusta.data.repository

import com.ls.iusta.data.models.category.CategoryInfoEntity
import com.ls.iusta.data.models.push.PushEntity
import com.ls.iusta.data.models.ticket.*
import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.data.models.worker.WorkerEntity


interface TicketRemote {
    suspend fun getTickets(
        ticket_status: Boolean,
        auth_token: String?,
        pageNumber: Int?
    ): GetTicketEntity

    suspend fun getTicket(auth_token: String?, ticketId: Long): GetTicketEntity

    suspend fun categories(menu_id: Int, auth_token: String?): CategoryInfoEntity
    suspend fun createTicket(
        attachments: List<AttachmentFileEntity>,
        category_id: Int,
        note: String?,
        auth_token: String?
    ): CreateTicketEntity

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

}
