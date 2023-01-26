package com.ls.iusta.remote

import com.ls.iusta.data.models.category.CategoryInfoEntity
import com.ls.iusta.data.models.push.PushEntity
import com.ls.iusta.data.models.ticket.*
import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.data.models.worker.WorkerEntity
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.remote.api.TicketService
import com.ls.iusta.remote.mappers.category.CategoryInfoEntityMapper
import com.ls.iusta.remote.mappers.ticket.*
import com.ls.iusta.remote.mappers.worker.RatingEntityMapper
import com.ls.iusta.remote.mappers.worker.WorkerEntityMapper
import javax.inject.Inject

class TicketRemoteImpl @Inject constructor(
    private val ticketService: TicketService,
    private val getTicketEntityMapper: GetTicketEntityMapper,
    private val categoryInfoEntityMapper: CategoryInfoEntityMapper,
    private val createTicketEntityMapper: CreateTicketEntityMapper,
    private val shortTicketEntityMapper: ShortTicketEntityMapper,
    private val workerEntityMapper: WorkerEntityMapper,
    private val ratingEntityMapper: RatingEntityMapper,
    private val attachmentFileEntityMapper: AttachmentFileEntityMapper
) : TicketRemote {
    override suspend fun getTickets(
        ticket_status: Boolean,
        auth_token: String?,
        pageNumber: Int?
    ): GetTicketEntity =
        if (ticket_status)
            getTicketEntityMapper.mapFromModel(ticketService.getActiveTickets(auth_token, pageNumber))
        else
            getTicketEntityMapper.mapFromModel(ticketService.getTickets(auth_token, pageNumber))


    override suspend fun getTicket(
        auth_token: String?,
        ticketId: Long
    ): GetTicketEntity = getTicketEntityMapper.mapFromModel(
        ticketService.getTicket(
            auth_token,
            ticketId
        )
    )

    override suspend fun categories(menu_id: Int, auth_token: String?): CategoryInfoEntity =
        categoryInfoEntityMapper.mapFromModel(
            ticketService.categories(
                menu_id,
                auth_token
            ).response
        )

    override suspend fun createTicket(
        attachments: List<AttachmentFileEntity>,
        category_id: Int,
        note: String?,
        auth_token: String?
    ): CreateTicketEntity =
        createTicketEntityMapper.mapFromModel(
            ticketService.createTicket(
                attachments.map {
                    attachmentFileEntityMapper.mapFromModel(it)
                },
                category_id,
                note,
                auth_token
            )
        )

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): ShortTicketEntity =
        ticketService.ticketNote(
            ticketId,
            ticketNote,
            auth_token
        ).response?.let {
            shortTicketEntityMapper.mapFromModel(
                it
            )
        }!!

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): String =
        ticketService.ticketNoteInfo(ticketId, ticketNote, auth_token).response

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String?,
        auth_token: String?
    ): Boolean =
        ticketService.addRating(workerRating, ticketId, ticketNote, auth_token).success

    override suspend fun getRating(ticketId: Long, auth_token: String?): RatingEntity =
        ratingEntityMapper.mapFromModel(ticketService.getRating(ticketId, auth_token).response)

    override suspend fun worker(id: Int, auth_token: String?): WorkerEntity =
        workerEntityMapper.mapFromModel(ticketService.worker(id, auth_token).response)

}