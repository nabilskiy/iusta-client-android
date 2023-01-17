package com.ls.iusta.data.source

import com.ls.iusta.data.models.category.CategoryInfoEntity
import com.ls.iusta.data.models.ticket.AttachmentFileEntity
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.data.models.ticket.TicketEntity
import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.data.models.worker.WorkerEntity
import com.ls.iusta.data.repository.TicketDataSource
import com.ls.iusta.data.repository.TicketRemote
import javax.inject.Inject

class TicketRemoteDataSource @Inject constructor(
    private val ticketRemote: TicketRemote
) : TicketDataSource {
    override suspend fun getTickets(
        ticket_status: Boolean,
        auth_token: String?,
        pageNumber: Int?
    ): List<TicketEntity> =
        ticketRemote.getTickets(ticket_status, auth_token, pageNumber)

    override suspend fun getTicket(
        auth_token: String?,
        ticketId: Long
    ): TicketEntity =
        ticketRemote.getTicket(auth_token, ticketId)


    override suspend fun categories(menu_id: Int, auth_token: String?): CategoryInfoEntity =
        ticketRemote.categories(menu_id, auth_token)

    override suspend fun createTicket(
        attachments: List<AttachmentFileEntity>,
        category_id: Int,
        note: String?,
        auth_token: String?
    ): ShortTicketEntity =
        ticketRemote.createTicket(attachments, category_id, note, auth_token)

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): ShortTicketEntity =
        ticketRemote.addNoteTicket(ticketId, ticketNote, auth_token)

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): String =
        ticketRemote.getNoteTicket(ticketId, ticketNote, auth_token)

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): Boolean =
        ticketRemote.addRating(workerRating, ticketId, ticketNote, auth_token)

    override suspend fun getRating(ticketId: Long, auth_token: String?): RatingEntity =
        ticketRemote.getRating(ticketId, auth_token)

    override suspend fun worker(id: Int, auth_token: String?): WorkerEntity =
        ticketRemote.worker(id, auth_token)

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