package com.ls.iusta.remote

import com.ls.iusta.data.models.*
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.remote.api.TicketService
import com.ls.iusta.remote.mappers.category.CategoryEntityMapper
import com.ls.iusta.remote.mappers.category.CategoryInfoEntityMapper
import com.ls.iusta.remote.mappers.ticket.ShortTicketEntityMapper
import com.ls.iusta.remote.mappers.ticket.TicketEntityMapper
import com.ls.iusta.remote.mappers.worker.RatingEntityMapper
import com.ls.iusta.remote.mappers.worker.WorkerEntityMapper
import javax.inject.Inject

class TicketRemoteImpl @Inject constructor(
    private val ticketService: TicketService,
    private val ticketEntityMapper: TicketEntityMapper,
    private val categoryInfoEntityMapper: CategoryInfoEntityMapper,
    private val shortTicketEntityMapper: ShortTicketEntityMapper,
    private val workerEntityMapper: WorkerEntityMapper,
    private val ratingEntityMapper: RatingEntityMapper
) : TicketRemote {
    override suspend fun getTickets(
        ticket_status: String,
        auth_token: String?
    ): List<TicketEntity> =
        ticketService.getTickets(ticket_status, auth_token).response.map {
            ticketEntityMapper.mapFromModel(it)
        }

    override suspend fun getTicket(
        ticket_status: String,
        auth_token: String?,
        ticketId: Long
    ): TicketEntity =
        ticketEntityMapper.mapFromModel(
            ticketService.getTicket(
                ticket_status,
                auth_token,
                ticketId
            ).response[0]
        )

    override suspend fun categories(menu_id: String, auth_token: String?): CategoryInfoEntity =
        categoryInfoEntityMapper.mapFromModel(
            ticketService.categories(
                menu_id,
                auth_token
            ).response
        )

    override suspend fun createTicket(category_id: String, auth_token: String?): ShortTicketEntity =
        shortTicketEntityMapper.mapFromModel(
            ticketService.createTicket(
                category_id,
                auth_token
            ).response
        )

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): ShortTicketEntity =
        shortTicketEntityMapper.mapFromModel(
            ticketService.ticketNote(
                ticketId,
                ticketNote,
                auth_token
            ).response
        )

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): String =
        ticketService.ticketNoteInfo(ticketId, ticketNote, auth_token).response

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Boolean =
        ticketService.addRating(workerRating, ticketId, ticketNote, auth_token).success

    override suspend fun getRating(ticketId: Long, auth_token: String?): RatingEntity =
        ratingEntityMapper.mapFromModel(ticketService.getRating(ticketId,auth_token).response)

    override suspend fun worker(id: Int, auth_token: String?): WorkerEntity =
        workerEntityMapper.mapFromModel(ticketService.worker(id, auth_token).response)
}